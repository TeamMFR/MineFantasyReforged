package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.mechanics.knowledge.ResearchLogic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.UUID;

public class ArtefactPacket extends PacketMF {
	private EntityPlayer user;
	private String artefactKey;
	private UUID username;
	private boolean used;

	public ArtefactPacket(EntityPlayer user, String artefactKey) {
		this.username = user.getUniqueID();
		this.user = user;
		this.artefactKey = artefactKey;
	}

	public ArtefactPacket() {
	}

	@Override
	public void readFromStream(ByteBuf packet) {
		used = packet.readBoolean();
		artefactKey = ByteBufUtils.readUTF8String(packet);
		username = UUID.fromString(ByteBufUtils.readUTF8String(packet));
	}

	@Override
	public void writeToStream(ByteBuf packet) {
		packet.writeBoolean(ResearchLogic.alreadyUsedArtefact(user, artefactKey));
		ByteBufUtils.writeUTF8String(packet, artefactKey);
		ByteBufUtils.writeUTF8String(packet, username.toString());
	}

	@Override
	protected void execute(EntityPlayer player) {
		if (username != null && player != null && player.getUniqueID().equals(username)) {
			if (used) {
				ResearchLogic.addArtefactUsed(player, artefactKey);
			}
		}
	}
}
