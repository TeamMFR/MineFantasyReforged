package minefantasy.mf2.block.tileentity;

import minefantasy.mf2.config.ConfigWorldGen;
import minefantasy.mf2.mechanics.worldGen.structure.StructureModuleMF;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityWorldGenMarker extends TileEntity
{
	public String className;
	public int ticks = 0, length = 0, prevID, prevMeta;
	
	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote && ticks >= getSpawnTime())
		{
			Block block = Block.getBlockById(prevID);
			worldObj.setBlock(xCoord, yCoord, zCoord, block != null ? block : Blocks.air, prevMeta, 2);
			StructureModuleMF.placeStructure(className, length, worldObj, xCoord, yCoord, zCoord, this.getBlockMetadata());
		}
		++ticks;
	}
	private int getSpawnTime() 
	{
		return ConfigWorldGen.structureTickRate;
	}
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		className = nbt.getString("ClassDirectory");
		length = nbt.getInteger("LengthPosition");
		prevID = nbt.getInteger("prevID");
		prevMeta = nbt.getInteger("prevMeta");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		nbt.setString("ClassDirectory", className);
		nbt.setInteger("LengthPosition", length);
		nbt.setInteger("prevID", prevID);
		nbt.setInteger("prevMeta", prevMeta);
	}
}
