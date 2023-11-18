package minefantasy.mfr.recipe;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.init.MineFantasyItems;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MineFantasyReforged.MOD_ID)
public class BasicRecipesMF {

	public static void init() {
		SalvageRecipes.init();
		CookingRecipes.init();
		MineFantasyItems.loadCrafting();
	}

}
