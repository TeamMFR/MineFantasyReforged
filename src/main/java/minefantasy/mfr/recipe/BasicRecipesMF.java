package minefantasy.mfr.recipe;

import net.minecraftforge.fml.common.registry.GameRegistry;
import minefantasy.mfr.api.MineFantasyRebornAPI;
import minefantasy.mfr.api.crafting.tanning.TanningRecipe;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.block.basic.ConstructionBlockMFR;
import minefantasy.mfr.init.BlockListMFR;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.init.FoodListMFR;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.ToolListMFR;
import minefantasy.mfr.init.DragonforgedStyle;
import minefantasy.mfr.init.OrnateStyle;
import minefantasy.mfr.init.KnowledgeListMFR;
import minefantasy.mfr.material.BaseMaterialMFR;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Iterator;

public class BasicRecipesMF {
    public static void init() {
        addFoodOutput();
        TempRecipesMF.init();// TODO remove temp recipes
        ForgingRecipes.init();
        CarpenterRecipes.init();
        SmeltingRecipesMF.init();
        SalvageRecipes.init();
        CookingRecipes.init();
        DragonforgedStyle.loadCrafting();
        OrnateStyle.loadCrafting();
        GameRegistry.addRecipe(new RecipeArmourDyeMF());

        ArrayList<CustomMaterial> wood = CustomMaterial.getList("wood");
        Iterator iteratorWood = wood.iterator();

        KnowledgeListMFR.stickRecipe = GameRegistry.addShapedRecipe(new ItemStack(Items.STICK, 2),
                new Object[]{"S", 'S', ComponentListMFR.plank});
        GameRegistry.addShapedRecipe(new ItemStack(Items.STICK, 2),
                new Object[]{"S", 'S', ComponentListMFR.plank_cut});

        KnowledgeListMFR.firepitRecipe = GameRegistry.addShapedRecipe(new ItemStack(BlockListMFR.FIREPIT),
                new Object[]{" P ", "S S", 'S', Items.STICK, 'P', ComponentListMFR.plank});
        KnowledgeListMFR.cooktopRecipe = GameRegistry.addShapedRecipe(new ItemStack(BlockListMFR.ROAST),
                new Object[]{"PSP", 'S', Blocks.COBBLESTONE, 'P', ComponentListMFR.plank});

        GameRegistry.addShapelessRecipe(new ItemStack(BlockListMFR.YEW_PLANKS, 4), new Object[]{BlockListMFR.LOG_YEW});
        GameRegistry.addShapelessRecipe(new ItemStack(BlockListMFR.IRONBARK_PLANKS, 4),
                new Object[]{BlockListMFR.LOG_IRONBARK});
        GameRegistry.addShapelessRecipe(new ItemStack(BlockListMFR.EBONY_PLANKS, 4),
                new Object[]{BlockListMFR.LOG_EBONY});

        while (iteratorWood.hasNext()) {
            CustomMaterial customMat = (CustomMaterial) iteratorWood.next();
            assembleWoodVariations(customMat);
        }

        KnowledgeListMFR.plantOilR = GameRegistry.addShapedRecipe(new ItemStack(ComponentListMFR.plant_oil, 4),
                new Object[]{" B ", "BFB", " B ", 'F', Items.WHEAT_SEEDS, 'B', FoodListMFR.jug_empty});
        KnowledgeListMFR.waterJugR = GameRegistry.addShapedRecipe(new ItemStack(FoodListMFR.jug_water, 4),
                new Object[]{" B ", "BWB", " B ", 'W', Items.WATER_BUCKET, 'B', FoodListMFR.jug_empty});
        KnowledgeListMFR.sugarRecipe = GameRegistry.addShapedRecipe(new ItemStack(FoodListMFR.sugarpot),
                new Object[]{"S", "S", "B", 'S', Items.SUGAR, 'B', ComponentListMFR.clay_pot,});
        GameRegistry.addShapelessRecipe(new ItemStack(Items.SUGAR, 2), new Object[]{FoodListMFR.sugarpot,});
        KnowledgeListMFR.milkJugR = GameRegistry.addShapedRecipe(new ItemStack(FoodListMFR.jug_milk, 4),
                new Object[]{" B ", "BMB", " B ", 'M', Items.MILK_BUCKET, 'B', FoodListMFR.jug_empty});
        GameRegistry.addShapedRecipe(new ItemStack(Items.MILK_BUCKET),
                new Object[]{" B ", "BMB", " B ", 'M', Items.BUCKET, 'B', FoodListMFR.jug_milk});
        GameRegistry.addShapedRecipe(new ItemStack(Items.WATER_BUCKET),
                new Object[]{" B ", "BMB", " B ", 'M', Items.BUCKET, 'B', FoodListMFR.jug_water});

        GameRegistry.addRecipe(new RecipeSyringe());
        // Just a way on making the overpowered gunpowder from black powder
        GameRegistry.addShapelessRecipe(new ItemStack(Items.GUNPOWDER),
                new Object[]{new ItemStack(ComponentListMFR.blackpowder), new ItemStack(ComponentListMFR.blackpowder),
                        new ItemStack(ComponentListMFR.nitre),});

        for (int id = 0; id < BlockListMFR.METAL_BLOCKS.length; id++) {
            BaseMaterialMFR material = BaseMaterialMFR.getMaterial(BlockListMFR.METAL_BLOCKS[id]);

            for (ItemStack ingot : OreDictionary.getOres("ingot" + material.name)) {
                GameRegistry.addRecipe(new ItemStack(BlockListMFR.STORAGE[id]),
                        new Object[]{"III", "III", "III", 'I', ingot});
                GameRegistry.addShapelessRecipe(new ItemStack(ingot.getItem(), 9),
                        new Object[]{BlockListMFR.STORAGE[id]});
            }
        }

        KnowledgeListMFR.fireclayR = MineFantasyRebornAPI.addBasicCarpenterRecipe(new ItemStack(ComponentListMFR.fireclay, 4),
                new Object[]{" C ", "CDC", " C ",

                        'D', ComponentListMFR.kaolinite_dust, 'C', Items.CLAY_BALL});
        KnowledgeListMFR.fireBrickR = MineFantasyRebornAPI
                .addBasicCarpenterRecipe(new ItemStack(ComponentListMFR.fireclay_brick), new Object[]{"C",

                        'C', ComponentListMFR.fireclay});
        KnowledgeListMFR.fireBricksR = MineFantasyRebornAPI.addBasicCarpenterRecipe(new ItemStack(BlockListMFR.FIREBRICKS),
                new Object[]{"BB", "BB",

                        'B', ComponentListMFR.strong_brick});
        KnowledgeListMFR.fireBrickStairR = MineFantasyRebornAPI
                .addBasicCarpenterRecipe(new ItemStack(BlockListMFR.FIREBRICK_STAIR), new Object[]{"B ", "BB",

                        'B', ComponentListMFR.strong_brick});
        BaseMaterialMFR mat = BaseMaterialMFR.iron;

        GameRegistry.addShapelessRecipe(new ItemStack(ComponentListMFR.hideSmall),
                new Object[]{ComponentListMFR.rawhideSmall, ComponentListMFR.flux});
        GameRegistry.addShapelessRecipe(new ItemStack(ComponentListMFR.hideMedium),
                new Object[]{ComponentListMFR.rawhideMedium, ComponentListMFR.flux});
        GameRegistry.addShapelessRecipe(new ItemStack(ComponentListMFR.hideLarge),
                new Object[]{ComponentListMFR.rawhideLarge, ComponentListMFR.flux});

        TanningRecipe.addRecipe(ComponentListMFR.hideSmall, mat.craftTimeModifier * 2F, -1,
                new ItemStack(Items.LEATHER));
        TanningRecipe.addRecipe(ComponentListMFR.hideMedium, mat.craftTimeModifier * 3F, -1,
                new ItemStack(Items.LEATHER, 3));
        TanningRecipe.addRecipe(ComponentListMFR.hideLarge, mat.craftTimeModifier * 4F, -1,
                new ItemStack(Items.LEATHER, 5));
        TanningRecipe.addRecipe(Items.LEATHER, mat.craftTimeModifier * 2F, -1, "shears",
                new ItemStack(ComponentListMFR.leather_strip, 4));

        if (!ConfigHardcore.HCCRemoveBooksCraft) {
            KnowledgeListMFR.artBookR = MineFantasyRebornAPI.addBasicCarpenterRecipe(
                    new ItemStack(ToolListMFR.skillbook_artisanry), new Object[]{"T", "D", "B", 'T',
                            ComponentListMFR.talisman_lesser, 'D', new ItemStack(Items.DYE, 1, 1), 'B', Items.BOOK,});
            KnowledgeListMFR.conBookR = MineFantasyRebornAPI.addBasicCarpenterRecipe(
                    new ItemStack(ToolListMFR.skillbook_construction), new Object[]{"T", "D", "B", 'T',
                            ComponentListMFR.talisman_lesser, 'D', new ItemStack(Items.DYE, 1, 14), 'B', Items.BOOK,});
            KnowledgeListMFR.proBookR = MineFantasyRebornAPI.addBasicCarpenterRecipe(
                    new ItemStack(ToolListMFR.skillbook_provisioning), new Object[]{"T", "D", "B", 'T',
                            ComponentListMFR.talisman_lesser, 'D', new ItemStack(Items.DYE, 1, 2), 'B', Items.BOOK,});
            KnowledgeListMFR.engBookR = MineFantasyRebornAPI.addBasicCarpenterRecipe(
                    new ItemStack(ToolListMFR.skillbook_engineering), new Object[]{"T", "D", "B", 'T',
                            ComponentListMFR.talisman_lesser, 'D', new ItemStack(Items.DYE, 1, 12), 'B', Items.BOOK,});
            KnowledgeListMFR.comBookR = MineFantasyRebornAPI.addBasicCarpenterRecipe(
                    new ItemStack(ToolListMFR.skillbook_combat), new Object[]{"T", "D", "B", 'T',
                            ComponentListMFR.talisman_lesser, 'D', new ItemStack(Items.DYE, 1, 5), 'B', Items.BOOK,});

            KnowledgeListMFR.artBook2R = MineFantasyRebornAPI
                    .addBasicCarpenterRecipe(new ItemStack(ToolListMFR.skillbook_artisanry2), new Object[]{"T", "B",
                            'T', ComponentListMFR.talisman_greater, 'B', ToolListMFR.skillbook_artisanry,});
            KnowledgeListMFR.conBook2R = MineFantasyRebornAPI
                    .addBasicCarpenterRecipe(new ItemStack(ToolListMFR.skillbook_construction2), new Object[]{"T", "B",
                            'T', ComponentListMFR.talisman_greater, 'B', ToolListMFR.skillbook_construction,});
            KnowledgeListMFR.proBook2R = MineFantasyRebornAPI
                    .addBasicCarpenterRecipe(new ItemStack(ToolListMFR.skillbook_provisioning2), new Object[]{"T", "B",
                            'T', ComponentListMFR.talisman_greater, 'B', ToolListMFR.skillbook_provisioning,});
            KnowledgeListMFR.engBook2R = MineFantasyRebornAPI
                    .addBasicCarpenterRecipe(new ItemStack(ToolListMFR.skillbook_engineering2), new Object[]{"T", "B",
                            'T', ComponentListMFR.talisman_greater, 'B', ToolListMFR.skillbook_engineering,});
            KnowledgeListMFR.comBook2R = MineFantasyRebornAPI
                    .addBasicCarpenterRecipe(new ItemStack(ToolListMFR.skillbook_combat2), new Object[]{"T", "B", 'T',
                            ComponentListMFR.talisman_greater, 'B', ToolListMFR.skillbook_combat,});
        }

        GameRegistry.addShapedRecipe(ComponentListMFR.plank.construct("ScrapWood"),
                new Object[]{"S", "S", 'S', Items.STICK,});

        Object rock = ConfigHardcore.HCCallowRocks ? ComponentListMFR.sharp_rock : Blocks.COBBLESTONE;
        KnowledgeListMFR.dryrocksR = GameRegistry.addShapedRecipe(new ItemStack(ToolListMFR.dryrocks),
                new Object[]{"R ", " R", 'R', rock});

        ((ConstructionBlockMFR.StairsConstBlock) BlockListMFR.MUD_BRICK_STAIR).addRecipe();
        ((ConstructionBlockMFR.StairsConstBlock) BlockListMFR.MUD_PAVEMENT_STAIR).addRecipe();
        ((ConstructionBlockMFR.StairsConstBlock) BlockListMFR.COBBLE_BRICK_STAIR).addRecipe();
        ((ConstructionBlockMFR.StairsConstBlock) BlockListMFR.COBBLE_PAVEMENT_STAIR).addRecipe();
        ((ConstructionBlockMFR.StairsConstBlock) BlockListMFR.REINFORCED_STONE_BRICK_STAIR).addRecipe();
        ((ConstructionBlockMFR.StairsConstBlock) BlockListMFR.REINFORCED_STONE_STAIR).addRecipe();

        ((ConstructionBlockMFR.StairsConstBlock) BlockListMFR.YEW_STAIR).addRecipe();
        ((ConstructionBlockMFR.StairsConstBlock) BlockListMFR.IRONBARK_STAIR).addRecipe();
        ((ConstructionBlockMFR.StairsConstBlock) BlockListMFR.EBONY_STAIR).addRecipe();
    }

