package minefantasy.mfr.tile.decor;

import minefantasy.mfr.api.heating.IQuenchBlock;
import minefantasy.mfr.block.decor.BlockTrough;
import minefantasy.mfr.container.ContainerBase;
import minefantasy.mfr.init.FoodListMFR;
import minefantasy.mfr.network.NetworkHandler;
import minefantasy.mfr.network.TroughPacket;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityTrough extends TileEntityWoodDecor implements IQuenchBlock, ITickable {
    public static int capacityScale = 8;
    public int fill;
    private int ticksExisted;

    public TileEntityTrough() {
        super("trough_wood");
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
    {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    public void update() {
        ++ticksExisted;
        if (ticksExisted == 20 || ticksExisted % 100 == 0) {
            syncData();
        }
        if (ticksExisted % 100 == 0 && world.isRainingAt(pos.add(0,1,0))) {
            addCapacity(1);
        }
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
        if (!held.isEmpty()) {
            int glass_bottle = 1, jug = 1, bucket = 16;
            if (fill < getCapacity())// Give
            {
                if (held.getItem() == Items.WATER_BUCKET) {
                    user.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BUCKET));
                    addCapacity(bucket);
                    BlockTrough.setActiveState(getFillCount(), world, pos);
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
                    BlockTrough.setActiveState(getFillCount(), world, pos);
                    return true;
                }
            }

            // Take
            if (fill >= 1 && held.getItem() == Items.GLASS_BOTTLE && held.getItemDamage() == 0) {
                givePlayerItem(user, held, new ItemStack(Items.POTIONITEM));
                addCapacity(-glass_bottle);
                BlockTrough.setActiveState(getFillCount(), world, pos);
                return true;
            }
            if (fill >= 16 && held.getItem() == Items.BUCKET) {
                givePlayerItem(user, held, new ItemStack(Items.WATER_BUCKET));
                addCapacity(-bucket);
                BlockTrough.setActiveState(getFillCount(), world, pos);
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

    public int getFillCount(){
        int max_fill = 96;
        int room_left = max_fill - fill;
        int fill_int;
        if (room_left == 0){
            fill_int = 6;
        }
        else if (room_left > 0 && room_left < 16){
            fill_int = 5;
        }
        else if (room_left > 16 && room_left < 32){
            fill_int = 4;
        }
        else if (room_left > 48 && room_left < 64){
            fill_int = 3;
        }
        else if (room_left > 64 && room_left < 80){
            fill_int = 2;
        }
        else if (room_left > 80 && room_left < 96){
            fill_int = 1;
        }
        else {
            fill_int = 0;
        }

        return fill_int;
    }

    public void syncData() {
        if (world.isRemote)
            return;
        NetworkHandler.sendToAllTrackingChunk (world, pos.getX() >> 4, pos.getZ() >> 4, new TroughPacket(this));
        super.sendPacketToClient();
    }

    @Override
    protected ItemStackHandler createInventory() {
        return null;
    }

    @Override
    public ItemStackHandler getInventory() {
        return null;
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
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return false;
    }

}
