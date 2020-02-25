package minefantasy.mfr.packet;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import minefantasy.mfr.block.tile.decor.TileEntityWoodDecor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class WoodDecorPacket extends PacketMF {
    public static final String packetName = "MF2_WdDecorPkt";
    private BlockPos coords;
    private String materialName;

    public WoodDecorPacket(TileEntityWoodDecor tile) {
        this.coords = tile.getPos();
        this.materialName = tile.getMaterialName();
    }

    public WoodDecorPacket() {
    }

    @Override
    public void process(ByteBuf packet, EntityPlayer player) {
        coords = new BlockPos(packet.readInt(), packet.readInt(), packet.readInt());
        TileEntity entity = player.world.getTileEntity(coords);
        materialName = ByteBufUtils.readUTF8String(packet);

        if (entity != null && entity instanceof TileEntityWoodDecor) {
            TileEntityWoodDecor tile = (TileEntityWoodDecor) entity;
            tile.trySetMaterial(materialName);
        }
    }

    @Override
    public String getChannel() {
        return packetName;
    }

    @Override
    public void write(ByteBuf packet) {
        packet.writeLong(coords.toLong());
        ByteBufUtils.writeUTF8String(packet, materialName);
    }
}
