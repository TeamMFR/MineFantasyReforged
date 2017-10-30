package minefantasy.mf2.block.tileentity;

import minefantasy.mf2.api.crafting.IBasicMetre;
import minefantasy.mf2.api.crafting.engineer.ICrossbowPart;
import minefantasy.mf2.api.helpers.ToolHelper;
import minefantasy.mf2.api.rpg.SkillList;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.network.NetworkUtils;
import minefantasy.mf2.network.packet.CrossbowBenchPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
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
    public void updateEntity() {
        ++ticksExisted;
        if (!worldObj.isRemote && (hasRecipe || ticksExisted % 100 == 0)) {
            syncData();
        }
    }

    public boolean tryCraft(EntityPlayer user) {
        ItemStack result = findResult();
        hasRecipe = result != null;

        if (result == null) {
            progress = 0F;
        } else if (ToolHelper.getCrafterTool(user.getHeldItem()).equalsIgnoreCase("spanner")) {
            if (user.getHeldItem() != null) {
                worldObj.playSoundEffect(xCoord + 0.5, yCoord, zCoord + 0.5, "minefantasy2:block.twistbolt", 0.25F,
                        1.0F);
                user.getHeldItem().damageItem(1, user);
                if (user.getHeldItem().getItemDamage() >= user.getHeldItem().getMaxDamage()) {
                    user.destroyCurrentEquippedItem();
                }
            }
            float efficiency = ToolHelper.getCrafterEfficiency(user.getHeldItem());

            if (user.swingProgress > 0 && user.swingProgress <= 1.0) {
                efficiency *= (0.5F - user.swingProgress);
            }

            if (!worldObj.isRemote) {
                progress += efficiency;
            }
            if ((progress >= maxProgress && craftItem(result))) {
                worldObj.playSoundEffect(xCoord + 0.5, yCoord, zCoord + 0.5, "random.door_open", 0.35F, 0.5F);
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
        if (worldObj.isRemote)
            return;

        NetworkUtils.sendToWatchers(new CrossbowBenchPacket(this).generatePacket(), (WorldServer) worldObj, this.xCoord, this.zCoord);

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

        return ToolListMF.crossbow_custom.constructCrossbow(stock, head, mod, muzzle);
    }

    @Override
    public int getSizeInventory() {
        return inv.length;
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

            if (this.inv[slot].stackSize <= num) {
                itemstack = this.inv[slot];
                this.inv[slot] = null;
                return itemstack;
            } else {
                itemstack = this.inv[slot].splitStack(num);

                if (this.inv[slot].stackSize == 0) {
                    this.inv[slot] = null;
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return inv[slot];
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack item) {
        onInventoryChanged();
        inv[slot] = item;
    }

    @Override
    public String getInventoryName() {
        return "gui.crossbowcraftmf.name";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer user) {
        return user.getDistance(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) < 8D;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
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
        progress = nbt.getFloat("progress");
        maxProgress = nbt.getFloat("maxProgress");
        hasRecipe = nbt.getBoolean("hasRecipe");
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
        nbt.setFloat("progress", progress);
        nbt.setFloat("maxProgress", maxProgress);
        nbt.setBoolean("hasRecipe", hasRecipe);
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[]{0, 1, 2, 3, 4};
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack item, int side) {
        return this.isItemValidForSlot(slot, item);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack item, int side) {
        return slot == 4;
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
        return StatCollector.translateToLocal("tile.crossbowBench.name");
    }
}
