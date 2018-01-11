package minefantasy.mf2.api.crafting;

import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.util.MFLogUtil;
import minefantasy.mf2.util.XSTRandom;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Salvage {
    public static HashMap<String, Object[]> salvageList = new HashMap<String, Object[]>();
    public static HashMap<String, Item> sharedSalvage = new HashMap<String, Item>();
    private static XSTRandom random = new XSTRandom();

    public static void shareSalvage(Item item1, Item item2) {
        sharedSalvage.put(CustomToolHelper.getSimpleReferenceName(item1), item2);
    }

    public static void addSalvage(Block input, Object... components) {
        addSalvage(Item.getItemFromBlock(input), components);
    }

    public static void addSalvage(Item input, Object... components) {
        salvageList.put(CustomToolHelper.getSimpleReferenceName(input), components);
    }

    public static void addSalvage(ItemStack item, Object... components) {
        if (item.isItemStackDamageable()) {
            item.setItemDamage(OreDictionary.WILDCARD_VALUE);
        }
        salvageList.put(CustomToolHelper.getReferenceName(item), components);
    }

    /**
     * Break an item to its parts
     *
     * @return a list of items
     */
    public static List<ItemStack> salvage(EntityPlayer user, ItemStack item) {
        return salvage(user, item, 1.0F);
    }

    public static List<ItemStack> salvage(EntityPlayer user, ItemStack item, float dropRate) {
        Object[] entryList = getSalvage(item);
        if (entryList == null) {
            return null;
        }

        float durability = 1F;
        if (item.isItemDamaged()) {
            durability = (float) (item.getMaxDamage() - item.getItemDamage()) / (float) item.getMaxDamage();
        }

        float chanceModifier = 1.25F;// 80% Succcess rate
        float chance = dropRate * durability;// Modifier for skill and durability

        return dropItems(item, user, entryList, chanceModifier, chance);
    }

    private static List<ItemStack> dropItems(ItemStack mainItem, EntityPlayer user, Object[] entryList,
                                             float chanceModifier, float chance) {
        List<ItemStack> items = new ArrayList<ItemStack>();
        for (Object entry : entryList) {
            if (entry != null) {
                if (entry instanceof Item && random.nextFloat() * chanceModifier < chance) {
                    items = dropItemStack(mainItem, user, items, new ItemStack((Item) entry), chanceModifier, chance);
                }
                if (entry instanceof Block && random.nextFloat() * chanceModifier < chance) {
                    items = dropItemStack(mainItem, user, items, new ItemStack((Block) entry), chanceModifier, chance);
                }
                if (entry instanceof ItemStack) {
                    items = dropItemStack(mainItem, user, items, (ItemStack) entry, chanceModifier, chance);
                }
            }
        }
        return items;
    }

    private static List<ItemStack> dropItemStack(ItemStack mainItem, EntityPlayer user, List<ItemStack> items,
                                                 ItemStack entry, float chanceModifier, float chance) {
        for (int a = 0; a < entry.stackSize; a++) {
            if (random.nextFloat() * chanceModifier < chance) {
                boolean canSalvage = true;

                if (entry.getItem() instanceof ISalvageDrop) {
                    canSalvage = ((ISalvageDrop) entry.getItem()).canSalvage(user, entry);
                }
                if (canSalvage) {
                    ItemStack newitem = entry.copy();
                    newitem.stackSize = 1;
                    newitem = CustomToolHelper.tryDeconstruct(newitem, mainItem);
                    items.add(newitem);
                }
            }
        }
        return items;
    }

    public static Object[] getSalvage(ItemStack item) {
        // SHARED
        Item shared = sharedSalvage.get(CustomToolHelper.getReferenceName(item, "any", false));
        if (shared != null) {
            MFLogUtil.logDebug("Found shared: " + shared.getUnlocalizedName());
            return getSalvage(new ItemStack(shared));
        }

        if (item != null && item.getItem() instanceof ISpecialSalvage) {
            Object[] special = ((ISpecialSalvage) item.getItem()).getSalvage(item);
            if (special != null) {
                return special;
            }
        }
        Object[] specific = salvageList.get(CustomToolHelper.getReferenceName(item));
        if (specific != null) {
            return specific;
        }
        Object[] specific2 = salvageList.get(CustomToolHelper.getReferenceName(item, "any"));
        if (specific2 != null) {
            return specific2;
        }

        return salvageList.get(CustomToolHelper.getReferenceName(item, "any", false));
    }

    private static boolean doesMatch(ItemStack item1, ItemStack item2) {
        if (!CustomToolHelper.doesMatchForRecipe(item1, item2)) {
            return false;
        }
        return item2.getItem() == item1.getItem() && (item2.getItemDamage() == OreDictionary.WILDCARD_VALUE
                || item2.getItemDamage() == item1.getItemDamage());
    }
}