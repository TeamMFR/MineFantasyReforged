package minefantasy.mf2.entity.mob;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.config.ConfigMobs;
import minefantasy.mf2.entity.EntityDragonBreath;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

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
    public void hitBlock(World world, EntityDragonBreath instance, int x, int y, int z, boolean impact) {
        Block hit = world.getBlock(x, y, z);
        if (!world.isRemote && (impact || instance.rand.nextInt(20) == 0)) {
            if (!world.isRemote && world.isAirBlock(x, y + 1, z)) {
                if (ConfigMobs.dragonGriefFire && hit.isSideSolid(world, x, y, z, ForgeDirection.UP)
                        && hit.isFlammable(world, x, y + 1, z, ForgeDirection.UP)) {
                    world.setBlock(x, y + 1, z, Blocks.fire);
                }
                if (!world.getGameRules().getGameRuleBooleanValue("mobGriefing"))
                    return;

                if (ConfigMobs.dragonGriefGeneral && hit == Blocks.ice) {
                    world.setBlock(x, y, z, Blocks.water);
                }
                if (ConfigMobs.dragonGriefGeneral && hit.getMaterial() == Material.glass) {
                    world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "dig.glass", 1.0F, 1.0F);
                    world.setBlockToAir(x, y, z);
                }
            }
        }
    }
}
