package minefantasy.mfr.world.gen.structure.pieces;

import minefantasy.mfr.block.BlockForge;
import minefantasy.mfr.block.BlockRoast;
import minefantasy.mfr.config.ConfigWorldGen;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.MineFantasyLoot;
import minefantasy.mfr.world.gen.structure.StructureModuleMFR;
import minefantasy.mfr.world.gen.structure.WorldGenDwarvenStronghold;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockSlab;
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
                // WALLS
                for (int y = 1; y <= height; y++) {
                    state = getWalls(width, depth, x, z);
                    if (state != null && this.allowBuildOverBlock(world.getBlockState(offsetPos(x, y, z, facing)))) {
                        placeBlockWithState(world, state,  x, y, z);
                    }
                }
                // CEILING
                state = getCeiling(width, depth, x, z);
                if (state != null) {
                    placeBlockWithState(world, state, x, height, z);
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

        placeBlock(world, MineFantasyBlocks.REINFORCED_STONE_ENGRAVED_0, 0, 3, -1);
        placeBlock(world, Blocks.AIR, 0, 1, -1);
        placeBlock(world, Blocks.AIR, 0, 2, -1);

        buildHomeFurnishings(width, depth, height);
    }

    private IBlockState getFloor(int width, int depth, int x, int z) {
        if (x == -(width - 1) || x == (width - 1) || z == 1 || z == depth - 1) {
            return MineFantasyBlocks.REINFORCED_STONE.getDefaultState();
        }
        return MineFantasyBlocks.COBBLE_BRICK.getDefaultState();
    }

    private IBlockState getCeiling(int width, int depth, int x, int z) {
        if (x == -(width - 1) || x == (width - 1) || z == 1 || z == depth - 1) {
            return MineFantasyBlocks.REINFORCED_STONE.getDefaultState();
        }
        return getRandomVariantBlock(MineFantasyBlocks.REINFORCED_STONE_BRICKS, random);
    }

    private IBlockState getWalls(int width, int depth, int x, int z) {
        if (x == -width || x == width || z == depth || z == 0) {
            if ((x == -width && (z == depth || z == 0)) || (x == width && (z == depth || z == 0))) {
                return MineFantasyBlocks.REINFORCED_STONE.getDefaultState();
            }

            return getRandomVariantBlock(MineFantasyBlocks.REINFORCED_STONE_BRICKS, random);
        }
        return Blocks.AIR.getDefaultState();
    }

    private void buildHomeFurnishings(int width, int depth, int height) {
        if (ConfigWorldGen.dwarvenStrongholdShouldFurnaceSpawn) {
            placeBlockWithState(world, Blocks.FURNACE.getDefaultState().withProperty(BlockFurnace.FACING, facing.rotateY()), width, 2, 2);
        }
        else {
            placeBlockWithState(world, MineFantasyBlocks.OVEN.getDefaultState().withProperty(BlockRoast.FACING, facing.rotateYCCW()), width, 2, 2);
            placeBlockWithState(world, MineFantasyBlocks.FORGE.getDefaultState().withProperty(BlockForge.UNDER, true), width, 1, 2);
        }

        placeBlock(world, Blocks.DOUBLE_STONE_SLAB, width - 3, 1, 1);
        placeBlock(world, Blocks.DOUBLE_STONE_SLAB, width - 3, 1, 2);

        for (int x = width - 1; x >= (width - 4); x--) {
            placeBlockWithState(world, getRandomVariantBlock(MineFantasyBlocks.REINFORCED_STONE_BRICKS, random), x, 1, 4);
            placeBlockWithState(world, getRandomVariantBlock(MineFantasyBlocks.REINFORCED_STONE_BRICKS, random), x, 2, 4);
            placeBlock(world, Blocks.STONE_SLAB, x, 3, 4);
        }
        placeBlock(world, Blocks.STONE_SLAB, width - 1, 1, 6);
        placeBlock(world, Blocks.STONE_SLAB, width - 2, 1, 6);
        if (ConfigWorldGen.dwarvenStrongholdShouldCauldronSpawn) {
            placeBlock(world, Blocks.CAULDRON, width - 2, 1, 1);
        }

        placeStairBlock(world, Blocks.STONE_BRICK_STAIRS, -(width - 1), 1, 1, facing, facing);
        placeBlockWithState(world, Blocks.STONE_SLAB.getDefaultState().withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.TOP), -(width - 1), 1, 2);
        placeStairBlock(world, Blocks.STONE_BRICK_STAIRS, -(width - 1), 1, 3, facing, facing.getOpposite());

        placeChest(width - 3, 1, 6, MineFantasyLoot.DWARVEN_HOME);
    }

    private void placeChest(int x, int y, int z, ResourceLocation loot) {
        placeBlockWithState(world, Blocks.CHEST.getDefaultState().withProperty(BlockChest.FACING, facing.rotateYCCW()), x, y, z);
        TileEntityChest chest = (TileEntityChest) world.getTileEntity(offsetPos(x, y, z, facing));

        if (chest != null) {
            chest.setLootTable(loot, random.nextLong());
        }
    }
}
