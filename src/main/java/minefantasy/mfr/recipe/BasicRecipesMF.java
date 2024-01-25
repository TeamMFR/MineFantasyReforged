package minefantasy.mfr.recipe;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.recipe.refine.PaintOilRecipe;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MineFantasyReforged.MOD_ID)
public class BasicRecipesMF {

	public static void init() {
		PaintOilRecipe.addRecipe(MineFantasyBlocks.NAILED_PLANKS, MineFantasyBlocks.REFINED_PLANKS);
		PaintOilRecipe.addRecipe(MineFantasyBlocks.NAILED_PLANKS_STAIR, MineFantasyBlocks.REFINED_PLANKS_STAIR);
		MineFantasyItems.loadCrafting();
	}

}
