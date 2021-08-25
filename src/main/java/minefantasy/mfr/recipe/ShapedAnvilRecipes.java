package minefantasy.mfr.recipe;

import minefantasy.mfr.api.heating.Heatable;
import minefantasy.mfr.api.heating.IHotItem;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;

/**
 * @author AnonymousProductions
 */
public class ShapedAnvilRecipes implements IRecipe, IAnvilRecipe {
	/**
	 * How many horizontal slots this recipe is wide.
	 */
	public int recipeWidth;
	/**
	 * How many vertical slots this recipe uses.
	 */
	public int recipeHeight;
	/**
	 * Is a array of ItemStack that composes the recipe.
	 */
	public ItemStack[] recipeItems;
	/**
	 * Is the ItemStack that you get when craft the recipe.
	 */
	public ItemStack recipeOutput;

	public final int recipeHammer;
	public final boolean outputHot;
	/**
	 * The Anvil needed to craft
	 */
	public final int anvilTier;
	public final int recipeTime;
	public final String toolType;
	public final String research;
	public final Skill skillUsed;

	public ShapedAnvilRecipes(int width, int height, ItemStack[] inputs, ItemStack output, String toolType, int time, int hammer, int anvil, boolean hot, String research, Skill skill) {
		this.outputHot = hot;
		this.recipeWidth = width;
		this.anvilTier = anvil;
		this.recipeHeight = height;
		this.recipeItems = inputs;
		this.recipeOutput = output;
		this.recipeTime = time;
		this.recipeHammer = hammer;
		this.toolType = toolType;
		this.research = research;
		this.skillUsed = skill;
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		return this.getAnvilRecipeOutput().copy();
	}

	/**
	 * Used to determine if this recipe can fit in a grid of the given width/height
	 */
	@Override
	public boolean canFit(int width, int height) {
		return width >= this.recipeWidth && height >= this.recipeHeight;
	}

	@Override
	public ItemStack getRecipeOutput(){
		return this.recipeOutput;
	}

	@Override
	public ItemStack getAnvilRecipeOutput() {
		return this.recipeOutput;
	}

	@Override
	public int getCraftTime() {
		return recipeTime;
	}

	@Override
	public int getRecipeHammer() {
		return recipeHammer;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(AnvilCraftMatrix matrix) {
		for (int x = 0; x <= ShapelessAnvilRecipes.globalWidth - this.recipeWidth; ++x) {
			for (int y = 0; y <= ShapelessAnvilRecipes.globalHeight - this.recipeHeight; ++y) {
				if (this.checkMatch(matrix, x, y, true) || this.checkMatch(matrix, x, y, false)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		for (int i = 0; i <= inv.getWidth() - this.recipeWidth; ++i) {
			for (int j = 0; j <= inv.getHeight() - this.recipeHeight; ++j) {
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
	protected boolean checkMatch(InventoryCrafting matrix, int x, int y, boolean b) {
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

				if ((inputItem != null && !inputItem.isEmpty()) || recipeItem != null && !recipeItem.isEmpty()) {
					// HEATING
					if (Heatable.requiresHeating && Heatable.canHeatItem(inputItem)) {
						return false;
					}
					if (!Heatable.isWorkable(inputItem)) {
						return false;
					}
					inputItem = getHotItem(inputItem);

					if (inputItem == null && recipeItem != null || inputItem != null && recipeItem == null) {
						return false;
					}

					if (inputItem == null) {
						return false;
					}

					if (recipeItem.getItem() != inputItem.getItem()) {
						return false;
					}

					if (recipeItem.getItemDamage() != OreDictionary.WILDCARD_VALUE && recipeItem.getItemDamage() != inputItem.getItemDamage()) {
						if (recipeItem.getItem() == MineFantasyItems.ENGIN_ANVIL_TOOLS){
							break;
						}
						return false;
					}
					if (!CustomToolHelper.doesMatchForRecipe(recipeItem, inputItem)) {
						return false;
					}
				}
			}
		}

		return true;
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
	public ItemStack getCraftingResult(AnvilCraftMatrix matrix) {
		return recipeOutput.copy();
	}

	/**
	 * Returns the size of the recipe area
	 */
	@Override
	public int getRecipeSize() {
		return this.recipeWidth * this.recipeHeight;
	}

	@Override
	public int getAnvilTier() {
		return anvilTier;
	}

	@Override
	public boolean outputHot() {
		return this.outputHot;
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

	@Override
	public IRecipe setRegistryName(ResourceLocation name) {
		return null;
	}

	@Nullable
	@Override
	public ResourceLocation getRegistryName() {
		return null;
	}

	@Override
	public Class<IRecipe> getRegistryType() {
		return null;
	}
}
