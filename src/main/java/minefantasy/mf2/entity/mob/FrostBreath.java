package minefantasy.mf2.entity.mob;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.stamina.StaminaBar;
import minefantasy.mf2.config.ConfigMobs;
import minefantasy.mf2.entity.EntityDragonBreath;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

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
            ((EntityLivingBase) target).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 100, 2));
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
            if (ConfigMobs.dragonGriefGeneral && world.isAirBlock(x, y + 1, z)) {
                if (hit.isSideSolid(world, x, y, z, ForgeDirection.UP)) {
                    world.setBlock(x, y + 1, z, Blocks.snow_layer);
                }

                if (!world.getGameRules().getGameRuleBooleanValue("mobGriefing"))
                    return;
                if (hit == Blocks.water || hit == Blocks.flowing_water) {
                    world.setBlock(x, y, z, Blocks.ice);
                }
                if (hit == Blocks.water || hit == Blocks.lava) {
                    world.setBlock(x, y, z, Blocks.obsidian);
                }
                if (hit == Blocks.water || hit == Blocks.flowing_lava) {
                    world.setBlock(x, y, z, Blocks.cobblestone);
                }
            }
        }
    }
}
