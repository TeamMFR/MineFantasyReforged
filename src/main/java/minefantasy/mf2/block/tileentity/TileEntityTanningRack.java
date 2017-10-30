package minefantasy.mf2.block.tileentity;

import minefantasy.mf2.api.crafting.tanning.TanningRecipe;
import minefantasy.mf2.api.helpers.ToolHelper;
import minefantasy.mf2.api.rpg.RPGElements;
import minefantasy.mf2.api.rpg.SkillList;
import minefantasy.mf2.block.crafting.BlockEngineerTanner;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.container.ContainerTanner;
import minefantasy.mf2.item.list.ComponentListMF;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import java.util.Random;

public class TileEntityTanningRack extends TileEntity implements IInventory {
    public final ContainerTanner container;
    public ItemStack[] items = new ItemStack[2];
    public float progress;
    public float maxProgress;
    public String tex = "";
    public int tier = 0;
    public String toolType = "knife";
    public float acTime;
    private int tempTicksExisted = 0;
    private Random rand = new Random();

    public TileEntityTanningRack() {
        this(0, "Basic");
    }

    public TileEntityTanningRack(int tier, String tex) {
        container = new ContainerTanner(this);
        this.tier = tier;
        this.tex = tex;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        ++tempTicksExisted;
        if (tempTicksExisted == 10) {
            blockMetadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        }
        if (isAutomated()) {
            if (acTime > 0) {
                acTime -= (1F / 20);
            }
            // syncAnimations();
        }
    }

