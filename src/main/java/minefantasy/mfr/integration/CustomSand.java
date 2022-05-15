package minefantasy.mfr.integration;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class CustomSand {

	private static NonNullList<ItemStack> customSands;

	public static void init() {
		if (!OreDictionary.doesOreNameExist("sand"))
			return;
		customSands = OreDictionary.getOres("sand");
	}

	public static boolean isSand(Block block) {
		return block == Blocks.SAND || isSand(new ItemStack(block));
	}

	public static boolean isSand(ItemStack stack) {
		for (ItemStack sandStack : customSands) {
			if (OreDictionary.itemMatches(sandStack, stack, false)) {
				return true;
			}
		}
		return false;
	}
}
