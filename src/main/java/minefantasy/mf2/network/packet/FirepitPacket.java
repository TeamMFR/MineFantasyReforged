package minefantasy.mf2.network.packet;

import io.netty.buffer.ByteBuf;
import minefantasy.mf2.block.tileentity.TileEntityFirepit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.ByteBufUtils;

public class FirepitPacket extends PacketMF
{
	public static final String packetName = "MF2_FirepitPacket";
	private int[] coords = new int[3];
	private int fuel = 0;
	
	public FirepitPacket(TileEntityFirepit tile)
	{
		coords = new int[]{tile.xCoord, tile.yCoord, tile.zCoord};
		this.fuel = tile.fuel;
	}

	public FirepitPacket() {
	}

	@Override
	public void process(ByteBuf packet, EntityPlayer player) 
	{
        coords = new int[]{packet.readInt(), packet.readInt(), packet.readInt()};
        fuel = packet.readInt();
        
        TileEntity entity = player.worldObj.getTileEntity(coords[0], coords[1], coords[2]);
        
        if(entity != null && entity instanceof TileEntityFirepit)
        {
	        TileEntityFirepit tile = (TileEntityFirepit)entity;
	        tile.fuel = fuel;
        }
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
		packet.writeInt(fuel);
	}
}
