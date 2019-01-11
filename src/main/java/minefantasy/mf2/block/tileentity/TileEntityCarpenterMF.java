package minefantasy.mf2.block.tileentity;

import minefantasy.mf2.api.crafting.carpenter.CarpenterCraftMatrix;
import minefantasy.mf2.api.crafting.carpenter.CraftingManagerCarpenter;
import minefantasy.mf2.api.crafting.carpenter.ICarpenter;
import minefantasy.mf2.api.crafting.carpenter.ShapelessCarpenterRecipes;
import minefantasy.mf2.api.helpers.ToolHelper;
import minefantasy.mf2.api.knowledge.ResearchLogic;
import minefantasy.mf2.api.rpg.Skill;
import minefantasy.mf2.container.ContainerCarpenterMF;
import minefantasy.mf2.item.armour.ItemArmourMF;
import minefantasy.mf2.network.NetworkUtils;
import minefantasy.mf2.network.packet.CarpenterPacket;
import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

import java.util.Random;

public class TileEntityCarpenterMF extends TileEntity implements IInventory, ICarpenter {
    public final int width = 4;
    public final int height = 4;
    public String resName = "<No Project Set>";
    public float progressMax;
    public float progress;
    private int tier;
    private ItemStack[] inventory;
    private Random rand = new Random();
    private int ticksExisted;
    private ContainerCarpenterMF syncCarpenter;
    private CarpenterCraftMatrix craftMatrix;
    private String lastPlayerHit = "";
    private String toolTypeRequired = "hands";
    private String craftSound = "step.wood";
    private String researchRequired = "";
    private Skill skillUsed;
    private boolean resetRecipe = false;
    private ItemStack recipe;
    private int hammerTierRequired;
    private int CarpenterTierRequired;

    public TileEntityCarpenterMF() {
        this(0);
    }

    public TileEntityCarpenterMF(int tier) {
        inventory = new ItemStack[width * height + 5];
        this.tier = tier;
        setContainer(new ContainerCarpenterMF(this));
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        tier = nbt.getInteger("tier");

        NBTTagList savedItems = nbt.getTagList("Items", 10);
        this.inventory = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < savedItems.tagCount(); ++i) {
            NBTTagCompound savedSlot = savedItems.getCompoundTagAt(i);
            byte slotNum = savedSlot.getByte("Slot");

            if (slotNum >= 0 && slotNum < this.inventory.length) {
                this.inventory[slotNum] = ItemStack.loadItemStackFromNBT(savedSlot);
            }
        }
        progress = nbt.getFloat("Progress");
        progressMax = nbt.getFloat("ProgressMax");
        resName = nbt.getString("ResultName");
        toolTypeRequired = nbt.getString("toolTypeRequired");
        craftSound = nbt.getString("craftSound");
        researchRequired = nbt.getString("researchRequired");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("tier", tier);

        NBTTagList savedItems = new NBTTagList();

        for (int i = 0; i < this.inventory.length; ++i) {
            if (this.inventory[i] != null) {
                NBTTagCompound savedSlot = new NBTTagCompound();
                savedSlot.setByte("Slot", (byte) i);
                this.inventory[i].writeToNBT(savedSlot);
                savedItems.appendTag(savedSlot);
            }
        }

        nbt.setTag("Items", savedItems);

