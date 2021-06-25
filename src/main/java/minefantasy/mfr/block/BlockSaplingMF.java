package minefantasy.mfr.block;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.proxy.IClientRegister;
import minefantasy.mfr.world.gen.feature.WorldGenMFTree;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockSaplingMF extends BlockBush implements IGrowable, IClientRegister {

	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);

	private final Block LOG;
	private final Block LEAVES;
	private float growthModifier;
	private String name;

	public BlockSaplingMF(String baseWood, Block log, Block leaves, float growthModifier) {
		super(Material.PLANTS);

		float f = 0.4F;
		new AxisAlignedBB(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
		this.setCreativeTab(CreativeTabs.DECORATIONS);
		this.growthModifier = growthModifier;
		name = baseWood.toLowerCase() + "_sapling";
		this.LOG = log;
		this.LEAVES = leaves;
		setRegistryName(name);
		setUnlocalizedName(name);
		setSoundType(SoundType.GROUND);
		MineFantasyReforged.PROXY.addClientRegister(this);
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		if (!world.isRemote) {
			super.updateTick(world, pos, state, rand);

			if (world.getLight(pos.add(0, 1, 0)) >= 9 && rand.nextFloat() * 100F < (14.30F) / growthModifier) {
				this.initGrow(world, pos, rand);
			}
		}
	}

	public void initGrow(World world, BlockPos pos, Random rand) {
		IBlockState state = world.getBlockState(pos);

		if (state == this.getDefaultState()) {
			world.setBlockState(pos, state, 4);
		} else {
			this.tryGrow(world, pos, rand);
		}
	}

	public void tryGrow(World world, BlockPos pos, Random rand) {
		if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(world, rand, pos))
			return;
		IBlockState state = world.getBlockState(pos);
		Object treegen = new WorldGenMFTree(true, LOG, LEAVES);

		boolean flag = false;

		IBlockState newBlock = (Blocks.AIR).getDefaultState();
		world.setBlockState(pos, newBlock, 1);

		if (!((WorldGenerator) treegen).generate(world, rand, pos)) {
			if (flag) {
				world.setBlockState(pos, state, 4);
				world.setBlockState(pos, state, 4);
				world.setBlockState(pos.add(0, 0, 1), state, 4);
				world.setBlockState(pos.add(1, 0, 1), state, 4);
			} else {
				world.setBlockState(pos, state, 4);
			}
		}
	}

	/**
	 * Determines the damage on the item the block drops. Used in cloth and wood.
	 */
	public int damageDropped(IBlockState state) {
		return MathHelper.clamp(state.hashCode() & 7, 0, 5);
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return false;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return false;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {

	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(STAGE, Integer.valueOf((meta & 8) >> 3));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		i = i | state.getValue(STAGE).intValue() << 3;
		return i;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {STAGE});
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerClient() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "normal"));
	}
}