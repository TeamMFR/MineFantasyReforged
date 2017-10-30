package minefantasy.mf2.integration.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import cpw.mods.fml.common.Optional;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.config.ConfigIntegration;

@Optional.Interface(iface = "codechicken.nei.api.IConfigureNEI", modid = "NotEnoughItems")
public class NEIConfig implements IConfigureNEI {

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
        if (ConfigIntegration.neiIntegration) {
            RecipeHandlerCarpenter handlerCarpenter = new RecipeHandlerCarpenter();
            API.registerRecipeHandler(handlerCarpenter);
            API.registerUsageHandler(handlerCarpenter);

            RecipeHandlerAnvil handlerAnvil = new RecipeHandlerAnvil();
            API.registerRecipeHandler(handlerAnvil);
            API.registerUsageHandler(handlerAnvil);

            RecipeHandlerBloom handlerBloom = new RecipeHandlerBloom();
            API.registerRecipeHandler(handlerBloom);
            API.registerUsageHandler(handlerBloom);

            RecipeHandlerQuern handlerQuern = new RecipeHandlerQuern();
            API.registerRecipeHandler(handlerQuern);
            API.registerUsageHandler(handlerQuern);

			/*RecipeHandlerTanning handlerTanning = new RecipeHandlerTanning();
            API.registerRecipeHandler(handlerTanning);
			API.registerUsageHandler(handlerTanning);*/

            RecipeHandlerCrucible handlerCrucible = new RecipeHandlerCrucible();
            API.registerRecipeHandler(handlerCrucible);
            API.registerUsageHandler(handlerCrucible);

            RecipeHandlerBigFurnace handlerBigFurnace = new RecipeHandlerBigFurnace();
            API.registerRecipeHandler(handlerBigFurnace);
            API.registerUsageHandler(handlerBigFurnace);

            RecipeHandlerBlastFurnace handlerBlastFurnace = new RecipeHandlerBlastFurnace();
            API.registerRecipeHandler(handlerBlastFurnace);
            API.registerUsageHandler(handlerBlastFurnace);
        }
    }
}