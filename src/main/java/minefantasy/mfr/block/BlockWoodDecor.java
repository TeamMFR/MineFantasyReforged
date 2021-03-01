package minefantasy.mfr.block;

import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.tile.TileEntityWoodDecor;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public abstract class BlockWoodDecor extends BlockTileEntity<TileEntityWoodDecor> {
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	private final String texture;

	public BlockWoodDecor(String texture) {
		super(Material.WOOD);
		this.texture = texture;
	}

	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return null;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase user, ItemStack stack) {
		TileEntityWoodDecor tile = getTile(world, pos);
		if (tile != null) {
			CustomMaterial material = CustomToolHelper.getCustomPrimaryMaterial(stack);
			if (material != null) {
				tile.setMaterial(material);
			}
		}
	}

	protected ItemStack modifyDrop(TileEntityWoodDecor tile, ItemStack item) {
		if (tile != null && item != null) {
			CustomMaterial.addMaterial(item, CustomToolHelper.slot_main, tile.getMaterialName());
		}
		return item;
	}

	private TileEntityWoodDecor getTile(World world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile != null && tile instanceof TileEntityWoodDecor) {
			return (TileEntityWoodDecor) tile;
		}
		return null;
	}

	public String getFullTexName() {
		return this.texture;
	}

	public ItemStack construct(String name) {
		return construct(name, 1);
	}

	public ItemStack construct(String name, int stacksize) {
		ItemStack item = new ItemStack(this, stacksize);
		CustomMaterial.addMaterial(item, CustomToolHelper.slot_main, name.toLowerCase());

		return item;
	}
}
