package minefantasy.mfr.network;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import minefantasy.mfr.block.decor.BlockRack;
import minefantasy.mfr.tile.decor.TileEntityRack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import java.util.UUID;

public class RackCommand extends PacketMF {
    public static final String packetName = "MF2_Command_Rack";
    private EntityPlayer user;
    private TileEntityRack rack;
    private int slot;
    private UUID username;

    public RackCommand(int slot, EntityPlayer user, TileEntityRack rack) {
        this.username = user.getCommandSenderEntity().getUniqueID();
        this.slot = slot;
        this.rack = rack;
        this.user = user;
    }

    public RackCommand() {
    }

    @Override
    public void process(ByteBuf packet, EntityPlayer player) {
        int x = packet.readInt();
        int y = packet.readInt();
        int z = packet.readInt();
        BlockPos coords = new BlockPos(x,y,z);

        slot = packet.readInt();
        username = UUID.fromString(ByteBufUtils.readUTF8String(packet));
        EntityPlayer user = player.world.getPlayerEntityByName(username.toString());
        TileEntity tile = player.world.getTileEntity(coords);

        if (user != null && tile != null && tile instanceof TileEntityRack) {
            rack = (TileEntityRack) tile;
            BlockRack.interact(slot, user.world, rack, user);
        }
    }

    @Override
    public String getChannel() {
        return packetName;
    }

    @Override
    public void write(ByteBuf packet) {
        packet.writeInt(rack.getPos().getX());
        packet.writeInt(rack.getPos().getY());
        packet.writeInt(rack.getPos().getZ());
        packet.writeInt(slot);
        ByteBufUtils.writeUTF8String(packet, username.toString());
    }
}
