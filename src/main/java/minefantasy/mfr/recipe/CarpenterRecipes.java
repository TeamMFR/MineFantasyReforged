package minefantasy.mfr.recipe;


import minefantasy.mfr.init.LeatherArmourListMFR;
import minefantasy.mfr.init.BlockListMFR;
import minefantasy.mfr.init.FoodListMFR;
import minefantasy.mfr.api.MineFantasyRebornAPI;
import minefantasy.mfr.api.crafting.Salvage;
import minefantasy.mfr.api.crafting.refine.PaintOilRecipe;
import minefantasy.mfr.api.crafting.refine.QuernRecipes;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.api.rpg.Skill;
import minefantasy.mfr.api.rpg.SkillList;
import minefantasy.mfr.block.decor.BlockWoodDecor;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.CustomToolListMFR;
import minefantasy.mfr.init.SoundsMFR;
import minefantasy.mfr.init.ToolListMFR;
import minefantasy.mfr.init.KnowledgeListMFR;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.oredict.OreDictionary;

public class CarpenterRecipes {
    public static final SoundEvent basic = SoundEvents.BLOCK_WOOD_STEP;
    public static final SoundEvent chopping = SoundEvents.BLOCK_WOOD_HIT;
    public static final SoundEvent primitive = SoundsMFR.CRAFT_PRIMITIVE;
    public static final SoundEvent sewing = SoundEvents.BLOCK_CLOTH_STEP;
    public static final SoundEvent stonemason = SoundsMFR.HAMMER_CARPENTER;
    public static final SoundEvent snipping = SoundEvents.ENTITY_SHEEP_SHEAR;
    public static final SoundEvent sawing = SoundsMFR.SAW_CARPENTER;
    public static final SoundEvent grinding = SoundEvents.BLOCK_GRAVEL_STEP;
    public static final SoundEvent nailHammer = SoundsMFR.HAMMER_CARPENTER;
    public static final SoundEvent woodHammer = SoundsMFR.CARPENTER_MALLET;
    public static final SoundEvent mixing = SoundEvents.BLOCK_WOOD_STEP;
    public static final SoundEvent spanner = SoundsMFR.TWIST_BOLT;

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
        MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.RESEARCH_BOOK), "", sewing, "hands", -1, 1, new Object[]{"B", 'B', Items.BOOK,});

        if (ConfigHardcore.HCCallowRocks) {
            KnowledgeListMFR.sharpRocksR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ComponentListMFR.SHARP_ROCK, 8), "", stonemason, "hammer", -1, 10, new Object[]{"S", 'S', Blocks.COBBLESTONE,});

        }
        Salvage.addSalvage(ToolListMFR.DRY_ROCKS, Blocks.COBBLESTONE);

        KnowledgeListMFR.threadR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ComponentListMFR.THREAD, 4),
                "commodities", sewing, "hands", -1, 5, new Object[]{"W", "S", 'W', Blocks.WOOL, 'S', Items.STICK,});
        KnowledgeListMFR.stringR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(Items.STRING), "commodities",
                sewing, "hands", -1, 10, new Object[]{"T", "T", "T", "T", 'T', ComponentListMFR.THREAD});

        KnowledgeListMFR.lStripsR = MineFantasyRebornAPI.addCarpenterRecipe(null,
                new ItemStack(ComponentListMFR.LEATHER_STRIP, 4), "commodities", snipping, "shears", -1, 10,
                new Object[]{"L", 'L', Items.LEATHER,});

        MineFantasyRebornAPI.addCarpenterRecipe(artisanry, new ItemStack(ToolListMFR.TRAINING_SWORD), nailHammer, "knife", 1,
                40, new Object[]{"NI  ", "SIII", "NI  ", 'N', ComponentListMFR.NAIL, 'S', ComponentListMFR.PLANK, 'I',
                        Blocks.PLANKS,});

        MineFantasyRebornAPI.addCarpenterRecipe(artisanry, new ItemStack(ToolListMFR.TRAINING_WARAXE), nailHammer, "knife", 1,
                30, new Object[]{" II ", "SSIN", "  I ", 'N', ComponentListMFR.NAIL, 'S', ComponentListMFR.PLANK, 'I',
                        Blocks.PLANKS,});
        MineFantasyRebornAPI.addCarpenterRecipe(artisanry, new ItemStack(ToolListMFR.TRAINING_MACE), nailHammer, "knife", 1, 35,
                new Object[]{"  II", "SSII", "  N ", 'N', ComponentListMFR.NAIL, 'S', ComponentListMFR.PLANK, 'I',
                        Blocks.PLANKS,});
        MineFantasyRebornAPI.addCarpenterRecipe(artisanry, new ItemStack(ToolListMFR.TRAINING_SPEAR), nailHammer, "knife", 1,
                20, new Object[]{"  N ", "SSSI", "  N ", 'N', ComponentListMFR.NAIL, 'S', ComponentListMFR.PLANK, 'I',
                        Blocks.PLANKS,});
        ItemStack scrapWood = ComponentListMFR.PLANK.construct("ScrapWood");
        Salvage.addSalvage(ToolListMFR.TRAINING_SWORD, new ItemStack(Blocks.PLANKS, 5),
                new ItemStack(ComponentListMFR.NAIL, 2), scrapWood);
        Salvage.addSalvage(ToolListMFR.TRAINING_WARAXE, new ItemStack(Blocks.PLANKS, 4), ComponentListMFR.NAIL, scrapWood,
                scrapWood);
        Salvage.addSalvage(ToolListMFR.TRAINING_MACE, new ItemStack(Blocks.PLANKS, 4), ComponentListMFR.NAIL, scrapWood,
                scrapWood);
        Salvage.addSalvage(ToolListMFR.TRAINING_WARAXE, Blocks.PLANKS, new ItemStack(ComponentListMFR.NAIL, 2), scrapWood,
                scrapWood, scrapWood);

        KnowledgeListMFR.badBandageR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(ToolListMFR.BANDAGE_CRUDE, 2), "bandage", sewing, "needle", -1, 10,
                new Object[]{"LLL", 'L', ComponentListMFR.RAWHIDE_SMALL,});

        MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(ToolListMFR.BANDAGE_CRUDE, 4), "bandage", sewing,
                "needle", -1, 20, new Object[]{"LLL", 'L', ComponentListMFR.RAWHIDE_MEDIUM,});
        MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(ToolListMFR.BANDAGE_CRUDE, 6), "bandage", sewing,
                "needle", -1, 30, new Object[]{"LLL", 'L', ComponentListMFR.RAWHIDE_LARGE,});
        KnowledgeListMFR.bandageR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(ToolListMFR.BANDAGE_WOOL, 4), "bandage", sewing, "needle", 1, 10,
                new Object[]{"CTC", 'T', ComponentListMFR.THREAD, 'C', Blocks.WOOL,});

        KnowledgeListMFR.goodBandageR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(ToolListMFR.BANDAGE_TOUGH), "bandageadv", sewing, "needle", 2, 20,
                new Object[]{"T", "L", "B", 'T', ComponentListMFR.THREAD, 'L', ComponentListMFR.LEATHER_STRIP, 'B',
                        ToolListMFR.BANDAGE_WOOL});

        KnowledgeListMFR.roughHelmetR = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 1, 0), "craftArmourBasic", sewing, "needle", -1, 25,
                new Object[]{"TLT", "S S", 'T', ComponentListMFR.THREAD, 'S', ComponentListMFR.LEATHER_STRIP, 'L',
                        Items.LEATHER});
        KnowledgeListMFR.roughChestR = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 1, 1), "craftArmourBasic", sewing, "needle", -1, 40,
                new Object[]{"S S", "LLL", "TLT", 'T', ComponentListMFR.THREAD, 'S', ComponentListMFR.LEATHER_STRIP,
                        'L', Items.LEATHER});
        KnowledgeListMFR.roughLegsR = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 1, 2), "craftArmourBasic", sewing, "needle", -1, 35,
                new Object[]{"TLT", "L L", "S S", 'T', ComponentListMFR.THREAD, 'S', ComponentListMFR.LEATHER_STRIP,
                        'L', Items.LEATHER});
        KnowledgeListMFR.roughBootsR = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 1, 3), "craftArmourBasic", sewing, "needle", -1, 20,
                new Object[]{"T T", "S S", 'T', ComponentListMFR.THREAD, 'S', ComponentListMFR.LEATHER_STRIP,});
        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 0),
                new ItemStack(ComponentListMFR.THREAD, 2), new ItemStack(ComponentListMFR.LEATHER_STRIP, 2),
                Items.LEATHER);
        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 1),
                new ItemStack(ComponentListMFR.THREAD, 4), new ItemStack(ComponentListMFR.LEATHER_STRIP, 2),
                new ItemStack(Items.LEATHER, 4));
        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 2),
                new ItemStack(ComponentListMFR.THREAD, 4), new ItemStack(ComponentListMFR.LEATHER_STRIP, 2),
                new ItemStack(Items.LEATHER, 3));
        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 3),
                new ItemStack(ComponentListMFR.THREAD, 4), new ItemStack(ComponentListMFR.LEATHER_STRIP, 2));

        KnowledgeListMFR.reHelmetR = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 2, 0), "craftArmourLight", sewing, "needle", 1, 50,
                new Object[]{"TTT", "UPU", 'T', ComponentListMFR.THREAD, 'P',
                        LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 1, 0), 'U', Items.LEATHER});
        KnowledgeListMFR.reChestR = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 2, 1), "craftArmourLight", sewing, "needle", 1, 80,
                new Object[]{"TTT", "UPU", 'T', ComponentListMFR.THREAD, 'P',
                        LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 1, 1), 'U', Items.LEATHER});
        KnowledgeListMFR.reLegsR = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 2, 2), "craftArmourLight", sewing, "needle", 1, 70,
                new Object[]{"TTT", "UPU", 'T', ComponentListMFR.THREAD, 'P',
                        LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 1, 2), 'U', Items.LEATHER});
        KnowledgeListMFR.reBootsR = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 2, 3), "craftArmourLight", sewing, "needle", 1, 40,
                new Object[]{"TTT", "UPU", 'T', ComponentListMFR.THREAD, 'P',
                        LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 1, 3), 'U', Items.LEATHER});
        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 0),
                LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 0), new ItemStack(ComponentListMFR.THREAD, 3),
                new ItemStack(Items.LEATHER, 2));
        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 1),
                LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 1), new ItemStack(ComponentListMFR.THREAD, 3),
                new ItemStack(Items.LEATHER, 2));
        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 2),
                LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 2), new ItemStack(ComponentListMFR.THREAD, 3),
                new ItemStack(Items.LEATHER, 2));
        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 3),
                LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 3), new ItemStack(ComponentListMFR.THREAD, 3),
                new ItemStack(Items.LEATHER, 2));

        // PADDING
        KnowledgeListMFR.padding[0] = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 4, 0), "craftArmourLight", sewing, "needle", 1, 50,
                new Object[]{" W ", "SPS", " S ", 'P', LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 1, 0), 'W',
                        Blocks.WOOL, 'S', ComponentListMFR.THREAD,});
        KnowledgeListMFR.padding[1] = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 4, 1), "craftArmourLight", sewing, "needle", 1, 80,
                new Object[]{" W ", "SPS", " S ", 'P', LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 1, 1), 'W',
                        Blocks.WOOL, 'S', ComponentListMFR.THREAD,});
        KnowledgeListMFR.padding[2] = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 4, 2), "craftArmourLight", sewing, "needle", 1, 70,
                new Object[]{" W ", "SPS", " S ", 'P', LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 1, 2), 'W',
                        Blocks.WOOL, 'S', ComponentListMFR.THREAD,});
        KnowledgeListMFR.padding[3] = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 4, 3), "craftArmourLight", sewing, "needle", 1, 40,
                new Object[]{" W ", "SPS", " S ", 'P', LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 1, 3), 'W',
                        Blocks.WOOL, 'S', ComponentListMFR.THREAD,});
        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 0),
                LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 0), new ItemStack(ComponentListMFR.THREAD, 3),
                Blocks.WOOL);
        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 1),
                LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 1), new ItemStack(ComponentListMFR.THREAD, 3),
                Blocks.WOOL);
        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 2),
                LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 2), new ItemStack(ComponentListMFR.THREAD, 3),
                Blocks.WOOL);
        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 4, 3),
                LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 1, 3), new ItemStack(ComponentListMFR.THREAD, 3),
                Blocks.WOOL);

        KnowledgeListMFR.repairBasicR = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                new ItemStack(BlockListMFR.REPAIR_BASIC), "repair_basic", sewing, "needle", 1, 20,
                new Object[]{"TTT", "FNH", "SLS", 'T', ComponentListMFR.THREAD, 'S', ComponentListMFR.LEATHER_STRIP,
                        'L', Items.LEATHER, 'F', Items.FLINT, 'H', CustomToolListMFR.STANDARD_HAMMER, 'N',
                        ComponentListMFR.NAIL,});
        ItemStack bronzePlate = ComponentListMFR.PLATE.createComm("bronze");
        KnowledgeListMFR.repairAdvancedR = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                new ItemStack(BlockListMFR.REPAIR_ADVANCED), "repair_advanced", sewing, "needle", 2, 50,
                new Object[]{"SCS", "PKH", "CSC", 'K', BlockListMFR.REPAIR_BASIC, 'P', bronzePlate, 'H',
                        CustomToolListMFR.STANDARD_HAMMER, 'C', Items.SLIME_BALL, 'S', Items.STRING,});
        KnowledgeListMFR.repairOrnateR = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                new ItemStack(BlockListMFR.REPAIR_ORNATE), "repair_ornate", sewing, "needle", 3, 100,
                new Object[]{"GDG", "LKL", "GLG", 'K', BlockListMFR.REPAIR_ADVANCED, 'G', Items.GOLD_INGOT, 'L',
                        new ItemStack(Items.DYE, 1, 4), 'D', Items.DIAMOND,});

        Salvage.addSalvage(BlockListMFR.REPAIR_BASIC, new ItemStack(ComponentListMFR.THREAD, 3), ComponentListMFR.NAIL,
                Items.FLINT, Items.LEATHER, new ItemStack(ComponentListMFR.LEATHER_STRIP, 2));
        Salvage.addSalvage(BlockListMFR.REPAIR_ADVANCED, BlockListMFR.REPAIR_BASIC, bronzePlate,
                new ItemStack(Items.SLIME_BALL, 3), new ItemStack(Items.STRING, 3));
        Salvage.addSalvage(BlockListMFR.REPAIR_ORNATE, BlockListMFR.REPAIR_ADVANCED, new ItemStack(Items.GOLD_INGOT, 4),
                Items.DIAMOND, new ItemStack(Items.DYE, 3, 4));

        //KnowledgeListMFR.trilogyRecipe = MineFantasyRebornAPI.addShapelessCarpenterRecipe(artisanry, new ItemStack(ComponentListMFR.artefacts, 1, 3), "smeltMaster", basic, "hands", -1, 1, new Object[]{new ItemStack(ComponentListMFR.artefacts, 1, 0), new ItemStack(ComponentListMFR.artefacts, 1, 1), new ItemStack(ComponentListMFR.artefacts, 1, 2)});
        //TODO: Replace with proper JSON recipe
    }

    public static void assembleWoodBasic() {
        //KnowledgeListMFR.carpenterRecipe = GameRegistry.addShapedRecipe(new ItemStack(BlockListMFR.CARPENTER), new Object[]{"PBP", "P P", 'B', Blocks.CRAFTING_TABLE, 'P', ComponentListMFR.plank});
        //TODO: Replace with proper JSON recipe

        Salvage.addSalvage(BlockListMFR.CARPENTER, ComponentListMFR.PLANK.construct("ScrapWood", 4), Blocks.CRAFTING_TABLE);

        KnowledgeListMFR.nailPlanksR = MineFantasyRebornAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMFR.NAILED_PLANKS), "refined_planks", nailHammer, "hammer", 1, 5,
                new Object[]{"N ", "PP", "PP", 'N', ComponentListMFR.NAIL, 'P',
                        ComponentListMFR.PLANK.construct("oak_wood"),});
        KnowledgeListMFR.nailStairR = MineFantasyRebornAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMFR.NAILED_PLANKS_STAIR), "refined_planks", nailHammer, "hammer", 1, 5,
                new Object[]{"N ", "P ", "PP", 'N', ComponentListMFR.NAIL, 'P',
                        ComponentListMFR.PLANK.construct("oak_wood"),});
        KnowledgeListMFR.tannerRecipe = MineFantasyRebornAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMFR.TANNER), "", nailHammer, "hammer", -1, 10,
                new Object[]{"PPP", "P P", "PPP", 'P', ComponentListMFR.PLANK,});

        KnowledgeListMFR.clayWallR = MineFantasyRebornAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMFR.CLAY_WALL, 4), "clay_wall", nailHammer, "hammer", 1, 2, new Object[]{"NPN",
                        "PCP", "NPN", 'N', ComponentListMFR.NAIL, 'P', ComponentListMFR.PLANK, 'C', Blocks.CLAY});

        KnowledgeListMFR.researchTableRecipe = MineFantasyRebornAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMFR.RESEARCH), "", nailHammer, "hammer", -1, 10,
                new Object[]{"B", "C", 'B', ToolListMFR.RESEARCH_BOOK, 'C', BlockListMFR.CARPENTER,});
        KnowledgeListMFR.bSalvageR = MineFantasyRebornAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMFR.SALVAGE_BASIC), "", nailHammer, "hammer", -1, 10, new Object[]{"SFS", "PWP",
                        'W', Blocks.CRAFTING_TABLE, 'S', Blocks.STONE, 'F', Items.FLINT, 'P', ComponentListMFR.PLANK});

        KnowledgeListMFR.framedGlassR = MineFantasyRebornAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMFR.FRAMED_GLASS), "", nailHammer, "hammer", -1, 10,
                new Object[]{"PGP", 'P', ComponentListMFR.PLANK, 'G', Blocks.GLASS});
        KnowledgeListMFR.windowR = MineFantasyRebornAPI.addCarpenterRecipe(construction, new ItemStack(BlockListMFR.WINDOW), "",
                nailHammer, "hammer", -1, 10,
                new Object[]{" P ", "PGP", " P ", 'P', ComponentListMFR.PLANK, 'G', Blocks.GLASS});

        Salvage.addSalvage(BlockListMFR.FRAMED_GLASS, ComponentListMFR.PLANK.construct("ScrapWood", 2), Blocks.GLASS);
        Salvage.addSalvage(BlockListMFR.WINDOW, ComponentListMFR.PLANK.construct("ScrapWood", 4), Blocks.GLASS);
        Salvage.addSalvage(BlockListMFR.CLAY_WALL, ComponentListMFR.NAIL, ComponentListMFR.PLANK.construct("ScrapWood"),
                Items.CLAY_BALL);
        Salvage.addSalvage(BlockListMFR.TANNER, ComponentListMFR.PLANK.construct("ScrapWood", 8));
        Salvage.addSalvage(BlockListMFR.RESEARCH, BlockListMFR.CARPENTER);
        Salvage.addSalvage(BlockListMFR.SALVAGE_BASIC, Items.FLINT, new ItemStack(Blocks.STONE, 2),
                ComponentListMFR.PLANK.construct("ScrapWood", 2), Blocks.CRAFTING_TABLE);
    }

    private static void addDusts() {
        QuernRecipes.addRecipe(new ItemStack(Items.DYE, 1, 3), new ItemStack(FoodListMFR
                .COCA_POWDER), 0, true);// ItemDye
        QuernRecipes.addRecipe(Items.WHEAT, new ItemStack(FoodListMFR
                .FLOUR), 0, true);
        QuernRecipes.addRecipe(Items.REEDS, new ItemStack(FoodListMFR
                .SUGAR_POT), 0, true);
        QuernRecipes.addRecipe(FoodListMFR
                .BREADROLL, new ItemStack(FoodListMFR
                .BREADCRUMBS), 0, true);

        QuernRecipes.addRecipe(FoodListMFR
                        .GENERIC_MEAT_UNCOOKED, new ItemStack(FoodListMFR
                        .GENERIC_MEAT_MINCE_UNCOOKED),
                0, true);
        QuernRecipes.addRecipe(FoodListMFR
                        .GENERIC_MEAT_STRIP_UNCOOKED,
                new ItemStack(FoodListMFR
                        .GENERIC_MEAT_MINCE_UNCOOKED), 0, true);
        QuernRecipes.addRecipe(FoodListMFR
                        .GENERIC_MEAT_CHUNK_UNCOOKED,
                new ItemStack(FoodListMFR
                        .GENERIC_MEAT_MINCE_UNCOOKED), 0, true);
        QuernRecipes.addRecipe(FoodListMFR
                .GENERIC_MEAT_COOKED, new ItemStack(FoodListMFR
                .GENERIC_MEAT_MINCE_COOKED), 0, true);
        QuernRecipes.addRecipe(FoodListMFR
                        .GENERIC_MEAT_STRIP_COOKED,
                new ItemStack(FoodListMFR
                        .GENERIC_MEAT_MINCE_COOKED), 0, true);
        QuernRecipes.addRecipe(FoodListMFR
                        .GENERIC_MEAT_CHUNK_COOKED,
                new ItemStack(FoodListMFR
                        .GENERIC_MEAT_MINCE_COOKED), 0, true);

        QuernRecipes.addRecipe(Items.COAL, new ItemStack(ComponentListMFR.COAL_DUST), 0, true);
        QuernRecipes.addRecipe(new ItemStack(Items.COAL, 1, 1), new ItemStack(ComponentListMFR.COAL_DUST), 0, true);
        QuernRecipes.addRecipe(ComponentListMFR.KAOLINITE, new ItemStack(ComponentListMFR.KAOLINITE_DUST), 0, true);
        QuernRecipes.addRecipe(Items.FLINT, new ItemStack(ComponentListMFR.SHRAPNEL), 0, true);

        QuernRecipes.addRecipe(ComponentListMFR.FLUX, new ItemStack(ComponentListMFR.FLUX_POT), 0, true);

        KnowledgeListMFR.pieTrayRecipe = MineFantasyRebornAPI.addCarpenterRecipe(null,
                new ItemStack(ComponentListMFR.PIE_TRAY_UNCOOKED), "", basic, "hands", -1, 10,
                new Object[]{"CC", 'C', Items.CLAY_BALL,});

        KnowledgeListMFR.potRecipe = MineFantasyRebornAPI.addCarpenterRecipe(null,
                new ItemStack(ComponentListMFR.CLAY_POT_UNCOOKED, 8), "", basic, "hands", -1, 5,
                new Object[]{"C  C", " CC ", 'C', Items.CLAY_BALL,});
        KnowledgeListMFR.mouldRecipe = MineFantasyRebornAPI.addCarpenterRecipe(null,
                new ItemStack(ComponentListMFR.INGOT_MOULD_UNCOOKED), "crucible", basic, "hands", -1, 10,
                new Object[]{"CCC", " C ", 'C', Items.CLAY_BALL,});
        KnowledgeListMFR.jugRecipe = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(FoodListMFR
                        .JUG_UNCOOKED, 4),
                "", basic, "hands", -1, 8, new Object[]{"C  ", "C C", " C ", 'C', Items.CLAY_BALL,});
        KnowledgeListMFR.blackpowderRec = MineFantasyRebornAPI.addCarpenterRecipe(null,
                new ItemStack(ComponentListMFR.BLACKPOWDER, 2), "blackpowder", basic, "hands", -1, 2,
                new Object[]{"NS", "CC", "PP", 'C', ComponentListMFR.COAL_DUST, 'N', ComponentListMFR.NITRE, 'S',
                        ComponentListMFR.SULFUR, 'P', ComponentListMFR.CLAY_POT,});
        KnowledgeListMFR.crudeBombR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ToolListMFR.BOMB_CRUDE), "blackpowder", primitive, "hands", -1, 5,
                new Object[]{"T", "B", "P",

                        'B', ComponentListMFR.BLACKPOWDER, 'T', ComponentListMFR.THREAD, 'P', Items.PAPER,});
        KnowledgeListMFR.advblackpowderRec = MineFantasyRebornAPI.addCarpenterRecipe(null,
                new ItemStack(ComponentListMFR.BLACKPOWDER_ADVANCED), "advblackpowder", basic, "hands", -1, 10,
                new Object[]{" B ", "RGR", " P ", 'B', ComponentListMFR.BLACKPOWDER, 'G', Items.GLOWSTONE_DUST, 'R',
                        Items.REDSTONE, 'P', ComponentListMFR.CLAY_POT,});
        KnowledgeListMFR.magmaRefinedR = MineFantasyRebornAPI.addCarpenterRecipe(null,
                new ItemStack(ComponentListMFR.MAGMA_CREAM_REFINED), "firebomb", grinding, "pestle", -1, 10,
                new Object[]{"B", "H", "C", "P", 'H', ComponentListMFR.DRAGON_HEART, 'B', Items.BLAZE_POWDER, 'C',
                        Items.MAGMA_CREAM, 'P', ComponentListMFR.CLAY_POT,});
        Salvage.addSalvage(ComponentListMFR.MAGMA_CREAM_REFINED, ComponentListMFR.DRAGON_HEART, Items.BLAZE_POWDER,
                Items.MAGMA_CREAM, ComponentListMFR.CLAY_POT);
    }

    private static void addWoodworks() {
        KnowledgeListMFR.refinedPlankBlockR = MineFantasyRebornAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMFR.REFINED_PLANKS), "refined_planks", nailHammer, "hammer", 1, 10,
                new Object[]{"N ", "PP", "PP", 'N', ComponentListMFR.NAIL, 'P',
                        ComponentListMFR.PLANK.construct("RefinedWood"),});

        KnowledgeListMFR.refinedStairR = MineFantasyRebornAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMFR.REFINED_PLANKS_STAIR), "refined_planks", nailHammer, "hammer", 1, 10,
                new Object[]{"N ", "P ", "PP", 'N', ComponentListMFR.NAIL, 'P',
                        ComponentListMFR.PLANK.construct("RefinedWood"),});
        Salvage.addSalvage(BlockListMFR.NAILED_PLANKS, ComponentListMFR.NAIL,
                ComponentListMFR.PLANK.construct("ScrapWood", 4));
        Salvage.addSalvage(BlockListMFR.REFINED_PLANKS, ComponentListMFR.NAIL,
                ComponentListMFR.PLANK.construct("RefinedWood", 4));
        Salvage.addSalvage(BlockListMFR.NAILED_PLANKS_STAIR, ComponentListMFR.NAIL,
                ComponentListMFR.PLANK.construct("ScrapWood", 3));
        Salvage.addSalvage(BlockListMFR.REFINED_PLANKS_STAIR, ComponentListMFR.NAIL,
                ComponentListMFR.PLANK.construct("RefinedWood", 3));

        KnowledgeListMFR.bellowsRecipe = MineFantasyRebornAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMFR.BELLOWS), "", nailHammer, "hammer", 1, 50,
                new Object[]{"NNN", "PPP", "LL ", "PP ", 'N', ComponentListMFR.NAIL, 'P',
                        ComponentListMFR.PLANK.construct("RefinedWood"), 'L', Items.LEATHER,});
        Salvage.addSalvage(BlockListMFR.BELLOWS, new ItemStack(ComponentListMFR.NAIL, 3),
                ComponentListMFR.PLANK.construct("RefinedWood", 5), new ItemStack(Items.LEATHER, 2));

        KnowledgeListMFR.woodTroughRecipe = MineFantasyRebornAPI.addCarpenterRecipe(construction,
                ((BlockWoodDecor) BlockListMFR.TROUGH_WOOD).construct("ScrapWood"), "", nailHammer, "hammer", -1, 20,
                new Object[]{"P P", "PPP",

                        'P', ComponentListMFR.PLANK,});

        KnowledgeListMFR.strongRackR = MineFantasyRebornAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMFR.TANNER_REFINED), "", nailHammer, "hammer", 1, 80,
                new Object[]{"NNN", "PPP", "P P", "PPP", 'N', ComponentListMFR.NAIL, 'P',
                        ComponentListMFR.PLANK.construct("RefinedWood"),});
        Salvage.addSalvage(BlockListMFR.TANNER_REFINED, ComponentListMFR.PLANK.construct("RefinedWood", 8),
                new ItemStack(ComponentListMFR.NAIL, 3));

        MineFantasyRebornAPI.addCarpenterRecipe(construction, new ItemStack(BlockListMFR.REFINED_PLANKS), "paint_brush",
                sewing, "brush", -1, 3,
                new Object[]{"O", "P", 'O', ComponentListMFR.PLANT_OIL, 'P', BlockListMFR.NAILED_PLANKS,});

        PaintOilRecipe.addRecipe(BlockListMFR.NAILED_PLANKS, BlockListMFR.REFINED_PLANKS);
        PaintOilRecipe.addRecipe(BlockListMFR.NAILED_PLANKS_STAIR, BlockListMFR.REFINED_PLANKS_STAIR);

    }

    private static void addStonemason() {
        KnowledgeListMFR.quernR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(BlockListMFR.QUERN), "",
                stonemason, "hammer", -1, 10, new Object[]{"FSF", "SSS", 'F', Items.FLINT, 'S', Blocks.STONE,});
        KnowledgeListMFR.stoneovenRecipe = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(BlockListMFR.OVEN),
                "", stonemason, "hammer", -1, 10,
                new Object[]{"S", "C", 'C', BlockListMFR.STOVE, 'S', Blocks.STONE,});

        KnowledgeListMFR.bloomeryR = MineFantasyRebornAPI.addCarpenterRecipe(artisanry, new ItemStack(BlockListMFR.BLOOMERY),
                "bloomery", stonemason, "hammer", -1, 10,
                new Object[]{" S ", "S S", "SCS", 'C', Blocks.COAL_BLOCK, 'S', Blocks.STONE,});
        KnowledgeListMFR.crucibleRecipe = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                new ItemStack(BlockListMFR.CRUCIBLE_STONE), "crucible", stonemason, "hammer", -1, 20,
                new Object[]{"SSS", "S S", "SSS", 'S', Blocks.STONE,});
        KnowledgeListMFR.advCrucibleRecipe = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                new ItemStack(BlockListMFR.CRUCIBLE_FIRECLAY), "crucible2", basic, 40,
                new Object[]{"SSS", "SCS", "SSS", 'S', ComponentListMFR.FIRECLAY, 'C', BlockListMFR.CRUCIBLE_STONE});
        Salvage.addSalvage(BlockListMFR.CRUCIBLE_STONE, new ItemStack(Blocks.STONE, 8));
        Salvage.addSalvage(BlockListMFR.CRUCIBLE_FIRECLAY, new ItemStack(ComponentListMFR.FIRECLAY, 8), BlockListMFR.CRUCIBLE_STONE);

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

        KnowledgeListMFR.curdRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMFR
                        .CURDS),
                "", basic, "hands", -1, 10, new Object[]{"T", "S", "M", "P", 'P', ComponentListMFR.CLAY_POT, 'T',
                        FoodListMFR
                                .SALT, 'S', FoodListMFR
                        .SUGAR_POT, 'M', FoodListMFR
                        .JUG_MILK,});

        KnowledgeListMFR.oatsRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMFR
                        .OATS), "",
                chopping, "knife", -1, 20, new Object[]{"M", "W", "S", "B", 'S', Items.WHEAT_SEEDS, 'W', Items.WHEAT,
                        'M', FoodListMFR
                        .JUG_MILK, 'B', Items.BOWL});
        KnowledgeListMFR.doughRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMFR
                        .DOUGH),
                "", basic, "hands", -1, 10,
                new Object[]{"W", "F", 'W', FoodListMFR
                        .JUG_WATER, 'F', FoodListMFR
                        .FLOUR,});
        KnowledgeListMFR.pastryRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMFR
                        .PASTRY),
                "", basic, "hands", -1, 10,
                new Object[]{" S ", "FEF", 'F', FoodListMFR
                        .FLOUR, 'E', Items.EGG, 'S', FoodListMFR
                        .SALT,});
        KnowledgeListMFR.breadRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR
                        .RAW_BREAD), "", basic, "hands", -1, 15,
                new Object[]{"DDD", 'D', FoodListMFR
                        .DOUGH,});
        KnowledgeListMFR.sweetrollRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR
                        .SWEETROLL_RAW), "sweetroll", basic, 5,
                new Object[]{" M ", "FES", "BBB", 'M', FoodListMFR
                        .JUG_MILK, 'S', FoodListMFR
                        .SUGAR_POT, 'B',
                        FoodListMFR
                                .BERRIES, 'E', Items.EGG, 'F', FoodListMFR
                        .FLOUR,});
        KnowledgeListMFR.icingRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMFR
                        .ICING),
                "", mixing, "spoon", -1, 10, new Object[]{"W", "S", "B", 'W', FoodListMFR
                        .JUG_WATER, 'S',
                        FoodListMFR
                                .SUGAR_POT, 'B', ComponentListMFR.CLAY_POT,});
        KnowledgeListMFR.chocoRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR
                        .CHOCOLATE), "icing", mixing, "spoon", -1, 10,
                new Object[]{" M ", "SCS", " B ", 'C', FoodListMFR
                        .COCA_POWDER, 'M', FoodListMFR
                        .JUG_MILK, 'S',
                        FoodListMFR
                                .SUGAR_POT, 'B', ComponentListMFR.CLAY_POT,});
        KnowledgeListMFR.custardRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR
                        .CUSTARD), "icing", mixing, "spoon", -1, 10,
                new Object[]{" M ", "SES", " B ", 'E', Items.EGG, 'M', FoodListMFR
                        .JUG_MILK, 'S', FoodListMFR
                        .SUGAR_POT,
                        'B', ComponentListMFR.CLAY_POT,});
        KnowledgeListMFR.iceSR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMFR
                        .SWEETROLL),
                "sweetroll", basic, "knife", -1, 15,
                new Object[]{"I", "R", 'I', FoodListMFR
                        .ICING, 'R', FoodListMFR
                        .SWEETROLL_UNICED,});
        KnowledgeListMFR.eclairDoughR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR
                        .ECLAIR_RAW), "eclair", basic, 8,
                new Object[]{"SSS", "PPP", 'P', FoodListMFR
                        .PASTRY, 'S', FoodListMFR
                        .SUGAR_POT,});
        KnowledgeListMFR.eclairIceR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR
                        .ECLAIR_EMPTY), "eclair", basic, "knife", 2, 20,
                new Object[]{"C", "E", 'C', FoodListMFR
                        .CHOCOLATE, 'E', FoodListMFR
                        .ECLAIR_UNICED,});
        KnowledgeListMFR.eclairFillR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMFR
                        .ECLAIR),
                "eclair", basic, "knife", 2, 20,
                new Object[]{"C", "E", 'C', FoodListMFR
                        .CUSTARD, 'E', FoodListMFR
                        .ECLAIR_EMPTY,});
        for (ItemStack food : OreDictionary.getOres("rawMeat")) {
            int size = getSize(food);
            KnowledgeListMFR.meatRecipes.add(MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                    new ItemStack(FoodListMFR
                            .GENERIC_MEAT_UNCOOKED, size), "", chopping, "knife", -1, 15,
                    new Object[]{"M", 'M', food,}));
        }
        for (ItemStack food : OreDictionary.getOres("cookedMeat")) {
            int size = 1;
            KnowledgeListMFR.meatRecipes.add(
                    MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMFR
                                    .GENERIC_MEAT_COOKED, size),
                            "", chopping, "knife", -1, 15, new Object[]{"M", 'M', food,}));
        }
        KnowledgeListMFR.meatStripR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.GENERIC_MEAT_STRIP_UNCOOKED), "", chopping, "knife", -1, 5,
                new Object[]{"M", 'M', FoodListMFR.GENERIC_MEAT_UNCOOKED,});
        MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMFR.GENERIC_MEAT_STRIP_COOKED), "",
                chopping, "knife", -1, 5, new Object[]{"M", 'M', FoodListMFR.GENERIC_MEAT_COOKED,});
        KnowledgeListMFR.meatHunkR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.GENERIC_MEAT_CHUNK_UNCOOKED), "", chopping, "knife", -1, 5,
                new Object[]{"M", 'M', FoodListMFR.GENERIC_MEAT_STRIP_UNCOOKED,});
        MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMFR.GENERIC_MEAT_CHUNK_COOKED), "",
                chopping, "knife", -1, 5, new Object[]{"M", 'M', FoodListMFR.GENERIC_MEAT_STRIP_COOKED,});
        KnowledgeListMFR.gutsRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMFR.GUTS), "",
                chopping, "knife", 1, 8, new Object[]{"MMMM", 'M', Items.ROTTEN_FLESH,});

        KnowledgeListMFR.stewRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMFR.STEW), "",
                chopping, "knife", -1, 15,
                new Object[]{"M", "B", 'M', FoodListMFR.GENERIC_MEAT_CHUNK_COOKED, 'B', Items.BOWL});
        KnowledgeListMFR.jerkyRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.JERKY, 1), "jerky", chopping, "knife", 2, 20,
                new Object[]{"S", "M", 'S', FoodListMFR.SALT, 'M', FoodListMFR.GENERIC_MEAT_STRIP_COOKED,});
        KnowledgeListMFR.saussageR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.SAUSAGE_RAW, 4), "saussage", chopping, "knife", 2, 30,
                new Object[]{" G ", "MMM", "BES", 'G', FoodListMFR.GUTS, 'E', Items.EGG, 'S', FoodListMFR.SALT, 'B',
                        FoodListMFR.BREADCRUMBS, 'M', FoodListMFR.GENERIC_MEAT_MINCE_UNCOOKED,});
        KnowledgeListMFR.meatPieRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.PIE_MEAT_UNCOOKED), "meatpie", chopping, "knife", 2, 150,
                new Object[]{" P ", "MMM", " P ", " T ", 'P', FoodListMFR.PASTRY, 'M',
                        FoodListMFR.GENERIC_MEAT_MINCE_COOKED, 'T', FoodListMFR.PIE_TRAY,});
        KnowledgeListMFR.breadSliceR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.BREAD_SLICE, 12), "", sewing, "knife", -1, 10,
                new Object[]{"B", 'B', Items.BREAD,});
        KnowledgeListMFR.sandwitchRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.SANDWITCH_MEAT), "sandwitch", chopping, "hands", -1, 4,
                new Object[]{"B", "C", "M", "B", 'C', FoodListMFR.CHEESE_SLICE, 'M', FoodListMFR.GENERIC_MEAT_COOKED,
                        'B', FoodListMFR.BREAD_SLICE});
        KnowledgeListMFR.sandwitchBigRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.SANDWITCH_BIG), "sandwitchBig", chopping, "knife", 1, 10,
                new Object[]{"CSC", "MBM", 'S', FoodListMFR.SALT, 'C', FoodListMFR.CHEESE_SLICE, 'M',
                        FoodListMFR.GENERIC_MEAT_COOKED, 'B', Items.BREAD});
        KnowledgeListMFR.shepardRecipe = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.PIE_SHEPARD_UNCOOKED), "shepardpie", chopping, "knife", 3, 200,
                new Object[]{"PFP", "MMM", "CFC", " T ", 'C', Items.CARROT, 'P', Items.POTATO, 'F', FoodListMFR.PASTRY,
                        'M', FoodListMFR.GENERIC_MEAT_MINCE_COOKED, 'T', FoodListMFR.PIE_TRAY,});

        KnowledgeListMFR.appleR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.PIE_APPLE_UNCOOKED), "applepie", chopping, "knife", 2, 120,
                new Object[]{"SPS", "MMM", "SPS", " T ", 'S', FoodListMFR.SUGAR_POT, 'P', FoodListMFR.PASTRY, 'M',
                        Items.APPLE, 'T', FoodListMFR.PIE_TRAY,});
        KnowledgeListMFR.pumpPieR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.PIE_PUMPKIN_UNCOOKED), "bread", chopping, "knife", 1, 50,
                new Object[]{"SMS", "SPS", " T ", 'S', FoodListMFR.SUGAR_POT, 'P', FoodListMFR.PASTRY, 'M',
                        Blocks.PUMPKIN, 'T', FoodListMFR.PIE_TRAY,});
        KnowledgeListMFR.berryR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.PIE_BERRY_UNCOOKED), "berrypie", chopping, "knife", 2, 100,
                new Object[]{"SPS", "MMM", "SPS", " T ", 'S', FoodListMFR.SUGAR_POT, 'P', FoodListMFR.PASTRY, 'M',
                        FoodListMFR.BERRIES, 'T', FoodListMFR.PIE_TRAY,});

        KnowledgeListMFR.simpCakeR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.CAKE_SIMPLE_RAW), "bread", mixing, "spoon", -1, 15,
                new Object[]{"MMM", "SES", "FFF", " T ", 'F', FoodListMFR.FLOUR, 'E', Items.EGG, 'M',
                        FoodListMFR.JUG_MILK, 'S', FoodListMFR.SUGAR_POT, 'T', FoodListMFR.CAKE_TIN,});

        KnowledgeListMFR.cakeR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMFR.CAKE_RAW),
                "cake", mixing, "spoon", -1, 20, new Object[]{"SMS", "SES", "FFF", " T ", 'F', FoodListMFR.FLOUR, 'E',
                        Items.EGG, 'M', FoodListMFR.JUG_MILK, 'S', FoodListMFR.SUGAR_POT, 'T', FoodListMFR.CAKE_TIN,});
        KnowledgeListMFR.carrotCakeR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.CAKE_CARROT_RAW), "carrotcake", mixing, "spoon", -1, 25,
                new Object[]{"SMS", "SES", "CCC", "FTF", 'C', Items.CARROT, 'F', FoodListMFR.FLOUR, 'E', Items.EGG,
                        'M', FoodListMFR.JUG_MILK, 'S', FoodListMFR.SUGAR_POT, 'T', FoodListMFR.CAKE_TIN,});
        KnowledgeListMFR.chocoCakeR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.CAKE_CHOC_RAW), "chococake", mixing, "spoon", -1, 25,
                new Object[]{"SMS", "SES", "CCC", "FTF", 'C', FoodListMFR.CHOCOLATE, 'F', FoodListMFR.FLOUR, 'E',
                        Items.EGG, 'M', FoodListMFR.JUG_MILK, 'S', FoodListMFR.SUGAR_POT, 'T', FoodListMFR.CAKE_TIN,});
        KnowledgeListMFR.bfCakeR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(FoodListMFR.CAKE_BF_RAW),
                "bfcake", mixing, "spoon", -1, 30,
                new Object[]{"SMMS", "SEES", "CBBC", "FTFF", 'B', FoodListMFR.BERRIES_JUICY, 'C', FoodListMFR.CHOCOLATE,
                        'F', FoodListMFR.FLOUR, 'E', Items.EGG, 'M', FoodListMFR.JUG_MILK, 'S', FoodListMFR.SUGAR_POT, 'T',
                        FoodListMFR.CAKE_TIN,});
        KnowledgeListMFR.simpCakeOut = MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(Items.CAKE),
                "bread", basic, "knife", -1, 10,
                new Object[]{"I", "R", 'I', FoodListMFR.ICING, 'R', FoodListMFR.CAKE_SIMPLE_UNICED,});

        KnowledgeListMFR.cakeI = MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(BlockListMFR.CAKE_VANILLA),
                "cake", basic, "knife", -1, 60,
                new Object[]{"III", " R ", 'I', FoodListMFR.ICING, 'R', FoodListMFR.CAKE_UNICED,});
        KnowledgeListMFR.carrotCakeI = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(BlockListMFR.CAKE_CARROT), "carrotcake", basic, "knife", -1, 60,
                new Object[]{"III", " R ", 'I', FoodListMFR.ICING, 'R', FoodListMFR.CAKE_CARROT_UNICED,});
        KnowledgeListMFR.chocoCakeI = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(BlockListMFR.CAKE_CHOCOLATE), "chococake", basic, "knife", -1, 60, new Object[]{"ICI",
                        " R ", 'C', FoodListMFR.CHOCOLATE, 'I', FoodListMFR.ICING, 'R', FoodListMFR.CAKE_CHOC_UNICED,});
        KnowledgeListMFR.bfCakeI = MineFantasyRebornAPI.addCarpenterRecipe(provisioning, new ItemStack(BlockListMFR.CAKE_BF),
                "bfcake", basic, "knife", -1, 100, new Object[]{"BBB", "III", "CRC", 'C', FoodListMFR.CHOCOLATE, 'B',
                        FoodListMFR.BERRIES, 'I', FoodListMFR.ICING, 'R', FoodListMFR.CAKE_BF_UNICED,});

        KnowledgeListMFR.cheeserollR = MineFantasyRebornAPI.addCarpenterRecipe(provisioning,
                new ItemStack(FoodListMFR.CHEESE_ROLL), "cheeseroll", chopping, "knife", 1, 30,
                new Object[]{"C", "R", 'C', FoodListMFR.CHEESE_SLICE, 'R', FoodListMFR.BREADROLL,});
    }

    private static int getSize(ItemStack food) {
        if (food != null && food.getItem() instanceof ItemFood) {
            int feed = ((ItemFood) food.getItem()).getHealAmount(food);
            return Math.max(1, feed - 1);
        }
        return 1;
    }

    private static void addMisc() {
        // Fletching
        KnowledgeListMFR.fletchingR = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                new ItemStack(ComponentListMFR.FLETCHING, 16), "arrows", chopping, 4, new Object[]{"T", "F",

                        'F', Items.FEATHER, 'T', ComponentListMFR.PLANK,});
        KnowledgeListMFR.fletchingR2 = MineFantasyRebornAPI.addCarpenterRecipe(artisanry,
                new ItemStack(ComponentListMFR.FLETCHING, 4), "arrows", chopping, 4, new Object[]{" T ", "PPP",

                        'P', Items.PAPER, 'T', ComponentListMFR.PLANK,});

        // BOMBS
        KnowledgeListMFR.bombCaseCeramicR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.BOMB_CASING_UNCOOKED, 2), "bombCeramic", basic, 2,
                new Object[]{" C ", "C C", " C ", 'C', Items.CLAY_BALL,});
        KnowledgeListMFR.mineCaseCeramicR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.MINE_CASING_UNCOOKED), "mineCeramic", basic, 2,
                new Object[]{" P ", "C C", " C ",

                        'P', Blocks.STONE_PRESSURE_PLATE, 'C', Items.CLAY_BALL,});
        KnowledgeListMFR.bombCaseCrystalR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.BOMB_CASING_CRYSTAL), "bombCrystal", basic, 10,
                new Object[]{" D ", "R R", " B ", 'B', Items.GLASS_BOTTLE, 'D', ComponentListMFR.DIAMOND_SHARDS, 'R',
                        Items.REDSTONE});
        KnowledgeListMFR.mineCaseCrystalR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.MINE_CASING_CRYSTAL), "mineCrystal", basic, 10,
                new Object[]{" P ", "RDR", " B ", 'P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 'B', Items.GLASS_BOTTLE,
                        'D', ComponentListMFR.DIAMOND_SHARDS, 'R', Items.REDSTONE});
        Salvage.addSalvage(ComponentListMFR.BOMB_CASING_UNCOOKED, new ItemStack(Items.CLAY_BALL, 2));
        Salvage.addSalvage(ComponentListMFR.MINE_CASING_UNCOOKED, new ItemStack(Items.CLAY_BALL, 3),
                Blocks.STONE_PRESSURE_PLATE);

        Salvage.addSalvage(ComponentListMFR.BOMB_CASING_CRYSTAL, Items.GLASS_BOTTLE, ComponentListMFR.DIAMOND_SHARDS,
                new ItemStack(Items.REDSTONE, 2));
        Salvage.addSalvage(ComponentListMFR.MINE_CASING_CRYSTAL, Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE,
                Items.GLASS_BOTTLE, ComponentListMFR.DIAMOND_SHARDS, new ItemStack(Items.REDSTONE, 2));

        KnowledgeListMFR.bombFuseR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.BOMB_FUSE, 8), "bombs", basic, 4, new Object[]{"R", "C", "S", 'S',
                        ComponentListMFR.THREAD, 'C', ComponentListMFR.COAL_DUST, 'R', Items.REDSTONE,});
        KnowledgeListMFR.longFuseR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.BOMB_FUSE_LONG), "bombs", basic, 1,
                new Object[]{"F", "R", "F", 'F', ComponentListMFR.BOMB_FUSE, 'R', Items.REDSTONE,});
        Salvage.addSalvage(ComponentListMFR.BOMB_FUSE_LONG, new ItemStack(ComponentListMFR.BOMB_FUSE, 2), Items.REDSTONE);

        KnowledgeListMFR.thatchR = MineFantasyRebornAPI.addCarpenterRecipe(construction, new ItemStack(BlockListMFR.THATCH), "",
                SoundEvents.BLOCK_GRASS_HIT, "hands", -1, 1, new Object[]{"HH", "HH", 'H', new ItemStack(Blocks.TALLGRASS, 1, 1)});
        KnowledgeListMFR.thatchStairR = MineFantasyRebornAPI.addCarpenterRecipe(construction,
                new ItemStack(BlockListMFR.THATCH_STAIR), "", SoundEvents.BLOCK_GRASS_HIT, "hands", -1, 1,
                new Object[]{"H ", "HH", 'H', new ItemStack(Blocks.TALLGRASS, 1, 1)});
        Salvage.addSalvage(BlockListMFR.THATCH_STAIR, new ItemStack(Blocks.TALLGRASS, 3, 1));
        Salvage.addSalvage(BlockListMFR.THATCH, new ItemStack(Blocks.TALLGRASS, 4, 1));

        KnowledgeListMFR.apronRecipe = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(LeatherArmourListMFR.LEATHER_APRON),
                "", sewing, "hands", -1, 1, new Object[]{"LCL", " L ", 'L', Items.LEATHER, 'C', Items.COAL,});
        Salvage.addSalvage(LeatherArmourListMFR.LEATHER_APRON, new ItemStack(Items.LEATHER, 3), Items.COAL);

        KnowledgeListMFR.hideHelmR = MineFantasyRebornAPI.addCarpenterRecipe(null,
                LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 0, 0), "", sewing, "hands", -1, 1,
                new Object[]{"H", "C", "H", 'H', ComponentListMFR.HIDE_SMALL, 'C', Blocks.WOOL,});
        KnowledgeListMFR.hideChestR = MineFantasyRebornAPI.addCarpenterRecipe(null,
                LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 0, 1), "", sewing, "hands", -1, 1,
                new Object[]{"H", "C", 'H', ComponentListMFR.HIDE_LARGE, 'C', Blocks.WOOL,});
        KnowledgeListMFR.hideLegsR = MineFantasyRebornAPI.addCarpenterRecipe(null,
                LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 0, 2), "", sewing, "hands", -1, 1,
                new Object[]{"H", "C", 'H', ComponentListMFR.HIDE_MEDIUM, 'C', Blocks.WOOL,});
        KnowledgeListMFR.hideBootsR = MineFantasyRebornAPI.addCarpenterRecipe(null,
                LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 0, 3), "", sewing, "hands", -1, 1,
                new Object[]{"H", "C", 'H', ComponentListMFR.HIDE_SMALL, 'C', Blocks.WOOL,});

        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 0, 0),
                new ItemStack(ComponentListMFR.HIDE_SMALL, 2), Blocks.WOOL);
        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 0, 1), ComponentListMFR.HIDE_LARGE, Blocks.WOOL);
        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 0, 2), ComponentListMFR.HIDE_MEDIUM,
                Blocks.WOOL);
        Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 0, 3), ComponentListMFR.HIDE_SMALL, Blocks.WOOL);
    }

    public static void addCrossbows() {
        // CROSSBOWS
        KnowledgeListMFR.crossHandleWoodR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.CROSSBOW_HANDLE_WOOD), "crossShafts", nailHammer, "hammer", 2, 150,
                new Object[]{"N N", "PP ", " P ", 'P', ComponentListMFR.PLANK.construct("RefinedWood"), 'N',
                        ComponentListMFR.NAIL});
        KnowledgeListMFR.crossStockWoodR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.CROSSBOW_STOCK_WOOD), "crossShafts", nailHammer, "hammer", 2, 300,
                new Object[]{"NN N", "PPPP", " PPP", 'P', ComponentListMFR.PLANK.construct("RefinedWood"), 'N',
                        ComponentListMFR.NAIL});
        KnowledgeListMFR.crossStockIronR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.CROSSBOW_STOCK_IRON), "crossShaftAdvanced", spanner, "spanner", 2, 300,
                new Object[]{" BBB", "BOGG", "SWSS", "    ", 'O', Blocks.OBSIDIAN, 'G',
                        ComponentListMFR.TUNGSTEN_GEARS, 'W', ComponentListMFR.CROSSBOW_STOCK_WOOD, 'S',
                        ComponentListMFR.IRON_STRUT, 'B', ComponentListMFR.BOLT,});

        KnowledgeListMFR.crossHeadLightR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.CROSS_ARMS_LIGHT), "crossHeads", nailHammer, "hammer", 2, 200,
                new Object[]{"PPP", "NSN", " P ", 'P', ComponentListMFR.PLANK.construct("RefinedWood"), 'N',
                        ComponentListMFR.NAIL, 'S', Items.STRING,});
        KnowledgeListMFR.crossHeadMediumR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.CROSS_ARMS_BASIC), "crossHeads", nailHammer, "hammer", 2, 250,
                new Object[]{"NNN", "PAP", 'N', ComponentListMFR.NAIL, 'P',
                        ComponentListMFR.PLANK.construct("RefinedWood"), 'A', ComponentListMFR.CROSS_ARMS_LIGHT,});
        KnowledgeListMFR.crossHeadHeavyR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.CROSS_ARMS_HEAVY), "crossHeads", nailHammer, "hammer", 2, 350,
                new Object[]{"NNN", "PAP", 'N', ComponentListMFR.NAIL, 'P',
                        ComponentListMFR.PLANK.construct("RefinedWood"), 'A', ComponentListMFR.CROSS_ARMS_BASIC,});
        KnowledgeListMFR.crossHeadAdvancedR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.CROSS_ARMS_ADVANCED), "crossHeadAdvanced", nailHammer, "hammer", 2, 350,
                new Object[]{"NRN", "RGR", " A ", 'G', ComponentListMFR.TUNGSTEN_GEARS, 'N', ComponentListMFR.NAIL, 'R',
                        ComponentListMFR.STEEL_TUBE, 'A', ComponentListMFR.CROSS_ARMS_BASIC,});

        KnowledgeListMFR.crossAmmoR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.CROSS_AMMO), "crossAmmo", nailHammer, "hammer", 2, 200,
                new Object[]{"NNN", "P P", "PGP", "PPP", 'G', ComponentListMFR.TUNGSTEN_GEARS, 'P',
                        ComponentListMFR.PLANK.construct("RefinedWood"), 'N', ComponentListMFR.NAIL,});
        KnowledgeListMFR.crossScopeR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.CROSS_SCOPE), "crossScope", spanner, "spanner", 2, 150,
                new Object[]{"BSB", "GP ", 'G', ComponentListMFR.TUNGSTEN_GEARS, 'S', ToolListMFR.SPYGLASS, 'P',
                        ComponentListMFR.PLANK.construct("RefinedWood"), 'B', ComponentListMFR.BOLT,});
        Salvage.addSalvage(ComponentListMFR.CROSS_ARMS_LIGHT, new ItemStack(ComponentListMFR.NAIL, 2), Items.STRING,
                ComponentListMFR.PLANK.construct("RefinedWood", 4));
        Salvage.addSalvage(ComponentListMFR.CROSS_ARMS_BASIC, new ItemStack(ComponentListMFR.NAIL, 3),
                ComponentListMFR.CROSS_ARMS_LIGHT, ComponentListMFR.PLANK.construct("RefinedWood", 2));
        Salvage.addSalvage(ComponentListMFR.CROSS_ARMS_HEAVY, new ItemStack(ComponentListMFR.NAIL, 3),
                ComponentListMFR.CROSS_ARMS_BASIC, ComponentListMFR.PLANK.construct("RefinedWood", 2));
        Salvage.addSalvage(ComponentListMFR.CROSS_ARMS_ADVANCED, ComponentListMFR.TUNGSTEN_GEARS,
                new ItemStack(ComponentListMFR.NAIL, 2), ComponentListMFR.CROSS_ARMS_BASIC,
                new ItemStack(ComponentListMFR.STEEL_TUBE, 3));

        Salvage.addSalvage(ComponentListMFR.CROSS_SCOPE, ComponentListMFR.TUNGSTEN_GEARS, ToolListMFR.SPYGLASS,
                new ItemStack(ComponentListMFR.BOLT, 2), ComponentListMFR.PLANK.construct("RefinedWood"));
        Salvage.addSalvage(ComponentListMFR.CROSS_AMMO, ComponentListMFR.TUNGSTEN_GEARS,
                new ItemStack(ComponentListMFR.NAIL, 3), ComponentListMFR.PLANK.construct("RefinedWood", 7));
        Salvage.addSalvage(ComponentListMFR.CROSSBOW_HANDLE_WOOD, new ItemStack(ComponentListMFR.NAIL, 2),
                ComponentListMFR.PLANK.construct("RefinedWood", 3));
        Salvage.addSalvage(ComponentListMFR.CROSSBOW_STOCK_WOOD, new ItemStack(ComponentListMFR.NAIL, 3),
                ComponentListMFR.PLANK.construct("RefinedWood", 7));
        Salvage.addSalvage(ComponentListMFR.CROSSBOW_STOCK_IRON, new ItemStack(ComponentListMFR.TUNGSTEN_GEARS, 2),
                Blocks.OBSIDIAN, new ItemStack(ComponentListMFR.BOLT, 4), new ItemStack(ComponentListMFR.IRON_STRUT, 3),
                ComponentListMFR.CROSSBOW_STOCK_WOOD);
    }

    private static void addEngineering() {
        addCrossbows();
        KnowledgeListMFR.bombBenchCraft = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(BlockListMFR.BOMB_BENCH), "bombs", spanner, "spanner", 0, 150,
                new Object[]{"BFB", "BCB", 'B', ComponentListMFR.BOLT, 'F', ComponentListMFR.IRON_FRAME, 'C', BlockListMFR.CARPENTER,});
        KnowledgeListMFR.bombPressCraft = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(BlockListMFR.BOMB_PRESS), "bpress", spanner, "spanner", 3, 200,
                new Object[]{"BFB", "GGL", "SPS", 'S', ComponentListMFR.IRON_STRUT, 'B', ComponentListMFR.BOLT, 'F',
                        ComponentListMFR.IRON_FRAME, 'L', Blocks.LEVER, 'P',new ItemStack(CustomToolListMFR.STANDARD_SPANNER), 'G', ComponentListMFR.BRONZE_GEARS,});

        KnowledgeListMFR.crossBenchCraft = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(BlockListMFR.CROSSBOW_BENCH), "crossbows", spanner, "spanner", 0, 200,
                new Object[]{" F ", "PSP", "NCN", 'F', ComponentListMFR.IRON_FRAME, 'P', ComponentListMFR.PLANK, 'N',
                        ComponentListMFR.BOLT, 'S', Items.STRING, 'C', BlockListMFR.CARPENTER,});

        KnowledgeListMFR.engTannerR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(BlockListMFR.TANNER_METAL), "engTanner", spanner, "spanner", 3, 300,
                new Object[]{"BLB", "SPS", "GGG", "SFS", 'S', ComponentListMFR.IRON_STRUT, 'B', ComponentListMFR.BOLT,
                        'F', ComponentListMFR.IRON_FRAME, 'L', Blocks.LEVER, 'P',
                        new ItemStack(CustomToolListMFR.STANDARD_KNIFE, 1, 0), 'G', ComponentListMFR.BRONZE_GEARS,});
        ItemStack blackPlate = ComponentListMFR.PLATE.createComm("blackSteel");
        KnowledgeListMFR.advancedForgeR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(BlockListMFR.FORGE_METAL), "advforge", spanner, "spanner", 4, 400,
                new Object[]{" T  ", "FRRF", "PPPP", "BBBB", 'B', ComponentListMFR.BOLT, 'F',
                        ComponentListMFR.IRON_FRAME, 'T', ToolListMFR.ENGIN_ANVIL_TOOLS, 'P', blackPlate, 'R',
                        Blocks.REDSTONE_BLOCK,});
        ItemStack steelPlate = ComponentListMFR.PLATE.createComm("steel");
        KnowledgeListMFR.autoCrucibleR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(BlockListMFR.CRUCIBLE_AUTO), "advcrucible", spanner, "spanner", 4, 200,
                new Object[]{" T ", "PCP", "PGP", "BBB", 'B', ComponentListMFR.BOLT, 'C', BlockListMFR.CRUCIBLE_FIRECLAY, 'G',
                        ComponentListMFR.TUNGSTEN_GEARS, 'T', ToolListMFR.ENGIN_ANVIL_TOOLS, 'P', steelPlate});
        KnowledgeListMFR.spyglassR = MineFantasyRebornAPI.addCarpenterRecipe(engineering, new ItemStack(ToolListMFR.SPYGLASS),
                "spyglass", spanner, "spanner", 1, 300,
                new Object[]{" T ", "BCB", "GPG", 'C', ComponentListMFR.BRONZE_GEARS, 'G', Blocks.GLASS, 'B',
                        ComponentListMFR.BOLT, 'T', ToolListMFR.ENGIN_ANVIL_TOOLS, 'P', ComponentListMFR.STEEL_TUBE,});

        KnowledgeListMFR.syringeR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ToolListMFR.SYRINGE_EMPTY), "syringe", spanner, "spanner", 1, 200,
                new Object[]{"E", "T", "B", "N", 'E', ToolListMFR.ENGIN_ANVIL_TOOLS, 'T', ComponentListMFR.STEEL_TUBE,
                        'B', Items.GLASS_BOTTLE, 'N', new ItemStack(CustomToolListMFR.STANDARD_NEEDLE),});

        KnowledgeListMFR.parachuteR = MineFantasyRebornAPI.addCarpenterRecipe(engineering, new ItemStack(ToolListMFR.PARACHUTE),
                "parachute", sewing, "needle", 1, 350,
                new Object[]{"TTT", "CCC", "BEB", "BLB", 'E', ToolListMFR.ENGIN_ANVIL_TOOLS, 'T',
                        ComponentListMFR.THREAD, 'B', ComponentListMFR.LEATHER_STRIP, 'L', Items.LEATHER, 'C',
                        Blocks.WOOL,});
        KnowledgeListMFR.cogShaftR = MineFantasyRebornAPI.addCarpenterRecipe(engineering,
                new ItemStack(ComponentListMFR.COGWORK_SHAFT), "cogArmour", spanner, "spanner", 4, 150,
                new Object[]{"BPB", "SGS", "BFB",

                        'P', Blocks.PISTON, 'G', ComponentListMFR.TUNGSTEN_GEARS, 'B', ComponentListMFR.BOLT, 'F',
                        ComponentListMFR.IRON_FRAME, 'S', ComponentListMFR.IRON_STRUT,});
        Salvage.addSalvage(ComponentListMFR.COGWORK_SHAFT, new ItemStack(ComponentListMFR.IRON_STRUT, 2),
                new ItemStack(ComponentListMFR.BOLT, 4), ComponentListMFR.IRON_FRAME, Blocks.PISTON,
                ComponentListMFR.TUNGSTEN_GEARS);

        Salvage.addSalvage(BlockListMFR.CRUCIBLE_AUTO, new ItemStack(ComponentListMFR.BOLT, 3),
                ComponentListMFR.TUNGSTEN_GEARS, BlockListMFR.CRUCIBLE_FIRECLAY, steelPlate, steelPlate, steelPlate,
                steelPlate);
        Salvage.addSalvage(BlockListMFR.BOMB_BENCH, new ItemStack(ComponentListMFR.BOLT, 4), ComponentListMFR.IRON_FRAME,
                BlockListMFR.CARPENTER);
        Salvage.addSalvage(BlockListMFR.CROSSBOW_BENCH, new ItemStack(ComponentListMFR.NAIL, 2),
                ComponentListMFR.PLANK.construct("ScrapWood", 2), Items.STRING, BlockListMFR.CARPENTER);
        Salvage.addSalvage(BlockListMFR.BOMB_PRESS, new ItemStack(ComponentListMFR.IRON_STRUT, 2),
                new ItemStack(ComponentListMFR.BOLT, 2), new ItemStack(ComponentListMFR.BRONZE_GEARS, 2), Blocks.LEVER,
                ComponentListMFR.IRON_FRAME);
        Salvage.addSalvage(BlockListMFR.TANNER_METAL, new ItemStack(ComponentListMFR.IRON_STRUT, 4),
                new ItemStack(ComponentListMFR.BOLT, 2), new ItemStack(ComponentListMFR.BRONZE_GEARS, 3),
                CustomToolListMFR.STANDARD_NEEDLE, Blocks.LEVER, ComponentListMFR.IRON_FRAME);
        Salvage.addSalvage(BlockListMFR.FORGE_METAL, new ItemStack(ComponentListMFR.BOLT, 4), blackPlate, blackPlate,
                blackPlate, blackPlate, new ItemStack(ComponentListMFR.IRON_FRAME, 2),
                new ItemStack(Blocks.REDSTONE_BLOCK, 2));
        Salvage.addSalvage(ToolListMFR.SPYGLASS, new ItemStack(ComponentListMFR.BOLT, 2), new ItemStack(Blocks.GLASS, 2),
                ComponentListMFR.STEEL_TUBE, ComponentListMFR.BRONZE_GEARS);
        Salvage.addSalvage(ToolListMFR.SYRINGE_EMPTY, Items.GLASS_BOTTLE, CustomToolListMFR.STANDARD_NEEDLE,
                ComponentListMFR.STEEL_TUBE);
        Salvage.addSalvage(ToolListMFR.PARACHUTE, new ItemStack(ComponentListMFR.THREAD, 3),
                new ItemStack(Blocks.WOOL, 3), new ItemStack(ComponentListMFR.LEATHER_STRIP, 4), Items.LEATHER);
    }

    private static void addNonPrimitiveStone() {
        KnowledgeListMFR.stoneKnifeR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.STONE_KNIFE), "",
                primitive, "hands", -1, 4,
                new Object[]{"R", "R", "S", 'R', Blocks.COBBLESTONE, 'S', ComponentListMFR.PLANK,});
        KnowledgeListMFR.stoneHammerR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.STONE_HAMMER),
                "", primitive, "hands", -1, 4,
                new Object[]{"R", "S", 'R', Blocks.COBBLESTONE, 'S', ComponentListMFR.PLANK,});

        KnowledgeListMFR.stoneTongsR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.STONE_TONGS), "",
                primitive, "hands", -1, 4,
                new Object[]{"R ", "SR", 'R', Blocks.COBBLESTONE, 'S', ComponentListMFR.PLANK,});
        KnowledgeListMFR.boneNeedleR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.BONE_NEEDLE), "",
                primitive, "hands", -1, 4, new Object[]{"B", 'B', Items.BONE,});

        Salvage.addSalvage(ToolListMFR.STONE_KNIFE, new ItemStack(Blocks.COBBLESTONE, 2),
                ComponentListMFR.PLANK.construct("ScrapWood"));
        Salvage.addSalvage(ToolListMFR.STONE_HAMMER, Blocks.COBBLESTONE, ComponentListMFR.PLANK.construct("ScrapWood"));
        Salvage.addSalvage(ToolListMFR.STONE_TONGS, new ItemStack(Blocks.COBBLESTONE, 2),
                ComponentListMFR.PLANK.construct("ScrapWood"));
        Salvage.addSalvage(ToolListMFR.BONE_NEEDLE, Items.BONE);
    }

    private static void addPrimitive() {
        KnowledgeListMFR.dirtRockR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ComponentListMFR.SHARP_ROCK),
                "", grinding, "hands", -1, 1, new Object[]{"D", 'D', Blocks.DIRT,});

        KnowledgeListMFR.stonePickR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.STONE_PICK), "",
                primitive, "hands", -1, 5, new Object[]{"RVR", " S ", " S ", 'R', ComponentListMFR.SHARP_ROCK, 'V',
                        ComponentListMFR.VINE, 'S', Items.STICK});
        KnowledgeListMFR.stoneAxeR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.STONE_AXE), "",
                primitive, "hands", -1, 5, new Object[]{"RV", "RS", " S", 'R', ComponentListMFR.SHARP_ROCK, 'V',
                        ComponentListMFR.VINE, 'S', Items.STICK});
        KnowledgeListMFR.stoneSpadeR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.STONE_SPADE), "",
                primitive, "hands", -1, 5, new Object[]{"VR", " S", " S", 'R', ComponentListMFR.SHARP_ROCK, 'V',
                        ComponentListMFR.VINE, 'S', Items.STICK});
        KnowledgeListMFR.stoneHoeR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.STONE_HOE), "",
                primitive, "hands", -1, 5, new Object[]{"RV", " S", " S", 'R', ComponentListMFR.SHARP_ROCK, 'V',
                        ComponentListMFR.VINE, 'S', Items.STICK});
        KnowledgeListMFR.stoneSwordR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.STONE_SWORD), "",
                primitive, "hands", -1, 8, new Object[]{"R ", "R ", "SV", 'R', ComponentListMFR.SHARP_ROCK, 'V',
                        ComponentListMFR.VINE, 'S', Items.STICK});
        KnowledgeListMFR.stoneWarR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.STONE_WARAXE), "",
                primitive, "hands", -1, 8, new Object[]{"VRV", "RS", " S", 'R', ComponentListMFR.SHARP_ROCK, 'V',
                        ComponentListMFR.VINE, 'S', Items.STICK});
        KnowledgeListMFR.stoneMaceR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.STONE_MACE), "",
                primitive, "hands", -1, 8, new Object[]{" V ", "RSR", " S ", 'R', ComponentListMFR.SHARP_ROCK, 'V',
                        ComponentListMFR.VINE, 'S', Items.STICK});
        KnowledgeListMFR.stoneSpearR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.STONE_SPEAR), "",
                primitive, "hands", -1, 8, new Object[]{"R", "V", "S", "S", 'R', ComponentListMFR.SHARP_ROCK, 'V',
                        ComponentListMFR.VINE, 'S', Items.STICK});
        KnowledgeListMFR.stoneKnifeR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.STONE_KNIFE), "",
                primitive, "hands", -1, 4, new Object[]{"R ", "SV", 'R', ComponentListMFR.SHARP_ROCK, 'V',
                        ComponentListMFR.VINE, 'S', Items.STICK});
        KnowledgeListMFR.stoneHammerR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.STONE_HAMMER),
                "", primitive, "hands", -1, 4, new Object[]{"R", "V", "S", 'R', ComponentListMFR.SHARP_ROCK, 'V',
                        ComponentListMFR.VINE, 'S', Items.STICK});

        KnowledgeListMFR.stoneTongsR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.STONE_TONGS), "",
                primitive, "hands", -1, 4, new Object[]{" R", "SV", 'R', ComponentListMFR.SHARP_ROCK, 'V',
                        ComponentListMFR.VINE, 'S', Items.STICK});
        KnowledgeListMFR.boneNeedleR = MineFantasyRebornAPI.addCarpenterRecipe(null, new ItemStack(ToolListMFR.BONE_NEEDLE), "",
                primitive, "hands", -1, 4, new Object[]{"B", 'B', Items.BONE,});

        Salvage.addSalvage(ToolListMFR.STONE_PICK, new ItemStack(ComponentListMFR.SHARP_ROCK, 2),
                new ItemStack(Items.STICK, 2), ComponentListMFR.VINE);
        Salvage.addSalvage(ToolListMFR.STONE_AXE, new ItemStack(ComponentListMFR.SHARP_ROCK, 2),
                new ItemStack(Items.STICK, 2), ComponentListMFR.VINE);
        Salvage.addSalvage(ToolListMFR.STONE_SPADE, ComponentListMFR.SHARP_ROCK, new ItemStack(Items.STICK, 2),
                ComponentListMFR.VINE);
        Salvage.addSalvage(ToolListMFR.STONE_HOE, ComponentListMFR.SHARP_ROCK, new ItemStack(Items.STICK, 2),
                ComponentListMFR.VINE);
        Salvage.addSalvage(ToolListMFR.STONE_SWORD, Items.STICK, new ItemStack(ComponentListMFR.SHARP_ROCK, 2),
                ComponentListMFR.VINE);
        Salvage.addSalvage(ToolListMFR.STONE_WARAXE, new ItemStack(ComponentListMFR.SHARP_ROCK, 2),
                new ItemStack(Items.STICK, 2), new ItemStack(ComponentListMFR.VINE, 2));
        Salvage.addSalvage(ToolListMFR.STONE_MACE, new ItemStack(ComponentListMFR.SHARP_ROCK, 2),
                new ItemStack(Items.STICK, 2), ComponentListMFR.VINE);
        Salvage.addSalvage(ToolListMFR.STONE_SPEAR, ComponentListMFR.SHARP_ROCK, new ItemStack(Items.STICK, 2),
                ComponentListMFR.VINE);
        Salvage.addSalvage(ToolListMFR.STONE_KNIFE, ComponentListMFR.SHARP_ROCK, Items.STICK, ComponentListMFR.VINE);
        Salvage.addSalvage(ToolListMFR.STONE_HAMMER, ComponentListMFR.SHARP_ROCK, Items.STICK, ComponentListMFR.VINE);
        Salvage.addSalvage(ToolListMFR.STONE_TONGS, ComponentListMFR.SHARP_ROCK, Items.STICK, ComponentListMFR.VINE);
        Salvage.addSalvage(ToolListMFR.BONE_NEEDLE, Items.BONE);
    }

    public static void initTierWood() {
        SoundEvent basic = CarpenterRecipes.basic;

        float time = 4;
        Item plank = ComponentListMFR.PLANK;

        KnowledgeListMFR.spoonR = MineFantasyRebornAPI.addCarpenterToolRecipe(artisanry, CustomToolListMFR.STANDARD_SPOON, "",
                basic, "hands", -1, 1 + (int) (1 * time), new Object[]{"W", "S", 'W', plank, 'S', Items.STICK});
        Salvage.addSalvage(CustomToolListMFR.STANDARD_SPOON, plank, Items.STICK);
        KnowledgeListMFR.malletR = MineFantasyRebornAPI.addCarpenterToolRecipe(artisanry, CustomToolListMFR.STANDARD_MALLET, "",
                basic, "hands", -1, 1 + (int) (2 * time), new Object[]{"WW", " S", 'W', plank, 'S', Items.STICK});
        Salvage.addSalvage(CustomToolListMFR.STANDARD_MALLET, plank, plank, Items.STICK);
        Salvage.addSalvage(CustomToolListMFR.STANDARD_SPOON, plank, Items.STICK);

        KnowledgeListMFR.refinedPlankR.add(MineFantasyRebornAPI.addCarpenterRecipe(construction,
                ComponentListMFR.PLANK.construct("RefinedWood"), "", basic, "hands", -1, 1,
                new Object[]{"O", "P", 'O', ComponentListMFR.PLANT_OIL, 'P', (ComponentListMFR.PLANK)}));
        KnowledgeListMFR.easyPaintPlank.add(MineFantasyRebornAPI.addCarpenterRecipe(construction,
                ComponentListMFR.PLANK.construct("RefinedWood", 4), "paint_brush", sewing, "brush", -1, 2,
                new Object[]{" O  ", "PPPP", 'O', ComponentListMFR.PLANT_OIL, 'P', (ComponentListMFR.PLANK)}));
    }

    static void tryAddSawPlanks(ItemStack planks, CustomMaterial material) {
        String sub = material.name.substring(0, material.name.length() - 4).toLowerCase();

        if (planks.getUnlocalizedName().toLowerCase().contains(sub)) {
            addSawPlanks(planks, material);
        }
    }

    static void addSawPlanks(ItemStack planks, CustomMaterial material) {
        MineFantasyRebornAPI.addCarpenterRecipe(construction, (ComponentListMFR.PLANK).construct(material.name, 4),
                "commodities", sawing, "saw", -1, 10, new Object[]{"P", 'P', planks.copy()});
    }
}
