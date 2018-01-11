package minefantasy.mf2.block.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.crafting.refine.QuernRecipes;
import minefantasy.mf2.item.list.ComponentListMF;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityQuern extends TileEntity implements IInventory, ISidedInventory {
    public int turnAngle;
    private ItemStack[] inv = new ItemStack[3]; //0 input, 1 pot, 2 output
    private int postUseTicks;

    public static final int getMaxRevs() {
        return 100;
    }

    public static boolean isInput(ItemStack input) {
        return getResult(input) != null;
    }

    private static QuernRecipes getResult(ItemStack input) {
        return QuernRecipes.getResult(input);
    }

    public static boolean isPot(ItemStack item) {
        return item != null && item.getItem() == ComponentListMF.clay_pot;
    }

    @Override
    public void updateEntity() {
        int max = getMaxRevs();
        int levels = max / 4;

        if (postUseTicks > 0) {
            --postUseTicks;
        }
        if (postUseTicks > 0
                || !((turnAngle == levels || turnAngle == levels * 2 || turnAngle == levels * 3 || turnAngle == 0))) {
            this.turnAngle++;
            if (!worldObj.isRemote && (turnAngle == levels || turnAngle == levels * 2 || turnAngle == levels * 3
                    || turnAngle == max)) {
                worldObj.playSoundEffect(xCoord, yCoord, zCoord, "minefantasy2:block.quern", 1.0F, 1.0F);
                onRevolutionComplete();
            }
            if (turnAngle >= max) {
                turnAngle = 0;
            }
        }
    }

    public boolean onUse(EntityPlayer user) {
        if (!worldObj.isRemote && turnAngle == 0 && postUseTicks == 0) {
            worldObj.playSoundEffect(xCoord, yCoord, zCoord, "minefantasy2:block.quern", 1.0F, 1.0F);
        }
        this.postUseTicks = 10;
        return true;
    }

    public boolean onRevolutionComplete() {
        QuernRecipes result = this.getResult(inv[0]);
        if (result != null && (!result.consumePot || inv[1] != null) && result.tier <= getTier()) {
            ItemStack craft = result.result;
            if (canFitResult(craft)) {
                if (!worldObj.isRemote) {
                    return tryCraft(craft, result.consumePot);
                } else {
                    worldObj.spawnParticle("smoke", xCoord + 0.5F, yCoord + 1F, zCoord + 0.5F, 0F, 0.2F, 0F);
                    return true;
                }
            }
        }

        return false;
    }

    private int getTier() {
        return 0;
    }

    private boolean canFitResult(ItemStack result) {
        ItemStack out = inv[2];
        if (out == null)
            return true;

        if (!out.isItemEqual(result))
            return false;
        if (out.stackSize + result.stackSize > result.getMaxStackSize())
            return false;

        return true;
    }

    private boolean tryCraft(ItemStack result, boolean consumePot) {
        worldObj.playSoundEffect(xCoord, yCoord, zCoord, "minefantasy2:block.craftprimitive", 0.5F, 1.2F);
        /*
         * if(rand.nextFloat() > 0.20F)//20% success rate {
         * worldObj.playSoundEffect(xCoord, yCoord, zCoord, "dig.gravel", 1.0F, 0.5F);
         * return false; } else
         */

        this.decrStackSize(0, 1);
        if (consumePot) {
            this.decrStackSize(1, 1);
        }
        ItemStack out = inv[2];
        if (out == null) {
            this.setInventorySlotContents(2, result.copy());
        } else {
            out.stackSize += result.stackSize;
        }
        return true;

    }

    @Override
    public int getSizeInventory() {
        return inv.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inv[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int num) {
        if (this.inv[slot] != null) {
            ItemStack itemstack;

            if (this.inv[slot].stackSize <= num) {
                itemstack = this.inv[slot];
                this.inv[slot] = null;
                return itemstack;
            } else {
                itemstack = this.inv[slot].splitStack(num);

                if (this.inv[slot].stackSize == 0) {
                    this.inv[slot] = null;
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return inv[slot];
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack item) {
        inv[slot] = item;
    }

    @Override
    public String getInventoryName() {
        return "gui.quern.name";
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
        if (item != null && getResult(item) != null) {
            return slot == 0;
        }
        if (item != null && item.getItem() == ComponentListMF.clay_pot) {
            return slot == 1;
        }
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagList savedItems = nbt.getTagList("Items", 10);
        this.inv = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < savedItems.tagCount(); ++i) {
            NBTTagCompound savedSlot = savedItems.getCompoundTagAt(i);
            byte slotNum = savedSlot.getByte("Slot");

            if (slotNum >= 0 && slotNum < this.inv.length) {
                this.inv[slotNum] = ItemStack.loadItemStackFromNBT(savedSlot);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        NBTTagList savedItems = new NBTTagList();

        for (int i = 0; i < this.inv.length; ++i) {
            if (this.inv[i] != null) {
                NBTTagCompound savedSlot = new NBTTagCompound();
                savedSlot.setByte("Slot", (byte) i);
                this.inv[i].writeToNBT(savedSlot);
                savedItems.appendTag(savedSlot);
            }
        }

        nbt.setTag("Items", savedItems);
    }

    @SideOnly(Side.CLIENT)
    public String getTextureName() {
        return "quern_basic";
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[]{0, 1, 2};
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack item, int side) {
        return isItemValidForSlot(slot, item);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack item, int side) {
        return slot == 2;
    }
}
