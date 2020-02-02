package minefantasy.mfr.packet;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.block.tile.TileEntityBombBench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class BombBenchPacket extends PacketMF {
    public static final String packetName = "MF2_BombBenchPkt";
    private BlockPos coords;
    private float progress;
    private float maxProgress;
    private boolean hasRecipe;

    public BombBenchPacket(TileEntityBombBench tile) {
        coords = tile.getPos();
        progress = tile.progress;
        maxProgress = tile.maxProgress;
        hasRecipe = tile.hasRecipe;
    }

    public BombBenchPacket() {
    }

    @Override
    public void process(ByteBuf packet, EntityPlayer player) {
        coords = new BlockPos(packet.readInt(), packet.readInt(), packet.readInt());
        TileEntity entity = player.world.getTileEntity(coords);
        float newProgress = packet.readFloat();
        float newMaxProgress = packet.readFloat();
        boolean newRecipe = packet.readBoolean();

        if (entity != null && entity instanceof TileEntityBombBench) {
            TileEntityBombBench tile = (TileEntityBombBench) entity;
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
