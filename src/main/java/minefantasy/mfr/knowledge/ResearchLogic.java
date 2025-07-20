package minefantasy.mfr.knowledge;

import minefantasy.mfr.config.ConfigResearch;
import minefantasy.mfr.data.IStoredVariable;
import minefantasy.mfr.data.Persistence;
import minefantasy.mfr.data.PlayerData;
import minefantasy.mfr.util.NbtUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResearchLogic {
	public static final IStoredVariable<NBTTagCompound> KNOWLEDGE_STATS_KEY = IStoredVariable.StoredVariable
			.ofNBT("knowledgeStats", Persistence.ALWAYS)
			.setSynced();
	public static final String KNOWLEDGE_LAYER_NBT = "layer";
	public static final String RESEARCH_ARTIFACT_INDEXES_NBT = "researchArtifactIndexes";
	public static final String IS_UNLOCKED_NBT = "isUnlocked";

	public static void init() {
		PlayerData.registerStoredVariables(KNOWLEDGE_STATS_KEY);
	}

	/**
	 * Used to allow creative bypass without breaking research unlocking
	 * @param player 	the Player to check for research
	 * @param research 	the research to check if the player has it
	 * @return 			if the player has the specified research or bypass if Player is in Creative
	 */
	public static boolean getResearchCheck(EntityPlayer player, ResearchBase research) {
		if (player.capabilities.isCreativeMode && !research.isPerk()) {
			return true;
		}
		if (research == KnowledgeManagerResearch.NONE) {
			return true;
		}

		return hasResearchUnlocked(player, research);
	}

	public static int useArtifact(ItemStack itemStack, ResearchBase research, EntityPlayer player) {
		int researchedArtifactCount = setResearchedArtifactIndexes(player, research, itemStack);
		if (researchedArtifactCount >= research.getRequiredArtifactCount() && tryUnlock(player, research)) {
			syncData(player);
			return -1;
		}
		return researchedArtifactCount;
	}

	/**
	 *
	 * @param player	The Player to check the Research for
	 * @param research	The Research to check if the Player can research it
	 * @return			If the Player can research the Research
	 */
	public static boolean canResearch(EntityPlayer player, ResearchBase research) {
		if (research.isPreUnlocked() || !canUnlockResearch(player, research)) {
			return false;
		}

		return !isResearchUnlocked(player, research);
	}

	/**
	 *
	 * @param player	The Player to check for the Research
	 * @param research	The Research to check if it can be unlocked
	 * @return			Returns true if the parent has been unlocked, or there is no parent
	 */
	public static boolean canUnlockResearch(EntityPlayer player, ResearchBase research) {
		return research.getParentResearches().isEmpty() ||
				research.getParentResearches()
				.stream()
				.allMatch(parentResearch -> hasResearchUnlocked(player, parentResearch));
	}

	/**
	 *
	 * @param player	The Player to check for the Research
	 * @param research	The Research to check if the Player has it unlocked
	 * @return			If the Research is unlocked
	 */
	public static boolean hasResearchUnlocked(EntityPlayer player, ResearchBase research) {
		if (research == KnowledgeManagerResearch.NONE) {
			return true;
		}

		if (player != null) {
			if (research.isPreUnlocked()) {
				return research.getParentResearches().isEmpty() ||
						research.getParentResearches()
								.stream()
								.allMatch(parentResearch -> hasResearchUnlocked(player, parentResearch));
			}

			return isResearchUnlocked(player, research);
		}
		return false;
	}

	/**
	 *
	 * @param player	The Player to check if they have researched the Stack
	 * @param research	The Research for which the Stack artifact is being checked
	 * @param stack		The Stack for the artifact being checked
	 * @return			If the Player has already researched the Item as an artifact for the Research
	 */
	public static boolean alreadyUsedArtifact(EntityPlayer player, ResearchBase research, ItemStack stack) {
		if (isResearchUnlocked(player, research)) {
			return true;
		}
		List<Integer> researchedArtifactIndexes = getResearchedArtifactIndexes(player, research);
		if (!researchedArtifactIndexes.isEmpty()) {
			for (int i : researchedArtifactIndexes) {
				Ingredient artifact = research.getArtifacts().get(i);
				if (artifact.apply(stack)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean tryUnlock(EntityPlayer player, ResearchBase research) {
		if (research.isPreUnlocked() || !canUnlockResearch(player, research)) {
			return false;
		}
		NBTTagCompound nbt = getNBT(player);
		String researchKeyName = getResearchKeyName(research);
		if (nbt.hasKey(researchKeyName)) {
			NBTTagCompound researchNbt = nbt.getCompoundTag(researchKeyName);
			researchNbt.setBoolean(IS_UNLOCKED_NBT, true);
			return true;
		}

		return false;
	}
	public static boolean checkAllParentResearches(EntityPlayer player, ResearchBase research) {
		List<ResearchBase> parentResearches = research.getParentResearches();
		if (parentResearches.isEmpty()) {
			return true;
		}

		boolean allResearchesUnlocked = true;
		for (ResearchBase parentResearch : parentResearches) {
			if (hasResearchUnlocked(player, parentResearch)) {
				allResearchesUnlocked = checkAllParentResearches(player, parentResearch);
			}
			else {
				allResearchesUnlocked = false;
			}
		}

		return allResearchesUnlocked;
	}

	public static void syncData(EntityPlayer player) {
		if (!player.world.isRemote) {
			PlayerData.get(player).sync();
		}
	}

	private static String getResearchKeyName(ResearchBase research) {
		return "research_" + research.getRegistryName();
	}


	private static boolean isResearchUnlocked(EntityPlayer player, ResearchBase research) {
		NBTTagCompound nbt = getNBT(player);
		return isResearchUnlocked(nbt, research);
	}

	private static boolean isResearchUnlocked(NBTTagCompound nbt, ResearchBase research) {
		String researchKeyName = getResearchKeyName(research);
		if (nbt.hasKey(researchKeyName)) {
			NBTTagCompound researchNbt = nbt.getCompoundTag(researchKeyName);
			return researchNbt.getBoolean(IS_UNLOCKED_NBT);
		}
		return false;
	}

	private static void setResearchUnlocked(EntityPlayer player, ResearchBase research, boolean isUnlocked) {
		NBTTagCompound nbt = getNBT(player);
		setResearchUnlocked(nbt, research, isUnlocked);
	}

	private static void setResearchUnlocked(NBTTagCompound nbt, ResearchBase research, boolean isUnlocked) {
		String researchKeyName = getResearchKeyName(research);
		if (nbt.hasKey(researchKeyName)) {
			NBTTagCompound researchNbt = nbt.getCompoundTag(researchKeyName);
			researchNbt.setBoolean(IS_UNLOCKED_NBT, isUnlocked);
		}
	}

	public static int getResearchedArtifactCount(EntityPlayer player, ResearchBase research) {
		NBTTagCompound nbt = getNBT(player);
		return getResearchedArtifactIndexes(nbt, research).size();
	}

	private static List<Integer> getResearchedArtifactIndexes(EntityPlayer player, ResearchBase research) {
		NBTTagCompound nbt = getNBT(player);
		return  getResearchedArtifactIndexes(nbt, research);
	}

	private static List<Integer> getResearchedArtifactIndexes(NBTTagCompound nbt, ResearchBase research) {
		String researchKeyName = getResearchKeyName(research);

		if (nbt.hasKey(researchKeyName)) {
			NBTTagCompound researchNbt = nbt.getCompoundTag(researchKeyName);
			return NbtUtils.mapNbtTagListToList(researchNbt.getTagList(RESEARCH_ARTIFACT_INDEXES_NBT, 3), Integer.class);
		}
		return Collections.emptyList();
	}

	private static int setResearchedArtifactIndexes(EntityPlayer player, ResearchBase research, ItemStack stack) {
		NBTTagCompound nbt = getNBT(player);
		return setResearchedArtifactIndexes(nbt, research, stack);
	}

	private static int setResearchedArtifactIndexes(NBTTagCompound nbt, ResearchBase research, ItemStack stack) {
		String researchKeyName = getResearchKeyName(research);
		Integer indexForStack = research.getArtifactIndexForStack(stack);
		List<Integer> researchedArtifactIndexes = new ArrayList<>();
		if (nbt.hasKey(researchKeyName)) {
			NBTTagCompound researchNbt = nbt.getCompoundTag(researchKeyName);

			researchedArtifactIndexes = NbtUtils
					.mapNbtTagListToList(researchNbt.getTagList(RESEARCH_ARTIFACT_INDEXES_NBT, 3), Integer.class);
			if (indexForStack != null && !researchedArtifactIndexes.contains(indexForStack)) {
				researchedArtifactIndexes.add(indexForStack);
			}

			researchNbt.setTag(RESEARCH_ARTIFACT_INDEXES_NBT, NbtUtils.mapListToNbtTagList(researchedArtifactIndexes, 3));
		}
		else {
			NBTTagCompound researchNbt = new NBTTagCompound();

			researchedArtifactIndexes.add(indexForStack);
			researchNbt.setTag(RESEARCH_ARTIFACT_INDEXES_NBT, NbtUtils.mapListToNbtTagList(researchedArtifactIndexes, 3));
			researchNbt.setBoolean(IS_UNLOCKED_NBT, false);

			nbt.setTag(researchKeyName, researchNbt);
		}
		return researchedArtifactIndexes.size();
	}

	private static NBTTagCompound getNBT(EntityPlayer player) {
		if (player != null) {
			PlayerData data = PlayerData.get(player);
			if (data != null) {
				NBTTagCompound nbt = data.getVariable(KNOWLEDGE_STATS_KEY);
				if (nbt == null) {
					NBTTagCompound tag = new NBTTagCompound();
					tag.setInteger(KNOWLEDGE_LAYER_NBT, ConfigResearch.KNOWLEDGE_LAYER);
					data.setVariable(KNOWLEDGE_STATS_KEY, tag);
				}
				else {
					if (nbt.getInteger(KNOWLEDGE_LAYER_NBT) != ConfigResearch.KNOWLEDGE_LAYER) {
						data.setVariable(KNOWLEDGE_STATS_KEY, null);

						NBTTagCompound tag = new NBTTagCompound();
						tag.setInteger(KNOWLEDGE_LAYER_NBT, ConfigResearch.KNOWLEDGE_LAYER);
						data.setVariable(KNOWLEDGE_STATS_KEY, tag);
					}
				}
				return data.getVariable(KNOWLEDGE_STATS_KEY);
			}
		}
		return null;
	}
}
