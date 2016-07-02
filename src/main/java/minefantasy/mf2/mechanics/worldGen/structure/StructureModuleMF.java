package minefantasy.mf2.mechanics.worldGen.structure;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class StructureModuleMF {
	public final int xCoord;
	public final int yCoord;
	public final int zCoord;
	public final int direction;
	protected final World worldObj;
	
	public StructureModuleMF(World world, int x, int y, int z, int d)
	{
		worldObj = world;
		xCoord = x;
		yCoord = y;
		zCoord = z;
		direction = d;
	}
	public StructureModuleMF(World world, int x, int y, int z)
	{
		this(world, x, y, z, 0);
	}
	
	public abstract void generate();
	
	public int rotateLeft()
	{
		switch(direction)
		{
		case 0:
			return 3;
		case 1:
			return 0;
		case 2:
			return 1;
		case 3:
			return 2;
		}
		return 0;
	}
	
	public int rotateRight()
	{
		switch(direction)
		{
		case 0:
			return 1;
		case 1:
			return 2;
		case 2:
			return 3;
		case 3:
			return 0;
		}
		return 0;
	}
	
	public int reverse()
	{
		switch(direction)
		{
		case 0:
			return 2;
		case 1:
			return 3;
		case 2:
			return 0;
		case 3:
			return 1;
		}
		return 0;
	}
	
	/**
	 * Places a block and rotates it on it's base direction
	 * @param id The blockID
	 * @param meta the block metadata
	 * @param x The x offset(relation to xCoord)
	 * @param y The y offset(relation to yCoord)
	 * @param z The z offset(relation to zCoord)
	 */
	public void placeBlock(Block id, int meta, int x, int y, int z)
	{
		placeBlock(id, meta, x, y, z, direction);
	}
	
	/**
	 * Places a block and rotates it on direction
	 * @param id The blockID
	 * @param meta the block metadata
	 * @param xo The x offset(relation to xCoord)
	 * @param yo The y offset(relation to yCoord)
	 * @param zo The z offset(relation to zCoord)
	 * @param dir The direction to face
	 */
	public void placeBlock(Block id, int meta, int xo, int yo, int zo, int dir)
	{
		int[] offset = offsetPos(xo, yo, zo, dir);
		worldObj.setBlock(offset[0], offset[1], offset[2], id, meta, 2);
	}
	
	protected void buildFoundation(Block block, int meta, int x, int z) 
	{
		buildFoundation(block, meta, x, z, 32, 0, false);
	}
	/**
	 * Builds a foundation column from -1y
	 * @param block The block used
	 * @param meta metadata used
	 * @param x the relative x position
	 * @param z the relative z position
	 * @param max the max depth
	 * @param penetrance how many solid blocks can it pass through
	 * @param relative whether or not it must be stopped by concecutive blocks.
	 */
	protected void buildFoundation(Block block, int meta, int x, int z, int max, int penetrance, boolean relative) 
	{
		int init_penetrance = penetrance;
		int y = -1;
		while(-y < max)
		{
			Block current = getBlock(x, y, z, direction);
			boolean empty = current.getMaterial().isReplaceable();
			
			if(isUnbreakable(x, y, z, direction))
			{
				return;
			}
			if(!empty)
			{
				-- penetrance;
			}
			else if(!relative)
			{
				penetrance = init_penetrance;
			}
			
			if(empty || penetrance >= 0)
			{
				placeBlock(block, meta, x, y, z);
				--y;
			}
			else
			{
				return;
			}
		}
	}
	
	public TileEntity getTileEntity(int xo, int yo, int zo, int dir)
	{
		int[] offset = offsetPos(xo, yo, zo, dir);
		return worldObj.getTileEntity(offset[0], offset[1], offset[2]);
	}
	
	public Block getBlock(int x, int y, int z)
	{
		return getBlock(x, y, z, direction);
	}
	public Block getBlock(int xo, int yo, int zo, int dir)
	{
		int[] offset = offsetPos(xo, yo, zo, dir);
		return worldObj.getBlock(offset[0], offset[1], offset[2]);
	}
	public boolean isUnbreakable(int xo, int yo, int zo, int dir)
	{
		return getHardness(xo, yo, zo, dir) < 0;
	}
	public float getHardness(int xo, int yo, int zo, int dir)
	{
		int[] offset = offsetPos(xo, yo, zo, dir);
		return worldObj.getBlock(offset[0], offset[1], offset[2]).getBlockHardness(worldObj, offset[0], offset[1], offset[2]);
	}
	
	public void notifyBlock(Block id, int xo, int yo, int zo, int dir)
	{
		int[] offset = offsetPos(xo, yo, zo, dir);
		worldObj.notifyBlockChange(offset[0], offset[1], offset[2], id);
	}
	
	public int[] offsetPos(int xo, int yo, int zo, int dir)
	{
		int u = 2;
		int x = xCoord;
		int y = yCoord+yo;
		int z = zCoord;
		
		switch(dir)
		{
		case 0://NORTH
			x += xo;
			z += zo;
			break;
			
		case 1://EAST
			x -= zo;
			z += xo;
			break;
			
		case 2://SOUTH
			x -= xo;
			z -= zo;
			break;
			
		case 3://WEST
			x += zo;
			z -= xo;
			break;
		}
		
		
		return new int[]{x, y, z};
	}
}
