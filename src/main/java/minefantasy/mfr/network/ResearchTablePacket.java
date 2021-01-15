package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.tile.TileEntityResearchBench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class ResearchTablePacket extends PacketMF {
    private BlockPos pos;
    private int id;
    private float[] progress = new float[2];
    private float progress1;
    private float progress2;
    private int ID;

    public ResearchTablePacket(TileEntityResearchBench tile) {
        pos = tile.getPos();
        id = tile.researchID;
        progress = new float[]{tile.progress, tile.maxProgress};
        if (progress[1] <= 0) {
            progress[1] = 0;
        }
    }

    public ResearchTablePacket() {
    }

    @Override
    public void readFromStream(ByteBuf packet) {
        pos = BlockPos.fromLong(packet.readLong());
        progress1 = packet.readFloat();
        progress2 = packet.readFloat();
        ID = packet.readInt();
    }

    @Override
    public void writeToStream(ByteBuf packet) {
        packet.writeLong(pos.toLong());
        packet.writeFloat(progress[0]);
        packet.writeFloat(progress[1]);
        packet.writeInt(id);
    }

    @Override
    protected void execute(EntityPlayer player) {
        TileEntity tileEntity = player.getEntityWorld().getTileEntity(pos);
        if (tileEntity instanceof TileEntityResearchBench) {
            progress[0] = progress1;
            progress[1] = progress2;
            id = ID;

            TileEntityResearchBench tile = (TileEntityResearchBench) tileEntity;
            tile.researchID = id;
            tile.progress = progress[0];
            tile.maxProgress = (int) progress[1];
        }
    }
}
