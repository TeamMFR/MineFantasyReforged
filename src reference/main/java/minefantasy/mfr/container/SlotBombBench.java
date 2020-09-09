package minefantasy.mfr.container;

import minefantasy.mfr.tile.TileEntityBombBench;
import minefantasy.mfr.item.gadget.ItemBomb;
import minefantasy.mfr.item.gadget.ItemMine;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotBombBench extends Slot {
    private final String type;

    public SlotBombBench(String type, IInventory parent, int id, int x, int y) {
        super(parent, id, x, y);
        this.type = type;
    }

    @Override
    public boolean isItemValid(ItemStack item) {
        if (type.equalsIgnoreCase("case")) {
            return TileEntityBombBench.isMatch(item, "arrow") || TileEntityBombBench.isMatch(item, "bolt")
                    || TileEntityBombBench.isMatch(item, "bombCase") || TileEntityBombBench.isMatch(item, "mineCase");
        }
        if (type.equalsIgnoreCase("bomb")) {
            return item.getItem() instanceof ItemBomb || item.getItem() instanceof ItemMine;
        }
        return TileEntityBombBench.isMatch(item, type);
    }
}
