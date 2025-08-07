package minefantasy.mfr.registry.knowledge.types;

import net.minecraft.util.IStringSerializable;

public enum ResearchType implements IStringSerializable {
	ARTIFACT_RESEARCH,
	PERK_RESEARCH,
	NONE;

	@Override
	public String getName() {
		return this.name().toLowerCase();
	}

	public static ResearchType deserialize(String name) {
		for (ResearchType type : values()) {

			if (type.getName().equals(name)) {
				return type;
			}
		}
		return NONE;
	}

	public static ResearchType getByNameWithModId(String name, String modId) {
		for (ResearchType type : values()) {

			if ((modId + ":" + type.getName()).equals(name)) {
				return type;
			}
		}
		return NONE;
	}
}
