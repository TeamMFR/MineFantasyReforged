package minefantasy.mfr.block;

import minefantasy.mfr.api.heating.ForgeFuel;
import minefantasy.mfr.api.heating.ForgeItemHandler;
import minefantasy.mfr.api.heating.Heatable;
import minefantasy.mfr.api.heating.TongsHelper;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.KnowledgeListMFR;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.item.ItemApron;
import minefantasy.mfr.item.ItemLighter;
import minefantasy.mfr.item.ItemTongs;
import minefantasy.mfr.mechanics.knowledge.ResearchLogic;
import minefantasy.mfr.tile.TileEntityForge;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockForge extends BlockTileEntity<TileEntityForge> {
	private static final PropertyBool BURNING = PropertyBool.create("burning");
	private static final PropertyBool UNDER = PropertyBool.create("under");
	private static final PropertyInteger FUEL_COUNT = PropertyInteger.create("fuel_count", 0, 3);

	public int tier;
	public String type;

	private static final AxisAlignedBB AABB = new AxisAlignedBB(0F, 0F, 0F, 1F, 0.5F, 1F);

	public BlockForge(String type, int tier) {
		super(tier == 1 ? Material.IRON : Material.ROCK);
		this.tier = tier;
		this.type = type;

		setRegistryName("forge_" + type);
		setUnlocalizedName("forge_" + type);
		this.setSoundType(SoundType.STONE);
		this.setHardness(5F);
		this.setResistance(8F);
		this.setCreativeTab(MineFantasyTabs.tabUtil);
		this.setLightOpacity(0);
	}

	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BURNING, FUEL_COUNT, UNDER);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityForge();
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntityForge tile = (TileEntityForge) getTile(world, pos);
		return state.withProperty(BURNING, tile.isLit()).withProperty(FUEL_COUNT, tile.getFuelCount()).withProperty(UNDER, tile.hasBlockAbove());
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	public static void setActiveState(boolean burning, int fuelCount, boolean under, World world, BlockPos pos) {
		world.setBlockState(pos, world.getBlockState(pos).withProperty(BURNING, burning).withProperty(FUEL_COUNT, fuelCount).withProperty(UNDER, under));
	}

	@Nonnull
	@Override
	public IBlockState getStateForPlacement(final World world, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer, final EnumHand hand) {
		return getDefaultState().withProperty(BURNING, false).withProperty(FUEL_COUNT, 0).withProperty(UNDER, false);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		TileEntityForge tile = (TileEntityForge) getTile(world, pos);
		if (tile != null) {
			setActiveState(tile.isLit(), tile.getFuelCount(), tile.hasBlockAbove(), world, pos);
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isBurning(IBlockAccess world, BlockPos pos) {
		TileEntityForge tile = (TileEntityForge) getTile(world, pos);
		return tile.isLit();
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.getValue(BURNING) ? 15 : 0;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack held = player.getHeldItemMainhand();
		TileEntityForge tile = (TileEntityForge) getTile(world, pos);
		if (tile != null) {
			if (tile.isLit() && !ItemApron.isUserProtected(player)) {
				player.setFire(5);
				player.attackEntityFrom(DamageSource.ON_FIRE, 1.0F);
			}
			if (!held.isEmpty()) {
				if (facing == EnumFacing.UP && held.getItem() instanceof ItemTongs && onUsedTongs(player, held, tile)) {
					return true;
				}
				int uses = ItemLighter.tryUse(held, player);
				if (!tile.isLit() && uses != 0) {
					player.playSound(SoundEvents.ITEM_FLINTANDSTEEL_USE, 1.0F, 1.0F);
					if (uses == 1) {
						tile.fireUpForge();
						held.damageItem(1, player);
					}
					return true;
				}
				if (Heatable.canHeatItem(held) && tile.tryAddHeatable(held)) {
					held.shrink(1);
					if (held.getCount() <= 0) {
						player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
					}
					return true;
				}

				ForgeFuel stats = ForgeItemHandler.getStats(held);
				if (stats != null && tile.addFuel(stats, true, tier)) {
					if (player.capabilities.isCreativeMode) {
						return true;
					}
					ItemStack heldItem = player.getHeldItemMainhand();
					if (heldItem.getItem().getContainerItem() != null) {
						ItemStack cont = new ItemStack(heldItem.getItem().getContainerItem());
						if (player.getHeldItem(hand).getCount() == 1) {
							player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, cont);
							return true;
						} else {
							if (!player.inventory.addItemStackToInventory(cont)) {
								player.entityDropItem(cont, 0.0F);
							}
						}
					}
					if (heldItem.getCount() == 1) {
						player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
					} else {
						heldItem.shrink(1);
					}
					return true;
				}
				if (!world.isRemote && ResearchLogic.hasInfoUnlocked(player, KnowledgeListMFR.smeltDragonforge) && held.getItem() == ComponentListMFR.DRAGON_HEART) {
					if (held.getCount() == 1) {
						player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
					} else {
						held.shrink(1);
					}
					tile.dragonHeartPower = 1.0F;
					return true;
				}
			}
			if (!world.isRemote && !tile.hasBlockAbove()) {
				TileEntityForge tileEntity = (TileEntityForge) getTile(world, pos);
				if (tileEntity != null) {
					tileEntity.openGUI(world, player);
				}
			}
		}
		return true;
	}

	private boolean onUsedTongs(EntityPlayer user, ItemStack held, TileEntityForge tile) {
		ItemStack contents = tile.getInventory().getStackInSlot(0);
		ItemStack grabbed = TongsHelper.getHeldItem(held);

		// GRAB
		if (grabbed.isEmpty()) {
			if (!contents.isEmpty() && contents.getItem() == ComponentListMFR.HOT_ITEM) {
				if (TongsHelper.trySetHeldItem(held, contents)) {
					tile.getInventory().setStackInSlot(0, ItemStack.EMPTY);
					return true;
				}
			}
		} else {
			if (contents.isEmpty()) {
				tile.getInventory().setStackInSlot(0, grabbed);
				TongsHelper.clearHeldItem(held, user);
				return true;
			}
		}
		return false;
	}

	public boolean getBurningValue(IBlockState state) {
		return state.getValue(BURNING);
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
		TileEntityForge tile = (TileEntityForge) getTile(world, pos);
		if (tier == 1 && !world.isRemote) {
			if (tile.isLit() && !world.isBlockPowered(pos)) {
				setActiveState(false, tile.getFuelCount(), tile.hasBlockAbove(), world, pos);
			} else if (!tile.isLit() && world.isBlockPowered(pos)) {
				setActiveState(true, tile.getFuelCount(), tile.hasBlockAbove(), world, pos);
			}
		}
		setActiveState(tile.isLit(), tile.getFuelCount(), tile.hasBlockAbove(), world, pos);
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		TileEntityForge tile = (TileEntityForge) getTile(world, pos);
		if (tier == 1 && !world.isRemote && tile.isLit() && !world.isBlockPowered(pos)) {
			setActiveState(tile.isLit(), tile.getFuelCount(), tile.hasBlockAbove(), world, pos);
		}
	}

}
