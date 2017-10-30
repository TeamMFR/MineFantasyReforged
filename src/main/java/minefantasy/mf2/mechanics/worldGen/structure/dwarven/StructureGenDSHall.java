package minefantasy.mf2.mechanics.worldGen.structure.dwarven;

import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.mechanics.worldGen.structure.StructureGenAncientForge;
import minefantasy.mf2.mechanics.worldGen.structure.StructureModuleMF;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;

import java.util.Random;

public class StructureGenDSHall extends StructureModuleMF {
    protected static Block floor = BlockListMF.cobble_pavement;
    protected String lootType = ChestGenHooks.DUNGEON_CHEST;

    public StructureGenDSHall(World world, StructureCoordinates position) {
        super(world, position);
    }

    public StructureGenDSHall(World world, int x, int y, int z, int direction) {
        super(world, x, y, z, direction);
    }

    public static int getRandomEngravedWall(Random rand) {
        return 2 + rand.nextInt(3);
    }

    @Override
    public boolean canGenerate() {
        int width_span = getWidthSpan();
        int depth = getDepthSpan();
        int height = getHeight();
        int empty_spaces = 0;
        int filledSpaces = 0, emptySpaces = 0;
        for (int x = -width_span; x <= width_span; x++) {
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
        int width_span = getWidthSpan();
        int depth = getDepthSpan();
        int height = getHeight();
        for (int x = -width_span; x <= width_span; x++) {
            for (int z = 0; z <= depth; z++) {
                Object[] blockarray;

                // FLOOR
                blockarray = getFloor(width_span, depth, x, z);
                if (blockarray != null) {
                    int meta = (Boolean) blockarray[1] ? StructureGenAncientForge.getRandomMetadata(rand) : 0;
                    placeBlock((Block) blockarray[0], meta, x, 0, z);
                }
                // WALLS
                for (int y = 1; y <= height + 1; y++) {
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
                            meta = (String) blockarray[1] == "Hall" ? getRandomEngravedWall(rand) : 0;
                        }
                        placeBlock((Block) blockarray[0], meta, x, y, z);
                    }
                }
                // CEILING
                blockarray = getCeiling(width_span, depth, x, z);
                if (blockarray != null) {
                    int meta = (Boolean) blockarray[1] ? StructureGenAncientForge.getRandomMetadata(rand) : 0;
                    placeBlock((Block) blockarray[0], meta, x, height + 1, z);
                }

                // TRIM
                blockarray = getTrim(width_span, depth, x, z);
                if (blockarray != null) {
                    int meta = (Boolean) blockarray[1] ? StructureGenAncientForge.getRandomMetadata(rand) : 0;
                    placeBlock((Block) blockarray[0], meta, x, height, z);
                    if ((Block) blockarray[0] == BlockListMF.reinforced_stone_framed) {
                        for (int h = height - 1; h > 1; h--) {
                            placeBlock(BlockListMF.reinforced_stone, h == 2 ? 1 : 0, x, h, z);
                        }
                        placeBlock(BlockListMF.reinforced_stone_framed, 0, x, 1, z);
                    }
                }

            }
        }

        // DOORWAY
        buildDoorway(width_span, depth, height);

        if (lengthId > 0) {
            buildNext(width_span, depth, height);
        } else {
            mapStructure(0, 0, depth, StructureGenDSRoom.class);
        }
        if (rand.nextInt(3) != 0) {
            tryPlaceMinorRoom((width_span), 0, (int) Math.floor((float) depth / 2), rotateLeft());
        }
        if (rand.nextInt(3) != 0) {
            tryPlaceMinorRoom(-(width_span), 0, (int) Math.floor((float) depth / 2), rotateRight());
        }
        if (this instanceof StructureGenDSIntersection || this.lengthId % 2 == 0) {
            placeBlock(Blocks.glowstone, 0, 0, height + 1, depth / 2);
        }
    }

    protected void buildDoorway(int width_span, int depth, int height) {
        for (int x = -1; x <= 1; x++) {
            for (int y = 1; y <= 3; y++) {
                for (int z = 0; z >= -1; z--) {
                    placeBlock(Blocks.air, 0, x, y, z);
                }
            }
        }
    }

    protected void buildNext(int width_span, int depth, int height) {
        tryPlaceHall(0, 0, depth, direction);
    }

    protected void tryPlaceHall(int x, int y, int z, int d) {
        Class extension = getRandomExtension();
        if (extension != null) {
            mapStructure(x, y, z, d, extension);
        }
    }

    protected void tryPlaceMinorRoom(int x, int y, int z, int d) {
        Class extension = getRandomMinorRoom();
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

    protected Class<? extends StructureModuleMF> getRandomMinorRoom() {
        return StructureGenDSRoomSml.class;
    }

    protected Class<? extends StructureModuleMF> getRandomExtension() {
        if (lengthId == 1) {
            return StructureGenDSRoom.class;
        }
        if (deviationCount > 0 && rand.nextInt(4) == 0) {
            return StructureGenDSIntersection.class;
        }
        if (rand.nextInt(16) == 0 && this.yCoord >= StructureGenDSStairs.minLevel) {
            return StructureGenDSStairs.class;
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
            return new Object[]{floor, false};
        }
        if (x == -radius || x == radius || z == depth || z == 0) {
            return new Object[]{BlockListMF.reinforced_stone, false};
        }
        if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
            return new Object[]{BlockListMF.reinforced_stone, false};
        }
        return new Object[]{floor, false};
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
