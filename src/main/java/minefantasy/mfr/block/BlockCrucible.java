package minefantasy.mfr.block;

import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.item.ItemFilledMould;
import minefantasy.mfr.tile.TileEntityCrucible;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCrucible extends BlockTileEntity<TileEntityCrucible> {
	private static final PropertyBool ACTIVE = PropertyBool.create("active");
	public int tier;
	public String type;
	public boolean isAuto;

	public BlockCrucible(String type, int tier) {
		super(Material.ROCK);
		this.tier = tier;
		this.type = type;
		setRegistryName("crucible_" + type);
		setUnlocalizedName("crucible_" + type);
		this.setSoundType(SoundType.STONE);
		this.setHardness(8F);
		this.setResistance(8F);
		this.setCreativeTab(MineFantasyTabs.tabUtil);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, ACTIVE);
	}

	@Override
	public TileEntity createTileEntity( World world, IBlockState state) {
		return new TileEntityCrucible();
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntityCrucible tile = (TileEntityCrucible) getTile(world, pos);
		return state.withProperty(ACTIVE, tile.getIsHot());
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return getDefaultState().withProperty(ACTIVE, false);
	}

	@Override
	public boolean onBlockActivated(final World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntityCrucible tile = (TileEntityCrucible) getTile(world, pos);
		if (tile != null) {
			ItemStack held = player.getHeldItemMainhand();
			if (!held.isEmpty() && held.getItem() == MineFantasyItems.TRILOGY_JEWEL) {
				if (tier == 2 && tile.isCoated()) {
					held.shrink(1);
					if (held.getCount() <= 0) {
						player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
					}
					if (!world.isRemote) {
						world.spawnEntity(new EntityLightningBolt(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, true));
						world.setBlockState(pos, (MineFantasyBlocks.CRUCIBLE_MASTER).getDefaultState(), 2);
					}
				}
				return true;
			}
			ItemStack out = tile.getInventory().getStackInSlot(tile.getInventory().getSlots() - 1);
			if (!held.isEmpty() && held.getItem() == MineFantasyItems.INGOT_MOULD
					&& !out.isEmpty()
					&& !(out.getItem() instanceof ItemBlock)) {
				ItemStack result = out.copy();
				result.setCount(1);
				tile.getInventory().extractItem(tile.getInventory().getSlots() - 1, 1, false);

				ItemStack mould = ItemFilledMould.createMould(result);
				if (held.getCount() == 1) {
					player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, mould);
				} else {
					held.shrink(1);
					if (!world.isRemote) {
						EntityItem drop = new EntityItem(world, player.posX, player.posY, player.posZ, mould);
						drop.setPickupDelay(0);
						world.spawnEntity(drop);
					}
				}
				return true;
			}

		}

		if (!world.isRemote) {
			final TileEntityCrucible tileEntity = (TileEntityCrucible) getTile(world, pos);
			if (tileEntity != null) {
				tileEntity.openGUI(world, player);
			}
		}

		return true;
	}

	public BlockCrucible setAuto() {
		isAuto = true;
		return this;
	}
}