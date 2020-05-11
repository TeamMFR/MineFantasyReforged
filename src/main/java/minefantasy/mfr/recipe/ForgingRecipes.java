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
import minefantasy.mfr.init.FoodListMFR;
import minefantasy.mfr.init.ArmourListMFR;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.ToolListMFR;
import minefantasy.mfr.init.KnowledgeListMFR;
import minefantasy.mfr.material.BaseMaterialMFR;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class ForgingRecipes {
    public static final HashMap<String, IAnvilRecipe> recipeMap = new HashMap<String, IAnvilRecipe>();
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

        KnowledgeListMFR.obsidianHunkR = MineFantasyRebornAPI.addAnvilRecipe(null, new ItemStack(ComponentListMFR.OBSIDIAN_ROCK, 4), "", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "D", 'D', Blocks.OBSIDIAN);
        KnowledgeListMFR.diamondR = MineFantasyRebornAPI.addAnvilRecipe(null, new ItemStack(ComponentListMFR.DIAMOND_SHARDS), "", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "D", 'D', Items.DIAMOND);

        time = 3;
        KnowledgeListMFR.encrustedR = MineFantasyRebornAPI.addAnvilRecipe(artisanry, ComponentListMFR.bar("Encrusted"), "smeltEncrusted", true, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "D", "I", 'D', ComponentListMFR.DIAMOND_SHARDS, 'I', ComponentListMFR.bar("Steel"));
        Salvage.addSalvage(ComponentListMFR.ENCRUSTED_INGOT, ComponentListMFR.STEEL_INGOT, ComponentListMFR.DIAMOND_SHARDS);
        Salvage.addSalvage(ComponentListMFR.bar("Encrusted"), ComponentListMFR.STEEL_INGOT,
                ComponentListMFR.DIAMOND_SHARDS);

        material = BaseMaterialMFR.pigiron;
        KnowledgeListMFR.steelR = MineFantasyRebornAPI.addAnvilRecipe(artisanry, ComponentListMFR.bar("Steel"), "smeltSteel", true, 1, 1, 5, "C", "H", 'C', ComponentListMFR.COAL_DUST, 'H', ComponentListMFR.bar("PigIron"));
        KnowledgeListMFR.fluxR = MineFantasyRebornAPI.addAnvilRecipe(null, new ItemStack(ComponentListMFR.FLUX, 4), "", false, -1, -1, 2, "H", 'H', BlockListMFR.LIMESTONE);

        // STUDDED
        material = BaseMaterialMFR.iron;
        // HELMET
        time = 10;
        KnowledgeListMFR.studHelmetR = MineFantasyRebornAPI.addAnvilRecipe(artisanry, ArmourListMFR.armour(ArmourListMFR.LEATHER, 3, 0), "craftArmourLight", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " I ", "IAI", " I ", 'I', ComponentListMFR.RIVET, 'A', ArmourListMFR.armourItem(ArmourListMFR.LEATHER, 2, 0));
        // CHEST
        time = 20;
        KnowledgeListMFR.studChestR = MineFantasyRebornAPI.addAnvilRecipe(artisanry, ArmourListMFR.armour(ArmourListMFR.LEATHER, 3, 1), "craftArmourLight", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " I ", "IAI", " I ", 'I', ComponentListMFR.RIVET, 'A', ArmourListMFR.armourItem(ArmourListMFR.LEATHER, 2, 1));
        // LEGS
        time = 15;
        KnowledgeListMFR.studLegsR = MineFantasyRebornAPI.addAnvilRecipe(artisanry, ArmourListMFR.armour(ArmourListMFR.LEATHER, 3, 2), "craftArmourLight", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " I ", "IAI", " I ", 'I', ComponentListMFR.RIVET, 'A', ArmourListMFR.armourItem(ArmourListMFR.LEATHER, 2, 2));
        // BOOTS
        time = 6;
        KnowledgeListMFR.studBootsR = MineFantasyRebornAPI.addAnvilRecipe(artisanry, ArmourListMFR.armour(ArmourListMFR.LEATHER, 3, 3), "craftArmourLight", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " I ", "IAI", " I ", 'I', ComponentListMFR.RIVET, 'A', ArmourListMFR.armourItem(ArmourListMFR.LEATHER, 2, 3));

        Salvage.addSalvage(ArmourListMFR.armourItem(ArmourListMFR.LEATHER, 3, 0), ArmourListMFR.armour(ArmourListMFR.LEATHER, 2, 0), new ItemStack(ComponentListMFR.RIVET, 4));
        Salvage.addSalvage(ArmourListMFR.armourItem(ArmourListMFR.LEATHER, 3, 1), ArmourListMFR.armour(ArmourListMFR.LEATHER, 2, 1), new ItemStack(ComponentListMFR.RIVET, 4));
        Salvage.addSalvage(ArmourListMFR.armourItem(ArmourListMFR.LEATHER, 3, 2), ArmourListMFR.armour(ArmourListMFR.LEATHER, 2, 2), new ItemStack(ComponentListMFR.RIVET, 4));
        Salvage.addSalvage(ArmourListMFR.armourItem(ArmourListMFR.LEATHER, 3, 3), ArmourListMFR.armour(ArmourListMFR.LEATHER, 2, 3), new ItemStack(ComponentListMFR.RIVET, 4));

        time = 2;
        material = BaseMaterialMFR.iron;
        if (ConfigCrafting.allowIronResmelt) {
            MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMFR.IRON_PREP), "blastfurn", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "IFI", 'I', ComponentListMFR.bar("iron"), 'F', ComponentListMFR.FLUX);
            MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMFR.IRON_PREP, 2), "blastfurn", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "IFI", 'I', ComponentListMFR.bar("iron"), 'F', ComponentListMFR.FLUX_STRONG);
        }
        KnowledgeListMFR.coalPrepR = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMFR.COAL_PREP), "coke", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "RCR", "CFC", "RCR", 'R', Items.REDSTONE, 'C', new ItemStack(Items.COAL, 1, 1), 'F', ComponentListMFR.FLUX_STRONG);
        GameRegistry.addSmelting(ComponentListMFR.COAL_PREP, new ItemStack(ComponentListMFR.COKE), 1F);

        KnowledgeListMFR.ironPrepR = MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMFR.IRON_PREP), "blastfurn", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "IFI", 'I', Blocks.IRON_ORE, 'F', ComponentListMFR.FLUX);
        KnowledgeListMFR.ironPrepR2 = MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMFR.IRON_PREP, 2), "blastfurn", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "IFI", 'I', Blocks.IRON_ORE, 'F', ComponentListMFR.FLUX_STRONG);

        MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMFR.IRON_PREP), "blastfurn", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "IFI", 'I', ComponentListMFR.ORE_IRON, 'F', ComponentListMFR.FLUX);
        MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMFR.IRON_PREP, 2), "blastfurn", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "IFI", 'I', ComponentListMFR.ORE_IRON, 'F', ComponentListMFR.FLUX_STRONG);
        ItemStack plate = ComponentListMFR.PLATE.createComm("iron");
        time = 10;
        KnowledgeListMFR.blastChamR = MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(BlockListMFR.BLAST_CHAMBER), "blastfurn", false, "hvyHammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "RP PR", "RP PR", "RP PR", "RP PR", 'R', ComponentListMFR.RIVET, 'P', plate);

        time = 15;
        KnowledgeListMFR.blastHeatR = MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(BlockListMFR.BLAST_HEATER), "blastfurn", false, "hvyHammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "RP PR", "RP PR", "RP PR", "RPFPR", 'R', ComponentListMFR.RIVET, 'P', plate, 'F', Blocks.FURNACE);

        Salvage.addSalvage(BlockListMFR.BLAST_HEATER, new ItemStack(ComponentListMFR.RIVET, 8), plate, plate, plate,
                plate, plate, plate, plate, plate, Blocks.FURNACE);
        Salvage.addSalvage(BlockListMFR.BLAST_CHAMBER, new ItemStack(ComponentListMFR.RIVET, 8), plate, plate, plate,
                plate, plate, plate, plate, plate);

        time = 10;
        KnowledgeListMFR.bigFurnR = MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(BlockListMFR.FURNACE_STONE), "bigfurn", false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "PRP", "RCR", "PFP", 'F', Blocks.FURNACE, 'R', ComponentListMFR.RIVET, 'P', plate, 'C', BlockListMFR.FIREBRICKS);
        time = 10;
        KnowledgeListMFR.bigHeatR = MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(BlockListMFR.FURNACE_HEATER), "bigfurn", false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "RCR", "PFP", 'R', ComponentListMFR.RIVET, 'P', plate, 'C', BlockListMFR.FIREBRICKS, 'F', BlockListMFR.FORGE);

        Salvage.addSalvage(BlockListMFR.FURNACE_HEATER, new ItemStack(ComponentListMFR.RIVET, 2), plate, plate, BlockListMFR.FIREBRICKS, BlockListMFR.FORGE);
        Salvage.addSalvage(BlockListMFR.FURNACE_STONE, new ItemStack(ComponentListMFR.RIVET, 3), plate, plate, plate, plate, BlockListMFR.FIREBRICKS, Blocks.FURNACE);

        material = BlockListMFR.ANVIL_BRONZE.material;
        IAnvilRecipe bronze_anvil_recipe = MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(BlockListMFR.ANVIL_BRONZE), "smelt" + material.name, false, "hammer", -1, -1, (int) (time * material.craftTimeModifier), " II", "III", " I ", 'I', ComponentListMFR.bar(material.name));
        Salvage.addSalvage(BlockListMFR.ANVIL_BRONZE, ComponentListMFR.bar(material.name, 6));

        material = BlockListMFR.ANVIL_IRON.material;
        IAnvilRecipe iron_anvil_recipe = MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(BlockListMFR.ANVIL_IRON), "smelt" + material.name, false, "hammer", -1, -1, (int) (time * material.craftTimeModifier), " II", "III", " I ", 'I', ComponentListMFR.bar(material.name));
        Salvage.addSalvage(BlockListMFR.ANVIL_IRON, ComponentListMFR.bar(material.name, 6));

        material = BlockListMFR.ANVIL_STEEL.material;
        IAnvilRecipe steel_anvil_recipe = MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(BlockListMFR.ANVIL_STEEL), "smelt" + material.name, false, "hammer", -1, -1, (int) (time * material.craftTimeModifier), " II", "III", " I ", 'I', ComponentListMFR.bar(material.name));
        Salvage.addSalvage(BlockListMFR.ANVIL_STEEL, ComponentListMFR.bar(material.name, 6));

        material = BlockListMFR.ANVIL_BLACK_STEEL.material;
        IAnvilRecipe black_steel_anvil_recipe = MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(BlockListMFR.ANVIL_BLACK_STEEL), "smelt" + material.name, false, "hammer", -1, -1, (int) (time * material.craftTimeModifier), " II", "III", " I ", 'I', ComponentListMFR.bar(material.name));
        Salvage.addSalvage(BlockListMFR.ANVIL_BLACK_STEEL, ComponentListMFR.bar(material.name, 6));

        material = BlockListMFR.ANVIL_BLUE_STEEL.material;
        IAnvilRecipe blue_steel_anvil_recipe = MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(BlockListMFR.ANVIL_BLUE_STEEL), "smelt" + material.name, false, "hammer", -1, -1, (int) (time * material.craftTimeModifier), " II", "III", " I ", 'I', ComponentListMFR.bar(material.name));
        Salvage.addSalvage(BlockListMFR.ANVIL_BLUE_STEEL, ComponentListMFR.bar(material.name, 6));

        material = BlockListMFR.ANVIL_RED_STEEL.material;
        IAnvilRecipe red_steel_recipe = MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(BlockListMFR.ANVIL_RED_STEEL), "smelt" + material.name, false, "hammer", -1, -1, (int) (time * material.craftTimeModifier), " II", "III", " I ", 'I', ComponentListMFR.bar(material.name));
        Salvage.addSalvage(BlockListMFR.ANVIL_RED_STEEL, ComponentListMFR.bar(material.name, 6));

        recipeMap.put("anvilBronze", bronze_anvil_recipe);
        recipeMap.put("anvilIron", iron_anvil_recipe);
        recipeMap.put("anvilSteel", steel_anvil_recipe);
        recipeMap.put("anvilBlackSteel", black_steel_anvil_recipe);
        recipeMap.put("anvilBlueSteel", blue_steel_anvil_recipe);
        recipeMap.put("anvilRedSteel", red_steel_recipe);


        ItemStack bronzeHunk = ComponentListMFR.METAL_HUNK.createComm("bronze");
        ItemStack ironHunk = ComponentListMFR.METAL_HUNK.createComm("iron");

        time = 2;
        material = BaseMaterialMFR.bronze;
        KnowledgeListMFR.framedStoneR = MineFantasyRebornAPI.addAnvilRecipe(construction, new ItemStack(BlockListMFR.REINFORCED_STONE_FRAMED), "decorated_stone", false, "hammer", material.hammerTier - 1, material.anvilTier - 1, (int) (time * material.craftTimeModifier), " N ", "NSN", " N ", 'N', bronzeHunk, 'S', BlockListMFR.REINFORCED_STONE);
        Salvage.addSalvage(BlockListMFR.REINFORCED_STONE_FRAMED, bronzeHunk, bronzeHunk, bronzeHunk, bronzeHunk,
                BlockListMFR.REINFORCED_STONE);
        time = 2;
        material = BaseMaterialMFR.iron;
        KnowledgeListMFR.iframedStoneR = MineFantasyRebornAPI.addAnvilRecipe(construction, new ItemStack(BlockListMFR.REINFORCED_STONE_FRAMED_IRON), "decorated_stone", false, "hammer", material.hammerTier - 1, material.anvilTier - 1, (int) (time * material.craftTimeModifier), " N ", "NSN", " N ", 'N', ironHunk, 'S', BlockListMFR.REINFORCED_STONE);
        Salvage.addSalvage(BlockListMFR.REINFORCED_STONE_FRAMED_IRON, ironHunk, ironHunk, ironHunk, ironHunk,
                BlockListMFR.REINFORCED_STONE);

        time = 2;
        material = BaseMaterialMFR.bronze;
        KnowledgeListMFR.smokePipeR = MineFantasyRebornAPI.addAnvilRecipe(construction, new ItemStack(BlockListMFR.CHIMNEY_PIPE, 4), "", false, "hammer", material.hammerTier - 1, material.anvilTier - 1, (int) (time * material.craftTimeModifier), "N  N", "PPPP", "N  N", 'N', bronzeHunk, 'P', BlockListMFR.CHIMNEY_STONE);
        Salvage.addSalvage(BlockListMFR.CHIMNEY_PIPE, BlockListMFR.CHIMNEY_STONE, bronzeHunk);

        material = BlockListMFR.BRONZE_BARS.material;
        KnowledgeListMFR.barsR.add(MineFantasyRebornAPI.addAnvilRecipe(construction, new ItemStack(BlockListMFR.BRONZE_BARS), "smelt" + material.name, false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "I I", "I I", 'I', bronzeHunk));
        Salvage.addSalvage(BlockListMFR.BRONZE_BARS, bronzeHunk, bronzeHunk, bronzeHunk, bronzeHunk);

        material = BlockListMFR.IRON_BARS.material;
        KnowledgeListMFR.barsR.add(MineFantasyRebornAPI.addAnvilRecipe(construction, new ItemStack(BlockListMFR.IRON_BARS), "smelt" + material.name, false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "I I", "I I", 'I', ironHunk));
        Salvage.addSalvage(BlockListMFR.IRON_BARS, ironHunk, ironHunk, ironHunk, ironHunk);

        material = BlockListMFR.STEEL_BARS.material;
        ItemStack steelHunk = ComponentListMFR.METAL_HUNK.createComm(material.name);
        KnowledgeListMFR.barsR.add(MineFantasyRebornAPI.addAnvilRecipe(construction, new ItemStack(BlockListMFR.STEEL_BARS), "smelt" + material.name, false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "I I", "I I", 'I', steelHunk));
        Salvage.addSalvage(BlockListMFR.STEEL_BARS, steelHunk, steelHunk, steelHunk, steelHunk);

        material = BlockListMFR.BLACK_STEEL_BARS.material;
        ItemStack blackSteelHunk = ComponentListMFR.METAL_HUNK.createComm(material.name);
        KnowledgeListMFR.barsR.add(MineFantasyRebornAPI.addAnvilRecipe(construction, new ItemStack(BlockListMFR.BLACK_STEEL_BARS), "smelt" + material.name, false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "I I", "I I", 'I', blackSteelHunk));
        Salvage.addSalvage(BlockListMFR.BLACK_STEEL_BARS, blackSteelHunk, blackSteelHunk, blackSteelHunk, blackSteelHunk);

        material = BlockListMFR.BLUE_STEEL_BARS.material;
        ItemStack blueSteelHunk = ComponentListMFR.METAL_HUNK.createComm(material.name);
        KnowledgeListMFR.barsR.add(MineFantasyRebornAPI.addAnvilRecipe(construction, new ItemStack(BlockListMFR.BLUE_STEEL_BARS), "smelt" + material.name, false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "I I", "I I", 'I', blueSteelHunk));
        Salvage.addSalvage(BlockListMFR.BLUE_STEEL_BARS, blueSteelHunk, blueSteelHunk, blueSteelHunk, blueSteelHunk);

        material = BlockListMFR.RED_STEEL_BARS.material;
        ItemStack redSteelHunk = ComponentListMFR.METAL_HUNK.createComm(material.name);
        KnowledgeListMFR.barsR.add(MineFantasyRebornAPI.addAnvilRecipe(construction, new ItemStack(BlockListMFR.RED_STEEL_BARS), "smelt" + material.name, false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "I I", "I I", 'I', redSteelHunk));
        Salvage.addSalvage(BlockListMFR.RED_STEEL_BARS, redSteelHunk, redSteelHunk, redSteelHunk, redSteelHunk);


        if (!ConfigHardcore.HCCRemoveTalismansCraft) {
            KnowledgeListMFR.talismanRecipe.add(MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMFR.TALISMAN_LESSER), "", true, "hammer", -1, -1, 20, "LGL", "GIG", " G ", 'L', new ItemStack(Items.DYE, 1, 4), 'I', ironbar, 'G', goldbar));
            KnowledgeListMFR.talismanRecipe.add(MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMFR.TALISMAN_LESSER), "", true, "hammer", -1, -1, 20, "LSL", "SIS", " S ", 'L', new ItemStack(Items.DYE, 1, 4), 'I', ironbar, 'S', silverbar));
            KnowledgeListMFR.greatTalismanRecipe = MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMFR.TALISMAN_GREATER), "", true, "hammer", 1, 1, 50, "GSG", "DTD", "GDG", 'G', goldbar, 'D', Items.DIAMOND, 'T', ComponentListMFR.TALISMAN_LESSER, 'S', Items.NETHER_STAR);
        }

        addEngineering();
        addConstruction();

        time = 10;
        material = BaseMaterialMFR.iron;
        KnowledgeListMFR.caketinRecipe = MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(FoodListMFR.CAKE_TIN), "", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " R ", "I I", " I ", 'I', ironbar, 'R', ComponentListMFR.RIVET);
        Salvage.addSalvage(FoodListMFR.CAKE_TIN, ComponentListMFR.bar("Iron", 3), ComponentListMFR.RIVET);

        KnowledgeListMFR.coalfluxR = MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMFR.COAL_FLUX, 2), "coalflux", false, material.hammerTier, material.anvilTier, 2, "F", "C", 'C', Items.COAL, 'F', ComponentListMFR.FLUX_POT);

        time = 4;
        material = BaseMaterialMFR.iron;
        KnowledgeListMFR.hingeRecipe = MineFantasyRebornAPI.addAnvilRecipe(construction, new ItemStack(ComponentListMFR.HINGE), "", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "LR", 'L', ComponentListMFR.LEATHER_STRIP, 'R', ComponentListMFR.RIVET);
        Salvage.addSalvage(ComponentListMFR.HINGE, ComponentListMFR.LEATHER_STRIP, ComponentListMFR.RIVET);

        time = 10;
        KnowledgeListMFR.crestR = MineFantasyRebornAPI.addAnvilRecipe(artisanry, new ItemStack(ComponentListMFR.ORNATE_ITEMS), "craftOrnate", false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " G ", "SLS", " G ", 'G', goldbar, 'S', silverbar, 'L', new ItemStack(Items.DYE, 1, 4));
    }

    private static Item getStrips(BaseMaterialMFR material) {
        return ComponentListMFR.LEATHER_STRIP;
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

        ItemStack bronzeHunk = ComponentListMFR.METAL_HUNK.createComm("bronze");
        ItemStack ironHunk = ComponentListMFR.METAL_HUNK.createComm("iron");
        ItemStack steelHunk = ComponentListMFR.METAL_HUNK.createComm("steel");
        ItemStack tungstenHunk = ComponentListMFR.METAL_HUNK.createComm("tungsten");
        ItemStack obsidianHunk = ComponentListMFR.METAL_HUNK.createComm("obsidian");

        BaseMaterialMFR material = BaseMaterialMFR.steel;
        int time = 15;
        KnowledgeListMFR.eatoolsR = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(ToolListMFR.ENGIN_ANVIL_TOOLS), "etools", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "SSLL", "LLSS", 'S', steelHunk, 'L', getStrips(material));
        time = 5;
        KnowledgeListMFR.iframeR = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMFR.IRON_FRAME), "ecomponents", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "RRR", "ISI", "STS", "ISI", 'T', ToolListMFR.ENGIN_ANVIL_TOOLS, 'R', ComponentListMFR.RIVET, 'I', ironHunk, 'S', steelHunk);
        time = 8;
        KnowledgeListMFR.istrutR = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMFR.IRON_STRUT), "ecomponents", true, "hvyhammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "RTR", "SIS", "SIS", 'T', ToolListMFR.ENGIN_ANVIL_TOOLS, 'R', ComponentListMFR.RIVET, 'I', iron, 'S', steelHunk);
        time = 8;
        KnowledgeListMFR.stubeR = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMFR.STEEL_TUBE), "ecomponents", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "TR R", "SSSS", 'T', ToolListMFR.ENGIN_ANVIL_TOOLS, 'R', ComponentListMFR.RIVET, 'S', steelHunk);
        time = 2;
        KnowledgeListMFR.boltR = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMFR.BOLT, 2), "etools", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " T ", "SIS", " S ", " S ", 'T', ToolListMFR.ENGIN_ANVIL_TOOLS, 'I', iron, 'S', steelHunk);
        time = 35;
        KnowledgeListMFR.climbPickbR = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(ToolListMFR.CLIMBING_PICK_BASIC), "climber", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "L SR", "IISR", "L T ", 'R', ComponentListMFR.RIVET, 'T', ToolListMFR.ENGIN_ANVIL_TOOLS, 'I', iron, 'S', steel, 'L', getStrips(material));
        time = 5;
        KnowledgeListMFR.bgearR = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMFR.BRONZE_GEARS), "ecomponents", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " T ", " B ", "BIB", " B ", 'T', ToolListMFR.ENGIN_ANVIL_TOOLS, 'I', iron, 'B', bronzeHunk);
        time = 8;
        material = BaseMaterialMFR.tungsten;
        KnowledgeListMFR.tgearR = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMFR.TUNGSTEN_GEARS), "tungsten", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " T ", " W ", "WGW", " W ", 'T', ToolListMFR.ENGIN_ANVIL_TOOLS, 'W', tungstenHunk, 'G', ComponentListMFR.BRONZE_GEARS);
        time = 5;
        material = BaseMaterialMFR.compositeAlloy;
        KnowledgeListMFR.compPlateR = MineFantasyRebornAPI.addAnvilRecipe(engineering, ComponentListMFR.bar("CompositeAlloy"), "cogArmour", true, "hvyhammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " T ", " S ", "RWR", " C ", 'R', ComponentListMFR.RIVET, 'T', ToolListMFR.ENGIN_ANVIL_TOOLS, 'C', copper, 'W', tungstenHunk, 'S', steel);
        material = BaseMaterialMFR.iron;

        time = 5;
        KnowledgeListMFR.bombCaseIronR = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMFR.BOMB_CASING_IRON, 2), "bombIron", true, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " T ", " I ", "IRI", " I ", 'T', ToolListMFR.ENGIN_ANVIL_TOOLS, 'I', ironHunk, 'R', ComponentListMFR.RIVET);
        KnowledgeListMFR.mineCaseIronR = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMFR.MINE_CASING_IRON, 2), "bombIron", true, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "  T  ", "  P  ", " IRI ", "IR RI", 'T', ToolListMFR.ENGIN_ANVIL_TOOLS, 'P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 'I', ironHunk, 'R', ComponentListMFR.RIVET);

        time = 5;
        KnowledgeListMFR.bombarrowR = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMFR.BOMB_CASING_ARROW), "bombarrow", true, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "   IR", "FPITI", "   IR", 'T', ToolListMFR.ENGIN_ANVIL_TOOLS, 'I', ironHunk, 'R', Items.REDSTONE, 'P', ComponentListMFR.PLANK.construct("RefinedWood"), 'F', ComponentListMFR.FLETCHING);
        KnowledgeListMFR.bombBoltR = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMFR.BOMB_CASING_BOLT), "bombarrow", true, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "  IR", "FITI", "  IR", 'T', ToolListMFR.ENGIN_ANVIL_TOOLS, 'I', ironHunk, 'R', Items.REDSTONE, 'F', ComponentListMFR.FLETCHING);

        material = BaseMaterialMFR.blacksteel;

        time = 5;
        KnowledgeListMFR.bombCaseObsidianR = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMFR.BOMB_CASING_OBSIDIAN, 2), "bombObsidian", true, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " T ", "RIR", "IOI", "RIR", 'T', ToolListMFR.ENGIN_ANVIL_TOOLS, 'O', Blocks.OBSIDIAN, 'I', obsidianHunk, 'R', ComponentListMFR.RIVET);
        KnowledgeListMFR.mineCaseObsidianR = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMFR.MINE_CASING_OBSIDIAN, 2), "mineObsidian", true, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "  T  ", "  P  ", " IRI ", "IRORI", 'T', ToolListMFR.ENGIN_ANVIL_TOOLS, 'O', Blocks.OBSIDIAN, 'P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 'I', obsidianHunk, 'R', ComponentListMFR.RIVET);
        time = 15;
        material = BaseMaterialMFR.steel;
        KnowledgeListMFR.crossBayonetR = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMFR.CROSS_BAYONET), "crossBayonet", true, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "R R I", "PIII ", 'P', ComponentListMFR.PLANK.construct("RefinedWood"), 'I', ironHunk, 'R', ComponentListMFR.RIVET);

        Salvage.addSalvage(ToolListMFR.ENGIN_ANVIL_TOOLS, steelHunk, steelHunk, steelHunk, steelHunk, new ItemStack(ComponentListMFR.LEATHER_STRIP, 4));

        Salvage.addSalvage(ComponentListMFR.IRON_FRAME, steelHunk, steelHunk, steelHunk, steelHunk, ironHunk, ironHunk, ironHunk, ironHunk, new ItemStack(ComponentListMFR.RIVET, 3));

        Salvage.addSalvage(ComponentListMFR.IRON_STRUT, steelHunk, steelHunk, steelHunk, steelHunk, ComponentListMFR.bar("Iron", 2), new ItemStack(ComponentListMFR.RIVET, 2));

        Salvage.addSalvage(ComponentListMFR.STEEL_TUBE, steelHunk, steelHunk, steelHunk, steelHunk, new ItemStack(ComponentListMFR.RIVET, 2));

        Salvage.addSalvage(ToolListMFR.CLIMBING_PICK_BASIC, ComponentListMFR.bar("Iron", 2), ComponentListMFR.bar("Steel", 2), new ItemStack(ComponentListMFR.RIVET, 2), new ItemStack(ComponentListMFR.LEATHER_STRIP, 2));

        Salvage.addSalvage(ComponentListMFR.BRONZE_GEARS, bronzeHunk, bronzeHunk, bronzeHunk, bronzeHunk, iron);

        Salvage.addSalvage(ComponentListMFR.TUNGSTEN_GEARS, tungstenHunk, tungstenHunk, tungstenHunk, tungstenHunk, ComponentListMFR.BRONZE_GEARS);

        Salvage.addSalvage(ComponentListMFR.bar("CompositeAlloy"), copper, steel, tungstenHunk, new ItemStack(ComponentListMFR.RIVET, 2));
        Salvage.addSalvage(ComponentListMFR.INGOT_COMPOSITE_ALLOY, copper, steel, tungstenHunk, new ItemStack(ComponentListMFR.RIVET, 2));

        Salvage.addSalvage(ComponentListMFR.BOMB_CASING_IRON, ironHunk, ironHunk);
        Salvage.addSalvage(ComponentListMFR.MINE_CASING_IRON, ironHunk, ironHunk, ComponentListMFR.RIVET, iron);
        Salvage.addSalvage(ComponentListMFR.BOMB_CASING_ARROW, ironHunk, ironHunk, ironHunk, ironHunk, new ItemStack(Items.REDSTONE, 2), ComponentListMFR.PLANK.construct("RefinedWood"), ComponentListMFR.FLETCHING);

        Salvage.addSalvage(ComponentListMFR.BOMB_CASING_BOLT, ironHunk, ironHunk, ironHunk, ironHunk, new ItemStack(Items.REDSTONE, 2), ComponentListMFR.FLETCHING);

        Salvage.addSalvage(ComponentListMFR.BOMB_CASING_OBSIDIAN, obsidianHunk, obsidianHunk, new ItemStack(ComponentListMFR.RIVET, 2));
        Salvage.addSalvage(ComponentListMFR.MINE_CASING_OBSIDIAN, obsidianHunk, obsidianHunk, iron, ComponentListMFR.RIVET);

        Salvage.addSalvage(ComponentListMFR.CROSS_BAYONET, ironHunk, ironHunk, ironHunk, ironHunk, ComponentListMFR.PLANK.construct("RefinedWood"), new ItemStack(ComponentListMFR.RIVET, 2));
    }

    private static void addConstruction() {
        BaseMaterialMFR material = BaseMaterialMFR.tin;
        ItemStack tin = ComponentListMFR.bar("Tin");
        int time = 10;
        KnowledgeListMFR.brushRecipe = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(ToolListMFR.PAINT_BRUSH), "paint_brush", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "W", "I", "P", 'W', Blocks.WOOL, 'I', tin, 'P', ComponentListMFR.PLANK.construct("RefinedWood"));

        Salvage.addSalvage(ToolListMFR.PAINT_BRUSH, Blocks.WOOL, tin, ComponentListMFR.PLANK.construct("RefinedWood"));
    }

    private static void addCogworkParts() {
        BaseMaterialMFR material = BaseMaterialMFR.steel;
        int time = 1;
        KnowledgeListMFR.frameBlockR = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(BlockListMFR.FRAME_BLOCK), "cogArmour", false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "R", "I", 'I', ComponentListMFR.IRON_FRAME, 'R', ComponentListMFR.RIVET);

        Salvage.addSalvage(BlockListMFR.FRAME_BLOCK, ComponentListMFR.IRON_FRAME, ComponentListMFR.RIVET);

        time = 10;
        KnowledgeListMFR.cogPulleyR = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(ComponentListMFR.COGWORK_PULLEY), "cogArmour", false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "RFR", "GBG", "RFR", 'B', Blocks.REDSTONE_BLOCK, 'F', ComponentListMFR.IRON_FRAME, 'R', ComponentListMFR.RIVET, 'G', ComponentListMFR.TUNGSTEN_GEARS);
        Salvage.addSalvage(ComponentListMFR.COGWORK_PULLEY, Blocks.REDSTONE_BLOCK, new ItemStack(ComponentListMFR.IRON_FRAME, 2), new ItemStack(ComponentListMFR.RIVET, 2), new ItemStack(ComponentListMFR.TUNGSTEN_GEARS, 2));
        Salvage.addSalvage(BlockListMFR.FRAME_BLOCK, ComponentListMFR.IRON_FRAME, ComponentListMFR.RIVET);

        time = 10;
        KnowledgeListMFR.cogHelmR = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(BlockListMFR.COGWORK_HELM), "cogArmour", false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "RFFR", "SEES", " RR ", 'E', Items.ENDER_EYE, 'F', ComponentListMFR.IRON_FRAME, 'R', ComponentListMFR.RIVET, 'S', ComponentListMFR.COGWORK_SHAFT);
        Salvage.addSalvage(BlockListMFR.COGWORK_HELM, new ItemStack(Items.ENDER_EYE, 2), new ItemStack(ComponentListMFR.IRON_FRAME, 2), new ItemStack(ComponentListMFR.COGWORK_SHAFT, 2), new ItemStack(ComponentListMFR.RIVET, 4));
        time = 15;
        KnowledgeListMFR.cogChestR = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(BlockListMFR.COGWORK_CHEST), "cogArmour", false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " RFR ", "RSFSR", "RFBFR", " SFS ", 'F', ComponentListMFR.IRON_FRAME, 'R', ComponentListMFR.RIVET, 'S', ComponentListMFR.COGWORK_SHAFT, 'B', Blocks.FURNACE);
        Salvage.addSalvage(BlockListMFR.COGWORK_CHEST, Blocks.FURNACE, new ItemStack(ComponentListMFR.IRON_FRAME, 5), new ItemStack(ComponentListMFR.COGWORK_SHAFT, 4), new ItemStack(ComponentListMFR.RIVET, 6));

        time = 10;
        KnowledgeListMFR.cogLegsR = MineFantasyRebornAPI.addAnvilRecipe(engineering, new ItemStack(BlockListMFR.COGWORK_LEGS), "cogArmour", false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "RFFFR", "RS SR", " S S ", " F F ", 'F', ComponentListMFR.IRON_FRAME, 'R', ComponentListMFR.RIVET, 'S', ComponentListMFR.COGWORK_SHAFT);
        Salvage.addSalvage(BlockListMFR.COGWORK_LEGS, new ItemStack(ComponentListMFR.IRON_FRAME, 5), new ItemStack(ComponentListMFR.COGWORK_SHAFT, 4), new ItemStack(ComponentListMFR.RIVET, 4));

    }
}
