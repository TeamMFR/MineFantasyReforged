package minefantasy.mfr.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import minefantasy.mfr.init.MineFantasyItems;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;

import javax.annotation.Nonnull;

@SuppressWarnings("unused")
public class RecipeSyringe extends ShapelessRecipes {
	public RecipeSyringe(String group, NonNullList<Ingredient> ingredients, ItemStack result) {
		super(group, result, ingredients);
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(InventoryCrafting matrix, @Nonnull World world) {
		ItemStack syringe = ItemStack.EMPTY;
		ItemStack filler = ItemStack.EMPTY;

		for (int i = 0; i < matrix.getSizeInventory(); ++i) {
			ItemStack itemstack1 = matrix.getStackInSlot(i);

			if (!itemstack1.isEmpty()) {
				if (itemstack1.getItem() == MineFantasyItems.SYRINGE_EMPTY) {
					syringe = itemstack1;
				} else if (itemstack1.getItem() instanceof ItemPotion) {
					ItemPotion potion = (ItemPotion) itemstack1.getItem();

					if (potion == Items.SPLASH_POTION) {
						return false;
					}

					filler = itemstack1;
				}
			}
		}

		return !syringe.isEmpty() && !filler.isEmpty();
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Nonnull
	@Override
	public ItemStack getCraftingResult(InventoryCrafting matrix) {
		ItemStack syringe = ItemStack.EMPTY;
		ItemStack filler = ItemStack.EMPTY;

		for (int i = 0; i < matrix.getSizeInventory(); ++i) {
			ItemStack itemstack1 = matrix.getStackInSlot(i);

			if (!itemstack1.isEmpty()) {
				if (itemstack1.getItem() == MineFantasyItems.SYRINGE_EMPTY) {
					syringe = itemstack1;
				} else if (itemstack1.getItem() instanceof ItemPotion) {
					if (itemstack1.getItem() == Items.SPLASH_POTION) {
						return ItemStack.EMPTY;
					}

					filler = itemstack1;
				}
			}
		}
		if (!syringe.isEmpty() && !filler.isEmpty()) {
			return PotionUtils.addPotionToItemStack(new ItemStack(MineFantasyItems.SYRINGE, 1), PotionUtils.getPotionFromItem(filler));
		}
		return ItemStack.EMPTY;
	}

	public static class Factory implements IRecipeFactory {
		@Override
		public IRecipe parse(JsonContext context, JsonObject json) {
			String group = JsonUtils.getString(json, "group", "");

			NonNullList<Ingredient> ingredients = NonNullList.create();
			for (JsonElement ele : JsonUtils.getJsonArray(json, "ingredients"))
				ingredients.add(CraftingHelper.getIngredient(ele, context));

			if (ingredients.isEmpty())
				throw new JsonParseException("No ingredients for shapeless recipe");

			ItemStack result = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);
			return new RecipeSyringe(group, ingredients, result);
		}
	}
}