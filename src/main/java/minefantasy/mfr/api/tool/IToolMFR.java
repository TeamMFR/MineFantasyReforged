package minefantasy.mfr.api.tool;

import minefantasy.mfr.constants.Tool;
import net.minecraft.item.ItemStack;

public interface IToolMFR {

	/**
	 * Gets the efficiency of this tool for processing (recommended to use
	 */
	float getEfficiency(ItemStack item);

	/**
	 * Gets the tier of the material, some recipes may need certain tiers
	 */
	int getTier(ItemStack item);

	Tool getToolType(ItemStack stack);
}
