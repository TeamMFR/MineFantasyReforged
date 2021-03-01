package minefantasy.mfr.tile;

import minefantasy.mfr.api.knowledge.ResearchLogic;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.constants.Tool;
import minefantasy.mfr.container.ContainerBase;
import minefantasy.mfr.container.ContainerCarpenter;
import minefantasy.mfr.item.ItemArmourMFR;
import minefantasy.mfr.network.CarpenterPacket;
import minefantasy.mfr.network.NetworkHandler;
import minefantasy.mfr.recipe.CarpenterCraftMatrix;
import minefantasy.mfr.recipe.CraftingManagerCarpenter;
import minefantasy.mfr.recipe.ICarpenter;
import minefantasy.mfr.recipe.ShapelessCarpenterRecipes;
import minefantasy.mfr.util.ToolHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
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

import static minefantasy.mfr.constants.Constants.CRAFTED_BY_NAME_TAG;

public class TileEntityCarpenter extends TileEntityBase implements ICarpenter {

    // the tier of this carpenter block
    private int tier;
    public static final int width = 4;
    public static final int height = 4;

    private ItemStack resultStack = ItemStack.EMPTY;

    public float progressMax;
    public float progress;
    private ContainerCarpenter syncCarpenter;
    private CarpenterCraftMatrix craftMatrix;
    private String lastPlayerHit = ""; // TODO: store entity UUID instead of string
    private SoundEvent craftSound = SoundEvents.BLOCK_WOOD_STEP;
    private String requiredResearch = "";
    private Skill requiredSkill;
    private Tool requiredToolType = Tool.HANDS;
    private int requiredToolTier;
    private int requiredCarpenterTier;

    public final ItemStackHandler inventory = createInventory();

    public TileEntityCarpenter() {
        setContainer(new ContainerCarpenter(this));
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
        return new ContainerCarpenter(player,this);
    }

    @Override
    protected int getGuiId() {
        return NetworkHandler.GUI_CARPENTER_BENCH;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return true;
    }

    public void onInventoryChanged() {
            updateCraftingData();
            IBlockState iblockstate = world.getBlockState(getPos());
            world.notifyBlockUpdate(getPos(), iblockstate, iblockstate, 3);
            markDirty();
    }

    public boolean tryCraft(EntityPlayer user) {
        if (user == null){
            return false;
        }

        Tool tool = ToolHelper.getToolTypeFromStack(user.getHeldItemMainhand());
        int toolTier = ToolHelper.getCrafterTier(user.getHeldItemMainhand());
        if (!(tool == Tool.OTHER)) {
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
                    && tool == requiredToolType
                    && tier >= requiredCarpenterTier
                    && toolTier >= requiredToolTier) {
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
            if (world.rand.nextInt(5) == 0) {
                return SoundEvents.UI_BUTTON_CLICK;
            }
            if (world.rand.nextInt(20) == 0) {
                return SoundEvents.BLOCK_WOODEN_DOOR_OPEN;
            }
            return SoundEvents.BLOCK_WOOD_STEP;
        }
        return craftSound;
    }

