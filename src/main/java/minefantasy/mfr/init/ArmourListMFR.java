package minefantasy.mfr.init;

import minefantasy.mfr.api.armour.ArmourDesign;
import minefantasy.mfr.api.armour.ArmourMaterialMFR;
import minefantasy.mfr.item.armour.ItemApron;
import minefantasy.mfr.item.armour.ItemArmourMFR;
import minefantasy.mfr.material.BaseMaterialMFR;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * @author Anonymous Productions
 */
public class ArmourListMFR {
    public static final String[] leathermats = new String[]{"hide", "roughleather", "strongleather", "studleather",
            "padded",};
    public static ArmourMaterialMFR LEATHER = new ArmourMaterialMFR("Leather", 5, 0.30F, 18, 1.00F);
    public static ArmourMaterialMFR APRON = new ArmourMaterialMFR("Apron", 6, 0.30F, 0, 1.00F);
    public static ItemArmourMFR[] leather = new ItemArmourMFR[leathermats.length * 4];
    public static ItemArmourMFR leatherapron;

    public static void load() {
        leatherapron = new ItemApron("leatherapron", BaseMaterialMFR.leatherapron, "leatherapron_layer_1", 0);

        for (int a = 0; a < leathermats.length; a++) {
            BaseMaterialMFR baseMat = BaseMaterialMFR.getMaterial(leathermats[a]);
            String matName = baseMat.name;
            int rarity = baseMat.rarity;
            int id = a * 4;
            float bulk = baseMat.weight;
            ArmourDesign design = baseMat == BaseMaterialMFR.padding ? ArmourDesign.PADDING : ArmourDesign.LEATHER;

            leather[id + 0] = new ItemArmourMFR(matName.toLowerCase() + "_helmet", baseMat, design, EntityEquipmentSlot.HEAD, matName.toLowerCase() + "_layer_1", rarity, bulk);
            leather[id + 1] = new ItemArmourMFR(matName.toLowerCase() + "_chest", baseMat, design, EntityEquipmentSlot.CHEST, matName.toLowerCase() + "_layer_1", rarity, bulk);
            leather[id + 2] = new ItemArmourMFR(matName.toLowerCase() + "_legs", baseMat, design, EntityEquipmentSlot.LEGS, matName.toLowerCase() + "_layer_2", rarity, bulk);
            leather[id + 3] = new ItemArmourMFR(matName.toLowerCase() + "_boots", baseMat, design, EntityEquipmentSlot.FEET, matName.toLowerCase() + "_layer_1", rarity, bulk);
        }
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
        if (material == BaseMaterialMFR.enderforge || material == BaseMaterialMFR.ignotumite
                || material == BaseMaterialMFR.mithium) {
            return true;
        }
        return false;
    }
}
