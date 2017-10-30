package minefantasy.mf2.api.helpers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.armour.*;
import minefantasy.mf2.api.weapon.IDamageType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.*;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.DamageSource;

/**
 * This class is used to calculate armour values like classes and weight
 */
public class ArmourCalculator {
    /**
     * This is the scale for helmet, chest, legs and boots. Use this with 'slot' to
     * scale things like weight and DT distribution
     */
    public static final float[] sizes = new float[]{0.2F, 0.3F, 0.3F, 0.2F};
    /**
     * For the percentage based armour, how much is ratio 2(50%) viewed as
     */
    public static final float armourRatingScale = 100;
    /**
     * What affects stamina regen, and what affects stamina cost
     */
    public static final float[] encumberanceArray = new float[]{10F, 40F};
    /**
     * When your movement starts and peaks at slowing down
     */
    public static final float slowAmount = 10F;
    public static final float[] moveSpeedThreshold = new float[]{30F, 40F};
    private static final String weightNBT = "MF_Worn_Weight";
    private static final String weightNBTNS = "MF_Worn_Weight_NS";
    public static boolean advancedDamageTypes = true;
    public static float slowRate = 1.0F;
    public static boolean useConfigIndirectDmg = true;

    /**
     * Gets the default using the items ArmourClass
     */
    private static float getDefaultSuitWeight(ItemStack armour) {
        return CustomArmourEntry.getEntryVars(armour)[0];
    }

    private static float getDefaultBulk(ItemStack armour) {
        return CustomArmourEntry.getEntryVars(armour)[1];
    }

    public static ArmourDesign getDefaultAD(ItemStack armour) {
        return ArmourDesign.SOLID;
    }

    /**
     * Converts a hit:damage ratio into vanilla AR(rounding off as it is an int)
     */
    public static int translateToVanillaAR(float ratio) {
        return (int) Math.min(24, 25F * convertToPercent(ratio));
    }

    /**
     * Converting a single piece to vanilla AR (rounding off as it is an int) Then
     * it divides on the slot. Used for armour bar
     */
    public static int translateToVanillaAR(float ratio, int piece, int max) {
        float scale = (float) piece / (float) max;

        return (int) Math.min(24, 25F * convertToPercent(ratio) * scale);
    }

    /**
     * Converts a hit:damage ratio into a decimal percentage
     */
    public static float convertToPercent(float ratio) {
        return (1F - (1F / ratio));
    }

    /**
     * Estimates the distribution of stats over a piece
     */
    private static float estimateScale(ItemStack item, int slot) {
        if (item == null) {
            return 0;
        }

        if (item.getItem() instanceof ItemArmor) {
            ItemArmor armour = (ItemArmor) item.getItem();
            ArmorMaterial material = armour.getArmorMaterial();

            // gets the total added for all 4 pieces
            int totalProtection = material.getDamageReductionAmount(0) + material.getDamageReductionAmount(1)
                    + material.getDamageReductionAmount(2) + material.getDamageReductionAmount(3);

            if (totalProtection > 0) {
                float singlePointWorth = 1F / totalProtection;
                return material.getDamageReductionAmount(slot) * singlePointWorth;
            }
        }
        return sizes[slot];
    }

    public static float getSpeedModForWeight(EntityLivingBase user) {
        float mod = 0.0F;
        float min = moveSpeedThreshold[0];
        float max = moveSpeedThreshold[1] - min;
        float mass = getTotalWeightOfWorn(user, true) - min;
        if (mass > 0 && max > 0) {
            mod -= (mass / max) * slowAmount * slowRate;
        }
        return mod;
    }

    /**
     * Gets the total worn weight of a user: this updates every 20 ticks
     */
    public static float getTotalWeightOfWorn(EntityLivingBase user, boolean considerSpeed) {
        String tag = considerSpeed ? weightNBTNS : weightNBT;
        if (!user.getEntityData().hasKey(tag)) {
            return setTotalWeightOfWorn(user, considerSpeed);
        } else {
            return user.getEntityData().getFloat(tag);
        }
    }

    public static void updateWeights(EntityLivingBase user) {
        setTotalWeightOfWorn(user, true);
        setTotalWeightOfWorn(user, false);
    }

    /**
     * @param considerSpeed if true: some pieces will be ignored, ment for speed calculations
     */
    public static float setTotalWeightOfWorn(EntityLivingBase user, boolean considerSpeed) {
        String tag = considerSpeed ? weightNBTNS : weightNBT;
        float weight = 0.0F;

        for (int a = 0; a < 4; a++) {
            ItemStack armour = user.getEquipmentInSlot(4 - a);

            if (!considerSpeed || shouldArmourAlterSpeed(armour)) {
                weight += getPieceWeight(armour, a);
            }
        }
        user.getEntityData().setFloat(tag, weight);
        return weight;
    }

