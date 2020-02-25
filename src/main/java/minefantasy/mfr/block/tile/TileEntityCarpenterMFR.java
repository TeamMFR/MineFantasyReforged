package minefantasy.mfr.block.tile;

import minefantasy.mfr.api.crafting.carpenter.CarpenterCraftMatrix;
import minefantasy.mfr.api.crafting.carpenter.CraftingManagerCarpenter;
import minefantasy.mfr.api.crafting.carpenter.ICarpenter;
import minefantasy.mfr.api.crafting.carpenter.ShapelessCarpenterRecipes;
import minefantasy.mfr.api.helpers.ToolHelper;
import minefantasy.mfr.api.knowledge.ResearchLogic;
import minefantasy.mfr.api.rpg.Skill;
import minefantasy.mfr.container.ContainerCarpenterMFR;
import minefantasy.mfr.item.armour.ItemArmourMFR;
import minefantasy.mfr.proxy.NetworkUtils;
import minefantasy.mfr.packet.CarpenterPacket;
import minefantasy.mfr.util.MFRLogUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.WorldServer;

import java.util.Random;

public class TileEntityCarpenterMFR extends TileEntity implements IInventory, ICarpenter {
    public final int width = 4;
    public final int height = 4;
    public String resName = "<No Project Set>";
    public float progressMax;
    public float progress;
    private int tier;
    private ItemStack[] inventory;
    private Random rand = new Random();
    private int ticksExisted;
    private ContainerCarpenterMFR syncCarpenter;
    private CarpenterCraftMatrix craftMatrix;
    private String lastPlayerHit = "";
    private String toolTypeRequired = "hands";
    private SoundEvent craftSound = SoundEvents.BLOCK_WOOD_STEP;
    private String researchRequired = "";
    private Skill skillUsed;
    private boolean resetRecipe = false;
    private ItemStack recipe;
    private int hammerTierRequired;
    private int CarpenterTierRequired;

    public TileEntityCarpenterMFR() {
        this(0);
    }

