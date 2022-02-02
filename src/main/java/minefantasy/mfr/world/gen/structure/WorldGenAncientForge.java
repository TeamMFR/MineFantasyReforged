package minefantasy.mfr.world.gen.structure;

import minefantasy.mfr.world.gen.structure.pieces.StructureGenAncientForgeEntry;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenAncientForge extends WorldGenStructureBase {
    public WorldGenAncientForge() {
    }

    @Override
    protected StructureModuleMFR getStartPiece(World world, BlockPos pos, EnumFacing facing, Random random) {
        return new StructureGenAncientForgeEntry(world, pos, facing, random);
    }

    @Override
    protected boolean isBlockAcceptableOrigin(World world, BlockPos pos) {
        return world.getBlockState(pos).getMaterial().isSolid() && isValidGround(world, pos);
    }

    @Override
    protected boolean canStructureBuild(StructureModuleMFR piece) {
        // SEARCH FOR CLIFF
         for (int x = -1; x <= 1; x++) {
            for (int y = 0; y < 3; y++) {
                for (int z = 1; z <= 2; z++) {
                    BlockPos pos = piece.offsetPos(x, y, z, piece.facing);
                    Material material = piece.world.getBlockState(pos).getMaterial();

                    if (!material.isOpaque()) {
                        return false;
                    }
                    if (!material.isSolid()) {
                        return false;
                    }
                }
                for (int z = 0; z > -2; z--) {
                    IBlockState state = piece.world.getBlockState(piece.offsetPos(x, y, z, piece.facing));
                    if (state.getMaterial().isSolid()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    protected boolean isDirectionRandom() {
        return false;
    }

    @Override
    protected int[] getYGenBounds(World world) {
        return new int[]{67, 255};
    }
}
