package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.api.knowledge.InformationBase;
import minefantasy.mfr.api.knowledge.InformationList;
import minefantasy.mfr.api.knowledge.ResearchLogic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.UUID;

public class ResearchRequestPacket extends PacketMF {
    private EntityPlayer user;
    private int researchID;
    private UUID username;

    public ResearchRequestPacket(EntityPlayer user, int id) {
        this.researchID = id;
        this.username = user.getUniqueID();
        this.user = user;
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
        if (username != null && user.getUniqueID() == username) {
            InformationBase research = InformationList.knowledgeList.get(researchID);
            if (user != null && research != null && research.isEasy()) {
                if (!user.world.isRemote) {
                    if (research.onPurchase(user)) {
                        ResearchLogic.syncData(user);
                    }
                }
            }
        }
    }
}