    private static void assembleWoodVariations(CustomMaterial material) {
        // TODO
        if (material.name != "RefinedWood") {
            ArrayList<ItemStack> list = OreDictionary.getOres("planks" + material.name);
            if (list.isEmpty()) {
                for (ItemStack planks : OreDictionary.getOres("plankWood")) {
                    if (planks.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
                        ItemStack item = planks.copy();
                        for (int i = 0; i < 16; i++) {
                            item.setItemDamage(i);
                            tryAddWoodPlanks(item, material);
                            CarpenterRecipes.tryAddSawPlanks(item, material);
                        }
                    } else {
                        tryAddWoodPlanks(planks, material);
                        CarpenterRecipes.tryAddSawPlanks(planks, material);
                    }
                }
            } else
                for (ItemStack block : list) {
                    KnowledgeListMFR.plankRecipe.add(GameRegistry.addShapedRecipe(
                            ComponentListMFR.plank.construct(material.name, 4), new Object[]{"P", "P", 'P', block}));
                    CarpenterRecipes.addSawPlanks(block, material);
                }
        }
    }

    private static void tryAddWoodPlanks(ItemStack planks, CustomMaterial material) {
        String sub = material.name.substring(0, material.name.length() - 4).toLowerCase();

        if (planks.getUnlocalizedName().toLowerCase().contains(sub)) {
            KnowledgeListMFR.plankRecipe.add(GameRegistry.addShapedRecipe(
                    ComponentListMFR.plank.construct(material.name, 4), new Object[]{"P", "P", 'P', planks.copy()}));
        }
    }

