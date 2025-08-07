package minefantasy.mfr.integration.jei;

import mezz.jei.Internal;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IStackHelper;
import mezz.jei.gui.Focus;
import mezz.jei.gui.GuiHelper;
import mezz.jei.runtime.JeiHelpers;
import mezz.jei.startup.StackHelper;
import minefantasy.mfr.config.ConfigIntegration;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.registry.recipe.ingredients.IngredientMaterial;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@JEIPlugin
public class JEIIntegration implements IModPlugin {
	static StackHelper stackHelper;

	public JEIIntegration() {
	}

	@Override
	public void registerCategories(@Nonnull IRecipeCategoryRegistration registry) {

		if (!ConfigIntegration.jeiIntegration) {
			return;
		}

		JeiHelpers jeiHelpers = Internal.getHelpers();
		GuiHelper guiHelper = jeiHelpers.getGuiHelper();
		stackHelper = jeiHelpers.getStackHelper();

		registry.addRecipeCategories(new JEICarpenterRecipeCategory(registry));
		registry.addRecipeCategories(new JEIAnvilRecipeCategory(registry));
		registry.addRecipeCategories(new JEIBigFurnaceRecipeCategory(registry, guiHelper));
		registry.addRecipeCategories(new JEIAlloyRecipeCategory(registry, guiHelper));
		registry.addRecipeCategories(new JEIBloomeryRecipeCategory(registry, guiHelper));
		registry.addRecipeCategories(new JEIBlastFurnaceRecipeCategory(registry, guiHelper));
		registry.addRecipeCategories(new JEIQuernRecipeCategory(registry, guiHelper));
		registry.addRecipeCategories(new JEITannerRecipeCategory(registry));
		registry.addRecipeCategories(new JEIRoastRecipeCategory(registry, guiHelper, 0, MineFantasyBlocks.OVEN, "oven"));
		registry.addRecipeCategories(new JEIRoastRecipeCategory(registry, guiHelper, 56, MineFantasyBlocks.STOVE, "stovetop"));
		registry.addRecipeCategories(new JEIKitchenBenchRecipeCategory(registry));
		registry.addRecipeCategories(new JEISalvageRecipeCategory(registry));
		registry.addRecipeCategories(new JEITransformationRecipeCategory(registry));
		registry.addRecipeCategories(new JEISpecialRecipeCategory(registry));
	}

	@Override
	public void register(@Nonnull IModRegistry registry) {

		if (!ConfigIntegration.jeiIntegration)
			return;

		// UNUSED
		// If we want to hide some items, those can be listed here
		IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();
		// e.g.:
		// blacklist.addIngredientToBlacklist(MineFantasyItems.ANCIENT_JEWEL_ADAMANT);
		// /UNUSED

		IStackHelper stackHelper = registry.getJeiHelpers().getStackHelper();

		List<ItemStack> fuelItemStacks = new ArrayList<>(registry.getIngredientRegistry().getFuels());

		addItemExtendedInfo(registry);

		registry.addRecipes(JEICarpenterRecipeCategory.generateRecipes(stackHelper), JEICarpenterRecipeCategory.UID);
		registry.addRecipes(JEIAnvilRecipeCategory.generateRecipes(stackHelper), JEIAnvilRecipeCategory.UID);
		registry.addRecipes(JEIBigFurnaceRecipeCategory.generateRecipes(stackHelper), JEIBigFurnaceRecipeCategory.UID);
		registry.addRecipes(JEIAlloyRecipeCategory.generateRecipes(stackHelper), JEIAlloyRecipeCategory.UID);
		registry.addRecipes(JEIBloomeryRecipeCategory.generateRecipes(stackHelper, fuelItemStacks), JEIBloomeryRecipeCategory.UID);
		registry.addRecipes(JEIBlastFurnaceRecipeCategory.generateRecipes(stackHelper, fuelItemStacks), JEIBlastFurnaceRecipeCategory.UID);
		registry.addRecipes(JEIQuernRecipeCategory.generateRecipes(stackHelper), JEIQuernRecipeCategory.UID);
		registry.addRecipes(JEITannerRecipeCategory.generateRecipes(stackHelper), JEITannerRecipeCategory.UID);
		registry.addRecipes(JEIRoastRecipeCategory.generateRecipes(stackHelper, true), "minefantasyreforged:oven");
		registry.addRecipes(JEIRoastRecipeCategory.generateRecipes(stackHelper, false), "minefantasyreforged:stovetop");
		registry.addRecipes(JEIKitchenBenchRecipeCategory.generateRecipes(stackHelper), JEIKitchenBenchRecipeCategory.UID);
		registry.addRecipes(JEISalvageRecipeCategory.generateRecipes(stackHelper), JEISalvageRecipeCategory.UID);
		registry.addRecipes(JEITransformationRecipeCategory.generateRecipes(), JEITransformationRecipeCategory.UID);
		registry.addRecipes(JEISpecialRecipeCategory.generateRecipes(stackHelper), JEISpecialRecipeCategory.UID);
	}

