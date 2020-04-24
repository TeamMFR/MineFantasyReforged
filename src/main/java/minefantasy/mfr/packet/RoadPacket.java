package minefantasy.mfr.packet;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.block.tile.TileEntityRoad;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class RoadPacket extends PacketMF {
    public static final String packetName = "MF2_RoadPacket";
    private BlockPos coords;
    private Block surface;
    private boolean isLocked;
    private boolean isRequest = false;

    public RoadPacket(TileEntityRoad tile) {
        this.coords = tile.getPos();
        this.surface = tile.surface;
        this.isLocked = tile.isLocked;
    }

    public RoadPacket() {
    }

    public RoadPacket request() {
        isRequest = true;
        return this;
    }

    @Override
    public void process(ByteBuf packet, EntityPlayer player) {
        this.coords = new BlockPos(packet.readInt(), packet.readInt(), packet.readInt());
        isRequest = packet.readBoolean();

        TileEntity entity = player.world.getTileEntity(coords);

        if (entity != null && entity instanceof TileEntityRoad) {
            TileEntityRoad tile = (TileEntityRoad) entity;
            if (isRequest) {
                tile.sendPacketToClients();
            } else {
                int block = packet.readInt();
                this.isLocked = packet.readBoolean();

                tile.surface = Block.getBlockById(block);
                tile.isLocked = this.isLocked;

                tile.refreshSurface();
            }
        }
        packet.clear();
    }

    @Override
    public String getChannel() {
        return packetName;
    }

    @Override
    public void write(ByteBuf packet) {
        packet.writeLong(coords.toLong());
        packet.writeBoolean(isRequest);

        if (!isRequest) {
            packet.writeInt(Block.getIdFromBlock(surface));
            packet.writeBoolean(this.isLocked);
        }
    }
}
