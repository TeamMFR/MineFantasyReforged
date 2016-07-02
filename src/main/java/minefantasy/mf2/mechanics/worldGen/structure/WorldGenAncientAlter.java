package minefantasy.mf2.mechanics.worldGen.structure;

import java.util.Random;

import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;


public class WorldGenAncientAlter extends WorldGenStructureBase
{
	public WorldGenAncientAlter()
	{		
	}
	@Override
	protected StructureModuleMF getStartPiece(World world, int x, int y, int z, int direction)
	{
		return new StructureGenAncientAlter(world, x, y, z, direction);
	}
	@Override
	protected boolean canPlaceAt(World world, StructureModuleMF piece) 
	{
		if(world.getBlock(piece.xCoord, piece.yCoord, piece.zCoord).getMaterial().isSolid() && world.isAirBlock(piece.xCoord, piece.yCoord+1, piece.zCoord) && world.canBlockSeeTheSky(piece.xCoord, piece.yCoord+1, piece.zCoord))
		{
			return true;
		}
		return false;
	}
	@Override
	protected boolean isDirectionRandom() 
	{
		return true;
	}
}
