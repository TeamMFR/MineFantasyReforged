package minefantasy.mfr.tile;

import minefantasy.mfr.block.BlockEngineerTanner;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.container.ContainerBase;
import minefantasy.mfr.container.ContainerTanner;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.mechanics.RPGElements;
import minefantasy.mfr.recipe.TanningRecipe;
import minefantasy.mfr.util.ToolHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class TileEntityTanningRack extends TileEntityBase implements ITickable {
	public float progress;
	public float maxProgress;
	public String tex = "";
	public int tier = 0;
	public String toolType = "knife";
	public float acTime;
	private Random rand = new Random();
	private int ticksExisted;

	public TileEntityTanningRack() {

	}

	@Override
	public boolean hasFastRenderer() {
		return true;
	}

	public final ItemStackHandler inventory = createInventory();

	@Override
	protected ItemStackHandler createInventory() {
		return new ItemStackHandler(2);
	}

	@Override
	public ItemStackHandler getInventory() {
		return this.inventory;
	}

	@Override
	public ContainerBase createContainer(EntityPlayer player) {
		return new ContainerTanner(player.inventory, this);
	}

	@Override
	protected int getGuiId() {
		return 0;
	}

	@Override
	public void update() {
		++ticksExisted;

		if (ticksExisted == 20 || ticksExisted % 120 == 0) {
			sendUpdates();
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
		createContainer(player).detectAndSendChanges();

		ItemStack held = player.getHeldItemMainhand();

		// Interaction
		if (!getInventory().getStackInSlot(1).isEmpty() && (leverPull || ToolHelper.getToolTypeFromStack(held).getName().equalsIgnoreCase(toolType))) {
			if (leverPull || ToolHelper.getCrafterTier(held) >= tier) {
				if (!leverPull) {
					held.damageItem(1, player);
					if (held.getItemDamage() >= held.getMaxDamage()) {
						player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
					}
				} else {
					world.playSound(player, pos.add(0.5D, 0.5D, 0.5D), SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.AMBIENT, 0.75F, 0.85F);
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
					world.playSound(player, pos.add(0.5D, 0.5D, 0.5D), SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.AMBIENT, 1.0F, 1.0F);
				} else {
					world.playSound(player, pos.add(0.5D, 0.5D, 0.5D), SoundEvents.BLOCK_CLOTH_BREAK, SoundCategory.AMBIENT, 1.0F, 1.0F);
				}
				if (progress >= maxProgress) {
					if (RPGElements.isSystemActive) {
						Skill.ARTISANRY.addXP(player, 1);
					}
					progress = 0;
					int ss = !getInventory().getStackInSlot(0).isEmpty() ? getInventory().getStackInSlot(0).getCount() : 1;
					ItemStack out = getInventory().getStackInSlot(1).copy();
					out.setCount(out.getCount() * ss);
					getInventory().setStackInSlot(0, out);
					updateRecipe();
					if (isShabbyRack() && rand.nextInt(10) == 0 && !world.isRemote) {
						for (int a = 0; a < rand.nextInt(10); a++) {
							ItemStack plank = MineFantasyItems.TIMBER.construct("ScrapWood");
							world.playSound(player, pos.add(0.5D, 0.5D, 0.5D), SoundEvents.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, SoundCategory.AMBIENT, 1.0F, 1.0F);
							dropItem(plank);
						}
						world.setBlockToAir(pos);
						return true;
					}
				}
			}
			return true;
		}
		if (!leftClick && (ToolHelper.getCrafterToolName(held).equalsIgnoreCase("nothing")
				|| ToolHelper.getCrafterToolName(held).equalsIgnoreCase("hands"))) {
			// Item placement
			ItemStack item = getInventory().getStackInSlot(0);
			if (item.isEmpty()) {
				if (!held.isEmpty() && !(held.getItem() instanceof ItemBlock) && TanningRecipe.getRecipe(held) != null) {
					ItemStack item2 = held.copy();
					item2.setCount(1);
					getInventory().setStackInSlot(0, item2);
					tryDecrMainItem(player);
					updateRecipe();
					world.playSound(player, pos.add(0.5D, 0.5D, 0.5D), SoundEvents.ENTITY_HORSE_SADDLE, SoundCategory.AMBIENT, 1.0F, 1.0F);
					return true;
				}
			} else {
				if (!player.inventory.addItemStackToInventory(item)) {
					player.entityDropItem(item, 0.0F);
				}
				getInventory().setStackInSlot(0, ItemStack.EMPTY);
				updateRecipe();
				world.playSound(player, pos.add(0.5D, 0.5D, 0.5D), SoundEvents.ENTITY_HORSE_SADDLE, SoundCategory.AMBIENT, 1.0F, 1.0F);
				return true;
			}
		}
		return false;
	}

	public boolean isAutomated() {
		if (world == null) {
			return tex.equalsIgnoreCase("metal");
		}
		return world.getBlockState(pos).getBlock() instanceof BlockEngineerTanner;
	}

	private void tryDecrMainItem(EntityPlayer player) {
		int held = player.inventory.currentItem;
		if (held >= 0 && held < 9) {
			player.inventory.decrStackSize(held, 1);
		}
	}

	public void updateRecipe() {
		TanningRecipe recipe = TanningRecipe.getRecipe(getInventory().getStackInSlot(0));
		if (recipe == null) {
			getInventory().setStackInSlot(1, ItemStack.EMPTY);
			progress = maxProgress = tier = 0;
		} else {
			getInventory().setStackInSlot(1, recipe.output);
			tier = recipe.tier;
			maxProgress = recipe.time;
			toolType = recipe.toolType;
		}
		progress = 0;
		sendUpdates();
	}

	public boolean doesPlayerKnowCraft() {
		return true;
	}

	public int getProgressBar(int i) {
		if (maxProgress <= 0)
			return 0;
		return (int) (i / maxProgress * progress);
	}

	public String getResultName() {
		if (!getInventory().getStackInSlot(1).isEmpty()) {
			return getInventory().getStackInSlot(1).getDisplayName();
		}
		return "";
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		return false;
	}

	private boolean isShabbyRack() {
		return world.getBlockState(pos).getBlock() == MineFantasyBlocks.TANNER;
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

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		acTime = nbt.getFloat("acTime");
		tex = nbt.getString("tex");
		tier = nbt.getInteger("tier");
		progress = nbt.getFloat("Progress");
		maxProgress = nbt.getFloat("maxProgress");
		toolType = nbt.getString("toolType");

		inventory.deserializeNBT(nbt.getCompoundTag("inventory"));
	}

	@Nonnull
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setFloat("acTime", acTime);
		nbt.setString("tex", tex);
		nbt.setInteger("tier", tier);
		nbt.setFloat("Progress", progress);
		nbt.setFloat("maxProgress", maxProgress);
		nbt.setString("toolType", toolType);
		nbt.setTag("inventory", inventory.serializeNBT());

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
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(getInventory());
		}
		return super.getCapability(capability, facing);
	}
}
