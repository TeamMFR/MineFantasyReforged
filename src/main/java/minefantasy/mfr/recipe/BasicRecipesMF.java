package minefantasy.mfr.recipe;

import java.util.ArrayList;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.MineFantasyRebornAPI;
import minefantasy.mfr.api.crafting.tanning.TanningRecipe;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.init.BlockListMFR;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.DragonforgedStyle;
import minefantasy.mfr.init.KnowledgeListMFR;
import minefantasy.mfr.init.OrnateStyle;
import minefantasy.mfr.init.ToolListMFR;
import minefantasy.mfr.material.BaseMaterialMFR;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistryModifiable;

@Mod.EventBusSubscriber(modid = MineFantasyReborn.MOD_ID)
public class BasicRecipesMF {

	@SubscribeEvent
	public static void registerItem(RegistryEvent.Register<IRecipe> event) {
		for (IRecipe recipe : ForgeRegistries.RECIPES) {
			if (recipe.getRecipeOutput().getItem() == Items.STICK
					&& !ForgeRegistries.RECIPES.getKey(recipe).getResourceDomain().equals(MineFantasyReborn.MOD_ID)) {
				((IForgeRegistryModifiable) ForgeRegistries.RECIPES).remove(ForgeRegistries.RECIPES.getKey(recipe));
			}
		}
	}

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
		// GameRegistry.addRecipe(new RecipeArmourDyeMF()); //TODO Replace with proper
		// recipe JSON

		ArrayList<CustomMaterial> wood = CustomMaterial.getList("wood");

		// KnowledgeListMFR.stickRecipe = GameRegistry.addShapedRecipe(new
		// ItemStack(Items.STICK, 2), new Object[]{"S", 'S', ComponentListMFR.plank});
		// TODO Replace with proper recipe JSON
		// GameRegistry.addShapedRecipe(new ItemStack(Items.STICK, 2), new Object[]{"S",
		// 'S', ComponentListMFR.plank_cut});
		// TODO Replace with proper recipe JSON

		// KnowledgeListMFR.firepitRecipe = GameRegistry.addShapedRecipe(new
		// ItemStack(BlockListMFR.FIREPIT), new Object[]{" P ", "S S", 'S', Items.STICK,
		// 'P', ComponentListMFR.plank});
		// TODO Replace with proper recipe JSON
		// KnowledgeListMFR.cooktopRecipe = GameRegistry.addShapedRecipe(new
		// ItemStack(BlockListMFR.ROAST), new Object[]{"PSP", 'S', Blocks.COBBLESTONE,
		// 'P', ComponentListMFR.plank});
		// TODO Replace with proper recipe JSON

		// GameRegistry.addShapelessRecipe(new ItemStack(BlockListMFR.YEW_PLANKS, 4),
		// new Object[]{BlockListMFR.LOG_YEW});
		// TODO Replace with proper recipe JSON
		// GameRegistry.addShapelessRecipe(new ItemStack(BlockListMFR.IRONBARK_PLANKS,
		// 4), new Object[]{BlockListMFR.LOG_IRONBARK});
		// TODO Replace with proper recipe JSON
		// GameRegistry.addShapelessRecipe(new ItemStack(BlockListMFR.EBONY_PLANKS, 4),
		// new Object[]{BlockListMFR.LOG_EBONY});
		// TODO Replace with proper recipe JSON

		for (CustomMaterial customMat : wood) {
			assembleWoodVariations(customMat);
		}

		// KnowledgeListMFR.plantOilR = GameRegistry.addShapedRecipe(new
		// ItemStack(ComponentListMFR.plant_oil, 4), new Object[]{" B ", "BFB", " B ",
		// 'F', Items.WHEAT_SEEDS, 'B', FoodListMFR.jug_empty});
		// TODO Replace with proper recipe JSON
		// KnowledgeListMFR.waterJugR = GameRegistry.addShapedRecipe(new
		// ItemStack(FoodListMFR.jug_water, 4), new Object[]{" B ", "BWB", " B ", 'W',
		// Items.WATER_BUCKET, 'B', FoodListMFR.jug_empty});
		// TODO Replace with proper recipe JSON
		// KnowledgeListMFR.sugarRecipe = GameRegistry.addShapedRecipe(new
		// ItemStack(FoodListMFR.sugarpot), new Object[]{"S", "S", "B", 'S',
		// Items.SUGAR, 'B', ComponentListMFR.clay_pot,});
		// TODO Replace with proper recipe JSON
		// GameRegistry.addShapelessRecipe(new ItemStack(Items.SUGAR, 2), new
		// Object[]{FoodListMFR.sugarpot,});
		// TODO Replace with proper recipe JSON
		// KnowledgeListMFR.milkJugR = GameRegistry.addShapedRecipe(new
		// ItemStack(FoodListMFR.jug_milk, 4), new Object[]{" B ", "BMB", " B ", 'M',
		// Items.MILK_BUCKET, 'B', FoodListMFR.jug_empty});
		// TODO Replace with proper recipe JSON
		// GameRegistry.addShapedRecipe(new ItemStack(Items.MILK_BUCKET), new Object[]{"
		// B ", "BMB", " B ", 'M', Items.BUCKET, 'B', FoodListMFR.jug_milk});
		// TODO Replace with proper recipe JSON
		// GameRegistry.addShapedRecipe(new ItemStack(Items.WATER_BUCKET), new
		// Object[]{" B ", "BMB", " B ", 'M', Items.BUCKET, 'B',
		// FoodListMFR.jug_water});
		// TODO Replace with proper recipe JSON

