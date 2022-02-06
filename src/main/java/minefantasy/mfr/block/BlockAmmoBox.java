package minefantasy.mfr.block;

import codechicken.lib.model.ModelRegistryHelper;
import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.client.model.block.ModelDummyParticle;
import minefantasy.mfr.client.render.block.TileEntityAmmoBoxRenderer;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.proxy.IClientRegister;
import minefantasy.mfr.tile.TileEntityAmmoBox;
import minefantasy.mfr.tile.TileEntityWoodDecor;
import minefantasy.mfr.util.MFRLogUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

public class BlockAmmoBox extends BlockWoodDecor implements IClientRegister {
	public static final String NBT_Ammo = "Ammo", NBT_Stock = "Stock";
	/**
	 * Food-Ammo-All
	 */
	public final byte storageType;
	public String name;

	public BlockAmmoBox(String name, byte storageType) {
		this(name, name, storageType);
	}

	/**
	 * @param storageType (Food, Ammo, All)
	 */
	public BlockAmmoBox(String name, String texName, byte storageType) {
		super(texName);
		this.storageType = storageType;
		this.name = name;

		setRegistryName(name);
		setUnlocalizedName(name);
		this.setHardness(0.5F);
		this.setResistance(2F);
		this.setCreativeTab(MineFantasyTabs.tabUtil);
		MineFantasyReforged.PROXY.addClientRegister(this);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityAmmoBox();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		float width = (storageType == 0 ? 8F : storageType == 1 ? 14F : 16F) / 16F;
		float height = (storageType == 0 ? 4F : storageType == 1 ? 8F : 9F) / 16F;
		float border = (1F - width) / 2;
		return new AxisAlignedBB(border, 0F, border, 1 - border, height, 1 - border);
	}

	public static ItemStack getHeld(ItemStack item, boolean removeTag) {
		if (item.hasTagCompound() && item.getTagCompound().hasKey(NBT_Ammo)) {
			ItemStack i = new ItemStack(item.getTagCompound().getCompoundTag(NBT_Ammo));
			if (removeTag) {
				item.getTagCompound().removeTag(NBT_Ammo);
			}
			return i;
		}
		return ItemStack.EMPTY;
	}

	public static int getStock(ItemStack item, boolean removeTag) {
		if (item.hasTagCompound() && item.getTagCompound().hasKey(NBT_Stock)) {
			int i = item.getTagCompound().getInteger(NBT_Stock);
			if (removeTag) {
				item.getTagCompound().removeTag(NBT_Stock);
			}
			return i;
		}
		return 0;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase user, ItemStack stack) {

		TileEntityAmmoBox tile = (TileEntityAmmoBox) getTile(world, pos);
		if (tile != null) {
			if (stack.hasTagCompound() && stack.getTagCompound().hasKey(NBT_Ammo) && stack.getTagCompound().hasKey(NBT_Stock)) {
				tile.inventoryStack = new ItemStack(stack.getTagCompound().getCompoundTag(NBT_Ammo));
				tile.stock = stack.getTagCompound().getInteger(NBT_Stock);

			}
		}
		super.onBlockPlacedBy(world, pos, state, user, stack);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer user, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack held = user.getHeldItemMainhand();
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileEntityAmmoBox) {
			return ((TileEntityAmmoBox) tile).interact(user, held);
		}
		return false;
	}

	@Override
	public ArrayList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		return ret;
	}

	@Override
	protected ItemStack modifyDrop(TileEntityWoodDecor tile, ItemStack item) {
		return modifyAmmo((TileEntityAmmoBox) tile, super.modifyDrop(tile, item));
	}

	private ItemStack modifyAmmo(TileEntityAmmoBox tile, ItemStack item) {
		if (tile != null && !item.isEmpty()) {
			if (!tile.inventoryStack.isEmpty()) {
				NBTTagCompound nbt = new NBTTagCompound();
				if (!item.hasTagCompound()) {
					item.setTagCompound(new NBTTagCompound());
				}
				tile.inventoryStack.writeToNBT(nbt);

				MFRLogUtil.logDebug("Added Drop: " + tile.inventoryStack.getDisplayName() + " x" + tile.stock);
				item.getTagCompound().setTag(NBT_Ammo, nbt);
				item.getTagCompound().setInteger(NBT_Stock, tile.stock);
			}
		}
		return item;
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
		ModelRegistryHelper.registerItemRenderer(Item.getItemFromBlock(this), new TileEntityAmmoBoxRenderer<>());
		ModelRegistryHelper.register(modelLocation, new ModelDummyParticle(this.getTexture()));
		ModelLoader.setCustomStateMapper(this, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return modelLocation;
			}
		});
	}
}
