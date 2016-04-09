package minefantasy.mf2.api.heating;

import net.minecraft.item.ItemStack;

public class ForgeFuel 
{
	public ItemStack fuel;
	public float duration;
	public int baseHeat;
	/**
	 * Applied to lava, auto-lights the forge when placed
	 */
	public boolean doesLight;

	public ForgeFuel(ItemStack item, float dura, int heat, boolean light)
	{
		fuel = item;
		duration = dura;
		baseHeat = heat;
		doesLight = light;
	}
}
