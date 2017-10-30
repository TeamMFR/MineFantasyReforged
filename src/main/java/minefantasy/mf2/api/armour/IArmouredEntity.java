package minefantasy.mf2.api.armour;

import net.minecraft.util.DamageSource;

public interface IArmouredEntity {
    public float getThreshold(DamageSource src);
}
