package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.entity.EntityCogwork;
import net.minecraft.entity.Entity;

public class CogworkControlPacket extends PacketMF {
	private EntityCogwork suit;
	private float forward, strafe;
	private boolean isJumping;
	private Entity entityPlayer;
	private int id;

	public CogworkControlPacket(EntityCogwork suit, Entity entity) {
		entityPlayer = entity;
		this.suit = suit;
		this.forward = suit.getMoveForward();
		this.strafe = suit.getMoveStrafe();
		this.isJumping = suit.getJumpControl();
	}

	public CogworkControlPacket() {
	}

	@Override
	public void readFromStream(ByteBuf packet) {
		id = packet.readInt();
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
	protected void execute() {
		Entity entity = entityPlayer.world.getEntityByID(id);
		if (entity instanceof EntityCogwork) {
			suit = (EntityCogwork) entity;
			suit.setMoveForward(forward);
			suit.setMoveStrafe(strafe);
			suit.setJumpControl(isJumping);
		}
	}
}
