package minefantasy.mf2.item.gadget;

public enum EnumPowderType {
    BLACKPOWDER("blackpowder", 1.0F, 1.0F), ADVBLACKPOWDER("advBlackpowder", 1.5F, 1.0F);

    public String name;
    public float damageModifier;
    public float rangeModifier;

    private EnumPowderType(String name, float damage, float range) {
        this.name = name;
        this.damageModifier = damage;
        this.rangeModifier = range;
    }

    public static EnumPowderType getType(byte i) {
        if (i == 1) {
            return ADVBLACKPOWDER;
        }
        return BLACKPOWDER;
    }
}
