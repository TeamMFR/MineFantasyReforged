package minefantasy.mfr.block.tile;

import minefantasy.mfr.api.crafting.IBasicMetre;
import minefantasy.mfr.api.crafting.engineer.ICrossbowPart;
import minefantasy.mfr.api.helpers.ToolHelper;
import minefantasy.mfr.api.rpg.SkillList;
import minefantasy.mfr.init.SoundsMFR;
import minefantasy.mfr.init.ToolListMFR;
import minefantasy.mfr.proxy.NetworkUtils;
import minefantasy.mfr.packet.CrossbowBenchPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.WorldServer;

import java.util.Random;

public class TileEntityCrossbowBench extends TileEntity implements IInventory, ISidedInventory, IBasicMetre {
    public float progress;
    public float maxProgress = 25F;
    public boolean hasRecipe;
    /**
     * Stock, Head, Mod, Muzzle, Result
     */
    private ItemStack[] inv = new ItemStack[5];
    private Random rand = new Random();
    private int ticksExisted;

    private static ICrossbowPart getCrossbowPart(ItemStack item) {
        if (item != null && item.getItem() instanceof ICrossbowPart) {
            return (ICrossbowPart) item.getItem();
        }
        return null;
    }

    public static boolean isMatch(ItemStack item, String var) {
        ICrossbowPart part = getCrossbowPart(item);
        if (part != null) {
            return part.getComponentType().equalsIgnoreCase(var);
        }
        return false;
    }

    @Override
    public void markDirty() {
        ++ticksExisted;
        if (!world.isRemote && (hasRecipe || ticksExisted % 100 == 0)) {
            syncData();
        }
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer user) {
        return user.getDistance(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) < 8D;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    public boolean tryCraft(EntityPlayer user) {
        ItemStack result = findResult();
        hasRecipe = result != null;

        if (result == null) {
            progress = 0F;
        } else if (ToolHelper.getCrafterTool(user.getHeldItemMainhand()).equalsIgnoreCase("spanner")) {
            if (user.getHeldItemMainhand() != null) {
                world.playSound(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, SoundsMFR.TWIST_BOLT, SoundCategory.NEUTRAL, 0.25F, 1.0F, true);
                user.getHeldItemMainhand().damageItem(1, user);
                if (user.getHeldItemMainhand().getItemDamage() >= user.getHeldItemMainhand().getMaxDamage()) {
                    user.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, null);
                }
            }
            float efficiency = ToolHelper.getCrafterEfficiency(user.getHeldItemMainhand());

            if (user.swingProgress > 0 && user.swingProgress <= 1.0) {
                efficiency *= (0.5F - user.swingProgress);
            }

            if (!world.isRemote) {
                progress += efficiency;
            }
            if ((progress >= maxProgress && craftItem(result))) {
                world.playSound(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, SoundEvents.BLOCK_WOODEN_DOOR_OPEN, SoundCategory.AMBIENT, 0.35F, 0.5F, false);
                progress = 0;
                if (user != null) {
                    SkillList.engineering.addXP(user, 10);
                }
                for (int a = 0; a < 4; a++) {
                    decrStackSize(a, 1);
                }
            }
            return true;
        }
        return false;
    }

    private boolean craftItem(ItemStack result) {
        if (inv[4] == null) {
            this.setInventorySlotContents(4, result);
            return true;
        } else {
            return false;
        }
    }

    public void syncData() {
        if (world.isRemote)
            return;

        NetworkUtils.sendToWatchers(new CrossbowBenchPacket(this).generatePacket(), (WorldServer) world, this.pos);

		/*
        List<EntityPlayer> players = ((WorldServer) worldObj).playerEntities;
		for (int i = 0; i < players.size(); i++) {
			EntityPlayer player = players.get(i);
			((WorldServer) worldObj).getEntityTracker().func_151248_b(player,
					new CrossbowBenchPacket(this).generatePacket());
		}
		*/
    }

    // INVENORY

    public void onInventoryChanged() {
    }

    private ItemStack findResult() {
        ICrossbowPart stock = getCrossbowPart(inv[0]);
        ICrossbowPart head = getCrossbowPart(inv[1]);
        ICrossbowPart mod = getCrossbowPart(inv[2]);
        ICrossbowPart muzzle = getCrossbowPart(inv[3]);

        if (stock == null || head == null)
            return null;

        return ToolListMFR.CROSSBOW_CUSTOM.constructCrossbow(stock, head, mod, muzzle);
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
    public ItemStack getStackInSlot(int slot) {
        return inv[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int num) {
        onInventoryChanged();
        if (this.inv[slot] != null) {
            ItemStack itemstack;

            if (this.inv[slot].getCount() <= num) {
                itemstack = this.inv[slot];
                this.inv[slot] = null;
                return itemstack;
            } else {
                itemstack = this.inv[slot].splitStack(num);

                if (this.inv[slot].getCount() == 0) {
                    this.inv[slot] = null;
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack removeStackFromSlot(int slot) {
        return inv[slot];
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack item) {
        onInventoryChanged();
        inv[slot] = item;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        if (this.isMatch(item, "stock")) {
            return slot == 0;
        }
        if (this.isMatch(item, "mechanism")) {
            return slot == 1;
        }
        if (this.isMatch(item, "mod")) {
            return slot == 2;
        }
        if (this.isMatch(item, "muzzle")) {
            return slot == 3;
        }
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
        progress = nbt.getFloat("progress");
        maxProgress = nbt.getFloat("maxProgress");
        hasRecipe = nbt.getBoolean("hasRecipe");
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
        nbt.setFloat("progress", progress);
        nbt.setFloat("maxProgress", maxProgress);
        nbt.setBoolean("hasRecipe", hasRecipe);
        return nbt;
    }

    @Override
    public int getMetreScale(int size) {
        return (int) Math.min(size, size / maxProgress * progress);
    }

    @Override
    public boolean shouldShowMetre() {
        return true;
    }

    @Override
    public String getLocalisedName() {
        return I18n.translateToLocal("tile.crossbowBench.name");
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return new int[]{0, 1, 2, 3, 4};
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack item, EnumFacing direction) {
        return this.isItemValidForSlot(slot, item);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, EnumFacing direction) {
        return slot == 4;
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
