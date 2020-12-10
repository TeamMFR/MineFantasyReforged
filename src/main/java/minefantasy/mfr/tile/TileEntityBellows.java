package minefantasy.mfr.tile;

import minefantasy.mfr.api.refine.IBellowsUseable;
import minefantasy.mfr.block.refining.BlockBellows;
import minefantasy.mfr.container.ContainerBase;
import minefantasy.mfr.init.MineFantasySounds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityBellows extends TileEntityBase implements ITickable {
    public int press = 0;

    public TileEntityBellows() {

    }

    @Override
    public boolean hasFastRenderer() {
        return true;
    }

    public void interact(EntityPlayer player, float powerLevel) {
        IBellowsUseable forge = getFacingForge();
        if (press < 10) {
            if (player != null) {
                player.playSound(MineFantasySounds.BELLOWS, 1, 1);
            } else {
                world.playSound(null, pos, MineFantasySounds.BELLOWS, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            press = 50;
            if (forge != null) {
                forge.onUsedWithBellows(powerLevel);
            }
        }
    }

    @Override
    public void update() {
        if (press > 0)
            press -= 2;
        if (press < 0)
            press = 0;
    }

    public IBellowsUseable getFacingForge() {
        EnumFacing facing = world.getBlockState(pos).getValue(BlockBellows.FACING);
        BlockPos forgePos = new BlockPos(pos.add(facing.getFrontOffsetX(), facing.getFrontOffsetY(), facing.getFrontOffsetZ()));

        TileEntity tile = world.getTileEntity(forgePos);

        if (tile != null && tile instanceof IBellowsUseable)
            return (IBellowsUseable) tile;

        if (world.getBlockState(forgePos).getMaterial() != null && world.getBlockState(forgePos).getMaterial().isSolid()) {
            return getFacingForgeThroughWall();
        }
        return null;
    }

    public IBellowsUseable getFacingForgeThroughWall() {
        EnumFacing facing = world.getBlockState(pos).getValue(BlockBellows.FACING);
        BlockPos forgePos = new BlockPos(pos.add(facing.getFrontOffsetX() * 2, facing.getFrontOffsetY() * 2, facing.getFrontOffsetZ() *2));

        TileEntity tile = world.getTileEntity(forgePos);
        if (tile == null)
            return null;
        if (tile instanceof IBellowsUseable) {
            return (IBellowsUseable) tile;
        }
        return null;
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

    @Override
    protected ItemStackHandler createInventory() {
        return null;
    }

    @Override
    public ItemStackHandler getInventory() {
        return null;
    }

    @Override
    public ContainerBase createContainer(EntityPlayer player) {
        return null;
    }

    @Override
    protected int getGuiId() {
        return 0;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return false;
    }
}
