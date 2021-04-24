package minefantasy.mfr.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;

public class ItemBlockBase extends ItemBlock {

	public ItemBlockBase(Block block) {
		super(block);

		//noinspection ConstantConditions
		setRegistryName(block.getRegistryName());
	}
}
