package minefantasy.mfr.item;

import minefantasy.mfr.api.crafting.exotic.ISpecialCraftItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;

public class ItemSpecialDesign extends ItemBaseMFR implements ISpecialCraftItem {
	private String design;

	public ItemSpecialDesign(String name, IRarity rarity, String design) {
		super(name, rarity);
		this.design = design;
	}

	@Override
	public String getDesign(ItemStack item) {
		return this.design;
	}

}
