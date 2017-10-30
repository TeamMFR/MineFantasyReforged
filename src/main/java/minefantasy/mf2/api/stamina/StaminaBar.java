package minefantasy.mf2.api.stamina;

import minefantasy.mf2.api.MineFantasyAPI;
import minefantasy.mf2.api.helpers.ArmourCalculator;
import minefantasy.mf2.api.helpers.PowerArmour;
import minefantasy.mf2.api.helpers.TacticalManager;
import minefantasy.mf2.api.knowledge.ResearchLogic;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;

public class StaminaBar {
    public static final String staminaRegenName = "MineFantasy_staminaRegen";
    public static final String staminaRegenTicksName = "MineFantasy_staminaRegenTicks";
    public static final String staminaBonusName = "MineFantasy_staminaMaxAddon";
    public static final String staminaBonusTicksName = "MineFantasy_staminaMaxAddonTicks";
    public static final String staminaMaxName = "MineFantasy_staminaMax";
    public static final String staminaValueName = "MineFantasy_staminaValue";
    public static final String staminaIdleName = "MineFantasy_staminaIdle";
    public static final String staminaFlashName = "MineFantasy_staminaFlashing";
    /**
     * Modifies the decay speed for armour, this scales to what a full suit of plate
     * does
     */
    private static final float armourWeightModifier = 1.0F;
    private static final float armourWeightModifierClimbing = 5.0F;
    private static final String noStaminaNBT = "MF_NoStamina";
    /**
     * This is the main variable for the entire stamina feature
     */
    public static boolean isSystemActive = true;
    public static float defaultMax = 100F;
    public static float regenModifier = 1.5F;

    // CONFIG VARS
    /**
     * The decay modifier before being scaled by difficulty
     */
    public static float decayModifierCfg = 1.0F;
    public static float decayModifierBase = 0.5F;
    public static float configRegenModifier = 1.0F;
    public static float pauseModifier = 1.0F;
    public static float configArmourWeightModifier = 1.0F;
    public static float configBulk = 1.0F;
    public static boolean scaleDifficulty = true;

    public static boolean levelUp = false;

    public static float levelAmount = 5F;
    public static boolean restrictSystem = true;
    /**
     * Modifies the regen rate slowdown for armour
     */
    private static float bulkModifier = 1.0F;

    // MAX STAMINA//
    public static float getTotalMaxStamina(EntityLivingBase user) {
        return getBaseMaxStamina(user) + getBonusStamina(user);
    }

    public static float getBaseMaxStamina(EntityLivingBase user) {
        float bonus = getStaminaLevelBoost(user);
        if (user.getEntityData() != null) {
            if (!user.getEntityData().hasKey(staminaMaxName)) {
                setMaxStamina(user, getDefaultMax(user) + bonus);
            }
            return user.getEntityData().getFloat(staminaMaxName) + bonus;
        }
        return getDefaultMax(user) + bonus;
    }

    public static float getDefaultMax(EntityLivingBase user) {
        return defaultMax;
    }

    public static void setMaxStamina(EntityLivingBase user, float value) {
        if (user.getEntityData() != null) {
            user.getEntityData().setFloat(staminaMaxName, value);
        }
    }

    public static void modifyMaxStamina(EntityLivingBase user, float mod) {
        setMaxStamina(user, getBaseMaxStamina(user) + mod);

        if (getStaminaValue(user) < 0) {
            setStaminaValue(user, 0);
        }
    }

    // REGEN BUFF STAMINA//
    public static float getBonusStaminaRegen(EntityLivingBase user) {
        float level = 0F;
        if (user.getEntityData() != null) {
            if (!user.getEntityData().hasKey(staminaRegenName)) {
                setBonusStaminaRegen(user, 0F);
            }
            level += user.getEntityData().getFloat(staminaRegenName);
        }
        if (user.getActivePotionEffect(Potion.regeneration) != null) {
            level += (user.getActivePotionEffect(Potion.regeneration).getAmplifier() + 1) * 2.5F;
        }
        return level;
    }

