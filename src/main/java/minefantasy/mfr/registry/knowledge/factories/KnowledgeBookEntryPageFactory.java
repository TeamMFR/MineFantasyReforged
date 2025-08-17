package minefantasy.mfr.registry.knowledge.factories;

import com.google.gson.JsonObject;
import minefantasy.mfr.client.knowledge.EntryPage;
import minefantasy.mfr.client.knowledge.EntryPageBlastFurnace;
import minefantasy.mfr.client.knowledge.EntryPageCrucible;
import minefantasy.mfr.client.knowledge.EntryPageGrind;
import minefantasy.mfr.client.knowledge.EntryPageImage;
import minefantasy.mfr.client.knowledge.EntryPageRecipeAnvil;
import minefantasy.mfr.client.knowledge.EntryPageRecipeBloom;
import minefantasy.mfr.client.knowledge.EntryPageRecipeCarpenter;
import minefantasy.mfr.client.knowledge.EntryPageRecipeKitchenBench;
import minefantasy.mfr.client.knowledge.EntryPageRecipeTanner;
import minefantasy.mfr.client.knowledge.EntryPageRoast;
import minefantasy.mfr.client.knowledge.EntryPageSmelting;
import minefantasy.mfr.client.knowledge.EntryPageText;
import minefantasy.mfr.registry.knowledge.types.KnowledgeBookEntryPageType;
import minefantasy.mfr.registry.recipe.AlloyRecipeBase;
import minefantasy.mfr.registry.recipe.AnvilRecipeBase;
import minefantasy.mfr.registry.recipe.BigFurnaceRecipeBase;
import minefantasy.mfr.registry.recipe.BlastFurnaceRecipeBase;
import minefantasy.mfr.registry.recipe.BloomeryRecipeBase;
import minefantasy.mfr.registry.recipe.CarpenterRecipeBase;
import minefantasy.mfr.registry.recipe.CraftingManagerAlloy;
import minefantasy.mfr.registry.recipe.CraftingManagerAnvil;
import minefantasy.mfr.registry.recipe.CraftingManagerBigFurnace;
import minefantasy.mfr.registry.recipe.CraftingManagerBlastFurnace;
import minefantasy.mfr.registry.recipe.CraftingManagerBloomery;
import minefantasy.mfr.registry.recipe.CraftingManagerCarpenter;
import minefantasy.mfr.registry.recipe.CraftingManagerKitchenBench;
import minefantasy.mfr.registry.recipe.CraftingManagerQuern;
import minefantasy.mfr.registry.recipe.CraftingManagerRoast;
import minefantasy.mfr.registry.recipe.CraftingManagerTanner;
import minefantasy.mfr.registry.recipe.KitchenBenchRecipeBase;
import minefantasy.mfr.registry.recipe.QuernRecipeBase;
import minefantasy.mfr.registry.recipe.RoastRecipeBase;
import minefantasy.mfr.registry.recipe.TannerRecipeBase;
import minefantasy.mfr.registry.recipe.types.RecipeType;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class KnowledgeBookEntryPageFactory {
	public EntryPage parse(JsonContext ctx, JsonObject json) {
		String type = JsonUtils.getString(json, "type");
		KnowledgeBookEntryPageType pageType = KnowledgeBookEntryPageType.deserialize(type);
		switch (pageType) {
			case ENTRY_PAGE_TEXT:
				return parseTextPage(ctx, json);
			case ENTRY_PAGE_IMAGE:
				return parseImagePage(ctx, json);
			case ENTRY_PAGE_RECIPE:
				return parseRecipePage(json);
			default:
				return null;
		}
	}

	private EntryPage parseRecipePage(JsonObject json) {
		RecipeType recipeType = RecipeType.deserialize(JsonUtils.getString(json, "recipe_type"));
		ResourceLocation recipeKey = new ResourceLocation(JsonUtils.getString(json, "recipe"));
		switch (recipeType) {
			case ALLOY_RECIPES:
				 AlloyRecipeBase alloyRecipe = CraftingManagerAlloy.getRecipeByResourceLocation(recipeKey);
				 return new EntryPageCrucible(alloyRecipe);
			case ANVIL_RECIPES:
				AnvilRecipeBase anvilRecipe = CraftingManagerAnvil.getRecipeByResourceLocation(recipeKey);
				return new EntryPageRecipeAnvil(anvilRecipe);
			case BIG_FURNACE_RECIPES:
				BigFurnaceRecipeBase bigFurnaceRecipe = CraftingManagerBigFurnace.getRecipeByResourceLocation(recipeKey);
				return new EntryPageSmelting(bigFurnaceRecipe);
			case BLAST_FURNACE_RECIPES:
				BlastFurnaceRecipeBase blastFurnaceRecipe = CraftingManagerBlastFurnace.getRecipeByResourceLocation(recipeKey);
				return new EntryPageBlastFurnace(blastFurnaceRecipe);
			case BLOOMERY_RECIPES:
				BloomeryRecipeBase bloomeryRecipe = CraftingManagerBloomery.getRecipeByResourceLocation(recipeKey);
				return new EntryPageRecipeBloom(bloomeryRecipe);
			case CARPENTER_RECIPES:
				CarpenterRecipeBase carpenterRecipe = CraftingManagerCarpenter.getRecipeByResourceLocation(recipeKey);
				return new EntryPageRecipeCarpenter(carpenterRecipe);
			case KITCHEN_BENCH_RECIPES:
				KitchenBenchRecipeBase kitchenBenchRecipeBase = CraftingManagerKitchenBench.getRecipeByResourceLocation(recipeKey);
				return new EntryPageRecipeKitchenBench(kitchenBenchRecipeBase);
			case QUERN_RECIPES:
				QuernRecipeBase quernRecipe = CraftingManagerQuern.getRecipeByResourceLocation(recipeKey);
				return new EntryPageGrind(quernRecipe);
			case ROAST_RECIPES:
				RoastRecipeBase roastRecipe = CraftingManagerRoast.getRecipeByResourceLocation(recipeKey);
				return new EntryPageRoast(roastRecipe);
			case TANNER_RECIPES:
				TannerRecipeBase tannerRecipe = CraftingManagerTanner.getRecipeByResourceLocation(recipeKey);
				return new EntryPageRecipeTanner(tannerRecipe);
			case NONE:
				return null;
		}
		return null;
	}

	private EntryPage parseImagePage(JsonContext ctx, JsonObject json) {
		return new EntryPageImage("", "");
	}

	private EntryPage parseTextPage(JsonContext ctx, JsonObject json) {
		return new EntryPageText("");
	}
}
