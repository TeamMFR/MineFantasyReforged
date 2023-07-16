package minefantasy.mfr.recipe.types;

import net.minecraft.util.IStringSerializable;

public enum AnvilRecipeType implements IStringSerializable {
	SHAPED_ANVIL_RECIPE,
	SHAPELESS_ANVIL_RECIPE,
	CUSTOM_MATERIAL_SHAPED_ANVIL_RECIPE,
	CUSTOM_MATERIAL_SHAPELESS_ANVIL_RECIPE,
	NONE;

	@Override
	public String getName() {
		return this.name().toLowerCase();
	}

	public static AnvilRecipeType deserialize(String name) {
		for (AnvilRecipeType type : values()) {

			if (type.getName().equals(name)) {
				return type;
			}
		}
		return NONE;
	}

	public static AnvilRecipeType getByNameWithModId(String name, String modId) {
		for (AnvilRecipeType type : values()) {

			if ((modId + ":" + type.getName()).equals(name)) {
				return type;
			}
		}
		return NONE;
	}
}
