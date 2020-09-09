package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.tile.decor.TileEntityWoodDecor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class WoodDecorPacket extends PacketMF {
    private BlockPos coords;
    private String materialName;
    private TileEntity tileEntity;

    public WoodDecorPacket(TileEntityWoodDecor tile) {
        tileEntity = tile;
        this.coords = tile.getPos();
        this.materialName = tile.getMaterialName();
    }

    public WoodDecorPacket() {
    }

    @Override
    public void readFromStream(ByteBuf packet) {
        coords = BlockPos.fromLong(packet.readLong());
        materialName = ByteBufUtils.readUTF8String(packet);
    }

    @Override
    public void writeToStream(ByteBuf packet) {
        packet.writeLong(coords.toLong());
        ByteBufUtils.writeUTF8String(packet, materialName);
    }

    @Override
    protected void execute() {
        if (tileEntity != null && tileEntity instanceof TileEntityWoodDecor) {
            TileEntityWoodDecor tile = (TileEntityWoodDecor) tileEntity;
            tile.trySetMaterial(materialName);
        }
    }
}
