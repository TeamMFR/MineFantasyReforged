package minefantasy.mfr.container.slots;

import minefantasy.mfr.tile.TileEntityBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class SlotRestrictive extends SlotItemHandler {
	private final TileEntityBase tile;
	private final int slot;

	public SlotRestrictive(TileEntityBase parent, int id, int x, int y) {
		super(parent.getInventory(), id, x, y);
		this.tile = parent;
		this.slot = id;
	}

	@Override
	public boolean isItemValid(ItemStack item) {
		return tile.isItemValidForSlot(slot, item);
	}
}
