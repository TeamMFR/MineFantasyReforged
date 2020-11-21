package minefantasy.mfr.recipe;

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
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = MineFantasyReborn.MOD_ID)
public class BasicRecipesMF {

	@SubscribeEvent
	public static void registerItem(RegistryEvent.Register<IRecipe> event) {
		MineFantasyRebornAPI.removeAllRecipes(Items.STICK);
	}

	public static void init() {
		TempRecipesMF.init();// TODO remove temp recipes
		ForgingRecipes.init();
		CarpenterRecipes.init();
		SmeltingRecipesMF.init();
		SalvageRecipes.init();
		CookingRecipes.init();
		DragonforgedStyle.loadCrafting();
		OrnateStyle.loadCrafting();
		// GameRegistry.addRecipe(new RecipeArmourDyeMF()); //TODO Replace with proper recipe JSON

		ArrayList<CustomMaterial> wood = CustomMaterial.getList("wood");

		for (CustomMaterial customMat : wood) {
			assembleWoodVariations(customMat);
		}

//        for (int id = 0; id < BlockListMFR.METAL_BLOCKS.length; id++) {
//            BaseMaterialMFR material = BaseMaterialMFR.getMaterial(BlockListMFR.METAL_BLOCKS[id]);
//
//            for (ItemStack ingot : OreDictionary.getOres("ingot" + material.name)) {
//                //GameRegistry.addRecipe(new ItemStack(BlockListMFR.STORAGE[id]), new Object[]{"III", "III", "III", 'I', ingot});

//                //GameRegistry.addShapelessRecipe(new ItemStack(ingot.getItem(), 9), new Object[]{BlockListMFR.STORAGE[id]});
//            }
//        }
		// TODO Replace with proper recipe JSON for each metal block

		KnowledgeListMFR.fireclayR = MineFantasyRebornAPI.addBasicCarpenterRecipe(new ItemStack(ComponentListMFR.FIRECLAY, 4),
				new Object[]{
						" C ",
						"CDC",
						" C ",
						'D', ComponentListMFR.KAOLINITE_DUST,
						'C', Items.CLAY_BALL});
		KnowledgeListMFR.fireBrickR = MineFantasyRebornAPI.addBasicCarpenterRecipe(new ItemStack(ComponentListMFR.FIRECLAY_BRICK),
				new Object[]{
						"C",
						'C', ComponentListMFR.FIRECLAY});
		KnowledgeListMFR.fireBricksR = MineFantasyRebornAPI.addBasicCarpenterRecipe(new ItemStack(BlockListMFR.FIREBRICKS),
				new Object[]{
						"BB",
						"BB",
						'B', ComponentListMFR.STRONG_BRICK});
		KnowledgeListMFR.fireBrickStairR = MineFantasyRebornAPI.addBasicCarpenterRecipe(new ItemStack(BlockListMFR.FIREBRICK_STAIR),
				new Object[]{
						"B ",
						"BB",
						'B', ComponentListMFR.STRONG_BRICK});
		BaseMaterialMFR mat = BaseMaterialMFR.IRON;

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
			// TODO Replace with proper recipe JSON - this should probably use oredict and might work already
		}
	}
}
