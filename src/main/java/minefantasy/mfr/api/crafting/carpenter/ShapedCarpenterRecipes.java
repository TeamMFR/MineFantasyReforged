package minefantasy.mfr.api.crafting.carpenter;

import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.rpg.Skill;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;

/**
 * @author AnonymousProductions
 */
public class ShapedCarpenterRecipes implements IRecipe, ICarpenterRecipe {

	/**
	 * How many horizontal slots this recipe is wide.
	 */
	public int recipeWidth;
	/**
	 * How many vertical slots this recipe uses.
	 */
	public int recipeHeight;

	public final int recipeHammer;
	public final boolean outputHot;
	/**
	 * The Block Tier needed to craft
	 */
	public final int blockTier;
	public final int recipeTime;
	public final float recipeExperience;
	public final String toolType;
	public final SoundEvent soundOfCraft;
	public final String research;
	public final Skill skillUsed;
	/**
	 * Is a array of ItemStack that composes the recipe.
	 */
	public ItemStack[] recipeItems;
	/**
	 * Is the ItemStack that you get when craft the recipe.
	 */
	public ItemStack recipeOutput;

	public ShapedCarpenterRecipes(int width, int height, ItemStack[] inputs, ItemStack output, String toolType, int time,
			int hammer, int anvil, float exp, boolean hot, SoundEvent sound, String research, Skill skill) {
		this.research = research;
		this.outputHot = hot;
		this.recipeWidth = width;
		this.blockTier = anvil;
		this.recipeHeight = height;
		this.recipeItems = inputs;
		this.recipeOutput = output;
		this.recipeTime = time;
		this.recipeHammer = hammer;
		this.recipeExperience = exp;
		this.toolType = toolType;
		this.soundOfCraft = sound;
		this.skillUsed = skill;
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		return this.getRecipeOutput().copy();
	}

	/**
	 * Used to determine if this recipe can fit in a grid of the given width/height
	 */
	@Override
	public boolean canFit(int width, int height) {
		return width >= this.recipeWidth && height >= this.recipeHeight;
	}

	/**
	 * Returns the resulting ItemStack of this recipe
	 */
	@Override
	public ItemStack getRecipeOutput() {
		return this.recipeOutput;
	}

	@Override
	public int getCraftTime() {
		return recipeTime;
	}

	@Override
	public float getExperience() {
		return this.recipeExperience;
	}

	@Override
	public int getRecipeHammer() {
		return recipeHammer;
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
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(CarpenterCraftMatrix matrix) {
		for (int var2 = 0; var2 <= ShapelessCarpenterRecipes.GLOBAL_WIDTH - this.recipeWidth; ++var2) {
			for (int var3 = 0; var3 <= ShapelessCarpenterRecipes.GLOBAL_HEIGHT - this.recipeHeight; ++var3) {
				if (this.checkMatch(matrix, var2, var3, true)) {
					return true;
				}

				if (this.checkMatch(matrix, var2, var3, false)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Checks if the region of a crafting inventory is match for the recipe.
	 */
	private boolean checkMatch(InventoryCrafting matrix, int x, int y, boolean b) {
		for (int var5 = 0; var5 < ShapelessCarpenterRecipes.GLOBAL_WIDTH; ++var5) {
			for (int var6 = 0; var6 < ShapelessCarpenterRecipes.GLOBAL_HEIGHT; ++var6) {
				int var7 = var5 - x;
				int var8 = var6 - y;
				ItemStack recipeItem = null;

				if (var7 >= 0 && var8 >= 0 && var7 < this.recipeWidth && var8 < this.recipeHeight) {
					if (b) {
						recipeItem = this.recipeItems[this.recipeWidth - var7 - 1 + var8 * this.recipeWidth];
					} else {
						recipeItem = this.recipeItems[var7 + var8 * this.recipeWidth];
					}
				}

				ItemStack inputItem = matrix.getStackInRowAndColumn(var5, var6);

				boolean i = ((inputItem != null && !inputItem.isEmpty()));
				boolean j = recipeItem != null && !recipeItem.isEmpty();
				if (i || j) {
					if (inputItem == null && recipeItem != null || inputItem != null && recipeItem == null) {
						return false;
					}

					if (inputItem == null) {
						return false;
					}

					if (recipeItem.getItem() != inputItem.getItem()) {
						return false;
					}
					if (!CustomToolHelper.doesMatchForRecipe(recipeItem, inputItem)) {
						return false;
					}
					// false if not using the wildcard value for item data AND the required item's data doesn't match with the input item
					if (recipeItem.getItemDamage() != OreDictionary.WILDCARD_VALUE && recipeItem.getItemDamage() != inputItem.getItemDamage()) {
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack getCraftingResult(CarpenterCraftMatrix matrix) {
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
	public int getAnvil() {
		return blockTier;
	}

	@Override
	public boolean outputHot() {
		return this.outputHot;
	}

	@Override
	public int getBlockTier() {
		return blockTier;
	}

	@Override
	public String getToolType() {
		return toolType;
	}

	@Override
	public SoundEvent getSound() {
		return soundOfCraft;
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
