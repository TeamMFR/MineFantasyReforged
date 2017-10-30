package minefantasy.mf2.entity.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.IChatComponent;

public class DamageSourceMobMF extends EntityDamageSource {
    protected Entity damageSourceEntity;
    private String attackName;

    public DamageSourceMobMF(String name, Entity attacker) {
        super("mob", attacker);
        this.attackName = name;
        this.damageSourceEntity = attacker;
    }

    @Override
    public Entity getEntity() {
        return this.damageSourceEntity;
    }

    @Override
    public IChatComponent func_151519_b(EntityLivingBase target) {
        String s = "death.attack." + this.damageType + "." + attackName;
        return new ChatComponentTranslation(s,
                new Object[]{target.func_145748_c_(), this.damageSourceEntity.func_145748_c_()});
    }
}