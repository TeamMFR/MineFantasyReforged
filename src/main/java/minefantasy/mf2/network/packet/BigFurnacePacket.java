package minefantasy.mf2.network.packet;

import io.netty.buffer.ByteBuf;
import minefantasy.mf2.block.tileentity.TileEntityBigFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class BigFurnacePacket extends PacketMF {
    public static final String packetName = "MF2_BigfurnPkt";
    private int[] coords = new int[3];
    private int fuel, progress, burn, doorAngle;

    public BigFurnacePacket(TileEntityBigFurnace tile) {
        coords = new int[]{tile.xCoord, tile.yCoord, tile.zCoord};
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
        coords = new int[]{packet.readInt(), packet.readInt(), packet.readInt()};

        fuel = packet.readInt();
        progress = packet.readInt();
        // heat = packet.readInt();
        burn = packet.readInt();
        // justShared = packet.readInt();
        doorAngle = packet.readInt();

        TileEntity entity = player.worldObj.getTileEntity(coords[0], coords[1], coords[2]);

        if (entity != null && entity instanceof TileEntityBigFurnace) {
            TileEntityBigFurnace tile = (TileEntityBigFurnace) entity;

            tile.fuel = fuel;
            tile.progress = progress;
            // tile.heat = heat;
            // tile.justShared = justShared;
            tile.doorAngle = doorAngle;

            if (tile.getWorldObj().isRemote) {
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
        for (int a = 0; a < coords.length; a++) {
            packet.writeInt(coords[a]);
        }
        packet.writeInt(fuel);
        packet.writeInt(progress);
        // packet.writeInt(heat);
        packet.writeInt(burn);
        // packet.writeInt(justShared);
        packet.writeInt(doorAngle);
    }
}
