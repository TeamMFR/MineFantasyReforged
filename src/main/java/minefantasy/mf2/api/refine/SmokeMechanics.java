package minefantasy.mf2.api.refine;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class SmokeMechanics {
    public static ISmokeHandler handler;
    /**
     * Do not use this from blocks, use emitSmoke instead Spawns smoke
     */
    private static Random rand = new Random();

    /**
     * Emits smoke from a carrying block
     *
     * @param max the maximum smoke emitted each tick (-1 for infinite)
     * @return true if it's successful
     */
    public static boolean emitSmokeFromCarrier(World world, int x, int y, int z, ISmokeCarrier tile, int max) {
        int value = tile.getSmokeValue();

        if (max >= 0 && value > max) {
            value = max;
        }
        if (value <= 0) {
            return true;// yes it succeeded because it doesn't need to... it just really means nothing
            // went wrong
        }
        boolean success = emitSmoke(world, x, y, z, value) != -1;

        if (success) {
            modifySmoke(tile, -value);
        }
        return success;
    }

    /**
     * Tries to emit smoke, sends it to chimneys if possible Returns -1 for fail, 0
     * for shared, 1 for success
     */
    public static int emitSmoke(World world, int x, int y, int z, int value) {
        TileEntity tile = world.getTileEntity(x, y + 1, z);// The above block
        if (tile == null) // No TileEntity above
        {
            if (world.isSideSolid(x, y + 1, z, ForgeDirection.DOWN))// if solid above
            {
                return -1;
            }
            // if(world.canBlockSeeTheSky(x, y+1, z))
            {
                spawnSmoke(world, x, y + 1, z, value);
                return 1;
            }
        } else {
            if (tile instanceof ISmokeCarrier) {
                if (((ISmokeCarrier) tile).getSmokeValue() < ((ISmokeCarrier) tile).getMaxSmokeStorage()) {
                    modifySmoke((ISmokeCarrier) tile, value);
                    return 0;
                }
            } else {
                if (world.isSideSolid(x, y + 1, z, ForgeDirection.DOWN))// if solid above
                {
                    return -1;
                }
            }
        }
        return -1;
    }

    public static void modifySmoke(ISmokeCarrier tile, int value) {
        tile.setSmokeValue(tile.getSmokeValue() + value);
        if (tile.getSmokeValue() <= 0) {
            tile.setSmokeValue(0);
        }
    }

    /**
     * Emits smoke to a coord, ignoring chimneys
     */
    public static void spawnSmoke(World world, int x, int y, int z, int value) {
        spawnSmokeD(world, x + 0.5D, y + 0.5D, z + 0.5D, value);
    }

    /**
     * Emits smoke, ignoring chimneys
     */
    public static void spawnSmokeD(World world, double x, double y, double z, int value) {
        if (!world.isRemote && handler != null) {
            handler.spawnSmoke(world, x, y, z, value);
        }

        for (int a = 0; a < value; a++) {
            float sprayRange = 0.2F;
            float sprayX = (rand.nextFloat() * sprayRange) - (sprayRange / 2);
            float sprayZ = (rand.nextFloat() * sprayRange) - (sprayRange / 2);
            float height = 0.2F;
            world.spawnParticle("smoke", x, y, z, sprayX, height, sprayZ);
        }
    }

    /**
     * Checks if smoke can go through a chimney
     */
    public static boolean tryUseChimney(World world, int x, int y, int z, int value) {
        return tryUseChimney(world, x, y, z, value, true);
    }

    /**
     * Checks if smoke can go through a chimney
     */
    public static boolean tryUseChimney(World world, int x, int y, int z, int value, boolean indirect) {
        TileEntity tile = world.getTileEntity(x, y, z);// The block
        if (tile != null) {
            if (tile instanceof ISmokeCarrier) {
                ISmokeCarrier chimney = (ISmokeCarrier) tile;
                if ((!indirect || chimney.canAbsorbIndirect())
                        && chimney.getSmokeValue() < chimney.getMaxSmokeStorage()) {
                    modifySmoke(chimney, value);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Emits smoke from a source, or goes through a chimney
     */
    public static void emitSmokeIndirect(World world, int xCoord, int yCoord, int zCoord, int value) {
        if (tryUseChimney(world, xCoord, yCoord, zCoord, value)) {
            return;
        }

        for (int x = -1; x <= 1; x++) {
            for (int y = 0; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (tryUseChimney(world, xCoord + x, yCoord + y, zCoord + z, value)) {
                        return;
                    }
                }
            }
        }
        spawnSmoke(world, xCoord, yCoord, zCoord, value);
    }
}
