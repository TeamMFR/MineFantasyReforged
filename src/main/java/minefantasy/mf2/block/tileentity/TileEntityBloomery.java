package minefantasy.mf2.block.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.crafting.MineFantasyFuels;
import minefantasy.mf2.api.crafting.refine.BloomRecipe;
import minefantasy.mf2.api.helpers.ToolHelper;
import minefantasy.mf2.api.knowledge.ResearchLogic;
import minefantasy.mf2.api.refine.SmokeMechanics;
import minefantasy.mf2.api.rpg.RPGElements;
import minefantasy.mf2.api.rpg.SkillList;
import minefantasy.mf2.block.tileentity.blastfurnace.TileEntityBlastFC;
import minefantasy.mf2.item.heatable.ItemHeated;
import minefantasy.mf2.knowledge.KnowledgeListMF;
import minefantasy.mf2.network.NetworkUtils;
import minefantasy.mf2.network.packet.BloomeryPacket;
import minefantasy.mf2.util.MFLogUtil;
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

public class TileEntityBloomery extends TileEntity implements IInventory {
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
        int amount = input.stackSize;
        int uses = MineFantasyFuels.getCarbon(coal);
        if (uses > 0) {
            int coalNeeded = (int) Math.ceil((float) amount / (float) uses);
            MFLogUtil.logDebug("Required Coal: " + coalNeeded);
            return coal.stackSize == coalNeeded;
        }
        return false;
    }

    @Override
    public void updateEntity() {
        ++ticksExisted;
        if (isActive && progressMax > 0) {
            if (!worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord)) {
                progressMax = progress = 0;
                isActive = false;
                return;
            }
            if (!worldObj.isRemote) {
                ++progress;
                if (progress >= progressMax) {
                    smeltItem();
                }
                if (rand.nextInt(4) == 0) {
                    SmokeMechanics.spawnSmoke(worldObj, xCoord, yCoord, zCoord, 1);
                }
            }
        }
        if (!worldObj.isRemote && ticksExisted % 20 == 0) {
            syncData();
        }
    }

    public void syncData() {
        if (worldObj.isRemote)
            return;

        NetworkUtils.sendToWatchers(new BloomeryPacket(this).generatePacket(), (WorldServer) worldObj, this.xCoord, this.zCoord);
    }

    /**
     * Light the bloomery, starting the process.
     *
     * @return true if it can smelt
     */
    public boolean light(EntityPlayer user) {
        ItemStack res = getResult();
        if (worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord) && res != null && !isActive) {
            if (!worldObj.isRemote) {
                if (res.getItem() == Items.iron_ingot
                        && !ResearchLogic.hasInfoUnlocked(user, KnowledgeListMF.smeltIron)) {
                    return false;
                }
                isActive = true;
                progressMax = inv[0].stackSize * getTime(inv[0]);// 15s per item
                worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "fire.ignite", 1.0F, 1.0F);
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
            res2.stackSize = inv[0].stackSize;
            inv[0] = inv[1] = null;
            inv[2] = res2;
        }
        isActive = false;
        progress = progressMax = 0;
    }

    public boolean tryHammer(EntityPlayer user) {
        if (worldObj.getBlock(xCoord, yCoord + 1, zCoord).getMaterial().isSolid()) {
            return false;
        }
        ItemStack held = user.getHeldItem();
        if (!hasBloom() || isActive) {
            return false;
        }
        String toolType = ToolHelper.getCrafterTool(held);
        float pwr = ToolHelper.getCrafterEfficiency(held);
        if (toolType.equalsIgnoreCase("hammer") || toolType.equalsIgnoreCase("hvyHammer")) {
            if (user.worldObj.isRemote)
                return true;

            held.damageItem(1, user);
            if (held.getItemDamage() >= held.getMaxDamage()) {
                user.destroyCurrentEquippedItem();
                user.setCurrentItemOrArmor(0, null);
            }

            if (rand.nextFloat() * 10F < pwr) {
                ItemStack drop = inv[2].copy();
                --inv[2].stackSize;
                if (inv[2].stackSize <= 0) {
                    inv[2] = null;
                }
                if (RPGElements.isSystemActive && RPGElements.getLevel(user, SkillList.artisanry) <= 20)// Only gain xp
                // up to level
                // 20
                {
                    SkillList.artisanry.addXP(user, 1);
                }
                drop.stackSize = 1;
                drop = ItemHeated.createHotItem(drop, 1200);
                entityDropItem(worldObj, xCoord, yCoord, zCoord, drop);
                syncData();
            }
            worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "minefantasy2:block.anvilsucceed",
                    0.25F, 1.0F);

            return true;
        }
        return false;
    }

    public EntityItem entityDropItem(World world, int x, int y, int z, ItemStack item) {
        if (item.stackSize != 0 && item.getItem() != null) {
            EntityItem entityitem = new EntityItem(world, x + 0.5D, y + 1.25F, z + 0.5D, item);
            entityitem.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(entityitem);
            entityitem.motionX = entityitem.motionY = entityitem.motionZ = 0;
            return entityitem;
        } else {
            return null;
        }
    }

    public boolean hasBloom() {
        if (worldObj.isRemote) {
            return hasBloom;
        }
        return inv[2] != null;
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
        inv[slot] = item;
    }

    @Override
    public String getInventoryName() {
        return "gui.carpentermf.name";
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
        if (item != null && TileEntityBlastFC.isCarbon(item)) {
            return slot == 1;
        }
        if (item != null && getResult(item) != null) {
            return slot == 0;
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
        progress = nbt.getFloat("Progress");
        progressMax = nbt.getFloat("ProgressMax");
        hasBloom = nbt.getBoolean("hasBloom");
        isActive = nbt.getBoolean("isActive");
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

        nbt.setFloat("Progress", progress);
        nbt.setFloat("ProgressMax", progressMax);
        nbt.setBoolean("hasBloom", hasBloom);
        nbt.setBoolean("isActive", isActive);
    }

    @SideOnly(Side.CLIENT)
    public String getTextureName() {
        return "bloomery_basic";
    }
}
