package minefantasy.mf2.item.tool.crafting;

import com.google.common.collect.Sets;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.tier.IToolMaterial;
import minefantasy.mf2.api.tool.IToolMF;
import minefantasy.mf2.api.weapon.IDamageType;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

import java.util.List;

/**
 * @author Anonymous Productions
 */
public class ItemBasicCraftTool extends ItemTool implements IToolMaterial, IToolMF, IDamageType {
    protected int itemRarity;
    private int tier;
    private String name;
    private String toolType;
    // ===================================================== CUSTOM START
    // =============================================================\\
    private boolean isCustom = false;
    private float efficiencyMod = 1.0F;

    public ItemBasicCraftTool(String name, String type, int tier, int uses) {
        super(1.0F, ToolMaterial.WOOD, Sets.newHashSet(new Block[]{}));
        this.tier = tier;
        setCreativeTab(CreativeTabMF.tabCraftTool);

        this.name = name;
        toolType = type;
        setTextureName("minefantasy2:Tool/Crafting/" + name);
        GameRegistry.registerItem(this, name, MineFantasyII.MODID);
        this.setUnlocalizedName(name);
        setMaxDamage(uses);
        this.setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack item, int layer) {
        return CustomToolHelper.getColourFromItemStack(item, layer, super.getColorFromItemStack(item, layer));
    }

    @Override
    public ToolMaterial getMaterial() {
        return toolMaterial;
    }

    @Override
    public String getToolType(ItemStack item) {
        return toolType;
    }

    @Override
    public float[] getDamageRatio(Object... implement) {
        return new float[]{0, 1, 0};
    }

    private void addSet(List list, Item[] items) {
        for (Item item : items) {
            list.add(new ItemStack(item));
        }
    }

    @Override
    public float getPenetrationLevel(Object implement) {
        return 0F;
    }

    public ItemBasicCraftTool setCustom(String s) {
        canRepair = false;
        setTextureName("minefantasy2:custom/tool/" + s + "/" + name);
        isCustom = true;
        return this;
    }

    public ItemStack construct(String main) {
        return CustomToolHelper.construct(this, main);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return CustomToolHelper.getMaxDamage(stack, super.getMaxDamage(stack));
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        if (isCustom) {
            list.add(this.construct("OakWood"));
            list.add(this.construct("IronbarkWood"));
            list.add(this.construct("EbonyWood"));
        } else {
            super.getSubItems(item, tab, list);
        }
    }

    @Override
    public void addInformation(ItemStack item, EntityPlayer user, List list, boolean extra) {
        super.addInformation(item, user, list, extra);
    }

    @Override
    public float getEfficiency(ItemStack item) {
        return CustomToolHelper.getEfficiencyForHds(item, toolMaterial.getEfficiencyOnProperMaterial(), efficiencyMod);
    }

    @Override
    public int getTier(ItemStack item) {
        return CustomToolHelper.getCrafterTier(item, tier);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack item) {
        String unlocalName = this.getUnlocalizedNameInefficiently(item) + ".name";
        return CustomToolHelper.getLocalisedName(item, unlocalName);
    }

    @Override
    public EnumRarity getRarity(ItemStack item) {
        return CustomToolHelper.getRarity(item, itemRarity);
    }
}
