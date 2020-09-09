package minefantasy.mfr.tile.decor;

import minefantasy.mfr.api.helpers.BlockPositionHelper;
import minefantasy.mfr.api.weapon.IRackItem;
import minefantasy.mfr.container.ContainerBase;
import minefantasy.mfr.network.NetworkHandler;
import minefantasy.mfr.network.TileInventoryPacket;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityRack extends TileEntityWoodDecor {
    public ItemStackHandler inventory = createInventory();
    private int ticksExisted;

    public TileEntityRack() {
        super("rack_wood");
    }

    @Override
    protected ItemStackHandler createInventory() {
        return new ItemStackHandler(4);
    }

    @Override
    public ItemStackHandler getInventory() {
        return inventory;
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
    public boolean hasFastRenderer() {
        return true;
    }

    @Override
    public void markDirty() {
        ++ticksExisted;
        if (ticksExisted == 10 || ticksExisted % 50 == 0) {
            syncItems();
        }
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        if (world.getTileEntity(pos) != this) {
            return false;
        }
        return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64D;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return false;
    }

    public void syncItems() {
        if (!world.isRemote) {

            NetworkHandler.sendToAllTrackingChunk (world, pos.getX() >> 4, pos.getZ() >> 4, new TileInventoryPacket(getInventory(), this));
        }
    }

    private int getEnchantment(int i) {
        ItemStack is = this.getInventory().getStackInSlot(i);
        if (is.isEmpty())
            return 0;

        if (is.isItemEnchanted())
            return 1;

        return 0;
    }

    /*
     * North: WEST: 0=2 South: SOUTH: 1=1 West: EAST: 2=3 East: NORTH: 3=0
     */
    public int getSlotFor(float x, float y, EnumFacing facing) {
        float offset = 1F / 16F;

        float x1 = 0.0F + offset;
        float x2 = 1.0F - offset;
        float y1 = 0.0F;
        float y2 = 1.0F;
        if (facing == EnumFacing.EAST || facing == EnumFacing.WEST) {
            x1 = 0.0F;
            x2 = 1.0F;
            y1 = 0.0F + offset;
            y2 = 1.0F - offset;
        }
        int[] coord = BlockPositionHelper.getCoordsFor(x, y, x1, x2, y1, y2, 4, 4, facing);

        if (coord == null) {
            return -1;
        }
        return coord[0];

    }

    public boolean canHang(ItemStack item, int slot) {
        if (item.isEmpty() || item.getItem() == null) {
            return false;
        }
        if (item.getItem() instanceof ItemBlock) {
            return false;
        }
        if (item.getItem() instanceof IRackItem) {
            return ((IRackItem) item.getItem()).canHang(this, item, slot);
        }
        if (item.getItem() instanceof ItemArmor) {
            return false;
        }
        // if(item.getItem() instanceof ItemCrossbow || item.getItem() instanceof
        // ItemBomb || item.getItem() instanceof ItemMine)return false;
        return item.getItem().isDamageable();
    }

    public boolean hasRackAbove(int slot) {
        TileEntity side = world.getTileEntity(pos.add(0,1,0));
        return side != null && side instanceof TileEntityRack;// && ((TileEntityRack)side).getStackInSlot(slot) == null;
    }

    public boolean hasRackBelow(int slot) {
        TileEntity side = world.getTileEntity(pos.add(0,-1,0));
        return side != null && side instanceof TileEntityRack;// && ((TileEntityRack)side).getStackInSlot(slot) == null;
    }

    public void updateInventory() {
        if (!world.isRemote) {
            for (int x = 0; x < getInventory().getSlots(); x++) {
                ItemStack item = this.getInventory().getStackInSlot(x);
                if (!item.isEmpty() && !canHang(item, x)) {
                    EntityItem drop = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, item);
                    world.spawnEntity(drop);
                    getInventory().setStackInSlot(x, ItemStack.EMPTY);
                }
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        inventory.deserializeNBT(nbt.getCompoundTag("inventory"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setTag("inventory", inventory.serializeNBT());
        return nbt;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
        }
        return super.getCapability(capability, facing);
    }
}
