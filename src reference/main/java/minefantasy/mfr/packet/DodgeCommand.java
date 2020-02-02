package minefantasy.mfr.packet;

import net.minecraftforge.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import minefantasy.mfr.mechanics.CombatMechanics;
import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;

public class DodgeCommand extends PacketMF {
    public static final String packetName = "MF2_Command_Dodge";
    private EntityPlayer user;
    private int ID;
    private UUID username;

    public DodgeCommand(EntityPlayer user, int id) {
        this.ID = id;
        this.username = user.getCommandSenderEntity().getUniqueID();
        this.user = user;
    }

    public DodgeCommand() {
    }

    @Override
    public void process(ByteBuf packet, EntityPlayer player) {
        ID = packet.readInt();
        username = UUID.fromString(ByteBufUtils.readUTF8String(packet));

        if (username != null && player.getCommandSenderEntity().equals(username)) {
            CombatMechanics.initDodge(player, ID);
        }
    }

    @Override
    public String getChannel() {
        return packetName;
    }

    @Override
    public void write(ByteBuf packet) {
        packet.writeInt(ID);
        ByteBufUtils.writeUTF8String(packet, username.toString());
    }
}
