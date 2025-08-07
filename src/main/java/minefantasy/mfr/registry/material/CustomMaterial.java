package minefantasy.mfr.registry.material;

import minefantasy.mfr.constants.Rarity;
import minefantasy.mfr.registry.material.types.CustomMaterialType;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class CustomMaterial extends IForgeRegistryEntry.Impl<CustomMaterial>{
	private static final int[] flameResistArray = new int[] {100, 300};

	private final String name;
	private final CustomMaterialType type;

	protected Ingredient materialIngredient;
	/**
	 * The material colour
	 */
	private final int[] colourRGB;
	/**
	 * Base threshold for armour rating
	 */
	private final Float hardness;
	/**
	 * The Modifier for durability (1pt per 250 uses)
	 */
	private final Float durability;
	/**
	 * used for bow power.. >1 weakens blunt prot, <1 weakens piercing prot
	 */
	private final Float flexibility;
	/**
	 * The Efficiency modifier (Like ToolMaterial) Also does damage
	 */
	private final Float sharpness;
	/**
	 * The modifier to resist elements like fire and corrosion)
	 */
	private final Float resistance;
	/**
	 * The weight Kg/U (Kilogram per unit)
	 */
	private final Float density;
	private final Integer tier;
	private final Rarity rarity;
	private final Integer enchantability;
	private final Integer crafterTier;
	private final Integer crafterAnvilTier;
	private final Float craftTimeModifier;
	private final Integer meltingPoint;
	private Float[] armourProtection; // TODO: consider making this property into a typed class
	private final Boolean unbreakable;

	public CustomMaterial(String name, CustomMaterialType type, Ingredient materialIngredient, int[] colourRGB, Float hardness,
			Float durability, Float flexibility, Float sharpness, Float resistance, Float density, Integer tier, Rarity rarity,
			Integer enchantability, Integer crafterTier, Integer crafterAnvilTier, Float craftTimeModifier, Integer meltingPoint,
			Float[] armourProtection, Boolean unbreakable) {
		this.name = name;
		this.type = type;
		this.materialIngredient = materialIngredient;
		this.colourRGB = colourRGB;
		this.hardness = hardness;
		this.durability = durability;
		this.flexibility = flexibility;
		this.sharpness = sharpness;
		this.resistance = resistance;
		this.density = density;
		this.tier = tier;
		this.rarity = rarity;
		this.enchantability = enchantability;
		this.crafterTier = crafterTier;
		this.crafterAnvilTier = crafterAnvilTier;
		this.craftTimeModifier = craftTimeModifier;
		this.meltingPoint = meltingPoint;
		this.armourProtection = armourProtection;
		this.unbreakable = unbreakable;
	}

	/**
	 * Gets material name
	 */
	public String getName() {
		return name.toLowerCase();
	}

	/**
	 * Gets material type
	 */
	public CustomMaterialType getType() {
		return type;
	}

	public void setMaterialIngredient(Ingredient materialIngredient) {
		this.materialIngredient = materialIngredient;
	}

	public Ingredient getMaterialIngredient() {
		return materialIngredient;
	}

	public int[] getColourRGB() {
		return colourRGB;
	}

	public int getColourInt() {
		return (colourRGB[0] << 16) + (colourRGB[1] << 8) + colourRGB[2];
	}

	public Float getHardness() {
		return hardness;
	}

	public Float getDurability() {
		return durability;
	}

	public Float getFlexibility() {
		return flexibility;
	}

	public Float getSharpness() {
		return sharpness;
	}

	public Float getResistance() {
		return resistance;
	}

	public Float getDensity() {
		return density;
	}

	public Integer getTier() {
		return tier;
	}

	public Rarity getRarity() {
		return rarity;
	}

	public Integer getEnchantability() {
		return enchantability;
	}

	public Integer getCrafterTier() {
		return crafterTier;
	}

	public Integer getCrafterAnvilTier() {
		return crafterAnvilTier;
	}

	public Float getCraftTimeModifier() {
		return craftTimeModifier;
	}

	public Integer getMeltingPoint() {
		return meltingPoint;
	}

	public void setArmourStats(float cutting, float blunt, float piercing) {
		armourProtection = new Float[] {cutting, blunt, piercing};
	}

	public Float[] getArmourProtection() {
		return armourProtection;
	}

	public Boolean isUnbreakable() {
		return unbreakable;
	}

	@SideOnly(Side.CLIENT)
	public String getMaterialString() {
		return I18n.format("materialtype." + this.type.getName() + ".name", this.crafterTier);
	}

	public float getArmourProtection(int id) {
		return armourProtection[id];
	}

	public float getFireResistance() {
		if (meltingPoint > flameResistArray[0]) {
			float max = flameResistArray[1] - flameResistArray[0];
			float heat = meltingPoint - flameResistArray[0];

			int res = (int) (heat / max * 100F);
			return Math.min(100, res);
		}
		return 0F;
	}

	// -----------------------------------BOW
	// FUNCTIONS----------------------------------------\\

	public int[] getHeatableStats() {
		int workableTemp = meltingPoint;
		int unstableTemp = (int) (workableTemp * 1.5F);
		int maxTemp = (int) (workableTemp * 2F);
		return new int[] {workableTemp, unstableTemp, maxTemp};
	}

	public boolean isHeatable() {
		return false;
	}
}
