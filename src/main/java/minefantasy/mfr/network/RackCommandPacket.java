package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.block.BlockRack;
import minefantasy.mfr.tile.decor.TileEntityRack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.UUID;

public class RackCommandPacket extends PacketMF {
    private TileEntityRack rack;
    private int slot;
    private UUID username;
    private BlockPos pos;

    public RackCommandPacket(int slot, EntityPlayer player, TileEntityRack rack) {
        this.username = player.getUniqueID();
        this.slot = slot;
        this.rack = rack;
        this.pos = rack.getPos();
    }

    public RackCommandPacket() {
    }

    @Override
    public void readFromStream(ByteBuf packet) {
        pos = BlockPos.fromLong(packet.readLong());
        slot = packet.readInt();
        username = UUID.fromString(ByteBufUtils.readUTF8String(packet));
    }

    @Override
    public void writeToStream(ByteBuf packet) {
        packet.writeLong(pos.toLong());
        packet.writeInt(slot);
        ByteBufUtils.writeUTF8String(packet, username.toString());
    }

    @Override
    protected void execute(EntityPlayer player) {
        TileEntity tile = player.world.getTileEntity(pos);

        if (tile instanceof TileEntityRack) {
            rack = (TileEntityRack) tile;
            BlockRack.interact(slot, player.world, rack, player);
        }
    }
}
