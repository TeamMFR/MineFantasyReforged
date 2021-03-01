package minefantasy.mfr.recipe;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.DragonforgedStyle;
import minefantasy.mfr.init.OrnateStyle;
import minefantasy.mfr.material.BaseMaterialMFR;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MineFantasyReborn.MOD_ID)
public class BasicRecipesMF {

	public static void init() {
		ForgingRecipes.init();
		CarpenterRecipes.init();
		SmeltingRecipesMF.init();
		SalvageRecipes.init();
		CookingRecipes.init();
		DragonforgedStyle.loadCrafting();
		OrnateStyle.loadCrafting();
		// GameRegistry.addRecipe(new RecipeArmourDyeMF()); //TODO Replace with proper recipe JSON_FILE_EXT

		// TODO Replace with proper recipe JSON_FILE_EXT for each metal block

		BaseMaterialMFR mat = BaseMaterialMFR.IRON;

		TanningRecipe.addRecipe(ComponentListMFR.HIDE_SMALL, mat.craftTimeModifier * 2F, -1,
				new ItemStack(Items.LEATHER));
		TanningRecipe.addRecipe(ComponentListMFR.HIDE_MEDIUM, mat.craftTimeModifier * 3F, -1,
				new ItemStack(Items.LEATHER, 3));
		TanningRecipe.addRecipe(ComponentListMFR.HIDE_LARGE, mat.craftTimeModifier * 4F, -1,
				new ItemStack(Items.LEATHER, 5));
		TanningRecipe.addRecipe(Items.LEATHER, mat.craftTimeModifier * 2F, -1, "shears",
				new ItemStack(ComponentListMFR.LEATHER_STRIP, 4));
	}

}
