package minefantasy.mf2.item.tool.crafting;

import com.google.common.collect.Sets;
import cpw.mods.fml.common.registry.GameRegistry;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.helpers.ToolHelper;
import minefantasy.mf2.api.tier.IToolMaterial;
import minefantasy.mf2.api.tool.IToolMF;
import minefantasy.mf2.api.weapon.IDamageType;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.item.list.ToolListMF;
import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

/**
 * @author Anonymous Productions
 */
public class ItemEngineerTool extends ItemTool implements IToolMaterial, IToolMF, IDamageType {
    private ToolMaterial material;
    private int tier;
    private String toolType;
    private int itemRarity;

    public ItemEngineerTool(String name, ToolMaterial material, int tier, String toolType, int rarity) {
        super(1.0F, material, Sets.newHashSet(new Block[]{}));
        this.toolType = toolType;
        this.material = material;
        this.tier = tier;
        itemRarity = rarity;
        setCreativeTab(CreativeTabMF.tabCraftTool);

        setTextureName("minefantasy2:Tool/Crafting/Engineer/" + name);
        GameRegistry.registerItem(this, name, MineFantasyII.MODID);
        this.setUnlocalizedName(name);
    }

    @Override
    public EnumRarity getRarity(ItemStack item) {
        int lvl = itemRarity + 1;

        if (item.isItemEnchanted()) {
            if (lvl == 0) {
                lvl++;
            }
            lvl++;
        }
        if (lvl >= ToolListMF.rarity.length) {
            lvl = ToolListMF.rarity.length - 1;
        }
        return ToolListMF.rarity[lvl];
    }

    @Override
    public ToolMaterial getMaterial() {
        return toolMaterial;
    }

    @Override
    public float getEfficiency(ItemStack item) {
        return ToolHelper.modifyDigOnQuality(item, material.getEfficiencyOnProperMaterial());
    }

    @Override
    public int getTier(ItemStack item) {
        return tier;
    }

    @Override
    public String getToolType(ItemStack item) {
        return toolType;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return ToolHelper.setDuraOnQuality(stack, super.getMaxDamage());
    }

    @Override
    public float[] getDamageRatio(Object... implement) {
        return new float[]{0, 1, 0};
    }

    @Override
    public float getPenetrationLevel(Object implement) {
        return 0F;
    }
}
