package minefantasy.mf2.mechanics;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

@Deprecated
public class ArrowListMF 
{
	public static List<ItemStack> useableArrows = new ArrayList();
	private static void addArrow(ItemStack arrow)
	{
		useableArrows.add(arrow);
	}
	private static boolean canShootArrow(ItemStack arrow)
	{
		for(ItemStack Comparearrow: useableArrows)
		{
			if(Comparearrow.isItemEqual(arrow))
				return true;
		}
		return false;
	}
}