		// GameRegistry.addRecipe(new RecipeSyringe());
		// TODO Replace with proper recipe JSON
		// Just a way on making the overpowered gunpowder from black powder
		// GameRegistry.addShapelessRecipe(new ItemStack(Items.GUNPOWDER), new
		// Object[]{new ItemStack(ComponentListMFR.blackpowder), new
		// ItemStack(ComponentListMFR.blackpowder), new
		// ItemStack(ComponentListMFR.nitre),});
		// TODO Replace with proper recipe JSON

//        for (int id = 0; id < BlockListMFR.METAL_BLOCKS.length; id++) {
//            BaseMaterialMFR material = BaseMaterialMFR.getMaterial(BlockListMFR.METAL_BLOCKS[id]);
//
//            for (ItemStack ingot : OreDictionary.getOres("ingot" + material.name)) {
//                //GameRegistry.addRecipe(new ItemStack(BlockListMFR.STORAGE[id]), new Object[]{"III", "III", "III", 'I', ingot});

//                //GameRegistry.addShapelessRecipe(new ItemStack(ingot.getItem(), 9), new Object[]{BlockListMFR.STORAGE[id]});
//            }
//        }
		// TODO Replace with proper recipe JSON for each metal block

		KnowledgeListMFR.fireclayR = MineFantasyRebornAPI.addBasicCarpenterRecipe(
				new ItemStack(ComponentListMFR.FIRECLAY, 4), " C ", "CDC", " C ", 'D', ComponentListMFR.KAOLINITE_DUST,
				'C', Items.CLAY_BALL);
		KnowledgeListMFR.fireBrickR = MineFantasyRebornAPI.addBasicCarpenterRecipe(
				new ItemStack(ComponentListMFR.FIRECLAY_BRICK), "C", 'C', ComponentListMFR.FIRECLAY);
		KnowledgeListMFR.fireBricksR = MineFantasyRebornAPI.addBasicCarpenterRecipe(
				new ItemStack(BlockListMFR.FIREBRICKS), "BB", "BB", 'B', ComponentListMFR.STRONG_BRICK);
		KnowledgeListMFR.fireBrickStairR = MineFantasyRebornAPI.addBasicCarpenterRecipe(
				new ItemStack(BlockListMFR.FIREBRICK_STAIR), "B ", "BB", 'B', ComponentListMFR.STRONG_BRICK);
		BaseMaterialMFR mat = BaseMaterialMFR.iron;

		// GameRegistry.addShapelessRecipe(new ItemStack(ComponentListMFR.hideSmall),
		// new Object[]{ComponentListMFR.rawhideSmall, ComponentListMFR.flux});
		// TODO Replace with proper recipe JSON
		// GameRegistry.addShapelessRecipe(new ItemStack(ComponentListMFR.hideMedium),
		// new Object[]{ComponentListMFR.rawhideMedium, ComponentListMFR.flux});
		// TODO Replace with proper recipe JSON
		// GameRegistry.addShapelessRecipe(new ItemStack(ComponentListMFR.hideLarge),
		// new Object[]{ComponentListMFR.rawhideLarge, ComponentListMFR.flux});
		// TODO Replace with proper recipe JSON

		TanningRecipe.addRecipe(ComponentListMFR.HIDE_SMALL, mat.craftTimeModifier * 2F, -1,
				new ItemStack(Items.LEATHER));
		TanningRecipe.addRecipe(ComponentListMFR.HIDE_MEDIUM, mat.craftTimeModifier * 3F, -1,
				new ItemStack(Items.LEATHER, 3));
		TanningRecipe.addRecipe(ComponentListMFR.HIDE_LARGE, mat.craftTimeModifier * 4F, -1,
				new ItemStack(Items.LEATHER, 5));
		TanningRecipe.addRecipe(Items.LEATHER, mat.craftTimeModifier * 2F, -1, "shears",
				new ItemStack(ComponentListMFR.LEATHER_STRIP, 4));

