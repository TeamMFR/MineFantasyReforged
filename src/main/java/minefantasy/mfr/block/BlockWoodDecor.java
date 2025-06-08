package minefantasy.mfr.block;

import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.registry.CustomMaterialRegistry;
import minefantasy.mfr.tile.TileEntityWoodDecor;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public abstract class BlockWoodDecor extends BlockTileEntity<TileEntityWoodDecor> {
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	private final String texture;
	private boolean isPlayerCreative;

	public BlockWoodDecor(String texture) {
		super(Material.WOOD);
		this.texture = texture;
	}

	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}

	/**
	 * Get the geometry of the queried face at the given position and state. This is used to decide whether things like
	 * buttons are allowed to be placed on the face, or how glass panes connect to the face, among other things.
	 * <p>
	 * Common values are {@code SOLID}, which is the default, and {@code UNDEFINED}, which represents something that
	 * does not fit the other descriptions and will generally cause other things not to connect to the face.
	 *
	 * @return an approximation of the form of the given face
	 */
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return null;
	}

	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		this.isPlayerCreative = player.capabilities.isCreativeMode;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase user, ItemStack stack) {
		TileEntityWoodDecor tile = getTile(world, pos);
		if (tile != null) {
			CustomMaterial material = CustomToolHelper.getCustomPrimaryMaterial(stack);
			if (material != CustomMaterialRegistry.NONE) {
				tile.setMaterial(material);
			}
		}
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		TileEntityWoodDecor tile = getTile(world, pos);
		if (tile != null){
			return construct(tile.getMaterialName());
		}
		return new ItemStack(Item.getItemFromBlock(this), 1, this.damageDropped(state));
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Items.AIR;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntityWoodDecor tile = getTile(world, pos);

		if (tile != null && !isPlayerCreative) {
			ItemStack itemstack = new ItemStack(Item.getItemFromBlock(this));
			itemstack = modifyDrop(tile, itemstack);
			spawnAsEntity(world, pos, itemstack);
		}
		super.breakBlock(world, pos, state);
	}

	protected ItemStack modifyDrop(TileEntityWoodDecor tile, ItemStack item) {
		if (tile != null && !item.isEmpty()) {
			CustomMaterialRegistry.addMaterial(item, CustomToolHelper.slot_main, tile.getMaterialName());
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
		CustomMaterialRegistry.addMaterial(item, CustomToolHelper.slot_main, name.toLowerCase());

		return item;
	}
}
