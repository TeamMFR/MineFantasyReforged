package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.tile.TileEntityRoad;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class RoadPacket extends PacketMF {
    public static final String packetName = "MF2_RoadPacket";
    private BlockPos coords;
    private int[] surface;
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
        this.coords = new int[]{packet.readInt(), packet.readInt(), packet.readInt()};
        isRequest = packet.readBoolean();

        TileEntity entity = player.world.getTileEntity(coords);

        if (entity != null && entity instanceof TileEntityRoad) {
            TileEntityRoad tile = (TileEntityRoad) entity;
            if (isRequest) {
                tile.sendPacketToClients();
            } else {
                int s0 = packet.readInt();
                int s1 = packet.readInt();
                this.isLocked = packet.readBoolean();

                tile.surface = new int[]{s0, s1};
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
        for (int a = 0; a < coords.toLong(); a++) {
            packet.writeInt(coords[a]);
        }
        packet.writeBoolean(isRequest);

        if (!isRequest) {
            packet.writeInt(surface[0]);
            packet.writeInt(surface[1]);
            packet.writeBoolean(this.isLocked);
        }
    }
}
