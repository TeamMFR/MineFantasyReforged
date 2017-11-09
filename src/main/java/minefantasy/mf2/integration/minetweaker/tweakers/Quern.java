package minefantasy.mf2.integration.minetweaker.tweakers;

import minefantasy.mf2.api.crafting.refine.QuernRecipes;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.mc1710.item.MCItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;

@ZenClass("mods.minefantasy.Quern")
public class Quern {

    @ZenMethod
    public static void addRecipe(IItemStack result, IIngredient input) {
        addRecipe(result, input, 0, true);
    }

    @ZenMethod
    public static void addRecipe(IItemStack result, IIngredient input, int tier) {
        addRecipe(result, input, tier, true);
    }

    @ZenMethod
    public static void addRecipe(IItemStack result, IIngredient input, int tier, boolean consumePot) {
        MineTweakerAPI.apply(new AddRecipeAction(result, input, tier, consumePot));
    }

    @ZenMethod
    public static void remove(IIngredient result){
        remove(result, null);
    }

    @ZenMethod
    public static void remove(IIngredient result, IIngredient input) {
        if (result == null)
            throw new IllegalArgumentException("Output value cannot be null");

        ArrayList<QuernRecipes> recipesToRemove = new ArrayList<QuernRecipes>();
        for (QuernRecipes recipes : QuernRecipes.recipeList) {
            if (result.matches(new MCItemStack(recipes.result)) && (input == null || input.matches(new MCItemStack(recipes.input)))) {
                recipesToRemove.add(recipes);
            }
        }

        MineTweakerAPI.apply(new RemoveAction(recipesToRemove));
    }

    private static class AddRecipeAction implements IUndoableAction {
        private final IItemStack result;
        private final IIngredient input;
        private final int tier;
        private final boolean consumePot;
        private final ArrayList<QuernRecipes> recipesToRemove = new ArrayList<QuernRecipes>();

        public AddRecipeAction(IItemStack result, IIngredient input, int tier, boolean consumePot) {
            this.result = result;
            this.input = input;
            this.tier = tier;
            this.consumePot = consumePot;
        }

        @Override
        public void apply() {
            for (IItemStack stack : input.getItems()) {
                ItemStack s = MineTweakerMC.getItemStack(stack);
                recipesToRemove.add(QuernRecipes.addRecipe(s, MineTweakerMC.getItemStack(result), this.tier, consumePot));
            }
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            for (QuernRecipes recipes : recipesToRemove) {
                QuernRecipes.recipeList.remove(recipes);
            }
            recipesToRemove.clear();
        }

        @Override
        public String describe() {
            return "Adding quern recipe for " + result.getDisplayName();
        }

        @Override
        public String describeUndo() {
            return "Removing quern recipe for " + result.getDisplayName();
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

    private static class RemoveAction implements IUndoableAction {
        private final ArrayList<QuernRecipes> recipesToRemove;

        public RemoveAction(ArrayList<QuernRecipes> recipesToRemove) {
            this.recipesToRemove = recipesToRemove;
        }

        @Override
        public void apply() {
            for (QuernRecipes recipe : recipesToRemove) {
                QuernRecipes.recipeList.remove(recipe);
            }
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            for (QuernRecipes recipe : recipesToRemove) {
                QuernRecipes.recipeList.add(recipe);
            }
        }

        @Override
        public String describe() {
            return "Removing " + recipesToRemove.size() + " bloomery recipes";
        }

        @Override
        public String describeUndo() {
            return "Restoring " + recipesToRemove.size() + " bloomery recipes";
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }
}
