package minefantasy.mfr.tile;

import minefantasy.mfr.api.crafting.IBasicMetre;
import minefantasy.mfr.api.crafting.engineer.IBombComponent;
import minefantasy.mfr.api.helpers.ToolHelper;
import minefantasy.mfr.api.knowledge.ResearchLogic;
import minefantasy.mfr.api.rpg.SkillList;
import minefantasy.mfr.init.SoundsMFR;
import minefantasy.mfr.item.gadget.ItemBomb;
import minefantasy.mfr.item.gadget.ItemExplodingArrow;
import minefantasy.mfr.init.ToolListMFR;
import minefantasy.mfr.knowledge.KnowledgeListMFR;
import minefantasy.mfr.proxy.NetworkUtils;
import minefantasy.mfr.network.BombBenchPacket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.translation.I18n;
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

    public boolean tryCraft(EntityPlayer user, boolean pressUsed) {
        boolean sticky = !pressUsed && ResearchLogic.hasInfoUnlocked(user, KnowledgeListMFR.stickybomb)
                && user.getHeldItemMainhand() != null && user.getHeldItemMainhand().getItem() == Items.SLIME_BALL;
        if (!world.isRemote && sticky && applySlime()) {
            int slot = user.inventory.getSlotFor(new ItemStack(Items.SLIME_BALL));
            user.inventory.removeStackFromSlot(slot);
            return true;
        }
        ItemStack result = findResult();
        hasRecipe = result != null;

        if (result == null) {
            progress = 0F;
        } else if ((pressUsed || ToolHelper.getCrafterTool(user.getHeldItemMainhand()).equalsIgnoreCase("spanner"))) {
            if (!pressUsed && user.getHeldItemMainhand() != null) {
                world.playSound(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, SoundsMFR.TWIST_BOLT, SoundCategory.NEUTRAL, 0.25F, 1.0F, true);
                user.getHeldItemMainhand().damageItem(1, user);
                if (user.getHeldItemMainhand().getItemDamage() >= user.getHeldItemMainhand().getMaxDamage()) {
                    user.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, null);
                }
            }
            float efficiency = pressUsed ? maxProgress : ToolHelper.getCrafterEfficiency(user.getHeldItemMainhand());

            if (!pressUsed && user.swingProgress > 0 && user.swingProgress <= 1.0) {
                efficiency *= (0.5F - user.swingProgress);
            }

            if (!world.isRemote) {
                progress += efficiency;
            }
            if ((progress >= maxProgress && craftItem(result))) {
                world.playSound(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, SoundEvents.BLOCK_WOODEN_DOOR_OPEN, SoundCategory.AMBIENT,0.35F, 0.5F, false);
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
                                if (spare.getCount() + cont.getCount() <= spare.getMaxStackSize()) {
                                    spare.grow(cont.getCount());
                                    cont = null;
                                } else {
                                    int room_left = spare.getMaxStackSize() - spare.getCount();
                                    spare.grow(room_left);
                                    cont.shrink(room_left);
                                }
                            }
                            if (cont != null && !world.isRemote) {
                                EntityItem ei = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                                        cont);
                                world.spawnEntity(ei);
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
            if (areItemsEqual(result, inv[4]) && (inv[4].getCount() + result.getCount()) <= inv[4].getMaxStackSize()) {
                inv[4].grow(1);
                return true;
            } else {
                return false;
            }
        }
    }

    public void syncData() {
        if (world.isRemote)
            return;

        NetworkUtils.sendToWatchers(new BombBenchPacket(this).generatePacket(), (WorldServer) world, this.pos);

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
            return ToolListMFR.bomb_custom;
        }
        if (type.equalsIgnoreCase("minecase")) {
            return ToolListMFR.mine_custom;
        }
        if (type.equalsIgnoreCase("arrow")) {
            return ToolListMFR.exploding_arrow;
        }
        if (type.equalsIgnoreCase("bolt")) {
            return ToolListMFR.exploding_bolt;
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
        return I18n.translateToLocal("tile.bombBench.name");
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
