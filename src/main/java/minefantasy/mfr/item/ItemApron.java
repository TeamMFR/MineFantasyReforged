package minefantasy.mfr.item;

import minefantasy.mfr.material.BaseMaterialMFR;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class ItemApron extends ItemClothing {
    public ItemApron(String name, BaseMaterialMFR material, String tex, int rarity) {
        super(name, material, EntityEquipmentSlot.CHEST, tex, rarity);
    }

    public static boolean isUserProtected(EntityPlayer user) {
        ItemStack worn = user.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        if (worn.isEmpty()) {
            return false;
        }
        return worn.getItem() instanceof ItemApron;
    }
}
