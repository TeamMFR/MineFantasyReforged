package minefantasy.mf2.integration.minetweaker.tweakers;

import minefantasy.mf2.api.cooking.CookRecipe;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ZenClass("mods.minefantasy.Cooking")
public class Cooking {

    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input, int minTemp, int maxTemp, int time, int burnTime, boolean requireBaking, @Optional boolean canBurn) {
        MineTweakerAPI.apply(new AddRecipeAction(output, input, minTemp, maxTemp, time, burnTime, requireBaking, canBurn));
    }

    private static class AddRecipeAction implements IUndoableAction {
        private final IItemStack output;
        private final IIngredient input;
        private final int minTemp, maxTemp, time, burnTime;
        private final boolean requireBaking, canBurn;
        private final List<CookRecipe> addedRecipes = new ArrayList<CookRecipe>();

        public AddRecipeAction(IItemStack output, IIngredient input, int minTemp, int maxTemp, int time, int burnTime, boolean requireBaking, boolean canBurn) {
            this.output = output;
            this.input = input;
            this.minTemp = minTemp;
            this.maxTemp = maxTemp;
            this.time = time;
            this.burnTime = burnTime;
            this.requireBaking = requireBaking;
            this.canBurn = canBurn;
        }

        @Override
        public void apply() {
            for (IIngredient ingredient : input.getItems()) {
                ItemStack mcInput = MineTweakerMC.getItemStack(ingredient);
                addedRecipes.add(CookRecipe.addRecipe(MineTweakerMC.getItemStack(output), mcInput, new ItemStack(CookRecipe.burnt_food), minTemp, maxTemp, time, burnTime, requireBaking, canBurn));
            }
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            for (Map.Entry<String, CookRecipe> recipeEntry : CookRecipe.recipeList.entrySet()) {
                for (CookRecipe recipe : addedRecipes) {
                    if (recipe.equals(recipe)) {
                        CookRecipe.recipeList.remove(recipeEntry);
                    }
                }
            }
            addedRecipes.clear();
        }

        @Override
        public String describe() {
            return null;
        }

        @Override
        public String describeUndo() {
            return null;
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

}
