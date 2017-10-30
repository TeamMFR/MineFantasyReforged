package minefantasy.mf2.network.packet;

import io.netty.buffer.ByteBuf;
import minefantasy.mf2.block.tileentity.TileEntityResearch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class ResearchTablePacket extends PacketMF {
    public static final String packetName = "MF2_ResearchTblPkt";
    private int[] coords = new int[3];
    private int id;
    private float[] progress = new float[2];

    public ResearchTablePacket(TileEntityResearch tile) {
        coords = new int[]{tile.xCoord, tile.yCoord, tile.zCoord};
        id = tile.researchID;
        progress = new float[]{tile.progress, tile.maxProgress};
        if (progress[1] <= 0) {
            progress[1] = 0;
        }
    }

    public ResearchTablePacket() {
    }

    @Override
    public void process(ByteBuf packet, EntityPlayer player) {
        coords = new int[]{packet.readInt(), packet.readInt(), packet.readInt()};
        TileEntity entity = player.worldObj.getTileEntity(coords[0], coords[1], coords[2]);
        float prog1 = packet.readFloat();
        float prog2 = packet.readFloat();
        int ID = packet.readInt();

        if (entity != null && entity instanceof TileEntityResearch) {
            progress[0] = prog1;
            progress[1] = prog2;
            id = ID;

            TileEntityResearch carpenter = (TileEntityResearch) entity;
            carpenter.researchID = id;
            carpenter.progress = progress[0];
            carpenter.maxProgress = (int) progress[1];
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
        packet.writeFloat(progress[0]);
        packet.writeFloat(progress[1]);
        packet.writeInt(id);
    }
}
