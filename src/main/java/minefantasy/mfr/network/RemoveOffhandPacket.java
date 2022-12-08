package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.UUID;

public class RemoveOffhandPacket extends PacketMF {
	private UUID username;

	public RemoveOffhandPacket(EntityPlayer user) {
		this.username = user.getUniqueID();
	}

	public RemoveOffhandPacket() {
	}

	@Override
	public void readFromStream(ByteBuf packet) {
		username = UUID.fromString(ByteBufUtils.readUTF8String(packet));
	}

	@Override
	public void writeToStream(ByteBuf packet) {
		ByteBufUtils.writeUTF8String(packet, username.toString());
	}

	@Override
	protected void execute(EntityPlayer player) {
		if (username != null && player.getUniqueID().equals(username)) {
			ItemStack offhandStack = player.getHeldItemOffhand();
			if (offhandStack.getMaxStackSize() == 1) {
				player.inventory.placeItemBackInInventory(player.world, offhandStack);
				player.inventory.offHandInventory.clear();
			}
		}
	}
}
