package minefantasy.mfr.block.crafting;

import minefantasy.mfr.tile.TileEntityTanningRack;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockEngineerTanner extends BlockTanningRack {

    public BlockEngineerTanner(int tier, String tex) {
        super(tier, tex);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer user, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntityTanningRack tile = getTile(world, pos);
        if (tile != null) {
            return tile.interact(user, false, facing == EnumFacing.UP);
        }
        return true;
    }
}
