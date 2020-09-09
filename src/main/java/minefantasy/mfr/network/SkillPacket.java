package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.api.rpg.RPGElements;
import minefantasy.mfr.api.rpg.Skill;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.UUID;

public class SkillPacket extends PacketMF {
    private UUID username;
    private int level, xp, xpMax;
    private String skillName;
    private String name;
    private int skillLevel;
    private int skillXP;
    private int skillMaxXP;

    public SkillPacket(EntityPlayer player, Skill skill) {
        this.username = player.getUniqueID();
        NBTTagCompound skilltag = RPGElements.getSkill(player, skill.skillName);
        skillName = skill.skillName;
        level = skilltag.getInteger("level");
        xp = skilltag.getInteger("xp");
        xpMax = skilltag.getInteger("xpMax");
    }

    public SkillPacket() {
    }

    @Override
    public void readFromStream(ByteBuf packet) {
        name = ByteBufUtils.readUTF8String(packet);
        skillLevel = packet.readInt();
        skillXP = packet.readInt();
        skillMaxXP = packet.readInt();
        username = UUID.fromString(ByteBufUtils.readUTF8String(packet));
    }


    @Override
    public void writeToStream(ByteBuf packet) {
        ByteBufUtils.writeUTF8String(packet, skillName);
        packet.writeInt(level);
        packet.writeInt(xp);
        packet.writeInt(xpMax);
        ByteBufUtils.writeUTF8String(packet, username.toString());
    }

    @Override
    protected void execute(EntityPlayer player) {
        if (username != null && player.getCommandSenderEntity() != null && player.getUniqueID() == username) {
            NBTTagCompound tag = RPGElements.getSkill(player, name);
            tag.setInteger("level", skillLevel);
            tag.setInteger("xp", skillXP);
            tag.setInteger("xpMax", skillMaxXP);
        }
    }
}
