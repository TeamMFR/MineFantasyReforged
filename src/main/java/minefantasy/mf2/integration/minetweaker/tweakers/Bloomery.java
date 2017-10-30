package minefantasy.mf2.integration.minetweaker.tweakers;

import minefantasy.mf2.api.crafting.refine.BloomRecipe;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.minefantasy.Bloomery")
public class Bloomery {

    @ZenMethod
    public static void addRecipe(IItemStack result, IIngredient input) {
        MineTweakerAPI.apply(new addBloomRecipe(result, input));
    }

    public static class addBloomRecipe implements IUndoableAction {

        IItemStack result;
        IIngredient input;

        public addBloomRecipe(IItemStack result, IIngredient input) {
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
            return "Adding Bloomery Recipe That Results In " + result.getDisplayName();
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
            return "STAHP UNDOOOOIN MAH RECIPES";
        }

        @Override
        public void undo() {
            BloomRecipe.recipeList.remove(input);
        }

    }

}
