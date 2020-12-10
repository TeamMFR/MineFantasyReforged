package minefantasy.mfr.tile.blastfurnace;

import minefantasy.mfr.api.crafting.MineFantasyFuels;
import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.refine.BlastFurnaceRecipes;
import minefantasy.mfr.api.refine.ISmokeCarrier;
import minefantasy.mfr.api.refine.SmokeMechanics;
import minefantasy.mfr.container.ContainerBase;
import minefantasy.mfr.container.ContainerBlastChamber;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.network.NetworkHandler;
import minefantasy.mfr.tile.TileEntityBase;
import minefantasy.mfr.util.MFRLogUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class TileEntityBlastChamber extends TileEntityBase implements ITickable, ISmokeCarrier {
    public int ticksExisted;
    public boolean isBuilt = false;
    public int fireTime;
    public int tempUses;
    protected int smokeStorage;
    protected Random rand = new Random();

    public final ItemStackHandler inventory = createInventory();

    @Override
    protected ItemStackHandler createInventory() {
        return new ItemStackHandler(2);
    }

    @Override
    public ItemStackHandler getInventory() {
        return this.inventory;
    }

    @Override
    public ContainerBase createContainer(EntityPlayer player) {
        return new ContainerBlastChamber(player.inventory,this);
    }

    @Override
    protected int getGuiId() {
        return NetworkHandler.GUI_BLAST_CHAMBER;
    }

    public static boolean isCarbon(ItemStack item) {
        return MineFantasyFuels.isCarbon(item);
    }


    public static boolean isInput(ItemStack item) {
        return getResult(item) != ItemStack.EMPTY;
    }

    protected static ItemStack getResult(ItemStack input) {
        if (!BlastFurnaceRecipes.smelting().getSmeltingResult(input).isEmpty()) {
            return BlastFurnaceRecipes.smelting().getSmeltingResult(input).copy();
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void update() {

        ++ticksExisted;
        int dropFrequency = 5;

        if (!world.isRemote && ticksExisted % dropFrequency == 0) {
            TileEntity neighbour = world.getTileEntity(pos.add(0,-1,0));
            if (neighbour instanceof TileEntityBlastChamber && !(neighbour instanceof TileEntityBlastHeater)) {
                interact((TileEntityBlastChamber) neighbour);
            }
        }
        if (ticksExisted % 200 == 0) {
            updateBuild();
        }
        if (smokeStorage > 0) {
            SmokeMechanics.emitSmokeFromCarrier(world,pos, this, 5);
        }
        if (!world.isRemote && smokeStorage > getMaxSmokeStorage() && rand.nextInt(1000) == 0) {
            world.newExplosion(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 5F, true, true);
        }
    }

    protected void interact(TileEntityBlastChamber tile) {
        if (!tile.isBuilt)
            return;

        for (int a = 0; a < getInventory().getSlots(); a++) {
            ItemStack mySlot = getInventory().getStackInSlot(a);

            if (mySlot != ItemStack.EMPTY && canShare(mySlot, a)) {
                ItemStack theirSlot = tile.getInventory().getStackInSlot(a);
                if (theirSlot == ItemStack.EMPTY) {
                    ItemStack copy = mySlot.copy();
                    copy.setCount(1);
                    tile.getInventory().setStackInSlot(a, copy);
                    getInventory().extractItem(a,1,false);
                } else if (CustomToolHelper.areEqual(theirSlot, mySlot)) {
                    if ((theirSlot.getCount()) < getMaxStackSizeForDistribute()) {
                        theirSlot.grow(1);
                        getInventory().extractItem(a,1,false);
                        tile.getInventory().setStackInSlot(a, theirSlot);
                    }
                }
            }
        }
    }

    private boolean canShare(ItemStack mySlot, int a) {
        if (a == 1)
            return isInput(mySlot);
        return isCarbon(mySlot);
    }

    public void updateBuild() {
        isBuilt = getIsBuilt();
    }

    protected boolean getIsBuilt() {
        return (isFirebrick(-1, 0) && isFirebrick(1, 0) && isFirebrick(0, -1) && isFirebrick(0, 1));
    }

    protected boolean isFirebrick(int x, int z) {
        Block block = world.getBlockState(pos.add(x, 0,z)).getBlock();
        return block == MineFantasyBlocks.FIREBRICKS;
    }

    protected boolean isAir(int x, int z) {
        return !world.isBlockNormalCube(pos.add(x, 0, z), false);
    }

    private int getMaxStackSizeForDistribute() {
        return 1;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        if (slot == 0) {
            return isCarbon(item);
        }
        return isInput(item);
    }

    @Override
    public int getSmokeValue() {
        return smokeStorage;
    }

    @Override
    public void setSmokeValue(int smoke) {
        smokeStorage = smoke;
    }

    @Override
    public int getMaxSmokeStorage() {
        return 10;
    }

    @Override
    public boolean canAbsorbIndirect() {
        return false;
    }

    public boolean shouldRemoveCarbon() {
        if (tempUses > 0) {
            --tempUses;
            MFRLogUtil.logDebug("Decr Carbon Uses: " + tempUses);
            return false;
        } else {
            int carb = MineFantasyFuels.getCarbon(getInventory().getStackInSlot(0)) - 1;
            if (carb > 0) {
                tempUses = carb;
                MFRLogUtil.logDebug("Set Carbon Uses: " + tempUses);
            }
            return true;
        }
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setInteger("fireTime", fireTime);
        nbt.setBoolean("isBuilt", isBuilt);
        nbt.setInteger("ticksExisted", ticksExisted);
        nbt.setInteger("StoredSmoke", smokeStorage);

        nbt.setTag("inventory", inventory.serializeNBT());
        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        fireTime = nbt.getInteger("fireTime");
        isBuilt = nbt.getBoolean("isBuilt");
        ticksExisted = nbt.getInteger("ticksExisted");
        smokeStorage = nbt.getInteger("StoredSmoke");

        inventory.deserializeNBT(nbt.getCompoundTag("inventory"));
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(getInventory());
        }
        return super.getCapability(capability, facing);
    }
}