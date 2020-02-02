package minefantasy.mfr.packet;

import net.minecraftforge.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import minefantasy.mfr.api.knowledge.InformationBase;
import minefantasy.mfr.api.knowledge.InformationList;
import minefantasy.mfr.api.knowledge.ResearchLogic;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class KnowledgePacket extends PacketMF {
    public static final String packetName = "MF2_KnowledgeSync";
    private EntityPlayer user;
    private UUID username;

    public KnowledgePacket(EntityPlayer user) {
        this.username = user.getCommandSenderEntity().getUniqueID();
        this.user = user;
    }

    public KnowledgePacket() {
    }

    @Override
    public void process(ByteBuf packet, EntityPlayer player) {
        int size = InformationList.knowledgeList.size();
        ArrayList<Object[]> completed = new ArrayList<Object[]>();
        for (int a = 0; a < size; a++) {
            boolean unlocked = packet.readBoolean();
            int artefactCount = packet.readInt();

            InformationBase base = InformationList.knowledgeList.get(a);
            if (base != null) {
                completed.add(new Object[]{base, unlocked, artefactCount});
            }
        }
        username = UUID.fromString(ByteBufUtils.readUTF8String(packet));
        if (username != null && player.getCommandSenderEntity().equals(username)) {
            Iterator researches = completed.iterator();
            while (researches.hasNext()) {
                Object[] entry = (Object[]) researches.next();
                InformationBase base = (InformationBase) entry[0];
                ResearchLogic.setArtefactCount(base.getUnlocalisedName(), player, (Integer) entry[2]);
                if ((Boolean) entry[1]) {
                    ResearchLogic.forceUnlock(player, base);
                }
            }
        }
        packet.clear();
        completed = null;
    }

    @Override
    public String getChannel() {
        return packetName;
    }

    @Override
    public void write(ByteBuf packet) {
        int size = InformationList.knowledgeList.size();

        for (int a = 0; a < size; a++) {
            InformationBase base = InformationList.knowledgeList.get(a);
            packet.writeBoolean(ResearchLogic.hasInfoUnlocked(user, base));
            packet.writeInt(ResearchLogic.getArtefactCount(base.getUnlocalisedName(), user));
        }
        ByteBufUtils.writeUTF8String(packet, username.toString());
    }
}
