package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.tile.TileEntityCarpenter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class CarpenterPacket extends PacketMF {
    private BlockPos coords;
    private String resultName;
    private String toolNeeded;
    private float[] progress = new float[2];
    private int[] tiers = new int[2];
    private String research;
    private TileEntity tileEntity;

    public CarpenterPacket(TileEntityCarpenter tile) {
        tileEntity = tile;
        coords = tile.getPos();
        resultName = tile.getResultName();
        toolNeeded = tile.getToolNeeded();
        progress = new float[]{tile.progress, tile.progressMax};
        tiers = new int[]{tile.getToolTierNeeded(), tile.getCarpenterTierNeeded()};
        if (progress[1] <= 0) {
            progress[1] = 0;
        }
        research = tile.getResearchNeeded();
    }

    public CarpenterPacket() {
    }

    @Override
    public void readFromStream(ByteBuf packet) {
        coords = BlockPos.fromLong(packet.readLong());
        progress[0] = packet.readFloat();
        progress[1] = packet.readFloat();
        tiers[0] = packet.readInt();
        tiers[1] = packet.readInt();
        resultName = ByteBufUtils.readUTF8String(packet);
        toolNeeded = ByteBufUtils.readUTF8String(packet);
        research = ByteBufUtils.readUTF8String(packet);
    }

    @Override
    public void writeToStream(ByteBuf packet) {
        packet.writeLong(coords.toLong());
        packet.writeFloat(progress[0]);
        packet.writeFloat(progress[1]);
        packet.writeInt(tiers[0]);
        packet.writeInt(tiers[1]);
        ByteBufUtils.writeUTF8String(packet, resultName);
        ByteBufUtils.writeUTF8String(packet, toolNeeded);
        ByteBufUtils.writeUTF8String(packet, research);
    }

    @Override
    protected void execute(EntityPlayer player) {
        if (tileEntity != null && tileEntity instanceof TileEntityCarpenter) {
            TileEntityCarpenter carpenter = (TileEntityCarpenter) tileEntity;
            carpenter.resName = resultName;
            carpenter.setToolType(toolNeeded);
            carpenter.setResearch(research);
            carpenter.progress = progress[0];
            carpenter.progressMax = progress[1];
            carpenter.setToolTier(tiers[0]);
            carpenter.setRequiredCarpenter(tiers[1]);
        }
    }
}