	private static void addItemExtendedInfo(IModRegistry registry) {
		addExtendedInfo(registry, MineFantasyItems.FLUX, ".desc_extended");

		addExtendedInfo(registry, MineFantasyItems.BOWL_WATER_SALT, ".desc_extended");

		addExtendedInfo(registry, MineFantasyBlocks.CHEESE_WHEEL, ".desc_extended");
		addExtendedInfo(registry, MineFantasyItems.CHEESE_SLICE, ".desc_extended");

		addExtendedInfo(registry, MineFantasyBlocks.PIE_MEAT, ".desc_extended");
		addExtendedInfo(registry, MineFantasyItems.MEATPIE_SLICE, ".desc_extended");
		addExtendedInfo(registry, MineFantasyBlocks.PIE_APPLE, ".desc_extended");
		addExtendedInfo(registry, MineFantasyItems.PIESLICE_APPLE, ".desc_extended");
		addExtendedInfo(registry, MineFantasyBlocks.PIE_BERRY, ".desc_extended");
		addExtendedInfo(registry, MineFantasyItems.PIESLICE_BERRY, ".desc_extended");
		addExtendedInfo(registry, MineFantasyBlocks.PIE_SHEPARDS, ".desc_extended");
		addExtendedInfo(registry, MineFantasyItems.PIESLICE_SHEPARDS, ".desc_extended");

		addExtendedInfo(registry, MineFantasyBlocks.CAKE_VANILLA, ".desc_extended");
		addExtendedInfo(registry, MineFantasyItems.CAKE_SLICE, ".desc_extended");
		addExtendedInfo(registry, MineFantasyBlocks.CAKE_CARROT, ".desc_extended");
		addExtendedInfo(registry, MineFantasyItems.CARROTCAKE_SLICE, ".desc_extended");
		addExtendedInfo(registry, MineFantasyBlocks.CAKE_CHOCOLATE, ".desc_extended");
		addExtendedInfo(registry, MineFantasyItems.CHOCCAKE_SLICE, ".desc_extended");
		addExtendedInfo(registry, MineFantasyBlocks.CAKE_BF, ".desc_extended");
		addExtendedInfo(registry, MineFantasyItems.BFCAKE_SLICE, ".desc_extended");

		addExtendedInfo(registry, MineFantasyItems.EXPLODING_ARROW, ".desc_extended");
		addExtendedInfo(registry, MineFantasyItems.EXPLODING_BOLT, ".desc_extended");
		addExtendedInfo(registry, MineFantasyItems.BOMB_CASING_ARROW, ".desc_extended");
		addExtendedInfo(registry, MineFantasyItems.BOMB_CASING_BOLT, ".desc_extended");

		addExtendedInfoCustom(registry, MineFantasyItems.BOMB_CUSTOM, "item.bomb_custom.desc_extended");
		addExtendedInfoCustom(registry, MineFantasyItems.MINE_CUSTOM, "item.mine_custom.desc_extended");
		addExtendedInfoCustom(registry, MineFantasyItems.BLACKPOWDER, "item.bomb_component.desc_extended");
		addExtendedInfoCustom(registry, MineFantasyItems.BLACKPOWDER_ADVANCED, "item.bomb_component.desc_extended");
		addExtendedInfoCustom(registry, MineFantasyItems.SHRAPNEL, "item.bomb_component.desc_extended");
		addExtendedInfoCustom(registry, MineFantasyItems.MAGMA_CREAM_REFINED, "item.bomb_component.desc_extended");
		addExtendedInfoCustom(registry, MineFantasyItems.BOMB_FUSE, "item.bomb_component.desc_extended");
		addExtendedInfoCustom(registry, MineFantasyItems.BOMB_FUSE_LONG, "item.bomb_component.desc_extended");
		addExtendedInfoCustom(registry, MineFantasyItems.BOMB_CASING_CERAMIC, "item.bomb_component.desc_extended");
		addExtendedInfoCustom(registry, MineFantasyItems.MINE_CASING_CERAMIC, "item.bomb_component.desc_extended");
		addExtendedInfoCustom(registry, MineFantasyItems.BOMB_CASING_IRON, "item.bomb_component.desc_extended");
		addExtendedInfoCustom(registry, MineFantasyItems.MINE_CASING_IRON, "item.bomb_component.desc_extended");
		addExtendedInfoCustom(registry, MineFantasyItems.BOMB_CASING_OBSIDIAN, "item.bomb_component.desc_extended");
		addExtendedInfoCustom(registry, MineFantasyItems.MINE_CASING_OBSIDIAN, "item.bomb_component.desc_extended");
		addExtendedInfoCustom(registry, MineFantasyItems.BOMB_CASING_CRYSTAL, "item.bomb_component.desc_extended");
		addExtendedInfoCustom(registry, MineFantasyItems.MINE_CASING_CRYSTAL, "item.bomb_component.desc_extended");

		addExtendedInfo(registry, MineFantasyItems.CROSSBOW_CUSTOM, ".desc_extended");
		addExtendedInfoCustom(registry, MineFantasyItems.CROSSBOW_ARMS_BASIC, "item.crossbow_component.desc_extended");
		addExtendedInfoCustom(registry, MineFantasyItems.CROSSBOW_ARMS_LIGHT, "item.crossbow_component.desc_extended");
		addExtendedInfoCustom(registry, MineFantasyItems.CROSSBOW_ARMS_HEAVY, "item.crossbow_component.desc_extended");
		addExtendedInfoCustom(registry, MineFantasyItems.CROSSBOW_ARMS_ADVANCED, "item.crossbow_component.desc_extended");
		addExtendedInfoCustom(registry, MineFantasyItems.CROSSBOW_STOCK_WOOD, "item.crossbow_component.desc_extended");
		addExtendedInfoCustom(registry, MineFantasyItems.CROSSBOW_STOCK_IRON, "item.crossbow_component.desc_extended");
		addExtendedInfoCustom(registry, MineFantasyItems.CROSSBOW_HANDLE_WOOD, "item.crossbow_component.desc_extended");
		addExtendedInfoCustom(registry, MineFantasyItems.CROSSBOW_AMMO, "item.crossbow_component.desc_extended");
		addExtendedInfoCustom(registry, MineFantasyItems.CROSSBOW_SCOPE, "item.crossbow_component.desc_extended");
		addExtendedInfoCustom(registry, MineFantasyItems.CROSSBOW_BAYONET, "item.crossbow_component.desc_extended");
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
		subtypeRegistry.useNbtForSubtypes(
				MineFantasyItems.BAR,
				MineFantasyItems.TIMBER,
				MineFantasyItems.TIMBER_CUT,
				MineFantasyItems.TIMBER_PANE,
				MineFantasyItems.CHAIN_MESH,
				MineFantasyItems.SCALE_MESH,
				MineFantasyItems.SPLINT_MESH,
				MineFantasyItems.PLATE,
				MineFantasyItems.PLATE_HUGE,
				MineFantasyItems.METAL_HUNK,
				MineFantasyItems.ARROWHEAD,
				MineFantasyItems.BODKIN_HEAD,
				MineFantasyItems.BROAD_HEAD,
				MineFantasyItems.COGWORK_ARMOUR,
				MineFantasyItems.STANDARD_SWORD,
				MineFantasyItems.STANDARD_WARAXE,
				MineFantasyItems.STANDARD_MACE,
				MineFantasyItems.STANDARD_DAGGER,
				MineFantasyItems.STANDARD_SPEAR,
				MineFantasyItems.STANDARD_GREATSWORD,
				MineFantasyItems.STANDARD_BATTLEAXE,
				MineFantasyItems.STANDARD_WARHAMMER,
				MineFantasyItems.STANDARD_KATANA,
				MineFantasyItems.STANDARD_HALBEARD,
				MineFantasyItems.STANDARD_LANCE,
				MineFantasyItems.STANDARD_PICK,
				MineFantasyItems.STANDARD_AXE,
				MineFantasyItems.STANDARD_SPADE,
				MineFantasyItems.STANDARD_HOE,
				MineFantasyItems.STANDARD_HEAVY_PICK,
				MineFantasyItems.STANDARD_HEAVY_SHOVEL,
				MineFantasyItems.STANDARD_HANDPICK,
				MineFantasyItems.STANDARD_TROW,
				MineFantasyItems.STANDARD_SCYTHE,
				MineFantasyItems.STANDARD_MATTOCK,
				MineFantasyItems.STANDARD_LUMBER,
				MineFantasyItems.STANDARD_HAMMER,
				MineFantasyItems.STANDARD_HEAVY_HAMMER,
				MineFantasyItems.STANDARD_TONGS,
				MineFantasyItems.STANDARD_SHEARS,
				MineFantasyItems.STANDARD_KNIFE,
				MineFantasyItems.STANDARD_NEEDLE,
				MineFantasyItems.STANDARD_SAW,
				MineFantasyItems.STANDARD_SPOON,
				MineFantasyItems.STANDARD_MALLET,
				MineFantasyItems.STANDARD_SPANNER,
				MineFantasyItems.STANDARD_BOW,
				MineFantasyItems.STANDARD_ARROW,
				MineFantasyItems.STANDARD_BOLT,
				MineFantasyItems.STANDARD_ARROW_BODKIN,
				MineFantasyItems.STANDARD_ARROW_BROAD,
				MineFantasyItems.EXPLODING_ARROW,
				MineFantasyItems.EXPLODING_BOLT,
				MineFantasyItems.STANDARD_SCALE_HELMET,
				MineFantasyItems.STANDARD_SCALE_CHESTPLATE,
				MineFantasyItems.STANDARD_SCALE_LEGGINGS,
				MineFantasyItems.STANDARD_SCALE_BOOTS,
				MineFantasyItems.STANDARD_CHAIN_HELMET,
				MineFantasyItems.STANDARD_CHAIN_CHESTPLATE,
				MineFantasyItems.STANDARD_CHAIN_LEGGINGS,
				MineFantasyItems.STANDARD_CHAIN_BOOTS,
				MineFantasyItems.STANDARD_SPLINT_HELMET,
				MineFantasyItems.STANDARD_SPLINT_CHESTPLATE,
				MineFantasyItems.STANDARD_SPLINT_LEGGINGS,
				MineFantasyItems.STANDARD_SPLINT_BOOTS,
				MineFantasyItems.STANDARD_PLATE_HELMET,
				MineFantasyItems.STANDARD_PLATE_CHESTPLATE,
				MineFantasyItems.STANDARD_PLATE_LEGGINGS,
				MineFantasyItems.STANDARD_PLATE_BOOTS,
				MineFantasyItems.DRAGONFORGED_SWORD,
				MineFantasyItems.DRAGONFORGED_WARAXE,
				MineFantasyItems.DRAGONFORGED_MACE,
				MineFantasyItems.DRAGONFORGED_DAGGER,
				MineFantasyItems.DRAGONFORGED_SPEAR,
				MineFantasyItems.DRAGONFORGED_GREATSWORD,
				MineFantasyItems.DRAGONFORGED_BATTLEAXE,
				MineFantasyItems.DRAGONFORGED_WARHAMMER,
				MineFantasyItems.DRAGONFORGED_KATANA,
				MineFantasyItems.DRAGONFORGED_HALBEARD,
				MineFantasyItems.DRAGONFORGED_LANCE,
				MineFantasyItems.DRAGONFORGED_PICK,
				MineFantasyItems.DRAGONFORGED_AXE,
				MineFantasyItems.DRAGONFORGED_SPADE,
				MineFantasyItems.DRAGONFORGED_HOE,
				MineFantasyItems.DRAGONFORGED_HEAVY_PICK,
				MineFantasyItems.DRAGONFORGED_HEAVY_SHOVEL,
				MineFantasyItems.DRAGONFORGED_HANDPICK,
				MineFantasyItems.DRAGONFORGED_TROW,
				MineFantasyItems.DRAGONFORGED_SCYTHE,
				MineFantasyItems.DRAGONFORGED_MATTOCK,
				MineFantasyItems.DRAGONFORGED_LUMBER,
				MineFantasyItems.DRAGONFORGED_HAMMER,
				MineFantasyItems.DRAGONFORGED_HEAVY_HAMMER,
				MineFantasyItems.DRAGONFORGED_TONGS,
				MineFantasyItems.DRAGONFORGED_SHEARS,
				MineFantasyItems.DRAGONFORGED_KNIFE,
				MineFantasyItems.DRAGONFORGED_NEEDLE,
				MineFantasyItems.DRAGONFORGED_SAW,
				MineFantasyItems.DRAGONFORGED_SPANNER,
				MineFantasyItems.DRAGONFORGED_BOW,
				MineFantasyItems.DRAGONFORGED_SCALE_HELMET,
				MineFantasyItems.DRAGONFORGED_SCALE_CHESTPLATE,
				MineFantasyItems.DRAGONFORGED_SCALE_LEGGINGS,
				MineFantasyItems.DRAGONFORGED_SCALE_BOOTS,
				MineFantasyItems.DRAGONFORGED_CHAIN_HELMET,
				MineFantasyItems.DRAGONFORGED_CHAIN_CHESTPLATE,
				MineFantasyItems.DRAGONFORGED_CHAIN_LEGGINGS,
				MineFantasyItems.DRAGONFORGED_CHAIN_BOOTS,
				MineFantasyItems.DRAGONFORGED_SPLINT_HELMET,
				MineFantasyItems.DRAGONFORGED_SPLINT_CHESTPLATE,
				MineFantasyItems.DRAGONFORGED_SPLINT_LEGGINGS,
				MineFantasyItems.DRAGONFORGED_SPLINT_BOOTS,
				MineFantasyItems.DRAGONFORGED_PLATE_HELMET,
				MineFantasyItems.DRAGONFORGED_PLATE_CHESTPLATE,
				MineFantasyItems.DRAGONFORGED_PLATE_LEGGINGS,
				MineFantasyItems.DRAGONFORGED_PLATE_BOOTS,
				MineFantasyItems.ORNATE_SWORD,
				MineFantasyItems.ORNATE_WARAXE,
				MineFantasyItems.ORNATE_MACE,
				MineFantasyItems.ORNATE_DAGGER,
				MineFantasyItems.ORNATE_SPEAR,
				MineFantasyItems.ORNATE_GREATSWORD,
				MineFantasyItems.ORNATE_BATTLEAXE,
				MineFantasyItems.ORNATE_WARHAMMER,
				MineFantasyItems.ORNATE_KATANA,
				MineFantasyItems.ORNATE_HALBEARD,
				MineFantasyItems.ORNATE_LANCE,
				MineFantasyItems.ORNATE_PICK,
				MineFantasyItems.ORNATE_AXE,
				MineFantasyItems.ORNATE_SPADE,
				MineFantasyItems.ORNATE_HOE,
				MineFantasyItems.ORNATE_HEAVY_PICK,
				MineFantasyItems.ORNATE_HEAVY_SHOVEL,
				MineFantasyItems.ORNATE_HANDPICK,
				MineFantasyItems.ORNATE_TROW,
				MineFantasyItems.ORNATE_SCYTHE,
				MineFantasyItems.ORNATE_MATTOCK,
				MineFantasyItems.ORNATE_LUMBER,
				MineFantasyItems.ORNATE_HAMMER,
				MineFantasyItems.ORNATE_HEAVY_HAMMER,
				MineFantasyItems.ORNATE_TONGS,
				MineFantasyItems.ORNATE_SHEARS,
				MineFantasyItems.ORNATE_KNIFE,
				MineFantasyItems.ORNATE_NEEDLE,
				MineFantasyItems.ORNATE_SAW,
				MineFantasyItems.ORNATE_SPANNER,
				MineFantasyItems.ORNATE_BOW,
				MineFantasyItems.ORNATE_SCALE_HELMET,
				MineFantasyItems.ORNATE_SCALE_CHESTPLATE,
				MineFantasyItems.ORNATE_SCALE_LEGGINGS,
				MineFantasyItems.ORNATE_SCALE_BOOTS,
				MineFantasyItems.ORNATE_CHAIN_HELMET,
				MineFantasyItems.ORNATE_CHAIN_CHESTPLATE,
				MineFantasyItems.ORNATE_CHAIN_LEGGINGS,
				MineFantasyItems.ORNATE_CHAIN_BOOTS,
				MineFantasyItems.ORNATE_SPLINT_HELMET,
				MineFantasyItems.ORNATE_SPLINT_CHESTPLATE,
				MineFantasyItems.ORNATE_SPLINT_LEGGINGS,
				MineFantasyItems.ORNATE_SPLINT_BOOTS,
				MineFantasyItems.ORNATE_PLATE_HELMET,
				MineFantasyItems.ORNATE_PLATE_CHESTPLATE,
				MineFantasyItems.ORNATE_PLATE_LEGGINGS,
				MineFantasyItems.ORNATE_PLATE_BOOTS,
				MineFantasyItems.BOMB_CUSTOM,
				MineFantasyItems.MINE_CUSTOM,
				MineFantasyBlocks.FOOD_BOX_BASIC_ITEM,
				MineFantasyBlocks.AMMO_BOX_BASIC_ITEM,
				MineFantasyBlocks.CRATE_BASIC_ITEM,
				MineFantasyBlocks.TOOL_RACK_WOOD_ITEM,
				MineFantasyBlocks.TROUGH_WOOD_ITEM
		);
	}

