package minefantasy.mfr.recipe;

import com.google.gson.JsonObject;
import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.material.WoodMaterial;
import minefantasy.mfr.util.CustomToolHelper;
import minefantasy.mfr.util.RecipeHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.ShapedOreRecipe;

import javax.annotation.Nonnull;

@SuppressWarnings("unused")
public class RecipeTimberDynamic extends ShapedOreRecipe {
	ItemStack inputItemStack;

	public RecipeTimberDynamic(ResourceLocation group, @Nonnull ItemStack result, CraftingHelper.ShapedPrimer primer) {
		super(group, result, primer);
	}

	@Override
	protected boolean checkMatch(InventoryCrafting matrix, int startX, int startY, boolean mirror) {
		for (int x = 0; x < matrix.getWidth(); x++) {
			for (int y = 0; y < matrix.getHeight(); y++) {
				int subX = x - startX;
				int subY = y - startY;
				Ingredient target = Ingredient.EMPTY;

				if (subX >= 0 && subY >= 0 && subX < width && subY < height) {
					if (mirror) {
						target = input.get(width - subX - 1 + subY * width);
					} else {
						target = input.get(subX + subY * width);
					}
				}

				if (!target.apply(matrix.getStackInRowAndColumn(x, y))) {
					return false;
				} else {
					ItemStack ingredientInput = matrix.getStackInRowAndColumn(x, y);
					ItemStack itemStackBelow = matrix.getStackInRowAndColumn(x, y + 1);
					if (!ingredientInput.isEmpty()) {
						inputItemStack = ingredientInput;
						if (!itemStackBelow.isEmpty()) {
							return itemStackBelow.isItemEqual(inputItemStack);
						}
					}
				}
			}
		}

		return true;
	}

	@Nonnull
	@Override
	public ItemStack getCraftingResult(@Nonnull InventoryCrafting matrix) {
		ItemStack outputModified = output.copy();
		CustomMaterial inputMaterial = CustomMaterial.NONE;
		for (CustomMaterial material : CustomMaterial.getList("wood")) {
			if (material instanceof WoodMaterial) {
				Item materialItem = ForgeRegistries.ITEMS.getValue(((WoodMaterial) material).inputItemResourceLocation);
				if (materialItem != null) {
					ItemStack materialItemStack = new ItemStack(materialItem, 1, ((WoodMaterial) material).inputItemMeta);
					if (inputItemStack.isItemEqual(materialItemStack)) {
						inputMaterial = material;
					}
				}
			}
		}
		CustomMaterial.addMaterial(outputModified, CustomToolHelper.slot_main, inputMaterial.name);
		return outputModified;
	}

	@Nonnull
	@Override
	public ItemStack getRecipeOutput() {
		return output.copy();
	}

	public static class Factory implements IRecipeFactory {
		@Override
		public IRecipe parse(JsonContext context, JsonObject json) {

			final String group = JsonUtils.getString(json, "group", "");
			final CraftingHelper.ShapedPrimer primer = RecipeHelper.parseShaped(context, json);
			final ItemStack result = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);

			return new RecipeTimberDynamic(group.isEmpty() ? null : new ResourceLocation(group), result, primer);
		}
	}
}
