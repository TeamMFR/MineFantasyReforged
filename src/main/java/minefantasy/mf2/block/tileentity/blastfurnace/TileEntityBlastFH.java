package minefantasy.mf2.block.tileentity.blastfurnace;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraftforge.common.util.ForgeDirection;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.MineFantasyAPI;
import minefantasy.mf2.block.refining.BlockBFH;
import minefantasy.mf2.block.tileentity.TileEntityCrucible;
import minefantasy.mf2.config.ConfigHardcore;
import minefantasy.mf2.entity.EntityFireBlast;
import minefantasy.mf2.item.list.ComponentListMF;

public class TileEntityBlastFH extends TileEntityBlastFC
{
	public int fuel;
	public int maxFuel;
	public float heat;
	public float progress;
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		
		boolean wasBurning = isBurning();
		if(fuel > 0)
		{
			--fuel;
		}
		if(isBuilt && smokeStorage < getMaxSmokeStorage())
		{
			if(fuel > 0)
			{
				++progress;
				float maxProgress = getMaxProgress();
				if(progress >= maxProgress)
				{
					progress -= maxProgress;
					smeltItem();
				}
				if(ticksExisted % 10 == 0)
				{
					smokeStorage+=2;
				}
			}
			else if(!worldObj.isRemote)
			{
				if(isFuel(items[0]))
				{
					fuel = maxFuel = getItemBurnTime(items[0]);
					decrStackSize(0, 1);
				}
			}
		}
		if(fireTime > 0)
		{
			if(fuel > 0)--fuel;
			smokeStorage++;
			fireTime--;
			if(ticksExisted % 2 == 0)
			shootFire();
			
		}
		if (!worldObj.isRemote && wasBurning != isBurning())
        {
            BlockBFH.updateFurnaceBlockState(isBurning(), this.worldObj, this.xCoord, this.yCoord, this.zCoord);
        }
	}
	public static float maxProgress = 1200;
	private float getMaxProgress() 
	{
		return maxProgress;
	}
	public static int maxFurnaceHeight = 8;
	private void smeltItem() 
	{
		for(int y = 0; y < maxFurnaceHeight; y++)
		{
			TileEntity tileEntity = worldObj.getTileEntity(xCoord, yCoord+y+1, zCoord);
			if(tileEntity != null && tileEntity instanceof TileEntityBlastFC && !(tileEntity instanceof TileEntityBlastFH))
			{
				ItemStack result = getSmeltedResult((TileEntityBlastFC) tileEntity, y+1);
				if(result != null)
				{
					dropItem(result);
				}
			}
			else
			{
				break;
			}
		}
		fireTime = 20;
		worldObj.playSoundEffect(xCoord+0.5, yCoord+0.5, zCoord+0.5, "random.fizz", 2.0F, 0.5F);
		worldObj.playSoundEffect(xCoord+0.5, yCoord+0.25, zCoord+0.5, "fire.fire", 1.0F, 0.75F);
		startFire(1, 0, 0);
		startFire(-1, 0, 0);
		startFire(0, 0, 1);
		startFire(0, 0, -1);
	}
	
	private void dropItem(ItemStack result)
	{
		TileEntity under = worldObj.getTileEntity(xCoord, yCoord-1, zCoord);
		if(under != null && under instanceof TileEntityCrucible)
		{
			TileEntityCrucible crucible = (TileEntityCrucible)under;
			int slot = crucible.getSizeInventory()-1;
			{
				if(crucible.getStackInSlot(slot) == null)
				{
					crucible.setInventorySlotContents(slot, result);
					return;
				}
				else if(crucible.getStackInSlot(slot).isItemEqual(result))
				{
					int freeSpace = crucible.getStackInSlot(slot).getMaxStackSize() - crucible.getStackInSlot(slot).stackSize;
					if(freeSpace >= result.stackSize)
					{
						crucible.getStackInSlot(slot).stackSize += result.stackSize;
						return;
					}
					else
					{
						crucible.getStackInSlot(slot).stackSize += freeSpace;
						result.stackSize -= freeSpace;
					}
				}
			}
		}
		if(result.stackSize <= 0)return;
		
		if(ConfigHardcore.HCCreduceIngots && rand.nextInt(3) == 0)
		{
			EntityItem entity = new EntityItem(worldObj, xCoord+0.5, yCoord+0.5, zCoord+0.5, result);
			worldObj.spawnEntityInWorld(entity);
		}
	}
	private void startFire(int x, int y, int z) 
	{
		if(!worldObj.isRemote && worldObj.isAirBlock(x, y, z))
		{
			worldObj.setBlock(xCoord+x, yCoord+y, zCoord+z, Blocks.fire);
		}
	}
	private void shootFire()
	{
		if(!worldObj.isRemote)
		{
			shootFire(-1, 0, 0);
			shootFire(1, 0, 0);
			shootFire(0, 0, -1);
			shootFire(0, 0, 1);
		}
	}
	private void shootFire(int x, int y, int z)
	{
		double v = 0.125D;
		EntityFireBlast fireball = new EntityFireBlast(worldObj, xCoord+0.5+x, yCoord, zCoord+0.5+z, (double)x*v, (double)y*v, (double)z*v);
		fireball.getEntityData().setString("Preset", "BlastFurnace");
		fireball.modifySpeed(0.5F);
		worldObj.spawnEntityInWorld(fireball);
	}
	
	private ItemStack getSmeltedResult(TileEntityBlastFC shaft, int y) 
	{
		if(shaft.getIsBuilt())
		{
			ItemStack input = shaft.getStackInSlot(1);
			if(shaft.getStackInSlot(0) == null || !isCoal(shaft.getStackInSlot(0)))
			{
				return null;
			}
			if(input != null)
			{
				ItemStack result = getResult(input);
				if(result != null)
				{
					for(int a = 0; a < shaft.getSizeInventory(); a++)
					{
						shaft.decrStackSize(a, 1);
					}
					return result;
				}
			}
		}
		return null;
	}
	@Override
	protected void interact(TileEntityBlastFC tile)
	{
		
	}
	@Override
	public void updateBuild()
	{
		isBuilt = getIsBuilt();
	}
	@Override
	protected boolean getIsBuilt() 
	{
		return (isFirebrick(-1, 0, -1) && isFirebrick(1, 0, -1) && isFirebrick(-1, 0, 1) && isFirebrick(1, 0, -1)) && (isAir(-1, 0, 0) && isAir(1, 0, 0) && isAir(0, 0, -1) && isAir(0, 0, 1));
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		nbt.setFloat("progress", progress);
		nbt.setInteger("fuel", fuel);
		nbt.setInteger("maxFuel", maxFuel);
	}
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		progress = nbt.getFloat("progress");
		fuel = nbt.getInteger("fuel");
		maxFuel = nbt.getInteger("maxFuel");
	}
	public static boolean isFuel(ItemStack item)
	{
		return getItemBurnTime(item) > 0;
	}
	@Override
	public int[] getAccessibleSlotsFromSide(int side) 
	{
		return side > 0 ? new int[]{0} : new int[]{};
	}
	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side)
	{
		return side != 0 && isItemValidForSlot(slot, item);
	}
	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side)
	{
		return false;
	}
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item)
	{
		if(slot == 0)
		{
			return isFuel(item);
		}
		return false;
	}
	
	public static int getItemBurnTime(ItemStack fuel)
    {
        return MineFantasyAPI.getFuelValue(fuel)/4;
    }

	public boolean isBurning()
	{
		return fuel > 0;
	}

	public int getBurnTimeRemainingScaled(int i) 
	{
		if (this.maxFuel <= 0)
        {
			return 0;
        }

        return fuel * i / maxFuel;
	}
	
	@Override
	public int getSizeInventory()
	{
		return 1;
	}
	@Override
	public int getMaxSmokeStorage() 
	{
		return 10;
	}
}
