package minefantasy.mf2.api.crafting.anvil;

import minefantasy.mf2.api.heating.Heatable;
import minefantasy.mf2.api.heating.IHotItem;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.rpg.Skill;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author AnonymousProductions
 */
public class ShapelessAnvilRecipes implements IAnvilRecipe {
    public static final int globalWidth = 6;
    public static final int globalHeight = 4;

    /**
     * Is the ItemStack that you get when craft the recipe.
     */
    public final ItemStack recipeOutput;

    public final boolean outputHot;
    /**
     * Is a List of ItemStack that composes the recipe.
     */
    public final List recipeItems;
    /**
     * The anvil Required
     */
    public final int anvil;
    public final int recipeTime;
    public final String toolType;
    public final String research;
    public final Skill skillUsed;
    private final int recipeHammer;

    public ShapelessAnvilRecipes(ItemStack output, String toolType, int hammer, int anvi, int time,
                                 List components, boolean hot, String research, Skill skill) {
        this.outputHot = hot;
        this.recipeOutput = output;
        this.anvil = anvi;
        this.recipeItems = components;
        this.recipeHammer = hammer;
        this.recipeTime = time;
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

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    @Override
    public boolean matches(AnvilCraftMatrix matrix) {
        ArrayList var2 = new ArrayList(this.recipeItems);

        for (int var3 = 0; var3 <= globalWidth; ++var3) {
            for (int var4 = 0; var4 <= globalHeight; ++var4) {
                ItemStack inputItem = matrix.getStackInRowAndColumn(var4, var3);

                if (inputItem != null) {
                    boolean var6 = false;
                    Iterator var7 = var2.iterator();

                    while (var7.hasNext()) {
                        ItemStack recipeItem = (ItemStack) var7.next();

                        // HEATING
                        if (Heatable.requiresHeating && Heatable.canHeatItem(inputItem)) {
                            return false;
                        }
                        if (!Heatable.isWorkable(inputItem)) {
                            return false;
                        }
                        inputItem = getHotItem(inputItem);

                        if (inputItem == null) {
                            return false;
                        }
                        if (!CustomToolHelper.doesMatchForRecipe(recipeItem, inputItem)) {
                            return false;
                        }
                        if (inputItem.getItem() == recipeItem.getItem()
                                && (recipeItem.getItemDamage() == OreDictionary.WILDCARD_VALUE
                                || inputItem.getItemDamage() == recipeItem.getItemDamage())) {
                            var6 = true;
                            var2.remove(recipeItem);
                            break;
                        }
                    }

                    if (!var6) {
                        return false;
                    }
                }
            }
        }

        return var2.isEmpty();
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
    public ItemStack getCraftingResult(AnvilCraftMatrix par1AnvilCraftMatrix) {
        return this.recipeOutput.copy();
    }

    /**
     * Returns the size of the recipe area
     */
    @Override
    public int getRecipeSize() {
        return this.recipeItems.size();
    }

    @Override
    public int getCraftTime() {
        return this.recipeTime;
    }

    @Override
    public int getRecipeHammer() {
        return this.recipeHammer;
    }

    @Override
    public int getAnvil() {
        return this.anvil;
    }

    @Override
    public boolean outputHot() {
        return outputHot;
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
