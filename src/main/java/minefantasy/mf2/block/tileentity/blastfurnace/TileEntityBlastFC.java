package minefantasy.mf2.block.tileentity.blastfurnace;

import minefantasy.mf2.api.crafting.MineFantasyFuels;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.refine.BlastFurnaceRecipes;
import minefantasy.mf2.api.refine.ISmokeCarrier;
import minefantasy.mf2.api.refine.SmokeMechanics;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import java.util.Random;

public class TileEntityBlastFC extends TileEntity implements IInventory, ISidedInventory, ISmokeCarrier {
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
    public void updateEntity() {
        super.updateEntity();
        ++ticksExisted;
        int dropFrequency = 5;

        if (!worldObj.isRemote && ticksExisted % dropFrequency == 0) {
            TileEntity neighbour = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
            if (neighbour != null && neighbour instanceof TileEntityBlastFC
                    && !(neighbour instanceof TileEntityBlastFH)) {
                interact((TileEntityBlastFC) neighbour);
            }
        }
        if (ticksExisted % 200 == 0) {
            updateBuild();
        }
        if (smokeStorage > 0) {
            SmokeMechanics.emitSmokeFromCarrier(worldObj, xCoord, yCoord, zCoord, this, 5);
        }
        if (!worldObj.isRemote && smokeStorage > getMaxSmokeStorage() && rand.nextInt(1000) == 0) {
            worldObj.newExplosion(null, xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, 5F, true, true);
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
                    copy.stackSize = 1;
                    tile.setInventorySlotContents(a, copy);
                    this.decrStackSize(a, 1);
                } else if (CustomToolHelper.areEqual(theirSlot, mySlot)) {
                    if ((theirSlot.stackSize) < getMaxStackSizeForDistribute()) {
                        theirSlot.stackSize++;
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
        Block block = worldObj.getBlock(xCoord + x, yCoord + y, zCoord + z);
        if (block != null) {
            return block == BlockListMF.firebricks;
        }
        return false;
    }

    protected boolean isAir(int x, int y, int z) {
        return !worldObj.isBlockNormalCubeDefault(xCoord + x, yCoord + y, zCoord + z, false);
    }

    private int getMaxStackSizeForDistribute() {
        return 1;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
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
                this.items[slotNum] = ItemStack.loadItemStackFromNBT(savedSlot);
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
    public ItemStack getStackInSlot(int slot) {
        return items[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int num) {
        onInventoryChanged();
        if (this.items[slot] != null) {
            ItemStack itemstack;

            if (this.items[slot].stackSize <= num) {
                itemstack = this.items[slot];
                this.items[slot] = null;
                return itemstack;
            } else {
                itemstack = this.items[slot].splitStack(num);

                if (this.items[slot].stackSize == 0) {
                    this.items[slot] = null;
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return items[slot];
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack item) {
        onInventoryChanged();
        items[slot] = item;
    }

    @Override
    public String getInventoryName() {
        return "gui.bombcraftmf.name";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer user) {
        return user.getDistance(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) < 8D;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        if (slot == 0) {
            return isCarbon(item);
        }
        return isInput(item);
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return side == 1 ? new int[]{0, 1} : new int[]{};
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack item, int side) {
        return isItemValidForSlot(slot, item);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack item, int side) {
        return false;
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
            MFLogUtil.logDebug("Decr Carbon Uses: " + tempUses);
            return false;
        } else {
            int carb = MineFantasyFuels.getCarbon(getStackInSlot(0)) - 1;
            if (carb > 0) {
                tempUses = carb;
                MFLogUtil.logDebug("Set Carbon Uses: " + tempUses);
            }
            return true;
        }
    }
}
