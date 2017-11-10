package minefantasy.mf2.integration.minetweaker.tweakers;

import minefantasy.mf2.api.refine.BigFurnaceRecipes;
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

@ZenClass("mods.minefantasy.BigFurnace")
public class BigFurnace {

    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input, @Optional int tier) {
        MineTweakerAPI.apply(new AddRecipeAction(output, input, tier));
    }

    private static class AddRecipeAction implements IUndoableAction {
        private final IItemStack output;
        private final IIngredient input;
        private final int tier;
        private final List<BigFurnaceRecipes> addedRecipes = new ArrayList<BigFurnaceRecipes>();

        public AddRecipeAction(IItemStack output, IIngredient input, int tier) {
            this.output = output;
            this.input = input;
            this.tier = tier;
        }

        @Override
        public void apply() {
            for (IIngredient ingredient : input.getItems()) {
                ItemStack mcInput = MineTweakerMC.getItemStack(ingredient);
                addedRecipes.add(BigFurnaceRecipes.addRecipe(mcInput, MineTweakerMC.getItemStack(output), tier));
            }
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            for (Map.Entry<String, BigFurnaceRecipes> entry : BigFurnaceRecipes.recipeList.entrySet()) {
                for (BigFurnaceRecipes recipeToRemove : addedRecipes) {
                    if (entry.getValue().equals(recipeToRemove)) {
                        BigFurnaceRecipes.recipeList.remove(entry);
                    }
                }
            }
            addedRecipes.clear();
        }

        @Override
        public String describe() {
            return "Adding big furnace recipe for " + output.getDisplayName();
        }

        @Override
        public String describeUndo() {
            return "Removing big furnace recipe for " + output.getDisplayName();
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

}
