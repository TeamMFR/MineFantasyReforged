package minefantasy.mfr.packet;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.block.tile.TileEntityBloomery;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class BloomeryPacket extends PacketMF {
    public static final String packetName = "MF2_BloomeryPacket";
    private BlockPos coords;
    private boolean renderBloom, isActive;

    public BloomeryPacket(TileEntityBloomery tile) {
        coords = tile.getPos();
        renderBloom = tile.hasBloom();
        isActive = tile.isActive;
    }

    public BloomeryPacket() {
    }

    @Override
    public void process(ByteBuf packet, EntityPlayer player) {
        coords = new BlockPos(packet.readInt(), packet.readInt(), packet.readInt());
        TileEntity entity = player.world.getTileEntity(coords);

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
        packet.writeLong(coords.toLong());
        packet.writeBoolean(renderBloom);
        packet.writeBoolean(isActive);
    }
}