    private void craftItem(EntityPlayer user) {
        if (this.canCraft()) {
            addXP(user);
            ItemStack result = resultStack.copy();
            if (!result.isEmpty() && result.getItem() instanceof ItemArmourMFR) {
                result = modifyArmour(result);
            }
            int output = getOutputSlotNum();

            if (this.getInventory().getStackInSlot(output).isEmpty()) {
                if (result.getMaxStackSize() == 1 && !lastPlayerHit.isEmpty()) {
                    getNBT(result).setString(CRAFTED_BY_NAME_TAG, lastPlayerHit);
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
            float f = world.rand.nextFloat() * 0.8F + 0.1F;
            float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
            float f2 = world.rand.nextFloat() * 0.8F + 0.1F;

            while (itemstack.getCount() > 0) {
                int j1 = world.rand.nextInt(21) + 10;

                if (j1 > itemstack.getCount()) {
                    j1 = itemstack.getCount();
                }

                itemstack.shrink(j1);
                EntityItem entityitem = new EntityItem(world, pos.getX() + f, pos.getY() + f1, pos.getZ() + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                if (itemstack.hasTagCompound()) {
                    entityitem.getItem().setTagCompound(itemstack.getTagCompound().copy());
                }

                float f3 = 0.05F;
                entityitem.motionX = (float) world.rand.nextGaussian() * f3;
                entityitem.motionY = (float) world.rand.nextGaussian() * f3 + 0.2F;
                entityitem.motionZ = (float) world.rand.nextGaussian() * f3;
                world.spawnEntity(entityitem);
            }
        }
    }

    public void syncData() {
        if (world.isRemote)
            return;
        NetworkHandler.sendToAllTrackingChunk (world, pos.getX() >> 4, pos.getZ() >> 4, new CarpenterPacket(this));
    }

    public String getRequiredToolType() {
        return requiredToolType.getName();
    }

    public SoundEvent getCraftingSound() {
        return craftSound;
    }

    @Override
    public void setCraftingSound(SoundEvent sound) {
        this.craftSound = sound;
    }

    public int getToolTierNeeded() {
        return this.requiredToolTier;
    }

    public int getCarpenterTierNeeded() {
        return this.requiredCarpenterTier;
    }

    public void consumeResources() {
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

        for (int a = 0; a < getOutputSlotNum(); a++) {
            craftMatrix.setInventorySlotContents(a, getInventory().getStackInSlot(a));
        }

        ItemStack result = CraftingManagerCarpenter.getInstance().findMatchingRecipe(this, craftMatrix, world);
        if (result == null) {
            result = ItemStack.EMPTY;
        }
        return result;
    }

    public String getResultName() {
        return resultStack == ItemStack.EMPTY || resultStack.getItem() == Item.getItemFromBlock(Blocks.AIR) ? I18n.format("gui.no_project_set") : resultStack.getDisplayName();
    }

    public void updateCraftingData() {
        if (!world.isRemote) {
            ItemStack oldRecipe = resultStack;
            resultStack = getResult();
            // syncItems();

            if (!canCraft() && progress > 0) {
                progress = 0;
                // quality = 100;
            }
            if (!resultStack.isEmpty() && !oldRecipe.isEmpty() && !resultStack.isItemEqual(oldRecipe)) {
                progress = 0;
            }
            if (progress > progressMax) progress = progressMax - 1;

            syncData();
            sendUpdates();
        }
    }

    public boolean canCraft() {
        if (progressMax > 0 && !resultStack.isEmpty() && resultStack instanceof ItemStack) {
            return this.canFitResult(resultStack);
        }
        return false;
    }

    @Override
    public void setProgressMax(int i) {
        progressMax = i;
    }

    @Override
    public void setRequiredToolTier(int i) {
        requiredToolTier = i;
    }

    @Override
    public void setRequiredCarpenter(int i) {
        requiredCarpenterTier = i;
    }

    @Override
    public void setHotOutput(boolean i) {
    }

    public void setContainer(ContainerCarpenter container) {
        syncCarpenter = container;
        craftMatrix = new CarpenterCraftMatrix(this, syncCarpenter, ShapelessCarpenterRecipes.GLOBAL_WIDTH, ShapelessCarpenterRecipes.GLOBAL_HEIGHT);
    }

    public boolean shouldRenderCraftMetre() {
        return !resultStack.isEmpty();
    }

    public int getProgressBar(int i) {
        return (int) Math.ceil(i / progressMax * progress);
    }

    @Override
    public void setRequiredToolType(String toolType) {
        this.requiredToolType = Tool.fromName(toolType);
    }

    @Override
    public void setResearch(String research) {
        this.requiredResearch = research;
    }

    public String getResearchNeeded() {
        return requiredResearch;
    }

    public boolean doesPlayerKnowCraft(EntityPlayer user) {
        if (getResearchNeeded().isEmpty()) {
            return true;
        }
        return ResearchLogic.hasInfoUnlocked(user, getResearchNeeded());
    }

    private void addXP(EntityPlayer smith) {
        if (requiredSkill != null) {
            float baseXP = this.progressMax / 10F;
            requiredSkill.addXP(smith, (int) baseXP + 1);
        }
    }

    @Override
    public void setRequiredSkill(Skill skill) {
        requiredSkill = skill;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        tier = nbt.getInteger(TIER_TAG);
        inventory.deserializeNBT(nbt.getCompoundTag(INVENTORY_TAG));
        progress = nbt.getFloat(PROGRESS_TAG);
        progressMax = nbt.getFloat(PROGRESS_MAX_TAG);
        resultStack = new ItemStack(nbt.getCompoundTag(RESULT_STACK_TAG));
        requiredToolType = Tool.fromName(nbt.getString(TOOL_TYPE_REQUIRED_TAG));
        requiredResearch = nbt.getString(RESEARCH_REQUIRED_TAG);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger(TIER_TAG, tier);
        nbt.setTag(INVENTORY_TAG, inventory.serializeNBT());
        nbt.setFloat(PROGRESS_TAG, progress);
        nbt.setFloat(PROGRESS_MAX_TAG, progressMax);
        nbt.setTag(RESULT_STACK_TAG, resultStack.writeToNBT(new NBTTagCompound()));
        nbt.setString(TOOL_TYPE_REQUIRED_TAG, requiredToolType.getName());
        nbt.setString(RESEARCH_REQUIRED_TAG, requiredResearch);
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
