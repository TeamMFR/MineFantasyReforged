package minefantasy.mf2.network.packet;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.block.tileentity.TileEntityComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class StorageBlockPacket extends PacketMF {
    public static final String packetName = "MF2_StoragePkt";
    private int[] coords = new int[3];
    private ItemStack component;
    private String type, tex, materialName;
    private int stackSize, max;

    public StorageBlockPacket(TileEntityComponent tile) {
        this.coords = new int[]{tile.xCoord, tile.yCoord, tile.zCoord};
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
        coords = new int[]{packet.readInt(), packet.readInt(), packet.readInt()};
        TileEntity entity = player.worldObj.getTileEntity(coords[0], coords[1], coords[2]);
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
        for (int a = 0; a < coords.length; a++) {
            packet.writeInt(coords[a]);
        }
        ByteBufUtils.writeUTF8String(packet, type);
        ByteBufUtils.writeUTF8String(packet, tex);
        ByteBufUtils.writeUTF8String(packet, materialName);
        ByteBufUtils.writeItemStack(packet, component);
        packet.writeInt(stackSize);
        packet.writeInt(max);
    }
}
