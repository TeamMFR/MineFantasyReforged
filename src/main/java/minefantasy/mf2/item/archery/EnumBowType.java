package minefantasy.mf2.item.archery;

public enum EnumBowType 
{
	/**
	 * Metal Bow
	 * 50% faster than basic
	 */
	RECURVE(1.0F, 1.0F, 1.0F),
	/**
	 * Standard bow Reinforced with metal
	 */
	COMPOSITE(1.0F, 1.0F, 1.0F);
	
	public final float damageModifier;
	public final float chargeTime;
	public final float durabilityModifier;
	
	/**
	 * @param dam modifes damage (1.0=normal)
	 * @param time is how many seconds until it reaches full charge
	 * @param durability modifies the durability (1.0=normal)
	 */
	private EnumBowType(float dam, float time, float durability)
	{
		damageModifier = dam;
		chargeTime = time*20F;
		durabilityModifier = durability;
	}
}
