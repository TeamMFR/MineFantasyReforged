package minefantasy.mfr.tile;

import minefantasy.mfr.api.crafting.MineFantasyFuels;
import minefantasy.mfr.api.refine.SmokeMechanics;
import minefantasy.mfr.block.BlockBloomery;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.constants.Tool;
import minefantasy.mfr.container.ContainerBase;
import minefantasy.mfr.container.ContainerBloomery;
import minefantasy.mfr.init.MineFantasyKnowledgeList;
import minefantasy.mfr.init.MineFantasySounds;
import minefantasy.mfr.item.ItemHeated;
import minefantasy.mfr.mechanics.RPGElements;
import minefantasy.mfr.mechanics.knowledge.ResearchLogic;
import minefantasy.mfr.network.NetworkHandler;
import minefantasy.mfr.recipe.refine.BloomRecipe;
import minefantasy.mfr.tile.blastfurnace.TileEntityBlastChamber;
import minefantasy.mfr.util.InventoryUtils;
import minefantasy.mfr.util.MFRLogUtil;
import minefantasy.mfr.util.ToolHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class TileEntityBloomery extends TileEntityBase implements ITickable {
	private int ticksExisted;
	public float progress, progressMax;
	public boolean hasBloom;
	public boolean isActive;
	private Random rand = new Random();

	private int OUT_SLOT = 2;

	public final ItemStackHandler inventory = createInventory();

	@Override
	protected ItemStackHandler createInventory() {
		return new ItemStackHandler(3) {
			@Override
			protected void onContentsChanged(int slot) {
				IBlockState iblockstate = world.getBlockState(pos);
				world.setBlockState(pos, iblockstate.withProperty(BlockBloomery.BLOOM, hasBloom()));
				markDirty();
			}
		};
	}

	@Override
	public ItemStackHandler getInventory() {
		return this.inventory;
	}

	@Override
	public ContainerBase createContainer(EntityPlayer player) {
		return new ContainerBloomery(player.inventory, this);
	}

	@Override
	protected int getGuiId() {
		return NetworkHandler.GUI_BLOOMERY;
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}

	public static boolean isInput(ItemStack input) {
		return !getResult(input).isEmpty();
	}

	private static ItemStack getResult(ItemStack input) {
		return BloomRecipe.getSmeltingResult(input);
	}

	public ItemStack getResult() {
		ItemStack input = inventory.getStackInSlot(0);
		ItemStack coal = inventory.getStackInSlot(1);

		if (hasBloom())
			return ItemStack.EMPTY;// Cannot smelt if a bloom exists
		if (input.isEmpty() || coal.isEmpty())
			return ItemStack.EMPTY;// Needs input

		if (!hasEnoughCarbon(input, coal)) {
			return ItemStack.EMPTY;
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

		if (isActive && progressMax > 0) {
			if (!world.canBlockSeeSky(pos.add(0, 1, 0))) {
				progressMax = progress = 0;
				isActive = false;
				return;
			}
			ticksExisted++;
			if (!world.isRemote) {
				++progress;
				if (progress >= progressMax) {
					smeltItem();
				}
				if (rand.nextInt(4) == 0) {
					SmokeMechanics.spawnSmoke(world, pos, 1);
				}
				if (ticksExisted % 20 == 0) {
					sendUpdates();
				}
			}
		}
	}

	/**
	 * Light the bloomery, starting the process.
	 *
	 * @return true if it can smelt
	 */
	public boolean light(EntityPlayer user) {
		ItemStack res = getResult();
		if (world.canBlockSeeSky(pos.add(0, 1, 0)) && res != null && !isActive) {
			if (!world.isRemote) {
				if (res.getItem() == Items.IRON_INGOT && !ResearchLogic.getResearchCheck(user, MineFantasyKnowledgeList.smelt_iron)) {
					return false;
				}
				isActive = true;
				progressMax = getInventory().getStackInSlot(0).getCount() * getTime(getInventory().getStackInSlot(0));// 15s per item
				world.playSound(user, pos.add(0.5D, 0.5D, +0.5D), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.AMBIENT, 1.0F, 1.0F);
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
		if (!result.isEmpty()) {
			ItemStack result2 = result.copy();
			result2.setCount(getInventory().getStackInSlot(0).getCount());
			getInventory().setStackInSlot(0, ItemStack.EMPTY);
			getInventory().setStackInSlot(1, ItemStack.EMPTY);
			getInventory().setStackInSlot(OUT_SLOT, result2);
		}
		isActive = false;
		progress = progressMax = 0;
	}

	public boolean tryHammer(EntityPlayer user) {
		if (world.getBlockState(pos.add(0, 1, 0)).getMaterial().isSolid()) {
			return false;
		}
		ItemStack held = user.getHeldItemMainhand();
		if (!hasBloom() || isActive) {
			return false;
		}

		Tool tool = ToolHelper.getToolTypeFromStack(held);
		float pwr = ToolHelper.getCrafterEfficiency(held);
		if (tool == Tool.HAMMER || tool == Tool.HEAVY_HAMMER) {
			if (user.world.isRemote)
				return true;

			held.damageItem(1, user);
			if (held.getItemDamage() >= held.getMaxDamage()) {
				user.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
			}

			if (rand.nextFloat() * 10F < pwr) {
				ItemStack drop = inventory.getStackInSlot(OUT_SLOT).copy();
				inventory.extractItem(OUT_SLOT, 1, false);
				if (inventory.getStackInSlot(OUT_SLOT).getCount() <= 0) {
					inventory.setStackInSlot(OUT_SLOT, ItemStack.EMPTY);
				}
				if (RPGElements.isSystemActive && RPGElements.getLevel(user, Skill.ARTISANRY) <= 20)// Only gain xp
				// up to level
				// 20
				{
					Skill.ARTISANRY.addXP(user, 1);
				}
				drop.setCount(1);
				drop = ItemHeated.createHotItem(drop, 1200);
				entityDropItem(world, pos, drop);
			}
			world.playSound(null, pos, MineFantasySounds.ANVIL_SUCCEED, SoundCategory.AMBIENT, 0.25F, 1.0F);

			return true;
		}
		return false;
	}

	public EntityItem entityDropItem(World world, BlockPos pos, ItemStack item) {
		if (item.getCount() != 0 && !item.isEmpty()) {
			EntityItem entityitem = new EntityItem(world, pos.getX() + 0.5D, pos.getY() + 1.25F, pos.getZ() + 0.5D, item);
			entityitem.setPickupDelay(10);
			entityitem.setCustomNameTag("bloomery");
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
		return !getInventory().getStackInSlot(OUT_SLOT).isEmpty();
	}

	public boolean hasNoBloom() {
		return getInventory().getStackInSlot(OUT_SLOT).isEmpty();
	}

	public boolean isItemValidForSlot(int slot, ItemStack item) {
		if (!item.isEmpty() && TileEntityBlastChamber.isCarbon(item)) {
			return slot == 1;
		}
		if (!item.isEmpty() && !getResult(item).isEmpty()) {
			return slot == 0;
		}
		return false;
	}

	@Override
	public void onBlockBreak() {
		if (!inventory.getStackInSlot(inventory.getSlots() - 1).isEmpty() && !(inventory.getStackInSlot(inventory.getSlots() - 1).getItem() instanceof ItemBlock)) {
			inventory.setStackInSlot(OUT_SLOT, ItemStack.EMPTY);
		}
		InventoryUtils.dropItemsInWorld(world, inventory, pos);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		inventory.deserializeNBT(nbt.getCompoundTag("inventory"));

		progress = nbt.getFloat("Progress");
		progressMax = nbt.getFloat("ProgressMax");
		isActive = nbt.getBoolean("isActive");
		hasBloom = nbt.getBoolean("hasBloom");
	}

	@Nonnull
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setTag("inventory", inventory.serializeNBT());

		nbt.setFloat("Progress", progress);
		nbt.setFloat("ProgressMax", progressMax);
		nbt.setBoolean("isActive", isActive);
		nbt.setBoolean("hasBloom", hasBloom);
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
