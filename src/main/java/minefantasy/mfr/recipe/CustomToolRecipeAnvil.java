package minefantasy.mfr.recipe;

import minefantasy.mfr.api.heating.Heatable;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.item.ItemHeated;
import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.material.MetalMaterial;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;

/**
 * @author AnonymousProductions
 */
public class CustomToolRecipeAnvil extends ShapedAnvilRecipes {
	String oreDictList;

	public CustomToolRecipeAnvil(int width, int height, ItemStack[] inputs, ItemStack output, String toolType, int time, int hammer, int anvil, boolean hot, String research, Skill skill) {
		super(width, height, inputs, output, toolType, time, hammer, anvil, hot, research, skill);
	}

	public CustomToolRecipeAnvil(int width, int height, ItemStack[] inputs, ItemStack output, String toolType, int time, int hammer, int anvil, boolean hot, String research, Skill skill, String oreDictList) {
		super(width, height, inputs, output, toolType, time, hammer, anvil, hot, research, skill);
		this.oreDictList = oreDictList;
	}

	/**
	 * Checks if the region of a crafting inventory is match for the recipe.
	 */
	protected boolean checkMatch(InventoryCrafting matrix, int x, int y, boolean b) {
		String wood = null;
		String metal = null;

		for (int matrixX = 0; matrixX < ShapelessAnvilRecipes.globalWidth; ++matrixX) {
			for (int matrixY = 0; matrixY < ShapelessAnvilRecipes.globalHeight; ++matrixY) {
				int recipeX = matrixX - x;
				int recipeY = matrixY - y;
				ItemStack recipeItem = ItemStack.EMPTY;
				if (recipeX >= 0 && recipeY >= 0 && recipeX < this.recipeWidth && recipeY < this.recipeHeight) {
					if (b) {
						recipeItem = this.recipeItems[this.recipeWidth - recipeX - 1 + recipeY * this.recipeWidth];
					} else {
						recipeItem = this.recipeItems[recipeX + recipeY * this.recipeWidth];
					}
				}

				ItemStack inputItem = matrix.getStackInRowAndColumn(matrixX, matrixY);

				if (!inputItem.isEmpty() || recipeItem != null && !recipeItem.isEmpty()) {

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

					if (oreDictList != null){
						NonNullList<ItemStack> oreDictStacks = OreDictionary.getOres(oreDictList);
						if (oreDictStacks.contains(recipeItem)){
							for (CustomMaterial material : CustomMaterial.getList("metal")){
								NonNullList<ItemStack> materialOreDictStacks = OreDictionary.getOres(((MetalMaterial)material).oreDictList);
								for (ItemStack materialOreDictStack : materialOreDictStacks){
									if (OreDictionary.itemMatches(materialOreDictStack, inputItem, true)){
										metal = material.name;
										recipeItem = materialOreDictStack;
									}
								}
							}
						}
					}

					if (inputItem == null && recipeItem != null || inputItem != null && recipeItem == null) {
						return false;
					}

					if (inputItem == null) {
						return false;
					}

					if (recipeItem.getItem() != inputItem.getItem()) {
						return false;
					}

					if (recipeItem.getItemDamage() != OreDictionary.WILDCARD_VALUE
							&& recipeItem.getItemDamage() != inputItem.getItemDamage()) {
						return false;
					}
					if (!CustomToolHelper.doesMatchForRecipe(recipeItem, inputItem)) {
						return false;
					}
				}
			}
		}
		if (matrix instanceof AnvilCraftMatrix) {
			if (!modifyTiers((AnvilCraftMatrix) matrix, metal, true)) {
				modifyTiers((AnvilCraftMatrix) matrix, wood, false);
			}
		}

		return true;
	}

	private boolean modifyTiers(AnvilCraftMatrix matrix, String tier, boolean isMain) {
		CustomMaterial material = CustomMaterial.getMaterial(tier);
		if (material != CustomMaterial.NONE) {
			int newTier = recipeHammer < 0 ? material.crafterTier : recipeHammer;
			int newAnvil = anvilTier < 0 ? material.crafterAnvilTier : anvilTier;
			matrix.modifyTier(newTier, newAnvil, (int) (recipeTime * material.craftTimeModifier));
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
	public boolean useCustomTiers() {
		return true;
	}
}