    private static void addFoodOutput() {
        KnowledgeListMFR.cheeseOut = GameRegistry.addShapedRecipe(new ItemStack(BlockListMFR.CHEESE_WHEEL),
                new Object[]{"F", 'F', FoodListMFR.cheese_pot,});
        KnowledgeListMFR.meatpieOut = GameRegistry.addShapedRecipe(new ItemStack(BlockListMFR.PIE_MEAT),
                new Object[]{"F", 'F', FoodListMFR.pie_meat_cooked});
        KnowledgeListMFR.shepardOut = GameRegistry.addShapedRecipe(new ItemStack(BlockListMFR.PIE_SHEPARDS),
                new Object[]{"F", 'F', FoodListMFR.pie_shepard_cooked});
        KnowledgeListMFR.appleOut = GameRegistry.addShapedRecipe(new ItemStack(BlockListMFR.PIE_APPLE),
                new Object[]{"F", 'F', FoodListMFR.pie_apple_cooked});
        KnowledgeListMFR.berryOut = GameRegistry.addShapedRecipe(new ItemStack(BlockListMFR.PIE_BERRY),
                new Object[]{"F", 'F', FoodListMFR.pie_berry_cooked});
        KnowledgeListMFR.pumpPieOut = GameRegistry.addShapedRecipe(new ItemStack(Items.PUMPKIN_PIE),
                new Object[]{"F", 'F', FoodListMFR.pie_pumpkin_cooked});
    }
}
