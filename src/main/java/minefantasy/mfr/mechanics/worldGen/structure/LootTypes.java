package minefantasy.mfr.mechanics.worldGen.structure;

import minefantasy.mfr.init.ToolListMFR;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LootTypes {

    public static final ResourceLocation DWARVEN_ARMOURY = LootTableList.register(new ResourceLocation("minefantasyreborn", "DwarvenArmory"));
    public static final ResourceLocation DWARVEN_AMMO = LootTableList.register(new ResourceLocation("minefantasyreborn", "DwarvenAmmo"));
    public static final ResourceLocation DWARVEN_FORGE = LootTableList.register(new ResourceLocation("minefantasyreborn", "DwarvenForge"));
    public static final ResourceLocation DWARVEN_HOME = LootTableList.register(new ResourceLocation("minefantasyreborn", "DwarvenHouse"));
    public static final ResourceLocation DWARVEN_HOME_RICH = LootTableList.register(new ResourceLocation("minefantasyreborn", "DwarvenHouseRich"));
    public static final ResourceLocation DWARVEN_STUDY = LootTableList.register(new ResourceLocation("minefantasyreborn", "DwarvenStudy"));
}
