package minefantasy.mfr.item.archery;


import codechicken.lib.model.ModelRegistryHelper;
import codechicken.lib.render.ModelHelper;
import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.archery.AmmoMechanicsMFR;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.material.BaseMaterialMFR;
import minefantasy.mfr.proxy.IClientRegister;
import minefantasy.mfr.util.ModelLoaderHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import minefantasy.mfr.api.archery.IArrowMFR;
import minefantasy.mfr.api.archery.IAmmo;
import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.entity.EntityArrowMFR;
import minefantasy.mfr.mechanics.MFArrowDispenser;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Anonymous Productions
 */
public class ItemArrowMFR extends Item implements IArrowMFR, IAmmo, IClientRegister {
    public static final DecimalFormat decimal_format = new DecimalFormat("#.##");
    public static final MFArrowDispenser dispenser = new MFArrowDispenser();
    protected float damage;
    protected String arrowName;
    protected ArrowType design;
    protected int itemRarity;
    private ToolMaterial arrowMat;
    private String ammoType = "arrow";
    // ===================================================== CUSTOM START
    // =============================================================\\
    private boolean isCustom = false;

    public ItemArrowMFR(String name, ArrowType type, int stackSize) {
        this(name, 0, ToolMaterial.WOOD, type);
        setMaxStackSize(stackSize);
    }

    public ItemArrowMFR(String name, ArrowType type) {
        this(name, 0, ToolMaterial.WOOD, type);
    }

    public ItemArrowMFR(String name) {
        this(name, 0, ToolMaterial.WOOD);
    }

    public ItemArrowMFR(String name, int rarity, ToolMaterial material) {
        this(name, rarity, material, ArrowType.NORMAL);
    }

    public ItemArrowMFR(String name, int rarity, ToolMaterial material, ArrowType type) {
        name = convertName(name);
        material = convertMaterial(material);

        super.setUnlocalizedName((type == ArrowType.EXPLOSIVE || type == ArrowType.EXPLOSIVEBOLT) ? name
                : type == ArrowType.BOLT ? (name + "_bolt") : (name + "_arrow"));
        name = getName(name, type);
        design = type;
        arrowName = name;
        arrowMat = material;
        damage = (3 + material.getAttackDamage()) * type.damageModifier;
        if (type == ArrowType.EXPLOSIVE || type == ArrowType.EXPLOSIVEBOLT) {
            damage = 1;
        }
        itemRarity = rarity;
        setRegistryName("MF_Com_" + name);
        setUnlocalizedName(name);

        setCreativeTab(CreativeTabMFR.tabOldTools);
        AmmoMechanicsMFR.addArrow(new ItemStack(this));
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, dispenser);
    }

    private ToolMaterial convertMaterial(ToolMaterial material) {
        if (material == BaseMaterialMFR.getMaterial("ornate").getToolConversion()) {
            return BaseMaterialMFR.getMaterial("silver").getToolConversion();
        }
        return material;
    }

    private String convertName(String name) {
        if (name.equalsIgnoreCase("ornate")) {
            return "silver";
        }
        return name;
    }

    private String getName(String mat, ArrowType type) {
        if (type == ArrowType.EXPLOSIVE) {
            return "exploding_arrow";
        }
        if (type == ArrowType.EXPLOSIVEBOLT) {
            return "exploding_bolt";
        }
        if (type.name.equalsIgnoreCase("normal")) {
            return mat + "_arrow";
        }
        if (type.name.equalsIgnoreCase("bolt")) {
            return mat + "_bolt";
        }
        return mat + "_arrow_" + type.name.toLowerCase();
    }

    public EntityArrowMFR getFiredArrow(EntityArrowMFR instance, ItemStack arrow) {
        instance.modifyVelocity(design.velocity);
        return instance.setArrow(arrow).setArrowTex(arrowName);
    }

    @Override
    public void onHitEntity(Entity arrowInstance, Entity shooter, Entity hit, float damage) {
        if (arrowMat == BaseMaterialMFR.getMaterial("dragonforge").getToolConversion()) {
            hit.setFire((int) (damage * (arrowInstance.isBurning() ? 2.0F : 1.0F)));
        }
    }

    public ItemArrowMFR setAmmoType(String type) {
        ammoType = type;
        return this;
    }

    @Override
    public String getAmmoType(ItemStack arrow) {
        return ammoType;
    }

    public ItemArrowMFR setCustom(String designType) {
        canRepair = false;
        isCustom = true;
        return this;
    }

    @Override
    public float getDamageModifier(ItemStack arrow) {
        return (4F + CustomToolHelper.getMeleeDamage(arrow, damage)) * design.damageModifier;
    }

    @Override
    public float getGravityModifier(ItemStack arrow) {
        float weight = 1.0F * design.weightModifier;
        return CustomToolHelper.getWeightModifier(arrow, weight);
    }

    @Override
    public EnumRarity getRarity(ItemStack item) {
        return CustomToolHelper.getRarity(item, itemRarity);
    }

    public ItemStack construct(String main) {
        return CustomToolHelper.construct(this, main, null);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (!isInCreativeTab(tab)) {
            return;
        }
        if (isCustom) {
            ArrayList<CustomMaterial> metal = CustomMaterial.getList("metal");
            Iterator iteratorMetal = metal.iterator();
            while (iteratorMetal.hasNext()) {
                CustomMaterial customMat = (CustomMaterial) iteratorMetal.next();
                if (MineFantasyReborn.isDebug() || customMat.getItem() != null) {
                    items.add(this.construct(customMat.name));
                }
            }
        }
    }

    @Override
    public void addInformation(ItemStack item, World world, List list, ITooltipFlag flag) {
        if (isCustom) {
            CustomToolHelper.addInformation(item, list);
        }
        super.addInformation(item, world, list, flag);
        list.add(TextFormatting.BLUE + I18n.translateToLocal("attribute.arrowPower.name") + ": "
                + decimal_format.format(getDamageModifier(item)));
    }

    @Override
    public String getItemStackDisplayName(ItemStack item) {
        String name = ("" + I18n.translateToLocal(this.getUnlocalizedNameInefficiently(item) + ".name"))
                .trim();

        if (isCustom)
            name = CustomToolHelper.getLocalisedName(item, name);

        if (design != ArrowType.NORMAL && design != ArrowType.EXPLOSIVE && design != ArrowType.BOLT
                && design != ArrowType.EXPLOSIVEBOLT) {
            name += " (" + I18n.translateToLocal("arrow.head." + design.name.toLowerCase() + ".name") + ")";
        }

        return name;
    }

    @Override
    public float getBreakChance(Entity entityArrow, ItemStack arrow) {
        int maxUses = CustomToolHelper.getMaxDamage(arrow, arrowMat.getMaxUses());
        return 1F / (maxUses / 150);
    }

    @Override
    public void registerClient() {
        ModelLoaderHelper.registerItem(this);
    }

    // ====================================================== CUSTOM END
    // ==============================================================\\
}
