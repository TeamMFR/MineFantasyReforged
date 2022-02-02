package minefantasy.mfr.block;

import codechicken.lib.model.ModelRegistryHelper;
import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.client.model.block.ModelDummyParticle;
import minefantasy.mfr.client.render.block.TileEntityBombPressRenderer;
import minefantasy.mfr.init.MineFantasyKnowledgeList;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.mechanics.knowledge.ResearchLogic;
import minefantasy.mfr.proxy.IClientRegister;
import minefantasy.mfr.tile.TileEntityBombPress;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class BlockBombPress extends BlockTileEntity<TileEntityBombPress> implements IClientRegister {
	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public BlockBombPress() {
		super(Material.IRON);

		setRegistryName("bomb_press");
		setUnlocalizedName("bomb_press");
		this.setSoundType(SoundType.METAL);
		this.setHardness(5F);
		this.setResistance(2F);
		this.setLightOpacity(0);
		this.setCreativeTab(MineFantasyTabs.tabUtil);
		MineFantasyReforged.PROXY.addClientRegister(this);
	}

	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityBombPress();
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

	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase user, ItemStack stack) {
		world.setBlockState(pos, state, 2);
	}

	/**
	 * Called upon block activation (right click on the block.)
	 */
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!ResearchLogic.getResearchCheck(player, MineFantasyKnowledgeList.bombs)) {
			if (world.isRemote)
				player.sendMessage(new TextComponentTranslation("knowledge.unknownUse"));
			return false;
		}
		TileEntityBombPress tile = (TileEntityBombPress) getTile(world, pos);
		if (tile != null) {
			tile.use(player);
		}
		return true;
	}

	@Override
	public String getTexture() {
		return "cauldron_side";
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
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerClient() {
		ModelResourceLocation modelLocation = new ModelResourceLocation(getRegistryName(), "special");
		ModelRegistryHelper.registerItemRenderer(Item.getItemFromBlock(this), new TileEntityBombPressRenderer<>());
		ModelRegistryHelper.register(modelLocation, new ModelDummyParticle(this.getTexture()));
		ModelLoader.setCustomStateMapper(this, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return modelLocation;
			}
		});
	}
}