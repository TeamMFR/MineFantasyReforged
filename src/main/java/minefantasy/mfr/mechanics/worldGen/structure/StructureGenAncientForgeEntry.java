package minefantasy.mfr.mechanics.worldGen.structure;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StructureGenAncientForgeEntry extends StructureModuleMFR {

    public StructureGenAncientForgeEntry(World world, BlockPos pos, int d) {
        super(world, pos, d);
    }

    public StructureGenAncientForgeEntry(World world, StructureCoordinates position) {
        super(world, position);
    }

    @Override
    public void generate() {
        // Stairway
        int length = 8 + rand.nextInt(8);
        if (pos.getY() > 48)
            length += (pos.getY() - 48);
        for (int d = 1; d <= length; d++) {
            for (int w = 0; w < 3; w++) {
                for (int h = 0; h < 6; h++) {
                    if (d != 1 || h != 5)
                        placeBlock(Blocks.STONEBRICK, new BlockPos(-1 + w, -d + h - 1, d + 1));
                }
            }

            for (int h = 0; h < 4; h++)
                placeBlock(Blocks.AIR, new BlockPos(0, -d + h, d + 1));
            placeBlock(Blocks.STONE_BRICK_STAIRS, new BlockPos(0, -d, d + 1));
        }
        placeBlock(Blocks.STONEBRICK, new BlockPos(0, 2, 2));
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 5; y++) {
                placeBlock(Blocks.STONEBRICK, new BlockPos(x, y - 3, 1));
            }
        }
        placeBlock(Blocks.AIR, new BlockPos(0, 0, 1));
        placeBlock(Blocks.AIR, new BlockPos(0, 1, 1));

        BlockPos coord = offsetPos(new BlockPos(0, -length + 1, length + 1), direction);

        StructureModuleMFR structure = new StructureGenAncientForge(world, coord, direction);
        structure.generate();
    }
}
