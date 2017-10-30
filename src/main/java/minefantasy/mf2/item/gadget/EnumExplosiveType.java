package minefantasy.mf2.item.gadget;

public enum EnumExplosiveType {
    BASIC("basic", 24F, 1.0F), SHRAPNEL("shrapnel", 32F, 1.0F), FIRE("fire", 40F, 1.0F);

    public String name;
    public float damage;
    public float range;

    private EnumExplosiveType(String name, float damage, float range) {
        this.name = name;
        this.damage = damage;
        this.range = range;
    }

    public static EnumExplosiveType getType(byte i) {
        if (i == 1) {
            return SHRAPNEL;
        }
        if (i == 2) {
            return FIRE;
        }
        return BASIC;
    }
}
