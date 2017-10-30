package minefantasy.mf2.block.tileentity.decor;

import minefantasy.mf2.api.helpers.BlockPositionHelper;
import minefantasy.mf2.api.weapon.IRackItem;
import minefantasy.mf2.network.NetworkUtils;
import minefantasy.mf2.network.packet.TileInventoryPacket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class TileEntityRack extends TileEntityWoodDecor implements IInventory {
    private ItemStack inv[];
    private int ticksExisted;

    public TileEntityRack() {
        super("rack_wood");
        inv = new ItemStack[4];
    }

    @Override
    public int getSizeInventory() {
        return inv.length;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return inv[i];
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        if (inv[i] != null) {
            if (inv[i].stackSize <= j) {
                ItemStack itemstack = inv[i];
                inv[i] = null;
                syncItems();
                return itemstack;
            }
            ItemStack itemstack1 = inv[i].splitStack(j);
            if (inv[i].stackSize == 0) {
                inv[i] = null;
            }
            syncItems();
            return itemstack1;
        } else {
            syncItems();
            updateInventory();
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        inv[i] = itemstack;
        updateInventory();
        syncItems();
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

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void updateEntity() {
        ++ticksExisted;
        if (ticksExisted == 10 || ticksExisted % 50 == 0) {
            syncItems();
        }
    }

    public void syncItems() {
        if (!worldObj.isRemote) {
            List<EntityPlayer> players = ((WorldServer) worldObj).playerEntities;

            NetworkUtils.sendToWatchers(new TileInventoryPacket(this, this).generatePacket(), (WorldServer) worldObj, this.xCoord, this.zCoord);
            super.sendPacketToClient();
            /*
			for (int i = 0; i < players.size(); i++) {
				EntityPlayer player = players.get(i);
				((WorldServer) worldObj).getEntityTracker().func_151248_b(player,
						new TileInventoryPacket(this, this).generatePacket());
				super.sendPacketToClient(player);
			}
			*/
        }
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        if (worldObj.getTileEntity(xCoord, yCoord, zCoord) != this) {
            return false;
        }
        return entityplayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64D;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1) {
        return null;
    }

    private int getEnchantment(int i) {
        ItemStack is = this.getStackInSlot(i);
        if (is == null)
            return 0;

        if (is.isItemEnchanted())
            return 1;

        return 0;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return false;
    }

    /*
     * North: WEST: 0=2 South: SOUTH: 1=1 West: EAST: 2=3 East: NORTH: 3=0
     */
    public int getSlotFor(float x, float y) {
        int direction = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        ForgeDirection FD = ForgeDirection.getOrientation(direction);
        float offset = 1F / 16F;

        float x1 = 0.0F + offset;
        float x2 = 1.0F - offset;
        float y1 = 0.0F;
        float y2 = 1.0F;
        if (FD == ForgeDirection.EAST || FD == ForgeDirection.WEST) {
            x1 = 0.0F;
            x2 = 1.0F;
            y1 = 0.0F + offset;
            y2 = 1.0F - offset;
        }
        int[] coord = BlockPositionHelper.getCoordsFor(x, y, x1, x2, y1, y2, 4, 4, direction);

        if (coord == null) {
            return -1;
        }
        return coord[0];

    }

    public boolean canHang(ItemStack item, int slot) {
        if (item == null || item.getItem() == null) {
            return false;
        }
        if (item.getItem() instanceof ItemBlock) {
            return false;
        }
        if (item.getItem() instanceof IRackItem) {
            return ((IRackItem) item.getItem()).canHang(this, item, slot);
        }
        if (item.getItem() instanceof ItemArmor)
            return false;
        // if(item.getItem() instanceof ItemCrossbow || item.getItem() instanceof
        // ItemBomb || item.getItem() instanceof ItemMine)return false;
        return item.getItem().isItemTool(item);
    }

    @Override
    public String getInventoryName() {
        return null;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {
    }

    public boolean hasRackAbove(int slot) {
        TileEntity side = worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
        return side != null && side instanceof TileEntityRack;// && ((TileEntityRack)side).getStackInSlot(slot) == null;
    }

    public boolean hasRackBelow(int slot) {
        TileEntity side = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
        return side != null && side instanceof TileEntityRack;// && ((TileEntityRack)side).getStackInSlot(slot) == null;
    }

    public void updateInventory() {
        if (!worldObj.isRemote) {
            for (int x = 0; x < getSizeInventory(); x++) {
                ItemStack item = this.getStackInSlot(x);
                if (item != null && !canHang(item, x)) {
                    EntityItem drop = new EntityItem(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, item);
                    worldObj.spawnEntityInWorld(drop);
                    setInventorySlotContents(x, null);
                }
            }
        }
    }
}
