package minefantasy.mfr.block;

import minefantasy.mfr.tile.TileEntityWorldGenMarker;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockWorldGenMarker extends BlockTileEntity<TileEntityWorldGenMarker> {

	public BlockWorldGenMarker(String name) {
		super(Material.AIR);
		this.setBlockUnbreakable();
		setRegistryName(name);

	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityWorldGenMarker();
	}
}
