package minefantasy.mfr.block.tile;

import minefantasy.mfr.api.crafting.IHeatUser;
import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.refine.Alloy;
import minefantasy.mfr.api.refine.AlloyRecipes;
import minefantasy.mfr.api.refine.SmokeMechanics;
import minefantasy.mfr.init.BlockListMFR;
import minefantasy.mfr.block.refining.BlockCrucible;
import minefantasy.mfr.block.tile.blastfurnace.TileEntityBlastFH;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;

import java.util.Random;

public class TileEntityCrucible extends TileEntity implements IInventory, ISidedInventory, IHeatUser, ITickable {
    private final int[] grid = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
    private final int[] output = new int[]{9};
    private final int[] whole = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    public float progress = 0, progressMax = 400;
    public float temperature;
    private ItemStack[] inv = new ItemStack[10];
    private Random rand = new Random();

    @Override
    public void update() {
        boolean isHot = getIsHot();
        temperature = getTemperature();
        if (getTier() >= 2) {
            progressMax = 2000;
        }

        /*
         * int time = 400; for(int a = 1; a < getSizeInventory()-1; a ++) { if(inv[a] !=
         * null) { time += 50; } } if(!worldObj.isRemote) { progressMax = time; }
         */

        if (isHot && canSmelt()) {
            progress += (temperature / 600F);
            if (progress >= progressMax) {
                progress = 0;
                smeltItem();
                if (isAuto()) {
                    onAutoSmelt();
                }
            }
        } else {
            progress = 0;
        }
        if (progress > 0 && rand.nextInt(4) == 0 && !isOutside() && this.getTier() < 2) {
            SmokeMechanics.emitSmokeIndirect(world, pos, 1);
        }

        if (isHot != getIsHot()) {
            BlockCrucible.updateFurnaceBlockState(getTemperature() > 0, world, pos);
        }
    }

    private boolean getIsHot() {
        if (this.getTier() >= 2) {
            return this.isCoated();
        }
        return getTemperature() > 0;
    }

