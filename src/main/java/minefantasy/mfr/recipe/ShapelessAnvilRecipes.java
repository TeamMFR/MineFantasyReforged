package minefantasy.mfr.recipe;

import minefantasy.mfr.api.heating.Heatable;
import minefantasy.mfr.api.heating.IHotItem;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AnonymousProductions
 */
public class ShapelessAnvilRecipes implements IAnvilRecipe {
	public static final int globalWidth = 6;
	public static final int globalHeight = 4;

	/**
	 * Is the ItemStack that you get when craft the recipe.
	 */
	public final ItemStack recipeOutput;

	public final boolean outputHot;
	/**
	 * Is a List of ItemStack that composes the recipe.
	 */
	public final List<ItemStack> recipeItems;
	/**
	 * The anvil Required
	 */
	public final int anvil;
	public final int recipeTime;
	public final String toolType;
	public final String research;
	public final Skill skillUsed;
	private final int recipeHammer;

	public ShapelessAnvilRecipes(ItemStack output, String toolType, int hammer, int anvi, int time, List<ItemStack> components, boolean hot, String research, Skill skill) {
		this.outputHot = hot;
		this.recipeOutput = output;
		this.anvil = anvi;
		this.recipeItems = components;
		this.recipeHammer = hammer;
		this.recipeTime = time;
		this.toolType = toolType;
		this.research = research;
		this.skillUsed = skill;
	}

	@Override
	public ItemStack getAnvilRecipeOutput() {
		return this.recipeOutput;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(AnvilCraftMatrix matrix) {
		ArrayList<ItemStack> var2 = new ArrayList<>(this.recipeItems);

		for (int column = 0; column <= globalWidth; ++column) {
			for (int row = 0; row <= globalHeight; ++row) {
				ItemStack inputItem = matrix.getStackInRowAndColumn(row, column);

				if (!inputItem.isEmpty()) {
					boolean var6 = false;

					for (Object o : var2) {
						ItemStack recipeItem = (ItemStack) o;

						// HEATING
						if (Heatable.requiresHeating && Heatable.canHeatItem(inputItem)) {
							return false;
						}
						if (!Heatable.isWorkable(inputItem)) {
							return false;
						}
						inputItem = getHotItem(inputItem);

						if (inputItem.isEmpty()) {
							return false;
						}
						if (!CustomToolHelper.doesMatchForRecipe(recipeItem, inputItem)) {
							return false;
						}
						if (inputItem.getItem() == recipeItem.getItem()
								&& (recipeItem.getItemDamage() == OreDictionary.WILDCARD_VALUE
								|| inputItem.getItemDamage() == recipeItem.getItemDamage())) {
							var6 = true;
							var2.remove(recipeItem);
							break;
						}
					}

					if (!var6) {
						return false;
					}
				}
			}
		}

		return var2.isEmpty();
	}

	protected ItemStack getHotItem(ItemStack item) {
		if (item.isEmpty())
			return ItemStack.EMPTY;
		if (!(item.getItem() instanceof IHotItem)) {
			return item;
		}

		ItemStack hotItem = Heatable.getItemStack(item);

		if (!hotItem.isEmpty()) {
			return hotItem;
		}

		return item;
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack getCraftingResult(AnvilCraftMatrix par1AnvilCraftMatrix) {
		return this.recipeOutput.copy();
	}

	/**
	 * Returns the size of the recipe area
	 */
	@Override
	public int getRecipeSize() {
		return this.recipeItems.size();
	}

	@Override
	public int getCraftTime() {
		return this.recipeTime;
	}

	@Override
	public int getHammerTier() {
		return this.recipeHammer;
	}

	@Override
	public int getAnvilTier() {
		return this.anvil;
	}

	@Override
	public boolean outputHot() {
		return outputHot;
	}

	@Override
	public String getToolType() {
		return toolType;
	}

	@Override
	public String getResearch() {
		return research;
	}

	@Override
	public Skill getSkill() {
		return skillUsed;
	}

	@Override
	public boolean useCustomTiers() {
		return false;
	}
}
