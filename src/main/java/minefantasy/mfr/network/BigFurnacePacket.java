package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.tile.TileEntityBigFurnace;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class BigFurnacePacket extends PacketMF {
    private BlockPos coords;
    private int fuel, progress, doorAngle;
    private TileEntity tileEntity;

    public BigFurnacePacket(TileEntityBigFurnace tile) {
        tileEntity = tile;
        coords = tile.getPos();
        fuel = tile.fuel;
        progress = tile.progress;
        //doorAngle = tile.doorAngle;
    }

    public BigFurnacePacket() {
    }

    @Override
    public void readFromStream(ByteBuf packet) {
        coords = BlockPos.fromLong(packet.readLong());
        fuel = packet.readInt();
        progress = packet.readInt();
        doorAngle = packet.readInt();
    }

    @Override
    public void writeToStream(ByteBuf packet) {
        packet.writeLong(coords.toLong());
        packet.writeInt(fuel);
        packet.writeInt(progress);
        packet.writeInt(doorAngle);
    }

    @Override
    protected void execute() {
        if (tileEntity != null && tileEntity instanceof TileEntityBigFurnace) {
            TileEntityBigFurnace tile = (TileEntityBigFurnace) tileEntity;

//            tile.fuel = fuel;
//            tile.progress = progress;
            //tile.doorAngle = doorAngle;
        }
    }
}
