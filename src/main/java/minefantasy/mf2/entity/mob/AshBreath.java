package minefantasy.mf2.entity.mob;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.entity.EntityDragonBreath;
import minefantasy.mf2.entity.EntitySmoke;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.world.World;

public class AshBreath extends DragonBreath {

    public AshBreath(String name) {
        super(name);
    }

    @Override
    public DamageSource getDamageSource(EntityDragonBreath breath, EntityLivingBase shooter) {
        return shooter == null ? new DamageSource("ashblastBase").setDamageBypassesArmor()
                : (new EntityDamageSourceIndirect("ashblast", breath, shooter).setDamageBypassesArmor());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getTexture(EntityDragonBreath instance) {
        return "textures/projectile/dragonbreath_ash";
    }

    public void onHitEntity(Entity target, EntityDragonBreath instance) {
        super.onHitEntity(target, instance);
        if (target instanceof EntityLivingBase) {
            float dam = instance.getDamage();
            ((EntityLivingBase) target).addPotionEffect(new PotionEffect(Potion.blindness.id, 100, 0));
        }
    }

    @Override
    public float modifyDamage(Entity hit, float dam) {
        return EntitySmoke.canPoison(hit, random) ? (dam / 5F + 1) : 0F;
    }

    @Override
    public void hitBlock(World world, EntityDragonBreath instance, int x, int y, int z, boolean impact) {
    }
}
