package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.tile.TileEntityForge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

public class ForgePacket extends PacketMF {
	private BlockPos coords;
	private int workableState;

	public ForgePacket(TileEntityForge tile) {
		coords = tile.getPos();
		workableState = tile.getWorkableState();
	}

	public ForgePacket() {
	}

	@Override
	protected void writeToStream(ByteBuf packet) {
		packet.writeLong(coords.toLong());
		packet.writeInt(workableState);
	}

	@Override
	protected void readFromStream(ByteBuf packet) {
		coords = BlockPos.fromLong(packet.readLong());
		workableState = packet.readInt();
	}

	@Override
	protected void execute(EntityPlayer player) {
		TileEntityForge tile = (TileEntityForge) player.world.getTileEntity(coords);
		if (tile != null) {
			tile.workableState = workableState;
		}
	}
}