package minefantasy.mfr.recipe;

import minefantasy.mfr.knowledge.ResearchBase;

/**
 * @author AnonymousProductions This interface is used by the anvil in
 * MineFantasy so it can be referred to without importing the mod
 */
public interface IAnvil {
	void setProgressMax(int i);

	void setRequiredToolTier(int i);

	void setRequiredAnvilTier(int i);

	void setRequiredResearch(ResearchBase research);
}
