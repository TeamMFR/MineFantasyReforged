package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.tile.TileEntityResearchBench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class ResearchTablePacket extends PacketMF {
    private BlockPos coords;
    private int id;
    private float[] progress = new float[2];
    private TileEntity tileEntity;
    private float progress1;
    private float progress2;
    private int ID;

    public ResearchTablePacket(TileEntityResearchBench tile) {
        tileEntity = tile;
        coords = tile.getPos();
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
        coords = BlockPos.fromLong(packet.readLong());
        progress1 = packet.readFloat();
        progress2 = packet.readFloat();
        ID = packet.readInt();
    }

    @Override
    public void writeToStream(ByteBuf packet) {
        packet.writeLong(coords.toLong());
        packet.writeFloat(progress[0]);
        packet.writeFloat(progress[1]);
        packet.writeInt(id);
    }

    @Override
    protected void execute(EntityPlayer player) {
        if (tileEntity != null && tileEntity instanceof TileEntityResearchBench) {
            progress[0] = progress1;
            progress[1] = progress2;
            id = ID;

            TileEntityResearchBench carpenter = (TileEntityResearchBench) tileEntity;
            carpenter.researchID = id;
            carpenter.progress = progress[0];
            carpenter.maxProgress = (int) progress[1];
        }
    }
}
