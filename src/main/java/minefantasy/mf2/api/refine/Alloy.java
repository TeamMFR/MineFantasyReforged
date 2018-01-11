package minefantasy.mf2.api.refine;

import minefantasy.mf2.api.crafting.MineFantasyFuels;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.*;

public class Alloy {
    public final List recipeItems;
    public final ItemStack recipeOutput;
    public final int level;
    private Map props = new HashMap();

    public Alloy(ItemStack output, int requiredLevel, List items) {
        recipeItems = items;
        recipeOutput = output;
        level = requiredLevel;
    }

    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }

    public Alloy addProperty(String id, Object prop) {
        props.put(id, prop);
        return this;
    }

    public Object getProperty(String id) {
        return props.get(id);
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(ItemStack[] inventory) {
        ArrayList checkRecipe = new ArrayList(this.recipeItems);

        for (ItemStack itemstack : inventory) {
            if (itemstack != null) {
                boolean matches = false;
                Iterator iterator = checkRecipe.iterator();

                while (iterator.hasNext()) {
                    ItemStack checkItem = (ItemStack) iterator.next();

                    if (itemstack.isItemEqual(checkItem) && areMaterialsEqual(itemstack, checkItem)
                            && (checkItem.getItemDamage() == OreDictionary.WILDCARD_VALUE
                            || itemstack.getItemDamage() == checkItem.getItemDamage())) {
                        matches = true;
                        checkRecipe.remove(checkItem);
                        break;
                    }
                    if (areBothCarbon(itemstack, checkItem)) {
                        matches = true;
                        checkRecipe.remove(checkItem);
                        break;
                    }
                }

                if (!matches) {
                    return false;
                }
            }
        }

        return checkRecipe.isEmpty();
    }

    private boolean areMaterialsEqual(ItemStack itemstack, ItemStack checkItem) {
        CustomMaterial material1 = CustomToolHelper.getCustomPrimaryMaterial(itemstack);
        CustomMaterial material2 = CustomToolHelper.getCustomPrimaryMaterial(checkItem);
        if (material1 == null && material2 == null) {
            return true;
        }
        if (material1 != null && material2 != null) {
            return material1 == material2;
        }
        return false;
    }

    public boolean areBothCarbon(ItemStack item1, ItemStack item2) {
        return MineFantasyFuels.isCarbon(item1) && MineFantasyFuels.isCarbon(item2);
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return this.recipeOutput.copy();
    }

    /**
     * Returns the size of the recipe area
     */
    public int getRecipeSize() {
        return this.recipeItems.size();
    }

    /**
     * Gets the level of the Alloy, requiring a more powerful smelter
     *
     * @return the minimal level required to make (crucible is 0, alloy forge = 1,
     * etc)
     */
    public int getLevel() {
        return level;
    }

}
