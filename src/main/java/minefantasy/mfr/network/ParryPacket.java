package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.mechanics.CombatMechanics;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.UUID;

public class ParryPacket extends PacketMF {
    private int value;
    private UUID username;
    private EntityPlayer player;

    public ParryPacket(int value, EntityPlayer user) {
        this.value = value;
        this.username = user.getUniqueID();
        player = user;
    }

    public ParryPacket() {
    }

    @Override
    public void readFromStream(ByteBuf packet) {
        value = packet.readInt();
        username = UUID.fromString(ByteBufUtils.readUTF8String(packet));
    }

    @Override
    public void writeToStream(ByteBuf packet) {
        packet.writeInt(value);
        ByteBufUtils.writeUTF8String(packet, username.toString());
    }

    @Override
    protected void execute() {
        if (username != null) {
            EntityPlayer entity = player.world.getPlayerEntityByName(username.toString());
            if (entity != null)
                CombatMechanics.setParryCooldown(entity, value);
        }
    }
}
