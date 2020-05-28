package minefantasy.mfr.init;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.armour.ArmourDesign;
import minefantasy.mfr.api.armour.ArmourMaterialMFR;
import minefantasy.mfr.item.armour.ItemApron;
import minefantasy.mfr.item.armour.ItemArmourMFR;
import minefantasy.mfr.material.BaseMaterialMFR;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * @author Anonymous Productions
 */
@Mod.EventBusSubscriber(modid = MineFantasyReborn.MOD_ID)
public class ArmourListMFR {
    public static final String[] leathermats = new String[]{"hide", "rough_leather", "strong_leather", "stud_leather", "padded",};
    public static ArmourMaterialMFR LEATHER_MAT;
    public static ArmourMaterialMFR APRON;
    public static ItemArmourMFR[] LEATHER;
    public static ItemArmourMFR LEATHER_APRON;


    public static void init(){
        LEATHER_MAT = new ArmourMaterialMFR("leather", 5, 0.30F, 18, 1.00F);
        APRON = new ArmourMaterialMFR("apron", 6, 0.30F, 0, 1.00F);
        LEATHER = new ItemArmourMFR[leathermats.length * 4];
        LEATHER_APRON = new ItemApron("leather_apron", BaseMaterialMFR.LEATHER_APRON, "leatherapron_layer_1", 0);

        for (int a = 0; a < leathermats.length; a++) {
            BaseMaterialMFR baseMat = BaseMaterialMFR.getMaterial(leathermats[a]);
            String matName = baseMat.name;
            int rarity = baseMat.rarity;
            int id = a * 4;
            float bulk = baseMat.weight;
            ArmourDesign design = baseMat == BaseMaterialMFR.PADDING ? ArmourDesign.PADDING : ArmourDesign.LEATHER;

            LEATHER[id + 0] = new ItemArmourMFR(matName.toLowerCase() + "_helmet", baseMat, design, EntityEquipmentSlot.HEAD, matName.toLowerCase() + "_layer_1", rarity, bulk);
            LEATHER[id + 1] = new ItemArmourMFR(matName.toLowerCase() + "_chest", baseMat, design, EntityEquipmentSlot.CHEST, matName.toLowerCase() + "_layer_1", rarity, bulk);
            LEATHER[id + 2] = new ItemArmourMFR(matName.toLowerCase() + "_legs", baseMat, design, EntityEquipmentSlot.LEGS, matName.toLowerCase() + "_layer_2", rarity, bulk);
            LEATHER[id + 3] = new ItemArmourMFR(matName.toLowerCase() + "_boots", baseMat, design, EntityEquipmentSlot.FEET, matName.toLowerCase() + "_layer_1", rarity, bulk);
        }
    }

    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        registry.registerAll(LEATHER);
        registry.register(LEATHER_APRON);
    }

    public static ItemStack armour(ItemArmourMFR[] pool, int tier, int piece) {
        int slot = tier * 4 + piece;
        if (slot >= pool.length) {
            slot = pool.length - 1;
        }
        return new ItemStack(pool[slot]);
    }

    public static Item armourItem(ItemArmourMFR[] pool, int tier, int piece) {
        int slot = tier * 4 + piece;
        if (slot >= pool.length) {
            slot = pool.length - 1;
        }
        return pool[slot];
    }

    public static boolean isUnbreakable(BaseMaterialMFR material, EntityLivingBase user) {
        if (material == BaseMaterialMFR.ENDERFORGE || material == BaseMaterialMFR.IGNOTUMITE
                || material == BaseMaterialMFR.MITHIUM) {
            return true;
        }
        return false;
    }
}
