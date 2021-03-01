package minefantasy.mfr.integration;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.oredict.OreDictionary;

public class CustomStone {

	private static NonNullList<ItemStack> customStones;

	public static void init() {
		String stoneEntry = ForgeVersion.getBuildVersion() < 934 ? "stoneSmooth" : "stone";
		if (!OreDictionary.doesOreNameExist(stoneEntry))
			return;
		customStones = OreDictionary.getOres(stoneEntry);
	}

	public static boolean isStone(Block block) {
		return block == Blocks.STONE || isStone(new ItemStack(block));
	}

	public static boolean isStone(ItemStack stack) {
		for (ItemStack stoneStack : customStones) {
			if (OreDictionary.itemMatches(stoneStack, stack, false)) {
				return true;
			}
		}
		return false;
	}
}