    public static void setBonusStaminaRegen(EntityLivingBase user, float value) {
        if (user.getEntityData() != null) {
            user.getEntityData().setFloat(staminaRegenName, value);
        }
    }

    public static int getBonusStaminaRegenTicks(EntityLivingBase user) {
        if (user.getEntityData() != null) {
            if (!user.getEntityData().hasKey(staminaRegenTicksName)) {
                setBonusStaminaRegenTicks(user, 0);
            }
            return user.getEntityData().getInteger(staminaRegenTicksName);
        }
        return 0;
    }

    public static void setBonusStaminaRegenTicks(EntityLivingBase user, int value) {
        if (user.getEntityData() != null) {
            user.getEntityData().setInteger(staminaRegenTicksName, value);
        }
    }

    public static float getRegenBonus(EntityLivingBase user) {
        return getBonusStaminaRegen(user) / 20F;
    }

    // TEMP STAMINA//
    public static float getBonusStamina(EntityLivingBase user) {
        if (user.getEntityData() != null) {
            if (!user.getEntityData().hasKey(staminaBonusName)) {
                setBonusStamina(user, 0F);
            }
            return user.getEntityData().getFloat(staminaBonusName);
        }
        return 0F;
    }

    private static float getStaminaLevelBoost(EntityLivingBase user) {
        if (user.worldObj.isRemote)
            return 0;

        float amount = 0F;
        if (user instanceof EntityPlayer && levelUp && levelAmount > 0) {
            EntityPlayer player = (EntityPlayer) user;
            if (player.experienceLevel > 0) {
                amount += (player.experienceLevel * levelAmount);
            }
        }
        return amount;
    }

    public static void setBonusStamina(EntityLivingBase user, float value) {
        if (user.getEntityData() != null) {
            user.getEntityData().setFloat(staminaBonusName, value);
        }
    }

    public static int getBonusStaminaTicks(EntityLivingBase user) {
        if (user.getEntityData() != null) {
            if (!user.getEntityData().hasKey(staminaBonusTicksName)) {
                setBonusStaminaTicks(user, 0);
            }
            return user.getEntityData().getInteger(staminaBonusTicksName);
        }
        return 0;
    }

    public static void setBonusStaminaTicks(EntityLivingBase user, int value) {
        if (user.getEntityData() != null) {
            user.getEntityData().setInteger(staminaBonusTicksName, value);
        }
    }

    public static void tickBonus(EntityLivingBase user) {
        if (user.worldObj.isRemote)
            return;

        int currentTicks = getBonusStaminaTicks(user);
        currentTicks--;
        if (currentTicks <= 0) {
            setBonusStaminaTicks(user, 0);
            setBonusStamina(user, 0);

            if (getStaminaValue(user) > getTotalMaxStamina(user)) {
                setStaminaValue(user, getTotalMaxStamina(user));
            }
            return;
        }
        setBonusStaminaTicks(user, currentTicks);
    }

    public static void tickBonusRegen(EntityLivingBase user) {
        if (user.worldObj.isRemote)
            return;

        int currentTicks = getBonusStaminaRegenTicks(user);
        currentTicks--;
        if (currentTicks <= 0) {
            setBonusStaminaRegenTicks(user, 0);
            setBonusStaminaRegen(user, 0);
            return;
        }
        setBonusStaminaRegenTicks(user, currentTicks);
    }

    /**
     * Call this to buff stamina temporarily, it will not override more potent
     * effects
     *
     * @param user    the entity to buff
     * @param mod     the amount of stamina added
     * @param seconds the time it takes to wear off
     * @return true if either time or modification is applied.
     */
    public static boolean buffStamina(EntityLivingBase user, float mod, int seconds) {
        if (!isSystemActive) {
            return false;
        }
        boolean success = false;
        int ticks = seconds * 20;

        float current = getBonusStamina(user);
        int currentTicks = getBonusStaminaTicks(user);
        if (mod >= current) {
            success = true;
            setBonusStaminaTicks(user, ticks);
            setBonusStamina(user, mod);
        }
        return false;
    }

