package minefantasy.mfr.packet;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.block.tile.TileEntityChimney;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class ChimneyPacket extends PacketMF {
    public static final String packetName = "MF2_ChimneyPacket";
    private BlockPos coords;
    private int block;

    public ChimneyPacket(TileEntityChimney tile) {
        coords = tile.getPos();
        block = Block.getIdFromBlock(tile.maskBlock);
    }

    public ChimneyPacket() {
    }

    @Override
    public void process(ByteBuf packet, EntityPlayer player) {
        coords = new BlockPos(packet.readInt(), packet.readInt(), packet.readInt());
        TileEntity entity = player.world.getTileEntity(coords);

        if (entity != null && entity instanceof TileEntityChimney) {
            TileEntityChimney tile = (TileEntityChimney) entity;

            int blockID = packet.readInt();
            int blockMetadata = packet.readInt();
            tile.setBlock(blockID, blockMetadata);
        }
    }

    @Override
    public String getChannel() {
        return packetName;
    }

    @Override
    public void write(ByteBuf packet) {
        packet.writeLong(coords.toLong());
        packet.writeInt(block);
    }
}