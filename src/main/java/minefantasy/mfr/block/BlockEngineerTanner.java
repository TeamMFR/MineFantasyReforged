package minefantasy.mfr.block;

import codechicken.lib.model.ModelRegistryHelper;
import minefantasy.mfr.client.model.block.ModelDummyParticle;
import minefantasy.mfr.client.render.block.TileEntityTanningRackRenderer;
import minefantasy.mfr.tile.TileEntityBase;
import minefantasy.mfr.tile.TileEntityTanningRack;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockEngineerTanner extends BlockTanningRack {

	public BlockEngineerTanner(int tier, String tex) {
		super(tier, tex);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntityTanningRack tile = (TileEntityTanningRack) getTile(world, pos);
		if (tile != null) {
			setTileResearches(player, tile);
			return tile.interact(player, false, player.isSneaking());
		}
		return true;
	}

	/**
	 * Used to determine if the blocks around this block have changed state
	 * MFR uses it to detect redstone changes, to trigger redstone automation on the tanning rack
	 * @param state BlockState of the block in question
	 * @param world The current world of the block
	 * @param pos The current position of the block
	 * @param block the type of block this is
	 * @param fromPos From which pos do you want to detect from
	 */
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		TileEntityTanningRack tile = (TileEntityTanningRack) getTile(world, pos);
		if (!world.isRemote) {
			if (world.getRedstonePowerFromNeighbors(pos) > 0) {
				tile.interact(null, false, true);
			}
		}
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
		TileEntityTanningRack tile = (TileEntityTanningRack) getTile(worldIn, pos);
		return TileEntityBase.calculateRedstoneFromInventory(tile.getRecipeInventory());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerClient() {
		ModelResourceLocation modelLocation = new ModelResourceLocation(getRegistryName(), "special");
		ModelRegistryHelper.registerItemRenderer(Item.getItemFromBlock(this), new TileEntityTanningRackRenderer<>());
		ModelRegistryHelper.register(modelLocation, new ModelDummyParticle(this.getTexture()));
		ModelLoader.setCustomStateMapper(this, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return modelLocation;
			}
		});
	}


}
