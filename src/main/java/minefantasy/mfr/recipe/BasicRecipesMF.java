package minefantasy.mfr.recipe;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.constants.Tool;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasyMaterials;
import minefantasy.mfr.material.BaseMaterial;
import minefantasy.mfr.recipe.refine.QuernRecipes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MineFantasyReforged.MOD_ID)
public class BasicRecipesMF {

	public static void init() {
		QuernRecipes.init();
		SmeltingRecipesMF.init();
		SalvageRecipes.init();
		CookingRecipes.init();
		MineFantasyItems.loadCrafting();
		// GameRegistry.addRecipe(new RecipeArmourDyeMF()); //TODO Replace with proper recipe JSON_FILE_EXT

		// TODO Replace with proper recipe JSON_FILE_EXT for each metal block

		BaseMaterial mat = MineFantasyMaterials.IRON;

		TanningRecipe.addRecipe(MineFantasyItems.HIDE_SMALL, mat.craftTimeModifier * 2F, -1, new ItemStack(Items.LEATHER));
		TanningRecipe.addRecipe(MineFantasyItems.HIDE_MEDIUM, mat.craftTimeModifier * 3F, -1, new ItemStack(Items.LEATHER, 3));
		TanningRecipe.addRecipe(MineFantasyItems.HIDE_LARGE, mat.craftTimeModifier * 4F, -1, new ItemStack(Items.LEATHER, 5));
		TanningRecipe.addRecipe(Items.LEATHER, mat.craftTimeModifier * 2F, -1, Tool.SHEARS, new ItemStack(MineFantasyItems.LEATHER_STRIP, 4));
	}

}
