package minefantasy.mfr.item;

import minefantasy.mfr.block.BlockComponent;
import minefantasy.mfr.constants.Constants;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.tile.TileEntityComponent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemPersistentComponentMarker extends ItemComponentMFR {
	public ItemPersistentComponentMarker(String name) {
		super(name);
	}

	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, EnumHand hand) {
		if (world.getTileEntity(pos) != null && !(world.getTileEntity(pos) instanceof TileEntityComponent)){
			return EnumActionResult.FAIL;
		}

		EnumFacing facingForPlacement = EnumFacing.getDirectionFromEntityLiving(pos, player);

		Block block = world.getBlockState(pos).getBlock();

		if (facingForPlacement != EnumFacing.UP && block instanceof BlockComponent){
			return EnumActionResult.FAIL;
		}

		if (!(block instanceof BlockComponent) && !world.isSideSolid(pos, facingForPlacement)) {
			return EnumActionResult.FAIL;
		}

		if (!(block instanceof BlockComponent) && !world.isSideSolid(pos, facingForPlacement)) {
			return EnumActionResult.FAIL;
		}

		BlockPos posToPlace = pos.offset(facingForPlacement);

		ItemStack stack = player.getHeldItem(hand);
		//Place Component with the Persistence Flag
		if (player.canPlayerEdit(posToPlace, facing, stack)) {
			if (world.isAirBlock(posToPlace)
					&& BlockComponent.canBuildOn(world, posToPlace.down())) {

				world.setBlockState(posToPlace, MineFantasyBlocks.COMPONENTS.getDefaultState()
						.withProperty(BlockComponent.FACING, player.getHorizontalFacing().getOpposite())
						.withProperty(BlockComponent.PERSIST, true));

				TileEntity tile = world.getTileEntity(posToPlace);
				if (tile instanceof TileEntityComponent) {
					TileEntityComponent tileComponent = (TileEntityComponent) tile;
					ItemStack newItem = stack.copy();
					newItem.setCount(1);
					tileComponent.setItem(
							newItem,
							Constants.StorageTextures.PERSIST_FLAG,
							Constants.StorageTextures.PERSIST_FLAG,
							0);
				}
			}
			if (!player.capabilities.isCreativeMode){
				stack.shrink(1);
				return EnumActionResult.SUCCESS;
			}
		}

		return EnumActionResult.FAIL;
	}
}
