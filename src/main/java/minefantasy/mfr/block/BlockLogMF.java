package minefantasy.mfr.block;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.proxy.IClientRegister;
import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLogMF extends BlockLog implements IClientRegister {
	public static final PropertyBool TREE_BASE = PropertyBool.create("tree_base");

	public BlockLogMF(String baseWood) {
		super();
		setDefaultState(blockState.getBaseState()
				.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y)
				.withProperty(TREE_BASE, false));

		String name = "log_" + baseWood.toLowerCase();
		setRegistryName(name);
		setTranslationKey(name);
		this.setHarvestLevel("axe", 0);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		MineFantasyReforged.PROXY.addClientRegister(this);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, LOG_AXIS, TREE_BASE);
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		super.getDrops(drops, world, pos, state, fortune);
		if (state.getValue(TREE_BASE)) {
			drops.add(new ItemStack(
					this == MineFantasyBlocks.LOG_EBONY ? MineFantasyBlocks.SAPLING_EBONY //if ebony, drop ebony sapling
					: this == MineFantasyBlocks.LOG_IRONBARK ? MineFantasyBlocks.SAPLING_IRONBARK //if ironbark, drop ironbark sapling
					: MineFantasyBlocks.SAPLING_YEW)); //if not anything else, drop yew sapling
		}
	}

	@Override
	public int damageDropped(IBlockState state) {
		return 0;
	}

	public IBlockState getStateFromMeta(int meta)
	{
		IBlockState iblockstate = this.getDefaultState().withProperty(TREE_BASE, meta == 1);

		switch (meta & 12)
		{
			case 0:
				iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
				break;
			case 4:
				iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
				break;
			case 8:
				iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
				break;
			default:
				iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
		}

		return iblockstate;
	}

	@SuppressWarnings("incomplete-switch")
	public int getMetaFromState(IBlockState state)
	{
		int i = state.getValue(TREE_BASE) ? 1 : 0;

		switch (state.getValue(LOG_AXIS))
		{
			case X:
				i |= 4;
				break;
			case Z:
				i |= 8;
				break;
			case NONE:
				i |= 12;
		}

		return i;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerClient() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "normal"));
	}
}
