package minefantasy.mf2.integration.minetweaker.tweakers;

import minefantasy.mf2.api.refine.BlastFurnaceRecipes;
import minefantasy.mf2.util.Utils;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Map;

@ZenClass("mods.minefantasy.BlastFurnace")
public class BlastFurnace {

    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input) {
        MineTweakerAPI.apply(new AddRecipeAction(output, input));
    }

    private static class AddRecipeAction implements IUndoableAction {

        private final IItemStack output;
        private final IIngredient input;

        public AddRecipeAction(IItemStack output, IIngredient input) {
            this.output = output;
            this.input = input;
        }

        @Override
        public void apply() {
            ItemStack mcOutput = MineTweakerMC.getItemStack(output);
            for (IIngredient ingredient : input.getItems()) {
                ItemStack mcInput = MineTweakerMC.getItemStack(ingredient);
                BlastFurnaceRecipes.smelting().addRecipe(mcInput, mcOutput);
            }
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            for (IIngredient ingredient : input.getItems()) {
                for (Map.Entry<ItemStack, ItemStack> entry : BlastFurnaceRecipes.smelting().getSmeltingList().entrySet()) {
                    if (Utils.doesMatch(entry.getKey(), MineTweakerMC.getItemStack(ingredient)) && Utils.doesMatch(entry.getValue(), MineTweakerMC.getItemStack(output))) {
                        BlastFurnaceRecipes.smelting().getSmeltingList().remove(entry);
                    }
                }
            }
        }

        @Override
        public String describe() {
            return "Adding blast furnace recipe for " + output.getDisplayName();
        }

        @Override
        public String describeUndo() {
            return "Removing blast furnace recipe for " + output.getDisplayName();
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }
}
