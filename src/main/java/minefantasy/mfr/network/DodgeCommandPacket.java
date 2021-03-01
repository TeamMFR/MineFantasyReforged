package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.mechanics.CombatMechanics;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.UUID;

public class DodgeCommandPacket extends PacketMF {
	private EntityPlayer user;
	private int ID;
	private UUID username;

	public DodgeCommandPacket(EntityPlayer user, int id) {
		this.ID = id;
		this.username = user.getUniqueID();
		this.user = user;
	}

	public DodgeCommandPacket() {
	}

	@Override
	public void readFromStream(ByteBuf packet) {
		ID = packet.readInt();
		username = UUID.fromString(ByteBufUtils.readUTF8String(packet));
	}

	@Override
	public void writeToStream(ByteBuf packet) {
		packet.writeInt(ID);
		ByteBufUtils.writeUTF8String(packet, username.toString());
	}

	@Override
	protected void execute() {
		if (user.getUniqueID() == username) {
			CombatMechanics.initDodge(user, ID);
		}
	}
}