    public boolean interact(EntityPlayer player, boolean leftClick, boolean leverPull) {
        if (leverPull && acTime > 0) {
            return true;
        }
        container.detectAndSendChanges();

        ItemStack held = player.getHeldItem();

        // Interaction
        if (items[1] != null && (leverPull || ToolHelper.getCrafterTool(held).equalsIgnoreCase(toolType))) {
            if (leverPull || ToolHelper.getCrafterTier(held) >= tier) {
                if (!leverPull) {
                    held.damageItem(1, player);
                    if (held.getItemDamage() >= held.getMaxDamage()) {
                        player.destroyCurrentEquippedItem();
                    }
                } else {
                    worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "tile.piston.out", 0.75F,
                            0.85F);
                    acTime = 1.0F;
                }

                float efficiency = leverPull ? 100F : ToolHelper.getCrafterEfficiency(held);
                if (!leverPull && player.swingProgress > 0 && player.swingProgress <= 1.0) {
                    efficiency *= (0.5F - player.swingProgress);
                }

                if (efficiency > 0) {
                    progress += efficiency;
                }
                if (toolType.equalsIgnoreCase("shears")) {
                    worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "mob.sheep.shear", 1.0F,
                            1.0F);
                } else {
                    worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "dig.cloth", 1.0F, 1.0F);
                }
                if (progress >= maxProgress) {
                    if (RPGElements.isSystemActive) {
                        SkillList.artisanry.addXP(player, 1);
                    }
                    progress = 0;
                    int ss = items[0] != null ? items[0].stackSize : 1;
                    ItemStack out = items[1].copy();
                    out.stackSize *= ss;
                    setInventorySlotContents(0, out);
                    updateRecipe();
                    if (isShabbyRack() && rand.nextInt(10) == 0 && !worldObj.isRemote) {
                        for (int a = 0; a < rand.nextInt(10); a++) {
                            ItemStack plank = ComponentListMF.plank.construct("ScrapWood");
                            worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "mob.zombie.woodbreak",
                                    1.0F, 1.5F);
                            dropItem(plank);
                        }
                        worldObj.setBlockToAir(xCoord, yCoord, zCoord);
                        return true;
                    }
                }
            }
            return true;
        }
        if (!leftClick && (ToolHelper.getCrafterTool(held).equalsIgnoreCase("nothing")
                || ToolHelper.getCrafterTool(held).equalsIgnoreCase("hands"))) {
            // Item placement
            ItemStack item = items[0];
            if (item == null) {
                if (held != null && !(held.getItem() instanceof ItemBlock) && TanningRecipe.getRecipe(held) != null) {
                    ItemStack item2 = held.copy();
                    item2.stackSize = 1;
                    setInventorySlotContents(0, item2);
                    tryDecrMainItem(player);
                    updateRecipe();
                    worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "mob.horse.leather", 1.0F,
                            1.0F);
                    return true;
                }
            } else {
                if (!player.inventory.addItemStackToInventory(item)) {
                    player.entityDropItem(item, 0.0F);
                }
                setInventorySlotContents(0, null);
                updateRecipe();
                worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "mob.horse.leather", 1.0F, 1.0F);
                return true;
            }
        }
        return false;
    }

    public boolean isAutomated() {
        if (worldObj == null) {
            return tex.equalsIgnoreCase("metal");
        }
        return worldObj.getBlock(xCoord, yCoord, zCoord) instanceof BlockEngineerTanner;
    }

    private void tryDecrMainItem(EntityPlayer player) {
        int held = player.inventory.currentItem;
        if (held >= 0 && held < 9) {
            player.inventory.decrStackSize(held, 1);
        }
    }

    public void updateRecipe() {
        TanningRecipe recipe = TanningRecipe.getRecipe(items[0]);
        if (recipe == null) {
            setInventorySlotContents(1, null);
            progress = maxProgress = tier = 0;
        } else {
            setInventorySlotContents(1, recipe.output);
            tier = recipe.tier;
            maxProgress = recipe.time;
            toolType = recipe.toolType;
        }
        progress = 0;
    }

    public boolean doesPlayerKnowCraft(EntityPlayer thePlayer) {
        return true;
    }

    public int getProgressBar(int i) {
        if (maxProgress <= 0)
            return 0;
        return (int) (i / maxProgress * progress);
    }

    public String getResultName() {
        if (items[1] != null) {
            return items[1].getDisplayName();
        }
        return "";
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        acTime = nbt.getFloat("acTime");
        tex = nbt.getString("tex");
        tier = nbt.getInteger("tier");
        progress = nbt.getFloat("Progress");
        maxProgress = nbt.getFloat("maxProgress");
        toolType = nbt.getString("toolType");

        NBTTagList savedItems = nbt.getTagList("Items", 10);

        for (int i = 0; i < savedItems.tagCount(); ++i) {
            NBTTagCompound savedSlot = savedItems.getCompoundTagAt(i);
            byte slotNum = savedSlot.getByte("Slot");

            if (slotNum >= 0 && slotNum < items.length) {
                items[slotNum] = ItemStack.loadItemStackFromNBT(savedSlot);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setFloat("acTime", acTime);
        nbt.setString("tex", tex);
        nbt.setInteger("tier", tier);
        nbt.setFloat("Progress", progress);
        nbt.setFloat("maxProgress", maxProgress);
        nbt.setString("toolType", toolType);

        NBTTagList savedItems = new NBTTagList();

        for (int i = 0; i < items.length; ++i) {
            if (items[i] != null) {
                NBTTagCompound savedSlot = new NBTTagCompound();
                savedSlot.setByte("Slot", (byte) i);
                items[i].writeToNBT(savedSlot);
                savedItems.appendTag(savedSlot);
            }
        }

        nbt.setTag("Items", savedItems);
    }

    // INVENTORY
    public void onInventoryChanged() {
    }

    @Override
    public int getSizeInventory() {
        return items.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return items[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int num) {
        onInventoryChanged();
        if (this.items[slot] != null) {
            ItemStack itemstack;

            if (this.items[slot].stackSize <= num) {
                itemstack = this.items[slot];
                this.items[slot] = null;
                return itemstack;
            } else {
                itemstack = this.items[slot].splitStack(num);

                if (this.items[slot].stackSize == 0) {
                    this.items[slot] = null;
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return items[slot];
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack item) {
        onInventoryChanged();
        items[slot] = item;
    }

    @Override
    public String getInventoryName() {
        return "tile.tanner.name";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
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
        return false;
    }

    private boolean isShabbyRack() {
        return worldObj.getBlock(xCoord, yCoord, zCoord) == BlockListMF.tanner;
    }

    private void dropItem(ItemStack itemstack) {
        if (itemstack != null) {
            float f = this.rand.nextFloat() * 0.8F + 0.1F;
            float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
            float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

            while (itemstack.stackSize > 0) {
                int j1 = this.rand.nextInt(21) + 10;

                if (j1 > itemstack.stackSize) {
                    j1 = itemstack.stackSize;
                }

                itemstack.stackSize -= j1;
                EntityItem entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2,
                        new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                if (itemstack.hasTagCompound()) {
                    entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
                }

                float f3 = 0.05F;
                entityitem.motionX = (float) this.rand.nextGaussian() * f3;
                entityitem.motionY = (float) this.rand.nextGaussian() * f3 + 0.2F;
                entityitem.motionZ = (float) this.rand.nextGaussian() * f3;
                worldObj.spawnEntityInWorld(entityitem);
            }
        }
    }
}
