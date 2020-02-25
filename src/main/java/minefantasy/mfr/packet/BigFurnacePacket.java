package minefantasy.mfr.packet;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.block.tile.TileEntityBigFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class BigFurnacePacket extends PacketMF {
    public static final String packetName = "MF2_BigfurnPkt";
    private BlockPos coords;
    private int fuel, progress, burn, doorAngle;

    public BigFurnacePacket(TileEntityBigFurnace tile) {
        coords = tile.getPos();
        fuel = tile.fuel;
        progress = tile.progress;
        // heat = (int)tile.heat;
        burn = tile.isBurning() ? 1 : 0;
        // justShared = tile.justShared;
        doorAngle = tile.doorAngle;
    }

    public BigFurnacePacket() {
    }

    @Override
    public void process(ByteBuf packet, EntityPlayer player) {
        coords = new BlockPos(packet.readInt(), packet.readInt(), packet.readInt());

        fuel = packet.readInt();
        progress = packet.readInt();
        // heat = packet.readInt();
        burn = packet.readInt();
        // justShared = packet.readInt();
        doorAngle = packet.readInt();

        TileEntity entity = player.world.getTileEntity(coords);

        if (entity != null && entity instanceof TileEntityBigFurnace) {
            TileEntityBigFurnace tile = (TileEntityBigFurnace) entity;

            tile.fuel = fuel;
            tile.progress = progress;
            // tile.heat = heat;
            // tile.justShared = justShared;
            tile.doorAngle = doorAngle;

            if (tile.getWorld().isRemote) {
                tile.isBurningClient = (burn == 1);
            }
        }
    }

    @Override
    public String getChannel() {
        return packetName;
    }

    @Override
    public void write(ByteBuf packet) {
        packet.writeLong(coords.toLong());
        packet.writeInt(fuel);
        packet.writeInt(progress);
        // packet.writeInt(heat);
        packet.writeInt(burn);
        // packet.writeInt(justShared);
        packet.writeInt(doorAngle);
    }
}
