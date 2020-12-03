package minefantasy.mfr.tile;

import minefantasy.mfr.api.crafting.carpenter.CarpenterCraftMatrix;
import minefantasy.mfr.api.crafting.carpenter.CraftingManagerCarpenter;
import minefantasy.mfr.api.crafting.carpenter.ICarpenter;
import minefantasy.mfr.api.crafting.carpenter.ShapelessCarpenterRecipes;
import minefantasy.mfr.api.helpers.ToolHelper;
import minefantasy.mfr.api.knowledge.ResearchLogic;
import minefantasy.mfr.api.rpg.Skill;
import minefantasy.mfr.container.ContainerBase;
import minefantasy.mfr.container.ContainerCarpenterMFR;
import minefantasy.mfr.item.armour.ItemArmourMFR;
import minefantasy.mfr.network.CarpenterPacket;
import minefantasy.mfr.network.NetworkHandler;
import minefantasy.mfr.util.MFRLogUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class TileEntityCarpenterMFR extends TileEntityBase implements ICarpenter {
    public final int width = 4;
    public final int height = 4;
    public String resName = "<No Project Set>";
    public float progressMax;
    public float progress;
    private int tier;
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

    public final ItemStackHandler inventory = createInventory();

    public TileEntityCarpenterMFR() {
        setContainer(new ContainerCarpenterMFR(this));
    }

    @Override
    protected ItemStackHandler createInventory() {
        return new ItemStackHandler(width * height + 5);
    }

    @Override
    public ItemStackHandler getInventory() {
        return this.inventory;
    }

    @Override
    public ContainerBase createContainer(EntityPlayer player) {
        return new ContainerCarpenterMFR(player,this);
    }

    @Override
    protected int getGuiId() {
        return NetworkHandler.GUI_CARPENTER_BENCH;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return true;
    }

    @Override
    public void markDirty() {
        ++ticksExisted;
        super.markDirty();
        if (!world.isRemote) {
            if (ticksExisted % 20 == 0) {
                updateCraftingData();
            }
            if (!canCraft() && ticksExisted > 1) {
                progress = progressMax = 0;
                this.resName = "";
                this.recipe = ItemStack.EMPTY;
            }
        }
        resetRecipe = false;
    }

    public void onInventoryChanged() {
        if (!resetRecipe) {
            updateCraftingData();
            MFRLogUtil.logDebug("Carpenter: Optimised Inv Tick");
            resetRecipe = true;
        }
    }

    public boolean tryCraft(EntityPlayer user) {
        if (user == null){
            return false;
        }

        String toolType = ToolHelper.getCrafterTool(user.getHeldItemMainhand());
        int hammerTier = ToolHelper.getCrafterTier(user.getHeldItemMainhand());
        if (!toolType.equalsIgnoreCase("nothing")) {
            if (!user.getHeldItemMainhand().isEmpty()) {
                user.getHeldItemMainhand().damageItem(1, user);
                if (user.getHeldItemMainhand().getItemDamage() >= user.getHeldItemMainhand().getMaxDamage()) {
                    if (world.isRemote)
                        user.renderBrokenItemStack(user.getHeldItemMainhand());
                    user.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
                }
            }
            if (world.isRemote){
                return true;
            }

            if (doesPlayerKnowCraft(user)
                    && canCraft()
                    && toolType.equalsIgnoreCase(toolTypeRequired)
                    && tier >= CarpenterTierRequired
                    && hammerTier >= hammerTierRequired) {
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
        if (craftSound.toString().equalsIgnoreCase("engineering")) {
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
            if (!result.isEmpty() && result.getItem() instanceof ItemArmourMFR) {
                result = modifyArmour(result);
            }
            int output = getOutputSlotNum();

            if (this.getInventory().getStackInSlot(output).isEmpty()) {
                if (result.getMaxStackSize() == 1 && !lastPlayerHit.isEmpty()) {
                    getNBT(result).setString("MF_CraftedByName", lastPlayerHit);
                }
                this.getInventory().setStackInSlot(output, result);
            } else if (this.getInventory().getStackInSlot(output).getItem() == result.getItem()) {
                this.getInventory().getStackInSlot(output).grow(result.getCount()); // Forge BugFix: Results may have multiple items
            }
            consumeResources();
        }
        onInventoryChanged();
        progress = 0;
    }

    private int getOutputSlotNum() {
        return getInventory().getSlots() - 5;
    }

    private ItemStack modifyArmour(ItemStack result) {
        ItemArmourMFR item = (ItemArmourMFR) result.getItem();
        boolean canColour = item.canColour();
        int colour = -1;
        for (int a = 0; a < getOutputSlotNum(); a++) {
            ItemStack slot = getInventory().getStackInSlot(a);
            if (!slot.isEmpty() && slot.getItem() instanceof ItemArmor) {
                ItemArmor slotItem = (ItemArmor) slot.getItem();
                if (canColour && slotItem.hasColor(slot)) {
                    colour = slotItem.getColor(slot);
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
        if (!itemstack.isEmpty()) {
            float f = this.rand.nextFloat() * 0.8F + 0.1F;
            float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
            float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

            while (itemstack.getCount() > 0) {
                int j1 = this.rand.nextInt(21) + 10;

                if (j1 > itemstack.getCount()) {
                    j1 = itemstack.getCount();
                }

                itemstack.shrink(j1);
                EntityItem entityitem = new EntityItem(world, pos.getX() + f, pos.getY() + f1, pos.getZ() + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

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
        NetworkHandler.sendToAllTrackingChunk (world, pos.getX() >> 4, pos.getZ() >> 4, new CarpenterPacket(this));
    }

    public String getResultName() {
        if (!world.isRemote && !recipe.isEmpty() && recipe.getItem() != null && recipe.getDisplayName() != null) {
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
            ItemStack item = getInventory().getStackInSlot(slot);
            if (!item.isEmpty() && item.getItem() != null && !item.getItem().getContainerItem(item).isEmpty()) {
                if (item.getCount() == 1) {
                    getInventory().setStackInSlot(slot, item.getItem().getContainerItem(item));
                } else {
                    ItemStack drop = processSurplus(item.getItem().getContainerItem(item));
                    if (!drop.isEmpty()) {
                        this.dropItem(drop);
                    }
                    this.getInventory().extractItem(slot, 1, false);
                }
            } else {
                this.getInventory().extractItem(slot, 1, false);
            }
        }
        resetRecipe = false;
        this.onInventoryChanged();
    }

    private ItemStack processSurplus(ItemStack item) {
        for (int a = 0; a < 4; a++) {
            if (item.isEmpty()) {
                return ItemStack.EMPTY;// If item was sorted
            }

            int s = getInventory().getSlots() - 4 + a;
            ItemStack slot = getInventory().getStackInSlot(s);
            if (slot.isEmpty()) {
                getInventory().setStackInSlot(s, item);
                return ItemStack.EMPTY;// All Placed
            } else {
                if (slot.isItemEqual(item) && slot.getCount() < slot.getMaxStackSize()) {
                    if (slot.getCount() + item.getCount() <= slot.getMaxStackSize()) {
                        slot.grow(item.getCount());
                        return ItemStack.EMPTY;// All Shared
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
        ItemStack resSlot = getInventory().getStackInSlot(getOutputSlotNum());
        if (!resSlot.isEmpty() && !result.isEmpty()) {
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
            return ItemStack.EMPTY;
        }

//        if (ticksExisted <= 1)
//            return ItemStack.EMPTY;

        for (int a = 0; a < getOutputSlotNum(); a++) {
            craftMatrix.setInventorySlotContents(a, getInventory().getStackInSlot(a));
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
            if (!recipe.isEmpty() && !oldRecipe.isEmpty() && !recipe.isItemEqual(oldRecipe)) {
                progress = 0;
            }
            if (progress > progressMax) progress = progressMax - 1;

            syncData();
        }
    }

    public boolean canCraft() {
        if (progressMax > 0 && !recipe.isEmpty() && recipe instanceof ItemStack) {
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
        craftMatrix = new CarpenterCraftMatrix(this, syncCarpenter, ShapelessCarpenterRecipes.GLOBAL_WIDTH, ShapelessCarpenterRecipes.GLOBAL_HEIGHT);
    }

    public boolean shouldRenderCraftMetre() {
        return !recipe.isEmpty();
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
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        tier = nbt.getInteger("tier");

        inventory.deserializeNBT(nbt.getCompoundTag("inventory"));

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

        nbt.setTag("inventory", inventory.serializeNBT());

        nbt.setFloat("Progress", progress);
        nbt.setFloat("ProgressMax", progressMax);
        nbt.setString("ResName", resName);
        nbt.setString("toolTypeRequired", toolTypeRequired);
        nbt.setString("researchRequired", researchRequired);
        return nbt;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
        }
        return super.getCapability(capability, facing);
    }
}
