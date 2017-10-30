package minefantasy.mf2.api.archery;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IArrowHandler {
    /**
     * Fires an arrow
     *
     * @return true if the arrow has been fired
     */
    public boolean onFireArrow(World world, ItemStack arrow, ItemStack bow, EntityPlayer user, float charge,
                               boolean infinite);
}
