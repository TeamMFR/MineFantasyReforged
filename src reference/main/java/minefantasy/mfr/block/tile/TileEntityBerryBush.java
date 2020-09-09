package minefantasy.mfr.tile;

import minefantasy.mfr.block.food.BlockBerryBush;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class TileEntityBerryBush extends TileEntity implements ITickable {
    private long nextGrowbackTime;
    private Random rand = new Random();

    @Override
    public void update() {
        if (!world.isRemote && world.getTotalWorldTime() >= nextGrowbackTime) {// Regrow
            world.setBlockState(pos, BlockBerryBush.getStateById(0), 2);
        }
    }

    public void harvestBerries() {
        double[] days = new double[]{2D, 7D};
        double modifier = rand.nextDouble();
        nextGrowbackTime = world.getTotalWorldTime() + (int) (24000 * modifier);
        world.setBlockState(pos, BlockBerryBush.getStateById(1), 2);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setLong("nextGrowbackTime", nextGrowbackTime);
        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        nextGrowbackTime = nbt.getLong("nextGrowbackTime");
    }
}
