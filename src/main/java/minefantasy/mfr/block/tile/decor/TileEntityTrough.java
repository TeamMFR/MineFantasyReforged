package minefantasy.mfr.block.tile.decor;

import minefantasy.mfr.api.heating.IQuenchBlock;
import minefantasy.mfr.init.FoodListMFR;
import minefantasy.mfr.proxy.NetworkUtils;
import minefantasy.mfr.packet.TroughPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
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
                if (held.getItem() == Items.WATER_BUCKET) {
                    user.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BUCKET));
                    addCapacity(bucket);
                    return true;
                }
                if (held.getItem() == FoodListMFR.JUG_WATER) {
                    givePlayerItem(user, held, new ItemStack(FoodListMFR.JUG_EMPTY));
                    addCapacity(jug);
                    return true;
                }
                if (held.getItem() == Items.POTIONITEM && held.getItemDamage() == 0) {
                    givePlayerItem(user, held, new ItemStack(Items.GLASS_BOTTLE));
                    addCapacity(glass_bottle);
                    return true;
                }
            }

            // Take
            if (fill >= 1 && held.getItem() == Items.GLASS_BOTTLE && held.getItemDamage() == 0) {
                givePlayerItem(user, held, new ItemStack(Items.POTIONITEM));
                addCapacity(-glass_bottle);
                return true;
            }
            if (fill >= 16 && held.getItem() == Items.BUCKET) {
                givePlayerItem(user, held, new ItemStack(Items.WATER_BUCKET));
                addCapacity(-bucket);
                return true;
            }

        }
        return false;
    }

    private void givePlayerItem(EntityPlayer user, ItemStack held, ItemStack jug) {
        if (held.getCount() == 1) {
            user.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, jug);
            return;
        }

        held.shrink(1);
        if (!user.inventory.addItemStackToInventory(jug)) {
            if (!user.world.isRemote) {
                user.entityDropItem(jug, 0F);
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("fill", fill);
		return nbt;
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
        if (world.isRemote)
            return;

        NetworkUtils.sendToWatchers(new TroughPacket(this).generatePacket(), (WorldServer) world, this.pos);
        super.sendPacketToClient();
    }

}
