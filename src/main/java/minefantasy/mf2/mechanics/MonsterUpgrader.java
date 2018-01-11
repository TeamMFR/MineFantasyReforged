package minefantasy.mf2.mechanics;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.config.ConfigHardcore;
import minefantasy.mf2.item.list.CustomArmourListMF;
import minefantasy.mf2.item.list.CustomToolListMF;
import minefantasy.mf2.item.weapon.ItemWeaponMF;
import minefantasy.mf2.util.XSTRandom;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.*;
import net.minecraft.init.Items;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class MonsterUpgrader {
    public static final String zombieArmourNBT = "MF_ZombieArmour";
    private static final float zombieWepChance = 10F;
    private static final float zombieKnightChance = 200F;
    private static final float zombieBruteChance = 200F;
    private static final float creeperJockeyChance = 60F;
    private static final float witchRiderChance = 100F;

    private static XSTRandom random = new XSTRandom();

    public void upgradeMob(EntityLivingBase mob) {
        int diff = mob.worldObj.difficultySetting.getDifficultyId();

        if (ConfigHardcore.upgradeZombieWep) {
            if (mob instanceof EntitySkeleton) {
                if (((EntitySkeleton) mob).getSkeletonType() == 1) {
                    giveEntityWeapon(mob, "Diamond", random.nextInt(8));
                } else if (CombatMechanics.swordSkeleton && random.nextInt(3) == 0) {
                    mob.setCurrentItemOrArmor(0, CustomToolListMF.standard_sword.construct("Bronze", "OakWood"));
                    ((EntitySkeleton) mob).setCombatTask();
                }
            } else if (mob instanceof EntityZombie) {
                String tier = "Iron";
                if (mob instanceof EntityPigZombie) {
                    tier = "Obsidian";
                    giveEntityWeapon(mob, tier, random.nextInt(7));
                    if (mob.getEntityAttribute(SharedMonsterAttributes.attackDamage) != null) {
                        mob.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0F);
                    }
                } else {
                    if (mob.getHeldItem() != null && mob.getHeldItem().getItem() == Items.iron_sword) {
                        giveEntityWeapon(mob, tier, 0);
                        if (mob.getEntityAttribute(SharedMonsterAttributes.attackDamage) != null) {
                            mob.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0F);
                        }
                    } else {
                        float mod = diff >= 2 ? ConfigHardcore.zombieWepChance * 2
                                : diff < 1 ? ConfigHardcore.zombieWepChance / 2 : ConfigHardcore.zombieWepChance;
                        float chance = random.nextFloat() * 100F * mod;
                        if (chance >= (100F - zombieWepChance)) {
                            giveEntityWeapon(mob, tier, random.nextInt(5));
                            if (mob.getEntityAttribute(SharedMonsterAttributes.attackDamage) != null) {
                                mob.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0F);
                            }
                        }
                    }
                }
                if (random.nextFloat() * (zombieKnightChance) < diff) {
                    createZombieKnight((EntityZombie) mob);
                } else if (random.nextFloat() * (zombieBruteChance) < diff) {
                    createZombieBrute((EntityZombie) mob);
                } else if (ConfigHardcore.fastZombies) {
                    mob.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3F);
                }
            }
        } else if (mob instanceof EntitySpider) {
            if (mob.riddenByEntity == null) {
                if (random.nextFloat() * (witchRiderChance) < diff) {
                    EntityWitch rider = new EntityWitch(mob.worldObj);
                    rider.setPosition(mob.posX, mob.posY, mob.posZ);
                    mob.worldObj.spawnEntityInWorld(rider);
                    rider.mountEntity(mob);
                } else if (random.nextFloat() * (creeperJockeyChance) < diff) {
                    EntityCreeper rider = new EntityCreeper(mob.worldObj);
                    rider.setPosition(mob.posX, mob.posY, mob.posZ);
                    mob.worldObj.spawnEntityInWorld(rider);
                    rider.mountEntity(mob);
                }
            }
        } else {
            if (mob.getHeldItem() != null && mob.getHeldItem().getItem() == Items.iron_sword) {
                giveEntityWeapon(mob, "iron", 0);
            }
        }
        if (mob instanceof EntityPigZombie) {
            mob.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0F);
        }
    }

    private void createZombieKnight(EntityZombie mob) {
        if (mob.isChild())
            return;
        String tier = "steel";
        int lootId = 0;
        if (mob instanceof EntityPigZombie) {
            lootId = 1;
            tier = "encrusted";
        }
        mob.setVillager(false);
        mob.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0F);
        mob.setCurrentItemOrArmor(0, CustomToolListMF.standard_greatsword.construct(tier, "OakWood"));
        setArmour(mob, 1, tier);
        mob.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2F);
        mob.getEntityData().setInteger("MF_LootDrop", lootId);
    }

    private void setArmour(EntityLivingBase mob, int id, String tier) {
        if (id == 1) {
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

    private void createZombieBrute(EntityZombie mob) {
        if (mob.isChild())
            return;
        String tier = "iron";
        int lootId = 0;
        if (mob instanceof EntityPigZombie) {
            lootId = 1;
            tier = "encrusted";
        }
        mob.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0F);
        mob.setCurrentItemOrArmor(0, CustomToolListMF.standard_waraxe.construct(tier, "OakWood"));
        setArmour(mob, 0, tier);
        mob.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35F);
        mob.getEntityData().setInteger("MF_LootDrop", lootId);
    }

    /**
     * 1=Axe.....2=Mace.....3=dagger.....4=spear.....else sword
     */
    private void giveEntityWeapon(EntityLivingBase mob, String tier, int weaponType) {
        if (CustomMaterial.getMaterial(tier) == null)
            return;

        ItemWeaponMF weapon = CustomToolListMF.standard_sword;
        if (weaponType == 1) {
            weapon = CustomToolListMF.standard_waraxe;
        }
        if (weaponType == 2) {
            weapon = CustomToolListMF.standard_mace;
        }
        if (weaponType == 3) {
            weapon = CustomToolListMF.standard_dagger;
        }
        if (weaponType == 4) {
            weapon = CustomToolListMF.standard_spear;
        }

        if (mob != null && weapon != null) {
            mob.setCurrentItemOrArmor(0, weapon.construct(tier, "OakWood"));
        }
    }

    @SubscribeEvent
    public void updateLiving(LivingUpdateEvent event) {
        EntityLivingBase living = event.entityLiving;

        if (isEnabled() && !living.worldObj.isRemote && !living.getEntityData().hasKey("giveMFWeapon")) {
            living.getEntityData().setBoolean("giveMFWeapon", true);
            upgradeMob(event.entityLiving);
        }
    }

    private boolean isEnabled() {
        return true;
    }
}
