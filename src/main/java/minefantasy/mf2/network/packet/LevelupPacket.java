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
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.common.network.ByteBufUtils;

public class LevelupPacket extends PacketMF
{
	public static final String packetName = "MF2_levelup";
	private EntityPlayer user;
	private String username;
	private int level;
	private String skillName;

	public LevelupPacket(EntityPlayer user, Skill skill, int level)
	{
		this.username = user.getCommandSenderName();
		this.user = user;
		this.skillName = skill.skillName;
		this.level = level;
	}

	public LevelupPacket() {
	}

	@Override
	public void process(ByteBuf packet, EntityPlayer player) 
	{
		String name = ByteBufUtils.readUTF8String(packet);
		int skillLvl = packet.readInt();
		
		username = ByteBufUtils.readUTF8String(packet);
		if (username != null) 
        {
            EntityPlayer entity = player.worldObj .getPlayerEntityByName(username);
            if(entity != null && player == entity)
            {
            	Skill skill = RPGElements.getSkillByName(name);
            	
            	if(skill != null)
            	{
	            	player.playSound("random.levelup", 1.0F, 0.5F);
	        		player.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("rpg.levelup", skill.getDisplayName().toLowerCase(), skillLvl)));
            	}
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
        ByteBufUtils.writeUTF8String(packet, username);
	}
}
