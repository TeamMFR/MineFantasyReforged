package minefantasy.mf2.integration.minetweaker.tweakers;

import minefantasy.mf2.api.crafting.Salvage;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.minefantasy.SalvageTweaker")
public class SalvageTweaker {

    @ZenMethod
    public static void addSalvage(IItemStack output, IIngredient input) {
        MineTweakerAPI.apply(new AddSalvageAction(output, input));
    }

    private static class AddSalvageAction implements IUndoableAction {
        private final IItemStack output;
        private final IIngredient input;

        public AddSalvageAction(IItemStack output, IIngredient input) {
            this.output = output;
            this.input = input;
        }

        @Override
        public void apply() {
            for (IItemStack stack : input.getItems()) {
                ItemStack s = MineTweakerMC.getItemStack(stack);
                Salvage.addSalvage(s, MineTweakerMC.getItemStack(output));
            }
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            Salvage.salvageList.remove(input);
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

    private static class RemoveAction implements IUndoableAction {

        public RemoveAction() {

        }

        @Override
        public void apply() {

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
