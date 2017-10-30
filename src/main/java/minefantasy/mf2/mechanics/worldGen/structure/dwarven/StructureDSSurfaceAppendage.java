package minefantasy.mf2.mechanics.worldGen.structure.dwarven;

import minefantasy.mf2.block.decor.BlockRack;
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

public class StructureDSSurfaceAppendage extends StructureModuleMF {
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
        return ((float) emptySpaces / (float) filledSpaces) > 1.0F;// at least 50% empty
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
                placeBlock(BlockListMF.reinforced_stone_bricks, StructureGenAncientForge.getRandomMetadata(rand), x, -1,
                        z);
                if (z < depth) {
                    placeBlock(BlockListMF.reinforced_stone_bricks, StructureGenAncientForge.getRandomMetadata(rand), x,
                            -2, z);
                }
                if (z < depth - 1) {
                    placeBlock(BlockListMF.reinforced_stone_bricks, StructureGenAncientForge.getRandomMetadata(rand), x,
                            -3, z);
                }
                // WALLS
                for (int y = 0; y <= height; y++) {
                    blockarray = getWalls(width, depth, height, x, y, z);
                    if (blockarray != null && this.allowBuildOverBlock(getBlock(x, y, z))) {
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
                // BATTLEMENT
                if (type == "Tower") {
                    blockarray = getRoofDecor(width, depth, x, z);
                    if (blockarray != null) {
                        int meta = (Boolean) blockarray[1] ? StructureGenAncientForge.getRandomMetadata(rand) : 0;
                        placeBlock((Block) blockarray[0], meta, x, height + 1, z);
                    }
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

        if (type == "Tower") {
            decorateTower(width, depth, height);
        } else {
            buildHomeFurnishings(width, depth, height);
        }
    }

    private Object[] getFloor(int width, int depth, int x, int z) {
        if (x <= -(width - 1) || x >= (width - 1) || z <= 1 || z >= depth - 1) {
            return new Object[]{BlockListMF.reinforced_stone, 0};
        }
        return new Object[]{BlockListMF.cobble_pavement, 0};
    }

    private Object[] getCeiling(int width, int depth, int x, int z) {
        if (x == -width || x == width || z == 0 || z == depth) {
            return new Object[]{BlockListMF.reinforced_stone, false};
        }
        return new Object[]{BlockListMF.reinforced_stone_bricks, true};
    }

    private Object[] getRoofDecor(int width, int depth, int x, int z) {
        if (x == -width || x == width || z == 0 || z == depth) {
            if (x == 0 || x == -width || x == width) {
                if (z == 0 || z == depth / 2 || z == depth) {
                    return new Object[]{BlockListMF.reinforced_stone, false};
                }
            }
            return new Object[]{BlockListMF.bars[0], false};
        }
        return null;
    }

    private Object[] getWalls(int radius, int depth, int height, int x, int y, int z) {
        if (x == -radius || x == radius || z == depth || z == 0) {
            if ((x == -radius && (z == depth || z == 0)) || (x == radius && (z == depth || z == 0))) {
                return new Object[]{BlockListMF.reinforced_stone, false};
            }

            return new Object[]{BlockListMF.reinforced_stone_bricks, true};
        }
        if (type == "Tower" && y == (height / 2)) {
            return new Object[]{BlockListMF.reinforced_stone_bricks, true};
        }
        if (y <= 0) {
            return null;
        }
        return new Object[]{Blocks.air, false};
    }

    private void decorateTower(int width, int depth, int height) {
        for (int y = 1; y <= height; y++) {
            if (y >= 2 && y <= (height - 2) && (y < (height / 2) - 1 || y > (height / 2) + 1)) {
                this.placeBlock(BlockListMF.bars[0], 0, -width, y, depth / 2);
                this.placeBlock(BlockListMF.bars[0], 0, width, y, depth / 2);
                this.placeBlock(BlockListMF.bars[0], 0, 0, y, depth);
                if (y > (height / 2) + 1) {
                    this.placeBlock(BlockListMF.bars[0], 0, 0, y, 0);
                }
            }
            this.placeBlock(Blocks.ladder, BlockRack.getDirection(direction), -1, y, 1);
        }
        this.placeBlock(Blocks.glowstone, 0, 0, height / 2, depth / 2);

    }

    private void buildHomeFurnishings(int width, int depth, int height) {
        for (int x = width - 1; x >= (width - 4); x--) {
            placeBlock(BlockListMF.reinforced_stone_bricks, StructureGenAncientForge.getRandomMetadata(rand), x, 1, 3);
            placeBlock(BlockListMF.reinforced_stone_bricks, StructureGenAncientForge.getRandomMetadata(rand), x, 2, 3);
            placeBlock(Blocks.stone_slab, 0, x, 3, 3);
        }
        placeBlock(Blocks.stone_slab, 0, width - 1, 1, depth - 1);
        placeBlock(Blocks.stone_slab, 0, width - 2, 1, depth - 1);

        placeChest(width - 3, 1, depth - 1, rotateLeft(), LootTypes.DWARVEN_HOME);

        for (int x = -(width - 1); x < width; x++) {
            for (int z = 1; z < depth; z++) {
                if (x == width - 1 || x == -(width - 1) || z == 1 || z == (depth - 1)) {
                    placeBlock(BlockListMF.reinforced_stone, 0, x, height + 1, z);
                }
            }
        }
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
