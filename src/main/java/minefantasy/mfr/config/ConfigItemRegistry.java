package minefantasy.mfr.config;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.api.armour.ArmourDesign;
import minefantasy.mfr.api.armour.CustomArmourEntry;
import minefantasy.mfr.api.armour.CustomDamageRatioEntry;
import minefantasy.mfr.api.crafting.CustomCrafterEntry;
import minefantasy.mfr.api.farming.CustomHoeEntry;
import minefantasy.mfr.api.stamina.CustomFoodEntry;
import minefantasy.mfr.util.MFRLogUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.Objects;

public class ConfigItemRegistry extends ConfigurationBaseMF {
	public static final String CATEGORY_ARMOUR = "Armour List";
	public static final String CATEGORY_FARM = "Farming";
	public static final String CATEGORY_TOOL = "Tools";
	public static final String CATEGORY_WEAPONS = "Weapon Register";
	public static final String CATEGORY_FOOD = "Food Register";
	public static final String CATEGORY_STONE_PICK_OVERRIDE = "Stone Pick Override";
	public static String[] armourListAC = new String[0];
	public static String[] hoeList = new String[0];
	public static String[] crafterList = new String[0];
	public static String[] customDamagerList = new String[0];
	public static String[] customDamagerEntityList = new String[0];
	public static String[] customFoodList = new String[0];
	public static String[] customStonePickOverride = new String[0];

	public ConfigItemRegistry(String name) {
		super(name);
	}

	public static void readCustoms() {
		MFRLogUtil.logDebug("Loading Custom Item Entries from config...");
		try {
			for (String s : armourListAC) {
				ArmorRegistryParser(s);
			}
			for (String s : hoeList) {
				HoeRegistryParser(s);
			}
			for (String s : crafterList) {
				CrafterRegistryParser(s);
			}
			for (String s : customDamagerList) {
				WeaponDamageRegistryParser(s);
			}
			for (String s : customDamagerEntityList) {
				ProjectileEntityDamageRegistryParser(s);
			}
			for (String s : customFoodList) {
				FoodRegistryParser(s);
			}
		}
		catch (Exception e) {
			MineFantasyReforged.LOG.warn("Failed to load Custom Item Entries from config. Check the config file");
		}
	}

	private static void ArmorRegistryParser(String entry) {
		String[] entryContents = entry.split("\\|");

		Item armorItem = Item.REGISTRY.getObject(new ResourceLocation(entryContents[0]));
		if (armorItem == null) {
			MineFantasyReforged.LOG.warn("Could not define armor item for: " + entryContents[0]);
			return;
		}

		ArmourDesign armourDesign = ArmourDesign.designs.get(entryContents[1]);
		if (armourDesign == null) {
			MineFantasyReforged.LOG.warn("Could not define armour design '" + entryContents[1] + "' for item id: " + armorItem.getRegistryName());
			return;
		}

		String weightClass = entryContents[2];
		if (Objects.equals(weightClass, "") || weightClass == null) {
			MineFantasyReforged.LOG.warn("Could not define armour weight class for '" + entryContents[2] + "' for item id: " + armorItem.getRegistryName());
			return;
		}

		float weightModifier = Float.parseFloat(entryContents[3]);
		if (weightModifier == 0.0F) {
			MineFantasyReforged.LOG.warn("Could not define weight modifier for '" + entryContents[3] + "' for item id: " + armorItem.getRegistryName());
			return;
		}

		CustomArmourEntry.registerItem(new ItemStack(armorItem), armourDesign, weightModifier, weightClass);
		MineFantasyReforged.LOG.info("Added Custom Armor entry for " + armorItem.getRegistryName() + " with armor design: " + armourDesign.getName() + ", with weight class: " + weightClass + ", with weight modifier: " + weightModifier);
	}

	private static void HoeRegistryParser(String entry) {
		String[] entryContents = entry.split("\\|");

		Item hoeItem = Item.REGISTRY.getObject(new ResourceLocation(entryContents[0]));
		if (hoeItem == null) {
			MineFantasyReforged.LOG.warn("Could not define hoe item for: " + entryContents[0]);
			return;
		}

		float hoeEfficiency = Float.parseFloat(entryContents[1]);
		if (hoeEfficiency == 0.0F) {
			MineFantasyReforged.LOG.warn("Could not define hoe efficiency '" + entryContents[2] + "' for item id: " + hoeItem.getRegistryName());
			return;
		}

		CustomHoeEntry.registerItem(new ItemStack(hoeItem), hoeEfficiency);
		MineFantasyReforged.LOG.info("Added Custom Hoe entry for " + hoeItem.getRegistryName() + " with efficiency: " + hoeEfficiency);
	}

