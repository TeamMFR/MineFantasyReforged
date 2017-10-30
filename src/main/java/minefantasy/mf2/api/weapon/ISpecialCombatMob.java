package minefantasy.mf2.api.weapon;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;

public interface ISpecialCombatMob {
    boolean canParry(DamageSource source);

    void onParry(DamageSource source, Entity attacker, float dam);
}
