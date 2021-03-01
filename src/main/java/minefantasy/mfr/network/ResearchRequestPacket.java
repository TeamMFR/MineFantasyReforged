package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.mechanics.knowledge.InformationBase;
import minefantasy.mfr.mechanics.knowledge.InformationList;
import minefantasy.mfr.mechanics.knowledge.ResearchLogic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.UUID;

public class ResearchRequestPacket extends PacketMF {
	private int researchID;
	private UUID username;

	public ResearchRequestPacket(EntityPlayer user, int id) {
		this.researchID = id;
		this.username = user.getUniqueID();
	}

	public ResearchRequestPacket() {
	}

	@Override
	public void readFromStream(ByteBuf packet) {
		researchID = packet.readInt();
		username = UUID.fromString(ByteBufUtils.readUTF8String(packet));
	}

	@Override
	public void writeToStream(ByteBuf packet) {
		packet.writeInt(researchID);
		ByteBufUtils.writeUTF8String(packet, username.toString());
	}

	@Override
	protected void execute(EntityPlayer player) {
		if (username != null && player != null && player.getUniqueID().equals(username)) {
			InformationBase research = InformationList.knowledgeList.get(researchID);
			if (research != null && research.isEasy()) {
				if (!player.world.isRemote) {
					if (research.onPurchase(player)) {
						ResearchLogic.syncData(player);
					}
				}
			}
		}
	}
}
