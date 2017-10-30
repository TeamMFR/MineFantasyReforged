package minefantasy.mf2.api.crafting.exotic;

import minefantasy.mf2.api.crafting.Salvage;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class SpecialForging {
    public static HashMap<Item, Item> dragonforgeCrafts = new HashMap<Item, Item>();
    public static HashMap<String, Item> specialCrafts = new HashMap<String, Item>();

    public static void addDragonforgeCraft(Block blackSteel, Block dragon) {
        addDragonforgeCraft(Item.getItemFromBlock(blackSteel), Item.getItemFromBlock(dragon));
    }

    public static void addDragonforgeCraft(Item base, Item dragon) {
        dragonforgeCrafts.put(base, dragon);
        Salvage.shareSalvage(dragon, base);
    }

    public static Item getDragonCraft(ItemStack blacksteel) {
        if (dragonforgeCrafts.containsKey(blacksteel.getItem())) {
            return dragonforgeCrafts.get(blacksteel.getItem());
        }
        return null;
    }

    public static void addSpecialCraft(String special, Item base, Item output) {
        specialCrafts.put(getIdentifier(base, special), output);
        Salvage.shareSalvage(output, base);
    }

    public static Item getSpecialCraft(String special, ItemStack input) {
        if (input != null) {
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
        if (item != null && item.getItem() instanceof ISpecialCraftItem) {
            return ((ISpecialCraftItem) item.getItem()).getDesign(item);
        }
        return null;
    }
}
