package minefantasy.mfr.item;

public enum EnumCasingType {
	CRUDE("crude", 0.5F, 3F, 1.0F),
	CERAMIC("ceramic", 1.0F, 5F, 1.0F),
	IRON("iron", 1.5F, 4F, 2.0F),
	OBSIDIAN("obsidian", 2.0F, 4F, 1.5F),
	CRYSTAL("crystal", 1.5F, 5F, 0.75F);

	public String name;
	public float damageModifier;
	public float rangeModifier;
	public float weightModifier;

	EnumCasingType(String name, float damage, float range, float weight) {
		this.name = name;
		this.damageModifier = damage;
		this.rangeModifier = range;
		this.weightModifier = weight;
	}

	public static EnumCasingType getType(String string) {
		if (string.equals("crude")) {
			return CRUDE;
		}
		if (string.equals("iron")) {
			return IRON;
		}
		if (string.equals("obsidian")) {
			return OBSIDIAN;
		}
		if (string.equals("crystal")) {
			return CRYSTAL;
		}
		return CERAMIC;
	}
}
