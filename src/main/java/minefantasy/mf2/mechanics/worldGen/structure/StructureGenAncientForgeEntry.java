package minefantasy.mf2.mechanics.worldGen.structure;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class StructureGenAncientForgeEntry extends StructureModuleMF {

    public StructureGenAncientForgeEntry(World world, int x, int y, int z, int d) {
        super(world, x, y, z, d);
    }

    public StructureGenAncientForgeEntry(World world, StructureCoordinates position) {
        super(world, position);
    }

    @Override
    public void generate() {
        // Stairway
        int length = 8 + rand.nextInt(8);
        if (yCoord > 48)
            length += (yCoord - 48);
        for (int d = 1; d <= length; d++) {
            for (int w = 0; w < 3; w++) {
                for (int h = 0; h < 6; h++) {
                    if (d != 1 || h != 5)
                        placeBlock(Blocks.stonebrick, StructureGenAncientForge.getRandomMetadata(rand), -1 + w,
                                -d + h - 1, d + 1);
                }
            }

            for (int h = 0; h < 4; h++)
                placeBlock(Blocks.air, 0, 0, -d + h, d + 1);
            placeBlock(Blocks.stone_brick_stairs, getStairDirection(reverse()), 0, -d, d + 1);
        }
        placeBlock(Blocks.stonebrick, StructureGenAncientForge.getRandomMetadata(rand), 0, 2, 2);
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 5; y++) {
                placeBlock(Blocks.stonebrick, StructureGenAncientForge.getRandomMetadata(rand), x, y - 3, 1);
            }
        }
        placeBlock(Blocks.air, 0, 0, 0, 1);
        placeBlock(Blocks.air, 0, 0, 1, 1);

        int[] coord = offsetPos(0, -length + 1, length + 1, direction);
        int x = coord[0];
        int y = coord[1];
        int z = coord[2];

        StructureModuleMF structure = new StructureGenAncientForge(worldObj, x, y, z, direction);
        structure.generate();
    }
}
