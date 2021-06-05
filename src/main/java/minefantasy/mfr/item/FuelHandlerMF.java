package minefantasy.mfr.item;

import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class FuelHandlerMF implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {
		if (fuel.isEmpty()) {
			return 0;
		}
		if (fuel.getItem() == MineFantasyItems.TIMBER) {
			return (int) (200 * CustomToolHelper.getBurnModifier(fuel));
		}
		if (fuel.getItem() == MineFantasyItems.COAL_DUST) {
			return 400;
		}
		if (fuel.getItem() == MineFantasyItems.COKE) {
			return 2400;
		}
		return 0;
	}

}
