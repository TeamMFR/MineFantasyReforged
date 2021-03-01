package minefantasy.mfr.item;

import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.util.ToolHelper;
import net.minecraft.item.ItemStack;

/**
 * @author Anonymous Productions
 */
public class ItemEAnvilTools extends ItemBaseMFR {
	public ItemEAnvilTools(String name, int uses) {
		super(name);
		setCreativeTab(MineFantasyTabs.tabCraftTool);

		this.setMaxDamage(uses);
		setMaxStackSize(1);
	}

	@Override
	public ItemStack getContainerItem(ItemStack item) {
		item.setItemDamage(item.getItemDamage() + 1);
		return item.getItemDamage() >= item.getMaxDamage() ? ItemStack.EMPTY : item;
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return ToolHelper.setDuraOnQuality(stack, super.getMaxDamage());
	}
}
