package minefantasy.mf2.api.weapon;

import minefantasy.mf2.block.tileentity.decor.TileEntityRack;
import net.minecraft.item.ItemStack;

public interface IRackItem {

    float getScale(ItemStack itemstack);

    float getOffsetX(ItemStack itemstack);

    float getOffsetY(ItemStack itemstack);

    float getOffsetZ(ItemStack itemstack);

    float getRotationOffset(ItemStack itemstack);

    boolean canHang(TileEntityRack rack, ItemStack item, int slot);

    boolean isSpecialRender(ItemStack item);

}