    public TileEntityCarpenterMFR(int tier) {
        inventory = new ItemStack[width * height + 5];
        this.tier = tier;
        setContainer(new ContainerCarpenterMFR(this));
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
                this.inventory[slotNum] = new ItemStack(savedSlot);
            }
        }
        progress = nbt.getFloat("Progress");
        progressMax = nbt.getFloat("ProgressMax");
        resName = nbt.getString("ResultName");
        toolTypeRequired = nbt.getString("toolTypeRequired");
        researchRequired = nbt.getString("researchRequired");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
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
        nbt.setString("researchRequired", researchRequired);
        return nbt;
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public boolean isEmpty() {
        return false;
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

            if (this.inventory[slot].getCount() <= num) {
                itemstack = this.inventory[slot];
                this.inventory[slot] = null;
                return itemstack;
            } else {
                itemstack = this.inventory[slot].splitStack(num);

                if (this.inventory[slot].getCount() == 0) {
                    this.inventory[slot] = null;
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack removeStackFromSlot(int slot) {
        return inventory[slot];
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack item) {
        onInventoryChanged();
        inventory[slot] = item;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return true;
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
    public void markDirty() {
        ++ticksExisted;
        if (!world.isRemote) {
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

    public void onInventoryChanged() {
        if (!resetRecipe) {
            updateCraftingData();
            MFRLogUtil.logDebug("Carpenter: Optimised Inv Tick");
            resetRecipe = true;
        }
    }

    public boolean tryCraft(EntityPlayer user) {
        if (user == null)
            return false;

        String toolType = ToolHelper.getCrafterTool(user.getHeldItemMainhand());
        int hammerTier = ToolHelper.getCrafterTier(user.getHeldItemMainhand());
        if (!toolType.equalsIgnoreCase("nothing")) {
            if (user.getHeldItemMainhand() != null) {
                user.getHeldItemMainhand().damageItem(1, user);
                if (user.getHeldItemMainhand().getItemDamage() >= user.getHeldItemMainhand().getMaxDamage()) {
                    if (world.isRemote)
                        user.renderBrokenItemStack(user.getHeldItemMainhand());
                    user.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, null);
                }
            }
            if (world.isRemote)
                return true;

            if (doesPlayerKnowCraft(user) && canCraft() && toolType.equalsIgnoreCase(toolTypeRequired)
                    && tier >= CarpenterTierRequired && hammerTier >= hammerTierRequired) {
                world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, getUseSound(), SoundCategory.AMBIENT, 1.0F, 1.0F, false);
                float efficiency = ToolHelper.getCrafterEfficiency(user.getHeldItemMainhand());

                if (user.swingProgress > 0 && user.swingProgress <= 1.0) {
                    efficiency *= (0.5F - user.swingProgress);
                }

                progress += Math.max(0.2F, efficiency);
                if (progress >= progressMax) {
                    craftItem(user);
                }
            } else {
                world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_STONE_STEP, SoundCategory.AMBIENT, 1.25F, 1.5F, false);
            }
            lastPlayerHit = user.getName();
            updateCraftingData();
            return true;
        }
        updateCraftingData();
        return false;
    }

    private SoundEvent getUseSound() {
        if (craftSound.toString().equals("engineering")) {
            if (rand.nextInt(5) == 0) {
                return SoundEvents.UI_BUTTON_CLICK;
            }
            if (rand.nextInt(20) == 0) {
                return SoundEvents.BLOCK_WOODEN_DOOR_OPEN;
            }
            return SoundEvents.BLOCK_WOOD_STEP;
        }
        return craftSound;
    }

    private void craftItem(EntityPlayer user) {
        if (this.canCraft()) {
            addXP(user);
            ItemStack result = recipe.copy();
            if (result != null && result.getItem() instanceof ItemArmourMFR) {
                result = modifyArmour(result);
            }
            int output = getOutputSlotNum();

            if (this.inventory[output] == null) {
                if (result.getMaxStackSize() == 1 && !lastPlayerHit.isEmpty()) {
                    getNBT(result).setString("MF_CraftedByName", lastPlayerHit);
                }
                this.inventory[output] = result;
            } else if (this.inventory[output].getItem() == result.getItem()) {
                this.inventory[output].grow(result.getCount()); // Forge BugFix: Results may have multiple items
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
        ItemArmourMFR item = (ItemArmourMFR) result.getItem();
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
            item.setColor(result, colour);
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

            while (itemstack.getCount() > 0) {
                int j1 = this.rand.nextInt(21) + 10;

                if (j1 > itemstack.getCount()) {
                    j1 = itemstack.getCount();
                }

                itemstack.shrink(j1);
                EntityItem entityitem = new EntityItem(world, pos.getX() + f, pos.getY() + f1, pos.getZ() + f2,
                        new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                if (itemstack.hasTagCompound()) {
                    entityitem.getItem().setTagCompound(itemstack.getTagCompound().copy());
                }

                float f3 = 0.05F;
                entityitem.motionX = (float) this.rand.nextGaussian() * f3;
                entityitem.motionY = (float) this.rand.nextGaussian() * f3 + 0.2F;
                entityitem.motionZ = (float) this.rand.nextGaussian() * f3;
                world.spawnEntity(entityitem);
            }
        }
    }

    public void syncData() {

        if (world.isRemote)
            return;

        NetworkUtils.sendToWatchers(new CarpenterPacket(this).generatePacket(), (WorldServer) world, this.pos);

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
        if (!world.isRemote && recipe != null && recipe.getItem() != null && recipe.getDisplayName() != null) {
            resName = recipe.getDisplayName();
        }
        return resName;
    }

    public String getToolNeeded() {
        return toolTypeRequired;
    }

    public SoundEvent getCraftingSound() {
        return craftSound;
    }

    @Override
    public void setCraftingSound(SoundEvent sound) {
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
                if (item.getCount() == 1) {
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
                if (slot.isItemEqual(item) && slot.getCount() < slot.getMaxStackSize()) {
                    if (slot.getCount() + item.getCount() <= slot.getMaxStackSize()) {
                        slot.grow(item.getCount());
                        return null;// All Shared
                    } else {
                        int room_left = slot.getMaxStackSize() - slot.getCount();
                        slot.grow(room_left);
                        item.shrink(room_left);// Share
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
            if (resSlot.getCount() + result.getCount() > resSlot.getMaxStackSize()) {
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
        if (!world.isRemote) {
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

    public void setContainer(ContainerCarpenterMFR container) {
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

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }
}
