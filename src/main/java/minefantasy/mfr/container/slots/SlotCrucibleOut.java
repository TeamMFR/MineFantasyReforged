package minefantasy.mfr.container.slots;

import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.tile.TileEntityCrucible;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class SlotCrucibleOut extends SlotItemHandler {
	private TileEntityCrucible tile;
	private int slotNum;

	public SlotCrucibleOut(TileEntityCrucible parent, int id, int x, int y) {
		super(parent.inventory, id, x, y);
		this.tile = parent;
		this.slotNum = id;
	}

	@Override
	public boolean canTakeStack(EntityPlayer player) {
		return canTakeStack();
	}

	public boolean canTakeStack() {
		ItemStack slot = tile.inventory.getStackInSlot(slotNum);
		if (!slot.isEmpty() && slot.getItem() instanceof ItemBlock) {
			return true;
		}
		return !ConfigHardcore.HCCreduceIngots || tile.isAuto();
	}

	@Override
	public boolean isItemValid(ItemStack item) {
		return false;
	}
}
