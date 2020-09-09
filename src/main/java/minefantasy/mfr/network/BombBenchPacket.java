package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.tile.TileEntityBombBench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class BombBenchPacket extends PacketMF {
    public static final String packetName = "MF2_BombBenchPkt";
    private BlockPos coords;
    private float progress;
    private float maxProgress;
    private boolean hasRecipe;
    private TileEntity tileEntity;
    private float newProgress;
    private float newMaxProgress;
    private boolean newRecipe;

    public BombBenchPacket(TileEntityBombBench tile) {
        tileEntity = tile;
        coords = tile.getPos();
        progress = tile.progress;
        maxProgress = tile.maxProgress;
        hasRecipe = tile.hasRecipe;
    }

    public BombBenchPacket() {
    }

    @Override
    public void readFromStream(ByteBuf packet) {
        coords = BlockPos.fromLong(packet.readLong());
        newProgress = packet.readFloat();
        newMaxProgress = packet.readFloat();
        newRecipe = packet.readBoolean();
    }

    @Override
    public void writeToStream(ByteBuf packet) {
       packet.writeLong(coords.toLong());
        packet.writeFloat(progress);
        packet.writeFloat(maxProgress);
        packet.writeBoolean(hasRecipe);
    }

    @Override
    protected void execute(EntityPlayer player) {
        if (tileEntity != null && tileEntity instanceof TileEntityBombBench) {
            TileEntityBombBench tile = (TileEntityBombBench) tileEntity;
            tile.progress = newProgress;
            tile.maxProgress = newMaxProgress;
            tile.hasRecipe = newRecipe;
        }
    }
}
