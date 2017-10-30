package minefantasy.mf2.api.tool;

import net.minecraft.item.ItemStack;

public interface IToolMF {
    /**
     * Gets the efficienty of this tool for processing (recommended to use
     * ToolMaterial.getEfficiencyOnProperMaterial())
     */
    public float getEfficiency(ItemStack item);

    /**
     * Gets the tier of the material, some recipes may need certain tiers
     */
    public int getTier(ItemStack item);

    /**
     * Gets the type of tool for crafting (eg, hammer, knife, needle)
     */
    public String getToolType(ItemStack item);
}
