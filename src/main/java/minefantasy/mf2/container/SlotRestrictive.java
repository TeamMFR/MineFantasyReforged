package minefantasy.mf2.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotRestrictive extends Slot {
    private IInventory tile;
    private int slot;

    public SlotRestrictive(IInventory parent, int id, int x, int y) {
        super(parent, id, x, y);
        this.tile = parent;
        this.slot = id;
    }

    @Override
    public boolean isItemValid(ItemStack item) {
        return tile.isItemValidForSlot(slot, item);
    }
}
