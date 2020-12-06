package minefantasy.mfr.tile;

import minefantasy.mfr.api.crafting.IQualityBalance;
import minefantasy.mfr.api.crafting.anvil.AnvilCraftMatrix;
import minefantasy.mfr.api.crafting.anvil.CraftingManagerAnvil;
import minefantasy.mfr.api.crafting.anvil.IAnvil;
import minefantasy.mfr.api.crafting.anvil.ShapelessAnvilRecipes;
import minefantasy.mfr.api.crafting.exotic.SpecialForging;
import minefantasy.mfr.api.heating.Heatable;
import minefantasy.mfr.api.heating.IHotItem;
import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.helpers.ToolHelper;
import minefantasy.mfr.api.knowledge.ResearchLogic;
import minefantasy.mfr.api.rpg.Skill;
import minefantasy.mfr.container.ContainerAnvilMF;
import minefantasy.mfr.container.ContainerBase;
import minefantasy.mfr.init.KnowledgeListMFR;
import minefantasy.mfr.init.SoundsMFR;
import minefantasy.mfr.item.armour.ItemArmourMFR;
import minefantasy.mfr.item.heatable.ItemHeated;
import minefantasy.mfr.mechanics.PlayerTickHandlerMF;
import minefantasy.mfr.network.AnvilPacket;
import minefantasy.mfr.network.NetworkHandler;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

import static minefantasy.mfr.constants.Constants.CRAFTED_BY_NAME_TAG;

public class TileEntityAnvilMFR extends TileEntityBase implements IAnvil, IQualityBalance {
    public int tier;
    public String resName = "<No Project Set>";
    public float progressMax;
    public float progress;
    public String texName = "";
    public float qualityBalance = 0F;
    public float thresholdPosition = 0.1F;
    public float leftHit = 0F;
    public float rightHit = 0F;
    private Random rand = new Random();
    private int ticksExisted;
    private ContainerAnvilMF syncAnvil;
    private AnvilCraftMatrix craftMatrix;
    private String lastPlayerHit = "";
    private String toolTypeRequired = "hammer";
    private String researchRequired = "";
    private boolean outputHot = false;
    private Skill skillUsed;
    private boolean resetRecipe = false;
    private boolean isFakeAnvil = false;
    private ItemStack recipe;
    private int hammerTierRequired;
    private int anvilTierRequired;

    public final ItemStackHandler inventory = createInventory();

    @Override
    protected ItemStackHandler createInventory() {
        return new ItemStackHandler(25);
    }

    @Override
    public ItemStackHandler getInventory() {
        return this.inventory;
    }

    @Override
    public ContainerBase createContainer(EntityPlayer player) {
        return new ContainerAnvilMF(player,this);
    }

    @Override
    protected int getGuiId() {
        return NetworkHandler.GUI_ANVIL;
    }

