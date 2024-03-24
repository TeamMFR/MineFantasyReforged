package minefantasy.mfr.recipe;

import minefantasy.mfr.api.heating.Heatable;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.item.ItemHeated;
import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.material.MetalMaterial;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnvilShapelessCustomMaterialRecipe extends AnvilRecipeBase {
	protected boolean tierModifyOutputCount;
	public AnvilShapelessCustomMaterialRecipe(NonNullList<Ingredient> inputs, ItemStack output,
			String toolType, int craftTime, int hammerTier, int anvilTier, boolean hotOutput,
			String requiredResearch, Skill requiredSkill,
			int skillXp, float vanillaXp,
			boolean tierModifyOutputCount) {
		super(inputs, output, toolType, craftTime, hammerTier, anvilTier, hotOutput,
				requiredResearch, requiredSkill, skillXp, vanillaXp);
		this.tierModifyOutputCount = tierModifyOutputCount;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	boolean matches(@Nonnull AnvilCraftMatrix matrix, @Nonnull World world) {
		String wood = null;
		String metal = null;
		NonNullList<Ingredient> ingredients = getIngredients();
		List<Boolean> ingredientsMatched = new ArrayList<>(Collections.nCopies(ingredients.size(), false));

		for (int i = 0; i < matrix.getSizeInventory(); ++i) {
			ItemStack inputItem = matrix.getStackInSlot(i);
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
		if (!modifyTiers(matrix, metal, true)) {
			modifyTiers(matrix, wood, false);
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
			ItemStack inputItem = matrix.getStackInSlot(i);
			String component_wood = CustomToolHelper.getComponentMaterial(inputItem, "wood");
			String component_metal = CustomToolHelper.getComponentMaterial(inputItem, "metal");

			for (CustomMaterial material : CustomMaterial.getList("metal")){
				NonNullList<ItemStack> materialOreDictStacks = OreDictionary.getOres(((MetalMaterial)material).oreDictList);
				for (ItemStack materialOreDictStack : materialOreDictStacks){
					if (OreDictionary.itemMatches(ItemHeated.getStack(inputItem), materialOreDictStack, true)){
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
		if (metal != null && !tierModifyOutputCount) {
			CustomMaterial.addMaterial(result, CustomToolHelper.slot_main, metal);
		}
		if (wood != null && !tierModifyOutputCount) {
			CustomMaterial.addMaterial(result, CustomToolHelper.slot_haft, wood);
		}

		if (tierModifyOutputCount) {
			int modifiedCount = MathHelper.clamp(
					MetalMaterial.getMaterial(metal).tier * result.getCount(),
					1,
					result.getMaxStackSize());
			result.setCount(modifiedCount);
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
	public boolean useCustomTiers() {
		return true;
	}

	public boolean isTierModifyOutputCount() {
		return tierModifyOutputCount;
	}
}
