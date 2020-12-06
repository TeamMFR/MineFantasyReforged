package minefantasy.mfr.item;

import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.init.ComponentListMFR;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class FuelHandlerMF implements IFuelHandler {

    @Override
    public int getBurnTime(ItemStack fuel) {
        if (fuel.getItem() == null) {
            return 0;
        }
        if (fuel.getItem() == ComponentListMFR.TIMBER) {
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
