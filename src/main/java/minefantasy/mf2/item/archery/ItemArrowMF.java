package minefantasy.mf2.item.archery;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.archery.AmmoMechanicsMF;
import minefantasy.mf2.api.archery.IAmmo;
import minefantasy.mf2.api.archery.IArrowMF;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.entity.EntityArrowMF;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.material.BaseMaterialMF;
import minefantasy.mf2.mechanics.MFArrowDispenser;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Anonymous Productions
 */
public class ItemArrowMF extends Item implements IArrowMF, IAmmo {
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
    @SideOnly(Side.CLIENT)
    private IIcon detailTex;

    public ItemArrowMF(String name, ArrowType type, int stackSize) {
        this(name, 0, ToolMaterial.WOOD, type);
        setMaxStackSize(stackSize);
    }

    public ItemArrowMF(String name, ArrowType type) {
        this(name, 0, ToolMaterial.WOOD, type);
    }

    public ItemArrowMF(String name) {
        this(name, 0, ToolMaterial.WOOD);
    }

    public ItemArrowMF(String name, int rarity, ToolMaterial material) {
        this(name, rarity, material, ArrowType.NORMAL);
    }

    public ItemArrowMF(String name, int rarity, ToolMaterial material, ArrowType type) {
        name = convertName(name);
        material = convertMaterial(material);

        super.setUnlocalizedName((type == ArrowType.EXPLOSIVE || type == ArrowType.EXPLOSIVEBOLT) ? name
                : type == ArrowType.BOLT ? (name + "_bolt") : (name + "_arrow"));
        name = getName(name, type);
        design = type;
        arrowName = name;
        arrowMat = material;
        damage = (3 + material.getDamageVsEntity()) * type.damageModifier;
        if (type == ArrowType.EXPLOSIVE || type == ArrowType.EXPLOSIVEBOLT) {
            damage = 1;
        }
        itemRarity = rarity;
        setTextureName("minefantasy2:Ammo/" + name);
        setCreativeTab(CreativeTabMF.tabOldTools);
        GameRegistry.registerItem(this, "MF_Com_" + name, MineFantasyII.MODID);

        AmmoMechanicsMF.addArrow(new ItemStack(this));
        if(Loader.isModLoaded("battlegear2")) {
            mods.battlegear2.api.quiver.QuiverArrowRegistry.addArrowToRegistry(new ItemStack(this), null);
        }
        BlockDispenser.dispenseBehaviorRegistry.putObject(this, dispenser);
    }

    private ToolMaterial convertMaterial(ToolMaterial material) {
        if (material == BaseMaterialMF.getMaterial("ornate").getToolConversion()) {
            return BaseMaterialMF.getMaterial("silver").getToolConversion();
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

    public EntityArrowMF getFiredArrow(EntityArrowMF instance, ItemStack arrow) {
        instance.modifyVelocity(design.velocity);
        return instance.setArrow(arrow).setArrowTex(arrowName);
    }

    @Override
    public void onHitEntity(Entity arrowInstance, Entity shooter, Entity hit, float damage) {
        if (arrowMat == BaseMaterialMF.getMaterial("dragonforge").getToolConversion()) {
            hit.setFire((int) (damage * (arrowInstance.isBurning() ? 2.0F : 1.0F)));
        }
    }

    public ItemArrowMF setAmmoType(String type) {
        ammoType = type;
        return this;
    }

    @Override
    public String getAmmoType(ItemStack arrow) {
        return ammoType;
    }

    public ItemArrowMF setCustom(String designType) {
        canRepair = false;
        setTextureName("minefantasy2:custom/ammo/" + designType + "/" + arrowName);
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

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        if (isCustom) {
            detailTex = reg.registerIcon(this.getIconString() + "_detail");
        }
        super.registerIcons(reg);
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        if (isCustom && pass > 0 && detailTex != null) {
            return detailTex;
        }
        return super.getIcon(stack, pass);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack item, int layer) {
        int c = CustomToolHelper.getColourFromItemStack(item, layer, super.getColorFromItemStack(item, layer));

        return c;
    }

    public ItemStack construct(String main) {
        return CustomToolHelper.construct(this, main, null);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        if (isCustom) {
            ArrayList<CustomMaterial> metal = CustomMaterial.getList("metal");
            Iterator iteratorMetal = metal.iterator();
            while (iteratorMetal.hasNext()) {
                CustomMaterial customMat = (CustomMaterial) iteratorMetal.next();
                if (MineFantasyII.isDebug() || customMat.getItem() != null) {
                    list.add(this.construct(customMat.name));
                }
            }
        }
    }

    @Override
    public void addInformation(ItemStack item, EntityPlayer user, List list, boolean extra) {
        if (isCustom) {
            CustomToolHelper.addInformation(item, list);
        }
        super.addInformation(item, user, list, extra);
        list.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal("attribute.arrowPower.name") + ": "
                + decimal_format.format(getDamageModifier(item)));
    }

    @Override
    public String getItemStackDisplayName(ItemStack item) {
        String name = ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(item) + ".name"))
                .trim();

        if (isCustom)
            name = CustomToolHelper.getLocalisedName(item, name);

        if (design != ArrowType.NORMAL && design != ArrowType.EXPLOSIVE && design != ArrowType.BOLT
                && design != ArrowType.EXPLOSIVEBOLT) {
            name += " (" + StatCollector.translateToLocal("arrow.head." + design.name.toLowerCase() + ".name") + ")";
        }

        return name;
    }

    @Override
    public float getBreakChance(Entity entityArrow, ItemStack arrow) {
        int maxUses = CustomToolHelper.getMaxDamage(arrow, arrowMat.getMaxUses());
        return 1F / (maxUses / 150);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return isCustom;
    }
    // ====================================================== CUSTOM END
    // ==============================================================\\
}
