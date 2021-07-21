package minefantasy.mfr.mechanics;

import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasyMaterials;
import minefantasy.mfr.item.ItemCustomArmour;
import minefantasy.mfr.item.ItemWeaponMFR;
import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.util.XSTRandom;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MonsterUpgrader {
	public static final String zombieArmourNBT = "MF_ZombieArmour";
	private static final float zombieWepChance = 10F;
	private static final float zombieKnightChance = 200F;
	private static final float zombieBruteChance = 200F;
	private static final float creeperJockeyChance = 60F;
	private static final float witchRiderChance = 100F;

	private static XSTRandom random = new XSTRandom();

	public void upgradeMob(EntityLivingBase mob) {
		int diff = mob.world.getDifficulty().getDifficultyId();

		if (ConfigHardcore.upgradeZombieWep) {
			if (mob instanceof AbstractSkeleton) {
				if ((mob) instanceof EntityWitherSkeleton) {
					giveEntityWeapon(mob, MineFantasyMaterials.Names.ENCRUSTED, random.nextInt(8));
				} else if (CombatMechanics.swordSkeleton && random.nextInt(3) == 0) {
					mob.setItemStackToSlot(EntityEquipmentSlot.MAINHAND,
							MineFantasyItems.STANDARD_SWORD.construct(MineFantasyMaterials.Names.BRONZE, MineFantasyMaterials.Names.OAK_WOOD));
					((AbstractSkeleton) mob).setCombatTask();
				}
			} else if (mob instanceof EntityZombie) {
				String tier = MineFantasyMaterials.Names.IRON;
				if (mob instanceof EntityPigZombie) {
					tier = MineFantasyMaterials.Names.OBSIDIAN;
					giveEntityWeapon(mob, tier, random.nextInt(7));
					if (mob.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) != null) {
						mob.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0F);
					}
				} else {
					if (!mob.getHeldItemMainhand().isEmpty()
							&& mob.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.IRON_SWORD) {
						giveEntityWeapon(mob, tier, 0);
						if (mob.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) != null) {
							mob.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0F);
						}
					} else {
						float mod = diff >= 2 ? ConfigHardcore.zombieWepChance * 2
								: diff < 1 ? ConfigHardcore.zombieWepChance / 2 : ConfigHardcore.zombieWepChance;
						float chance = random.nextFloat() * 100F * mod;
						if (chance >= (100F - zombieWepChance)) {
							giveEntityWeapon(mob, tier, random.nextInt(5));
							if (mob.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) != null) {
								mob.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0F);
							}
						}
					}
				}
				if (random.nextFloat() * (zombieKnightChance) < diff) {
					createZombieKnight((EntityZombie) mob);
				} else if (random.nextFloat() * (zombieBruteChance) < diff) {
					createZombieBrute((EntityZombie) mob);
				} else if (ConfigHardcore.fastZombies) {
					mob.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3F);
				}
			}
		} else if (mob instanceof EntitySpider) {
			if (!mob.isBeingRidden()) {
				if (random.nextFloat() * (witchRiderChance) < diff) {
					EntityWitch rider = new EntityWitch(mob.world);
					rider.setPosition(mob.posX, mob.posY, mob.posZ);
					mob.world.spawnEntity(rider);
					rider.startRiding(mob);
				} else if (random.nextFloat() * (creeperJockeyChance) < diff) {
					EntityCreeper rider = new EntityCreeper(mob.world);
					rider.setPosition(mob.posX, mob.posY, mob.posZ);
					mob.world.spawnEntity(rider);
					rider.startRiding(mob);
				}
			}
		} else {
			if (!mob.getHeldItemMainhand().isEmpty()
					&& mob.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.IRON_SWORD) {
				giveEntityWeapon(mob, MineFantasyMaterials.Names.IRON, 0);
			}
		}
		if (mob instanceof EntityPigZombie) {
			mob.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0F);
		}
	}

	private void createZombieKnight(EntityZombie mob) {
		if (mob.isChild())
			return;
		String tier = MineFantasyMaterials.Names.STEEL;
		int lootId = 0;
		if (mob instanceof EntityPigZombie) {
			lootId = 1;
			tier = MineFantasyMaterials.Names.ENCRUSTED;
		}
		mob.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0F);
		mob.setItemStackToSlot(EntityEquipmentSlot.MAINHAND,
				MineFantasyItems.STANDARD_GREATSWORD.construct(tier, MineFantasyMaterials.Names.OAK_WOOD));
		setArmour(mob, 1, tier);
		mob.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2F);
		mob.getEntityData().setInteger("MF_LootDrop", lootId);
	}

	private void setArmour(EntityLivingBase mob, int id, String tier) {
		if (id == 1) {
			ItemStack boots = ((ItemCustomArmour) MineFantasyItems.STANDARD_PLATE_BOOTS).construct(tier);
			ItemStack legs = ((ItemCustomArmour) MineFantasyItems.STANDARD_PLATE_LEGGINGS).construct(tier);
			ItemStack chest = ((ItemCustomArmour) MineFantasyItems.STANDARD_PLATE_CHESTPLATE).construct(tier);
			ItemStack helmet = ((ItemCustomArmour) MineFantasyItems.STANDARD_PLATE_HELMET).construct(tier);

			if (!boots.isEmpty())
				mob.setItemStackToSlot(EntityEquipmentSlot.FEET, boots);
			if (!legs.isEmpty())
				mob.setItemStackToSlot(EntityEquipmentSlot.LEGS, legs);
			if (!chest.isEmpty())
				mob.setItemStackToSlot(EntityEquipmentSlot.CHEST, chest);
			if (!helmet.isEmpty())
				mob.setItemStackToSlot(EntityEquipmentSlot.HEAD, helmet);
			return;
		}

		ItemStack boots = ((ItemCustomArmour) MineFantasyItems.STANDARD_CHAIN_BOOTS).construct(tier);
		ItemStack legs = ((ItemCustomArmour) MineFantasyItems.STANDARD_CHAIN_LEGGINGS).construct(tier);
		ItemStack chest = ((ItemCustomArmour) MineFantasyItems.STANDARD_CHAIN_CHESTPLATE).construct(tier);
		ItemStack helmet = ((ItemCustomArmour) MineFantasyItems.STANDARD_CHAIN_HELMET).construct(tier);

		if (!boots.isEmpty())
			mob.setItemStackToSlot(EntityEquipmentSlot.FEET, boots);
		if (!legs.isEmpty())
			mob.setItemStackToSlot(EntityEquipmentSlot.LEGS, legs);
		if (!chest.isEmpty())
			mob.setItemStackToSlot(EntityEquipmentSlot.CHEST, chest);
		if (!helmet.isEmpty())
			mob.setItemStackToSlot(EntityEquipmentSlot.HEAD, helmet);
	}

	private void createZombieBrute(EntityZombie mob) {
		if (mob.isChild())
			return;
		String tier = MineFantasyMaterials.Names.IRON;
		int lootId = 0;
		if (mob instanceof EntityPigZombie) {
			lootId = 1;
			tier = MineFantasyMaterials.Names.ENCRUSTED;
		}
		mob.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0F);
		mob.setItemStackToSlot(EntityEquipmentSlot.MAINHAND,
				MineFantasyItems.STANDARD_WARAXE.construct(tier, MineFantasyMaterials.Names.OAK_WOOD));
		setArmour(mob, 0, tier);
		mob.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35F);
		mob.getEntityData().setInteger("MF_LootDrop", lootId);
	}

	/**
	 * 1=Axe.....2=Mace.....3=dagger.....4=spear.....else sword
	 */
	private void giveEntityWeapon(EntityLivingBase mob, String tier, int weaponType) {
		if (CustomMaterial.getMaterial(tier) == CustomMaterial.NONE)
			return;

		ItemWeaponMFR weapon = MineFantasyItems.STANDARD_SWORD;
		if (weaponType == 1) {
			weapon = MineFantasyItems.STANDARD_WARAXE;
		}
		if (weaponType == 2) {
			weapon = MineFantasyItems.STANDARD_MACE;
		}
		if (weaponType == 3) {
			weapon = MineFantasyItems.STANDARD_DAGGER;
		}
		if (weaponType == 4) {
			weapon = MineFantasyItems.STANDARD_SPEAR;
		}

		if (mob != null && weapon != null) {
			mob.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, weapon.construct(tier, MineFantasyMaterials.Names.OAK_WOOD));
		}
	}

	@SubscribeEvent
	public void updateLiving(LivingUpdateEvent event) {
		EntityLivingBase living = event.getEntityLiving();

		if (isEnabled() && !living.world.isRemote && !living.getEntityData().hasKey("giveMFWeapon")) {
			living.getEntityData().setBoolean("giveMFWeapon", true);
			upgradeMob(event.getEntityLiving());
		}
	}

	private boolean isEnabled() {
		return true;
	}
}
