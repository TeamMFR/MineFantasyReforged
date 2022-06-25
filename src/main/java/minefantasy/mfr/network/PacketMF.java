package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

import java.util.HashMap;
import java.util.function.Supplier;

public abstract class PacketMF {

	private static HashMap<Integer, Supplier<? extends PacketMF>> packetList = new HashMap<>();
	private static HashMap<Class<? extends PacketMF>, Integer> packetIDs = new HashMap<>();

	public static <T extends PacketMF> void registerPacket(int typeNum, Class<T> packetClz, Supplier<T> instantiate) {
		packetList.put(typeNum, instantiate);
		packetIDs.put(packetClz, typeNum);
	}

	public PacketMF() {
	}

	private void writeHeaderToStream(ByteBuf data) {
		data.writeByte(packetIDs.get(getClass()));
	}

	private static PacketMF readHeaderFromStream(ByteBuf data) {
		int typeNum = data.readByte();

		if (!packetList.containsKey(typeNum)) {
			throw new IllegalArgumentException("Unregistered packet id received - " + typeNum);
		}

		return packetList.get(typeNum).get();
	}

	protected abstract void writeToStream(ByteBuf data);

	protected abstract void readFromStream(ByteBuf data);

	protected void execute() {

	}

	@SuppressWarnings("squid:S1172") //used in overrides
	protected void execute(EntityPlayer player) {
		execute();
	}

	static PacketMF readPacket(ByteBuf data) {
		PacketMF pkt = readHeaderFromStream(data);
		pkt.readFromStream(data);
		return pkt;
	}

	final FMLProxyPacket getFMLPacket() {
		PacketBuffer buf = new PacketBuffer(Unpooled.buffer());
		writeHeaderToStream(buf);
		writeToStream(buf);
		return new FMLProxyPacket(buf, NetworkHandler.CHANNEL_NAME);
	}
}
