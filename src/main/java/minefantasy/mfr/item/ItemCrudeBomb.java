package minefantasy.mfr.item;

import minefantasy.mfr.api.crafting.ISpecialSalvage;
import minefantasy.mfr.init.MineFantasyItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemCrudeBomb extends ItemBomb implements ISpecialSalvage {

	public ItemCrudeBomb(String name) {
		super(name);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack item) {
		return 20;
	}

	@Override
	public String getUnlocalizedName(ItemStack item) {
		return "item.bomb_crude";
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (!isInCreativeTab(tab)) {
			return;
		}
		items.add(new ItemStack(this));
	}

	@Override
	public EnumRarity getRarity(ItemStack item) {
		return MineFantasyItems.POOR;
	}

	@Override
	public Object[] getSalvage(ItemStack item) {
		return new Object[] {Items.PAPER, MineFantasyItems.THREAD, MineFantasyItems.BLACKPOWDER};
	}

	@Override
	public String getItemFuse(String value) {
		return "basic";
	}

	@Override
	public String getItemFilling(String value) {
		return "basic";
	}

	@Override
	public String getItemCasing(String value) {
		return "crude";
	}

	@Override
	public String getItemPowder(String value) {
		return "black_powder";
	}
}