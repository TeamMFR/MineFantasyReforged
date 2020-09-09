package minefantasy.mfr.network;

import net.minecraftforge.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import minefantasy.mfr.api.stamina.StaminaBar;
import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;

public class StaminaPacket extends PacketMF {
    public static final String packetName = "MF2_Staminabar";
    private float[] value;
    private UUID username;

    public StaminaPacket(float[] value, EntityPlayer user) {
        this.value = value;
        this.username = user.getCommandSenderEntity().getUniqueID();
    }

    public StaminaPacket() {
    }

    @Override
    public void process(ByteBuf packet, EntityPlayer player) {
        value = new float[]{StaminaBar.getDefaultMax(player), StaminaBar.getDefaultMax(player), 0, 0F};
        value[0] = packet.readFloat();
        value[1] = packet.readFloat();
        value[2] = packet.readFloat();
        value[3] = packet.readFloat();
        username = UUID.fromString(ByteBufUtils.readUTF8String(packet));

        if (username != null && player.getCommandSenderEntity().equals(username)) {
            StaminaBar.setStaminaValue(player, value[0]);
            StaminaBar.setMaxStamina(player, value[1]);
            StaminaBar.setFlashTime(player, value[2]);
            StaminaBar.setBonusStamina(player, value[3]);
        }
    }

    @Override
    public String getChannel() {
        return packetName;
    }

    @Override
    public void write(ByteBuf packet) {
        for (float variable : value) {
            packet.writeFloat(variable);
        }
        ByteBufUtils.writeUTF8String(packet, username.toString());
    }
}
