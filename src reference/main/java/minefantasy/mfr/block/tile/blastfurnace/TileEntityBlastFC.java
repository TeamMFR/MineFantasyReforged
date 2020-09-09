package minefantasy.mfr.tile.blastfurnace;

import minefantasy.mfr.api.crafting.MineFantasyFuels;
import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.refine.BlastFurnaceRecipes;
import minefantasy.mfr.api.refine.ISmokeCarrier;
import minefantasy.mfr.api.refine.SmokeMechanics;
import minefantasy.mfr.init.BlockListMFR;
import minefantasy.mfr.util.MFRLogUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

import java.util.Random;

public class TileEntityBlastFC extends TileEntity implements IInventory, ISidedInventory, ISmokeCarrier, ITickable {
    public int ticksExisted;
    public boolean isBuilt = false;
    public int fireTime;
    public int tempUses;
    protected ItemStack[] items = new ItemStack[2];
    protected int smokeStorage;
    protected Random rand = new Random();

    public static boolean isCarbon(ItemStack item) {
        return MineFantasyFuels.isCarbon(item);
    }

    public static boolean isFlux(ItemStack item) {
        return true;
    }

    public static boolean isInput(ItemStack item) {
        return getResult(item) != null;
    }

    protected static ItemStack getResult(ItemStack input) {
        if (BlastFurnaceRecipes.smelting().getSmeltingResult(input) != null) {
            return BlastFurnaceRecipes.smelting().getSmeltingResult(input).copy();
        }
        return null;
    }

    @Override
    public void update() {
        ++ticksExisted;
        int dropFrequency = 5;

        if (!world.isRemote && ticksExisted % dropFrequency == 0) {
            TileEntity neighbour = world.getTileEntity(pos.add(0,-1,0));
            if (neighbour != null && neighbour instanceof TileEntityBlastFC
                    && !(neighbour instanceof TileEntityBlastFH)) {
                interact((TileEntityBlastFC) neighbour);
            }
        }
        if (ticksExisted % 200 == 0) {
            updateBuild();
        }
        if (smokeStorage > 0) {
            SmokeMechanics.emitSmokeFromCarrier(world, pos, this, 5);
        }
        if (!world.isRemote && smokeStorage > getMaxSmokeStorage() && rand.nextInt(1000) == 0) {
            world.newExplosion(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 5F, true, true);
        }
    }

    protected void interact(TileEntityBlastFC tile) {
        if (!tile.isBuilt)
            return;

        for (int a = 0; a < getSizeInventory(); a++) {
            ItemStack mySlot = getStackInSlot(a);

            if (mySlot != null && canShare(mySlot, a)) {
                ItemStack theirSlot = tile.getStackInSlot(a);
                if (theirSlot == null) {
                    ItemStack copy = mySlot.copy();
                    copy.setCount(1);
                    tile.setInventorySlotContents(a, copy);
                    this.decrStackSize(a, 1);
                } else if (CustomToolHelper.areEqual(theirSlot, mySlot)) {
                    if ((theirSlot.getCount()) < getMaxStackSizeForDistribute()) {
                        theirSlot.grow(1);
                        this.decrStackSize(a, 1);
                        tile.setInventorySlotContents(a, theirSlot);
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
        return (isFirebrick(-1, 0, 0) && isFirebrick(1, 0, 0) && isFirebrick(0, 0, -1) && isFirebrick(0, 0, 1));
    }

    protected boolean isFirebrick(int x, int y, int z) {
        Block block = world.getBlockState(pos.add(x,y,z)).getBlock();
        if (block != null) {
            return block == BlockListMFR.FIREBRICKS;
        }
        return false;
    }

    protected boolean isAir(int x, int y, int z) {
        return !world.isAirBlock(pos.add(x,y,z));
    }

    private int getMaxStackSizeForDistribute() {
        return 1;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setInteger("fireTime", fireTime);
        nbt.setBoolean("isBuilt", isBuilt);
        nbt.setInteger("ticksExisted", ticksExisted);
        nbt.setInteger("StoredSmoke", smokeStorage);
        NBTTagList savedItems = new NBTTagList();

        for (int i = 0; i < this.items.length; ++i) {
            if (this.items[i] != null) {
                NBTTagCompound savedSlot = new NBTTagCompound();
                savedSlot.setByte("Slot", (byte) i);
                this.items[i].writeToNBT(savedSlot);
                savedItems.appendTag(savedSlot);
            }
        }

        nbt.setTag("Items", savedItems);
        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        fireTime = nbt.getInteger("fireTime");
        isBuilt = nbt.getBoolean("isBuilt");
        ticksExisted = nbt.getInteger("ticksExisted");
        smokeStorage = nbt.getInteger("StoredSmoke");
        NBTTagList savedItems = nbt.getTagList("Items", 10);
        this.items = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < savedItems.tagCount(); ++i) {
            NBTTagCompound savedSlot = savedItems.getCompoundTagAt(i);
            byte slotNum = savedSlot.getByte("Slot");

            if (slotNum >= 0 && slotNum < this.items.length) {
                this.items[slotNum] = new ItemStack(savedSlot);
            }
        }
    }

    // INVENTORY
    public void onInventoryChanged() {
    }

    @Override
    public int getSizeInventory() {
        return items.length;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return items[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int num) {
        onInventoryChanged();
        if (this.items[slot] != null) {
            ItemStack itemstack;

            if (this.items[slot].getCount() <= num) {
                itemstack = this.items[slot];
                this.items[slot] = null;
                return itemstack;
            } else {
                itemstack = this.items[slot].splitStack(num);

                if (this.items[slot].getCount() == 0) {
                    this.items[slot] = null;
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack removeStackFromSlot(int slot) {
        return items[slot];
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack item) {
        onInventoryChanged();
        items[slot] = item;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return player.getDistance(pos.getY() + 0.5D, pos.getY() + 0.5D, pos.getY() + 0.5D) < 8D;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        if (slot == 0) {
            return isCarbon(item);
        }
        return isInput(item);
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {

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
            int carb = MineFantasyFuels.getCarbon(getStackInSlot(0)) - 1;
            if (carb > 0) {
                tempUses = carb;
                MFRLogUtil.logDebug("Set Carbon Uses: " + tempUses);
            }
            return true;
        }
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return side == EnumFacing.UP ? new int[]{0, 1} : new int[]{};
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack item, EnumFacing direction) {
        return isItemValidForSlot(slot, item);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }
}
