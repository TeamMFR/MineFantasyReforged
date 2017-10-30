package minefantasy.mf2.api.refine;

import net.minecraft.world.World;

/**
 * This is only implemented once by MineFantasy just a hook to allow MF entities
 * to be used
 */
public interface ISmokeHandler {
    void spawnSmoke(World world, double x, double y, double z, int value);
}
