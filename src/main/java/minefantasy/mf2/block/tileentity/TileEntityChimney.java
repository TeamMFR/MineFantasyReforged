package minefantasy.mf2.block.tileentity;

import java.util.List;
import java.util.Random;

import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.refine.BlastFurnaceRecipes;
import minefantasy.mf2.api.refine.ISmokeCarrier;
import minefantasy.mf2.api.refine.SmokeMechanics;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.block.refining.BlockChimney;
import minefantasy.mf2.block.refining.BlockChimney;
import minefantasy.mf2.block.refining.BlockForge;
import minefantasy.mf2.block.tileentity.blastfurnace.TileEntityBlastFH;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.network.packet.ChimneyPacket;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityChimney extends TileEntity implements ISmokeCarrier
{
	public Block maskBlock = Blocks.air;
	public int maskMeta = 0;
	private int isIndirect = -1;
	
	protected int smokeStorage;
	public int ticksExisted;
	public int ticksExistedTemp;
	private Random rand = new Random();
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		++ticksExisted;
		++ticksExistedTemp;
		
		/*TODO Custom Tex
		if(ticksExistedTemp == 20)
		{
			MineFantasyII.debugMsg("Chimney Loaded R = " + worldObj.isRemote);
			sync();
		}
		*/
		if(smokeStorage > 0)
		{
			SmokeMechanics.emitSmokeFromCarrier(worldObj, xCoord, yCoord, zCoord, this, 5);
		}
		if(!worldObj.isRemote && smokeStorage > getMaxSmokeStorage() && rand.nextInt(500) == 0)
		{
			worldObj.newExplosion(null, xCoord+0.5D, yCoord+0.5D, zCoord+0.5D, 2F, false, true);
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		nbt.setInteger("ticksExisted", ticksExisted);
		nbt.setInteger("StoredSmoke", smokeStorage);
		NBTTagList savedItems = new NBTTagList();
		
		nbt.setInteger("BlockID", Block.getIdFromBlock(maskBlock));
	}
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		ticksExisted = nbt.getInteger("ticksExisted");
		smokeStorage = nbt.getInteger("StoredSmoke");
		setBlock(nbt.getInteger("BlockID"));
	}
	
	public void setBlock(int id) 
	{
		setBlock(id, 0);
	}
	public void setBlock(int id, int subId) 
	{
		/*TODO Custom Tex
		maskMeta = subId;
		Block newblock = Block.getBlockById(id);
		if(newblock != null)
		{
			maskBlock = newblock;
		}
		if(worldObj != null && worldObj.isRemote)
		{
			worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
		}
		*/
	}

	@Override
	public int getSmokeValue() 
	{
		return smokeStorage;
	}
	@Override
	public void setSmokeValue(int smoke) 
	{
		smokeStorage = smoke;
	}
	@Override
	public int getMaxSmokeStorage() 
	{
		if(this.blockType != null && blockType instanceof BlockChimney)
		{
			return ((BlockChimney)blockType).size;
		}
		return 5;
	}

	public void sync() 
	{
		/*TODO Custom Tex
		MineFantasyII.debugMsg("Block For Chimney = " + maskBlock.getUnlocalizedName() + " R = " + worldObj.isRemote);
		if(!worldObj.isRemote)
		{
			MineFantasyII.debugMsg("Syncing Chimney " + xCoord + ", " + yCoord + ", " + zCoord);
			List<EntityPlayer> players = ((WorldServer)worldObj).playerEntities;
			for(int i = 0; i < players.size(); i++)
			{
				EntityPlayer player = players.get(i);
				((WorldServer)worldObj).getEntityTracker().func_151248_b(player, new ChimneyPacket(this).generatePacket());
			}
		}
		MineFantasyII.debugMsg("Syncing Render " + xCoord + ", " + yCoord + ", " + zCoord);
		worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
		*/
	}

	public BlockChimney getActiveBlock()
	{
		if(worldObj == null)return null;
		
		Block block = worldObj.getBlock(xCoord, yCoord, zCoord);
		
		if(block != null && block instanceof BlockChimney)
		{
			return (BlockChimney)block;
		}
		return null;
	}
	public boolean isIndirect()
	{
		BlockChimney block =  getActiveBlock();
		return block != null && block.isIndirect;
	}
	@Override
	public boolean canAbsorbIndirect() 
	{
		if(isIndirect == -1)
		{
			isIndirect = isIndirect() ? 1 : 0;
		}
		return isIndirect == 1;
	}
}
