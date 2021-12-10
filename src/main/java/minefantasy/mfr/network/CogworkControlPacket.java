package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.entity.EntityCogwork;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class CogworkControlPacket extends PacketMF {
	private EntityCogwork suit;
	private float forward, strafe;
	private boolean isJumping;
	private int entityID;

	public CogworkControlPacket(EntityCogwork suit) {
		this.suit = suit;
		this.forward = suit.getMoveForward();
		this.strafe = suit.getMoveStrafe();
		this.isJumping = suit.getJumpControl();
	}

	public CogworkControlPacket() {
	}

	@Override
	public void readFromStream(ByteBuf packet) {
		entityID = packet.readInt();
		forward = packet.readFloat();
		strafe = packet.readFloat();
		isJumping = packet.readBoolean();
	}

	@Override
	public void writeToStream(ByteBuf packet) {
		packet.writeInt(suit.getEntityId());
		packet.writeFloat(forward);
		packet.writeFloat(strafe);
		packet.writeBoolean(isJumping);
	}

	@Override
	protected void execute(EntityPlayer player) {
		Entity entity = player.world.getEntityByID(entityID);

		if (entity instanceof EntityCogwork) {
			suit = (EntityCogwork) entity;
			suit.setMoveForward(forward);
			suit.setMoveStrafe(strafe);
			suit.setJumpControl(isJumping);
		}

	}
}
