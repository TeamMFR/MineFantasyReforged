package minefantasy.mf2.api.crafting.anvil;

import minefantasy.mf2.api.heating.Heatable;
import minefantasy.mf2.api.heating.IHotItem;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.rpg.Skill;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

/**
 * @author AnonymousProductions
 */
public class ShapedAnvilRecipes implements IAnvilRecipe {
    public final int recipeHammer;
    public final boolean outputHot;
    /**
     * The Anvil needed to craft
     */
    public final int anvil;
    public final int recipeTime;
    public final String toolType;
    public final String research;
    public final Skill skillUsed;
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

    public ShapedAnvilRecipes(int wdth, int heit, ItemStack[] inputs, ItemStack output, String toolType, int time,
                              int hammer, int anvi, boolean hot, String research, Skill skill) {
        this.outputHot = hot;
        this.recipeWidth = wdth;
        this.anvil = anvi;
        this.recipeHeight = heit;
        this.recipeItems = inputs;
        this.recipeOutput = output;
        this.recipeTime = time;
        this.recipeHammer = hammer;
        this.toolType = toolType;
        this.research = research;
        this.skillUsed = skill;
    }

    private static NBTTagCompound getNBT(ItemStack item) {
        if (!item.hasTagCompound())
            item.setTagCompound(new NBTTagCompound());
        return item.getTagCompound();
    }

    @Override
    public ItemStack getRecipeOutput() {
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
     * Checks if the region of a crafting inventory is match for the recipe.
     */
    protected boolean checkMatch(AnvilCraftMatrix matrix, int x, int y, boolean b) {
        for (int matrixX = 0; matrixX < ShapelessAnvilRecipes.globalWidth; ++matrixX) {
            for (int matrixY = 0; matrixY < ShapelessAnvilRecipes.globalHeight; ++matrixY) {
                int recipeX = matrixX - x;
                int recipeY = matrixY - y;
                ItemStack recipeItem = null;

                if (recipeX >= 0 && recipeY >= 0 && recipeX < this.recipeWidth && recipeY < this.recipeHeight) {
                    if (b) {
                        recipeItem = this.recipeItems[this.recipeWidth - recipeX - 1 + recipeY * this.recipeWidth];
                    } else {
                        recipeItem = this.recipeItems[recipeX + recipeY * this.recipeWidth];
                    }
                }

                ItemStack inputItem = matrix.getStackInRowAndColumn(matrixX, matrixY);

                if (inputItem != null || recipeItem != null) {
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

                    if (recipeItem.getItemDamage() != OreDictionary.WILDCARD_VALUE
                            && recipeItem.getItemDamage() != inputItem.getItemDamage()) {
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
        if (item == null)
            return null;
        if (!(item.getItem() instanceof IHotItem)) {
            return item;
        }

        ItemStack hotItem = Heatable.getItem(item);

        if (hotItem != null) {
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
    public int getAnvil() {
        return anvil;
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
}