        nbt.setFloat("Progress", progress);
        nbt.setFloat("ProgressMax", progressMax);
        nbt.setString("ResName", resName);
        nbt.setString("toolTypeRequired", toolTypeRequired);
        nbt.setString("craftSound", craftSound);
        nbt.setString("researchRequired", researchRequired);
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int num) {
        onInventoryChanged();
        if (this.inventory[slot] != null) {
            ItemStack itemstack;

            if (this.inventory[slot].stackSize <= num) {
                itemstack = this.inventory[slot];
                this.inventory[slot] = null;
                return itemstack;
            } else {
                itemstack = this.inventory[slot].splitStack(num);

                if (this.inventory[slot].stackSize == 0) {
                    this.inventory[slot] = null;
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return inventory[slot];
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack item) {
        onInventoryChanged();
        inventory[slot] = item;
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
        return true;
    }

    @Override
    public void updateEntity() {
        ++ticksExisted;
        super.updateEntity();
        if (!worldObj.isRemote) {
            if (ticksExisted % 20 == 0) {
                updateCraftingData();
            }
            if (!canCraft() && ticksExisted > 1) {
                progress = progressMax = 0;
                this.resName = "";
                this.recipe = null;
            }
        }
        resetRecipe = false;
    }

    public void onInventoryChanged() {
        if (!resetRecipe) {
            updateCraftingData();
            MFLogUtil.logDebug("Carpenter: Optimised Inv Tick");
            resetRecipe = true;
        }
    }

    public boolean tryCraft(EntityPlayer user) {
        if (user == null)
            return false;

        String toolType = ToolHelper.getCrafterTool(user.getHeldItem());
        int hammerTier = ToolHelper.getCrafterTier(user.getHeldItem());
        if (!toolType.equalsIgnoreCase("nothing")) {
            if (user.getHeldItem() != null) {
                user.getHeldItem().damageItem(1, user);
                if (user.getHeldItem().getItemDamage() >= user.getHeldItem().getMaxDamage()) {
                    if (worldObj.isRemote)
                        user.renderBrokenItemStack(user.getHeldItem());

                    user.destroyCurrentEquippedItem();
                }
            }
            if (worldObj.isRemote)
                return true;

            if (doesPlayerKnowCraft(user) && canCraft() && toolType.equalsIgnoreCase(toolTypeRequired)
                    && tier >= CarpenterTierRequired && hammerTier >= hammerTierRequired) {
                worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, getUseSound(), 1.0F, 1.0F);
                float efficiency = ToolHelper.getCrafterEfficiency(user.getHeldItem());

                if (user.swingProgress > 0 && user.swingProgress <= 1.0) {
                    efficiency *= (0.5F - user.swingProgress);
                }

                progress += Math.max(0.2F, efficiency);
                if (progress >= progressMax) {
                    craftItem(user);
                }
            } else {
                worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "step.stone", 1.25F, 1.5F);
            }
            lastPlayerHit = user.getCommandSenderName();
            updateCraftingData();
            return true;
        }
        updateCraftingData();
        return false;
    }

    private String getUseSound() {
        if (craftSound.equalsIgnoreCase("engineering")) {
            if (rand.nextInt(5) == 0) {
                return "random.click";
            }
            if (rand.nextInt(20) == 0) {
                return "random.door_open";
            }
            return "step.wood";
        }
        return craftSound;
    }

    private void craftItem(EntityPlayer user) {
        if (this.canCraft()) {
            addXP(user);
            ItemStack result = recipe.copy();
            if (result != null && result.getItem() instanceof ItemArmourMF) {
                result = modifyArmour(result);
            }
            int output = getOutputSlotNum();

            if (this.inventory[output] == null) {
                if (result.getMaxStackSize() == 1 && !lastPlayerHit.isEmpty()) {
                    getNBT(result).setString("MF_CraftedByName", lastPlayerHit);
                }
                this.inventory[output] = result;
            } else if (this.inventory[output].getItem() == result.getItem()) {
                this.inventory[output].stackSize += result.stackSize; // Forge BugFix: Results may have multiple items
            }
            consumeResources();
        }
        onInventoryChanged();
        progress = 0;
    }

    private int getOutputSlotNum() {
        return getSizeInventory() - 5;
    }

    private ItemStack modifyArmour(ItemStack result) {
        ItemArmourMF item = (ItemArmourMF) result.getItem();
        boolean canColour = item.canColour();
        int colour = -1;
        for (int a = 0; a < getOutputSlotNum(); a++) {
            ItemStack slot = getStackInSlot(a);
            if (slot != null && slot.getItem() instanceof ItemArmor) {
                ItemArmor slotitem = (ItemArmor) slot.getItem();
                if (canColour && slotitem.hasColor(slot)) {
                    colour = slotitem.getColor(slot);
                }
                if (result.isItemStackDamageable()) {
                    result.setItemDamage(slot.getItemDamage());
                }
            }
        }
        if (colour != -1 && canColour) {
            item.func_82813_b(result, colour);
        }
        return result;
    }

    private NBTTagCompound getNBT(ItemStack item) {
        if (!item.hasTagCompound()) {
            item.setTagCompound(new NBTTagCompound());
        }
        return item.getTagCompound();
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

    public void syncData() {

        if (worldObj.isRemote)
            return;

        NetworkUtils.sendToWatchers(new CarpenterPacket(this).generatePacket(), (WorldServer) worldObj, this.xCoord, this.zCoord);

		/*
        List<EntityPlayer> players = ((WorldServer) worldObj).playerEntities;
		for (int i = 0; i < players.size(); i++) {
			EntityPlayer player = players.get(i);
			((WorldServer) worldObj).getEntityTracker().func_151248_b(player,
					new CarpenterPacket(this).generatePacket());
		}
		*/
    }

    public String getResultName() {
        if (!worldObj.isRemote && recipe != null && recipe.getItem() != null && recipe.getDisplayName() != null) {
            resName = recipe.getDisplayName();
        }
        return resName;
    }

    public String getToolNeeded() {
        return toolTypeRequired;
    }

    public String getCraftingSound() {
        return craftSound;
    }

    @Override
    public void setCraftingSound(String sound) {
        this.craftSound = sound;
    }

    public int getToolTierNeeded() {
        return this.hammerTierRequired;
    }

    public int getCarpenterTierNeeded() {
        return this.CarpenterTierRequired;
    }

    public void consumeResources() {
        resetRecipe = true;
        for (int slot = 0; slot < getOutputSlotNum(); slot++) {
            ItemStack item = getStackInSlot(slot);
            if (item != null && item.getItem() != null && item.getItem().getContainerItem(item) != null) {
                if (item.stackSize == 1) {
                    setInventorySlotContents(slot, item.getItem().getContainerItem(item));
                } else {
                    ItemStack drop = processSurplus(item.getItem().getContainerItem(item));
                    if (drop != null) {
                        this.dropItem(drop);
                    }
                    this.decrStackSize(slot, 1);
                }
            } else {
                this.decrStackSize(slot, 1);
            }
        }
        resetRecipe = false;
        this.onInventoryChanged();
    }

    private ItemStack processSurplus(ItemStack item) {
        for (int a = 0; a < 4; a++) {
            if (item == null) {
                return null;// If item was sorted
            }

            int s = getSizeInventory() - 4 + a;
            ItemStack slot = inventory[s];
            if (slot == null) {
                setInventorySlotContents(s, item);
                return null;// All Placed
            } else {
                if (slot.isItemEqual(item) && slot.stackSize < slot.getMaxStackSize()) {
                    if (slot.stackSize + item.stackSize <= slot.getMaxStackSize()) {
                        slot.stackSize += item.stackSize;
                        return null;// All Shared
                    } else {
                        int room_left = slot.getMaxStackSize() - slot.stackSize;
                        slot.stackSize += room_left;
                        item.stackSize -= room_left;// Share
                    }
                }
            }
        }
        return item;
    }

    private boolean canFitResult(ItemStack result) {
        ItemStack resSlot = inventory[getOutputSlotNum()];
        if (resSlot != null && result != null) {
            if (!resSlot.isItemEqual(result)) {
                return false;
            }
            if (resSlot.stackSize + result.stackSize > resSlot.getMaxStackSize()) {
                return false;
            }
        }
        return true;
    }

    // CRAFTING CODE
    public ItemStack getResult() {
        if (syncCarpenter == null || craftMatrix == null) {
            return null;
        }

        if (ticksExisted <= 1)
            return null;

        for (int a = 0; a < getOutputSlotNum(); a++) {
            craftMatrix.setInventorySlotContents(a, inventory[a]);
        }

        return CraftingManagerCarpenter.getInstance().findMatchingRecipe(this, craftMatrix);
    }

    public void updateCraftingData() {
        if (!worldObj.isRemote) {
            ItemStack oldRecipe = recipe;
            recipe = getResult();
            // syncItems();

            if (!canCraft() && progress > 0) {
                progress = 0;
                // quality = 100;
            }
            if (recipe != null && oldRecipe != null && !recipe.isItemEqual(oldRecipe)) {
                progress = 0;
            }
            if (progress > progressMax)
                progress = progressMax - 1;
            syncData();
        }
    }

    public boolean canCraft() {
        if (progressMax > 0 && recipe != null && recipe instanceof ItemStack) {
            return this.canFitResult(recipe);
        }
        return false;
    }

    @Override
    public void setForgeTime(int i) {
        progressMax = i;
    }

    @Override
    public void setToolTier(int i) {
        hammerTierRequired = i;
    }

    @Override
    public void setRequiredCarpenter(int i) {
        CarpenterTierRequired = i;
    }

    @Override
    public void setHotOutput(boolean i) {
    }

    public void setContainer(ContainerCarpenterMF container) {
        syncCarpenter = container;
        craftMatrix = new CarpenterCraftMatrix(this, syncCarpenter, ShapelessCarpenterRecipes.globalWidth,
                ShapelessCarpenterRecipes.globalHeight);
    }

    public boolean shouldRenderCraftMetre() {
        return recipe != null;
    }

    public int getProgressBar(int i) {
        return (int) Math.ceil(i / progressMax * progress);
    }

    @Override
    public void setToolType(String toolType) {
        this.toolTypeRequired = toolType;
    }

    @Override
    public void setResearch(String research) {
        this.researchRequired = research;
    }

    public String getResearchNeeded() {
        return researchRequired;
    }

    public boolean doesPlayerKnowCraft(EntityPlayer user) {
        if (getResearchNeeded().isEmpty()) {
            return true;
        }
        return ResearchLogic.hasInfoUnlocked(user, getResearchNeeded());
    }

    private void addXP(EntityPlayer smith) {
        if (skillUsed != null) {
            float baseXP = this.progressMax / 10F;
            skillUsed.addXP(smith, (int) baseXP + 1);
        }
    }

    @Override
    public void setSkill(Skill skill) {
        skillUsed = skill;
    }
}
