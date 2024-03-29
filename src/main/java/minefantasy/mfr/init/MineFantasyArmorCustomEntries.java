package minefantasy.mfr.init;

import minefantasy.mfr.api.armour.ArmourDesign;
import minefantasy.mfr.api.armour.CustomArmourEntry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;

public class MineFantasyArmorCustomEntries {

	public static void initVanillaArmorEntries() {
		CustomArmourEntry.registerItem(Items.LEATHER_HELMET, ArmourDesign.LEATHER);
		CustomArmourEntry.registerItem(Items.LEATHER_CHESTPLATE, ArmourDesign.LEATHER);
		CustomArmourEntry.registerItem(Items.LEATHER_LEGGINGS, ArmourDesign.LEATHER);
		CustomArmourEntry.registerItem(Items.LEATHER_BOOTS, ArmourDesign.LEATHER);

		CustomArmourEntry.registerItem(Items.CHAINMAIL_HELMET, ArmourDesign.MAIL);
		CustomArmourEntry.registerItem(Items.CHAINMAIL_CHESTPLATE, ArmourDesign.MAIL);
		CustomArmourEntry.registerItem(Items.CHAINMAIL_LEGGINGS, ArmourDesign.MAIL);
		CustomArmourEntry.registerItem(Items.CHAINMAIL_BOOTS, ArmourDesign.MAIL);

		CustomArmourEntry.registerItem(Items.IRON_HELMET, ArmourDesign.SOLID, 1.5F, "medium");
		CustomArmourEntry.registerItem(Items.IRON_CHESTPLATE, ArmourDesign.SOLID, 1.5F, "medium");
		CustomArmourEntry.registerItem(Items.IRON_LEGGINGS, ArmourDesign.SOLID, 1.5F, "medium");
		CustomArmourEntry.registerItem(Items.IRON_BOOTS, ArmourDesign.SOLID, 1.5F, "medium");

		CustomArmourEntry.registerItem(Items.GOLDEN_HELMET, ArmourDesign.SOLID, 2F, "medium");
		CustomArmourEntry.registerItem(Items.GOLDEN_CHESTPLATE, ArmourDesign.SOLID, 2F, "medium");
		CustomArmourEntry.registerItem(Items.GOLDEN_LEGGINGS, ArmourDesign.SOLID, 2F, "medium");
		CustomArmourEntry.registerItem(Items.GOLDEN_BOOTS, ArmourDesign.SOLID, 2F, "medium");

		CustomArmourEntry.registerItem(Items.DIAMOND_HELMET, ArmourDesign.SOLID, 2.5F, "medium");
		CustomArmourEntry.registerItem(Items.DIAMOND_CHESTPLATE, ArmourDesign.SOLID, 2.5F, "medium");
		CustomArmourEntry.registerItem(Items.DIAMOND_LEGGINGS, ArmourDesign.SOLID, 2.5F, "medium");
		CustomArmourEntry.registerItem(Items.DIAMOND_BOOTS, ArmourDesign.SOLID, 2.5F, "medium");

		CustomArmourEntry.registerItem(Items.ELYTRA, ArmourDesign.CLOTH, 0.25F, "light");

		CustomArmourEntry.registerItem(Items.SKULL, ArmourDesign.CLOTH, 0.25F, "light");
	}

	public static void initModdedArmorCustomEntries() {
		if (Loader.isModLoaded("bewitchment")) {
			loadBewitchment();
		}
		if(Loader.isModLoaded("ebwizardry")) {
			loadEbWizardry();
		}
		if(Loader.isModLoaded("thaumcraft")) {
			loadThaumcraft();
		}
		if(Loader.isModLoaded("mowziesmobs")) {
			loadMowziesMobs();
		}
		if(Loader.isModLoaded("millenaire")) {
			loadMillenaire();
		}
	}

