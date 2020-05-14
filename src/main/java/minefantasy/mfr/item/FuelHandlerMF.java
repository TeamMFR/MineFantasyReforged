package minefantasy.mfr.item;

import net.minecraftforge.fml.common.IFuelHandler;
import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.init.ComponentListMFR;
import net.minecraft.item.ItemStack;

public class FuelHandlerMF implements IFuelHandler {

    @Override
    public int getBurnTime(ItemStack fuel) {
        if (fuel.getItem() == null) {
            return 0;
        }
        if (fuel.getItem() == ComponentListMFR.PLANK) {
            return (int) (200 * CustomToolHelper.getBurnModifier(fuel));
        }
        if (fuel.getItem() == ComponentListMFR.COAL_DUST) {
            return 400;
        }
        if (fuel.getItem() == ComponentListMFR.COKE) {
            return 2400;
        }
        return 0;
    }

}
