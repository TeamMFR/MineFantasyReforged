package minefantasy.mf2.api.weapon;

import net.minecraft.util.DamageSource;

public class DamageSourceAP extends DamageSource {
    public static DamageSource blunt = new DamageSourceAP();

    public DamageSourceAP() {
        super("battlegearExtra");
        this.setDamageBypassesArmor();
        this.setDamageIsAbsolute();
    }

}
