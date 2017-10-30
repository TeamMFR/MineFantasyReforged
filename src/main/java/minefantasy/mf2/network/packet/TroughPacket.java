package minefantasy.mf2.network.packet;

import io.netty.buffer.ByteBuf;
import minefantasy.mf2.block.tileentity.decor.TileEntityTrough;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class TroughPacket extends PacketMF {
    public static final String packetName = "MF2_TroughPacket";
    private int[] coords = new int[3];
    private int fill;

    public TroughPacket(TileEntityTrough tile) {
        coords = new int[]{tile.xCoord, tile.yCoord, tile.zCoord};
        fill = tile.fill;
    }

    public TroughPacket() {
    }

    @Override
    public void process(ByteBuf packet, EntityPlayer player) {
        coords = new int[]{packet.readInt(), packet.readInt(), packet.readInt()};
        TileEntity entity = player.worldObj.getTileEntity(coords[0], coords[1], coords[2]);
        int newFill = packet.readInt();

        if (entity != null && entity instanceof TileEntityTrough) {
            TileEntityTrough tile = (TileEntityTrough) entity;
            tile.fill = newFill;
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
        packet.writeInt(fill);
    }
}
