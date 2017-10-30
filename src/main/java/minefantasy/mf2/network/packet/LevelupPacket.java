package minefantasy.mf2.network.packet;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import minefantasy.mf2.api.rpg.RPGElements;
import minefantasy.mf2.api.rpg.Skill;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;

public class LevelupPacket extends PacketMF {
    public static final String packetName = "MF2_levelup";
    private EntityPlayer user;
    private String username;
    private int level;
    private String skillName;

    public LevelupPacket(EntityPlayer user, Skill skill, int level) {
        this.username = user.getCommandSenderName();
        this.user = user;
        this.skillName = skill.skillName;
        this.level = level;
    }

    public LevelupPacket() {
    }

    @Override
    public void process(ByteBuf packet, EntityPlayer player) {
        String name = ByteBufUtils.readUTF8String(packet);
        int skillLvl = packet.readInt();

        username = ByteBufUtils.readUTF8String(packet);
        if (username != null && player.getCommandSenderName().equalsIgnoreCase(username)) {
            Skill skill = RPGElements.getSkillByName(name);

            if (skill != null) {
                player.playSound("random.levelup", 1.0F, 0.5F);
                player.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("rpg.levelup",
                        skill.getDisplayName().toLowerCase(), skillLvl)));
            }
        }
    }

    @Override
    public String getChannel() {
        return packetName;
    }

    @Override
    public void write(ByteBuf packet) {
        ByteBufUtils.writeUTF8String(packet, skillName);
        packet.writeInt(level);
        ByteBufUtils.writeUTF8String(packet, username);
    }
}