    public static boolean buffStaminaRegen(EntityLivingBase user, float mod, int seconds) {
        if (!isSystemActive) {
            return false;
        }
        boolean success = false;
        int ticks = seconds * 20;

        float current = getBonusStaminaRegen(user);
        int currentTicks = getBonusStaminaRegenTicks(user);
        if (mod >= current && ticks >= currentTicks) {
            success = true;
            setBonusStaminaRegenTicks(user, ticks);
            setBonusStaminaRegen(user, mod);
        }
        return false;
    }

    /**
     * This increases the users max stamina
     *
     * @param user     the user
     * @param value    the amount to add to max stamina
     * @param maxBonus the maximum this can achieve (not counting base stamina), <0 means
     *                 no limit
     */
    public static boolean incrStaminaMax(EntityLivingBase user, float value, float maxBonus) {
        float current = getBaseMaxStamina(user);
        if (maxBonus > 0 && current >= maxBonus) {
            return false;
        }
        setMaxStamina(user, Math.min(maxBonus, current + value));
        MineFantasyAPI.debugMsg("Increased Max Stamina by " + value);
        return true;
    }

    // STAMINA VALUE//
    public static float getStaminaValue(EntityLivingBase user) {
        if (user.getEntityData() != null) {
            if (!user.getEntityData().hasKey(staminaValueName)) {
                setStaminaValue(user, getDefaultMax(user));
            }
            return user.getEntityData().getFloat(staminaValueName);
        }
        return getDefaultMax(user);
    }

    public static void setStaminaValue(EntityLivingBase user, float value) {
        if (user.getEntityData() != null) {
            user.getEntityData().setFloat(staminaValueName, value);
        }
    }

    public static void modifyStaminaValue(EntityLivingBase user, float mod) {
        if (mod < 0 && PowerArmour.isPowered(user)) {
            return;
        }
        setStaminaValue(user, getStaminaValue(user) + mod);

        if (mod < 0 && getStaminaValue(user) < 0) {
            setStaminaValue(user, 0);
        }
        float max = getTotalMaxStamina(user);
        if (getStaminaValue(user) > max) {
            setStaminaValue(user, max);
        }
    }

    // DECIMAL PERCENT VALUE//
    public static float getStaminaDecimal(EntityLivingBase user) {
        return getStaminaValue(user) / getTotalMaxStamina(user);
    }

    // INIT//
    public static void refreshSystem(EntityLivingBase user) {
        if (user.getEntityData() != null) {
            NBTTagCompound nbt = user.getEntityData();

            if (!nbt.hasKey(staminaMaxName)) {
                nbt.setFloat(staminaMaxName, getDefaultMax(user));
            }
            if (!nbt.hasKey(staminaValueName)) {
                nbt.setFloat(staminaValueName, getDefaultMax(user));
            }
            if (!nbt.hasKey(staminaFlashName)) {
                nbt.setFloat(staminaFlashName, 0);
            }
        }
    }

    // STAMINA IDLE//
    public static float getIdleTime(EntityLivingBase user) {
        if (user.getEntityData() != null) {
            if (!user.getEntityData().hasKey(staminaIdleName)) {
                setIdleTime(user, 0);
            }
            return user.getEntityData().getFloat(staminaIdleName);
        }
        return 0;
    }

    public static void setIdleTime(EntityLivingBase user, float value) {
        if (user.getEntityData() != null) {
            user.getEntityData().setFloat(staminaIdleName, value);
        }
    }

    public static void ModifyIdleTime(EntityLivingBase user, float mod) {
        setIdleTime(user, getIdleTime(user) + mod);
    }

    // STAMINA FLASH//
    public static float getFlashTime(EntityLivingBase user) {
        if (user.getEntityData() != null) {
            if (!user.getEntityData().hasKey(staminaFlashName)) {
                setFlashTime(user, 0);
            }
            return user.getEntityData().getFloat(staminaFlashName);
        }
        return 0;
    }

