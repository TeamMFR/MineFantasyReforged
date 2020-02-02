package minefantasy.mfr.recipe;

import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.util.MFRLogUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

public class RecipeRemover {
    public static void removeRecipes() {
        MFRLogUtil.log("MineFantasy: Removing replaced recipes...");
        for (int a = 0; a < CraftingManager.getInstance().getRecipeList().size(); a++) {
            IRecipe rec = (IRecipe) CraftingManager.getInstance().getRecipeList().get(a);
            if (rec.getRecipeOutput() != null && willRemoveItem(rec.getRecipeOutput(), ConfigHardcore.HCCRemoveCraft)) {
                CraftingManager.getInstance().getRecipeList().remove(a);
            }
        }
    }

    private static boolean willRemoveItem(ItemStack item, boolean HCC) {
        if (item.getItem() == Items.STICK)
            return true;

        if (HCC) {
            return item.getItem() == Items.BREAD || item.getItem() == Items.PUMPKIN_PIE || item.getItem() == Items.CAKE
                    || item.getItem() == Items.FLINT_AND_STEEL || item.getItem() == Items.BUCKET;
        }
        return false;
    }
}
