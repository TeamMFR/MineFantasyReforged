package minefantasy.mfr.api.crafting;

import minefantasy.mfr.registry.material.types.CustomMaterialType;

public interface IMaterialDoubleComponent extends IMaterialComponent{
	/**
	 * is it made of "wood", "metal", etc
	 */
	CustomMaterialType getPrimaryMaterialType();

	/**
	 * is it made of "wood", "metal", etc
	 */
	CustomMaterialType getSecondaryMaterialType();
}
