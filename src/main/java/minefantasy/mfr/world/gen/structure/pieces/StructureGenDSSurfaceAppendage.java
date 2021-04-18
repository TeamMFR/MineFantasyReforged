package minefantasy.mfr.world.gen.structure.pieces;

import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.MineFantasyLoot;
import minefantasy.mfr.world.gen.structure.StructureModuleMFR;
import minefantasy.mfr.world.gen.structure.WorldGenDwarvenStronghold;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class StructureGenDSSurfaceAppendage extends StructureModuleMFR {
    private final String type;

    public StructureGenDSSurfaceAppendage(World world, BlockPos pos, EnumFacing facing, Random random) {
        super(world, pos, facing, random);
        type = randomiseType();
    }

    private String randomiseType() {
        return random.nextInt(3) == 0 ? "tower" : "room";
    }

    protected int getHeight() {
        return type.equals("tower") ? 12 : 5;
    }

    protected int getDepth() {
        return 6;
    }

    protected int getWidth() {
        return 3;
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
        return ((float) emptySpaces / (float) filledSpaces) > 1.0F;// at least 50% empty
    }

    private boolean allowBuildOverBlock(IBlockState state) {
        return state != MineFantasyBlocks.REINFORCED_STONE_BRICKS.getDefaultState()
                && state != MineFantasyBlocks.REINFORCED_STONE.getDefaultState()
                && state != MineFantasyBlocks.REINFORCED_STONE_ENGRAVED_0.getDefaultState()
                && state != MineFantasyBlocks.REINFORCED_STONE_ENGRAVED_1.getDefaultState()
                && state != MineFantasyBlocks.REINFORCED_STONE_ENGRAVED_2.getDefaultState()
                && state != MineFantasyBlocks.REINFORCED_STONE_ENGRAVED_3.getDefaultState();
    }

    @Override
    public void generate() {
        int width = getWidth(), depth = getDepth(), height = getHeight();

        for (int x = -width; x <= width; x++) {
            for (int z = 0; z <= depth; z++) {
                IBlockState state;

                // FLOOR
                state = getFloor(width, depth, x, z);
                if (state != null) {
                    placeBlockWithState(world, state, x, 0, z);
                }
                placeBlockWithState(world, getRandomVariantBlock(MineFantasyBlocks.REINFORCED_STONE_BRICKS, random), x, -1, z);
                if (z < depth) {
                    placeBlockWithState(world, getRandomVariantBlock(MineFantasyBlocks.REINFORCED_STONE_BRICKS, random), x, -2, z);
                }
                if (z < depth - 1) {
                    placeBlockWithState(world, getRandomVariantBlock(MineFantasyBlocks.REINFORCED_STONE_BRICKS, random), x, -3, z);
                }
                // WALLS
                for (int y = 0; y <= height; y++) {
                    state = getWalls(width, depth, height, x, y, z);
                    if (state != null && this.allowBuildOverBlock(world.getBlockState(offsetPos(x, y, z, facing)))) {
                        placeBlockWithState(world, state, x, y, z);
                    }
                }
                // CEILING
                state = getCeiling(width, depth, x, z);
                if (state != null) {
                    placeBlockWithState(world, state, x, height, z);
                }
                // BATTLEMENT
                if (type.equals("tower")) {
                    state = getRoofDecor(width, depth, x, z);
                    if (state != null) {
                        placeBlockWithState(world, state, x, height + 1, z);
                    }
                }
            }
        }
        placeBlock(world, Blocks.AIR, 0, 1, 0);
        placeBlock(world, Blocks.AIR, 0, 2, 0);

        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_FRAMED, -1, 1, -1);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, -1, 2, -1);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_FRAMED_IRON, -1, 3, -1);

        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_FRAMED, 1, 1, -1);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, 1, 2, -1);
        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_FRAMED_IRON, 1, 3, -1);

        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, 0, 3, -1);
        placeBlock(world, Blocks.AIR, 0, 1, -1);
        placeBlock(world, Blocks.AIR, 0, 2, -1);

        if (type.equals("tower")) {
            decorateTower(width, depth, height);
        } else {
            buildHomeFurnishings(width, depth, height);
        }
    }

    private IBlockState getFloor(int width, int depth, int x, int z) {
        if (x <= -(width - 1) || x >= (width - 1) || z <= 1 || z >= depth - 1) {
            return MineFantasyBlocks.REINFORCED_STONE.getDefaultState();
        }
        return MineFantasyBlocks.COBBLE_BRICK.getDefaultState();
    }

    private IBlockState getCeiling(int width, int depth, int x, int z) {
        if (x == -width || x == width || z == 0 || z == depth) {
            return MineFantasyBlocks.REINFORCED_STONE.getDefaultState();
        }
        return getRandomVariantBlock(MineFantasyBlocks.REINFORCED_STONE_BRICKS, random);
    }

    private IBlockState getRoofDecor(int width, int depth, int x, int z) {
        if (x == -width || x == width || z == 0 || z == depth) {
            if (x == 0 || x == -width || x == width) {
                if (z == 0 || z == depth / 2 || z == depth) {
                    return MineFantasyBlocks.REINFORCED_STONE.getDefaultState();
                }
            }
            return MineFantasyBlocks.BRONZE_BARS.getDefaultState();
        }
        return null;
    }

    private IBlockState getWalls(int radius, int depth, int height, int x, int y, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            if ((x == -radius && (z == depth || z == 0)) || (x == radius && (z == depth || z == 0))) {
                return MineFantasyBlocks.REINFORCED_STONE.getDefaultState();
            }

            return getRandomVariantBlock(MineFantasyBlocks.REINFORCED_STONE_BRICKS, random);
        }
        if (type.equals("tower") && y == (height / 2)) {
            return getRandomVariantBlock(MineFantasyBlocks.REINFORCED_STONE_BRICKS, random);
        }
        if (y <= 0) {
            return null;
        }
        return Blocks.AIR.getDefaultState();
    }

    private void decorateTower(int width, int depth, int height) {
        for (int y = 1; y <= height; y++) {
            if (y >= 2 && y <= (height - 2) && (y < (height / 2) - 1 || y > (height / 2) + 1)) {
                this.placeBlock(world, MineFantasyBlocks.BRONZE_BARS, -width, y, depth / 2);
                this.placeBlock(world, MineFantasyBlocks.BRONZE_BARS, width, y, depth / 2);
                this.placeBlock(world, MineFantasyBlocks.BRONZE_BARS, 0, y, depth);
                if (y > (height / 2) + 1) {
                    this.placeBlock(world, MineFantasyBlocks.BRONZE_BARS,0, y, 0);
                }
            }
            this.placeBlockWithState(world, Blocks.LADDER.getDefaultState().withProperty(BlockChest.FACING, facing.getOpposite()), -1, y, 1);
        }
        this.placeBlock(world, Blocks.GLOWSTONE,  0, height / 2, depth / 2);

    }

    private void buildHomeFurnishings(int width, int depth, int height) {
        for (int x = width - 1; x >= (width - 4); x--) {
            placeBlockWithState(world, getRandomVariantBlock(MineFantasyBlocks.REINFORCED_STONE_BRICKS, random), x, 1, 3);
            placeBlockWithState(world, getRandomVariantBlock(MineFantasyBlocks.REINFORCED_STONE_BRICKS, random), x, 2, 3);
            placeBlock(world, Blocks.STONE_SLAB,  x, 3, 3);
        }
        placeBlock(world, Blocks.STONE_SLAB, width - 1, 1, depth - 1);
        placeBlock(world, Blocks.STONE_SLAB, width - 2, 1, depth - 1);

        placeChest(width - 3, depth - 1, MineFantasyLoot.DWARVEN_HOME);

        for (int x = -(width - 1); x < width; x++) {
            for (int z = 1; z < depth; z++) {
                if (x == width - 1 || x == -(width - 1) || z == 1 || z == (depth - 1)) {
                    placeBlock(world, MineFantasyBlocks.REINFORCED_STONE, x, height + 1, z);
                }
            }
        }
    }

    private void placeChest(int x, int z, ResourceLocation loot) {
        placeBlockWithState(world, Blocks.CHEST.getDefaultState().withProperty(BlockChest.FACING, facing.rotateYCCW()), x, 1, z);
        TileEntityChest chest = (TileEntityChest) world.getTileEntity(offsetPos(x, 1, z, facing));

        if (chest != null) {
            chest.setLootTable(loot, random.nextLong());
        }
    }
}
