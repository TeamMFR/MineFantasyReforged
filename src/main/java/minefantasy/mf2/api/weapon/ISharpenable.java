package minefantasy.mf2.api.weapon;

import net.minecraft.item.ItemStack;

public interface ISharpenable {
    /**
     * The max amount of uses of sharpness -1 means can't be sharpened
     */
    public float getMaxSharpness(ItemStack item);

    /**
     * The max percent that it can increase damage
     */
    public float getDamagePercentMax(ItemStack item);

    /**
     * The modifier for how much percent is added each use
     */
    public float getSharpUsesModifier(ItemStack item);
}
