package minefantasy.mfr.world.gen.structure.pieces;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.world.gen.structure.StructureModuleMFR;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class StructureGenAncientForgeEntry extends StructureModuleMFR {

    public StructureGenAncientForgeEntry(World world, BlockPos pos, EnumFacing facing, Random random) {
        super(world, pos, facing, random);
    }

    @Override
    public void generate() {
        // Stairway
        int length = 8 + random.nextInt(8);
        if (pos.getY() > 48)
            length += (pos.getY() - 48);
        for (int d = 1; d <= length; d++) {
            for (int w = 0; w < 3; w++) {
                for (int h = 0; h < 7; h++) {
                    if (d != 1 || h != 6){
                        placeRandomStoneVariantBlock(world, -1 + w, -d + h - 1, d + 1, random);
                    }
                }
            }

            for (int h = 0; h < 5; h++){
                placeBlock(world,Blocks.AIR, 0, -d + h, d + 1);
            }
            placeStairBlock(world, Blocks.STONE_BRICK_STAIRS, 0, -d, d + 1, facing, facing);
        }

        placeRandomStoneVariantBlock(world, 0, 3, 2, random);
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 6; y++) {
                placeRandomStoneVariantBlock(world, x, y - 3, 1, random);
            }
        }

        //Doorway
        placeBlock(world, Blocks.AIR, 0,0,1);
        placeBlock(world, Blocks.AIR, 0, 1, 1);
        placeBlock(world, Blocks.AIR, 0, 2, 1);

        MineFantasyReborn.LOG.error("Placed Ancient Forge at: " + pos);

        StructureModuleMFR structure = new StructureGenAncientForge(world, offsetPos(0, -length, length + 1, facing), facing, random);
        structure.generate();
    }
}
