package minefantasy.mf2.integration.minetweaker.tweakers;

import minefantasy.mf2.api.crafting.tanning.TanningRecipe;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.mc1710.item.MCItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

@ZenClass("mods.minefantasy.TanningRack")
public class TanningRack {

    @ZenMethod
    public static void addTieredRecipe(IItemStack output, IIngredient input, float time, int tier) {
        MineTweakerAPI.apply(new TanningAction(output, input, time, tier, "knife"));
    }

    @ZenMethod
    public static void addTieredAndToolRecipe(IItemStack output, IIngredient input, float time, int tier, String tool) {
        MineTweakerAPI.apply(new TanningAction(output, input, time, tier, tool));
    }

    @ZenMethod
    public static void remove(IItemStack output, IIngredient input) {
        if (output == null)
            throw new IllegalArgumentException("Output value cannot be null");

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

    public static class TanningAction extends OneWayAction {
        IItemStack output;
        IIngredient input;
        float time;
        int tier;
        String tool;

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
                TanningRecipe.addRecipe(stack, time, tier, tool, MineTweakerMC.getItemStack(output));
            }
        }

        @Override
        public String describe() {
            return "Creating Tanning Recipe";
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