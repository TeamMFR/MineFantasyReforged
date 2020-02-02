package minefantasy.mfr.block.decor;

import java.util.Random;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.block.tile.decor.TileEntityRack;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.packet.RackCommand;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * @author Anonymous Productions
 *         <p>
 *         Sources are provided for educational reasons. though small bits of
 *         code, or methods can be used in your own creations.
 */
public class BlockRack extends BlockWoodDecor {
	public static EnumBlockRenderType rack_RI;

	public static final PropertyDirection FACING = BlockDirectional.FACING;

	public BlockRack(String name) {
		super(name);

		setHardness(1.0F);
		setResistance(1.0F);
		GameRegistry.findRegistry(Block.class).register(this);
		setRegistryName(name);
		setUnlocalizedName(MineFantasyReborn.MODID + "." + name);
		this.setCreativeTab(CreativeTabMFR.tabUtil);
	}

	/**
	 * Convert standard South, West, North, East to rack's (North, South, East,
	 * West)
	 */
	public static int getDirection(int dir) {
		int[] directions = new int[] { 3, 4, 2, 5 };
		return directions[Math.min(3, dir)];
	}

	public static boolean interact(int slot, World world, TileEntityRack tile, EntityPlayer player) {
		if (player.isSneaking()) {
			player.openGui(MineFantasyReborn.instance, 0, world, tile.getPos().getX(), tile.getPos().getY(),
					tile.getPos().getZ());
			return false;
		}

		ItemStack held = player.getHeldItem(EnumHand.MAIN_HAND);
		if (held == null) {
			ItemStack hung = tile.getStackInSlot(slot);
			if (hung != null) {
				if (!world.isRemote) {
					player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, hung);
					tile.setInventorySlotContents(slot, null);
					tile.syncItems();
				}
				player.swingArm(EnumHand.MAIN_HAND);
				return true;
			}
		} else {
			ItemStack hung = tile.getStackInSlot(slot);

			if (hung == null && tile.canHang(player.getHeldItem(EnumHand.MAIN_HAND), slot)) {
				if (!world.isRemote) {
					tile.setInventorySlotContents(slot, player.getHeldItem(EnumHand.MAIN_HAND).copy());
					player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, null);
					tile.syncItems();
				}
				player.swingArm(EnumHand.MAIN_HAND);
				return true;
			} else if (held != null && hung != null) {
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
							player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, null);
						}
						player.swingArm(EnumHand.MAIN_HAND);
						return true;
					}
				}
			}
		}
		player.openGui(MineFantasyReborn.instance, 0, world, tile.getPos().getX(), tile.getPos().getY(),
				tile.getPos().getZ());
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
	 * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z,
	 * side, hitX, hitY, hitZ, block metadata
	 */

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return rack_RI;
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
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 4);
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which
	 * neighbor changed (coordinates passed are their own) Args: x, y, z, neighbor
	 * Block
	 */
	@Override
	public void onNeighborChange(IBlockAccess worldIn, BlockPos pos, BlockPos neighbor) {
		TileEntity tile = worldIn.getTileEntity(pos);
		World world = tile.getWorld();
		if (tile != null && tile instanceof TileEntityRack) {
			((TileEntityRack) tile).updateInventory();
		}
		int l = getMetaFromState(world.getBlockState(pos));
		boolean flag = false;

		if (l == 2 && world.isSideSolid(pos.south(), EnumFacing.NORTH)) {
			flag = true;
		}

		if (l == 3 && world.isSideSolid(pos.north(), EnumFacing.SOUTH)) {
			flag = true;
		}

		if (l == 4 && world.isSideSolid(pos.east(), EnumFacing.WEST)) {
			flag = true;
		}

		if (l == 5 && world.isSideSolid(pos.west(), EnumFacing.EAST)) {
			flag = true;
		}

		if (!flag) {
			this.dropBlockAsItem(tile.getWorld(), pos, getDefaultState(), 0);
			;
			tile.getWorld().setBlockToAir(pos);
		}
		super.onNeighborChange(world, pos, neighbor);
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random rand) {
		return 1;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int m) {
		return new TileEntityRack();
	}

	/**
	 * Called whenever the block is removed.
	 */
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntityRack tile = (TileEntityRack) world.getTileEntity(pos);

		if (tile != null) {
			for (int i = 0; i < tile.getSizeInventory(); ++i) {
				ItemStack stack = tile.getStackInSlot(i);

				if (stack != null) {
					float var8 = world.rand.nextFloat() * 0.8F + 0.1F;
					float var9 = world.rand.nextFloat() * 0.8F + 0.1F;
					float var10 = world.rand.nextFloat() * 0.8F + 0.1F;

					while (stack.getCount() > 0) {
						int randomNumber = world.rand.nextInt(21) + 10;

						if (randomNumber > stack.getCount()) {
							randomNumber = stack.getCount();
						}

						stack.setCount(stack.getCount() - randomNumber);
						EntityItem item = new EntityItem(world, x + var8, y + var9, z + var10,
								new ItemStack(stack.getItem(), randomNumber, stack.getItemDamage()));

						if (stack.hasTagCompound()) {
							item.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
						}

						float var13 = 0.05F;
						item.motionX = (float) world.rand.nextGaussian() * var13;
						item.motionY = (float) world.rand.nextGaussian() * var13 + 0.2F;
						item.motionZ = (float) world.rand.nextGaussian() * var13;
						world.spawnEntityInWorld(item);
					}
				}
			}
		}
		super.breakBlock(world, pos, state);
	}
	
//	@Override
//	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
//		TileEntityRack tile = (TileEntityRack) world.getTileEntity(x, y, z);
//
//		if (tile != null) {
//			for (int var6 = 0; var6 < tile.getSizeInventory(); ++var6) {
//				ItemStack var7 = tile.getStackInSlot(var6);
//
//				if (var7 != null) {
//					float var8 = world.rand.nextFloat() * 0.8F + 0.1F;
//					float var9 = world.rand.nextFloat() * 0.8F + 0.1F;
//					float var10 = world.rand.nextFloat() * 0.8F + 0.1F;
//
//					while (var7.stackSize > 0) {
//						int var11 = world.rand.nextInt(21) + 10;
//
//						if (var11 > var7.stackSize) {
//							var11 = var7.stackSize;
//						}
//
//						var7.stackSize -= var11;
//						EntityItem var12 = new EntityItem(world, x + var8, y + var9, z + var10,
//								new ItemStack(var7.getItem(), var11, var7.getItemDamage()));
//
//						if (var7.hasTagCompound()) {
//							var12.getEntityItem().setTagCompound((NBTTagCompound) var7.getTagCompound().copy());
//						}
//
//						float var13 = 0.05F;
//						var12.motionX = (float) world.rand.nextGaussian() * var13;
//						var12.motionY = (float) world.rand.nextGaussian() * var13 + 0.2F;
//						var12.motionZ = (float) world.rand.nextGaussian() * var13;
//						world.spawnEntityInWorld(var12);
//					}
//				}
//			}
//		}
//
//		super.breakBlock(world, x, y, z, block, meta);
//	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer user,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntityRack tile = (TileEntityRack) world.getTileEntity(pos);
		if (world.isRemote) {
			int slot = tile.getSlotFor(hitX,hitZ);
			if (slot >= 0 && slot < 4) {
				((EntityPlayerMP) user).comasendQueue.addToSendQueue(new RackCommand(slot, user, tile).generatePacket());
			}
		}

		return true;
	}
}
