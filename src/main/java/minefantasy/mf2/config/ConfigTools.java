package minefantasy.mf2.config;

public class ConfigTools extends ConfigurationBaseMF 
{
	public static final String CATEGORY_BONUS = "Bonuses";
	
	public static float handpickBonus;

	
	public static final String CATEGORY_PENALTIES = "Penalties";
	
	public static float hvyDropChance;
	
	
	@Override
	protected void loadConfig() 
	{
		handpickBonus = Float.parseFloat(config.get(CATEGORY_BONUS, "Handpick double-drop chance", 20F, "This is the percent value (0-100) handpicks double item drops").getString());
		
		hvyDropChance = Float.parseFloat(config.get(CATEGORY_PENALTIES, "Heavy ruin-drop chance", 25F, "This is the percent value (0-100) heavy picks (and shovels) do NOT drop blocks on break").getString());
	}

}
