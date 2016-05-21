package minefantasy.mf2.config;

import minefantasy.mf2.api.cooking.CookRecipe;
import minefantasy.mf2.block.tileentity.blastfurnace.TileEntityBlastFH;
import minefantasy.mf2.mechanics.CombatMechanics;

public class ConfigCrafting extends ConfigurationBaseMF
{
	public static final String CATEGORY_REFINING = "Refining";
	public static boolean allowIronResmelt;	
	public static final String CATEGORY_COOKING = "Cooking";
	
	@Override
	protected void loadConfig()
	{
		allowIronResmelt =  Boolean.parseBoolean(config.get(CATEGORY_REFINING, "Allow Iron ingots to make Pig Iron", false, "If you're not resoureful: you can allow iron ingots to make prepared iron for refining.").getString());
		TileEntityBlastFH.maxFurnaceHeight =  Integer.parseInt(config.get(CATEGORY_REFINING, "Max Blast Furnace Height", 16, "The max amount of chambers a blast furnace can read").getString());
		CookRecipe.canCookBasics = Boolean.parseBoolean(config.get(CATEGORY_COOKING, "Cook non-mf food on cooktop", true, "This means non-mf food cooked in a furnace can work on a cooking plate").getString());
	}

}
