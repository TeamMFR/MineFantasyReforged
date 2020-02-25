package minefantasy.mfr.packet;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.block.tile.TileEntityComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class StorageBlockPacket extends PacketMF {
    public static final String packetName = "MF2_StoragePkt";
    private BlockPos coords;
    private ItemStack component;
    private String type, tex, materialName;
    private int stackSize, max;

    public StorageBlockPacket(TileEntityComponent tile) {
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
    public void process(ByteBuf packet, EntityPlayer player) {
        coords = new BlockPos(packet.readInt(), packet.readInt(), packet.readInt());
        TileEntity entity = player.world.getTileEntity(coords);
        type = ByteBufUtils.readUTF8String(packet);
        tex = ByteBufUtils.readUTF8String(packet);
        materialName = ByteBufUtils.readUTF8String(packet);
        component = ByteBufUtils.readItemStack(packet);
        stackSize = packet.readInt();
        max = packet.readInt();

        if (entity != null && entity instanceof TileEntityComponent) {
            TileEntityComponent tile = (TileEntityComponent) entity;
            tile.type = this.type;
            tile.tex = this.tex;
            tile.material = CustomMaterial.getMaterial(materialName);
            tile.item = this.component;
            tile.stackSize = this.stackSize;
            tile.max = this.max;
        }
    }

    @Override
    public String getChannel() {
        return packetName;
    }

    @Override
    public void write(ByteBuf packet) {
        packet.writeLong(coords.toLong());
        ByteBufUtils.writeUTF8String(packet, type);
        ByteBufUtils.writeUTF8String(packet, tex);
        ByteBufUtils.writeUTF8String(packet, materialName);
        ByteBufUtils.writeItemStack(packet, component);
        packet.writeInt(stackSize);
        packet.writeInt(max);
    }
}
