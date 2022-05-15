package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.item.ItemBowMFR;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.UUID;

public class OpenBowGUIPacket extends PacketMF {
	private UUID username;

	public OpenBowGUIPacket(EntityPlayer user) {
		this.username = user.getUniqueID();
	}

	public OpenBowGUIPacket() {
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
			ItemStack bowStack = player.getHeldItemMainhand();
			if (bowStack.getItem() instanceof ItemBowMFR) {
				((ItemBowMFR) bowStack.getItem()).reloadBow(player);
			}
		}
	}
}
