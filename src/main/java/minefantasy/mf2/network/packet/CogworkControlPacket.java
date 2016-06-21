package minefantasy.mf2.network.packet;

import io.netty.buffer.ByteBuf;
import minefantasy.mf2.api.knowledge.InformationBase;
import minefantasy.mf2.api.knowledge.InformationList;
import minefantasy.mf2.api.knowledge.ResearchLogic;
import minefantasy.mf2.entity.EntityCogwork;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class CogworkControlPacket extends PacketMF
{
	public static final String packetName = "MF2_CogworkCtrl";
	private EntityCogwork suit;
	private float forward, strafe;

	public CogworkControlPacket(EntityCogwork suit)
	{
		this.suit = suit;
		this.forward = suit.getMoveForward();
		this.strafe = suit.getMoveStrafe();
	}

	public CogworkControlPacket() {
	}

	@Override
	public void process(ByteBuf packet, EntityPlayer player) 
	{
		int id = packet.readInt();
		forward = packet.readFloat();
		strafe = packet.readFloat();
		Entity entity = player.worldObj.getEntityByID(id);
		
		if (entity != null && entity instanceof EntityCogwork) 
        {
			suit = (EntityCogwork)entity;
			suit.setMoveForward(forward);
			suit.setMoveStrafe(strafe);
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
		packet.writeInt(suit.getEntityId());
		packet.writeFloat(forward);
		packet.writeFloat(strafe);
	}
}
