package minefantasy.mfr.entity.mob;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import minefantasy.mfr.api.stamina.StaminaBar;
import minefantasy.mfr.config.ConfigMobs;
import minefantasy.mfr.entity.EntityDragonBreath;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.world.World;

public class FrostBreath extends DragonBreath {

    public FrostBreath(String name) {
        super(name);
    }

    @Override
    public DamageSource getDamageSource(EntityDragonBreath breath, EntityLivingBase shooter) {
        return shooter == null ? new DamageSource("frostblastBase")
                : (new EntityDamageSourceIndirect("frostblast", breath, shooter));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getTexture(EntityDragonBreath instance) {
        return "textures/projectile/dragonbreath_frost";
    }

    public void onHitEntity(Entity target, EntityDragonBreath instance) {
        super.onHitEntity(target, instance);
        if (target instanceof EntityLivingBase) {
            if (StaminaBar.isSystemActive && StaminaBar.doesAffectEntity((EntityLivingBase) target)) {
                StaminaBar.modifyStaminaValue((EntityLivingBase) target, -1F);
            }
            ((EntityLivingBase) target).addPotionEffect(new PotionEffect(Potion.getPotionById(2), 100, 2));
        }
    }

    @Override
    public float modifyDamage(Entity hit, float dam) {
        return dam;
    }

    @Override
    public void hitBlock(World world, IBlockState state, EntityDragonBreath instance, BlockPos pos, boolean impact) {
        IBlockState hit = world.getBlockState(pos);
        if (!world.isRemote && (impact || instance.rand.nextInt(20) == 0)) {
            if (ConfigMobs.dragonGriefGeneral && world.isAirBlock(pos.add(0,1,0))) {
                if (hit.isSideSolid(world, pos, EnumFacing.UP)) {
                    world.setBlockState(pos.add(0,1,0), (IBlockState) Blocks.SNOW_LAYER);
                }

                if (!world.getGameRules().getBoolean("mobGriefing"))
                    return;
                if (hit == Blocks.WATER || hit == Blocks.FLOWING_WATER) {
                    world.setBlockState(pos, (IBlockState) Blocks.ICE);
                }
                if (hit == Blocks.WATER || hit == Blocks.LAVA) {
                    world.setBlockState(pos, (IBlockState) Blocks.OBSIDIAN);
                }
                if (hit == Blocks.WATER || hit == Blocks.FLOWING_LAVA) {
                    world.setBlockState(pos, (IBlockState) Blocks.COBBLESTONE);
                }
            }
        }
    }
}
