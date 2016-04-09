package minefantasy.mf2.mechanics.worldGen;

import minefantasy.mf2.item.list.ToolListMF;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

public class ChestGenMF
{
	public void init()
	{
		addLoot(ChestGenHooks.STRONGHOLD_CORRIDOR, ToolListMF.bandage_tough, 1, 5, 5);
	}
	private static void addLoot(String type, Item item, int min, int max, int chance)
	{
		addLoot(type, item, 1, min, max, chance);
	}
	private static void addLoot(String type, Item item, int stack, int min, int max, int chance)
	{
		ChestGenHooks.addItem(type, new WeightedRandomChestContent(item, stack, min, max, chance));
	}
	private static void addLoot(String type, ItemStack item, int min, int max, int chance)
	{
		ChestGenHooks.addItem(type, new WeightedRandomChestContent(item, min, max, chance));
	}
}
