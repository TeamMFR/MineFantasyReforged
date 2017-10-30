package minefantasy.mf2.mechanics.worldGen.structure.dwarven;

import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.mechanics.worldGen.structure.LootTypes;
import minefantasy.mf2.mechanics.worldGen.structure.StructureGenAncientForge;
import minefantasy.mf2.mechanics.worldGen.structure.StructureModuleMF;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;

public class StructureGenDSRoomSml2 extends StructureModuleMF {
    public StructureGenDSRoomSml2(World world, StructureCoordinates position) {
        super(world, position);
    }

    protected int getHeight() {
        return 5;
    }

    protected int getDepth() {
        return 10;
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
                    Block block = this.getBlock(x, y, z);
                    if (!allowBuildOverBlock(block) || this.isUnbreakable(x, y, z, direction)) {
                        return false;
                    }
                    if (!block.getMaterial().isSolid()) {
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

    private boolean allowBuildOverBlock(Block block) {
        if (block == BlockListMF.reinforced_stone_bricks || block == BlockListMF.reinforced_stone) {
            return false;
        }
        return true;
    }

    @Override
    public void generate() {
        int width = getWidth(), depth = getDepth(), height = getHeight();

        for (int x = -width; x <= width; x++) {
            for (int z = 0; z <= depth; z++) {
                Object[] blockarray;
                // FLOOR
                blockarray = getFloor(width, depth, x, z);
                if (blockarray != null) {
                    placeBlock((Block) blockarray[0], 0, x, 0, z);
                }
                // WALLS
                for (int y = 1; y <= height; y++) {
                    blockarray = getWalls(width, depth, x, z);
                    if (blockarray != null) {
                        int meta = (Boolean) blockarray[1] ? StructureGenAncientForge.getRandomMetadata(rand) : 0;
                        placeBlock((Block) blockarray[0], meta, x, y, z);
                    }
                }
                // CEILING
                blockarray = getCeiling(width, depth, x, z);
                if (blockarray != null) {
                    int meta = (Boolean) blockarray[1] ? StructureGenAncientForge.getRandomMetadata(rand) : 0;
                    placeBlock((Block) blockarray[0], meta, x, height, z);
                }
            }
        }
        placeBlock(Blocks.air, 0, 0, 1, 0);
        placeBlock(Blocks.air, 0, 0, 2, 0);

        placeBlock(BlockListMF.reinforced_stone_framed, 0, -1, 1, -1);
        placeBlock(BlockListMF.reinforced_stone, 0, -1, 2, -1);
        placeBlock(BlockListMF.reinforced_stone_framediron, 0, -1, 3, -1);

        placeBlock(BlockListMF.reinforced_stone_framed, 0, 1, 1, -1);
        placeBlock(BlockListMF.reinforced_stone, 0, 1, 2, -1);
        placeBlock(BlockListMF.reinforced_stone_framediron, 0, 1, 3, -1);

        placeBlock(BlockListMF.reinforced_stone, 1, 0, 3, -1);
        placeBlock(Blocks.air, 0, 0, 1, -1);
        placeBlock(Blocks.air, 0, 0, 2, -1);

        buildHomeFurnishings(width, depth, height);
    }

    private Object[] getFloor(int width, int depth, int x, int z) {
        if (x == -(width - 1) || x == (width - 1) || z == 1 || z == depth - 1) {
            return new Object[]{BlockListMF.reinforced_stone, 0};
        }
        return new Object[]{BlockListMF.refined_planks, 0};
    }

    private Object[] getCeiling(int width, int depth, int x, int z) {
        if (x == -(width - 1) || x == (width - 1) || z == 1 || z == depth - 1) {
            return new Object[]{BlockListMF.reinforced_stone, false};
        }
        return new Object[]{BlockListMF.reinforced_stone_bricks, true};
    }

    private Object[] getWalls(int width, int depth, int x, int z) {
        if (x == -width || x == width || z == depth || z == 0) {
            if ((x == -width && (z == depth || z == 0)) || (x == width && (z == depth || z == 0))) {
                return new Object[]{BlockListMF.reinforced_stone, false};
            }

            return new Object[]{BlockListMF.reinforced_stone_bricks, true};
        }
        return new Object[]{Blocks.air, false};
    }

    private void buildHomeFurnishings(int width, int depth, int height) {
        placeBlock(Blocks.furnace, rotateLeft(), width, 2, 2);
        placeBlock(Blocks.double_stone_slab, 0, width - 3, 1, 1);
        placeBlock(Blocks.double_stone_slab, 0, width - 3, 1, 2);

        for (int x = width - 1; x >= (width - 4); x--) {
            placeBlock(BlockListMF.reinforced_stone_bricks, StructureGenAncientForge.getRandomMetadata(rand), x, 1, 5);
            placeBlock(BlockListMF.reinforced_stone_bricks, StructureGenAncientForge.getRandomMetadata(rand), x, 2, 5);
            placeBlock(Blocks.stone_slab, 0, x, 3, 5);
        }
        placeBlock(Blocks.stone_slab, 0, width - 1, 1, 7);
        placeBlock(Blocks.stone_slab, 0, width - 2, 1, 7);
        placeBlock(Blocks.stone_slab, 0, width - 1, 1, 8);
        placeBlock(Blocks.stone_slab, 0, width - 2, 1, 8);
        placeBlock(Blocks.cauldron, 0, width - 2, 1, 1);

        placeBlock(Blocks.stone_brick_stairs, this.getStairDirection(reverse()), -(width - 1), 1, 1);
        placeBlock(Blocks.stone_slab, 8, -(width - 1), 1, 2);
        placeBlock(Blocks.stone_brick_stairs, this.getStairDirection(direction), -(width - 1), 1, 3);

        placeChest(width - 3, 1, 7, rotateLeft(), LootTypes.DWARVEN_HOME_RICH);
    }

    private void placeChest(int x, int y, int z, int d, String loot) {
        placeBlock(Blocks.chest, d, x, y, z);
        TileEntityChest tile = (TileEntityChest) getTileEntity(x, y, z, direction);

        if (tile != null) {
            WeightedRandomChestContent.generateChestContents(rand, ChestGenHooks.getItems(loot, rand), tile,
                    2 + rand.nextInt(3));
        }
    }
}
