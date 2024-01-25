package minefantasy.mfr.api.crafting.exotic;

import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class SpecialForging {
	public static HashMap<Item, Item> dragonforgeCrafts = new HashMap<>();
	public static HashMap<String, Item> specialCrafts = new HashMap<>();

	public static void addDragonforgeCraft(Item base, Item dragon) {
		dragonforgeCrafts.put(base, dragon);
	}

	public static Item getDragonCraft(ItemStack stack) {
		if (dragonforgeCrafts.containsKey(stack.getItem())) {
			return dragonforgeCrafts.get(stack.getItem());
		}
		return null;
	}

	public static void addSpecialCraft(String special, Item base, Item output) {
		specialCrafts.put(getIdentifier(base, special), output);
	}

	public static Item getSpecialCraft(String special, ItemStack input) {
		if (!input.isEmpty()) {
			String id = getIdentifier(input.getItem(), special);
			if (specialCrafts.containsKey(id)) {
				return specialCrafts.get(id);
			}
		}
		return null;
	}

	private static String getIdentifier(Item item, String special) {
		return "[" + special + "]" + CustomToolHelper.getSimpleReferenceName(item);
	}

	public static String getItemDesign(ItemStack item) {
		if (!item.isEmpty() && item.getItem() instanceof ISpecialCraftItem) {
			return ((ISpecialCraftItem) item.getItem()).getDesign(item);
		}
		return null;
	}
}
