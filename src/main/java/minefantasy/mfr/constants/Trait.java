package minefantasy.mfr.constants;

public enum Trait {
	Unbreakable("Unbreakable"),
	Inferior("MF_Inferior");

	private final String name;

	Trait(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
