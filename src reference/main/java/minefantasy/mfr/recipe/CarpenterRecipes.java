package minefantasy.mfr.recipe;


import minefantasy.mfr.init.ArmourListMFR;
import minefantasy.mfr.init.BlockListMFR;
import minefantasy.mfr.init.FoodListMFR;
import net.minecraftforge.fml.common.registry.GameRegistry;
import minefantasy.mfr.api.MineFantasyRebornAPI;
import minefantasy.mfr.api.crafting.Salvage;
import minefantasy.mfr.recipe.refine.PaintOilRecipe;
import minefantasy.mfr.recipe.refine.QuernRecipes;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.api.rpg.Skill;
import minefantasy.mfr.api.rpg.SkillList;
import minefantasy.mfr.block.decor.BlockWoodDecor;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.CustomToolListMFR;
import minefantasy.mfr.init.ToolListMFR;
import minefantasy.mfr.mechanics.knowledge.KnowledgeListMFR;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class CarpenterRecipes {
    public static final String basic = "step.wood";
    public static final String chopping = "dig.wood";
    public static final String primitive = "minefantasy2:block.craftprimitive";
    public static final String sewing = "step.cloth";
    public static final String stonemason = "minefantasy2:block.hammercarpenter";
    public static final String snipping = "mob.sheep.shear";
    public static final String sawing = "minefantasy2:block.sawcarpenter";
    public static final String grinding = "dig.gravel";
    public static final String nailHammer = "minefantasy2:block.hammercarpenter";
    public static final String woodHammer = "minefantasy2:block.carpentermallet";
    public static final String mixing = "step.wood";
    public static final String spanner = "minefantasy2:block.twistbolt";

    private static final Skill artisanry = SkillList.artisanry;
    private static final Skill engineering = SkillList.engineering;
    private static final Skill construction = SkillList.construction;
    private static final Skill provisioning = SkillList.provisioning;

    public static void init() {
        /*
         * ArrayList<CustomMaterial> wood = CustomMaterial.getList("wood"); Iterator
         * iteratorWood = wood.iterator(); while(iteratorWood.hasNext()) {
         * CustomMaterial customMat = (CustomMaterial) iteratorWood.next();
         *
         * }
         */
        assembleWoodBasic();
        CustomWoodRecipes.init();
        addDusts();
        addWoodworks();
        addStonemason();
        addCooking();
        addMisc();
        addEngineering();
        if (ConfigHardcore.HCCallowRocks) {
            addPrimitive();
        } else {
            addNonPrimitiveStone();
        }
        MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.researchBook), "", sewing, "hands", -1, 1,
                new Object[]{"B", 'B', Items.BOOK,});

        if (ConfigHardcore.HCCallowRocks) {
            KnowledgeListMFR.sharpRocksR = MineFantasyRebornAPI.addCarpenterRecipe(null,
                    new ItemStack(ComponentListMFR.sharp_rock, 8), "", stonemason, "hammer", -1, 10,
                    new Object[]{"S", 'S', Blocks.COBBLESTONE,});

        } else {

        }
        Salvage.addSalvage(ToolListMFR.dryrocks, Blocks.COBBLESTONE);

        KnowledgeListMFR.threadR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ComponentListMFR.thread, 4),
                "commodities", sewing, "hands", -1, 5, new Object[]{"W", "S", 'W', Blocks.WOOL, 'S', Items.STICK,});
        KnowledgeListMFR.stringR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(Items.STRING), "commodities",
                sewing, "hands", -1, 10, new Object[]{"T", "T", "T", "T", 'T', ComponentListMFR.thread});

        KnowledgeListMFR.lStripsR = MineFantasyRebornAPI.addCarpenterRecipe(null,
                new ItemStack(ComponentListMFR.leather_strip, 4), "commodities", snipping, "shears", -1, 10,
                new Object[]{"L", 'L', Items.LEATHER,});

        MineFantasyRebornAPI.addCarpenterRecipe(artisanry, new ItemStack(ToolListMFR.swordTraining), nailHammer, "knife", 1,
                40, new Object[]{"NI  ", "SIII", "NI  ", 'N', ComponentListMFR.nail, 'S', ComponentListMFR.plank, 'I',
                        Blocks.PLANKS,});

        MineFantasyRebornAPI.addCarpenterRecipe(artisanry, new ItemStack(ToolListMFR.waraxeTraining), nailHammer, "knife", 1,
                30, new Object[]{" II ", "SSIN", "  I ", 'N', ComponentListMFR.nail, 'S', ComponentListMFR.plank, 'I',
                        Blocks.PLANKS,});
        MineFantasyRebornAPI.addCarpenterRecipe(artisanry, new ItemStack(ToolListMFR.maceTraining), nailHammer, "knife", 1, 35,
                new Object[]{"  II", "SSII", "  N ", 'N', ComponentListMFR.nail, 'S', ComponentListMFR.plank, 'I',
                        Blocks.PLANKS,});
        MineFantasyRebornAPI.addCarpenterRecipe(artisanry, new ItemStack(ToolListMFR.spearTraining), nailHammer, "knife", 1,
                20, new Object[]{"  N ", "SSSI", "  N ", 'N', ComponentListMFR.nail, 'S', ComponentListMFR.plank, 'I',
                        Blocks.PLANKS,});
        ItemStack scrapWood = ComponentListMFR.plank.construct("ScrapWood");
        Salvage.addSalvage(ToolListMFR.swordTraining, new ItemStack(Blocks.PLANKS, 5),
                new ItemStack(ComponentListMFR.nail, 2), scrapWood);
        Salvage.addSalvage(ToolListMFR.waraxeTraining, new ItemStack(Blocks.PLANKS, 4), ComponentListMFR.nail, scrapWood,
                scrapWood);
        Salvage.addSalvage(ToolListMFR.maceTraining, new ItemStack(Blocks.PLANKS, 4), ComponentListMFR.nail, scrapWood,
                scrapWood);
        Salvage.addSalvage(ToolListMFR.waraxeTraining, Blocks.PLANKS, new ItemStack(ComponentListMFR.nail, 2), scrapWood,
                scrapWood, scrapWood);

        KnowledgeListMFR.badBandageR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(ToolListMFR.bandage_crude, 2), "bandage", sewing, "needle", -1, 10,
                new Object[]{"LLL", 'L', ComponentListMFR.rawhideSmall,});

        MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(ToolListMFR.bandage_crude, 4), "bandage", sewing,
                "needle", -1, 20, new Object[]{"LLL", 'L', ComponentListMFR.rawhideMedium,});
        MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(ToolListMFR.bandage_crude, 6), "bandage", sewing,
                "needle", -1, 30, new Object[]{"LLL", 'L', ComponentListMFR.rawhideLarge,});
        KnowledgeListMFR.bandageR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(ToolListMFR.bandage_wool, 4), "bandage", sewing, "needle", 1, 10,
                new Object[]{"CTC", 'T', ComponentListMFR.thread, 'C', Blocks.WOOL,});

        KnowledgeListMFR.goodBandageR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(ToolListMFR.bandage_tough), "bandageadv", sewing, "needle", 2, 20,
                new Object[]{"T", "L", "B", 'T', ComponentListMFR.thread, 'L', ComponentListMFR.leather_strip, 'B',
                        ToolListMFR.bandage_wool});

        KnowledgeListMFR.roughHelmetR = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                ArmourListMFR.armour(ArmourListMFR.leather, 1, 0), "craftArmourBasic", sewing, "needle", -1, 25,
                new Object[]{"TLT", "S S", 'T', ComponentListMFR.thread, 'S', ComponentListMFR.leather_strip, 'L',
                        Items.LEATHER});
        KnowledgeListMFR.roughChestR = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                ArmourListMFR.armour(ArmourListMFR.leather, 1, 1), "craftArmourBasic", sewing, "needle", -1, 40,
                new Object[]{"S S", "LLL", "TLT", 'T', ComponentListMFR.thread, 'S', ComponentListMFR.leather_strip,
                        'L', Items.LEATHER});
        KnowledgeListMFR.roughLegsR = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                ArmourListMFR.armour(ArmourListMFR.leather, 1, 2), "craftArmourBasic", sewing, "needle", -1, 35,
                new Object[]{"TLT", "L L", "S S", 'T', ComponentListMFR.thread, 'S', ComponentListMFR.leather_strip,
                        'L', Items.LEATHER});
        KnowledgeListMFR.roughBootsR = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                ArmourListMFR.armour(ArmourListMFR.leather, 1, 3), "craftArmourBasic", sewing, "needle", -1, 20,
                new Object[]{"T T", "S S", 'T', ComponentListMFR.thread, 'S', ComponentListMFR.leather_strip,});
        Salvage.addSalvage(ArmourListMFR.armourItem(ArmourListMFR.leather, 1, 0),
                new ItemStack(ComponentListMFR.thread, 2), new ItemStack(ComponentListMFR.leather_strip, 2),
                Items.LEATHER);
        Salvage.addSalvage(ArmourListMFR.armourItem(ArmourListMFR.leather, 1, 1),
                new ItemStack(ComponentListMFR.thread, 4), new ItemStack(ComponentListMFR.leather_strip, 2),
                new ItemStack(Items.LEATHER, 4));
        Salvage.addSalvage(ArmourListMFR.armourItem(ArmourListMFR.leather, 1, 2),
                new ItemStack(ComponentListMFR.thread, 4), new ItemStack(ComponentListMFR.leather_strip, 2),
                new ItemStack(Items.LEATHER, 3));
        Salvage.addSalvage(ArmourListMFR.armourItem(ArmourListMFR.leather, 1, 3),
                new ItemStack(ComponentListMFR.thread, 4), new ItemStack(ComponentListMFR.leather_strip, 2));

        KnowledgeListMFR.reHelmetR = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                ArmourListMFR.armour(ArmourListMFR.leather, 2, 0), "craftArmourLight", sewing, "needle", 1, 50,
                new Object[]{"TTT", "UPU", 'T', ComponentListMFR.thread, 'P',
                        ArmourListMFR.armour(ArmourListMFR.leather, 1, 0), 'U', Items.LEATHER});
        KnowledgeListMFR.reChestR = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                ArmourListMFR.armour(ArmourListMFR.leather, 2, 1), "craftArmourLight", sewing, "needle", 1, 80,
                new Object[]{"TTT", "UPU", 'T', ComponentListMFR.thread, 'P',
                        ArmourListMFR.armour(ArmourListMFR.leather, 1, 1), 'U', Items.LEATHER});
        KnowledgeListMFR.reLegsR = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                ArmourListMFR.armour(ArmourListMFR.leather, 2, 2), "craftArmourLight", sewing, "needle", 1, 70,
                new Object[]{"TTT", "UPU", 'T', ComponentListMFR.thread, 'P',
                        ArmourListMFR.armour(ArmourListMFR.leather, 1, 2), 'U', Items.LEATHER});
        KnowledgeListMFR.reBootsR = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                ArmourListMFR.armour(ArmourListMFR.leather, 2, 3), "craftArmourLight", sewing, "needle", 1, 40,
                new Object[]{"TTT", "UPU", 'T', ComponentListMFR.thread, 'P',
                        ArmourListMFR.armour(ArmourListMFR.leather, 1, 3), 'U', Items.LEATHER});
        Salvage.addSalvage(ArmourListMFR.armourItem(ArmourListMFR.leather, 2, 0),
                ArmourListMFR.armourItem(ArmourListMFR.leather, 1, 0), new ItemStack(ComponentListMFR.thread, 3),
                new ItemStack(Items.LEATHER, 2));
        Salvage.addSalvage(ArmourListMFR.armourItem(ArmourListMFR.leather, 2, 1),
                ArmourListMFR.armourItem(ArmourListMFR.leather, 1, 1), new ItemStack(ComponentListMFR.thread, 3),
                new ItemStack(Items.LEATHER, 2));
        Salvage.addSalvage(ArmourListMFR.armourItem(ArmourListMFR.leather, 2, 2),
                ArmourListMFR.armourItem(ArmourListMFR.leather, 1, 2), new ItemStack(ComponentListMFR.thread, 3),
                new ItemStack(Items.LEATHER, 2));
        Salvage.addSalvage(ArmourListMFR.armourItem(ArmourListMFR.leather, 2, 3),
                ArmourListMFR.armourItem(ArmourListMFR.leather, 1, 3), new ItemStack(ComponentListMFR.thread, 3),
                new ItemStack(Items.LEATHER, 2));

        // PADDING
        KnowledgeListMFR.padding[0] = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                ArmourListMFR.armour(ArmourListMFR.leather, 4, 0), "craftArmourLight", sewing, "needle", 1, 50,
                new Object[]{" W ", "SPS", " S ", 'P', ArmourListMFR.armour(ArmourListMFR.leather, 1, 0), 'W',
                        Blocks.WOOL, 'S', ComponentListMFR.thread,});
        KnowledgeListMFR.padding[1] = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                ArmourListMFR.armour(ArmourListMFR.leather, 4, 1), "craftArmourLight", sewing, "needle", 1, 80,
                new Object[]{" W ", "SPS", " S ", 'P', ArmourListMFR.armour(ArmourListMFR.leather, 1, 1), 'W',
                        Blocks.WOOL, 'S', ComponentListMFR.thread,});
        KnowledgeListMFR.padding[2] = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                ArmourListMFR.armour(ArmourListMFR.leather, 4, 2), "craftArmourLight", sewing, "needle", 1, 70,
                new Object[]{" W ", "SPS", " S ", 'P', ArmourListMFR.armour(ArmourListMFR.leather, 1, 2), 'W',
                        Blocks.WOOL, 'S', ComponentListMFR.thread,});
        KnowledgeListMFR.padding[3] = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                ArmourListMFR.armour(ArmourListMFR.leather, 4, 3), "craftArmourLight", sewing, "needle", 1, 40,
                new Object[]{" W ", "SPS", " S ", 'P', ArmourListMFR.armour(ArmourListMFR.leather, 1, 3), 'W',
                        Blocks.WOOL, 'S', ComponentListMFR.thread,});
        Salvage.addSalvage(ArmourListMFR.armourItem(ArmourListMFR.leather, 4, 0),
                ArmourListMFR.armourItem(ArmourListMFR.leather, 1, 0), new ItemStack(ComponentListMFR.thread, 3),
                Blocks.WOOL);
        Salvage.addSalvage(ArmourListMFR.armourItem(ArmourListMFR.leather, 4, 1),
                ArmourListMFR.armourItem(ArmourListMFR.leather, 1, 1), new ItemStack(ComponentListMFR.thread, 3),
                Blocks.WOOL);
        Salvage.addSalvage(ArmourListMFR.armourItem(ArmourListMFR.leather, 4, 2),
                ArmourListMFR.armourItem(ArmourListMFR.leather, 1, 2), new ItemStack(ComponentListMFR.thread, 3),
                Blocks.WOOL);
        Salvage.addSalvage(ArmourListMFR.armourItem(ArmourListMFR.leather, 4, 3),
                ArmourListMFR.armourItem(ArmourListMFR.leather, 1, 3), new ItemStack(ComponentListMFR.thread, 3),
                Blocks.WOOL);

        KnowledgeListMFR.repairBasicR = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                new ItemStack(BlockListMFR.REPAIR_BASIC), "repair_basic", sewing, "needle", 1, 20,
                new Object[]{"TTT", "FNH", "SLS", 'T', ComponentListMFR.thread, 'S', ComponentListMFR.leather_strip,
                        'L', Items.LEATHER, 'F', Items.FLINT, 'H', CustomToolListMFR.standard_hammer, 'N',
                        ComponentListMFR.nail,});
        ItemStack bronzePlate = ComponentListMFR.plate.createComm("bronze");
        KnowledgeListMFR.repairAdvancedR = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                new ItemStack(BlockListMFR.REPAIR_ADVANCED), "repair_advanced", sewing, "needle", 2, 50,
                new Object[]{"SCS", "PKH", "CSC", 'K', BlockListMFR.REPAIR_BASIC, 'P', bronzePlate, 'H',
                        CustomToolListMFR.standard_hammer, 'C', Items.SLIME_BALL, 'S', Items.STRING,});
        KnowledgeListMFR.repairOrnateR = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                new ItemStack(BlockListMFR.REPAIR_ORNATE), "repair_ornate", sewing, "needle", 3, 100,
                new Object[]{"GDG", "LKL", "GLG", 'K', BlockListMFR.REPAIR_ADVANCED, 'G', Items.GOLD_INGOT, 'L',
                        new ItemStack(Items.DYE, 1, 4), 'D', Items.DIAMOND,});

        Salvage.addSalvage(BlockListMFR.REPAIR_BASIC, new ItemStack(ComponentListMFR.thread, 3), ComponentListMFR.nail,
                Items.FLINT, Items.LEATHER, new ItemStack(ComponentListMFR.leather_strip, 2));
        Salvage.addSalvage(BlockListMFR.REPAIR_ADVANCED, BlockListMFR.REPAIR_BASIC, bronzePlate,
                new ItemStack(Items.SLIME_BALL, 3), new ItemStack(Items.STRING, 3));
        Salvage.addSalvage(BlockListMFR.REPAIR_ORNATE, BlockListMFR.REPAIR_ADVANCED, new ItemStack(Items.GOLD_INGOT, 4),
                Items.DIAMOND, new ItemStack(Items.DYE, 3, 4));

        KnowledgeListMFR.trilogyRecipe = MineFantasyRebornAPI.addShapelessCarpenterRecipe(artisanry,
                new ItemStack(ComponentListMFR.artefacts, 1, 3), "smeltMaster", basic, "hands", -1, 1,
                new Object[]{new ItemStack(ComponentListMFR.artefacts, 1, 0),
                        new ItemStack(ComponentListMFR.artefacts, 1, 1),
                        new ItemStack(ComponentListMFR.artefacts, 1, 2)});
    }

    public static void assembleWoodBasic() {
        KnowledgeListMFR.carpenterRecipe = GameRegistry.addShapedRecipe(new ItemStack(BlockListMFR.CARPENTER),
                new Object[]{"PBP", "P P", 'B', Blocks.CRAFTING_TABLE, 'P', ComponentListMFR.plank});
        Salvage.addSalvage(BlockListMFR.CARPENTER, ComponentListMFR.plank.construct("ScrapWood", 4),
                Blocks.CRAFTING_TABLE);

        KnowledgeListMFR.nailPlanksR = MineFantasyRebornAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMFR.NAILED_PLANKS), "refined_planks", nailHammer, "hammer", 1, 5,
                new Object[]{"N ", "PP", "PP", 'N', ComponentListMFR.nail, 'P',
                        ComponentListMFR.plank.construct("OakWood"),});
        KnowledgeListMFR.nailStairR = MineFantasyRebornAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMFR.NAILED_PLANKS_STAIR), "refined_planks", nailHammer, "hammer", 1, 5,
                new Object[]{"N ", "P ", "PP", 'N', ComponentListMFR.nail, 'P',
                        ComponentListMFR.plank.construct("OakWood"),});
        KnowledgeListMFR.tannerRecipe = MineFantasyRebornAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMFR.TANNER), "", nailHammer, "hammer", -1, 10,
                new Object[]{"PPP", "P P", "PPP", 'P', ComponentListMFR.plank,});

        KnowledgeListMFR.clayWallR = MineFantasyRebornAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMFR.CLAY_WALL, 4), "clay_wall", nailHammer, "hammer", 1, 2, new Object[]{"NPN",
                        "PCP", "NPN", 'N', ComponentListMFR.nail, 'P', ComponentListMFR.plank, 'C', Blocks.CLAY});

        KnowledgeListMFR.researchTableRecipe = MineFantasyRebornAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMFR.RESEARCH), "", nailHammer, "hammer", -1, 10,
                new Object[]{"B", "C", 'B', ToolListMFR.researchBook, 'C', BlockListMFR.CARPENTER,});
        KnowledgeListMFR.bSalvageR = MineFantasyRebornAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMFR.SALVAGE_BASIC), "", nailHammer, "hammer", -1, 10, new Object[]{"SFS", "PWP",
                        'W', Blocks.CRAFTING_TABLE, 'S', Blocks.STONE, 'F', Items.FLINT, 'P', ComponentListMFR.plank});

        KnowledgeListMFR.framedGlassR = MineFantasyRebornAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMFR.FRAMED_GLASS), "", nailHammer, "hammer", -1, 10,
                new Object[]{"PGP", 'P', ComponentListMFR.plank, 'G', Blocks.GLASS});
        KnowledgeListMFR.windowR = MineFantasyRebornAPI.addCarpenterRecipe(construction, new ItemStack(BlockListMFR.WINDOW), "",
                nailHammer, "hammer", -1, 10,
                new Object[]{" P ", "PGP", " P ", 'P', ComponentListMFR.plank, 'G', Blocks.GLASS});

        Salvage.addSalvage(BlockListMFR.FRAMED_GLASS, ComponentListMFR.plank.construct("ScrapWood", 2), Blocks.GLASS);
        Salvage.addSalvage(BlockListMFR.WINDOW, ComponentListMFR.plank.construct("ScrapWood", 4), Blocks.GLASS);
        Salvage.addSalvage(BlockListMFR.CLAY_WALL, ComponentListMFR.nail, ComponentListMFR.plank.construct("ScrapWood"),
                Items.CLAY_BALL);
        Salvage.addSalvage(BlockListMFR.TANNER, ComponentListMFR.plank.construct("ScrapWood", 8));
        Salvage.addSalvage(BlockListMFR.RESEARCH, BlockListMFR.CARPENTER);
        Salvage.addSalvage(BlockListMFR.SALVAGE_BASIC, Items.FLINT, new ItemStack(Blocks.STONE, 2),
                ComponentListMFR.plank.construct("ScrapWood", 2), Blocks.CRAFTING_TABLE);
    }

    private static void addDusts() {
        QuernRecipes.addRecipe(new ItemStack(Items.DYE, 1, 3), new ItemStack(FoodListMFR
                .coca_powder), 0, true);// ItemDye
        QuernRecipes.addRecipe(Items.WHEAT, new ItemStack(FoodListMFR
                .flour), 0, true);
        QuernRecipes.addRecipe(Items.REEDS, new ItemStack(FoodListMFR
                .sugarpot), 0, true);
        QuernRecipes.addRecipe(FoodListMFR
                .breadroll, new ItemStack(FoodListMFR
                .breadcrumbs), 0, true);

        QuernRecipes.addRecipe(FoodListMFR
                        .generic_meat_uncooked, new ItemStack(FoodListMFR
                        .generic_meat_mince_uncooked),
                0, true);
        QuernRecipes.addRecipe(FoodListMFR
                        .generic_meat_strip_uncooked,
                new ItemStack(FoodListMFR
                        .generic_meat_mince_uncooked), 0, true);
        QuernRecipes.addRecipe(FoodListMFR
                        .generic_meat_chunk_uncooked,
                new ItemStack(FoodListMFR
                        .generic_meat_mince_uncooked), 0, true);
        QuernRecipes.addRecipe(FoodListMFR
                .generic_meat_cooked, new ItemStack(FoodListMFR
                .generic_meat_mince_cooked), 0, true);
        QuernRecipes.addRecipe(FoodListMFR
                        .generic_meat_strip_cooked,
                new ItemStack(FoodListMFR
                        .generic_meat_mince_cooked), 0, true);
        QuernRecipes.addRecipe(FoodListMFR
                        .generic_meat_chunk_cooked,
                new ItemStack(FoodListMFR
                        .generic_meat_mince_cooked), 0, true);

        QuernRecipes.addRecipe(Items.COAL, new ItemStack(ComponentListMFR.coalDust), 0, true);
        QuernRecipes.addRecipe(new ItemStack(Items.COAL, 1, 1), new ItemStack(ComponentListMFR.coalDust), 0, true);
        QuernRecipes.addRecipe(ComponentListMFR.kaolinite, new ItemStack(ComponentListMFR.kaolinite_dust), 0, true);
        QuernRecipes.addRecipe(Items.FLINT, new ItemStack(ComponentListMFR.shrapnel), 0, true);

        QuernRecipes.addRecipe(ComponentListMFR.flux, new ItemStack(ComponentListMFR.flux_pot), 0, true);

        KnowledgeListMFR.pieTrayRecipe = MineFantasyRebornAPI.addCarpenterRecipe(null,
                new ItemStack(ComponentListMFR.pie_tray_uncooked), "", basic, "hands", -1, 10,
                new Object[]{"CC", 'C', Items.CLAY_BALL,});

        KnowledgeListMFR.potRecipe = MineFantasyRebornAPI.addCarpenterRecipe(null,
                new ItemStack(ComponentListMFR.clay_pot_uncooked, 8), "", basic, "hands", -1, 5,
                new Object[]{"C  C", " CC ", 'C', Items.CLAY_BALL,});
        KnowledgeListMFR.mouldRecipe = MineFantasyRebornAPI.addCarpenterRecipe(null,
                new ItemStack(ComponentListMFR.ingot_mould_uncooked), "crucible", basic, "hands", -1, 10,
                new Object[]{"CCC", " C ", 'C', Items.CLAY_BALL,});
        KnowledgeListMFR.jugRecipe = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(FoodListMFR
                        .jug_uncooked, 4),
                "", basic, "hands", -1, 8, new Object[]{"C  ", "C C", " C ", 'C', Items.CLAY_BALL,});
        KnowledgeListMFR.blackpowderRec = MineFantasyRebornAPI.addCarpenterRecipe(null,
                new ItemStack(ComponentListMFR.blackpowder, 2), "blackpowder", basic, "hands", -1, 2,
                new Object[]{"NS", "CC", "PP", 'C', ComponentListMFR.coalDust, 'N', ComponentListMFR.nitre, 'S',
                        ComponentListMFR.sulfur, 'P', ComponentListMFR.clay_pot,});
        KnowledgeListMFR.crudeBombR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ToolListMFR.bomb_crude), "blackpowder", primitive, "hands", -1, 5,
                new Object[]{"T", "B", "P",

                        'B', ComponentListMFR.blackpowder, 'T', ComponentListMFR.thread, 'P', Items.PAPER,});
        KnowledgeListMFR.advblackpowderRec = MineFantasyRebornAPI.addCarpenterRecipe(null,
                new ItemStack(ComponentListMFR.blackpowder_advanced), "advblackpowder", basic, "hands", -1, 10,
                new Object[]{" B ", "RGR", " P ", 'B', ComponentListMFR.blackpowder, 'G', Items.GLOWSTONE_DUST, 'R',
                        Items.REDSTONE, 'P', ComponentListMFR.clay_pot,});
        KnowledgeListMFR.magmaRefinedR = MineFantasyRebornAPI.addCarpenterRecipe(null,
                new ItemStack(ComponentListMFR.magma_cream_refined), "firebomb", grinding, "pestle", -1, 10,
                new Object[]{"B", "H", "C", "P", 'H', ComponentListMFR.dragon_heart, 'B', Items.BLAZE_POWDER, 'C',
                        Items.MAGMA_CREAM, 'P', ComponentListMFR.clay_pot,});
        Salvage.addSalvage(ComponentListMFR.magma_cream_refined, ComponentListMFR.dragon_heart, Items.BLAZE_POWDER,
                Items.MAGMA_CREAM, ComponentListMFR.clay_pot);
    }

    private static void addWoodworks() {
        KnowledgeListMFR.refinedPlankBlockR = MineFantasyRebornAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMFR.REFINED_PLANKS), "refined_planks", nailHammer, "hammer", 1, 10,
                new Object[]{"N ", "PP", "PP", 'N', ComponentListMFR.nail, 'P',
                        ComponentListMFR.plank.construct("RefinedWood"),});

        KnowledgeListMFR.refinedStairR = MineFantasyRebornAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMFR.REFINED_PLANKS_STAIR), "refined_planks", nailHammer, "hammer", 1, 10,
                new Object[]{"N ", "P ", "PP", 'N', ComponentListMFR.nail, 'P',
                        ComponentListMFR.plank.construct("RefinedWood"),});
        Salvage.addSalvage(BlockListMFR.NAILED_PLANKS, ComponentListMFR.nail,
                ComponentListMFR.plank.construct("ScrapWood", 4));
        Salvage.addSalvage(BlockListMFR.REFINED_PLANKS, ComponentListMFR.nail,
                ComponentListMFR.plank.construct("RefinedWood", 4));
        Salvage.addSalvage(BlockListMFR.NAILED_PLANKS_STAIR, ComponentListMFR.nail,
                ComponentListMFR.plank.construct("ScrapWood", 3));
        Salvage.addSalvage(BlockListMFR.REFINED_PLANKS_STAIR, ComponentListMFR.nail,
                ComponentListMFR.plank.construct("RefinedWood", 3));

        KnowledgeListMFR.bellowsRecipe = MineFantasyRebornAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMFR.BELLOWS), "", nailHammer, "hammer", 1, 50,
                new Object[]{"NNN", "PPP", "LL ", "PP ", 'N', ComponentListMFR.nail, 'P',
                        ComponentListMFR.plank.construct("RefinedWood"), 'L', Items.LEATHER,});
        Salvage.addSalvage(BlockListMFR.BELLOWS, new ItemStack(ComponentListMFR.nail, 3),
                ComponentListMFR.plank.construct("RefinedWood", 5), new ItemStack(Items.LEATHER, 2));

        KnowledgeListMFR.woodTroughRecipe = MineFantasyRebornAPI.addCarpenterRecipe(construction,
                ((BlockWoodDecor) BlockListMFR.TROUGH_WOOD).construct("ScrapWood"), "", nailHammer, "hammer", -1, 20,
                new Object[]{"P P", "PPP",

                        'P', ComponentListMFR.plank,});

        KnowledgeListMFR.strongRackR = MineFantasyRebornAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMFR.ADV_TANNER), "", nailHammer, "hammer", 1, 80,
                new Object[]{"NNN", "PPP", "P P", "PPP", 'N', ComponentListMFR.nail, 'P',
                        ComponentListMFR.plank.construct("RefinedWood"),});
        Salvage.addSalvage(BlockListMFR.ADV_TANNER, ComponentListMFR.plank.construct("RefinedWood", 8),
                new ItemStack(ComponentListMFR.nail, 3));

        MineFantasyRebornAPI.addCarpenterRecipe(construction, new ItemStack(BlockListMFR.REFINED_PLANKS), "paint_brush",
                sewing, "brush", -1, 3,
                new Object[]{"O", "P", 'O', ComponentListMFR.plant_oil, 'P', BlockListMFR.NAILED_PLANKS,});

        PaintOilRecipe.addRecipe(BlockListMFR.NAILED_PLANKS, BlockListMFR.REFINED_PLANKS);
        PaintOilRecipe.addRecipe(BlockListMFR.NAILED_PLANKS_STAIR, BlockListMFR.REFINED_PLANKS_STAIR);

    }

    private static void addStonemason() {
        KnowledgeListMFR.quernR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(BlockListMFR.QUERN), "",
                stonemason, "hammer", -1, 10, new Object[]{"FSF", "SSS", 'F', Items.FLINT, 'S', Blocks.STONE,});
        KnowledgeListMFR.stoneovenRecipe = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(BlockListMFR.OVEN_STONE),
                "", stonemason, "hammer", -1, 10,
                new Object[]{"S", "C", 'C', BlockListMFR.ROAST, 'S', Blocks.STONE,});

        KnowledgeListMFR.bloomeryR = MineFantasyRebornAPI.addCarpenterRecipe(artisanry, new ItemStack(BlockListMFR.BLOOMERY),
                "bloomery", stonemason, "hammer", -1, 10,
                new Object[]{" S ", "S S", "SCS", 'C', Blocks.COAL_BLOCK, 'S', Blocks.STONE,});
        KnowledgeListMFR.crucibleRecipe = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                new ItemStack(BlockListMFR.CRUCIBLE), "crucible", stonemason, "hammer", -1, 20,
                new Object[]{"SSS", "S S", "SSS", 'S', Blocks.STONE,});
        KnowledgeListMFR.advCrucibleRecipe = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                new ItemStack(BlockListMFR.CRUCIBLE_ADV), "crucible2", basic, 40,
                new Object[]{"SSS", "SCS", "SSS", 'S', ComponentListMFR.fireclay, 'C', BlockListMFR.CRUCIBLE});
        Salvage.addSalvage(BlockListMFR.CRUCIBLE, new ItemStack(Blocks.STONE, 8));
        Salvage.addSalvage(BlockListMFR.CRUCIBLE_ADV, new ItemStack(ComponentListMFR.fireclay, 8), BlockListMFR.CRUCIBLE);

        KnowledgeListMFR.chimneyRecipe = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                new ItemStack(BlockListMFR.CHIMNEY_STONE, 8), "", stonemason, "hammer", -1, 30,
                new Object[]{"S S", "S S", "S S", 'S', Blocks.STONE,});
        KnowledgeListMFR.wideChimneyRecipe = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                new ItemStack(BlockListMFR.CHIMNEY_STONE_WIDE), "", stonemason, "hammer", -1, 10,
                new Object[]{"S", "C", 'C', BlockListMFR.CHIMNEY_STONE, 'S', Blocks.STONE,});
        KnowledgeListMFR.extractChimneyRecipe = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                new ItemStack(BlockListMFR.CHIMNEY_STONE_EXTRACTOR), "", stonemason, "hammer", -1, 15,
                new Object[]{"C", 'C', BlockListMFR.CHIMNEY_STONE_WIDE,});

        KnowledgeListMFR.stoneAnvilRecipe = MineFantasyRebornAPI.addCarpenterRecipe(null,
                new ItemStack(BlockListMFR.ANVIL_STONE), "", stonemason, "hammer", -1, 10,
                new Object[]{"SS ", "SSS", " S ", 'S', Blocks.STONE});
        KnowledgeListMFR.forgeRecipe = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(BlockListMFR.FORGE), "",
                stonemason, "hammer", -1, 10, new Object[]{"S S", "SCS", 'C', Items.COAL, 'S', Blocks.STONE});
        Salvage.addSalvage(BlockListMFR.FORGE, new ItemStack(Blocks.STONE, 4), Items.COAL);
        Salvage.addSalvage(BlockListMFR.ANVIL_STONE, new ItemStack(Blocks.STONE, 6));

        Salvage.addSalvage(BlockListMFR.CHIMNEY_STONE, Blocks.STONE);
        Salvage.addSalvage(BlockListMFR.CHIMNEY_STONE_WIDE, BlockListMFR.CHIMNEY_STONE, Blocks.STONE);
        Salvage.addSalvage(BlockListMFR.CHIMNEY_STONE_EXTRACTOR, BlockListMFR.CHIMNEY_STONE_WIDE);
        Salvage.addSalvage(BlockListMFR.QUERN, new ItemStack(Items.FLINT, 2), new ItemStack(Blocks.STONE, 4));
    }

    private static void addCooking() {
        String meatRaw = "rawMeat";
        String cookedMeat = "cookedMeat";
        OreDictionary.registerOre(cookedMeat, Items.COOKED_BEEF);
        OreDictionary.registerOre(cookedMeat, Items.COOKED_CHICKEN);
        OreDictionary.registerOre(cookedMeat, Items.COOKED_PORKCHOP);
        OreDictionary.registerOre(cookedMeat, FoodListMFR
                .wolf_cooked);
        OreDictionary.registerOre(cookedMeat, FoodListMFR
                .horse_cooked);
        OreDictionary.registerOre(cookedMeat, Items.COOKED_FISH);
        OreDictionary.registerOre(cookedMeat, new ItemStack(Items.COOKED_FISH, 1, 1));
        addOreD("listAllporkcooked", cookedMeat);
        addOreD("listAllmuttoncooked", cookedMeat);
        addOreD("listAllbeefcooked", cookedMeat);
        addOreD("listAllchickencooked", cookedMeat);
        addOreD("listAllfishcooked", cookedMeat);

        OreDictionary.registerOre(meatRaw, FoodListMFR
                .guts);
        OreDictionary.registerOre(meatRaw, Items.BEEF);
        OreDictionary.registerOre(meatRaw, Items.CHICKEN);
        OreDictionary.registerOre(meatRaw, Items.PORKCHOP);
        OreDictionary.registerOre(meatRaw, FoodListMFR
                .wolf_raw);
        OreDictionary.registerOre(meatRaw, FoodListMFR
                .horse_raw);
        OreDictionary.registerOre(meatRaw, Items.FISH);
        OreDictionary.registerOre(meatRaw, new ItemStack(Items.FISH, 1, 1));
        addOreD("listAllporkraw", meatRaw);
        addOreD("listAllmuttonraw", meatRaw);
        addOreD("listAllbeefraw", meatRaw);
        addOreD("listAllchickenraw", meatRaw);
        addOreD("listAllfishraw", meatRaw);

        KnowledgeListMFR.curdRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMFR
                        .curds),
                "", basic, "hands", -1, 10, new Object[]{"T", "S", "M", "P", 'P', ComponentListMFR.clay_pot, 'T',
                        FoodListMFR
                                .salt, 'S', FoodListMFR
                        .sugarpot, 'M', FoodListMFR
                        .jug_milk,});

        KnowledgeListMFR.oatsRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMFR
                        .oats), "",
                chopping, "knife", -1, 20, new Object[]{"M", "W", "S", "B", 'S', Items.WHEAT_SEEDS, 'W', Items.WHEAT,
                        'M', FoodListMFR
                        .jug_milk, 'B', Items.BOWL});
        KnowledgeListMFR.doughRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMFR
                        .dough),
                "", basic, "hands", -1, 10,
                new Object[]{"W", "F", 'W', FoodListMFR
                        .jug_water, 'F', FoodListMFR
                        .flour,});
        KnowledgeListMFR.pastryRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMFR
                        .pastry),
                "", basic, "hands", -1, 10,
                new Object[]{" S ", "FEF", 'F', FoodListMFR
                        .flour, 'E', Items.EGG, 'S', FoodListMFR
                        .salt,});
        KnowledgeListMFR.breadRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR
                        .raw_bread), "", basic, "hands", -1, 15,
                new Object[]{"DDD", 'D', FoodListMFR
                        .dough,});
        KnowledgeListMFR.sweetrollRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR
                        .sweetroll_raw), "sweetroll", basic, 5,
                new Object[]{" M ", "FES", "BBB", 'M', FoodListMFR
                        .jug_milk, 'S', FoodListMFR
                        .sugarpot, 'B',
                        FoodListMFR
                                .berries, 'E', Items.EGG, 'F', FoodListMFR
                        .flour,});
        KnowledgeListMFR.icingRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMFR
                        .icing),
                "", mixing, "spoon", -1, 10, new Object[]{"W", "S", "B", 'W', FoodListMFR
                        .jug_water, 'S',
                        FoodListMFR
                                .sugarpot, 'B', ComponentListMFR.clay_pot,});
        KnowledgeListMFR.chocoRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR
                        .chocolate), "icing", mixing, "spoon", -1, 10,
                new Object[]{" M ", "SCS", " B ", 'C', FoodListMFR
                        .coca_powder, 'M', FoodListMFR
                        .jug_milk, 'S',
                        FoodListMFR
                                .sugarpot, 'B', ComponentListMFR.clay_pot,});
        KnowledgeListMFR.custardRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR
                        .custard), "icing", mixing, "spoon", -1, 10,
                new Object[]{" M ", "SES", " B ", 'E', Items.EGG, 'M', FoodListMFR
                        .jug_milk, 'S', FoodListMFR
                        .sugarpot,
                        'B', ComponentListMFR.clay_pot,});
        KnowledgeListMFR.iceSR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMFR
                        .sweetroll),
                "sweetroll", basic, "knife", -1, 15,
                new Object[]{"I", "R", 'I', FoodListMFR
                        .icing, 'R', FoodListMFR
                        .sweetroll_uniced,});
        KnowledgeListMFR.eclairDoughR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR
                        .eclair_raw), "eclair", basic, 8,
                new Object[]{"SSS", "PPP", 'P', FoodListMFR
                        .pastry, 'S', FoodListMFR
                        .sugarpot,});
        KnowledgeListMFR.eclairIceR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR
                        .eclair_empty), "eclair", basic, "knife", 2, 20,
                new Object[]{"C", "E", 'C', FoodListMFR
                        .chocolate, 'E', FoodListMFR
                        .eclair_uniced,});
        KnowledgeListMFR.eclairFillR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMFR
                        .eclair),
                "eclair", basic, "knife", 2, 20,
                new Object[]{"C", "E", 'C', FoodListMFR
                        .custard, 'E', FoodListMFR
                        .eclair_empty,});
        for (ItemStack food : OreDictionary.getOres(meatRaw)) {
            int size = getSize(food);
            KnowledgeListMFR.meatRecipes.add(MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                    new ItemStack(FoodListMFR
                            .generic_meat_uncooked, size), "", chopping, "knife", -1, 15,
                    new Object[]{"M", 'M', food,}));
        }
        for (ItemStack food : OreDictionary.getOres(cookedMeat)) {
            int size = 1;
            KnowledgeListMFR.meatRecipes.add(
                    MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMFR
                                    .generic_meat_cooked, size),
                            "", chopping, "knife", -1, 15, new Object[]{"M", 'M', food,}));
        }
        KnowledgeListMFR.meatStripR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.generic_meat_strip_uncooked), "", chopping, "knife", -1, 5,
                new Object[]{"M", 'M', FoodListMFR.generic_meat_uncooked,});
        MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMFR.generic_meat_strip_cooked), "",
                chopping, "knife", -1, 5, new Object[]{"M", 'M', FoodListMFR.generic_meat_cooked,});
        KnowledgeListMFR.meatHunkR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.generic_meat_chunk_uncooked), "", chopping, "knife", -1, 5,
                new Object[]{"M", 'M', FoodListMFR.generic_meat_strip_uncooked,});
        MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMFR.generic_meat_chunk_cooked), "",
                chopping, "knife", -1, 5, new Object[]{"M", 'M', FoodListMFR.generic_meat_strip_cooked,});
        KnowledgeListMFR.gutsRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMFR.guts), "",
                chopping, "knife", 1, 8, new Object[]{"MMMM", 'M', Items.ROTTEN_FLESH,});

        KnowledgeListMFR.stewRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMFR.stew), "",
                chopping, "knife", -1, 15,
                new Object[]{"M", "B", 'M', FoodListMFR.generic_meat_chunk_cooked, 'B', Items.BOWL});
        KnowledgeListMFR.jerkyRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.jerky, 1), "jerky", chopping, "knife", 2, 20,
                new Object[]{"S", "M", 'S', FoodListMFR.salt, 'M', FoodListMFR.generic_meat_strip_cooked,});
        KnowledgeListMFR.saussageR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.saussage_raw, 4), "saussage", chopping, "knife", 2, 30,
                new Object[]{" G ", "MMM", "BES", 'G', FoodListMFR.guts, 'E', Items.EGG, 'S', FoodListMFR.salt, 'B',
                        FoodListMFR.breadcrumbs, 'M', FoodListMFR.generic_meat_mince_uncooked,});
        KnowledgeListMFR.meatPieRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.pie_meat_uncooked), "meatpie", chopping, "knife", 2, 150,
                new Object[]{" P ", "MMM", " P ", " T ", 'P', FoodListMFR.pastry, 'M',
                        FoodListMFR.generic_meat_mince_cooked, 'T', FoodListMFR.pie_tray,});
        KnowledgeListMFR.breadSliceR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.breadSlice, 12), "", "step.cloth", "knife", -1, 10,
                new Object[]{"B", 'B', Items.BREAD,});
        KnowledgeListMFR.sandwitchRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.sandwitch_meat), "sandwitch", chopping, "hands", -1, 4,
                new Object[]{"B", "C", "M", "B", 'C', FoodListMFR.cheese_slice, 'M', FoodListMFR.generic_meat_cooked,
                        'B', FoodListMFR.breadSlice});
        KnowledgeListMFR.sandwitchBigRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.sandwitch_big), "sandwitchBig", chopping, "knife", 1, 10,
                new Object[]{"CSC", "MBM", 'S', FoodListMFR.salt, 'C', FoodListMFR.cheese_slice, 'M',
                        FoodListMFR.generic_meat_cooked, 'B', Items.BREAD});
        KnowledgeListMFR.shepardRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.pie_shepard_uncooked), "shepardpie", chopping, "knife", 3, 200,
                new Object[]{"PFP", "MMM", "CFC", " T ", 'C', Items.CARROT, 'P', Items.POTATO, 'F', FoodListMFR.pastry,
                        'M', FoodListMFR.generic_meat_mince_cooked, 'T', FoodListMFR.pie_tray,});

        KnowledgeListMFR.appleR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.pie_apple_uncooked), "applepie", chopping, "knife", 2, 120,
                new Object[]{"SPS", "MMM", "SPS", " T ", 'S', FoodListMFR.sugarpot, 'P', FoodListMFR.pastry, 'M',
                        Items.APPLE, 'T', FoodListMFR.pie_tray,});
        KnowledgeListMFR.pumpPieR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.pie_pumpkin_uncooked), "bread", chopping, "knife", 1, 50,
                new Object[]{"SMS", "SPS", " T ", 'S', FoodListMFR.sugarpot, 'P', FoodListMFR.pastry, 'M',
                        Blocks.PUMPKIN, 'T', FoodListMFR.pie_tray,});
        KnowledgeListMFR.berryR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.pie_berry_uncooked), "berrypie", chopping, "knife", 2, 100,
                new Object[]{"SPS", "MMM", "SPS", " T ", 'S', FoodListMFR.sugarpot, 'P', FoodListMFR.pastry, 'M',
                        FoodListMFR.berries, 'T', FoodListMFR.pie_tray,});

        KnowledgeListMFR.simpCakeR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.cake_simple_raw), "bread", mixing, "spoon", -1, 15,
                new Object[]{"MMM", "SES", "FFF", " T ", 'F', FoodListMFR.flour, 'E', Items.EGG, 'M',
                        FoodListMFR.jug_milk, 'S', FoodListMFR.sugarpot, 'T', FoodListMFR.cake_tin,});

        KnowledgeListMFR.cakeR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMFR.cake_raw),
                "cake", mixing, "spoon", -1, 20, new Object[]{"SMS", "SES", "FFF", " T ", 'F', FoodListMFR.flour, 'E',
                        Items.EGG, 'M', FoodListMFR.jug_milk, 'S', FoodListMFR.sugarpot, 'T', FoodListMFR.cake_tin,});
        KnowledgeListMFR.carrotCakeR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.cake_carrot_raw), "carrotcake", mixing, "spoon", -1, 25,
                new Object[]{"SMS", "SES", "CCC", "FTF", 'C', Items.CARROT, 'F', FoodListMFR.flour, 'E', Items.EGG,
                        'M', FoodListMFR.jug_milk, 'S', FoodListMFR.sugarpot, 'T', FoodListMFR.cake_tin,});
        KnowledgeListMFR.chocoCakeR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.cake_choc_raw), "chococake", mixing, "spoon", -1, 25,
                new Object[]{"SMS", "SES", "CCC", "FTF", 'C', FoodListMFR.chocolate, 'F', FoodListMFR.flour, 'E',
                        Items.EGG, 'M', FoodListMFR.jug_milk, 'S', FoodListMFR.sugarpot, 'T', FoodListMFR.cake_tin,});
        KnowledgeListMFR.bfCakeR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMFR.cake_bf_raw),
                "bfcake", mixing, "spoon", -1, 30,
                new Object[]{"SMMS", "SEES", "CBBC", "FTFF", 'B', FoodListMFR.berriesJuicy, 'C', FoodListMFR.chocolate,
                        'F', FoodListMFR.flour, 'E', Items.EGG, 'M', FoodListMFR.jug_milk, 'S', FoodListMFR.sugarpot, 'T',
                        FoodListMFR.cake_tin,});
        KnowledgeListMFR.simpCakeOut = MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(Items.CAKE),
                "bread", basic, "knife", -1, 10,
                new Object[]{"I", "R", 'I', FoodListMFR.icing, 'R', FoodListMFR.cake_simple_uniced,});

        KnowledgeListMFR.cakeI = MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(BlockListMFR.CAKE_VANILLA),
                "cake", basic, "knife", -1, 60,
                new Object[]{"III", " R ", 'I', FoodListMFR.icing, 'R', FoodListMFR.cake_uniced,});
        KnowledgeListMFR.carrotCakeI = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(BlockListMFR.CAKE_CARROT), "carrotcake", basic, "knife", -1, 60,
                new Object[]{"III", " R ", 'I', FoodListMFR.icing, 'R', FoodListMFR.cake_carrot_uniced,});
        KnowledgeListMFR.chocoCakeI = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(BlockListMFR.CAKE_CHOCOLATE), "chococake", basic, "knife", -1, 60, new Object[]{"ICI",
                        " R ", 'C', FoodListMFR.chocolate, 'I', FoodListMFR.icing, 'R', FoodListMFR.cake_choc_uniced,});
        KnowledgeListMFR.bfCakeI = MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(BlockListMFR.CAKE_BF),
                "bfcake", basic, "knife", -1, 100, new Object[]{"BBB", "III", "CRC", 'C', FoodListMFR.chocolate, 'B',
                        FoodListMFR.berries, 'I', FoodListMFR.icing, 'R', FoodListMFR.cake_bf_uniced,});

        KnowledgeListMFR.cheeserollR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.cheese_roll), "cheeseroll", chopping, "knife", 1, 30,
                new Object[]{"C", "R", 'C', FoodListMFR.cheese_slice, 'R', FoodListMFR.breadroll,});
    }

    private static void addOreD(String list, String mfList) {
        for (ItemStack stack : OreDictionary.getOres(list)) {
            OreDictionary.registerOre(mfList, stack);
        }
    }

    private static int getSize(ItemStack food) {
        if (food != null && food.getItem() instanceof ItemFood) {
            int feed = ((ItemFood) food.getItem()).func_150905_g(food);
            return Math.max(1, feed - 1);
        }
        return 1;
    }

    private static void addMisc() {
        // Fletching
        KnowledgeListMFR.fletchingR = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                new ItemStack(ComponentListMFR.fletching, 16), "arrows", chopping, 4, new Object[]{"T", "F",

                        'F', Items.FEATHER, 'T', ComponentListMFR.plank,});
        KnowledgeListMFR.fletchingR2 = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                new ItemStack(ComponentListMFR.fletching, 4), "arrows", chopping, 4, new Object[]{" T ", "PPP",

                        'P', Items.PAPER, 'T', ComponentListMFR.plank,});

        // BOMBS
        KnowledgeListMFR.bombCaseCeramicR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.bomb_casing_uncooked, 2), "bombCeramic", basic, 2,
                new Object[]{" C ", "C C", " C ", 'C', Items.CLAY_BALL,});
        KnowledgeListMFR.mineCaseCeramicR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.mine_casing_uncooked), "mineCeramic", basic, 2,
                new Object[]{" P ", "C C", " C ",

                        'P', Blocks.STONE_PRESSURE_PLATE, 'C', Items.CLAY_BALL,});
        KnowledgeListMFR.bombCaseCrystalR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.bomb_casing_crystal), "bombCrystal", basic, 10,
                new Object[]{" D ", "R R", " B ", 'B', Items.GLASS_BOTTLE, 'D', ComponentListMFR.diamond_shards, 'R',
                        Items.REDSTONE});
        KnowledgeListMFR.mineCaseCrystalR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.mine_casing_crystal), "mineCrystal", basic, 10,
                new Object[]{" P ", "RDR", " B ", 'P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 'B', Items.GLASS_BOTTLE,
                        'D', ComponentListMFR.diamond_shards, 'R', Items.REDSTONE});
        Salvage.addSalvage(ComponentListMFR.bomb_casing_uncooked, new ItemStack(Items.CLAY_BALL, 2));
        Salvage.addSalvage(ComponentListMFR.mine_casing_uncooked, new ItemStack(Items.CLAY_BALL, 3),
                Blocks.STONE_PRESSURE_PLATE);

        Salvage.addSalvage(ComponentListMFR.bomb_casing_crystal, Items.GLASS_BOTTLE, ComponentListMFR.diamond_shards,
                new ItemStack(Items.REDSTONE, 2));
        Salvage.addSalvage(ComponentListMFR.mine_casing_crystal, Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE,
                Items.GLASS_BOTTLE, ComponentListMFR.diamond_shards, new ItemStack(Items.REDSTONE, 2));

        KnowledgeListMFR.bombFuseR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.bomb_fuse, 8), "bombs", basic, 4, new Object[]{"R", "C", "S", 'S',
                        ComponentListMFR.thread, 'C', ComponentListMFR.coalDust, 'R', Items.REDSTONE,});
        KnowledgeListMFR.longFuseR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.bomb_fuse_long), "bombs", basic, 1,
                new Object[]{"F", "R", "F", 'F', ComponentListMFR.bomb_fuse, 'R', Items.REDSTONE,});
        Salvage.addSalvage(ComponentListMFR.bomb_fuse_long, new ItemStack(ComponentListMFR.bomb_fuse, 2), Items.REDSTONE);

        KnowledgeListMFR.thatchR = MineFantasyRebornAPI.addCarpenterRecipe(construction, new ItemStack(BlockListMFR.THATCH), "",
                "dig.grass", "hands", -1, 1, new Object[]{"HH", "HH", 'H', new ItemStack(Blocks.TALLGRASS, 1, 1)});
        KnowledgeListMFR.thatchStairR = MineFantasyRebornAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMFR.THATCH_STAIR), "", "dig.grass", "hands", -1, 1,
                new Object[]{"H ", "HH", 'H', new ItemStack(Blocks.TALLGRASS, 1, 1)});
        Salvage.addSalvage(BlockListMFR.THATCH_STAIR, new ItemStack(Blocks.TALLGRASS, 3, 1));
        Salvage.addSalvage(BlockListMFR.THATCH, new ItemStack(Blocks.TALLGRASS, 4, 1));

        KnowledgeListMFR.apronRecipe = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ArmourListMFR.leatherapron),
                "", sewing, "hands", -1, 1, new Object[]{"LCL", " L ", 'L', Items.LEATHER, 'C', Items.COAL,});
        Salvage.addSalvage(ArmourListMFR.leatherapron, new ItemStack(Items.LEATHER, 3), Items.COAL);

        KnowledgeListMFR.hideHelmR = MineFantasyRebornAPI.addCarpenterRecipe(null,
                ArmourListMFR.armour(ArmourListMFR.leather, 0, 0), "", sewing, "hands", -1, 1,
                new Object[]{"H", "C", "H", 'H', ComponentListMFR.hideSmall, 'C', Blocks.WOOL,});
        KnowledgeListMFR.hideChestR = MineFantasyRebornAPI.addCarpenterRecipe(null,
                ArmourListMFR.armour(ArmourListMFR.leather, 0, 1), "", sewing, "hands", -1, 1,
                new Object[]{"H", "C", 'H', ComponentListMFR.hideLarge, 'C', Blocks.WOOL,});
        KnowledgeListMFR.hideLegsR = MineFantasyRebornAPI.addCarpenterRecipe(null,
                ArmourListMFR.armour(ArmourListMFR.leather, 0, 2), "", sewing, "hands", -1, 1,
                new Object[]{"H", "C", 'H', ComponentListMFR.hideMedium, 'C', Blocks.WOOL,});
        KnowledgeListMFR.hideBootsR = MineFantasyRebornAPI.addCarpenterRecipe(null,
                ArmourListMFR.armour(ArmourListMFR.leather, 0, 3), "", sewing, "hands", -1, 1,
                new Object[]{"H", "C", 'H', ComponentListMFR.hideSmall, 'C', Blocks.WOOL,});

        Salvage.addSalvage(ArmourListMFR.armourItem(ArmourListMFR.leather, 0, 0),
                new ItemStack(ComponentListMFR.hideSmall, 2), Blocks.WOOL);
        Salvage.addSalvage(ArmourListMFR.armourItem(ArmourListMFR.leather, 0, 1), ComponentListMFR.hideLarge, Blocks.WOOL);
        Salvage.addSalvage(ArmourListMFR.armourItem(ArmourListMFR.leather, 0, 2), ComponentListMFR.hideMedium,
                Blocks.WOOL);
        Salvage.addSalvage(ArmourListMFR.armourItem(ArmourListMFR.leather, 0, 3), ComponentListMFR.hideSmall, Blocks.WOOL);

        KnowledgeListMFR.bedrollR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.bedroll), "",
                sewing, "needle", -1, 50,
                new Object[]{"TLT", "CCC", 'C', Blocks.WOOL, 'T', ComponentListMFR.thread, 'L', Items.LEATHER});
        Salvage.addSalvage(ToolListMFR.bedroll, new ItemStack(Blocks.WOOL, 3), Items.LEATHER,
                new ItemStack(ComponentListMFR.thread, 3));
    }

    public static void addCrossbows() {
        // CROSSBOWS
        KnowledgeListMFR.crossHandleWoodR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.crossbow_handle_wood), "crossShafts", nailHammer, "hammer", 2, 150,
                new Object[]{"N N", "PP ", " P ", 'P', ComponentListMFR.plank.construct("RefinedWood"), 'N',
                        ComponentListMFR.nail});
        KnowledgeListMFR.crossStockWoodR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.crossbow_stock_wood), "crossShafts", nailHammer, "hammer", 2, 300,
                new Object[]{"NN N", "PPPP", " PPP", 'P', ComponentListMFR.plank.construct("RefinedWood"), 'N',
                        ComponentListMFR.nail});
        KnowledgeListMFR.crossStockIronR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.crossbow_stock_iron), "crossShaftAdvanced", spanner, "spanner", 2, 300,
                new Object[]{" BBB", "BOGG", "SWSS", "    ", 'O', Blocks.OBSIDIAN, 'G',
                        ComponentListMFR.tungsten_gears, 'W', ComponentListMFR.crossbow_stock_wood, 'S',
                        ComponentListMFR.iron_strut, 'B', ComponentListMFR.bolt,});

        KnowledgeListMFR.crossHeadLightR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.cross_arms_light), "crossHeads", nailHammer, "hammer", 2, 200,
                new Object[]{"PPP", "NSN", " P ", 'P', ComponentListMFR.plank.construct("RefinedWood"), 'N',
                        ComponentListMFR.nail, 'S', Items.STRING,});
        KnowledgeListMFR.crossHeadMediumR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.cross_arms_basic), "crossHeads", nailHammer, "hammer", 2, 250,
                new Object[]{"NNN", "PAP", 'N', ComponentListMFR.nail, 'P',
                        ComponentListMFR.plank.construct("RefinedWood"), 'A', ComponentListMFR.cross_arms_light,});
        KnowledgeListMFR.crossHeadHeavyR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.cross_arms_heavy), "crossHeads", nailHammer, "hammer", 2, 350,
                new Object[]{"NNN", "PAP", 'N', ComponentListMFR.nail, 'P',
                        ComponentListMFR.plank.construct("RefinedWood"), 'A', ComponentListMFR.cross_arms_basic,});
        KnowledgeListMFR.crossHeadAdvancedR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.cross_arms_advanced), "crossHeadAdvanced", nailHammer, "hammer", 2, 350,
                new Object[]{"NRN", "RGR", " A ", 'G', ComponentListMFR.tungsten_gears, 'N', ComponentListMFR.nail, 'R',
                        ComponentListMFR.steel_tube, 'A', ComponentListMFR.cross_arms_basic,});

        KnowledgeListMFR.crossAmmoR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.cross_ammo), "crossAmmo", nailHammer, "hammer", 2, 200,
                new Object[]{"NNN", "P P", "PGP", "PPP", 'G', ComponentListMFR.tungsten_gears, 'P',
                        ComponentListMFR.plank.construct("RefinedWood"), 'N', ComponentListMFR.nail,});
        KnowledgeListMFR.crossScopeR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.cross_scope), "crossScope", spanner, "spanner", 2, 150,
                new Object[]{"BSB", "GP ", 'G', ComponentListMFR.tungsten_gears, 'S', ToolListMFR.spyglass, 'P',
                        ComponentListMFR.plank.construct("RefinedWood"), 'B', ComponentListMFR.bolt,});
        Salvage.addSalvage(ComponentListMFR.cross_arms_light, new ItemStack(ComponentListMFR.nail, 2), Items.STRING,
                ComponentListMFR.plank.construct("RefinedWood", 4));
        Salvage.addSalvage(ComponentListMFR.cross_arms_basic, new ItemStack(ComponentListMFR.nail, 3),
                ComponentListMFR.cross_arms_light, ComponentListMFR.plank.construct("RefinedWood", 2));
        Salvage.addSalvage(ComponentListMFR.cross_arms_heavy, new ItemStack(ComponentListMFR.nail, 3),
                ComponentListMFR.cross_arms_basic, ComponentListMFR.plank.construct("RefinedWood", 2));
        Salvage.addSalvage(ComponentListMFR.cross_arms_advanced, ComponentListMFR.tungsten_gears,
                new ItemStack(ComponentListMFR.nail, 2), ComponentListMFR.cross_arms_basic,
                new ItemStack(ComponentListMFR.steel_tube, 3));

        Salvage.addSalvage(ComponentListMFR.cross_scope, ComponentListMFR.tungsten_gears, ToolListMFR.spyglass,
                new ItemStack(ComponentListMFR.bolt, 2), ComponentListMFR.plank.construct("RefinedWood"));
        Salvage.addSalvage(ComponentListMFR.cross_ammo, ComponentListMFR.tungsten_gears,
                new ItemStack(ComponentListMFR.nail, 3), ComponentListMFR.plank.construct("RefinedWood", 7));
        Salvage.addSalvage(ComponentListMFR.crossbow_handle_wood, new ItemStack(ComponentListMFR.nail, 2),
                ComponentListMFR.plank.construct("RefinedWood", 3));
        Salvage.addSalvage(ComponentListMFR.crossbow_stock_wood, new ItemStack(ComponentListMFR.nail, 3),
                ComponentListMFR.plank.construct("RefinedWood", 7));
        Salvage.addSalvage(ComponentListMFR.crossbow_stock_iron, new ItemStack(ComponentListMFR.tungsten_gears, 2),
                Blocks.OBSIDIAN, new ItemStack(ComponentListMFR.bolt, 4), new ItemStack(ComponentListMFR.iron_strut, 3),
                ComponentListMFR.crossbow_stock_wood);
    }

    private static void addEngineering() {
        addCrossbows();
        KnowledgeListMFR.bombBenchCraft = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(BlockListMFR.BOMB_BENCH), "bombs", spanner, "spanner", 0, 150, new Object[]{"BFB", "BCB",
                        'B', ComponentListMFR.bolt, 'F', ComponentListMFR.iron_frame, 'C', BlockListMFR.CARPENTER,});
        KnowledgeListMFR.bombPressCraft = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(BlockListMFR.BOMB_PRESS), "bpress", spanner, "spanner", 3, 200,
                new Object[]{"BFB", "GGL", "SPS", 'S', ComponentListMFR.iron_strut, 'B', ComponentListMFR.bolt, 'F',
                        ComponentListMFR.iron_frame, 'L', Blocks.LEVER, 'P',
                        new ItemStack(CustomToolListMFR.standard_spanner, 1, 0), 'G', ComponentListMFR.bronze_gears,});

        KnowledgeListMFR.crossBenchCraft = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(BlockListMFR.CROSSBOW_BENCH), "crossbows", spanner, "spanner", 0, 200,
                new Object[]{" F ", "PSP", "NCN", 'F', ComponentListMFR.iron_frame, 'P', ComponentListMFR.plank, 'N',
                        ComponentListMFR.bolt, 'S', Items.STRING, 'C', BlockListMFR.CARPENTER,});

        KnowledgeListMFR.engTannerR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(BlockListMFR.ENG_TANNER), "engTanner", spanner, "spanner", 3, 300,
                new Object[]{"BLB", "SPS", "GGG", "SFS", 'S', ComponentListMFR.iron_strut, 'B', ComponentListMFR.bolt,
                        'F', ComponentListMFR.iron_frame, 'L', Blocks.LEVER, 'P',
                        new ItemStack(CustomToolListMFR.standard_knife, 1, 0), 'G', ComponentListMFR.bronze_gears,});
        ItemStack blackPlate = ComponentListMFR.plate.createComm("blackSteel");
        KnowledgeListMFR.advancedForgeR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(BlockListMFR.FORGE_METAL), "advforge", spanner, "spanner", 4, 400,
                new Object[]{" T  ", "FRRF", "PPPP", "BBBB", 'B', ComponentListMFR.bolt, 'F',
                        ComponentListMFR.iron_frame, 'T', ToolListMFR.engin_anvil_tools, 'P', blackPlate, 'R',
                        Blocks.REDSTONE_BLOCK,});
        ItemStack steelPlate = ComponentListMFR.plate.createComm("steel");
        KnowledgeListMFR.autoCrucibleR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(BlockListMFR.CRUCIBLE_AUTO), "advcrucible", spanner, "spanner", 4, 200,
                new Object[]{" T ", "PCP", "PGP", "BBB", 'B', ComponentListMFR.bolt, 'C', BlockListMFR.CRUCIBLE_ADV, 'G',
                        ComponentListMFR.tungsten_gears, 'T', ToolListMFR.engin_anvil_tools, 'P', steelPlate});
        KnowledgeListMFR.spyglassR = MineFantasyRebornAPI.addCarpenterRecipe(engineering, new ItemStack(ToolListMFR.spyglass),
                "spyglass", spanner, "spanner", 1, 300,
                new Object[]{" T ", "BCB", "GPG", 'C', ComponentListMFR.bronze_gears, 'G', Blocks.GLASS, 'B',
                        ComponentListMFR.bolt, 'T', ToolListMFR.engin_anvil_tools, 'P', ComponentListMFR.steel_tube,});

        KnowledgeListMFR.syringeR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ToolListMFR.syringe_empty), "syringe", spanner, "spanner", 1, 200,
                new Object[]{"E", "T", "B", "N", 'E', ToolListMFR.engin_anvil_tools, 'T', ComponentListMFR.steel_tube,
                        'B', Items.GLASS_BOTTLE, 'N', new ItemStack(CustomToolListMFR.standard_needle),});

        KnowledgeListMFR.parachuteR = MineFantasyRebornAPI.addCarpenterRecipe(engineering, new ItemStack(ToolListMFR.parachute),
                "parachute", sewing, "needle", 1, 350,
                new Object[]{"TTT", "CCC", "BEB", "BLB", 'E', ToolListMFR.engin_anvil_tools, 'T',
                        ComponentListMFR.thread, 'B', ComponentListMFR.leather_strip, 'L', Items.LEATHER, 'C',
                        Blocks.WOOL,});
        KnowledgeListMFR.cogShaftR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.cogwork_shaft), "cogArmour", spanner, "spanner", 4, 150,
                new Object[]{"BPB", "SGS", "BFB",

                        'P', Blocks.PISTON, 'G', ComponentListMFR.tungsten_gears, 'B', ComponentListMFR.bolt, 'F',
                        ComponentListMFR.iron_frame, 'S', ComponentListMFR.iron_strut,});
        Salvage.addSalvage(ComponentListMFR.cogwork_shaft, new ItemStack(ComponentListMFR.iron_strut, 2),
                new ItemStack(ComponentListMFR.bolt, 4), ComponentListMFR.iron_frame, Blocks.PISTON,
                ComponentListMFR.tungsten_gears);

        Salvage.addSalvage(BlockListMFR.CRUCIBLE_AUTO, new ItemStack(ComponentListMFR.bolt, 3),
                ComponentListMFR.tungsten_gears, BlockListMFR.CRUCIBLE_ADV, steelPlate, steelPlate, steelPlate,
                steelPlate);
        Salvage.addSalvage(BlockListMFR.BOMB_BENCH, new ItemStack(ComponentListMFR.bolt, 4), ComponentListMFR.iron_frame,
                BlockListMFR.CARPENTER);
        Salvage.addSalvage(BlockListMFR.CROSSBOW_BENCH, new ItemStack(ComponentListMFR.nail, 2),
                ComponentListMFR.plank.construct("ScrapWood", 2), Items.STRING, BlockListMFR.CARPENTER);
        Salvage.addSalvage(BlockListMFR.BOMB_PRESS, new ItemStack(ComponentListMFR.iron_strut, 2),
                new ItemStack(ComponentListMFR.bolt, 2), new ItemStack(ComponentListMFR.bronze_gears, 2), Blocks.LEVER,
                ComponentListMFR.iron_frame);
        Salvage.addSalvage(BlockListMFR.ENG_TANNER, new ItemStack(ComponentListMFR.iron_strut, 4),
                new ItemStack(ComponentListMFR.bolt, 2), new ItemStack(ComponentListMFR.bronze_gears, 3),
                CustomToolListMFR.standard_needle, Blocks.LEVER, ComponentListMFR.iron_frame);
        Salvage.addSalvage(BlockListMFR.FORGE_METAL, new ItemStack(ComponentListMFR.bolt, 4), blackPlate, blackPlate,
                blackPlate, blackPlate, new ItemStack(ComponentListMFR.iron_frame, 2),
                new ItemStack(Blocks.REDSTONE_BLOCK, 2));
        Salvage.addSalvage(ToolListMFR.spyglass, new ItemStack(ComponentListMFR.bolt, 2), new ItemStack(Blocks.GLASS, 2),
                ComponentListMFR.steel_tube, ComponentListMFR.bronze_gears);
        Salvage.addSalvage(ToolListMFR.syringe_empty, Items.GLASS_BOTTLE, CustomToolListMFR.standard_needle,
                ComponentListMFR.steel_tube);
        Salvage.addSalvage(ToolListMFR.parachute, new ItemStack(ComponentListMFR.thread, 3),
                new ItemStack(Blocks.WOOL, 3), new ItemStack(ComponentListMFR.leather_strip, 4), Items.LEATHER);
    }

    private static void addNonPrimitiveStone() {
        KnowledgeListMFR.stoneKnifeR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.knifeStone), "",
                primitive, "hands", -1, 4,
                new Object[]{"R", "R", "S", 'R', Blocks.COBBLESTONE, 'S', ComponentListMFR.plank,});
        KnowledgeListMFR.stoneHammerR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.hammerStone),
                "", primitive, "hands", -1, 4,
                new Object[]{"R", "S", 'R', Blocks.COBBLESTONE, 'S', ComponentListMFR.plank,});

        KnowledgeListMFR.stoneTongsR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.tongsStone), "",
                primitive, "hands", -1, 4,
                new Object[]{"R ", "SR", 'R', Blocks.COBBLESTONE, 'S', ComponentListMFR.plank,});
        KnowledgeListMFR.boneNeedleR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.needleBone), "",
                primitive, "hands", -1, 4, new Object[]{"B", 'B', Items.BONE,});

        Salvage.addSalvage(ToolListMFR.knifeStone, new ItemStack(Blocks.COBBLESTONE, 2),
                ComponentListMFR.plank.construct("ScrapWood"));
        Salvage.addSalvage(ToolListMFR.hammerStone, Blocks.COBBLESTONE, ComponentListMFR.plank.construct("ScrapWood"));
        Salvage.addSalvage(ToolListMFR.tongsStone, new ItemStack(Blocks.COBBLESTONE, 2),
                ComponentListMFR.plank.construct("ScrapWood"));
        Salvage.addSalvage(ToolListMFR.needleBone, Items.BONE);
    }

    private static void addPrimitive() {
        KnowledgeListMFR.dirtRockR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ComponentListMFR.sharp_rock),
                "", "minecraft:dig.gravel", "hands", -1, 1, new Object[]{"D", 'D', Blocks.DIRT,});

        KnowledgeListMFR.stonePickR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.pickStone), "",
                primitive, "hands", -1, 5, new Object[]{"RVR", " S ", " S ", 'R', ComponentListMFR.sharp_rock, 'V',
                        ComponentListMFR.vine, 'S', Items.STICK});
        KnowledgeListMFR.stoneAxeR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.axeStone), "",
                primitive, "hands", -1, 5, new Object[]{"RV", "RS", " S", 'R', ComponentListMFR.sharp_rock, 'V',
                        ComponentListMFR.vine, 'S', Items.STICK});
        KnowledgeListMFR.stoneSpadeR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.spadeStone), "",
                primitive, "hands", -1, 5, new Object[]{"VR", " S", " S", 'R', ComponentListMFR.sharp_rock, 'V',
                        ComponentListMFR.vine, 'S', Items.STICK});
        KnowledgeListMFR.stoneHoeR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.hoeStone), "",
                primitive, "hands", -1, 5, new Object[]{"RV", " S", " S", 'R', ComponentListMFR.sharp_rock, 'V',
                        ComponentListMFR.vine, 'S', Items.STICK});
        KnowledgeListMFR.stoneSwordR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.swordStone), "",
                primitive, "hands", -1, 8, new Object[]{"R ", "R ", "SV", 'R', ComponentListMFR.sharp_rock, 'V',
                        ComponentListMFR.vine, 'S', Items.STICK});
        KnowledgeListMFR.stoneWarR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.waraxeStone), "",
                primitive, "hands", -1, 8, new Object[]{"VRV", "RS", " S", 'R', ComponentListMFR.sharp_rock, 'V',
                        ComponentListMFR.vine, 'S', Items.STICK});
        KnowledgeListMFR.stoneMaceR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.maceStone), "",
                primitive, "hands", -1, 8, new Object[]{" V ", "RSR", " S ", 'R', ComponentListMFR.sharp_rock, 'V',
                        ComponentListMFR.vine, 'S', Items.STICK});
        KnowledgeListMFR.stoneSpearR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.spearStone), "",
                primitive, "hands", -1, 8, new Object[]{"R", "V", "S", "S", 'R', ComponentListMFR.sharp_rock, 'V',
                        ComponentListMFR.vine, 'S', Items.STICK});
        KnowledgeListMFR.stoneKnifeR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.knifeStone), "",
                primitive, "hands", -1, 4, new Object[]{"R ", "SV", 'R', ComponentListMFR.sharp_rock, 'V',
                        ComponentListMFR.vine, 'S', Items.STICK});
        KnowledgeListMFR.stoneHammerR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.hammerStone),
                "", primitive, "hands", -1, 4, new Object[]{"R", "V", "S", 'R', ComponentListMFR.sharp_rock, 'V',
                        ComponentListMFR.vine, 'S', Items.STICK});

        KnowledgeListMFR.stoneTongsR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.tongsStone), "",
                primitive, "hands", -1, 4, new Object[]{" R", "SV", 'R', ComponentListMFR.sharp_rock, 'V',
                        ComponentListMFR.vine, 'S', Items.STICK});
        KnowledgeListMFR.boneNeedleR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.needleBone), "",
                primitive, "hands", -1, 4, new Object[]{"B", 'B', Items.BONE,});

        Salvage.addSalvage(ToolListMFR.pickStone, new ItemStack(ComponentListMFR.sharp_rock, 2),
                new ItemStack(Items.STICK, 2), ComponentListMFR.vine);
        Salvage.addSalvage(ToolListMFR.axeStone, new ItemStack(ComponentListMFR.sharp_rock, 2),
                new ItemStack(Items.STICK, 2), ComponentListMFR.vine);
        Salvage.addSalvage(ToolListMFR.spadeStone, ComponentListMFR.sharp_rock, new ItemStack(Items.STICK, 2),
                ComponentListMFR.vine);
        Salvage.addSalvage(ToolListMFR.hoeStone, ComponentListMFR.sharp_rock, new ItemStack(Items.STICK, 2),
                ComponentListMFR.vine);
        Salvage.addSalvage(ToolListMFR.swordStone, Items.STICK, new ItemStack(ComponentListMFR.sharp_rock, 2),
                ComponentListMFR.vine);
        Salvage.addSalvage(ToolListMFR.waraxeStone, new ItemStack(ComponentListMFR.sharp_rock, 2),
                new ItemStack(Items.STICK, 2), new ItemStack(ComponentListMFR.vine, 2));
        Salvage.addSalvage(ToolListMFR.maceStone, new ItemStack(ComponentListMFR.sharp_rock, 2),
                new ItemStack(Items.STICK, 2), ComponentListMFR.vine);
        Salvage.addSalvage(ToolListMFR.spearStone, ComponentListMFR.sharp_rock, new ItemStack(Items.STICK, 2),
                ComponentListMFR.vine);
        Salvage.addSalvage(ToolListMFR.knifeStone, ComponentListMFR.sharp_rock, Items.STICK, ComponentListMFR.vine);
        Salvage.addSalvage(ToolListMFR.hammerStone, ComponentListMFR.sharp_rock, Items.STICK, ComponentListMFR.vine);
        Salvage.addSalvage(ToolListMFR.tongsStone, ComponentListMFR.sharp_rock, Items.STICK, ComponentListMFR.vine);
        Salvage.addSalvage(ToolListMFR.needleBone, Items.BONE);
    }

    public static void initTierWood() {
        String basic = CarpenterRecipes.basic;

        float time = 4;
        Item plank = ComponentListMFR.plank;

        KnowledgeListMFR.spoonR = MineFantasyRebornAPI.addCarpenterToolRecipe(artisanry, CustomToolListMFR.standard_spoon, "",
                basic, "hands", -1, 1 + (int) (1 * time), new Object[]{"W", "S", 'W', plank, 'S', Items.STICK});
        Salvage.addSalvage(CustomToolListMFR.standard_spoon, plank, Items.STICK);
        KnowledgeListMFR.malletR = MineFantasyRebornAPI.addCarpenterToolRecipe(artisanry, CustomToolListMFR.standard_mallet, "",
                basic, "hands", -1, 1 + (int) (2 * time), new Object[]{"WW", " S", 'W', plank, 'S', Items.STICK});
        Salvage.addSalvage(CustomToolListMFR.standard_mallet, plank, plank, Items.STICK);
        Salvage.addSalvage(CustomToolListMFR.standard_spoon, plank, Items.STICK);

        KnowledgeListMFR.refinedPlankR.add(MineFantasyRebornAPI.addCarpenterRecipe(construction,
                ComponentListMFR.plank.construct("RefinedWood"), "", basic, "hands", -1, 1,
                new Object[]{"O", "P", 'O', ComponentListMFR.plant_oil, 'P', (ComponentListMFR.plank)}));
        KnowledgeListMFR.easyPaintPlank.add(MineFantasyRebornAPI.addCarpenterRecipe(construction,
                ComponentListMFR.plank.construct("RefinedWood", 4), "paint_brush", sewing, "brush", -1, 2,
                new Object[]{" O  ", "PPPP", 'O', ComponentListMFR.plant_oil, 'P', (ComponentListMFR.plank)}));
    }

    static void tryAddSawPlanks(ItemStack planks, CustomMaterial material) {
        String sub = material.name.substring(0, material.name.length() - 4).toLowerCase();

        if (planks.getUnlocalizedName().toLowerCase().contains(sub)) {
            addSawPlanks(planks, material);
        }
    }

    static void addSawPlanks(ItemStack planks, CustomMaterial material) {
        MineFantasyRebornAPI.addCarpenterRecipe(construction, (ComponentListMFR.plank).construct(material.name, 4),
                "commodities", sawing, "saw", -1, 10, new Object[]{"P", 'P', planks.copy()});
    }
}
