package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.tile.TileEntityComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class StorageBlockPacket extends PacketMF {
    private BlockPos coords;
    private ItemStack component;
    private String type, tex, materialName;
    private int stackSize, max;
    private TileEntity tileEntity;

    public StorageBlockPacket(TileEntityComponent tile) {
        tileEntity = tile;
        this.coords = tile.getPos();
        this.type = tile.type;
        this.tex = tile.tex;
        this.materialName = tile.material != null ? tile.material.name : "steel";
        this.component = tile.item;
        this.stackSize = tile.stackSize;
        this.max = tile.max;
    }

    public StorageBlockPacket() {
    }

    @Override
    public void readFromStream(ByteBuf packet) {
        coords = BlockPos.fromLong(packet.readLong());
        type = ByteBufUtils.readUTF8String(packet);
        tex = ByteBufUtils.readUTF8String(packet);
        materialName = ByteBufUtils.readUTF8String(packet);
        component = ByteBufUtils.readItemStack(packet);
        stackSize = packet.readInt();
        max = packet.readInt();
    }

    @Override
    public void writeToStream(ByteBuf packet) {
        packet.writeLong(coords.toLong());
        ByteBufUtils.writeUTF8String(packet, type);
        ByteBufUtils.writeUTF8String(packet, tex);
        ByteBufUtils.writeUTF8String(packet, materialName);
        ByteBufUtils.writeItemStack(packet, component);
        packet.writeInt(stackSize);
        packet.writeInt(max);
    }

    @Override
    protected void execute() {
        if (tileEntity != null && tileEntity instanceof TileEntityComponent) {
            TileEntityComponent tile = (TileEntityComponent) tileEntity;
            tile.type = this.type;
            tile.tex = this.tex;
            tile.material = CustomMaterial.getMaterial(materialName);
            tile.item = this.component;
            tile.stackSize = this.stackSize;
            tile.max = this.max;
        }
    }
}
