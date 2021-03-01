package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.tile.TileEntityTrough;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TroughPacket extends PacketMF {
	private BlockPos coords;
	private int fill;
	private TileEntity tileEntity;
	private int newFill;

	public TroughPacket(TileEntityTrough tile) {
		tileEntity = tile;
		coords = tile.getPos();
		fill = tile.fill;
	}

	public TroughPacket() {
	}

	@Override
	public void readFromStream(ByteBuf packet) {
		coords = BlockPos.fromLong(packet.readLong());
		newFill = packet.readInt();
	}

	@Override
	public void writeToStream(ByteBuf packet) {
		packet.writeLong(coords.toLong());
		packet.writeInt(fill);
	}

	@Override
	protected void execute() {
		if (tileEntity != null && tileEntity instanceof TileEntityTrough) {
			TileEntityTrough tile = (TileEntityTrough) tileEntity;
			tile.fill = newFill;
		}
	}
}
