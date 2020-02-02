package minefantasy.mfr.entity.mob;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import minefantasy.mfr.entity.EntityDragonBreath;
import minefantasy.mfr.entity.EntitySmoke;
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
            ((EntityLivingBase) target).addPotionEffect(new PotionEffect(Potion.getPotionById(15), 100, 0));
        }
    }

    @Override
    public float modifyDamage(Entity hit, float dam) {
        return EntitySmoke.canPoison(hit, random) ? (dam / 5F + 1) : 0F;
    }

    @Override
    public void hitBlock(World world, IBlockState state, EntityDragonBreath instance, BlockPos pos, boolean impact) {
    }
}
