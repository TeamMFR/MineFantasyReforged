package minefantasy.mf2.api.crafting.carpenter;

import minefantasy.mf2.api.rpg.Skill;
import net.minecraft.item.ItemStack;

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

    float getExperiance();

    int getAnvil();

    boolean outputHot();

    String getToolType();

    String getSound();

    ItemStack getRecipeOutput();

    String getResearch();

    Skill getSkill();
}
