package minefantasy.mfr.registry.types;

import net.minecraft.util.IStringSerializable;

public enum CustomMaterialType implements IStringSerializable {
	WOOD_MATERIAL("wood_types"),
	METAL_MATERIAL("metal_types"),
	LEATHER_MATERIAL("leather_types"),
	NONE("");

	final String fileName;

	CustomMaterialType(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	@Override
	public String getName() {
		return this.name().toLowerCase();
	}

	public static CustomMaterialType deserialize(String name) {
		for (CustomMaterialType type : values()) {

			if (type.getName().equals(name)) {
				return type;
			}
		}
		return NONE;
	}

	public static CustomMaterialType getByNameWithModId(String name, String modId) {
		for (CustomMaterialType type : values()) {

			if ((modId + ":" + type.getName()).equals(name)) {
				return type;
			}
		}
		return NONE;
	}
}
