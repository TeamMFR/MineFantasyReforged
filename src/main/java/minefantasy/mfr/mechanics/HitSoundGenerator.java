package minefantasy.mfr.mechanics;

import minefantasy.mfr.api.weapon.WeaponClass;
import minefantasy.mfr.network.HitSoundPacket;
import minefantasy.mfr.network.NetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.world.WorldServer;

public class HitSoundGenerator {
    public static void makeHitSound(ItemStack weapon, Entity target) {
        WeaponClass WC = WeaponClass.findClassForAny(weapon);
        String material = "metal";
        String type = "blunt";

        material = getMaterial(material, weapon);
        if (WC != null) {
            type = WC.getSound();
            if (!target.world.isRemote) {
                NetworkHandler.sendToAllTracking(target, new HitSoundPacket(type,material, target));
            }
        }
    }

    public static String getMaterial(String material, ItemStack itemstack) {
        if (itemstack == null) {
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
