package minefantasy.mfr.block.basic;

import minefantasy.mfr.MineFantasyReborn;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.registry.GameRegistry;
import minefantasy.mfr.material.BaseMaterialMFR;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.Random;

public class BlockMetalBarsMF extends BlockPane {
    private BaseMaterialMFR baseMat;
    private Random rand = new Random();

    public BlockMetalBarsMF(BaseMaterialMFR material) {
        super(Material.IRON,  true);
        String name = material.name.toLowerCase() + "_bars";
        GameRegistry.findRegistry(Block.class).register(this);
        setRegistryName(name);
        setUnlocalizedName(MineFantasyReborn.MOD_ID + "." + name);
        this.setHarvestLevel("pickaxe", material.harvestLevel);
        this.setSoundType(SoundType.METAL);
        this.setHardness(material.hardness + 1 / 2F);
        this.setResistance(material.hardness + 1);
        this.baseMat = material;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity hitter) {
        if (baseMat.name.equalsIgnoreCase("dragonforge") && !hitter.isImmuneToFire()
                && hitter instanceof EntityLivingBase) {
            hitter.setFire(10);
            hitter.attackEntityFrom(DamageSource.IN_FIRE, 1);
            world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + rand.nextDouble(), pos.getY() + rand.nextDouble(), pos.getZ() + rand.nextDouble(), 0D, 0D,
                    0D);
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + rand.nextDouble(), pos.getY() + rand.nextDouble(), pos.getZ() + rand.nextDouble(), 0D, 0D,
                    0D);
        }
    }
}
