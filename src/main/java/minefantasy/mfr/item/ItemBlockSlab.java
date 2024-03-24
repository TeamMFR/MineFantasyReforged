package minefantasy.mfr.item;

import minefantasy.mfr.block.BlockSlab;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockSlab extends ItemBlockBase{

	BlockSlab blockSlab;

	public ItemBlockSlab(BlockSlab blockSlab) {
		super(blockSlab);
		this.blockSlab = blockSlab;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing,
			float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		IBlockState state = world.getBlockState(pos);
		int meta = state.getBlock().getMetaFromState(state);
		IBlockState newState = this.blockSlab.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, player);


		if (!stack.isEmpty() && player.canPlayerEdit(pos.offset(facing), facing, stack)) {
			if (newState != state ) {
				AxisAlignedBB axisalignedbb = newState.getCollisionBoundingBox(world, pos);
				BlockSlab.SlabType slabType = state.getValue(BlockSlab.TYPE);

				if (axisalignedbb != Block.NULL_AABB && world.checkNoEntityCollision(axisalignedbb.offset(pos))) {
					if (facing == EnumFacing.UP && slabType == BlockSlab.SlabType.BOTTOM
							&& world.setBlockState(pos, newState, 11)) {
						handleBlockPlacementEffects(player, world, pos, newState, stack);
						return EnumActionResult.SUCCESS;
					}
					if (facing == EnumFacing.DOWN && slabType == BlockSlab.SlabType.TOP
							&& world.setBlockState(pos, newState, 11)) {
						handleBlockPlacementEffects(player, world, pos, newState, stack);
						return EnumActionResult.SUCCESS;
					}
				}
			}
			return this.tryPlace(player, stack, world, pos.offset(facing), facing, hitX, hitY, hitZ, meta)
					? EnumActionResult.SUCCESS
					: super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
		}
		return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
	}

	@SideOnly(Side.CLIENT)
	public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing facing, EntityPlayer player, ItemStack stack) {
		BlockPos blockpos = pos;
		IBlockState state = world.getBlockState(pos);

		if (state.getBlock() == this.blockSlab) {
			boolean isTop = state.getValue(BlockSlab.TYPE) == BlockSlab.SlabType.TOP;

			if ((facing == EnumFacing.UP && !isTop || facing == EnumFacing.DOWN && isTop)) {
				return true;
			}
		}

		pos = pos.offset(facing);
		IBlockState offsetState = world.getBlockState(pos);
		if (offsetState.getBlock() == this.blockSlab ) {
			return true;
		}
		else {
			return super.canPlaceBlockOnSide(world, blockpos, facing, player, stack);
		}
	}

	private boolean tryPlace(EntityPlayer player, ItemStack stack, World world, BlockPos pos, EnumFacing facing,
			float hitX, float hitY, float hitZ, int meta )
	{
		IBlockState iblockstate = world.getBlockState(pos);

		if (iblockstate.getBlock() == this.blockSlab)
		{
			IBlockState newBlockState = this.blockSlab.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, player);
			AxisAlignedBB axisalignedbb = newBlockState.getCollisionBoundingBox(world, pos);

			if (axisalignedbb != Block.NULL_AABB && world.checkNoEntityCollision(axisalignedbb.offset(pos))
					&& world.setBlockState(pos, newBlockState, 11)) {
				handleBlockPlacementEffects(player, world, pos, newBlockState, stack);
			}

			return true;
		}

		return false;
	}

	private void handleBlockPlacementEffects(EntityPlayer player, World world, BlockPos pos, IBlockState newState, ItemStack stack) {
		SoundType soundtype = this.blockSlab.getSoundType(newState, world, pos, player);
		world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
		stack.shrink(1);
	}
}
