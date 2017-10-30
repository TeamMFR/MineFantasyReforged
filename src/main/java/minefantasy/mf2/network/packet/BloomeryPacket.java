package minefantasy.mf2.network.packet;

import io.netty.buffer.ByteBuf;
import minefantasy.mf2.block.tileentity.TileEntityBloomery;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class BloomeryPacket extends PacketMF {
    public static final String packetName = "MF2_BloomeryPacket";
    private int[] coords = new int[3];
    private boolean renderBloom, isActive;

    public BloomeryPacket(TileEntityBloomery tile) {
        coords = new int[]{tile.xCoord, tile.yCoord, tile.zCoord};
        renderBloom = tile.hasBloom();
        isActive = tile.isActive;
    }

    public BloomeryPacket() {
    }

    @Override
    public void process(ByteBuf packet, EntityPlayer player) {
        coords = new int[]{packet.readInt(), packet.readInt(), packet.readInt()};
        TileEntity entity = player.worldObj.getTileEntity(coords[0], coords[1], coords[2]);

        renderBloom = packet.readBoolean();
        isActive = packet.readBoolean();

        if (entity != null && entity instanceof TileEntityBloomery) {
            TileEntityBloomery tile = (TileEntityBloomery) entity;
            tile.hasBloom = renderBloom;
            tile.isActive = isActive;
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
        packet.writeBoolean(renderBloom);
        packet.writeBoolean(isActive);
    }
}