	private static void addExtendedInfo(IModRegistry registry, Item item, String... suffixes) {
		NonNullList<ItemStack> subItems = NonNullList.create();
		item.getSubItems(item.getCreativeTab(), subItems);
		for (ItemStack stack : subItems)
			addExtendedInfo(registry, stack, suffixes);
	}

	private static void addExtendedInfo(IModRegistry registry, Block block, String... suffixes) {
		Item item = Item.getItemFromBlock(block);
		NonNullList<ItemStack> subItems = NonNullList.create();
		item.getSubItems(item.getCreativeTab(), subItems);
		for (ItemStack stack : subItems)
			addExtendedInfo(registry, stack, suffixes);
	}

	private static void addExtendedInfo(IModRegistry registry, ItemStack stack, String... suffixes) {
		String prefix = stack.getItem().getTranslationKey(stack);
		String[] keys = Arrays.stream(suffixes).map(s -> prefix + s).toArray(String[]::new);
		registry.addIngredientInfo(stack, VanillaTypes.ITEM, keys);
	}

	private static void addExtendedInfoCustom(IModRegistry registry, Item item, String key) {
		addExtendedInfoCustom(registry, new ItemStack(item), key);
	}

	private static void addExtendedInfoCustom(IModRegistry registry, ItemStack stack, String key) {
		registry.addIngredientInfo(stack, VanillaTypes.ITEM, key);
	}

