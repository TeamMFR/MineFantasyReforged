package minefantasy.mfr.mechanics;

import minefantasy.mfr.api.weapon.WeaponClass;
import minefantasy.mfr.init.SoundsMFR;
import minefantasy.mfr.packet.HitSoundPacket;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.SoundEvent;
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
                ((WorldServer) target.world).getEntityTracker().sendToTracking(target, new HitSoundPacket(getSound(type,material), target).generatePacket());
            }
        }
    }

    public static SoundEvent getSound (String type, String material){
        if (type.equals("blunt") && material.equals("wood")){
            return SoundsMFR.BLUNT_WOOD;
        }
        if (type.equals("blunt") && material.equals("metal")){
            return SoundsMFR.BLUNT_METAL;
        }
        if (type.equals("blunt") && material.equals("stone")){
            return SoundsMFR.BLUNT_STONE;
        }
        if (type.equals("blade") && material.equals("wood")){
            return SoundsMFR.BLADE_WOOD;
        }
        if (type.equals("blade") && material.equals("metal")){
            return SoundsMFR.BLADE_METAL;
        }
        if (type.equals("blade") && material.equals("stone")){
            return SoundsMFR.BLADE_STONE;
        }
        return null;
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
