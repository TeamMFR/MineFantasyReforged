package minefantasy.mfr.tile;

import minefantasy.mfr.api.crafting.IBasicMetre;
import minefantasy.mfr.container.ContainerBase;
import minefantasy.mfr.container.ContainerResearchBench;
import minefantasy.mfr.data.PlayerData;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.init.MineFantasySounds;
import minefantasy.mfr.mechanics.knowledge.IArtefact;
import minefantasy.mfr.network.NetworkHandler;
import minefantasy.mfr.network.ResearchTablePacket;
import minefantasy.mfr.registry.knowledge.KnowledgeManagerResearch;
import minefantasy.mfr.registry.knowledge.ResearchBase;
import minefantasy.mfr.registry.knowledge.ResearchLogic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TileEntityResearchBench extends TileEntityBase implements IBasicMetre {
	public float progress;
	public float maxProgress;
	public int researchID = -1;
	private final Random rand = new Random();
	private int ticksExisted;

	public final ItemStackHandler inventory = createInventory();

	@Override
	protected ItemStackHandler createInventory() {
		return new ItemStackHandler(1);
	}

	@Override
	public ItemStackHandler getInventory() {
		return this.inventory;
	}

	@Override
	public ContainerBase createContainer(EntityPlayer player) {
		return new ContainerResearchBench(player, this);
	}

	@Override
	protected int getGuiId() {
		return NetworkHandler.GUI_RESEARCH_BENCH;
	}

	public static List<ResearchBase> getResearches(ItemStack stack) {
		if (stack.isEmpty()) {
			return Collections.emptyList();
		}

		List<ResearchBase> researches = KnowledgeManagerResearch.getResearchesByItemStack(stack);
		return !researches.isEmpty() ? researches : Collections.emptyList();
	}

	public static boolean canAccept(ItemStack stack) {
		List<ResearchBase> info = getResearches(stack);
		return !info.isEmpty();
	}

	public boolean interact(EntityPlayer user) {
		if (world.isRemote) {
			return true;
		}
		syncData();

		ItemStack stackInSlot = getInventory().getStackInSlot(0);

		if (stackInSlot.isEmpty()) {
			return false;
		}

		List<ResearchBase> researches = getResearches(stackInSlot);
		int result = canResearch(user, researches);

		if (!researches.isEmpty()) {
			switch (result) {
				case -1:
					if (!user.world.isRemote){
						user.sendMessage(new TextComponentTranslation("research.noskill"));
					}
					return true;
				case -2:
					if (!user.world.isRemote){
						user.sendMessage(new TextComponentTranslation("research.noresearch"));
					}
					return true;
				case 1:
					maxProgress = getMaxTime();
					if (maxProgress > 0) {
						addProgress(user);
						if (progress >= maxProgress) {
							addResearch(researches, user);
							progress = 0;
						}
						return true;
					}
				default:
					if (!user.world.isRemote){
						user.sendMessage(new TextComponentTranslation("research.null"));
					}
					progress = 0;
			}
		}

		return !stackInSlot.isEmpty();
	}

	private void addResearch(List<ResearchBase> researches, EntityPlayer user) {
		for (ResearchBase research: researches) {
			if (research != null && !ResearchLogic.alreadyUsedArtifact(user, research, getInventory().getStackInSlot(0))
					&& ResearchLogic.canResearch(user, research) && research.hasSkillsUnlocked(user)) {
				int researchedArtifactCount = ResearchLogic.useArtifact(getInventory().getStackInSlot(0), research, user);
				if (user instanceof EntityPlayerMP) {
					PlayerData.get(user).sync();
				}
				world.playSound(null, pos, MineFantasySounds.UPDATE_RESEARCH, SoundCategory.BLOCKS, 1.0F, 1.0F);
				if (!user.world.isRemote) {
					TextComponentTranslation name = new TextComponentTranslation("knowledge." + research.getName());
					if (researchedArtifactCount == -1) {
						user.sendMessage(new TextComponentTranslation("research.finishResearch", name));
					} else {
						user.sendMessage(new TextComponentTranslation("research.addArtefact", name, researchedArtifactCount, research.getArtifacts().size()));
					}
				}
				return;
			}

		}
	}

	// 0 nothing, -1 for no skill, -2 for missing parent research, 1 for yes
	private int canResearch(EntityPlayer user, List<ResearchBase> researches) {
		if (researches.isEmpty()) {
			return 0;
		}
		int result = 0;

		for (ResearchBase research : researches) {
			if (research != null && !ResearchLogic.alreadyUsedArtifact(user, research, getInventory().getStackInSlot(0))) {
				if (ResearchLogic.canResearch(user, research) && research.hasSkillsUnlocked(user)) {
					return 1;
				} else if (!ResearchLogic.hasResearchUnlocked(user, research) && !research.hasSkillsUnlocked(user)) {
					result = -1;
				} else if (!ResearchLogic.hasResearchUnlocked(user, research) && research.hasSkillsUnlocked(user)) {
					result = -2;
				}
			}
		}
		return result;
	}

	private float getMaxTime() {
		int time = 10;
		if (!getInventory().getStackInSlot(0).isEmpty() && getInventory().getStackInSlot(0).getItem() instanceof IArtefact) {
			return ((IArtefact) getInventory().getStackInSlot(0).getItem()).getStudyTime(getInventory().getStackInSlot(0));
		}
		return !getResearches(getInventory().getStackInSlot(0)).isEmpty() ? time : 0;
	}

	private void addProgress(EntityPlayer user) {
		ItemStack held = user.getHeldItemMainhand();
		if (!held.isEmpty() && (held.getItem() == MineFantasyItems.TALISMAN_LESSER || held.getItem() == MineFantasyItems.TALISMAN_GREATER)) {
			progress = maxProgress;
			if (!user.capabilities.isCreativeMode && held.getItem() == MineFantasyItems.TALISMAN_LESSER) {
				held.shrink(1);
				if (held.getCount() <= 0) {
					user.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
				}
			}
			return;
		}
		float efficiency = 1.0F;
		if (user.swingProgress > 0) {
			efficiency *= Math.max(0F, 1.0F - user.swingProgress);
		}
		world.playSound(null, pos, MineFantasySounds.FLIP_PAGE, SoundCategory.BLOCKS, 1.0F, rand.nextFloat() * 0.4F + 0.8F);
		efficiency *= getEnvironmentBoost();
		progress += efficiency;
		syncData();
	}

	private float getEnvironmentBoost() {
		int books = 0;
		for (int x = -8; x <= 8; x++) {
			for (int y = -8; y <= 8; y++) {
				for (int z = -8; z <= 8; z++) {
					if (world.getBlockState(pos.add(x, y, z)).getBlock() == Blocks.BOOKSHELF) {
						++books;
					}
				}
			}
		}
		return 1.0F + (0.1F * books);
	}

	@Override
	public void markDirty() {
		syncData();
		sendUpdates();
	}

	public void syncData() {
		if (world.isRemote)
			return;
		NetworkHandler.sendToAllTrackingChunk(world, pos.getX() >> 4, pos.getZ() >> 4, new ResearchTablePacket(this));
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		return canAccept(item);
	}

	@Override
	public int getMetreScale(int size) {
		if (maxProgress <= 0) {
			return 0;
		}
		return (int) Math.min(size, Math.ceil(size / maxProgress * progress));
	}

	@Override
	public boolean shouldShowMetre() {
		return maxProgress > 0;
	}

	@Override
	public String getLocalisedName() {
		return "";
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		ticksExisted = nbt.getInteger("ticksExisted");
		researchID = nbt.getInteger("researchID");
		progress = nbt.getFloat("progress");
		maxProgress = nbt.getFloat("maxProgress");

		inventory.deserializeNBT(nbt.getCompoundTag("inventory"));
	}

	@Nonnull
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setInteger("researchID", researchID);
		nbt.setInteger("ticksExisted", ticksExisted);
		nbt.setFloat("progress", progress);
		nbt.setFloat("maxProgress", maxProgress);

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
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
		}
		return super.getCapability(capability, facing);
	}
}
