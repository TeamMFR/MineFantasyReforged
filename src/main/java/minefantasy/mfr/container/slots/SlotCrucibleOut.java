package minefantasy.mfr.container.slots;

import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.tile.TileEntityCrucible;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class SlotCrucibleOut extends SlotCraftingOutput {
	private final TileEntityCrucible tile;
	private final int slotNum;

	public SlotCrucibleOut(TileEntityCrucible tile, EntityPlayer player, int id, int x, int y) {
		super(tile, player, tile.inventory, id, x, y);
		this.tile = tile;
		this.slotNum = id;
	}

	@Override
	public boolean canTakeStack(EntityPlayer player) {
		ItemStack slot = tile.inventory.getStackInSlot(slotNum);
		if (!slot.isEmpty() && slot.getItem() instanceof ItemBlock) {
			return true;
		}
		return !ConfigHardcore.HCCreduceIngots || tile.isAuto();
	}
}
