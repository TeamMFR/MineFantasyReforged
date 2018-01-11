package minefantasy.mf2.entity.mob;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.entity.EntityDragonBreath;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

public abstract class DragonBreath {
    public static ArrayList<DragonBreath> projectiles = new ArrayList<DragonBreath>();

    public static int nextID = 0;
    public static DragonBreath fire, frost, poison, ash;
    public final String name;
    public int id = 0;
    protected Random random = new Random();

    public DragonBreath(String name) {
        this.name = name;
        register();
    }

    public static void init() {
        fire = new FireBreath("fire");
        frost = new FrostBreath("frost");
        poison = new PoisonBreath("poison");
        ash = new AshBreath("ash");
    }

    public void register() {
        this.id = nextID;
        projectiles.add(this);
        ++nextID;
    }

    public abstract DamageSource getDamageSource(EntityDragonBreath breath, EntityLivingBase shooter);

    public boolean shouldExpand() {
        return true;
    }

    public int getLifeSpan() {
        return 30;
    }

    @SideOnly(Side.CLIENT)
    public abstract String getTexture(EntityDragonBreath instance);

    public void onHitEntity(Entity entityHit, EntityDragonBreath instance) {
        entityHit.attackEntityFrom(getDamageSource(instance, instance.shootingEntity),
                modifyDamage(entityHit, instance.getDamage()));
    }

    public float modifyDamage(Entity hit, float dam) {
        return dam;
    }

    public void hitBlock(World world, EntityDragonBreath instance, int x, int y, int z, boolean impact) {
    }
}
