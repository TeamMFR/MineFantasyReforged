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

//		BaseMaterial mat = MineFantasyMaterials.IRON;
//
//		TanningRecipe.addRecipe(MineFantasyItems.HIDE_SMALL, 6 * 2F, -1, new ItemStack(Items.LEATHER));
//		TanningRecipe.addRecipe(MineFantasyItems.HIDE_MEDIUM, 6 * 3F, -1, new ItemStack(Items.LEATHER, 3));
//		TanningRecipe.addRecipe(MineFantasyItems.HIDE_LARGE, 6 * 4F, -1, new ItemStack(Items.LEATHER, 5));
//		TanningRecipe.addRecipe(Items.LEATHER, 6 * 2F, -1, Tool.SHEARS, new ItemStack(MineFantasyItems.LEATHER_STRIP, 4));
	}

}
