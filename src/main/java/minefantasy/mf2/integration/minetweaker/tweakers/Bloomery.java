package minefantasy.mf2.integration.minetweaker.tweakers;

import minefantasy.mf2.api.crafting.refine.BloomRecipe;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.mc1710.item.MCItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.NotNull;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ZenClass("mods.minefantasy.Bloomery")
public class Bloomery {

    @ZenMethod
    public static void addRecipe(@NotNull IItemStack result, @NotNull IIngredient input) {
        MineTweakerAPI.apply(new AddRecipeAction(result, input));
    }

    @ZenMethod
    public static void remove(@NotNull IIngredient output, @Optional IIngredient input) {
        HashMap<ItemStack, ItemStack> recipeList = BloomRecipe.recipeList;

        List<ItemStack> toRemove = new ArrayList<ItemStack>();
        List<ItemStack> toRemoveValues = new ArrayList<ItemStack>();
        for (Map.Entry<ItemStack, ItemStack> entry : recipeList.entrySet()) {
            if (output.matches(new MCItemStack(entry.getValue()))
                    && (input == null || input.matches(new MCItemStack(entry.getKey())))) {
                toRemove.add(entry.getKey());
                toRemoveValues.add(entry.getValue());
            }
        }

        if (!toRemove.isEmpty()) {
            MineTweakerAPI.apply(new RemoveAction(toRemove, toRemoveValues));
        } else {
            MineTweakerAPI.logWarning("No bloomery recipes for " + output.toString());
        }
    }

    private static class AddRecipeAction implements IUndoableAction {
        IItemStack result;
        IIngredient input;

        public AddRecipeAction(IItemStack result, IIngredient input) {
            this.result = result;
            this.input = input;
        }

        @Override
        public void apply() {
            for (IItemStack stack : input.getItems()) {
                ItemStack s = MineTweakerMC.getItemStack(stack);
                BloomRecipe.addRecipe(s, MineTweakerMC.getItemStack(result));
            }
        }

        @Override
        public String describe() {
            return "Adding bloomery recipe for " + result.getDisplayName();
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public String describeUndo() {
            return "Removing bloomery recipe for " + result.getDisplayName();
        }

        @Override
        public void undo() {
            BloomRecipe.recipeList.remove(input);
        }

    }

    private static class RemoveAction implements IUndoableAction {
        private final List<ItemStack> items;
        private final List<ItemStack> values;

        public RemoveAction(List<ItemStack> items, List<ItemStack> values) {
            this.items = items;
            this.values = values;
        }

        @Override
        public void apply() {
            for (ItemStack item : items) {
                BloomRecipe.recipeList.remove(item);
            }
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            for (int i = 0; i < items.size(); i++) {
                BloomRecipe.recipeList.put(items.get(i), values.get(i));
            }
        }

        @Override
        public String describe() {
            return "Removing " + items.size() + " bloomery recipes";
        }

        @Override
        public String describeUndo() {
            return "Restoring " + items.size() + " bloomery recipes";
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

}
