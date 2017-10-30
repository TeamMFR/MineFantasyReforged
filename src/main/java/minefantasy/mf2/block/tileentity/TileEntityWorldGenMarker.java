package minefantasy.mf2.block.tileentity;

import minefantasy.mf2.config.ConfigWorldGen;
import minefantasy.mf2.mechanics.worldGen.structure.StructureModuleMF;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.chunk.Chunk;

public class TileEntityWorldGenMarker extends TileEntity {
    public String className, type;
    public int ticks = 0, length = 0, deviation = 0, prevID, prevMeta;

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote && areChunksLoaded() && ticks >= getSpawnTime()) {
            Block block = Block.getBlockById(prevID);
            worldObj.setBlock(xCoord, yCoord, zCoord, block != null ? block : Blocks.air, prevMeta, 2);
            StructureModuleMF.placeStructure(className, type, length, deviation, worldObj, xCoord, yCoord, zCoord,
                    this.getBlockMetadata());
        }

        ++ticks;
    }

    private boolean areChunksLoaded() {
        return chunkLoaded(0, 0) && chunkLoaded(-16, 0) && chunkLoaded(16, 0) && chunkLoaded(0, -16)
                && chunkLoaded(0, 16);
    }

    private boolean chunkLoaded(int x, int z) {
        Chunk chunk = worldObj.getChunkFromBlockCoords(x, z);
        return chunk != null && chunk.isChunkLoaded;
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
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setString("ClassDirectory", className);
        nbt.setString("SubType", type);
        nbt.setInteger("LengthPosition", length);
        nbt.setInteger("prevID", prevID);
        nbt.setInteger("prevMeta", prevMeta);
        nbt.setInteger("Deviation", deviation);
    }
}
