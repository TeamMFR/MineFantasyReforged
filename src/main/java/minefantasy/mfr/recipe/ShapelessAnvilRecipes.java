package minefantasy.mfr.recipe;

import minefantasy.mfr.api.heating.Heatable;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author AnonymousProductions
 */
public class ShapelessAnvilRecipes extends AnvilRecipeBase {

	public ShapelessAnvilRecipes(NonNullList<Ingredient> inputs, ItemStack output, String toolType, int craftTime, int hammerTier, int anvilTier, boolean hotOutput, String requiredResearch, Skill requiredSkill) {
		super(inputs, output, toolType, craftTime, hammerTier, anvilTier, hotOutput, requiredResearch, requiredSkill);
	}

	@Override
	public ItemStack getAnvilRecipeOutput() {
		return this.output;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	boolean matches(@Nonnull AnvilCraftMatrix inv, @Nonnull World world) {
		NonNullList<Ingredient> ingredients = getIngredients();
		List<Boolean> ingredientsMatched = new ArrayList<>(Collections.nCopies(ingredients.size(), false));

		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack inputItem = inv.getStackInSlot(i);
			if (inputItem.isEmpty()) {
				continue;
			}
			boolean matched = false;
			for (int j = 0; j < ingredients.size(); j++) {
				boolean passesChecks = true;

				// HEATING
				if (Heatable.requiresHeating && Heatable.canHeatItem(inputItem)) {
					passesChecks = false;
				}
				if (!Heatable.isWorkable(inputItem)) {
					passesChecks = false;
				}
				inputItem = getHotItem(inputItem);

				if (inputItem.isEmpty()) {
					passesChecks = false;
				}
				if (!CustomToolHelper.doesMatchForRecipe(ingredients.get(j), inputItem)) {
					passesChecks = false;
				}

				if (passesChecks && ingredients.get(j).apply(inputItem)) {
					ingredientsMatched.set(j, true);
					matched = true;
					break;
				}
			}
			if (!matched) {
				return false;
			}
		}

		return !ingredientsMatched.contains(false);
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack getCraftingResult(AnvilCraftMatrix par1AnvilCraftMatrix) {
		return this.output.copy();
	}

	/**
	 * Returns the size of the recipe area
	 */
	@Override
	public int getRecipeSize() {
		return this.inputs.size();
	}

	@Override
	public int getCraftTime() {
		return this.craftTime;
	}

	@Override
	public int getHammerTier() {
		return this.hammerTier;
	}

	@Override
	public int getAnvilTier() {
		return this.anvilTier;
	}

	@Override
	public boolean outputHot() {
		return hotOutput;
	}

	@Override
	public String getToolType() {
		return toolType;
	}

	@Override
	public String getResearch() {
		return requiredResearch;
	}

	@Override
	public Skill getSkill() {
		return requiredSkill;
	}

	@Override
	public boolean useCustomTiers() {
		return false;
	}
}
