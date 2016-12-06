package minefantasy.mf2.block.tileentity;

import java.util.List;

import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.network.packet.StorageBlockPacket;
import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

public class TileEntityComponent extends TileEntity
{
	public ItemStack item;
	private int max;
	public int stackSize;
	public String type = "bar";
	public String tex = "bar";
	public CustomMaterial material;
	private int ticksExisted;
	
	public void setItem(ItemStack item, String type, String tex, int max)
	{
		this.item = item;
		this.item.stackSize = 1;
		this.stackSize = 1;
		this.max = max;
		this.type = type;
		this.tex = tex;
		this.material = CustomToolHelper.getCustomPrimaryMaterial(item);
		this.ticksExisted = 19;
	}
	public boolean interact(EntityPlayer user, ItemStack held, boolean leftClick) 
	{
		if(item != null && stackSize > 0)
		{
			if(leftClick)//Take
			{
				if(held == null)
				{
					held = item.copy();
					held.stackSize = 1;
					user.setCurrentItemOrArmor(0, held);
					--stackSize;
				}
				else
				{
					ItemStack newitem = item.copy();
					newitem.stackSize = 1;
					if(user.inventory.addItemStackToInventory(newitem))
					{
						--stackSize;
					}
				}
				if(item == null || stackSize <= 0)
				{
					worldObj.setBlockToAir(xCoord, yCoord, zCoord);
					worldObj.removeTileEntity(xCoord, yCoord, zCoord);
				}
				syncData();
				return true;
			}
			else if(held != null)//PLACE
			{
				if(stackSize < max && item.isItemEqual(held) && ItemStack.areItemStackTagsEqual(item, held))
				{
					++stackSize;
					--held.stackSize;
				}
				
				if(held.stackSize <= 0)
				{
					user.setCurrentItemOrArmor(0, null);
				}
				syncData();
				return true;
			}
		}
		syncData();
		return false;
	}
	
	@Override
	public void updateEntity()
	{
		++ticksExisted;
		
		if(ticksExisted == 20 || ticksExisted % 120 == 0)
		{
			syncData();
		}
	}
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		max = nbt.getInteger("MaxStack");
		type = nbt.getString("type");
		tex = nbt.getString("tex");
		stackSize = nbt.getInteger("StackSize");
		
		NBTTagCompound itemsave = nbt.getCompoundTag("ItemSave");
		item = ItemStack.loadItemStackFromNBT(itemsave);
		if(nbt.hasKey("MaterialName"))
		{
			this.material = CustomMaterial.getMaterial(nbt.getString("MaterialName"));
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		nbt.setInteger("MaxStack", max);
		nbt.setString("type", type);
		nbt.setString("tex", tex);
		nbt.setInteger("StackSize", stackSize);
		
		if(item != null)
		{
			NBTTagCompound itemsave = new NBTTagCompound();
			item.writeToNBT(itemsave);
			nbt.setTag("ItemSave", itemsave);
		}
		if(material != null)
		{
			nbt.setString("MaterialName", material.name);
		}
	}
	
	public void syncData() 
	{
		if(worldObj.isRemote)return;
		
		List<EntityPlayer> players = ((WorldServer)worldObj).playerEntities;
		for(int i = 0; i < players.size(); i++)
		{
			EntityPlayer player = players.get(i);
			((WorldServer)worldObj).getEntityTracker().func_151248_b(player, new StorageBlockPacket(this).generatePacket());
		}
		MFLogUtil.logDebug("Send Packet from component");
	}
}
