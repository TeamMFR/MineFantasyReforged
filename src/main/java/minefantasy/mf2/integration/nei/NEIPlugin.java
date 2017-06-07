package minefantasy.mf2.integration.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import minefantasy.mf2.MineFantasyII;

public class NEIPlugin implements IConfigureNEI {

	public NEIPlugin() {
		this.loadConfig();
	}
	
	@Override
	public String getName() {
		return MineFantasyII.NAME + " NEI Plugin";
	}

	@Override
	public String getVersion() {
		return MineFantasyII.VERSION;
	}

	@Override
	public void loadConfig() {
		API.registerRecipeHandler(new RecipeHandlerCarpenter());
		API.registerUsageHandler(new RecipeHandlerCarpenter());
		
		API.registerRecipeHandler(new RecipeHandlerAnvil());
		API.registerUsageHandler(new RecipeHandlerAnvil());
		
		API.registerRecipeHandler(new RecipeHandlerBloom());
		API.registerUsageHandler(new RecipeHandlerBloom());
		
		API.registerRecipeHandler(new RecipeHandlerQuern());
		API.registerUsageHandler(new RecipeHandlerQuern());
		
		API.registerRecipeHandler(new RecipeHandlerTanning());
		API.registerUsageHandler(new RecipeHandlerTanning());
		
		API.registerRecipeHandler(new RecipeHandlerCrucible());
		API.registerUsageHandler(new RecipeHandlerCrucible());
		
		API.registerRecipeHandler(new RecipeHandlerBigFurnace());
		API.registerUsageHandler(new RecipeHandlerBigFurnace());
		
		API.registerRecipeHandler(new RecipeHandlerBlastFurnace());
		API.registerUsageHandler(new RecipeHandlerBlastFurnace());
	}
}