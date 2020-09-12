package minefantasy.mfr.tile;

import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.block.decor.BlockComponent;
import minefantasy.mfr.network.NetworkHandler;
import minefantasy.mfr.network.StorageBlockPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityComponent extends TileEntity {
    public ItemStack item;
    public int stackSize, max;
    public String type = "bar";
    public String tex = "bar";
    public CustomMaterial material;
    private int ticksExisted;

    public void setItem(ItemStack item, String type, String tex, int max, int stackSize) {
        this.item = item;
        this.item.setCount(1);
        this.stackSize = stackSize;
        this.max = max;
        this.type = type;
        this.tex = tex;
        this.material = CustomToolHelper.getCustomPrimaryMaterial(item);
        this.ticksExisted = 19;
    }

    public void interact(EntityPlayer user, ItemStack held, boolean leftClick) {
        if (!item.isEmpty() && stackSize > 0) {
            if (leftClick)// Take
            {
                if (held.isEmpty()) {
                    held = item.copy();
                    held.setCount(1);
                    user.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, held);
                    --stackSize;
                } else {
                    ItemStack newitem = item.copy();
                    newitem.setCount(1);
                    if (user.inventory.addItemStackToInventory(newitem)) {
                        --stackSize;
                    }
                }
                if (item.isEmpty() || stackSize <= 0) {
                    world.setBlockToAir(pos);
                    world.removeTileEntity(pos);
                }
            } else if (!held.isEmpty())// PLACE
            {
                if (stackSize < max && item.isItemEqual(held) && ItemStack.areItemStackTagsEqual(item, held)) {
                    ++stackSize;
                    held.shrink(1);
                }

                if (held.getCount() <= 0) {
                    user.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
                }
            }
        }
        checkStack();
        syncData();
    }

    public void checkStack() {
        if (!BlockComponent.canBuildOn(world, pos.add(0,-1,0))) {
            world.setBlockToAir(pos);
        }
        TileEntity tile = world.getTileEntity(pos.add(0,1,0));
        if (tile != null && tile instanceof TileEntityComponent) {
            ((TileEntityComponent) tile).checkStack();
        }
    }

    @Override
    public void markDirty() {
        ++ticksExisted;

        if (ticksExisted == 20 || ticksExisted % 120 == 0) {
            syncData();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        max = nbt.getInteger("max_stack");
        type = nbt.getString("type");
        tex = nbt.getString("tex");
        stackSize = nbt.getInteger("stack_size");

        NBTTagCompound itemsave = nbt.getCompoundTag("item_save");
        item = new ItemStack(itemsave);
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

        if (item != null) {
            NBTTagCompound itemsave = new NBTTagCompound();
            item.writeToNBT(itemsave);
            nbt.setTag("item_save", itemsave);
        }
        if (material != null) {
            nbt.setString("material_name", material.getName());
        }
        return nbt;
    }

    public void syncData() {
        if (world.isRemote)
            return;
        NetworkHandler.sendToAllTrackingChunk (world, pos.getX(), pos.getZ(), new StorageBlockPacket(this));
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
}
