package minefantasy.mf2.entity.mob;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import minefantasy.mf2.entity.EntityDragonBreath;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.world.World;

public abstract class DragonBreath 
{
	public static ArrayList<DragonBreath> projectiles = new ArrayList<DragonBreath>();
	
	public static int nextID = 0;
	public static DragonBreath fire, frost, poison;
	public static void init()
	{
		fire = new FireBreath("fire");
		frost = new FrostBreath("frost");
		poison = new PoisonBreath("poison");
	}
	
	public int id = 0;
	public final String name;
	public DragonBreath(String name) 
	{
		this.name = name;
		register();
	}
	
	public void register()
	{
		this.id = nextID;
		projectiles.add(this);
		++nextID;
	}

	public abstract DamageSource getDamageSource(EntityDragonBreath breath, EntityLivingBase shooter);

	public boolean shouldExpand(){
		return true;
	}

	public int getLifeSpan() {
		return 30;
	}

	@SideOnly(Side.CLIENT)
	public abstract String getTexture(EntityDragonBreath instance);
	
	public void onHitEntity(Entity entityHit, EntityDragonBreath instance) {
		entityHit.attackEntityFrom(getDamageSource(instance, instance.shootingEntity), modifyDamage(instance.getDamage()));
	}
	
	public float modifyDamage(float dam)
	{
		return dam;
	}

	public void hitBlock(World world, EntityDragonBreath instance, int x, int y, int z, boolean impact) 
	{
	}
}
