package minefantasy.mfr.recipe;

import minefantasy.mfr.constants.Skill;
import net.minecraft.util.SoundEvent;

/**
 * @author ThatPolishKid99 This interface is used by the Cooking Bench in
 * MineFantasy, so it can be referred to without importing the mod
 */
public interface IKitchenBench {
	void setProgressMax(int i);

	void setRequiredToolTier(int i);

	void setRequiredKitchenBenchTier(int i);

	void setRequiredToolType(String toolType);

	void setCraftingSound(SoundEvent sound);

	void setRequiredResearch(String research);

	void setRequiredSkill(Skill skill);

	void setDirtyProgressAmount(int amount);
}
