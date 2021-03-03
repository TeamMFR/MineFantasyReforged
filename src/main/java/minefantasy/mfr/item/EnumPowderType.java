package minefantasy.mfr.item;

public enum EnumPowderType {
	BLACKPOWDER("black_powder", 1.0F, 1.0F),
	ADVANCED_BLACK_POWDER("advanced_black_powder", 1.5F, 1.0F);

	public String name;
	public float damageModifier;
	public float rangeModifier;

	EnumPowderType(String name, float damage, float range) {
		this.name = name;
		this.damageModifier = damage;
		this.rangeModifier = range;
	}

	public static EnumPowderType getType(String string) {
		if (string.equals("advanced_black_powder")) {
			return ADVANCED_BLACK_POWDER;
		}
		return BLACKPOWDER;
	}
}
