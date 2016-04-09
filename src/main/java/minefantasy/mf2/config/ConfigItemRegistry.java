package minefantasy.mf2.config;

import java.util.Arrays;

import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.armour.ArmourDesign;
import minefantasy.mf2.api.armour.CustomArmourEntry;
import minefantasy.mf2.api.armour.CustomDamageRatioEntry;
import minefantasy.mf2.api.farming.CustomHoeEntry;
import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.item.Item;

public class ConfigItemRegistry extends ConfigurationBaseMF 
{
	public static final String CATEGORY_ARMOUR = "Armour List";
	
	public static String[] armourListAC = new String[0];
	
	
	public static final String CATEGORY_FARM = "Farming";
	
	public static String[] hoeList = new String[0];
	
	
	public static final String CATEGORY_WEPS = "Weapon Register";
	
	public static String[] customDamagerList = new String[0];
	public static String[] customDamagerEntityList = new String[0];

	@Override
	protected void loadConfig() 
	{
		//Weight
		
		String AAdesc = 
		"This will register items under a certain 'Design' calculating the variables itself.\n Each entry has it's own line:\n" +
		"Order itemid|Design|WeightModifier \n" +
		"The WeightModifier alters the weight for heavier or lighter materials keep it at 1.0 unless you have a special material (like mithril and adamamantium)\n" +
		"Designs can be any that are registered: MineFantasy designs are 'clothing', 'leather', 'mail', 'default'(that's just basic metal armour), and 'plate'\n" +
		"EXAMPLE (This is what vanilla gold is registered under) \n" +
		"314|default|2.0 \n"+
		"315|default|2.0 \n"+
		"316|default|2.0 \n"+
		"317|default|2.0 \n"+
		"The 2.0 means it is 2x heavier than other vanilla armours \n"+
		"This does not override existing MF armours \n";
		
		armourListAC = config.get(CATEGORY_ARMOUR, "Auto-Armour Registry", new String[0], AAdesc).getStringList();
	    Arrays.sort(armourListAC);
	    
	    //Hoes
	    
	    String hoeDesc = 
		"This Registers Hoe items to an efficiency level: (It uses the same variable as efficiency, you may need to find that out first, by default: it should be able to guess it:\n" +
		"Order itemid|efficicncy \n" +
		"Efficicney is a variable that goes into play with the failure chance, higher efficicnecy has easier tiling\n";
		
		hoeList = config.get(CATEGORY_FARM, "Hoe Registry", new String[0], hoeDesc).getStringList();
	    Arrays.sort(hoeList);
	    
	    //Weapons
	    
	    String damageDesc = 
	    "[Experimental] This registers weapons for the damage variable mechanics implemented by option. \n" +
	    "Though mod-added armours have absolutely no support, and never can without being specifically coded to \n" +
	    "MineFantasy armours will take these variables and function differently on the values. But weapon items can \n" +
	    "be added to the list: Put each entry on it's own line set out like this: \n" +
	    "id|cutting|blunt \n" +
	    "id is the item id (you need to find it out yourself), cutting and blunt are the ratio. \n" +
	    "The difference between the ratio is what determines damage 1|0 means 100% cutting damage, 3|1 means it's 3 cutting to 1 blunt (or 75%, 25%). use whatever numbers you need to make the ratio.";
		
	    customDamagerList = config.get(CATEGORY_WEPS, "Custom Damage Ratios", new String[0], damageDesc).getStringList();
	    Arrays.sort(customDamagerList);
	    
	    String damageEDesc = 
		"Similar method to 'Custom Damage Ratios' only with entities, This is for registering things like arrows, same format only with the entity registry name (usually modid:name)";
		
	    customDamagerEntityList = config.get(CATEGORY_WEPS, "Custom Entity Damage Ratios", new String[0], damageEDesc).getStringList();
	    Arrays.sort(customDamagerEntityList);
	}
	
	public static void readCustoms()
	{
		MFLogUtil.logDebug("Loading Custom Item Entries from config...");
		try
		{
			for(String s: armourListAC)
		    {
		    	breakdownLineForAutoarmour(s);
		    }
			for(String s: hoeList)
		    {
				breakdownLineForHoe(s);
		    }
			for(String s: customDamagerList)
		    {
				breakdownLineForDamage(s);
		    }
			for(String s: customDamagerEntityList)
		    {
				breakdownLineForDamage(s);
		    }
		}
		catch(Exception e)
		{
			MFLogUtil.logWarn("Failed to load Custom Item Entries from config. Check the config file");
		}
	}

	private static void registerArmourclassEntry(Item piece, String design, float weight)
	{
		ArmourDesign AC = getClassFor(design);
		if(AC == null)
		{
			MFLogUtil.logWarn("Could not define armour design '" + design + "' for item id: " + Item.getIdFromItem(piece));
			return;
		}
		CustomArmourEntry.registerItem(piece, AC, weight);
	}

