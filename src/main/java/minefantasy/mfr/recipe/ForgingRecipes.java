package minefantasy.mfr.recipe;

import minefantasy.mfr.api.MineFantasyRebornAPI;
import minefantasy.mfr.api.crafting.Salvage;
import minefantasy.mfr.config.ConfigCrafting;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.FoodListMFR;
import minefantasy.mfr.init.KnowledgeListMFR;
import minefantasy.mfr.init.LeatherArmourListMFR;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.ToolListMFR;
import minefantasy.mfr.material.BaseMaterialMFR;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.HashMap;

public class ForgingRecipes {
	public static final HashMap<String, IAnvilRecipe> recipeMap = new HashMap<String, IAnvilRecipe>();

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
		material = BaseMaterialMFR.ENCRUSTED;

		KnowledgeListMFR.obsidianHunkR = MineFantasyRebornAPI.addAnvilRecipe(null, new ItemStack(ComponentListMFR.OBSIDIAN_ROCK, 4), "", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "D", 'D', Blocks.OBSIDIAN);
		KnowledgeListMFR.diamondR = MineFantasyRebornAPI.addAnvilRecipe(null, new ItemStack(ComponentListMFR.DIAMOND_SHARDS), "", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "D", 'D', Items.DIAMOND);

		time = 3;
		KnowledgeListMFR.encrustedR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, ComponentListMFR.bar("Encrusted"), "smeltEncrusted", true, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "D", "I", 'D', ComponentListMFR.DIAMOND_SHARDS, 'I', ComponentListMFR.bar("Steel"));
		Salvage.addSalvage(ComponentListMFR.ENCRUSTED_INGOT, ComponentListMFR.STEEL_INGOT, ComponentListMFR.DIAMOND_SHARDS);
		Salvage.addSalvage(ComponentListMFR.bar("Encrusted"), ComponentListMFR.STEEL_INGOT,
				ComponentListMFR.DIAMOND_SHARDS);

		material = BaseMaterialMFR.PIG_IRON;
		KnowledgeListMFR.steelR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, ComponentListMFR.bar("Steel"), "smeltSteel", true, 1, 1, 5, "C", "H", 'C', ComponentListMFR.COAL_DUST, 'H', ComponentListMFR.bar("PigIron"));
		KnowledgeListMFR.fluxR = MineFantasyRebornAPI.addAnvilRecipe(null, new ItemStack(ComponentListMFR.FLUX, 4), "", false, -1, -1, 2, "H", 'H', MineFantasyBlocks.LIMESTONE);

		// STUDDED
		material = BaseMaterialMFR.IRON;
		// HELMET
		time = 10;

		// TODO fixme
		//        KnowledgeListMFR.studHelmetR = MineFantasyRebornAPI.addAnvilRecipe(artisanry, LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 3, 0), "craftArmourLight", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " I ", "IAI", " I ", 'I', ComponentListMFR.RIVET, 'A', LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 0));
		//        // CHEST
		//        time = 20;
		//        KnowledgeListMFR.studChestR = MineFantasyRebornAPI.addAnvilRecipe(artisanry, LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 3, 1), "craftArmourLight", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " I ", "IAI", " I ", 'I', ComponentListMFR.RIVET, 'A', LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 1));
		//        // LEGS
		//        time = 15;
		//        KnowledgeListMFR.studLegsR = MineFantasyRebornAPI.addAnvilRecipe(artisanry, LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 3, 2), "craftArmourLight", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " I ", "IAI", " I ", 'I', ComponentListMFR.RIVET, 'A', LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 2));
		//        // BOOTS
		//        time = 6;
		//        KnowledgeListMFR.studBootsR = MineFantasyRebornAPI.addAnvilRecipe(artisanry, LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 3, 3), "craftArmourLight", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " I ", "IAI", " I ", 'I', ComponentListMFR.RIVET, 'A', LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 2, 3));

		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 3, 0), LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 2, 0), new ItemStack(ComponentListMFR.RIVET, 4));
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 3, 1), LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 2, 1), new ItemStack(ComponentListMFR.RIVET, 4));
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 3, 2), LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 2, 2), new ItemStack(ComponentListMFR.RIVET, 4));
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 3, 3), LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 2, 3), new ItemStack(ComponentListMFR.RIVET, 4));

		time = 2;
		material = BaseMaterialMFR.IRON;
		if (ConfigCrafting.allowIronResmelt) {
			MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(ComponentListMFR.IRON_PREP), "blastfurn", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "IFI", 'I', ComponentListMFR.bar("iron"), 'F', ComponentListMFR.FLUX);
			MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(ComponentListMFR.IRON_PREP, 2), "blastfurn", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "IFI", 'I', ComponentListMFR.bar("iron"), 'F', ComponentListMFR.FLUX_STRONG);
		}
		KnowledgeListMFR.coalPrepR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(ComponentListMFR.COAL_PREP), "coke", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "RCR", "CFC", "RCR", 'R', Items.REDSTONE, 'C', new ItemStack(Items.COAL, 1, 1), 'F', ComponentListMFR.FLUX_STRONG);
		GameRegistry.addSmelting(ComponentListMFR.COAL_PREP, new ItemStack(ComponentListMFR.COKE), 1F);

		KnowledgeListMFR.ironPrepR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(ComponentListMFR.IRON_PREP), "blastfurn", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "IFI", 'I', Blocks.IRON_ORE, 'F', ComponentListMFR.FLUX);
		KnowledgeListMFR.ironPrepR2 = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(ComponentListMFR.IRON_PREP, 2), "blastfurn", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "IFI", 'I', Blocks.IRON_ORE, 'F', ComponentListMFR.FLUX_STRONG);

		MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(ComponentListMFR.IRON_PREP), "blastfurn", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "IFI", 'I', ComponentListMFR.ORE_IRON, 'F', ComponentListMFR.FLUX);
		MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(ComponentListMFR.IRON_PREP, 2), "blastfurn", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "IFI", 'I', ComponentListMFR.ORE_IRON, 'F', ComponentListMFR.FLUX_STRONG);
		ItemStack plate = ComponentListMFR.PLATE.createComm("iron");
		time = 10;
		KnowledgeListMFR.blastChamR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyBlocks.BLAST_CHAMBER), "blastfurn", false, "hvyHammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "RP PR", "RP PR", "RP PR", "RP PR", 'R', ComponentListMFR.RIVET, 'P', plate);

		time = 15;
		KnowledgeListMFR.blastHeatR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyBlocks.BLAST_HEATER), "blastfurn", false, "hvyHammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "RP PR", "RP PR", "RP PR", "RPFPR", 'R', ComponentListMFR.RIVET, 'P', plate, 'F', Blocks.FURNACE);

		Salvage.addSalvage(MineFantasyBlocks.BLAST_HEATER, new ItemStack(ComponentListMFR.RIVET, 8), plate, plate, plate,
				plate, plate, plate, plate, plate, Blocks.FURNACE);
		Salvage.addSalvage(MineFantasyBlocks.BLAST_CHAMBER, new ItemStack(ComponentListMFR.RIVET, 8), plate, plate, plate,
				plate, plate, plate, plate, plate);

		time = 10;
		KnowledgeListMFR.bigFurnR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyBlocks.FURNACE_STONE), "bigfurn", false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "PRP", "RCR", "PFP", 'F', Blocks.FURNACE, 'R', ComponentListMFR.RIVET, 'P', plate, 'C', MineFantasyBlocks.FIREBRICKS);
		time = 10;
		KnowledgeListMFR.bigHeatR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyBlocks.FURNACE_HEATER), "bigfurn", false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "RCR", "PFP", 'R', ComponentListMFR.RIVET, 'P', plate, 'C', MineFantasyBlocks.FIREBRICKS, 'F', MineFantasyBlocks.FORGE);

		Salvage.addSalvage(MineFantasyBlocks.FURNACE_HEATER, new ItemStack(ComponentListMFR.RIVET, 2), plate, plate, MineFantasyBlocks.FIREBRICKS, MineFantasyBlocks.FORGE);
		Salvage.addSalvage(MineFantasyBlocks.FURNACE_STONE, new ItemStack(ComponentListMFR.RIVET, 3), plate, plate, plate, plate, MineFantasyBlocks.FIREBRICKS, Blocks.FURNACE);

		material = MineFantasyBlocks.ANVIL_BRONZE.material;
		IAnvilRecipe bronze_anvil_recipe = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyBlocks.ANVIL_BRONZE), "smelt" + material.name, false, "hammer", -1, -1, (int) (time * material.craftTimeModifier), " II", "III", " I ", 'I', ComponentListMFR.bar(material.name));
		Salvage.addSalvage(MineFantasyBlocks.ANVIL_BRONZE, ComponentListMFR.bar(material.name, 6));

		material = MineFantasyBlocks.ANVIL_IRON.material;
		IAnvilRecipe iron_anvil_recipe = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyBlocks.ANVIL_IRON), "smelt" + material.name, false, "hammer", -1, -1, (int) (time * material.craftTimeModifier), " II", "III", " I ", 'I', ComponentListMFR.bar(material.name));
		Salvage.addSalvage(MineFantasyBlocks.ANVIL_IRON, ComponentListMFR.bar(material.name, 6));

		material = MineFantasyBlocks.ANVIL_STEEL.material;
		IAnvilRecipe steel_anvil_recipe = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyBlocks.ANVIL_STEEL), "smelt" + material.name, false, "hammer", -1, -1, (int) (time * material.craftTimeModifier), " II", "III", " I ", 'I', ComponentListMFR.bar(material.name));
		Salvage.addSalvage(MineFantasyBlocks.ANVIL_STEEL, ComponentListMFR.bar(material.name, 6));

		material = MineFantasyBlocks.ANVIL_BLACK_STEEL.material;
		IAnvilRecipe black_steel_anvil_recipe = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyBlocks.ANVIL_BLACK_STEEL), "smelt" + material.name, false, "hammer", -1, -1, (int) (time * material.craftTimeModifier), " II", "III", " I ", 'I', ComponentListMFR.bar(material.name));
		Salvage.addSalvage(MineFantasyBlocks.ANVIL_BLACK_STEEL, ComponentListMFR.bar(material.name, 6));

		material = MineFantasyBlocks.ANVIL_BLUE_STEEL.material;
		IAnvilRecipe blue_steel_anvil_recipe = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyBlocks.ANVIL_BLUE_STEEL), "smelt" + material.name, false, "hammer", -1, -1, (int) (time * material.craftTimeModifier), " II", "III", " I ", 'I', ComponentListMFR.bar(material.name));
		Salvage.addSalvage(MineFantasyBlocks.ANVIL_BLUE_STEEL, ComponentListMFR.bar(material.name, 6));

		material = MineFantasyBlocks.ANVIL_RED_STEEL.material;
		IAnvilRecipe red_steel_recipe = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyBlocks.ANVIL_RED_STEEL), "smelt" + material.name, false, "hammer", -1, -1, (int) (time * material.craftTimeModifier), " II", "III", " I ", 'I', ComponentListMFR.bar(material.name));
		Salvage.addSalvage(MineFantasyBlocks.ANVIL_RED_STEEL, ComponentListMFR.bar(material.name, 6));

		recipeMap.put("anvilBronze", bronze_anvil_recipe);
		recipeMap.put("anvilIron", iron_anvil_recipe);
		recipeMap.put("anvilSteel", steel_anvil_recipe);
		recipeMap.put("anvilBlackSteel", black_steel_anvil_recipe);
		recipeMap.put("anvilBlueSteel", blue_steel_anvil_recipe);
		recipeMap.put("anvilRedSteel", red_steel_recipe);

		ItemStack bronzeHunk = ComponentListMFR.METAL_HUNK.createComm("bronze");
		ItemStack ironHunk = ComponentListMFR.METAL_HUNK.createComm("iron");

		time = 2;
		material = BaseMaterialMFR.BRONZE;
		KnowledgeListMFR.framedStoneR = MineFantasyRebornAPI.addAnvilRecipe(Skill.CONSTRUCTION, new ItemStack(MineFantasyBlocks.REINFORCED_STONE_FRAMED), "decorated_stone", false, "hammer", material.hammerTier - 1, material.anvilTier - 1, (int) (time * material.craftTimeModifier), " N ", "NSN", " N ", 'N', bronzeHunk, 'S', MineFantasyBlocks.REINFORCED_STONE);
		Salvage.addSalvage(MineFantasyBlocks.REINFORCED_STONE_FRAMED, bronzeHunk, bronzeHunk, bronzeHunk, bronzeHunk,
				MineFantasyBlocks.REINFORCED_STONE);
		time = 2;
		material = BaseMaterialMFR.IRON;
		KnowledgeListMFR.iframedStoneR = MineFantasyRebornAPI.addAnvilRecipe(Skill.CONSTRUCTION, new ItemStack(MineFantasyBlocks.REINFORCED_STONE_FRAMED_IRON), "decorated_stone", false, "hammer", material.hammerTier - 1, material.anvilTier - 1, (int) (time * material.craftTimeModifier), " N ", "NSN", " N ", 'N', ironHunk, 'S', MineFantasyBlocks.REINFORCED_STONE);
		Salvage.addSalvage(MineFantasyBlocks.REINFORCED_STONE_FRAMED_IRON, ironHunk, ironHunk, ironHunk, ironHunk,
				MineFantasyBlocks.REINFORCED_STONE);

		time = 2;
		material = BaseMaterialMFR.BRONZE;
		KnowledgeListMFR.smokePipeR = MineFantasyRebornAPI.addAnvilRecipe(Skill.CONSTRUCTION, new ItemStack(MineFantasyBlocks.CHIMNEY_PIPE, 4), "", false, "hammer", material.hammerTier - 1, material.anvilTier - 1, (int) (time * material.craftTimeModifier), "N  N", "PPPP", "N  N", 'N', bronzeHunk, 'P', MineFantasyBlocks.CHIMNEY_STONE);
		Salvage.addSalvage(MineFantasyBlocks.CHIMNEY_PIPE, MineFantasyBlocks.CHIMNEY_STONE, bronzeHunk);

		material = MineFantasyBlocks.BRONZE_BARS.material;
		KnowledgeListMFR.barsR.add(MineFantasyRebornAPI.addAnvilRecipe(Skill.CONSTRUCTION, new ItemStack(MineFantasyBlocks.BRONZE_BARS), "smelt" + material.name, false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "I I", "I I", 'I', bronzeHunk));
		Salvage.addSalvage(MineFantasyBlocks.BRONZE_BARS, bronzeHunk, bronzeHunk, bronzeHunk, bronzeHunk);

		material = MineFantasyBlocks.IRON_BARS.material;
		KnowledgeListMFR.barsR.add(MineFantasyRebornAPI.addAnvilRecipe(Skill.CONSTRUCTION, new ItemStack(MineFantasyBlocks.IRON_BARS), "smelt" + material.name, false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "I I", "I I", 'I', ironHunk));
		Salvage.addSalvage(MineFantasyBlocks.IRON_BARS, ironHunk, ironHunk, ironHunk, ironHunk);

		material = MineFantasyBlocks.STEEL_BARS.material;
		ItemStack steelHunk = ComponentListMFR.METAL_HUNK.createComm(material.name);
		KnowledgeListMFR.barsR.add(MineFantasyRebornAPI.addAnvilRecipe(Skill.CONSTRUCTION, new ItemStack(MineFantasyBlocks.STEEL_BARS), "smelt" + material.name, false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "I I", "I I", 'I', steelHunk));
		Salvage.addSalvage(MineFantasyBlocks.STEEL_BARS, steelHunk, steelHunk, steelHunk, steelHunk);

		material = MineFantasyBlocks.BLACK_STEEL_BARS.material;
		ItemStack blackSteelHunk = ComponentListMFR.METAL_HUNK.createComm(material.name);
		KnowledgeListMFR.barsR.add(MineFantasyRebornAPI.addAnvilRecipe(Skill.CONSTRUCTION, new ItemStack(MineFantasyBlocks.BLACK_STEEL_BARS), "smelt" + material.name, false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "I I", "I I", 'I', blackSteelHunk));
		Salvage.addSalvage(MineFantasyBlocks.BLACK_STEEL_BARS, blackSteelHunk, blackSteelHunk, blackSteelHunk, blackSteelHunk);

		material = MineFantasyBlocks.BLUE_STEEL_BARS.material;
		ItemStack blueSteelHunk = ComponentListMFR.METAL_HUNK.createComm(material.name);
		KnowledgeListMFR.barsR.add(MineFantasyRebornAPI.addAnvilRecipe(Skill.CONSTRUCTION, new ItemStack(MineFantasyBlocks.BLUE_STEEL_BARS), "smelt" + material.name, false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "I I", "I I", 'I', blueSteelHunk));
		Salvage.addSalvage(MineFantasyBlocks.BLUE_STEEL_BARS, blueSteelHunk, blueSteelHunk, blueSteelHunk, blueSteelHunk);

		material = MineFantasyBlocks.RED_STEEL_BARS.material;
		ItemStack redSteelHunk = ComponentListMFR.METAL_HUNK.createComm(material.name);
		KnowledgeListMFR.barsR.add(MineFantasyRebornAPI.addAnvilRecipe(Skill.CONSTRUCTION, new ItemStack(MineFantasyBlocks.RED_STEEL_BARS), "smelt" + material.name, false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "I I", "I I", 'I', redSteelHunk));
		Salvage.addSalvage(MineFantasyBlocks.RED_STEEL_BARS, redSteelHunk, redSteelHunk, redSteelHunk, redSteelHunk);

		if (!ConfigHardcore.HCCRemoveTalismansCraft) {
			KnowledgeListMFR.talismanRecipe.add(MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(ComponentListMFR.TALISMAN_LESSER), "", true, "hammer", -1, -1, 20, "LGL", "GIG", " G ", 'L', new ItemStack(Items.DYE, 1, 4), 'I', ironbar, 'G', goldbar));
			KnowledgeListMFR.talismanRecipe.add(MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(ComponentListMFR.TALISMAN_LESSER), "", true, "hammer", -1, -1, 20, "LSL", "SIS", " S ", 'L', new ItemStack(Items.DYE, 1, 4), 'I', ironbar, 'S', silverbar));
			KnowledgeListMFR.greatTalismanRecipe = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(ComponentListMFR.TALISMAN_GREATER), "", true, "hammer", 1, 1, 50, "GSG", "DTD", "GDG", 'G', goldbar, 'D', Items.DIAMOND, 'T', ComponentListMFR.TALISMAN_LESSER, 'S', Items.NETHER_STAR);
		}

		addEngineering();
		addConstruction();

		time = 10;
		material = BaseMaterialMFR.IRON;
		KnowledgeListMFR.caketinRecipe = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(FoodListMFR.CAKE_TIN), "", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " R ", "I I", " I ", 'I', ironbar, 'R', ComponentListMFR.RIVET);
		Salvage.addSalvage(FoodListMFR.CAKE_TIN, ComponentListMFR.bar("Iron", 3), ComponentListMFR.RIVET);

		KnowledgeListMFR.coalfluxR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(ComponentListMFR.COAL_FLUX, 2), "coalflux", false, material.hammerTier, material.anvilTier, 2, "F", "C", 'C', Items.COAL, 'F', ComponentListMFR.FLUX_POT);

		time = 4;
		material = BaseMaterialMFR.IRON;
		KnowledgeListMFR.hingeRecipe = MineFantasyRebornAPI.addAnvilRecipe(Skill.CONSTRUCTION, new ItemStack(ComponentListMFR.HINGE), "", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "LR", 'L', ComponentListMFR.LEATHER_STRIP, 'R', ComponentListMFR.RIVET);
		Salvage.addSalvage(ComponentListMFR.HINGE, ComponentListMFR.LEATHER_STRIP, ComponentListMFR.RIVET);

		time = 10;
		KnowledgeListMFR.crestR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(ComponentListMFR.ORNATE_ITEMS), "craftOrnate", false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " G ", "SLS", " G ", 'G', goldbar, 'S', silverbar, 'L', new ItemStack(Items.DYE, 1, 4));
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

		BaseMaterialMFR material = BaseMaterialMFR.STEEL;
		int time = 15;
		KnowledgeListMFR.eatoolsR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(ToolListMFR.ENGIN_ANVIL_TOOLS), "etools", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "SSLL", "LLSS", 'S', steelHunk, 'L', getStrips(material));
		time = 5;
		KnowledgeListMFR.iframeR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(ComponentListMFR.IRON_FRAME), "ecomponents", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "RRR", "ISI", "STS", "ISI", 'T', ToolListMFR.ENGIN_ANVIL_TOOLS, 'R', ComponentListMFR.RIVET, 'I', ironHunk, 'S', steelHunk);
		time = 8;
		KnowledgeListMFR.istrutR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(ComponentListMFR.IRON_STRUT), "ecomponents", true, "hvyhammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "RTR", "SIS", "SIS", 'T', ToolListMFR.ENGIN_ANVIL_TOOLS, 'R', ComponentListMFR.RIVET, 'I', iron, 'S', steelHunk);
		time = 8;
		KnowledgeListMFR.stubeR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(ComponentListMFR.STEEL_TUBE), "ecomponents", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "TR R", "SSSS", 'T', ToolListMFR.ENGIN_ANVIL_TOOLS, 'R', ComponentListMFR.RIVET, 'S', steelHunk);
		time = 2;
		KnowledgeListMFR.boltR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(ComponentListMFR.BOLT, 2), "etools", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " T ", "SIS", " S ", " S ", 'T', ToolListMFR.ENGIN_ANVIL_TOOLS, 'I', iron, 'S', steelHunk);
		time = 35;
		KnowledgeListMFR.climbPickbR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(ToolListMFR.CLIMBING_PICK_BASIC), "climber", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "L SR", "IISR", "L T ", 'R', ComponentListMFR.RIVET, 'T', ToolListMFR.ENGIN_ANVIL_TOOLS, 'I', iron, 'S', steel, 'L', getStrips(material));
		time = 5;
		KnowledgeListMFR.bgearR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(ComponentListMFR.BRONZE_GEARS), "ecomponents", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " T ", " B ", "BIB", " B ", 'T', ToolListMFR.ENGIN_ANVIL_TOOLS, 'I', iron, 'B', bronzeHunk);
		time = 8;
		material = BaseMaterialMFR.TUNGSTEN;
		KnowledgeListMFR.tgearR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(ComponentListMFR.TUNGSTEN_GEARS), "tungsten", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " T ", " W ", "WGW", " W ", 'T', ToolListMFR.ENGIN_ANVIL_TOOLS, 'W', tungstenHunk, 'G', ComponentListMFR.BRONZE_GEARS);
		time = 5;
		material = BaseMaterialMFR.COMPOSITE_ALLOY;
		KnowledgeListMFR.compPlateR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, ComponentListMFR.bar("CompositeAlloy"), "cogArmour", true, "hvyhammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " T ", " S ", "RWR", " C ", 'R', ComponentListMFR.RIVET, 'T', ToolListMFR.ENGIN_ANVIL_TOOLS, 'C', copper, 'W', tungstenHunk, 'S', steel);
		material = BaseMaterialMFR.IRON;

		time = 5;
		KnowledgeListMFR.bombCaseIronR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(ComponentListMFR.BOMB_CASING_IRON, 2), "bombIron", true, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " T ", " I ", "IRI", " I ", 'T', ToolListMFR.ENGIN_ANVIL_TOOLS, 'I', ironHunk, 'R', ComponentListMFR.RIVET);
		KnowledgeListMFR.mineCaseIronR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(ComponentListMFR.MINE_CASING_IRON, 2), "bombIron", true, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "  T  ", "  P  ", " IRI ", "IR RI", 'T', ToolListMFR.ENGIN_ANVIL_TOOLS, 'P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 'I', ironHunk, 'R', ComponentListMFR.RIVET);

		time = 5;
		KnowledgeListMFR.bombarrowR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(ComponentListMFR.BOMB_CASING_ARROW), "bombarrow", true, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "   IR", "FPITI", "   IR", 'T', ToolListMFR.ENGIN_ANVIL_TOOLS, 'I', ironHunk, 'R', Items.REDSTONE, 'P', ComponentListMFR.TIMBER.construct("RefinedWood"), 'F', ComponentListMFR.FLETCHING);
		KnowledgeListMFR.bombBoltR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(ComponentListMFR.BOMB_CASING_BOLT), "bombarrow", true, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "  IR", "FITI", "  IR", 'T', ToolListMFR.ENGIN_ANVIL_TOOLS, 'I', ironHunk, 'R', Items.REDSTONE, 'F', ComponentListMFR.FLETCHING);

		material = BaseMaterialMFR.BLACK_STEEL;

		time = 5;
		KnowledgeListMFR.bombCaseObsidianR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(ComponentListMFR.BOMB_CASING_OBSIDIAN, 2), "bombObsidian", true, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " T ", "RIR", "IOI", "RIR", 'T', ToolListMFR.ENGIN_ANVIL_TOOLS, 'O', Blocks.OBSIDIAN, 'I', obsidianHunk, 'R', ComponentListMFR.RIVET);
		KnowledgeListMFR.mineCaseObsidianR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(ComponentListMFR.MINE_CASING_OBSIDIAN, 2), "mineObsidian", true, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "  T  ", "  P  ", " IRI ", "IRORI", 'T', ToolListMFR.ENGIN_ANVIL_TOOLS, 'O', Blocks.OBSIDIAN, 'P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 'I', obsidianHunk, 'R', ComponentListMFR.RIVET);
		time = 15;
		material = BaseMaterialMFR.STEEL;
		KnowledgeListMFR.crossBayonetR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(ComponentListMFR.CROSSBOW_BAYONET), "crossBayonet", true, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "R R I", "PIII ", 'P', ComponentListMFR.TIMBER.construct("RefinedWood"), 'I', ironHunk, 'R', ComponentListMFR.RIVET);

		Salvage.addSalvage(ToolListMFR.ENGIN_ANVIL_TOOLS, steelHunk, steelHunk, steelHunk, steelHunk, new ItemStack(ComponentListMFR.LEATHER_STRIP, 4));

		Salvage.addSalvage(ComponentListMFR.IRON_FRAME, steelHunk, steelHunk, steelHunk, steelHunk, ironHunk, ironHunk, ironHunk, ironHunk, new ItemStack(ComponentListMFR.RIVET, 3));

		Salvage.addSalvage(ComponentListMFR.IRON_STRUT, steelHunk, steelHunk, steelHunk, steelHunk, ComponentListMFR.bar("Iron", 2), new ItemStack(ComponentListMFR.RIVET, 2));

		Salvage.addSalvage(ComponentListMFR.STEEL_TUBE, steelHunk, steelHunk, steelHunk, steelHunk, new ItemStack(ComponentListMFR.RIVET, 2));

		Salvage.addSalvage(ToolListMFR.CLIMBING_PICK_BASIC, ComponentListMFR.bar("Iron", 2), ComponentListMFR.bar("Steel", 2), new ItemStack(ComponentListMFR.RIVET, 2), new ItemStack(ComponentListMFR.LEATHER_STRIP, 2));

		Salvage.addSalvage(ComponentListMFR.BRONZE_GEARS, bronzeHunk, bronzeHunk, bronzeHunk, bronzeHunk, iron);

		Salvage.addSalvage(ComponentListMFR.TUNGSTEN_GEARS, tungstenHunk, tungstenHunk, tungstenHunk, tungstenHunk, ComponentListMFR.BRONZE_GEARS);

		Salvage.addSalvage(ComponentListMFR.bar("CompositeAlloy"), copper, steel, tungstenHunk, new ItemStack(ComponentListMFR.RIVET, 2));
		Salvage.addSalvage(ComponentListMFR.COMPOSITE_ALLOY_INGOT, copper, steel, tungstenHunk, new ItemStack(ComponentListMFR.RIVET, 2));

		Salvage.addSalvage(ComponentListMFR.BOMB_CASING_IRON, ironHunk, ironHunk);
		Salvage.addSalvage(ComponentListMFR.MINE_CASING_IRON, ironHunk, ironHunk, ComponentListMFR.RIVET, iron);
		Salvage.addSalvage(ComponentListMFR.BOMB_CASING_ARROW, ironHunk, ironHunk, ironHunk, ironHunk, new ItemStack(Items.REDSTONE, 2), ComponentListMFR.TIMBER.construct("RefinedWood"), ComponentListMFR.FLETCHING);

		Salvage.addSalvage(ComponentListMFR.BOMB_CASING_BOLT, ironHunk, ironHunk, ironHunk, ironHunk, new ItemStack(Items.REDSTONE, 2), ComponentListMFR.FLETCHING);

		Salvage.addSalvage(ComponentListMFR.BOMB_CASING_OBSIDIAN, obsidianHunk, obsidianHunk, new ItemStack(ComponentListMFR.RIVET, 2));
		Salvage.addSalvage(ComponentListMFR.MINE_CASING_OBSIDIAN, obsidianHunk, obsidianHunk, iron, ComponentListMFR.RIVET);

		Salvage.addSalvage(ComponentListMFR.CROSSBOW_BAYONET, ironHunk, ironHunk, ironHunk, ironHunk, ComponentListMFR.TIMBER.construct("RefinedWood"), new ItemStack(ComponentListMFR.RIVET, 2));
	}

	private static void addConstruction() {
		BaseMaterialMFR material = BaseMaterialMFR.TIN;
		ItemStack tin = ComponentListMFR.bar("Tin");
		int time = 10;
		KnowledgeListMFR.brushRecipe = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(ToolListMFR.PAINT_BRUSH), "paint_brush", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "W", "I", "P", 'W', Blocks.WOOL, 'I', tin, 'P', ComponentListMFR.TIMBER.construct("RefinedWood"));

		Salvage.addSalvage(ToolListMFR.PAINT_BRUSH, Blocks.WOOL, tin, ComponentListMFR.TIMBER.construct("RefinedWood"));
	}

	private static void addCogworkParts() {
		BaseMaterialMFR material = BaseMaterialMFR.STEEL;
		int time = 1;
		KnowledgeListMFR.frameBlockR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(MineFantasyBlocks.FRAME_BLOCK), "cogArmour", false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "R", "I", 'I', ComponentListMFR.IRON_FRAME, 'R', ComponentListMFR.RIVET);

		Salvage.addSalvage(MineFantasyBlocks.FRAME_BLOCK, ComponentListMFR.IRON_FRAME, ComponentListMFR.RIVET);

		time = 10;
		KnowledgeListMFR.cogPulleyR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(ComponentListMFR.COGWORK_PULLEY), "cogArmour", false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "RFR", "GBG", "RFR", 'B', Blocks.REDSTONE_BLOCK, 'F', ComponentListMFR.IRON_FRAME, 'R', ComponentListMFR.RIVET, 'G', ComponentListMFR.TUNGSTEN_GEARS);
		Salvage.addSalvage(ComponentListMFR.COGWORK_PULLEY, Blocks.REDSTONE_BLOCK, new ItemStack(ComponentListMFR.IRON_FRAME, 2), new ItemStack(ComponentListMFR.RIVET, 2), new ItemStack(ComponentListMFR.TUNGSTEN_GEARS, 2));
		Salvage.addSalvage(MineFantasyBlocks.FRAME_BLOCK, ComponentListMFR.IRON_FRAME, ComponentListMFR.RIVET);

		time = 10;
		KnowledgeListMFR.cogHelmR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(MineFantasyBlocks.BLOCKCOGWORK_HELM), "cogArmour", false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "RFFR", "SEES", " RR ", 'E', Items.ENDER_EYE, 'F', ComponentListMFR.IRON_FRAME, 'R', ComponentListMFR.RIVET, 'S', ComponentListMFR.COGWORK_SHAFT);
		Salvage.addSalvage(MineFantasyBlocks.BLOCKCOGWORK_HELM, new ItemStack(Items.ENDER_EYE, 2), new ItemStack(ComponentListMFR.IRON_FRAME, 2), new ItemStack(ComponentListMFR.COGWORK_SHAFT, 2), new ItemStack(ComponentListMFR.RIVET, 4));
		time = 15;
		KnowledgeListMFR.cogChestR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(MineFantasyBlocks.BLOCKCOGWORK_CHESTPLATE), "cogArmour", false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " RFR ", "RSFSR", "RFBFR", " SFS ", 'F', ComponentListMFR.IRON_FRAME, 'R', ComponentListMFR.RIVET, 'S', ComponentListMFR.COGWORK_SHAFT, 'B', Blocks.FURNACE);
		Salvage.addSalvage(MineFantasyBlocks.BLOCKCOGWORK_CHESTPLATE, Blocks.FURNACE, new ItemStack(ComponentListMFR.IRON_FRAME, 5), new ItemStack(ComponentListMFR.COGWORK_SHAFT, 4), new ItemStack(ComponentListMFR.RIVET, 6));

		time = 10;
		KnowledgeListMFR.cogLegsR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(MineFantasyBlocks.BLOCKCOGWORK_LEGS), "cogArmour", false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "RFFFR", "RS SR", " S S ", " F F ", 'F', ComponentListMFR.IRON_FRAME, 'R', ComponentListMFR.RIVET, 'S', ComponentListMFR.COGWORK_SHAFT);
		Salvage.addSalvage(MineFantasyBlocks.BLOCKCOGWORK_LEGS, new ItemStack(ComponentListMFR.IRON_FRAME, 5), new ItemStack(ComponentListMFR.COGWORK_SHAFT, 4), new ItemStack(ComponentListMFR.RIVET, 4));

	}
}
