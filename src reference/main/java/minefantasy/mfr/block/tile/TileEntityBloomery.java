package minefantasy.mfr.tile;

import minefantasy.mfr.init.SoundsMFR;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import minefantasy.mfr.api.crafting.MineFantasyFuels;
import minefantasy.mfr.api.crafting.refine.BloomRecipe;
import minefantasy.mfr.api.helpers.ToolHelper;
import minefantasy.mfr.api.knowledge.ResearchLogic;
import minefantasy.mfr.api.refine.SmokeMechanics;
import minefantasy.mfr.api.rpg.RPGElements;
import minefantasy.mfr.api.rpg.SkillList;
import minefantasy.mfr.tile.blastfurnace.TileEntityBlastFC;
import minefantasy.mfr.item.heatable.ItemHeated;
import minefantasy.mfr.knowledge.KnowledgeListMFR;
import minefantasy.mfr.proxy.NetworkUtils;
import minefantasy.mfr.network.BloomeryPacket;
import minefantasy.mfr.util.MFRLogUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.Random;

public class TileEntityBloomery extends TileEntity implements IInventory, ITickable {
    public float progress, progressMax;
    public boolean hasBloom, isActive;
    private ItemStack[] inv = new ItemStack[3];
    private Random rand = new Random();
    private int ticksExisted;

    public static boolean isInput(ItemStack input) {
        return getResult(input) != null;
    }

    private static ItemStack getResult(ItemStack input) {
        return BloomRecipe.getSmeltingResult(input);
    }

    public ItemStack getResult() {
        ItemStack input = inv[0];
        ItemStack coal = inv[1];

        if (hasBloom())
            return null;// Cannot smelt if a bloom exists
        if (input == null || coal == null)
            return null;// Needs input

        if (!hasEnoughCarbon(input, coal)) {
            return null;
        }

        return getResult(input);
    }

    private boolean hasEnoughCarbon(ItemStack input, ItemStack coal) {
        int amount = input.getCount();
        int uses = MineFantasyFuels.getCarbon(coal);
        if (uses > 0) {
            int coalNeeded = (int) Math.ceil((float) amount / (float) uses);
            MFRLogUtil.logDebug("Required Coal: " + coalNeeded);
            return coal.getCount() == coalNeeded;
        }
        return false;
    }

    @Override
    public void update() {
        ++ticksExisted;
        if (isActive && progressMax > 0) {
            if (!world.canBlockSeeSky(pos.add(0,1,0))) {
                progressMax = progress = 0;
                isActive = false;
                return;
            }
            if (!world.isRemote) {
                ++progress;
                if (progress >= progressMax) {
                    smeltItem();
                }
                if (rand.nextInt(4) == 0) {
                    SmokeMechanics.spawnSmoke(world, pos, 1);
                }
            }
        }
        if (!world.isRemote && ticksExisted % 20 == 0) {
            syncData();
        }
    }

    public void syncData() {
        if (world.isRemote)
            return;

        NetworkUtils.sendToWatchers(new BloomeryPacket(this).generatePacket(), (WorldServer) world, this.pos);
    }

    /**
     * Light the bloomery, starting the process.
     *
     * @return true if it can smelt
     */
    public boolean light(EntityPlayer user) {
        ItemStack res = getResult();
        if (world.canBlockSeeSky(pos.add(0,1,0)) && res != null && !isActive) {
            if (!world.isRemote) {
                if (res.getItem() == Items.IRON_INGOT
                        && !ResearchLogic.hasInfoUnlocked(user, KnowledgeListMFR.smeltIron)) {
                    return false;
                }
                isActive = true;
                progressMax = inv[0].getCount() * getTime(inv[0]);// 15s per item
                world.playSound(user,pos.add(0.5D, 0.5D, + 0.5D), SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.AMBIENT, 1.0F, 1.0F);
            }

            return true;
        }
        return false;
    }

    private int getTime(ItemStack itemStack) {
        return 300;
    }

    /**
     * Consumes ALL input and sets output
     */
    public void smeltItem() {
        ItemStack result = getResult();
        if (result != null) {
            ItemStack res2 = result.copy();
            res2.setCount(inv[0].getCount());
            inv[0] = inv[1] = null;
            inv[2] = res2;
        }
        isActive = false;
        progress = progressMax = 0;
    }

    public boolean tryHammer(EntityPlayer user) {
        if (world.getBlockState(pos.add(0,1,0)).getMaterial().isSolid()) {
            return false;
        }
        ItemStack held = user.getHeldItem(EnumHand.MAIN_HAND);
        if (!hasBloom() || isActive) {
            return false;
        }
        String toolType = ToolHelper.getCrafterTool(held);
        float pwr = ToolHelper.getCrafterEfficiency(held);
        if (toolType.equalsIgnoreCase("hammer") || toolType.equalsIgnoreCase("hvyHammer")) {
            if (user.world.isRemote)
                return true;

            held.damageItem(1, user);
            if (held.getItemDamage() >= held.getMaxDamage()) {
                user.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, null);
            }

            if (rand.nextFloat() * 10F < pwr) {
                ItemStack drop = inv[2].copy();
                inv[2].shrink(1);
                if (inv[2].getCount() <= 0) {
                    inv[2] = null;
                }
                if (RPGElements.isSystemActive && RPGElements.getLevel(user, SkillList.artisanry) <= 20)// Only gain xp
                // up to level
                // 20
                {
                    SkillList.artisanry.addXP(user, 1);
                }
                drop.setCount(1);
                drop = ItemHeated.createHotItem(drop, 1200);
                entityDropItem(world, pos, drop);
                syncData();
            }
            world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundsMFR.ANVIL_SUCCEED, SoundCategory.NEUTRAL, 0.25F, 1.0F, true);

            return true;
        }
        return false;
    }

    public EntityItem entityDropItem(World world, BlockPos pos, ItemStack item) {
        if (item.getCount() != 0 && item.getItem() != null) {
            EntityItem entityitem = new EntityItem(world, pos.getX() + 0.5D, pos.getY() + 1.25F, pos.getZ() + 0.5D, item);
            entityitem.setPickupDelay(10);
            world.spawnEntity(entityitem);
            entityitem.motionX = entityitem.motionY = entityitem.motionZ = 0;
            return entityitem;
        } else {
            return null;
        }
    }

    public boolean hasBloom() {
        if (world.isRemote) {
            return hasBloom;
        }
        return inv[2] != null;
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
        inv[slot] = item;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
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

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        if (item != null && TileEntityBlastFC.isCarbon(item)) {
            return slot == 1;
        }
        if (item != null && getResult(item) != null) {
            return slot == 0;
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
        progress = nbt.getFloat("Progress");
        progressMax = nbt.getFloat("ProgressMax");
        hasBloom = nbt.getBoolean("hasBloom");
        isActive = nbt.getBoolean("isActive");
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

        nbt.setFloat("Progress", progress);
        nbt.setFloat("ProgressMax", progressMax);
        nbt.setBoolean("hasBloom", hasBloom);
        nbt.setBoolean("isActive", isActive);
        return nbt;
    }

    @SideOnly(Side.CLIENT)
    public String getTextureName() {
        return "bloomery_basic";
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
