package minefantasy.mf2.api.crafting.anvil;

import minefantasy.mf2.api.heating.Heatable;
import minefantasy.mf2.api.heating.IHotItem;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.api.rpg.Skill;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

/**
 * 
 * @author AnonymousProductions
 *
 */
public class CustomToolRecipe implements IAnvilRecipe
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

    public CustomToolRecipe(int wdth, int heit, ItemStack[] inputs, ItemStack output, String toolType, int time, int hammer, int anvi, float exp, boolean hot, String research, Skill skill)
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
	public boolean matches(AnvilCraftMatrix matrix)
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
    protected boolean checkMatch(AnvilCraftMatrix matrix, int x, int y, boolean b)
    {
    	String wood = null;
    	String metal = null;
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
                	String component_wood = CustomToolHelper.getComponentMaterial(inputItem, "wood");
                	String component_metal = CustomToolHelper.getComponentMaterial(inputItem, "metal");
               
                	if(component_metal != null)//CHECK CUSTOM METAL
                	{
                		if(metal == null)
                		{
                			metal = component_metal;
                		}
                		else
                		{
                			if(!metal.equalsIgnoreCase(component_metal))
                			{
                				return false;
                			}
                		}
                	}
                	
                	if(component_wood != null)//CHECK CUSTOM WOOD
                	{
                		if(wood == null)
                		{
                			wood = component_wood;
                		}
                		else
                		{
                			if(!wood.equalsIgnoreCase(component_wood))
                			{
                				return false;
                			}
                		}
                	}
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
    	if(!modifyTiers(matrix, metal))
    	{
    		modifyTiers(matrix, wood);
    	}
        
        return true;
    }

    private boolean modifyTiers(AnvilCraftMatrix matrix, String tier) 
    {
    	CustomMaterial material = CustomMaterial.getMaterial(tier);
    	if(material != null)
    	{
    		matrix.modifyTier(material.crafterTier, material.crafterAnvilTier, (int)(recipeTime * material.craftTimeModifier));
    		return true;
    	}
    	return false;
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
	public ItemStack getCraftingResult(AnvilCraftMatrix matrix)
    {
        ItemStack result = recipeOutput.copy();
        String wood = null;
        String metal = null;
        for(int i = 0; i < matrix.getSizeInventory(); i++)
        {
        	ItemStack item = matrix.getStackInSlot(i);
        	String component_wood = CustomToolHelper.getComponentMaterial(item, "wood");
        	String component_metal = CustomToolHelper.getComponentMaterial(item, "metal");
        	if(wood == null && component_wood != null){wood = component_wood;}
        	if(metal == null && component_metal != null){metal = component_metal;}
        }
        if(metal != null)
        {
        	CustomMaterial.addMaterial(result, CustomToolHelper.slot_main, metal);
        }
        if(wood != null)
        {
        	CustomMaterial.addMaterial(result, CustomToolHelper.slot_haft, wood);
        }
        return result;
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
	
	@Override
	public boolean useCustomTiers() 
	{
		return true;
	}
}
