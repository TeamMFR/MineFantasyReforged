package minefantasy.mf2.block.tileentity;

import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.block.decor.BlockComponent;
import minefantasy.mf2.network.NetworkUtils;
import minefantasy.mf2.network.packet.StorageBlockPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

public class TileEntityComponent extends TileEntity {
    public ItemStack item;
    public int stackSize, max;
    public String type = "bar";
    public String tex = "bar";
    public CustomMaterial material;
    private int ticksExisted;

    public void setItem(ItemStack item, String type, String tex, int max, int stackSize) {
        this.item = item;
        this.item.stackSize = 1;
        this.stackSize = stackSize;
        this.max = max;
        this.type = type;
        this.tex = tex;
        this.material = CustomToolHelper.getCustomPrimaryMaterial(item);
        this.ticksExisted = 19;
    }

    public void interact(EntityPlayer user, ItemStack held, boolean leftClick) {
        if (item != null && stackSize > 0) {
            if (leftClick)// Take
            {
                if (held == null) {
                    held = item.copy();
                    held.stackSize = 1;
                    user.setCurrentItemOrArmor(0, held);
                    --stackSize;
                } else {
                    ItemStack newitem = item.copy();
                    newitem.stackSize = 1;
                    if (user.inventory.addItemStackToInventory(newitem)) {
                        --stackSize;
                    }
                }
                if (item == null || stackSize <= 0) {
                    worldObj.setBlockToAir(xCoord, yCoord, zCoord);
                    worldObj.removeTileEntity(xCoord, yCoord, zCoord);
                }
            } else if (held != null)// PLACE
            {
                if (stackSize < max && item.isItemEqual(held) && ItemStack.areItemStackTagsEqual(item, held)) {
                    ++stackSize;
                    --held.stackSize;
                }

                if (held.stackSize <= 0) {
                    user.setCurrentItemOrArmor(0, null);
                }
            }
        }
        checkStack();
        syncData();
    }

    public void checkStack() {
        if (!BlockComponent.canBuildOn(worldObj, xCoord, yCoord - 1, zCoord)) {
            worldObj.setBlockToAir(xCoord, yCoord, zCoord);
        }
        TileEntity tile = worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
        if (tile != null && tile instanceof TileEntityComponent) {
            ((TileEntityComponent) tile).checkStack();
        }
    }

    @Override
    public void updateEntity() {
        ++ticksExisted;

        if (ticksExisted == 20 || ticksExisted % 120 == 0) {
            syncData();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        max = nbt.getInteger("MaxStack");
        type = nbt.getString("type");
        tex = nbt.getString("tex");
        stackSize = nbt.getInteger("StackSize");

        NBTTagCompound itemsave = nbt.getCompoundTag("ItemSave");
        item = ItemStack.loadItemStackFromNBT(itemsave);
        if (nbt.hasKey("MaterialName")) {
            this.material = CustomMaterial.getMaterial(nbt.getString("MaterialName"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setInteger("MaxStack", max);
        nbt.setString("type", type);
        nbt.setString("tex", tex);
        nbt.setInteger("StackSize", stackSize);

        if (item != null) {
            NBTTagCompound itemsave = new NBTTagCompound();
            item.writeToNBT(itemsave);
            nbt.setTag("ItemSave", itemsave);
        }
        if (material != null) {
            nbt.setString("MaterialName", material.getName());
        }
    }

    public void syncData() {
        if (worldObj.isRemote)
            return;

        NetworkUtils.sendToWatchers(new StorageBlockPacket(this).generatePacket(), (WorldServer) worldObj, this.xCoord, this.zCoord);
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
        if (type.equalsIgnoreCase("bigplate")) {
            return Math.max(0.125F, stackSize * 0.125F);
        }
        if (type.equalsIgnoreCase("jug")) {
            return stackSize > 16 ? 1.0F : 0.5F;
        }
        return 1.0F;
    }
}
