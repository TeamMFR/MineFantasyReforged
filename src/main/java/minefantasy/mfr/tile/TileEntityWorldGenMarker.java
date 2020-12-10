package minefantasy.mfr.tile;

import minefantasy.mfr.config.ConfigWorldGen;
import minefantasy.mfr.world.gen.structure.StructureModuleMFR;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.chunk.Chunk;

public class TileEntityWorldGenMarker extends TileEntity {
    public String className, type;
    public int ticks = 0, length = 0, deviation = 0, prevID, prevMeta;

    @Override
    public void markDirty() {
        if (!world.isRemote && areChunksLoaded() && ticks >= getSpawnTime()) {
            Block block = Block.getBlockById(prevID);
            world.setBlockState(pos, (IBlockState) (block != null ? block : Blocks.AIR), 2);
            StructureModuleMFR.placeStructure(className, type, length, deviation, world, pos, this.getBlockMetadata());
        }

        ++ticks;
    }

    private boolean areChunksLoaded() {
        return chunkLoaded(0, 0) && chunkLoaded(-16, 0) && chunkLoaded(16, 0) && chunkLoaded(0, -16)
                && chunkLoaded(0, 16);
    }

    private boolean chunkLoaded(int x, int z) {
        Chunk chunk = world.getChunkFromBlockCoords(pos);
        return chunk != null && chunk.isLoaded();
    }

    private int getSpawnTime() {
        return ConfigWorldGen.structureTickRate;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        className = nbt.getString("ClassDirectory");
        type = nbt.getString("SubType");
        length = nbt.getInteger("LengthPosition");
        deviation = nbt.getInteger("Deviation");
        prevID = nbt.getInteger("prevID");
        prevMeta = nbt.getInteger("prevMeta");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setString("ClassDirectory", className);
        nbt.setString("SubType", type);
        nbt.setInteger("LengthPosition", length);
        nbt.setInteger("prevID", prevID);
        nbt.setInteger("prevMeta", prevMeta);
        nbt.setInteger("Deviation", deviation);
        return nbt;
    }
}
