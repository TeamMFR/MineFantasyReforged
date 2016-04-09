package minefantasy.mf2.api.crafting.anvil;

import minefantasy.mf2.api.heating.Heatable;
import minefantasy.mf2.api.heating.IHotItem;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.api.rpg.Skill;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

/**
 * 
 * @author AnonymousProductions
 *
 */
public class ShapedAnvilRecipes implements IAnvilRecipe
{
    /** How many horizontal slots this recipe is wide. */
	public int recipeWidth;
    
	public final int recipeHammer;

	public final boolean outputHot;
    /** How many vertical slots this recipe uses. */
	public int recipeHeight;
    
    /** The Anvil needed to craft */
	public final int anvil;

    /** Is a array of ItemStack that composes the recipe. */
	public ItemStack[] recipeItems;

    /** Is the ItemStack that you get when craft the recipe. */
	public ItemStack recipeOutput;
    
	public final int recipeTime;
	public final float recipeExperiance;
	public final String toolType;
	public final String research;
	public final Skill skillUsed;

    public ShapedAnvilRecipes(int wdth, int heit, ItemStack[] inputs, ItemStack output, String toolType, int time, int hammer, int anvi, float exp, boolean hot, String research, Skill skill)
    {
    	this.outputHot = hot;
        this.recipeWidth = wdth;
        this.anvil = anvi;
        this.recipeHeight = heit;
        this.recipeItems = inputs;
        this.recipeOutput = output;
        this.recipeTime = time;
        this.recipeHammer = hammer;
        this.recipeExperiance = exp;
        this.toolType = toolType;
        this.research = research;
        this.skillUsed = skill;
    }

    @Override
	public ItemStack getRecipeOutput()
    {
        return this.recipeOutput;
    }
    @Override
	public int getCraftTime()
    {
    	return recipeTime;
    }
    @Override
	public float getExperiance()
    {
    	return this.recipeExperiance;
    }
    @Override
	public int getRecipeHammer()
    {
    	return recipeHammer;
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    @Override
	public boolean matches(InventoryCrafting matrix)
    {
        for (int var2 = 0; var2 <= ShapelessAnvilRecipes.globalWidth - this.recipeWidth; ++var2)
        {
            for (int var3 = 0; var3 <= ShapelessAnvilRecipes.globalHeight - this.recipeHeight; ++var3)
            {
                if (this.checkMatch(matrix, var2, var3, true))
                {
                    return true;
                }

                if (this.checkMatch(matrix, var2, var3, false))
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks if the region of a crafting inventory is match for the recipe.
     */
    protected boolean checkMatch(InventoryCrafting matrix, int x, int y, boolean b)
    {
        for (int var5 = 0; var5 < ShapelessAnvilRecipes.globalWidth; ++var5)
        {
            for (int var6 = 0; var6 < ShapelessAnvilRecipes.globalHeight; ++var6)
            {
                int var7 = var5 - x;
                int var8 = var6 - y;
                ItemStack recipeItem = null;

                if (var7 >= 0 && var8 >= 0 && var7 < this.recipeWidth && var8 < this.recipeHeight)
                {
                    if (b)
                    {
                        recipeItem = this.recipeItems[this.recipeWidth - var7 - 1 + var8 * this.recipeWidth];
                    }
                    else
                    {
                        recipeItem = this.recipeItems[var7 + var8 * this.recipeWidth];
                    }
                }

                ItemStack inputItem = matrix.getStackInRowAndColumn(var5, var6);

                if (inputItem != null || recipeItem != null)
                {
                	//HEATING
                	if (Heatable.requiresHeating && Heatable.canHeatItem(inputItem)) 
                	{
						return false;
					}
                	if(!Heatable.isWorkable(inputItem))
                	{
                		return false;
                	}
					inputItem = getHotItem(inputItem);
					
                    if (inputItem == null && recipeItem != null || inputItem != null && recipeItem == null)
                    {
                        return false;
                    }
                    
                    if(inputItem == null)
                    {
                    	return false;
                    }

                    if (recipeItem.getItem() != inputItem.getItem())
                    {
                        return false;
                    }

                    if (recipeItem.getItemDamage() != OreDictionary.WILDCARD_VALUE && recipeItem.getItemDamage() != inputItem.getItemDamage())
                    {
                        return false;
                    }
                    if(!CustomToolHelper.doesMatchForRecipe(recipeItem, inputItem))
                    {
                    	return false;
                    }
                }
            }
        }

        return true;
    }

    protected ItemStack getHotItem(ItemStack item) 
    {
    	if(item == null)return null;
    	if(!(item.getItem() instanceof IHotItem))
    	{
    		return item;
    	}
    	
		ItemStack hotItem = Heatable.getItem(item);
		
		if (hotItem != null) 
		{
			return hotItem;
		}
		
		return item;
	}

	private static NBTTagCompound getNBT(ItemStack item) {
		if (!item.hasTagCompound())
			item.setTagCompound(new NBTTagCompound());
		return item.getTagCompound();
	}
	
	/**
     * Returns an Item that is the result of this recipe
     */
    @Override
	public ItemStack getCraftingResult(InventoryCrafting matrix)
    {
        return recipeOutput.copy();
    }

    /**
     * Returns the size of the recipe area
     */
    @Override
	public int getRecipeSize()
    {
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
	public String getToolType()
	{
		return toolType;
	}

	@Override
	public String getResearch()
	{
		return research;
	}

	@Override
	public Skill getSkill() 
	{
		return skillUsed;
	}
}
