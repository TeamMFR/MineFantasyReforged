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
import minefantasy.mfr.init.SoundsMFR;
import minefantasy.mfr.item.armour.ItemArmourMFR;
import minefantasy.mfr.item.heatable.ItemHeated;
import minefantasy.mfr.knowledge.KnowledgeListMFR;
import minefantasy.mfr.mechanics.PlayerTickHandlerMF;
import minefantasy.mfr.proxy.NetworkUtils;
import minefantasy.mfr.network.AnvilPacket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.WorldServer;

import java.util.ArrayList;
import java.util.Random;

public class TileEntityAnvilMFR extends TileEntity implements IInventory, IAnvil, IQualityBalance {
    public int tier;
    public String resName = "<No Project Set>";
    public float progressMax;
    public float progress;
    public String texName = "";
    public float qualityBalance = 0F;
    public float thresholdPosition = 0.1F;
    public float leftHit = 0F;
    public float rightHit = 0F;
    private ItemStack[] inventory;
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

    public TileEntityAnvilMFR() {
        this(0, "Iron");
    }

    public TileEntityAnvilMFR(int tier, String name) {
        inventory = new ItemStack[25];
        this.tier = tier;
        texName = name;
        setContainer(new ContainerAnvilMF(this));
    }

    public TileEntityAnvilMFR setDisplay() {
        isFakeAnvil = true;
        return this;
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
        nbt.setString("TextureName", texName);
        nbt.setBoolean("outputHot", outputHot);
        nbt.setFloat("Quality", qualityBalance);
        nbt.setFloat("leftHit", leftHit);
        nbt.setFloat("rightHit", rightHit);
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
    public boolean isUsableByPlayer(EntityPlayer player) {
        return player.getDistance(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) < 8D;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

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
            if (result == null) {
                return;
            }

            if (result.getItem() instanceof ItemArmourMFR) {
                result = modifyArmour(result);
            }

            if (result.getMaxStackSize() == 1 && !lastPlayerHit.isEmpty()) {
                getNBT(result).setString("MF_CraftedByName", lastPlayerHit);
            }

            int temp = this.averageTemp();
            if (outputHot && temp > 0) {
                result = ItemHeated.createHotItem(result, temp);
            }

            int outputSlot = getSizeInventory() - 1;

            if (inventory[outputSlot] == null || SpecialForging.getItemDesign(inventory[outputSlot]) != null) {
                inventory[outputSlot] = result;
            } else {
                if (inventory[outputSlot].isItemEqual(result) && ItemStack.areItemStackTagsEqual(inventory[outputSlot], result)) {
                    if (inventory[outputSlot].getCount() + result.getCount() <= getStackSize(inventory[outputSlot])) {
                        inventory[outputSlot].grow(result.getCount());
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
        for (int a = 0; a < getSizeInventory() - 1; a++) {
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

    private ItemStack modifySpecials(ItemStack result) {
        boolean hasHeart = false;
        boolean isTool = result.getMaxStackSize() == 1 && result.isItemStackDamageable();
        EntityPlayer player = world.getPlayerEntityByName(lastPlayerHit);

        Item DF = SpecialForging.getDragonCraft(result);

        if (DF != null) {
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
            result = new ItemStack(DF, result.getCount(), result.getItemDamage());
            if (nbt != null) {
                result.setTagCompound((NBTTagCompound) nbt);
            }
        } else {
            Item special = SpecialForging
                    .getSpecialCraft(SpecialForging.getItemDesign(inventory[getSizeInventory() - 1]), result);
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
        for (ItemStack item : inventory) {
            if (item != null && item.hasTagCompound()) {
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
        for (int a = 0; a < getSizeInventory() - 1; a++) {
            ItemStack item = getStackInSlot(a);
            if (item != null && item.getItem() instanceof IHotItem) {
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
        if (itemstack != null) {
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
            if (slot != null && slot.isItemEqual(item)) {
                count += slot.getCount();
            }
        }
        return count >= number;
    }

    public void syncData() {

        if (world.isRemote)
            return;

        NetworkUtils.sendToWatchers(new AnvilPacket(this).generatePacket(), (WorldServer) world, this.pos);
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
        for (int slot = 0; slot < getSizeInventory() - 1; slot++) {
            ItemStack item = getStackInSlot(slot);
            if (item != null && item.getItem() != null && item.getItem().getContainerItem(item) != null) {
                if (item.getCount() == 1) {
                    setInventorySlotContents(slot, item.getItem().getContainerItem(item));
                } else {
                    this.dropItem(item.getItem().getContainerItem(item));
                    this.decrStackSize(slot, 1);
                }
            } else {
                this.decrStackSize(slot, 1);
            }
        }
        resetRecipe = false;
        this.onInventoryChanged();
    }

    private boolean canFitResult(ItemStack result) {
        ItemStack resSlot = inventory[getSizeInventory() - 1];
        if (resSlot != null && result != null) {
            int maxStack = getStackSize(resSlot);
            if (resSlot.getCount() + result.getCount() > maxStack) {
                return false;
            }
            if (resSlot.getItem() instanceof IHotItem) {
                ItemStack heated = Heatable.getItem(resSlot);
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
        if (slot == null)
            return 0;

        if (slot.getItem() instanceof IHotItem) {
            ItemStack held = Heatable.getItem(slot);
            if (held != null)
                return held.getMaxStackSize();
        }
        return slot.getMaxStackSize();
    }

    // CRAFTING CODE
    public ItemStack getResult() {
        if (syncAnvil == null || craftMatrix == null) {
            return null;
        }

        if (ticksExisted <= 1)
            return null;

        for (int a = 0; a < getSizeInventory() - 1; a++) {
            craftMatrix.setInventorySlotContents(a, inventory[a]);
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

            if (recipe != null && oldRecipe != null && !recipe.isItemEqual(oldRecipe)) {
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
        if (item == null)
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

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }
}
