package minefantasy.mfr.mechanics.knowledge;

import minefantasy.mfr.data.IStoredVariable;
import minefantasy.mfr.data.Persistence;
import minefantasy.mfr.data.PlayerData;
import minefantasy.mfr.network.ArtefactPacket;
import minefantasy.mfr.network.KnowledgePacket;
import minefantasy.mfr.network.NetworkHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ResearchLogic {
	public static final IStoredVariable<NBTTagCompound> KNOWLEDGE_STATS_KEY = IStoredVariable.StoredVariable.ofNBT("knowledgeStats", Persistence.ALWAYS);
	public static int knowledgelyr = 0;

	static {
		PlayerData.registerStoredVariables(KNOWLEDGE_STATS_KEY);
	}

	private static NBTTagCompound getNBT(EntityPlayer player) {
		if (player != null) {
			PlayerData data = PlayerData.get(player);
			if (data != null) {
				if (data.getVariable(KNOWLEDGE_STATS_KEY) == null) {
					NBTTagCompound tag = new NBTTagCompound();
					tag.setInteger("layer", knowledgelyr);
					data.setVariable(KNOWLEDGE_STATS_KEY, tag);
				}
				if (data.getVariable(KNOWLEDGE_STATS_KEY).getInteger("Layer") != knowledgelyr) {
					data.setVariable(KNOWLEDGE_STATS_KEY, null);

					NBTTagCompound tag = new NBTTagCompound();
					tag.setInteger("Layer", knowledgelyr);
					data.setVariable(KNOWLEDGE_STATS_KEY, tag);
				}
				return data.getVariable(KNOWLEDGE_STATS_KEY);
			}
		}
		return null;
	}

	public static boolean canPurchase(EntityPlayer player, InformationBase base) {
		if (base.isPreUnlocked() || !canUnlockInfo(player, base)) {
			return false;
		}
		NBTTagCompound nbt = getNBT(player);
		if (!nbt.hasKey("Research_" + base.getUnlocalisedName())) {
			return true;
		}
		return false;
	}

	public static void forceUnlock(EntityPlayer player, InformationBase base) {
		NBTTagCompound nbt = getNBT(player);
		nbt.setBoolean("Research_" + base.getUnlocalisedName(), true);
	}

	public static boolean tryUnlock(EntityPlayer player, InformationBase base) {
		if (base.isPreUnlocked() || !canUnlockInfo(player, base)) {
			return false;
		}
		NBTTagCompound nbt = getNBT(player);
		if (!nbt.hasKey("Research_" + base.getUnlocalisedName())) {
			nbt.setBoolean("Research_" + base.getUnlocalisedName(), true);
			return true;
		}
		return false;
	}

	public static boolean hasInfoUnlocked(EntityPlayer player, InformationBase base) {
		if (player != null) {
			if (base.isPreUnlocked())
				return base.parentInfo == null || hasInfoUnlocked(player, base.parentInfo);

			NBTTagCompound nbt = getNBT(player);
			String basename = base.getUnlocalisedName();
			if (nbt.hasKey("Research_" + basename)) {
				return nbt.getBoolean("Research_" + basename);
			}
			return false;
		}
		return false;
	}

	public static boolean hasInfoUnlocked(EntityPlayer player, String basename) {
		InformationBase base = getResearch(basename);
		if (base != null) {
			return hasInfoUnlocked(player, base);
		}
		return true;
	}

	public static boolean hasInfoUnlocked(EntityPlayer player, String[] basenames) {
		for (String basename : basenames) {
			InformationBase base = InformationList.nameMap.get(basename);
			if (base != null) {
				if (!hasInfoUnlocked(player, base)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Used to allow creative bypass without breaking research unlocking
	 * @param player the Player to check for research
	 * @param research the research to check if the player has it
	 * @return if the player has the specified research or bypass if Player is in Creative
	 */

	public static boolean getResearchCheck(EntityPlayer player, InformationBase research) {
		if (player.capabilities.isCreativeMode){
			return true;
		}
		return ResearchLogic.hasInfoUnlocked(player, research);
	}

	/**
	 * Returns true if the parent has been unlocked, or there is no parent
	 */
	public static boolean canUnlockInfo(EntityPlayer player, InformationBase base) {
		return base.parentInfo == null || hasInfoUnlocked(player, base.parentInfo);
	}

	@SideOnly(Side.CLIENT)
	public static int getResearchVisibility(EntityPlayer player, InformationBase base) {
		if (hasInfoUnlocked(player, base)) {
			return 0;
		} else {
			int i = 0;

			for (InformationBase knowledge1 = base.parentInfo; knowledge1 != null
					&& !hasInfoUnlocked(player, knowledge1); ++i) {
				knowledge1 = knowledge1.parentInfo;
			}
			return i;
		}
	}

	public static void syncData(EntityPlayer player) {
		if (!player.world.isRemote) {
			NetworkHandler.sendToPlayer((EntityPlayerMP) player, new KnowledgePacket(player));
		}
	}

	public static InformationBase getResearch(String researchName) {
		return InformationList.nameMap.get(researchName.toLowerCase());
	}

	public static int getArtefactCount(String research, EntityPlayer user) {
		NBTTagCompound nbt = getNBT(user);
		return nbt.getInteger("ArtefactCount-" + research);
	}

	public static void setArtefactCount(String research, EntityPlayer user, int count) {
		NBTTagCompound nbt = getNBT(user);
		nbt.setInteger("ArtefactCount-" + research, count);
	}

	public static int addArtefact(String research, EntityPlayer user) {
		int count = getArtefactCount(research, user);
		++count;
		setArtefactCount(research, user, count);
		return count;
	}

	public static void addArtefactUsed(EntityPlayer user, InformationBase base, ItemStack item) {
		NBTTagCompound nbt = getNBT(user);
		nbt.setBoolean(getKeyName(item, base), true);
		if (!user.world.isRemote) {
			NetworkHandler.sendToPlayer((EntityPlayerMP) user, new ArtefactPacket(user, getKeyName(item, base)));
		}
	}

	public static void addArtefactUsed(EntityPlayer user, String artefactKey) {
		NBTTagCompound nbt = getNBT(user);
		nbt.setBoolean(artefactKey, true);
	}

	public static boolean alreadyUsedArtefact(EntityPlayer user, InformationBase base, ItemStack item) {
		NBTTagCompound nbt = getNBT(user);
		return nbt.hasKey(getKeyName(item, base));
	}

	public static boolean alreadyUsedArtefact(EntityPlayer user, String artefactKey) {
		NBTTagCompound nbt = getNBT(user);
		return nbt.hasKey(artefactKey);
	}

	private static String getKeyName(ItemStack item, InformationBase base) {
		return "research_" + base.getUnlocalisedName() + "_" + item.getUnlocalizedName() + "_" + item.getItemDamage();
	}
}
