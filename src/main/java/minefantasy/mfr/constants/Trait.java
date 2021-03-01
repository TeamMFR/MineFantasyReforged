package minefantasy.mfr.constants;

public enum Trait {
	Unbreakable("Unbreakable"),
	Inferior("MF_Inferior"); // TODO: apply snake_case naming convention as these are nbt tags

	private final String name;

	Trait(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