    public static float getPieceWeight(ItemStack item, int slot) {
        if (item == null) {
            return 0.0F;
        }
        if (item.getItem() instanceof IArmourMF) {
            return ((IArmourMF) item.getItem()).getPieceWeight(item, slot);
        }
        if (slot >= sizes.length) {
            return 0F;
        }
        return getDefaultSuitWeight(item) * sizes[slot];
    }

    private static boolean shouldArmourAlterSpeed(ItemStack armour) {
        if (armour == null) {
            return false;
        }

        return armour.getItem() instanceof IArmourMF || CustomArmourEntry.doesPieceSlowDown(armour);
    }

    public static float convertKgToIbs(float kg) {
        return kg * 2.5F;
    }

    public static float convertIbsToKg(float pounds) {
        return pounds / 2.5F;
    }

    /**
     * Modifes a value for resistence
     *
     * @param src         the source (breaks down to ratio)
     * @param value       the variable to modify
     * @param cuttingProt the cutting resistence
     * @param bluntProt   the blunt resistence
     * @param pierceProt  the piercing resistence
     * @return
     */
    public static float adjustACForDamage(DamageSource src, float value, float cuttingProt, float bluntProt,
                                          float pierceProt) {
        float[] ratio = getRatioForSource(src);
        if (ratio == null) {
            return value;// Null means undefined
        }

        return modifyACForType(value, ratio[0], ratio[1], ratio[2], cuttingProt, bluntProt, pierceProt,
                getArmourPenetration(src));
    }

    public static float modifyACForType(int type, float value, float cuttingProt, float bluntProt, float pierceProt) {
        float[] f = new float[]{0F, 0F, 0F};
        f[type] = 1.0F;
        return modifyACForType(value, f[0], f[1], f[2], cuttingProt, bluntProt, pierceProt, 0F);
    }

    public static float modifyACForType(float value, float cutting, float blunt, float pierce, float cuttingProt,
                                        float bluntProt, float pierceProt, float specialAP) {
        if (advancedDamageTypes) {
            // Averages the ratio between cutting and blunt, while modifying it by the
            // armour traits
            value *= (((cutting * cuttingProt) + (blunt * bluntProt) + (pierce * pierceProt))
                    / (cutting + blunt + pierce));
        }
        float ACModifier = 1.0F + specialAP;
        value *= ACModifier;
        return value > 0F ? value : 0F;
    }

    public static float getArmourPenetration(DamageSource source) {
        if (source != null && source.getEntity() != null) {
            Entity user = source.getEntity();// The attacker
            Entity damager = source.getSourceOfDamage();// The thing causing damage(like arrows)

            if (user == damager && user instanceof EntityLivingBase
                    && ((EntityLivingBase) user).getHeldItem() != null) {
                if (((EntityLivingBase) user).getHeldItem().getItem() instanceof IDamageType) {
                    return ((IDamageType) ((EntityLivingBase) user).getHeldItem().getItem())
                            .getPenetrationLevel(((EntityLivingBase) user).getHeldItem());
                }
            }
            if (user != damager) {
                if (damager instanceof IDamageType) {
                    return ((IDamageType) damager).getPenetrationLevel(damager);
                }
            }
        }
        return 0F;
    }

    public static float[] getRatioForSource(DamageSource source) {
        if (source.isMagicDamage() || source.isFireDamage() || source == DamageSource.starve) {
            return null;
        }

        if (source.isExplosion() || source == DamageSource.anvil || source == DamageSource.fallingBlock) {
            return new float[]{0, 1, 0};// blunt
        }
        if (source == DamageSource.cactus) {
            return new float[]{0, 0, 1};// pierce
        }
        if (source != null && source.getSourceOfDamage() != null && source.getEntity() != null) {
            Entity user = source.getEntity();// The attacker
            Entity damager = source.getSourceOfDamage();// The thing causing damage(like arrows)

            if (user == damager && user instanceof EntityLivingBase) {
                return getRatioForMelee((EntityLivingBase) user, ((EntityLivingBase) user).getHeldItem());
            }
            return getRatioForIndirect(damager);
        }

        return new float[]{0, 1, 0};
    }

    private static float[] getRatioForIndirect(Entity damager) {
        if (damager == null) {
            return new float[]{1, 1, 1};// No damage type.
        }
        if (damager instanceof IDamageType) {
            return ((IDamageType) damager).getDamageRatio(damager);
        }
        if (damager instanceof EntityArrow) {
            return new float[]{0, 0, 1};
        }

        if (useConfigIndirectDmg) {
            return CustomDamageRatioEntry.getTraits(getEntityRegisterName(damager));
        }
        return new float[]{1, 1, 1};
    }

    public static float[] getRatioForMelee(EntityLivingBase user, ItemStack weapon) {
        if (weapon == null) {
            return getMobDefault(user);
        }
        return getRatioForWeapon(user, weapon);
    }

