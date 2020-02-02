package minefantasy.mfr.packet;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import minefantasy.mfr.util.MFRLogUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileInventoryPacket extends PacketMF {
    public static final String packetName = "MF2_TileInvPacket";
    private BlockPos coords;
    private int invSize;
    private IInventory inventory;

    public TileInventoryPacket(IInventory inv, TileEntity tile) {
        this.coords = tile.getPos();
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
        BlockPos coords = new BlockPos(x,y,z);
        int size = packet.readInt();

        TileEntity entity = player.world.getTileEntity(coords);

        if (entity != null && entity instanceof IInventory) {
            IInventory inv = (IInventory) entity;
            for (int s = 0; s < size; s++) {
                ItemStack item = ByteBufUtils.readItemStack(packet);
                if (s < inv.getSizeInventory()) {
                    inv.setInventorySlotContents(s, item);
                } else {
                    item = null;
                    MFRLogUtil.logDebug("Dropped Packet Item " + s);
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
        packet.writeInt(coords.getX());
        packet.writeInt(coords.getY());
        packet.writeInt(coords.getZ());
        packet.writeInt(invSize);
        for (int s = 0; s < invSize; s++) {
            ByteBufUtils.writeItemStack(packet, inventory.getStackInSlot(s));
        }
    }
}
