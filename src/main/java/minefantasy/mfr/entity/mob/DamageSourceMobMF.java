package minefantasy.mfr.entity.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class DamageSourceMobMF extends EntityDamageSource {
    protected Entity damageSourceEntity;
    private String attackName;

    public DamageSourceMobMF(String name, Entity attacker) {
        super("mob", attacker);
        this.attackName = name;
        this.damageSourceEntity = attacker;
    }

    @Override
    public Entity getTrueSource() {
        return this.damageSourceEntity;
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase target) {
        String string = "death.attack." + this.damageType + "." + attackName;
        return new TextComponentTranslation(string, target.getDisplayName(), this.damageSourceEntity.getDisplayName()) {};
    }
}