package minefantasy.mfr.container.slots;

import minefantasy.mfr.item.ItemBomb;
import minefantasy.mfr.item.ItemMine;
import minefantasy.mfr.tile.TileEntityBombBench;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotBombBench extends SlotItemHandler {
	private final String type;

	public SlotBombBench(String type, ItemStackHandler parent, int id, int x, int y) {
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
