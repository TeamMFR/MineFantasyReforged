package minefantasy.mfr.item;

import minefantasy.mfr.block.BlockOreMF;
import minefantasy.mfr.init.MineFantasyItems;
import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemBlockOreMFR extends ItemBlockBase {
	private BlockOreMF ore;

	public ItemBlockOreMFR(Block block) {
		super(block);
		ore = (BlockOreMF) block;

	}

	@Override
	public EnumRarity getRarity(ItemStack item) {
		int lvl = ore.rarity + 1;

		if (item.isItemEnchanted()) {
			if (lvl == 0) {
				lvl++;
			}
			lvl++;
		}
		if (lvl >= MineFantasyItems.RARITY.length) {
			lvl = MineFantasyItems.RARITY.length - 1;
		}
		return MineFantasyItems.RARITY[lvl];
	}
}
