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

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShapelessCustomMaterialAnvilRecipe extends AnvilRecipeBase {

	public ShapelessCustomMaterialAnvilRecipe(NonNullList<Ingredient> inputs, ItemStack output,
			String toolType, int craftTime, int hammerTier, int anvilTier, boolean hotOutput,
			String requiredResearch, Skill requiredSkill) {

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
		String wood = null;
		String metal = null;
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

				String component_wood = CustomToolHelper.getComponentMaterial(inputItem, "wood");
				String component_metal = CustomToolHelper.getComponentMaterial(inputItem, "metal");

				// CHECK CUSTOM METAL
				if (component_metal != null) {
					if (metal == null) {
						metal = component_metal;
					} else {
						if (!metal.equalsIgnoreCase(component_metal)) {
							passesChecks = false;
						}
					}
				}
				// CHECK CUSTOM WOOD
				if (component_wood != null) {
					if (wood == null) {
						wood = component_wood;
					} else {
						if (!wood.equalsIgnoreCase(component_wood)) {
							passesChecks = false;
						}
					}
				}

				Ingredient ingredient = ingredients.get(j);
				// HEATING
				if (Heatable.requiresHeating && Heatable.canHeatItem(inputItem)) {
					passesChecks = false;
				}
				if (!Heatable.isWorkable(inputItem)) {
					passesChecks = false;
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
