package minefantasy.mfr.block;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.constants.Tool;
import minefantasy.mfr.entity.EntityCogwork;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.proxy.IClientRegister;
import minefantasy.mfr.util.PowerArmour;
import minefantasy.mfr.util.ToolHelper;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCogwork extends BlockDirectional implements IClientRegister {
	private final boolean isMain;

	public BlockCogwork(String name, boolean helmet) {
		super(Material.CIRCUITS);
		this.isMain = helmet;

		setRegistryName(name);
		setUnlocalizedName(name);
		this.setTickRandomly(true);
		this.setCreativeTab(MineFantasyTabs.tabGadget);
		this.setHardness(1F);
		this.setResistance(5F);
		this.setLightOpacity(0);
		MineFantasyReforged.PROXY.addClientRegister(this);
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);

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
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		world.setBlockState(pos, state.withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer)), 2);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!world.isRemote && ToolHelper.getToolTypeFromStack(player.getHeldItem(hand)) == Tool.SPANNER) {
			tryBuild(player, world, pos);
			return true;
		}
		return false;
	}

	public void tryBuild(EntityPlayer builder, World world, BlockPos pos) {
		if (isMain && PowerArmour.isStationBlock(world, pos.add(0, 2, 0))
				&& world.getBlockState(pos.add(0, -1, 0)).getBlock() == MineFantasyBlocks.BLOCK_COGWORK_LEGS
				&& world.getBlockState(pos.add(0, 1, 0)).getBlock() == MineFantasyBlocks.BLOCK_COGWORK_HELM) {
			if (!world.isRemote) {
				world.setBlockState(pos, Blocks.AIR.getDefaultState());
				world.setBlockState(pos.add(0, -1, 0), Blocks.AIR.getDefaultState());
				world.setBlockState(pos.add(0, 1, 0), Blocks.AIR.getDefaultState());

				EntityCogwork suit = new EntityCogwork(world);
				int angle = getAngleFor(builder);
				suit.setLocationAndAngles(pos.getX() + 0.5D, pos.getY() - 0.95D, pos.getZ() + 0.5D, angle, 0.0F);
				suit.rotationYaw = suit.rotationYawHead = suit.prevRotationYaw = suit.prevRotationYawHead = angle;
				world.spawnEntity(suit);
			}

		}
	}

	private int getAngleFor(EntityPlayer user) {
		int l = MathHelper.floor(user.rotationYaw * 4.0F / 360.0F + 2.5D) & 3;
		return l * 90;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerClient() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "normal"));
	}
}