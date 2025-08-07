package minefantasy.mfr.registry.knowledge;

import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.mechanics.RPGElements;
import net.minecraft.entity.player.EntityPlayer;

public class SkillRequirement {
	protected Skill skill;
	protected int level;

	public SkillRequirement(Skill skill, int level) {
		this.skill = skill;
		this.level = level;
	}

	public boolean isAvailable(EntityPlayer player) {
		return RPGElements.hasLevel(player, skill, level);
	}

	public Skill getSkill() {
		return skill;
	}

	public int getLevel() {
		return level;
	}
}
