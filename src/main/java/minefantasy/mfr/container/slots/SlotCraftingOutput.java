package minefantasy.mfr.container.slots;

import minefantasy.mfr.recipe.IRecipeMFR;
import minefantasy.mfr.tile.TileEntityBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotCraftingOutput extends SlotItemHandler {
	/**
	 * The Tile Entity using this slot
	 */
	private final TileEntityBase tile;

	/**
	 * The player that is using the GUI where this slot resides.
	 */
	private final EntityPlayer player;
	private int removeCount;

	public SlotCraftingOutput(TileEntityBase tile, EntityPlayer player, IItemHandler inventory,
			int slotIndex, int xPosition, int yPosition) {
		super(inventory, slotIndex, xPosition, yPosition);
		this.tile = tile;
		this.player = player;
	}

	/**
	 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
	 * @param stack the itemStack being checked
	 * @return if the stack is valid or not
	 */
	@Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}

	/**
	 * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg.
	 * <p>
	 * Overwritten for MFR Crafting to populate removeCount
	 * @param amount the amount being decreased
	 * @return the stack being decreased
	 */
	@Override
	public ItemStack decrStackSize(int amount) {
		if (this.getHasStack()) {
			this.removeCount += Math.min(amount, this.getStack().getCount());
		}

		return super.decrStackSize(amount);
	}

	/**
	 * Triggers when ItemStacks is taken from this slot
	 * @param player The Player taking the stack
	 * @param stack The Stack being taken
	 * @return The Stack being taken
	 */
	@Override
	public ItemStack onTake(EntityPlayer player, ItemStack stack) {
		this.onCrafting(stack);
		super.onTake(player, stack);
		return stack;
	}

	/**
	 * Triggers when the crafting occurs, meaning when this slot updates
	 * @param stack The Stack in this slot
	 * @param amount The amount of the change
	 */
	@Override
	protected void onCrafting(ItemStack stack, int amount) {
		this.removeCount += amount;
		this.onCrafting(stack);
	}

	/**
	 * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
	 */
	@Override
	protected void onCrafting(ItemStack stack) {
		if (removeCount == 0) {
			removeCount = stack.getCount();
		}
		stack.onCrafting(this.player.world, this.player, this.removeCount);
		IRecipeMFR recipe = tile.getRecipeByOutput(stack);

		if (recipe != null) {
			if (recipe.shouldSlotGiveSkillXp() && !player.world.isRemote) {
				recipe.giveVanillaXp(player, 0, removeCount);
				recipe.giveSkillXpPerCount(player, 0, removeCount);
			}
		}

		this.removeCount = 0;
	}
}
