package minefantasy.mf2.block.tileentity;

import java.util.List;
import java.util.Random;

import minefantasy.mf2.api.crafting.IBasicMetre;
import minefantasy.mf2.api.knowledge.InformationBase;
import minefantasy.mf2.api.knowledge.InformationList;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.network.packet.ResearchTablePacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

public class TileEntityResearch extends TileEntity implements IInventory, IBasicMetre
{
	private ItemStack[] items = new ItemStack[1];
	public float progress;
	public float maxProgress;
	public int researchID = -1;
	
	public boolean interact(EntityPlayer user)
	{
		maxProgress = getMaxTime();
		if(maxProgress > 0)
		{
			addProgress(user);
			return true;
		}
		else
		{
			progress = 0;
		}
		return false;
	}
	private void addProgress(EntityPlayer user)
	{
		ItemStack held = user.getHeldItem();
		if(held != null && (held.getItem() == ComponentListMF.talisman_lesser || held.getItem() == ComponentListMF.talisman_greater))
		{
			progress = maxProgress;
			if(!user.capabilities.isCreativeMode && held.getItem() == ComponentListMF.talisman_lesser)
			{
				--held.stackSize;
				if(held.stackSize <= 0)
				{
					user.setCurrentItemOrArmor(0, null);
				}
			}
			return;
		}
		float efficiency = 10F/60F;//10s taken each swing
		if(user.swingProgress > 0 && user.swingProgress <= 1.0)
		{
			efficiency *= (0.5F-user.swingProgress);
		}
		worldObj.playSoundEffect(xCoord+0.5, yCoord+0.5, zCoord+0.5, "minefantasy2:block.flipPage", 1.0F, rand.nextFloat()*0.4F+0.8F);
		efficiency *= getEnvironmentBoost();
		progress += efficiency;
	}
	private float getEnvironmentBoost() 
	{
		int books = 0;
		for(int x = -8; x <= 8; x++)
		{
			for(int y = -8; y <= 8; y++)
			{
				for(int z = -8; z <= 8; z++)
				{
					if(worldObj.getBlock(xCoord + x, yCoord + y, zCoord + z) == Blocks.bookshelf)
					{
						++books;
					}
				}	
			}	
		}
		return 1.0F + (0.1F*books);
	}
	private Random rand = new Random();
	private void createComplete() 
	{
		if(items[0] != null && items[0].getItem() == ToolListMF.research_scroll)
		{
			int id = items[0].getItemDamage();
			ItemStack newItem = new ItemStack(ToolListMF.research_scroll_complete, 1, id);
			this.setInventorySlotContents(0, newItem);
		}
		this.maxProgress = 0;
		this.progress = 0;
		researchID = -1;
	}
	private int getMaxTime()
	{
		ItemStack item = items[0];
		
		if(item == null || !(item.getItem() == ToolListMF.research_scroll))
		{
			researchID = -1;
			progress = 0;
			return -1;
		}
		
		if(item.getItemDamage() >= InformationList.knowledgeList.size())
		{
			researchID = -1;
			progress = 0;
			return -1;
		}
		InformationBase info = InformationList.knowledgeList.get(item.getItemDamage());
		if(info != null)
		{
			researchID = info.ID;
			return info.getTime();
		}
		researchID = -1;
		progress = 0;
		return -1;
	}
	private int ticksExisted;
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		
		if(!worldObj.isRemote)
		{
			if(++ticksExisted % 20 == 0)
			{
				maxProgress = getMaxTime();
				progress += 1F/60F;//+1 each minute
			}
			if(maxProgress > 0 && progress >= maxProgress)
			{
				createComplete();
			}
		}
		syncData();
	}
	public void syncData()
	{
		
		if(worldObj.isRemote)return;
		
		List<EntityPlayer> players = ((WorldServer)worldObj).playerEntities;
		for(int i = 0; i < players.size(); i++)
		{
			EntityPlayer player = players.get(i);
			((WorldServer)worldObj).getEntityTracker().func_151248_b(player, new ResearchTablePacket(this).generatePacket());
		}
	}
	
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		nbt.setInteger("researchID", researchID);
		nbt.setInteger("ticksExisted", ticksExisted);
		nbt.setFloat("progress", progress);
		nbt.setFloat("maxProgress", maxProgress);
		NBTTagList savedItems = new NBTTagList();

        for (int i = 0; i < this.items.length; ++i)
        {
            if (this.items[i] != null)
            {
                NBTTagCompound savedSlot = new NBTTagCompound();
                savedSlot.setByte("Slot", (byte)i);
                this.items[i].writeToNBT(savedSlot);
                savedItems.appendTag(savedSlot);
            }
        }

        nbt.setTag("Items", savedItems);
	}
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		ticksExisted = nbt.getInteger("ticksExisted");
		researchID = nbt.getInteger("researchID");
		progress = nbt.getFloat("progress");
		maxProgress = nbt.getFloat("maxProgress");
		NBTTagList savedItems = nbt.getTagList("Items", 10);
        this.items = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < savedItems.tagCount(); ++i)
        {
            NBTTagCompound savedSlot = savedItems.getCompoundTagAt(i);
            byte slotNum = savedSlot.getByte("Slot");

            if (slotNum >= 0 && slotNum < this.items.length)
            {
                this.items[slotNum] = ItemStack.loadItemStackFromNBT(savedSlot);
            }
        }
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
		items[slot] = item;
	}

	@Override
	public String getInventoryName()
	{
		return "gui.bombcraftmf.name";
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
		return canAccept(item);
	}

	public static boolean canAccept(ItemStack item)
	{
		return item != null && item.getItem() == ToolListMF.research_scroll;
	}
	
	@Override
	public int getMetreScale(int size) 
	{
		if(maxProgress <= 0)
		{
			return 0;
		}
		return (int)Math.min(size, Math.ceil(size / maxProgress * progress));
	}
	@Override
	public boolean shouldShowMetre() 
	{
		return maxProgress > 0;
	}
	@Override
	public String getLocalisedName() 
	{
		if(researchID >= 0)
		{
			InformationBase base = InformationList.knowledgeList.get(researchID);
			if(base != null)
			{
				return base.getName();
			}
		}
		return "";
	}
}
