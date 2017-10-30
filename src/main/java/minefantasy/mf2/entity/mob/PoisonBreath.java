package minefantasy.mf2.entity.mob;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.entity.EntityCogwork;
import minefantasy.mf2.entity.EntityDragonBreath;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.world.World;

public class PoisonBreath extends DragonBreath {

    public PoisonBreath(String name) {
        super(name);
    }

    @Override
    public DamageSource getDamageSource(EntityDragonBreath breath, EntityLivingBase shooter) {
        return shooter == null ? new DamageSource("acidblastBase")
                : (new EntityDamageSourceIndirect("acidblast", breath, shooter));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getTexture(EntityDragonBreath instance) {
        return "textures/projectile/dragonbreath_poison";
    }

    public void onHitEntity(Entity target, EntityDragonBreath instance) {
        super.onHitEntity(target, instance);
        if (target instanceof EntityLivingBase) {
            float dam = instance.getDamage();
            ((EntityLivingBase) target).addPotionEffect(new PotionEffect(Potion.poison.id, 40, (int) dam));
            ((EntityLivingBase) target).addPotionEffect(new PotionEffect(Potion.confusion.id, 50, 10));

            if (instance.rand.nextInt(10) == 0) {
                for (int a = 0; a < 5; a++) {
                    ItemStack item = ((EntityLivingBase) target).getEquipmentInSlot(a);
                    if (item != null) {
                        item.damageItem((int) dam, ((EntityLivingBase) target));
                        if (item.getItemDamage() >= item.getMaxDamage()) {
                            ((EntityLivingBase) target).renderBrokenItemStack(item);
                            ((EntityLivingBase) target).setCurrentItemOrArmor(a, null);
                        }
                    }
                }
            }
        }
    }

    @Override
    public float modifyDamage(Entity hit, float dam) {
        if (hit instanceof EntityCogwork) {
            return dam * 2;
        }
        return dam;
    }

    @Override
    public void hitBlock(World world, EntityDragonBreath instance, int x, int y, int z, boolean impact) {
    }
}
