package minefantasy.mf2.block.tileentity.decor;

import java.util.List;

import minefantasy.mf2.api.archery.AmmoMechanicsMF;
import minefantasy.mf2.api.archery.IAmmo;
import minefantasy.mf2.api.archery.IFirearm;
import minefantasy.mf2.api.crafting.IBasicMetre;
import minefantasy.mf2.network.packet.AmmoBoxPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;
public class TileEntityAmmoBox extends TileEntity implements IBasicMetre
{
	public int maxStack = 8;
	public int angle, stock;
	public ItemStack ammo;
	private String tex;
	
	public TileEntityAmmoBox()
	{
		this("basic");
	}
	public TileEntityAmmoBox(String tex)
	{
		this.tex = tex;
	}
	
	public boolean interact(EntityPlayer user, ItemStack held) 
	{
		if(held != null)
		{
			if(ammo != null && held.getItem() instanceof IFirearm && loadGun(held))
			{
				open();
				syncData();
				return true;
			}
			if(held.getItem() instanceof IAmmo)
			{
				int max = this.getMaxAmmo(ammo != null ? ammo : held, (IAmmo)held.getItem());
				if(ammo == null)
				{
					open();
					placeInEmpty(user, held, max);
				}
				else if(areItemStacksEqual(held, ammo) && stock < max)
				{
					open();
					addToBox(user, held, max);
				}
				
				syncData();
				return true;
			}
			return false;
		}
		else if(ammo != null)
		{
			open();
			takeStack(user, held);
			syncData();
			return true;
		}
		syncData();
		return false;
	}
	private boolean loadGun(ItemStack held) 
	{
		IFirearm gun = (IFirearm)held.getItem();
		if(gun.canAcceptAmmo(held, getAmmoClass()))
		{
			ItemStack loaded = AmmoMechanicsMF.getAmmo(held);
			if(loaded == null)
			{
				int ss = Math.min(ammo.getMaxStackSize(), stock);
				ItemStack newloaded = ammo.copy();
				newloaded.stackSize = ss;
				AmmoMechanicsMF.setAmmo(held, newloaded);
				stock -= ss;
				if(stock <= 0)
				{
					ammo = null;
				}
				return true;
			}
			else if(areItemStacksEqual(loaded, ammo))
			{
				int room_left = loaded.getMaxStackSize() - loaded.stackSize;
				if(stock > room_left)
				{
					stock -= room_left;
					loaded.stackSize += room_left;
					AmmoMechanicsMF.setAmmo(held, loaded);
				}
				else
				{
					loaded.stackSize += stock;
					AmmoMechanicsMF.setAmmo(held, loaded);
					stock = 0;
					ammo = null;
				}
						
				return true;
			}
		}
		return false;
	}
	private void addToBox(EntityPlayer user, ItemStack held, int max) 
	{
		int room_left = max - stock;
		if(held.stackSize <= room_left)
		{
			stock += held.stackSize;
			user.setCurrentItemOrArmor(0, null);
		}
		else
		{
			held.stackSize -= room_left;
			stock += room_left;
		}
	}
	
	private void takeStack(EntityPlayer user, ItemStack held) 
	{
		int ss = stock;
		ItemStack taken = ammo.copy();
		if(ss > taken.getMaxStackSize())
		{
			ss = taken.stackSize = taken.getMaxStackSize();
		}
		stock -= ss;
		user.setCurrentItemOrArmor(0, taken);
		if(stock <= 0)
		{
			ammo = null;
		}
	}
	/**
	 * Place ammo in empty box
	 */
	private void placeInEmpty(EntityPlayer user, ItemStack held, int max) 
	{
		ammo = held.copy();
		int ss = held.stackSize;
		if(held.stackSize <= max)//Place all
		{
			user.setCurrentItemOrArmor(0, null);
		}
		else //Fill as much
		{
			stock = max;
			held.stackSize -= max;
		}
		stock = ss;
	}
	private void open()
	{
		angle = 16;
		 this.worldObj.playSoundEffect((double)this.xCoord + 0.5D, (double)this.yCoord + 0.25D, (double)this.zCoord + 0.5D, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 1.4F);
	}
	private int ticksExisted;
	
	@Override
	public void updateEntity()
	{
		++ticksExisted;
		if(ticksExisted == 20 || ticksExisted % 100 == 0)
		{
			syncData();
		}
		if(angle > 0)--angle;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("stock", stock);
		if(ammo != null)
		{
			NBTTagCompound itemsave = new NBTTagCompound();
			ammo.writeToNBT(itemsave);
			nbt.setTag("storage", itemsave);
		}
	}
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		stock = nbt.getInteger("stock");
		if(nbt.hasKey("storage"))
		{
			NBTTagCompound itemsave = nbt.getCompoundTag("storage");
			ammo = ItemStack.loadItemStackFromNBT(itemsave);
		}
	}
	
	public void syncData()
	{
		if(worldObj.isRemote)return;
		
		List<EntityPlayer> players = ((WorldServer)worldObj).playerEntities;
		for(int i = 0; i < players.size(); i++)
		{
			EntityPlayer player = players.get(i);
			((WorldServer)worldObj).getEntityTracker().func_151248_b(player, new AmmoBoxPacket(this).generatePacket());
		}
	}
	public int getMaxAmmo(ItemStack ammo)
	{
		if(ammo.getItem() instanceof IAmmo)
		{
			return getMaxAmmo(ammo, (IAmmo)ammo.getItem());
		}
		return getMaxAmmo(ammo, (IAmmo)null);
	}
	public int getMaxAmmo(ItemStack item, IAmmo ammo)
	{
		return item.getMaxStackSize() * maxStack;
	}
	@Override
	public int getMetreScale(int size)
	{
		if(ammo != null)
		{
			return (int)Math.min(size, (float)size / (float)getMaxAmmo(ammo) * (float)stock);
		}
		return 0;
	}
	@Override
	public boolean shouldShowMetre() {
		return ammo != null;
	}
	@Override
	public String getLocalisedName() 
	{
		if(ammo != null)
		{
			return ammo.getDisplayName() + " x" + stock;
		}
		return "";
	}
	public String getTexName() {
		return tex;
	}
	private boolean areItemStacksEqual(ItemStack i1, ItemStack i2) 
	{
		if(i1 == null || i2 == null)return false;
		
		return i1.isItemEqual(i2) && ItemStack.areItemStackTagsEqual(i1, i2);
	}
	private String getAmmoClass() 
	{
		if(ammo != null && ammo.getItem() instanceof IAmmo)
		{
			return ((IAmmo)ammo.getItem()).getAmmoType(ammo);
		}
		return "null";
	}
}
