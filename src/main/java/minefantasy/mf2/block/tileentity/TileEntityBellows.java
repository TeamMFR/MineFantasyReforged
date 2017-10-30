package minefantasy.mf2.block.tileentity;

import minefantasy.mf2.api.refine.IBellowsUseable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityBellows extends TileEntity {
    public int direction;
    public int press = 0;

    public TileEntityBellows() {

    }

    public void interact(EntityPlayer player, float powerLevel) {
        int x = xCoord;
        int y = yCoord;
        int z = zCoord;
        IBellowsUseable forge = getFacingForge();
        if (press < 10) {
            if (player != null) {
                player.playSound("minefantasy2:block.bellows", 1, 1);
            } else {
                worldObj.playSound(xCoord, yCoord, zCoord, "minefantasy2:block.bellows", 1.0F, 1.0F, false);
            }
            press = 50;
            if (forge != null) {
                forge.onUsedWithBellows(powerLevel);
            }
            sendPacketToClients();
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (press > 0)
            press -= 2;
        if (press < 0)
            press = 0;
        sendPacketToClients();
    }

    private void sendPacketToClients() {
        // TODO Auto-generated method stub

    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        direction = nbt.getInteger("direction");
        press = nbt.getInteger("press");
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("direction", direction);
        nbt.setInteger("press", press);
    }

    public ForgeDirection getFacing() {
        int dir = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        switch (dir)// clockwise
        {
            case 0: // SOUTH
                return ForgeDirection.SOUTH;
            case 1: // WEST
                return ForgeDirection.WEST;
            case 2: // NORTH
                return ForgeDirection.NORTH;
            case 3: // EAST
                return ForgeDirection.EAST;
        }
        return ForgeDirection.SOUTH;
    }

    public IBellowsUseable getFacingForge() {
        ForgeDirection dir = getFacing();
        int x2 = xCoord + dir.offsetX;
        int y2 = yCoord + dir.offsetY;
        int z2 = zCoord + dir.offsetZ;

        TileEntity tile = worldObj.getTileEntity(x2, y2, z2);

        if (tile != null && tile instanceof IBellowsUseable)
            return (IBellowsUseable) tile;

        if (worldObj.getBlock(x2, y2, z2).getMaterial() != null
                && worldObj.getBlock(x2, y2, z2).getMaterial().isSolid()) {
            return getFacingForgeThroughWall();
        }
        return null;
    }

    public IBellowsUseable getFacingForgeThroughWall() {
        ForgeDirection dir = getFacing();
        int x2 = xCoord + (dir.offsetX * 2);
        int y2 = yCoord + (dir.offsetY * 2);
        int z2 = zCoord + (dir.offsetZ * 2);

        TileEntity tile = worldObj.getTileEntity(x2, y2, z2);
        if (tile == null)
            return null;
        if (tile instanceof IBellowsUseable) {
            return (IBellowsUseable) tile;
        }
        return null;
    }
}
