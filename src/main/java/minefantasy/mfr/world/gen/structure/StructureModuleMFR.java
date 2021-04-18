package minefantasy.mfr.world.gen.structure;

import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.tile.TileEntityWorldGenMarker;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.DungeonHooks;

import java.util.Random;

public abstract class StructureModuleMFR {
    public final World world;
    public final BlockPos pos;
    /**
     * (0-3) South, West, North, East
     */
    public final EnumFacing facing;

    public final Random random;

    /**
     * How much longer can units be added
     */
    public int lengthId;
    /**
     * How many more branches can be added
     */
    public int deviationCount;
    protected String subtype = "normal";

    public StructureModuleMFR(World world, BlockPos pos, EnumFacing facing, Random random) {
        this.world = world;
        this.pos = pos;
        this.facing = facing;
        this.random = random;
    }

    public static void placeStructure(String className, String subtype, int lengthID, int deviation, World world, BlockPos pos, EnumFacing facing, Random random) {
        try {
            Class<?> testClass = Class.forName(className);

            Class<? extends StructureModuleMFR> structureClass = testClass.asSubclass(StructureModuleMFR.class);

            if (className != null) {
                StructureModuleMFR module = structureClass.getConstructor(new Class[]{World.class, BlockPos.class, EnumFacing.class, Random.class}).newInstance(world, pos, facing, random);

                if (module != null) {
                    module.subtype = subtype;
                    module.lengthId = lengthID;
                    module.deviationCount = deviation;
                    if (module.canGenerate()) {
                        module.generate();
                        world.removeTileEntity(pos);
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

    /**
     * Places a block and rotates it on it's base direction
     *
     * @param block   The block
     * @param x       The x offset relative to the pos
     * @param y       The y offset relative to the pos
     * @param z       The z offset relative to the pos
     */
    public void placeBlock(World world, Block block, int x, int y, int z) {
        placeBlock(world, block, x, y, z, facing);
    }

    /**
     * Places a block and rotates it on direction
     *
     * @param block   The block
     * @param x       The x offset relative to the pos
     * @param y       The y offset relative to the pos
     * @param z       The z offset relative to the pos
     * @param facing  The facing of the block
     */
    public void placeBlock(World world, Block block, int x, int y, int z, EnumFacing facing) {
        world.setBlockState(offsetPos(x, y, z, facing), block.getDefaultState(), 2);
    }

    /**
     * Places a block with a specific state with default facing
     *
     * @param state   The IBlockState of the variant
     * @param x       The x offset relative to the pos
     * @param y       The y offset relative to the pos
     * @param z       The z offset relative to the pos
     */
    public void placeBlockWithState(World world, IBlockState state, int x, int y, int z) {
        placeBlockWithState(world, state, x, y, z, facing);
    }

    /**
     * Places a block with a specific state with facing
     *
     * @param state   The IBlockState of the variant
     * @param x       The x offset relative to the pos
     * @param y       The y offset relative to the pos
     * @param z       The z offset relative to the pos
     * @param facing  The facing of the block
     */
    public void placeBlockWithState(World world, IBlockState state, int x, int y, int z, EnumFacing facing) {
        world.setBlockState(offsetPos(x, y, z, facing), state, 2);
    }

    /**
     * Places a stair block and rotates it on direction
     *
     * @param block         The block
     * @param x       The x offset relative to the pos
     * @param y       The y offset relative to the pos
     * @param z       The z offset relative to the pos
     * @param offsetFacing  The offsetFacing of the block
     * @param stairFacing   The stairFacing of the block
     */
    public void placeStairBlock(World world, Block block, int x, int y, int z, EnumFacing offsetFacing, EnumFacing stairFacing) {
        world.setBlockState(offsetPos(x, y, z, offsetFacing), block.getDefaultState().withProperty(BlockStairs.FACING, stairFacing), 2);
    }

    /**
     * Accepted Blocks: StoneBrick, MineFantasy Reinforced StoneBrick, MineFantasy Reinforced Engraved Stone after type 0
     *
     * @param block  The Block to get the random variant of
     * @param random Random value for randomization
     */

    public IBlockState getRandomVariantBlock(Block block, Random random) {
        IBlockState state = Blocks.AIR.getDefaultState();

        float f = random.nextFloat();

        if (block == Blocks.STONEBRICK){
            if (f < 0.2F) {
                state = Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.MOSSY);
            }
            else if (f < 0.5F) {
                state = Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED);
            }
            else if (f < 0.55F) {
                state = Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CRACKED);
            }
            else {
                state = Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.DEFAULT);
            }
        }

        if (block == MineFantasyBlocks.REINFORCED_STONE_BRICKS){
            if (f < 0.2F) {
                state = MineFantasyBlocks.REINFORCED_STONE_BRICKS_MOSSY.getDefaultState();
            }
            else if (f < 0.55F) {
                state = MineFantasyBlocks.REINFORCED_STONE_BRICKS_CRACKED.getDefaultState();
            }
            else {
                state = MineFantasyBlocks.REINFORCED_STONE_BRICKS.getDefaultState();
            }
        }

        if (block == MineFantasyBlocks.REINFORCED_STONE_ENGRAVED_1){
            if (f < 0.2F) {
                state = MineFantasyBlocks.REINFORCED_STONE_ENGRAVED_1.getDefaultState();
            }
            else if (f < 0.5F) {
                state = MineFantasyBlocks.REINFORCED_STONE_ENGRAVED_2.getDefaultState();
            }
            else if (f < 0.55F) {
                state = MineFantasyBlocks.REINFORCED_STONE_ENGRAVED_3.getDefaultState();
            }
            else {
                state = MineFantasyBlocks.REINFORCED_STONE_ENGRAVED_1.getDefaultState();
            }
        }

        return state;

    }

    public void placeSpawner(World world, int x, int y, int z, Random random) {
        placeSpawner(world, x, y, z, DungeonHooks.getRandomDungeonMob(random));
    }

    public void placeSpawner(World world, int x, int y, int z, ResourceLocation mob) {
        this.placeBlock(world, Blocks.MOB_SPAWNER, x , y, z);
        TileEntityMobSpawner spawner = (TileEntityMobSpawner) world.getTileEntity(offsetPos(x, y, z, facing));

        if (spawner != null) {
            spawner.getSpawnerBaseLogic().setEntityId(mob);
        } else {
            this.placeBlock(world, Blocks.AIR, x, y, z);
        }
    }

    public void placeEntity(World world, Entity entity, int x, int y, int z) {
        placeEntity(world, entity, x, y, z, facing);
    }

    public void placeEntity(World world, Entity entity, int x, int y, int z, EnumFacing facing) {
        BlockPos offset = offsetPos(x, y, z ,facing);
        entity.setLocationAndAngles(offset.getX() + 0.5D, offset.getY() + entity.height - 1.0F, offset.getZ() + 0.5D, MathHelper.wrapDegrees(world.rand.nextFloat() * 360.0F), 0.0F);
        world.spawnEntity(entity);
    }

    public boolean isUnbreakable(World world, int x, int y, int z, EnumFacing facing) {
        return getHardness(world, x, y, z, facing) < 0;
    }

    public float getHardness(World world, int x, int y, int z, EnumFacing facing) {
        return world.getBlockState(offsetPos(x, y, z, facing)).getBlockHardness(world, offsetPos(x, y, z, facing));
    }

    public BlockPos offsetPos(int xo, int yo, int zo, EnumFacing facing) {
        int x = pos.getX();
        int y = pos.getY() + yo;
        int z = pos.getZ();

        switch (facing) {
            case NORTH:// NORTH
                x += xo;
                z += zo;
                break;

            case EAST:// EAST
                x -= zo;
                z += xo;
                break;

            case SOUTH:// SOUTH
                x -= xo;
                z -= zo;
                break;

            case WEST:// WEST
                x += zo;
                z -= xo;
                break;
        }
        return new BlockPos(x, y, z);
    }

    /**
     * Builds a foundation column from -1y
     *
     * @param state      The state used
     * @param x          the relative x position
     * @param y          the relative y position to start
     * @param z          the relative z position
     * @param max        the max depth
     * @param penetration how many solid blocks can it pass through
     * @param relative   whether or not it must be stopped by concecutive blocks.
     */
    protected void buildFoundation(IBlockState state, int x, int y, int z, int max, int penetration, boolean relative) {
        penetration -= y;
        int init_penetration = penetration;
        while (-y < max) {
            IBlockState current = world.getBlockState(offsetPos(x, y, z, facing));
            boolean empty = current.getMaterial().isReplaceable();

            if (isUnbreakable(world, x, y, z, facing)) {
                return;
            }
            if (!empty) {
                --penetration;
            } else if (!relative) {
                penetration = init_penetration;
            }

            if (empty || penetration >= 0) {
                placeBlockWithState(world, state, x, y, z);
                --y;
            } else {
                return;
            }
        }
    }

    protected void mapStructure(int x, int y, int z, Class<? extends StructureModuleMFR> piece) {
        mapStructure(x, y, z, facing, piece);
    }

    protected void mapStructure(int x, int y, int z, EnumFacing facing, Class<? extends StructureModuleMFR> piece) {
        mapStructure(x, y, z, facing, piece, 0);
    }

    protected void mapStructure(int x, int y, int z, EnumFacing facing, Class<? extends StructureModuleMFR> piece, int delay) {
        BlockPos offsetPos = offsetPos(x, y, z, this.facing);

        Block block = world.getBlockState(offsetPos).getBlock();

        world.setBlockState(offsetPos, MineFantasyBlocks.WG_MARK.getDefaultState(), 2);
        TileEntity tile = world.getTileEntity(offsetPos);
        if (tile instanceof TileEntityWorldGenMarker) {
            ((TileEntityWorldGenMarker) tile).className = piece.getName();
            ((TileEntityWorldGenMarker) tile).length = lengthId - 1;
            ((TileEntityWorldGenMarker) tile).deviation = deviationCount;
            ((TileEntityWorldGenMarker) tile).prevID = Block.getIdFromBlock(block);
            ((TileEntityWorldGenMarker) tile).prevFacing = facing.getIndex();
            ((TileEntityWorldGenMarker) tile).ticks -= delay;
            ((TileEntityWorldGenMarker) tile).type = this.subtype;
        }
    }
}
