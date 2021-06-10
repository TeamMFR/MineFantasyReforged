package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.data.IVariable;
import minefantasy.mfr.data.PlayerData;
import minefantasy.mfr.mechanics.RPGElements;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlayerDataPacket extends PacketMF {
	public Map<IVariable, Object> playerData;

	public PlayerDataPacket(Map<IVariable, Object> playerDataList) {
		this.playerData = playerDataList;
	}

	public PlayerDataPacket() {
	}

	@Override
	public void readFromStream(ByteBuf packet) {
		this.playerData = new HashMap<>();
		PlayerData.getSyncedVariables().forEach(v -> playerData.put(v, v.read(packet)));
		// Have to send empty tags to guarantee correct ByteBuf size/order, but no point keeping the resulting nulls
		playerData.values().removeIf(Objects::isNull);
	}

	@Override
	public void writeToStream(ByteBuf packet) {
		PlayerData.getSyncedVariables().forEach(v -> v.write(packet, playerData.get(v)));
	}

	@Override
	protected void execute(EntityPlayer player) {
		PlayerData data = PlayerData.get(player);
		if (player.world.isRemote && data != null){
			playerData.forEach(data::setVariable);
		}
	}
}
