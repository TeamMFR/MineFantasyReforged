package minefantasy.mfr.block.tile;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.proxy.NetworkUtils;
import minefantasy.mfr.packet.RoadPacket;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
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
    public void markDirty() {

        ++ticksExisted;
        if (!world.isRemote) {
            if (ticksExisted % 1200 == 0) {
                sendPacketToClients();
            }
        } else if (ticksExisted == 20) {
            requestPacket();
        }
    }

    public void setSurface(Block id, IBlockState state) {
        if (world == null) {
            return;
        }
        if (id == Blocks.GRASS) {
            id = Blocks.DIRT;
        }
        world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_GRAVEL_STEP, SoundCategory.AMBIENT, 0.5F, 1.0F, true);
        surface[0] = Block.getIdFromBlock(id);
        surface[1] = state;
        sendPacketToClients();
        refreshSurface();
    }

    public void sendPacketToClients() {
        if (world.isRemote)
            return;

        NetworkUtils.sendToWatchers(new RoadPacket(this).generatePacket(), (WorldServer) world, this.pos);

		/*
        List<EntityPlayer> players = ((WorldServer) worldObj).playerEntities;
		for (int i = 0; i < players.size(); i++) {
			EntityPlayer player = players.get(i);
			((WorldServer) worldObj).getEntityTracker().func_151248_b(player, new RoadPacket(this).generatePacket());
		}
		*/
    }

    public void requestPacket() {
        if (!world.isRemote)
            return;
        EntityPlayer player = MineFantasyReborn.proxy.getClientPlayer();
        if (player != null) {
            ((EntityPlayerMP) player).sendQueue.addToSendQueue(new RoadPacket(this).request().generatePacket());
        }
    }

    private boolean blockChange(int id, int meta) {
        if (id != surface[0])
            return true;
        if (meta != surface[1])
            return true;

        return false;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setIntArray("surface", surface);
        nbt.setBoolean("isLocked", isLocked);
        return nbt;
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
        if (world == null) {
            return false;
        }
        return true;
    }

    public void refreshSurface() {
        world.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    public Block getBaseBlock() {
        if (surface[0] <= 0) {
            return Blocks.DIRT;
        }
        Block block = Block.getBlockById(surface[0]);
        return block != null ? block : Blocks.DIRT;
    }
}
