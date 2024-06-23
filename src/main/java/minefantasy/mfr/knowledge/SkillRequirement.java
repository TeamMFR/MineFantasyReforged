package minefantasy.mfr.knowledge;

import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.mechanics.RPGElements;
import net.minecraft.entity.player.EntityPlayer;

public class SkillRequirement {
	protected Skill skill;
	protected int level;

	SkillRequirement(Skill skill, int level) {
		this.skill = skill;
		this.level = level;
	}

	public boolean isAvailable(EntityPlayer player) {
		return RPGElements.hasLevel(player, skill, level);
	}
}
