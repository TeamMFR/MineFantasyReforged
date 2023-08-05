package minefantasy.mfr.item;

import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.util.ToolHelper;
import net.minecraft.item.ItemStack;

import java.util.Random;

/**
 * @author Anonymous Productions
 */
public class ItemEAnvilTools extends ItemBaseMFR {
	public ItemEAnvilTools(String name, int uses) {
		super(name);
		setCreativeTab(MineFantasyTabs.tabCraftTool);
		this.setContainerItem(this);
		this.setMaxDamage(uses);
		setMaxStackSize(1);
	}

	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		ItemStack damagedStack = stack.copy();
		damagedStack.attemptDamageItem(1, new Random(), null);
		return damagedStack.getItemDamage() >= damagedStack.getMaxDamage() ? ItemStack.EMPTY : damagedStack;
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return ToolHelper.setDuraOnQuality(stack, super.getMaxDamage());
	}
}