	private static ArmourDesign getClassFor(String name)
	{
		ArmourDesign design = ArmourDesign.designs.get(name);
		if(design != null)
		{
			return design;
		}
		return null;
	}
	
	private static void breakdownLineForAutoarmour(String config) 
	{
		String temp = "";
		int phase = 0;
		int id = 0;
		ArmourDesign AD = null;
		float weight = 1.0F;
		for(int a = 0; a < config.length(); a ++)
		{
			if(a == config.length()-1)
			{
				temp = temp + config.charAt(a);
			}
			if(config.charAt(a) == "|".charAt(0) || a == config.length()-1)
			{
				if(phase == 0)
				{
					id = Integer.valueOf(temp);
					temp = "";
					phase ++;
				}
				else if(phase == 1)
				{
					ArmourDesign design = getClassFor(temp);
					if(design != null)
					{
						AD = design;
					}
					temp = "";
					phase ++;
				}
				else if(phase == 2)
				{
					weight = Float.valueOf(temp);
					temp = "";
					
					Item piece = Item.getItemById(id);
					if(AD != null && piece != null)
					{
						CustomArmourEntry.registerItem(piece, AD, weight);
						MFLogUtil.logDebug("Added Armour: " + id + " To " + AD.getName() + " modified weight is " + weight);
					}
					phase = 0;
				}
			}
			else
			{
				if(config.charAt(a) != " ".charAt(0))
				{
					temp = temp + config.charAt(a);
				}
			}
		}
	}
	
	
	private static void breakdownLineForHoe(String config) 
	{
		String temp = "";
		int phase = 0;
		int id = 0;
		float eff = 6.0F;
		
		for(int a = 0; a < config.length(); a ++)
		{
			if(a == config.length()-1)
			{
				temp = temp + config.charAt(a);
			}
			if(config.charAt(a) == "|".charAt(0) || a == config.length()-1)
			{
				if(phase == 0)
				{
					id = Integer.valueOf(temp);
					temp = "";
					phase ++;
				}
				else if(phase == 1)
				{
					eff = Float.valueOf(temp);
					temp = "";
					
					Item hoe = Item.getItemById(id);
					if(hoe != null)
					{
						CustomHoeEntry.registerItem(hoe, eff);
						MFLogUtil.logDebug("Added Hoe: " + id + " With Efficiency " + eff);
					}
					phase = 0;
				}
			}
			else
			{
				if(config.charAt(a) != " ".charAt(0))
				{
					temp = temp + config.charAt(a);
				}
			}
		}
	}
	
	private static void breakdownLineForDamage(String config) 
	{
		String temp = "";
		int phase = 0;
		int id = 0;
		float cut = 0;
		float blunt = 0;
		
		for(int a = 0; a < config.length(); a ++)
		{
			if(a == config.length()-1)
			{
				temp = temp + config.charAt(a);
			}
			if(config.charAt(a) == "|".charAt(0) || a == config.length()-1)
			{
				if(phase == 0)
				{
					id = Integer.valueOf(temp);
					temp = "";
					phase ++;
				}
				else if(phase == 1)
				{
					cut = Float.valueOf(temp);
					temp = "";
					phase ++;
				}
				else if(phase == 2)
				{
					blunt = Float.valueOf(temp);
					temp = "";
					
					Item weapon = Item.getItemById(id);
					if(weapon != null)
					{
						CustomDamageRatioEntry.registerItem(weapon, new float[]{cut, blunt});
						MFLogUtil.logDebug("Added Custom weapon: " + id + " With Ratio " + cut + ":" + blunt);
					}
					phase = 0;
				}
			}
			else
			{
				if(config.charAt(a) != " ".charAt(0))
				{
					temp = temp + config.charAt(a);
				}
			}
		}
	}
	
	private static void breakdownLineForDamageEntity(String config) 
	{
		String temp = "";
		int phase = 0;
		String id = "";
		float cut = 0;
		float blunt = 0;
		
		for(int a = 0; a < config.length(); a ++)
		{
			if(a == config.length()-1)
			{
				temp = temp + config.charAt(a);
			}
			if(config.charAt(a) == "|".charAt(0) || a == config.length()-1)
			{
				if(phase == 0)
				{
					id = temp;
					temp = "";
					phase ++;
				}
				else if(phase == 1)
				{
					cut = Float.valueOf(temp);
					temp = "";
					phase ++;
				}
				else if(phase == 2)
				{
					blunt = Float.valueOf(temp);
					temp = "";
					
					CustomDamageRatioEntry.registerEntity(id, new float[]{cut, blunt});
					MFLogUtil.logDebug("Added Custom entity: " + id + " With Ratio " + cut + ":" + blunt);
					phase = 0;
				}
			}
			else
			{
				if(config.charAt(a) != " ".charAt(0))
				{
					temp = temp + config.charAt(a);
				}
			}
		}
	}
}
