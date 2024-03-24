package minefantasy.mfr.container.slots;

import minefantasy.mfr.tile.TileEntityBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class SlotCraftingOutRestrictive extends SlotCraftingOutput {
	private final TileEntityBase tile;
	private final int slot;

	public SlotCraftingOutRestrictive(TileEntityBase parent, EntityPlayer player, int id, int x, int y) {
		super(parent, player, parent.getInventory(), id, x, y);
		this.tile = parent;
		this.slot = id;
	}

	@Override
	public boolean isItemValid(ItemStack item) {
		return tile.isItemValidForSlot(slot, item);
	}
}
