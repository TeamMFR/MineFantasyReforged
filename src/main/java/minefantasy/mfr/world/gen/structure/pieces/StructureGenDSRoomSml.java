package minefantasy.mfr.world.gen.structure.pieces;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.MineFantasyLoot;
import minefantasy.mfr.world.gen.structure.StructureModuleMFR;
import minefantasy.mfr.world.gen.structure.WorldGenDwarvenStronghold;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class StructureGenDSRoomSml extends StructureModuleMFR {
    public StructureGenDSRoomSml(World world, BlockPos pos, EnumFacing facing, Random random) {
        super(world, pos, facing, random);
    }

    protected int getHeight() {
        return 5;
    }

    protected int getDepth() {
        return 8;
    }

    protected int getWidth() {
        return 4;
    }

    @Override
    public boolean canGenerate() {
        int width = getWidth(), depth = getDepth(), height = getHeight();
        int filledSpaces = 0, emptySpaces = 0;

        for (int x = -width; x <= width; x++) {
            for (int y = 0; y <= height; y++) {
                for (int z = 1; z <= depth; z++) {
                    IBlockState state = world.getBlockState(offsetPos(x, y, z, facing));
                    if (!allowBuildOverBlock(state) || this.isUnbreakable(world, x, y, z, facing)) {
                        return false;
                    }
                    if (!state.getMaterial().isSolid()) {
                        ++emptySpaces;
                    } else {
                        ++filledSpaces;
                    }
                }
            }
        }
        if (WorldGenDwarvenStronghold.debug_air) {
            return true;
        }
        return ((float) emptySpaces / (float) (emptySpaces + filledSpaces)) < WorldGenDwarvenStronghold.maxAir;// at
        // least
        // 75%
        // full
    }

    private boolean allowBuildOverBlock(IBlockState state) {
        return state != MineFantasyBlocks.REINFORCED_STONE_BRICKS.getDefaultState() && state != MineFantasyBlocks.REINFORCED_STONE.getDefaultState();
    }

    @Override
    public void generate() {
        int width = getWidth(), depth = getDepth(), height = getHeight();

        for (int x = -width; x <= width; x++) {
            for (int z = 0; z <= depth; z++) {
                Block block;
                // FLOOR
                block = getFloor(width, depth, x, z);
                if (block != null) {
                    placeBlock(world, block, x, 0, z);
                }
                // WALLS
                for (int y = 1; y <= height; y++) {
                    block = getWalls(width, depth, x, z);
                    if (block != null && this.allowBuildOverBlock(world.getBlockState(offsetPos(x, y, z, facing)))) {
                        placeBlock(world, block,  x, y, z);
                    }
                }
                // CEILING
                block = getCeiling(width, depth, x, z);
                if (block != null) {
                    placeBlock(world, block, x, height, z);
                }
            }
        }
        placeBlock(world, Blocks.AIR,0, 1, 0);
        placeBlock(world, Blocks.AIR,  0, 2, 0);

        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_FRAMED, -1, 1, -1);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, -1, 2, -1);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_FRAMED_IRON, -1, 3, -1);

        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_FRAMED, 1, 1, -1);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, 1, 2, -1);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_FRAMED_IRON, 1, 3, -1);

        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, 1, 3, -1);
        placeBlock(world, Blocks.AIR, 0, 1, -1);
        placeBlock(world, Blocks.AIR, 0, 2, -1);

        buildHomeFurnishings(width, depth, height);

        MineFantasyReborn.LOG.error("Placed DSRoomSml at: " + pos);
    }

    private Block getFloor(int width, int depth, int x, int z) {
        if (x == -(width - 1) || x == (width - 1) || z == 1 || z == depth - 1) {
            return MineFantasyBlocks.REINFORCED_STONE;
        }
        return MineFantasyBlocks.COBBLE_BRICK;
    }

    private Block getCeiling(int width, int depth, int x, int z) {
        if (x == -(width - 1) || x == (width - 1) || z == 1 || z == depth - 1) {
            return MineFantasyBlocks.REINFORCED_STONE;
        }
        return MineFantasyBlocks.REINFORCED_STONE_BRICKS;
    }

    private Block getWalls(int width, int depth, int x, int z) {
        if (x == -width || x == width || z == depth || z == 0) {
            if ((x == -width && (z == depth || z == 0)) || (x == width && (z == depth || z == 0))) {
                return MineFantasyBlocks.REINFORCED_STONE;
            }

            return MineFantasyBlocks.REINFORCED_STONE_BRICKS;
        }
        return Blocks.AIR;
    }

    private void buildHomeFurnishings(int width, int depth, int height) {
        placeBlock(world, Blocks.FURNACE, width, 2, 2);

        placeBlock(world, Blocks.DOUBLE_STONE_SLAB, width - 3, 1, 1);
        placeBlock(world, Blocks.DOUBLE_STONE_SLAB, width - 3, 1, 2);

        for (int x = width - 1; x >= (width - 4); x--) {
            placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_BRICKS, x, 1, 4);
            placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_BRICKS, x, 2, 4);
            placeBlock(world, Blocks.STONE_SLAB, x, 3, 4);
        }
        placeBlock(world, Blocks.STONE_SLAB, width - 1, 1, 6);
        placeBlock(world, Blocks.STONE_SLAB, width - 2, 1, 6);
        placeBlock(world, Blocks.CAULDRON, width - 2, 1, 1);

        placeBlock(world, Blocks.STONE_BRICK_STAIRS, -(width - 1), 1, 1);
        placeBlock(world, Blocks.STONE_SLAB, -(width - 1), 1, 2);
        placeBlock(world, Blocks.STONE_BRICK_STAIRS, -(width - 1), 1, 3);

        placeChest(width - 3, 1, 6, MineFantasyLoot.DWARVEN_HOME);
    }

    private void placeChest(int x, int y, int z, ResourceLocation loot) {
        placeBlock(world, Blocks.CHEST, x, y, z);
        TileEntityChest chest = (TileEntityChest) world.getTileEntity(offsetPos(x, y, z, facing));

        if (chest != null) {
            chest.setLootTable(loot, random.nextLong());
        }
    }
}