    /**
     * determines if the stamina can take a specific value
     */

    public static void setFlashTime(EntityLivingBase user, float value) {
        if (!(user instanceof EntityPlayer)) {
            return;
        }
        if (user.getEntityData() != null) {
            user.getEntityData().setFloat(staminaFlashName, value);
        }
    }

    public static void ModifyFlashTime(EntityLivingBase user, float mod) {
        setFlashTime(user, getFlashTime(user) + mod);
    }

    /**
     * Checks if stamina is available
     *
     * @param onUse whether or not stamina is trying to be used
     * @return
     */
    public static boolean isStaminaAvailable(EntityLivingBase user, float level, boolean onUse) {
        if (getStaminaValue(user) >= level) {
            return true;
        }
        if (onUse) {
            setFlashTime(user, 40);
        }
        return false;
    }

    public static boolean isAnyStamina(EntityLivingBase user, boolean flash) {
        return isStaminaAvailable(user, 1, flash);
    }

    public static boolean isPercentStamAvailable(EntityLivingBase user, float decimal, boolean flash) {
        if (PowerArmour.isPowered(user)) {
            return true;// Cogwork infinite power
        }

        return isStaminaAvailable(user, getTotalMaxStamina(user) * decimal, flash);
    }

    public static float getBaseDecayModifier(EntityLivingBase user, boolean countArmour, boolean countHeld) {
        if (PowerArmour.isPowered(user)) {
            return 0F;
        }
        float value = getDecayModifier(user.worldObj);
        float AM = 1.0F;
        if (user instanceof EntityPlayer) {
            value *= getBasePerkStaminaModifier(value, (EntityPlayer) user);
            AM = getPerkArmModifier(value, (EntityPlayer) user);
        }
        if (TacticalManager.isImmuneToWeight(user)) {
            return value * 0.5F;
        }

        if (countHeld) {
            if (user.getHeldItem() != null && user.getHeldItem().getItem() instanceof IHeldStaminaItem) {
                value *= (((IHeldStaminaItem) user.getHeldItem().getItem()).getDecayMod(user, user.getHeldItem()));
            }
        }
        if (countArmour) {
            float armourMod = 1.0F;
            for (int slot = 1; slot <= 4; slot++) {
                ItemStack armour = user.getEquipmentInSlot(slot);
                if (armour != null && armour.getItem() instanceof IWornStaminaItem) {
                    armourMod += (((IWornStaminaItem) armour.getItem()).getDecayModifier(user, armour));
                }
            }
            float min = ArmourCalculator.encumberanceArray[0];
            float max = ArmourCalculator.encumberanceArray[1];
            float mass = ArmourCalculator.getTotalWeightOfWorn(user, false) - min;

            if (mass > 0F) {
                float modifiers = AM * configArmourWeightModifier * armourWeightModifier;
                armourMod += ((mass + min) / max);
            }
            value *= armourMod;
        }
        return value;
    }

    public static float getClimbinbDecayModifier(EntityLivingBase user, boolean countArmour) {
        float value = getDecayModifier(user.worldObj);

        if (!TacticalManager.isImmuneToWeight(user) && countArmour) {
            float armourMod = 1.0F;
            for (int slot = 1; slot <= 4; slot++) {
                ItemStack armour = user.getEquipmentInSlot(slot);
                if (armour != null && armour.getItem() instanceof IWornStaminaItem) {
                    armourMod += ((IWornStaminaItem) armour.getItem()).getDecayModifier(user, armour);
                }
            }
            float min = ArmourCalculator.encumberanceArray[0];
            float max = ArmourCalculator.encumberanceArray[1];
            float mass = ArmourCalculator.getTotalWeightOfWorn(user, false) - min;
            if (mass > 0) {
                value *= (1 + ((min + mass) / max * configArmourWeightModifier * (armourWeightModifierClimbing - 1)));
            }
            value *= armourMod;
        }
        return value;
    }

