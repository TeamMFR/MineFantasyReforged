package minefantasy.mfr.network;

import io.netty.buffer.ByteBuf;
import minefantasy.mfr.tile.TileEntityCrossbowBench;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class CrossbowBenchPacket extends PacketMF {
	private BlockPos coords;
	private float progress;
	private float maxProgress;
	private boolean hasRecipe;
	private TileEntity tileEntity;
	private float newProgress;
	private float newMaxProgress;
	private boolean newRecipe;

	public CrossbowBenchPacket(TileEntityCrossbowBench tile) {
		tileEntity = tile;
		coords = new BlockPos(tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ());
		progress = tile.progress;
		maxProgress = tile.maxProgress;
		hasRecipe = tile.hasRecipe;
	}

	public CrossbowBenchPacket() {
	}

	@Override
	public void readFromStream(ByteBuf packet) {
		coords = BlockPos.fromLong(packet.readLong());
		newProgress = packet.readFloat();
		newMaxProgress = packet.readFloat();
		newRecipe = packet.readBoolean();
	}

	@Override
	public void writeToStream(ByteBuf packet) {
		packet.writeLong(coords.toLong());
		packet.writeFloat(progress);
		packet.writeFloat(maxProgress);
		packet.writeBoolean(hasRecipe);
	}

	@Override
	protected void execute() {
		if (tileEntity != null && tileEntity instanceof TileEntityCrossbowBench) {
			TileEntityCrossbowBench tile = (TileEntityCrossbowBench) tileEntity;
			tile.progress = newProgress;
			tile.maxProgress = newMaxProgress;
			tile.hasRecipe = newRecipe;
		}
	}
}
