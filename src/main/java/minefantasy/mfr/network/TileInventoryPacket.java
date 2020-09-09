package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.tile.TileEntityRoast;
import minefantasy.mfr.tile.decor.TileEntityRack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.items.ItemStackHandler;

public class TileInventoryPacket extends PacketMF {
    private BlockPos coords;
    private int invSize;
    private ItemStackHandler inventory;
    private TileEntity tileEntity;

    public TileInventoryPacket(ItemStackHandler inv, TileEntity tile) {
        tileEntity = tile;
        this.coords = tile.getPos();
        this.inventory = inv;
        this.invSize = inv.getSlots();
    }

    public TileInventoryPacket(){}

    @Override
    public void readFromStream(ByteBuf packet) {
        coords = BlockPos.fromLong(packet.readLong());
        for (int s = 0; s < invSize; s++) {
            inventory.setStackInSlot(s, ByteBufUtils.readItemStack(packet));
        }
        packet.clear();
    }

    @Override
    public void writeToStream(ByteBuf packet) {
        packet.writeLong(coords.toLong());
        packet.writeInt(invSize);
        for (int s = 0; s < invSize; s++) {
            ByteBufUtils.writeItemStack(packet, inventory.getStackInSlot(s));
        }
    }

    @Override
    protected void execute() {
        if (tileEntity != null && tileEntity instanceof TileEntityRack) {
            ((TileEntityRack) tileEntity).inventory = this.inventory;
        }
        if (tileEntity != null && tileEntity instanceof TileEntityRoast) {
            ((TileEntityRoast) tileEntity).inventory = this.inventory;
        }
    }
}
