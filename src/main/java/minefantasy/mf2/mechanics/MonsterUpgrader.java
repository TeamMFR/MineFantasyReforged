package minefantasy.mf2.mechanics;

import java.util.Random;

import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.config.ConfigHardcore;
import minefantasy.mf2.item.armour.ItemCustomArmour;
import minefantasy.mf2.item.list.ArmourListMF;
import minefantasy.mf2.item.list.CustomArmourListMF;
import minefantasy.mf2.item.list.CustomToolListMF;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.item.weapon.ItemWeaponMF;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class MonsterUpgrader 
{
	private static final float zombieWepChance = 10F;
	private static final float zombieKnightChance = 200F;
	private static final float zombieBruteChance =  200F;
	
	private static final float creeperJockeyChance = 60F;
	private static final float witchRiderChance = 100F;
	
	private static Random rand = new Random();
	public static final String zombieArmourNBT = "MF_ZombieArmour";
	
	public void upgradeMob(EntityLivingBase mob)
    {
		int diff = mob.worldObj.difficultySetting.getDifficultyId();

		if(ConfigHardcore.upgradeZombieWep)
		{
			if(mob instanceof EntitySkeleton)
			{
				if(((EntitySkeleton)mob).getSkeletonType() == 1)
				{
					giveEntityWeapon(mob, "Diamond", rand.nextInt(8));
				}
				else if(CombatMechanics.swordSkeleton && rand.nextInt(3) == 0)
				{
					mob.setCurrentItemOrArmor(0, CustomToolListMF.standard_sword.construct("Bronze","OakWood"));
					((EntitySkeleton)mob).setCombatTask();
				}
			}
			else if(mob instanceof EntityZombie)
			{
				String tier = "Iron";
				if(mob instanceof EntityPigZombie)
				{
					tier = "Obsidian";
					giveEntityWeapon(mob, tier, rand.nextInt(7));
				}
				else
				{
					if(mob.getHeldItem() != null && mob.getHeldItem().getItem() == Items.iron_sword)
					{
						giveEntityWeapon(mob, tier, 0);
					}
					else
					{
						float mod = diff >= 2 ? ConfigHardcore.zombieWepChance*2 : diff < 1 ? ConfigHardcore.zombieWepChance/2 : ConfigHardcore.zombieWepChance;
						float chance = rand.nextFloat()*100F*mod;
						if(chance >= (100F-zombieWepChance))
						{
							giveEntityWeapon(mob, tier, rand.nextInt(5));
						}
					}
				}
				if(rand.nextFloat()*(zombieKnightChance*ConfigHardcore.zombieWepChance) < diff)
				{
					createZombieKnight((EntityZombie)mob);
				}
				else if(rand.nextFloat()*(zombieBruteChance*ConfigHardcore.zombieWepChance) < diff)
				{
					createZombieBrute((EntityZombie)mob);
				}
				else if(ConfigHardcore.fastZombies)
				{
					mob.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3F);
				}
			}
		}
		else if(mob instanceof EntitySpider)
		{
			if(mob.riddenByEntity == null)
			{
				if(rand.nextFloat()*(witchRiderChance*ConfigHardcore.zombieWepChance) < diff)
				{
					EntityWitch rider = new EntityWitch(mob.worldObj);
					rider.setPosition(mob.posX, mob.posY, mob.posZ);
					mob.worldObj.spawnEntityInWorld(rider);
					rider.mountEntity(mob);
				}
				else if(rand.nextFloat()*(creeperJockeyChance*ConfigHardcore.zombieWepChance) < diff)
				{
					EntityCreeper rider = new EntityCreeper(mob.worldObj);
					rider.setPosition(mob.posX, mob.posY, mob.posZ);
					mob.worldObj.spawnEntityInWorld(rider);
					rider.mountEntity(mob);
				}
			}
		}
		else
		{
			if(mob.getHeldItem() != null && mob.getHeldItem().getItem() == Items.iron_sword)
			{
				giveEntityWeapon(mob, "iron", 0);
			}
		}
		if(mob instanceof EntityPigZombie)
		{
			mob.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0F);
		}
    }
	

	private void createZombieKnight(EntityZombie mob) 
	{
		if(mob.isChild())return;
		String tier = "steel";
		int lootId = 0;
		if(mob instanceof EntityPigZombie)
		{
			lootId = 1;
			tier = "encrusted";
		}
		mob.setVillager(false);
		mob.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0F);
		mob.setCurrentItemOrArmor(0, CustomToolListMF.standard_greatsword.construct(tier,"OakWood"));
		setArmour(mob, 1, tier);
		mob.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2F);
		mob.getEntityData().setInteger("MF_LootDrop", lootId);
	}
	private void setArmour(EntityLivingBase mob, int id, String tier) 
	{
		if(id == 1)
		{
			mob.setCurrentItemOrArmor(1, CustomArmourListMF.standard_plate_boots.construct(tier));
			mob.setCurrentItemOrArmor(2, CustomArmourListMF.standard_plate_legs.construct(tier));
			mob.setCurrentItemOrArmor(3, CustomArmourListMF.standard_plate_chest.construct(tier));
			mob.setCurrentItemOrArmor(4, CustomArmourListMF.standard_plate_helmet.construct(tier));
			return;
		}
		mob.setCurrentItemOrArmor(1, CustomArmourListMF.standard_chain_boots.construct(tier));
		mob.setCurrentItemOrArmor(2, CustomArmourListMF.standard_chain_legs.construct(tier));
		mob.setCurrentItemOrArmor(3, CustomArmourListMF.standard_chain_chest.construct(tier));
		mob.setCurrentItemOrArmor(4, CustomArmourListMF.standard_chain_helmet.construct(tier));
	}


	private void createZombieBrute(EntityZombie mob) 
	{
		if(mob.isChild())return;
		String tier = "iron";
		int lootId = 0;
		if(mob instanceof EntityPigZombie)
		{
			lootId = 1;
			tier = "encrusted";
		}
		mob.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0F);
		mob.setCurrentItemOrArmor(0, CustomToolListMF.standard_waraxe.construct(tier,"OakWood"));
		setArmour(mob, 0, tier);
		mob.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35F);
		mob.getEntityData().setInteger("MF_LootDrop", lootId);
	}


	/**
	 * 1=Axe.....2=Mace.....3=dagger.....4=spear.....else sword
	 */
	private void giveEntityWeapon(EntityLivingBase mob, String tier, int weaponType)
	{
		if(CustomMaterial.getMaterial(tier) == null)return;
		
		ItemWeaponMF weapon = CustomToolListMF.standard_sword;
		if(weaponType == 1)
		{
			weapon = CustomToolListMF.standard_waraxe;
		}
		if(weaponType == 2)
		{
			weapon = CustomToolListMF.standard_mace;
		}
		if(weaponType == 3)
		{
			weapon = CustomToolListMF.standard_dagger;
		}
		if(weaponType == 4)
		{
			weapon = CustomToolListMF.standard_spear;
		}
		mob.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0F);
		mob.setCurrentItemOrArmor(0, weapon.construct(tier,"OakWood"));
	}
	
	@SubscribeEvent
	public void updateLiving(LivingUpdateEvent event)
	{
		EntityLivingBase living = event.entityLiving;
		
		if(isEnabled() && !living.worldObj.isRemote && !living.getEntityData().hasKey("giveMFWeapon"))
		{
			living.getEntityData().setBoolean("giveMFWeapon", true);
			upgradeMob(event.entityLiving);
		}
	}


	private boolean isEnabled()
	{
		return true;
	}
}
