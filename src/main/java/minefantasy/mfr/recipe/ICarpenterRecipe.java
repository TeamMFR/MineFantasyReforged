package minefantasy.mfr.recipe;

import minefantasy.mfr.constants.Skill;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;

/**
 * @author AnonymousProductions
 */
public interface ICarpenterRecipe {
	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	boolean matches(CarpenterCraftMatrix var1);

	/**
	 * Returns an Item that is the result of this recipe
	 */
	ItemStack getCraftingResult(CarpenterCraftMatrix var1);

	int getCraftTime();

	/**
	 * Returns the size of the recipe area
	 */
	int getRecipeSize();

	int getRecipeHammer();

	float getExperience();

	int getAnvil();

	int getBlockTier();

	boolean outputHot();

	String getToolType();

	SoundEvent getSound();

	ItemStack getCarpenterRecipeOutput();

	String getResearch();

	Skill getSkill();
}
