package minefantasy.mf2.mechanics.worldGen.structure.dwarven;

import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.entity.mob.EntityMinotaur;
import minefantasy.mf2.entity.mob.MinotaurBreed;
import minefantasy.mf2.mechanics.worldGen.structure.StructureGenAncientForge;
import minefantasy.mf2.mechanics.worldGen.structure.StructureModuleMF;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;

public class StructureGenDSEntry extends StructureModuleMF {
    private static Block floor = BlockListMF.cobble_pavement;
    public boolean isSurfaceBuild;
    private String lootType = ChestGenHooks.DUNGEON_CHEST;

    public StructureGenDSEntry(World world, StructureCoordinates position) {
        super(world, position);
    }

    public StructureGenDSEntry(World world, int x, int y, int z, int direction, boolean surface) {
        super(world, x, y, z, direction);
        this.isSurfaceBuild = surface;
    }

    @Override
    public void generate() {
        int environment = MinotaurBreed.getEnvironment(worldObj, xCoord, zCoord, 0);
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
                    this.buildFoundation((Block) blockarray[0], meta, x, -1, z, 32, 4, false);
                }

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
                    int meta = (Boolean) blockarray[1] ? StructureGenAncientForge.getRandomMetadata(rand) : 0;
                    placeBlock((Block) blockarray[0], meta, x, height + 2, z);
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
        for (int x = -1; x <= 1; x++) {
            for (int y = 1; y <= 3; y++) {
                placeBlock(Blocks.air, 0, x, y, 0);
            }
            placeBlock(BlockListMF.reinforced_stone_bricks, StructureGenAncientForge.getRandomMetadata(rand), x, 0, -1);
            placeBlock(BlockListMF.reinforced_stone_bricks, StructureGenAncientForge.getRandomMetadata(rand), x, 0, -2);

            placeBlock(BlockListMF.reinforced_stone, 0, x, -1, -1);
            placeBlock(BlockListMF.reinforced_stone, 0, x, -1, -2);
            placeBlock(BlockListMF.reinforced_stone, 0, x, 0, -3);
        }
        placeBlock(BlockListMF.reinforced_stone, 0, -2, 0, -1);
        placeBlock(BlockListMF.reinforced_stone, 0, -2, 0, -2);
        placeBlock(BlockListMF.reinforced_stone, 0, 2, 0, -1);
        placeBlock(BlockListMF.reinforced_stone, 0, 2, 0, -2);

        placeDoorway(-1, width_span, height, depth);
        placeDoorway(1, width_span, height, depth);
        placeBlock(BlockListMF.reinforced_stone, 0, 0, height - 1, -3);
        placeBlock(BlockListMF.reinforced_stone, 0, 0, height, -1);

        EntityMinotaur mob = new EntityMinotaur(worldObj);
        this.placeEntity(mob, 0, 1, depth / 2);
        mob.worldGenTier(MinotaurBreed.getEnvironment(subtype), 1);

        this.lengthId = -99;
        mapStructure(0, 0, depth, StructureGenDSStairs.class);

        if (isSurfaceBuild) {
            mapStructure(-width_span, 0, depth - 1, rotateRight(), StructureDSSurfaceAppendage.class);
            mapStructure(-width_span, 0, 2, rotateRight(), StructureDSSurfaceAppendage.class);

            mapStructure(width_span, 0, depth - 1, rotateLeft(), StructureDSSurfaceAppendage.class);
            mapStructure(width_span, 0, 2, rotateLeft(), StructureDSSurfaceAppendage.class);
        }
    }

    private void placeDoorway(int mod, int width_span, int height, int depth) {
        int h = height;
        placeBlock(BlockListMF.reinforced_stone, 0, 3 * mod, 1, -1);
        placeBlock(BlockListMF.reinforced_stone, 0, 3 * mod, 1, -2);
        for (int y = 1; y < h; y++) {
            placeBlock(BlockListMF.reinforced_stone, 0, 2 * mod, y, -3);
        }
        placeBlock(BlockListMF.reinforced_stone, 0, 1 * mod, height - 1, -3);

        placeBlock(BlockListMF.reinforced_stone, 0, 2 * mod, height, -3);
        placeBlock(BlockListMF.reinforced_stone, 0, 2 * mod, height, -2);
        placeBlock(BlockListMF.reinforced_stone, 0, 1 * mod, height, -1);
        placeBlock(BlockListMF.reinforced_stone, 0, 2 * mod, height, -1);
        placeBlock(BlockListMF.reinforced_stone, 0, 3 * mod, height, -1);

        placeBlock(BlockListMF.reinforced_stone, 0, 2 * mod, height + 1, -1);
        placeBlock(BlockListMF.reinforced_stone, 0, 2 * mod, height + 2, -1);
        placeBlock(BlockListMF.reinforced_stone, 0, 2 * mod, height + 2, 0);
    }

    private Object[] getTrim(int radius, int depth, int x, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            return null;
        }
        if (x == -(radius - 1) || x == (radius - 1)) {
            if (z == (int) Math.ceil((float) depth / 2)) {
                return new Object[]{BlockListMF.reinforced_stone_framed, false};
            }
        }
        if (x == -(radius - 1) || x == (radius - 1) || z == (depth - 1) || z == 1) {
            if ((x == -(radius - 1) && (z == (depth - 1) || z == 1))
                    || (x == (radius - 1) && (z == (depth - 1) || z == 1))) {
                return new Object[]{BlockListMF.reinforced_stone_framed, false};
            }
            return new Object[]{BlockListMF.reinforced_stone, false};
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

    private Object[] getFoundation(int radius, int depth, int x, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            if (x == -radius || x == radius || x == 0) {
                if (z == depth || z == Math.ceil((float) depth / 2) || z == 0) {
                    return new Object[]{BlockListMF.reinforced_stone, false};
                }
            }
        }
        return new Object[]{BlockListMF.reinforced_stone_bricks, true};
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
}
