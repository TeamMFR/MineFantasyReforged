package minefantasy.mf2.block.tileentity;

import java.util.List;
import java.util.Random;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.network.packet.RoadPacket;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

public class TileEntityRoad extends TileEntity
{
	public int[] surface = new int[]{0, 0};
	public boolean isLocked = false;
	private int ticksExisted;
	private Random rand = new Random();
	
	public TileEntityRoad()
	{
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		
		if(!worldObj.isRemote)
		{
			++ticksExisted;
			if(ticksExisted == 20 || ticksExisted % 1200 == 0)
			{
				sendPacketToClients();
			}
		}
	}
	
	public void setSurface(Block id, int meta)
	{
		if(worldObj == null)
		{
			return;
		}
		if(id == Blocks.grass)
		{
			id = Blocks.dirt;
		}
		worldObj.playSoundEffect(xCoord, yCoord, zCoord, "dig.grass", 0.5F, 1.0F);
		surface[0] = Block.getIdFromBlock(id);
		surface[1] = meta;
		sendPacketToClients();
		refreshSurface();
	}

	public void sendPacketToClients()
	{
		if(worldObj.isRemote)return;
		
		List<EntityPlayer> players = ((WorldServer)worldObj).playerEntities;
		for(int i = 0; i < players.size(); i++)
		{
			EntityPlayer player = players.get(i);
			((WorldServer)worldObj).getEntityTracker().func_151248_b(player, new RoadPacket(this).generatePacket());
		}
	}
	
	private boolean blockChange(int id, int meta)
	{
		if(id != surface[0])return true;
		if(meta != surface[1])return true;
		
		return false;
	}
	
	
	public void writeToNBT(NBTTagCompound nbt)
    {
		super.writeToNBT(nbt);
		
		nbt.setIntArray("surface", surface);
		nbt.setBoolean("isLocked", isLocked);
    }
	public void readFromNBT(NBTTagCompound nbt)
    {
		super.readFromNBT(nbt);
		
		surface = nbt.getIntArray("surface");
		isLocked = nbt.getBoolean("isLocked");
    }

	public int[] getSurface() 
	{
		return surface;
	}

	public boolean canBuild()
	{
		if(worldObj == null)
		{
			return false;
		}
		return true;
	}

	public void refreshSurface() 
	{
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	public Block getBaseBlock()
	{
		if(surface[0] <= 0)
		{
			return Blocks.dirt;
		}
		Block block = Block.getBlockById(surface[0]);
		return block != null ? block : Blocks.dirt;
	}
}
