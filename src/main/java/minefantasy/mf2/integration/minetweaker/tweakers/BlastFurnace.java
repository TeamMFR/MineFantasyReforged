package minefantasy.mf2.integration.minetweaker.tweakers;

import minefantasy.mf2.api.refine.BlastFurnaceRecipes;
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
import java.util.Map;

@ZenClass("mods.minefantasy.BlastFurnace")
public class BlastFurnace {

    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input) {
        MineTweakerAPI.apply(new AddRecipeAction(output, input));
    }

    @ZenMethod
    public static void remove(@NotNull IIngredient output, @Optional IIngredient input) {
        List<ItemStack> toRemove = new ArrayList<>();
        List<ItemStack> toRemoveValues = new ArrayList<>();
        for (Map.Entry<ItemStack, ItemStack> entry : BlastFurnaceRecipes.smelting().getSmeltingList().entrySet()) {
            if (output.matches(new MCItemStack(entry.getValue()))
                    && (input == null || input.matches(new MCItemStack(entry.getKey())))) {
                toRemove.add(entry.getKey());
                toRemoveValues.add(entry.getValue());
            }
        }

        if (toRemove.isEmpty()) {
            MineTweakerAPI.logWarning("No Blast Furnace recipes for " + output.toString());
            return;
        }

        MineTweakerAPI.apply(new RemoveAction(toRemove, toRemoveValues));
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
                BlastFurnaceRecipes.smelting().removeRecipe(MineTweakerMC.getItemStack(ingredient), MineTweakerMC.getItemStack(output));
            }
        }

        @Override
        public String describe() {
            return "Adding Blast Furnace recipe for " + output.getDisplayName();
        }

        @Override
        public String describeUndo() {
            return "Removing Blast Furnace recipe for " + output.getDisplayName();
        }

        @Override
        public Object getOverrideKey() {
            return null;
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
                BlastFurnaceRecipes.smelting().getSmeltingList().remove(item);
            }
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            for (int i = 0; i < items.size(); i++) {
                BlastFurnaceRecipes.smelting().addRecipe(items.get(i), values.get(i));
            }
        }

        @Override
        public String describe() {
            return "Removing " + items.size() + " Blast Furnace recipes";
        }

        @Override
        public String describeUndo() {
            return "Restoring " + items.size() + " Blast Furnace recipes";
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }
}