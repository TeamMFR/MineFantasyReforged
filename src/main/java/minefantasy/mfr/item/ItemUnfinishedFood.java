package minefantasy.mfr.item;

import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasyTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

/**
 * @author Anonymous Productions
 */
public class ItemUnfinishedFood extends ItemBaseMFR {
	private int itemRarity;

	public ItemUnfinishedFood(String name) {
		this(name, 0);
	}

	public ItemUnfinishedFood(String name, int rarity) {
		super(name);
		setMaxStackSize(1);
		itemRarity = rarity;

		this.setCreativeTab(MineFantasyTabs.tabFood);
	}

	@Override
	public EnumRarity getRarity(ItemStack item) {
		int lvl = itemRarity + 1;

		if (item.isItemEnchanted()) {
			if (lvl == 0) {
				lvl++;
			}
			lvl++;
		}
		if (lvl >= MineFantasyItems.RARITY.length) {
			lvl = MineFantasyItems.RARITY.length - 1;
		}
		return MineFantasyItems.RARITY[lvl];
	}

}