	public static Focus<ItemStack> getFocus(IRecipeLayout recipeLayout, IFocus.Mode mode) {
		Focus<ItemStack> focus = null;
		IFocus<?> iFocus = recipeLayout.getFocus();
		if (iFocus != null && iFocus.getMode() == mode) {
			Object focusValue = Focus.check(iFocus).getValue();
			if (focusValue instanceof ItemStack) {
				//noinspection unchecked
				focus = (Focus<ItemStack>) Focus.check(iFocus);
			}
		}
		return focus;
	}

	public static void addAnyMaterialTooltip(
			NonNullList<Ingredient> ingredients,
			ItemStack slotStack,
			List<String> tooltip,
			Focus<ItemStack> focus,
			ItemStack recipeOutput) {

		if (focus == null || stackHelper.isEquivalent(focus.getValue(), recipeOutput)) {
			Ingredient ingredient = Ingredient.EMPTY;
			for (Ingredient inputIngredient : ingredients) {
				if (inputIngredient instanceof IngredientMaterial) {
					for (ItemStack stack : inputIngredient.getMatchingStacks()) {
						if (stackHelper.isEquivalent(stack, slotStack)) {
							ingredient = inputIngredient;
							break;
						}
					}
				}
			}
			if (ingredient != Ingredient.EMPTY && ingredient instanceof IngredientMaterial) {
				String emptyStackName = new ItemStack(slotStack.getItem()).getDisplayName();
				String anyMaterialString = I18n.translateToLocalFormatted("integration.jei.tooltip.recipe.any.material", emptyStackName);
				IngredientMaterial ingredientMaterial = (IngredientMaterial) ingredient;

				if (!ingredientMaterial.getExcludedMaterials().isEmpty()) {
					List<String> excludedMaterials = ingredientMaterial.getExcludedMaterials()
							.stream()
							.map(CustomToolHelper::getMaterialNameForTooltip)
							.collect(Collectors.toList());
					anyMaterialString = anyMaterialString
							+ " "
							+ I18n.translateToLocalFormatted("integration.jei.tooltip.recipe.except.material",
							excludedMaterials);
				}
				String acceptsAny = TextFormatting.GRAY + anyMaterialString;
				tooltip.add(acceptsAny);
			}
		}
	}
}
