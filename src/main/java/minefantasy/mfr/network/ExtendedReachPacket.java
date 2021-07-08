package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.api.weapon.IExtendedReachWeapon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class ExtendedReachPacket extends PacketMF {
	private int entityID;

	public ExtendedReachPacket(int entityID) {
		this.entityID = entityID;
	}

	public ExtendedReachPacket() {
	}

	@Override
	public void readFromStream(ByteBuf packet) {
		entityID = packet.readInt();
	}

	@Override
	public void writeToStream(ByteBuf packet) {
		packet.writeInt(entityID);
	}

	@Override
	protected void execute(EntityPlayer player) {
		Entity entity = player.world.getEntityByID(entityID);
		if (entity != null && !player.world.isRemote){
			if (player.getHeldItemMainhand().isEmpty()) {
				return;
			}
			if (player.getHeldItemMainhand().getItem() instanceof IExtendedReachWeapon) {
				IExtendedReachWeapon extendedReachWeapon = (IExtendedReachWeapon) player.getHeldItemMainhand().getItem();
				double distanceSq = player.getDistanceSq(entity);
				float reach = extendedReachWeapon.getReachModifierInBlocks() + 4;
				double reachSq = reach * reach;
				if (reachSq >= distanceSq) {

					player.attackTargetEntityWithCurrentItem(entity);
				}
			}
		}
	}
}
