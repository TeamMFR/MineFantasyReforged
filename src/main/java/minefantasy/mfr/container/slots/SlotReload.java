package minefantasy.mfr.container.slots;

import minefantasy.mfr.config.ConfigWeapon;
import minefantasy.mfr.container.ContainerReload;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotReload extends SlotItemHandler {
	private ContainerReload container;

	public SlotReload(ContainerReload container, ItemStackHandler parent, int id, int x, int y) {
		super(parent, id, x, y);
		this.container = container;
	}

	@Override
	public boolean isItemValid(ItemStack item) {
		return container.canAccept(item);
	}

	@Override
	public int getSlotStackLimit() {
		return ConfigWeapon.vanillaArrowStackLimit;
	}
}
