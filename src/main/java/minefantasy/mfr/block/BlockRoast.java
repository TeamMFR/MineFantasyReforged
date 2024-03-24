package minefantasy.mfr.block;

import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.mechanics.knowledge.ResearchLogic;
import minefantasy.mfr.recipe.CraftingManagerRoast;
import minefantasy.mfr.tile.TileEntityRoast;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class BlockRoast extends BlockTileEntity<TileEntityRoast> {
	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	private boolean isOven;

	public BlockRoast(boolean isOven) {
		super(Material.ROCK);
		this.isOven = isOven;
		String name = isOven ? "oven" : "stove";

		setRegistryName(name);
		setTranslationKey(name);
		this.setHardness(1.5F);
		this.setResistance(1F);
		this.setLightOpacity(0);
		this.setCreativeTab(MineFantasyTabs.tabUtil);
	}

	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityRoast();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntityRoast tile = (TileEntityRoast) getTile(world, pos);
		if (tile != null) {
			Set<String> playerResearches = new HashSet<>();
			for (String roastResearch : CraftingManagerRoast.getRoastResearches()) {
				if (ResearchLogic.getResearchCheck(player, ResearchLogic.getResearch(roastResearch))) {
					playerResearches.add(roastResearch);
				}
			}
			tile.setKnownResearches(playerResearches);

			return tile.interact(player);
		}
		return true;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	public boolean isOven() {
		return isOven;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		if (isOven()) {
			return new AxisAlignedBB(3F / 16F, 0F, 3F / 16F, 13F / 16F, 1F / 1.6F, 13F / 16F);
		} else {
			return new AxisAlignedBB(3 / 16F, 0, 3 / 16F, 13F / 16F, 2 / 16F, 13F / 16F);
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.byIndex(meta);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
}
