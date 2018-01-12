package minefantasy.mf2.api.armour;

import java.util.HashMap;

public class ArmourDesign {
    public static HashMap<String, ArmourDesign> designs = new HashMap<String, ArmourDesign>();

    public static final String light = "light";
    public static final String medium = "medium";
    public static final String heavy = "heavy";
    // Weight: Above 50kg slows down and increases stamina decay, and classes as
    // heavy armour
    // Bulk: Above 0.0 means it slows stamina regen
    // Reg Name Prot Dura Weight Bulk Ctg Bnt Png
    public static final ArmourDesign CLOTH = new ArmourDesign("clothing", "Clothing", 1.0F, 1.0F, 0F, 0.25F)
            .calibrateTraits(0.5F, 1.0F, 0.5F).setWeightGroup(light);// Weak
    public static final ArmourDesign SOLID = new ArmourDesign("default", "Basic", 1.0F, 1.0F, 40F, 1.00F)
            .setWeightGroup(medium);// Basic Armour
    public static final ArmourDesign MAIL = new ArmourDesign("mail", "Mail", 1.0F, 1.0F, 35F, 0.75F)
            .calibrateTraits(1.0F, 0.7F, 0.9F).setWeightGroup(medium);// Vulnerable to blunt
    public static final ArmourDesign PLATE = new ArmourDesign("plate", "Plate", 1.5F, 1.0F, 60F, 1.00F)
            .calibrateTraits(1.0F, 1.0F, 0.6F).setWeightGroup(heavy);// Vulnerable to piercing
    // LIGHT ARMOUR
    public static final ArmourDesign LEATHER = new ArmourDesign("leather", "Leather", 1.0F, 1.0F, 10F, 0.25F)
            .calibrateTraits(0.7F, 1.0F, 0.5F).setWeightGroup(light);// Slightly better blunt
    public static final ArmourDesign PADDING = new ArmourDesign("padding", "Padding", 1.0F, 1.0F, 10F, 1.00F)
            .calibrateTraits(0.85F, 0.9F, 0.6F).setWeightGroup(light);// 10kg, Cutting resistant
    public static final ArmourDesign STUDDED = new ArmourDesign("studded", "Studded", 0.8F, 1.0F, 10F, 0.25F)
            .calibrateTraits(0.6F, 1.2F, 0.9F).setWeightGroup(light);// 10kg, Blunt protection
    // MEDIUM ARMOUR
    public static final ArmourDesign SCALEMAIL = new ArmourDesign("scalemail", "Scalemail", 1.0F, 1.0F, 8F, 0.30F)
            .calibrateTraits(1.00F, 1.00F, 1.00F).setWeightGroup(medium);// 20kg, Average
    public static final ArmourDesign CHAINMAIL = new ArmourDesign("chainmail", "Chainmail", 1.0F, 1.0F, 8F, 0.65F)
            .calibrateTraits(1.20F, 0.60F, 1.20F).setWeightGroup(medium);// 20kg, Less blunt more piercing
    // HEAVY ARMOUR
    public static final ArmourDesign SPLINTMAIL = new ArmourDesign("splintmail", "Splintmail", 1.2F, 1.0F, 12F, 0.75F)
            .calibrateTraits(1.00F, 1.00F, 1.00F).setWeightGroup(heavy);// 30kg, Average
    public static final ArmourDesign FIELDPLATE = new ArmourDesign("fieldplate", "Fieldplate", 1.2F, 1.0F, 16F, 1.00F)
            .calibrateTraits(1.25F, 1.25F, 1.00F).setWeightGroup(heavy);// 40kg, Vulnerable to piercing
    public static final ArmourDesign COGWORK = new ArmourDesign("cogwork", "Cogwork", 4.0F, 8.0F, 30F, 2.00F)
            .calibrateTraits(1.00F, 1.00F, 1.00F).setWeightGroup(heavy);// >120kg, Massive Protection

    // ArmourCalculator
    private String name;
    private float durability;
    private float armourWeight;

    private float baseProtection;

    private float cuttingModifier = 1.0F;
    private float piercingModifier = 1.0F;
    private float bluntModifier = 1.0F;

    private float bulk;
    private String weightGroup = "medium";

    /**
     * @param register The string that it is registered under (For config)
     * @param name     The name of the Design
     * @param prot     The protection against basic damage (1.0F = normal protection for
     *                 tier)
     * @param dura     The durability modifier
     * @param weight   the weight in Kg of the suit
     * @param bulk     the bulk of the suit (1.0 = plate, 0.5 = splint, etc)
     */
    public ArmourDesign(String register, String name, float prot, float dura, float weight, float bulk) {
        designs.put(register, this);
        this.name = name;
        baseProtection = prot;

        durability = dura;
        armourWeight = weight;

        this.bulk = bulk;
    }

    /**
     * These traits only apply when armours are coded to take them
     */
    public ArmourDesign calibrateTraits(float cutting, float blunt, float pierce) {
        cuttingModifier = cutting;
        bluntModifier = blunt;
        piercingModifier = pierce;

        return this;
    }

    public String getName() {
        return name;
    }

    public float getDurability() {
        return durability;
    }

    public float getWeight() {
        return armourWeight;
    }

    public float getRating() {
        return baseProtection;
    }

    public float[] getProtectiveTraits() {
        return new float[]{cuttingModifier, bluntModifier, piercingModifier};
    }

    public float getBulk() {
        return bulk;
    }

    /**
     * @param group "light" "medium" or "heavy"
     */
    public ArmourDesign setWeightGroup(String group) {
        weightGroup = group;
        return this;
    }

    /**
     * @return "light" "medium" or "heavy"
     */
    public String getGroup() {
        return weightGroup;
    }
}
