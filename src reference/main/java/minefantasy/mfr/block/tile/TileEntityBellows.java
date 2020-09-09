package minefantasy.mfr.tile;

import minefantasy.mfr.api.refine.IBellowsUseable;
import minefantasy.mfr.init.SoundsMFR;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;

public class TileEntityBellows extends TileEntity {
    public int press = 0;

    public TileEntityBellows() {

    }

    public void interact(EntityPlayer player, float powerLevel) {
        IBellowsUseable forge = getFacingForge();
        if (press < 10) {
            if (player != null) {
                player.playSound(SoundsMFR.BELLOWS, 1, 1);
            } else {
                world.playSound(player, pos, SoundsMFR.BELLOWS, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            press = 50;
            if (forge != null) {
                forge.onUsedWithBellows(powerLevel);
            }
            sendPacketToClients();
        }
    }

    @Override
    public void markDirty() {
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
        press = nbt.getInteger("press");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("press", press);
        return nbt;
    }

    public IBellowsUseable getFacingForge() {
        EnumFacing dir = EnumFacing.getFacingFromVector(pos.getX(), pos.getY(), pos.getZ());
        BlockPos forgePos = new BlockPos(pos.getX() + dir.getFrontOffsetZ(), pos.getY() + dir.getFrontOffsetY(), pos.getZ() + dir.getFrontOffsetZ());

        TileEntity tile = world.getTileEntity(forgePos);

        if (tile != null && tile instanceof IBellowsUseable)
            return (IBellowsUseable) tile;

        if (world.getBlockState(forgePos).getMaterial() != null
                && world.getBlockState(forgePos).getMaterial().isSolid()) {
            return getFacingForgeThroughWall();
        }
        return null;
    }

    public IBellowsUseable getFacingForgeThroughWall() {
        EnumFacing dir = EnumFacing.getFacingFromVector(pos.getX(), pos.getY(), pos.getZ());
        BlockPos forgePos = new BlockPos(pos.getX() + (dir.getFrontOffsetZ() * 2), pos.getY() + (dir.getFrontOffsetY() * 2), pos.getZ() + (dir.getFrontOffsetZ() * 2));

        TileEntity tile = world.getTileEntity(forgePos);
        if (tile == null)
            return null;
        if (tile instanceof IBellowsUseable) {
            return (IBellowsUseable) tile;
        }
        return null;
    }
}
