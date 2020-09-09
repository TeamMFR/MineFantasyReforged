package minefantasy.mfr.tile.decor;

import minefantasy.mfr.api.archery.AmmoMechanicsMFR;
import minefantasy.mfr.api.archery.IAmmo;
import minefantasy.mfr.api.archery.IFirearm;
import minefantasy.mfr.api.crafting.IBasicMetre;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.api.tool.IStorageBlock;
import minefantasy.mfr.block.decor.BlockAmmoBox;
import minefantasy.mfr.item.ItemBandage;
import minefantasy.mfr.item.gadget.ItemSyringe;
import minefantasy.mfr.proxy.NetworkUtils;
import minefantasy.mfr.network.AmmoBoxPacket;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
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
            ItemStack loaded = AmmoMechanicsMFR.getAmmo(held);
            if (loaded == null) {
                int ss = Math.min(ammo.getMaxStackSize(), stock);
                ItemStack newloaded = ammo.copy();
                newloaded.setCount(ss);
                AmmoMechanicsMFR.setAmmo(held, newloaded);
                stock -= ss;
                if (stock <= 0) {
                    ammo = null;
                }
                return true;
            } else if (areItemStacksEqual(loaded, ammo)) {
                int room_left = loaded.getMaxStackSize() - loaded.getCount();
                if (stock > room_left) {
                    stock -= room_left;
                    loaded.grow(room_left);
                    AmmoMechanicsMFR.setAmmo(held, loaded);
                } else {
                    loaded.grow(stock);
                    AmmoMechanicsMFR.setAmmo(held, loaded);
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
        if (held.getCount() <= room_left) {
            stock += held.getCount();
            user.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, null);
        } else {
            held.shrink(room_left);
            stock += room_left;
        }
    }

    private void takeStack(EntityPlayer user, ItemStack held) {
        int ss = stock;
        ItemStack taken = ammo.copy();
        if (ss > taken.getMaxStackSize()) {
            ss = taken.getMaxStackSize();
        }
        taken.setCount(ss);
        stock -= ss;
        user.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, taken);
        if (stock <= 0) {
            ammo = null;
        }
    }

    /**
     * Place ammo in empty box
     */
    private void placeInEmpty(EntityPlayer user, ItemStack held, int max) {
        ammo = held.copy();
        int ss = held.getCount();
        if (held.getCount() <= max)// Place all
        {
            user.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, null);
        } else // Fill as much
        {
            stock = max;
            held.shrink(max);
        }
        stock = ss;
    }

    private void open() {
        angle = 16;
        this.world.playSound(this.pos.getX() + 0.5D, this.pos.getY() + 0.25D, this.pos.getZ() + 0.5D, SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.AMBIENT, 0.5F, this.world.rand.nextFloat() * 0.1F + 1.4F, false);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("stock", stock);
        if (ammo != null) {
            NBTTagCompound itemsave = new NBTTagCompound();
            ammo.writeToNBT(itemsave);
            nbt.setTag("storage", itemsave);
        }
        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        stock = nbt.getInteger("stock");
        if (nbt.hasKey("storage")) {
            NBTTagCompound itemsave = nbt.getCompoundTag("storage");
            ammo = new ItemStack(itemsave);
        }
    }

    public void syncData() {
        if (world.isRemote)
            return;

        NetworkUtils.sendToWatchers(new AmmoBoxPacket(this).generatePacket(), (WorldServer) world, this.pos);

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
        if (world != null) {
            Block block = world.getBlockState(pos).getBlock();
            if (block instanceof BlockAmmoBox) {
                return ((BlockAmmoBox) block).storageType;
            }
        }
        return storageSize;
    }
}
