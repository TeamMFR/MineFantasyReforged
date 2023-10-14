package minefantasy.mfr.recipe;

import minefantasy.mfr.constants.Skill;
import net.minecraft.util.SoundEvent;

/**
 * @author AnonymousProductions This interface is used by the Carpenter in
 * MineFantasy so it can be referred to without importing the mod
 */
public interface ICarpenter {
	void setProgressMax(int i);

	void setRequiredToolTier(int i);

	void setRequiredCarpenterTier(int i);

	void setRequiredToolType(String toolType);

	void setCraftingSound(SoundEvent sound);

	void setRequiredResearch(String research);

	void setRequiredSkill(Skill skill);
}
