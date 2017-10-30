package minefantasy.mf2.network.packet;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import minefantasy.mf2.api.knowledge.InformationBase;
import minefantasy.mf2.api.knowledge.InformationList;
import minefantasy.mf2.api.knowledge.ResearchLogic;
import net.minecraft.entity.player.EntityPlayer;

public class ResearchRequest extends PacketMF {
    public static final String packetName = "MF2_RequestResearch";
    private EntityPlayer user;
    private int researchID;
    private String username;

    public ResearchRequest(EntityPlayer user, int id) {
        this.researchID = id;
        this.username = user.getCommandSenderName();
        this.user = user;
    }

    public ResearchRequest() {
    }

    @Override
    public void process(ByteBuf packet, EntityPlayer player) {
        researchID = packet.readInt();
        username = ByteBufUtils.readUTF8String(packet);

        if (username != null && player.getCommandSenderName().equals(username)) {
            InformationBase research = InformationList.knowledgeList.get(researchID);
            if (player != null && research != null && research.isEasy()) {
                if (!player.worldObj.isRemote) {
                    if (research.onPurchase(player)) {
                        ResearchLogic.syncData(player);
                    }
                }
            }
        }
    }

    @Override
    public String getChannel() {
        return packetName;
    }

    @Override
    public void write(ByteBuf packet) {
        packet.writeInt(researchID);
        ByteBufUtils.writeUTF8String(packet, username);
    }
}
