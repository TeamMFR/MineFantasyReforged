package minefantasy.mfr.config;

public class ConfigWorldGen extends ConfigurationBaseMF {
	public static String CATEGORY_COPPER = "1A: [Ore Gen] Copper Ore";
	public static String CATEGORY_TIN = "1B: [Ore Gen] Tin Ore";
	public static String CATEGORY_SILVER = "1C: [Ore Gen] Silver Ore";
	public static String CATEGORY_WOLFRAMITE = "1D: [Ore Gen] Wolframite Ore";
	public static String CATEGORY_MYTHIC = "1E: [Ore Gen] Mythic Ore";
	public static String CATEGORY_KAOLINITE = "2A: [Mineral Gen] Kaolinite Deposit";
	public static String CATEGORY_CLAY = "2B: [Mineral Gen] Clay Deposit";
	public static String CATEGORY_NITRE = "2C: [Mineral Gen] Nitre Deposit";
	public static String CATEGORY_SULFUR = "2D: [Mineral Gen] Sulfur Deposit";
	public static String CATEGORY_BORAX = "2E: [Mineral Gen] Borax Deposit";
	public static String CATEGORY_RICH_COAL = "2F: [Mineral Gen] Rich Coal Deposit";
	public static String CATEGORY_LIMESTONE = "3A: [Rock Gen] Limestone";

	public static String CATEGORY_BERRY = "4A: [Plant Gen] Berry Bush";
	public static String CATEGORY_YEW = "5A: [Tree Gen] Yew";
	public static String CATEGORY_IRONBARK = "5B: [Tree Gen] Ironbark";
	public static String CATEGORY_EBONY = "5C: [Tree Gen] Ebony";

	public static String CATEGORY_STRUCTURE_GENERAL = "6A: [Structure Gen] General";
	public static String CATEGORY_ANCIENT_FORGE = "6B: [Structure Gen] Ancient Forge";
	public static String CATEGORY_ANCIENT_ALTAR = "6C: [Structure Gen] Ancient Altar";
	public static String CATEGORY_DWARVEN_STRONGHOLD = "6D: [Structure Gen] Dwarven Stronghold";
	public static float copperRarity;
	public static int copperFrequencyMin;
	public static int copperFrequencyMax;
	public static int copperLayerMin;
	public static int copperLayerMax;
	public static int copperSize;

	public static float tinRarity;
	public static int tinFrequencyMin;
	public static int tinFrequencyMax;
	public static int tinLayerMin;
	public static int tinLayerMax;
	public static int tinSize;

	public static float silverRarity;
	public static int silverFrequencyMin;
	public static int silverFrequencyMax;
	public static int silverLayerMin;
	public static int silverLayerMax;
	public static int silverSize;

	public static float wolframiteRarity;
	public static int wolframiteFrequencyMin;
	public static int wolframiteFrequencyMax;
	public static int wolframiteLayerMin;
	public static int wolframiteLayerMax;
	public static int wolframiteSize;

	public static float mythicRarity;
	public static int mythicFrequencyMin;
	public static int mythicFrequencyMax;
	public static int mythicLayerMin;
	public static int mythicLayerMax;
	public static int mythicSize;

	public static float kaoliniteRarity;
	public static int kaoliniteFrequencyMin;
	public static int kaoliniteFrequencyMax;
	public static int kaoliniteLayerMin;
	public static int kaoliniteLayerMax;
	public static int kaoliniteSize;

	public static float clayRarity;
	public static int clayFrequencyMin;
	public static int clayFrequencyMax;
	public static int clayLayerMin;
	public static int clayLayerMax;
	public static int claySize;

	public static float nitreRarity;
	public static int nitreFrequencyMin;
	public static int nitreFrequencyMax;
	public static int nitreLayerMin;
	public static int nitreLayerMax;
	public static int nitreSize;

	public static float sulfurRarity;
	public static int sulfurFrequencyMin;
	public static int sulfurFrequencyMax;
	public static int sulfurLayerMin;
	public static int sulfurLayerMax;
	public static int sulfurSize;

	public static float boraxRarity;
	public static int boraxFrequencyMin;
	public static int boraxFrequencyMax;
	public static int boraxLayerMin;
	public static int boraxLayerMax;
	public static int boraxSize;

	public static float coalRarity;
	public static int coalFrequencyMin;
	public static int coalFrequencyMax;
	public static int coalLayerMin;
	public static int coalLayerMax;
	public static int coalSize;

