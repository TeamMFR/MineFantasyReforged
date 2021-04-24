package minefantasy.mfr.item;

import codechicken.lib.model.ModelRegistryHelper;
import codechicken.lib.render.item.IItemRenderer;
import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.block.BlockTileEntity;
import minefantasy.mfr.client.model.block.ModelDummyParticle;
import minefantasy.mfr.proxy.IClientRegister;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockSpecialRender extends ItemBlock {

	public ItemBlockSpecialRender(BlockTileEntity block) {
		super(block);

		//noinspection ConstantConditions
		setRegistryName(block.getRegistryName());
	}


}
