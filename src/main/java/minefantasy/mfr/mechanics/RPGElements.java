package minefantasy.mfr.mechanics;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.data.IStoredVariable;
import minefantasy.mfr.data.Persistence;
import minefantasy.mfr.data.PlayerData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class RPGElements {

	public static final IStoredVariable<NBTTagCompound> SKILL_STATS_KEY = IStoredVariable.StoredVariable.ofNBT("skillStats", Persistence.ALWAYS).setSynced();
	public static boolean isSystemActive = true;
	public static float levelSpeedModifier = 1.0F;
	public static float levelUpModifier = 1.5F;
	public static ArrayList<Skill> skillsList = new ArrayList<Skill>();
	private static final HashMap<String, Skill> skillsMap = new HashMap<String, Skill>();

	static {
		PlayerData.registerStoredVariables(SKILL_STATS_KEY);
		skillsList.addAll(Arrays.asList(Skill.values()));
		for (Skill skill : Skill.values()) {
			skillsMap.put(skill.unlocalizedName, skill);
		}
	}

	public static NBTTagCompound getSkill(EntityPlayer player, String skillname) {
		PlayerData data = PlayerData.get(player);
		NBTTagCompound nbt = data.getVariable(SKILL_STATS_KEY);
		if (nbt == null) {
			MineFantasyReforged.LOG.error("Skill Stats are null! This is bad, please report");
			return null;
		}
		return nbt.getCompoundTag(skillname);
	}

	public static void initSkills(PlayerData data) {
		if (data.getVariable(SKILL_STATS_KEY) == null) {
			NBTTagCompound nbt = new NBTTagCompound();
			NBTTagCompound tag = new NBTTagCompound();
			for (Skill skill : skillsList) {
				if (skill != Skill.NONE) {
					skill.init(tag);
					tag.setString("name", skill.unlocalizedName);
					nbt.setTag(skill.unlocalizedName, tag);
					tag = new NBTTagCompound(); //This is the key to everything
					MineFantasyReforged.LOG.info("Initiate skill: " + skill.unlocalizedName);
				}
			}
			data.setVariable(SKILL_STATS_KEY, nbt);
		}
	}

	public static Skill getSkillByName(String name) {
		return skillsMap.get(name.toLowerCase());
	}

	public static int getLevel(EntityPlayer player, Skill skill) {
		return RPGElements.getSkill(player, skill.unlocalizedName).getInteger("level");
	}

	public static float getWeaponModifier(EntityPlayer player, Skill skill) {
		int minSkill = 25;// No bonus damage until level 25+
		int level = getLevel(player, skill);
		if (level <= minSkill) {
			return 1.0F;
		}

		float progress = ((float) level - minSkill) / ((float) skill.getMaxLevel() - minSkill);
		return 1.0F + (progress);
	}

	public static boolean hasLevel(EntityPlayer player, Skill skill, int requirement) {
		return getSkill(player, skill.unlocalizedName).getInteger("level") >= requirement;
	}

}