	public static float limestoneRarity;
	public static int limestoneFrequencyMax;
	public static int limestoneFrequencyMin;
	public static int limestoneLayerMax;
	public static int limestoneLayerMin;
	public static int limestoneSize;

	public static int berryRarity;
	public static int berryGroupSize;
	public static float berryMinTemp;
	public static float berryMaxTemp;
	public static float berryMinRain;
	public static float berryMaxRain;

	public static float yewRarity;
	public static float yewMinTemp;
	public static float yewMaxTemp;
	public static float yewMinRain;
	public static float yewMaxRain;

	public static float ironbarkRarity;
	public static float ironbarkMinTemp;
	public static float ironbarkMaxTemp;
	public static float ironbarkMinRain;
	public static float ironbarkMaxRain;

	public static float ebonyRarity;
	public static float ebonyMinTemp;
	public static float ebonyMaxTemp;
	public static float ebonyMinRain;
	public static float ebonyMaxRain;

	public static int structureTickRate;

	public static float ancientForgeSpawnChance;
	public static int ancientForgeGrid;

	public static float ancientAltarSpawnChance;
	public static int ancientAltarGrid;

	public static float dwarvenStrongholdSpawnChance;
	public static int dwarvenStrongholdGrid;
	public static int dwarvenStrongholdLength;
	public static int dwarvenStrongholdDeviations;

	public static boolean dwarvenStrongholdShouldFurnaceSpawn;

	public static boolean dwarvenStrongholdShouldCauldronSpawn;

	public ConfigWorldGen(String name) {
		super(name);
	}

	@Override
	protected void initializeCategories() {
		config.addCustomCategoryComment(CATEGORY_COPPER, "Controls how Copper Ore spawns");
		config.addCustomCategoryComment(CATEGORY_TIN, "Controls how Tin Ore spawns");
		config.addCustomCategoryComment(CATEGORY_SILVER, "Controls how Silver Ore spawns");
		config.addCustomCategoryComment(CATEGORY_WOLFRAMITE, "Controls how Wolframite Ore spawns");
		config.addCustomCategoryComment(CATEGORY_MYTHIC, "Controls how Mythic Ore spawns");
		config.addCustomCategoryComment(CATEGORY_KAOLINITE, "Controls how Kaolinite Ore spawns");
		config.addCustomCategoryComment(CATEGORY_CLAY, "Controls how Rich Clay spawns");
		config.addCustomCategoryComment(CATEGORY_NITRE, "Controls how Nitre Ore spawns");
		config.addCustomCategoryComment(CATEGORY_SULFUR, "Controls how Sulfur Ore spawns");
		config.addCustomCategoryComment(CATEGORY_BORAX, "Controls how Borax Ore spawns");
		config.addCustomCategoryComment(CATEGORY_RICH_COAL, "Controls how Rich Coal Ore spawns");
		config.addCustomCategoryComment(CATEGORY_LIMESTONE, "Controls how Limestone Ore spawns");

		config.addCustomCategoryComment(CATEGORY_BERRY, "Controls how Berry Bushes spawn");
		config.addCustomCategoryComment(CATEGORY_YEW, "Controls how Yew Trees spawn");
		config.addCustomCategoryComment(CATEGORY_IRONBARK, "Controls how Ironbark Trees spawn");
		config.addCustomCategoryComment(CATEGORY_EBONY, "Controls how Ebony Trees spawn");

		config.addCustomCategoryComment(CATEGORY_STRUCTURE_GENERAL, "General Structure Settings");
		config.addCustomCategoryComment(CATEGORY_ANCIENT_FORGE, "Controls how Ancient Forges spawn");
		config.addCustomCategoryComment(CATEGORY_ANCIENT_ALTAR, "Controls how Ancient Altars spawn");
		config.addCustomCategoryComment(CATEGORY_DWARVEN_STRONGHOLD, "Controls how Dwarven Strongholds spawn");
	}

