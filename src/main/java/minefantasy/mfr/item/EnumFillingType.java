package minefantasy.mfr.item;

public enum EnumFillingType {
    BASIC("basic", 24F, 1.0F), SHRAPNEL("shrapnel", 32F, 1.0F), FIRE("fire", 40F, 1.0F);

    public String name;
    public float damage;
    public float range;

    EnumFillingType(String name, float damage, float range) {
        this.name = name;
        this.damage = damage;
        this.range = range;
    }

    public static EnumFillingType getType(String string) {
        if (string.equals("shrapnel")) {
            return SHRAPNEL;
        }
        if (string.equals("fire")) {
            return FIRE;
        }
        return BASIC;
    }
}
