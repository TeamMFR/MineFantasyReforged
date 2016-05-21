package minefantasy.mf2.block.tileentity;

import java.util.List;
import java.util.Random;

import minefantasy.mf2.api.crafting.IHeatSource;
import minefantasy.mf2.api.crafting.IHeatUser;
import minefantasy.mf2.api.refine.BigFurnaceRecipes;
import minefantasy.mf2.network.packet.BigFurnacePacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

public class TileEntityBigFurnace extends TileEntity implements IInventory, IHeatUser
{
	public ItemStack[] items = new ItemStack[2];
	public float progress;
	public float maxProgress;
	private int tempTicksExisted = 0;
	private Random rand = new Random();
	private int ticksExisted;
	
	public TileEntityBigFurnace()
	{
	}
	private Object recipe;
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		++tempTicksExisted;
		if(tempTicksExisted == 10)
		{
			blockMetadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			updateRecipe();
		}
		int temp = getTemp();
		++ticksExisted;
		if(ticksExisted % 20 == 0 && !worldObj.isRemote)
		{
			if(getResult() != null && temp > 0 && maxProgress > 0 && temp > getMinTemperature())
			{
				progress += (temp/100F);
				if(progress >= maxProgress && smeltItem())
				{
					this.decrStackSize(0, 1);
					updateRecipe();
				}
			}
		}
	}
	private int getMinTemperature() 
	{
		if(recipe != null && recipe instanceof BigFurnaceRecipes)
		{
			return ((BigFurnaceRecipes)recipe).minTemperature;
		}
		return 150;
	}
	private int getSmeltTime() 
	{
		if(recipe != null && recipe instanceof BigFurnaceRecipes)
		{
			return ((BigFurnaceRecipes)recipe).time;
		}
		return 20;
	}
	private ItemStack getResult() 
	{
		if(recipe == null)return null;
		if(recipe != null && recipe instanceof BigFurnaceRecipes)
		{
			return ((BigFurnaceRecipes)recipe).output;
		}
		if(recipe instanceof ItemStack)
		{
			return (ItemStack)recipe;
		}
		return null;
	}
	
	private boolean smeltItem() 
	{
		ItemStack recipeItem = getResult();
		ItemStack currentSlot = getStackInSlot(1);
		if(currentSlot == null)
		{
			setInventorySlotContents(1, recipeItem.copy());
			return true;
		}
		if(currentSlot.isItemEqual(recipeItem) && (currentSlot.stackSize + recipeItem.stackSize) <= currentSlot.getMaxStackSize())
		{
			currentSlot.stackSize += recipeItem.stackSize;
			return true;
		}
		return false;
	}
	private int getTemp() 
	{
		TileEntity tile = worldObj.getTileEntity(xCoord, yCoord-1, zCoord);
		if(tile != null && tile instanceof IHeatSource)
		{
			return ((IHeatSource)tile).getHeat();
		}
		return 0;
	}
	public void updateRecipe()
	{
		recipe = BigFurnaceRecipes.getResult(getStackInSlot(0));
		if(recipe != null)
		{
			maxProgress = getSmeltTime();
		}
		else if(getStackInSlot(0) != null)
		{
			ItemStack item = FurnaceRecipes.smelting().getSmeltingResult(getStackInSlot(0));
			if(item != null && !(item.getItem() instanceof ItemFood))
			{
				recipe = item;
			}
		}
		progress = 0;
		sendPacketToClients();
	}
	public int getProgressBar(int i) 
	{
		if(maxProgress <= 0)return 0;
		return (int)((float)i / maxProgress * progress);
	}
	public String getResultName() 
	{
		if(getResult() != null)
		{
			return getResult().getDisplayName();
		}
		return "";
	}
	
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		progress = nbt.getFloat("Progress");
        maxProgress = nbt.getFloat("maxProgress");
		
		NBTTagList savedItems = nbt.getTagList("Items", 10);

        for (int i = 0; i < savedItems.tagCount(); ++i)
        {
            NBTTagCompound savedSlot = savedItems.getCompoundTagAt(i);
            byte slotNum = savedSlot.getByte("Slot");

            if (slotNum >= 0 && slotNum < items.length)
            {
            	items[slotNum] = ItemStack.loadItemStackFromNBT(savedSlot);
            }
        }
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setFloat("Progress", progress);
        nbt.setFloat("maxProgress", maxProgress);
        
		NBTTagList savedItems = new NBTTagList();
		
        for (int i = 0; i < items.length; ++i)
        {
            if (items[i] != null)
            {
                NBTTagCompound savedSlot = new NBTTagCompound();
                savedSlot.setByte("Slot", (byte)i);
                items[i].writeToNBT(savedSlot);
                savedItems.appendTag(savedSlot);
            }
        }

        nbt.setTag("Items", savedItems);
	}
	
	//INVENTORY
	public void onInventoryChanged() 
	{
	}
	
	@Override
	public int getSizeInventory()
	{
		return items.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return items[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int num)
    {
		onInventoryChanged();
        if (this.items[slot] != null)
        {
            ItemStack itemstack;

            if (this.items[slot].stackSize <= num)
            {
                itemstack = this.items[slot];
                this.items[slot] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.items[slot].splitStack(num);

                if (this.items[slot].stackSize == 0)
                {
                    this.items[slot] = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		return items[slot];
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack item)
	{
		onInventoryChanged();
		items[slot] = item;
	}

	@Override
	public String getInventoryName()
	{
		return "tile.roast.name";
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer user)
	{
		return user.getDistance(xCoord+0.5D, yCoord+0.5D, zCoord+0.5D) < 8D;
	}

	@Override
	public void openInventory()
	{
	}

	@Override
	public void closeInventory()
	{
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item)
	{
		return false;
	}
	
	@Override
	public boolean canAccept(TileEntity tile) 
	{
		return true;
	}
	
	private void sendPacketToClients() 
	{
		if(worldObj.isRemote)return;
		
		List<EntityPlayer> players = ((WorldServer)worldObj).playerEntities;
		for(int i = 0; i < players.size(); i++)
		{
			EntityPlayer player = players.get(i);
			((WorldServer)worldObj).getEntityTracker().func_151248_b(player, new BigFurnacePacket(this).generatePacket());
		}
	}
}
