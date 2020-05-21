package minefantasy.mfr.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public interface IMultiTexturedItem {
	ResourceLocation getModelName(ItemStack stack);
}