    private void onAutoSmelt() {
        world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.AMBIENT,1.0F, 1.0F, true);
        world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.AMBIENT,1.0F, 1.0F, true);
    }

    private boolean isOutside() {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (!world.canBlockSeeSky(pos.add(x,1,y))) {
                    return false;
                }
            }
        }
        return true;
    }

    public void smeltItem() {
        if (!canSmelt()) {
            return;
        }

        ItemStack itemstack = getRecipe();

        if (inv[getOutSlot()] == null) {
            inv[getOutSlot()] = itemstack.copy();
        } else if (CustomToolHelper.areEqual(inv[getOutSlot()], itemstack)) {
            inv[getOutSlot()].grow(itemstack.getCount());
        }

        for (int a = 0; a < (getSizeInventory() - 1); a++) {
            if (inv[a] != null) {
                inv[a].shrink(1);
                if (inv[a].getCount() <= 0) {
                    inv[a] = null;
                }
            }
        }
        if (world.isRemote && getTier() >= 2) {
            spawnParticle(-3, 0, 0);
            spawnParticle(3, 0, 0);
            spawnParticle(0, 0, -3);
            spawnParticle(0, 0, 3);
        }
    }

    private void spawnParticle(int x, int y, int z) {
        this.world.playBroadcastSound(2003, pos.add(x,y,z), 0);
    }

    private boolean canSmelt() {
        if (temperature <= 0) {
            return false;
        }

        ItemStack result = getRecipe();

        if (result == null) {
            return false;
        }

        if (inv[getOutSlot()] == null)
            return true;
        if (inv[getOutSlot()] != null && CustomToolHelper.areEqual(inv[getOutSlot()], result)
                && inv[getOutSlot()].getCount() < (inv[getOutSlot()].getMaxStackSize() - (result.getCount() - 1)))
            return true;
        return false;
    }

    private int getOutSlot() {
        return 9;
    }

    private ItemStack getRecipe() {
        ItemStack[] input = new ItemStack[getSizeInventory() - 1];
        for (int a = 0; a < 9; a++) {
            input[a] = inv[a];
        }
        Alloy alloy = AlloyRecipes.getResult(input);
        if (alloy != null) {
            if (alloy.getLevel() <= getTier()) {
                return AlloyRecipes.getResult(input).getRecipeOutput();
            }
        }
        return null;
    }

    @Override
    public Block getBlockType() {
        if (world == null)
            return Blocks.AIR;

        return super.getBlockType();
    }

    public int getTier() {
        if (this.getBlockType() != null && this.getBlockType() instanceof BlockCrucible) {
            return ((BlockCrucible) this.getBlockType()).tier;
        }
        return 0;
    }

    public boolean isAuto() {
        if (this.getBlockType() != null && this.getBlockType() instanceof BlockCrucible) {
            return ((BlockCrucible) this.getBlockType()).isAuto;
        }
        return false;
    }

    public float getTemperature() {
        if (this.getTier() >= 1 && !isCoated()) {
            return 0F;
        }
        if (getTier() >= 2) {
            return 500;
        }
        IBlockState under = world.getBlockState(pos.add(0,-1,0));

        if (under.getMaterial() == Material.FIRE) {
            return 10F;
        }
        if (under.getMaterial() == Material.LAVA) {
            return 50F;
        }
        TileEntity tile = world.getTileEntity(pos.add(0,-1,0));
        if (tile != null && tile instanceof TileEntityForge) {
            return ((TileEntityForge) tile).getBlockTemperature();
        }
        return 0F;
    }

    public boolean isCoated() {
        if (this.getTier() >= 2) {
            return isEnderAlter(-1, -1, -3) && isEnderAlter(-1, -1, 3) && isEnderAlter(-3, -1, -1)
                    && isEnderAlter(3, -1, -1) && isEnderAlter(1, -1, -3) && isEnderAlter(1, -1, 3)
                    && isEnderAlter(-3, -1, 1) && isEnderAlter(3, -1, 1);
        }
        return isFirebrick(0, 0, -1) && isFirebrick(0, 0, 1) && isFirebrick(-1, 0, 0) && isFirebrick(1, 0, 0);
    }

    private boolean isFirebrick(int x, int y, int z) {
        return world.getBlockState(pos.add(x,y,z)).getBlock() == BlockListMFR.FIREBRICKS;
    }
    // INVENTORY

    private boolean isEnderAlter(int x, int y, int z) {
        Block block = world.getBlockState(pos.add(x,y,z)).getBlock();

        // worldObj.setBlock(xCoord+x, yCoord+y, zCoord+z, Blocks.end_portal_frame, 0,
        // 2);
        return block == Blocks.END_PORTAL_FRAME && block == block.getDefaultState().withProperty(BlockEndPortalFrame.EYE, true);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setFloat("progress", progress);
        nbt.setFloat("progressMax", progressMax);

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
        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        progress = nbt.getFloat("progress");
        progressMax = nbt.getFloat("progressMax");

        NBTTagList savedItems = nbt.getTagList("Items", 10);
        this.inv = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < savedItems.tagCount(); ++i) {
            NBTTagCompound savedSlot = savedItems.getCompoundTagAt(i);
            byte slotNum = savedSlot.getByte("Slot");

            if (slotNum >= 0 && slotNum < this.inv.length) {
                this.inv[slotNum] = new ItemStack(savedSlot);
            }
        }
    }

    @Override
    public int getSizeInventory() {
        return inv.length;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inv[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int num) {
        onInventoryChanged();
        if (this.inv[slot] != null) {
            ItemStack itemstack;

            if (this.inv[slot].getCount() <= num) {
                itemstack = this.inv[slot];
                this.inv[slot] = null;
                return itemstack;
            } else {
                itemstack = this.inv[slot].splitStack(num);

                if (this.inv[slot].getCount() == 0) {
                    this.inv[slot] = null;
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack removeStackFromSlot(int slot) {
        return inv[slot];
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack item) {
        onInventoryChanged();
        inv[slot] = item;
    }

    public void onInventoryChanged() {
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer user) {
        return user.getDistance(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) < 8D;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return true;
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

    private boolean isBlastOutput() {
        if (world == null)
            return false;
        TileEntity tile = world.getTileEntity(pos.add(0,1,0));
        return tile != null && tile instanceof TileEntityBlastFH;
    }

    @Override
    public boolean canAccept(TileEntity tile) {
        return tile instanceof TileEntityForge;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        if (isBlastOutput()) {
            return whole;
        }
        return side == EnumFacing.UP ? output : grid;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack item, EnumFacing direction) {
        return !isBlastOutput() && slot < getOutSlot();
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing direction) {
        return isAuto() && slot == getOutSlot();
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
