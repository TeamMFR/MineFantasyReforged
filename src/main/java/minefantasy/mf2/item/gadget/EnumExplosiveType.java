package minefantasy.mf2.item.gadget;

public enum EnumExplosiveType
{
	BASIC("basic",       24F, 5.5F),
	SHRAPNEL("shrapnel", 32F, 5.5F),
	FIRE("fire",         40F, 5.5F);
	
	public String name;
	public float damage;
	public float range;
	
	private EnumExplosiveType(String name, float damage, float range)
	{
		this.name = name;
		this.damage = damage;
		this.range = range;
	}
	
	public static EnumExplosiveType getType(byte i)
	{
		if(i == 1)
		{
			return SHRAPNEL;
		}
		if(i == 2)
		{
			return FIRE;
		}
		return BASIC;
	}
}
