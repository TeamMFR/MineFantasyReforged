package minefantasy.mfr.recipe;

import minefantasy.mfr.constants.Skill;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

public interface IRecipeMFR {
	String getName();
	String getRequiredResearch();

	Skill getSkill();

	int getSkillXp();

	boolean shouldSlotGiveSkillXp();

	float getVanillaXp();

	default void giveSkillXp(EntityPlayer player, float baseXp) {
		Skill skill = getSkill();
		if (skill != null && skill != Skill.NONE) {
			skill.addXP(player, (int) (baseXp + getSkillXp()));
		}
	}

	default void giveSkillXpPerCount(EntityPlayer player, float baseXP, int count) {
		Skill skill = getSkill();
		if (skill != null && skill != Skill.NONE) {
			float baseXp = ((float) getSkillXp() / 10) + baseXP;

			if (baseXp == 0.0F) {
				count = 0;
			} else if (baseXp < 1.0F) {
				int j = MathHelper.floor((float) count * baseXp);

				if (j < MathHelper.ceil((float) count * baseXp)
						&& Math.random() < (double) ((float) count * baseXp - (float) j)) {
					++j;
				}

				count = j;
			}

			while (count > 0) {
				int k = EntityXPOrb.getXPSplit(count);
				count -= k;
				skill.addXP(player, k);
			}
		}
	}

	default void giveVanillaXp(EntityPlayer player, float baseXP, int count) {
		float baseXp = getVanillaXp() + baseXP;

		if (baseXp == 0.0F) {
			count = 0;
		} else if (baseXp < 1.0F) {
			int j = MathHelper.floor((float) count * baseXp);

			if (j < MathHelper.ceil((float) count * baseXp)
					&& Math.random() < (double) ((float) count * baseXp - (float) j)) {
				++j;
			}

			count = j;
		}

		while (count > 0) {
			int k = EntityXPOrb.getXPSplit(count);
			count -= k;
			player.world.spawnEntity(new EntityXPOrb(player.world,
					player.posX, player.posY + 0.5D, player.posZ + 0.5D, k));
		}
	}
}
