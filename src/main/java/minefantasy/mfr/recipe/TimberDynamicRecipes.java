package minefantasy.mfr.recipe;

import com.google.gson.JsonObject;
import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.material.WoodMaterial;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import javax.annotation.Nonnull;

public class TimberDynamicRecipes extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

	protected Item output;
	protected NonNullList<Ingredient> ingredients;
	protected ItemStack input;
	protected int count;

	protected int width;
	protected int height;

	protected TimberDynamicRecipes(Item output, int count, CraftingHelper.ShapedPrimer primer) {
		this.output = output;
		this.count = count;
		this.ingredients = primer.input;
		this.width = primer.width;
		this.height = primer.height;
	}

	@Override
	public boolean matches(@Nonnull InventoryCrafting inv, @Nonnull World world) {
		for (int x = 0; x <= ShapedOreRecipe.MAX_CRAFT_GRID_WIDTH - width; x++) {
			for (int y = 0; y <= ShapedOreRecipe.MAX_CRAFT_GRID_HEIGHT - height; ++y) {
				if (checkMatch(inv, x, y)) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean checkMatch(InventoryCrafting inv, int startX, int startY) {
		for (int x = 0; x < ShapedOreRecipe.MAX_CRAFT_GRID_WIDTH; x++) {
			for (int y = 0; y < ShapedOreRecipe.MAX_CRAFT_GRID_HEIGHT; y++) {
				int subX = x - startX;
				int subY = y - startY;
				Ingredient target = Ingredient.EMPTY;

				if (subX >= 0 && subY >= 0 && subX < width && subY < height) {
					target = getIngredients().get(subX + subY * width);
				}

				ItemStack itemstack = inv.getStackInRowAndColumn(x, y);

				if (!itemstack.isEmpty()) {
					for (ItemStack ingredient : OreDictionary.getOres("plankWood")) {
						if (itemstack.getItem() == ingredient.getItem() && (itemstack.getMetadata() == ingredient.getMetadata() || ingredient.getMetadata() == OreDictionary.WILDCARD_VALUE)) {
							this.input = itemstack;
						}
					}
				}

				if (!target.apply(inv.getStackInRowAndColumn(x, y))) {
					return false;
				}
			}
		}

		return true;
	}

	public NonNullList<Ingredient> getIngredients() {
		return ingredients;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack out = getRecipeOutput();
		for (int i = 0; i < inv.getHeight(); ++i) {
			for (int j = 0; j < inv.getWidth(); ++j) {
				ItemStack itemstack = inv.getStackInRowAndColumn(j, i);
				if (!itemstack.isEmpty() && (itemstack.getItem() == input.getItem() || itemstack.getMetadata() == input.getMetadata())) {
					return addNBT(input, out);
				}
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return new ItemStack(output, count, 0);
	}

	public static ItemStack addNBT(ItemStack input, ItemStack output) {
		for (CustomMaterial material : CustomMaterial.getList("wood")){
			if (material instanceof WoodMaterial) {
				Item materialInputItem = ForgeRegistries.ITEMS.getValue(((WoodMaterial) material).inputItemResourceLocation);
				if (materialInputItem != null){
					ItemStack materialInputItemStack = new ItemStack(materialInputItem, 1, ((WoodMaterial) material).inputItemMeta);
					if (input.isItemEqual(materialInputItemStack)){
						CustomMaterial.addMaterial(output, CustomToolHelper.slot_main, material.name);
						return output;
					}
				}
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canFit(int width, int height) {
		return width * height >= 2;
	}

	public static class Factory implements IRecipeFactory {
		@Override
		public IRecipe parse(JsonContext context, JsonObject json) {

			ShapedOreRecipe recipe = ShapedOreRecipe.factory(context, json);

			CraftingHelper.ShapedPrimer primer = new CraftingHelper.ShapedPrimer();
			primer.height = recipe.getRecipeHeight();
			primer.width = recipe.getRecipeWidth();
			primer.input = recipe.getIngredients();
			primer.mirrored = false;

			final ItemStack result = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);
			final int count = JsonUtils.getInt(json, "count");

			return new TimberDynamicRecipes(result.getItem(), count, primer);
		}
	}
}
