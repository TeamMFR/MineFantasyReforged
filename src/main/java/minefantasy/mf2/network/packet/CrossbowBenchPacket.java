package minefantasy.mf2.network.packet;

import io.netty.buffer.ByteBuf;
import minefantasy.mf2.block.tileentity.TileEntityCrossbowBench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class CrossbowBenchPacket extends PacketMF {
    public static final String packetName = "MF2_CrossbowBenchPkt";
    private int[] coords = new int[3];
    private float progress;
    private float maxProgress;
    private boolean hasRecipe;

    public CrossbowBenchPacket(TileEntityCrossbowBench tile) {
        coords = new int[]{tile.xCoord, tile.yCoord, tile.zCoord};
        progress = tile.progress;
        maxProgress = tile.maxProgress;
        hasRecipe = tile.hasRecipe;
    }

    public CrossbowBenchPacket() {
    }

    @Override
    public void process(ByteBuf packet, EntityPlayer player) {
        coords = new int[]{packet.readInt(), packet.readInt(), packet.readInt()};
        TileEntity entity = player.worldObj.getTileEntity(coords[0], coords[1], coords[2]);
        float newProgress = packet.readFloat();
        float newMaxProgress = packet.readFloat();
        boolean newRecipe = packet.readBoolean();

        if (entity != null && entity instanceof TileEntityCrossbowBench) {
            TileEntityCrossbowBench tile = (TileEntityCrossbowBench) entity;
            tile.progress = newProgress;
            tile.maxProgress = newMaxProgress;
            tile.hasRecipe = newRecipe;
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
        packet.writeFloat(progress);
        packet.writeFloat(maxProgress);
        packet.writeBoolean(hasRecipe);
    }
}
