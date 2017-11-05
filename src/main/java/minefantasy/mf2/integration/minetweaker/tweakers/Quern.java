package minefantasy.mf2.integration.minetweaker.tweakers;

import minefantasy.mf2.api.crafting.refine.QuernRecipes;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenMethod;

public class Quern {

    @ZenMethod
    public static void addRecipe(IItemStack result, IIngredient input) {
        MineTweakerAPI.apply(new AddRecipeAction(result, input, 0, true));
    }

    @ZenMethod
    public static void addRecipe(IItemStack result, IIngredient input, int tier) {
        MineTweakerAPI.apply(new AddRecipeAction(result, input, tier, true));
    }

    @ZenMethod
    public static void addRecipe(IItemStack result, IIngredient input, int tier, boolean consumePot) {
        MineTweakerAPI.apply(new AddRecipeAction(result, input, tier, consumePot));
    }

    private static class AddRecipeAction implements IUndoableAction {
        IItemStack result;
        IIngredient input;
        int tier;
        boolean consumePot;

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
                QuernRecipes.addRecipe(s, MineTweakerMC.getItemStack(result), this.tier, consumePot);
            }
        }

        @Override
        public boolean canUndo() {
            return false;
        }

        @Override
        public void undo() {

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
