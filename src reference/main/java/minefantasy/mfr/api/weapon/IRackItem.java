package minefantasy.mfr.api.weapon;

import minefantasy.mfr.block.tile.decor.TileEntityRack;
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
