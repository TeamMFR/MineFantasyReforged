package minefantasy.mf2.mechanics;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import minefantasy.mf2.api.archery.AmmoMechanicsMF;
import minefantasy.mf2.api.archery.IFirearm;
import minefantasy.mf2.api.heating.IHotItem;
import minefantasy.mf2.api.helpers.ArmourCalculator;
import minefantasy.mf2.api.helpers.PlayerTagData;
import minefantasy.mf2.api.helpers.TacticalManager;
import minefantasy.mf2.api.knowledge.ResearchLogic;
import minefantasy.mf2.api.rpg.RPGElements;
import minefantasy.mf2.api.stamina.StaminaBar;
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
import minefantasy.mf2.util.XSTRandom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.EnumDifficulty;

import java.util.Iterator;
import java.util.List;

public class PlayerTickHandlerMF {
    private static ItemStack lastStack;
    private static String stepNBT = "MF_LastStep";
    private static String chunkCoords = "MF_BedPos";
    private static String resetBed = "MF_Resetbed";

    private static XSTRandom random = new XSTRandom();

    public static void spawnDragon(EntityPlayer player) {
        spawnDragon(player, 64);
    }

    public static void spawnDragon(EntityPlayer player, int offset) {
        int y = (int) (player.posY + offset);
        boolean canMobSpawn = !player.worldObj.isRemote && canDragonSpawnOnPlayer(player, y);
        if (canMobSpawn && !player.worldObj.isRemote) {
            int tier = getDragonTier(player);// Gets tier on kills
            EntityDragon dragon = new EntityDragon(player.worldObj);
            dragon.setPosition(player.posX, y, player.posZ);
            player.worldObj.spawnEntityInWorld(dragon);
            dragon.setDragon(tier);
            dragon.disengage(100);
            player.worldObj.playSoundEffect(dragon.posX, dragon.posY - 16D, dragon.posZ, "mob.enderdragon.growl", 3.0F,
                    1.5F);
            dragon.fireBreathCooldown = 200;

            if (ConfigMobs.dragonMSG && !player.worldObj.isRemote) {
                player.addChatMessage(new ChatComponentText(
                        EnumChatFormatting.GOLD + StatCollector.translateToLocal("event.dragonnear.name")));

                List<?> list = player.worldObj.playerEntities;
                Iterator<?> players = list.iterator();
                while (players.hasNext()) {
                    Object instance = players.next();
                    if (instance != null && instance instanceof EntityPlayer) {
                        if (((EntityPlayer) instance).getDistanceToEntity(player) < 256D && instance != player) {
                            ((EntityPlayer) instance).addChatMessage(new ChatComponentText(
                                    EnumChatFormatting.GOLD + StatCollector.translateToLocal("event.dragonnear.name")));
                        }
                    }
                }
            }
        }
    }

