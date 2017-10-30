package minefantasy.mf2.item.armour;

import minefantasy.mf2.material.BaseMaterialMF;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemApron extends ItemClothingMF {
    public ItemApron(String name, BaseMaterialMF material, String tex, int rarity) {
        super(name, material, 1, tex, rarity);
    }

    public static boolean isUserProtected(EntityPlayer user) {
        ItemStack worn = user.getCurrentArmor(2);
        if (worn == null) {
            return false;
        }
        return worn.getItem() instanceof ItemApron;
    }
}
