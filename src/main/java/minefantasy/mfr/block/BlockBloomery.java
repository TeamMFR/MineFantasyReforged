package minefantasy.mfr.block;

import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.item.ItemLighter;
import minefantasy.mfr.tile.TileEntityBloomery;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class BlockBloomery extends BlockTileEntity<TileEntityBloomery> {
	public static final PropertyBool BLOOM = PropertyBool.create("bloom");

	public BlockBloomery() {
		super(Material.ROCK);

		setRegistryName("bloomery");
		setUnlocalizedName("bloomery");
		this.setSoundType(SoundType.STONE);
		this.setHardness(8F);
		this.setResistance(10F);
		this.setCreativeTab(MineFantasyTabs.tabUtil);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
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

	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BLOOM);
	}

	@Override
	public TileEntity createTileEntity(final World world, final IBlockState state) {
		return new TileEntityBloomery();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(BLOOM, (meta == 1));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(BLOOM) ? 1 : 0;
	}

	@Nonnull
	@Override
	public IBlockState getStateForPlacement(final World world, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer, final EnumHand hand) {
		return getDefaultState().withProperty(BLOOM, false);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntityBloomery tile = (TileEntityBloomery) getTile(world, pos);
		if (tile != null) {
			ItemStack held = player.getHeldItem(hand);

			// Hammer
			if (!player.isSwingInProgress && tile.tryHammer(player)) {
				return true;
			}
			// Light
			int l = ItemLighter.tryUse(held, player);
			if (!tile.isActive && !tile.hasBloom()) {
				if (!held.isEmpty() && l != 0) {
					player.playSound(SoundEvents.ITEM_FLINTANDSTEEL_USE, 1.0F, 1.0F);
					if (world.isRemote)
						return true;
					if (l == 1 && tile.light(player)) {
						held.damageItem(1, player);
					}
					return true;
				}
			}
			// GUI
			if (!world.isRemote && !tile.isActive && !tile.hasBloom()) {
				final TileEntityBloomery tileEntity = (TileEntityBloomery) getTile(world, pos);
				if (tileEntity != null) {
					tileEntity.openGUI(world, player);
				}
			}
		}
		return true;
	}

	@Override
	public void onBlockClicked(World world, BlockPos pos, EntityPlayer user) {
		TileEntityBloomery tile = (TileEntityBloomery) getTile(world, pos);
		if (tile != null) {
			if (user.swingProgress <= 0.1F) {
				tile.tryHammer(user);
			}
		}
	}

}