    private static boolean canDragonSpawnOnPlayer(EntityPlayer player, int y) {
        for (int x = -3; x <= 3; x++) {
            for (int z = -3; z <= 3; z++) {
                if (!player.worldObj.canBlockSeeTheSky((int) player.posX + x, y, (int) player.posZ + z)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int getDragonTier(EntityPlayer player) {
        int kills = getDragonEnemyPoints(player);
        if (kills < 5) {
            return 0;// Young 100%
        }
        if (kills < 10) {
            if (random.nextInt(5) == 0)
                return 0;// 20% chance for Young
            return 1;// Adult
        }
        if (kills < 15) {
            if (random.nextInt(10) == 0)
                return 0;// 10% chance for Young
            return 1;// Adult
        }
        if (kills < 25) {
            if (random.nextInt(20) == 0)
                return 0;// 5% chance for Young
            if (random.nextInt(10) == 0)
                return 2;// 10% chance for Mature
            return 1;// Adult
        }
        if (kills < 35) {
            if (random.nextInt(4) == 0)
                return 2;// 25% chance for Mature
            return 1;// Adult
        }
        if (kills >= 50) {
            if (random.nextInt(10) == 0)
                return 1;// 10% chance for Adult
            if (random.nextInt(5) == 0)
                return 3;// 20% chance Elder
            return 2;// Mature
        }
        if (kills > 75) {
            if (random.nextInt(100) == 0)
                return 4;// 1% chance Ancient
            if (random.nextInt(2) == 0)
                return 3;// 50% chance Elder
            return 2;// Mature
        }

        return 0;// Young 100%
    }

    public static void addDragonKill(EntityPlayer player) {
        addDragonEnemyPts(player, 1);
    }

    public static void addDragonEnemyPts(EntityPlayer player, int i) {
        setDragonEnemyPts(player, getDragonEnemyPoints(player) + i);
    }

    public static void setDragonEnemyPts(EntityPlayer player, int i) {
        if (i < 0) {
            i = 0;
        }
        NBTTagCompound nbt = PlayerTagData.getPersistedData(player);
        nbt.setInteger("MF_DragonKills", i);
    }

    public static int getDragonEnemyPoints(EntityPlayer player) {
        NBTTagCompound nbt = PlayerTagData.getPersistedData(player);
        return nbt.getInteger("MF_DragonKills");
    }

    public static void wakeUp(EntityPlayer player) {
        if (StaminaBar.isSystemActive) {
            StaminaBar.setStaminaValue(player, StaminaBar.getBaseMaxStamina(player));
        }
        if (player.getEntityData().hasKey(chunkCoords + "_x")) {
            player.getEntityData().setBoolean(resetBed, true);
        }
    }

    public static void readyToResetBedPosition(EntityPlayer player) {
        ChunkCoordinates coords = player.getBedLocation(player.dimension);
        if (coords != null) {
            player.getEntityData().setInteger(chunkCoords + "_x", coords.posX);
            player.getEntityData().setInteger(chunkCoords + "_y", coords.posY);
            player.getEntityData().setInteger(chunkCoords + "_z", coords.posZ);
        }
    }

    // SPRINT JUMPING
    // DEFAULT:= 0:22 (50seconds till starve, 35s till nosprint) (16m in MC time for
    // 4 missing bars)
    // SLOW=5: = 2:20 (5mins till starve, 3:30 till nosprint) (1h 40m in MC time for
    // 4 missing bars)
    // EXHAUSTION SCALE = 3.0F = 1hunger
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            ItemStack held = event.player.getHeldItem();
            if (held != null) {
                int parry = ItemWeaponMF.getParry(held);
                if (parry > 0) {
                    ItemWeaponMF.setParry(held, parry - 1);
                }
            }

            // COMMON
            TacticalManager.applyArmourWeight(event.player);

            if (event.player.worldObj.isRemote) {
                if (isNextStep(event.player)) {
                    onStep(event.player, event.player.getEntityData().getInteger(stepNBT) % 2 == 0);
                }
            }
            /*
             * if(RPGElements.isSystemActive) { if(event.player.isSprinting() &&
             * event.player.ticksExisted % 10 == 0) {
             * SkillList.athletics.addXP(event.player, 1); } else
             * if(event.player.isSneaking() && TacticalManager.isEntityMoving(event.player)
             * && event.player.ticksExisted % 10 == 0) { SkillList.sneak.addXP(event.player,
             * 1); } }
             */
            // DRAGON EVENT
            if (!event.player.worldObj.isRemote) {
                tickDragonSpawner(event.player);
            }
            if (!event.player.worldObj.isRemote) {
                tryResetBed(event.player);
            }
            // updatePitch(event.player); (This keeps track of player camera angles, could
            // make a mechanic based on swinging the camera)
        }

        if (event.phase == TickEvent.Phase.START) {
            applyBalance(event.player);
            ItemFoodMF.onTick(event.player);

            if (!event.player.worldObj.isRemote
                    && !(!ConfigHardcore.HCChotBurn && ItemApron.isUserProtected(event.player))
                    && event.player.ticksExisted % 100 == 0) {
                for (int a = 0; a < event.player.inventory.getSizeInventory(); a++) {
                    ItemStack item = event.player.inventory.getStackInSlot(a);
                    if (item != null && item.getItem() instanceof IHotItem) {
                        event.player.setFire(5);
                        event.player.attackEntityFrom(DamageSource.onFire, 1.0F);
                    }
                }
            }
            if (event.player.worldObj.isRemote) {
                ItemStack item = event.player.getHeldItem();
                if (lastStack == null && item != null) {
                    if (item.getItem() instanceof IFirearm) {
                        NBTTagCompound nbt = AmmoMechanicsMF.getNBT(item);
                        if (nbt.hasKey(ItemCrossbow.useTypeNBT)
                                && nbt.getString(ItemCrossbow.useTypeNBT).equalsIgnoreCase("fire")) {
                            nbt.setString(ItemCrossbow.useTypeNBT, "null");
                        }
                    }
                }
                if (lastStack != null && (item == null || item != lastStack)) {
                    if (lastStack.getItem() instanceof IFirearm) {
                        NBTTagCompound nbt = AmmoMechanicsMF.getNBT(lastStack);
                        if (nbt.hasKey(ItemCrossbow.useTypeNBT)
                                && nbt.getString(ItemCrossbow.useTypeNBT).equalsIgnoreCase("fire")) {
                            nbt.setString(ItemCrossbow.useTypeNBT, "null");
                        }
                    }
                }
                lastStack = item;
            }

            float weight = ArmourCalculator.getTotalWeightOfWorn(event.player, false);
            if (weight > 100F) {
                if (event.player.isInWater()) {
                    event.player.motionY -= (weight / 20000F);
                }
            }
        }
    }

    private void onStep(EntityPlayer player, boolean alternateStep) {
        if (ArmourCalculator.getTotalWeightOfWorn(player, false) >= 50) {
            player.playSound("mob.irongolem.throw", 1.0F, 1.0F);
        }
    }

    private boolean isNextStep(EntityPlayer player) {
        int prevStep = player.getEntityData().getInteger(stepNBT);
        int stepcount = (int) player.distanceWalkedOnStepModified;
        player.getEntityData().setInteger(stepNBT, stepcount);

        return prevStep != stepcount;
    }

    /*
     * private static String lastPitchNBT = "MF_last_AimPitch"; public static void
     * setLastPitch(EntityPlayer user, float value) {
     * user.getEntityData().setFloat(lastPitchNBT, value); } public static void
     * updatePitch(EntityPlayer user) { user.getEntityData().setFloat(lastPitchNBT,
     * user.rotationPitch); } public static float getPitchMovement(EntityPlayer
     * user) { float lastPitch = user.getEntityData().getFloat(lastPitchNBT) +
     * 1000F; float nowPitch = user.rotationPitch + 1000F;
     *
     * return nowPitch - lastPitch; }
     */
    private void tickDragonSpawner(EntityPlayer player) {
        if (player.worldObj.difficultySetting != EnumDifficulty.PEACEFUL && player.dimension == 0) {
            int i = ConfigMobs.dragonInterval;
            float chance = ConfigMobs.dragonChance;

            if (PlayerTickHandlerMF.getDragonEnemyPoints(player) >= 100) {
                i /= 2;// twice as frequent
            }
            if (PlayerTickHandlerMF.getDragonEnemyPoints(player) >= 50) {
                chance *= 2;// twice the chance
            }
            if (!player.worldObj.isRemote && player.worldObj.getTotalWorldTime() % i == 0
                    && random.nextFloat() * 100F < chance) {
                spawnDragon(player);
            }
        }
    }

    private void applyBalance(EntityPlayer entityPlayer) {
        float weight = 2.0F;
        float pitchBalance = entityPlayer.getEntityData().hasKey("MF_Balance_Pitch")
                ? entityPlayer.getEntityData().getFloat("MF_Balance_Pitch")
                : 0F;
        float yawBalance = entityPlayer.getEntityData().hasKey("MF_Balance_Yaw")
                ? entityPlayer.getEntityData().getFloat("MF_Balance_Yaw")
                : 0F;

        if (pitchBalance > 0) {
            if (pitchBalance < 1.0F && pitchBalance > -1.0F)
                weight = pitchBalance;
            pitchBalance -= weight;

            if (ConfigWeapon.useBalance) {
                entityPlayer.rotationPitch += pitchBalance > 0 ? weight : -weight;
            }

            if (pitchBalance < 0)
                pitchBalance = 0;
        }
        if (yawBalance > 0) {
            if (yawBalance < 1.0F && yawBalance > -1.0F)
                weight = yawBalance;
            yawBalance -= weight;

            if (ConfigWeapon.useBalance) {
                MFLogUtil.logDebug("Weapon Balance Move");
                entityPlayer.rotationYaw += yawBalance > 0 ? weight : -weight;
            }

            if (yawBalance < 0)
                yawBalance = 0;
        }
        entityPlayer.getEntityData().setFloat("MF_Balance_Pitch", pitchBalance);
        entityPlayer.getEntityData().setFloat("MF_Balance_Yaw", yawBalance);
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        onPlayerEnterWorld(event.player);
    }

    @SubscribeEvent
    public void onPlayerTravel(PlayerEvent.PlayerChangedDimensionEvent event) {
        onPlayerEnterWorld(event.player);
    }

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        onPlayerEnterWorld(event.player);
    }

    public void onPlayerEnterWorld(EntityPlayer player) {
        if (player.worldObj.isRemote)
            return;

        NBTTagCompound persist = PlayerTagData.getPersistedData(player);
        MFLogUtil.logDebug("Sync data");
        ResearchLogic.syncData(player);

        if (!persist.hasKey("MF_HasBook")) {
            persist.setBoolean("MF_HasBook", true);
            if (player.capabilities.isCreativeMode)
                return;

            player.inventory.addItemStackToInventory(new ItemStack(ToolListMF.researchBook));
        }
        if (RPGElements.isSystemActive) {
            RPGElements.initSkills(player);
        }
    }

    private void tryResetBed(EntityPlayer player) {
        if (player.getEntityData().hasKey(resetBed)) {
            player.getEntityData().removeTag(resetBed);
            resetBedPosition(player);
        }
    }

    private void resetBedPosition(EntityPlayer player) {
        if (player.getEntityData().hasKey(chunkCoords + "_x")) {
            MFLogUtil.logDebug("Reset bed data for " + player.getCommandSenderName());
            int x = player.getEntityData().getInteger(chunkCoords + "_x");
            int y = player.getEntityData().getInteger(chunkCoords + "_y");
            int z = player.getEntityData().getInteger(chunkCoords + "_z");
            ChunkCoordinates coords = new ChunkCoordinates(x, y, z);

            player.getEntityData().removeTag(chunkCoords + "_x");
            player.getEntityData().removeTag(chunkCoords + "_y");
            player.getEntityData().removeTag(chunkCoords + "_z");

            player.setSpawnChunk(coords, false);
        }
    }
}
