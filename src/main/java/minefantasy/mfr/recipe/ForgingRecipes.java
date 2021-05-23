package minefantasy.mfr.recipe;

import minefantasy.mfr.api.MineFantasyRebornAPI;
import minefantasy.mfr.api.crafting.Salvage;
import minefantasy.mfr.config.ConfigCrafting;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.constants.Constants;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.init.LeatherArmourListMFR;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasyKnowledgeList;
import minefantasy.mfr.init.MineFantasyMaterials;
import minefantasy.mfr.item.ItemMetalComponent;
import minefantasy.mfr.material.BaseMaterial;
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

		ItemStack ironbar = MineFantasyItems.bar("Iron");
		ItemStack steelbar = MineFantasyItems.bar("Steel");
		ItemStack goldbar = MineFantasyItems.bar("Gold");
		ItemStack silverbar = MineFantasyItems.bar("Silver");

		// MISC
		BaseMaterial material;
		int time;
		time = 1;
		material = MineFantasyMaterials.ENCRUSTED;

		MineFantasyKnowledgeList.obsidianHunkR = MineFantasyRebornAPI.addAnvilRecipe(null, new ItemStack(MineFantasyItems.OBSIDIAN_ROCK, 4), "", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "D", 'D', Blocks.OBSIDIAN);
		MineFantasyKnowledgeList.diamondR = MineFantasyRebornAPI.addAnvilRecipe(null, new ItemStack(MineFantasyItems.DIAMOND_SHARDS), "", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "D", 'D', Items.DIAMOND);

		time = 3;
		MineFantasyKnowledgeList.encrustedR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, MineFantasyItems.bar("Encrusted"), "smeltEncrusted", true, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "D", "I", 'D', MineFantasyItems.DIAMOND_SHARDS, 'I', MineFantasyItems.bar("Steel"));
		Salvage.addSalvage(MineFantasyItems.ENCRUSTED_INGOT, MineFantasyItems.STEEL_INGOT, MineFantasyItems.DIAMOND_SHARDS);
		Salvage.addSalvage(MineFantasyItems.bar("Encrusted"), MineFantasyItems.STEEL_INGOT,
				MineFantasyItems.DIAMOND_SHARDS);

		material = MineFantasyMaterials.PIG_IRON;
		MineFantasyKnowledgeList.steelR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, MineFantasyItems.bar("Steel"), "smeltSteel", true, 1, 1, 5, "C", "H", 'C', MineFantasyItems.COAL_DUST, 'H', MineFantasyItems.bar("PigIron"));
		MineFantasyKnowledgeList.fluxR = MineFantasyRebornAPI.addAnvilRecipe(null, new ItemStack(MineFantasyItems.FLUX, 4), "", false, -1, -1, 2, "H", 'H', MineFantasyBlocks.LIMESTONE);

		// STUDDED
		material = MineFantasyMaterials.IRON;
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

		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 3, 0), LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 2, 0), new ItemStack(MineFantasyItems.RIVET, 4));
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 3, 1), LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 2, 1), new ItemStack(MineFantasyItems.RIVET, 4));
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 3, 2), LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 2, 2), new ItemStack(MineFantasyItems.RIVET, 4));
		Salvage.addSalvage(LeatherArmourListMFR.armourItem(LeatherArmourListMFR.LEATHER, 3, 3), LeatherArmourListMFR.armour(LeatherArmourListMFR.LEATHER, 2, 3), new ItemStack(MineFantasyItems.RIVET, 4));

		time = 2;
		material = MineFantasyMaterials.IRON;
		if (ConfigCrafting.allowIronResmelt) {
			MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.PREPARED_IRON), "blastfurn", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "IFI", 'I', MineFantasyItems.bar("iron"), 'F', MineFantasyItems.FLUX);
			MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.PREPARED_IRON, 2), "blastfurn", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "IFI", 'I', MineFantasyItems.bar("iron"), 'F', MineFantasyItems.FLUX_STRONG);
		}
		MineFantasyKnowledgeList.coalPrepR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(MineFantasyItems.PREPARED_COAL), "coke", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "RCR", "CFC", "RCR", 'R', Items.REDSTONE, 'C', new ItemStack(Items.COAL, 1, 1), 'F', MineFantasyItems.FLUX_STRONG);
		GameRegistry.addSmelting(MineFantasyItems.PREPARED_COAL, new ItemStack(MineFantasyItems.COKE), 1F);

		MineFantasyKnowledgeList.ironPrepR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.PREPARED_IRON), "blastfurn", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "IFI", 'I', Blocks.IRON_ORE, 'F', MineFantasyItems.FLUX);
		MineFantasyKnowledgeList.ironPrepR2 = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.PREPARED_IRON, 2), "blastfurn", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "IFI", 'I', Blocks.IRON_ORE, 'F', MineFantasyItems.FLUX_STRONG);

		MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.PREPARED_IRON), "blastfurn", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "IFI", 'I', MineFantasyItems.ORE_IRON, 'F', MineFantasyItems.FLUX);
		MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.PREPARED_IRON, 2), "blastfurn", false, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "IFI", 'I', MineFantasyItems.ORE_IRON, 'F', MineFantasyItems.FLUX_STRONG);
		ItemStack plate = ((ItemMetalComponent)MineFantasyItems.PLATE).createComponentItemStack("iron");
		time = 10;
		MineFantasyKnowledgeList.blastChamR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyBlocks.BLAST_CHAMBER), "blastfurn", false, "heavy_hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "RP PR", "RP PR", "RP PR", "RP PR", 'R', MineFantasyItems.RIVET, 'P', plate);

		time = 15;
		MineFantasyKnowledgeList.blastHeatR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyBlocks.BLAST_HEATER), "blastfurn", false, "heavy_hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "RP PR", "RP PR", "RP PR", "RPFPR", 'R', MineFantasyItems.RIVET, 'P', plate, 'F', Blocks.FURNACE);

		Salvage.addSalvage(MineFantasyBlocks.BLAST_HEATER, new ItemStack(MineFantasyItems.RIVET, 8), plate, plate, plate,
				plate, plate, plate, plate, plate, Blocks.FURNACE);
		Salvage.addSalvage(MineFantasyBlocks.BLAST_CHAMBER, new ItemStack(MineFantasyItems.RIVET, 8), plate, plate, plate,
				plate, plate, plate, plate, plate);

		time = 10;
		MineFantasyKnowledgeList.bigFurnR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyBlocks.FURNACE_STONE), "bigfurn", false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "PRP", "RCR", "PFP", 'F', Blocks.FURNACE, 'R', MineFantasyItems.RIVET, 'P', plate, 'C', MineFantasyBlocks.FIREBRICKS);
		time = 10;
		MineFantasyKnowledgeList.bigHeatR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyBlocks.FURNACE_HEATER), "bigfurn", false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "RCR", "PFP", 'R', MineFantasyItems.RIVET, 'P', plate, 'C', MineFantasyBlocks.FIREBRICKS, 'F', MineFantasyBlocks.FORGE);

		Salvage.addSalvage(MineFantasyBlocks.FURNACE_HEATER, new ItemStack(MineFantasyItems.RIVET, 2), plate, plate, MineFantasyBlocks.FIREBRICKS, MineFantasyBlocks.FORGE);
		Salvage.addSalvage(MineFantasyBlocks.FURNACE_STONE, new ItemStack(MineFantasyItems.RIVET, 3), plate, plate, plate, plate, MineFantasyBlocks.FIREBRICKS, Blocks.FURNACE);

		material = MineFantasyBlocks.ANVIL_BRONZE.material;
		IAnvilRecipe bronze_anvil_recipe = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyBlocks.ANVIL_BRONZE), "smelt" + material.name, false, "hammer", -1, -1, (int) (time * material.craftTimeModifier), " II", "III", " I ", 'I', MineFantasyItems.bar(material.name));
		Salvage.addSalvage(MineFantasyBlocks.ANVIL_BRONZE, MineFantasyItems.bar(material.name, 6));

		material = MineFantasyBlocks.ANVIL_IRON.material;
		IAnvilRecipe iron_anvil_recipe = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyBlocks.ANVIL_IRON), "smelt" + material.name, false, "hammer", -1, -1, (int) (time * material.craftTimeModifier), " II", "III", " I ", 'I', MineFantasyItems.bar(material.name));
		Salvage.addSalvage(MineFantasyBlocks.ANVIL_IRON, MineFantasyItems.bar(material.name, 6));

		material = MineFantasyBlocks.ANVIL_STEEL.material;
		IAnvilRecipe steel_anvil_recipe = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyBlocks.ANVIL_STEEL), "smelt" + material.name, false, "hammer", -1, -1, (int) (time * material.craftTimeModifier), " II", "III", " I ", 'I', MineFantasyItems.bar(material.name));
		Salvage.addSalvage(MineFantasyBlocks.ANVIL_STEEL, MineFantasyItems.bar(material.name, 6));

		material = MineFantasyBlocks.ANVIL_BLACK_STEEL.material;
		IAnvilRecipe black_steel_anvil_recipe = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyBlocks.ANVIL_BLACK_STEEL), "smelt" + material.name, false, "hammer", -1, -1, (int) (time * material.craftTimeModifier), " II", "III", " I ", 'I', MineFantasyItems.bar(material.name));
		Salvage.addSalvage(MineFantasyBlocks.ANVIL_BLACK_STEEL, MineFantasyItems.bar(material.name, 6));

		material = MineFantasyBlocks.ANVIL_BLUE_STEEL.material;
		IAnvilRecipe blue_steel_anvil_recipe = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyBlocks.ANVIL_BLUE_STEEL), "smelt" + material.name, false, "hammer", -1, -1, (int) (time * material.craftTimeModifier), " II", "III", " I ", 'I', MineFantasyItems.bar(material.name));
		Salvage.addSalvage(MineFantasyBlocks.ANVIL_BLUE_STEEL, MineFantasyItems.bar(material.name, 6));

		material = MineFantasyBlocks.ANVIL_RED_STEEL.material;
		IAnvilRecipe red_steel_recipe = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyBlocks.ANVIL_RED_STEEL), "smelt" + material.name, false, "hammer", -1, -1, (int) (time * material.craftTimeModifier), " II", "III", " I ", 'I', MineFantasyItems.bar(material.name));
		Salvage.addSalvage(MineFantasyBlocks.ANVIL_RED_STEEL, MineFantasyItems.bar(material.name, 6));

		recipeMap.put("anvilBronze", bronze_anvil_recipe);
		recipeMap.put("anvilIron", iron_anvil_recipe);
		recipeMap.put("anvilSteel", steel_anvil_recipe);
		recipeMap.put("anvilBlackSteel", black_steel_anvil_recipe);
		recipeMap.put("anvilBlueSteel", blue_steel_anvil_recipe);
		recipeMap.put("anvilRedSteel", red_steel_recipe);

		ItemStack bronzeHunk = ((ItemMetalComponent)MineFantasyItems.METAL_HUNK).createComponentItemStack("bronze");
		ItemStack ironHunk = ((ItemMetalComponent)MineFantasyItems.METAL_HUNK).createComponentItemStack("iron");

		time = 2;
		material = MineFantasyMaterials.BRONZE;
		MineFantasyKnowledgeList.framedStoneR = MineFantasyRebornAPI.addAnvilRecipe(Skill.CONSTRUCTION, new ItemStack(MineFantasyBlocks.REINFORCED_STONE_FRAMED), "decorated_stone", false, "hammer", material.hammerTier - 1, material.anvilTier - 1, (int) (time * material.craftTimeModifier), " N ", "NSN", " N ", 'N', bronzeHunk, 'S', MineFantasyBlocks.REINFORCED_STONE);
		Salvage.addSalvage(MineFantasyBlocks.REINFORCED_STONE_FRAMED, bronzeHunk, bronzeHunk, bronzeHunk, bronzeHunk,
				MineFantasyBlocks.REINFORCED_STONE);
		time = 2;
		material = MineFantasyMaterials.IRON;
		MineFantasyKnowledgeList.iframedStoneR = MineFantasyRebornAPI.addAnvilRecipe(Skill.CONSTRUCTION, new ItemStack(MineFantasyBlocks.REINFORCED_STONE_FRAMED_IRON), "decorated_stone", false, "hammer", material.hammerTier - 1, material.anvilTier - 1, (int) (time * material.craftTimeModifier), " N ", "NSN", " N ", 'N', ironHunk, 'S', MineFantasyBlocks.REINFORCED_STONE);
		Salvage.addSalvage(MineFantasyBlocks.REINFORCED_STONE_FRAMED_IRON, ironHunk, ironHunk, ironHunk, ironHunk,
				MineFantasyBlocks.REINFORCED_STONE);

		time = 2;
		material = MineFantasyMaterials.BRONZE;
		MineFantasyKnowledgeList.smokePipeR = MineFantasyRebornAPI.addAnvilRecipe(Skill.CONSTRUCTION, new ItemStack(MineFantasyBlocks.CHIMNEY_PIPE, 4), "", false, "hammer", material.hammerTier - 1, material.anvilTier - 1, (int) (time * material.craftTimeModifier), "N  N", "PPPP", "N  N", 'N', bronzeHunk, 'P', MineFantasyBlocks.CHIMNEY_STONE);
		Salvage.addSalvage(MineFantasyBlocks.CHIMNEY_PIPE, MineFantasyBlocks.CHIMNEY_STONE, bronzeHunk);

		material = MineFantasyBlocks.BRONZE_BARS.material;
		MineFantasyKnowledgeList.barsR.add(MineFantasyRebornAPI.addAnvilRecipe(Skill.CONSTRUCTION, new ItemStack(MineFantasyBlocks.BRONZE_BARS), "smelt" + material.name, false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "I I", "I I", 'I', bronzeHunk));
		Salvage.addSalvage(MineFantasyBlocks.BRONZE_BARS, bronzeHunk, bronzeHunk, bronzeHunk, bronzeHunk);

		material = MineFantasyBlocks.IRON_BARS.material;
		MineFantasyKnowledgeList.barsR.add(MineFantasyRebornAPI.addAnvilRecipe(Skill.CONSTRUCTION, new ItemStack(MineFantasyBlocks.IRON_BARS), "smelt" + material.name, false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "I I", "I I", 'I', ironHunk));
		Salvage.addSalvage(MineFantasyBlocks.IRON_BARS, ironHunk, ironHunk, ironHunk, ironHunk);

		material = MineFantasyBlocks.STEEL_BARS.material;
		ItemStack steelHunk = ((ItemMetalComponent)MineFantasyItems.METAL_HUNK).createComponentItemStack(material.name);
		MineFantasyKnowledgeList.barsR.add(MineFantasyRebornAPI.addAnvilRecipe(Skill.CONSTRUCTION, new ItemStack(MineFantasyBlocks.STEEL_BARS), "smelt" + material.name, false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "I I", "I I", 'I', steelHunk));
		Salvage.addSalvage(MineFantasyBlocks.STEEL_BARS, steelHunk, steelHunk, steelHunk, steelHunk);

		material = MineFantasyBlocks.BLACK_STEEL_BARS.material;
		ItemStack blackSteelHunk = ((ItemMetalComponent)MineFantasyItems.METAL_HUNK).createComponentItemStack(material.name);
		MineFantasyKnowledgeList.barsR.add(MineFantasyRebornAPI.addAnvilRecipe(Skill.CONSTRUCTION, new ItemStack(MineFantasyBlocks.BLACK_STEEL_BARS), "smelt" + material.name, false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "I I", "I I", 'I', blackSteelHunk));
		Salvage.addSalvage(MineFantasyBlocks.BLACK_STEEL_BARS, blackSteelHunk, blackSteelHunk, blackSteelHunk, blackSteelHunk);

		material = MineFantasyBlocks.BLUE_STEEL_BARS.material;
		ItemStack blueSteelHunk = ((ItemMetalComponent)MineFantasyItems.METAL_HUNK).createComponentItemStack(material.name);
		MineFantasyKnowledgeList.barsR.add(MineFantasyRebornAPI.addAnvilRecipe(Skill.CONSTRUCTION, new ItemStack(MineFantasyBlocks.BLUE_STEEL_BARS), "smelt" + material.name, false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "I I", "I I", 'I', blueSteelHunk));
		Salvage.addSalvage(MineFantasyBlocks.BLUE_STEEL_BARS, blueSteelHunk, blueSteelHunk, blueSteelHunk, blueSteelHunk);

		material = MineFantasyBlocks.RED_STEEL_BARS.material;
		ItemStack redSteelHunk = ((ItemMetalComponent)MineFantasyItems.METAL_HUNK).createComponentItemStack(material.name);
		MineFantasyKnowledgeList.barsR.add(MineFantasyRebornAPI.addAnvilRecipe(Skill.CONSTRUCTION, new ItemStack(MineFantasyBlocks.RED_STEEL_BARS), "smelt" + material.name, false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "I I", "I I", 'I', redSteelHunk));
		Salvage.addSalvage(MineFantasyBlocks.RED_STEEL_BARS, redSteelHunk, redSteelHunk, redSteelHunk, redSteelHunk);

		if (!ConfigHardcore.HCCRemoveTalismansCraft) {
			MineFantasyKnowledgeList.talismanRecipe.add(MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.TALISMAN_LESSER), "", true, "hammer", -1, -1, 20, "LGL", "GIG", " G ", 'L', new ItemStack(Items.DYE, 1, 4), 'I', ironbar, 'G', goldbar));
			MineFantasyKnowledgeList.talismanRecipe.add(MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.TALISMAN_LESSER), "", true, "hammer", -1, -1, 20, "LSL", "SIS", " S ", 'L', new ItemStack(Items.DYE, 1, 4), 'I', ironbar, 'S', silverbar));
			MineFantasyKnowledgeList.greatTalismanRecipe = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.TALISMAN_GREATER), "", true, "hammer", 1, 1, 50, "GSG", "DTD", "GDG", 'G', goldbar, 'D', Items.DIAMOND, 'T', MineFantasyItems.TALISMAN_LESSER, 'S', Items.NETHER_STAR);
		}

		addEngineering();
		addConstruction();

		time = 10;
		material = MineFantasyMaterials.IRON;
		MineFantasyKnowledgeList.caketinRecipe = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.CAKE_TIN), "", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " R ", "I I", " I ", 'I', ironbar, 'R', MineFantasyItems.RIVET);
		Salvage.addSalvage(MineFantasyItems.CAKE_TIN, MineFantasyItems.bar("Iron", 3), MineFantasyItems.RIVET);

		MineFantasyKnowledgeList.coalfluxR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.COAL_FLUX, 2), "coalflux", false, material.hammerTier, material.anvilTier, 2, "F", "C", 'C', Items.COAL, 'F', MineFantasyItems.FLUX_POT);

		time = 4;
		material = MineFantasyMaterials.IRON;
		MineFantasyKnowledgeList.hingeRecipe = MineFantasyRebornAPI.addAnvilRecipe(Skill.CONSTRUCTION, new ItemStack(MineFantasyItems.HINGE), "", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "LR", 'L', MineFantasyItems.LEATHER_STRIP, 'R', MineFantasyItems.RIVET);
		Salvage.addSalvage(MineFantasyItems.HINGE, MineFantasyItems.LEATHER_STRIP, MineFantasyItems.RIVET);

		time = 10;
		MineFantasyKnowledgeList.crestR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ARTISANRY, new ItemStack(MineFantasyItems.ORNATE_ITEMS), "craftOrnate", false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " G ", "SLS", " G ", 'G', goldbar, 'S', silverbar, 'L', new ItemStack(Items.DYE, 1, 4));
	}

	private static Item getStrips(BaseMaterial material) {
		return MineFantasyItems.LEATHER_STRIP;
	}

	private static Item getLeather(BaseMaterial material) {
		return Items.LEATHER;
	}

	private static void addEngineering() {
		ItemStack copper = MineFantasyItems.bar("Copper");
		ItemStack bronze = MineFantasyItems.bar("Bronze");
		ItemStack iron = MineFantasyItems.bar("Iron");
		ItemStack steel = MineFantasyItems.bar("Steel");
		ItemStack tungsten = MineFantasyItems.bar("Tungsten");
		ItemStack blacksteel = MineFantasyItems.bar("BlackSteel");

		ItemStack bronzeHunk = ((ItemMetalComponent)MineFantasyItems.METAL_HUNK).createComponentItemStack("bronze");
		ItemStack ironHunk = ((ItemMetalComponent)MineFantasyItems.METAL_HUNK).createComponentItemStack("iron");
		ItemStack steelHunk = ((ItemMetalComponent)MineFantasyItems.METAL_HUNK).createComponentItemStack("steel");
		ItemStack tungstenHunk = ((ItemMetalComponent)MineFantasyItems.METAL_HUNK).createComponentItemStack("tungsten");
		ItemStack obsidianHunk = ((ItemMetalComponent)MineFantasyItems.METAL_HUNK).createComponentItemStack("obsidian");

		BaseMaterial material = MineFantasyMaterials.STEEL;
		int time = 15;
		MineFantasyKnowledgeList.eatoolsR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(MineFantasyItems.ENGIN_ANVIL_TOOLS), "etools", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "SSLL", "LLSS", 'S', steelHunk, 'L', getStrips(material));
		time = 5;
		MineFantasyKnowledgeList.iframeR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(MineFantasyItems.IRON_FRAME), "ecomponents", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "RRR", "ISI", "STS", "ISI", 'T', MineFantasyItems.ENGIN_ANVIL_TOOLS, 'R', MineFantasyItems.RIVET, 'I', ironHunk, 'S', steelHunk);
		time = 8;
		MineFantasyKnowledgeList.istrutR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(MineFantasyItems.IRON_STRUT), "ecomponents", true, "heavy_hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "RTR", "SIS", "SIS", 'T', MineFantasyItems.ENGIN_ANVIL_TOOLS, 'R', MineFantasyItems.RIVET, 'I', iron, 'S', steelHunk);
		time = 8;
		MineFantasyKnowledgeList.stubeR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(MineFantasyItems.STEEL_TUBE), "ecomponents", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "TR R", "SSSS", 'T', MineFantasyItems.ENGIN_ANVIL_TOOLS, 'R', MineFantasyItems.RIVET, 'S', steelHunk);
		time = 2;
		MineFantasyKnowledgeList.boltR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(MineFantasyItems.BOLT, 2), "etools", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " T ", "SIS", " S ", " S ", 'T', MineFantasyItems.ENGIN_ANVIL_TOOLS, 'I', iron, 'S', steelHunk);
		time = 35;
		MineFantasyKnowledgeList.climbPickbR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(MineFantasyItems.CLIMBING_PICK_BASIC), "climber", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "L SR", "IISR", "L T ", 'R', MineFantasyItems.RIVET, 'T', MineFantasyItems.ENGIN_ANVIL_TOOLS, 'I', iron, 'S', steel, 'L', getStrips(material));
		time = 5;
		MineFantasyKnowledgeList.bgearR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(MineFantasyItems.BRONZE_GEARS), "ecomponents", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " T ", " B ", "BIB", " B ", 'T', MineFantasyItems.ENGIN_ANVIL_TOOLS, 'I', iron, 'B', bronzeHunk);
		time = 8;
		material = MineFantasyMaterials.TUNGSTEN;
		MineFantasyKnowledgeList.tgearR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(MineFantasyItems.TUNGSTEN_GEARS), "tungsten", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " T ", " W ", "WGW", " W ", 'T', MineFantasyItems.ENGIN_ANVIL_TOOLS, 'W', tungstenHunk, 'G', MineFantasyItems.BRONZE_GEARS);
		time = 5;
		material = MineFantasyMaterials.COMPOSITE_ALLOY;
		MineFantasyKnowledgeList.compPlateR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, MineFantasyItems.bar("CompositeAlloy"), "cogArmour", true, "heavy_hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " T ", " S ", "RWR", " C ", 'R', MineFantasyItems.RIVET, 'T', MineFantasyItems.ENGIN_ANVIL_TOOLS, 'C', copper, 'W', tungstenHunk, 'S', steel);
		material = MineFantasyMaterials.IRON;

		time = 5;
		MineFantasyKnowledgeList.bombCaseIronR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(MineFantasyItems.BOMB_CASING_IRON, 2), "bombIron", true, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " T ", " I ", "IRI", " I ", 'T', MineFantasyItems.ENGIN_ANVIL_TOOLS, 'I', ironHunk, 'R', MineFantasyItems.RIVET);
		MineFantasyKnowledgeList.mineCaseIronR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(MineFantasyItems.MINE_CASING_IRON, 2), "bombIron", true, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "  T  ", "  P  ", " IRI ", "IR RI", 'T', MineFantasyItems.ENGIN_ANVIL_TOOLS, 'P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 'I', ironHunk, 'R', MineFantasyItems.RIVET);

		time = 5;
		MineFantasyKnowledgeList.bombarrowR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(MineFantasyItems.BOMB_CASING_ARROW), "bombarrow", true, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "   IR", "FPITI", "   IR", 'T', MineFantasyItems.ENGIN_ANVIL_TOOLS, 'I', ironHunk, 'R', Items.REDSTONE, 'P', MineFantasyItems.TIMBER.construct(Constants.REFINED_WOOD_TAG), 'F', MineFantasyItems.FLETCHING);
		MineFantasyKnowledgeList.bombBoltR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(MineFantasyItems.BOMB_CASING_BOLT), "bombarrow", true, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "  IR", "FITI", "  IR", 'T', MineFantasyItems.ENGIN_ANVIL_TOOLS, 'I', ironHunk, 'R', Items.REDSTONE, 'F', MineFantasyItems.FLETCHING);

		material = MineFantasyMaterials.BLACK_STEEL;

		time = 5;
		MineFantasyKnowledgeList.bombCaseObsidianR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(MineFantasyItems.BOMB_CASING_OBSIDIAN, 2), "bombObsidian", true, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " T ", "RIR", "IOI", "RIR", 'T', MineFantasyItems.ENGIN_ANVIL_TOOLS, 'O', Blocks.OBSIDIAN, 'I', obsidianHunk, 'R', MineFantasyItems.RIVET);
		MineFantasyKnowledgeList.mineCaseObsidianR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(MineFantasyItems.MINE_CASING_OBSIDIAN, 2), "mineObsidian", true, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "  T  ", "  P  ", " IRI ", "IRORI", 'T', MineFantasyItems.ENGIN_ANVIL_TOOLS, 'O', Blocks.OBSIDIAN, 'P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 'I', obsidianHunk, 'R', MineFantasyItems.RIVET);
		time = 15;
		material = MineFantasyMaterials.STEEL;
		MineFantasyKnowledgeList.crossBayonetR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(MineFantasyItems.CROSSBOW_BAYONET), "crossBayonet", true, material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "R R I", "PIII ", 'P', MineFantasyItems.TIMBER.construct(Constants.REFINED_WOOD_TAG), 'I', ironHunk, 'R', MineFantasyItems.RIVET);

		Salvage.addSalvage(MineFantasyItems.ENGIN_ANVIL_TOOLS, steelHunk, steelHunk, steelHunk, steelHunk, new ItemStack(MineFantasyItems.LEATHER_STRIP, 4));

		Salvage.addSalvage(MineFantasyItems.IRON_FRAME, steelHunk, steelHunk, steelHunk, steelHunk, ironHunk, ironHunk, ironHunk, ironHunk, new ItemStack(MineFantasyItems.RIVET, 3));

		Salvage.addSalvage(MineFantasyItems.IRON_STRUT, steelHunk, steelHunk, steelHunk, steelHunk, MineFantasyItems.bar("Iron", 2), new ItemStack(MineFantasyItems.RIVET, 2));

		Salvage.addSalvage(MineFantasyItems.STEEL_TUBE, steelHunk, steelHunk, steelHunk, steelHunk, new ItemStack(MineFantasyItems.RIVET, 2));

		Salvage.addSalvage(MineFantasyItems.CLIMBING_PICK_BASIC, MineFantasyItems.bar("Iron", 2), MineFantasyItems.bar("Steel", 2), new ItemStack(MineFantasyItems.RIVET, 2), new ItemStack(MineFantasyItems.LEATHER_STRIP, 2));

		Salvage.addSalvage(MineFantasyItems.BRONZE_GEARS, bronzeHunk, bronzeHunk, bronzeHunk, bronzeHunk, iron);

		Salvage.addSalvage(MineFantasyItems.TUNGSTEN_GEARS, tungstenHunk, tungstenHunk, tungstenHunk, tungstenHunk, MineFantasyItems.BRONZE_GEARS);

		Salvage.addSalvage(MineFantasyItems.bar("CompositeAlloy"), copper, steel, tungstenHunk, new ItemStack(MineFantasyItems.RIVET, 2));
		Salvage.addSalvage(MineFantasyItems.COMPOSITE_ALLOY_INGOT, copper, steel, tungstenHunk, new ItemStack(MineFantasyItems.RIVET, 2));

		Salvage.addSalvage(MineFantasyItems.BOMB_CASING_IRON, ironHunk, ironHunk);
		Salvage.addSalvage(MineFantasyItems.MINE_CASING_IRON, ironHunk, ironHunk, MineFantasyItems.RIVET, iron);
		Salvage.addSalvage(MineFantasyItems.BOMB_CASING_ARROW, ironHunk, ironHunk, ironHunk, ironHunk, new ItemStack(Items.REDSTONE, 2), MineFantasyItems.TIMBER.construct(Constants.REFINED_WOOD_TAG), MineFantasyItems.FLETCHING);

		Salvage.addSalvage(MineFantasyItems.BOMB_CASING_BOLT, ironHunk, ironHunk, ironHunk, ironHunk, new ItemStack(Items.REDSTONE, 2), MineFantasyItems.FLETCHING);

		Salvage.addSalvage(MineFantasyItems.BOMB_CASING_OBSIDIAN, obsidianHunk, obsidianHunk, new ItemStack(MineFantasyItems.RIVET, 2));
		Salvage.addSalvage(MineFantasyItems.MINE_CASING_OBSIDIAN, obsidianHunk, obsidianHunk, iron, MineFantasyItems.RIVET);

		Salvage.addSalvage(MineFantasyItems.CROSSBOW_BAYONET, ironHunk, ironHunk, ironHunk, ironHunk, MineFantasyItems.TIMBER.construct(Constants.REFINED_WOOD_TAG), new ItemStack(MineFantasyItems.RIVET, 2));
	}

	private static void addConstruction() {
		BaseMaterial material = MineFantasyMaterials.TIN;
		ItemStack tin = MineFantasyItems.bar("Tin");
		int time = 10;
		MineFantasyKnowledgeList.brushRecipe = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(MineFantasyItems.PAINT_BRUSH), "paint_brush", true, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "W", "I", "P", 'W', Blocks.WOOL, 'I', tin, 'P', MineFantasyItems.TIMBER.construct(Constants.REFINED_WOOD_TAG));

		Salvage.addSalvage(MineFantasyItems.PAINT_BRUSH, Blocks.WOOL, tin, MineFantasyItems.TIMBER.construct(Constants.REFINED_WOOD_TAG));
	}

	private static void addCogworkParts() {
		BaseMaterial material = MineFantasyMaterials.STEEL;
		int time = 1;
		MineFantasyKnowledgeList.frameBlockR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(MineFantasyBlocks.FRAME_BLOCK), "cogArmour", false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "R", "I", 'I', MineFantasyItems.IRON_FRAME, 'R', MineFantasyItems.RIVET);

		Salvage.addSalvage(MineFantasyBlocks.FRAME_BLOCK, MineFantasyItems.IRON_FRAME, MineFantasyItems.RIVET);

		time = 10;
		MineFantasyKnowledgeList.cogPulleyR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(MineFantasyItems.COGWORK_PULLEY), "cogArmour", false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "RFR", "GBG", "RFR", 'B', Blocks.REDSTONE_BLOCK, 'F', MineFantasyItems.IRON_FRAME, 'R', MineFantasyItems.RIVET, 'G', MineFantasyItems.TUNGSTEN_GEARS);
		Salvage.addSalvage(MineFantasyItems.COGWORK_PULLEY, Blocks.REDSTONE_BLOCK, new ItemStack(MineFantasyItems.IRON_FRAME, 2), new ItemStack(MineFantasyItems.RIVET, 2), new ItemStack(MineFantasyItems.TUNGSTEN_GEARS, 2));
		Salvage.addSalvage(MineFantasyBlocks.FRAME_BLOCK, MineFantasyItems.IRON_FRAME, MineFantasyItems.RIVET);

		time = 10;
		MineFantasyKnowledgeList.cogHelmR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(MineFantasyBlocks.BLOCK_COGWORK_HELM), "cogArmour", false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "RFFR", "SEES", " RR ", 'E', Items.ENDER_EYE, 'F', MineFantasyItems.IRON_FRAME, 'R', MineFantasyItems.RIVET, 'S', MineFantasyItems.COGWORK_SHAFT);
		Salvage.addSalvage(MineFantasyBlocks.BLOCK_COGWORK_HELM, new ItemStack(Items.ENDER_EYE, 2), new ItemStack(MineFantasyItems.IRON_FRAME, 2), new ItemStack(MineFantasyItems.COGWORK_SHAFT, 2), new ItemStack(MineFantasyItems.RIVET, 4));
		time = 15;
		MineFantasyKnowledgeList.cogChestR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(MineFantasyBlocks.BLOCK_COGWORK_CHESTPLATE), "cogArmour", false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), " RFR ", "RSFSR", "RFBFR", " SFS ", 'F', MineFantasyItems.IRON_FRAME, 'R', MineFantasyItems.RIVET, 'S', MineFantasyItems.COGWORK_SHAFT, 'B', Blocks.FURNACE);
		Salvage.addSalvage(MineFantasyBlocks.BLOCK_COGWORK_CHESTPLATE, Blocks.FURNACE, new ItemStack(MineFantasyItems.IRON_FRAME, 5), new ItemStack(MineFantasyItems.COGWORK_SHAFT, 4), new ItemStack(MineFantasyItems.RIVET, 6));

		time = 10;
		MineFantasyKnowledgeList.cogLegsR = MineFantasyRebornAPI.addAnvilRecipe(Skill.ENGINEERING, new ItemStack(MineFantasyBlocks.BLOCK_COGWORK_LEGS), "cogArmour", false, "hammer", material.hammerTier, material.anvilTier, (int) (time * material.craftTimeModifier), "RFFFR", "RS SR", " S S ", " F F ", 'F', MineFantasyItems.IRON_FRAME, 'R', MineFantasyItems.RIVET, 'S', MineFantasyItems.COGWORK_SHAFT);
		Salvage.addSalvage(MineFantasyBlocks.BLOCK_COGWORK_LEGS, new ItemStack(MineFantasyItems.IRON_FRAME, 5), new ItemStack(MineFantasyItems.COGWORK_SHAFT, 4), new ItemStack(MineFantasyItems.RIVET, 4));

	}
}
