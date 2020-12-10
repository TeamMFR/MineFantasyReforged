package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.tile.TileEntityAmmoBox;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class AmmoBoxCommandPacket extends PacketMF {
    private BlockPos coords;
    private int stock;
    private ItemStack ammo;
    private TileEntity tileEntity;

    public AmmoBoxCommandPacket(TileEntityAmmoBox tile) {
        tileEntity = tile;
        this.coords = tile.getPos();
        this.ammo = tile.inventoryStack;
        this.stock = tile.stock;
    }

    public AmmoBoxCommandPacket() {
    }

    @Override
    public void readFromStream(ByteBuf packet) {
        coords = BlockPos.fromLong(packet.readLong());
        stock = packet.readInt();
        ammo = ByteBufUtils.readItemStack(packet);
    }

    @Override
    public void writeToStream(ByteBuf packet) {
        packet.writeLong(coords.toLong());
        packet.writeInt(stock);
        ByteBufUtils.writeItemStack(packet, ammo);
    }

    @Override
    protected void execute() {
        if (tileEntity instanceof TileEntityAmmoBox) {
            TileEntityAmmoBox tile = (TileEntityAmmoBox) tileEntity;
            tile.inventoryStack = this.ammo;
            tile.stock = this.stock;
        }
    }
}
