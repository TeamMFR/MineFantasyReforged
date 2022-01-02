package minefantasy.mfr.recipe;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import minefantasy.mfr.item.ItemArmourMFR;
import minefantasy.mfr.util.RecipeHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapedOreRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings("unused")
public class RecipeArmourDyeMF extends ShapedOreRecipe {

	public RecipeArmourDyeMF(@Nullable final ResourceLocation group, final ItemStack result, final CraftingHelper.ShapedPrimer primer) {
		super(group, result, primer);
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	public boolean matches(InventoryCrafting inv, World worldIn) {
		ItemStack itemstack = ItemStack.EMPTY;
		List<ItemStack> list = Lists.newArrayList();

		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack stackInSlot = inv.getStackInSlot(i);

			if (!stackInSlot.isEmpty()) {
				if (stackInSlot.getItem() instanceof ItemArmourMFR) {
					ItemArmourMFR itemArmourMFR = (ItemArmourMFR) stackInSlot.getItem();

					if (!itemArmourMFR.canColour() || !itemstack.isEmpty()) {
						return false;
					}

					itemstack = stackInSlot;
				} else {
					if (!net.minecraftforge.oredict.DyeUtils.isDye(stackInSlot)) {
						return false;
					}

					list.add(stackInSlot);
				}
			}
		}

		return !itemstack.isEmpty() && !list.isEmpty();
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	public ItemStack getCraftingResult(InventoryCrafting matrix) {
		ItemStack itemstack = ItemStack.EMPTY;
		int[] aint = new int[3];
		int i = 0;
		int j = 0;
		ItemArmourMFR itemarmor = null;

		for (int k = 0; k < matrix.getSizeInventory(); ++k) {
			ItemStack stackInSlot = matrix.getStackInSlot(k);

			if (!stackInSlot.isEmpty()) {
				if (stackInSlot.getItem() instanceof ItemArmor) {
					itemarmor = (ItemArmourMFR) stackInSlot.getItem();

					if (!itemarmor.canColour() || !itemstack.isEmpty()) {
						return ItemStack.EMPTY;
					}

					itemstack = stackInSlot.copy();
					itemstack.setCount(1);

					if (itemarmor.hasColor(stackInSlot)) {
						int l = itemarmor.getColor(itemstack);
						float f = (float) (l >> 16 & 255) / 255.0F;
						float f1 = (float) (l >> 8 & 255) / 255.0F;
						float f2 = (float) (l & 255) / 255.0F;
						i = (int) ((float) i + Math.max(f, Math.max(f1, f2)) * 255.0F);
						aint[0] = (int) ((float) aint[0] + f * 255.0F);
						aint[1] = (int) ((float) aint[1] + f1 * 255.0F);
						aint[2] = (int) ((float) aint[2] + f2 * 255.0F);
						++j;
					}
				} else {
					if (!net.minecraftforge.oredict.DyeUtils.isDye(stackInSlot)) {
						return ItemStack.EMPTY;
					}

					float[] afloat = net.minecraftforge.oredict.DyeUtils.colorFromStack(stackInSlot).get().getColorComponentValues();
					int l1 = (int) (afloat[0] * 255.0F);
					int i2 = (int) (afloat[1] * 255.0F);
					int j2 = (int) (afloat[2] * 255.0F);
					i += Math.max(l1, Math.max(i2, j2));
					aint[0] += l1;
					aint[1] += i2;
					aint[2] += j2;
					++j;
				}
			}
		}

		if (itemarmor == null) {
			return ItemStack.EMPTY;
		} else {
			int i1 = aint[0] / j;
			int j1 = aint[1] / j;
			int k1 = aint[2] / j;
			float f3 = (float) i / (float) j;
			float f4 = (float) Math.max(i1, Math.max(j1, k1));
			i1 = (int) ((float) i1 * f3 / f4);
			j1 = (int) ((float) j1 * f3 / f4);
			k1 = (int) ((float) k1 * f3 / f4);
			int k2 = (i1 << 8) + j1;
			k2 = (k2 << 8) + k1;
			itemarmor.setColor(itemstack, k2);
			return itemstack;
		}
	}

	@Nonnull
	@Override
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean isDynamic() {
		return true;
	}

	@Override
	public boolean canFit(int width, int height) {
		return width >= 5 && height >= 5;
	}

	public static class Factory implements IRecipeFactory {
		@Override
		public IRecipe parse(JsonContext context, JsonObject json) {

			final String group = JsonUtils.getString(json, "group", "");
			final CraftingHelper.ShapedPrimer primer = RecipeHelper.parseShaped(context, json);
			final ItemStack result = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);

			return new RecipeArmourDyeMF(group.isEmpty() ? null : new ResourceLocation(group), result, primer);
		}
	}
}