	private static void CrafterRegistryParser(String entry) {
		String[] entryContents = entry.split("\\|");

		Item crafterItem = Item.REGISTRY.getObject(new ResourceLocation(entryContents[0]));
		if (crafterItem == null) {
			MineFantasyReforged.LOG.warn("Could not define crafter item for: " + entryContents[0]);
			return;
		}

		String crafterType = entryContents[1];
		if (Objects.equals(crafterType, "") || crafterType == null) {
			MineFantasyReforged.LOG.warn("Could not define crafter type '" + entryContents[1] + "' for item id: " + crafterItem.getRegistryName());
			return;
		}

		float crafterEfficiency = Float.parseFloat(entryContents[2]);
		if (crafterEfficiency < 0F) {
			MineFantasyReforged.LOG.warn("Could not define crafter efficiency for '" + entryContents[2] + "' for item id: " + crafterItem.getRegistryName());
			return;
		}

		int crafterTier = Integer.parseInt(entryContents[3]);
		if (crafterTier < 0) {
			MineFantasyReforged.LOG.warn("Could not define crafter tier for '" + entryContents[3] + "' for item id: " + crafterItem.getRegistryName());
			return;
		}

		CustomCrafterEntry.registerItem(new ItemStack(crafterItem), crafterType, crafterEfficiency, crafterTier);
		MineFantasyReforged.LOG.info("Added Custom Crafter entry for " + crafterItem.getRegistryName() + " with type: " + crafterType + ", with efficiency: " + crafterEfficiency + ", with a tier: " + crafterTier);
	}

	private static void WeaponDamageRegistryParser(String entry) {
		String[] entryContents = entry.split("\\|");

		Item weaponItem = Item.REGISTRY.getObject(new ResourceLocation(entryContents[0]));
		if (weaponItem == null) {
			MineFantasyReforged.LOG.warn("Could not define weapon item for: " + entryContents[0]);
			return;
		}

		float cut = Float.parseFloat(entryContents[1]);
		if (cut < 0) {
			MineFantasyReforged.LOG.warn("Could not define cut damage for '" + entryContents[1] + "' for item id: " + weaponItem.getRegistryName());
			return;
		}

		float blunt = Float.parseFloat(entryContents[2]);
		if (blunt < 0) {
			MineFantasyReforged.LOG.warn("Could not define blunt damage for '" + entryContents[2] + "' for item id: " + weaponItem.getRegistryName());
			return;
		}

		float pierce = Float.parseFloat(entryContents[3]);
		if (pierce < 0) {
			MineFantasyReforged.LOG.warn("Could not define pierce damage for '" + entryContents[3] + "' for item id: " + weaponItem.getRegistryName());
			return;
		}

		CustomDamageRatioEntry.registerItem(new ItemStack(weaponItem), new float[] {cut, blunt, pierce});
		MineFantasyReforged.LOG.info("Added Custom Weapon Damage entry for " + weaponItem.getRegistryName() + " with damage stats: " + Arrays.toString(new float[] {cut, blunt, pierce}));
	}

	private static void ProjectileEntityDamageRegistryParser(String entry) {
		String[] entryContents = entry.split("\\|");

		String projectileEntityID = entryContents[0];
		if (Objects.equals(projectileEntityID, "") || projectileEntityID == null) {
			MineFantasyReforged.LOG.warn("Could not define projectile entity for: " + entryContents[0]);
			return;
		}

		float cut = Float.parseFloat(entryContents[1]);
		if (cut < 0) {
			MineFantasyReforged.LOG.warn("Could not define cut damage for '" + entryContents[1] + "' for projectile entity id: " + projectileEntityID);
			return;
		}

		float blunt = Float.parseFloat(entryContents[2]);
		if (blunt < 0) {
			MineFantasyReforged.LOG.warn("Could not define blunt damage for '" + entryContents[2] + "' for projectile entity id: " + projectileEntityID);
			return;
		}

		float pierce = Float.parseFloat(entryContents[3]);
		if (pierce < 0) {
			MineFantasyReforged.LOG.warn("Could not define pierce damage for '" + entryContents[1] + "' for projectile entity id: " + projectileEntityID);
			return;
		}

		CustomDamageRatioEntry.registerEntity(new ResourceLocation(projectileEntityID), new float[] {cut, blunt, pierce});
		MineFantasyReforged.LOG.info("Added Custom Projectile Entity Damage entry for " + projectileEntityID + " with damage stats: " + Arrays.toString(new float[] {cut, blunt, pierce}));

	}

