package minefantasy.mf2.network.packet;

import io.netty.buffer.ByteBuf;
import minefantasy.mf2.block.tileentity.TileEntityTanningRack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class TannerPacket extends PacketMF {
    public static final String packetName = "MF2_TannerPacket";
    private int[] coords = new int[3];
    private float animation;

    public TannerPacket(TileEntityTanningRack tile) {
        coords = new int[]{tile.xCoord, tile.yCoord, tile.zCoord};
        animation = tile.acTime;
    }

    public TannerPacket() {
    }

    @Override
    public void process(ByteBuf packet, EntityPlayer player) {
        coords = new int[]{packet.readInt(), packet.readInt(), packet.readInt()};
        TileEntity entity = player.worldObj.getTileEntity(coords[0], coords[1], coords[2]);
        float newAnim = packet.readFloat();

        if (entity != null && entity instanceof TileEntityTanningRack) {
            TileEntityTanningRack tile = (TileEntityTanningRack) entity;
            tile.acTime = newAnim;
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
        packet.writeFloat(animation);
    }
}
