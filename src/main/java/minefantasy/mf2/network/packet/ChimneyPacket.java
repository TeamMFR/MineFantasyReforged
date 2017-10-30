package minefantasy.mf2.network.packet;

import io.netty.buffer.ByteBuf;
import minefantasy.mf2.block.tileentity.TileEntityChimney;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class ChimneyPacket extends PacketMF {
    public static final String packetName = "MF2_ChimneyPacket";
    private int[] coords = new int[3];
    private int block, meta;

    public ChimneyPacket(TileEntityChimney tile) {
        coords = new int[]{tile.xCoord, tile.yCoord, tile.zCoord};
        block = Block.getIdFromBlock(tile.maskBlock);
        meta = tile.blockMetadata;
    }

    public ChimneyPacket() {
    }

    @Override
    public void process(ByteBuf packet, EntityPlayer player) {
        coords = new int[]{packet.readInt(), packet.readInt(), packet.readInt()};
        TileEntity entity = player.worldObj.getTileEntity(coords[0], coords[1], coords[2]);

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
        for (int a = 0; a < coords.length; a++) {
            packet.writeInt(coords[a]);
        }
        packet.writeInt(block);
        packet.writeInt(meta);
    }
}
