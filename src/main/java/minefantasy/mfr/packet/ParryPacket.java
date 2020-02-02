package minefantasy.mfr.packet;

import net.minecraftforge.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import minefantasy.mfr.mechanics.CombatMechanics;
import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;

public class ParryPacket extends PacketMF {
    public static final String packetName = "MF2_ParryPacket";
    private int value;
    private UUID username;

    public ParryPacket(int value, EntityPlayer user) {
        this.value = value;
        this.username = user.getCommandSenderEntity().getUniqueID();
    }

    public ParryPacket() {
    }

    @Override
    public void process(ByteBuf packet, EntityPlayer player) {
        value = packet.readInt();
        username = UUID.fromString(ByteBufUtils.readUTF8String(packet));

        if (username != null) {
            EntityPlayer entity = player.world.getPlayerEntityByName(username.toString());
            if (entity != null)
                CombatMechanics.setParryCooldown(entity, value);
        }
    }

    @Override
    public String getChannel() {
        return packetName;
    }

    @Override
    public void write(ByteBuf packet) {
        packet.writeInt(value);
        ByteBufUtils.writeUTF8String(packet, username.toString());
    }
}
