package minefantasy.mfr.item;

import net.minecraftforge.fml.common.IFuelHandler;
import minefantasy.mfr.api.crafting.MineFantasyFuels;
import minefantasy.mfr.init.ComponentListMFR;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class AdvancedFuelHandlerMF implements IFuelHandler {

    public static void registerItems() {
        MineFantasyFuels.addCarbon(new ItemStack(Items.COAL, 1, OreDictionary.WILDCARD_VALUE), 1);
        MineFantasyFuels.addCarbon(ComponentListMFR.COKE, 1);
        MineFantasyFuels.addCarbon(ComponentListMFR.COAL_FLUX, 1);
        MineFantasyFuels.addCarbon(Blocks.COAL_BLOCK, 9);
    }

    @Override
    public int getBurnTime(ItemStack fuel) {
        if (fuel == null) {
            return 0;
        }
        if (fuel.getItem() == ComponentListMFR.COAL_DUST) {
            return 300;// 15s
        }
        if (fuel.getItem() == ComponentListMFR.COKE) {
            return 2400;
        }
        if (fuel.getItem() == Items.COAL) {
            return 1200;
        }
        if (fuel.getItem() == Item.getItemFromBlock(Blocks.COAL_BLOCK)) {
            return 10800;
        }
        return 0;
    }

}
