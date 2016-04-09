package minefantasy.mf2.network.packet;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.Iterator;

import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.rpg.RPGElements;
import minefantasy.mf2.api.rpg.Skill;
import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.ByteBufUtils;

public class SkillPacket extends PacketMF
{
	public static final String packetName = "MF2_SkillSync";
	private EntityPlayer user;
	private String username;
	private int level, xp, xpMax;
	private String skillName;

	public SkillPacket(EntityPlayer user, Skill skill)
	{
		this.username = user.getCommandSenderName();
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
	public void process(ByteBuf packet, EntityPlayer player) 
	{
		String name = ByteBufUtils.readUTF8String(packet);
		int skillLvl = packet.readInt();
		int skillXp = packet.readInt();
		int skillMaxXp = packet.readInt();
		
		username = ByteBufUtils.readUTF8String(packet);
		if (username != null) 
        {
            EntityPlayer entity = player.worldObj .getPlayerEntityByName(username);
            if(entity != null)
            {
            	MFLogUtil.logDebug("Skill Packet Recieved: " + name + " L" + skillLvl + " " + skillXp + "/" + skillMaxXp);
            	NBTTagCompound tag = RPGElements.getSkill(player, name);
            	tag.setInteger("level", skillLvl);
            	tag.setInteger("xp", skillXp);
            	tag.setInteger("xpMax", skillMaxXp);
            }
        }
	}

	@Override
	public String getChannel()
	{
		return packetName;
	}

	@Override
	public void write(ByteBuf packet) 
	{
		ByteBufUtils.writeUTF8String(packet, skillName);
		packet.writeInt(level);
		packet.writeInt(xp);
		packet.writeInt(xpMax);
        ByteBufUtils.writeUTF8String(packet, username);
	}
}
