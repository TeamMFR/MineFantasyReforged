package minefantasy.mfr.recipe;

/**
 * @author AnonymousProductions This interface is used by the Carpenter in
 * MineFantasy so it can be referred to without importing the mod
 */
public interface ICarpenter {
	void setProgressMax(int i);

	void setRequiredToolTier(int i);

	void setRequiredCarpenterTier(int i);
}
