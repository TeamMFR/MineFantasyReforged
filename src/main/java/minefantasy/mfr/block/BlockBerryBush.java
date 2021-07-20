package minefantasy.mfr.block;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.proxy.IClientRegister;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Random;

public class BlockBerryBush extends BlockBush implements IGrowable, IShearable, IClientRegister {

	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 4);

	private Random rand = new Random();

	private static final AxisAlignedBB AABB = new AxisAlignedBB(1 / 16F, 0F, 1 / 16F, 15 / 16F, 14 / 16F, 15 / 16F);

	public BlockBerryBush(String name) {
		super(Material.LEAVES);

		setRegistryName(name);
		setUnlocalizedName(name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
		this.setTickRandomly(true);
		this.setCreativeTab(CreativeTabs.DECORATIONS);
		this.setHardness(0.3F);
		this.setLightOpacity(1);
		this.setSoundType(SoundType.PLANT);
		this.setTickRandomly(true);
		MineFantasyReforged.PROXY.addClientRegister(this);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, AGE);
	}

	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(AGE, Integer.valueOf(meta));
	}

	public int getMetaFromState(IBlockState state) {
		return ((Integer) state.getValue(AGE)).intValue();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
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

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
		return true;
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
		arrayList.add(new ItemStack(world.getBlockState(pos).getBlock(), 1));
		return arrayList;
	}

	protected PropertyInteger getAgeProperty() {
		return AGE;
	}

	public int getMaxAge() {
		return 4;
	}

	protected int getAge(IBlockState state) {
		return ((Integer) state.getValue(this.getAgeProperty())).intValue();
	}

	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		int i = getAge(state);

		if (i < getMaxAge() && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt(8) == 0)) {
			IBlockState newState = state.withProperty(AGE, i + 1);
			worldIn.setBlockState(pos, newState, 2);
			net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, newState);
		}

		super.updateTick(worldIn, pos, state, rand);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		}

		if (getAge(state) == getMaxAge()) {

			ItemStack itemstack = rand.nextInt(10) == 0 ? new ItemStack(MineFantasyItems.BERRIES_JUICY) : new ItemStack(MineFantasyItems.BERRIES, 1);

			float f = this.rand.nextFloat() * 0.8F + 0.1F;
			float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
			float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

			EntityItem entityitem = new EntityItem(world, pos.getX() + f, pos.getY() + f1, pos.getZ() + f2,
					itemstack);

			float f3 = 0.03F;
			entityitem.motionX = (float) this.rand.nextGaussian() * f3;
			entityitem.motionY = (float) this.rand.nextGaussian() * f3 + 0.2F;
			entityitem.motionZ = (float) this.rand.nextGaussian() * f3;
			world.spawnEntity(entityitem);

			state = state.withProperty(AGE, 0);
			world.setBlockState(pos, state, 2);
			world.playSound(null, pos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 0.3F, f);
			return true;
		}
		return true;
	}

	@Override
	public ArrayList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		return new ArrayList<ItemStack>() {};
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return true;
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return true;
	}

	protected int getBonemealAgeIncrease(World worldIn) {
		return MathHelper.getInt(worldIn.rand, 2, 3);
	}

	public IBlockState withAge(int age) {
		return this.getDefaultState().withProperty(this.getAgeProperty(), Integer.valueOf(age));
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		int i = this.getAge(state) + this.getBonemealAgeIncrease(worldIn);
		int j = this.getMaxAge();

		if (i > j) {
			i = j;
		}

		worldIn.setBlockState(pos, this.withAge(i), 2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerClient() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "normal"));
	}
}