    public TileEntityAnvilMFR() {setContainer(new ContainerAnvilMF(this)); }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return true;
    }

    @Override
    public void markDirty() {
        ++ticksExisted;
        if (!world.isRemote && (leftHit == 0 || rightHit == 0)) {
            reassignHitValues();
        }
        if (!world.isRemote) {
            if (!isFakeAnvil && ticksExisted % 20 == 0) {
                updateCraftingData();
            }
            if (!canCraft() && ticksExisted > 1) {
                progress = progressMax = 0;
                this.resName = "";
                this.recipe = null;
            }
        }
        if (!world.isRemote) {
            updateThreshold();
        }
        resetRecipe = false;
    }

    public void onInventoryChanged() {
        if (!resetRecipe) {
            updateCraftingData();
            resetRecipe = true;
        }
    }

    public boolean tryCraft(EntityPlayer user, boolean rightClick) {
        if (user == null)
            return false;

        String toolType = ToolHelper.getCrafterTool(user.getHeldItemMainhand());
        int hammerTier = ToolHelper.getCrafterTier(user.getHeldItemMainhand());
        if (toolType.equalsIgnoreCase("hammer") || toolType.equalsIgnoreCase("hvyHammer")) {
            if (!user.getHeldItemMainhand().isEmpty()) {
                user.getHeldItemMainhand().damageItem(1, user);
                if (user.getHeldItemMainhand().getItemDamage() >= user.getHeldItemMainhand().getMaxDamage()) {
                    if (world.isRemote)
                        user.renderBrokenItemStack(user.getHeldItemMainhand());

                    user.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
                }
            }
            if (world.isRemote)
                return true;

            if (doesPlayerKnowCraft(user) && canCraft() && toolType.equalsIgnoreCase(toolTypeRequired)) {
                float mod = 1.0F;
                if (hammerTier < hammerTierRequired) {
                    mod = 2.0F;
                    if (rand.nextInt(5) == 0) {
                        reassignHitValues();
                    }
                }
                if (rightClick) {
                    this.qualityBalance += (rightHit * mod);
                } else {
                    this.qualityBalance -= (leftHit * mod);
                }
                if (qualityBalance >= 1.0F || qualityBalance <= -1.0F) {
                    ruinCraft();
                }

                world.playSound(user, pos.add(0.5D, 0.5D, 0.5D), SoundsMFR.ANVIL_SUCCEED, SoundCategory.NEUTRAL, 0.25F, rightClick ? 1.2F : 1.0F);
                float efficiency = ToolHelper.getCrafterEfficiency(user.getHeldItem(EnumHand.MAIN_HAND)) * (rightClick ? 0.75F : 1.0F);

                if (user.swingProgress > 0 && user.swingProgress <= 1.0) {
                    efficiency *= (0.5F - user.swingProgress);
                }

                progress += Math.max(0.2F, efficiency);
                if (progress >= progressMax) {
                    craftItem(user);
                }
            } else {
                world.playSound(user, pos.add(0.5D, 0.5D, 0.5D), SoundsMFR.ANVIL_FAIL, SoundCategory.NEUTRAL, 0.25F, 1.0F);
            }
            lastPlayerHit = user.getName();
            updateCraftingData();

            return true;
        }
        updateCraftingData();
        return false;
    }

    private void ruinCraft() {
        if (!world.isRemote) {
            consumeResources();
            reassignHitValues();
            progress = progressMax = qualityBalance = 0;
        }
        world.playSound(pos.getX() + 0.5, pos.getY() + 0.8, pos.getZ() + 0.5, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.AMBIENT, 1.0F, 0.8F, false);
    }

    public boolean doesPlayerKnowCraft(EntityPlayer user) {
        return getResearchNeeded().isEmpty() || ResearchLogic.hasInfoUnlocked(user, getResearchNeeded());
    }

    private void craftItem(EntityPlayer lastHit) {
        if (this.canCraft()) {
            ItemStack result = modifySpecials(recipe);
            if (result.isEmpty()) {
                return;
            }

            if (result.getItem() instanceof ItemArmourMFR) {
                result = modifyArmour(result);
            }

            if (result.getMaxStackSize() == 1 && !lastPlayerHit.isEmpty()) {
                getNBT(result).setString(CRAFTED_BY_NAME_TAG, lastPlayerHit);
            }

            int temp = this.averageTemp();
            if (outputHot && temp > 0) {
                result = ItemHeated.createHotItem(result, temp);
            }

            int outputSlot = getInventory().getSlots() - 1;

            if (getInventory().getStackInSlot(outputSlot).isEmpty() || SpecialForging.getItemDesign(getInventory().getStackInSlot(outputSlot)) != null) {
                getInventory().setStackInSlot(outputSlot, result);
            } else {
                if (getInventory().getStackInSlot(outputSlot).isItemEqual(result) && ItemStack.areItemStackTagsEqual(getInventory().getStackInSlot(outputSlot), result)) {
                    if (getInventory().getStackInSlot(outputSlot).getCount() + result.getCount() <= getStackSize(getInventory().getStackInSlot(outputSlot))) {
                        getInventory().getStackInSlot(outputSlot).grow(result.getCount());
                    } else {
                        dropItem(result);
                    }
                } else {
                    dropItem(result);
                }
            }

            addXP(lastHit);
            consumeResources();
        }
        onInventoryChanged();
        progress = 0;
        reassignHitValues();
        qualityBalance = 0;
    }

    private void addXP(EntityPlayer smith) {
        if (skillUsed == null)
            return;

        float baseXP = this.progressMax / 10F;
        baseXP /= (1.0F + getAbsoluteBalance());

        skillUsed.addXP(smith, (int) baseXP + 1);
    }

    private ItemStack modifyArmour(ItemStack result) {
        ItemArmourMFR item = (ItemArmourMFR) result.getItem();
        boolean canColour = item.canColour();
        int colour = -1;
        for (int a = 0; a < getInventory().getSlots() - 1; a++) {
            ItemStack slot = getInventory().getStackInSlot(a);
            if (!slot.isEmpty() && slot.getItem() instanceof ItemArmor) {
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

    private ItemStack modifySpecials(ItemStack result) {
        boolean hasHeart = false;
        boolean isTool = result.getMaxStackSize() == 1 && result.isItemStackDamageable();
        EntityPlayer player = world.getPlayerEntityByName(lastPlayerHit);

        Item dragonCraft = SpecialForging.getDragonCraft(result);

        if (dragonCraft != null) {
            // DRAGONFORGE
            for (int x = -4; x <= 4; x++) {
                for (int y = -4; y <= 4; y++) {
                    for (int z = -4; z <= 4; z++) {
                        TileEntity tile = world.getTileEntity(pos.add(x,y,z));
                        if (player != null && ResearchLogic.hasInfoUnlocked(player, KnowledgeListMFR.smeltDragonforge)
                                && tile != null && tile instanceof TileEntityForge) {
                            if (((TileEntityForge) tile).dragonHeartPower > 0) {
                                hasHeart = true;
                                ((TileEntityForge) tile).dragonHeartPower = 0;
                                world.createExplosion(null, pos.getX() + x, pos.getY() + y, pos.getZ() + z, 1F, false);
                                PlayerTickHandlerMF.spawnDragon(player);
                                PlayerTickHandlerMF.addDragonEnemyPts(player, 2);

                                break;
                            }
                        }
                    }
                }
            }
        }
        if (hasHeart) {
            NBTBase nbt = !(result.hasTagCompound()) ? null : result.getTagCompound().copy();
            result = new ItemStack(dragonCraft, result.getCount(), result.getItemDamage());
            if (nbt != null) {
                result.setTagCompound((NBTTagCompound) nbt);
            }
        } else {
            Item special = SpecialForging.getSpecialCraft(SpecialForging.getItemDesign(getInventory().getStackInSlot(getInventory().getSlots() -1)), result);
            if (special != null) {
                NBTBase nbt = !(result.hasTagCompound()) ? null : result.getTagCompound().copy();
                result = new ItemStack(special, result.getCount(), result.getItemDamage());
                if (nbt != null) {
                    result.setTagCompound((NBTTagCompound) nbt);
                }
            }
        }

        if (isPerfectItem() && !isMythicRecipe()) {
            this.setTrait(result, "MF_Inferior", false);
            if (CustomToolHelper.isMythic(result)) {
                result.getTagCompound().setBoolean("Unbreakable", true);
            } else {
                ToolHelper.setQuality(result, 200.0F);
            }
            return result;
        }
        if (isTool) {
            result = modifyQualityComponents(result);
        }
        return damageItem(result);
    }

    private ItemStack modifyQualityComponents(ItemStack result) {
        float totalPts = 0F;
        int totalItems = 0;
        for (int a = 0; a < getInventory().getSlots() ; a++) {
            ItemStack item = getInventory().getStackInSlot(a);
            if (!item.isEmpty() && item.hasTagCompound()) {
                if (item.getTagCompound().hasKey("MF_Inferior")) {
                    ++totalItems;
                    boolean inf = item.getTagCompound().getBoolean("MF_Inferior");
                    totalPts += (inf ? -50F : 100F);
                }
            }
        }
        if (totalItems > 0 && totalPts > 0) {
            totalPts /= totalItems;
            ToolHelper.setQuality(result, ToolHelper.getQualityLevel(result) + totalPts);
            if (totalPts <= -85F) {
                this.setTrait(result, "MF_Inferior", true);
            }
            if (totalPts >= 80) {
                this.setTrait(result, "MF_Inferior", false);
            }
        }
        return result;
    }

    private int averageTemp() {
        float totalTemp = 0;
        int itemCount = 0;
        for (int a = 0; a < getInventory().getSlots() - 1; a++) {
            ItemStack item = getInventory().getStackInSlot(a);
            if (!item.isEmpty() && item.getItem() instanceof IHotItem) {
                ++itemCount;
                totalTemp += Heatable.getTemp(item);
            }
        }
        if (totalTemp > 0 && itemCount > 0) {
            return (int) (totalTemp / itemCount);
        }
        return 0;
    }

    private NBTTagCompound getNBT(ItemStack item) {
        if (!item.hasTagCompound()) {
            item.setTagCompound(new NBTTagCompound());
        }
        return item.getTagCompound();
    }

    private void dropItem(ItemStack itemstack) {
        if (!itemstack.isEmpty()) {
            float f = this.rand.nextFloat() * 0.4F + 0.3F;
            float f1 = this.rand.nextFloat() * 0.4F + 0.3F;
            float f2 = this.rand.nextFloat() * 0.4F + 0.3F;

            while (itemstack.getCount() > 0) {
                int j1 = this.rand.nextInt(21) + 10;

                if (j1 > itemstack.getCount()) {
                    j1 = itemstack.getCount();
                }

                boolean delay = true;
                double[] positions = new double[]{pos.getX() + f, pos.getY() + f1 + 1, pos.getZ() + f2};
                if (world.getBlockState(pos.add(0,1,0)).getMaterial().isSolid() && lastPlayerHit != null) {
                    EntityPlayer smith = world.getPlayerEntityByName(lastPlayerHit);
                    if (smith != null) {
                        delay = false;
                        positions = new double[]{smith.posX, smith.posY, smith.posZ};
                    }
                }

                itemstack.shrink(j1);
                EntityItem entityitem = new EntityItem(world, positions[0], positions[1], positions[2],
                        new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
                if (itemstack.hasTagCompound()) {
                    entityitem.getItem().setTagCompound( itemstack.getTagCompound().copy());
                }

                entityitem.setPickupDelay(delay ? 20 : 0);
                entityitem.motionX = entityitem.motionY = entityitem.motionZ = 0;

                world.spawnEntity(entityitem);
            }
        }
    }

    public boolean hasItems(EntityPlayer user, ItemStack[] items) {
        for (ItemStack check : items) {
            if (!hasItems(user, check)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasItems(EntityPlayer user, ItemStack item) {
        return hasItems(user, item, item.getCount());
    }

    public boolean hasItems(EntityPlayer user, ItemStack item, int number) {
        int count = 0;
        for (int a = 0; a < user.inventory.getSizeInventory(); a++) {
            ItemStack slot = user.inventory.getStackInSlot(a);
            if (!slot.isEmpty() && slot.isItemEqual(item)) {
                count += slot.getCount();
            }
        }
        return count >= number;
    }

    public void syncData() {

        if (world.isRemote)
            return;
        NetworkHandler.sendToAllTrackingChunk (world, pos.getX(), pos.getZ(), new AnvilPacket(this));
    }

    public String getResultName() {
        if (!world.isRemote && recipe != null && recipe.getDisplayName() != null) {
            resName = recipe.getDisplayName();
        }
        return resName;
    }

    public String getResearchNeeded() {
        return researchRequired;
    }

    public String getToolNeeded() {
        return toolTypeRequired;
    }

    public int getToolTierNeeded() {
        return this.hammerTierRequired;
    }

    public int getAnvilTierNeeded() {
        return this.anvilTierRequired;
    }

    public void consumeResources() {
        resetRecipe = true;
        for (int slot = 0; slot < getInventory().getSlots() - 1; slot++) {
            ItemStack item = getInventory().getStackInSlot(slot);
            if (!item.isEmpty() && item.getItem() != null && !item.getItem().getContainerItem(item).isEmpty()) {
                if (item.getCount() == 1) {
                    getInventory().setStackInSlot(slot, item.getItem().getContainerItem(item));
                } else {
                    this.dropItem(item.getItem().getContainerItem(item));
                    getInventory().extractItem(slot, 1, false);
                }
            } else {
               getInventory().extractItem(slot, 1, false);
            }
        }
        resetRecipe = false;
        this.onInventoryChanged();
    }

    private boolean canFitResult(ItemStack result) {
        ItemStack resSlot = getInventory().getStackInSlot(getInventory().getSlots() - 1);
        if (!resSlot.isEmpty() && !result.isEmpty()) {
            int maxStack = getStackSize(resSlot);
            if (resSlot.getCount() + result.getCount() > maxStack) {
                return false;
            }
            if (resSlot.getItem() instanceof IHotItem) {
                ItemStack heated = Heatable.getItemStack(resSlot);
                if (heated != null) {
                    resSlot = heated;
                }
            }
            if (!resSlot.isItemEqual(result)
                    && (SpecialForging.getItemDesign(resSlot) == null || resSlot.getCount() > 1)) {
                return false;
            }
        }
        return true;
    }

    private int getStackSize(ItemStack slot) {
        if (slot.isEmpty())
            return 0;

        if (slot.getItem() instanceof IHotItem) {
            ItemStack held = Heatable.getItemStack(slot);
            if (!held.isEmpty())
                return held.getMaxStackSize();
        }
        return slot.getMaxStackSize();
    }

    // CRAFTING CODE
    public ItemStack getResult() {
        if (syncAnvil == null || craftMatrix == null) {
            return ItemStack.EMPTY;
        }

        if (ticksExisted <= 1)
            return ItemStack.EMPTY;

        for (int a = 0; a < getInventory().getSlots() - 1; a++) {
            craftMatrix.setInventorySlotContents(a, getInventory().getStackInSlot(a));
        }

        return CraftingManagerAnvil.getInstance().findMatchingRecipe(this, craftMatrix);
    }

    public void updateCraftingData() {
        if (!world.isRemote) {
            ItemStack oldRecipe = recipe;
            recipe = getResult();
            // syncItems();

            if (!canCraft() && progress > 0) {
                progress = 0;
                reassignHitValues();
                qualityBalance = 0;
            }

            if (!recipe.isEmpty() && !oldRecipe.isEmpty() && !recipe.isItemEqual(oldRecipe)) {
                progress = 0;
                reassignHitValues();
                qualityBalance = 0;
            }
            if (progress > progressMax)
                progress = progressMax - 1;
            syncData();
        }
    }

    public boolean canCraft() {
        if (this.isMythicRecipe() && !this.isMythicReady()) {
            return false;
        }

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
    public void setHammerUsed(int i) {
        hammerTierRequired = i;
    }

    @Override
    public void setRequiredAnvil(int i) {
        anvilTierRequired = i;
    }

    @Override
    public void setHotOutput(boolean i) {
        outputHot = i;
    }

    public void setContainer(ContainerAnvilMF container) {
        syncAnvil = container;
        craftMatrix = new AnvilCraftMatrix(this, syncAnvil, ShapelessAnvilRecipes.globalWidth,
                ShapelessAnvilRecipes.globalHeight);
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

    private boolean isMythicRecipe() {
        return false;// this.hammerTierRequired >= 6;
    }

    private boolean isMythicReady() {
        return true;
    }

    public String getTextureName() {
        return texName;
    }

    @Override
    public float getMarkerPosition() {
        return qualityBalance;
    }

    @Override
    public boolean shouldShowMetre() {
        return true;
    }

    @Override
    public float getThresholdPosition() {
        return thresholdPosition;
    }

    @Override
    public float getSuperThresholdPosition() {
        return thresholdPosition / 3.5F;
    }

    private float getAbsoluteBalance() {
        return qualityBalance < 0 ? -qualityBalance : qualityBalance;
    }

    private float getItemDamage() {
        int threshold = (int) (100F * thresholdPosition / 2F);
        int total = (int) (100F * getAbsoluteBalance() - threshold);

        if (total > threshold) {
            float percent = ((float) total - (float) threshold) / (100F - threshold);
            return percent;
        }
        return 0F;
    }

    private boolean isPerfectItem() {
        int threshold = (int) (100F * getSuperThresholdPosition() / 2F);
        int total = (int) (100F * getAbsoluteBalance() - threshold);

        return total <= threshold;
    }

    private ItemStack damageItem(ItemStack item) {
        float itemdam = getItemDamage();
        if (itemdam > 0.5F) {
            setTrait(item, "MF_Inferior");
            float q = 100F * (0.75F - (itemdam - 0.5F));
            ToolHelper.setQuality(item, Math.max(10F, q));
        }
        float damage = itemdam * item.getMaxDamage();
        if (item.isItemStackDamageable()) {
            if (damage > 0) {
                item.setItemDamage((int) (damage));
                if (isMythicRecipe()) {
                    setTrait(item, "MF_Inferior");
                }
            } else if (isMythicRecipe()) {
                setTrait(item, "Unbreakable");
            }
        }
        return item;
    }

    private void setTrait(ItemStack item, String trait) {
        setTrait(item, trait, true);
    }

    private void setTrait(ItemStack item, String trait, boolean flag) {
        if (item.isEmpty())
            return;
        if (item.getMaxStackSize() > 1 || !item.isItemStackDamageable()) {
            return;
        }

        NBTTagCompound nbt = this.getNBT(item);
        nbt.setBoolean(trait, flag);
    }

    private void updateThreshold() {
        float modifier = 1.0F;
        if (tier < anvilTierRequired) {
            modifier *= 0.25F;
        }

        float baseThreshold = world.getDifficulty().getDifficultyId() >= 2 ? 7.5F : 10F;
        thresholdPosition = (isMythicRecipe() ? 0.05F : baseThreshold / 100F) * modifier;
    }

    public void upset(EntityPlayer user) {
        if (this.progress > 0 && this.progressMax > 0) {
            world.playSound(user, pos.add(0.5D, 0.5D, 0.5D), SoundsMFR.ANVIL_SUCCEED, SoundCategory.NEUTRAL, 0.25F, 0.75F);
            if (!world.isRemote) {
                progress -= (progressMax / 10F);
                if (progress < 0) {
                    ruinCraft();
                }
            }
        }
    }

    private void reassignHitValues() {
        if (!world.isRemote) {
            leftHit = 0.1F + (0.01F * rand.nextInt(11));
            rightHit = 0.1F + (0.01F * rand.nextInt(11));
        }
    }

    @Override
    public void setSkill(Skill skill) {
        skillUsed = skill;
    }

    @Override
    public int getRecipeHammer() {
        return hammerTierRequired;
    }

    @Override
    public int getRecipeAnvil() {
        return anvilTierRequired;
    }

    @SuppressWarnings("Duplicates")
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
        texName = nbt.getString("TextureName");
        outputHot = nbt.getBoolean("outputHot");
        qualityBalance = nbt.getFloat("Quality");
        leftHit = nbt.getFloat("leftHit");
        rightHit = nbt.getFloat("rightHit");
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
        nbt.setString("TextureName", texName);
        nbt.setBoolean("outputHot", outputHot);
        nbt.setFloat("Quality", qualityBalance);
        nbt.setFloat("leftHit", leftHit);
        nbt.setFloat("rightHit", rightHit);
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
