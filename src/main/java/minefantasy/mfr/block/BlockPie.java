package minefantasy.mfr.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockPie extends BlockCakeMF {

    protected static final AxisAlignedBB[] PIE_AABB = new AxisAlignedBB[] {
            new AxisAlignedBB(1D / 16D, 0.0D, 1D / 16D, 15D / 16D, 6D / 16D, 15D / 16D),  //0
            new AxisAlignedBB(1D / 16D, 0.0D, 1D / 16D, 15D / 16D, 6D / 16D, 13.5D / 16D),//1
            new AxisAlignedBB(1D / 16D, 0.0D, 1D / 16D, 15D / 16D, 6D / 16D, 12D / 16D),  //2
            new AxisAlignedBB(1D / 16D, 0.0D, 1D / 16D, 15D / 16D, 6D / 16D, 10.5D / 16D),//3
            new AxisAlignedBB(1D / 16D, 0.0D, 1D / 16D, 15D / 16D, 6D / 16D, 9D / 16D),   //4
            new AxisAlignedBB(1D / 16D, 0.0D, 1D / 16D, 15D / 16D, 6D / 16D, 7.5D / 16D), //5
            new AxisAlignedBB(1D / 16D, 0.0D, 1D / 16D, 15D / 16D, 6D / 16D, 6D / 16D),   //6
            new AxisAlignedBB(1D / 16D, 0.0D, 1D / 16D, 15D / 16D, 6D / 16D, 4.5D / 16D), //7
            new AxisAlignedBB(1D / 16D, 0.0D, 1D / 16D, 15D / 16D, 6D / 16D, 3D / 16D)    //8
    };

    public BlockPie(String name, Item slice) {
        super(name, slice);
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return PIE_AABB[((Integer)state.getValue(BITES)).intValue()];
    }

}
