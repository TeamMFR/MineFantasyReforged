package minefantasy.mf2.api.armour;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.helpers.ArmourCalculator;
import minefantasy.mf2.api.helpers.ToolHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.util.EnumHelper;

import java.text.DecimalFormat;
import java.util.List;

public class ItemArmourMFBase extends ItemArmor implements ISpecialArmor, IArmourMF, IArmourRating, ISpecialArmourMF {
    public static final DecimalFormat decimal_format = new DecimalFormat("#.#");
    private static final float[] AC = new float[]{40F, 60F};
    public static ArmorMaterial baseMaterial = EnumHelper.addArmorMaterial("MF Armour Base", 0,
            new int[]{2, 6, 5, 2}, 0);
    public float baseAR;
    public int enchantment;
    public String texture;
    public float armourWeight;
    public ArmourMaterialMF material;
    public ArmourDesign design;
    public float DT;
    protected float suitBulk;
    private int piece;
    private int baseRating;

    public ItemArmourMFBase(String name, ArmourMaterialMF material, ArmourDesign AD, int slot, String tex) {
        super(baseMaterial, 0, slot);
        this.material = material;
        baseAR = material.baseAR;
        armourWeight = AD.getWeight() * material.armourWeight;
        enchantment = material.enchantment;
        this.piece = slot;
        design = AD;
        suitBulk = design.getBulk();
        texture = tex;
        float baseDura = material.durability * design.getDurability() / 2F;
        float dura = baseDura / 2F + (baseDura / 2F * ArmourCalculator.sizes[slot] / ArmourCalculator.sizes[1]);
        this.setMaxDamage((int) dura);
        this.setUnlocalizedName(name);
        setDT(getBaseAC() * scalePiece());

        baseRating = ArmourCalculator.translateToVanillaAR(getBaseAC() + 1F,
                baseMaterial.getDamageReductionAmount(slot), 15);
    }

    public ItemArmourMFBase setDT(float DT) {
        this.DT = DT;
        return this;
    }

