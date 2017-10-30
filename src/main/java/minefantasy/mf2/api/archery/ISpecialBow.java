package minefantasy.mf2.api.archery;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public interface ISpecialBow {
    /**
     * This is called when an arrow is fired from a bow if you have your own arrows
     * firing, call this method when spawning it in world
     * <p>
     * Allows to modify an arrow when fired from this bow
     *
     * @param arrow the arrow fired(use a cast to determine what class it is)
     * @return the same arrow, but modified
     */
    public Entity modifyArrow(ItemStack bow, Entity arrow);

    public float getMaxCharge();

    public float getRange(ItemStack bow);

    public float getSpread(ItemStack bow);
}
