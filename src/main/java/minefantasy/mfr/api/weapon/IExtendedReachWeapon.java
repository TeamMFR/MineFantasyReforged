package minefantasy.mfr.api.weapon;

public interface IExtendedReachWeapon {
	/**
	 * The distance the weapon will hit as an addition to the standard hit distance, which is 4
	 *
	 * @return the reach modifier
	 */
	float getReachModifierInBlocks();
}
