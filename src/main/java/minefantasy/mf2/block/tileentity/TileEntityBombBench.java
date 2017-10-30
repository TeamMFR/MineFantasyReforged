package minefantasy.mf2.block.tileentity;

import minefantasy.mf2.api.crafting.IBasicMetre;
import minefantasy.mf2.api.crafting.engineer.IBombComponent;
import minefantasy.mf2.api.helpers.ToolHelper;
import minefantasy.mf2.api.knowledge.ResearchLogic;
import minefantasy.mf2.api.rpg.SkillList;
import minefantasy.mf2.item.gadget.ItemBomb;
import minefantasy.mf2.item.gadget.ItemExplodingArrow;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.knowledge.KnowledgeListMF;
import minefantasy.mf2.network.NetworkUtils;
import minefantasy.mf2.network.packet.BombBenchPacket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.WorldServer;

import java.util.Random;

public class TileEntityBombBench extends TileEntity implements IInventory, ISidedInventory, IBasicMetre {
    public float progress;
    public float maxProgress = 25F;
    public boolean hasRecipe;
    private ItemStack[] inv = new ItemStack[6];
    private Random rand = new Random();
    private int ticksExisted;

    public static boolean isMatch(ItemStack item, String type) {
        String component = getComponentType(item);
        return component != null && component.equalsIgnoreCase(type);
    }

    public static String getComponentType(ItemStack item) {
        if (item != null && item.getItem() != null && item.getItem() instanceof IBombComponent) {
            return ((IBombComponent) item.getItem()).getComponentType();
        }
        return null;
    }

    public static byte getComponentTier(ItemStack item) {
        if (item != null && item.getItem() != null && item.getItem() instanceof IBombComponent) {
            return ((IBombComponent) item.getItem()).getTier();
        }
        return (byte) 0;
    }

    @Override
    public void updateEntity() {
        ++ticksExisted;
        if (!worldObj.isRemote && (hasRecipe || ticksExisted % 100 == 0)) {
            syncData();
        }
    }

    public boolean tryCraft(EntityPlayer user, boolean pressUsed) {
        boolean sticky = !pressUsed && ResearchLogic.hasInfoUnlocked(user, KnowledgeListMF.stickybomb)
                && user.getHeldItem() != null && user.getHeldItem().getItem() == Items.slime_ball;
        if (!worldObj.isRemote && sticky && applySlime()) {
            user.inventory.consumeInventoryItem(Items.slime_ball);
            return true;
        }
        ItemStack result = findResult();
        hasRecipe = result != null;

        if (result == null) {
            progress = 0F;
        } else if ((pressUsed || ToolHelper.getCrafterTool(user.getHeldItem()).equalsIgnoreCase("spanner"))) {
            if (!pressUsed && user.getHeldItem() != null) {
                worldObj.playSoundEffect(xCoord + 0.5, yCoord, zCoord + 0.5, "minefantasy2:block.twistbolt", 0.25F,
                        1.0F);
                user.getHeldItem().damageItem(1, user);
                if (user.getHeldItem().getItemDamage() >= user.getHeldItem().getMaxDamage()) {
                    user.destroyCurrentEquippedItem();
                }
            }
            float efficiency = pressUsed ? maxProgress : ToolHelper.getCrafterEfficiency(user.getHeldItem());

            if (!pressUsed && user.swingProgress > 0 && user.swingProgress <= 1.0) {
                efficiency *= (0.5F - user.swingProgress);
            }

            if (!worldObj.isRemote) {
                progress += efficiency;
            }
            if ((progress >= maxProgress && craftItem(result))) {
                worldObj.playSoundEffect(xCoord + 0.5, yCoord, zCoord + 0.5, "random.door_open", 0.35F, 0.5F);
                progress = 0;
                if (user != null) {
                    SkillList.engineering.addXP(user, 2);
                }
                boolean isArrow = isMatch(0, "arrow") || isMatch(0, "bolt");
                for (int a = 0; a < 4; a++) {
                    if (!(isArrow && a == 3)) {
                        ItemStack item = getStackInSlot(a);
                        if (item != null && item.getItem().getContainerItem(item) != null) {
                            // START CONTAINER CODE
                            ItemStack cont = item.getItem().getContainerItem(item);
                            ItemStack spare = getStackInSlot(5);
                            if (spare == null) {
                                setInventorySlotContents(5, cont);
                                cont = null;
                            } else if (spare.isItemEqual(cont)) {
                                if (spare.stackSize + cont.stackSize <= spare.getMaxStackSize()) {
                                    spare.stackSize += cont.stackSize;
                                    cont = null;
                                } else {
                                    int room_left = spare.getMaxStackSize() - spare.stackSize;
                                    spare.stackSize += room_left;
                                    cont.stackSize -= room_left;
                                }
                            }
                            if (cont != null && !worldObj.isRemote) {
                                EntityItem ei = new EntityItem(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5,
                                        cont);
                                worldObj.spawnEntityInWorld(ei);
                            }
                        }
                        // END CONTAINER CODE
                        decrStackSize(a, 1);
                    }
                }
            }
            return true;
        }
        return false;
    }