    public static float[] getRatioForWeapon(ItemStack weapon) {
        return getRatioForWeapon(null, weapon);
    }

    public static float[] getRatioForWeapon(EntityLivingBase user, ItemStack weapon) {
        Item item = weapon.getItem();

        if (item instanceof IDamageType) {
            return user != null ? ((IDamageType) item).getDamageRatio(weapon, user)
                    : ((IDamageType) item).getDamageRatio(weapon);
        }
        if (item instanceof ItemSword) {
            return new float[]{1, 0, 0};// sword is cutting by default
        }
        if (item instanceof ItemAxe) {
            return new float[]{3, 1, 0};// Axe Ratio is about 3:1 (25%blunt)
        }
        if (item instanceof ItemPickaxe) {
            return new float[]{0, 0, 1};// Picks are pierce
        }
        if (item instanceof ItemHoe || item instanceof ItemSpade) {
            return new float[]{0, 1, 0};// Tools are blunt
        }

        return CustomDamageRatioEntry.getTraits(item);
    }

    private static float[] getMobDefault(EntityLivingBase user) {
        if (user instanceof EntitySpider) {
            return new float[]{20F, 10F, 80F};// 10% blunt, 20% slash, 80% pierce
        }
        if (user instanceof IArmourPenetrationMob) {
            return ((IArmourPenetrationMob) user).getHitTraits();
        }
        return new float[]{0, 1, 0};
    }

    public static String getEntityRegisterName(Entity entity) {
        if (entity == null) {
            return "genetic";
        }
        String s = EntityList.getEntityString(entity);

        if (s == null) {
            s = "generic";
        }

        return s;
    }

    public static float getTotalBulk(EntityLivingBase user) {
        if (PowerArmour.isWearingCogwork(user)) {
            return 5F;
        }
        return getEquipmentBulk(user);
    }

    /**
     * Defined by Light/Medium/Heavy armour average 0 for light, 1 for full medium,
     * 2 for full heavy
     */
    public static float getEquipmentBulk(EntityLivingBase user) {
        float bulk = 0.0F;
        for (int a = 0; a < 4; a++) {
            String s = getArmourClass(user.getEquipmentInSlot(4 - a));
            if (s != null) {
                if (s.equalsIgnoreCase("Medium")) {
                    bulk += 1;
                }
                if (s.equalsIgnoreCase("Heavy")) {
                    bulk += 2;
                }
            }

        }
        return bulk / 4;
    }

    public static String getArmourClass(ItemStack armour) {
        if (armour == null) {
            return null;
        }
        if (armour.getItem() instanceof IArmourMF) {
            return ((IArmourMF) armour.getItem()).getSuitWeigthType(armour);
        }
        return CustomArmourEntry.getArmourClass(armour);
    }

    // THRESHOLD
    @SideOnly(Side.CLIENT)
    /**
     * CLIENT WHOLE ENTITY: Gets the total DT of an entity by damage type: Used by
     * the Screen renderer
     */
    public static float getDTDisplay(EntityLivingBase user, int id) {
        float armourDT = 0;
        for (int a = 0; a < 4; a++) {
            ItemStack armour = user.getEquipmentInSlot(a + 1);
            if (armour != null && armour.getItem() instanceof ISpecialArmourMF) {
                float threshold = getArmourValueMod(armour,
                        ((ISpecialArmourMF) armour.getItem()).getDTDisplay(armour, id));
                armourDT += threshold;
            }
        }
        return armourDT;
    }

    @SideOnly(Side.CLIENT)
    /**
     * CLIENT SINGLE PIECE: Gets a stat for a single piece: Used by Tooltips
     */
    public static float getDTForDisplayPiece(ItemStack armour, int id) {
        if (armour != null && armour.getItem() instanceof ISpecialArmourMF) {
            return getArmourValueMod(armour, ((ISpecialArmourMF) armour.getItem()).getDTDisplay(armour, id));
        }
        return 0F;
    }

    @SideOnly(Side.CLIENT)
    /**
     * CLIENT SINGLE PIECE: Gets a stat for a single piece: Used by Tooltips
     */
    public static float getDRForDisplayPiece(ItemStack armour, int id) {
        if (armour == null) {
            return 0F;
        }
        if (armour.getItem() instanceof ISpecialArmourMF) {
            return getArmourValueMod(armour, ((ISpecialArmourMF) armour.getItem()).getDRDisplay(armour, id));
        }
        return 0F;
    }

