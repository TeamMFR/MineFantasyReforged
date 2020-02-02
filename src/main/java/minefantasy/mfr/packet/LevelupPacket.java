package minefantasy.mfr.packet;

import net.minecraft.init.SoundEvents;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import minefantasy.mfr.api.rpg.RPGElements;
import minefantasy.mfr.api.rpg.Skill;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;

import java.util.UUID;

public class LevelupPacket extends PacketMF {
    public static final String packetName = "MF2_levelup";
    private EntityPlayer user;
    private UUID username;
    private int level;
    private String skillName;

    public LevelupPacket(EntityPlayer user, Skill skill, int level) {
        this.username = user.getCommandSenderEntity().getUniqueID();
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


        username = UUID.fromString(ByteBufUtils.readUTF8String(packet));
        if (username != null && player.getCommandSenderEntity().getUniqueID().equals(username)) {
            Skill skill = RPGElements.getSkillByName(name);

            if (skill != null) {
                player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0F, 0.5F);
                player.sendMessage(new TextComponentString(I18n.translateToLocalFormatted("rpg.levelup",
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
        ByteBufUtils.writeUTF8String(packet, username.toString());
    }
}
