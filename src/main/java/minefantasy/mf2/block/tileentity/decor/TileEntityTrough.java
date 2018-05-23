package minefantasy.mf2.block.tileentity.decor;

import minefantasy.mf2.api.heating.IQuenchBlock;
import minefantasy.mf2.item.food.FoodListMF;
import minefantasy.mf2.network.NetworkUtils;
import minefantasy.mf2.network.packet.TroughPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldServer;

public class TileEntityTrough extends TileEntityWoodDecor implements IQuenchBlock {
    public static int capacityScale = 8;
    public int fill;
    private int ticksExisted;

    public TileEntityTrough() {
        super("trough_wood");
    }

    @Override
    public float quench() {
        if (fill > 0) {
            --fill;
            return 0F;
        }
        return -1F;
    }

    public boolean interact(EntityPlayer user, ItemStack held) {
        if (held != null) {
            int glass_bottle = 1, jug = 1, bucket = 16;
            if (fill < getCapacity())// Give
            {
                if (held.getItem() == Items.water_bucket) {
                    user.setCurrentItemOrArmor(0, new ItemStack(Items.bucket));
                    addCapacity(bucket);
                    return true;
                }
                if (held.getItem() == FoodListMF.jug_water) {
                    givePlayerItem(user, held, new ItemStack(FoodListMF.jug_empty));
                    addCapacity(jug);
                    return true;
                }
                if (held.getItem() == Items.potionitem && held.getItemDamage() == 0) {
                    givePlayerItem(user, held, new ItemStack(Items.glass_bottle));
                    addCapacity(glass_bottle);
                    return true;
                }
            }

            // Take
            if (fill >= 1 && held.getItem() == Items.glass_bottle && held.getItemDamage() == 0) {
                givePlayerItem(user, held, new ItemStack(Items.potionitem));
                addCapacity(-glass_bottle);
                return true;
            }
            if (fill >= 16 && held.getItem() == Items.bucket) {
                givePlayerItem(user, held, new ItemStack(Items.water_bucket));
                addCapacity(-bucket);
                return true;
            }

        }
        return false;
    }

    private void givePlayerItem(EntityPlayer user, ItemStack held, ItemStack jug) {
        if (held.stackSize == 1) {
            user.setCurrentItemOrArmor(0, jug);
            return;
        }

        --held.stackSize;
        if (!user.inventory.addItemStackToInventory(jug)) {
            if (!user.worldObj.isRemote) {
                user.entityDropItem(jug, 0F);
            }
        }
    }

    @Override
    public void updateEntity() {
        ++ticksExisted;
        if (ticksExisted == 20 || ticksExisted % 100 == 0) {
            syncData();
        }
        if (ticksExisted % 100 == 0 && worldObj.canLightningStrikeAt(xCoord, yCoord + 1, zCoord)) {
            addCapacity(1);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("fill", fill);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        fill = nbt.getInteger("fill");
    }

    private void addCapacity(int i) {
        int cap = getCapacity();
        fill = Math.min(cap, fill + i);
        syncData();
    }

    @Override
    public int getCapacity() {
        return super.getCapacity() * capacityScale;
    }

    public void syncData() {
        if (worldObj.isRemote)
            return;

        NetworkUtils.sendToWatchers(new TroughPacket(this).generatePacket(), (WorldServer) worldObj, this.xCoord, this.zCoord);
        super.sendPacketToClient();
    }

}
