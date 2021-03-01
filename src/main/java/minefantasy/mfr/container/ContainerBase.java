package minefantasy.mfr.container;

import minefantasy.mfr.tile.TileEntityBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerBase extends Container {
	private TileEntityBase tile;

	public ContainerBase(InventoryPlayer playerInventory, TileEntityBase tile) {

		this.tile = tile;

	}

	public ContainerBase() {}

	protected void addTileSlots(int width, int height, int xPos, int yPos) {
		IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int slot = y * width + x;
				this.addSlotToContainer(new SlotItemHandler(inventory, slot, xPos + x * 18, yPos + y * 18));
			}
		}
	}

	protected void addPlayerSlots(InventoryPlayer playerInventory, int xPosition, int yPosition) {
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, xPosition + j * 18, yPosition - 58 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(playerInventory, i, xPosition + i * 18, yPosition));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
}
