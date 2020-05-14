package minefantasy.mfr.recipe;

import net.minecraftforge.fml.common.registry.GameRegistry;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.init.BlockListMFR;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.KnowledgeListMFR;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;

public class TempRecipesMF {
    public static void init() {
        ArrayList<CustomMaterial> wood = CustomMaterial.getList("wood");
        for (CustomMaterial customMat : wood) {
            assembleWoodVariations(customMat);
        }

        // RESOURCES

        ItemStack steel = ComponentListMFR.bar("Steel");

        //GameRegistry.addRecipe(new ItemStack(Blocks.RAIL, 64), new Object[]{"I I", "ISI", "I I", 'I', steel, 'S', Items.STICK,});
        //TODO Replace with proper recipe JSON

        //GameRegistry.addShapedRecipe(new ItemStack(Blocks.COBBLESTONE), new Object[]{"C", 'C', BlockListMFR.COBBLE_BRICK});
        //TODO Replace with proper recipe JSON
        //GameRegistry.addShapedRecipe(new ItemStack(Blocks.cobblestone), new Object[]{"C", 'C', BlockListMFR.cobble_pavement});
        //TODO Replace with proper recipe JSON
        //KnowledgeListMFR.stoneBricksR.add(GameRegistry.addShapedRecipe(new ItemStack(BlockListMFR.COBBLE_BRICK, 4), new Object[]{"C C", "   ", "C C", 'C', Blocks.COBBLESTONE}));
        //TODO Replace with proper recipe JSON
        //KnowledgeListMFR.stoneBricksR.add(GameRegistry.addShapedRecipe(new ItemStack(BlockListMFR.cobble_pavement, 4), new Object[]{"CC", "CC", 'C', Blocks.COBBLESTONE}));
        //TODO Replace with proper recipe JSON

        //GameRegistry.addShapedRecipe(new ItemStack(BlockListMFR.REINFORCED_STONE), new Object[]{"C", 'C', BlockListMFR.REINFORCED_STONE_BRICKS});
        //TODO Replace with proper recipe JSON
        //KnowledgeListMFR.stoneBricksR.add(GameRegistry.addShapedRecipe(new ItemStack(BlockListMFR.REINFORCED_STONE_BRICKS, 4), new Object[]{"CC", "CC", 'C', BlockListMFR.REINFORCED_STONE}));
        //TODO Replace with proper recipe JSON

        //GameRegistry.addShapedRecipe(new ItemStack(Blocks.DIRT), new Object[]{"C", 'C', BlockListMFR.MUD_BRICK});
        //TODO Replace with proper recipe JSON
        //GameRegistry.addShapedRecipe(new ItemStack(Blocks.DIRT), new Object[]{"C", 'C', BlockListMFR.MUD_PAVEMENT});
        //TODO Replace with proper recipe JSON
        //KnowledgeListMFR.stoneBricksR.add(GameRegistry.addShapedRecipe(new ItemStack(BlockListMFR.MUD_BRICK, 4), new Object[]{"C C", "   ", "C C", 'C', Blocks.DIRT}));
        //TODO Replace with proper recipe JSON
        //KnowledgeListMFR.stoneBricksR.add(GameRegistry.addShapedRecipe(new ItemStack(BlockListMFR.MUD_PAVEMENT, 4), new Object[]{"CC", "CC", 'C', Blocks.DIRT}));
        //TODO Replace with proper recipe JSON

        //GameRegistry.addShapedRecipe(new ItemStack(BlockListMFR.FRAMED_PANE, 16), new Object[]{"GGG", "GGG", 'G', BlockListMFR.FRAMED_GLASS});
        //TODO Replace with proper recipe JSON
        //GameRegistry.addShapedRecipe(new ItemStack(BlockListMFR.WINDOW_PANE, 16), new Object[]{"GGG", "GGG", 'G', BlockListMFR.WINDOW});
        //TODO Replace with proper recipe JSON
    }

    private static void assembleWoodVariations(CustomMaterial material) {

    }
}
