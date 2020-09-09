package minefantasy.mfr.mechanics.worldGen.structure;

import minefantasy.mfr.tile.TileEntityWorldGenMarker;
import minefantasy.mfr.init.BlockListMFR;
import minefantasy.mfr.util.XSTRandom;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.DungeonHooks;

public abstract class StructureModuleMFR {
    public final BlockPos pos;
    /**
     * (0-3) South, West, North, East
     */
    public final int direction;
    public final World world;
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

    public StructureModuleMFR(World world, StructureCoordinates position) {
        this.world = world;
        pos = position.pos;
        direction = position.direction;

    }

    public StructureModuleMFR(World world, BlockPos pos, int d) {
        this(world, new StructureCoordinates(pos, d));
    }

    public StructureModuleMFR(World world, BlockPos pos) {
        this(world, pos, 0);
    }

    public static void placeStructure(String classname, String subtype, int lengthID, int deviation, World world, BlockPos pos, int dir) {
        try {
            Class oclass = Class.forName(classname);

            if (oclass != null) {
                StructureModuleMFR module = (StructureModuleMFR) oclass
                        .getConstructor(new Class[]{World.class, StructureCoordinates.class})
                        .newInstance(world, new StructureCoordinates(pos, dir));

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
     * @param block the block metadata
     * @param pos the block position
     */
    public void pregenPlaceBlock( Block block, BlockPos pos) {
        if (!(pos.getX() == 0 && pos.getY() == 0 && pos.getZ() == 0)) {
            placeBlock( block, pos, direction);
        }
    }

    /**
     * Places a block and rotates it on it's base direction
     *
     * @param block the block state
     * @param pos   the block position
     */
    public void placeBlock(Block block, BlockPos pos) {
        placeBlock(block, pos, direction);
    }

    /**
     * Places a block and rotates it on direction
     *
     * @param block the block metadata
     * @param dir  The direction to face
     */
    public void placeBlock(Block block, BlockPos pos, int dir) {
        BlockPos offset = offsetPos(pos, dir);
        world.setBlockState(pos, block.getDefaultState(), 2);
    }

    public void placeBlockIfAir(IBlockState state, BlockPos pos) {
        BlockPos offset = offsetPos(pos, direction);
        if (world.getBlockState(pos).getBlock().isReplaceable(world, pos)) {
            world.setBlockState(pos, state, 2);
        }
    }

    public void placeEntity(Entity entity, BlockPos pos) {
        placeEntity(entity, pos, direction);
    }

    public void placeEntity(Entity entity, BlockPos pos, int dir) {
        BlockPos offset = offsetPos(pos, dir);
        entity.setLocationAndAngles(pos.getX() + 0.5D, pos.getY() + entity.height - 1.0F, pos.getZ() + 0.5D,
                MathHelper.wrapDegrees(world.rand.nextFloat() * 360.0F), 0.0F);
        world.spawnEntity(entity);
    }

    protected void buildFoundation(Block block, BlockPos pos) {
        buildFoundation(block, pos.add(0,-1, 0), 32, 0, false);
    }

    /**
     * Builds a foundation column from -1y
     *
     * @param block      The block used
     * @param pos        The block position
     * @param max        the max depth
     * @param penetrance how many solid blocks can it pass through
     * @param relative   whether or not it must be stopped by concecutive blocks.
     */
    protected void buildFoundation(Block block, BlockPos pos, int max, int penetrance,
                                   boolean relative) {
        penetrance -= pos.getY();
        int init_penetrance = penetrance;
        while (-pos.getY() < max) {
            IBlockState current = getBlock(pos, direction);
            boolean empty = current.getMaterial().isReplaceable();
            if (isUnbreakable(pos, direction)) {
                return;
            }
            if (!empty) {
                --penetrance;
            } else if (!relative) {
                penetrance = init_penetrance;
            }

            if (empty || penetrance >= 0) {
                placeBlock(block, pos);
            } else {
                return;
            }
        }
    }

    public TileEntity getTileEntity(BlockPos pos, int dir) {
        BlockPos offset = offsetPos(pos, dir);
        return world.getTileEntity(offset);
    }

    public IBlockState getBlock(BlockPos pos) {
        return getBlock(pos, direction);
    }

    public IBlockState getBlock(BlockPos pos, int dir) {
        BlockPos offset = offsetPos(pos, dir);
        return world.getBlockState(offset);
    }

    public boolean isUnbreakable(BlockPos pos, int dir) {
        return getHardness(pos, dir) < 0;
    }

    public float getHardness(BlockPos pos, int dir) {
        BlockPos offset = offsetPos(pos, dir);
        return world.getBlockState(offset).getBlockHardness(world, offset);
    }

    public void notifyBlock(Block block, BlockPos pos, int dir) {
        BlockPos offset = offsetPos(pos, dir);
        world.notifyNeighborsOfStateChange(offset, block, true);
    }

    public BlockPos offsetPos(BlockPos newPos, int dir) {
        int u = 2;
        int x = pos.getX();
        int y = pos.getY() + newPos.getY();
        int z = pos.getZ();

        switch (dir) {
            case 0:// NORTH
                x += pos.getX();
                z += pos.getZ();
                break;

            case 1:// EAST
                x -= pos.getZ();
                z += pos.getX();
                break;

            case 2:// SOUTH
                x -= pos.getX();
                z -= pos.getZ();
                break;

            case 3:// WEST
                x += pos.getZ();
                z -= pos.getX();
                break;
        }

        return new BlockPos(x, y, z);
    }

    protected void mapStructure(BlockPos pos, Class<? extends StructureModuleMFR> piece) {
        mapStructure(pos, direction, piece);
    }

    protected void mapStructure(BlockPos pos, int dir, Class<? extends StructureModuleMFR> piece) {
        mapStructure(pos, dir, piece, 0);
    }

    protected void mapStructure(BlockPos pos, int dir, Class<? extends StructureModuleMFR> piece, int delay) {
        BlockPos offset = offsetPos(pos, this.direction);

        Block block = world.getBlockState(offset).getBlock();

        world.setBlockState(offset, BlockListMFR.WG_MARK.getDefaultState(), 2);
        TileEntity tile = world.getTileEntity(offset);
        if (tile != null && tile instanceof TileEntityWorldGenMarker) {
            ((TileEntityWorldGenMarker) tile).className = piece.getName();
            ((TileEntityWorldGenMarker) tile).length = lengthId - 1;
            ((TileEntityWorldGenMarker) tile).deviation = deviationCount;
            ((TileEntityWorldGenMarker) tile).prevID = Block.getIdFromBlock(block);
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

    public void placeSpawner(BlockPos pos) {
        placeSpawner(pos, DungeonHooks.getRandomDungeonMob(rand));
    }

    public void placeSpawner(BlockPos pos, ResourceLocation mob) {
        this.placeBlock(Blocks.MOB_SPAWNER, pos, 0 );
        TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner) getTileEntity(pos, direction);

        if (tileentitymobspawner != null) {
            tileentitymobspawner.getSpawnerBaseLogic().setEntityId(mob);
        } else {
            this.placeBlock( Blocks.AIR, pos, 0);
        }
    }

    public static class StructureCoordinates {
        public final int direction;
        public final BlockPos pos;

        public StructureCoordinates(BlockPos pos, int direction) {
            this.pos = pos;
            this.direction = direction;
        }
    }
}
