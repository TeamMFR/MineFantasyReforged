package minefantasy.mf2.mechanics.worldGen.structure;

import java.util.Random;

import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;


public abstract class WorldGenStructureBase extends WorldGenerator
{
	public WorldGenStructureBase()
	{		
	}
	@Override
	public boolean generate(World world, Random random, int i, int j, int k)
    {
		StructureModuleMF building;
		for(int y = 0; y < 255; y ++)
		{
	    	if(true)
	    	{
	    		if(isDirectionRandom())
	    		{
	    			building = getStartPiece(world, i, y+1, k, random.nextInt(4));
		    		if(building != null && canPlaceAt(world, building))
		    		{
		        		building.generate();
		        		return true;
		    		}
	    		}
	    		else
	    		{
		    		for(int d = 0; d < 4; d++)
		    		{
			    		building = getStartPiece(world, i, y+1, k, d);
			    		if(building != null && canPlaceAt(world, building))
			    		{
			        		building.generate();
			        		return true;
			    		}
		    		}
	    		}
	    	}
		}
        return false;
    }
	
	protected abstract boolean isDirectionRandom();
	protected abstract StructureModuleMF getStartPiece(World world, int x, int y, int z, int direction);
	protected abstract boolean canPlaceAt(World world, StructureModuleMF ruin) ;
}
