package minefantasy.mf2.block.decor;

import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 * 
 * ROAD META: (0 and up)
 * Dirt, Sand, Cobblestone, Stone, Gravel
 */
public class BlockRoad extends Block
{
    
    public BlockRoad(String name, float f)
    {
        super(Material.ground);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f/16F, 1.0F);
        this.setLightOpacity(0);
        GameRegistry.registerBlock(this, name);
		setBlockName(name);
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
    	if(this == BlockListMF.lowroad)
    		return AxisAlignedBB.getBoundingBox((double)(x + 0), (double)(y + 0), (double)(z + 0), (double)(x + 1), (double)(y + 0.5), (double)(z + 1));
    	
        return AxisAlignedBB.getBoundingBox((double)(x + 0), (double)(y + 0), (double)(z + 0), (double)(x + 1), (double)(y + 1), (double)(z + 1));
    }
    
    @Override
    public IIcon getIcon(int side, int meta)
    {
    	if(meta == 2)
    	{
    		return BlockListMF.limestone.getIcon(side, 1);
    	}
    	if(meta == 1)
    	{
    		return Blocks.sand.getIcon(side, 0);
    	}
    	return Blocks.dirt.getIcon(side, 0);
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
    	this.updateTick(world, x, y, z, new Random());
    	super.onBlockAdded(world, x, y, z);
    }
    
    @Override
    public void updateTick(World world, int x, int y, int z, Random random)
    {
    	if(world.getBlock(x, y-1, z) == Blocks.grass)
    	{
    		world.setBlock(x, y-1, z, Blocks.dirt, 0, 2);
    	}
    	super.updateTick(world, x, y, z, random);
    }
    
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float f1, float f2)
    {
        ItemStack itemstack = player.getHeldItem();
        if(itemstack != null)
        {
        	if(!player.canPlayerEdit(x, y, z, i, itemstack))
        	{
        		return false;
        	}
        	
        	Block block = Block.getBlockFromItem(itemstack.getItem());
        	if(itemstack.getItem() instanceof ItemBlock && block != null)
    		{
        		if(upgradeRoad(world, x, y, z, 4, itemstack, block))
        		{
        			if(!player.capabilities.isCreativeMode && !world.isRemote)
        			{
        				itemstack.stackSize --;
        				if(itemstack.stackSize <= 0)
        				{
        					player.setCurrentItemOrArmor(0, null);
        				}
        			}
        			return true;
    			}
    		}
    		
    		if(itemstack.getItem() instanceof ItemSpade)
            {
    			if(this == BlockListMF.road)
    			{
    				if(!world.isRemote)
    				{
    					int m = world.getBlockMetadata(x, y, z);
    					world.setBlock(x, y, z, BlockListMF.lowroad, m, 2);
    				}
                	return true;
                }
        	}
        }
        return false;
    }
    /**
     * Resets the Texture
     * @param ID the block right clicked with
     * @return
     */
    private boolean upgradeRoad(World world, int x, int y, int z, int r, ItemStack held, Block block) 
    {
    	int nm = -1;
    	if(block == BlockListMF.limestone)
    	{
    		nm = 2;
    	}
    	if(block == Blocks.dirt)
    	{
    		nm = 0;
    	}
    	if(block == Blocks.sand)
    	{
    		nm = 1;
    	}
    	if(held == null)
    	{
    		return false;
    	}
    	if(nm < 0)
    	{
    		return false;
    	}
    	boolean flag = false;
    	
		for(int x2 = -r; x2 <= r; x2 ++)
		{
			for(int y2 = -r; y2 <= r; y2 ++)
			{
				for(int z2 = -r; z2 <= r; z2 ++)
				{
					Block id = world.getBlock(x+x2, y+y2, z+z2);
					int m = world.getBlockMetadata(x+x2, y+y2, z+z2);
					if((id == BlockListMF.road || id == BlockListMF.lowroad))
					{
						if(getDistance(x+x2, y+y2, z+z2, x, y, z) < r*1)
						{
							flag = true;
							{
								world.setBlockMetadataWithNotify(x+x2, y+y2, z+z2, nm, 2);
							}
						}
					}
				}
			}
		}
		return flag;
	}

	public double getDistance(double x, double y, double z, int posX, int posY, int posZ)
    {
        double var7 = posX - x;
        double var9 = posY - y;
        double var11 = posZ - z;
        return (double)MathHelper.sqrt_double(var7 * var7 + var9 * var9 + var11 * var11);
    }
}
