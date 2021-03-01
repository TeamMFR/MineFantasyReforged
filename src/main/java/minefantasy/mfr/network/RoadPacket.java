package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.tile.TileEntityRoad;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class RoadPacket extends PacketMF {
	private BlockPos coords;
	private Block surface;
	private boolean isLocked;
	private boolean isRequest = false;
	private TileEntity tileEntity;
	private int block;

	public RoadPacket(TileEntityRoad tile) {
		tileEntity = tile;
		this.coords = tile.getPos();
		this.surface = tile.surface;
		this.isLocked = tile.isLocked;
	}

	public RoadPacket() {
	}

	@Override
	public void readFromStream(ByteBuf packet) {
		this.coords = new BlockPos(packet.readInt(), packet.readInt(), packet.readInt());
		isRequest = packet.readBoolean();
		block = packet.readInt();
		this.isLocked = packet.readBoolean();
		packet.clear();
	}

	@Override
	public void writeToStream(ByteBuf packet) {
		packet.writeLong(coords.toLong());
		packet.writeBoolean(isRequest);

		if (!isRequest) {
			packet.writeInt(Block.getIdFromBlock(surface));
			packet.writeBoolean(this.isLocked);
		}
	}

	protected void execute() {
		TileEntity entity = tileEntity;

		if (entity instanceof TileEntityRoad) {
			TileEntityRoad tile = (TileEntityRoad) entity;
			if (isRequest) {
				tile.sendPacketToClients();
			} else {
				tile.surface = Block.getBlockById(block);
				tile.isLocked = this.isLocked;

				tile.refreshSurface();
			}
		}
	}
}
