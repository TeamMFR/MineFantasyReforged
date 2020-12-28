package minefantasy.mfr.block;

import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.network.NetworkHandler;
import minefantasy.mfr.network.RackCommandPacket;
import minefantasy.mfr.tile.TileEntityRack;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * @author Anonymous Productions
 *         <p>
 *         Sources are provided for educational reasons. though small bits of
 *         code, or methods can be used in your own creations.
 */
public class BlockRack extends BlockWoodDecor {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public BlockRack(String name) {
		super(name);

		setHardness(1.0F);
		setResistance(1.0F);

		setRegistryName(name);
		setUnlocalizedName(name);
		this.setCreativeTab(CreativeTabMFR.tabUtil);
	}

	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}


	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityRack();
	}

	public static boolean interact(int slot, World world, TileEntityRack tile, EntityPlayer player) {

		ItemStack held = player.getHeldItemMainhand();
		ItemStack hung = tile.getInventory().getStackInSlot(slot);
		if (held.isEmpty()) {
			if (!hung.isEmpty()) {
				if (!world.isRemote) {
					player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, hung);
					tile.getInventory().setStackInSlot(slot, ItemStack.EMPTY);
					tile.sendUpdates();
				}
				player.swingArm(EnumHand.MAIN_HAND);
				return true;
			}
		} else {
			if (hung.isEmpty() && tile.canHang(player.getHeldItemMainhand(), slot)) {
				if (!world.isRemote) {
					tile.getInventory().setStackInSlot(slot, player.getHeldItemMainhand().copy());
					player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
					tile.sendUpdates();
				}
				player.swingArm(EnumHand.MAIN_HAND);
				return true;
			} else if (!held.isEmpty() && !hung.isEmpty()) {
				if (hung.isItemEqual(held)) {
					int space = hung.getMaxStackSize() - hung.getCount();

					if (held.getCount() > space) {
						if (!world.isRemote) {
							held.shrink(space);
							hung.grow(space);
						}
						player.swingArm(EnumHand.MAIN_HAND);
						return true;
					} else {
						if (!world.isRemote) {
							hung.grow(held.getCount());
							player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
						}
						player.swingArm(EnumHand.MAIN_HAND);
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		float f = 0.25F;

		if (state.getValue(FACING) == EnumFacing.getFront(2)) {
			return new AxisAlignedBB(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
		}

		if (state.getValue(FACING) == EnumFacing.getFront(3)) {
			return new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
		}

		if (state.getValue(FACING) == EnumFacing.getFront(4)) {
			return new AxisAlignedBB(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}

		if (state.getValue(FACING) == EnumFacing.getFront(5)) {
			return new AxisAlignedBB(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
		} else {
			return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		}
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube? This determines whether or
	 * not to render the shared face of two adjacent blocks and also whether the
	 * player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	/**
	 * Checks to see if its valid to put this block at the specified coordinates.
	 * Args: world, x, y, z
	 */
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return worldIn.isSideSolid(pos.east(), EnumFacing.EAST) || worldIn.isSideSolid(pos.west(), EnumFacing.WEST)
				|| worldIn.isSideSolid(pos.south(), EnumFacing.SOUTH)
				|| worldIn.isSideSolid(pos.north(), EnumFacing.NORTH);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 4);
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random rand) {
		return 1;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntityRack tile = (TileEntityRack) world.getTileEntity(pos);
		if (!world.isRemote && tile != null) {
			int slot = tile.getSlotFor(hitX, hitZ, facing);
			if (slot >= 0 && slot < 4) {
				NetworkHandler.sendToServer(new RackCommandPacket(slot, player, tile));
			}
		}

		return true;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing enumfacing = EnumFacing.getFront(meta);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y)
		{
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(FACING).getIndex();
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
}
