package minefantasy.mfr.recipe;

import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.init.ComponentListMFR;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class TempRecipesMF {
    public static void init() {
        ArrayList<CustomMaterial> wood = CustomMaterial.getList("wood");
        for (CustomMaterial customMat : wood) {
            assembleWoodVariations(customMat);
        }

        // RESOURCES

        ItemStack steel = ComponentListMFR.bar("Steel");

        //GameRegistry.addShapedRecipe(new ItemStack(BlockListMFR.FRAMED_PANE, 16), new Object[]{"GGG", "GGG", 'G', BlockListMFR.FRAMED_GLASS});
        // actually this seem to be a carpenter recipe in MF2
    }

    private static void assembleWoodVariations(CustomMaterial material) {

    }
}
