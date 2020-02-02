package minefantasy.mfr.entity.mob;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import minefantasy.mfr.config.ConfigMobs;
import minefantasy.mfr.entity.EntityDragonBreath;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.world.World;

public class FireBreath extends DragonBreath {

    public FireBreath(String name) {
        super(name);
    }

    @Override
    public DamageSource getDamageSource(EntityDragonBreath breath, EntityLivingBase shooter) {
        return shooter == null ? new DamageSource("fireblastBase").setFireDamage()
                : (new EntityDamageSourceIndirect("fireblast", breath, shooter)).setFireDamage();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getTexture(EntityDragonBreath instance) {
        return "textures/projectile/dragonbreath_fire";
    }

    public void onHitEntity(Entity target, EntityDragonBreath instance) {
        super.onHitEntity(target, instance);
        if (target instanceof EntityLivingBase) {
            target.setFire(5);
        }
    }

    @Override
    public float modifyDamage(Entity hit, float dam) {
        return dam;
    }

    @Override
    public void hitBlock(World world, IBlockState state, EntityDragonBreath instance, BlockPos pos, boolean impact) {
        Block hit = world.getBlockState(pos).getBlock();
        if (!world.isRemote && (impact || instance.rand.nextInt(20) == 0)) {
            if (!world.isRemote && world.isAirBlock(pos.add(0,1,0))) {
                if (ConfigMobs.dragonGriefFire && hit.isSideSolid(state, world, pos, EnumFacing.UP)
                        && hit.isFlammable(world, pos.add(0,1,0), EnumFacing.UP)) {
                    world.setBlockState(pos.add(0,1,0), (IBlockState) Blocks.FIRE);
                }
                if (!world.getGameRules().getBoolean("mobGriefing"))
                    return;

                if (ConfigMobs.dragonGriefGeneral && hit == Blocks.ICE) {
                    world.setBlockState(pos, (IBlockState) Blocks.WATER);
                }
                if (ConfigMobs.dragonGriefGeneral && hit.getMaterial(state) == Material.GLASS) {
                    world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_GLASS_HIT, SoundCategory.AMBIENT, 1.0F, 1.0F, true);
                    world.setBlockToAir(pos);
                }
            }
        }
    }
}
