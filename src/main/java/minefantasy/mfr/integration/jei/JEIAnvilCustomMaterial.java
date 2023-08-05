package minefantasy.mfr.integration.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IStackHelper;
import mezz.jei.util.Translator;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.material.MetalMaterial;
import minefantasy.mfr.recipe.AnvilRecipeBase;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a JEI "recipe" for the MFR anvil.
 */
public class JEIAnvilCustomMaterial extends JEIAnvilRecipe {

	private final List<List<ItemStack>> ingredients;
	private final List<List<ItemStack>> outputs;

	public JEIAnvilCustomMaterial(AnvilRecipeBase recipe, IStackHelper stackHelper) {
		super(recipe, stackHelper);
		List<List<ItemStack>> ingredients = stackHelper.expandRecipeItemStackInputs(recipe.inputs);
		ItemStack result = recipe.getAnvilRecipeOutput();
		this.outputs = getOutputsFromIngredients(ingredients, result);
		this.ingredients = ingredients;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, this.ingredients);
		ingredients.setOutputLists(VanillaTypes.ITEM, outputs);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);

		String recipeName = super.recipe.getRegistryName().getPath();

		if (recipeName.equalsIgnoreCase("bar") || recipeName.equalsIgnoreCase("ingot")) {
			minecraft.fontRenderer.drawStringWithShadow(Translator.translateToLocal("recipe." + recipeName + ".desc"), (float) (0), (float) -15, 16777215);
		}

	}

	protected List<List<ItemStack>> getOutputsFromIngredients(List<List<ItemStack>> ingredients, ItemStack result) {

		List<ItemStack> outputs = new ArrayList<>();
		for (List<ItemStack> stacks : ingredients) {
			if (result.getItem() == MineFantasyItems.HOT_ITEM) {//Dummy item to match in recipe
				for (CustomMaterial material : CustomMaterial.getList("metal")) {
					if (material instanceof MetalMaterial) {
						NonNullList<ItemStack> oreDictItemStacks = OreDictionary.getOres(((MetalMaterial) material).oreDictList);
						outputs.addAll(oreDictItemStacks);
					}
				}
			}
			else {
				for (ItemStack stack : stacks) {
					ItemStack resultCopy = result.copy();
					String wood = null;
					String metal = null;

					if (stack != null && !stack.isEmpty()) {
						String component_wood = CustomToolHelper.getComponentMaterial(stack, "wood");
						String component_metal = CustomToolHelper.getComponentMaterial(stack, "metal");

						if (component_metal == null && component_wood == null) {
							for (CustomMaterial material : CustomMaterial.getList("metal")){
								NonNullList<ItemStack> materialOreDictStacks = OreDictionary.getOres(((MetalMaterial)material).oreDictList);
								for (ItemStack materialOreDictStack : materialOreDictStacks){
									if (OreDictionary.itemMatches(stack, materialOreDictStack, true)){
										component_metal = material.name;
										break;
									}
								}
							}
						}

						if (component_wood != null) {
							wood = component_wood;
						}
						if (component_metal != null) {
							metal = component_metal;
						}
					}
					if (metal != null) {
						CustomMaterial.addMaterial(resultCopy, CustomToolHelper.slot_main, metal);
					}
					if (wood != null) {
						CustomMaterial.addMaterial(resultCopy, CustomToolHelper.slot_haft, wood);
					}

					outputs.add(resultCopy);
				}
			}
		}

		return Collections.singletonList(outputs);
	}
}
