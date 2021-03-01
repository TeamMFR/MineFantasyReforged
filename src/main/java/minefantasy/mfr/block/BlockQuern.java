package minefantasy.mfr.block;

import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.tile.TileEntityQuern;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class BlockQuern extends BlockTileEntity<TileEntityQuern> {

	public BlockQuern(String type) {
		super(Material.ROCK);

		setRegistryName(type);
		setUnlocalizedName(type);
		this.setSoundType(SoundType.STONE);
		this.setHardness(5F);
		this.setResistance(5F);
		this.setCreativeTab(MineFantasyTabs.tabUtil);
	}

	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this);
	}

	@Override
	public TileEntity createTileEntity(final World world, final IBlockState state) {
		return new TileEntityQuern();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Nonnull
	@Override
	public IBlockState getStateForPlacement(final World world, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer, final EnumHand hand) {
		return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntityQuern tile = (TileEntityQuern) getTile(world, pos);
		if (tile != null) {
			if (facing == EnumFacing.UP) {
				tile.onUse();
			}
			// GUI
			else if (!world.isRemote) {

				if (tile != null) {
					tile.openGUI(world, player);
				}
			}
		}
		return true;
	}

	@Override
	public String getTexture() {
		return "cauldron_side";
	}
}
