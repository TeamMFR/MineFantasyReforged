package minefantasy.mfr.item;

import minefantasy.mfr.init.MineFantasyTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemRawOre extends ItemComponentMFR {
	public ItemRawOre(String name, int rarity) {
		super(name, rarity);
		this.setCreativeTab(MineFantasyTabs.tabMaterials);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		// from net.minecraft.item.Item.getSubItems to avoid the code in minefantasy.mfr.item.ItemComponentMFR.getSubItems
		if (this.isInCreativeTab(tab))
		{
			items.add(new ItemStack(this));
		}
	}
}
