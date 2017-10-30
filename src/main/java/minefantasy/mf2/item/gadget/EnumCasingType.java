package minefantasy.mf2.item.gadget;

public enum EnumCasingType {
    CRUDE("crude", 0.5F, 3F, 1.0F), CERAMIC("ceramic", 1.0F, 5F, 1.0F), IRON("iron", 1.5F, 4F,
            2.0F), OBSIDIAN("obsidian", 2.0F, 4F, 1.5F), CRYSTAL("crystal", 1.5F, 5F, 0.75F);

    public String name;
    public float damageModifier;
    public float rangeModifier;
    public float weightModifier;

    private EnumCasingType(String name, float damage, float range, float weight) {
        this.name = name;
        this.damageModifier = damage;
        this.rangeModifier = range;
        this.weightModifier = weight;
    }

    public static EnumCasingType getType(byte i) {
        if (i == -1) {
            return CRUDE;
        }
        if (i == 1) {
            return IRON;
        }
        if (i == 2) {
            return OBSIDIAN;
        }
        if (i == 3) {
            return CRYSTAL;
        }
        return CERAMIC;
    }
}
