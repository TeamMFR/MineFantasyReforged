package minefantasy.mfr.item.armour;

import minefantasy.mfr.material.BaseMaterialMFR;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class ItemApron extends ItemClothingMFR {
    public ItemApron(String name, BaseMaterialMFR material, String tex, int rarity) {
        super(name, material, EntityEquipmentSlot.CHEST, tex, rarity);
    }

    public static boolean isUserProtected(EntityPlayer user) {
        ItemStack worn = user.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        if (worn == null) {
            return false;
        }
        return worn.getItem() instanceof ItemApron;
    }
}
