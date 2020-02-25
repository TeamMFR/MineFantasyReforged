package minefantasy.mfr.packet;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.block.tile.decor.TileEntityTrough;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TroughPacket extends PacketMF {
    public static final String packetName = "MF2_TroughPacket";
    private BlockPos coords;
    private int fill;

    public TroughPacket(TileEntityTrough tile) {
        coords = tile.getPos();
        fill = tile.fill;
    }

    public TroughPacket() {
    }

    @Override
    public void process(ByteBuf packet, EntityPlayer player) {
        coords = new BlockPos(packet.readInt(), packet.readInt(), packet.readInt());
        TileEntity entity = player.world.getTileEntity(coords);
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
        packet.writeLong(coords.toLong());
        packet.writeInt(fill);
    }
}
