package minefantasy.mf2.network.packet;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import minefantasy.mf2.block.tileentity.decor.TileEntityAmmoBox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class AmmoBoxPacket extends PacketMF {
    public static final String packetName = "MF2_AmmoBoxPkt";
    private int[] coords = new int[3];
    private int stock;
    private ItemStack ammo;

    public AmmoBoxPacket(TileEntityAmmoBox tile) {
        this.coords = new int[]{tile.xCoord, tile.yCoord, tile.zCoord};
        this.ammo = tile.ammo;
        this.stock = tile.stock;
    }

    public AmmoBoxPacket() {
    }

    @Override
    public void process(ByteBuf packet, EntityPlayer player) {
        coords = new int[]{packet.readInt(), packet.readInt(), packet.readInt()};
        TileEntity entity = player.worldObj.getTileEntity(coords[0], coords[1], coords[2]);
        stock = packet.readInt();
        ammo = ByteBufUtils.readItemStack(packet);

        if (entity != null && entity instanceof TileEntityAmmoBox) {
            TileEntityAmmoBox tile = (TileEntityAmmoBox) entity;
            tile.ammo = this.ammo;
            tile.stock = this.stock;
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
        packet.writeInt(stock);
        ByteBufUtils.writeItemStack(packet, ammo);
    }
}
