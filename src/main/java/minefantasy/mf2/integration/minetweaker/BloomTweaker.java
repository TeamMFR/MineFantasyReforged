package minefantasy.mf2.integration.minetweaker;

import minefantasy.mf2.api.crafting.refine.BloomRecipe;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.minefantasy.Bloomery")
public class BloomTweaker {

	@ZenMethod
	public static void addRecipe(IItemStack input, IItemStack output) {
		MineTweakerAPI.apply(new AddBloomRecipe(input, output));
	}

	public static class AddBloomRecipe implements IUndoableAction {

		private ItemStack input;
		private ItemStack output;

		public AddBloomRecipe(IItemStack input, IItemStack output) {
			this.input = MineTweakerMC.getItemStack(input);
			this.output = MineTweakerMC.getItemStack(output);
		}

		@Override
		public void apply() {
			BloomRecipe.addRecipe(input, output);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public String describe() {
			return String.format("Adding recipe %s -> %s * %d to Bloomery", input.getDisplayName(),
					output.getDisplayName(), output.stackSize);
		}

		@Override
		public String describeUndo() {
			return String.format("Removing recipe %s -> %s * %d from Bloomery", input.getDisplayName(),
					output.getDisplayName(), output.stackSize);
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public void undo() {
			BloomRecipe.recipeList.remove(input, output);
		}
	}
}
