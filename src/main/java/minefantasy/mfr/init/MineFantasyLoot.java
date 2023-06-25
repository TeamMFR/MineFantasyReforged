package minefantasy.mfr.init;

import com.google.common.collect.ImmutableList;
import minefantasy.mfr.MineFantasyReforged;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

@Mod.EventBusSubscriber(modid = MineFantasyReforged.MOD_ID)
public class MineFantasyLoot {
	private static final List<String> VANILLA_CHEST_INJECTION_TABLES = ImmutableList.of("village_blacksmith", "simple_dungeon", "stronghold_library", "desert_pyramid", "jungle_temple", "abandoned_mineshaft", "stronghold_corridor", "stronghold_crossing");
	public static ResourceLocation DWARVEN_ARMOURY;
	public static ResourceLocation DWARVEN_FORGE;
	public static ResourceLocation DWARVEN_HOME;
	public static ResourceLocation DWARVEN_HOME_RICH;
	public static ResourceLocation DWARVEN_STUDY;
	public static ResourceLocation DWARVEN_AMMO_BOX;
	public static ResourceLocation LOOT_SACK_COMMON;
	public static ResourceLocation LOOT_SACK_VALUABLE;
	public static ResourceLocation LOOT_SACK_EXQUISITE;

	public static void load() {
		for (String s : VANILLA_CHEST_INJECTION_TABLES) {
			LootTableList.register(new ResourceLocation(MineFantasyReforged.MOD_ID, "inject/chests/" + s));
		}
		DWARVEN_ARMOURY = LootTableList.register(new ResourceLocation(MineFantasyReforged.MOD_ID, "dwarven_tables/dwarven_armoury"));
		DWARVEN_FORGE = LootTableList.register(new ResourceLocation(MineFantasyReforged.MOD_ID, "dwarven_tables/dwarven_forge"));
		DWARVEN_HOME = LootTableList.register(new ResourceLocation(MineFantasyReforged.MOD_ID, "dwarven_tables/dwarven_home"));
		DWARVEN_HOME_RICH = LootTableList.register(new ResourceLocation(MineFantasyReforged.MOD_ID, "dwarven_tables/dwarven_home_rich"));
		DWARVEN_STUDY = LootTableList.register(new ResourceLocation(MineFantasyReforged.MOD_ID, "dwarven_tables/dwarven_study"));
		DWARVEN_AMMO_BOX = LootTableList.register(new ResourceLocation(MineFantasyReforged.MOD_ID, "dwarven_tables/dwarven_ammo_box"));
		LOOT_SACK_COMMON = LootTableList.register(new ResourceLocation(MineFantasyReforged.MOD_ID, "loot_sack_tables/common"));
		LOOT_SACK_VALUABLE = LootTableList.register(new ResourceLocation(MineFantasyReforged.MOD_ID, "loot_sack_tables/valuable"));
		LOOT_SACK_EXQUISITE = LootTableList.register(new ResourceLocation(MineFantasyReforged.MOD_ID, "loot_sack_tables/exquisite"));
	}

	//code from AncientWarfare 2, AWCoreLoot
	private static final String CHESTS_PREFIX = "minecraft:chests/";

	@SubscribeEvent
	public static void lootLoad(LootTableLoadEvent evt) {
		String name = evt.getName().toString();

		if (name.startsWith(CHESTS_PREFIX) && VANILLA_CHEST_INJECTION_TABLES.contains(name.substring(CHESTS_PREFIX.length()))) {
			String file = name.substring("minecraft:".length());
			evt.getTable().addPool(getInjectPool(file));
		}
	}

	private static LootPool getInjectPool(String entryName) {
		return new LootPool(new LootEntry[] {getInjectEntry(entryName)}, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0, 1), MineFantasyReforged.MOD_ID + "_inject_pool");
	}

	private static LootEntryTable getInjectEntry(String name) {
		return new LootEntryTable(new ResourceLocation(MineFantasyReforged.MOD_ID, "inject/" + name), 1, 0, new LootCondition[0], MineFantasyReforged.MOD_ID + "_inject_entry");
	}

}
