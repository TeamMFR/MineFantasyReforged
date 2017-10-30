package minefantasy.mf2.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import minefantasy.mf2.api.MineFantasyAPI;
import minefantasy.mf2.api.crafting.Salvage;
import minefantasy.mf2.api.crafting.anvil.IAnvilRecipe;
import minefantasy.mf2.api.rpg.Skill;
import minefantasy.mf2.api.rpg.SkillList;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.config.ConfigCrafting;
import minefantasy.mf2.config.ConfigHardcore;
import minefantasy.mf2.item.ItemComponentMF;
import minefantasy.mf2.item.food.FoodListMF;
import minefantasy.mf2.item.list.ArmourListMF;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.knowledge.KnowledgeListMF;
import minefantasy.mf2.material.BaseMaterialMF;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class ForgingRecipes {
    public static final HashMap<String, IAnvilRecipe[]> recipeMap = new HashMap<String, IAnvilRecipe[]>();
    private static final Skill artisanry = SkillList.artisanry;
    private static final Skill engineering = SkillList.engineering;
    private static final Skill construction = SkillList.construction;

    public static void init() {
        ForgedToolRecipes.init();
        ForgedArmourRecipes.init();
        addCogworkParts();

        ItemStack ironbar = ComponentListMF.bar("Iron");
        ItemStack steelbar = ComponentListMF.bar("Steel");
        ItemStack goldbar = ComponentListMF.bar("Gold");
        ItemStack silverbar = ComponentListMF.bar("Silver");

        // MISC
        BaseMaterialMF material;
        int time;
        time = 1;
        material = BaseMaterialMF.encrusted;

        KnowledgeListMF.obsidianHunkR = MineFantasyAPI.addAnvilRecipe(null,
                new ItemStack(ComponentListMF.obsidian_rock, 4), "", false, material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier), new Object[]{"D", 'D', Blocks.obsidian,});
        KnowledgeListMF.diamondR = MineFantasyAPI.addAnvilRecipe(null, new ItemStack(ComponentListMF.diamond_shards),
                "", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{"D", 'D', Items.diamond,});

        time = 3;
        KnowledgeListMF.encrustedR = MineFantasyAPI.addAnvilRecipe(artisanry, ComponentListMF.bar("Encrusted"),
                "smeltEncrusted", true, material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier),
                new Object[]{"D", "I", 'D', ComponentListMF.diamond_shards, 'I', ComponentListMF.bar("Steel"),});
        Salvage.addSalvage(ComponentListMF.ingots[5], ComponentListMF.ingots[4], ComponentListMF.diamond_shards);
        Salvage.addSalvage(ComponentListMF.bar("Encrusted"), ComponentListMF.ingots[4],
                ComponentListMF.diamond_shards);

        material = BaseMaterialMF.pigiron;
        KnowledgeListMF.steelR = MineFantasyAPI.addAnvilRecipe(artisanry, ComponentListMF.bar("Steel"), "smeltSteel",
                true, 1, 1, 5,
                new Object[]{"C", "H", 'C', ComponentListMF.coalDust, 'H', ComponentListMF.bar("PigIron")});
        KnowledgeListMF.fluxR = MineFantasyAPI.addAnvilRecipe(null, new ItemStack(ComponentListMF.flux, 4), "", false,
                -1, -1, 2, new Object[]{"H", 'H', BlockListMF.limestone});

        // STUDDED
        material = BaseMaterialMF.iron;
        // HELMET
        time = 10;
        KnowledgeListMF.studHelmetR = MineFantasyAPI.addAnvilRecipe(artisanry,
                ArmourListMF.armour(ArmourListMF.leather, 3, 0), "craftArmourLight", false, material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier), new Object[]{" I ", "IAI", " I ", 'I',
                        ComponentListMF.rivet, 'A', ArmourListMF.armourItem(ArmourListMF.leather, 2, 0),});
        // CHEST
        time = 20;
        KnowledgeListMF.studChestR = MineFantasyAPI.addAnvilRecipe(artisanry,
                ArmourListMF.armour(ArmourListMF.leather, 3, 1), "craftArmourLight", false, material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier), new Object[]{" I ", "IAI", " I ", 'I',
                        ComponentListMF.rivet, 'A', ArmourListMF.armourItem(ArmourListMF.leather, 2, 1),});
        // LEGS
        time = 15;
        KnowledgeListMF.studLegsR = MineFantasyAPI.addAnvilRecipe(artisanry,
                ArmourListMF.armour(ArmourListMF.leather, 3, 2), "craftArmourLight", false, material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier), new Object[]{" I ", "IAI", " I ", 'I',
                        ComponentListMF.rivet, 'A', ArmourListMF.armourItem(ArmourListMF.leather, 2, 2),});
        // BOOTS
        time = 6;
        KnowledgeListMF.studBootsR = MineFantasyAPI.addAnvilRecipe(artisanry,
                ArmourListMF.armour(ArmourListMF.leather, 3, 3), "craftArmourLight", false, material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier), new Object[]{" I ", "IAI", " I ", 'I',
                        ComponentListMF.rivet, 'A', ArmourListMF.armourItem(ArmourListMF.leather, 2, 3),});

        Salvage.addSalvage(ArmourListMF.armourItem(ArmourListMF.leather, 3, 0),
                ArmourListMF.armour(ArmourListMF.leather, 2, 0), new ItemStack(ComponentListMF.rivet, 4));
        Salvage.addSalvage(ArmourListMF.armourItem(ArmourListMF.leather, 3, 1),
                ArmourListMF.armour(ArmourListMF.leather, 2, 1), new ItemStack(ComponentListMF.rivet, 4));
        Salvage.addSalvage(ArmourListMF.armourItem(ArmourListMF.leather, 3, 2),
                ArmourListMF.armour(ArmourListMF.leather, 2, 2), new ItemStack(ComponentListMF.rivet, 4));
        Salvage.addSalvage(ArmourListMF.armourItem(ArmourListMF.leather, 3, 3),
                ArmourListMF.armour(ArmourListMF.leather, 2, 3), new ItemStack(ComponentListMF.rivet, 4));

        time = 2;
        material = BaseMaterialMF.iron;
        if (ConfigCrafting.allowIronResmelt) {
            MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMF.iron_prep), "blastfurn", false,
                    material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier),
                    new Object[]{"IFI", 'I', ComponentListMF.bar("iron"), 'F', ComponentListMF.flux,});
            MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMF.iron_prep, 2), "blastfurn", false,
                    material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier),
                    new Object[]{"IFI", 'I', ComponentListMF.bar("iron"), 'F', ComponentListMF.flux_strong,});
        }
        KnowledgeListMF.coalPrepR = MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMF.coal_prep),
                "coke", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{"RCR", "CFC", "RCR", 'R', Items.redstone, 'C', new ItemStack(Items.coal, 1, 1), 'F',
                        ComponentListMF.flux_strong,});
        GameRegistry.addSmelting(ComponentListMF.coal_prep, new ItemStack(ComponentListMF.coke), 1F);

        KnowledgeListMF.ironPrepR = MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMF.iron_prep),
                "blastfurn", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{"IFI", 'I', Blocks.iron_ore, 'F', ComponentListMF.flux,});
        KnowledgeListMF.ironPrepR2 = MineFantasyAPI.addAnvilRecipe(artisanry,
                new ItemStack(ComponentListMF.iron_prep, 2), "blastfurn", false, material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{"IFI", 'I', Blocks.iron_ore, 'F', ComponentListMF.flux_strong,});

        MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMF.iron_prep), "blastfurn", false,
                material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{"IFI", 'I', ComponentListMF.oreIron, 'F', ComponentListMF.flux,});
        MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMF.iron_prep, 2), "blastfurn", false,
                material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{"IFI", 'I', ComponentListMF.oreIron, 'F', ComponentListMF.flux_strong,});
        ItemStack plate = ComponentListMF.plate.createComm("iron");
        time = 10;
        KnowledgeListMF.blastChamR = MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(BlockListMF.blast_chamber),
                "blastfurn", false, "hvyHammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier),
                new Object[]{"RP PR", "RP PR", "RP PR", "RP PR", 'R', ComponentListMF.rivet, 'P', plate,});

        time = 15;
        KnowledgeListMF.blastHeatR = MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(BlockListMF.blast_heater),
                "blastfurn", false, "hvyHammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier), new Object[]{"RP PR", "RP PR", "RP PR", "RPFPR", 'R',
                        ComponentListMF.rivet, 'P', plate, 'F', Blocks.furnace,});

        Salvage.addSalvage(BlockListMF.blast_heater, new ItemStack(ComponentListMF.rivet, 8), plate, plate, plate,
                plate, plate, plate, plate, plate, Blocks.furnace);
        Salvage.addSalvage(BlockListMF.blast_chamber, new ItemStack(ComponentListMF.rivet, 8), plate, plate, plate,
                plate, plate, plate, plate, plate);

        time = 10;
        KnowledgeListMF.bigFurnR = MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(BlockListMF.furnace_stone),
                "bigfurn", false, "hammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier), new Object[]{"PRP", "RCR", "PFP", 'F', Blocks.furnace, 'R',
                        ComponentListMF.rivet, 'P', plate, 'C', BlockListMF.firebricks});
        time = 10;
        KnowledgeListMF.bigHeatR = MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(BlockListMF.furnace_heater),
                "bigfurn", false, "hammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier), new Object[]{"RCR", "PFP", 'R', ComponentListMF.rivet, 'P',
                        plate, 'C', BlockListMF.firebricks, 'F', BlockListMF.forge,});

        Salvage.addSalvage(BlockListMF.furnace_heater, new ItemStack(ComponentListMF.rivet, 2), plate, plate,
                BlockListMF.firebricks, BlockListMF.forge);
        Salvage.addSalvage(BlockListMF.furnace_stone, new ItemStack(ComponentListMF.rivet, 3), plate, plate, plate,
                plate, BlockListMF.firebricks, Blocks.furnace);

        IAnvilRecipe[] anvilRecs = new IAnvilRecipe[BlockListMF.anvils.length];
        for (int id = 0; id < BlockListMF.anvils.length; id++) {
            time = 8;
            material = BaseMaterialMF.getMaterial(BlockListMF.anvils[id]);
            ItemStack bar = ComponentListMF.bar(material.name);

            anvilRecs[id] = MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(BlockListMF.anvil[id]),
                    "smelt" + material.name, false, "hammer", -1, -1, (int) (time * material.craftTimeModifier),
                    new Object[]{" II", "III", " I ", 'I', bar,});

            Salvage.addSalvage(BlockListMF.anvil[id], ComponentListMF.bar(material.name, 6));
        }
        recipeMap.put("anvilCrafting", anvilRecs);

        ItemStack bronzeHunk = ComponentListMF.metalHunk.createComm("bronze");
        ItemStack ironHunk = ComponentListMF.metalHunk.createComm("iron");

        time = 2;
        material = BaseMaterialMF.bronze;
        KnowledgeListMF.framedStoneR = MineFantasyAPI.addAnvilRecipe(construction,
                new ItemStack(BlockListMF.reinforced_stone_framed), "decorated_stone", false, "hammer",
                material.hammerTier - 1, material.anvilTier - 1, (int) (time * material.craftTimeModifier),
                new Object[]{" N ", "NSN", " N ", 'N', bronzeHunk, 'S', BlockListMF.reinforced_stone,});
        Salvage.addSalvage(BlockListMF.reinforced_stone_framed, bronzeHunk, bronzeHunk, bronzeHunk, bronzeHunk,
                BlockListMF.reinforced_stone);
        time = 2;
        material = BaseMaterialMF.iron;
        KnowledgeListMF.iframedStoneR = MineFantasyAPI.addAnvilRecipe(construction,
                new ItemStack(BlockListMF.reinforced_stone_framediron), "decorated_stone", false, "hammer",
                material.hammerTier - 1, material.anvilTier - 1, (int) (time * material.craftTimeModifier),
                new Object[]{" N ", "NSN", " N ", 'N', ironHunk, 'S', BlockListMF.reinforced_stone,});
        Salvage.addSalvage(BlockListMF.reinforced_stone_framediron, ironHunk, ironHunk, ironHunk, ironHunk,
                BlockListMF.reinforced_stone);

        time = 2;
        material = BaseMaterialMF.bronze;
        KnowledgeListMF.smokePipeR = MineFantasyAPI.addAnvilRecipe(construction,
                new ItemStack(BlockListMF.chimney_pipe, 4), "", false, "hammer", material.hammerTier - 1,
                material.anvilTier - 1, (int) (time * material.craftTimeModifier),
                new Object[]{"N  N", "PPPP", "N  N", 'N', bronzeHunk, 'P', BlockListMF.chimney_stone,});
        Salvage.addSalvage(BlockListMF.chimney_pipe, BlockListMF.chimney_stone, bronzeHunk);

        for (int id = 0; id < BlockListMF.specialMetalBlocks.length; id++) {
            time = 2;
            material = BaseMaterialMF.getMaterial(BlockListMF.specialMetalBlocks[id]);
            ItemStack hunk = ComponentListMF.metalHunk.createComm(material.name);
            if (hunk != null) {
                KnowledgeListMF.barsR
                        .add(MineFantasyAPI.addAnvilRecipe(construction, new ItemStack(BlockListMF.bars[id]),
                                "smelt" + material.name, false, "hammer", material.hammerTier, material.anvilTier,
                                (int) (time * material.craftTimeModifier), new Object[]{"I I", "I I", 'I', hunk,}));
                if (hunk.getItem() instanceof ItemComponentMF) {
                    Salvage.addSalvage(BlockListMF.bars[id], hunk, hunk, hunk, hunk);
                }
            }
        }
        if (!ConfigHardcore.HCCRemoveTalismansCraft) {
            KnowledgeListMF.talismanRecipe.add(MineFantasyAPI.addAnvilRecipe(artisanry,
                    new ItemStack(ComponentListMF.talisman_lesser), "", true, "hammer", -1, -1, 20, new Object[]{
                            "LGL", "GIG", " G ", 'L', new ItemStack(Items.dye, 1, 4), 'I', ironbar, 'G', goldbar,}));
            KnowledgeListMF.talismanRecipe.add(MineFantasyAPI.addAnvilRecipe(artisanry,
                    new ItemStack(ComponentListMF.talisman_lesser), "", true, "hammer", -1, -1, 20, new Object[]{
                            "LSL", "SIS", " S ", 'L', new ItemStack(Items.dye, 1, 4), 'I', ironbar, 'S', silverbar,}));
            KnowledgeListMF.greatTalismanRecipe = MineFantasyAPI.addAnvilRecipe(artisanry,
                    new ItemStack(ComponentListMF.talisman_greater), "", true, "hammer", 1, 1, 50,
                    new Object[]{"GSG", "DTD", "GDG", 'G', goldbar, 'D', Items.diamond, 'T',
                            ComponentListMF.talisman_lesser, 'S', Items.nether_star,});
        }

        addEngineering();
        addConstruction();

        time = 10;
        material = BaseMaterialMF.iron;
        KnowledgeListMF.caketinRecipe = MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(FoodListMF.cake_tin), "",
                true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{" R ", "I I", " I ", 'I', ironbar, 'R', ComponentListMF.rivet,});
        Salvage.addSalvage(FoodListMF.cake_tin, ComponentListMF.bar("Iron", 3), ComponentListMF.rivet);

        KnowledgeListMF.coalfluxR = MineFantasyAPI.addAnvilRecipe(artisanry,
                new ItemStack(ComponentListMF.coal_flux, 2), "coalflux", false, material.hammerTier, material.anvilTier,
                2, new Object[]{"F", "C", 'C', Items.coal, 'F', ComponentListMF.flux_pot,});

        time = 4;
        material = BaseMaterialMF.iron;
        KnowledgeListMF.hingeRecipe = MineFantasyAPI.addAnvilRecipe(construction, new ItemStack(ComponentListMF.hinge),
                "", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{"LR", 'L', ComponentListMF.leather_strip, 'R', ComponentListMF.rivet,});
        Salvage.addSalvage(ComponentListMF.hinge, ComponentListMF.leather_strip, ComponentListMF.rivet);

        time = 10;
        KnowledgeListMF.crestR = MineFantasyAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMF.ornate_items),
                "craftOrnate", false, "hammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier),
                new Object[]{" G ", "SLS", " G ", 'G', goldbar, 'S', silverbar, 'L', new ItemStack(Items.dye, 1, 4)

                });
    }

    private static Item getStrips(BaseMaterialMF material) {
        return ComponentListMF.leather_strip;
    }

    private static Item getLeather(BaseMaterialMF material) {
        return Items.leather;
    }

    private static void addEngineering() {
        ItemStack copper = ComponentListMF.bar("Copper");
        ItemStack bronze = ComponentListMF.bar("Bronze");
        ItemStack iron = ComponentListMF.bar("Iron");
        ItemStack steel = ComponentListMF.bar("Steel");
        ItemStack tungsten = ComponentListMF.bar("Tungsten");
        ItemStack blacksteel = ComponentListMF.bar("BlackSteel");

        ItemStack bronzeHunk = ComponentListMF.metalHunk.createComm("bronze");
        ItemStack ironHunk = ComponentListMF.metalHunk.createComm("iron");
        ItemStack steelHunk = ComponentListMF.metalHunk.createComm("steel");
        ItemStack tungstenHunk = ComponentListMF.metalHunk.createComm("tungsten");
        ItemStack obsidianHunk = ComponentListMF.metalHunk.createComm("obsidian");

        BaseMaterialMF material = BaseMaterialMF.steel;
        int time = 15;
        KnowledgeListMF.eatoolsR = MineFantasyAPI.addAnvilRecipe(engineering,
                new ItemStack(ToolListMF.engin_anvil_tools), "etools", true, "hammer", material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{"SSLL", "LLSS", 'S', steelHunk, 'L', getStrips(material),});
        time = 5;
        KnowledgeListMF.iframeR = MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMF.iron_frame),
                "ecomponents", true, "hammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier), new Object[]{"RRR", "ISI", "STS", "ISI", 'T',
                        ToolListMF.engin_anvil_tools, 'R', ComponentListMF.rivet, 'I', ironHunk, 'S', steelHunk,});
        time = 8;
        KnowledgeListMF.istrutR = MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMF.iron_strut),
                "ecomponents", true, "hvyhammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier), new Object[]{"RTR", "SIS", "SIS", 'T',
                        ToolListMF.engin_anvil_tools, 'R', ComponentListMF.rivet, 'I', iron, 'S', steelHunk,});
        time = 8;
        KnowledgeListMF.stubeR = MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMF.steel_tube),
                "ecomponents", true, "hammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier), new Object[]{"TR R", "SSSS", 'T',
                        ToolListMF.engin_anvil_tools, 'R', ComponentListMF.rivet, 'S', steelHunk,});
        time = 2;
        KnowledgeListMF.boltR = MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMF.bolt, 2),
                "etools", true, "hammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier), new Object[]{" T ", "SIS", " S ", " S ", 'T',
                        ToolListMF.engin_anvil_tools, 'I', iron, 'S', steelHunk,});
        time = 35;
        KnowledgeListMF.climbPickbR = MineFantasyAPI.addAnvilRecipe(engineering,
                new ItemStack(ToolListMF.climbing_pick_basic), "climber", true, "hammer", material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{"L SR", "IISR", "L T ", 'R', ComponentListMF.rivet, 'T', ToolListMF.engin_anvil_tools,
                        'I', iron, 'S', steel, 'L', getStrips(material),});
        time = 5;
        KnowledgeListMF.bgearR = MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMF.bronze_gears),
                "ecomponents", true, "hammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier), new Object[]{" T ", " B ", "BIB", " B ", 'T',
                        ToolListMF.engin_anvil_tools, 'I', iron, 'B', bronzeHunk,});
        time = 8;
        material = BaseMaterialMF.tungsten;
        KnowledgeListMF.tgearR = MineFantasyAPI.addAnvilRecipe(engineering,
                new ItemStack(ComponentListMF.tungsten_gears), "tungsten", true, "hammer", material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{" T ", " W ", "WGW", " W ", 'T', ToolListMF.engin_anvil_tools, 'W', tungstenHunk, 'G',
                        ComponentListMF.bronze_gears,});
        time = 5;
        material = BaseMaterialMF.compositeAlloy;
        KnowledgeListMF.compPlateR = MineFantasyAPI.addAnvilRecipe(engineering, ComponentListMF.bar("CompositeAlloy"),
                "cogArmour", true, "hvyhammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier), new Object[]{" T ", " S ", "RWR", " C ",

                        'R', ComponentListMF.rivet, 'T', ToolListMF.engin_anvil_tools, 'C', copper, 'W', tungstenHunk,
                        'S', steel,});
        material = BaseMaterialMF.iron;

        time = 5;
        KnowledgeListMF.bombCaseIronR = MineFantasyAPI.addAnvilRecipe(engineering,
                new ItemStack(ComponentListMF.bomb_casing_iron, 2), "bombIron", true, material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier), new Object[]{" T ", " I ", "IRI",
                        " I ", 'T', ToolListMF.engin_anvil_tools, 'I', ironHunk, 'R', ComponentListMF.rivet,});
        KnowledgeListMF.mineCaseIronR = MineFantasyAPI.addAnvilRecipe(engineering,
                new ItemStack(ComponentListMF.mine_casing_iron, 2), "bombIron", true, material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{"  T  ", "  P  ", " IRI ", "IR RI", 'T', ToolListMF.engin_anvil_tools, 'P',
                        Blocks.heavy_weighted_pressure_plate, 'I', ironHunk, 'R', ComponentListMF.rivet,});

        time = 5;
        KnowledgeListMF.bombarrowR = MineFantasyAPI.addAnvilRecipe(engineering,
                new ItemStack(ComponentListMF.bomb_casing_arrow), "bombarrow", true, material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{"   IR", "FPITI", "   IR", 'T', ToolListMF.engin_anvil_tools, 'I', ironHunk, 'R',
                        Items.redstone, 'P', ComponentListMF.plank.construct("RefinedWood"), 'F',
                        ComponentListMF.fletching,});
        KnowledgeListMF.bombBoltR = MineFantasyAPI.addAnvilRecipe(engineering,
                new ItemStack(ComponentListMF.bomb_casing_bolt), "bombarrow", true, material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{"  IR", "FITI", "  IR", 'T', ToolListMF.engin_anvil_tools, 'I', ironHunk, 'R',
                        Items.redstone, 'F', ComponentListMF.fletching,});

        material = BaseMaterialMF.blacksteel;

        time = 5;
        KnowledgeListMF.bombCaseObsidianR = MineFantasyAPI.addAnvilRecipe(engineering,
                new ItemStack(ComponentListMF.bomb_casing_obsidian, 2), "bombObsidian", true, material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{" T ", "RIR", "IOI", "RIR", 'T', ToolListMF.engin_anvil_tools, 'O', Blocks.obsidian, 'I',
                        obsidianHunk, 'R', ComponentListMF.rivet,});
        KnowledgeListMF.mineCaseObsidianR = MineFantasyAPI.addAnvilRecipe(engineering,
                new ItemStack(ComponentListMF.mine_casing_obsidian, 2), "mineObsidian", true, material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{"  T  ", "  P  ", " IRI ", "IRORI", 'T', ToolListMF.engin_anvil_tools, 'O',
                        Blocks.obsidian, 'P', Blocks.heavy_weighted_pressure_plate, 'I', obsidianHunk, 'R',
                        ComponentListMF.rivet,});
        time = 15;
        material = BaseMaterialMF.steel;
        KnowledgeListMF.crossBayonetR = MineFantasyAPI.addAnvilRecipe(engineering,
                new ItemStack(ComponentListMF.cross_bayonet), "crossBayonet", true, material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier), new Object[]{"R R I", "PIII ", 'P',
                        ComponentListMF.plank.construct("RefinedWood"), 'I', ironHunk, 'R', ComponentListMF.rivet,});

        Salvage.addSalvage(ToolListMF.engin_anvil_tools, steelHunk, steelHunk, steelHunk, steelHunk,
                new ItemStack(ComponentListMF.leather_strip, 4));

        Salvage.addSalvage(ComponentListMF.iron_frame, steelHunk, steelHunk, steelHunk, steelHunk, ironHunk, ironHunk,
                ironHunk, ironHunk, new ItemStack(ComponentListMF.rivet, 3));

        Salvage.addSalvage(ComponentListMF.iron_strut, steelHunk, steelHunk, steelHunk, steelHunk,
                ComponentListMF.bar("Iron", 2), new ItemStack(ComponentListMF.rivet, 2));

        Salvage.addSalvage(ComponentListMF.steel_tube, steelHunk, steelHunk, steelHunk, steelHunk,
                new ItemStack(ComponentListMF.rivet, 2));

        Salvage.addSalvage(ToolListMF.climbing_pick_basic, ComponentListMF.bar("Iron", 2),
                ComponentListMF.bar("Steel", 2), new ItemStack(ComponentListMF.rivet, 2),
                new ItemStack(ComponentListMF.leather_strip, 2));

        Salvage.addSalvage(ComponentListMF.bronze_gears, bronzeHunk, bronzeHunk, bronzeHunk, bronzeHunk, iron);

        Salvage.addSalvage(ComponentListMF.tungsten_gears, tungstenHunk, tungstenHunk, tungstenHunk, tungstenHunk,
                ComponentListMF.bronze_gears);

        Salvage.addSalvage(ComponentListMF.bar("CompositeAlloy"), copper, steel, tungstenHunk,
                new ItemStack(ComponentListMF.rivet, 2));
        Salvage.addSalvage(ComponentListMF.ingotCompositeAlloy, copper, steel, tungstenHunk,
                new ItemStack(ComponentListMF.rivet, 2));

        Salvage.addSalvage(ComponentListMF.bomb_casing_iron, ironHunk, ironHunk);
        Salvage.addSalvage(ComponentListMF.mine_casing_iron, ironHunk, ironHunk, ComponentListMF.rivet, iron);
        Salvage.addSalvage(ComponentListMF.bomb_casing_arrow, ironHunk, ironHunk, ironHunk, ironHunk,
                new ItemStack(Items.redstone, 2), ComponentListMF.plank.construct("RefinedWood"),
                ComponentListMF.fletching);

        Salvage.addSalvage(ComponentListMF.bomb_casing_bolt, ironHunk, ironHunk, ironHunk, ironHunk,
                new ItemStack(Items.redstone, 2), ComponentListMF.fletching);

        Salvage.addSalvage(ComponentListMF.bomb_casing_obsidian, obsidianHunk, obsidianHunk,
                new ItemStack(ComponentListMF.rivet, 2));
        Salvage.addSalvage(ComponentListMF.mine_casing_obsidian, obsidianHunk, obsidianHunk, iron,
                ComponentListMF.rivet);

        Salvage.addSalvage(ComponentListMF.cross_bayonet, ironHunk, ironHunk, ironHunk, ironHunk,
                ComponentListMF.plank.construct("RefinedWood"), new ItemStack(ComponentListMF.rivet, 2));
    }

    private static void addConstruction() {
        BaseMaterialMF material = BaseMaterialMF.tin;
        ItemStack tin = ComponentListMF.bar("Tin");
        int time = 10;
        KnowledgeListMF.brushRecipe = MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(ToolListMF.paint_brush),
                "paint_brush", true, "hammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier), new Object[]{"W", "I", "P",

                        'W', Blocks.wool, 'I', tin, 'P', ComponentListMF.plank.construct("RefinedWood"),});

        Salvage.addSalvage(ToolListMF.paint_brush, Blocks.wool, tin, ComponentListMF.plank.construct("RefinedWood"));
    }

    private static void addCogworkParts() {
        BaseMaterialMF material = BaseMaterialMF.steel;
        int time = 1;
        KnowledgeListMF.frameBlockR = MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(BlockListMF.frame_block),
                "cogArmour", false, "hammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier), new Object[]{"R", "I",

                        'I', ComponentListMF.iron_frame, 'R', ComponentListMF.rivet,});

        Salvage.addSalvage(BlockListMF.frame_block, ComponentListMF.iron_frame, ComponentListMF.rivet);

        time = 10;
        KnowledgeListMF.cogPulleyR = MineFantasyAPI.addAnvilRecipe(engineering,
                new ItemStack(ComponentListMF.cogwork_pulley), "cogArmour", false, "hammer", material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier), new Object[]{"RFR", "GBG", "RFR",

                        'B', Blocks.redstone_block, 'F', ComponentListMF.iron_frame, 'R', ComponentListMF.rivet, 'G',
                        ComponentListMF.tungsten_gears,});
        Salvage.addSalvage(ComponentListMF.cogwork_pulley, Blocks.redstone_block,
                new ItemStack(ComponentListMF.iron_frame, 2), new ItemStack(ComponentListMF.rivet, 2),
                new ItemStack(ComponentListMF.tungsten_gears, 2));
        Salvage.addSalvage(BlockListMF.frame_block, ComponentListMF.iron_frame, ComponentListMF.rivet);

        time = 10;
        KnowledgeListMF.cogHelmR = MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(BlockListMF.cogwork_helm),
                "cogArmour", false, "hammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier), new Object[]{"RFFR", "SEES", " RR ",

                        'E', Items.ender_eye, 'F', ComponentListMF.iron_frame, 'R', ComponentListMF.rivet, 'S',
                        ComponentListMF.cogwork_shaft,});
        Salvage.addSalvage(BlockListMF.cogwork_helm, new ItemStack(Items.ender_eye, 2),
                new ItemStack(ComponentListMF.iron_frame, 2), new ItemStack(ComponentListMF.cogwork_shaft, 2),
                new ItemStack(ComponentListMF.rivet, 4));
        time = 15;
        KnowledgeListMF.cogChestR = MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(BlockListMF.cogwork_chest),
                "cogArmour", false, "hammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier), new Object[]{" RFR ", "RSFSR", "RFBFR", " SFS ",

                        'F', ComponentListMF.iron_frame, 'R', ComponentListMF.rivet, 'S', ComponentListMF.cogwork_shaft,
                        'B', Blocks.furnace,});
        Salvage.addSalvage(BlockListMF.cogwork_chest, Blocks.furnace, new ItemStack(ComponentListMF.iron_frame, 5),
                new ItemStack(ComponentListMF.cogwork_shaft, 4), new ItemStack(ComponentListMF.rivet, 6));

        time = 10;
        KnowledgeListMF.cogLegsR = MineFantasyAPI.addAnvilRecipe(engineering, new ItemStack(BlockListMF.cogwork_legs),
                "cogArmour", false, "hammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier), new Object[]{"RFFFR", "RS SR", " S S ", " F F ",

                        'F', ComponentListMF.iron_frame, 'R', ComponentListMF.rivet, 'S',
                        ComponentListMF.cogwork_shaft,});
        Salvage.addSalvage(BlockListMF.cogwork_legs, new ItemStack(ComponentListMF.iron_frame, 5),
                new ItemStack(ComponentListMF.cogwork_shaft, 4), new ItemStack(ComponentListMF.rivet, 4));

    }
}
