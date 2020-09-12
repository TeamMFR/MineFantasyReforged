package minefantasy.mfr.item.armour;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.armour.ArmourDesign;
import minefantasy.mfr.api.helpers.ArmourCalculator;
import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.init.CustomArmourListMFR;
import minefantasy.mfr.material.BaseMaterialMFR;
import minefantasy.mfr.mechanics.CombatMechanics;
import minefantasy.mfr.util.MFRLogUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemCustomArmour extends ItemArmourMFR {
    private String specialDesign = "standard";
    private float ratingModifier = 1.0F;

    public ItemCustomArmour(String craftDesign, String name, ArmourDesign AD, EntityEquipmentSlot slot, String tex, int rarity) {
        super(craftDesign + "_" + name, BaseMaterialMFR.IRON, AD, slot, craftDesign + "_" + tex, rarity);
        this.specialDesign = craftDesign;
        canRepair = false;
    }

    /**
     * Adds a suit ONLY IF the material ingot exists
     */
    public static void tryAddSuits(List list, String plating) {
        NonNullList<ItemStack> mats = OreDictionary.getOres("ingot" + plating);
        if (MineFantasyReborn.isDebug() || (mats != null && !mats.isEmpty())) {
            addSuits(list, plating);
        }
    }

    public static void addSuits(List<ItemStack> list, String material) {
        list.add(((ItemCustomArmour)CustomArmourListMFR.STANDARD_CHAIN_HELMET).construct(material));
        list.add(((ItemCustomArmour)CustomArmourListMFR.STANDARD_CHAIN_CHEST).construct(material));
        list.add(((ItemCustomArmour)CustomArmourListMFR.STANDARD_CHAIN_LEGS).construct(material));
        list.add(((ItemCustomArmour)CustomArmourListMFR.STANDARD_CHAIN_BOOTS).construct(material));

        list.add(((ItemCustomArmour)CustomArmourListMFR.STANDARD_SCALE_HELMET).construct(material));
        list.add(((ItemCustomArmour)CustomArmourListMFR.STANDARD_SCALE_CHEST).construct(material));
        list.add(((ItemCustomArmour)CustomArmourListMFR.STANDARD_SCALE_LEGS).construct(material));
        list.add(((ItemCustomArmour)CustomArmourListMFR.STANDARD_SCALE_BOOTS).construct(material));

        list.add(((ItemCustomArmour)CustomArmourListMFR.STANDARD_SPLINT_HELMET).construct(material));
        list.add(((ItemCustomArmour)CustomArmourListMFR.STANDARD_SPLINT_CHEST).construct(material));
        list.add(((ItemCustomArmour)CustomArmourListMFR.STANDARD_SPLINT_LEGS).construct(material));
        list.add(((ItemCustomArmour)CustomArmourListMFR.STANDARD_SPLINT_BOOTS).construct(material));

        list.add(((ItemCustomArmour)CustomArmourListMFR.STANDARD_PLATE_HELMET).construct(material));
        list.add(((ItemCustomArmour)CustomArmourListMFR.STANDARD_PLATE_CHEST).construct(material));
        list.add(((ItemCustomArmour)CustomArmourListMFR.STANDARD_PLATE_LEGS).construct(material));
        list.add(((ItemCustomArmour)CustomArmourListMFR.STANDARD_PLATE_BOOTS).construct(material));
    }

    public ItemCustomArmour modifyRating(float rating) {
        this.ratingModifier = rating;
        return this;
    }

    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
        super.damageArmor(entity, stack, source, damage, slot);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack item) {
        String unlocalName = this.getUnlocalizedNameInefficiently(item) + ".name";
        return CustomToolHelper.getLocalisedName(item, unlocalName);
    }

    @Override
    public String getArmourTextureName(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        String tex = "minefantasy2:textures/models/armour/custom/" + specialDesign + "/" + texture;
        if (type == null)// bottom layer
        {
            return tex;// COLOUR LAYER
        }
        return tex + "_detail";// STATIC LAYER
    }

    @Override
    public boolean hasColor(ItemStack item) {
        return true;
    }

    @Override
    public boolean canColour() {
        return true;
    }

    /**
     * Return the colour of the material it is made of.
     */
    @Override
    public int getBaseColour(ItemStack item) {
        CustomMaterial material = getCustomMaterial(item);
        if (material == null) {
            return (255 << 16) + (255 << 8) + 255;
        }
        return material.getColourInt();
    }

    @Override
    public CustomMaterial getCustomMaterial(ItemStack item) {
        CustomMaterial material = CustomMaterial.getMaterialFor(item, CustomToolHelper.slot_main);
        if (material != null) {
            return material;
        }
        return null;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (!isInCreativeTab(tab)) {
            return;
        }
        ArrayList<CustomMaterial> metal = CustomMaterial.getList("metal");
        Iterator iteratorMetal = metal.iterator();
        if (this.getCreativeTab() != CreativeTabMFR.tabArmour) {
            while (iteratorMetal.hasNext()) {
                CustomMaterial customMat = (CustomMaterial) iteratorMetal.next();

                if (customMat.getItemStack() != null) {
                    items.add(construct(customMat.name));
                }
            }
            return;
        }
        if (this != CustomArmourListMFR.STANDARD_CHAIN_BOOTS)
            return;

        while (iteratorMetal.hasNext()) {
            CustomMaterial customMat = (CustomMaterial) iteratorMetal.next();

            if (customMat.getItemStack() != null) {
                addSuits(items, customMat.name);
            }
        }
    }

    @Override
    public float getPieceWeight(ItemStack item) {
        float baseWeight = armourWeight * ArmourCalculator.sizes[getEquipmentSlot().getIndex()];
        CustomMaterial material = this.getCustomMaterial(item);
        if (material != null) {
            baseWeight *= material.density;
        }
        return baseWeight;
    }

    public int getMaxDamage(ItemStack stack) {
        CustomMaterial material = this.getCustomMaterial(stack);
        if (material != null) {
            return (int) ((material.durability * 250) * (design.getDurability() / 2F));
        }
        return getMaxDamage();
    }

    public boolean isCustom() {
        return true;
    }

    @Override
    protected float getSpecialModifier(ItemStack armour, DamageSource source) {
        float modifier = CombatMechanics.getSpecialModifier(this.getCustomMaterial(armour), this.specialDesign, source.getImmediateSource(), false);

        MFRLogUtil.logDebug("Modifier = " + modifier);

        return super.getSpecialModifier(armour, source) * modifier;
    }

    @Override
    protected float getProtectionRatio(ItemStack item) {
        return super.getProtectionRatio(item) * ratingModifier;
    }
}
