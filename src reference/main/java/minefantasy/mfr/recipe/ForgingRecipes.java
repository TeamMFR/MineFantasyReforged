package minefantasy.mfr.recipe;

import net.minecraftforge.fml.common.registry.GameRegistry;
import minefantasy.mfr.api.MineFantasyRebornAPI;
import minefantasy.mfr.api.crafting.Salvage;
import minefantasy.mfr.api.crafting.anvil.IAnvilRecipe;
import minefantasy.mfr.api.rpg.Skill;
import minefantasy.mfr.api.rpg.SkillList;
import minefantasy.mfr.init.BlockListMFR;
import minefantasy.mfr.config.ConfigCrafting;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.item.ItemComponentMFR;
import minefantasy.mfr.init.FoodListMFR;
import minefantasy.mfr.init.ArmourListMFR;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.ToolListMFR;
import minefantasy.mfr.knowledge.KnowledgeListMFR;
import minefantasy.mfr.material.BaseMaterialMFR;
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

        ItemStack ironbar = ComponentListMFR.bar("Iron");
        ItemStack steelbar = ComponentListMFR.bar("Steel");
        ItemStack goldbar = ComponentListMFR.bar("Gold");
        ItemStack silverbar = ComponentListMFR.bar("Silver");

        // MISC
        BaseMaterialMFR material;
        int time;
        time = 1;
        material = BaseMaterialMFR.encrusted;

        KnowledgeListMFR.obsidianHunkR = MineFantasyRebornAPI.addAnvilRecipe(null,
                new ItemStack(ComponentListMFR.obsidian_rock, 4), "", false, material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier), new Object[]{"D", 'D', Blocks.OBSIDIAN,});
        KnowledgeListMFR.diamondR = MineFantasyRebornAPI.addAnvilRecipe(null, new ItemStack(ComponentListMFR.diamond_shards),
                "", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{"D", 'D', Items.DIAMOND,});

        time = 3;
        KnowledgeListMFR.encrustedR = MineFantasyRebornAPI.addAnvilRecipe(artisanry, ComponentListMFR.bar("Encrusted"),
                "smeltEncrusted", true, material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier),
                new Object[]{"D", "I", 'D', ComponentListMFR.diamond_shards, 'I', ComponentListMFR.bar("Steel"),});
        Salvage.addSalvage(ComponentListMFR.ingots[5], ComponentListMFR.ingots[4], ComponentListMFR.diamond_shards);
        Salvage.addSalvage(ComponentListMFR.bar("Encrusted"), ComponentListMFR.ingots[4],
                ComponentListMFR.diamond_shards);

        material = BaseMaterialMFR.pigiron;
        KnowledgeListMFR.steelR = MineFantasyRebornAPI.addAnvilRecipe(artisanry, ComponentListMFR.bar("Steel"), "smeltSteel",
                true, 1, 1, 5,
                new Object[]{"C", "H", 'C', ComponentListMFR.coalDust, 'H', ComponentListMFR.bar("PigIron")});
        KnowledgeListMFR.fluxR = MineFantasyRebornAPI.addAnvilRecipe(null, new ItemStack(ComponentListMFR.flux, 4), "", false,
                -1, -1, 2, new Object[]{"H", 'H', BlockListMFR.LIMESTONE});

        // STUDDED
        material = BaseMaterialMFR.iron;
        // HELMET
        time = 10;
        KnowledgeListMFR.studHelmetR = MineFantasyRebornAPI.addAnvilRecipe(artisanry,
                ArmourListMFR.armour(ArmourListMFR.leather, 3, 0), "craftArmourLight", false, material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier), new Object[]{" I ", "IAI", " I ", 'I',
                        ComponentListMFR.rivet, 'A', ArmourListMFR.armourItem(ArmourListMFR.leather, 2, 0),});
        // CHEST
        time = 20;
        KnowledgeListMFR.studChestR = MineFantasyRebornAPI.addAnvilRecipe(artisanry,
                ArmourListMFR.armour(ArmourListMFR.leather, 3, 1), "craftArmourLight", false, material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier), new Object[]{" I ", "IAI", " I ", 'I',
                        ComponentListMFR.rivet, 'A', ArmourListMFR.armourItem(ArmourListMFR.leather, 2, 1),});
        // LEGS
        time = 15;
        KnowledgeListMFR.studLegsR = MineFantasyRebornAPI.addAnvilRecipe(artisanry,
                ArmourListMFR.armour(ArmourListMFR.leather, 3, 2), "craftArmourLight", false, material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier), new Object[]{" I ", "IAI", " I ", 'I',
                        ComponentListMFR.rivet, 'A', ArmourListMFR.armourItem(ArmourListMFR.leather, 2, 2),});
        // BOOTS
        time = 6;
        KnowledgeListMFR.studBootsR = MineFantasyRebornAPI.addAnvilRecipe(artisanry,
                ArmourListMFR.armour(ArmourListMFR.leather, 3, 3), "craftArmourLight", false, material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier), new Object[]{" I ", "IAI", " I ", 'I',
                        ComponentListMFR.rivet, 'A', ArmourListMFR.armourItem(ArmourListMFR.leather, 2, 3),});

        Salvage.addSalvage(ArmourListMFR.armourItem(ArmourListMFR.leather, 3, 0),
                ArmourListMFR.armour(ArmourListMFR.leather, 2, 0), new ItemStack(ComponentListMFR.rivet, 4));
        Salvage.addSalvage(ArmourListMFR.armourItem(ArmourListMFR.leather, 3, 1),
                ArmourListMFR.armour(ArmourListMFR.leather, 2, 1), new ItemStack(ComponentListMFR.rivet, 4));
        Salvage.addSalvage(ArmourListMFR.armourItem(ArmourListMFR.leather, 3, 2),
                ArmourListMFR.armour(ArmourListMFR.leather, 2, 2), new ItemStack(ComponentListMFR.rivet, 4));
        Salvage.addSalvage(ArmourListMFR.armourItem(ArmourListMFR.leather, 3, 3),
                ArmourListMFR.armour(ArmourListMFR.leather, 2, 3), new ItemStack(ComponentListMFR.rivet, 4));

        time = 2;
        material = BaseMaterialMFR.iron;
        if (ConfigCrafting.allowIronResmelt) {
            MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMFR.iron_prep), "blastfurn", false,
                    material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier),
                    new Object[]{"IFI", 'I', ComponentListMFR.bar("iron"), 'F', ComponentListMFR.flux,});
            MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMFR.iron_prep, 2), "blastfurn", false,
                    material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier),
                    new Object[]{"IFI", 'I', ComponentListMFR.bar("iron"), 'F', ComponentListMFR.flux_strong,});
        }
        KnowledgeListMFR.coalPrepR = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMFR.coal_prep),
                "coke", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{"RCR", "CFC", "RCR", 'R', Items.REDSTONE, 'C', new ItemStack(Items.COAL, 1, 1), 'F',
                        ComponentListMFR.flux_strong,});
        GameRegistry.addSmelting(ComponentListMFR.coal_prep, new ItemStack(ComponentListMFR.coke), 1F);

        KnowledgeListMFR.ironPrepR = MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMFR.iron_prep),
                "blastfurn", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{"IFI", 'I', Blocks.IRON_ORE, 'F', ComponentListMFR.flux,});
        KnowledgeListMFR.ironPrepR2 = MineFantasyRebornAPI.addAnvilRecipe(artisanry,
                new ItemStack(ComponentListMFR.iron_prep, 2), "blastfurn", false, material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{"IFI", 'I', Blocks.IRON_ORE, 'F', ComponentListMFR.flux_strong,});

        MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMFR.iron_prep), "blastfurn", false,
                material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{"IFI", 'I', ComponentListMFR.oreIron, 'F', ComponentListMFR.flux,});
        MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMFR.iron_prep, 2), "blastfurn", false,
                material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{"IFI", 'I', ComponentListMFR.oreIron, 'F', ComponentListMFR.flux_strong,});
        ItemStack plate = ComponentListMFR.plate.createComm("iron");
        time = 10;
        KnowledgeListMFR.blastChamR = MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(BlockListMFR.BLAST_CHAMBER),
                "blastfurn", false, "hvyHammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier),
                new Object[]{"RP PR", "RP PR", "RP PR", "RP PR", 'R', ComponentListMFR.rivet, 'P', plate,});

        time = 15;
        KnowledgeListMFR.blastHeatR = MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(BlockListMFR.BLAST_HEATER),
                "blastfurn", false, "hvyHammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier), new Object[]{"RP PR", "RP PR", "RP PR", "RPFPR", 'R',
                        ComponentListMFR.rivet, 'P', plate, 'F', Blocks.FURNACE,});

        Salvage.addSalvage(BlockListMFR.BLAST_HEATER, new ItemStack(ComponentListMFR.rivet, 8), plate, plate, plate,
                plate, plate, plate, plate, plate, Blocks.FURNACE);
        Salvage.addSalvage(BlockListMFR.BLAST_CHAMBER, new ItemStack(ComponentListMFR.rivet, 8), plate, plate, plate,
                plate, plate, plate, plate, plate);

        time = 10;
        KnowledgeListMFR.bigFurnR = MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(BlockListMFR.FURNACE_STONE),
                "bigfurn", false, "hammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier), new Object[]{"PRP", "RCR", "PFP", 'F', Blocks.FURNACE, 'R',
                        ComponentListMFR.rivet, 'P', plate, 'C', BlockListMFR.FIREBRICKS});
        time = 10;
        KnowledgeListMFR.bigHeatR = MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(BlockListMFR.FURNACE_HEATER),
                "bigfurn", false, "hammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier), new Object[]{"RCR", "PFP", 'R', ComponentListMFR.rivet, 'P',
                        plate, 'C', BlockListMFR.FIREBRICKS, 'F', BlockListMFR.FORGE,});

        Salvage.addSalvage(BlockListMFR.FURNACE_HEATER, new ItemStack(ComponentListMFR.rivet, 2), plate, plate,
                BlockListMFR.FIREBRICKS, BlockListMFR.FORGE);
        Salvage.addSalvage(BlockListMFR.FURNACE_STONE, new ItemStack(ComponentListMFR.rivet, 3), plate, plate, plate,
                plate, BlockListMFR.FIREBRICKS, Blocks.FURNACE);

        IAnvilRecipe[] anvilRecs = new IAnvilRecipe[BlockListMFR.ANVILS.length];
        for (int id = 0; id < BlockListMFR.ANVILS.length; id++) {
            time = 8;
            material = BaseMaterialMFR.getMaterial(BlockListMFR.ANVILS[id]);
            ItemStack bar = ComponentListMFR.bar(material.name);

            anvilRecs[id] = MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(BlockListMFR.ANVIL[id]),
                    "smelt" + material.name, false, "hammer", -1, -1, (int) (time * material.craftTimeModifier),
                    new Object[]{" II", "III", " I ", 'I', bar,});

            Salvage.addSalvage(BlockListMFR.ANVIL[id], ComponentListMFR.bar(material.name, 6));
        }
        recipeMap.put("anvilCrafting", anvilRecs);

        ItemStack bronzeHunk = ComponentListMFR.metalHunk.createComm("bronze");
        ItemStack ironHunk = ComponentListMFR.metalHunk.createComm("iron");

        time = 2;
        material = BaseMaterialMFR.bronze;
        KnowledgeListMFR.framedStoneR = MineFantasyRebornAPI.addAnvilRecipe(construction,
                new ItemStack(BlockListMFR.REINFORCED_STONE_FRAMED), "decorated_stone", false, "hammer",
                material.hammerTier - 1, material.anvilTier - 1, (int) (time * material.craftTimeModifier),
                new Object[]{" N ", "NSN", " N ", 'N', bronzeHunk, 'S', BlockListMFR.REINFORCED_STONE,});
        Salvage.addSalvage(BlockListMFR.REINFORCED_STONE_FRAMED, bronzeHunk, bronzeHunk, bronzeHunk, bronzeHunk,
                BlockListMFR.REINFORCED_STONE);
        time = 2;
        material = BaseMaterialMFR.iron;
        KnowledgeListMFR.iframedStoneR = MineFantasyRebornAPI.addAnvilRecipe(construction,
                new ItemStack(BlockListMFR.REINFORCED_STONE_FRAMED_IRON), "decorated_stone", false, "hammer",
                material.hammerTier - 1, material.anvilTier - 1, (int) (time * material.craftTimeModifier),
                new Object[]{" N ", "NSN", " N ", 'N', ironHunk, 'S', BlockListMFR.REINFORCED_STONE,});
        Salvage.addSalvage(BlockListMFR.REINFORCED_STONE_FRAMED_IRON, ironHunk, ironHunk, ironHunk, ironHunk,
                BlockListMFR.REINFORCED_STONE);

        time = 2;
        material = BaseMaterialMFR.bronze;
        KnowledgeListMFR.smokePipeR = MineFantasyRebornAPI.addAnvilRecipe(construction,
                new ItemStack(BlockListMFR.CHIMNEY_PIPE, 4), "", false, "hammer", material.hammerTier - 1,
                material.anvilTier - 1, (int) (time * material.craftTimeModifier),
                new Object[]{"N  N", "PPPP", "N  N", 'N', bronzeHunk, 'P', BlockListMFR.CHIMNEY_STONE,});
        Salvage.addSalvage(BlockListMFR.CHIMNEY_PIPE, BlockListMFR.CHIMNEY_STONE, bronzeHunk);

        for (int id = 0; id < BlockListMFR.SPECIAL_METAL_BLOCKS.length; id++) {
            time = 2;
            material = BaseMaterialMFR.getMaterial(BlockListMFR.SPECIAL_METAL_BLOCKS[id]);
            ItemStack hunk = ComponentListMFR.metalHunk.createComm(material.name);
            if (hunk != null) {
                KnowledgeListMFR.barsR
                        .add(MineFantasyRebornAPI.addAnvilRecipe(construction, new ItemStack(BlockListMFR.BARS[id]),
                                "smelt" + material.name, false, "hammer", material.hammerTier, material.anvilTier,
                                (int) (time * material.craftTimeModifier), new Object[]{"I I", "I I", 'I', hunk,}));
                if (hunk.getItem() instanceof ItemComponentMFR) {
                    Salvage.addSalvage(BlockListMFR.BARS[id], hunk, hunk, hunk, hunk);
                }
            }
        }
        if (!ConfigHardcore.HCCRemoveTalismansCraft) {
            KnowledgeListMFR.talismanRecipe.add(MineFantasyRebornAPI.addAnvilRecipe(artisanry,
                    new ItemStack(ComponentListMFR.talisman_lesser), "", true, "hammer", -1, -1, 20, new Object[]{
                            "LGL", "GIG", " G ", 'L', new ItemStack(Items.DYE, 1, 4), 'I', ironbar, 'G', goldbar,}));
            KnowledgeListMFR.talismanRecipe.add(MineFantasyRebornAPI.addAnvilRecipe(artisanry,
                    new ItemStack(ComponentListMFR.talisman_lesser), "", true, "hammer", -1, -1, 20, new Object[]{
                            "LSL", "SIS", " S ", 'L', new ItemStack(Items.DYE, 1, 4), 'I', ironbar, 'S', silverbar,}));
            KnowledgeListMFR.greatTalismanRecipe = MineFantasyRebornAPI.addAnvilRecipe(artisanry,
                    new ItemStack(ComponentListMFR.talisman_greater), "", true, "hammer", 1, 1, 50,
                    new Object[]{"GSG", "DTD", "GDG", 'G', goldbar, 'D', Items.DIAMOND, 'T',
                            ComponentListMFR.talisman_lesser, 'S', Items.NETHER_STAR,});
        }

        addEngineering();
        addConstruction();

        time = 10;
        material = BaseMaterialMFR.iron;
        KnowledgeListMFR.caketinRecipe = MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(FoodListMFR.cake_tin), "",
                true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{" R ", "I I", " I ", 'I', ironbar, 'R', ComponentListMFR.rivet,});
        Salvage.addSalvage(FoodListMFR.cake_tin, ComponentListMFR.bar("Iron", 3), ComponentListMFR.rivet);

        KnowledgeListMFR.coalfluxR = MineFantasyRebornAPI.addAnvilRecipe(artisanry,
                new ItemStack(ComponentListMFR.coal_flux, 2), "coalflux", false, material.hammerTier, material.anvilTier,
                2, new Object[]{"F", "C", 'C', Items.COAL, 'F', ComponentListMFR.flux_pot,});

        time = 4;
        material = BaseMaterialMFR.iron;
        KnowledgeListMFR.hingeRecipe = MineFantasyRebornAPI.addAnvilRecipe(construction, new ItemStack(ComponentListMFR.hinge),
                "", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{"LR", 'L', ComponentListMFR.leather_strip, 'R', ComponentListMFR.rivet,});
        Salvage.addSalvage(ComponentListMFR.hinge, ComponentListMFR.leather_strip, ComponentListMFR.rivet);

        time = 10;
        KnowledgeListMFR.crestR = MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMFR.ornate_items),
                "craftOrnate", false, "hammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier),
                new Object[]{" G ", "SLS", " G ", 'G', goldbar, 'S', silverbar, 'L', new ItemStack(Items.DYE, 1, 4)

                });
    }

    private static Item getStrips(BaseMaterialMFR material) {
        return ComponentListMFR.leather_strip;
    }

    private static Item getLeather(BaseMaterialMFR material) {
        return Items.LEATHER;
    }

    private static void addEngineering() {
        ItemStack copper = ComponentListMFR.bar("Copper");
        ItemStack bronze = ComponentListMFR.bar("Bronze");
        ItemStack iron = ComponentListMFR.bar("Iron");
        ItemStack steel = ComponentListMFR.bar("Steel");
        ItemStack tungsten = ComponentListMFR.bar("Tungsten");
        ItemStack blacksteel = ComponentListMFR.bar("BlackSteel");

        ItemStack bronzeHunk = ComponentListMFR.metalHunk.createComm("bronze");
        ItemStack ironHunk = ComponentListMFR.metalHunk.createComm("iron");
        ItemStack steelHunk = ComponentListMFR.metalHunk.createComm("steel");
        ItemStack tungstenHunk = ComponentListMFR.metalHunk.createComm("tungsten");
        ItemStack obsidianHunk = ComponentListMFR.metalHunk.createComm("obsidian");

        BaseMaterialMFR material = BaseMaterialMFR.steel;
        int time = 15;
        KnowledgeListMFR.eatoolsR = MineFantasyRebornAPI.addAnvilRecipe(engineering,
                new ItemStack(ToolListMFR.engin_anvil_tools), "etools", true, "hammer", material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{"SSLL", "LLSS", 'S', steelHunk, 'L', getStrips(material),});
        time = 5;
        KnowledgeListMFR.iframeR = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMFR.iron_frame),
                "ecomponents", true, "hammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier), new Object[]{"RRR", "ISI", "STS", "ISI", 'T',
                        ToolListMFR.engin_anvil_tools, 'R', ComponentListMFR.rivet, 'I', ironHunk, 'S', steelHunk,});
        time = 8;
        KnowledgeListMFR.istrutR = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMFR.iron_strut),
                "ecomponents", true, "hvyhammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier), new Object[]{"RTR", "SIS", "SIS", 'T',
                        ToolListMFR.engin_anvil_tools, 'R', ComponentListMFR.rivet, 'I', iron, 'S', steelHunk,});
        time = 8;
        KnowledgeListMFR.stubeR = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMFR.steel_tube),
                "ecomponents", true, "hammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier), new Object[]{"TR R", "SSSS", 'T',
                        ToolListMFR.engin_anvil_tools, 'R', ComponentListMFR.rivet, 'S', steelHunk,});
        time = 2;
        KnowledgeListMFR.boltR = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMFR.bolt, 2),
                "etools", true, "hammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier), new Object[]{" T ", "SIS", " S ", " S ", 'T',
                        ToolListMFR.engin_anvil_tools, 'I', iron, 'S', steelHunk,});
        time = 35;
        KnowledgeListMFR.climbPickbR = MineFantasyRebornAPI.addAnvilRecipe(engineering,
                new ItemStack(ToolListMFR.climbing_pick_basic), "climber", true, "hammer", material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{"L SR", "IISR", "L T ", 'R', ComponentListMFR.rivet, 'T', ToolListMFR.engin_anvil_tools,
                        'I', iron, 'S', steel, 'L', getStrips(material),});
        time = 5;
        KnowledgeListMFR.bgearR = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMFR.bronze_gears),
                "ecomponents", true, "hammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier), new Object[]{" T ", " B ", "BIB", " B ", 'T',
                        ToolListMFR.engin_anvil_tools, 'I', iron, 'B', bronzeHunk,});
        time = 8;
        material = BaseMaterialMFR.tungsten;
        KnowledgeListMFR.tgearR = MineFantasyRebornAPI.addAnvilRecipe(engineering,
                new ItemStack(ComponentListMFR.tungsten_gears), "tungsten", true, "hammer", material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{" T ", " W ", "WGW", " W ", 'T', ToolListMFR.engin_anvil_tools, 'W', tungstenHunk, 'G',
                        ComponentListMFR.bronze_gears,});
        time = 5;
        material = BaseMaterialMFR.compositeAlloy;
        KnowledgeListMFR.compPlateR = MineFantasyRebornAPI.addAnvilRecipe(engineering, ComponentListMFR.bar("CompositeAlloy"),
                "cogArmour", true, "hvyhammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier), new Object[]{" T ", " S ", "RWR", " C ",

                        'R', ComponentListMFR.rivet, 'T', ToolListMFR.engin_anvil_tools, 'C', copper, 'W', tungstenHunk,
                        'S', steel,});
        material = BaseMaterialMFR.iron;

        time = 5;
        KnowledgeListMFR.bombCaseIronR = MineFantasyRebornAPI.addAnvilRecipe(engineering,
                new ItemStack(ComponentListMFR.bomb_casing_iron, 2), "bombIron", true, material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier), new Object[]{" T ", " I ", "IRI",
                        " I ", 'T', ToolListMFR.engin_anvil_tools, 'I', ironHunk, 'R', ComponentListMFR.rivet,});
        KnowledgeListMFR.mineCaseIronR = MineFantasyRebornAPI.addAnvilRecipe(engineering,
                new ItemStack(ComponentListMFR.mine_casing_iron, 2), "bombIron", true, material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{"  T  ", "  P  ", " IRI ", "IR RI", 'T', ToolListMFR.engin_anvil_tools, 'P',
                        Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 'I', ironHunk, 'R', ComponentListMFR.rivet,});

        time = 5;
        KnowledgeListMFR.bombarrowR = MineFantasyRebornAPI.addAnvilRecipe(engineering,
                new ItemStack(ComponentListMFR.bomb_casing_arrow), "bombarrow", true, material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{"   IR", "FPITI", "   IR", 'T', ToolListMFR.engin_anvil_tools, 'I', ironHunk, 'R',
                        Items.REDSTONE, 'P', ComponentListMFR.plank.construct("RefinedWood"), 'F',
                        ComponentListMFR.fletching,});
        KnowledgeListMFR.bombBoltR = MineFantasyRebornAPI.addAnvilRecipe(engineering,
                new ItemStack(ComponentListMFR.bomb_casing_bolt), "bombarrow", true, material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{"  IR", "FITI", "  IR", 'T', ToolListMFR.engin_anvil_tools, 'I', ironHunk, 'R',
                        Items.REDSTONE, 'F', ComponentListMFR.fletching,});

        material = BaseMaterialMFR.blacksteel;

        time = 5;
        KnowledgeListMFR.bombCaseObsidianR = MineFantasyRebornAPI.addAnvilRecipe(engineering,
                new ItemStack(ComponentListMFR.bomb_casing_obsidian, 2), "bombObsidian", true, material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{" T ", "RIR", "IOI", "RIR", 'T', ToolListMFR.engin_anvil_tools, 'O', Blocks.OBSIDIAN, 'I',
                        obsidianHunk, 'R', ComponentListMFR.rivet,});
        KnowledgeListMFR.mineCaseObsidianR = MineFantasyRebornAPI.addAnvilRecipe(engineering,
                new ItemStack(ComponentListMFR.mine_casing_obsidian, 2), "mineObsidian", true, material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier),
                new Object[]{"  T  ", "  P  ", " IRI ", "IRORI", 'T', ToolListMFR.engin_anvil_tools, 'O',
                        Blocks.OBSIDIAN, 'P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 'I', obsidianHunk, 'R',
                        ComponentListMFR.rivet,});
        time = 15;
        material = BaseMaterialMFR.steel;
        KnowledgeListMFR.crossBayonetR = MineFantasyRebornAPI.addAnvilRecipe(engineering,
                new ItemStack(ComponentListMFR.cross_bayonet), "crossBayonet", true, material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier), new Object[]{"R R I", "PIII ", 'P',
                        ComponentListMFR.plank.construct("RefinedWood"), 'I', ironHunk, 'R', ComponentListMFR.rivet,});

        Salvage.addSalvage(ToolListMFR.engin_anvil_tools, steelHunk, steelHunk, steelHunk, steelHunk,
                new ItemStack(ComponentListMFR.leather_strip, 4));

        Salvage.addSalvage(ComponentListMFR.iron_frame, steelHunk, steelHunk, steelHunk, steelHunk, ironHunk, ironHunk,
                ironHunk, ironHunk, new ItemStack(ComponentListMFR.rivet, 3));

        Salvage.addSalvage(ComponentListMFR.iron_strut, steelHunk, steelHunk, steelHunk, steelHunk,
                ComponentListMFR.bar("Iron", 2), new ItemStack(ComponentListMFR.rivet, 2));

        Salvage.addSalvage(ComponentListMFR.steel_tube, steelHunk, steelHunk, steelHunk, steelHunk,
                new ItemStack(ComponentListMFR.rivet, 2));

        Salvage.addSalvage(ToolListMFR.climbing_pick_basic, ComponentListMFR.bar("Iron", 2),
                ComponentListMFR.bar("Steel", 2), new ItemStack(ComponentListMFR.rivet, 2),
                new ItemStack(ComponentListMFR.leather_strip, 2));

        Salvage.addSalvage(ComponentListMFR.bronze_gears, bronzeHunk, bronzeHunk, bronzeHunk, bronzeHunk, iron);

        Salvage.addSalvage(ComponentListMFR.tungsten_gears, tungstenHunk, tungstenHunk, tungstenHunk, tungstenHunk,
                ComponentListMFR.bronze_gears);

        Salvage.addSalvage(ComponentListMFR.bar("CompositeAlloy"), copper, steel, tungstenHunk,
                new ItemStack(ComponentListMFR.rivet, 2));
        Salvage.addSalvage(ComponentListMFR.ingotCompositeAlloy, copper, steel, tungstenHunk,
                new ItemStack(ComponentListMFR.rivet, 2));

        Salvage.addSalvage(ComponentListMFR.bomb_casing_iron, ironHunk, ironHunk);
        Salvage.addSalvage(ComponentListMFR.mine_casing_iron, ironHunk, ironHunk, ComponentListMFR.rivet, iron);
        Salvage.addSalvage(ComponentListMFR.bomb_casing_arrow, ironHunk, ironHunk, ironHunk, ironHunk,
                new ItemStack(Items.REDSTONE, 2), ComponentListMFR.plank.construct("RefinedWood"),
                ComponentListMFR.fletching);

        Salvage.addSalvage(ComponentListMFR.bomb_casing_bolt, ironHunk, ironHunk, ironHunk, ironHunk,
                new ItemStack(Items.REDSTONE, 2), ComponentListMFR.fletching);

        Salvage.addSalvage(ComponentListMFR.bomb_casing_obsidian, obsidianHunk, obsidianHunk,
                new ItemStack(ComponentListMFR.rivet, 2));
        Salvage.addSalvage(ComponentListMFR.mine_casing_obsidian, obsidianHunk, obsidianHunk, iron,
                ComponentListMFR.rivet);

        Salvage.addSalvage(ComponentListMFR.cross_bayonet, ironHunk, ironHunk, ironHunk, ironHunk,
                ComponentListMFR.plank.construct("RefinedWood"), new ItemStack(ComponentListMFR.rivet, 2));
    }

    private static void addConstruction() {
        BaseMaterialMFR material = BaseMaterialMFR.tin;
        ItemStack tin = ComponentListMFR.bar("Tin");
        int time = 10;
        KnowledgeListMFR.brushRecipe = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(ToolListMFR.paint_brush),
                "paint_brush", true, "hammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier), new Object[]{"W", "I", "P",

                        'W', Blocks.WOOL, 'I', tin, 'P', ComponentListMFR.plank.construct("RefinedWood"),});

        Salvage.addSalvage(ToolListMFR.paint_brush, Blocks.WOOL, tin, ComponentListMFR.plank.construct("RefinedWood"));
    }

    private static void addCogworkParts() {
        BaseMaterialMFR material = BaseMaterialMFR.steel;
        int time = 1;
        KnowledgeListMFR.frameBlockR = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(BlockListMFR.FRAME_BLOCK),
                "cogArmour", false, "hammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier), new Object[]{"R", "I",

                        'I', ComponentListMFR.iron_frame, 'R', ComponentListMFR.rivet,});

        Salvage.addSalvage(BlockListMFR.FRAME_BLOCK, ComponentListMFR.iron_frame, ComponentListMFR.rivet);

        time = 10;
        KnowledgeListMFR.cogPulleyR = MineFantasyRebornAPI.addAnvilRecipe(engineering,
                new ItemStack(ComponentListMFR.cogwork_pulley), "cogArmour", false, "hammer", material.hammerTier,
                material.anvilTier, (int) (time * material.craftTimeModifier), new Object[]{"RFR", "GBG", "RFR",

                        'B', Blocks.REDSTONE_BLOCK, 'F', ComponentListMFR.iron_frame, 'R', ComponentListMFR.rivet, 'G',
                        ComponentListMFR.tungsten_gears,});
        Salvage.addSalvage(ComponentListMFR.cogwork_pulley, Blocks.REDSTONE_BLOCK,
                new ItemStack(ComponentListMFR.iron_frame, 2), new ItemStack(ComponentListMFR.rivet, 2),
                new ItemStack(ComponentListMFR.tungsten_gears, 2));
        Salvage.addSalvage(BlockListMFR.FRAME_BLOCK, ComponentListMFR.iron_frame, ComponentListMFR.rivet);

        time = 10;
        KnowledgeListMFR.cogHelmR = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(BlockListMFR.COGWORK_HELM),
                "cogArmour", false, "hammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier), new Object[]{"RFFR", "SEES", " RR ",

                        'E', Items.ENDER_EYE, 'F', ComponentListMFR.iron_frame, 'R', ComponentListMFR.rivet, 'S',
                        ComponentListMFR.cogwork_shaft,});
        Salvage.addSalvage(BlockListMFR.COGWORK_HELM, new ItemStack(Items.ENDER_EYE, 2),
                new ItemStack(ComponentListMFR.iron_frame, 2), new ItemStack(ComponentListMFR.cogwork_shaft, 2),
                new ItemStack(ComponentListMFR.rivet, 4));
        time = 15;
        KnowledgeListMFR.cogChestR = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(BlockListMFR.COGWORK_CHEST),
                "cogArmour", false, "hammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier), new Object[]{" RFR ", "RSFSR", "RFBFR", " SFS ",

                        'F', ComponentListMFR.iron_frame, 'R', ComponentListMFR.rivet, 'S', ComponentListMFR.cogwork_shaft,
                        'B', Blocks.FURNACE,});
        Salvage.addSalvage(BlockListMFR.COGWORK_CHEST, Blocks.FURNACE, new ItemStack(ComponentListMFR.iron_frame, 5),
                new ItemStack(ComponentListMFR.cogwork_shaft, 4), new ItemStack(ComponentListMFR.rivet, 6));

        time = 10;
        KnowledgeListMFR.cogLegsR = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(BlockListMFR.COGWORK_LEGS),
                "cogArmour", false, "hammer", material.hammerTier, material.anvilTier,
                (int) (time * material.craftTimeModifier), new Object[]{"RFFFR", "RS SR", " S S ", " F F ",

                        'F', ComponentListMFR.iron_frame, 'R', ComponentListMFR.rivet, 'S',
                        ComponentListMFR.cogwork_shaft,});
        Salvage.addSalvage(BlockListMFR.COGWORK_LEGS, new ItemStack(ComponentListMFR.iron_frame, 5),
                new ItemStack(ComponentListMFR.cogwork_shaft, 4), new ItemStack(ComponentListMFR.rivet, 4));

    }
}
