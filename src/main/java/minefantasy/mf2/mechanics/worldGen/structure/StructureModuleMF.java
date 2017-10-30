package minefantasy.mf2.mechanics.worldGen.structure;

import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.block.tileentity.TileEntityWorldGenMarker;
import minefantasy.mf2.util.XSTRandom;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.DungeonHooks;

public abstract class StructureModuleMF {
    public final int xCoord;
    public final int yCoord;
    public final int zCoord;
    /**
     * (0-3) South, West, North, East
     */
    public final int direction;
    public final World worldObj;
    /**
     * How much longer can units be added
     */
    public int lengthId;
    /**
     * How many more branches can be added
     */
    public int deviationCount;
    protected XSTRandom rand = new XSTRandom();
    protected String subtype = "Normal";

    public StructureModuleMF(World world, StructureCoordinates position) {
        worldObj = world;
        xCoord = position.posX;
        yCoord = position.posY;
        zCoord = position.posZ;
        direction = position.direction;

    }

    public StructureModuleMF(World world, int x, int y, int z, int d) {
        this(world, new StructureCoordinates(x, y, z, d));
    }

    public StructureModuleMF(World world, int x, int y, int z) {
        this(world, x, y, z, 0);
    }

    public static void placeStructure(String classname, String subtype, int lengthID, int deviation, World world, int x,
                                      int y, int z, int dir) {
        try {
            Class oclass = Class.forName(classname);

            if (oclass != null) {
                StructureModuleMF module = (StructureModuleMF) oclass
                        .getConstructor(new Class[]{World.class, StructureCoordinates.class})
                        .newInstance(world, new StructureCoordinates(x, y, z, dir));

                if (module != null) {
                    module.subtype = subtype;
                    module.lengthId = lengthID;
                    module.deviationCount = deviation;
                    if (module.canGenerate()) {
                        module.generate();
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public abstract void generate();

    public boolean canGenerate() {
        return true;
    }

    public int rotateLeft() {
        switch (direction) {
            case 0:
                return 3;
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 2;
        }
        return 0;
    }

    public int rotateRight() {
        switch (direction) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 0;
        }
        return 0;
    }

    public int reverse() {
        switch (direction) {
            case 0:
                return 2;
            case 1:
                return 3;
            case 2:
                return 0;
            case 3:
                return 1;
        }
        return 0;
    }

    /**
     * Places a block and rotates it on it's base direction for pregen designed not
     * to override the worldGen marker
     *
     * @param id   The blockID
     * @param meta the block metadata
     * @param x    The x offset(relation to xCoord)
     * @param y    The y offset(relation to yCoord)
     * @param z    The z offset(relation to zCoord)
     */
    public void pregenPlaceBlock(Block id, int meta, int x, int y, int z) {
        if (!(x == 0 && y == 0 && z == 0)) {
            placeBlock(id, meta, x, y, z, direction);
        }
    }

    /**
     * Places a block and rotates it on it's base direction
     *
     * @param id   The blockID
     * @param meta the block metadata
     * @param x    The x offset(relation to xCoord)
     * @param y    The y offset(relation to yCoord)
     * @param z    The z offset(relation to zCoord)
     */
    public void placeBlock(Block id, int meta, int x, int y, int z) {
        placeBlock(id, meta, x, y, z, direction);
    }

    /**
     * Places a block and rotates it on direction
     *
     * @param id   The blockID
     * @param meta the block metadata
     * @param xo   The x offset(relation to xCoord)
     * @param yo   The y offset(relation to yCoord)
     * @param zo   The z offset(relation to zCoord)
     * @param dir  The direction to face
     */
    public void placeBlock(Block id, int meta, int xo, int yo, int zo, int dir) {
        int[] offset = offsetPos(xo, yo, zo, dir);
        worldObj.setBlock(offset[0], offset[1], offset[2], id, meta, 2);
    }

    public void placeBlockIfAir(Block id, int meta, int xo, int yo, int zo) {
        int[] offset = offsetPos(xo, yo, zo, direction);
        if (worldObj.getBlock(offset[0], offset[1], offset[2]).isReplaceable(worldObj, offset[0], offset[1],
                offset[2])) {
            worldObj.setBlock(offset[0], offset[1], offset[2], id, meta, 2);
        }
    }

    public void placeEntity(Entity entity, int x, int y, int z) {
        placeEntity(entity, x, y, z, direction);
    }

    public void placeEntity(Entity entity, int xo, int yo, int zo, int dir) {
        int[] offset = offsetPos(xo, yo, zo, dir);
        entity.setLocationAndAngles(offset[0] + 0.5D, offset[1] + entity.height - 1.0F, offset[2] + 0.5D,
                MathHelper.wrapAngleTo180_float(worldObj.rand.nextFloat() * 360.0F), 0.0F);
        worldObj.spawnEntityInWorld(entity);
    }

    protected void buildFoundation(Block block, int meta, int x, int z) {
        buildFoundation(block, meta, x, -1, z, 32, 0, false);
    }

    /**
     * Builds a foundation column from -1y
     *
     * @param block      The block used
     * @param meta       metadata used
     * @param x          the relative x position
     * @param y          the relative y position to start
     * @param z          the relative z position
     * @param max        the max depth
     * @param penetrance how many solid blocks can it pass through
     * @param relative   whether or not it must be stopped by concecutive blocks.
     */
    protected void buildFoundation(Block block, int meta, int x, int y, int z, int max, int penetrance,
                                   boolean relative) {
        penetrance -= y;
        int init_penetrance = penetrance;
        while (-y < max) {
            Block current = getBlock(x, y, z, direction);
            boolean empty = current.getMaterial().isReplaceable();

            if (isUnbreakable(x, y, z, direction)) {
                return;
            }
            if (!empty) {
                --penetrance;
            } else if (!relative) {
                penetrance = init_penetrance;
            }

            if (empty || penetrance >= 0) {
                placeBlock(block, meta == -1 ? StructureGenAncientForge.getRandomMetadata(rand) : meta, x, y, z);
                --y;
            } else {
                return;
            }
        }
    }

    public TileEntity getTileEntity(int xo, int yo, int zo, int dir) {
        int[] offset = offsetPos(xo, yo, zo, dir);
        return worldObj.getTileEntity(offset[0], offset[1], offset[2]);
    }

    public Block getBlock(int x, int y, int z) {
        return getBlock(x, y, z, direction);
    }

    public Block getBlock(int xo, int yo, int zo, int dir) {
        int[] offset = offsetPos(xo, yo, zo, dir);
        return worldObj.getBlock(offset[0], offset[1], offset[2]);
    }

    public boolean isUnbreakable(int xo, int yo, int zo, int dir) {
        return getHardness(xo, yo, zo, dir) < 0;
    }

    public float getHardness(int xo, int yo, int zo, int dir) {
        int[] offset = offsetPos(xo, yo, zo, dir);
        return worldObj.getBlock(offset[0], offset[1], offset[2]).getBlockHardness(worldObj, offset[0], offset[1],
                offset[2]);
    }

    public void notifyBlock(Block id, int xo, int yo, int zo, int dir) {
        int[] offset = offsetPos(xo, yo, zo, dir);
        worldObj.notifyBlockChange(offset[0], offset[1], offset[2], id);
    }

    public int[] offsetPos(int xo, int yo, int zo, int dir) {
        int u = 2;
        int x = xCoord;
        int y = yCoord + yo;
        int z = zCoord;

        switch (dir) {
            case 0:// NORTH
                x += xo;
                z += zo;
                break;

            case 1:// EAST
                x -= zo;
                z += xo;
                break;

            case 2:// SOUTH
                x -= xo;
                z -= zo;
                break;

            case 3:// WEST
                x += zo;
                z -= xo;
                break;
        }

        return new int[]{x, y, z};
    }

    protected void mapStructure(int x, int y, int z, Class<? extends StructureModuleMF> piece) {
        mapStructure(x, y, z, direction, piece);
    }

    protected void mapStructure(int x, int y, int z, int dir, Class<? extends StructureModuleMF> piece) {
        mapStructure(x, y, z, dir, piece, 0);
    }

    protected void mapStructure(int x, int y, int z, int dir, Class<? extends StructureModuleMF> piece, int delay) {
        int[] offset = offsetPos(x, y, z, this.direction);

        Block block = worldObj.getBlock(offset[0], offset[1], offset[2]);
        int meta = worldObj.getBlockMetadata(offset[0], offset[1], offset[2]);

        worldObj.setBlock(offset[0], offset[1], offset[2], BlockListMF.WG_Mark, dir, 2);
        TileEntity tile = worldObj.getTileEntity(offset[0], offset[1], offset[2]);
        if (tile != null && tile instanceof TileEntityWorldGenMarker) {
            ((TileEntityWorldGenMarker) tile).className = piece.getName();
            ((TileEntityWorldGenMarker) tile).length = lengthId - 1;
            ((TileEntityWorldGenMarker) tile).deviation = deviationCount;
            ((TileEntityWorldGenMarker) tile).prevID = Block.getIdFromBlock(block);
            ((TileEntityWorldGenMarker) tile).prevMeta = meta;
            ((TileEntityWorldGenMarker) tile).ticks -= delay;
            ((TileEntityWorldGenMarker) tile).type = this.subtype;
        }
        block = null;
    }

    protected int getStairDirection(int d) {
        switch (d) {
            case 0:
                return 2;

            case 1:
                return 1;

            case 2:
                return 3;

            case 3:
                return 0;

        }
        return 0;
    }

    public void placeSpawner(int x, int y, int z) {
        placeSpawner(x, y, z, DungeonHooks.getRandomDungeonMob(rand));
    }

    public void placeSpawner(int x, int y, int z, String mob) {
        this.placeBlock(Blocks.mob_spawner, 0, x, y, z);
        TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner) getTileEntity(x, y, z, direction);

        if (tileentitymobspawner != null) {
            tileentitymobspawner.func_145881_a().setEntityName(mob);
        } else {
            this.placeBlock(Blocks.air, 0, x, y, z);
        }
    }

    public static class StructureCoordinates {
        public final int posX, posY, posZ, direction;

        public StructureCoordinates(int x, int y, int z, int direction) {
            this.posX = x;
            this.posY = y;
            this.posZ = z;
            this.direction = direction;
        }
    }
}
