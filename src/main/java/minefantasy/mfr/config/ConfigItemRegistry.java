package minefantasy.mfr.config;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.api.armour.ArmourDesign;
import minefantasy.mfr.api.armour.CustomArmourEntry;
import minefantasy.mfr.api.armour.CustomDamageRatioEntry;
import minefantasy.mfr.api.crafting.CustomCrafterEntry;
import minefantasy.mfr.api.farming.CustomHoeEntry;
import minefantasy.mfr.util.MFRLogUtil;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.Objects;

public class ConfigItemRegistry extends ConfigurationBaseMF {
	public static final String CATEGORY_ARMOUR = "Armour List";
	public static final String CATEGORY_FARM = "Farming";
	public static final String CATEGORY_TOOL = "Tools";
	public static final String CATEGORY_WEPS = "Weapon Register";
	public static String[] armourListAC = new String[0];
	public static String[] hoeList = new String[0];
	public static String[] crafterList = new String[0];
	public static String[] customDamagerList = new String[0];
	public static String[] customDamagerEntityList = new String[0];

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

		CustomArmourEntry.registerItem(armorItem, armourDesign, weightModifier, weightClass);
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
			MineFantasyReforged.LOG.warn("Could not define hoe efficiency '" + entryContents[1] + "' for item id: " + hoeItem.getRegistryName());
			return;
		}

		CustomHoeEntry.registerItem(hoeItem, hoeEfficiency);
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

		CustomCrafterEntry.registerItem(crafterItem, crafterType, crafterEfficiency, crafterTier);
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
			MineFantasyReforged.LOG.warn("Could not define cut damage for '" + entryContents[2] + "' for item id: " + weaponItem.getRegistryName());
			return;
		}

		float blunt = Float.parseFloat(entryContents[2]);
		if (blunt < 0) {
			MineFantasyReforged.LOG.warn("Could not define blunt damage for '" + entryContents[2] + "' for item id: " + weaponItem.getRegistryName());
			return;
		}

		float pierce = Float.parseFloat(entryContents[3]);
		if (pierce < 0) {
			MineFantasyReforged.LOG.warn("Could not define pierce damage for '" + entryContents[2] + "' for item id: " + weaponItem.getRegistryName());
			return;
		}

		CustomDamageRatioEntry.registerItem(weaponItem, new float[] {cut, blunt, pierce});
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
			MineFantasyReforged.LOG.warn("Could not define cut damage for '" + entryContents[2] + "' for projectile entity id: " + projectileEntityID);
			return;
		}

		float blunt = Float.parseFloat(entryContents[2]);
		if (blunt < 0) {
			MineFantasyReforged.LOG.warn("Could not define blunt damage for '" + entryContents[2] + "' for projectile entity id: " + projectileEntityID);
			return;
		}

		float pierce = Float.parseFloat(entryContents[3]);
		if (pierce < 0) {
			MineFantasyReforged.LOG.warn("Could not define pierce damage for '" + entryContents[2] + "' for projectile entity id: " + projectileEntityID);
			return;
		}

		CustomDamageRatioEntry.registerEntity(new ResourceLocation(projectileEntityID), new float[] {cut, blunt, pierce});
		MineFantasyReforged.LOG.info("Added Custom Projectile Entity Damage entry for " + projectileEntityID + " with damage stats: " + Arrays.toString(new float[] {cut, blunt, pierce}));

	}

	@Override
	protected void loadConfig() {
		// Weight

		String ArmorRegistryDescription = "This will register items under a certain 'Design' calculating the variables itself.\n Each entry has it's own line:\n"
				+ "Order itemid|Design|WeightGroup|WeightModifier \n"
				+ "The WeightModifier alters the weight for heavier or lighter materials keep it at 1.0 unless you have a special material (like mithril and adamamantium)\n"
				+ "Designs can be any that are registered: MineFantasy designs are 'clothing', 'leather', 'mail', 'solid'(that's just basic metal armour), and 'plate'\n"
				+ "WeightGroup refers to whether it is light medium or heavy armour \n"
				+ "EXAMPLE (This is what vanilla gold is registered under) \n"
				+ "minecraft:golden_helmet|solid|medium|2.0 \n" + "minecraft:golden_chestplate|solid|medium|2.0 \n"
				+ "minecraft:golden_leggings|solid|medium|2.0 \n" + "minecraft:golden_boots|solid|medium|2.0 \n"
				+ "The 2.0 means it is 2x heavier than other vanilla armours \n"
				+ "This does not override existing MF armours \n";

		armourListAC = config.get(CATEGORY_ARMOUR, "Armour Registry", new String[0], ArmorRegistryDescription).getStringList();
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
				+ "EXAMPLE: ancientwarfare:iron_hammer|hammer|2.0|3";

		crafterList = config.get(CATEGORY_TOOL, "Crafter Registry", new String[0], crafterDescription).getStringList();
		Arrays.sort(crafterList);

		// Weapons

		String weaponDamageDescription = "This registers weapons for the damage variable mechanics implemented by option. \n"
				+ "Though mod-added armours have absolutely no support, and never can without being specifically coded to \n"
				+ "MineFantasy armours will take these variables and function differently on the values. But weapon items can \n"
				+ "be added to the list: Put each entry on it's own line set out like this: \n"
				+ "id|cutting|pierce|blunt \n"
				+ "id is the item id as a string (you need to find it out yourself), cutting and blunt are the ratio. \n"
				+ "EXAMPLE (for example... making a stick to piercing damage) \n" + "minecraft:stick|0|1.0|0 \n"
				+ "The difference between the ratio is what determines damage 1|0 means 100% cutting damage, 3|1 means it's 3 cutting to 1 blunt (or 75%, 25%). use whatever numbers you need to make the ratio.";

		customDamagerList = config.get(CATEGORY_WEPS, "Custom Damage Ratios", new String[0], weaponDamageDescription).getStringList();
		Arrays.sort(customDamagerList);

		String projectileEntityDamageDescription = "Similar method to 'Custom Damage Ratios' only with entities, This is for registering things like arrows, same format only with the entity registry name (usually modid:name)";

		customDamagerEntityList = config.get(CATEGORY_WEPS, "Custom Entity Damage Ratios", new String[0], projectileEntityDamageDescription).getStringList();
		Arrays.sort(customDamagerEntityList);
	}
}
