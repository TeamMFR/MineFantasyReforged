package minefantasy.mfr.block;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.proxy.IClientRegister;
import minefantasy.mfr.tile.TileEntityBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockTileEntity<TE extends TileEntityBase> extends Block implements IClientRegister {

	public BlockTileEntity(final Material material, final MapColor mapColor) {
		super(material, mapColor);
		MineFantasyReforged.PROXY.addClientRegister(this);
	}

	public BlockTileEntity(final Material materialIn) {
		this(materialIn, materialIn.getMaterialMapColor());
	}

	@Override
	public boolean hasTileEntity(final IBlockState state) {
		return true;
	}

	@Override
	public abstract TileEntity createTileEntity(World world, IBlockState state);

	protected static TileEntityBase getTile(IBlockAccess world, BlockPos pos) {
		return (TileEntityBase) world.getTileEntity(pos);
	}

	public String getTexture() {
		return "planks_oak";
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {

		TileEntityBase tile = getTile(world, pos);
		tile.onBlockBreak();
		world.removeTileEntity(pos);
		super.breakBlock(world, pos, state);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerClient() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "normal"));
	}
}
