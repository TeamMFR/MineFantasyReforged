package minefantasy.mfr.item.tool.crafting;

import com.google.common.collect.Sets;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.helpers.ToolHelper;
import minefantasy.mfr.api.tier.IToolMaterial;
import minefantasy.mfr.api.tool.IToolMFR;
import minefantasy.mfr.api.weapon.IDamageType;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.init.ToolListMFR;
import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

/**
 * @author Anonymous Productions
 */
public class ItemEngineerTool extends ItemTool implements IToolMaterial, IToolMFR, IDamageType {
    private ToolMaterial material;
    private int tier;
    private String toolType;
    private int itemRarity;

    public ItemEngineerTool(String name, ToolMaterial material, int tier, String toolType, int rarity) {
        super(1.0F, 1.0F, material, Sets.newHashSet(new Block[]{}));
        this.toolType = toolType;
        this.material = material;
        this.tier = tier;
        itemRarity = rarity;
        setCreativeTab(CreativeTabMFR.tabCraftTool);

        setRegistryName(name);
        setUnlocalizedName(name);

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
        if (lvl >= ToolListMFR.RARITY.length) {
            lvl = ToolListMFR.RARITY.length - 1;
        }
        return ToolListMFR.RARITY[lvl];
    }

    @Override
    public ToolMaterial getMaterial() {
        return toolMaterial;
    }

    @Override
    public float getEfficiency(ItemStack item) {
        return ToolHelper.modifyDigOnQuality(item, material.getEfficiency());
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
