package minefantasy.mfr.tile;

import minefantasy.mfr.api.crafting.IQualityBalance;
import minefantasy.mfr.api.crafting.exotic.SpecialForging;
import minefantasy.mfr.api.heating.Heatable;
import minefantasy.mfr.api.heating.IHotItem;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.constants.Tool;
import minefantasy.mfr.constants.Trait;
import minefantasy.mfr.container.ContainerAnvil;
import minefantasy.mfr.container.ContainerBase;
import minefantasy.mfr.init.MineFantasyKnowledgeList;
import minefantasy.mfr.init.MineFantasySounds;
import minefantasy.mfr.item.ItemArmourMFR;
import minefantasy.mfr.item.ItemHeated;
import minefantasy.mfr.mechanics.PlayerTickHandler;
import minefantasy.mfr.mechanics.knowledge.ResearchLogic;
import minefantasy.mfr.network.NetworkHandler;
import minefantasy.mfr.recipe.AnvilCraftMatrix;
import minefantasy.mfr.recipe.CraftingManagerAnvil;
import minefantasy.mfr.recipe.IAnvil;
import minefantasy.mfr.recipe.ShapelessAnvilRecipes;
import minefantasy.mfr.util.CustomToolHelper;
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
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static minefantasy.mfr.constants.Constants.CRAFTED_BY_NAME_TAG;

public class TileEntityAnvil extends TileEntityBase implements IAnvil, IQualityBalance, ITickable {

	private static final String LEFT_HIT_TAG = "left_hit";
	private static final String RIGHT_HIT_TAG = "right_hit";
	private static final String OUTPUT_HOT_TAG = "output_hot";
	private static final String TEXTURE_NAME_TAG = "texture_name";
	private static final String REQUIRED_HAMMER_TIER_TAG = "required_hammer_tier";
	private static final String REQUIRED_ANVIL_TIER_TAG = "required_anvil_tier";

	// the tier of this anvil block
	private int tier;
	private String textureName;

	// research specific fields

	private ItemStack resultStack = ItemStack.EMPTY;

	public float progressMax;
	public float progress;
	public float qualityBalance = 0F;
	public float thresholdPosition = 0.1F;
	public float leftHit = 0F;
	public float rightHit = 0F;
	private ContainerAnvil syncAnvil;
	private AnvilCraftMatrix craftMatrix;
	private Tool requiredToolType = Tool.HAMMER;
	private String requiredResearch = "";
	private boolean outputHot = false;
	private Skill requiredSkill;
	private int requiredHammerTier;
	private int requiredAnvilTier;

	public final ItemStackHandler inventory = createInventory();