    public static float getBaseRegenModifier(EntityLivingBase user, boolean countArmour, boolean countHeld) {
        float value = configRegenModifier * regenModifier * (5F / 3F);

        if (!TacticalManager.isImmuneToWeight(user)) {
            if (countHeld) {
                if (user.getHeldItem() != null && user.getHeldItem().getItem() instanceof IHeldStaminaItem) {
                    value *= ((IHeldStaminaItem) user.getHeldItem().getItem()).getRegenModifier(user,
                            user.getHeldItem());
                }
            }
            if (countArmour) {
                float AM = 1.0F;
                if (user instanceof EntityPlayer) {
                    AM = getPerkArmModifier(value, (EntityPlayer) user);
                }

                float armourMod = 1.0F;
                for (int slot = 1; slot <= 4; slot++) {
                    ItemStack armour = user.getEquipmentInSlot(slot);
                    if (armour != null && armour.getItem() instanceof IWornStaminaItem) {
                        armourMod += ((IWornStaminaItem) armour.getItem()).getRegenModifier(user, armour);
                    }
                }
                float weightMod = ArmourCalculator.getTotalWeightOfWorn(user, false) * bulkModifier * configBulk;
                if (weightMod > 0) {
                    float min = ArmourCalculator.encumberanceArray[0];
                    float max = ArmourCalculator.encumberanceArray[1];
                    value /= (1.0F + ((min + weightMod) / (max / 2) * AM * 0.5F));
                }
                value *= armourMod;
            }
        }
        if (user.isSneaking()) {
            value *= 1.5F;
        }

        return value;
    }

    public static float getBaseIdleModifier(EntityLivingBase user, boolean countArmour, boolean countHeld) {
        float value = pauseModifier;

        if (!TacticalManager.isImmuneToWeight(user)) {
            if (countHeld) {
                if (user.getHeldItem() != null && user.getHeldItem().getItem() instanceof IHeldStaminaItem) {
                    value *= ((IHeldStaminaItem) user.getHeldItem().getItem()).getIdleModifier(user,
                            user.getHeldItem());
                }
            }
            if (countArmour) {
                float armourMod = 1.0F;
                for (int slot = 1; slot <= 4; slot++) {
                    ItemStack armour = user.getEquipmentInSlot(slot);
                    if (armour != null && armour.getItem() instanceof IWornStaminaItem) {
                        armourMod += ((IWornStaminaItem) armour.getItem()).getIdleModifier(user, armour);
                    }
                }
            }
        }

        return value;
    }

    /**
     * This scales the decay modifier across all fields on difficulty It's base
     * level is on normal with easy,peaceful being 25% - 50% slower. Hard is 25%
     * faster
     */
    public static float getDecayModifier(World world) {
        if (!scaleDifficulty) {
            return decayModifierCfg * decayModifierBase;
        }
        int difficultyMod = world.difficultySetting.getDifficultyId() - 2;
        return (decayModifierCfg * decayModifierBase) + (0.25F * difficultyMod);
    }

    /**
     * This toggles the stamina use for entities
     */
    public static void setStaminaFlag(EntityLivingBase entity, boolean flag) {
        entity.getEntityData().setBoolean(noStaminaNBT, flag);
    }

    public static boolean doesAffectEntity(EntityLivingBase entity) {
        if (entity.getEntityData().hasKey(noStaminaNBT)) {
            return entity.getEntityData().getBoolean(noStaminaNBT);
        }
        if (entity instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) entity;
            return !p.capabilities.isCreativeMode;
        } else if (restrictSystem) {
            return false;
        }
        return MineFantasyAPI.isInDebugMode || !entity.isEntityUndead();
    }

    private static float getPerkArmModifier(float value, EntityPlayer user) {
        if (ResearchLogic.hasInfoUnlocked(user, "armourpro")) {
            value *= 0.5F;
        }
        return value;
    }

    private static float getBasePerkStaminaModifier(float value, EntityPlayer user) {
        if (ResearchLogic.hasInfoUnlocked(user, "fitness")) {
            value *= 0.75F;
        }
        return value;
    }
}
