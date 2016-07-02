package minefantasy.mf2.mechanics.worldGen.structure;

import java.util.Random;

import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;


public class WorldGenAncientForge extends WorldGenStructureBase
{
	public WorldGenAncientForge()
	{		
	}
	@Override
	protected StructureModuleMF getStartPiece(World world, int x, int y, int z, int direction)
	{
		return new StructureGenAncientForgeEntry(world, x, y, z, direction);
	}
	@Override
	protected boolean canPlaceAt(World world, StructureModuleMF piece) 
	{
		if(world.getBlock(piece.xCoord, piece.yCoord, piece.zCoord).getMaterial().isSolid() && world.isAirBlock(piece.xCoord, piece.yCoord+1, piece.zCoord) && world.canBlockSeeTheSky(piece.xCoord, piece.yCoord+1, piece.zCoord))
		{
			//SEARCH FOR CLIFF
			for(int x = -1; x <= 1; x ++)
				for(int y = 0; y < 3; y ++)
				for(int z = 1; z <= 2; z ++)
				{
					int[] pos = piece.offsetPos(x, y, z, piece.direction);
					Material material = world.getBlock(pos[0], pos[1], pos[2]).getMaterial();
					
					if(!material.isOpaque())
					{
						return false;
					}
					if(!material.isSolid())
					{
						return false;
					}
				}
			return true;
		}
		return false;
	}
	@Override
	protected boolean isDirectionRandom() {
		return false;
	}
}
