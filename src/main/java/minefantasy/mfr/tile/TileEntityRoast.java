package minefantasy.mfr.tile;

import minefantasy.mfr.api.crafting.IHeatSource;
import minefantasy.mfr.api.crafting.IHeatUser;
import minefantasy.mfr.block.BlockRoast;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.container.ContainerBase;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.recipe.CraftingManagerRoast;
import minefantasy.mfr.recipe.RoastRecipeBase;
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
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class TileEntityRoast extends TileEntityBase implements IHeatUser, ITickable {
	public static final String LAST_RECIPE_NAME_TAG = "lastRecipe";
	/**
	 * Enable high temperatures ruin cooking
	 */
	public float progress;
	public float maxProgress;
	private int tempTicksExisted = 0;
	private final Random rand = new Random();
	private int ticksExisted;
	private RoastRecipeBase lastRecipe;

	public ItemStackHandler inventory = createInventory();
	private Set<String> knownResearches = new HashSet<>();

	public TileEntityRoast() {
	}

	@Override
	public boolean hasFastRenderer() {
		return true;
	}

	@Override
	protected ItemStackHandler createInventory() {
		return new ItemStackHandler(1) {
			//Used to limit the stack size to 1 for the slot, so automated interactions can't over fill it
			@Override
			protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
				return 1;
			}

			//Used to detect content changes in the inventory, and update the recipe, so it will begin cooking
			@Override
			protected void onContentsChanged(int slot) {
				updateRecipe();
			}
		};
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
			//progress cooking progress
			if ((getRecipe() != null || lastRecipe != null)) {
				progress += (temp / 100F);
			}
			//Handle normal cooking completion
			if (getRecipe() instanceof RoastRecipeBase && temp > 0 && maxProgress > 0 ) {
				RoastRecipeBase recipe = (RoastRecipeBase) getRecipe();
				if (progress >= maxProgress && temp > recipe.getMinTemperature()) {
					getInventory().setStackInSlot(0, recipe.getRoastRecipeOutput().copy());
					updateRecipe();
					progress = 0;
				}
			}
			//Handle Burnt cooking completion
			if (lastRecipe != null && temp > 0 && temp > lastRecipe.getMinTemperature()) {
				if (ConfigHardcore.enableOverheat && lastRecipe.canBurn()) {
					if (progress > lastRecipe.getBurnTime() || temp > lastRecipe.getMaxTemperature()) {
						getInventory().setStackInSlot(0, lastRecipe.getBurntOutput().copy());
						updateRecipe();
						lastRecipe = null;
						progress = 0;
					}
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
		//Put Item from Player to Tile
		if (item.isEmpty()) {
			if (!held.isEmpty() && !(held.getItem() instanceof ItemBlock)
					&& CraftingManagerRoast.findMatchingRecipe(held, isOven(), knownResearches) != null) {
				ItemStack item2 = held.copy();
				item2.setCount(1);
				getInventory().setStackInSlot(0, item2);
				tryDecrMainItem(player);
				updateRecipe();
				progress = 0;
				if (!isOven() && this.getTemp() > 0) {
					world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}
				return true;
			}
		} else {
			if (!world.isRemote) {
				RoastRecipeBase recipe = CraftingManagerRoast.findRecipeByOutput(item);
				if (recipe != null) {
					recipe.giveVanillaXp(player, 1, 1);
					recipe.giveSkillXp(player, 0);
				}
			}

			//Take Item from Tile to Player or drop on ground
			if (!player.inventory.addItemStackToInventory(item)) {
				player.entityDropItem(item, 0.0F);
			}
			getInventory().setStackInSlot(0, ItemStack.EMPTY);
			updateRecipe();
			progress = 0;
			return true;
		}
		return false;
	}

	public void setKnownResearches(Set<String> knownResearches) {
		this.knownResearches = knownResearches;
	}

	private void tryDecrMainItem(EntityPlayer player) {
		int held = player.inventory.currentItem;
		if (held >= 0 && held < 9) {
			player.inventory.decrStackSize(held, 1);
		}
	}

	public void updateRecipe() {
		RoastRecipeBase roastRecipe = CraftingManagerRoast.findMatchingRecipe(getInventory().getStackInSlot(0), isOven(), knownResearches);
		setRecipe(roastRecipe);

		if (roastRecipe != null) {
			maxProgress = roastRecipe.getCookTime();
			lastRecipe = roastRecipe;
		}
		if (getInventory().getStackInSlot(0).isEmpty()) {
			lastRecipe = null;
			progress = 0;
		}
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
		if (!nbt.getString(RECIPE_NAME_TAG).isEmpty()) {
			this.setRecipe(CraftingManagerRoast.getRecipeByName(nbt.getString(RECIPE_NAME_TAG), true));
		}
		if (!nbt.getString(LAST_RECIPE_NAME_TAG).isEmpty()) {
			this.lastRecipe = CraftingManagerRoast.getRecipeByName(LAST_RECIPE_NAME_TAG, true);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setFloat("Progress", progress);
		nbt.setFloat("maxProgress", maxProgress);
		nbt.setTag("inventory", inventory.serializeNBT());
		if (getRecipe() != null) {
			nbt.setString(RECIPE_NAME_TAG, getRecipe().getName());
		}
		if (lastRecipe != null) {
			nbt.setString(LAST_RECIPE_NAME_TAG, lastRecipe.getName());
		}
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
