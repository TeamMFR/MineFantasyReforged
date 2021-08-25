package minefantasy.mfr.tile;

import minefantasy.mfr.api.crafting.IHeatSource;
import minefantasy.mfr.api.crafting.IHeatUser;
import minefantasy.mfr.block.BlockRoast;
import minefantasy.mfr.container.ContainerBase;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.recipe.CookRecipe;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class TileEntityRoast extends TileEntityBase implements IHeatUser, ITickable {
	/**
	 * Enable high temperatures ruin cooking
	 */
	public static boolean enableOverheat = true;
	public float progress;
	public float maxProgress;
	private int tempTicksExisted = 0;
	private Random rand = new Random();
	private int ticksExisted;
	private CookRecipe recipe;

	public ItemStackHandler inventory = createInventory();

	public TileEntityRoast() {
	}

	@Override
	public boolean hasFastRenderer() {
		return true;
	}

	@Override
	protected ItemStackHandler createInventory() {
		return new ItemStackHandler(1);
	}

	@Override
	public ItemStackHandler getInventory() {
		return this.inventory;
	}

	@Override
	public void update() {
		++tempTicksExisted;
		if (tempTicksExisted == 10) {
			updateRecipe();
		}
		int temp = getTemp();
		++ticksExisted;
		if (ticksExisted % 20 == 0 && !world.isRemote) {
			sendUpdates();
			if (recipe != null && temp > 0 && maxProgress > 0 && temp > recipe.minTemperature) {
				if (enableOverheat && recipe.canBurn && temp > recipe.maxTemperature) {
					getInventory().setStackInSlot(0, recipe.burnt.copy());
					updateRecipe();
				}
				progress += (temp / 100F);
				if (progress >= maxProgress) {
					getInventory().setStackInSlot(0, recipe.output.copy());
					updateRecipe();
				}
			}
		}
		if (temp > 0 && world.isRemote && rand.nextInt(20) == 0) {
			world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.2F + (rand.nextFloat() * 0.6F), pos.getY() + 0.2F,
					pos.getZ() + 0.2F + (rand.nextFloat() * 0.6F), 0F, 0F, 0F);
		}
	}

	private int getTemp() {
		TileEntity tile = world.getTileEntity(pos.add(0, -1, 0));
		if (tile instanceof IHeatSource) {
			return ((IHeatSource) tile).getHeat();
		}
		return 0;
	}

	public boolean isOven() {
		if (world == null)
			return false;
		Block base = world.getBlockState(pos).getBlock();
		if (base instanceof BlockRoast) {
			return ((BlockRoast) base).isOven();
		}
		return false;
	}

	public boolean interact(EntityPlayer player) {
		ItemStack held = player.getHeldItemMainhand();
		ItemStack item = getInventory().getStackInSlot(0);
		if (item.isEmpty()) {
			if (!held.isEmpty() && !(held.getItem() instanceof ItemBlock) && CookRecipe.getResult(held, isOven()) != null) {
				ItemStack item2 = held.copy();
				item2.setCount(1);
				getInventory().setStackInSlot(0, item2);
				tryDecrMainItem(player);
				updateRecipe();
				if (!isOven() && this.getTemp() > 0) {
					world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}
				return true;
			}
		} else {
			if (!player.inventory.addItemStackToInventory(item)) {
				player.entityDropItem(item, 0.0F);
			}
			getInventory().setStackInSlot(0, ItemStack.EMPTY);
			updateRecipe();
			return true;
		}
		return false;
	}

	private void tryDecrMainItem(EntityPlayer player) {
		int held = player.inventory.currentItem;
		if (held >= 0 && held < 9) {
			player.inventory.decrStackSize(held, 1);
		}
	}

	public void updateRecipe() {
		recipe = CookRecipe.getResult(getInventory().getStackInSlot(0), isOven());
		if (recipe != null) {
			maxProgress = recipe.time;
		}
		progress = 0;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		return false;
	}

	@Override
	public boolean canAccept(TileEntity tile) {
		return true;
	}

	@Override
	public Block getBlockType() {
		if (world == null)
			return MineFantasyBlocks.OVEN;

		return super.getBlockType();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		progress = nbt.getFloat("Progress");
		maxProgress = nbt.getFloat("maxProgress");
		inventory.deserializeNBT(nbt.getCompoundTag("inventory"));

	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setFloat("Progress", progress);
		nbt.setFloat("maxProgress", maxProgress);
		nbt.setTag("inventory", inventory.serializeNBT());
		return nbt;
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(getInventory());
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public ContainerBase createContainer(EntityPlayer player) {
		return null;
	}

	@Override
	protected int getGuiId() {
		return 0;
	}
}
