package minefantasy.mfr.mechanics;

import minefantasy.mfr.constants.WeaponClass;
import minefantasy.mfr.network.HitSoundPacket;
import minefantasy.mfr.network.NetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public class HitSoundGenerator {
    public static void makeHitSound(ItemStack weapon, Entity target) {
        WeaponClass WC = WeaponClass.findClassForAny(weapon);
        String material = "metal";
        String stringCategory;

        material = getMaterial(material, weapon);
        if (WC != null) {
            stringCategory = WC.getSoundCategory();
            if (!target.world.isRemote) {
                NetworkHandler.sendToAllTracking(target, new HitSoundPacket(stringCategory, material, target));
            }
        }
    }

    public static String getMaterial(String material, ItemStack itemstack) {
        if (itemstack.isEmpty()) {
            return "metal";
        }
        Item item = itemstack.getItem();
        if (item instanceof ItemTool) {
            if (((ItemTool) item).getToolMaterialName().equalsIgnoreCase("WOOD")) {
                material = "wood";
            }
            if (((ItemTool) item).getToolMaterialName().equalsIgnoreCase("STONE")) {
                material = "stone";
            }
        }

        if (item instanceof ItemSword) {
            if (((ItemSword) item).getToolMaterialName().equalsIgnoreCase("WOOD")) {
                material = "wood";
            }
            if (((ItemSword) item).getToolMaterialName().equalsIgnoreCase("STONE")) {
                material = "stone";
            }
        }

        return material;
    }
}
