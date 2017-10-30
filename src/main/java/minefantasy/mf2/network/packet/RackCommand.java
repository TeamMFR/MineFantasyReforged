package minefantasy.mf2.network.packet;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import minefantasy.mf2.block.decor.BlockRack;
import minefantasy.mf2.block.tileentity.decor.TileEntityRack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class RackCommand extends PacketMF {
    public static final String packetName = "MF2_Command_Rack";
    private EntityPlayer user;
    private TileEntityRack rack;
    private int slot;
    private String username;

    public RackCommand(int slot, EntityPlayer user, TileEntityRack rack) {
        this.username = user.getCommandSenderName();
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

        slot = packet.readInt();
        username = ByteBufUtils.readUTF8String(packet);
        EntityPlayer user = player.worldObj.getPlayerEntityByName(username);
        TileEntity tile = player.worldObj.getTileEntity(x, y, z);

        if (user != null && tile != null && tile instanceof TileEntityRack) {
            rack = (TileEntityRack) tile;
            BlockRack.interact(slot, user.worldObj, rack, user);
        }
    }

    @Override
    public String getChannel() {
        return packetName;
    }

    @Override
    public void write(ByteBuf packet) {
        packet.writeInt(rack.xCoord);
        packet.writeInt(rack.yCoord);
        packet.writeInt(rack.zCoord);
        packet.writeInt(slot);
        ByteBufUtils.writeUTF8String(packet, username);
    }
}
