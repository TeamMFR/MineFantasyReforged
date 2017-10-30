package minefantasy.mf2.api.crafting.carpenter;

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
public class ShapelessCarpenterRecipes implements ICarpenterRecipe {
    public static final int globalWidth = 4;
    public static final int globalHeight = 4;
    /**
     * Is a List of ItemStack that composes the recipe.
     */
    public final List recipeItems;
    /**
     * Is the ItemStack that you get when craft the recipe.
     */
    private final ItemStack recipeOutput;
    private final boolean outputHot;
    private final int recipeHammer;

    /**
     * The Block Tier Required
     */
    private final int blockTier;

    private final int recipeTime;
    private final float recipeExperiance;
    private final String toolType;
    private final String soundOfCraft;
    private final String research;
    private final Skill skillUsed;

    public ShapelessCarpenterRecipes(ItemStack output, String toolType, float exp, int hammer, int anvi, int time,
                                     List components, boolean hot, String sound, String research, Skill skill) {
        this.research = research;
        this.outputHot = hot;
        this.recipeOutput = output;
        this.blockTier = anvi;
        this.recipeItems = components;
        this.recipeHammer = hammer;
        this.recipeTime = time;
        this.recipeExperiance = exp;
        this.toolType = toolType;
        this.soundOfCraft = sound;
        this.skillUsed = skill;
    }

    public static int getTemp(ItemStack item) {
        NBTTagCompound tag = getNBT(item);

        if (tag.hasKey("MFtemp"))
            return tag.getInteger("MFtemp");

        return 0;
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
    public boolean matches(CarpenterCraftMatrix par1InventoryCrafting) {
        ArrayList var2 = new ArrayList(this.recipeItems);

        for (int var3 = 0; var3 <= globalWidth; ++var3) {
            for (int var4 = 0; var4 <= globalHeight; ++var4) {
                ItemStack inputItem = par1InventoryCrafting.getStackInRowAndColumn(var4, var3);

                if (inputItem != null) {
                    boolean var6 = false;
                    Iterator var7 = var2.iterator();

                    while (var7.hasNext()) {
                        ItemStack recipeItem = (ItemStack) var7.next();

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

    /**
     * Returns an Item that is the result of this recipe
     */
    @Override
    public ItemStack getCraftingResult(CarpenterCraftMatrix par1InventoryCrafting) {
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
    public float getExperiance() {
        return this.recipeExperiance;
    }

    @Override
    public int getAnvil() {
        return this.blockTier;
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
    public String getSound() {
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
}