		if (!ConfigHardcore.HCCRemoveBooksCraft) {
			KnowledgeListMFR.artBookR = MineFantasyRebornAPI.addBasicCarpenterRecipe(
					new ItemStack(ToolListMFR.SKILLBOOK_ARTISANRY), "T", "D", "B", 'T',
					ComponentListMFR.TALISMAN_LESSER, 'D', new ItemStack(Items.DYE, 1, 1), 'B', Items.BOOK);
			KnowledgeListMFR.conBookR = MineFantasyRebornAPI.addBasicCarpenterRecipe(
					new ItemStack(ToolListMFR.SKILLBOOK_CONSTRUCTION), "T", "D", "B", 'T',
					ComponentListMFR.TALISMAN_LESSER, 'D', new ItemStack(Items.DYE, 1, 14), 'B', Items.BOOK);
			KnowledgeListMFR.proBookR = MineFantasyRebornAPI.addBasicCarpenterRecipe(
					new ItemStack(ToolListMFR.SKILLBOOK_PROVISIONING), "T", "D", "B", 'T',
					ComponentListMFR.TALISMAN_LESSER, 'D', new ItemStack(Items.DYE, 1, 2), 'B', Items.BOOK);
			KnowledgeListMFR.engBookR = MineFantasyRebornAPI.addBasicCarpenterRecipe(
					new ItemStack(ToolListMFR.SKILLBOOK_ENGINEERING), "T", "D", "B", 'T',
					ComponentListMFR.TALISMAN_LESSER, 'D', new ItemStack(Items.DYE, 1, 12), 'B', Items.BOOK);
			KnowledgeListMFR.comBookR = MineFantasyRebornAPI.addBasicCarpenterRecipe(
					new ItemStack(ToolListMFR.SKILLBOOK_COMBAT), "T", "D", "B", 'T', ComponentListMFR.TALISMAN_LESSER,
					'D', new ItemStack(Items.DYE, 1, 5), 'B', Items.BOOK);

			KnowledgeListMFR.artBook2R = MineFantasyRebornAPI.addBasicCarpenterRecipe(
					new ItemStack(ToolListMFR.SKILLBOOK_ARTISANRY_MAX), "T", "B", 'T',
					ComponentListMFR.TALISMAN_GREATER, 'B', ToolListMFR.SKILLBOOK_ARTISANRY);
			KnowledgeListMFR.conBook2R = MineFantasyRebornAPI.addBasicCarpenterRecipe(
					new ItemStack(ToolListMFR.SKILLBOOK_CONSTRUCTION_MAX), "T", "B", 'T',
					ComponentListMFR.TALISMAN_GREATER, 'B', ToolListMFR.SKILLBOOK_CONSTRUCTION);
			KnowledgeListMFR.proBook2R = MineFantasyRebornAPI.addBasicCarpenterRecipe(
					new ItemStack(ToolListMFR.SKILLBOOK_PROVISIONING_MAX), "T", "B", 'T',
					ComponentListMFR.TALISMAN_GREATER, 'B', ToolListMFR.SKILLBOOK_PROVISIONING);
			KnowledgeListMFR.engBook2R = MineFantasyRebornAPI.addBasicCarpenterRecipe(
					new ItemStack(ToolListMFR.SKILLBOOK_ENGINEERING_MAX), "T", "B", 'T',
					ComponentListMFR.TALISMAN_GREATER, 'B', ToolListMFR.SKILLBOOK_ENGINEERING);
			KnowledgeListMFR.comBook2R = MineFantasyRebornAPI.addBasicCarpenterRecipe(
					new ItemStack(ToolListMFR.SKILLBOOK_COMBAT_MAX), "T", "B", 'T', ComponentListMFR.TALISMAN_GREATER,
					'B', ToolListMFR.SKILLBOOK_COMBAT);
		}

		// GameRegistry.addShapedRecipe(ComponentListMFR.plank.construct("ScrapWood"),
		// new Object[]{"S", "S", 'S', Items.STICK,});
		// TODO Replace with proper recipe JSON

