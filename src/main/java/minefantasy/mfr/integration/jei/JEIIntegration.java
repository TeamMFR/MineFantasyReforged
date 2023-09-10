package minefantasy.mfr.integration.jei;

import mezz.jei.Internal;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IStackHelper;
import mezz.jei.gui.GuiHelper;
import mezz.jei.runtime.JeiHelpers;
import minefantasy.mfr.config.ConfigIntegration;
import minefantasy.mfr.init.MineFantasyItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import java.util.Arrays;

@JEIPlugin
public class JEIIntegration implements IModPlugin {

	public JEIIntegration() {
	}

	@Override
	public void registerCategories(@Nonnull IRecipeCategoryRegistration registry) {

		if (!ConfigIntegration.jeiIntegration) {
			return;
		}

		JeiHelpers jeiHelpers = Internal.getHelpers();
		GuiHelper guiHelper = jeiHelpers.getGuiHelper();

		registry.addRecipeCategories(new JEICarpenterRecipeCategory(registry));
		registry.addRecipeCategories(new JEIAnvilRecipeCategory(registry));
		registry.addRecipeCategories(new JEIBigFurnaceRecipeCategory(registry, guiHelper));
		// TODO: same for anvil, salvage, ...
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

		addExtendedInfo(registry, MineFantasyItems.FLUX, ".desc_extended");

		registry.addRecipes(JEICarpenterRecipeCategory.generateRecipes(stackHelper), JEICarpenterRecipeCategory.UID);
		registry.addRecipes(JEIAnvilRecipeCategory.generateRecipes(stackHelper), JEIAnvilRecipeCategory.UID);
		registry.addRecipes(JEIBigFurnaceRecipeCategory.generateRecipes(stackHelper), JEIBigFurnaceRecipeCategory.UID);
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
		subtypeRegistry.useNbtForSubtypes(
				MineFantasyItems.BAR,
				MineFantasyItems.TIMBER,
				MineFantasyItems.TIMBER_CUT,
				MineFantasyItems.TIMBER_PANE,
				MineFantasyItems.SCALE_MESH,
				MineFantasyItems.CHAIN_MESH,
				MineFantasyItems.SPLINT_MESH,
				MineFantasyItems.METAL_HUNK,
				MineFantasyItems.PLATE,
				MineFantasyItems.PLATE_HUGE,
				MineFantasyItems.ARROWHEAD,
				MineFantasyItems.BROAD_HEAD,
				MineFantasyItems.BODKIN_HEAD,
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
				MineFantasyItems.STANDARD_PLATE_BOOTS
//				MineFantasyItems.DRAGONFORGED_SWORD,
//				MineFantasyItems.DRAGONFORGED_WARAXE,
//				MineFantasyItems.DRAGONFORGED_MACE,
//				MineFantasyItems.DRAGONFORGED_DAGGER,
//				MineFantasyItems.DRAGONFORGED_SPEAR,
//				MineFantasyItems.DRAGONFORGED_GREATSWORD,
//				MineFantasyItems.DRAGONFORGED_BATTLEAXE,
//				MineFantasyItems.DRAGONFORGED_WARHAMMER,
//				MineFantasyItems.DRAGONFORGED_KATANA,
//				MineFantasyItems.DRAGONFORGED_HALBEARD,
//				MineFantasyItems.DRAGONFORGED_LANCE,
//				MineFantasyItems.DRAGONFORGED_PICK,
//				MineFantasyItems.DRAGONFORGED_AXE,
//				MineFantasyItems.DRAGONFORGED_SPADE,
//				MineFantasyItems.DRAGONFORGED_HOE,
//				MineFantasyItems.DRAGONFORGED_HEAVY_PICK,
//				MineFantasyItems.DRAGONFORGED_HEAVY_SHOVEL,
//				MineFantasyItems.DRAGONFORGED_HANDPICK,
//				MineFantasyItems.DRAGONFORGED_TROW,
//				MineFantasyItems.DRAGONFORGED_SCYTHE,
//				MineFantasyItems.DRAGONFORGED_MATTOCK,
//				MineFantasyItems.DRAGONFORGED_LUMBER,
//				MineFantasyItems.DRAGONFORGED_HAMMER,
//				MineFantasyItems.DRAGONFORGED_HEAVY_HAMMER,
//				MineFantasyItems.DRAGONFORGED_TONGS,
//				MineFantasyItems.DRAGONFORGED_SHEARS,
//				MineFantasyItems.DRAGONFORGED_KNIFE,
//				MineFantasyItems.DRAGONFORGED_NEEDLE,
//				MineFantasyItems.DRAGONFORGED_SAW,
//				MineFantasyItems.DRAGONFORGED_SPANNER,
//				MineFantasyItems.DRAGONFORGED_BOW,
//				MineFantasyItems.DRAGONFORGED_SCALE_HELMET,
//				MineFantasyItems.DRAGONFORGED_SCALE_CHESTPLATE,
//				MineFantasyItems.DRAGONFORGED_SCALE_LEGGINGS,
//				MineFantasyItems.DRAGONFORGED_SCALE_BOOTS,
//				MineFantasyItems.DRAGONFORGED_CHAIN_HELMET,
//				MineFantasyItems.DRAGONFORGED_CHAIN_CHESTPLATE,
//				MineFantasyItems.DRAGONFORGED_CHAIN_LEGGINGS,
//				MineFantasyItems.DRAGONFORGED_CHAIN_BOOTS,
//				MineFantasyItems.DRAGONFORGED_SPLINT_HELMET,
//				MineFantasyItems.DRAGONFORGED_SPLINT_CHESTPLATE,
//				MineFantasyItems.DRAGONFORGED_SPLINT_LEGGINGS,
//				MineFantasyItems.DRAGONFORGED_SPLINT_BOOTS,
//				MineFantasyItems.DRAGONFORGED_PLATE_HELMET,
//				MineFantasyItems.DRAGONFORGED_PLATE_CHESTPLATE,
//				MineFantasyItems.DRAGONFORGED_PLATE_LEGGINGS,
//				MineFantasyItems.DRAGONFORGED_PLATE_BOOTS,
//				MineFantasyItems.ORNATE_SWORD,
//				MineFantasyItems.ORNATE_WARAXE,
//				MineFantasyItems.ORNATE_MACE,
//				MineFantasyItems.ORNATE_DAGGER,
//				MineFantasyItems.ORNATE_SPEAR,
//				MineFantasyItems.ORNATE_GREATSWORD,
//				MineFantasyItems.ORNATE_BATTLEAXE,
//				MineFantasyItems.ORNATE_WARHAMMER,
//				MineFantasyItems.ORNATE_KATANA,
//				MineFantasyItems.ORNATE_HALBEARD,
//				MineFantasyItems.ORNATE_LANCE,
//				MineFantasyItems.ORNATE_PICK,
//				MineFantasyItems.ORNATE_AXE,
//				MineFantasyItems.ORNATE_SPADE,
//				MineFantasyItems.ORNATE_HOE,
//				MineFantasyItems.ORNATE_HEAVY_PICK,
//				MineFantasyItems.ORNATE_HEAVY_SHOVEL,
//				MineFantasyItems.ORNATE_HANDPICK,
//				MineFantasyItems.ORNATE_TROW,
//				MineFantasyItems.ORNATE_SCYTHE,
//				MineFantasyItems.ORNATE_MATTOCK,
//				MineFantasyItems.ORNATE_LUMBER,
//				MineFantasyItems.ORNATE_HAMMER,
//				MineFantasyItems.ORNATE_HEAVY_HAMMER,
//				MineFantasyItems.ORNATE_TONGS,
//				MineFantasyItems.ORNATE_SHEARS,
//				MineFantasyItems.ORNATE_KNIFE,
//				MineFantasyItems.ORNATE_NEEDLE,
//				MineFantasyItems.ORNATE_SAW,
//				MineFantasyItems.ORNATE_SPANNER,
//				MineFantasyItems.ORNATE_BOW,
//				MineFantasyItems.ORNATE_SCALE_HELMET,
//				MineFantasyItems.ORNATE_SCALE_CHESTPLATE,
//				MineFantasyItems.ORNATE_SCALE_LEGGINGS,
//				MineFantasyItems.ORNATE_SCALE_BOOTS,
//				MineFantasyItems.ORNATE_CHAIN_HELMET,
//				MineFantasyItems.ORNATE_CHAIN_CHESTPLATE,
//				MineFantasyItems.ORNATE_CHAIN_LEGGINGS,
//				MineFantasyItems.ORNATE_CHAIN_BOOTS,
//				MineFantasyItems.ORNATE_SPLINT_HELMET,
//				MineFantasyItems.ORNATE_SPLINT_CHESTPLATE,
//				MineFantasyItems.ORNATE_SPLINT_LEGGINGS,
//				MineFantasyItems.ORNATE_SPLINT_BOOTS,
//				MineFantasyItems.ORNATE_PLATE_HELMET,
//				MineFantasyItems.ORNATE_PLATE_CHESTPLATE,
//				MineFantasyItems.ORNATE_PLATE_LEGGINGS,
//				MineFantasyItems.ORNATE_PLATE_BOOTS
				);
	}

	private static void addExtendedInfo(IModRegistry registry, Item item, String... suffixes) {
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
}
