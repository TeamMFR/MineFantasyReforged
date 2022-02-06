package minefantasy.mfr.block;

import minefantasy.mfr.init.MineFantasyKnowledgeList;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.mechanics.knowledge.ResearchLogic;
import minefantasy.mfr.tile.blastfurnace.TileEntityBlastChamber;
import net.minecraft.block.Block;
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
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class BlockBlastChamber extends BlockTileEntity<TileEntityBlastChamber> {

	public BlockBlastChamber() {
		super(Material.ANVIL);

		setRegistryName("blast_chamber");
		setUnlocalizedName("blastfurnchamber");
		this.setSoundType(SoundType.METAL);
		this.setHardness(8F);
		this.setResistance(10F);
		this.setCreativeTab(MineFantasyTabs.tabUtil);
	}

	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityBlastChamber();
	}

	@Nonnull
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Nonnull
	@Override
	public IBlockState getStateForPlacement(final World world, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer, final EnumHand hand) {
		return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		TileEntityBlastChamber tile = (TileEntityBlastChamber) getTile(world, pos);
		if (tile != null)
			tile.updateBuild();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!ResearchLogic.getResearchCheck(player, MineFantasyKnowledgeList.blast_furnace)) {
			if (!world.isRemote)
				player.sendMessage(new TextComponentTranslation("knowledge.unknownUse"));
			return false;
		}
		if (!world.isRemote) {
			final TileEntityBlastChamber tileEntity = (TileEntityBlastChamber) getTile(world, pos);
			if (tileEntity != null) {
				tileEntity.openGUI(world, player);
			}
		}

		return true;
	}
}