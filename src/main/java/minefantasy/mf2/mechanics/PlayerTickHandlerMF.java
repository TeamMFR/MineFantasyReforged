package minefantasy.mf2.mechanics;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.MineFantasyAPI;
import minefantasy.mf2.api.archery.AmmoMechanicsMF;
import minefantasy.mf2.api.archery.IFirearm;
import minefantasy.mf2.api.armour.CogworkArmour;
import minefantasy.mf2.api.heating.IHotItem;
import minefantasy.mf2.api.helpers.ArmourCalculator;
import minefantasy.mf2.api.helpers.PlayerTagData;
import minefantasy.mf2.api.helpers.TacticalManager;
import minefantasy.mf2.api.knowledge.ResearchLogic;
import minefantasy.mf2.api.rpg.RPGElements;
import minefantasy.mf2.api.rpg.SkillList;
import minefantasy.mf2.config.ConfigHardcore;
import minefantasy.mf2.config.ConfigMobs;
import minefantasy.mf2.config.ConfigWeapon;
import minefantasy.mf2.entity.mob.EntityDragon;
import minefantasy.mf2.item.armour.ItemApron;
import minefantasy.mf2.item.food.ItemFoodMF;
import minefantasy.mf2.item.gadget.ItemCrossbow;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.item.weapon.ItemWeaponMF;
import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class PlayerTickHandlerMF
{
	private static Random rand = new Random();
	private static ItemStack lastStack;
	//SPRINT JUMPING
	//DEFAULT:= 0:22 (50seconds till starve, 35s till nosprint) (16m in MC time for 4 missing bars)
	//SLOW=5: = 2:20 (5mins till starve, 3:30 till nosprint) (1h 40m in MC time for 4 missing bars)
	//EXHAUSTION SCALE = 3.0F = 1hunger
	@SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		if(event.phase == TickEvent.Phase.END)
        {
			ItemStack held = event.player.getHeldItem();
	    	if(held != null)
	    	{
	    		int parry = ItemWeaponMF.getParry(held);
	    		if(parry > 0)
	    		{
	    			ItemWeaponMF.setParry(held, parry-1);
	    		}
	    	}
	    	
			//COMMON
	    	TacticalManager.applyArmourWeight(event.player);
        	
        	if(event.player.ticksExisted % 20 == 0)
        	{
        		CogworkArmour.updateVars(event.player);
        		
        		if(event.player.isBurning() && TacticalManager.getResistance(event.player, DamageSource.inFire) <= 0.0F)
            	{
            		event.player.extinguish();
            	}
        		CogworkArmour.tickUser(event.player);
    		}
        	if(event.player.worldObj.isRemote)
        	{
	        	if(isNextStep(event.player))
	        	{
	        		onStep(event.player, event.player.getEntityData().getInteger(stepNBT) % 2 == 0);
	        	}
        	}
        	/*
        	if(RPGElements.isSystemActive)
        	{
        		if(event.player.isSprinting() && event.player.ticksExisted % 10 == 0)
        		{
        			SkillList.athletics.addXP(event.player, 1);
        		}
        		else if(event.player.isSneaking() && TacticalManager.isEntityMoving(event.player) && event.player.ticksExisted % 10 == 0)
        		{
        			SkillList.sneak.addXP(event.player, 1);
        		}
        	}
        	*/
        	//CLIENT
        	if(event.player.worldObj.isRemote)
        	{
        		playSounds(event.player);
        	}
        	//DRAGON EVENT
        	if(!event.player.worldObj.isRemote)
        	{
        		tickDragonSpawner(event.player);
        	}
        	//updatePitch(event.player); (This keeps track of player camera angles, could make a mechanic based on swinging the camera)
        }
		
        if(event.phase == TickEvent.Phase.START)
        {
        	applyBalance(event.player);
        	ItemFoodMF.onTick(event.player);
        	
        	if(!event.player.worldObj.isRemote && !(!ConfigHardcore.HCChotBurn && ItemApron.isUserProtected(event.player)) && event.player.ticksExisted % 100 == 0)
        	{
        		for(int a = 0; a < event.player.inventory.getSizeInventory(); a++)
        		{
        			ItemStack item = event.player.inventory.getStackInSlot(a);
        			if(item != null && item.getItem() instanceof IHotItem)
        			{
        				event.player.setFire(5);
        				event.player.attackEntityFrom(DamageSource.onFire, 1.0F);
        			}
        		}
        	}
        	if(event.player.worldObj.isRemote)
        	{
        		ItemStack item = event.player.getHeldItem();
        		if(lastStack == null && item != null)
        		{
        			if(item.getItem() instanceof IFirearm)
        			{
        				NBTTagCompound nbt = AmmoMechanicsMF.getNBT(item);
        				if(nbt.hasKey(ItemCrossbow.useTypeNBT) && nbt.getString(ItemCrossbow.useTypeNBT).equalsIgnoreCase("fire"))
        				{
        					nbt.setString(ItemCrossbow.useTypeNBT, "null");
        				}
        			}
        		}
        		if(lastStack != null && (item == null || item != lastStack))
        		{
        			if(lastStack.getItem() instanceof IFirearm)
        			{
        				NBTTagCompound nbt = AmmoMechanicsMF.getNBT(lastStack);
        				if(nbt.hasKey(ItemCrossbow.useTypeNBT) && nbt.getString(ItemCrossbow.useTypeNBT).equalsIgnoreCase("fire"))
        				{
        					nbt.setString(ItemCrossbow.useTypeNBT, "null");
        				}
        			}
        		}
        		lastStack = item;
        	}
        	
        	float weight = ArmourCalculator.getTotalWeightOfWorn(event.player, false);
    		if(weight > 100F)
    		{
    			if(event.player.isInWater())
    			{
    				event.player.motionY -= (weight/20000F);
    			}
    		}
        }
    }

	private void onStep(EntityPlayer player, boolean alternateStep) 
	{
		if(CogworkArmour.isWearingAnyCogwork(player))
		{
			String s = alternateStep ? "in" : "out";
			player.playSound("tile.piston."+s, 0.5F, 1.0F);
			float f1 = 2.0F;
			//player.rotationPitch += (alternateStep ? f1 : -f1);
			
			CogworkArmour.onStep(player);
		}
		else if(ArmourCalculator.getTotalWeightOfWorn(player, false) >= 50)
        {
			player.playSound("mob.irongolem.throw", 1.0F, 1.0F);
        }
	}
	private static String stepNBT = "MF_LastStep";
	private boolean isNextStep(EntityPlayer player) 
	{
		int prevStep = player.getEntityData().getInteger(stepNBT);
		int stepcount = (int) player.distanceWalkedOnStepModified;
		player.getEntityData().setInteger(stepNBT, stepcount);
		
		return prevStep != stepcount;
	}
	
	/*
	private static String lastPitchNBT = "MF_last_AimPitch";
	public static void setLastPitch(EntityPlayer user, float value)
	{
		user.getEntityData().setFloat(lastPitchNBT, value);
	}
	public static void updatePitch(EntityPlayer user)
	{
		user.getEntityData().setFloat(lastPitchNBT, user.rotationPitch);
	}
	public static float getPitchMovement(EntityPlayer user)
	{
		float lastPitch = user.getEntityData().getFloat(lastPitchNBT) + 1000F;
		float nowPitch = user.rotationPitch + 1000F;
		
		return nowPitch - lastPitch;
	}
	 */
	private void tickDragonSpawner(EntityPlayer player) 
	{
		if(player.worldObj.difficultySetting != EnumDifficulty.PEACEFUL && player.dimension == 0)
    	{
        	int i = ConfigMobs.dragonInterval;
        	float chance = ConfigMobs.dragonChance;
        	
        	if(this.getDragonEnemyPoints(player) >= 100)
        	{
        		i /= 2;//twice as frequent
        	}
        	if(this.getDragonEnemyPoints(player) >= 50)
        	{
        		chance *= 2;//twice the chance
        	}
        	if(!player.worldObj.isRemote && player.worldObj.getTotalWorldTime() % i == 0 && rand.nextFloat()*100F < chance)
        	{
        		spawnDragon(player);
        	}
    	}
	}
	
	public static void spawnDragon(EntityPlayer player) 
	{
		spawnDragon(player, 64);
	}
	public static void spawnDragon(EntityPlayer player, int offset) 
	{
		int y = (int) (player.posY + offset);
		boolean canMobSpawn = !player.worldObj.isRemote && canDragonSpawnOnPlayer(player, y);
		if(canMobSpawn && !player.worldObj.isRemote)
		{
			int tier = getDragonTier(player);//Gets tier on kills
    		EntityDragon dragon = new EntityDragon(player.worldObj);
    		dragon.setPosition(player.posX, y, player.posZ);
    		player.worldObj.spawnEntityInWorld(dragon);
    		dragon.setDragon(tier);
    		dragon.disengage(100);
    		player.worldObj.playSoundEffect(dragon.posX, dragon.posY-16D, dragon.posZ, "mob.enderdragon.growl", 3.0F, 1.5F);
    		dragon.fireBreathCooldown = 200;
    		
    		if(ConfigMobs.dragonMSG && !player.worldObj.isRemote)
    		{
    			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + StatCollector.translateToLocal("event.dragonnear.name")));
    			
	    		List list = player.worldObj.playerEntities;
	    		Iterator players = list.iterator();
	    		while(players.hasNext())
	    		{
	    			Object instance = players.next();
	    			if(instance != null && instance instanceof EntityPlayer)
	    			{
	    				if(((EntityPlayer)instance).getDistanceToEntity(player) < 256D && instance != player)
	    				{
	    					((EntityPlayer)instance).addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + StatCollector.translateToLocal("event.dragonnear.name")));
	    				}
	    			}
	    		}
    		}
		}
	}

	private static boolean canDragonSpawnOnPlayer(EntityPlayer player, int y) 
	{
		for(int x = -3; x <= 3; x++)
		{
			for(int z = -3; z <= 3; z++)
			{
				if(!player.worldObj.canBlockSeeTheSky((int)player.posX + x, y, (int)player.posZ + z))
				{
					return false;
				}
			}
		}
		return true;
	}

	private void playSounds(EntityPlayer user)
	{
	}
	
	private void applyBalance(EntityPlayer entityPlayer) 
	{
        float weight = 2.0F;
        float pitchBalance = entityPlayer.getEntityData().hasKey("MF_Balance_Pitch") ? entityPlayer.getEntityData().getFloat("MF_Balance_Pitch"): 0F;
        float yawBalance = entityPlayer.getEntityData().hasKey("MF_Balance_Yaw") ? entityPlayer.getEntityData().getFloat("MF_Balance_Yaw"): 0F;
        
        if(pitchBalance > 0)
        {
        	if(pitchBalance < 1.0F && pitchBalance > -1.0F)weight = pitchBalance;
        	pitchBalance -= weight;
        	
        	if(ConfigWeapon.useBalance)
            {
        		entityPlayer.rotationPitch += pitchBalance > 0 ? weight : -weight;
            }
        	
        	if(pitchBalance < 0)pitchBalance = 0;
        }
        if(yawBalance > 0)
        {
        	if(yawBalance < 1.0F && yawBalance > -1.0F)weight = yawBalance;
        	yawBalance -= weight;
        	
        	if(ConfigWeapon.useBalance)
            {
        		MFLogUtil.logDebug("Weapon Balance Move");
        		entityPlayer.rotationYaw += yawBalance > 0 ? weight : -weight;
            }
        	
        	if(yawBalance < 0)yawBalance = 0;
        }
    	entityPlayer.getEntityData().setFloat("MF_Balance_Pitch", pitchBalance);
    	entityPlayer.getEntityData().setFloat("MF_Balance_Yaw", yawBalance);
	}
	
	@SubscribeEvent
	public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
    {
		onPlayerEnterWorld(event.player);
    }
	@SubscribeEvent
	public void onPlayerTravel(PlayerEvent.PlayerChangedDimensionEvent event)
    {
		onPlayerEnterWorld(event.player);
    }
	@SubscribeEvent
	public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event)
    {
		onPlayerEnterWorld(event.player);
    }
	public void onPlayerEnterWorld(EntityPlayer player)
	{
		if(player.worldObj.isRemote)return;
		
		NBTTagCompound persist = PlayerTagData.getPersistedData(player);
		MFLogUtil.logDebug("Sync data");
    	ResearchLogic.syncData((EntityPlayer) player);
    	
    	if(!persist.hasKey("MF_HasBook"))
    	{
    		persist.setBoolean("MF_HasBook", true);
    		if(player.capabilities.isCreativeMode)return;
    		
    		player.inventory.addItemStackToInventory(new ItemStack(ToolListMF.researchBook));
    	}
    	if(RPGElements.isSystemActive)
    	{
    		RPGElements.initSkills(player);
    	}
    }
	
	public static int getDragonTier(EntityPlayer player)
	{
		int kills = getDragonEnemyPoints(player);
		if(kills < 10)
		{
			return 0;//Young 100%
		}
		if(kills < 20)
		{
			if(rand.nextInt(3) == 0)return 1;//33% chance for Adult
			return 0;//Young
		}
		if(kills < 35)
		{
			if(rand.nextInt(3) == 0)return 0;//33% chance for Young
			return 1;//Adult
		}
		if(kills < 50)
		{
			if(rand.nextInt(10) == 0)return 0;//10% chance for Young
			if(rand.nextInt(10) == 0)return 2;//10% chance for Mature
			return 1;//Adult
		}
		if(kills < 60)
		{
			if(rand.nextInt(4) == 0)return 2;//25% chance for Mature
			return 1;//Adult
		}
		if(kills >= 70)
		{
			if(rand.nextInt(10) == 0)return 1;//10% chance for Adult
			if(rand.nextInt(5) == 0)return 3;//20% chance Elder
			return 2;//Mature
		}
		if(kills > 100)
		{
			if(rand.nextInt(100) == 0)return 4;//1% chance Ancient
			if(rand.nextInt(2) == 0)return 3;//50% chance Elder
			return 2;//Mature
		}
		
		return 0;//Young 100%
	}
	
	public static void addDragonKill(EntityPlayer player)
	{
		addDragonEnemyPts(player, 1);
	}
	public static void addDragonEnemyPts(EntityPlayer player, int i)
	{
		setDragonEnemyPts(player, getDragonEnemyPoints(player)+i);
	}
	public static void setDragonEnemyPts(EntityPlayer player, int i)
	{
		if(i < 0)
		{
			i = 0;
		}
		NBTTagCompound nbt = PlayerTagData.getPersistedData(player);
		nbt.setInteger("MF_DragonKills", i);
	}
	
	public static int getDragonEnemyPoints(EntityPlayer player)
	{
		NBTTagCompound nbt = PlayerTagData.getPersistedData(player);
		return nbt.getInteger("MF_DragonKills");
	}
}
