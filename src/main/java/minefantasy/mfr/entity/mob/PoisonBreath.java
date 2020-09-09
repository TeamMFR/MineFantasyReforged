package minefantasy.mfr.entity.mob;

import minefantasy.mfr.entity.EntityCogwork;
import minefantasy.mfr.entity.EntityDragonBreath;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
            ((EntityLivingBase) target).addPotionEffect(new PotionEffect(MobEffects.POISON, 40, (int) dam));
            ((EntityLivingBase) target).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 50, 10));

            if (instance.rand.nextInt(10) == 0) {
                Iterable<ItemStack> equipment = target.getEquipmentAndArmor();
                for (ItemStack item: equipment) {
                    if (item != null) {
                        item.damageItem((int) dam, ((EntityLivingBase) target));
                        if (item.getItemDamage() >= item.getMaxDamage()) {
                            ((EntityLivingBase) target).renderBrokenItemStack(item);
                            target.setItemStackToSlot(EntityEquipmentSlot.valueOf(item.getDisplayName()), null);
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
    public void hitBlock(World world, IBlockState state, EntityDragonBreath instance, BlockPos pos, boolean impact) {
    }
}