	private static void FoodRegistryParser(String entry) {
		String[] entryContents = entry.split("\\|");

		Item foodItem = Item.REGISTRY.getObject(new ResourceLocation(entryContents[0]));
		if (foodItem == null) {
			MineFantasyReforged.LOG.warn("Could not define food item for: " + entryContents[0]);
			return;
		}

		int tier = Integer.parseInt(entryContents[1]);
		if (tier < 0) {
			MineFantasyReforged.LOG.warn("Could not define tier value for '" + entryContents[1] + "' for item id: " + foodItem.getRegistryName());
			return;
		}

		float sugar = Float.parseFloat(entryContents[2]);
		if (sugar < 0) {
			MineFantasyReforged.LOG.warn("Could not define sugar value for '" + entryContents[2] + "' for item id: " + foodItem.getRegistryName());
			return;
		}

		float carbs = Float.parseFloat(entryContents[3]);
		if (carbs < 0) {
			MineFantasyReforged.LOG.warn("Could not define carbs value for '" + entryContents[3] + "' for item id: " + foodItem.getRegistryName());
			return;
		}

		float fats = Float.parseFloat(entryContents[4]);
		if (fats < 0) {
			MineFantasyReforged.LOG.warn("Could not define fats value for '" + entryContents[4] + "' for item id: " + foodItem.getRegistryName());
			return;
		}

		int meta;
		try {
			String metadataString = entryContents[5];
			if (metadataString != null) {
				meta = Integer.parseInt(metadataString);
			}
			else {
				meta = 0;
			}
		}
		catch (NumberFormatException | ArrayIndexOutOfBoundsException exception) {
			meta = 0;
		}

		CustomFoodEntry.registerItem(new ItemStack(foodItem, 1, meta), tier, sugar, carbs, fats);
		MineFantasyReforged.LOG.info("Added Custom Food entry for " + foodItem.getRegistryName() + ":" + meta + " with foods stats: " + Arrays.toString(new float[] {tier, sugar, carbs, fats}));
	}

	@Override
	protected void initializeCategories() {
		config.addCustomCategoryComment(CATEGORY_ARMOUR, "Handles Custom Armor Registrations");
		config.addCustomCategoryComment(CATEGORY_WEAPONS, "Handles Custom Weapon Registrations");
		config.addCustomCategoryComment(CATEGORY_TOOL, "Handles Custom Crafter Registrations");
		config.addCustomCategoryComment(CATEGORY_FARM, "Handles Custom Hoe Registrations");
		config.addCustomCategoryComment(CATEGORY_FOOD, "Handles Custom Food Registrations");
		config.addCustomCategoryComment(CATEGORY_STONE_PICK_OVERRIDE, "Handles MFR Stone Pick Harvest Level Override by Blockstate Registrations");
	}

