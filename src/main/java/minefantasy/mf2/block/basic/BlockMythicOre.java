package minefantasy.mf2.block.basic;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.World;

import java.util.Random;

public class BlockMythicOre extends BlockOreMF {
    private boolean isPure;

    public BlockMythicOre(String name, boolean pure) {
        super(name, 4, pure ? 3 : 2);
        isPure = pure;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        if (rand.nextInt(20) == 0 && world.isRemote) {
            // "minefantasy2:block.mythicore"
            world.playSound(x + 0.5D, y + 0.5D, z + 0.5D, (isPure ? "minefantasy2:block.mythicore" : "random.levelup"),
                    1.0F, rand.nextFloat() * 0.4F + 1.1F, true);
        }
    }
}
