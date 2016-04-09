package minefantasy.mf2.config;

import minefantasy.mf2.api.helpers.ArmourCalculator;
import minefantasy.mf2.api.helpers.TacticalManager;

public class ConfigArmour extends ConfigurationBaseMF 
{
	public static final String CATEGORY_BONUS = "Bonuses";
	
	public static boolean resistArrow;

	
	public static final String CATEGORY_PENALTIES = "Penalties";
	
	
	public static final String CATEGORY_MISC = "Other Features";

	@Override
	protected void loadConfig() 
	{
		resistArrow = Boolean.parseBoolean(config.get(CATEGORY_BONUS, "Deflect Arrows", true, "With this option; arrows have a chance to deflect off armour. It compares the arrow damage and armour rating. Chain and plate have higher rates. Arrows bounced off can hurt enemies").getString());
		TacticalManager.arrowDeflectChance = Float.parseFloat(config.get(CATEGORY_BONUS, "Deflect Arrows Modifier", 1.0F, "This modifies the chance for arrows to deflect off armour: Increasing this increases the deflect chance, decreasing decreases the chance, 0 means it's impossible").getString());
		
		TacticalManager.shouldSlow = Boolean.parseBoolean(config.get(CATEGORY_PENALTIES, "Armour Slow Movement", true, "With this: Heavier armours slow your movement down acrosss both walking and sprinting. Speed is affected by design and material").getString());
		TacticalManager.minWeightSpeed = Float.parseFloat(config.get(CATEGORY_PENALTIES, "Min armour weigh speed", 10F, "The minimal speed percent capable from weight (10 = 10%), MF armours don't go lower than 75% anyway").getString());
		ArmourCalculator.slowRate = Float.parseFloat(config.get(CATEGORY_PENALTIES, "Slowdown Rate", 1.0F, "A modifier for the rate armours slow down movement, increasing this slows you more, decreasing it slows less, just keep it above 0").getString());
		
		//ArmourCalculator.useThresholdSystem = Boolean.parseBoolean(config.get(CATEGORY_MISC, "DT Armour", false, "(As of 2.4.2) Armour has two mechanics how it works: All armours have a value that effects how it works against cutting/blunt/piercing ratios and will react to durability loss... However the Damage Threshold system cuts a set amount of damage off, (default uses damage ratio which cuts off a percentage)").getString());
	}

}
