package minefantasy.mf2.mechanics.worldGen.structure.dwarven;

import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.mechanics.worldGen.structure.StructureGenAncientForge;
import minefantasy.mf2.mechanics.worldGen.structure.StructureModuleMF;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;

public class StructureGenDSStairs extends StructureModuleMF {
    /**
     * The minimal height for a stairway to be created
     */
    public static final int minLevel = 20;
    protected String lootType = ChestGenHooks.DUNGEON_CHEST;

    public StructureGenDSStairs(World world, StructureCoordinates position) {
        super(world, position);
    }

    public StructureGenDSStairs(World world, int x, int y, int z, int direction) {
        super(world, x, y, z, direction);
    }

    @Override
    public boolean canGenerate() {
        if (lengthId == -100) {
            return true;
        }

        int width_span = getWidthSpan();
        int depth = getDepthSpan();
        int height = getHeight();
        int empty_spaces = 0;
        int filledSpaces = 0, emptySpaces = 0;
        for (int x = -width_span; x <= width_span; x++) {
            for (int y = 0; y <= height; y++) {
                for (int z = 1; z <= depth; z++) {
                    Block block = this.getBlock(x, y - z, z);
                    if (!allowBuildOverBlock(block) || this.isUnbreakable(x, y - z, z, direction)) {
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
        int width_span = getWidthSpan();
        int depth = getDepthSpan();
        int height = getHeight();
        for (int x = -width_span; x <= width_span; x++) {
            for (int z = 0; z <= depth; z++) {
                Object[] blockarray;

                // FLOOR
                blockarray = getFloor(width_span, depth, x, z);
                if (blockarray != null) {
                    int meta = (Integer) blockarray[1];
                    placeBlock((Block) blockarray[0], meta, x, -z - 1, z);

                    placeBlock(BlockListMF.reinforced_stone_bricks, StructureGenAncientForge.getRandomMetadata(rand), x,
                            -z - 2, z);

                    if (x == (width_span - 1) || x == -(width_span - 1)) {
                        placeBlock(BlockListMF.reinforced_stone, 0, x, -z, z);
                    } else {
                        placeBlock(Blocks.air, 0, x, -z, z);
                    }
                }
                // WALLS
                for (int y = 0; y <= height + 1; y++) {
                    blockarray = getWalls(width_span, height, depth, x, y, z);
                    if (blockarray != null) {
                        int meta = 0;
                        if (blockarray[1] instanceof Integer) {
                            meta = (Integer) blockarray[1];
                        }
                        if (blockarray[1] instanceof Boolean) {
                            meta = (Boolean) blockarray[1] ? StructureGenAncientForge.getRandomMetadata(rand) : 0;
                        }
                        if (blockarray[1] instanceof String) {
                            meta = (String) blockarray[1] == "Hall" ? StructureGenDSHall.getRandomEngravedWall(rand)
                                    : 0;
                        }
                        placeBlock((Block) blockarray[0], meta, x, y - z, z);
                    }
                }
                // CEILING
                blockarray = getCeiling(width_span, depth, x, z);
                if (blockarray != null) {
                    int meta = (Boolean) blockarray[1] ? StructureGenAncientForge.getRandomMetadata(rand) : 0;
                    placeBlock((Block) blockarray[0], meta, x, height + 1 - z, z);
                }

                // TRIM
                blockarray = getTrim(width_span, depth, x, z);
                if (blockarray != null) {
                    int meta = (Boolean) blockarray[1] ? StructureGenAncientForge.getRandomMetadata(rand) : 0;
                    placeBlock((Block) blockarray[0], meta, x, height - z, z);
                    if ((Block) blockarray[0] == BlockListMF.reinforced_stone_framed) {
                        placeBlock(BlockListMF.reinforced_stone, 0, x, height - z, z);
                        placeBlock(BlockListMF.reinforced_stone_framed, 0, x, height - z - 1, z);

                        for (int h = height - 1; h > 1; h--) {
                            placeBlock(BlockListMF.reinforced_stone, h == 2 ? 1 : 0, x, h - z - 1, z);
                        }
                        placeBlock(BlockListMF.reinforced_stone_framed, 0, x, -z, z);
                    }
                }

            }
        }

        // DOORWAY
        buildDoorway(width_span, depth, height);

        if (lengthId == -100) {
            this.lengthId = -99;
            if ((yCoord > 64 && rand.nextInt(3) != 0) || (yCoord >= 56 && rand.nextInt(3) == 0)) {
                mapStructure(0, -depth, depth, StructureGenDSStairs.class);
            } else {
                mapStructure(0, -depth, depth, StructureGenDSCrossroads.class);
            }
        }
        ++lengthId;// Stairs don't count toward length
        if (lengthId > 0) {
            buildNext(width_span, depth, height);
        }
    }

    protected void buildDoorway(int width_span, int depth, int height) {
        for (int x = -1; x <= 1; x++) {
            for (int y = 1; y <= 3; y++) {
                for (int z = 0; z >= -1; z--) {
                    placeBlock(Blocks.air, 0, x, y, z);
                }
            }
            placeBlock(BlockListMF.cobble_pavement_stair, getStairDirection(reverse()), x, 0, -1);
        }
    }

    protected void buildNext(int width_span, int depth, int height) {
        if (lengthId > 1 && rand.nextInt(3) == 0 && yCoord >= minLevel) {
            mapStructure(0, -depth, depth, StructureGenDSStairs.class);
        } else {
            tryPlaceHall(0, -depth, depth, direction);
        }
    }

    protected void tryPlaceHall(int x, int y, int z, int d) {
        Class extension = getRandomExtension();
        if (extension != null) {
            mapStructure(x, y, z, d, extension);
        }
    }

    protected int getHeight() {
        return 4;
    }

    protected int getDepthSpan() {
        return 9;
    }

    protected int getWidthSpan() {
        return 3;
    }

    protected Class<? extends StructureModuleMF> getRandomExtension() {
        if (rand.nextInt(20) == 0 && this.yCoord > 24) {
            return StructureGenDSStairs.class;
        }
        if (lengthId == 1) {
            return StructureGenDSRoom.class;
        }
        if (deviationCount > 0 && rand.nextInt(4) == 0) {
            return StructureGenDSIntersection.class;
        }
        return StructureGenDSHall.class;
    }

    protected Object[] getTrim(int radius, int depth, int x, int z) {
        if (x == -radius || x == radius) {
            return null;
        }

        if (x == -(radius - 1) || x == (radius - 1)) {
            if (z == Math.floor((float) depth / 2)) {
                return new Object[]{BlockListMF.reinforced_stone_framed, false};
            }
            return new Object[]{BlockListMF.reinforced_stone, false};
        }
        return null;
    }

    protected Object[] getCeiling(int radius, int depth, int x, int z) {
        return x == 0 ? new Object[]{BlockListMF.reinforced_stone, false}
                : new Object[]{BlockListMF.reinforced_stone_bricks, true};
    }

    protected Object[] getFloor(int radius, int depth, int x, int z) {
        if (x >= -1 && x <= 1) {
            if (z >= depth - 1) {
                return new Object[]{BlockListMF.cobble_pavement, 0};
            }
            return new Object[]{BlockListMF.cobble_pavement_stair, Integer.valueOf(getStairDirection(reverse()))};
        }
        if (x == -radius || x == radius || z == depth || z == 0) {
            return new Object[]{BlockListMF.reinforced_stone, Integer.valueOf(0)};
        }
        if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
            return new Object[]{BlockListMF.reinforced_stone, Integer.valueOf(0)};
        }
        return new Object[]{BlockListMF.cobble_pavement, 0};
    }

    protected Object[] getWalls(int radius, int height, int depth, int x, int y, int z) {
        if (x != -radius && x != radius && z == 0) {
            return new Object[]{Blocks.air, false};
        }
        if (x == -radius || x == radius || z == depth) {
            return y == height / 2 ? new Object[]{BlockListMF.reinforced_stone, "Hall"}
                    : new Object[]{BlockListMF.reinforced_stone_bricks, true};
        }
        return new Object[]{Blocks.air, false};
    }

    public StructureModuleMF setLoot(String loot) {
        this.lootType = loot;
        return this;
    }
}
