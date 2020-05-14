package minefantasy.mfr.mechanics.worldGen.structure.dwarven;

import minefantasy.mfr.init.BlockListMFR;
import minefantasy.mfr.init.LootRegistryMFR;
import minefantasy.mfr.mechanics.worldGen.structure.StructureModuleMFR;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StructureDSSurfaceAppendage extends StructureModuleMFR {
    private String type;

    public StructureDSSurfaceAppendage(World world, StructureCoordinates position) {
        super(world, position);
        type = randomiseType();
    }

    private String randomiseType() {
        return rand.nextInt(3) == 0 ? "Tower" : "Room";
    }

    protected int getHeight() {
        return type == "Tower" ? 12 : 5;
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
                    BlockPos pos = new BlockPos(x,y,z);
                    IBlockState state = this.getBlock(pos);
                    if (!allowBuildOverBlock(state) || this.isUnbreakable(pos, direction)) {
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

    private boolean allowBuildOverBlock(IBlockState block) {
        if (block == BlockListMFR.REINFORCED_STONE_BRICKS || block == BlockListMFR.REINFORCED_STONE) {
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
                BlockPos pos = new BlockPos(x,0,z);
                if (blockarray != null) {
                    placeBlock((Block) blockarray[0], pos);
                }
                placeBlock( BlockListMFR.REINFORCED_STONE_BRICKS, pos.add(0,-1,0));
                if (z < depth) {
                    placeBlock( BlockListMFR.REINFORCED_STONE_BRICKS, pos.add(0,-2,0));
                }
                if (z < depth - 1) {
                    placeBlock( BlockListMFR.REINFORCED_STONE_BRICKS, pos.add(0,-3,0));
                }
                // WALLS
                for (int y = 0; y <= height; y++) {
                    BlockPos wallPos = new BlockPos(x,y,z);
                    blockarray = getWalls(width, depth, height, wallPos);
                    if (blockarray != null && this.allowBuildOverBlock(getBlock(wallPos))) {
                        placeBlock((Block) blockarray[0], wallPos);
                    }
                }
                // CEILING
                blockarray = getCeiling(width, depth, x, z);
                BlockPos ceilingPos = new BlockPos(x, height ,z);
                if (blockarray != null) {
                    placeBlock((Block) blockarray[0], ceilingPos);
                }
                // BATTLEMENT
                if (type == "Tower") {
                    blockarray = getRoofDecor(width, depth, x, z);
                    if (blockarray != null) {
                        placeBlock((Block) blockarray[0], ceilingPos.add(0,1,0));
                    }
                }
            }
        }
        placeBlock(Blocks.AIR, new BlockPos(0, 1, 0));
        placeBlock(Blocks.AIR, new BlockPos(0, 2, 0));

        placeBlock(BlockListMFR.REINFORCED_STONE_FRAMED, new BlockPos(-1, 1, -1) );
        placeBlock(BlockListMFR.REINFORCED_STONE,  new BlockPos(-1, 2, -1) );
        placeBlock(BlockListMFR.REINFORCED_STONE_FRAMED_IRON,  new BlockPos(-1, 3, -1) );

        placeBlock(BlockListMFR.REINFORCED_STONE_FRAMED,  new BlockPos(1, 1, -1) );
        placeBlock(BlockListMFR.REINFORCED_STONE,  new BlockPos(1, 2, -1) );
        placeBlock(BlockListMFR.REINFORCED_STONE_FRAMED_IRON, new BlockPos(1, 3, -1) );

        placeBlock(BlockListMFR.REINFORCED_STONE,  new BlockPos(0, 3, -1) );
        placeBlock(Blocks.AIR,  new BlockPos(0, 1, -1) );
        placeBlock(Blocks.AIR,  new BlockPos(0, 2, -1) );

        if (type == "Tower") {
            decorateTower(width, depth, height);
        } else {
            buildHomeFurnishings(width, depth, height);
        }
    }

    private Object[] getFloor(int width, int depth, int x, int z) {
        if (x <= -(width - 1) || x >= (width - 1) || z <= 1 || z >= depth - 1) {
            return new Object[]{BlockListMFR.REINFORCED_STONE, 0};
        }
        return new Object[]{BlockListMFR.COBBLE_PAVEMENT, 0};
    }

    private Object[] getCeiling(int width, int depth, int x, int z) {
        if (x == -width || x == width || z == 0 || z == depth) {
            return new Object[]{BlockListMFR.REINFORCED_STONE, false};
        }
        return new Object[]{BlockListMFR.REINFORCED_STONE_BRICKS, true};
    }

    private Object[] getRoofDecor(int width, int depth, int x, int z) {
        if (x == -width || x == width || z == 0 || z == depth) {
            if (x == 0 || x == -width || x == width) {
                if (z == 0 || z == depth / 2 || z == depth) {
                    return new Object[]{BlockListMFR.REINFORCED_STONE, false};
                }
            }
            return new Object[]{BlockListMFR.BRONZE_BARS, false};
        }
        return null;
    }

    private Object[] getWalls(int radius, int depth, int height, BlockPos pos) {
        if (pos.getX() == -radius || pos.getX() == radius || pos.getZ() == depth || pos.getZ() == 0) {
            if ((pos.getX() == -radius && (pos.getZ() == depth || pos.getZ() == 0)) || (pos.getX() == radius && (pos.getZ() == depth || pos.getZ() == 0))) {
                return new Object[]{BlockListMFR.REINFORCED_STONE, false};
            }

            return new Object[]{BlockListMFR.REINFORCED_STONE_BRICKS, true};
        }
        if (type == "Tower" && pos.getY() == (height / 2)) {
            return new Object[]{BlockListMFR.REINFORCED_STONE_BRICKS, true};
        }
        if (pos.getY() <= 0) {
            return null;
        }
        return new Object[]{Blocks.AIR, false};
    }

    private void decorateTower(int width, int depth, int height) {
        for (int y = 1; y <= height; y++) {
            if (y >= 2 && y <= (height - 2) && (y < (height / 2) - 1 || y > (height / 2) + 1)) {
                this.placeBlock(BlockListMFR.BRONZE_BARS, new BlockPos(-width, y, depth / 2) );
                this.placeBlock(BlockListMFR.BRONZE_BARS, new BlockPos(width, y, depth / 2) );
                this.placeBlock(BlockListMFR.BRONZE_BARS, new BlockPos(0, y, depth) );
                if (y > (height / 2) + 1) {
                    this.placeBlock(BlockListMFR.BRONZE_BARS, new BlockPos(0, y, 0) );
                }
            }
            this.placeBlock(Blocks.LADDER, new BlockPos( -1, y, 1));
        }
        this.placeBlock(Blocks.GLOWSTONE, new BlockPos(0, height / 2, depth / 2) );

    }

    private void buildHomeFurnishings(int width, int depth, int height) {
        for (int x = width - 1; x >= (width - 4); x--) {
            placeBlock(BlockListMFR.REINFORCED_STONE_BRICKS, new BlockPos(x, 1, 3) );
            placeBlock(BlockListMFR.REINFORCED_STONE_BRICKS, new BlockPos(x, 2, 3) );
            placeBlock(Blocks.STONE_SLAB, new BlockPos(x, 3, 3) );
        }
        placeBlock(Blocks.STONE_SLAB, new BlockPos(width - 1, 1, depth - 1) );
        placeBlock(Blocks.STONE_SLAB, new BlockPos(width - 2, 1, depth - 1) );

        placeChest(new BlockPos(width - 3, 1, depth - 1), LootRegistryMFR.DWARVEN_HOME);

        for (int x = -(width - 1); x < width; x++) {
            for (int z = 1; z < depth; z++) {
                if (x == width - 1 || x == -(width - 1) || z == 1 || z == (depth - 1)) {
                    placeBlock(BlockListMFR.REINFORCED_STONE, new BlockPos( x, height + 1, z));
                }
            }
        }
    }

    private void placeChest(BlockPos pos, ResourceLocation loot) {
        placeBlock(Blocks.CHEST, pos);
        TileEntityChest tile = (TileEntityChest) getTileEntity(pos, direction);

        if (tile != null) {
            tile.setLootTable(loot, 2 + rand.nextInt(3) );
        }
    }
}
