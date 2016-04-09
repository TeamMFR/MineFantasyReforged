package minefantasy.mf2.api.helpers;

import java.util.List;

import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.item.list.ToolListMF;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CustomToolHelper 
{
	public static final String slot_main = "main_metal";
	public static final String slot_haft = "haft_wood";
	/**
	 * A bit of the new system, gets custom materials for the head
	 */
	public static CustomMaterial getCustomMetalMaterial(ItemStack item)
	{
		if(item == null)return null;
		
		CustomMaterial material = CustomMaterial.getMaterialFor(item, slot_main);
		if(material != null)
		{
			return material;
		}
		return null;
	}
	public static CustomMaterial getCustomWoodMaterial(ItemStack item)
	{
		if(item == null)return null;
		
		CustomMaterial material = CustomMaterial.getMaterialFor(item, slot_haft);
		if(material != null)
		{
			return material;
		}
		return null;
	}
	
	public static ItemStack construct(Item base, String main)
	{
		return construct(base,main,"OakWood");
	}
	
	public static ItemStack construct(Item base, String main, String haft)
	{
		ItemStack item = new ItemStack(base);
		CustomMaterial.addMaterial(item, slot_main, main.toLowerCase());
		if(haft != null)
		{
			CustomMaterial.addMaterial(item, slot_haft, haft.toLowerCase());
		}
		return item;
	}
	
	public static ItemStack constructSingleColoredLayer(Item base, String main)
	{
		return constructSingleColoredLayer(base, main, 1);
	}
	public static ItemStack constructSingleColoredLayer(Item base, String main, int stacksize)
	{
		ItemStack item = new ItemStack(base, stacksize);
		CustomMaterial.addMaterial(item, slot_main, main.toLowerCase());
		return item;
	}
	/**
	 * Gets the rarity for a custom item
	 * @param itemRarity is the default id
	 */
	public static EnumRarity getRarity(ItemStack item, int itemRarity)
	{
    	int lvl = itemRarity+1;
    	CustomMaterial material = CustomMaterial.getMaterialFor(item, slot_main);
		if(material != null)
		{
			lvl = material.rarityID+1;
		}
		
		if(item.isItemEnchanted())
		{
			if(lvl == 0)
			{
				lvl++;
			}
			lvl ++;
		}
		if(lvl >= rarity.length)
		{
			lvl = rarity.length-1;
		}
		return rarity[lvl];
	}
	public static EnumRarity poor = EnumHelper.addRarity("Poor", EnumChatFormatting.DARK_GRAY, "poor");
	public static EnumRarity[] rarity = new EnumRarity[]{poor, EnumRarity.common, EnumRarity.uncommon, EnumRarity.rare, EnumRarity.epic};
	/**
	 * Gets the max durability
	 * @param dura is the default dura
	 */
	public static int getMaxDamage(ItemStack stack, int dura)
	{
		CustomMaterial head = getCustomMetalMaterial(stack);
		CustomMaterial haft = getCustomWoodMaterial(stack);
		if(head != null)
		{
			dura = (int)(head.durability * 200);
		}
		if(haft != null)
		{
			dura += (int)(haft.durability * 50);//Hafts add a 1/5th to the durability
		}
		return ToolHelper.setDuraOnQuality(stack, dura);
	}
	
	/**
	 * Gets the colour for a layer
	 * @param base is default colour
	 * 0 is base
	 * 1 is haft
	 * 2 is detail
	 */
	public static int getColourFromItemStack(ItemStack item, int layer, int base)
    {
    	if(layer == 0)
    	{
    		CustomMaterial material = CustomMaterial.getMaterialFor(item, slot_main);
    		if(material != null)
    		{
    			return material.getColourInt();
    		}
    	}
    	if(layer == 1)
    	{
    		CustomMaterial material = CustomMaterial.getMaterialFor(item, slot_haft);
    		if(material != null)
    		{
    			return material.getColourInt();
    		}
    	}
    	return base;
    }
	
	public static float getWeightModifier(ItemStack item, float base)
	{
		CustomMaterial metal = getCustomMetalMaterial(item);
		CustomMaterial wood = getCustomWoodMaterial(item);
		
    	if(metal != null)
    	{
    		base = (metal.density/2.5F) * base;
    	}
    	if(wood != null)
    	{
    		base += (wood.density/2.5F);
    	}
		return base;
	}
	
	/**
	 * Gets the material modifier if it exists
	 * @param defaultModifier default if no material exists
	 */
	public static float getMeleeDamage(ItemStack item, float defaultModifier) 
    {
    	CustomMaterial custom = getCustomMetalMaterial(item);
    	if(custom != null)
    	{
    		return custom.sharpness;
    	}
    	return defaultModifier;
	}
	public static float getBowDamage(ItemStack item, float defaultModifier) 
    {
		CustomMaterial base = getCustomWoodMaterial(item);
		CustomMaterial joints = getCustomMetalMaterial(item);
		
    	if(base != null)
    	{
    		defaultModifier = base.flexibility;
    	}
    	if(joints != null)
    	{
    		defaultModifier *= joints.flexibility;
    	}
		return defaultModifier;
	}
	/**
	 * The total damage of a bow and arrow
	 */
	public static float getBaseDamages(ItemStack item, float defaultModifier)
	{
		CustomMaterial custom = getCustomMetalMaterial(item);
    	if(custom != null)
    	{
    		return getBaseDamage(custom.sharpness*custom.flexibility);
    	}
		return getBaseDamage(defaultModifier);
	}
	/**
	 * The damage a bow and arrow should do (same as a sword)
	 */
	public static float getBaseDamage(float modifier)
	{
		return 4F + modifier;
	}
	
	public static float getEfficiencyForHds(ItemStack item, float value, float mod) 
    {
    	CustomMaterial custom = getCustomMetalMaterial(item);
    	if(custom != null)
    	{
    		value = 2.0F + (custom.hardness*4F);//Efficiency starts at 2 and each point of sharpness adds 2
    	}
    	return ToolHelper.modifyDigOnQuality(item, value) * mod;
	}
	
	public static float getEfficiency(ItemStack item, float value, float mod) 
    {
    	CustomMaterial custom = getCustomMetalMaterial(item);
    	if(custom != null)
    	{
    		value = 2.0F + (custom.sharpness*2F);//Efficiency starts at 2 and each point of sharpness adds 2
    	}
    	return ToolHelper.modifyDigOnQuality(item, value) * mod;
	}
	
	public static int getCrafterTier(ItemStack item, int value) 
    {
    	CustomMaterial custom = getCustomMetalMaterial(item);
    	if(custom != null)
    	{
    		return custom.crafterTier;
    	}
    	return value;
	}
	
	public static int getHarvestLevel(ItemStack item, int value) 
    {
		if(value <= 0)
    	{
    		return value;//If its not effective
    	}
		
    	CustomMaterial custom = getCustomMetalMaterial(item);
    	if(custom != null)
    	{
    		if(custom.tier == 0)return 1;
    		if(custom.tier <= 2)return 2;
    		return Math.max(custom.tier, 2);
    	}
    	return value;
	}
	
	@SideOnly(Side.CLIENT)
	public static void addInformation(ItemStack item, List list) 
	{
		CustomMaterial haft = getCustomWoodMaterial(item);
		
		if(materialOnTooltip())
		{
			CustomMaterial main = getCustomMetalMaterial(item);
			if(main != null)
			{
				String matName = StatCollector.translateToLocal(StatCollector.translateToLocal("material."+main.name.toLowerCase() + ".name"));
	    		list.add(EnumChatFormatting.GOLD + matName);
			}
		}
		
    	if(haft != null)
    	{
    		String matName = StatCollector.translateToLocalFormatted("item.mod_haft.name", StatCollector.translateToLocal("material."+haft.name.toLowerCase() + ".name"));
    		list.add(EnumChatFormatting.GOLD + matName);
    	}
    	
	}
	
	/**
	 * Gets if the language puts tiers in the tooltip, leaving the name blank
	 * @return
	 */
	public static boolean materialOnTooltip()
	{
		String cfg = StatCollector.translateToLocal("languagecfg.tooltiptier");
		return cfg.equalsIgnoreCase("true");
	}
	@SideOnly(Side.CLIENT)
	public static void addBowInformation(ItemStack item, List list) 
	{
		
		CustomMaterial metals = getCustomMetalMaterial(item);
    	if(metals != null)
    	{
    		String matName = StatCollector.translateToLocalFormatted("item.mod_joint.name", StatCollector.translateToLocal("material."+metals.name.toLowerCase() + ".name"));
    		list.add(EnumChatFormatting.GOLD + matName);
    	}
    	
	}
	@SideOnly(Side.CLIENT)
	public static String getWoodenLocalisedName(ItemStack item, String unlocalName)
	{
		if(materialOnTooltip())
    	{
    		StatCollector.translateToLocal(unlocalName);
    	}
		
		CustomMaterial base = getCustomWoodMaterial(item);
		String name = "any";
    	if(base != null)
    	{
    		name = base.name.toLowerCase();
    	}
    	return StatCollector.translateToLocalFormatted(unlocalName, StatCollector.translateToLocal("material."+name + ".name"));
	}
	@SideOnly(Side.CLIENT)
	public static String getLocalisedName(ItemStack item, String unlocalName)
	{
		if(materialOnTooltip())
    	{
    		StatCollector.translateToLocal(unlocalName);
    	}
		
		CustomMaterial base = getCustomMetalMaterial(item);
		String name = "any";
    	if(base != null)
    	{
    		name = base.name.toLowerCase();
    	}
    	return StatCollector.translateToLocalFormatted(unlocalName, StatCollector.translateToLocal("material."+name + ".name"));
	}
	/**
	 * Checks if two items' materials match
	 */
	public static boolean doesMatchForRecipe(ItemStack recipeItem, ItemStack inputItem) 
	{
		return doesMainMatchForRecipe(recipeItem, inputItem) && doesHaftMatchForRecipe(recipeItem, inputItem);
	}
	public static boolean doesMainMatchForRecipe(ItemStack recipeItem, ItemStack inputItem) 
	{
		CustomMaterial recipeMat = CustomToolHelper.getCustomMetalMaterial(recipeItem);
        CustomMaterial inputMat = CustomToolHelper.getCustomMetalMaterial(inputItem);
        
        if(recipeMat == null)
        {
        	return true;
        }
        
        if (inputMat == null && recipeMat != null)
        {
            return false;
        }
        if(recipeMat != inputMat)
		{
        	return false;
    	}
		return true;
	}
	public static boolean doesHaftMatchForRecipe(ItemStack recipeItem, ItemStack inputItem) 
	{
		CustomMaterial recipeMat = CustomToolHelper.getCustomWoodMaterial(recipeItem);
        CustomMaterial inputMat = CustomToolHelper.getCustomWoodMaterial(inputItem);
        
        if(recipeMat == null)
        {
        	return true;
        }
        
        if (inputMat == null && recipeMat != null)
        {
            return false;
        }
        if(recipeMat != inputMat)
		{
        	return false;
    	}
		return true;
	}
	public static void addComponentString(ItemStack tool, List list, CustomMaterial base) 
	{
    	if(base != null)
    	{
    		float mass = base.density;
    		list.add(EnumChatFormatting.GOLD + base.getMaterialString());
    		list.add(CustomMaterial.getWeightString(mass));
    		
    		if(base.isHeatable())
    		{
	    		int maxTemp = base.getHeatableStats()[0];
	    		int beyondMax = base.getHeatableStats()[1];
	    		list.add(StatCollector.translateToLocalFormatted("materialtype.workable.name", maxTemp, beyondMax));
    		}
		}
	}
	public static int getBurnProperties(ItemStack fuel) 
	{
		CustomMaterial mat = CustomMaterial.getMaterialFor(fuel, slot_main);
		if(mat != null && mat.type.equalsIgnoreCase("wood"))
		{
			return 200 + (int) (100 * mat.density);
		}
		return 0;
	}
	public static String getReferenceName(ItemStack item) 
	{
		String dam = "any";
		int d = item.getItemDamage();
		if(d != OreDictionary.WILDCARD_VALUE)
		{
			dam = ""+d;
		}
		
		return getReferenceName(item, dam);
	}
	public static String getReferenceName(ItemStack item, String dam) 
	{
		String reference = item.getUnlocalizedName().toLowerCase() + "_@" + dam;
		
		CustomMaterial base = getCustomMetalMaterial(item);
		CustomMaterial haft = getCustomWoodMaterial(item);
		
		if(base != null)
		{
			reference += "_"+base.name.toLowerCase();
		}
		if(haft != null)
		{
			reference += "_"+haft.name.toLowerCase();
		}
		
		return reference;
	}
	public static boolean areToolsSame(ItemStack item1, ItemStack item2) 
	{
		CustomMaterial main1 = getCustomMetalMaterial(item1);
		CustomMaterial main2 = getCustomWoodMaterial(item2);
		CustomMaterial haft1 = getCustomMetalMaterial(item1);
		CustomMaterial haft2 = getCustomWoodMaterial(item2);
		if((main1 == null && main2 != null) || (main2 == null && main1 != null))return false;
		if((haft1 == null && haft2 != null) || (haft2 == null && haft1 != null))return false;
		
		if(main1 != null && main2 != null && main1 != main2)return false;
		if(haft1 != null && haft2 != null && haft1 != haft2)return false;
		
		return true;
	}
	
}