    @SideOnly(Side.CLIENT)
    /**
     * CLIENT WHOLE ENTITY: Gets the total DT of an entity by damage type: Used by
     * the Screen renderer
     */
    public static float getDRDisplay(EntityLivingBase user, int id) {
        float armourDT = 0;
        for (int a = 0; a < 4; a++) {
            ItemStack armour = user.getEquipmentInSlot(a + 1);
            if (armour != null && armour.getItem() instanceof ISpecialArmourMF) {
                float threshold = getArmourValueMod(armour,
                        ((ISpecialArmourMF) armour.getItem()).getDRDisplay(armour, id));
                armourDT += threshold;
            }
        }
        return armourDT;
    }

    /**
     * MECHANIC WHOLE ENTITY: Gets the threshold of a whole entity
     */
    public static float getACThreshold(EntityLivingBase user, DamageSource src) {
        float naturalAC = getACForMob(user);

        if (user instanceof IArmouredEntity) {
            naturalAC = ((IArmouredEntity) user).getThreshold(src);
        }

        float armourDT = 0;
        for (int a = 0; a < 4; a++) {
            ItemStack armour = user.getEquipmentInSlot(a + 1);
            if (armour != null && armour.getItem() instanceof ISpecialArmourMF) {
                float threshold = getArmourValueMod(armour,
                        ((ISpecialArmourMF) armour.getItem()).getDTValue(user, armour, src));
                armourDT += threshold;
            }
        }
        return naturalAC + armourDT;
    }

    /**
     * MECHANIC DT MODIFIER: Gets the DT Modifier for durability loss
     */
    private static float modifyDTOnDura(ItemStack armour) {
        float percentQuality = getPercentQuality(armour);

        float reduction = 0.8F;

        if (percentQuality < reduction) {
            float mod = Math.max(0.1F, percentQuality / reduction);

            return mod;
        }
        return 1.0F;
    }

    /**
     * MECHANIC DAMAGE FINDER: Gets the percent durability of an item
     */
    private static float getPercentQuality(ItemStack armour) {
        return 1F - ((float) armour.getItemDamage() / (float) armour.getMaxDamage());
    }

    /**
     * MECHANIC DT CALCULATOR: Gets the natural DT of an entity
     */
    private static float getACForMob(EntityLivingBase user) {
        if (user.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD) {
            return user.getMaxHealth() / 10F;// 1.6AC for spiders
        }
        return 0F;
    }

    /**
     * MECHANIC DT CALCULATOR: Gets the modifiers based on the itemstack (such as
     * damage and NBT) DT OR DR
     */
    public static float getArmourValueMod(ItemStack armour, float DT) {
        float initDT = DT;
        if (armour.hasTagCompound() && armour.getTagCompound().hasKey("MF_Inferior")) {
            DT *= (armour.getTagCompound().getBoolean("MF_Inferior") ? 0.8F : 1.2F);
        }
        DT *= modifyDTOnDura(armour);
        return DT;
    }

    /**
     * MECHANIC DT CALCULATOR: How much is armour damaged by a hit
     */
    public static int getDamageToDura(EntityLivingBase user, DamageSource source, ItemStack armour, float dam) {
        if (source.isUnblockable()) {
            return 0;
        }
        if (source.getSourceOfDamage() != null && source.getSourceOfDamage() == source.getEntity()) {
            dam *= getMobArmourDamage(source.getSourceOfDamage());
        }
        if (getPercentQuality(armour) > 0.1F) {
            dam = Math.max(1.0F, dam / 4F);
        }
        return (int) dam;
    }

    /**
     * MECHANIC DT CALCULATOR: How much armour damage the entity does
     */
    private static float getMobArmourDamage(Entity src) {
        if (src instanceof EntitySpider) {
            return 3.0F;
        }
        return 0.5F;
    }

    public static void damageArmour(EntityLivingBase user, int dura) {
        for (int a = 0; a < 4; a++) {
            ItemStack armour = user.getEquipmentInSlot(a + 1);
            if (armour != null) {
                if (!user.worldObj.isRemote) {
                    if (armour.getItemDamage() + dura < armour.getMaxDamage()) {
                        armour.damageItem(dura, user);
                    } else {
                        armour.setItemDamage(armour.getMaxDamage());
                    }
                }
                if (armour.getItemDamage() >= armour.getMaxDamage()) {
                    user.setCurrentItemOrArmor(a + 1, null);
                    user.worldObj.playSoundEffect(user.posX, user.posY + user.getEyeHeight() - (0.4F * a), user.posZ,
                            "random.break", 1.0F, 1.0F);
                }
            }
        }
    }

    /**
     * Determines how much armour reduces parrying ability
     */
    public static float getParryModifier(EntityLivingBase user) {
        float bulk = getTotalBulk(user);

        return 1.0F / ((bulk * 0.5F) + 1);// 50% in heavy
    }

    public static int modifyParryCooldown(EntityLivingBase user, int ticks) {
        float bulk = getTotalBulk(user);

        return (int) Math.max(5, ticks + (bulk * 5));// plate adds 5t
    }
}
