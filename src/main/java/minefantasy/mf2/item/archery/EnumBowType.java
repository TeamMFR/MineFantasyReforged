package minefantasy.mf2.item.archery;

public enum EnumBowType {
    /**
     * Standard bow Reinforced with metal
     */
    SHORTBOW(1.0F, 1.0F, 1.0F, 1.0F, 1.0F),
    /**
     * Larger varient, more power
     */
    LONGBOW(1.0F, 1.5F, 1.0F, 1.5F, 0.5F);

    public final float damageModifier;
    public final float velocity;
    public final float spread;
    public final float chargeTime;
    public final float durabilityModifier;

    /**
     * @param dam        modifes damage (1.0=normal)
     * @param time       is how many seconds until it reaches full charge
     * @param durability modifies the durability (1.0=normal)
     * @param velocity   modifies the speed of the fired projectile
     */
    private EnumBowType(float dam, float time, float durability, float velocity, float spread) {
        this.damageModifier = dam;
        this.chargeTime = time * 20F;
        this.durabilityModifier = durability;
        this.velocity = velocity;
        this.spread = spread;
    }
}
