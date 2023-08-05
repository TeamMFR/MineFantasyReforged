package minefantasy.mfr.recipe;

import minefantasy.mfr.api.heating.Heatable;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.item.ItemHeated;
import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.material.MetalMaterial;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class ShapedCustomMaterialAnvilRecipe extends AnvilRecipeBase {
	public ShapedCustomMaterialAnvilRecipe(NonNullList<Ingredient> inputs, ItemStack output,
			String toolType, int craftTime, int hammerTier, int anvilTier, boolean hotOutput,
			String requiredResearch, Skill requiredSkill) {
		super(inputs, output, toolType, craftTime, hammerTier, anvilTier, hotOutput, requiredResearch, requiredSkill);
	}

	@Override
	public boolean matches(AnvilCraftMatrix inv, World worldIn) {
		for (int i = 0; i <= inv.getWidth() - WIDTH; ++i) {
			for (int j = 0; j <= inv.getHeight() - HEIGHT; ++j) {
				if (this.checkMatch(inv, i, j, true)) {
					return true;
				}

				if (this.checkMatch(inv, i, j, false)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Checks if the region of a crafting inventory is match for the recipe.
	 */
	protected boolean checkMatch(AnvilCraftMatrix matrix, int x, int y, boolean b) {
		String wood = null;
		String metal = null;

		for (int matrixX = 0; matrixX < WIDTH; ++matrixX) {
			for (int matrixY = 0; matrixY < HEIGHT; ++matrixY) {
				int recipeX = matrixX - x;
				int recipeY = matrixY - y;
				Ingredient ingredient = Ingredient.EMPTY;

				if (recipeX >= 0 && recipeY >= 0 && recipeX < WIDTH && recipeY < HEIGHT) {
					if (b) {
						ingredient = inputs.get(WIDTH - recipeX - 1 + recipeY * WIDTH);
					} else {
						ingredient = inputs.get(recipeX + recipeY * WIDTH);
					}
				}

				ItemStack inputItem = matrix.getStackInRowAndColumn(matrixX, matrixY);

				if (!inputItem.isEmpty() || !ingredient.apply(ItemStack.EMPTY)) {

					String component_wood = CustomToolHelper.getComponentMaterial(inputItem, "wood");
					String component_metal = CustomToolHelper.getComponentMaterial(inputItem, "metal");

					// CHECK CUSTOM METAL
					if (component_metal != null) {
						if (metal == null) {
							metal = component_metal;
						} else {
							if (!metal.equalsIgnoreCase(component_metal)) {
								return false;
							}
						}
					}
					// CHECK CUSTOM WOOD
					if (component_wood != null) {
						if (wood == null) {
							wood = component_wood;
						} else {
							if (!wood.equalsIgnoreCase(component_wood)) {
								return false;
							}
						}
					}

					// HEATING
					if (Heatable.requiresHeating && Heatable.canHeatItem(inputItem)) {
						return false;
					}
					if (!Heatable.isWorkable(inputItem)) {
						return false;
					}
					inputItem = getHotItem(inputItem);

					for (CustomMaterial material : CustomMaterial.getList("metal")){
						NonNullList<ItemStack> materialOreDictStacks = OreDictionary.getOres(((MetalMaterial)material).oreDictList);
						for (ItemStack materialOreDictStack : materialOreDictStacks){
							if (OreDictionary.itemMatches(materialOreDictStack, inputItem, true)){
								if (ingredient.apply(materialOreDictStack)) {
									metal = material.name;
								}
							}
						}
					}

					if (inputItem == null && ingredient != null || inputItem != null && ingredient == null) {
						return false;
					}

					if (inputItem == null) {
						return false;
					}

					if (!ingredient.apply(inputItem)) {
							return false;
					}

					if (!CustomToolHelper.doesMatchForRecipe(ingredient, inputItem)) {
						return false;
					}
				}
			}
		}
		if (!modifyTiers(matrix, metal, true)) {
			modifyTiers(matrix, wood, false);
		}

		return true;
	}

	private boolean modifyTiers(AnvilCraftMatrix matrix, String tier, boolean isMain) {
		CustomMaterial material = CustomMaterial.getMaterial(tier);
		if (material != CustomMaterial.NONE) {
			int newTier = hammerTier < 0 ? material.crafterTier : hammerTier;
			int newAnvil = anvilTier < 0 ? material.crafterAnvilTier : anvilTier;
			matrix.modifyTier(newTier, newAnvil, (int) (craftTime * material.craftTimeModifier));
			if (isMain) {
				matrix.modifyResearch("smelt_" + material.getName());
			}
			return true;
		}
		return false;
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack getCraftingResult(AnvilCraftMatrix matrix) {
		ItemStack result = super.getCraftingResult(matrix);

		String wood = null;
		String metal = null;
		for (int i = 0; i < matrix.getSizeInventory(); i++) {
			ItemStack item = matrix.getStackInSlot(i);
			String component_wood = CustomToolHelper.getComponentMaterial(item, "wood");
			String component_metal = CustomToolHelper.getComponentMaterial(item, "metal");

			for (CustomMaterial material : CustomMaterial.getList("metal")){
				NonNullList<ItemStack> materialOreDictStacks = OreDictionary.getOres(((MetalMaterial)material).oreDictList);
				for (ItemStack materialOreDictStack : materialOreDictStacks){
					if (OreDictionary.itemMatches(ItemHeated.getStack(item), materialOreDictStack, true)){
						component_metal = material.name;
					}
				}
			}

			if (wood == null && component_wood != null) {
				wood = component_wood;
			}
			if (metal == null && component_metal != null) {
				metal = component_metal;
			}
		}
		if (metal != null) {
			CustomMaterial.addMaterial(result, CustomToolHelper.slot_main, metal);
		}
		if (wood != null) {
			CustomMaterial.addMaterial(result, CustomToolHelper.slot_haft, wood);
		}

		if (result.getItem() == MineFantasyItems.HOT_ITEM){ //Dummy item to match in recipe
			for (CustomMaterial material : CustomMaterial.getList("metal")){
				if (material instanceof MetalMaterial) {
					if (material.getName().equals(metal)){
						NonNullList<ItemStack> oreDictItemStacks = OreDictionary.getOres(((MetalMaterial) material).oreDictList);
						result = oreDictItemStacks.get(0);
					}
				}
			}
		}

		return result;
	}

	@Override
	public ItemStack getAnvilRecipeOutput() {
		return output;
	}

	/**
	 * Returns the size of the recipe area
	 */
	@Override
	public int getRecipeSize() {
		return WIDTH * HEIGHT;
	}

	@Override
	public int getAnvilTier() {
		return anvilTier;
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
		return true;
	}
}
