package minefantasy.mfr.item.tool.crafting;

import com.google.common.collect.Sets;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.tier.IToolMaterial;
import minefantasy.mfr.api.tool.IToolMFR;
import minefantasy.mfr.api.weapon.IDamageType;
import minefantasy.mfr.init.CreativeTabMFR;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

import java.util.List;

/**
 * @author Anonymous Productions
 */
public class ItemBasicCraftTool extends ItemTool implements IToolMaterial, IToolMFR, IDamageType {
    protected int itemRarity;
    private int tier;
    private String name;
    private String toolType;
    // ===================================================== CUSTOM START
    // =============================================================\\
    private boolean isCustom = false;
    private float efficiencyMod = 1.0F;

    public ItemBasicCraftTool(String name, String type, int tier, int uses) {
        super(1.0F, 1.0F, ToolMaterial.WOOD, Sets.newHashSet(new Block[]{}));
        this.tier = tier;
        setCreativeTab(CreativeTabMFR.tabCraftTool);

        this.name = name;
        toolType = type;
        setRegistryName(name);
        setUnlocalizedName(name);

        setMaxDamage(uses);
        this.setMaxStackSize(1);
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
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isCustom) {
            items.add(this.construct("OakWood"));
            items.add(this.construct("IronbarkWood"));
            items.add(this.construct("EbonyWood"));
        } else {
            super.getSubItems(tab, items);
        }
    }

    @Override
    public void addInformation(ItemStack item, World world, List list, ITooltipFlag flag) {
        super.addInformation(item, world, list, flag);
    }

    @Override
    public float getEfficiency(ItemStack item) {
        return CustomToolHelper.getEfficiencyForHds(item, toolMaterial.getEfficiency(), efficiencyMod);
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
