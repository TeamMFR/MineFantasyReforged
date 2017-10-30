package minefantasy.mf2.container;

import minefantasy.mf2.block.tileentity.TileEntityCrucible;
import minefantasy.mf2.config.ConfigHardcore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class SlotCrucibleOut extends Slot {
    private TileEntityCrucible tile;
    private int slotNum;

    public SlotCrucibleOut(TileEntityCrucible parent, int id, int x, int y) {
        super(parent, id, x, y);
        this.tile = parent;
        this.slotNum = id;
    }

    @Override
    public boolean canTakeStack(EntityPlayer player) {
        return canTakeStack();
    }

    public boolean canTakeStack() {
        ItemStack slot = tile.getStackInSlot(slotNum);
        if (slot != null && slot.getItem() instanceof ItemBlock) {
            return true;
        }
        return !ConfigHardcore.HCCreduceIngots || tile.isAuto();
    }

    @Override
    public boolean isItemValid(ItemStack item) {
        return true;
    }
}
