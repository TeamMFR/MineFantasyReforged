package minefantasy.mfr.tile;

import minefantasy.mfr.api.crafting.IBasicMetre;
import minefantasy.mfr.api.crafting.engineer.IBombComponent;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.constants.Tool;
import minefantasy.mfr.container.ContainerBase;
import minefantasy.mfr.container.ContainerBombBench;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasyKnowledgeList;
import minefantasy.mfr.init.MineFantasySounds;
import minefantasy.mfr.item.ItemBomb;
import minefantasy.mfr.item.ItemBombComponent;
import minefantasy.mfr.item.ItemExplodingArrow;
import minefantasy.mfr.mechanics.knowledge.ResearchLogic;
import minefantasy.mfr.network.NetworkHandler;
import minefantasy.mfr.util.ToolHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityBombBench extends TileEntityBase implements IBasicMetre {
	public float progress;
	public float maxProgress = 25F;
	public boolean hasRecipe;

	public final ItemStackHandler inventory = createInventory();

	@Override
	protected ItemStackHandler createInventory() {
		return new ItemStackHandler(6);
	}

	@Override
	public ItemStackHandler getInventory() {
		return this.inventory;
	}

	@Override
	public ContainerBase createContainer(EntityPlayer player) {
		return new ContainerBombBench(player, this);
	}

	@Override
	protected int getGuiId() {
		return NetworkHandler.GUI_BOMB_BENCH;
	}

	public static boolean isMatch(ItemStack item, String type) {
		String component = getComponentType(item);
		return component != null && component.equalsIgnoreCase(type);
	}

	public static String getComponentType(ItemStack item) {
		if (!item.isEmpty() && item.getItem() instanceof IBombComponent) {
			return ((IBombComponent) item.getItem()).getComponentType();
		}
		return null;
	}

	public static String getComponentName(ItemStack item) {
		if (!item.isEmpty() && item.getItem() instanceof ItemBombComponent) {
			return ((ItemBombComponent) item.getItem()).getComponentName();
		}
		return null;
	}

	public static byte getComponentTier(ItemStack item) {
		if (!item.isEmpty() && item.getItem() instanceof IBombComponent) {
			return ((IBombComponent) item.getItem()).getTier();
		}
		return (byte) 0;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer user) {
		return user.getDistance(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) < 8D;
	}

	public boolean tryCraft(EntityPlayer player, boolean pressUsed) {
		boolean sticky = !pressUsed && ResearchLogic.getResearchCheck(player, MineFantasyKnowledgeList.stickybomb) && !player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() == Items.SLIME_BALL;
		if (world.isRemote && sticky && applySlime()) {
			int slot = player.inventory.getSlotFor(new ItemStack(Items.SLIME_BALL));
			player.inventory.removeStackFromSlot(slot);
			return true;
		}
		ItemStack result = findResult();
		hasRecipe = !result.isEmpty();

		if (result.isEmpty()) {
			progress = 0F;
		} else if (canCraft() && (pressUsed || ToolHelper.getToolTypeFromStack(player.getHeldItemMainhand()) == Tool.SPANNER)) {
			if (!pressUsed && !player.getHeldItemMainhand().isEmpty()) {
				world.playSound(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, MineFantasySounds.TWIST_BOLT, SoundCategory.NEUTRAL, 0.25F, 1.0F, true);
				player.getHeldItemMainhand().damageItem(1, player);
				if (player.getHeldItemMainhand().getItemDamage() >= player.getHeldItemMainhand().getMaxDamage()) {
					player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
				}
			}
			float efficiency = pressUsed ? maxProgress : ToolHelper.getCrafterEfficiency(player.getHeldItemMainhand());

			if (!pressUsed && player.swingProgress > 0 && player.swingProgress <= 1.0) {
				efficiency *= (0.5F - player.swingProgress);
			}

			progress += efficiency;

			if ((progress >= maxProgress && craftItem(result))) {
				world.playSound(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, SoundEvents.BLOCK_WOODEN_DOOR_OPEN, SoundCategory.AMBIENT, 0.35F, 0.5F, false);
				progress = 0;
				if (player != null) {
					Skill.ENGINEERING.addXP(player, 2);
				}
				boolean isArrow = isMatch(0, "arrow") || isMatch(0, "bolt");
				for (int a = 0; a < 4; a++) {
					if (!(isArrow && a == 3)) {
						ItemStack itemStack = getInventory().getStackInSlot(a);
						if (!itemStack.isEmpty() && !itemStack.getItem().getContainerItem(itemStack).isEmpty()) {
							// START CONTAINER CODE
							ItemStack cont = itemStack.getItem().getContainerItem(itemStack);
							ItemStack spare = getInventory().getStackInSlot(5);
							if (spare.isEmpty()) {
								getInventory().setStackInSlot(5, cont);
								cont = ItemStack.EMPTY;
							} else if (spare.isItemEqual(cont)) {
								if (spare.getCount() + cont.getCount() <= spare.getMaxStackSize()) {
									spare.grow(cont.getCount());
									cont = ItemStack.EMPTY;
								} else {
									int room_left = spare.getMaxStackSize() - spare.getCount();
									spare.grow(room_left);
									cont.shrink(room_left);
								}
							}
							if (!cont.isEmpty() && !world.isRemote) {
								EntityItem ei = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, cont);
								world.spawnEntity(ei);
							}
						}
						// END CONTAINER CODE
						getInventory().extractItem(a, 1, false);
					}
				}
			}
			return true;
		}
		return false;
	}

	private boolean applySlime() {
		ItemStack res = getInventory().getStackInSlot(4);

		if (!res.isEmpty() && res.getItem() instanceof ItemBomb) {
			if (res.hasTagCompound() && !res.getTagCompound().hasKey("stickyBomb")) {
				res.getTagCompound().setBoolean("stickyBomb", true);
				return true;
			}
		}
		return false;
	}

	private boolean craftItem(ItemStack result) {
		if (getInventory().getStackInSlot(4).isEmpty()) {
			getInventory().setStackInSlot(4, result);
			getInventory().setStackInSlot(4, result);
			return true;
		} else {
			if (areItemsEqual(result, getInventory().getStackInSlot(4)) && (getInventory().getStackInSlot(4).getCount() + result.getCount()) <= getInventory().getStackInSlot(4).getMaxStackSize()) {
				getInventory().getStackInSlot(4).grow(1);
				return true;
			} else {
				return false;
			}
		}
	}


	public boolean canCraft() {
		if (maxProgress > 0 && !findResult().isEmpty()) {
			return this.canFitResult(findResult());
		}
		return false;
	}

	private boolean canFitResult(ItemStack result) {
		ItemStack resSlot = getInventory().getStackInSlot(getInventory().getSlots() - 1);
		if (!resSlot.isEmpty() && !result.isEmpty()) {
			int maxStack = getStackSize(resSlot);
			return resSlot.getCount() + result.getCount() <= maxStack;
		}
		return true;
	}

	private int getStackSize(ItemStack slot) {
		if (slot.isEmpty()){
			return 0;
		}
		return slot.getMaxStackSize();
	}

	private boolean areItemsEqual(ItemStack bomb1, ItemStack bomb2) {
		if (!ItemBomb.getCasing(bomb1).equals(ItemBomb.getCasing(bomb2))) {
			return false;
		}
		if (!ItemBomb.getPowder(bomb1).equals(ItemBomb.getPowder(bomb2))) {
			return false;
		}
		if (!ItemBomb.getFilling(bomb1).equals(ItemBomb.getFilling(bomb2))) {
			return false;
		}
		if (!ItemBomb.getFuse(bomb1).equals(ItemBomb.getFuse(bomb2))) {
			return false;
		}
		return bomb1.isItemEqual(bomb2);
	}

	public void onInventoryChanged() {
	}

	private ItemStack findResult() {
		boolean isArrow = isMatch(0, "arrow") || isMatch(0, "bolt");
		if (!isMatch(1, "powder") || (!isArrow && !isMatch(3, "fuse"))) {
			return ItemStack.EMPTY;
		}
		String inputCasing = "basic";
		String inputFilling = "basic";
		String inputFuse = "basic";
		String inputPowder;
		Item design = null;
		String casing = getComponentType(getInventory().getStackInSlot(0));
		String powder = getComponentType(getInventory().getStackInSlot(1));
		String filling = getComponentType(getInventory().getStackInSlot(2));
		String fuse = getComponentType(getInventory().getStackInSlot(3));
		if (casing != null) {
			inputCasing = getComponentName(getInventory().getStackInSlot(0));
			design = getDesignCrafted(casing);
		}
		if (powder != null) {
			if (powder.equalsIgnoreCase("powder")) {
				inputPowder = getComponentName(getInventory().getStackInSlot(1));
			} else {
				return ItemStack.EMPTY;
			}
		} else
			return ItemStack.EMPTY;
		if (filling != null) {
			if (filling.equalsIgnoreCase("filling")) {
				inputFilling = getComponentName(getInventory().getStackInSlot(2));
			} else {
				return ItemStack.EMPTY;
			}
		}
		if (isArrow || fuse != null) {
			if (!isArrow) {
				if (fuse.equalsIgnoreCase("fuse")) {
					inputFuse = getComponentName(getInventory().getStackInSlot(3));
				} else {
					return ItemStack.EMPTY;
				}
			}
		} else
			return ItemStack.EMPTY;

		if (design != null && isArrow && inputPowder != null) {
			return ItemExplodingArrow.createBombArrow(design, inputPowder, inputFilling);
		} else if (design != null && inputFuse != null && inputPowder != null) {
			return ItemBomb.createExplosive(design, inputCasing, inputFilling, inputFuse, inputPowder, 1, false);
		}
		return ItemStack.EMPTY;
	}

	private Item getDesignCrafted(String type) {
		if (type.equalsIgnoreCase("bombcase")) {
			return MineFantasyItems.BOMB_CUSTOM;
		}
		if (type.equalsIgnoreCase("minecase")) {
			return MineFantasyItems.MINE_CUSTOM;
		}
		if (type.equalsIgnoreCase("arrow")) {
			return MineFantasyItems.EXPLODING_ARROW;
		}
		if (type.equalsIgnoreCase("bolt")) {
			return MineFantasyItems.EXPLODING_BOLT;
		}
		return null;
	}

	private boolean isMatch(int slot, String type) {
		return isMatch(getInventory().getStackInSlot(slot), type);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		if (isMatch(item, "bolt") || isMatch(item, "arrow") || isMatch(item, "bombcase") || isMatch(item, "minecase")) {
			return slot == 0;
		}
		if (isMatch(item, "powder")) {
			return slot == 1;
		}
		if (isMatch(item, "filling")) {
			return slot == 2;
		}
		if (isMatch(item, "fuse")) {
			return slot == 3;
		}
		return false;
	}

	@Override
	public int getMetreScale(int size) {
		return (int) Math.min(size, size / maxProgress * progress);
	}

	@Override
	public boolean shouldShowMetre() {
		return true;
	}

	@Override
	public String getLocalisedName() {
		return I18n.format("tile.bomb_bench.name");
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		inventory.deserializeNBT(nbt.getCompoundTag("inventory"));

		progress = nbt.getFloat("progress");
		maxProgress = nbt.getFloat("maxProgress");
		hasRecipe = nbt.getBoolean("hasRecipe");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setTag("inventory", inventory.serializeNBT());

		nbt.setFloat("progress", progress);
		nbt.setFloat("maxProgress", maxProgress);
		nbt.setBoolean("hasRecipe", hasRecipe);
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
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
		}
		return super.getCapability(capability, facing);
	}
}
