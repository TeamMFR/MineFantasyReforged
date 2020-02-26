package minefantasy.mfr.packet;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.block.tile.TileEntityForge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class ForgePacket extends PacketMF {
    public static final String packetName = "MF2_ForgePacket";
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
    public void process(ByteBuf packet, EntityPlayer player) {
        coords = new BlockPos(packet.readInt(), packet.readInt(), packet.readInt());
        fuels[0] = packet.readFloat();
        fuels[1] = packet.readFloat();
        temps[0] = packet.readFloat();
        temps[1] = packet.readFloat();
        temps[2] = packet.readFloat();
        workableState = packet.readInt();

        TileEntity entity = player.world.getTileEntity(coords);

        if (entity != null && entity instanceof TileEntityForge) {
            TileEntityForge tile = (TileEntityForge) entity;
            tile.fuel = fuels[0];
            tile.maxFuel = fuels[1];
            tile.temperature = temps[0];
            tile.fuelTemperature = temps[1];
            tile.exactTemperature = (int) temps[2];
            tile.workableState = workableState;
        }
    }

    @Override
    public String getChannel() {
        return packetName;
    }

    @Override
    public void write(ByteBuf packet) {
        packet.writeLong(coords.toLong());
        packet.writeFloat(fuels[0]);
        packet.writeFloat(fuels[1]);
        packet.writeFloat(temps[0]);
        packet.writeFloat(temps[1]);
        packet.writeFloat(temps[2]);
        packet.writeInt(workableState);
    }
}