    /*
     * Piece | Slot 0 | 3 1 | 2 2 | 1 3 | 0
     */
    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
        initArmourDamage(entity, stack, damage);
    }

    public void initArmourDamage(EntityLivingBase user, ItemStack armour, float damage) {
        armour.damageItem((int) (damage / 5F) + 1, user);
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armour, int slot) {
        float max = armour.getMaxDamage();
        float dam = max - armour.getItemDamage();

        return Math.round(5F / max * dam);
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armour, DamageSource source, double damage,
                                         int slot) {
        float AC = getProtectionRatio(armour);
        AC = ArmourCalculator.getArmourValueMod(armour, AC);

        if (ArmourCalculator.advancedDamageTypes && !player.worldObj.isRemote) {
            AC = ArmourCalculator.adjustACForDamage(source, AC, getProtectiveTrait(armour, 0),
                    getProtectiveTrait(armour, 1), getProtectiveTrait(armour, 2));
        }

        AC *= getACModifier(player, armour, source, damage);

        if (source == DamageSource.fall && design == ArmourDesign.PLATE) {
            AC = 1F / material.armourWeight;
        } else if (source.isMagicDamage()) {
            AC = getMagicAC(AC, source, damage, player);
        } else if (source == DamageSource.onFire) {
            AC *= getACForBurn();
        } else if (source.isUnblockable()) {
            AC *= getUnblockableResistance(armour, source);
        }
        AC *= getSpecialModifier(armour, source);

        if (player.getEntityData().hasKey("MF_ZombieArmour")) {
            AC -= 1.5F;
            if (AC < 0)
                AC = 0;
        }
        AC++;// because 1.0AC = no armour so it adds ontop of this

        double totalPercent = ArmourCalculator.convertToPercent(AC);
        double maxPercent = 0.99D;// max percentage is 99% damage to avoid immunity

        if (totalPercent > maxPercent) {
            totalPercent = maxPercent;
        }
        double percent = totalPercent * scalePiece();
        if (percent < 0) {
            percent = 0;
        }

        return new ArmorProperties(0, percent, Integer.MAX_VALUE);
    }

    protected float getSpecialModifier(ItemStack armour, DamageSource source) {
        return 1.0F;
    }

    private float getACForBurn() {
        return armourWeight >= ArmourCalculator.encumberanceArray[1] ? 0.1F
                : armourWeight >= ArmourCalculator.encumberanceArray[0] ? 0.05F : 0F;
    }

    public float getUnblockableResistance(ItemStack item, DamageSource source) {
        return 0F;
    }

    public float getMagicAC(float AC, DamageSource source, double damage, EntityLivingBase player) {
        return 0F;
    }

    protected float getACModifier(EntityLivingBase player, ItemStack armour, DamageSource source, double damage) {
        return 1.0F;
    }

    public float scalePiece() {
        return ArmourCalculator.sizes[piece];
    }

    protected float getProtectionRatio(ItemStack item) {
        return getBaseAC();
    }

    protected float getBaseAC() {
        return material.baseAR * design.getRating();
    }

    private void addModifier(List list, ItemStack item, float ratio, String name) {
        if (getProtectionRatio(item) != ratio) {
            if (getProtectionRatio(item) > ratio) {
                float percent = (ratio / getProtectionRatio(item)) - 1F;
                list.add(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted(
                        "attribute.modifier.take." + 1, decimal_format.format(-percent * 100),
                        StatCollector.translateToLocal("attribute.armour." + name)));
            } else {
                float percent = (ratio / getProtectionRatio(item)) - 1F;
                list.add(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocalFormatted(
                        "attribute.modifier.plus." + 1, decimal_format.format(percent * 100),
                        StatCollector.translateToLocal("attribute.armour." + name)));
            }
        }
    }

    public ArmourMaterialMF getMaterial() {
        return this.material;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        if (isUnbreakable()) {
            setUnbreakable(stack);
        }
        return ToolHelper.setDuraOnQuality(stack, super.getMaxDamage());
    }

    protected boolean isUnbreakable() {
        return false;
    }

    public static void setUnbreakable(ItemStack tool) {
        if (!tool.hasTagCompound()) {
            tool.setTagCompound(new NBTTagCompound());
        }
        tool.getTagCompound().setBoolean("Unbreakable", true);
    }

    @Override
    public float scalePiece(ItemStack item) {
        return scalePiece();
    }

    @Override
    public int getItemEnchantability() {
        return material.enchantment;
    }

    @Override
    public float getPieceWeight(ItemStack item, int slot) {
        return armourWeight * ArmourCalculator.sizes[slot];
    }

    @Override
    public String getSuitWeigthType(ItemStack item) {
        return design.getGroup();
    }

    @Override
    public float getDTValue(EntityLivingBase user, ItemStack armour, DamageSource src) {
        float DT2 = DT;

        if (ArmourCalculator.advancedDamageTypes && !user.worldObj.isRemote) {
            DT2 = ArmourCalculator.adjustACForDamage(src, DT2, getProtectiveTrait(armour, 0),
                    getProtectiveTrait(armour, 1), getProtectiveTrait(armour, 2));
        }
        return DT2;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getDTDisplay(ItemStack armour, int damageType) {
        return design.getProtectiveTraits()[damageType] * DT;
    }

    @Override
    public float getDRValue(EntityLivingBase user, ItemStack armour, DamageSource src) {
        float DR = getProtectionRatio(armour) * scalePiece();

        if (ArmourCalculator.advancedDamageTypes && !user.worldObj.isRemote) {
            DR = ArmourCalculator.adjustACForDamage(src, DR, getProtectiveTrait(armour, 0),
                    getProtectiveTrait(armour, 1), getProtectiveTrait(armour, 2));
        }
        return DR;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getDRDisplay(ItemStack armour, int damageType) {
        float DR = getProtectionRatio(armour) * scalePiece();

        if (ArmourCalculator.advancedDamageTypes) {
            DR = ArmourCalculator.modifyACForType(damageType, DR, getProtectiveTrait(armour, 0),
                    getProtectiveTrait(armour, 1), getProtectiveTrait(armour, 2));
        }
        return DR;
    }

    public float getProtectiveTrait(ItemStack item, int dtype) {
        return design.getProtectiveTraits()[dtype];
    }

    /**
     * Gets a modifier, improving resistances against the elements
     *
     * @param hazard is the type of damage, like "fire"
     */
    public float getResistanceModifier(ItemStack item, String hazard) {
        return 1.0F;
    }
}
