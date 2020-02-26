package minefantasy.mfr.packet;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.block.tile.TileEntityTanningRack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TannerPacket extends PacketMF {
    public static final String packetName = "MF2_TannerPacket";
    private BlockPos coords;
    private float animation;

    public TannerPacket(TileEntityTanningRack tile) {
        coords = tile.getPos();
        animation = tile.acTime;
    }

    public TannerPacket() {
    }

    @Override
    public void process(ByteBuf packet, EntityPlayer player) {
        coords = new BlockPos(packet.readInt(), packet.readInt(), packet.readInt());
        TileEntity entity = player.world.getTileEntity(coords);
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
        packet.writeLong(coords.toLong());
        packet.writeFloat(animation);
    }
}
