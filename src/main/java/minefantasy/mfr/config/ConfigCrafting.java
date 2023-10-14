package minefantasy.mfr.config;

import minefantasy.mfr.MineFantasyReforged;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

public class ConfigCrafting extends ConfigurationBaseMF {
	public static final String CATEGORY_REFINING = "01 Refining";
	public static final String CATEGORY_COOKING = "02 Cooking";
	public static final String ANVIL_RECIPE_SETTINGS = "03 Anvil Recipe Settings";
	public static final String CARPENTER_RECIPE_SETTINGS = "04 Carpenter Recipe Settings";
	private static final String BIG_FURNACE_RECIPE_SETTINGS = "05 Big Furnace Recipe Settings";
	private static final String ALLOY_RECIPE_SETTINGS = "06 Alloy Recipe Settings";
	public static boolean allowIronResmelt;
	public static int maxFurnaceHeight;
	public static boolean canCookBasics = true;

	public ConfigCrafting(String name) {
		super(name);
	}

	@Override
	protected void initializeCategories() {
		config.addCustomCategoryComment(CATEGORY_REFINING, "Configs that control settings in Refining");
		config.addCustomCategoryComment(CATEGORY_COOKING, "Configs that handle cooking");
		config.addCustomCategoryComment(ANVIL_RECIPE_SETTINGS, "Control whether or not an item's anvil recipe should be enabled");
		config.addCustomCategoryComment(CARPENTER_RECIPE_SETTINGS, "Controls whether or not an item's carpenter recipe should be enabled");
		config.addCustomCategoryComment(BIG_FURNACE_RECIPE_SETTINGS, "Controls whether or not an item's big furnace recipe should be enabled");
		config.addCustomCategoryComment(ALLOY_RECIPE_SETTINGS, "Controls whether or not an item's alloy recipe should be enabled");
	}

	@Override
	protected void initializeValues() {
		allowIronResmelt = Boolean.parseBoolean(config.get(CATEGORY_REFINING,
				"Allow Iron ingots to make Pig Iron",
				false,
				"If you're not resourceful: you can allow iron ingots to make prepared iron for refining.").getString());

		maxFurnaceHeight = Integer.parseInt(config.get(CATEGORY_REFINING,
				"Max Blast Furnace Height",
				16,
				"The max amount of chambers a blast furnace can read").getString());

		canCookBasics = Boolean.parseBoolean(config.get(CATEGORY_COOKING,
				"Cook non-mf food on cooktop",
				true,
				"This means non-mf food cooked in a furnace can work on a cooking plate").getString());
	}

	public static boolean isAnvilItemCraftable(ItemStack itemStack) {
		//Checks if the given Item should load default recipes for the anvil.
		//If an entry for it does not exist, it will be added when queried, defaulting to try
		String name = generateNameFromItemAndNBT(itemStack);
		return get().getBoolean(name, ANVIL_RECIPE_SETTINGS, true, "");
	}

	public static boolean isCarpenterItemCraftable(ItemStack itemStack) {
		//Checks if the given Item should load default recipes for the carpenter.
		//If an entry for it does not exist, it will be added when queried, defaulting to try
		String name = generateNameFromItemAndNBT(itemStack);
		return get().getBoolean(name, CARPENTER_RECIPE_SETTINGS, true, "");
	}

	public static boolean isBigFurnaceItemCraftable(ItemStack itemStack) {
		//Checks if the given Item should load default recipes for the big furnace.
		//If an entry for it does not exist, it will be added when queried, defaulting to try
		String name = generateNameFromItemAndNBT(itemStack);
		return get().getBoolean(name, BIG_FURNACE_RECIPE_SETTINGS, true, "");
	}

	public static boolean isAlloyItemCraftable(ItemStack itemStack) {
		//Checks if the given Item should load default recipes for the crucible.
		//If an entry for it does not exist, it will be added when queried, defaulting to try
		String name = generateNameFromItemAndNBT(itemStack);
		return get().getBoolean(name, ALLOY_RECIPE_SETTINGS, true, "");
	}

	public static Configuration get() {
		return MineFantasyReforged.configCrafting.getConfig();
	}


	public static String generateNameFromItemAndNBT(ItemStack itemStack) {
		String name;
		if (itemStack.getTagCompound() != null) {
			name = itemStack.getItem().getRegistryName().toString() + "|" + itemStack.getTagCompound().toString();
		}
		else {
			name = itemStack.getItem().getRegistryName().toString();
		}

		return name.replaceAll("[{}'\"]", "");
	}
}
