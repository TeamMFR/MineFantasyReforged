package minefantasy.mf2.item.archery;

import java.util.HashMap;

public class ArrowType {
    public static HashMap<String, ArrowType> arrowMap = new HashMap<String, ArrowType>();
    public static ArrowType BOLT = new ArrowType("bolt", 1.50F, 1.50F, 1.50F).setRatio(0F, 1F, 0F);// Crossbow Bolt
    public static ArrowType EXPLOSIVEBOLT = new ArrowType("explosivebolt", 1.50F, 1.50F, 0.25F).setRatio(0F, 0F, 1F);// Blunt
    public static ArrowType NORMAL = new ArrowType("normal", 1.00F, 1.00F, 1.00F).setRatio(1F, 3F, 0F);// Some cut, most
    // pierce
    public static ArrowType BODKIN = new ArrowType("bodkin", 1.50F, 0.65F, 0.75F).setRatio(0F, 1F, 0F);// Pure piercing
    public static ArrowType BROADHEAD = new ArrowType("broad", 0.80F, 1.20F, 1.50F).setRatio(1F, 2F, 0F);// More cutting
    // than
    // normal
    public static ArrowType EXPLOSIVE = new ArrowType("explosive", 1.0F, 1.50F, 0.25F).setRatio(0F, 0F, 1F);// Blunt
    public final float weightModifier;
    public final float damageModifier;
    public final float velocity;
    public final String name;
    public float[] ratio = new float[]{0F, 0F, 1F};

    public ArrowType(String name, float velocity, float weightModifier, float damageModifier) {
        this.name = name;
        this.velocity = velocity;
        this.weightModifier = weightModifier;
        this.damageModifier = damageModifier;
        arrowMap.put(name, this);
    }

    /**
     * Sets up a ratio system for damage values
     *
     * @param cutting  the amount cutting damage
     * @param piercing the amount piercing damage
     * @param blunt    the amount blunt damage
     * @return
     */
    public ArrowType setRatio(float cutting, float piercing, float blunt) {
        ratio = new float[]{cutting, blunt, piercing};
        return this;
    }

}
