package minefantasy.mfr.constants;

import minefantasy.mfr.api.MineFantasyReforgedAPI;
import minefantasy.mfr.data.PlayerData;
import minefantasy.mfr.event.LevelUpEvent;
import minefantasy.mfr.mechanics.RPGElements;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;

public enum Skill {

	ARTISANRY("artisanry"),
	ENGINEERING("engineering"),
	CONSTRUCTION("construction"),
	PROVISIONING("provisioning"),
	COMBAT("combat"),
	NONE("none");

	Skill(String unlocalizedName) {
		this.unlocalizedName = unlocalizedName;
	}

	public final String unlocalizedName;

	public int getMaxLevel() {
		return 100;
	}

	public int getStartLevel() {
		return 1;
	}

	/**
	 * Returns the skill with the given name, or throws an {@link java.lang.IllegalArgumentException} if no such
	 * skill exists.
	 */
	public static Skill fromName(String name) {

		for (Skill skill : values()) {
			if (skill.unlocalizedName.equals(name))
				return skill;
		}

		throw new IllegalArgumentException("No such skill with unlocalized name: " + name);
	}

	/**
	 * Gets how much xp needed to level up
	 */
	public int getLvlXP(int level) {
		float rise = 0.2F * RPGElements.levelUpModifier;// 20% rize each level
		return (int) Math.floor(10F * (1.0F + (rise * (level - 1))));
	}

	/**
	 * Get the current xp and max xp of the given skill
	 * @param player the player to get the skill stats from
	 * @return an int array where the first element is the current xp of the skill, and the second element is the max xp of the current level
	 */
	public int[] getXP(EntityPlayer player) {
		NBTTagCompound skill = RPGElements.getSkill(player, unlocalizedName);

		if (skill != null) {
			int value = skill.getInteger("xp");
			int max = skill.getInteger("xpMax");
			return new int[] {value, max};
		}
		return new int[] {0, 0};
	}

	public void manualLvlUp(EntityPlayer player, int newLevel) {
		NBTTagCompound skill = RPGElements.getSkill(player, unlocalizedName);
		if (skill == null)
			return;

		skill.setInteger("xp", 0);

		skill.setInteger("level", newLevel);
		skill.setInteger("xp", 0);
		skill.setInteger("xpMax", getLvlXP(newLevel));
		levelUp(player, newLevel);
	}

	/**
	 * Adds xp to the skill, negative values take xp away
	 */
	public void addXP(EntityPlayer player, int xp) {
		xp = (int) (RPGElements.levelSpeedModifier * xp);
		NBTTagCompound skill = RPGElements.getSkill(player, unlocalizedName);
		if (skill == null)
			return;

		int value = skill.getInteger("xp");
		int max = skill.getInteger("xpMax");
		int currentLevel = RPGElements.getLevel(player, this);

		if (max <= 0 || currentLevel >= getMaxLevel()) {
			return;
		}
		value += xp;
		skill.setInteger("xp", value);

		if (value >= max) {
			value -= max;
			int level = skill.getInteger("level") + 1;
			skill.setInteger("level", level);
			skill.setInteger("xp", value);
			skill.setInteger("xpMax", getLvlXP(level));
			levelUp(player, level);
		} else {
			if (value < 0) {
				int level = skill.getInteger("level") - 1;
				if (level < 1)
					return;

				skill.setInteger("level", level);

				int newMax = getLvlXP(level);
				skill.setInteger("xp", value + newMax);
				skill.setInteger("xpMax", newMax);
			}
		}
		if (player instanceof EntityPlayerMP) {
			PlayerData.get(player).sync();
		}
	}

	private void levelUp(EntityPlayer player, int newlvl) {
		if (!player.world.isRemote) {
			MineFantasyReforgedAPI.debugMsg("Level up detected for " + unlocalizedName);
			MinecraftForge.EVENT_BUS.post(new LevelUpEvent(player, this, newlvl));
		}
	}

	public void init(NBTTagCompound tag) {
		int start = getStartLevel();
		tag.setInteger("level", start);
		tag.setInteger("xp", 0);
		tag.setInteger("xpMax", getLvlXP(start));
	}

	public String getDisplayName() {
		return I18n.format("skill." + unlocalizedName + ".name");
	}
}
