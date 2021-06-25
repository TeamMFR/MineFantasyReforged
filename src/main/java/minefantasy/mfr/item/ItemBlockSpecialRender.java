package minefantasy.mfr.item;

import minefantasy.mfr.block.BlockTileEntity;
import net.minecraft.item.ItemBlock;

public class ItemBlockSpecialRender extends ItemBlock {

	public ItemBlockSpecialRender(BlockTileEntity block) {
		super(block);

		//noinspection ConstantConditions
		setRegistryName(block.getRegistryName());
	}


}