	public TileEntityAnvil() {
		setContainer(new ContainerAnvil(this));
	}

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
		return new ContainerAnvil(player, this);
	}

	@Override
	protected int getGuiId() {
		return NetworkHandler.GUI_ANVIL;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		return true;
	}

	@Override
	public void update() {
		if (!world.isRemote && world.getTotalWorldTime() % 120 == 0){
			for (int slot = 0; slot < this.getInventory().getSlots(); slot++){
				ItemStack stack = this.getInventory().getStackInSlot(slot);
				if (stack.getItem() instanceof IHotItem){
					ItemHeated.setTemp(stack, ItemHeated.getTemp(stack) -1);

					if (ItemHeated.getTemp(stack) <= 0){
						ItemStack cooledStack =  ItemHeated.getStack(stack);
						cooledStack.setCount(stack.getCount());
						this.getInventory().setStackInSlot(slot,cooledStack);
					}
				}
			}
		}
	}

	// FIXME: this is still probably being called more times than necessary
	// TODO: implement recipe caching
	public void onInventoryChanged() {
		updateCraftingData();
		IBlockState iblockstate = world.getBlockState(getPos());
		world.notifyBlockUpdate(getPos(), iblockstate, iblockstate, 3);
		super.markDirty();
	}

	public boolean tryCraft(EntityPlayer user, boolean rightClick) {
		if (user == null)
			return false;

		Tool tool = ToolHelper.getToolTypeFromStack(user.getHeldItemMainhand());
		int hammerTier = ToolHelper.getCrafterTier(user.getHeldItemMainhand());
		if (tool == Tool.HAMMER || tool == Tool.HEAVY_HAMMER) {
			if (!user.getHeldItemMainhand().isEmpty()) {
				user.getHeldItemMainhand().damageItem(1, user);
				if (user.getHeldItemMainhand().getItemDamage() >= user.getHeldItemMainhand().getMaxDamage()) {
					if (world.isRemote)
						user.renderBrokenItemStack(user.getHeldItemMainhand());

					user.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
				}
			}

			if (doesPlayerKnowCraft(user) && canCraft() && tool == requiredToolType) {
				float mod = 1.0F;
				if (hammerTier < requiredHammerTier) {
					mod = 2.0F;
					if (world.rand.nextInt(5) == 0) {
						reassignHitValues();
					}
				}
				if (rightClick) {
					this.qualityBalance += (rightHit * mod);
				} else {
					this.qualityBalance -= (leftHit * mod);
				}
				if (qualityBalance >= 1.0F || qualityBalance <= -1.0F) {
					ruinCraft(user);
				}

				world.playSound(user, pos.add(0.5D, 0.5D, 0.5D), MineFantasySounds.ANVIL_SUCCEED, SoundCategory.NEUTRAL, 0.25F, rightClick ? 1.2F : 1.0F);
				float efficiency = ToolHelper.getCrafterEfficiency(user.getHeldItemMainhand()) * (rightClick ? 0.75F : 1.0F);

				if (user.swingProgress > 0 && user.swingProgress <= 1.0) {
					efficiency *= (0.5F - user.swingProgress);
				}

				progress += Math.max(0.2F, efficiency);
				if (progress >= progressMax) {
					craftItem(user);
				}
			} else {
				world.playSound(user, pos.add(0.5D, 0.5D, 0.5D), MineFantasySounds.ANVIL_FAIL, SoundCategory.NEUTRAL, 0.25F, 1.0F);
			}
			updateCraftingData();

			return true;
		}
		updateCraftingData();
		return false;
	}

	private void ruinCraft(EntityPlayer player) {
		if (!world.isRemote) {
			consumeResources(player);
			reassignHitValues();
			progress = progressMax = qualityBalance = 0;
		}
		world.playSound(null, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1.0F, 0.8F);
	}

	public boolean doesPlayerKnowCraft(EntityPlayer user) {
		return getRequiredResearch().isEmpty() || ResearchLogic.hasInfoUnlocked(user, getRequiredResearch());
	}

	private void craftItem(EntityPlayer lastHit) {
		if (this.canCraft()) {
			ItemStack result = modifySpecials(this.resultStack, lastHit);
			if (result.isEmpty()) {
				return;
			}

			if (result.getItem() instanceof ItemArmourMFR) {
				result = modifyArmour(result);
			}

			if (result.getMaxStackSize() == 1 && lastHit != null) {
				getNBT(result).setString(CRAFTED_BY_NAME_TAG, lastHit.getName());
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
						if (lastHit != null){
							EntityItem entityItem = lastHit.dropItem(result, false);
							if (entityItem != null){
								entityItem.setPosition(lastHit.posX, lastHit.posY, lastHit.posZ);
								entityItem.setNoPickupDelay();
							}
						}
					}
				} else {
					if (lastHit != null){
						EntityItem entityItem = lastHit.dropItem(result, false);
						if (entityItem != null){
							entityItem.setPosition(lastHit.posX, lastHit.posY, lastHit.posZ);
							entityItem.setNoPickupDelay();
						}
					}
				}
			}

			addXP(lastHit);
			consumeResources(lastHit);
		}
		onInventoryChanged();
		progress = 0;
		reassignHitValues();
		qualityBalance = 0;
	}

	private void addXP(EntityPlayer smith) {
		if (!world.isRemote){
			if (requiredSkill == Skill.NONE)
				return;
			if (requiredSkill == null){
				return;
			}

			float baseXP = this.progressMax / 10F;
			baseXP /= (1.0F + getAbsoluteBalance());

			requiredSkill.addXP(smith, (int) baseXP + 1);
		}
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

	private ItemStack modifySpecials(ItemStack result, EntityPlayer player) {
		boolean hasHeart = false;
		boolean isTool = result.getMaxStackSize() == 1 && result.isItemStackDamageable();

		Item dragonCraft = SpecialForging.getDragonCraft(result);

		if (dragonCraft != null) {
			// DRAGONFORGE
			for (int x = -4; x <= 4; x++) {
				for (int y = -4; y <= 4; y++) {
					for (int z = -4; z <= 4; z++) {
						TileEntity tile = world.getTileEntity(pos.add(x, y, z));
						if (player != null && ResearchLogic.getResearchCheck(player, MineFantasyKnowledgeList.smelt_dragonforged)
								&& tile instanceof TileEntityForge) {
							if (((TileEntityForge) tile).dragonHeartPower > 0) {
								hasHeart = true;
								((TileEntityForge) tile).dragonHeartPower = 0;
								world.createExplosion(null, pos.getX() + x, pos.getY() + y, pos.getZ() + z, 1F, false);
								PlayerTickHandler.spawnDragon(player);
								PlayerTickHandler.addDragonEnemyPts(player, 2);

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
			Item special = SpecialForging.getSpecialCraft(SpecialForging.getItemDesign(getInventory().getStackInSlot(getInventory().getSlots() - 1)), result);
			if (special != null) {
				NBTBase nbt = !(result.hasTagCompound()) ? null : result.getTagCompound().copy();
				result = new ItemStack(special, result.getCount(), result.getItemDamage());
				if (nbt != null) {
					result.setTagCompound((NBTTagCompound) nbt);
				}
			}
		}

		if (isPerfectItem() && !isMythicRecipe()) {
			this.setTrait(result, Trait.Inferior.getName(), false);
			if (CustomToolHelper.isMythic(result)) {
				result.getTagCompound().setBoolean(Trait.Unbreakable.getName(), true);
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
		for (int a = 0; a < getInventory().getSlots(); a++) {
			ItemStack item = getInventory().getStackInSlot(a);
			if (!item.isEmpty() && item.hasTagCompound()) {
				if (item.getTagCompound().hasKey(Trait.Inferior.getName())) {
					++totalItems;
					boolean inf = item.getTagCompound().getBoolean(Trait.Inferior.getName());
					totalPts += (inf ? -50F : 100F);
				}
			}
		}
		if (totalItems > 0 && totalPts > 0) {
			totalPts /= totalItems;
			ToolHelper.setQuality(result, ToolHelper.getQualityLevel(result) + totalPts);
			if (totalPts <= -85F) {
				this.setTrait(result, Trait.Inferior.getName(), true);
			}
			if (totalPts >= 80) {
				this.setTrait(result, Trait.Inferior.getName(), false);
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

	public void consumeResources(EntityPlayer player) {
		for (int slot = 0; slot < getInventory().getSlots() - 1; slot++) {
			ItemStack item = getInventory().getStackInSlot(slot);
			if (!item.isEmpty() && item.getItem() != null && !item.getItem().getContainerItem(item).isEmpty()) {
				if (item.getCount() == 1) {
					getInventory().setStackInSlot(slot, item.getItem().getContainerItem(item));
				} else {
					if (player != null){
						EntityItem entityItem = player.dropItem(item.getItem().getContainerItem(item), false);
						if (entityItem != null){
							entityItem.setPosition(player.posX, player.posY, player.posZ);
							entityItem.setNoPickupDelay();
						}
					}
					getInventory().extractItem(slot, 1, false);
				}
			} else {
				getInventory().extractItem(slot, 1, false);
			}
		}
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
				if (!heated.isEmpty()) {
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
	public ItemStack getResultStack() {
		if (syncAnvil == null || craftMatrix == null) {
			return ItemStack.EMPTY;
		}

		for (int a = 0; a < getInventory().getSlots() - 1; a++) {
			craftMatrix.setInventorySlotContents(a, getInventory().getStackInSlot(a));
		}

		ItemStack result = CraftingManagerAnvil.getInstance().findMatchingRecipe(this, craftMatrix, world);
		if (result == null || result.getItem() == Item.getItemFromBlock(Blocks.AIR)) { // right side is only necessary till we fix the empty AIR recipes
			result = ItemStack.EMPTY;
			resetProject();
		}
		return result;
	}

	public void updateCraftingData() {
		if (!world.isRemote) {
			ItemStack oldRecipe = resultStack;
			resultStack = getResultStack();

			if (!canCraft() && progress > 0) {
				progress = 0;
				reassignHitValues();
				qualityBalance = 0;
			}

			if (!resultStack.isEmpty() && !oldRecipe.isEmpty() && !resultStack.isItemEqual(oldRecipe)) {
				progress = 0;
				reassignHitValues();
				qualityBalance = 0;
			}

			if (progress > progressMax)
				progress = progressMax - 1;
		}
	}

	public boolean canCraft() {
		if (this.isMythicRecipe() && !this.isMythicReady()) {
			return false;
		}

		if (progressMax > 0 && !resultStack.isEmpty()) {
			return this.canFitResult(resultStack);
		}
		return false;
	}

	@Override
	public void setHotOutput(boolean i) {
		outputHot = i;
	}

	public void setContainer(ContainerAnvil container) {
		syncAnvil = container;
		craftMatrix = new AnvilCraftMatrix(this, syncAnvil, ShapelessAnvilRecipes.globalWidth, ShapelessAnvilRecipes.globalHeight);
	}

	public int getProgressBar(int i) {
		return (int) Math.ceil(i / progressMax * progress);
	}

	private boolean isMythicRecipe() {
		return false;// this.hammerTierRequired >= 6;
	}

	private boolean isMythicReady() {
		return true;
	}

	public String getTextureName() {
		return textureName;
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
			return ((float) total - (float) threshold) / (100F - threshold);
		}
		return 0F;
	}

	private boolean isPerfectItem() {
		int threshold = (int) (100F * getSuperThresholdPosition() / 2F);
		int total = (int) (100F * getAbsoluteBalance() - threshold);

		return total <= threshold;
	}

	private ItemStack damageItem(ItemStack item) {
		float currentDamage = getItemDamage();
		if (currentDamage > 0.5F) {
			setTrait(item, Trait.Inferior.getName(), true);
			float q = 100F * (0.75F - (currentDamage - 0.5F));
			ToolHelper.setQuality(item, Math.max(10F, q));
		}
		float damage = currentDamage * item.getMaxDamage();
		if (item.isItemStackDamageable()) {
			if (damage > 0) {
				item.setItemDamage((int) (damage));
				if (isMythicRecipe()) {
					setTrait(item, Trait.Inferior.getName(), true);
				}
			} else if (isMythicRecipe()) {
				setTrait(item, Trait.Unbreakable.getName(), true);
			}
		}
		return item;
	}

	public void setTier(int tier) {
		this.tier = tier;
	}

	public void setTextureName(String textureName) {
		this.textureName = textureName;
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
		if (tier < requiredAnvilTier) {
			modifier *= 0.25F;
		}

		float baseThreshold = world.getDifficulty().getDifficultyId() >= 2 ? 7.5F : 10F;
		thresholdPosition = (isMythicRecipe() ? 0.05F : baseThreshold / 100F) * modifier;
	}

	public void upset(EntityPlayer user) {
		if (this.progress > 0 && this.progressMax > 0) {
			world.playSound(user, pos.add(0.5D, 0.5D, 0.5D), MineFantasySounds.ANVIL_SUCCEED, SoundCategory.NEUTRAL, 0.25F, 0.75F);
			progress -= (progressMax / 10F);
			if (progress < 0) {
				ruinCraft(user);
			}
		}
	}

	public void reassignHitValues() {
		if (!world.isRemote) {
			leftHit = 0.1F + (0.01F * world.rand.nextInt(11));
			rightHit = 0.1F + (0.01F * world.rand.nextInt(11));
		}
	}

	@Override
	public void setRequiredToolType(String toolType) {
		this.requiredToolType = Tool.fromName(toolType);
	}

	@Override
	public void setRequiredResearch(String research) {
		this.requiredResearch = research;
	}

	@Override
	public void setRequiredSkill(Skill skill) {
		this.requiredSkill = skill;
	}

	@Override
	public void setProgressMax(int i) {
		progressMax = i;
	}

	@Override
	public void setRequiredHammerTier(int i) {
		requiredHammerTier = i;
	}

	@Override
	public void setRequiredAnvilTier(int i) {
		requiredAnvilTier = i;
	}

	public int getTier() {
		return tier;
	}

	public String getResultName() {
		return resultStack.isEmpty() || resultStack.getItem() == Item.getItemFromBlock(Blocks.AIR) ? I18n.format("gui.no_project_set") : resultStack.getDisplayName();
	}

	@Override
	public int getRequiredHammerTier() { return requiredHammerTier; }

	@Override
	public int getRequiredAnvilTier() {
		return requiredAnvilTier;
	}

	public String getRequiredResearch() {
		return requiredResearch;
	}

	public Tool getRequiredToolType() {
		return requiredToolType;
	}

	public int getToolTierNeeded() {
		return this.requiredHammerTier;
	}

	private void resetProject() {
		resultStack = ItemStack.EMPTY;
		requiredHammerTier = 0;
		requiredAnvilTier = 0;
		progress = 0;
		progressMax = 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		tier = nbt.getInteger(TIER_TAG);
		inventory.deserializeNBT(nbt.getCompoundTag(INVENTORY_TAG));
		progress = nbt.getFloat(PROGRESS_TAG);
		progressMax = nbt.getFloat(PROGRESS_MAX_TAG);
		resultStack = new ItemStack(nbt.getCompoundTag(RESULT_STACK_TAG));
		requiredAnvilTier = nbt.getInteger(REQUIRED_ANVIL_TIER_TAG);
		requiredHammerTier = nbt.getInteger(REQUIRED_HAMMER_TIER_TAG);
		requiredToolType = Tool.fromName(nbt.getString(TOOL_TYPE_REQUIRED_TAG));
		requiredResearch = nbt.getString(RESEARCH_REQUIRED_TAG);
		textureName = nbt.getString(TEXTURE_NAME_TAG);
		outputHot = nbt.getBoolean(OUTPUT_HOT_TAG);
		qualityBalance = nbt.getFloat(QUALITY_TAG);
		leftHit = nbt.getFloat(LEFT_HIT_TAG);
		rightHit = nbt.getFloat(RIGHT_HIT_TAG);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger(TIER_TAG, tier);
		nbt.setTag(INVENTORY_TAG, inventory.serializeNBT());
		nbt.setFloat(PROGRESS_TAG, progress);
		nbt.setFloat(PROGRESS_MAX_TAG, progressMax);
		nbt.setTag(RESULT_STACK_TAG, resultStack.writeToNBT(new NBTTagCompound()));
		nbt.setInteger(REQUIRED_ANVIL_TIER_TAG, requiredAnvilTier);
		nbt.setInteger(REQUIRED_HAMMER_TIER_TAG, requiredHammerTier);
		nbt.setString(TOOL_TYPE_REQUIRED_TAG, requiredToolType.getName());
		nbt.setString(RESEARCH_REQUIRED_TAG, requiredResearch);
		nbt.setString(TEXTURE_NAME_TAG, textureName);
		nbt.setBoolean(OUTPUT_HOT_TAG, outputHot);
		nbt.setFloat(QUALITY_TAG, qualityBalance);
		nbt.setFloat(LEFT_HIT_TAG, leftHit);
		nbt.setFloat(RIGHT_HIT_TAG, rightHit);
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
