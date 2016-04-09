package minefantasy.mf2.api.armour;

import java.util.Random;

import minefantasy.mf2.api.helpers.ArmourCalculator;
import minefantasy.mf2.api.refine.SmokeMechanics;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CogworkArmour 
{
	private static final String poweredNBT="MF_WearingPwrdSuit";
	private static final String inCogworkNBT="MF_WearingCogwork";
	/**
	 * Is the user going to be weighed down
	 */
	public static boolean shouldDebuff(EntityLivingBase user)
	{
		return isWearingAnyCogwork(user) && !hasPoweredSuit(user);
	}
	/**
	 * Detemines if any piece worn is cogwork, if so: you will be affected.
	 */
	public static boolean isWearingAnyCogwork(EntityLivingBase user)
	{
		return user.getEntityData().getBoolean(inCogworkNBT);
	}
	/**
	 * Determines if a full, powered suit is worn
	 */
	public static boolean hasPoweredSuit(EntityLivingBase user)
	{
		return user.getEntityData().getBoolean(poweredNBT);
	}
	
	public static void updateVars(EntityLivingBase user)
	{
		boolean inCog = getIsWearingAnyCogwork(user);
		boolean pwr = getHasPoweredSuit(user);
		
		user.getEntityData().setBoolean(inCogworkNBT, inCog);
		user.getEntityData().setBoolean(poweredNBT, pwr);
	}
	private static boolean getHasPoweredSuit(EntityLivingBase user)
	{
		for(int x = 1; x < 4; x++)
		{
			if(!isPoweredCogwork(user, x))
			{
				return false;
			}
		}
		return true;
	}
	private static boolean getIsWearingAnyCogwork(EntityLivingBase user)
	{
		for(int x = 0; x < 4; x++)
		{
			if(isCogwork(user, x))
			{
				return true;
			}
		}
		return false;
	}
	private static boolean isCogwork(EntityLivingBase user, int slot)
	{
		ItemStack armour = user.getEquipmentInSlot(slot+1);
		if(armour != null && armour.getItem() instanceof ICogworkArmour)
		{
			return true;
		}
		return false;
	}
	private static boolean isPoweredCogwork(EntityLivingBase user, int slot)
	{
		ItemStack armour = user.getEquipmentInSlot(slot);
		if(armour != null && armour.getItem() instanceof ICogworkArmour)
		{
			if(slot == 3 && ((ICogworkArmour)armour.getItem()).needsPower(armour))
			{
				return getFuelValue(armour) > 0;
			}
			else
			{
				return true;
			}
		}
		return false;
	}
	private static void consumeFuel(EntityLivingBase user)
	{
		ItemStack armour = user.getEquipmentInSlot(3);
		if(armour != null && armour.getItem() instanceof ICogworkArmour)
		{
			if(((ICogworkArmour)armour.getItem()).needsPower(armour))
			{
				float fuel = getFuelValue(armour);
				if(fuel > 0)
				{
					SmokeMechanics.spawnSmokeD(user.worldObj, user.posX, user.posY+user.height+0.5F, user.posZ, 1);
					float cost = ((ICogworkArmour)armour.getItem()).getPowerCost(armour);
					if(user.isSprinting())
					{
						cost*=2;
					}
					cost *= (ArmourCalculator.getTotalWeightOfWorn(user, false) / 150F);
					
					if(user.isSwingInProgress)
					{
						++cost;
					}
					fuel -= cost;
					if(!user.worldObj.isRemote)
					System.out.println("Cost: " + cost);
					NBTTagCompound nbt = getOrCreateNBT(armour);
					nbt.setFloat(fuelNBT, Math.max(0, fuel));
				}
			}
		}
	}
	
	public static boolean canFitFuel(ItemStack armour, float fuel)
	{
		return getFuelValue(armour)+fuel <= getMaxFuel(armour);
	}
	
	public static void addFuel(ItemStack armour, float fuel)
	{
		float value = Math.min(getFuelValue(armour)+fuel, getMaxFuel(armour));
		setFuel(armour, value);
	}
	public static void setFuel(ItemStack armour, float fuel)
	{
		if(armour != null && armour.getItem() instanceof ICogworkArmour)
		{
			if(((ICogworkArmour)armour.getItem()).needsPower(armour))
			{
				NBTTagCompound nbt = getOrCreateNBT(armour);
				nbt.setFloat(fuelNBT, fuel);
			}
		}
	}
	
	private static NBTTagCompound getOrCreateNBT(ItemStack armour)
	{
		if(!armour.hasTagCompound())
		{
			armour.setTagCompound(new NBTTagCompound());
		}
		return armour.getTagCompound();
	}

	public static final String fuelNBT = "MF_CogworkFuelValue";
	public static float getFuelValue(ItemStack item)
	{
		if(item.hasTagCompound())
		{
			return item.getTagCompound().getFloat(fuelNBT);
		}
		return 0;
	}
	private static Random rand = new Random();
	public static void tickUser(EntityPlayer player) 
	{
		player.stepHeight = isWearingAnyCogwork(player) ? 1.0F : 0.5F;
		
		if(!player.worldObj.isRemote)
		{
			if(shouldDebuff(player))
			{
				player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 100, 100));
				player.addPotionEffect(new PotionEffect(Potion.weakness.id, 100, 100));
				player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 100, 100));
			}
			consumeFuel(player);
		}
	}
	public static void onStep(EntityPlayer player)
	{
	}
	public static final int coalFuel = 200, maxFuel = 3200;//16 to full
	public static int getMaxFuel(ItemStack item) 
	{
		return maxFuel;
	}
	
	@SideOnly(Side.CLIENT)
	public static int getScaledMetre(ItemStack item, int size) 
	{
		return (int) (Math.ceil((float)size / (float)getMaxFuel(item) * (float)getFuelValue(item)));
	}
}
