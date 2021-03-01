package minefantasy.mfr.network;

import net.minecraftforge.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import minefantasy.mfr.mechanics.knowledge.InformationBase;
import minefantasy.mfr.mechanics.knowledge.InformationList;
import minefantasy.mfr.mechanics.knowledge.ResearchLogic;
import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;

public class ResearchRequest extends PacketMF {
    public static final String packetName = "MF2_RequestResearch";
    private EntityPlayer user;
    private int researchID;
    private UUID username;

    public ResearchRequest(EntityPlayer user, int id) {
        this.researchID = id;
        this.username = user.getCommandSenderEntity().getUniqueID();
        this.user = user;
    }

    public ResearchRequest() {
    }

    @Override
    public void process(ByteBuf packet, EntityPlayer player) {
        researchID = packet.readInt();
        username = UUID.fromString(ByteBufUtils.readUTF8String(packet));

        if (username != null && player.getCommandSenderEntity().equals(username)) {
            InformationBase research = InformationList.knowledgeList.get(researchID);
            if (player != null && research != null && research.isEasy()) {
                if (!player.world.isRemote) {
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
        ByteBufUtils.writeUTF8String(packet, username.toString());
    }
}
