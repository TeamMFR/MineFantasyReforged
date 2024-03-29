package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.mechanics.knowledge.InformationBase;
import minefantasy.mfr.mechanics.knowledge.InformationList;
import minefantasy.mfr.mechanics.knowledge.ResearchLogic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.ArrayList;
import java.util.UUID;

public class KnowledgePacket extends PacketMF {
	private EntityPlayer user;
	private UUID username;
	private ArrayList<Object[]> completed;

	public KnowledgePacket(EntityPlayer user) {
		this.username = user.getUniqueID();
		this.user = user;
	}

	public KnowledgePacket() {
	}

	@Override
	public void readFromStream(ByteBuf packet) {
		int size = InformationList.knowledgeList.size();
		completed = new ArrayList<>();
		for (int a = 0; a < size; a++) {
			boolean unlocked = packet.readBoolean();
			int artefactCount = packet.readInt();

			InformationBase base = InformationList.knowledgeList.get(a);
			if (base != null) {
				completed.add(new Object[] {base, unlocked, artefactCount});
			}
		}
		username = UUID.fromString(ByteBufUtils.readUTF8String(packet));
	}

	@Override
	public void writeToStream(ByteBuf packet) {
		int size = InformationList.knowledgeList.size();

		for (int a = 0; a < size; a++) {
			InformationBase base = InformationList.knowledgeList.get(a);
			packet.writeBoolean(ResearchLogic.hasInfoUnlocked(user, base));
			packet.writeInt(ResearchLogic.getArtefactCount(base.getUnlocalisedName(), user));
		}
		ByteBufUtils.writeUTF8String(packet, username.toString());
	}

	@Override
	protected void execute(EntityPlayer player) {
		if (username != null && player != null && player.getUniqueID().equals(username)) {
			for (Object[] entry : completed) {
				InformationBase base = (InformationBase) entry[0];
				ResearchLogic.setArtefactCount(base.getUnlocalisedName(), player, (Integer) entry[2]);
				if ((Boolean) entry[1]) {
					ResearchLogic.forceUnlock(player, base);
				}
			}
		}
	}
}
