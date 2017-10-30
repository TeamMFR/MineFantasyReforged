package minefantasy.mf2.network.packet;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileInventoryPacket extends PacketMF {
    public static final String packetName = "MF2_TileInvPacket";
    private int[] coords = new int[3];
    private int invSize;
    private IInventory inventory;

    public TileInventoryPacket(IInventory inv, TileEntity tile) {
        this.coords = new int[]{tile.xCoord, tile.yCoord, tile.zCoord};
        this.inventory = inv;
        this.invSize = inv.getSizeInventory();
    }

    public TileInventoryPacket() {
    }

    @Override
    public void process(ByteBuf packet, EntityPlayer player) {
        int x = packet.readInt();
        int y = packet.readInt();
        int z = packet.readInt();
        int size = packet.readInt();

        TileEntity entity = player.worldObj.getTileEntity(x, y, z);

        if (entity != null && entity instanceof IInventory) {
            IInventory inv = (IInventory) entity;
            for (int s = 0; s < size; s++) {
                ItemStack item = ByteBufUtils.readItemStack(packet);
                if (s < inv.getSizeInventory()) {
                    inv.setInventorySlotContents(s, item);
                } else {
                    item = null;
                    MFLogUtil.logDebug("Dropped Packet Item " + s);
                }
            }
        }
        packet.clear();
    }

    @Override
    public String getChannel() {
        return packetName;
    }

    @Override
    public void write(ByteBuf packet) {
        packet.writeInt(coords[0]);
        packet.writeInt(coords[1]);
        packet.writeInt(coords[2]);
        packet.writeInt(invSize);
        for (int s = 0; s < invSize; s++) {
            ByteBufUtils.writeItemStack(packet, inventory.getStackInSlot(s));
        }
    }
}
