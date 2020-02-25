package minefantasy.mfr.packet;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import minefantasy.mfr.block.tile.decor.TileEntityAmmoBox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class AmmoBoxPacket extends PacketMF {
    public static final String packetName = "MF2_AmmoBoxPkt";
    private BlockPos coords;
    private int stock;
    private ItemStack ammo;

    public AmmoBoxPacket(TileEntityAmmoBox tile) {
        this.coords = tile.getPos();
        this.ammo = tile.ammo;
        this.stock = tile.stock;
    }

    public AmmoBoxPacket() {
    }

    @Override
    public void process(ByteBuf packet, EntityPlayer player) {
        coords = new BlockPos(packet.readInt(), packet.readInt(), packet.readInt());
        TileEntity entity = player.world.getTileEntity(coords);
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
        packet.writeLong(coords.toLong());
        packet.writeInt(stock);
        ByteBufUtils.writeItemStack(packet, ammo);
    }
}
