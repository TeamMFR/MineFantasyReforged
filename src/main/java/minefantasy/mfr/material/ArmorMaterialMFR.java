package minefantasy.mfr.material;

public class ArmorMaterialMFR {
	/**
	 * EnumArmorMaterial VANILLA GUIDE: Leather_____28% Ratio: 1.3 Chain_______48%
	 * Ratio: 1.9 Iron________60% Ratio: 2.5 Gold________44% Ratio: 1.7
	 * Diamond_____80% Ratio: 5.0
	 */

	// TODO: vanilla uses the net.minecraft.item.ItemArmor.ArmorMaterial enum to define this information, but since MF has more properties, we can consider
	//  registering our materials there for mod compat, but generally it won't cover all of our needs?

	public final String name;
	public final int durability;
	public final float baseArmorRating;
	public final int enchantability;
	public final float armourWeight;
	public boolean isMythic = false;

	public float magicResistanceModifier = 0F;
	public float fireResistanceModifier = 0F;

	public ArmorMaterialMFR(String title, int durability, float armorClass, int enchantability, float armourWeight) {
		name = title;
		this.durability = durability;
		baseArmorRating = armorClass;
		this.enchantability = enchantability;
		this.armourWeight = armourWeight;
	}

	public ArmorMaterialMFR setMagicResistance(float magic) {
		magicResistanceModifier = magic;
		return this;
	}

	public ArmorMaterialMFR setFireResistance(float fire) {
		fireResistanceModifier = fire;
		return this;
	}

	public ArmorMaterialMFR setMythic(boolean flag) {
		isMythic = flag;
		return this;
	}
}
