package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.tile.TileEntityCrossbowBench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class CrossbowBenchPacket extends PacketMF {
    public static final String packetName = "MF2_CrossbowBenchPkt";
    private BlockPos coords;
    private float progress;
    private float maxProgress;
    private boolean hasRecipe;

    public CrossbowBenchPacket(TileEntityCrossbowBench tile) {
        coords = new BlockPos(tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ());
        progress = tile.progress;
        maxProgress = tile.maxProgress;
        hasRecipe = tile.hasRecipe;
    }

    public CrossbowBenchPacket() {
    }

    @Override
    public void process(ByteBuf packet, EntityPlayer player) {
        coords = new BlockPos(packet.readInt(), packet.readInt(), packet.readInt());
        TileEntity entity = player.world.getTileEntity(coords);
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
        packet.writeLong(coords.toLong());
        packet.writeFloat(progress);
        packet.writeFloat(maxProgress);
        packet.writeBoolean(hasRecipe);
    }
}
