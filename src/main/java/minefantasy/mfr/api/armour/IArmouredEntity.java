package minefantasy.mfr.api.armour;

import net.minecraft.util.DamageSource;

public interface IArmouredEntity {
	float getThreshold(DamageSource src);
}
