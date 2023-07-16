package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.data.PlayerData;
import minefantasy.mfr.mechanics.StaminaBar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.UUID;

public class StaminaPacket extends PacketMF {
	private float[] value;
	private UUID username;
	private EntityPlayer player;

	public StaminaPacket(float[] value, EntityPlayer user) {
		this.value = value;
		this.username = user.getUniqueID();
		player = user;
	}

	public StaminaPacket() {
	}

	@Override
	public void readFromStream(ByteBuf packet) {
		value = new float[] {StaminaBar.getDefaultMax(), StaminaBar.getDefaultMax(), 0, 0F};
		value[0] = packet.readFloat();
		value[1] = packet.readFloat();
		value[2] = packet.readFloat();
		value[3] = packet.readFloat();
		username = UUID.fromString(ByteBufUtils.readUTF8String(packet));
	}

	@Override
	public void writeToStream(ByteBuf packet) {
		for (float variable : value) {
			packet.writeFloat(variable);
		}
		ByteBufUtils.writeUTF8String(packet, username.toString());
	}

	@Override
	public void execute(EntityPlayer player) {
		if (username != null && player != null && player.getUniqueID().equals(username)) {
			PlayerData data = PlayerData.get(player);
			StaminaBar.setStaminaValue(data, value[0]);
			StaminaBar.setMaxStamina(data, value[1]);
			StaminaBar.setFlashTime(data, value[2]);
			StaminaBar.setBonusStamina(data, value[3]);
		}
	}
}
