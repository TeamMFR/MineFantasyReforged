package minefantasy.mfr.recipe;

import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.rpg.Skill;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AnonymousProductions
 */
public class ShapelessCarpenterRecipes implements ICarpenterRecipe {
    public static final int GLOBAL_WIDTH = 4;
    public static final int GLOBAL_HEIGHT = 4;
    /**
     * Is a List of ItemStack that composes the recipe.
     */
    public final List<ItemStack> recipeItems;
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
    private final SoundEvent soundOfCraft;
    private final String research;
    private final Skill skillUsed;

    public ShapelessCarpenterRecipes(ItemStack output, String toolType, float exp, int hammer, int anvil, int time, List<ItemStack> components, boolean hot, SoundEvent sound, String research, Skill skill) {
        this.research = research;
        this.outputHot = hot;
        this.recipeOutput = output;
        this.blockTier = anvil;
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
        ArrayList<ItemStack> itemStacks = new ArrayList<>(this.recipeItems);

        for (int i = 0; i <= GLOBAL_WIDTH; ++i) {
            for (int j = 0; j <= GLOBAL_HEIGHT; ++j) {
                ItemStack inputItem = par1InventoryCrafting.getStackInRowAndColumn(j, i);

                if (!inputItem.isEmpty()) {
                    boolean check = false;

                    for (ItemStack recipeItem : itemStacks) {
                        if (inputItem.isEmpty()) {
                            return false;
                        }
                        if (!CustomToolHelper.doesMatchForRecipe(recipeItem, inputItem)) {
                            return false;
                        }
                        if (inputItem.getItem() == recipeItem.getItem() && (recipeItem.getItemDamage() == OreDictionary.WILDCARD_VALUE || inputItem.getItemDamage() == recipeItem.getItemDamage())) {
                            check = true;
                            itemStacks.remove(recipeItem);
                            break;
                        }
                    }

                    if (!check) {
                        return false;
                    }
                }
            }
        }

        return itemStacks.isEmpty();
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
    public float getExperience() {
        return this.recipeExperiance;
    }

    @Override
    public int getAnvil() {
        return this.blockTier;
    }

    @Override
    public int getBlockTier() {
        return blockTier;
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
}
