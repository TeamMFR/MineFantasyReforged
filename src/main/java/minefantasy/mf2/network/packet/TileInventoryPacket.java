package minefantasy.mf2.network.packet;

import io.netty.buffer.ByteBuf;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileInventoryPacket extends PacketMF
{
	public static final String packetName = "MF2_TileInvPacket";
	private int[] coords = new int[3];
	private int stackCount;
	private IInventory inventory;

	public TileInventoryPacket(IInventory inv, TileEntity tile)
	{
		this.coords = new int[]{tile.xCoord, tile.yCoord, tile.zCoord};
		this.stackCount = inv.getSizeInventory();
		this.inventory = inv;
	}

	public TileInventoryPacket() {
	}

	@Override
	public void process(ByteBuf packet, EntityPlayer player) 
	{
        coords = new int[]{packet.readInt(), packet.readInt(), packet.readInt()};
        TileEntity entity = player.worldObj.getTileEntity(coords[0], coords[1], coords[2]);
        
        if(entity != null && entity instanceof IInventory)
        {
        	int invSize = packet.readInt();
        	IInventory inv = (IInventory)entity;
        	for(int a = 0; a < invSize; a++)
        	{
        		ItemStack item = CustomToolHelper.readFromPacket(packet);
        		if(a < inv.getSizeInventory())
        		{
        			inv.setInventorySlotContents(a, item);
        		}
        		else
        		{
        			item = null;
        		}
        	}
        }
    	packet.clear();
	}

	@Override
	public String getChannel()
	{
		return packetName;
	}

	@Override
	public void write(ByteBuf packet) 
	{
		for(int a = 0; a < coords.length; a++)
		{
			packet.writeInt(coords[a]);
		}
		packet.writeInt(stackCount);
		for(int a = 0; a < stackCount; a++)
		{
			CustomToolHelper.writeToPacket(packet, inventory.getStackInSlot(a));
		}
	}
}
