package minefantasy.mf2.item;

import minefantasy.mf2.item.list.ComponentListMF;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class AdvancedFuelHandlerMF implements IFuelHandler
{

	@Override
	public int getBurnTime(ItemStack fuel)
	{
		if(fuel == null)
		{
			return 0;
		}
		if(fuel.getItem() == ComponentListMF.coalDust)
		{
			return 300;//15s
		}
		if(fuel.getItem() == ComponentListMF.coke)
		{
			return 2400;
		}
		if(fuel.getItem() == Items.coal)
		{
			return 1200;
		}
		if(fuel.getItem() == Item.getItemFromBlock(Blocks.coal_block))
		{
			return 10800;
		}
		return 0;
	}

}