	@Override
	protected void initializeValues() {
		// Weight

		String ArmorRegistryDescription = "This will register items under a certain 'Design' calculating the variables itself.\n Each entry has it's own line:\n"
				+ "Order itemid|Design|WeightGroup|WeightModifier \n"
				+ "The WeightModifier alters the weight for heavier or lighter materials keep it at 1.0 unless you have a special material (like mithril and adamamantium)\n"
				+ "Designs can be any that are registered: MineFantasy designs are 'clothing', 'leather', 'mail', 'solid'(that's just basic metal armour), and 'plate'\n"
				+ "WeightGroup refers to whether it is light medium or heavy armour \n"
				+ "metadata refers to the item's metadata, this can be left empty"
				+ "EXAMPLE (This is what vanilla gold is registered under) \n"
				+ "minecraft:golden_helmet|solid|medium|2.0 \n" + "minecraft:golden_chestplate|solid|medium|2.0 \n"
				+ "minecraft:golden_leggings|solid|medium|2.0 \n" + "minecraft:golden_boots|solid|medium|2.0 \n"
				+ "The 2.0 means it is 2x heavier than other vanilla armours \n"
				+ "This does not override existing MF armours \n";

		armourListAC = config.get(CATEGORY_ARMOUR, "Armour Registry", new String[0], ArmorRegistryDescription)
				.getStringList();
		Arrays.sort(armourListAC);

		// Hoes

		String hoeRegistryDescription = "This Registers Hoe items to an efficiency level: (It uses the same variable as efficiency, you may need to find that out first, by default: it should be able to guess it:\n"
				+ "Order itemid|efficiency \n"
				+ "Efficiency is a variable that goes into play with the failure chance, higher efficiency has easier tiling\n";

		hoeList = config.get(CATEGORY_FARM, "Hoe Registry", new String[0], hoeRegistryDescription).getStringList();
		Arrays.sort(hoeList);

		// Crafters

		String crafterDescription = "This Registers items to a tool type and efficiency (such as hammer, heavy hammer, knife, saw, etc):\n"
				+ "Order itemid|tooltype|efficiency|tier \n"
				+ "tooltype can be hammer, heavy_hammer, knife, shears, needle, spoon, mallet, saw, spanner, or brush \n"
				+ "efficiency is the measure of how fast it works (similar to dig speed)"
				+ "tier is the tier of the crafter"
				+ "metadata is the metadata of the item, this is optional"
				+ "EXAMPLE: ancientwarfare:iron_hammer|hammer|2.0|3";

		crafterList = config.get(CATEGORY_TOOL, "Crafter Registry", new String[0], crafterDescription).getStringList();
		Arrays.sort(crafterList);

		// Weapons

		String weaponDamageDescription = "This registers weapons for the damage variable mechanics implemented by option. \n"
				+ "Though mod-added armours have absolutely no support, and never can without being specifically coded to \n"
				+ "MineFantasy armours will take these variables and function differently on the values. But weapon items can \n"
				+ "be added to the list: Put each entry on it's own line set out like this: \n"
				+ "id|cutting|pierce|blunt \n"
				+ "id is the item id as a string (you need to find it out yourself), cutting, peirce, and blunt are the ratio. \n"
				+ "metadata is the metadata of the item, it is optional"
				+ "EXAMPLE (for example... making a stick to piercing damage) \n" + "minecraft:stick|0|1.0|0 \n"
				+ "The difference between the ratio is what determines damage 1|0 means 100% cutting damage, 3|1 means it's 3 cutting to 1 blunt (or 75%, 25%). use whatever numbers you need to make the ratio.";

		customDamagerList = config.get(CATEGORY_WEAPONS, "Custom Damage Ratios", new String[0], weaponDamageDescription).getStringList();
		Arrays.sort(customDamagerList);

		String projectileEntityDamageDescription = "Similar method to 'Custom Damage Ratios' only with entities, This is for registering things like arrows, same format only with the entity registry name (usually modid:name)";

		customDamagerEntityList = config.get(CATEGORY_WEAPONS, "Custom Entity Damage Ratios", new String[0], projectileEntityDamageDescription).getStringList();
		Arrays.sort(customDamagerEntityList);

		//Foods
		String foodStatsDescription = "This registers custom food stats for use in the MFR stamina system. \n"
				+ "MineFantasy foods are assigned food stats by default, which affects how they impact the Player's stamina in various ways.\n"
				+ "This is done by assigning the tier of the food, the sugar value of the food, the carbs value of the food, and the fats value of the food \n"
				+ "Put each entry on it's own line, then set it out like this: \n"
				+ "id|tier|sugar|carbs|fats|metadata (optional) \n"
				+ "id is the item id as a string (you need to find it out yourself) and it must be a Food item, i.e, extending from vanilla class ItemFood \n"
				+ "tier will multiply the other food stats. \n"
				+ "sugar will control Stamina restore modifier and Stamina regen modifier. \n"
				+ "carbs will control max Stamina modifier. \n"
				+ "fat will control eat delay modifier and fat accumulation modifier. \n"
				+ "metadata is the metadata of the item, this is optional. \n"
				+ "If your food item takes damage, i.e, like the MFR sandwich, you need to define the entry for each metadata of that item, which should be the item damage."
				+ "EXAMPLE (for example... making a steak have the same stats as MFR Jerky) \n"
				+ "minecraft:cooked_beef|2|0.0|0.0|1.0|0 \n";

		customFoodList = config.get(CATEGORY_FOOD, "Custom Food Stats", new String[0], foodStatsDescription).getStringList();
		Arrays.sort(customFoodList);

		//Stone Pick Overrides
		String stonePickOverrideDescription = "This registers blockstates that the MFR Stone Pick can mine outside of its usual tier limits. \n"
				+ "Most of the time you can get away with just the block registry name, like this: minecraft:dirt or minefantasyreforged:copper_ore \n"
				+ "However, if it has properties, you must enter it exactly as the F3 screen has it, with brackets [] around the properties. Like this: minecraft:dirt[snowy=false,variant=podzol] \n";
		customStonePickOverride = config.get(CATEGORY_STONE_PICK_OVERRIDE, "Stone Pick Overrides", new String[0], stonePickOverrideDescription).getStringList();
		Arrays.sort(customStonePickOverride);
	}
}
