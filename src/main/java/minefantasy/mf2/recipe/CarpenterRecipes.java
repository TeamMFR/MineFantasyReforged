package minefantasy.mf2.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import minefantasy.mf2.api.MineFantasyAPI;
import minefantasy.mf2.api.crafting.Salvage;
import minefantasy.mf2.api.crafting.refine.PaintOilRecipe;
import minefantasy.mf2.api.crafting.refine.QuernRecipes;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.api.rpg.Skill;
import minefantasy.mf2.api.rpg.SkillList;
import minefantasy.mf2.block.decor.BlockWoodDecor;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.config.ConfigHardcore;
import minefantasy.mf2.item.food.FoodListMF;
import minefantasy.mf2.item.list.ArmourListMF;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.item.list.CustomToolListMF;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.knowledge.KnowledgeListMF;
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
        MineFantasyAPI.addCarpenterRecipe(null, new ItemStack(ToolListMF.researchBook), "", sewing, "hands", -1, 1,
                new Object[]{"B", 'B', Items.book,});

        if (ConfigHardcore.HCCallowRocks) {
            KnowledgeListMF.sharpRocksR = MineFantasyAPI.addCarpenterRecipe(null,
                    new ItemStack(ComponentListMF.sharp_rock, 8), "", stonemason, "hammer", -1, 10,
                    new Object[]{"S", 'S', Blocks.cobblestone,});

        } else {

        }
        Salvage.addSalvage(ToolListMF.dryrocks, Blocks.cobblestone);

        KnowledgeListMF.threadR1 = MineFantasyAPI.addCarpenterRecipe(null, new ItemStack(ComponentListMF.thread, 4),
                "commodities", sewing, "hands", -1, 5, new Object[]{"W", "S", 'W', Blocks.wool, 'S', Items.stick,});
        KnowledgeListMF.threadR2 = MineFantasyAPI.addCarpenterRecipe(null, new ItemStack(ComponentListMF.thread),
                "commodities", sewing, "hands", -1, 5, new Object[]{" V ", "VSV", " V ", 'S', Items.stick, 'V', ComponentListMF.vine});
        KnowledgeListMF.stringR = MineFantasyAPI.addCarpenterRecipe(null, new ItemStack(Items.string), "commodities",
                sewing, "hands", -1, 10, new Object[]{"T", "T", "T", "T", 'T', ComponentListMF.thread});

        KnowledgeListMF.lStripsR = MineFantasyAPI.addCarpenterRecipe(null,
                new ItemStack(ComponentListMF.leather_strip, 4), "commodities", snipping, "shears", -1, 10,
                new Object[]{"L", 'L', Items.leather,});

        MineFantasyAPI.addCarpenterRecipe(artisanry, new ItemStack(ToolListMF.swordTraining), nailHammer, "knife", 1,
                40, new Object[]{"NI  ", "SIII", "NI  ", 'N', ComponentListMF.nail, 'S', ComponentListMF.plank, 'I',
                        Blocks.planks,});

        MineFantasyAPI.addCarpenterRecipe(artisanry, new ItemStack(ToolListMF.waraxeTraining), nailHammer, "knife", 1,
                30, new Object[]{" II ", "SSIN", "  I ", 'N', ComponentListMF.nail, 'S', ComponentListMF.plank, 'I',
                        Blocks.planks,});
        MineFantasyAPI.addCarpenterRecipe(artisanry, new ItemStack(ToolListMF.maceTraining), nailHammer, "knife", 1, 35,
                new Object[]{"  II", "SSII", "  N ", 'N', ComponentListMF.nail, 'S', ComponentListMF.plank, 'I',
                        Blocks.planks,});
        MineFantasyAPI.addCarpenterRecipe(artisanry, new ItemStack(ToolListMF.spearTraining), nailHammer, "knife", 1,
                20, new Object[]{"  N ", "SSSI", "  N ", 'N', ComponentListMF.nail, 'S', ComponentListMF.plank, 'I',
                        Blocks.planks,});
        ItemStack scrapWood = ComponentListMF.plank.construct("ScrapWood");
        Salvage.addSalvage(ToolListMF.swordTraining, new ItemStack(Blocks.planks, 5),
                new ItemStack(ComponentListMF.nail, 2), scrapWood);
        Salvage.addSalvage(ToolListMF.waraxeTraining, new ItemStack(Blocks.planks, 4), ComponentListMF.nail, scrapWood,
                scrapWood);
        Salvage.addSalvage(ToolListMF.maceTraining, new ItemStack(Blocks.planks, 4), ComponentListMF.nail, scrapWood,
                scrapWood);
        Salvage.addSalvage(ToolListMF.waraxeTraining, Blocks.planks, new ItemStack(ComponentListMF.nail, 2), scrapWood,
                scrapWood, scrapWood);

        KnowledgeListMF.badBandageR = MineFantasyAPI.addCarpenterRecipe(provisioning,
                new ItemStack(ToolListMF.bandage_crude, 2), "bandage", sewing, "needle", -1, 10,
                new Object[]{"LLL", 'L', ComponentListMF.rawhideSmall,});

        MineFantasyAPI.addCarpenterRecipe(provisioning, new ItemStack(ToolListMF.bandage_crude, 4), "bandage", sewing,
                "needle", -1, 20, new Object[]{"LLL", 'L', ComponentListMF.rawhideMedium,});
        MineFantasyAPI.addCarpenterRecipe(provisioning, new ItemStack(ToolListMF.bandage_crude, 6), "bandage", sewing,
                "needle", -1, 30, new Object[]{"LLL", 'L', ComponentListMF.rawhideLarge,});
        KnowledgeListMF.bandageR = MineFantasyAPI.addCarpenterRecipe(provisioning,
                new ItemStack(ToolListMF.bandage_wool, 4), "bandage", sewing, "needle", 1, 10,
                new Object[]{"CTC", 'T', ComponentListMF.thread, 'C', Blocks.wool,});

        KnowledgeListMF.goodBandageR = MineFantasyAPI.addCarpenterRecipe(provisioning,
                new ItemStack(ToolListMF.bandage_tough), "bandageadv", sewing, "needle", 2, 20,
                new Object[]{"T", "L", "B", 'T', ComponentListMF.thread, 'L', ComponentListMF.leather_strip, 'B',
                        ToolListMF.bandage_wool});

        KnowledgeListMF.roughHelmetR = MineFantasyAPI.addCarpenterRecipe(artisanry,
                ArmourListMF.armour(ArmourListMF.leather, 1, 0), "craftArmourBasic", sewing, "needle", -1, 25,
                new Object[]{"TLT", "S S", 'T', ComponentListMF.thread, 'S', ComponentListMF.leather_strip, 'L',
                        Items.leather});
        KnowledgeListMF.roughChestR = MineFantasyAPI.addCarpenterRecipe(artisanry,
                ArmourListMF.armour(ArmourListMF.leather, 1, 1), "craftArmourBasic", sewing, "needle", -1, 40,
                new Object[]{"S S", "LLL", "TLT", 'T', ComponentListMF.thread, 'S', ComponentListMF.leather_strip,
                        'L', Items.leather});
        KnowledgeListMF.roughLegsR = MineFantasyAPI.addCarpenterRecipe(artisanry,
                ArmourListMF.armour(ArmourListMF.leather, 1, 2), "craftArmourBasic", sewing, "needle", -1, 35,
                new Object[]{"TLT", "L L", "S S", 'T', ComponentListMF.thread, 'S', ComponentListMF.leather_strip,
                        'L', Items.leather});
        KnowledgeListMF.roughBootsR = MineFantasyAPI.addCarpenterRecipe(artisanry,
                ArmourListMF.armour(ArmourListMF.leather, 1, 3), "craftArmourBasic", sewing, "needle", -1, 20,
                new Object[]{"T T", "S S", 'T', ComponentListMF.thread, 'S', ComponentListMF.leather_strip,});
        Salvage.addSalvage(ArmourListMF.armourItem(ArmourListMF.leather, 1, 0),
                new ItemStack(ComponentListMF.thread, 2), new ItemStack(ComponentListMF.leather_strip, 2),
                Items.leather);
        Salvage.addSalvage(ArmourListMF.armourItem(ArmourListMF.leather, 1, 1),
                new ItemStack(ComponentListMF.thread, 4), new ItemStack(ComponentListMF.leather_strip, 2),
                new ItemStack(Items.leather, 4));
        Salvage.addSalvage(ArmourListMF.armourItem(ArmourListMF.leather, 1, 2),
                new ItemStack(ComponentListMF.thread, 4), new ItemStack(ComponentListMF.leather_strip, 2),
                new ItemStack(Items.leather, 3));
        Salvage.addSalvage(ArmourListMF.armourItem(ArmourListMF.leather, 1, 3),
                new ItemStack(ComponentListMF.thread, 4), new ItemStack(ComponentListMF.leather_strip, 2));

        KnowledgeListMF.reHelmetR = MineFantasyAPI.addCarpenterRecipe(artisanry,
                ArmourListMF.armour(ArmourListMF.leather, 2, 0), "craftArmourLight", sewing, "needle", 1, 50,
                new Object[]{"TTT", "UPU", 'T', ComponentListMF.thread, 'P',
                        ArmourListMF.armour(ArmourListMF.leather, 1, 0), 'U', Items.leather});
        KnowledgeListMF.reChestR = MineFantasyAPI.addCarpenterRecipe(artisanry,
                ArmourListMF.armour(ArmourListMF.leather, 2, 1), "craftArmourLight", sewing, "needle", 1, 80,
                new Object[]{"TTT", "UPU", 'T', ComponentListMF.thread, 'P',
                        ArmourListMF.armour(ArmourListMF.leather, 1, 1), 'U', Items.leather});
        KnowledgeListMF.reLegsR = MineFantasyAPI.addCarpenterRecipe(artisanry,
                ArmourListMF.armour(ArmourListMF.leather, 2, 2), "craftArmourLight", sewing, "needle", 1, 70,
                new Object[]{"TTT", "UPU", 'T', ComponentListMF.thread, 'P',
                        ArmourListMF.armour(ArmourListMF.leather, 1, 2), 'U', Items.leather});
        KnowledgeListMF.reBootsR = MineFantasyAPI.addCarpenterRecipe(artisanry,
                ArmourListMF.armour(ArmourListMF.leather, 2, 3), "craftArmourLight", sewing, "needle", 1, 40,
                new Object[]{"TTT", "UPU", 'T', ComponentListMF.thread, 'P',
                        ArmourListMF.armour(ArmourListMF.leather, 1, 3), 'U', Items.leather});
        Salvage.addSalvage(ArmourListMF.armourItem(ArmourListMF.leather, 2, 0),
                ArmourListMF.armourItem(ArmourListMF.leather, 1, 0), new ItemStack(ComponentListMF.thread, 3),
                new ItemStack(Items.leather, 2));
        Salvage.addSalvage(ArmourListMF.armourItem(ArmourListMF.leather, 2, 1),
                ArmourListMF.armourItem(ArmourListMF.leather, 1, 1), new ItemStack(ComponentListMF.thread, 3),
                new ItemStack(Items.leather, 2));
        Salvage.addSalvage(ArmourListMF.armourItem(ArmourListMF.leather, 2, 2),
                ArmourListMF.armourItem(ArmourListMF.leather, 1, 2), new ItemStack(ComponentListMF.thread, 3),
                new ItemStack(Items.leather, 2));
        Salvage.addSalvage(ArmourListMF.armourItem(ArmourListMF.leather, 2, 3),
                ArmourListMF.armourItem(ArmourListMF.leather, 1, 3), new ItemStack(ComponentListMF.thread, 3),
                new ItemStack(Items.leather, 2));

        // PADDING
        KnowledgeListMF.padding[0] = MineFantasyAPI.addCarpenterRecipe(artisanry,
                ArmourListMF.armour(ArmourListMF.leather, 4, 0), "craftArmourLight", sewing, "needle", 1, 50,
                new Object[]{" W ", "SPS", " S ", 'P', ArmourListMF.armour(ArmourListMF.leather, 1, 0), 'W',
                        Blocks.wool, 'S', ComponentListMF.thread,});
        KnowledgeListMF.padding[1] = MineFantasyAPI.addCarpenterRecipe(artisanry,
                ArmourListMF.armour(ArmourListMF.leather, 4, 1), "craftArmourLight", sewing, "needle", 1, 80,
                new Object[]{" W ", "SPS", " S ", 'P', ArmourListMF.armour(ArmourListMF.leather, 1, 1), 'W',
                        Blocks.wool, 'S', ComponentListMF.thread,});
        KnowledgeListMF.padding[2] = MineFantasyAPI.addCarpenterRecipe(artisanry,
                ArmourListMF.armour(ArmourListMF.leather, 4, 2), "craftArmourLight", sewing, "needle", 1, 70,
                new Object[]{" W ", "SPS", " S ", 'P', ArmourListMF.armour(ArmourListMF.leather, 1, 2), 'W',
                        Blocks.wool, 'S', ComponentListMF.thread,});
        KnowledgeListMF.padding[3] = MineFantasyAPI.addCarpenterRecipe(artisanry,
                ArmourListMF.armour(ArmourListMF.leather, 4, 3), "craftArmourLight", sewing, "needle", 1, 40,
                new Object[]{" W ", "SPS", " S ", 'P', ArmourListMF.armour(ArmourListMF.leather, 1, 3), 'W',
                        Blocks.wool, 'S', ComponentListMF.thread,});
        Salvage.addSalvage(ArmourListMF.armourItem(ArmourListMF.leather, 4, 0),
                ArmourListMF.armourItem(ArmourListMF.leather, 1, 0), new ItemStack(ComponentListMF.thread, 3),
                Blocks.wool);
        Salvage.addSalvage(ArmourListMF.armourItem(ArmourListMF.leather, 4, 1),
                ArmourListMF.armourItem(ArmourListMF.leather, 1, 1), new ItemStack(ComponentListMF.thread, 3),
                Blocks.wool);
        Salvage.addSalvage(ArmourListMF.armourItem(ArmourListMF.leather, 4, 2),
                ArmourListMF.armourItem(ArmourListMF.leather, 1, 2), new ItemStack(ComponentListMF.thread, 3),
                Blocks.wool);
        Salvage.addSalvage(ArmourListMF.armourItem(ArmourListMF.leather, 4, 3),
                ArmourListMF.armourItem(ArmourListMF.leather, 1, 3), new ItemStack(ComponentListMF.thread, 3),
                Blocks.wool);

        KnowledgeListMF.repairBasicR = MineFantasyAPI.addCarpenterRecipe(artisanry,
                new ItemStack(BlockListMF.repair_basic), "repair_basic", sewing, "needle", 1, 20,
                new Object[]{"TTT", "FNH", "SLS", 'T', ComponentListMF.thread, 'S', ComponentListMF.leather_strip,
                        'L', Items.leather, 'F', Items.flint, 'H', CustomToolListMF.standard_hammer, 'N',
                        ComponentListMF.nail,});
        ItemStack bronzePlate = ComponentListMF.plate.createComm("bronze");
        KnowledgeListMF.repairAdvancedR = MineFantasyAPI.addCarpenterRecipe(artisanry,
                new ItemStack(BlockListMF.repair_advanced), "repair_advanced", sewing, "needle", 2, 50,
                new Object[]{"SCS", "PKH", "CSC", 'K', BlockListMF.repair_basic, 'P', bronzePlate, 'H',
                        CustomToolListMF.standard_hammer, 'C', Items.slime_ball, 'S', Items.string,});
        KnowledgeListMF.repairOrnateR = MineFantasyAPI.addCarpenterRecipe(artisanry,
                new ItemStack(BlockListMF.repair_ornate), "repair_ornate", sewing, "needle", 3, 100,
                new Object[]{"GDG", "LKL", "GLG", 'K', BlockListMF.repair_advanced, 'G', Items.gold_ingot, 'L',
                        new ItemStack(Items.dye, 1, 4), 'D', Items.diamond,});

        Salvage.addSalvage(BlockListMF.repair_basic, new ItemStack(ComponentListMF.thread, 3), ComponentListMF.nail,
                Items.flint, Items.leather, new ItemStack(ComponentListMF.leather_strip, 2));
        Salvage.addSalvage(BlockListMF.repair_advanced, BlockListMF.repair_basic, bronzePlate,
                new ItemStack(Items.slime_ball, 3), new ItemStack(Items.string, 3));
        Salvage.addSalvage(BlockListMF.repair_ornate, BlockListMF.repair_advanced, new ItemStack(Items.gold_ingot, 4),
                Items.diamond, new ItemStack(Items.dye, 3, 4));

        KnowledgeListMF.trilogyRecipe = MineFantasyAPI.addShapelessCarpenterRecipe(artisanry,
                new ItemStack(ComponentListMF.artefacts, 1, 3), "smeltMaster", basic, "hands", -1, 1,
                new Object[]{new ItemStack(ComponentListMF.artefacts, 1, 0),
                        new ItemStack(ComponentListMF.artefacts, 1, 1),
                        new ItemStack(ComponentListMF.artefacts, 1, 2)});
    }

    public static void assembleWoodBasic() {
        KnowledgeListMF.carpenterRecipe = GameRegistry.addShapedRecipe(new ItemStack(BlockListMF.carpenter),
                new Object[]{"PBP", "P P", 'B', Blocks.crafting_table, 'P', ComponentListMF.plank});
        Salvage.addSalvage(BlockListMF.carpenter, ComponentListMF.plank.construct("ScrapWood", 4),
                Blocks.crafting_table);

        KnowledgeListMF.nailPlanksR = MineFantasyAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMF.nailed_planks), "refined_planks", nailHammer, "hammer", 1, 5,
                new Object[]{"N ", "PP", "PP", 'N', ComponentListMF.nail, 'P',
                        ComponentListMF.plank.construct("OakWood"),});
        KnowledgeListMF.nailStairR = MineFantasyAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMF.nailed_planks_stair), "refined_planks", nailHammer, "hammer", 1, 5,
                new Object[]{"N ", "P ", "PP", 'N', ComponentListMF.nail, 'P',
                        ComponentListMF.plank.construct("OakWood"),});
        KnowledgeListMF.tannerRecipe = MineFantasyAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMF.tanner), "", nailHammer, "hammer", -1, 10,
                new Object[]{"PPP", "P P", "PPP", 'P', ComponentListMF.plank,});

        KnowledgeListMF.clayWallR = MineFantasyAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMF.clayWall, 4), "clay_wall", nailHammer, "hammer", 1, 2, new Object[]{"NPN",
                        "PCP", "NPN", 'N', ComponentListMF.nail, 'P', ComponentListMF.plank, 'C', Blocks.clay});

        KnowledgeListMF.researchTableRecipe = MineFantasyAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMF.research), "", nailHammer, "hammer", -1, 10,
                new Object[]{"B", "C", 'B', ToolListMF.researchBook, 'C', BlockListMF.carpenter,});
        KnowledgeListMF.bSalvageR = MineFantasyAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMF.salvage_basic), "", nailHammer, "hammer", -1, 10, new Object[]{"SFS", "PWP",
                        'W', Blocks.crafting_table, 'S', Blocks.stone, 'F', Items.flint, 'P', ComponentListMF.plank});

        KnowledgeListMF.framedGlassR = MineFantasyAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMF.framed_glass), "", nailHammer, "hammer", -1, 10,
                new Object[]{"PGP", 'P', ComponentListMF.plank, 'G', Blocks.glass});
        KnowledgeListMF.windowR = MineFantasyAPI.addCarpenterRecipe(construction, new ItemStack(BlockListMF.window), "",
                nailHammer, "hammer", -1, 10,
                new Object[]{" P ", "PGP", " P ", 'P', ComponentListMF.plank, 'G', Blocks.glass});

        Salvage.addSalvage(BlockListMF.framed_glass, ComponentListMF.plank.construct("ScrapWood", 2), Blocks.glass);
        Salvage.addSalvage(BlockListMF.window, ComponentListMF.plank.construct("ScrapWood", 4), Blocks.glass);
        Salvage.addSalvage(BlockListMF.clayWall, ComponentListMF.nail, ComponentListMF.plank.construct("ScrapWood"),
                Items.clay_ball);
        Salvage.addSalvage(BlockListMF.tanner, ComponentListMF.plank.construct("ScrapWood", 8));
        Salvage.addSalvage(BlockListMF.research, BlockListMF.carpenter);
        Salvage.addSalvage(BlockListMF.salvage_basic, Items.flint, new ItemStack(Blocks.stone, 2),
                ComponentListMF.plank.construct("ScrapWood", 2), Blocks.crafting_table);
    }

    private static void addDusts() {
        QuernRecipes.addRecipe(new ItemStack(Items.dye, 1, 3), new ItemStack(FoodListMF.coca_powder), 0, true);// ItemDye
        QuernRecipes.addRecipe(Items.wheat, new ItemStack(FoodListMF.flour), 0, true);
        QuernRecipes.addRecipe(Items.reeds, new ItemStack(FoodListMF.sugarpot), 0, true);
        QuernRecipes.addRecipe(FoodListMF.breadroll, new ItemStack(FoodListMF.breadcrumbs), 0, true);

        QuernRecipes.addRecipe(FoodListMF.generic_meat_uncooked, new ItemStack(FoodListMF.generic_meat_mince_uncooked),
                0, true);
        QuernRecipes.addRecipe(FoodListMF.generic_meat_strip_uncooked,
                new ItemStack(FoodListMF.generic_meat_mince_uncooked), 0, true);
        QuernRecipes.addRecipe(FoodListMF.generic_meat_chunk_uncooked,
                new ItemStack(FoodListMF.generic_meat_mince_uncooked), 0, true);
        QuernRecipes.addRecipe(FoodListMF.generic_meat_cooked, new ItemStack(FoodListMF.generic_meat_mince_cooked), 0, true);
        QuernRecipes.addRecipe(FoodListMF.generic_meat_strip_cooked,
                new ItemStack(FoodListMF.generic_meat_mince_cooked), 0, true);
        QuernRecipes.addRecipe(FoodListMF.generic_meat_chunk_cooked,
                new ItemStack(FoodListMF.generic_meat_mince_cooked), 0, true);

        QuernRecipes.addRecipe(Items.coal, new ItemStack(ComponentListMF.coalDust), 0, true);
        QuernRecipes.addRecipe(new ItemStack(Items.coal, 1, 1), new ItemStack(ComponentListMF.coalDust), 0, true);
        QuernRecipes.addRecipe(ComponentListMF.kaolinite, new ItemStack(ComponentListMF.kaolinite_dust), 0, true);
        QuernRecipes.addRecipe(Items.flint, new ItemStack(ComponentListMF.shrapnel), 0, true);

        QuernRecipes.addRecipe(ComponentListMF.flux, new ItemStack(ComponentListMF.flux_pot), 0, true);

        KnowledgeListMF.pieTrayRecipe = MineFantasyAPI.addCarpenterRecipe(null,
                new ItemStack(ComponentListMF.pie_tray_uncooked), "", basic, "hands", -1, 10,
                new Object[]{"CC", 'C', Items.clay_ball,});

        KnowledgeListMF.potRecipe = MineFantasyAPI.addCarpenterRecipe(null,
                new ItemStack(ComponentListMF.clay_pot_uncooked, 8), "", basic, "hands", -1, 5,
                new Object[]{"C  C", " CC ", 'C', Items.clay_ball,});
        KnowledgeListMF.mouldRecipe = MineFantasyAPI.addCarpenterRecipe(null,
                new ItemStack(ComponentListMF.ingot_mould_uncooked), "crucible", basic, "hands", -1, 10,
                new Object[]{"CCC", " C ", 'C', Items.clay_ball,});
        KnowledgeListMF.jugRecipe = MineFantasyAPI.addCarpenterRecipe(null, new ItemStack(FoodListMF.jug_uncooked, 4),
                "", basic, "hands", -1, 8, new Object[]{"C  ", "C C", " C ", 'C', Items.clay_ball,});
        KnowledgeListMF.blackpowderRec = MineFantasyAPI.addCarpenterRecipe(null,
                new ItemStack(ComponentListMF.blackpowder, 2), "blackpowder", basic, "hands", -1, 2,
                new Object[]{"NS", "CC", "PP", 'C', ComponentListMF.coalDust, 'N', ComponentListMF.nitre, 'S',
                        ComponentListMF.sulfur, 'P', ComponentListMF.clay_pot,});
        KnowledgeListMF.crudeBombR = MineFantasyAPI.addCarpenterRecipe(engineering,
                new ItemStack(ToolListMF.bomb_crude), "blackpowder", primitive, "hands", -1, 5,
                new Object[]{"T", "B", "P",

                        'B', ComponentListMF.blackpowder, 'T', ComponentListMF.thread, 'P', Items.paper,});
        KnowledgeListMF.advblackpowderRec = MineFantasyAPI.addCarpenterRecipe(null,
                new ItemStack(ComponentListMF.blackpowder_advanced), "advblackpowder", basic, "hands", -1, 10,
                new Object[]{" B ", "RGR", " P ", 'B', ComponentListMF.blackpowder, 'G', Items.glowstone_dust, 'R',
                        Items.redstone, 'P', ComponentListMF.clay_pot,});
        KnowledgeListMF.magmaRefinedR = MineFantasyAPI.addCarpenterRecipe(null,
                new ItemStack(ComponentListMF.magma_cream_refined), "firebomb", grinding, "pestle", -1, 10,
                new Object[]{"B", "H", "C", "P", 'H', ComponentListMF.dragon_heart, 'B', Items.blaze_powder, 'C',
                        Items.magma_cream, 'P', ComponentListMF.clay_pot,});
        Salvage.addSalvage(ComponentListMF.magma_cream_refined, ComponentListMF.dragon_heart, Items.blaze_powder,
                Items.magma_cream, ComponentListMF.clay_pot);
    }

    private static void addWoodworks() {
        KnowledgeListMF.refinedPlankBlockR = MineFantasyAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMF.refined_planks), "refined_planks", nailHammer, "hammer", 1, 10,
                new Object[]{"N ", "PP", "PP", 'N', ComponentListMF.nail, 'P',
                        ComponentListMF.plank.construct("RefinedWood"),});

        KnowledgeListMF.refinedStairR = MineFantasyAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMF.refined_planks_stair), "refined_planks", nailHammer, "hammer", 1, 10,
                new Object[]{"N ", "P ", "PP", 'N', ComponentListMF.nail, 'P',
                        ComponentListMF.plank.construct("RefinedWood"),});
        Salvage.addSalvage(BlockListMF.nailed_planks, ComponentListMF.nail,
                ComponentListMF.plank.construct("ScrapWood", 4));
        Salvage.addSalvage(BlockListMF.refined_planks, ComponentListMF.nail,
                ComponentListMF.plank.construct("RefinedWood", 4));
        Salvage.addSalvage(BlockListMF.nailed_planks_stair, ComponentListMF.nail,
                ComponentListMF.plank.construct("ScrapWood", 3));
        Salvage.addSalvage(BlockListMF.refined_planks_stair, ComponentListMF.nail,
                ComponentListMF.plank.construct("RefinedWood", 3));

        KnowledgeListMF.bellowsRecipe = MineFantasyAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMF.bellows), "", nailHammer, "hammer", 1, 50,
                new Object[]{"NNN", "PPP", "LL ", "PP ", 'N', ComponentListMF.nail, 'P',
                        ComponentListMF.plank.construct("RefinedWood"), 'L', Items.leather,});
        Salvage.addSalvage(BlockListMF.bellows, new ItemStack(ComponentListMF.nail, 3),
                ComponentListMF.plank.construct("RefinedWood", 5), new ItemStack(Items.leather, 2));

        KnowledgeListMF.woodTroughRecipe = MineFantasyAPI.addCarpenterRecipe(construction,
                ((BlockWoodDecor) BlockListMF.trough_wood).construct("ScrapWood"), "", nailHammer, "hammer", -1, 20,
                new Object[]{"P P", "PPP",

                        'P', ComponentListMF.plank,});

        KnowledgeListMF.strongRackR = MineFantasyAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMF.advTanner), "", nailHammer, "hammer", 1, 80,
                new Object[]{"NNN", "PPP", "P P", "PPP", 'N', ComponentListMF.nail, 'P',
                        ComponentListMF.plank.construct("RefinedWood"),});
        Salvage.addSalvage(BlockListMF.advTanner, ComponentListMF.plank.construct("RefinedWood", 8),
                new ItemStack(ComponentListMF.nail, 3));

        MineFantasyAPI.addCarpenterRecipe(construction, new ItemStack(BlockListMF.refined_planks), "paint_brush",
                sewing, "brush", -1, 3,
                new Object[]{"O", "P", 'O', ComponentListMF.plant_oil, 'P', BlockListMF.nailed_planks,});

        PaintOilRecipe.addRecipe(BlockListMF.nailed_planks, BlockListMF.refined_planks);
        PaintOilRecipe.addRecipe(BlockListMF.nailed_planks_stair, BlockListMF.refined_planks_stair);

    }

    private static void addStonemason() {
        KnowledgeListMF.quernR = MineFantasyAPI.addCarpenterRecipe(null, new ItemStack(BlockListMF.quern), "",
                stonemason, "hammer", -1, 10, new Object[]{"FSF", "SSS", 'F', Items.flint, 'S', Blocks.stone,});
        KnowledgeListMF.stoneovenRecipe = MineFantasyAPI.addCarpenterRecipe(null, new ItemStack(BlockListMF.oven_stone),
                "", stonemason, "hammer", -1, 10,
                new Object[]{"S", "C", 'C', BlockListMF.roast, 'S', Blocks.stone,});

        KnowledgeListMF.bloomeryR = MineFantasyAPI.addCarpenterRecipe(artisanry, new ItemStack(BlockListMF.bloomery),
                "bloomery", stonemason, "hammer", -1, 10,
                new Object[]{" S ", "S S", "SCS", 'C', Blocks.coal_block, 'S', Blocks.stone,});
        KnowledgeListMF.crucibleRecipe = MineFantasyAPI.addCarpenterRecipe(artisanry,
                new ItemStack(BlockListMF.crucible), "crucible", stonemason, "hammer", -1, 20,
                new Object[]{"SSS", "S S", "SSS", 'S', Blocks.stone,});
        KnowledgeListMF.advCrucibleRecipe = MineFantasyAPI.addCarpenterRecipe(artisanry,
                new ItemStack(BlockListMF.crucibleadv), "crucible2", basic, 40,
                new Object[]{"SSS", "SCS", "SSS", 'S', ComponentListMF.fireclay, 'C', BlockListMF.crucible});
        Salvage.addSalvage(BlockListMF.crucible, new ItemStack(Blocks.stone, 8));
        Salvage.addSalvage(BlockListMF.crucibleadv, new ItemStack(ComponentListMF.fireclay, 8), BlockListMF.crucible);

        KnowledgeListMF.chimneyRecipe = MineFantasyAPI.addCarpenterRecipe(artisanry,
                new ItemStack(BlockListMF.chimney_stone, 8), "", stonemason, "hammer", -1, 30,
                new Object[]{"S S", "S S", "S S", 'S', Blocks.stone,});
        KnowledgeListMF.wideChimneyRecipe = MineFantasyAPI.addCarpenterRecipe(artisanry,
                new ItemStack(BlockListMF.chimney_stone_wide), "", stonemason, "hammer", -1, 10,
                new Object[]{"S", "C", 'C', BlockListMF.chimney_stone, 'S', Blocks.stone,});
        KnowledgeListMF.extractChimneyRecipe = MineFantasyAPI.addCarpenterRecipe(artisanry,
                new ItemStack(BlockListMF.chimney_stone_extractor), "", stonemason, "hammer", -1, 15,
                new Object[]{"C", 'C', BlockListMF.chimney_stone_wide,});

        KnowledgeListMF.stoneAnvilRecipe = MineFantasyAPI.addCarpenterRecipe(null,
                new ItemStack(BlockListMF.anvilStone), "", stonemason, "hammer", -1, 10,
                new Object[]{"SS ", "SSS", " S ", 'S', Blocks.stone});
        KnowledgeListMF.forgeRecipe = MineFantasyAPI.addCarpenterRecipe(null, new ItemStack(BlockListMF.forge), "",
                stonemason, "hammer", -1, 10, new Object[]{"S S", "SCS", 'C', Items.coal, 'S', Blocks.stone});
        Salvage.addSalvage(BlockListMF.forge, new ItemStack(Blocks.stone, 4), Items.coal);
        Salvage.addSalvage(BlockListMF.anvilStone, new ItemStack(Blocks.stone, 6));

        Salvage.addSalvage(BlockListMF.chimney_stone, Blocks.stone);
        Salvage.addSalvage(BlockListMF.chimney_stone_wide, BlockListMF.chimney_stone, Blocks.stone);
        Salvage.addSalvage(BlockListMF.chimney_stone_extractor, BlockListMF.chimney_stone_wide);
        Salvage.addSalvage(BlockListMF.quern, new ItemStack(Items.flint, 2), new ItemStack(Blocks.stone, 4));
    }

    private static void addCooking() {
        String meatRaw = "rawMeat";
        String cookedMeat = "cookedMeat";
        OreDictionary.registerOre(cookedMeat, Items.cooked_beef);
        OreDictionary.registerOre(cookedMeat, Items.cooked_chicken);
        OreDictionary.registerOre(cookedMeat, Items.cooked_porkchop);
        OreDictionary.registerOre(cookedMeat, FoodListMF.wolf_cooked);
        OreDictionary.registerOre(cookedMeat, FoodListMF.horse_cooked);
        OreDictionary.registerOre(cookedMeat, Items.cooked_fished);
        OreDictionary.registerOre(cookedMeat, new ItemStack(Items.cooked_fished, 1, 1));
        addOreD("listAllporkcooked", cookedMeat);
        addOreD("listAllmuttoncooked", cookedMeat);
        addOreD("listAllbeefcooked", cookedMeat);
        addOreD("listAllchickencooked", cookedMeat);
        addOreD("listAllfishcooked", cookedMeat);

        OreDictionary.registerOre(meatRaw, FoodListMF.guts);
        OreDictionary.registerOre(meatRaw, Items.beef);
        OreDictionary.registerOre(meatRaw, Items.chicken);
        OreDictionary.registerOre(meatRaw, Items.porkchop);
        OreDictionary.registerOre(meatRaw, FoodListMF.wolf_raw);
        OreDictionary.registerOre(meatRaw, FoodListMF.horse_raw);
        OreDictionary.registerOre(meatRaw, Items.fish);
        OreDictionary.registerOre(meatRaw, new ItemStack(Items.fish, 1, 1));
        addOreD("listAllporkraw", meatRaw);
        addOreD("listAllmuttonraw", meatRaw);
        addOreD("listAllbeefraw", meatRaw);
        addOreD("listAllchickenraw", meatRaw);
        addOreD("listAllfishraw", meatRaw);

        KnowledgeListMF.curdRecipe = MineFantasyAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMF.curds),
                "", basic, "hands", -1, 10, new Object[]{"T", "S", "M", "P", 'P', ComponentListMF.clay_pot, 'T',
                        FoodListMF.salt, 'S', FoodListMF.sugarpot, 'M', FoodListMF.jug_milk,});

        KnowledgeListMF.oatsRecipe = MineFantasyAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMF.oats), "",
                chopping, "knife", -1, 20, new Object[]{"M", "W", "S", "B", 'S', Items.wheat_seeds, 'W', Items.wheat,
                        'M', FoodListMF.jug_milk, 'B', Items.bowl});
        KnowledgeListMF.doughRecipe = MineFantasyAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMF.dough),
                "", basic, "hands", -1, 10,
                new Object[]{"W", "F", 'W', FoodListMF.jug_water, 'F', FoodListMF.flour,});
        KnowledgeListMF.pastryRecipe = MineFantasyAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMF.pastry),
                "", basic, "hands", -1, 10,
                new Object[]{" S ", "FEF", 'F', FoodListMF.flour, 'E', Items.egg, 'S', FoodListMF.salt,});
        KnowledgeListMF.breadRecipe = MineFantasyAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMF.raw_bread), "", basic, "hands", -1, 15,
                new Object[]{"DDD", 'D', FoodListMF.dough,});
        KnowledgeListMF.sweetrollRecipe = MineFantasyAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMF.sweetroll_raw), "sweetroll", basic, 5,
                new Object[]{" M ", "FES", "BBB", 'M', FoodListMF.jug_milk, 'S', FoodListMF.sugarpot, 'B',
                        FoodListMF.berries, 'E', Items.egg, 'F', FoodListMF.flour,});
        KnowledgeListMF.icingRecipe = MineFantasyAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMF.icing),
                "", mixing, "spoon", -1, 10, new Object[]{"W", "S", "B", 'W', FoodListMF.jug_water, 'S',
                        FoodListMF.sugarpot, 'B', ComponentListMF.clay_pot,});
        KnowledgeListMF.chocoRecipe = MineFantasyAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMF.chocolate), "icing", mixing, "spoon", -1, 10,
                new Object[]{" M ", "SCS", " B ", 'C', FoodListMF.coca_powder, 'M', FoodListMF.jug_milk, 'S',
                        FoodListMF.sugarpot, 'B', ComponentListMF.clay_pot,});
        KnowledgeListMF.custardRecipe = MineFantasyAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMF.custard), "icing", mixing, "spoon", -1, 10,
                new Object[]{" M ", "SES", " B ", 'E', Items.egg, 'M', FoodListMF.jug_milk, 'S', FoodListMF.sugarpot,
                        'B', ComponentListMF.clay_pot,});
        KnowledgeListMF.iceSR = MineFantasyAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMF.sweetroll),
                "sweetroll", basic, "knife", -1, 15,
                new Object[]{"I", "R", 'I', FoodListMF.icing, 'R', FoodListMF.sweetroll_uniced,});
        KnowledgeListMF.eclairDoughR = MineFantasyAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMF.eclair_raw), "eclair", basic, 8,
                new Object[]{"SSS", "PPP", 'P', FoodListMF.pastry, 'S', FoodListMF.sugarpot,});
        KnowledgeListMF.eclairIceR = MineFantasyAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMF.eclair_empty), "eclair", basic, "knife", 2, 20,
                new Object[]{"C", "E", 'C', FoodListMF.chocolate, 'E', FoodListMF.eclair_uniced,});
        KnowledgeListMF.eclairFillR = MineFantasyAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMF.eclair),
                "eclair", basic, "knife", 2, 20,
                new Object[]{"C", "E", 'C', FoodListMF.custard, 'E', FoodListMF.eclair_empty,});
        for (ItemStack food : OreDictionary.getOres(meatRaw)) {
            int size = getSize(food);
            KnowledgeListMF.meatRecipes.add(MineFantasyAPI.addCarpenterRecipe(provisioning,
                    new ItemStack(FoodListMF.generic_meat_uncooked, size), "", chopping, "knife", -1, 15,
                    new Object[]{"M", 'M', food,}));
        }
        for (ItemStack food : OreDictionary.getOres(cookedMeat)) {
            int size = 1;
            KnowledgeListMF.meatRecipes.add(
                    MineFantasyAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMF.generic_meat_cooked, size),
                            "", chopping, "knife", -1, 15, new Object[]{"M", 'M', food,}));
        }
        KnowledgeListMF.meatStripR = MineFantasyAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMF.generic_meat_strip_uncooked), "", chopping, "knife", -1, 5,
                new Object[]{"M", 'M', FoodListMF.generic_meat_uncooked,});
        MineFantasyAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMF.generic_meat_strip_cooked), "",
                chopping, "knife", -1, 5, new Object[]{"M", 'M', FoodListMF.generic_meat_cooked,});
        KnowledgeListMF.meatHunkR = MineFantasyAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMF.generic_meat_chunk_uncooked), "", chopping, "knife", -1, 5,
                new Object[]{"M", 'M', FoodListMF.generic_meat_strip_uncooked,});
        MineFantasyAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMF.generic_meat_chunk_cooked), "",
                chopping, "knife", -1, 5, new Object[]{"M", 'M', FoodListMF.generic_meat_strip_cooked,});
        KnowledgeListMF.gutsRecipe = MineFantasyAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMF.guts), "",
                chopping, "knife", 1, 8, new Object[]{"MMMM", 'M', Items.rotten_flesh,});

        KnowledgeListMF.stewRecipe = MineFantasyAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMF.stew), "",
                chopping, "knife", -1, 15,
                new Object[]{"M", "B", 'M', FoodListMF.generic_meat_chunk_cooked, 'B', Items.bowl});
        KnowledgeListMF.jerkyRecipe = MineFantasyAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMF.jerky, 1), "jerky", chopping, "knife", 2, 20,
                new Object[]{"S", "M", 'S', FoodListMF.salt, 'M', FoodListMF.generic_meat_strip_cooked,});
        KnowledgeListMF.saussageR = MineFantasyAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMF.saussage_raw, 4), "saussage", chopping, "knife", 2, 30,
                new Object[]{" G ", "MMM", "BES", 'G', FoodListMF.guts, 'E', Items.egg, 'S', FoodListMF.salt, 'B',
                        FoodListMF.breadcrumbs, 'M', FoodListMF.generic_meat_mince_uncooked,});
        KnowledgeListMF.meatPieRecipe = MineFantasyAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMF.pie_meat_uncooked), "meatpie", chopping, "knife", 2, 150,
                new Object[]{" P ", "MMM", " P ", " T ", 'P', FoodListMF.pastry, 'M',
                        FoodListMF.generic_meat_mince_cooked, 'T', FoodListMF.pie_tray,});
        KnowledgeListMF.breadSliceR = MineFantasyAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMF.breadSlice, 12), "", "step.cloth", "knife", -1, 10,
                new Object[]{"B", 'B', Items.bread,});
        KnowledgeListMF.sandwitchRecipe = MineFantasyAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMF.sandwitch_meat), "sandwitch", chopping, "hands", -1, 4,
                new Object[]{"B", "C", "M", "B", 'C', FoodListMF.cheese_slice, 'M', FoodListMF.generic_meat_cooked,
                        'B', FoodListMF.breadSlice});
        KnowledgeListMF.sandwitchBigRecipe = MineFantasyAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMF.sandwitch_big), "sandwitchBig", chopping, "knife", 1, 10,
                new Object[]{"CSC", "MBM", 'S', FoodListMF.salt, 'C', FoodListMF.cheese_slice, 'M',
                        FoodListMF.generic_meat_cooked, 'B', Items.bread});
        KnowledgeListMF.shepardRecipe = MineFantasyAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMF.pie_shepard_uncooked), "shepardpie", chopping, "knife", 3, 200,
                new Object[]{"PFP", "MMM", "CFC", " T ", 'C', Items.carrot, 'P', Items.potato, 'F', FoodListMF.pastry,
                        'M', FoodListMF.generic_meat_mince_cooked, 'T', FoodListMF.pie_tray,});

        KnowledgeListMF.appleR = MineFantasyAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMF.pie_apple_uncooked), "applepie", chopping, "knife", 2, 120,
                new Object[]{"SPS", "MMM", "SPS", " T ", 'S', FoodListMF.sugarpot, 'P', FoodListMF.pastry, 'M',
                        Items.apple, 'T', FoodListMF.pie_tray,});
        KnowledgeListMF.pumpPieR = MineFantasyAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMF.pie_pumpkin_uncooked), "bread", chopping, "knife", 1, 50,
                new Object[]{"SMS", "SPS", " T ", 'S', FoodListMF.sugarpot, 'P', FoodListMF.pastry, 'M',
                        Blocks.pumpkin, 'T', FoodListMF.pie_tray,});
        KnowledgeListMF.berryR = MineFantasyAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMF.pie_berry_uncooked), "berrypie", chopping, "knife", 2, 100,
                new Object[]{"SPS", "MMM", "SPS", " T ", 'S', FoodListMF.sugarpot, 'P', FoodListMF.pastry, 'M',
                        FoodListMF.berries, 'T', FoodListMF.pie_tray,});

        KnowledgeListMF.simpCakeR = MineFantasyAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMF.cake_simple_raw), "bread", mixing, "spoon", -1, 15,
                new Object[]{"MMM", "SES", "FFF", " T ", 'F', FoodListMF.flour, 'E', Items.egg, 'M',
                        FoodListMF.jug_milk, 'S', FoodListMF.sugarpot, 'T', FoodListMF.cake_tin,});

        KnowledgeListMF.cakeR = MineFantasyAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMF.cake_raw),
                "cake", mixing, "spoon", -1, 20, new Object[]{"SMS", "SES", "FFF", " T ", 'F', FoodListMF.flour, 'E',
                        Items.egg, 'M', FoodListMF.jug_milk, 'S', FoodListMF.sugarpot, 'T', FoodListMF.cake_tin,});
        KnowledgeListMF.carrotCakeR = MineFantasyAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMF.cake_carrot_raw), "carrotcake", mixing, "spoon", -1, 25,
                new Object[]{"SMS", "SES", "CCC", "FTF", 'C', Items.carrot, 'F', FoodListMF.flour, 'E', Items.egg,
                        'M', FoodListMF.jug_milk, 'S', FoodListMF.sugarpot, 'T', FoodListMF.cake_tin,});
        KnowledgeListMF.chocoCakeR = MineFantasyAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMF.cake_choc_raw), "chococake", mixing, "spoon", -1, 25,
                new Object[]{"SMS", "SES", "CCC", "FTF", 'C', FoodListMF.chocolate, 'F', FoodListMF.flour, 'E',
                        Items.egg, 'M', FoodListMF.jug_milk, 'S', FoodListMF.sugarpot, 'T', FoodListMF.cake_tin,});
        KnowledgeListMF.bfCakeR = MineFantasyAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMF.cake_bf_raw),
                "bfcake", mixing, "spoon", -1, 30,
                new Object[]{"SMMS", "SEES", "CBBC", "FTFF", 'B', FoodListMF.berriesJuicy, 'C', FoodListMF.chocolate,
                        'F', FoodListMF.flour, 'E', Items.egg, 'M', FoodListMF.jug_milk, 'S', FoodListMF.sugarpot, 'T',
                        FoodListMF.cake_tin,});
        KnowledgeListMF.simpCakeOut = MineFantasyAPI.addCarpenterRecipe(provisioning, new ItemStack(Items.cake),
                "bread", basic, "knife", -1, 10,
                new Object[]{"I", "R", 'I', FoodListMF.icing, 'R', FoodListMF.cake_simple_uniced,});

        KnowledgeListMF.cakeI = MineFantasyAPI.addCarpenterRecipe(provisioning, new ItemStack(BlockListMF.cake_vanilla),
                "cake", basic, "knife", -1, 60,
                new Object[]{"III", " R ", 'I', FoodListMF.icing, 'R', FoodListMF.cake_uniced,});
        KnowledgeListMF.carrotCakeI = MineFantasyAPI.addCarpenterRecipe(provisioning,
                new ItemStack(BlockListMF.cake_carrot), "carrotcake", basic, "knife", -1, 60,
                new Object[]{"III", " R ", 'I', FoodListMF.icing, 'R', FoodListMF.cake_carrot_uniced,});
        KnowledgeListMF.chocoCakeI = MineFantasyAPI.addCarpenterRecipe(provisioning,
                new ItemStack(BlockListMF.cake_chocolate), "chococake", basic, "knife", -1, 60, new Object[]{"ICI",
                        " R ", 'C', FoodListMF.chocolate, 'I', FoodListMF.icing, 'R', FoodListMF.cake_choc_uniced,});
        KnowledgeListMF.bfCakeI = MineFantasyAPI.addCarpenterRecipe(provisioning, new ItemStack(BlockListMF.cake_bf),
                "bfcake", basic, "knife", -1, 100, new Object[]{"BBB", "III", "CRC", 'C', FoodListMF.chocolate, 'B',
                        FoodListMF.berries, 'I', FoodListMF.icing, 'R', FoodListMF.cake_bf_uniced,});

        KnowledgeListMF.cheeserollR = MineFantasyAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMF.cheese_roll), "cheeseroll", chopping, "knife", 1, 30,
                new Object[]{"C", "R", 'C', FoodListMF.cheese_slice, 'R', FoodListMF.breadroll,});
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
        KnowledgeListMF.fletchingR = MineFantasyAPI.addCarpenterRecipe(artisanry,
                new ItemStack(ComponentListMF.fletching, 16), "arrows", chopping, 4, new Object[]{"T", "F",

                        'F', Items.feather, 'T', ComponentListMF.plank,});
        KnowledgeListMF.fletchingR2 = MineFantasyAPI.addCarpenterRecipe(artisanry,
                new ItemStack(ComponentListMF.fletching, 4), "arrows", chopping, 4, new Object[]{" T ", "PPP",

                        'P', Items.paper, 'T', ComponentListMF.plank,});

        // BOMBS
        KnowledgeListMF.bombCaseCeramicR = MineFantasyAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMF.bomb_casing_uncooked, 2), "bombCeramic", basic, 2,
                new Object[]{" C ", "C C", " C ", 'C', Items.clay_ball,});
        KnowledgeListMF.mineCaseCeramicR = MineFantasyAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMF.mine_casing_uncooked), "mineCeramic", basic, 2,
                new Object[]{" P ", "C C", " C ",

                        'P', Blocks.stone_pressure_plate, 'C', Items.clay_ball,});
        KnowledgeListMF.bombCaseCrystalR = MineFantasyAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMF.bomb_casing_crystal), "bombCrystal", basic, 10,
                new Object[]{" D ", "R R", " B ", 'B', Items.glass_bottle, 'D', ComponentListMF.diamond_shards, 'R',
                        Items.redstone});
        KnowledgeListMF.mineCaseCrystalR = MineFantasyAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMF.mine_casing_crystal), "mineCrystal", basic, 10,
                new Object[]{" P ", "RDR", " B ", 'P', Blocks.heavy_weighted_pressure_plate, 'B', Items.glass_bottle,
                        'D', ComponentListMF.diamond_shards, 'R', Items.redstone});
        Salvage.addSalvage(ComponentListMF.bomb_casing_uncooked, new ItemStack(Items.clay_ball, 2));
        Salvage.addSalvage(ComponentListMF.mine_casing_uncooked, new ItemStack(Items.clay_ball, 3),
                Blocks.stone_pressure_plate);

        Salvage.addSalvage(ComponentListMF.bomb_casing_crystal, Items.glass_bottle, ComponentListMF.diamond_shards,
                new ItemStack(Items.redstone, 2));
        Salvage.addSalvage(ComponentListMF.mine_casing_crystal, Blocks.heavy_weighted_pressure_plate,
                Items.glass_bottle, ComponentListMF.diamond_shards, new ItemStack(Items.redstone, 2));

        KnowledgeListMF.bombFuseR = MineFantasyAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMF.bomb_fuse, 8), "bombs", basic, 4, new Object[]{"R", "C", "S", 'S',
                        ComponentListMF.thread, 'C', ComponentListMF.coalDust, 'R', Items.redstone,});
        KnowledgeListMF.longFuseR = MineFantasyAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMF.bomb_fuse_long), "bombs", basic, 1,
                new Object[]{"F", "R", "F", 'F', ComponentListMF.bomb_fuse, 'R', Items.redstone,});
        Salvage.addSalvage(ComponentListMF.bomb_fuse_long, new ItemStack(ComponentListMF.bomb_fuse, 2), Items.redstone);

        KnowledgeListMF.thatchR = MineFantasyAPI.addCarpenterRecipe(construction, new ItemStack(BlockListMF.thatch), "",
                "dig.grass", "hands", -1, 1, new Object[]{"HH", "HH", 'H', new ItemStack(Blocks.tallgrass, 1, 1)});
        KnowledgeListMF.thatchStairR = MineFantasyAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMF.thatch_stair), "", "dig.grass", "hands", -1, 1,
                new Object[]{"H ", "HH", 'H', new ItemStack(Blocks.tallgrass, 1, 1)});
        Salvage.addSalvage(BlockListMF.thatch_stair, new ItemStack(Blocks.tallgrass, 3, 1));
        Salvage.addSalvage(BlockListMF.thatch, new ItemStack(Blocks.tallgrass, 4, 1));

        KnowledgeListMF.apronRecipe = MineFantasyAPI.addCarpenterRecipe(null, new ItemStack(ArmourListMF.leatherapron),
                "", sewing, "hands", -1, 1, new Object[]{"LCL", " L ", 'L', Items.leather, 'C', Items.coal,});
        Salvage.addSalvage(ArmourListMF.leatherapron, new ItemStack(Items.leather, 3), Items.coal);

        KnowledgeListMF.hideHelmR = MineFantasyAPI.addCarpenterRecipe(null,
                ArmourListMF.armour(ArmourListMF.leather, 0, 0), "", sewing, "hands", -1, 1,
                new Object[]{"H", "C", "H", 'H', ComponentListMF.hideSmall, 'C', Blocks.wool,});
        KnowledgeListMF.hideChestR = MineFantasyAPI.addCarpenterRecipe(null,
                ArmourListMF.armour(ArmourListMF.leather, 0, 1), "", sewing, "hands", -1, 1,
                new Object[]{"H", "C", 'H', ComponentListMF.hideLarge, 'C', Blocks.wool,});
        KnowledgeListMF.hideLegsR = MineFantasyAPI.addCarpenterRecipe(null,
                ArmourListMF.armour(ArmourListMF.leather, 0, 2), "", sewing, "hands", -1, 1,
                new Object[]{"H", "C", 'H', ComponentListMF.hideMedium, 'C', Blocks.wool,});
        KnowledgeListMF.hideBootsR = MineFantasyAPI.addCarpenterRecipe(null,
                ArmourListMF.armour(ArmourListMF.leather, 0, 3), "", sewing, "hands", -1, 1,
                new Object[]{"H", "C", 'H', ComponentListMF.hideSmall, 'C', Blocks.wool,});

        Salvage.addSalvage(ArmourListMF.armourItem(ArmourListMF.leather, 0, 0),
                new ItemStack(ComponentListMF.hideSmall, 2), Blocks.wool);
        Salvage.addSalvage(ArmourListMF.armourItem(ArmourListMF.leather, 0, 1), ComponentListMF.hideLarge, Blocks.wool);
        Salvage.addSalvage(ArmourListMF.armourItem(ArmourListMF.leather, 0, 2), ComponentListMF.hideMedium,
                Blocks.wool);
        Salvage.addSalvage(ArmourListMF.armourItem(ArmourListMF.leather, 0, 3), ComponentListMF.hideSmall, Blocks.wool);

        KnowledgeListMF.bedrollR = MineFantasyAPI.addCarpenterRecipe(null, new ItemStack(ToolListMF.bedroll), "",
                sewing, "needle", -1, 50,
                new Object[]{"TLT", "CCC", 'C', Blocks.wool, 'T', ComponentListMF.thread, 'L', Items.leather});
        Salvage.addSalvage(ToolListMF.bedroll, new ItemStack(Blocks.wool, 3), Items.leather,
                new ItemStack(ComponentListMF.thread, 3));
    }

    public static void addCrossbows() {
        // CROSSBOWS
        KnowledgeListMF.crossHandleWoodR = MineFantasyAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMF.crossbow_handle_wood), "crossShafts", nailHammer, "hammer", 2, 150,
                new Object[]{"N N", "PP ", " P ", 'P', ComponentListMF.plank.construct("RefinedWood"), 'N',
                        ComponentListMF.nail});
        KnowledgeListMF.crossStockWoodR = MineFantasyAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMF.crossbow_stock_wood), "crossShafts", nailHammer, "hammer", 2, 300,
                new Object[]{"NN N", "PPPP", " PPP", 'P', ComponentListMF.plank.construct("RefinedWood"), 'N',
                        ComponentListMF.nail});
        KnowledgeListMF.crossStockIronR = MineFantasyAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMF.crossbow_stock_iron), "crossShaftAdvanced", spanner, "spanner", 2, 300,
                new Object[]{" BBB", "BOGG", "SWSS", "    ", 'O', Blocks.obsidian, 'G',
                        ComponentListMF.tungsten_gears, 'W', ComponentListMF.crossbow_stock_wood, 'S',
                        ComponentListMF.iron_strut, 'B', ComponentListMF.bolt,});

        KnowledgeListMF.crossHeadLightR = MineFantasyAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMF.cross_arms_light), "crossHeads", nailHammer, "hammer", 2, 200,
                new Object[]{"PPP", "NSN", " P ", 'P', ComponentListMF.plank.construct("RefinedWood"), 'N',
                        ComponentListMF.nail, 'S', Items.string,});
        KnowledgeListMF.crossHeadMediumR = MineFantasyAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMF.cross_arms_basic), "crossHeads", nailHammer, "hammer", 2, 250,
                new Object[]{"NNN", "PAP", 'N', ComponentListMF.nail, 'P',
                        ComponentListMF.plank.construct("RefinedWood"), 'A', ComponentListMF.cross_arms_light,});
        KnowledgeListMF.crossHeadHeavyR = MineFantasyAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMF.cross_arms_heavy), "crossHeads", nailHammer, "hammer", 2, 350,
                new Object[]{"NNN", "PAP", 'N', ComponentListMF.nail, 'P',
                        ComponentListMF.plank.construct("RefinedWood"), 'A', ComponentListMF.cross_arms_basic,});
        KnowledgeListMF.crossHeadAdvancedR = MineFantasyAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMF.cross_arms_advanced), "crossHeadAdvanced", nailHammer, "hammer", 2, 350,
                new Object[]{"NRN", "RGR", " A ", 'G', ComponentListMF.tungsten_gears, 'N', ComponentListMF.nail, 'R',
                        ComponentListMF.steel_tube, 'A', ComponentListMF.cross_arms_basic,});

        KnowledgeListMF.crossAmmoR = MineFantasyAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMF.cross_ammo), "crossAmmo", nailHammer, "hammer", 2, 200,
                new Object[]{"NNN", "P P", "PGP", "PPP", 'G', ComponentListMF.tungsten_gears, 'P',
                        ComponentListMF.plank.construct("RefinedWood"), 'N', ComponentListMF.nail,});
        KnowledgeListMF.crossScopeR = MineFantasyAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMF.cross_scope), "crossScope", spanner, "spanner", 2, 150,
                new Object[]{"BSB", "GP ", 'G', ComponentListMF.tungsten_gears, 'S', ToolListMF.spyglass, 'P',
                        ComponentListMF.plank.construct("RefinedWood"), 'B', ComponentListMF.bolt,});
        Salvage.addSalvage(ComponentListMF.cross_arms_light, new ItemStack(ComponentListMF.nail, 2), Items.string,
                ComponentListMF.plank.construct("RefinedWood", 4));
        Salvage.addSalvage(ComponentListMF.cross_arms_basic, new ItemStack(ComponentListMF.nail, 3),
                ComponentListMF.cross_arms_light, ComponentListMF.plank.construct("RefinedWood", 2));
        Salvage.addSalvage(ComponentListMF.cross_arms_heavy, new ItemStack(ComponentListMF.nail, 3),
                ComponentListMF.cross_arms_basic, ComponentListMF.plank.construct("RefinedWood", 2));
        Salvage.addSalvage(ComponentListMF.cross_arms_advanced, ComponentListMF.tungsten_gears,
                new ItemStack(ComponentListMF.nail, 2), ComponentListMF.cross_arms_basic,
                new ItemStack(ComponentListMF.steel_tube, 3));

        Salvage.addSalvage(ComponentListMF.cross_scope, ComponentListMF.tungsten_gears, ToolListMF.spyglass,
                new ItemStack(ComponentListMF.bolt, 2), ComponentListMF.plank.construct("RefinedWood"));
        Salvage.addSalvage(ComponentListMF.cross_ammo, ComponentListMF.tungsten_gears,
                new ItemStack(ComponentListMF.nail, 3), ComponentListMF.plank.construct("RefinedWood", 7));
        Salvage.addSalvage(ComponentListMF.crossbow_handle_wood, new ItemStack(ComponentListMF.nail, 2),
                ComponentListMF.plank.construct("RefinedWood", 3));
        Salvage.addSalvage(ComponentListMF.crossbow_stock_wood, new ItemStack(ComponentListMF.nail, 3),
                ComponentListMF.plank.construct("RefinedWood", 7));
        Salvage.addSalvage(ComponentListMF.crossbow_stock_iron, new ItemStack(ComponentListMF.tungsten_gears, 2),
                Blocks.obsidian, new ItemStack(ComponentListMF.bolt, 4), new ItemStack(ComponentListMF.iron_strut, 3),
                ComponentListMF.crossbow_stock_wood);
    }

    private static void addEngineering() {
        addCrossbows();
        KnowledgeListMF.bombBenchCraft = MineFantasyAPI.addCarpenterRecipe(engineering,
                new ItemStack(BlockListMF.bombBench), "bombs", spanner, "spanner", 0, 150, new Object[]{"BFB", "BCB",
                        'B', ComponentListMF.bolt, 'F', ComponentListMF.iron_frame, 'C', BlockListMF.carpenter,});
        KnowledgeListMF.bombPressCraft = MineFantasyAPI.addCarpenterRecipe(engineering,
                new ItemStack(BlockListMF.bombPress), "bpress", spanner, "spanner", 3, 200,
                new Object[]{"BFB", "GGL", "SPS", 'S', ComponentListMF.iron_strut, 'B', ComponentListMF.bolt, 'F',
                        ComponentListMF.iron_frame, 'L', Blocks.lever, 'P',
                        new ItemStack(CustomToolListMF.standard_spanner, 1, 0), 'G', ComponentListMF.bronze_gears,});

        KnowledgeListMF.crossBenchCraft = MineFantasyAPI.addCarpenterRecipe(engineering,
                new ItemStack(BlockListMF.crossbowBench), "crossbows", spanner, "spanner", 0, 200,
                new Object[]{" F ", "PSP", "NCN", 'F', ComponentListMF.iron_frame, 'P', ComponentListMF.plank, 'N',
                        ComponentListMF.bolt, 'S', Items.string, 'C', BlockListMF.carpenter,});

        KnowledgeListMF.engTannerR = MineFantasyAPI.addCarpenterRecipe(engineering,
                new ItemStack(BlockListMF.engTanner), "engTanner", spanner, "spanner", 3, 300,
                new Object[]{"BLB", "SPS", "GGG", "SFS", 'S', ComponentListMF.iron_strut, 'B', ComponentListMF.bolt,
                        'F', ComponentListMF.iron_frame, 'L', Blocks.lever, 'P',
                        new ItemStack(CustomToolListMF.standard_knife, 1, 0), 'G', ComponentListMF.bronze_gears,});
        ItemStack blackPlate = ComponentListMF.plate.createComm("blackSteel");
        KnowledgeListMF.advancedForgeR = MineFantasyAPI.addCarpenterRecipe(engineering,
                new ItemStack(BlockListMF.forge_metal), "advforge", spanner, "spanner", 4, 400,
                new Object[]{" T  ", "FRRF", "PPPP", "BBBB", 'B', ComponentListMF.bolt, 'F',
                        ComponentListMF.iron_frame, 'T', ToolListMF.engin_anvil_tools, 'P', blackPlate, 'R',
                        Blocks.redstone_block,});
        ItemStack steelPlate = ComponentListMF.plate.createComm("steel");
        KnowledgeListMF.autoCrucibleR = MineFantasyAPI.addCarpenterRecipe(engineering,
                new ItemStack(BlockListMF.crucibleauto), "advcrucible", spanner, "spanner", 4, 200,
                new Object[]{" T ", "PCP", "PGP", "BBB", 'B', ComponentListMF.bolt, 'C', BlockListMF.crucibleadv, 'G',
                        ComponentListMF.tungsten_gears, 'T', ToolListMF.engin_anvil_tools, 'P', steelPlate});
        KnowledgeListMF.spyglassR = MineFantasyAPI.addCarpenterRecipe(engineering, new ItemStack(ToolListMF.spyglass),
                "spyglass", spanner, "spanner", 1, 300,
                new Object[]{" T ", "BCB", "GPG", 'C', ComponentListMF.bronze_gears, 'G', Blocks.glass, 'B',
                        ComponentListMF.bolt, 'T', ToolListMF.engin_anvil_tools, 'P', ComponentListMF.steel_tube,});

        KnowledgeListMF.syringeR = MineFantasyAPI.addCarpenterRecipe(engineering,
                new ItemStack(ToolListMF.syringe_empty), "syringe", spanner, "spanner", 1, 200,
                new Object[]{"E", "T", "B", "N", 'E', ToolListMF.engin_anvil_tools, 'T', ComponentListMF.steel_tube,
                        'B', Items.glass_bottle, 'N', new ItemStack(CustomToolListMF.standard_needle),});

        KnowledgeListMF.parachuteR = MineFantasyAPI.addCarpenterRecipe(engineering, new ItemStack(ToolListMF.parachute),
                "parachute", sewing, "needle", 1, 350,
                new Object[]{"TTT", "CCC", "BEB", "BLB", 'E', ToolListMF.engin_anvil_tools, 'T',
                        ComponentListMF.thread, 'B', ComponentListMF.leather_strip, 'L', Items.leather, 'C',
                        Blocks.wool,});
        KnowledgeListMF.cogShaftR = MineFantasyAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMF.cogwork_shaft), "cogArmour", spanner, "spanner", 4, 150,
                new Object[]{"BPB", "SGS", "BFB",

                        'P', Blocks.piston, 'G', ComponentListMF.tungsten_gears, 'B', ComponentListMF.bolt, 'F',
                        ComponentListMF.iron_frame, 'S', ComponentListMF.iron_strut,});
        Salvage.addSalvage(ComponentListMF.cogwork_shaft, new ItemStack(ComponentListMF.iron_strut, 2),
                new ItemStack(ComponentListMF.bolt, 4), ComponentListMF.iron_frame, Blocks.piston,
                ComponentListMF.tungsten_gears);

        Salvage.addSalvage(BlockListMF.crucibleauto, new ItemStack(ComponentListMF.bolt, 3),
                ComponentListMF.tungsten_gears, BlockListMF.crucibleadv, steelPlate, steelPlate, steelPlate,
                steelPlate);
        Salvage.addSalvage(BlockListMF.bombBench, new ItemStack(ComponentListMF.bolt, 4), ComponentListMF.iron_frame,
                BlockListMF.carpenter);
        Salvage.addSalvage(BlockListMF.crossbowBench, new ItemStack(ComponentListMF.nail, 2),
                ComponentListMF.plank.construct("ScrapWood", 2), Items.string, BlockListMF.carpenter);
        Salvage.addSalvage(BlockListMF.bombPress, new ItemStack(ComponentListMF.iron_strut, 2),
                new ItemStack(ComponentListMF.bolt, 2), new ItemStack(ComponentListMF.bronze_gears, 2), Blocks.lever,
                ComponentListMF.iron_frame);
        Salvage.addSalvage(BlockListMF.engTanner, new ItemStack(ComponentListMF.iron_strut, 4),
                new ItemStack(ComponentListMF.bolt, 2), new ItemStack(ComponentListMF.bronze_gears, 3),
                CustomToolListMF.standard_needle, Blocks.lever, ComponentListMF.iron_frame);
        Salvage.addSalvage(BlockListMF.forge_metal, new ItemStack(ComponentListMF.bolt, 4), blackPlate, blackPlate,
                blackPlate, blackPlate, new ItemStack(ComponentListMF.iron_frame, 2),
                new ItemStack(Blocks.redstone_block, 2));
        Salvage.addSalvage(ToolListMF.spyglass, new ItemStack(ComponentListMF.bolt, 2), new ItemStack(Blocks.glass, 2),
                ComponentListMF.steel_tube, ComponentListMF.bronze_gears);
        Salvage.addSalvage(ToolListMF.syringe_empty, Items.glass_bottle, CustomToolListMF.standard_needle,
                ComponentListMF.steel_tube);
        Salvage.addSalvage(ToolListMF.parachute, new ItemStack(ComponentListMF.thread, 3),
                new ItemStack(Blocks.wool, 3), new ItemStack(ComponentListMF.leather_strip, 4), Items.leather);
    }

    private static void addNonPrimitiveStone() {
        KnowledgeListMF.stoneKnifeR = MineFantasyAPI.addCarpenterRecipe(null, new ItemStack(ToolListMF.knifeStone), "",
                primitive, "hands", -1, 4,
                new Object[]{"R", "R", "S", 'R', Blocks.cobblestone, 'S', ComponentListMF.plank,});
        KnowledgeListMF.stoneHammerR = MineFantasyAPI.addCarpenterRecipe(null, new ItemStack(ToolListMF.hammerStone),
                "", primitive, "hands", -1, 4,
                new Object[]{"R", "S", 'R', Blocks.cobblestone, 'S', ComponentListMF.plank,});

        KnowledgeListMF.stoneTongsR = MineFantasyAPI.addCarpenterRecipe(null, new ItemStack(ToolListMF.tongsStone), "",
                primitive, "hands", -1, 4,
                new Object[]{"R ", "SR", 'R', Blocks.cobblestone, 'S', ComponentListMF.plank,});
        KnowledgeListMF.boneNeedleR = MineFantasyAPI.addCarpenterRecipe(null, new ItemStack(ToolListMF.needleBone), "",
                primitive, "hands", -1, 4, new Object[]{"B", 'B', Items.bone,});

        Salvage.addSalvage(ToolListMF.knifeStone, new ItemStack(Blocks.cobblestone, 2),
                ComponentListMF.plank.construct("ScrapWood"));
        Salvage.addSalvage(ToolListMF.hammerStone, Blocks.cobblestone, ComponentListMF.plank.construct("ScrapWood"));
        Salvage.addSalvage(ToolListMF.tongsStone, new ItemStack(Blocks.cobblestone, 2),
                ComponentListMF.plank.construct("ScrapWood"));
        Salvage.addSalvage(ToolListMF.needleBone, Items.bone);
    }

    private static void addPrimitive() {
        KnowledgeListMF.dirtRockR = MineFantasyAPI.addCarpenterRecipe(null, new ItemStack(ComponentListMF.sharp_rock),
                "", "minecraft:dig.gravel", "hands", -1, 1, new Object[]{"D", 'D', Blocks.dirt,});

        KnowledgeListMF.stonePickR = MineFantasyAPI.addCarpenterRecipe(null, new ItemStack(ToolListMF.pickStone), "",
                primitive, "hands", -1, 5, new Object[]{"RVR", " S ", " S ", 'R', ComponentListMF.sharp_rock, 'V',
                        ComponentListMF.vine, 'S', Items.stick});
        KnowledgeListMF.stoneAxeR = MineFantasyAPI.addCarpenterRecipe(null, new ItemStack(ToolListMF.axeStone), "",
                primitive, "hands", -1, 5, new Object[]{"RV", "RS", " S", 'R', ComponentListMF.sharp_rock, 'V',
                        ComponentListMF.vine, 'S', Items.stick});
        KnowledgeListMF.stoneSpadeR = MineFantasyAPI.addCarpenterRecipe(null, new ItemStack(ToolListMF.spadeStone), "",
                primitive, "hands", -1, 5, new Object[]{"VR", " S", " S", 'R', ComponentListMF.sharp_rock, 'V',
                        ComponentListMF.vine, 'S', Items.stick});
        KnowledgeListMF.stoneHoeR = MineFantasyAPI.addCarpenterRecipe(null, new ItemStack(ToolListMF.hoeStone), "",
                primitive, "hands", -1, 5, new Object[]{"RV", " S", " S", 'R', ComponentListMF.sharp_rock, 'V',
                        ComponentListMF.vine, 'S', Items.stick});
        KnowledgeListMF.stoneSwordR = MineFantasyAPI.addCarpenterRecipe(null, new ItemStack(ToolListMF.swordStone), "",
                primitive, "hands", -1, 8, new Object[]{"R ", "R ", "SV", 'R', ComponentListMF.sharp_rock, 'V',
                        ComponentListMF.vine, 'S', Items.stick});
        KnowledgeListMF.stoneWarR = MineFantasyAPI.addCarpenterRecipe(null, new ItemStack(ToolListMF.waraxeStone), "",
                primitive, "hands", -1, 8, new Object[]{"VRV", "RS", " S", 'R', ComponentListMF.sharp_rock, 'V',
                        ComponentListMF.vine, 'S', Items.stick});
        KnowledgeListMF.stoneMaceR = MineFantasyAPI.addCarpenterRecipe(null, new ItemStack(ToolListMF.maceStone), "",
                primitive, "hands", -1, 8, new Object[]{" V ", "RSR", " S ", 'R', ComponentListMF.sharp_rock, 'V',
                        ComponentListMF.vine, 'S', Items.stick});
        KnowledgeListMF.stoneSpearR = MineFantasyAPI.addCarpenterRecipe(null, new ItemStack(ToolListMF.spearStone), "",
                primitive, "hands", -1, 8, new Object[]{"R", "V", "S", "S", 'R', ComponentListMF.sharp_rock, 'V',
                        ComponentListMF.vine, 'S', Items.stick});
        KnowledgeListMF.stoneKnifeR = MineFantasyAPI.addCarpenterRecipe(null, new ItemStack(ToolListMF.knifeStone), "",
                primitive, "hands", -1, 4, new Object[]{"R ", "SV", 'R', ComponentListMF.sharp_rock, 'V',
                        ComponentListMF.vine, 'S', Items.stick});
        KnowledgeListMF.stoneHammerR = MineFantasyAPI.addCarpenterRecipe(null, new ItemStack(ToolListMF.hammerStone),
                "", primitive, "hands", -1, 4, new Object[]{"R", "V", "S", 'R', ComponentListMF.sharp_rock, 'V',
                        ComponentListMF.vine, 'S', Items.stick});

        KnowledgeListMF.stoneTongsR = MineFantasyAPI.addCarpenterRecipe(null, new ItemStack(ToolListMF.tongsStone), "",
                primitive, "hands", -1, 4, new Object[]{" R", "SV", 'R', ComponentListMF.sharp_rock, 'V',
                        ComponentListMF.vine, 'S', Items.stick});
        KnowledgeListMF.boneNeedleR = MineFantasyAPI.addCarpenterRecipe(null, new ItemStack(ToolListMF.needleBone), "",
                primitive, "hands", -1, 4, new Object[]{"B", 'B', Items.bone,});

        Salvage.addSalvage(ToolListMF.pickStone, new ItemStack(ComponentListMF.sharp_rock, 2),
                new ItemStack(Items.stick, 2), ComponentListMF.vine);
        Salvage.addSalvage(ToolListMF.axeStone, new ItemStack(ComponentListMF.sharp_rock, 2),
                new ItemStack(Items.stick, 2), ComponentListMF.vine);
        Salvage.addSalvage(ToolListMF.spadeStone, ComponentListMF.sharp_rock, new ItemStack(Items.stick, 2),
                ComponentListMF.vine);
        Salvage.addSalvage(ToolListMF.hoeStone, ComponentListMF.sharp_rock, new ItemStack(Items.stick, 2),
                ComponentListMF.vine);
        Salvage.addSalvage(ToolListMF.swordStone, Items.stick, new ItemStack(ComponentListMF.sharp_rock, 2),
                ComponentListMF.vine);
        Salvage.addSalvage(ToolListMF.waraxeStone, new ItemStack(ComponentListMF.sharp_rock, 2),
                new ItemStack(Items.stick, 2), new ItemStack(ComponentListMF.vine, 2));
        Salvage.addSalvage(ToolListMF.maceStone, new ItemStack(ComponentListMF.sharp_rock, 2),
                new ItemStack(Items.stick, 2), ComponentListMF.vine);
        Salvage.addSalvage(ToolListMF.spearStone, ComponentListMF.sharp_rock, new ItemStack(Items.stick, 2),
                ComponentListMF.vine);
        Salvage.addSalvage(ToolListMF.knifeStone, ComponentListMF.sharp_rock, Items.stick, ComponentListMF.vine);
        Salvage.addSalvage(ToolListMF.hammerStone, ComponentListMF.sharp_rock, Items.stick, ComponentListMF.vine);
        Salvage.addSalvage(ToolListMF.tongsStone, ComponentListMF.sharp_rock, Items.stick, ComponentListMF.vine);
        Salvage.addSalvage(ToolListMF.needleBone, Items.bone);
    }

    public static void initTierWood() {
        String basic = CarpenterRecipes.basic;

        float time = 4;
        Item plank = ComponentListMF.plank;

        KnowledgeListMF.spoonR = MineFantasyAPI.addCarpenterToolRecipe(artisanry, CustomToolListMF.standard_spoon, "",
                basic, "hands", -1, 1 + (int) (1 * time), new Object[]{"W", "S", 'W', plank, 'S', Items.stick});
        Salvage.addSalvage(CustomToolListMF.standard_spoon, plank, Items.stick);
        KnowledgeListMF.malletR = MineFantasyAPI.addCarpenterToolRecipe(artisanry, CustomToolListMF.standard_mallet, "",
                basic, "hands", -1, 1 + (int) (2 * time), new Object[]{"WW", " S", 'W', plank, 'S', Items.stick});
        Salvage.addSalvage(CustomToolListMF.standard_mallet, plank, plank, Items.stick);
        Salvage.addSalvage(CustomToolListMF.standard_spoon, plank, Items.stick);

        KnowledgeListMF.refinedPlankR.add(MineFantasyAPI.addCarpenterRecipe(construction,
                ComponentListMF.plank.construct("RefinedWood"), "", basic, "hands", -1, 1,
                new Object[]{"O", "P", 'O', ComponentListMF.plant_oil, 'P', (ComponentListMF.plank)}));
        KnowledgeListMF.easyPaintPlank.add(MineFantasyAPI.addCarpenterRecipe(construction,
                ComponentListMF.plank.construct("RefinedWood", 4), "paint_brush", sewing, "brush", -1, 2,
                new Object[]{" O  ", "PPPP", 'O', ComponentListMF.plant_oil, 'P', (ComponentListMF.plank)}));
    }

    static void tryAddSawPlanks(ItemStack planks, CustomMaterial material) {
        String sub = material.name.substring(0, material.name.length() - 4).toLowerCase();

        if (planks.getUnlocalizedName().toLowerCase().contains(sub)) {
            addSawPlanks(planks, material);
        }
    }

    static void addSawPlanks(ItemStack planks, CustomMaterial material) {
        MineFantasyAPI.addCarpenterRecipe(construction, (ComponentListMF.plank).construct(material.name, 4),
                "commodities", sawing, "saw", -1, 10, new Object[]{"P", 'P', planks.copy()});
    }
}
