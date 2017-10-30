package minefantasy.mf2.block.basic;

import cpw.mods.fml.common.registry.GameRegistry;
import minefantasy.mf2.material.BaseMaterialMF;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.Random;

public class BlockMetalBarsMF extends BlockPane {
    private BaseMaterialMF baseMat;
    private Random rand = new Random();

    public BlockMetalBarsMF(BaseMaterialMF material) {
        super("minefantasy2:metal/" + material.name.toLowerCase() + "_bars",
                "minefantasy2:metal/" + material.name.toLowerCase() + "_bars", Material.iron, true);
        String name = material.name.toLowerCase() + "_bars";
        GameRegistry.registerBlock(this, name);
        setBlockName(name);

        this.setHarvestLevel("pickaxe", material.harvestLevel);
        this.setStepSound(Block.soundTypeMetal);
        this.setHardness(material.hardness + 1 / 2F);
        this.setResistance(material.hardness + 1);
        this.baseMat = material;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity hitter) {
        if (baseMat.name.equalsIgnoreCase("dragonforge") && !hitter.isImmuneToFire()
                && hitter instanceof EntityLivingBase) {
            hitter.setFire(10);
            hitter.attackEntityFrom(DamageSource.inFire, 1);
            world.spawnParticle("flame", x + rand.nextDouble(), y + rand.nextDouble(), z + rand.nextDouble(), 0D, 0D,
                    0D);
            world.spawnParticle("smoke", x + rand.nextDouble(), y + rand.nextDouble(), z + rand.nextDouble(), 0D, 0D,
                    0D);
        }
    }
}
