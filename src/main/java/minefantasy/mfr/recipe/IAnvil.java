package minefantasy.mfr.recipe;

import minefantasy.mfr.constants.Skill;

/**
 * @author AnonymousProductions This interface is used by the anvil in
 * MineFantasy so it can be referred to without importing the mod
 */
public interface IAnvil {
	void setProgressMax(int i);

	void setRequiredHammerTier(int i);

	void setRequiredAnvilTier(int i);

	void setHotOutput(boolean i);

	void setRequiredToolType(String toolType);

	void setRequiredSkill(Skill skill);

	void setRequiredResearch(String research);

	int getRequiredHammerTier();

	int getRequiredAnvilTier();
}
