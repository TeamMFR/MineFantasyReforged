package minefantasy.mfr.tile;

import minefantasy.mfr.config.ConfigWorldGen;
import minefantasy.mfr.container.ContainerBase;
import minefantasy.mfr.world.gen.structure.StructureModuleMFR;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityWorldGenMarker extends TileEntityBase implements ITickable {
	public String className, type;
	public int ticks = 0, length = 0, deviation = 0, prevID, prevFacing;

	@Override
	public void update() {
		if (!world.isRemote && chunkLoaded() && ticks >= getSpawnTime()) {
			Block block = Block.getBlockById(prevID);
			world.setBlockState(pos, (block != null ? block.getDefaultState() : Blocks.AIR.getDefaultState()), 2);

			StructureModuleMFR.placeStructure(className, type, length, deviation, world, pos, EnumFacing.getFront(prevFacing), world.rand);
		}

		++ticks;
	}

	private boolean chunkLoaded() {
		Chunk chunk = world.getChunkFromBlockCoords(pos);
		return chunk != null && chunk.isLoaded();
	}

	private int getSpawnTime() {
		return ConfigWorldGen.structureTickRate;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		className = nbt.getString("class_directory");
		type = nbt.getString("sub_type");
		length = nbt.getInteger("length_position");
		deviation = nbt.getInteger("deviation");
		prevID = nbt.getInteger("prev_id");
		prevFacing = nbt.getInteger("prev_facing");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setString("class_directory", className);
		nbt.setString("sub_type", type);
		nbt.setInteger("length_position", length);
		nbt.setInteger("prev_id", prevID);
		nbt.setInteger("prev_facing", prevFacing);
		nbt.setInteger("deviation", deviation);
		return nbt;
	}

	@Override
	protected ItemStackHandler createInventory() {
		return null;
	}

	@Override
	public ItemStackHandler getInventory() {
		return null;
	}

	@Override
	public ContainerBase createContainer(EntityPlayer player) {
		return null;
	}

	@Override
	protected int getGuiId() {
		return 0;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		return false;
	}
}
