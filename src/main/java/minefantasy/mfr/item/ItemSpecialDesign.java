package minefantasy.mfr.item;

import minefantasy.mfr.api.crafting.exotic.ISpecialCraftItem;
import net.minecraft.item.ItemStack;

public class ItemSpecialDesign extends ItemComponentMFR implements ISpecialCraftItem {
	private String design;

	public ItemSpecialDesign(String name, int rarity, String design) {
		super(name, rarity);
		this.design = design;
	}

	@Override
	public String getDesign(ItemStack item) {
		return this.design;
	}

}
