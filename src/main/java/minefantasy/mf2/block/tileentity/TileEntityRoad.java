package minefantasy.mf2.block.tileentity;

import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.network.NetworkUtils;
import minefantasy.mf2.network.packet.RoadPacket;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

import java.util.Random;

public class TileEntityRoad extends TileEntity {
    public int[] surface = new int[]{0, 0};
    public boolean isLocked = false;
    private int ticksExisted;
    private Random rand = new Random();

    public TileEntityRoad() {
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        ++ticksExisted;
        if (!worldObj.isRemote) {
            if (ticksExisted % 1200 == 0) {
                sendPacketToClients();
            }
        } else if (ticksExisted == 20) {
            requestPacket();
        }
    }

    public void setSurface(Block id, int meta) {
        if (worldObj == null) {
            return;
        }
        if (id == Blocks.grass) {
            id = Blocks.dirt;
        }
        worldObj.playSoundEffect(xCoord, yCoord, zCoord, "dig.grass", 0.5F, 1.0F);
        surface[0] = Block.getIdFromBlock(id);
        surface[1] = meta;
        sendPacketToClients();
        refreshSurface();
    }

    public void sendPacketToClients() {
        if (worldObj.isRemote)
            return;

        NetworkUtils.sendToWatchers(new RoadPacket(this).generatePacket(), (WorldServer) worldObj, this.xCoord, this.zCoord);

		/*
        List<EntityPlayer> players = ((WorldServer) worldObj).playerEntities;
		for (int i = 0; i < players.size(); i++) {
			EntityPlayer player = players.get(i);
			((WorldServer) worldObj).getEntityTracker().func_151248_b(player, new RoadPacket(this).generatePacket());
		}
		*/
    }

    public void requestPacket() {
        if (!worldObj.isRemote)
            return;
        EntityPlayer player = MineFantasyII.proxy.getClientPlayer();
        if (player != null) {
            ((EntityClientPlayerMP) player).sendQueue.addToSendQueue(new RoadPacket(this).request().generatePacket());
        }
    }

    private boolean blockChange(int id, int meta) {
        if (id != surface[0])
            return true;
        if (meta != surface[1])
            return true;

        return false;
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setIntArray("surface", surface);
        nbt.setBoolean("isLocked", isLocked);
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        surface = nbt.getIntArray("surface");
        isLocked = nbt.getBoolean("isLocked");
    }

    public int[] getSurface() {
        return surface;
    }

    public boolean canBuild() {
        if (worldObj == null) {
            return false;
        }
        return true;
    }

    public void refreshSurface() {
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    public Block getBaseBlock() {
        if (surface[0] <= 0) {
            return Blocks.dirt;
        }
        Block block = Block.getBlockById(surface[0]);
        return block != null ? block : Blocks.dirt;
    }
}
