package minefantasy.mf2.network.packet;

import io.netty.buffer.ByteBuf;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.knowledge.InformationBase;
import minefantasy.mf2.api.knowledge.InformationList;
import minefantasy.mf2.api.knowledge.ResearchLogic;
import minefantasy.mf2.mechanics.CombatMechanics;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.ByteBufUtils;

public class DodgeCommand extends PacketMF
{
	public static final String packetName = "MF2_Command_Dodge";
	private EntityPlayer user;
	private int ID;
	private String username;

	public DodgeCommand(EntityPlayer user, int id)
	{
		this.ID = id;
		this.username = user.getCommandSenderName();
		this.user = user;
	}

	public DodgeCommand() {
	}

	@Override
	public void process(ByteBuf packet, EntityPlayer player) 
	{
		ID = packet.readInt();
		username = ByteBufUtils.readUTF8String(packet);
		
		if (username != null && player.getCommandSenderName().equals(username)) 
        {
            CombatMechanics.initDodge(player, ID);
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
		packet.writeInt(ID);
        ByteBufUtils.writeUTF8String(packet, username);
	}
}
