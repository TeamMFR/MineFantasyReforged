package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.tile.TileEntityForge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

public class ForgePacket extends PacketMF {
    private BlockPos coords;
    private float[] fuels = new float[2];
    private float[] temps = new float[3];
    private int workableState;

    public ForgePacket(TileEntityForge tile) {
        coords = tile.getPos();
        fuels = new float[]{tile.fuel, tile.maxFuel};
        if (fuels[0] > fuels[1]) {
            fuels[0] = fuels[1];
        }
        temps = new float[]{tile.temperature, tile.fuelTemperature, tile.getHeat()};
        workableState = tile.getWorkableState();
    }

    public ForgePacket() {
    }

    @Override
    protected void writeToStream(ByteBuf packet) {
        packet.writeLong(coords.toLong());
        packet.writeFloat(fuels[0]);
        packet.writeFloat(fuels[1]);
        packet.writeFloat(temps[0]);
        packet.writeFloat(temps[1]);
        packet.writeFloat(temps[2]);
        packet.writeInt(workableState);
    }

    @Override
    protected void readFromStream(ByteBuf packet) {
        coords = BlockPos.fromLong(packet.readLong());
        fuels[0] = packet.readFloat();
        fuels[1] = packet.readFloat();
        temps[0] = packet.readFloat();
        temps[1] = packet.readFloat();
        temps[2] = packet.readFloat();
        workableState = packet.readInt();
    }

    @Override
    protected void execute(EntityPlayer player) {
        TileEntityForge tile = (TileEntityForge) player.world.getTileEntity(coords);
        if (tile != null) {
            tile.fuel = fuels[0];
            tile.maxFuel = fuels[1];
            tile.temperature = temps[0];
            tile.fuelTemperature = temps[1];
            tile.exactTemperature = (int) temps[2];
            tile.workableState = workableState;
        }
    }
}