	@Override
	protected void initializeValues() {
		copperRarity = Float.parseFloat(config.get(CATEGORY_COPPER, "Copper Rarity", 1.0D,
				"The chance for copper to spawn in a chunk. (0=never, 1.0=always), this means some chunks may not have any copper")
				.getString());
		copperFrequencyMin = Integer.parseInt(config
				.get(CATEGORY_COPPER, "Copper Frequency Min", 8, "Copper will try spawn between this and max veins per chunk")
				.getString());
		copperFrequencyMax = Integer.parseInt(config
				.get(CATEGORY_COPPER, "Copper Frequency Max", 8, "Copper will try spawn between min and this veins per chunk")
				.getString());
		copperLayerMin = Integer.parseInt(
				config.get(CATEGORY_COPPER, "Copper Layer Min", 48, "Copper veins spawn above this layer").getString());
		copperLayerMax = Integer.parseInt(
				config.get(CATEGORY_COPPER, "Copper Layer Max", 96, "Copper veins spawn below this layer").getString());
		copperSize = Integer
				.parseInt(config.get(CATEGORY_COPPER, "Copper Size", 8, "How many blocks consist of the vein").getString());

		tinRarity = Float.parseFloat(config.get(CATEGORY_TIN, "Tin Rarity", 1.0D,
				"The chance for tin to spawn in a chunk. (0=never, 1.0=always), this means some chunks may not have any tin")
				.getString());
		tinFrequencyMin = Integer.parseInt(
				config.get(CATEGORY_TIN, "Tin Frequency Min", 8, "Tin will try spawn between this and max veins per chunk")
						.getString());
		tinFrequencyMax = Integer.parseInt(
				config.get(CATEGORY_TIN, "Tin Frequency Max", 8, "Tin will try spawn between min and this veins per chunk")
						.getString());
		tinLayerMin = Integer
				.parseInt(config.get(CATEGORY_TIN, "Tin Layer Min", 48, "Tin veins spawn above this layer").getString());
		tinLayerMax = Integer
				.parseInt(config.get(CATEGORY_TIN, "Tin Layer Max", 96, "Tin veins spawn below this layer").getString());
		tinSize = Integer.parseInt(config.get(CATEGORY_TIN, "Tin Size", 5, "How many blocks consist of the vein").getString());

		silverRarity = Float.parseFloat(config.get(CATEGORY_SILVER, "Silver Rarity", 1.0D,
				"The chance for silver to spawn in a chunk. (0=never, 1.0=always), this means some chunks may not have any ")
				.getString());
		silverFrequencyMin = Integer.parseInt(config
				.get(CATEGORY_SILVER, "Silver Frequency Min", 3, "Silver will try spawn between this and max veins per chunk")
				.getString());
		silverFrequencyMax = Integer.parseInt(config
				.get(CATEGORY_SILVER, "Silver Frequency Max", 4, "Silver will try spawn between min and this veins per chunk")
				.getString());
		silverLayerMin = Integer
				.parseInt(config.get(CATEGORY_SILVER, "Silver Layer Min", 0, "Silver veins spawn above this layer").getString());
		silverLayerMax = Integer.parseInt(
				config.get(CATEGORY_SILVER, "Silver Layer Max", 32, "Silver veins spawn below this layer").getString());
		silverSize = Integer
				.parseInt(config.get(CATEGORY_SILVER, "Silver Size", 8, "How many blocks consist of the vein").getString());

		wolframiteRarity = Float.parseFloat(config.get(CATEGORY_WOLFRAMITE, "Wolframite Rarity", 1.0D,
				"The chance for wolframite to spawn in a chunk. (0=never, 1.0=always), this means some chunks may not have any ")
				.getString());
		wolframiteFrequencyMin = Integer.parseInt(config.get(CATEGORY_WOLFRAMITE, "Wolframite Frequency Min", 1,
				"Wolframite will try spawn between this and max veins per chunk").getString());
		wolframiteFrequencyMax = Integer.parseInt(config.get(CATEGORY_WOLFRAMITE, "Wolframite Frequency Max", 1,
				"Wolframite will try spawn between min and this veins per chunk").getString());
		wolframiteLayerMin = Integer.parseInt(config
				.get(CATEGORY_WOLFRAMITE, "Wolframite Layer Min", 0, "Wolframite veins spawn above this layer").getString());
		wolframiteLayerMax = Integer.parseInt(config
				.get(CATEGORY_WOLFRAMITE, "Wolframite Layer Max", 16, "Wolframite veins spawn below this layer").getString());
		wolframiteSize = Integer.parseInt(
				config.get(CATEGORY_WOLFRAMITE, "Wolframite Size", 7, "How many blocks consist of the vein").getString());

		mythicRarity = Float.parseFloat(config.get(CATEGORY_MYTHIC, "Mythic Rarity", 0.05D,
				"The chance for mythic to spawn in a chunk. (0=never, 1.0=always), this means some chunks may not have any mythic")
				.getString());
		mythicFrequencyMin = Integer.parseInt(config
				.get(CATEGORY_MYTHIC, "Mythic Frequency Min", 2, "Mythic will try spawn between this and max veins per chunk")
				.getString());
		mythicFrequencyMax = Integer.parseInt(config
				.get(CATEGORY_MYTHIC, "Mythic Frequency Max", 5, "Mythic will try spawn between min and this veins per chunk")
				.getString());
		mythicLayerMin = Integer
				.parseInt(config.get(CATEGORY_MYTHIC, "Mythic Layer Min", 4, "Mythic veins spawn above this layer").getString());
		mythicLayerMax = Integer
				.parseInt(config.get(CATEGORY_MYTHIC, "Mythic Layer Max", 6, "Mythic veins spawn below this layer").getString());
		mythicSize = Integer
				.parseInt(config.get(CATEGORY_MYTHIC, "Mythic Size", 8, "How many blocks consist of the vein").getString());

		kaoliniteRarity = Float.parseFloat(config.get(CATEGORY_KAOLINITE, "Kaolinite Rarity", 0.25D,
				"The chance for kaolinite to spawn in a chunk. (0=never, 1.0=always), this means some chunks may not have any kaolinite")
				.getString());
		kaoliniteFrequencyMin = Integer.parseInt(config.get(CATEGORY_KAOLINITE, "Kaolinite Frequency Min", 1,
				"Kaolinite will try spawn between this and max deposits per chunk").getString());
		kaoliniteFrequencyMax = Integer.parseInt(config.get(CATEGORY_KAOLINITE, "Kaolinite Frequency Max", 1,
				"Kaolinite will try spawn between min and this deposits per chunk").getString());
		kaoliniteLayerMin = Integer.parseInt(config
				.get(CATEGORY_KAOLINITE, "Kaolinite Layer Min", 48, "Kaolinite deposits spawn above this layer").getString());
		kaoliniteLayerMax = Integer.parseInt(config
				.get(CATEGORY_KAOLINITE, "Kaolinite Layer Max", 72, "Kaolinite deposits spawn below this layer").getString());
		kaoliniteSize = Integer.parseInt(
				config.get(CATEGORY_KAOLINITE, "Kaolinite Size", 16, "How many blocks consist of the deposit").getString());

		// Coal: 20 deposits of 16: Up to 320 coal value per chunk
		// Rich Coal: 10 deposits of 8: up to 80 coal value
		coalRarity = Float.parseFloat(config.get(CATEGORY_RICH_COAL, "Rich Coal Rarity", 1F,
				"The chance for rich coal to spawn in a chunk. (0=never, 1.0=always), this means some chunks may not have any rich coal deposits")
				.getString());
		coalFrequencyMin = Integer.parseInt(config.get(CATEGORY_RICH_COAL, "Rich Coal Frequency Min", 5,
				"Rich Coal will try spawn between this and max deposits per chunk").getString());
		coalFrequencyMax = Integer.parseInt(config.get(CATEGORY_RICH_COAL, "Rich Coal Frequency Max", 5,
				"Rich Coal will try spawn between min and this deposits per chunk").getString());
		coalLayerMin = Integer.parseInt(
				config.get(CATEGORY_RICH_COAL, "Rich Coal Layer Min", 0, "Rich Coal deposits spawn above this layer").getString());
		coalLayerMax = Integer.parseInt(
				config.get(CATEGORY_RICH_COAL, "Rich Coal Layer Max", 64, "Rich Coal deposits spawn below this layer").getString());
		coalSize = Integer
				.parseInt(config.get(CATEGORY_RICH_COAL, "Rich Coal Size", 8, "How many blocks consist of the deposit").getString());

		clayRarity = Float.parseFloat(config.get(CATEGORY_CLAY, "Clay Rarity", 0.15D,
				"The chance for clay to spawn in a chunk. (0=never, 1.0=always), this means some chunks may not have any clay")
				.getString());
		clayFrequencyMin = Integer.parseInt(
				config.get(CATEGORY_CLAY, "Clay Frequency Min", 1, "Clay will try spawn between this and max deposits per chunk")
						.getString());
		clayFrequencyMax = Integer.parseInt(
				config.get(CATEGORY_CLAY, "Clay Frequency Max", 1, "Clay will try spawn between min and this deposits per chunk")
						.getString());
		clayLayerMin = Integer
				.parseInt(config.get(CATEGORY_CLAY, "Clay Layer Min", 60, "Clay deposits spawn above this layer").getString());
		clayLayerMax = Integer
				.parseInt(config.get(CATEGORY_CLAY, "Clay Layer Max", 68, "Clay deposits spawn below this layer").getString());
		claySize = Integer
				.parseInt(config.get(CATEGORY_CLAY, "Clay Size", 32, "How many blocks consist of the deposit").getString());

		nitreRarity = Float.parseFloat(config.get(CATEGORY_NITRE, "Nitre Rarity", 1.0D,
				"The chance for nitre to spawn in a chunk. (0=never, 1.0=always), this means some chunks may not have any nitre")
				.getString());
		nitreFrequencyMin = Integer.parseInt(config
				.get(CATEGORY_NITRE, "Nitre Frequency Min", 2, "Nitre will try spawn between this and max deposits per chunk")
				.getString());
		nitreFrequencyMax = Integer.parseInt(config
				.get(CATEGORY_NITRE, "Nitre Frequency Max", 5, "Nitre will try spawn between min and this deposits per chunk")
				.getString());
		nitreLayerMin = Integer.parseInt(
				config.get(CATEGORY_NITRE, "Nitre Layer Min", 16, "Nitre deposits spawn above this layer").getString());
		nitreLayerMax = Integer.parseInt(
				config.get(CATEGORY_NITRE, "Nitre Layer Max", 64, "Nitre deposits spawn below this layer").getString());
		nitreSize = Integer
				.parseInt(config.get(CATEGORY_NITRE, "Nitre Size", 8, "How many blocks consist of the deposit").getString());

		sulfurRarity = Float.parseFloat(config.get(CATEGORY_SULFUR, "Sulfur Rarity", 1.0D,
				"The chance for sulfur to spawn in a chunk. (0=never, 1.0=always), this means some chunks may not have any sulfur")
				.getString());
		sulfurFrequencyMin = Integer.parseInt(config
				.get(CATEGORY_SULFUR, "Sulfur Frequency Min", 6, "Sulfur will try spawn between this and max deposits per chunk")
				.getString());
		sulfurFrequencyMax = Integer.parseInt(config.get(CATEGORY_SULFUR, "Sulfur Frequency Max", 12,
				"Sulfur will try spawn between min and this deposits per chunk").getString());
		sulfurLayerMin = Integer.parseInt(
				config.get(CATEGORY_SULFUR, "Sulfur Layer Min", 0, "Sulfur deposits spawn above this layer").getString());
		sulfurLayerMax = Integer.parseInt(
				config.get(CATEGORY_SULFUR, "Sulfur Layer Max", 16, "Sulfur deposits spawn below this layer").getString());
		sulfurSize = Integer
				.parseInt(config.get(CATEGORY_SULFUR, "Sulfur Size", 4, "How many blocks consist of the deposit").getString());

		boraxRarity = Float.parseFloat(config.get(CATEGORY_BORAX, "Borax Rarity", 0.1D,
				"The chance for borax to spawn in a chunk. (0=never, 1.0=always), this means some chunks may not have any borax")
				.getString());
		boraxFrequencyMin = Integer.parseInt(config
				.get(CATEGORY_BORAX, "Borax Frequency Min", 5, "Borax will try spawn between this and max deposits per chunk")
				.getString());
		boraxFrequencyMax = Integer.parseInt(config
				.get(CATEGORY_BORAX, "Borax Frequency Max", 10, "Borax will try spawn between min and this deposits per chunk")
				.getString());
		boraxLayerMin = Integer.parseInt(
				config.get(CATEGORY_BORAX, "Borax Layer Min", 48, "Borax deposits spawn above this layer").getString());
		boraxLayerMax = Integer.parseInt(
				config.get(CATEGORY_BORAX, "Borax Layer Max", 96, "Borax deposits spawn below this layer").getString());
		boraxSize = Integer
				.parseInt(config.get(CATEGORY_BORAX, "Borax Size", 8, "How many blocks consist of the deposit").getString());

		limestoneRarity = Float.parseFloat(config.get(CATEGORY_LIMESTONE, "Limestone Rarity", 2.5E-2D,
				"The chance for limestone to spawn in a chunk. (0=never, 1.0=always), this means some chunks may not have any limestone")
				.getString());
		limestoneFrequencyMin = Integer.parseInt(config.get(CATEGORY_LIMESTONE, "Limestone Frequency Min", 1,
				"Limestone will try spawn between this and max deposits per chunk").getString());
		limestoneFrequencyMax = Integer.parseInt(config.get(CATEGORY_LIMESTONE, "Limestone Frequency Max", 2,
				"Limestone will try spawn between min and this deposits per chunk").getString());
		limestoneLayerMin = Integer.parseInt(config
				.get(CATEGORY_LIMESTONE, "Limestone Layer Min", 48, "Limestone deposits spawn above this layer").getString());
		limestoneLayerMax = Integer.parseInt(config
				.get(CATEGORY_LIMESTONE, "Limestone Layer Max", 96, "Limestone deposits spawn below this layer").getString());
		limestoneSize = Integer.parseInt(
				config.get(CATEGORY_LIMESTONE, "Limestone Size", 48, "How many blocks consist of the deposit").getString());

		berryRarity = Integer.parseInt(config.get(CATEGORY_BERRY, "Berry Bush Rarity", 20,
				"The chance for berry bushes to spawn in a chunk.")
				.getString());
		berryGroupSize = Integer.parseInt(config.get(CATEGORY_BERRY, "Berry Bush Group Size", 20,
				"The size of a grouping for berry bushes to spawn in a chunk.")
				.getString());

		yewRarity = Float.parseFloat(config.get(CATEGORY_YEW, "Yew Tree Rarity", 1.0E-3D,
				"The chance for yew trees to spawn in a chunk. (0=never, 1.0=always), this means many chunks may not have any trees")
				.getString());

		ironbarkRarity = Float.parseFloat(config.get(CATEGORY_IRONBARK, "Ironbark Tree Rarity", 1.5E-3D,
				"The chance for ironbark trees to spawn in a chunk. (0=never, 1.0=always), this means many chunks may not have any trees")
				.getString());

		ebonyRarity = Float.parseFloat(config.get(CATEGORY_EBONY, "Ebony Tree Rarity", 5.0E-4D,
				"The chance for ebony trees to spawn in a chunk. (0=never, 1.0=always), this means many chunks may not have any trees")
				.getString());

		berryMinTemp = Float.parseFloat(
				config.get(CATEGORY_BERRY, "Berry Bush Spawn Temp Min", 0.2D, "The minimal biome temperature berries can spawn")
						.getString());
		berryMaxTemp = Float.parseFloat(
				config.get(CATEGORY_BERRY, "Berry Bush Spawn Temp Max", 1.0D, "The maximum biome temperature berries can spawn")
						.getString());
		berryMinRain = Float.parseFloat(
				config.get(CATEGORY_BERRY, "Berry Bush Spawn Rain Min", 0.3D, "The minimal biome rainfall berries can spawn")
						.getString());
		berryMaxRain = Float.parseFloat(
				config.get(CATEGORY_BERRY, "Berry Bush Spawn Rain Max", 1.0D, "The maximum biome rainfall berries can spawn")
						.getString());

		yewMinTemp = Float.parseFloat(config
				.get(CATEGORY_YEW, "Yew Tree Spawn Temp Min", 0.2D, "The minimal biome temperature yew can spawn").getString());
		yewMaxTemp = Float.parseFloat(config
				.get(CATEGORY_YEW, "Yew Tree Spawn Temp Max", 1.0D, "The maximum biome temperature yew can spawn").getString());
		yewMinRain = Float.parseFloat(config
				.get(CATEGORY_YEW, "Yew Tree Spawn Rain Min", 0.3D, "The minimal biome rainfall yew can spawn").getString());
		yewMaxRain = Float.parseFloat(config
				.get(CATEGORY_YEW, "Yew Tree Spawn Rain Max", 1.0D, "The maximum biome rainfall yew can spawn").getString());

		ironbarkMinTemp = Float.parseFloat(config
				.get(CATEGORY_IRONBARK, "Ironbark Tree Spawn Temp Min", 0.4D, "The minimal biome temperature ironbark can spawn")
				.getString());
		ironbarkMaxTemp = Float.parseFloat(config
				.get(CATEGORY_IRONBARK, "Ironbark Tree Spawn Temp Max", 1.2D, "The maximum biome temperature ironbark can spawn")
				.getString());
		ironbarkMinRain = Float.parseFloat(config
				.get(CATEGORY_IRONBARK, "Ironbark Tree Spawn Rain Min", 0.0D, "The minimal biome rainfall ironbark can spawn")
				.getString());
		ironbarkMaxRain = Float.parseFloat(config
				.get(CATEGORY_IRONBARK, "Ironbark Tree Spawn Rain Max", 0.8D, "The maximum biome rainfall ironbark can spawn")
				.getString());

		ebonyMinTemp = Float.parseFloat(
				config.get(CATEGORY_EBONY, "Ebony Tree Spawn Temp Min", 0.2D, "The minimal biome temperature ebony can spawn")
						.getString());
		ebonyMaxTemp = Float.parseFloat(
				config.get(CATEGORY_EBONY, "Ebony Tree Spawn Temp Max", 1.0D, "The maximum biome temperature ebony can spawn")
						.getString());
		ebonyMinRain = Float.parseFloat(
				config.get(CATEGORY_EBONY, "Ebony Tree Spawn Rain Min", 0.4D, "The minimal biome rainfall ebony can spawn")
						.getString());
		ebonyMaxRain = Float.parseFloat(
				config.get(CATEGORY_EBONY, "Ebony Tree Spawn Rain Max", 1.0D, "The maximum biome rainfall ebony can spawn")
						.getString());

		structureTickRate = Integer.parseInt(config
				.get(CATEGORY_STRUCTURE_GENERAL, "Generation Tick rate", 1, "How many ticks (1/20seconds) for each section to generate")
				.getString());

		ancientForgeSpawnChance = Float
				.parseFloat(config
						.get(CATEGORY_ANCIENT_FORGE, "Spawn Chance", 2.5E-2D,
								"The chance for this structure to generate in a chunk. (0=never, 1.0=always)")
						.getString());
		ancientForgeGrid = Integer.parseInt(config.get(CATEGORY_ANCIENT_FORGE, "Ancient Forge Grid size", 2,
						"Ancient Forges generate on a grid on the map to avoid overlaps, this defines the minimum distance between two strongholds (in 16x16 block chunks)")
				.getString());

		ancientAltarSpawnChance = Float
				.parseFloat(config
						.get(CATEGORY_ANCIENT_ALTAR, "Spawn Chance", 2.0E-2D,
								"The chance for this structure to generate in a chunk. (0=never, 1.0=always)")
						.getString());
		ancientAltarGrid = Integer.parseInt(config.get(CATEGORY_ANCIENT_ALTAR, "Ancient Altar Grid size", 6,
						"Ancient Altars generate on a grid on the map to avoid overlaps, this defines the minimum distance between two strongholds (in 16x16 block chunks)")
				.getString());

		dwarvenStrongholdSpawnChance = Float
				.parseFloat(config
						.get(CATEGORY_DWARVEN_STRONGHOLD, "Spawn Chance", 8.0E-2D,
								"The chance for this structure to generate in a chunk. (0=never, 1.0=always)")
						.getString());

		dwarvenStrongholdGrid = Integer.parseInt(config.get(CATEGORY_DWARVEN_STRONGHOLD, "Stronghold Grid size", 12,
				"Strongholds generate on a grid on the map to avoid overlaps, this defines the minimum distance between two strongholds (in 16x16 block chunks)")
				.getString());
		dwarvenStrongholdLength = Integer.parseInt(config.get(CATEGORY_DWARVEN_STRONGHOLD, "Stronghold max length", 8,
				"Max amount of halls making the length of dwarf strongholds (some may be smaller or larger, but this is a guideline)")
				.getString());
		dwarvenStrongholdDeviations = Integer.parseInt(config.get(CATEGORY_DWARVEN_STRONGHOLD, "Stronghold max deviations", 1,
				"Max amount of intersections in dwarf stronghold hallways (not including living hubs). This can significantly increase complexity of strongholds and in turn lag when generating")
				.getString());
		dwarvenStrongholdShouldFurnaceSpawn = Boolean.parseBoolean(config.get(CATEGORY_DWARVEN_STRONGHOLD, "Stronghold Should Furnace Spawn", true,
						"Should the furnace block spawn inside the Stronghold")
				.getString());
		dwarvenStrongholdShouldCauldronSpawn = Boolean.parseBoolean(config.get(CATEGORY_DWARVEN_STRONGHOLD, "Stronghold Should Cauldron Spawn", false,
						"Should the cauldron block spawn inside the Stronghold")
				.getString());
	}

}
