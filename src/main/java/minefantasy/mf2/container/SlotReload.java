package minefantasy.mf2.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotReload extends Slot {
    private ContainerReload container;
    private int slot;

    public SlotReload(ContainerReload container, IInventory parent, int id, int x, int y) {
        super(parent, id, x, y);
        this.container = container;
        this.slot = id;
    }

    @Override
    public boolean isItemValid(ItemStack item) {
        return container.canAccept(item);
    }
}
