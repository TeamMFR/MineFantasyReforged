package minefantasy.mfr.packet;

import net.minecraftforge.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import minefantasy.mfr.api.rpg.RPGElements;
import minefantasy.mfr.api.rpg.Skill;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.UUID;

public class SkillPacket extends PacketMF {
    public static final String packetName = "MF2_SkillSync";
    private EntityPlayer user;
    private UUID username;
    private int level, xp, xpMax;
    private String skillName;

    public SkillPacket(EntityPlayer user, Skill skill) {
        this.username = user.getCommandSenderEntity().getUniqueID();
        this.user = user;
        NBTTagCompound skilltag = RPGElements.getSkill(user, skill.skillName);
        skillName = skill.skillName;
        level = skilltag.getInteger("level");
        xp = skilltag.getInteger("xp");
        xpMax = skilltag.getInteger("xpMax");
    }

    public SkillPacket() {
    }

    @Override
    public void process(ByteBuf packet, EntityPlayer player) {
        String name = ByteBufUtils.readUTF8String(packet);
        int skillLvl = packet.readInt();
        int skillXp = packet.readInt();
        int skillMaxXp = packet.readInt();

        username = UUID.fromString(ByteBufUtils.readUTF8String(packet));
        if (username != null && player.getCommandSenderEntity() != null && player.getCommandSenderEntity().equals(username)) {
            NBTTagCompound tag = RPGElements.getSkill(player, name);
            tag.setInteger("level", skillLvl);
            tag.setInteger("xp", skillXp);
            tag.setInteger("xpMax", skillMaxXp);
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
        packet.writeInt(xp);
        packet.writeInt(xpMax);
        ByteBufUtils.writeUTF8String(packet, username.toString());
    }
}