    private boolean applySlime() {
        ItemStack res = getStackInSlot(4);

        if (res != null && res.getItem() instanceof ItemBomb) {
            if (res.hasTagCompound() && !res.getTagCompound().hasKey("stickyBomb")) {
                res.getTagCompound().setBoolean("stickyBomb", true);
                return true;
            }
        }
        return false;
    }

    private boolean craftItem(ItemStack result) {
        if (inv[4] == null) {
            this.setInventorySlotContents(4, result);
            return true;
        } else {
            if (areItemsEqual(result, inv[4]) && (inv[4].stackSize + result.stackSize) <= inv[4].getMaxStackSize()) {
                inv[4].stackSize++;
                return true;
            } else {
                return false;
            }
        }
    }

    public void syncData() {
        if (worldObj.isRemote)
            return;

        NetworkUtils.sendToWatchers(new BombBenchPacket(this).generatePacket(), (WorldServer) worldObj, this.xCoord, this.zCoord);

		/*
        List<EntityPlayer> players = ((WorldServer) worldObj).playerEntities;
		for (int i = 0; i < players.size(); i++) {
			EntityPlayer player = players.get(i);
			((WorldServer) worldObj).getEntityTracker().func_151248_b(player,
					new BombBenchPacket(this).generatePacket());
		}
		*/
    }

    private boolean areItemsEqual(ItemStack bomb1, ItemStack bomb2) {
        if (ItemBomb.getCasing(bomb1) != ItemBomb.getCasing(bomb2)) {
            return false;
        }
        if (ItemBomb.getPowder(bomb1) != ItemBomb.getPowder(bomb2)) {
            return false;
        }
        if (ItemBomb.getFilling(bomb1) != ItemBomb.getFilling(bomb2)) {
            return false;
        }
        if (ItemBomb.getFuse(bomb1) != ItemBomb.getFuse(bomb2)) {
            return false;
        }
        return bomb1.isItemEqual(bomb2);
    }

    public void onInventoryChanged() {
    }

    private ItemStack findResult() {
        boolean isArrow = isMatch(0, "arrow") || isMatch(0, "bolt");
        if (!isMatch(1, "powder") || (!isArrow && !isMatch(3, "fuse"))) {
            return null;
        }
        byte caseTier = -1;
        byte filling = 0;
        byte fuse = -1;
        byte powder = -1;
        Item design = null;
        String com0 = getComponentType(inv[0]);
        String com1 = getComponentType(inv[1]);
        String com2 = getComponentType(inv[2]);
        String com3 = getComponentType(inv[3]);
        if (com0 != null) {
            caseTier = getComponentTier(inv[0]);
            String type = com0;
            design = getDesignCrafted(type);
        }
        if (com1 != null) {
            if (com1.equalsIgnoreCase("powder")) {
                powder = getComponentTier(inv[1]);
            } else {
                return null;
            }
        } else
            return null;
        if (com2 != null) {
            if (com2.equalsIgnoreCase("filling")) {
                filling = getComponentTier(inv[2]);
            } else {
                return null;
            }
        }
        if (isArrow || com3 != null) {
            if (!isArrow) {
                if (com3.equalsIgnoreCase("fuse")) {
                    fuse = getComponentTier(inv[3]);
                } else {
                    return null;
                }
            }
        } else
            return null;

        if (design != null && isArrow && powder > -1) {
            return ItemExplodingArrow.createBombArrow(design, powder, filling);
        } else if (design != null && fuse > -1 && powder > -1) {
            return ItemBomb.createExplosive(design, caseTier, filling, fuse, powder, 1, false);
        }
        return null;
    }

    private Item getDesignCrafted(String type) {
        if (type.equalsIgnoreCase("bombcase")) {
            return ToolListMF.bomb_custom;
        }
        if (type.equalsIgnoreCase("minecase")) {
            return ToolListMF.mine_custom;
        }
        if (type.equalsIgnoreCase("arrow")) {
            return ToolListMF.exploding_arrow;
        }
        if (type.equalsIgnoreCase("bolt")) {
            return ToolListMF.exploding_bolt;
        }
        return null;
    }

    private boolean isMatch(int slot, String type) {
        return isMatch(inv[slot], type);
    }

    // INVENORY

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
        return "gui.bombcraftmf.name";
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
        if (this.isMatch(item, "bolt") || this.isMatch(item, "arrow") || this.isMatch(item, "bombcase")
                || this.isMatch(item, "minecase")) {
            return slot == 0;
        }
        if (this.isMatch(item, "powder")) {
            return slot == 1;
        }
        if (this.isMatch(item, "filling")) {
            return slot == 2;
        }
        if (this.isMatch(item, "fuse")) {
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
        return StatCollector.translateToLocal("tile.bombBench.name");
    }
}
