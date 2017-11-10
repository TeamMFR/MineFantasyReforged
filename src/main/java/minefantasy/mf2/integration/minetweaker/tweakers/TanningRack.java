package minefantasy.mf2.integration.minetweaker.tweakers;

import minefantasy.mf2.api.crafting.tanning.TanningRecipe;
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
import java.util.List;

@ZenClass("mods.minefantasy.TanningRack")
public class TanningRack {

    @ZenMethod
    public static void addTieredAndToolRecipe(IItemStack output, IIngredient input, float time, @Optional int tier, @Optional String tool) {
        MineTweakerAPI.apply(new TanningAction(output, input, time, tier, tool));
    }

    @ZenMethod
    public static void remove(@NotNull IIngredient output, @Optional IIngredient input) {
        ArrayList<TanningRecipe> recipeList = TanningRecipe.recipeList;
        List<TanningRecipe> recipesToRemove = new ArrayList<TanningRecipe>();

        for (TanningRecipe recipe : recipeList) {
            if (output.matches(new MCItemStack(recipe.output)) && (input == null || input.matches(new MCItemStack(recipe.input)))) {
                recipesToRemove.add(recipe);
            }
        }

        if (recipesToRemove.isEmpty()) {
            MineTweakerAPI.logWarning("No tanning rack recipes for " + output.toString());
            return;
        }

        MineTweakerAPI.apply(new RemoveAction(recipesToRemove));
    }

    public static class TanningAction implements IUndoableAction {
        private final IItemStack output;
        private final IIngredient input;
        private final float time;
        private final int tier;
        private final String tool;
        private final List<TanningRecipe> addedRecipes = new ArrayList<TanningRecipe>();

        public TanningAction(IItemStack output, IIngredient input, float time, int tier, String tool) {
            this.output = output;
            this.input = input;
            this.time = time;
            this.tier = tier;
            this.tool = tool;
        }

        @Override
        public void apply() {
            for (IItemStack s : input.getItems()) {
                ItemStack stack = MineTweakerMC.getItemStack(s);
                addedRecipes.add(TanningRecipe.addRecipe(stack, time, tier, tool, MineTweakerMC.getItemStack(output)));
            }
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            for (TanningRecipe recipe : addedRecipes) {
                TanningRecipe.recipeList.remove(recipe);
            }
            addedRecipes.clear();
        }

        @Override
        public String describe() {
            return "Adding tanning rack recipe for " + output.getDisplayName();
        }

        @Override
        public String describeUndo() {/**/
            return "Removing tanning rack recipe for " + output.getDisplayName();
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }

    }

    public static class RemoveAction implements IUndoableAction {
        private final List<TanningRecipe> recipes;

        public RemoveAction(List<TanningRecipe> recipes) {
            this.recipes = recipes;
        }

        @Override
        public void apply() {
            for (TanningRecipe recipe : recipes) {
                TanningRecipe.recipeList.remove(recipe);
            }
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            for (TanningRecipe recipe : recipes) {
                TanningRecipe.recipeList.add(recipe);
            }
            recipes.clear();
        }

        @Override
        public String describe() {
            return "Removing " + recipes.size() + " tanning rack recipes";
        }

        @Override
        public String describeUndo() {
            return "Restoring " + recipes.size() + " tanning rack recipes";
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }
}