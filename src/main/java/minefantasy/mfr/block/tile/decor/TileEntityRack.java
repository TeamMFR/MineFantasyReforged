package minefantasy.mfr.block.tile.decor;

import minefantasy.mfr.api.helpers.BlockPositionHelper;
import minefantasy.mfr.api.weapon.IRackItem;
import minefantasy.mfr.proxy.NetworkUtils;
import minefantasy.mfr.packet.TileInventoryPacket;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

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
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return inv[i];
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        if (inv[i] != null) {
            if (inv[i].getCount() <= j) {
                ItemStack itemstack = inv[i];
                inv[i] = null;
                syncItems();
                return itemstack;
            }
            ItemStack itemstack1 = inv[i].splitStack(j);
            if (inv[i].getCount() == 0) {
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
    public ItemStack removeStackFromSlot(int index) {
        return null;
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
                this.inv[slotNum] = new ItemStack(savedSlot);
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
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
		return nbt;
	}

    @Override
    public int getInventoryStackLimit() {
        return 64;
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
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    public void syncItems() {
        if (!world.isRemote) {
            List<EntityPlayer> players = ((WorldServer) world).playerEntities;

            NetworkUtils.sendToWatchers(new TileInventoryPacket(this, this).generatePacket(), (WorldServer) world, this.pos);
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

    /*
     * North: WEST: 0=2 South: SOUTH: 1=1 West: EAST: 2=3 East: NORTH: 3=0
     */
    public int getSlotFor(float x, float y, EntityPlayer user) {
        int direction = Block.getStateId(world.getBlockState(pos));
        EnumFacing facing = EnumFacing.getDirectionFromEntityLiving(pos, user);
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
            for (int x = 0; x < getSizeInventory(); x++) {
                ItemStack item = this.getStackInSlot(x);
                if (item != null && !canHang(item, x)) {
                    EntityItem drop = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, item);
                    world.spawnEntity(drop);
                    setInventorySlotContents(x, null);
                }
            }
        }
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
