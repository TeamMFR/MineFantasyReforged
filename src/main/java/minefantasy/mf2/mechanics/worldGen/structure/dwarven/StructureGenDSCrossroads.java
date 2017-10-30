package minefantasy.mf2.mechanics.worldGen.structure.dwarven;

import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.config.ConfigWorldGen;
import minefantasy.mf2.entity.mob.EntityMinotaur;
import minefantasy.mf2.entity.mob.MinotaurBreed;
import minefantasy.mf2.mechanics.worldGen.structure.StructureGenAncientForge;
import minefantasy.mf2.mechanics.worldGen.structure.StructureModuleMF;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;

public class StructureGenDSCrossroads extends StructureModuleMF {
    protected String lootType = ChestGenHooks.DUNGEON_CHEST;
    protected Block floor_block = BlockListMF.cobble_pavement;

    public StructureGenDSCrossroads(World world, StructureCoordinates position) {
        super(world, position);
    }

    public StructureGenDSCrossroads(World world, int x, int y, int z, int direction) {
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
        if (lengthId == -100) {
            this.lengthId = ConfigWorldGen.DSLength;
            this.deviationCount = ConfigWorldGen.DSDeviations;
        }

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
                    blockarray = getWalls(width_span, depth, x, z);
                    if (blockarray != null) {
                        int meta = (Boolean) blockarray[1] ? StructureGenAncientForge.getRandomMetadata(rand) : 0;
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
                    int meta = 0;
                    if (blockarray[1] instanceof Integer) {
                        meta = (Integer) blockarray[1];
                    }
                    if (blockarray[1] instanceof Boolean) {
                        meta = (Boolean) blockarray[1] ? StructureGenAncientForge.getRandomMetadata(rand) : 0;
                    }
                    placeBlock((Block) blockarray[0], meta, x, height, z);
                    placeBlock(BlockListMF.reinforced_stone, meta, x, 4, z);
                    if ((Block) blockarray[0] == BlockListMF.reinforced_stone_framed) {
                        for (int h = height - 1; h > 1; h--) {
                            placeBlock(h == 5 ? Blocks.glowstone : BlockListMF.reinforced_stone, h == 2 ? 1 : 0, x, h,
                                    z);
                        }
                        placeBlock(BlockListMF.reinforced_stone_framed, 0, x, 1, z);
                    }
                }

            }
        }

        // DOORWAY
        buildDoorway(width_span, depth, height);

        generateCrossroad();
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

    protected int getHeight() {
        return 7;
    }

    protected int getDepthSpan() {
        return 19;
    }

    protected int getWidthSpan() {
        return 7;
    }

    private Object[] getTrim(int radius, int depth, int x, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            return null;
        }
        if (x == -(radius - 1) || x == (radius - 1)) {
            if (z == (int) Math.ceil((float) depth / 2) || z == (int) Math.floor((float) depth / 2)) {
                return new Object[]{BlockListMF.reinforced_stone_framed, false};
            }
        }
        if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
            if ((x == -(radius - 1) && (z == (depth - 1) || z == 1))
                    || (x == (radius - 1) && (z == (depth - 1) || z == 1))) {
                return new Object[]{BlockListMF.reinforced_stone_framed, false};
            }
            int m = 0;
            if (x == 0 || z == 5 || z == (depth - 5)) {
                m = 1;
            }
            return new Object[]{BlockListMF.reinforced_stone, m};
        }
        return null;
    }

    private Object[] getCeiling(int radius, int depth, int x, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            return null;
        }
        if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
            return new Object[]{BlockListMF.reinforced_stone, false};
        }
        return new Object[]{BlockListMF.reinforced_stone_bricks, true};
    }

    private Object[] getFloor(int radius, int depth, int x, int z) {
        if (z < 2 && x >= -1 && x <= 1) {
            return new Object[]{floor_block, false};
        }
        if (x == -radius || x == radius || z == depth || z == 0) {
            return new Object[]{BlockListMF.reinforced_stone, false};
        }
        if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
            return new Object[]{BlockListMF.reinforced_stone, false};
        }
        return new Object[]{floor_block, false};
    }

    private Object[] getWalls(int radius, int depth, int x, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            if ((x == -radius && (z == depth || z == 0)) || (x == radius && (z == depth || z == 0))) {
                return new Object[]{BlockListMF.reinforced_stone, false};
            }

            return new Object[]{BlockListMF.reinforced_stone_bricks, true};
        }
        return new Object[]{Blocks.air, false};
    }

    public StructureModuleMF setLoot(String loot) {
        this.lootType = loot;
        return this;
    }

    public void generateCrossroad() {
        int width_span = getWidthSpan();
        int depth = getDepthSpan();
        int height = getHeight();

        int zOffset = 4;

        boolean stairSide = rand.nextBoolean();

        tryPlaceCrossroadHall(0, 0, depth + 1, direction, stairSide, 0);

        tryPlaceCrossroadHall((width_span + 1), 0, zOffset, rotateLeft(), stairSide, 20);
        tryPlaceCrossroadHall((width_span + 1), 0, depth - zOffset, rotateLeft(), !stairSide, 40);

        tryPlaceCrossroadHall(-(width_span + 1), 0, zOffset, rotateRight(), stairSide, 60);
        tryPlaceCrossroadHall(-(width_span + 1), 0, depth - zOffset, rotateRight(), !stairSide, 80);

        this.placeMinotaur(0, 1, depth - 2, 1);
        this.placeMinotaur(0, 1, 2, 1);

        int w = 2, d = 3;
        for (int x = -w; x <= w; x++) {
            for (int z = -d; z <= d; z++) {
                Block block = Blocks.water;
                if (z == -d || z == d || x == -w || x == w) {
                    block = BlockListMF.reinforced_stone;
                }
                this.placeBlock(block, 0, x, 1, depth / 2 + z);
            }
        }

    }

    private void placeMinotaur(int x, int y, int z, int tier) {
        EntityMinotaur mob = new EntityMinotaur(worldObj);
        this.placeEntity(mob, x, y, z);
        mob.setSpecies(MinotaurBreed.getEnvironment(subtype));
        mob.worldGenTier(MinotaurBreed.getEnvironment(subtype), tier);
    }

    protected void tryPlaceCrossroadHall(int x, int y, int z, int d, boolean stair, int delay) {
        Class extension = stair ? StructureGenDSStairs.class : StructureGenDSHall.class;
        if (extension != null) {
            mapStructure(x, y, z, d, extension, delay);
        }
    }
}