		Object rock = ConfigHardcore.HCCallowRocks ? ComponentListMFR.SHARP_ROCK : Blocks.COBBLESTONE;
		// KnowledgeListMFR.dryrocksR = GameRegistry.addShapedRecipe(new
		// ItemStack(ToolListMFR.dryrocks), new Object[]{"R ", " R", 'R', rock});
		// TODO Replace with proper recipe JSON

//        ((ConstructionBlockMF.StairsConstBlock) BlockListMFR.MUD_BRICK_STAIR).addRecipe();//TODO Replace with proper recipe JSON
//        ((ConstructionBlockMF.StairsConstBlock) BlockListMFR.MUD_PAVEMENT_STAIR).addRecipe();//TODO Replace with proper recipe JSON
//        ((ConstructionBlockMF.StairsConstBlock) BlockListMFR.COBBLE_BRICK_STAIR).addRecipe();//TODO Replace with proper recipe JSON
//        ((ConstructionBlockMF.StairsConstBlock) BlockListMFR.COBBLE_PAVEMENT_STAIR).addRecipe();//TODO Replace with proper recipe JSON
//        ((ConstructionBlockMF.StairsConstBlock) BlockListMFR.REINFORCED_STONE_BRICK_STAIR).addRecipe();//TODO Replace with proper recipe JSON
//        ((ConstructionBlockMF.StairsConstBlock) BlockListMFR.REINFORCED_STONE_STAIR).addRecipe();//TODO Replace with proper recipe JSON
//
//        ((ConstructionBlockMF.StairsConstBlock) BlockListMFR.YEW_STAIR).addRecipe();//TODO Replace with proper recipe JSON
//        ((ConstructionBlockMF.StairsConstBlock) BlockListMFR.IRONBARK_STAIR).addRecipe();//TODO Replace with proper recipe JSON
//        ((ConstructionBlockMF.StairsConstBlock) BlockListMFR.EBONY_STAIR).addRecipe();//TODO Replace with proper recipe JSON
	}

	private static void assembleWoodVariations(CustomMaterial material) {
		// TODO
		if (material.name != "RefinedWood") {
			NonNullList<ItemStack> list = OreDictionary.getOres("planks" + material.name);
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
					// KnowledgeListMFR.plankRecipe.add(GameRegistry.addShapedRecipe(ComponentListMFR.plank.construct(material.name,
					// 4), new Object[]{"P", "P", 'P', block}));
					// TODO Replace with proper recipe JSON
					CarpenterRecipes.addSawPlanks(block, material);
				}
		}
	}

	private static void tryAddWoodPlanks(ItemStack planks, CustomMaterial material) {
		String sub = material.name.substring(0, material.name.length() - 4).toLowerCase();

		if (planks.getUnlocalizedName().toLowerCase().contains(sub)) {
			// KnowledgeListMFR.plankRecipe.add(GameRegistry.addShapedRecipe(ComponentListMFR.plank.construct(material.name,
			// 4), new Object[]{"P", "P", 'P', planks.copy()}));
			// TODO Replace with proper recipe JSON
		}
	}

	private static void addFoodOutput() {
//        KnowledgeListMFR.cheeseOut = GameRegistry.addShapedRecipe(new ItemStack(BlockListMFR.CHEESE_WHEEL), new Object[]{"F", 'F', FoodListMFR.cheese_pot,});//TODO Replace with proper recipe JSON
//        KnowledgeListMFR.meatpieOut = GameRegistry.addShapedRecipe(new ItemStack(BlockListMFR.PIE_MEAT), new Object[]{"F", 'F', FoodListMFR.pie_meat_cooked});//TODO Replace with proper recipe JSON
//        KnowledgeListMFR.shepardOut = GameRegistry.addShapedRecipe(new ItemStack(BlockListMFR.PIE_SHEPARDS), new Object[]{"F", 'F', FoodListMFR.pie_shepard_cooked});//TODO Replace with proper recipe JSON
//        KnowledgeListMFR.appleOut = GameRegistry.addShapedRecipe(new ItemStack(BlockListMFR.PIE_APPLE), new Object[]{"F", 'F', FoodListMFR.pie_apple_cooked});//TODO Replace with proper recipe JSON
//        KnowledgeListMFR.berryOut = GameRegistry.addShapedRecipe(new ItemStack(BlockListMFR.PIE_BERRY), new Object[]{"F", 'F', FoodListMFR.pie_berry_cooked});//TODO Replace with proper recipe JSON
//        KnowledgeListMFR.pumpPieOut = GameRegistry.addShapedRecipe(new ItemStack(Items.PUMPKIN_PIE), new Object[]{"F", 'F', FoodListMFR.pie_pumpkin_cooked});//TODO Replace with proper recipe JSON
	}
}
