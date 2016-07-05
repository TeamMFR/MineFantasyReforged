package minefantasy.mf2.mechanics.worldGen.structure;

import java.util.Random;

import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;


public class WorldGenDwarvenStronghold extends WorldGenStructureBase
{
	public static final boolean debug_air = true;
	public WorldGenDwarvenStronghold()
	{		
	}
	@Override
	protected StructureModuleMF getStartPiece(World world, int x, int y, int z, int direction)
	{
		return new StructureGenDSEntry(world, x, y-1, z, direction);
	}
	@Override
	protected boolean isBlockAcceptableOrigin(World world, int x, int y, int z) 
	{
		return isValidGround(world, x, y, z) && world.canBlockSeeTheSky(x, y+2, z);
	}
	@Override
	protected boolean canStructureBuild(StructureModuleMF piece) 
	{
		if(debug_air)
		{
			return true;
		}
		//SEARCH FOR CLIFF
		for(int x = -3; x <= 3; x ++)
		{
			for(int y = 0; y < 5; y ++)
			{
				for(int z = 4; z <= 8; z ++)
				{
					int[] pos = piece.offsetPos(x, y, z, piece.direction);
					Material material = piece.worldObj.getBlock(pos[0], pos[1], pos[2]).getMaterial();
					
					if(!material.isOpaque())
					{
						return false;
					}
					if(!material.isSolid())
					{
						return false;
					}
				}
			}
		}
		return true;
	}
	@Override
	protected boolean isDirectionRandom() 
	{
		return false;
	}
	@Override
	protected int[] getYGenBounds(World world) {
		return new int[]{60, 255};
	}
}
