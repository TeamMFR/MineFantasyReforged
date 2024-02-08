package minefantasy.mfr.recipe.types;

import net.minecraft.util.IStringSerializable;

public enum TransformationRecipeType implements IStringSerializable {
	TRANSFORMATION_RECIPE,
	TRANSFORMATION_RECIPE_BLOCKSTATE,
	NONE;

	@Override
	public String getName() {
		return this.name().toLowerCase();
	}

	public static TransformationRecipeType deserialize(String name) {
		for (TransformationRecipeType type : values()) {

			if (type.getName().equals(name)) {
				return type;
			}
		}
		return NONE;
	}

	public static TransformationRecipeType getByNameWithModId(String name, String modId) {
		for (TransformationRecipeType type : values()) {

			if ((modId + ":" + type.getName()).equals(name)) {
				return type;
			}
		}
		return NONE;
	}
}
