package minefantasy.mf2.block.tileentity.decor;

import minefantasy.mf2.api.archery.AmmoMechanicsMF;
import minefantasy.mf2.api.archery.IAmmo;
import minefantasy.mf2.api.archery.IFirearm;
import minefantasy.mf2.api.crafting.IBasicMetre;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.api.tool.IStorageBlock;
import minefantasy.mf2.block.decor.BlockAmmoBox;
import minefantasy.mf2.item.ItemBandage;
import minefantasy.mf2.item.gadget.ItemSyringe;
import minefantasy.mf2.network.NetworkUtils;
import minefantasy.mf2.network.packet.AmmoBoxPacket;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldServer;

public class TileEntityAmmoBox extends TileEntityWoodDecor implements IBasicMetre {
    public int angle, stock;
    public ItemStack ammo;
    private byte storageSize = -1;
    private int ticksExisted;

    public TileEntityAmmoBox() {
        super("ammo_box_basic", CustomMaterial.getMaterial("RefinedWood"));
    }

    public TileEntityAmmoBox(String tex, CustomMaterial material, byte size) {
        super(tex, material);
        this.storageSize = size;
    }

    public boolean interact(EntityPlayer user, ItemStack held) {
        if (held != null) {
            if (this.getStorageType() == 1 && ammo != null && held.getItem() instanceof IFirearm && loadGun(held)) {
                open();
                syncData();
                return true;
            }
            if (canAcceptItem(held)) {
                int max = this.getMaxAmmo(ammo != null ? ammo : held);
                if (ammo == null) {
                    open();
                    placeInEmpty(user, held, max);
                } else if (areItemStacksEqual(held, ammo) && stock < max) {
                    open();
                    addToBox(user, held, max);
                }

                syncData();
                return true;
            }
            return false;
        } else if (ammo != null) {
            open();
            takeStack(user, held);
            syncData();
            return true;
        }
        syncData();
        return false;
    }

    private boolean canAcceptItem(ItemStack held) {
        if (held.getItem() instanceof IStorageBlock) {
            return false;
        }
        byte type = this.getStorageType();

        return type == 0 ? isFood(held) : type == 1 ? held.getItem() instanceof IAmmo : type == 2;
    }

    private boolean isFood(ItemStack held) {
        return held.getItem() instanceof ItemFood || held.getItem() instanceof ItemBandage
                || held.getItem() instanceof ItemSyringe;
    }

    private boolean loadGun(ItemStack held) {
        IFirearm gun = (IFirearm) held.getItem();
        if (gun.canAcceptAmmo(held, getAmmoClass())) {
            ItemStack loaded = AmmoMechanicsMF.getAmmo(held);
            if (loaded == null) {
                int ss = Math.min(ammo.getMaxStackSize(), stock);
                ItemStack newloaded = ammo.copy();
                newloaded.stackSize = ss;
                AmmoMechanicsMF.setAmmo(held, newloaded);
                stock -= ss;
                if (stock <= 0) {
                    ammo = null;
                }
                return true;
            } else if (areItemStacksEqual(loaded, ammo)) {
                int room_left = loaded.getMaxStackSize() - loaded.stackSize;
                if (stock > room_left) {
                    stock -= room_left;
                    loaded.stackSize += room_left;
                    AmmoMechanicsMF.setAmmo(held, loaded);
                } else {
                    loaded.stackSize += stock;
                    AmmoMechanicsMF.setAmmo(held, loaded);
                    stock = 0;
                    ammo = null;
                }

                return true;
            }
        }
        return false;
    }

    private void addToBox(EntityPlayer user, ItemStack held, int max) {
        int room_left = max - stock;
        if (held.stackSize <= room_left) {
            stock += held.stackSize;
            user.setCurrentItemOrArmor(0, null);
        } else {
            held.stackSize -= room_left;
            stock += room_left;
        }
    }

    private void takeStack(EntityPlayer user, ItemStack held) {
        int ss = stock;
        ItemStack taken = ammo.copy();
        if (ss > taken.getMaxStackSize()) {
            ss = taken.getMaxStackSize();
        }
        taken.stackSize = ss;
        stock -= ss;
        user.setCurrentItemOrArmor(0, taken);
        if (stock <= 0) {
            ammo = null;
        }
    }

    /**
     * Place ammo in empty box
     */
    private void placeInEmpty(EntityPlayer user, ItemStack held, int max) {
        ammo = held.copy();
        int ss = held.stackSize;
        if (held.stackSize <= max)// Place all
        {
            user.setCurrentItemOrArmor(0, null);
        } else // Fill as much
        {
            stock = max;
            held.stackSize -= max;
        }
        stock = ss;
    }

    private void open() {
        angle = 16;
        this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.25D, this.zCoord + 0.5D, "random.chestclosed",
                0.5F, this.worldObj.rand.nextFloat() * 0.1F + 1.4F);
    }

    @Override
    public void updateEntity() {
        ++ticksExisted;
        if (ticksExisted == 20 || ticksExisted % 100 == 0) {
            syncData();
        }
        if (angle > 0)
            --angle;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("stock", stock);
        if (ammo != null) {
            NBTTagCompound itemsave = new NBTTagCompound();
            ammo.writeToNBT(itemsave);
            nbt.setTag("storage", itemsave);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        stock = nbt.getInteger("stock");
        if (nbt.hasKey("storage")) {
            NBTTagCompound itemsave = nbt.getCompoundTag("storage");
            ammo = ItemStack.loadItemStackFromNBT(itemsave);
        }
    }

    public void syncData() {
        if (worldObj.isRemote)
            return;

        NetworkUtils.sendToWatchers(new AmmoBoxPacket(this).generatePacket(), (WorldServer) worldObj, this.xCoord, this.zCoord);

		/*
        List<EntityPlayer> players = ((WorldServer) worldObj).playerEntities;
		for (int i = 0; i < players.size(); i++) {
			EntityPlayer player = players.get(i);
			((WorldServer) worldObj).getEntityTracker().func_151248_b(player, new AmmoBoxPacket(this).generatePacket());
			super.sendPacketToClient(player);
		}
		*/
    }

    public int getMaxAmmo(ItemStack ammo) {
        return ammo.getMaxStackSize() * getCapacity(getMaterial().tier);
    }

    @Override
    public int getMetreScale(int size) {
        if (ammo != null) {
            return (int) Math.min(size, (float) size / (float) getMaxAmmo(ammo) * stock);
        }
        return 0;
    }

    @Override
    public boolean shouldShowMetre() {
        return ammo != null;
    }

    @Override
    public String getLocalisedName() {
        if (ammo != null) {
            return ammo.getDisplayName() + " x" + stock;
        }
        return "";
    }

    private boolean areItemStacksEqual(ItemStack i1, ItemStack i2) {
        if (i1 == null || i2 == null)
            return false;

        return i1.isItemEqual(i2) && ItemStack.areItemStackTagsEqual(i1, i2);
    }

    private String getAmmoClass() {
        if (ammo != null && ammo.getItem() instanceof IAmmo) {
            return ((IAmmo) ammo.getItem()).getAmmoType(ammo);
        }
        return "null";
    }

    public byte getStorageType() {
        if (worldObj != null) {
            Block block = worldObj.getBlock(xCoord, yCoord, zCoord);
            if (block instanceof BlockAmmoBox) {
                return ((BlockAmmoBox) block).storageType;
            }
        }
        return storageSize;
    }
}
