package minefantasy.mfr.material;

public class ArmourMaterial {
	/**
	 * EnumArmorMaterial VANILLA GUIDE: Leather_____28% Ratio: 1.3 Chain_______48%
	 * Ratio: 1.9 Iron________60% Ratio: 2.5 Gold________44% Ratio: 1.7
	 * Diamond_____80% Ratio: 5.0
	 */
	public final String name;
	public final int durability;
	public final float baseAR;
	public final int enchantment;
	public final float armourWeight;
	public boolean isMythic = false;

	public float magicResistanceModifier = 0F;
	public float fireResistanceModifier = 0F;

	public ArmourMaterial(String title, int dura, float AC, int enchant, float weight) {
		name = title;
		durability = dura;
		baseAR = AC;
		enchantment = enchant;
		armourWeight = weight;
	}

	public ArmourMaterial setMagicResistance(float magic) {
		magicResistanceModifier = magic;
		return this;
	}

	public ArmourMaterial setFireResistance(float fire) {
		fireResistanceModifier = fire;
		return this;
	}

	public ArmourMaterial setMythic(boolean flag) {
		isMythic = flag;
		return this;
	}
}
