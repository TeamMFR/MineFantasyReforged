package minefantasy.mfr.tile;

import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.block.BlockComponent;
import minefantasy.mfr.container.ContainerBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityComponent extends TileEntityBase {
    public int stackSize, max;
    public String type = "bar";
    public String tex = "bar";
    public CustomMaterial material;
    private int ticksExisted;

    public final ItemStackHandler inventory = createInventory();

    @Override
    protected ItemStackHandler createInventory() {
        return new ItemStackHandler(8);
    }

    @Override
    public ItemStackHandler getInventory() {
        return inventory;
    }

    public ItemStack getItem(){
        return getInventory().getStackInSlot(0);
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return true;
    }

    @Override
    public boolean hasFastRenderer() {
        return true;
    }

    public void setItem(ItemStack item, String type, String tex, int max, int stackSize) {
        this.getInventory().setStackInSlot(0, item);
        this.getItem().setCount(1);
        this.stackSize = stackSize;
        this.max = max;
        this.type = type;
        this.tex = tex;
        this.material = CustomToolHelper.getCustomPrimaryMaterial(item);
        this.ticksExisted = 19;
    }

    public void interact(EntityPlayer user, ItemStack held, boolean leftClick) {
        if (!getItem().isEmpty() && stackSize > 0) {
            if (leftClick)// Take
            {
                int amount = user.isSneaking() ? stackSize : 1;
                if (held.isEmpty()) {
                    held = getItem().copy();
                    held.setCount(amount);
                    user.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, held);
                    stackSize -= amount;
                } else {
                    ItemStack newitem = getItem().copy();
                    newitem.setCount(amount);
                    if (user.inventory.addItemStackToInventory(newitem)) {
                        stackSize -= amount;
                    }
                }
                if (getItem().isEmpty() || stackSize <= 0) {
                    world.setBlockToAir(pos);
                    world.removeTileEntity(pos);
                }
            } else if (!held.isEmpty())// PLACE
            {
                if (stackSize < max && getItem().isItemEqual(held) && ItemStack.areItemStackTagsEqual(getItem(), held)) {
                    int amount = user.isSneaking() ? held.getCount() : 1;
                    if (amount != 1) {
                        amount = amount < max ? Math.min(amount, (max - stackSize)) : (max - stackSize);
                    }
                    stackSize += amount;
                    held.shrink(amount);
                }

                if (held.getCount() <= 0) {
                    user.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
                }
            }
        }
        checkStack();
        sendUpdates();
    }

    public void checkStack() {
        if (!BlockComponent.canBuildOn(world, pos.add(0,-1,0))) {
            world.setBlockToAir(pos);
        }
        TileEntity tile = world.getTileEntity(pos.add(0,1,0));
        if (tile instanceof TileEntityComponent) {
            ((TileEntityComponent) tile).checkStack();
        }
    }

    @Override
    public void markDirty() {
        ++ticksExisted;

        if (ticksExisted == 20 || ticksExisted % 120 == 0) {
            sendUpdates();
        }
    }

    public boolean isFull() {
        return stackSize >= max;
    }

    public float getBlockHeight() {
        if (type.equalsIgnoreCase("sheet")) {
            return stackSize * 0.0625F;
        }
        if (type.equalsIgnoreCase("plank")) {
            float f = 0.125F;
            if (stackSize > 42)
                return 8F * f;
            if (stackSize > 36)
                return 7F * f;
            if (stackSize > 30)
                return 6F * f;
            if (stackSize > 24)
                return 5F * f;
            if (stackSize > 18)
                return 4F * f;
            if (stackSize > 12)
                return 3F * f;
            if (stackSize > 6)
                return 2F * f;

            return f;
        }
        if (type.equalsIgnoreCase("bar")) {
            float f = 0.125F;
            if (stackSize > 50)
                return 8F * f;
            if (stackSize > 48)
                return 7F * f;
            if (stackSize > 34)
                return 6F * f;
            if (stackSize > 32)
                return 5F * f;
            if (stackSize > 18)
                return 4F * f;
            if (stackSize > 16)
                return 3F * f;
            if (stackSize > 2)
                return 2F * f;

            return f;
        }
        if (type.equalsIgnoreCase("pot")) {
            float f = 0.25F;
            if (stackSize > 48)
                return 4F * f;
            if (stackSize > 32)
                return 3F * f;
            if (stackSize > 16)
                return 2F * f;

            return f;
        }
        if (type.equalsIgnoreCase("big_plate")) {
            return Math.max(0.125F, stackSize * 0.125F);
        }
        if (type.equalsIgnoreCase("jug")) {
            return stackSize > 16 ? 1.0F : 0.5F;
        }
        return 1.0F;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        max = nbt.getInteger("max_stack");
        type = nbt.getString("type");
        tex = nbt.getString("tex");
        stackSize = nbt.getInteger("stack_size");
        inventory.deserializeNBT(nbt.getCompoundTag("inventory"));
        if (nbt.hasKey("material_name")) {
            this.material = CustomMaterial.getMaterial(nbt.getString("material_name"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setInteger("max_stack", max);
        nbt.setString("type", type);
        nbt.setString("tex", tex);
        nbt.setInteger("stack_size", stackSize);

        if (!getItem().isEmpty()) {
            nbt.setTag("inventory", inventory.serializeNBT());
        }
        if (material != null) {
            nbt.setString("material_name", material.getName());
        }
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

    @Override
    public ContainerBase createContainer(EntityPlayer player) {
        return null;
    }

    @Override
    protected int getGuiId() {
        return 0;
    }
}