	private static void loadEbWizardry() {
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_boots_earth"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_boots_fire"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_boots_healing"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_boots_ice"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_boots_lightning"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_boots_necromancy"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_boots_sorcery"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_boots"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_chestplate_earth"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_chestplate_fire"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_chestplate_healing"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_chestplate_ice"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_chestplate_lightning"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_chestplate_necromancy"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_chestplate_sorcery"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_chestplate"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_helmet_earth"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_helmet_fire"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_helmet_healing"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_helmet_ice"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_helmet_lightning"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_helmet_necromancy"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_helmet_sorcery"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_helmet"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_leggings_earth"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_leggings_fire"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_leggings_healing"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_leggings_ice"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_leggings_lightning"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_leggings_necromancy"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_leggings_sorcery"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:battlemage_leggings"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_boots_earth"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_boots_fire"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_boots_healing"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_boots_ice"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_boots_lightning"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_boots_necromancy"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_boots_sorcery"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_boots"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_hat_earth"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_hat_fire"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_hat_healing"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_hat_ice"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_hat_lightning"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_hat_necromancy"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_hat_sorcery"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_hat"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_leggings_earth"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_leggings_fire"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_leggings_healing"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_leggings_ice"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_leggings_lightning"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_leggings_necromancy"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_leggings_sorcery"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_leggings"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_robe_earth"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_robe_fire"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_robe_healing"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_robe_ice"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_robe_lightning"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_robe_necromancy"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_robe_sorcery"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:sage_robe"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_boots_earth"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_boots_fire"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_boots_healing"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_boots_ice"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_boots_lightning"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_boots_necromancy"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_boots_sorcery"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_boots"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_hood_earth"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_hood_fire"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_hood_healing"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_hood_ice"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_hood_lightning"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_hood_necromancy"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_hood_sorcery"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_hood"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_leggings_earth"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_leggings_fire"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_leggings_healing"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_leggings_ice"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_leggings_lightning"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_leggings_necromancy"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_leggings_sorcery"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_leggings"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_robe_earth"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_robe_fire"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_robe_healing"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_robe_ice"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_robe_lightning"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_robe_necromancy"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_robe_sorcery"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:warlock_robe"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_boots_earth"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_boots_fire"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_boots_healing"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_boots_ice"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_boots_lightning"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_boots_necromancy"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_boots_sorcery"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_boots"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_hat_earth"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_hat_fire"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_hat_healing"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_hat_ice"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_hat_lightning"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_hat_necromancy"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_hat_sorcery"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_hat"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_leggings_earth"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_leggings_fire"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_leggings_healing"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_leggings_ice"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_leggings_lightning"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_leggings_necromancy"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_leggings_sorcery"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_leggings"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_robe_earth"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_robe_fire"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_robe_healing"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_robe_ice"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_robe_lightning"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_robe_necromancy"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_robe_sorcery"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("ebwizardry:wizard_robe"),
				ArmourDesign.CLOTH,
				1F,
				"light");
	}

	private static void loadBewitchment() {
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("bewitchment:alchemist_hat"),
				ArmourDesign.CLOTH,
				0.5F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("bewitchment:alchemist_hood"),
				ArmourDesign.CLOTH,
				0.5F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("bewitchment:alchemist_pants"),
				ArmourDesign.CLOTH,
				0.5F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("bewitchment:alchemist_robes"),
				ArmourDesign.CLOTH,
				0.5F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("bewitchment:besmirched_hat"),
				ArmourDesign.CLOTH,
				0.5F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("bewitchment:besmirched_hood"),
				ArmourDesign.CLOTH,
				0.5F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("bewitchment:besmirched_pants"),
				ArmourDesign.CLOTH,
				0.5F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("bewitchment:besmirched_robes"),
				ArmourDesign.CLOTH,
				0.5F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("bewitchment:green_witch_hat"),
				ArmourDesign.CLOTH,
				0.5F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("bewitchment:green_witch_hood"),
				ArmourDesign.CLOTH,
				0.5F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("bewitchment:green_witch_pants"),
				ArmourDesign.CLOTH,
				0.5F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("bewitchment:green_witch_robes"),
				ArmourDesign.CLOTH,
				0.5F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("bewitchment:witches_cowl"),
				ArmourDesign.CLOTH,
				0.5F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("bewitchment:witches_hat"),
				ArmourDesign.CLOTH,
				0.5F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("bewitchment:witches_pants"),
				ArmourDesign.CLOTH,
				0.5F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("bewitchment:witches_robes"),
				ArmourDesign.CLOTH,
				0.5F,
				"light");
	}

	private static void loadThaumcraft() {
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("thaumcraft:cloth_boots"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("thaumcraft:cloth_legs"),
				ArmourDesign.CLOTH,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("thaumcraft:crimson_boots"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("thaumcraft:crimson_plate_chest"),
				ArmourDesign.PLATE,
				0.25F,
				"heavy");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("thaumcraft:crimson_plate_helm"),
				ArmourDesign.PLATE,
				0.25F,
				"heavy");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("thaumcraft:crimson_plate_legs"),
				ArmourDesign.PLATE,
				0.25F,
				"heavy");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("thaumcraft:goggles"),
				ArmourDesign.CLOTH,
				0.4F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("thaumcraft:thaumium_boots"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("thaumcraft:thaumium_chest"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("thaumcraft:thaumium_helm"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("thaumcraft:thaumium_legs"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("thaumcraft:traveller_boots"),
				ArmourDesign.LEATHER,
				1F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("thaumcraft:void_boots"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("thaumcraft:void_chest"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("thaumcraft:void_helm"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("thaumcraft:void_legs"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
	}

	private static void loadMowziesMobs() {
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("mowziesmobs:barakoa_mask_bliss"),
				ArmourDesign.LEATHER,
				0.8F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("mowziesmobs:barakoa_mask_fear"),
				ArmourDesign.LEATHER,
				0.8F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("mowziesmobs:barakoa_mask_fury"),
				ArmourDesign.LEATHER,
				0.8F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("mowziesmobs:barakoa_mask_misery"),
				ArmourDesign.LEATHER,
				0.8F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("mowziesmobs:barakoa_mask_rage"),
				ArmourDesign.LEATHER,
				0.8F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("mowziesmobs:barako_mask"),
				ArmourDesign.LEATHER,
				0.8F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("mowziesmobs:wrought_helmet"),
				ArmourDesign.PLATE,
				0.25F,
				"medium");
	}

	private static void loadMillenaire() {
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("millenaire:byzantineboots"),
				ArmourDesign.MAIL,
				0.5F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("millenaire:byzantinehelmet"),
				ArmourDesign.MAIL,
				0.5F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("millenaire:byzantinelegs"),
				ArmourDesign.MAIL,
				0.5F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("millenaire:byzantineplate"),
				ArmourDesign.MAIL,
				0.5F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("millenaire:furboots"),
				ArmourDesign.MAIL,
				0.5F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("millenaire:furhelmet"),
				ArmourDesign.MAIL,
				0.5F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("millenaire:furlegs"),
				ArmourDesign.MAIL,
				0.5F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("millenaire:furplate"),
				ArmourDesign.MAIL,
				0.5F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("millenaire:japaneseblueboots"),
				ArmourDesign.MAIL,
				0.5F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("millenaire:japanesebluehelmet"),
				ArmourDesign.MAIL,
				0.5F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("millenaire:japanesebluelegs"),
				ArmourDesign.MAIL,
				0.5F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("millenaire:japaneseblueplate"),
				ArmourDesign.MAIL,
				0.5F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("millenaire:japaneseguardboots"),
				ArmourDesign.MAIL,
				0.5F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("millenaire:japaneseguardhelmet"),
				ArmourDesign.MAIL,
				0.5F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("millenaire:japaneseguardlegs"),
				ArmourDesign.MAIL,
				0.5F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("millenaire:japaneseguardplate"),
				ArmourDesign.MAIL,
				0.5F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("millenaire:japaneseredboots"),
				ArmourDesign.MAIL,
				0.5F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("millenaire:japaneseredhelmet"),
				ArmourDesign.MAIL,
				0.5F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("millenaire:japaneseredlegs"),
				ArmourDesign.MAIL,
				0.5F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("millenaire:japaneseredplate"),
				ArmourDesign.MAIL,
				0.5F,
				"light");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("millenaire:normanboots"),
				ArmourDesign.MAIL,
				0.5F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("millenaire:normanhelmet"),
				ArmourDesign.MAIL,
				0.5F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("millenaire:normanlegs"),
				ArmourDesign.MAIL,
				0.5F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("millenaire:normanplate"),
				ArmourDesign.MAIL,
				0.5F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("millenaire:seljukboots"),
				ArmourDesign.MAIL,
				0.5F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("millenaire:seljukhelmet"),
				ArmourDesign.MAIL,
				0.5F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("millenaire:seljuklegs"),
				ArmourDesign.MAIL,
				0.5F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("millenaire:seljukplate"),
				ArmourDesign.MAIL,
				0.5F,
				"medium");
		CustomArmourEntry.registerItem(
				getItemFromRegistryName("millenaire:seljukturban"),
				ArmourDesign.MAIL,
				0.5F,
				"light");
	}

	private static Item getItemFromRegistryName(String itemRegistry) {
		return Item.REGISTRY.getObject(new ResourceLocation(itemRegistry));
	}
}
