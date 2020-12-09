package minefantasy.mfr.mechanics.worldGen.structure.dwarven;

import minefantasy.mfr.entity.mob.EntityMinotaur;
import minefantasy.mfr.entity.mob.MinotaurBreed;
import minefantasy.mfr.init.BlockListMFR;
import minefantasy.mfr.mechanics.worldGen.structure.StructureGenAncientForge;
import minefantasy.mfr.mechanics.worldGen.structure.StructureModuleMFR;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class StructureGenDSEntry extends StructureModuleMFR {
    private static Block floor = BlockListMFR.COBBLESTONE_ROAD;
    public boolean isSurfaceBuild;
    private ResourceLocation lootType = LootTableList.CHESTS_SIMPLE_DUNGEON;

    public StructureGenDSEntry(World world, StructureCoordinates position) {
        super(world, position);
    }

    public StructureGenDSEntry(World world, BlockPos pos, int direction, boolean surface) {
        super(world, pos, direction);
        this.isSurfaceBuild = surface;
    }

    @Override
    public void generate() {
        int environment = MinotaurBreed.getEnvironment(world, pos.getX(), pos.getZ(), 0);
        subtype = environment == 2 ? "Frost" : "Normal";

        int width_span = 4;
        int depth = 10;
        int height = 5;
        for (int x = -width_span; x <= width_span; x++) {
            for (int z = 0; z <= depth; z++) {
                // FOUNDATION
                Object[] blockarray = getFoundation(width_span, depth, x, z);
                if (blockarray != null) {
                    int meta = (Boolean) blockarray[1] ? -1 : 0;
                    this.buildFoundation((Block) blockarray[0], new BlockPos(x, -1, z), 32, 4, false);
                }

                // FLOOR
                blockarray = getFloor(width_span, depth, x, z);
                if (blockarray != null) {
                    int meta = (Boolean) blockarray[1] ? StructureGenAncientForge.getRandomMetadata(rand) : 0;
                    placeBlock((Block) blockarray[0], new BlockPos(x, 0, z) );
                }
                // WALLS
                for (int y = 1; y <= height + 1; y++) {
                    blockarray = getWalls(width_span, depth, x, z);
                    if (blockarray != null) {
                        int meta = (Boolean) blockarray[1] ? StructureGenAncientForge.getRandomMetadata(rand) : 0;
                        placeBlock((Block) blockarray[0], new BlockPos(x, y, z) );
                    }
                }
                // CEILING
                blockarray = getCeiling(width_span, depth, x, z);
                if (blockarray != null) {
                    int meta = (Boolean) blockarray[1] ? StructureGenAncientForge.getRandomMetadata(rand) : 0;
                    placeBlock((Block) blockarray[0], new BlockPos(x, height + 1, z));
                }

                // TRIM
                blockarray = getTrim(width_span, depth, x, z);
                if (blockarray != null) {
                    int meta = (Boolean) blockarray[1] ? StructureGenAncientForge.getRandomMetadata(rand) : 0;
                    placeBlock((Block) blockarray[0], new BlockPos(x, height + 2, z) );
                    placeBlock((Block) blockarray[0], new BlockPos(x, height, z) );
                    if ((Block) blockarray[0] == BlockListMFR.REINFORCED_STONE_FRAMED) {
                        for (int h = height - 1; h > 1; h--) {
                            placeBlock(BlockListMFR.REINFORCED_STONE, new BlockPos(x, h, z) );
                        }
                        placeBlock(BlockListMFR.REINFORCED_STONE_FRAMED, new BlockPos(x, 1, z));
                    }
                }

            }
        }

        // DOORWAY
        for (int x = -1; x <= 1; x++) {
            for (int y = 1; y <= 3; y++) {
                placeBlock(Blocks.AIR, new BlockPos(x, y, 0) );
            }
            placeBlock(BlockListMFR.REINFORCED_STONE_BRICKS,  new BlockPos(x, 0, -1) );
            placeBlock(BlockListMFR.REINFORCED_STONE_BRICKS,  new BlockPos(x, 0, -2) );

            placeBlock(BlockListMFR.REINFORCED_STONE,  new BlockPos(x, -1, -1) );
            placeBlock(BlockListMFR.REINFORCED_STONE,  new BlockPos(x, -1, -2) );
            placeBlock(BlockListMFR.REINFORCED_STONE,  new BlockPos(x, 0, -3) );
        }
        placeBlock(BlockListMFR.REINFORCED_STONE,  new BlockPos( -2, 0, -1));
        placeBlock(BlockListMFR.REINFORCED_STONE,  new BlockPos(-2, 0, -2) );
        placeBlock(BlockListMFR.REINFORCED_STONE,  new BlockPos(2, 0, -1) );
        placeBlock(BlockListMFR.REINFORCED_STONE,  new BlockPos(2, 0, -2) );

        placeDoorway(-1, width_span, height, depth);
        placeDoorway(1, width_span, height, depth);
        placeBlock(BlockListMFR.REINFORCED_STONE, new BlockPos(0, height - 1, -3) );
        placeBlock(BlockListMFR.REINFORCED_STONE,  new BlockPos( 0, height, -1));

        EntityMinotaur mob = new EntityMinotaur(world);
        this.placeEntity(mob,  new BlockPos(0, 1, depth / 2));
        mob.worldGenTier(MinotaurBreed.getEnvironment(subtype), 1);

        this.lengthId = -99;
        mapStructure( new BlockPos(0, 0, depth), StructureGenDSStairs.class);

        if (isSurfaceBuild) {
            mapStructure( new BlockPos(-width_span, 0, depth - 1), rotateRight(), StructureDSSurfaceAppendage.class);
            mapStructure( new BlockPos(-width_span, 0, 2), rotateRight(), StructureDSSurfaceAppendage.class);

            mapStructure( new BlockPos(width_span, 0, depth - 1), rotateLeft(), StructureDSSurfaceAppendage.class);
            mapStructure( new BlockPos(width_span, 0, 2), rotateLeft(), StructureDSSurfaceAppendage.class);
        }
    }

    private void placeDoorway(int mod, int width_span, int height, int depth) {
        int h = height;
        placeBlock(BlockListMFR.REINFORCED_STONE,  new BlockPos(3 * mod, 1, -1) );
        placeBlock(BlockListMFR.REINFORCED_STONE,  new BlockPos(3 * mod, 1, -2) );
        for (int y = 1; y < h; y++) {
            placeBlock(BlockListMFR.REINFORCED_STONE,  new BlockPos(2 * mod, y, -3) );
        }
        placeBlock(BlockListMFR.REINFORCED_STONE,  new BlockPos(1 * mod, height - 1, -3) );

        placeBlock(BlockListMFR.REINFORCED_STONE,  new BlockPos(2 * mod, height, -3) );
        placeBlock(BlockListMFR.REINFORCED_STONE,  new BlockPos(2 * mod, height, -2) );
        placeBlock(BlockListMFR.REINFORCED_STONE,  new BlockPos(1 * mod, height, -1) );
        placeBlock(BlockListMFR.REINFORCED_STONE,  new BlockPos(2 * mod, height, -1) );
        placeBlock(BlockListMFR.REINFORCED_STONE,  new BlockPos(3 * mod, height, -1) );

        placeBlock(BlockListMFR.REINFORCED_STONE,  new BlockPos(2 * mod, height + 1, -1) );
        placeBlock(BlockListMFR.REINFORCED_STONE,  new BlockPos(2 * mod, height + 2, -1) );
        placeBlock(BlockListMFR.REINFORCED_STONE,  new BlockPos(2 * mod, height + 2, 0) );
    }

    private Object[] getTrim(int radius, int depth, int x, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            return null;
        }
        if (x == -(radius - 1) || x == (radius - 1)) {
            if (z == (int) Math.ceil((float) depth / 2)) {
                return new Object[]{BlockListMFR.REINFORCED_STONE_FRAMED, false};
            }
        }
        if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
            if ((x == -(radius - 1) && (z == (depth - 1) || z == 1))
                    || (x == (radius - 1) && (z == (depth - 1) || z == 1))) {
                return new Object[]{BlockListMFR.REINFORCED_STONE_FRAMED, false};
            }
            return new Object[]{BlockListMFR.REINFORCED_STONE, false};
        }
        return null;
    }

    private Object[] getCeiling(int radius, int depth, int x, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            return null;
        }
        if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
            return new Object[]{BlockListMFR.REINFORCED_STONE, false};
        }
        return new Object[]{BlockListMFR.REINFORCED_STONE_BRICKS, true};
    }

    private Object[] getFloor(int radius, int depth, int x, int z) {
        if (z < 2 && x >= -1 && x <= 1) {
            return new Object[]{floor, false};
        }
        if (x == -radius || x == radius || z == depth || z == 0) {
            return new Object[]{BlockListMFR.REINFORCED_STONE, false};
        }
        if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
            return new Object[]{BlockListMFR.REINFORCED_STONE, false};
        }
        return new Object[]{floor, false};
    }

    private Object[] getFoundation(int radius, int depth, int x, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            if (x == -radius || x == radius || x == 0) {
                if (z == depth || z == Math.ceil((float) depth / 2) || z == 0) {
                    return new Object[]{BlockListMFR.REINFORCED_STONE, false};
                }
            }
        }
        return new Object[]{BlockListMFR.REINFORCED_STONE_BRICKS, true};
    }

    private Object[] getWalls(int radius, int depth, int x, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            if ((x == -radius && (z == depth || z == 0)) || (x == radius && (z == depth || z == 0))) {
                return new Object[]{BlockListMFR.REINFORCED_STONE, false};
            }

            return new Object[]{BlockListMFR.REINFORCED_STONE_BRICKS, true};
        }
        return new Object[]{Blocks.AIR, false};
    }

    public StructureModuleMFR setLoot(ResourceLocation loot) {
        this.lootType = loot;
        return this;
    }
}
