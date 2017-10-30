package minefantasy.mf2.item;

import cpw.mods.fml.common.IFuelHandler;
import minefantasy.mf2.api.crafting.MineFantasyFuels;
import minefantasy.mf2.item.list.ComponentListMF;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class AdvancedFuelHandlerMF implements IFuelHandler {

    public static void registerItems() {
        MineFantasyFuels.addCarbon(new ItemStack(Items.coal, 1, OreDictionary.WILDCARD_VALUE), 1);
        MineFantasyFuels.addCarbon(ComponentListMF.coke, 1);
        MineFantasyFuels.addCarbon(ComponentListMF.coal_flux, 1);
        MineFantasyFuels.addCarbon(Blocks.coal_block, 9);
    }

    @Override
    public int getBurnTime(ItemStack fuel) {
        if (fuel == null) {
            return 0;
        }
        if (fuel.getItem() == ComponentListMF.coalDust) {
            return 300;// 15s
        }
        if (fuel.getItem() == ComponentListMF.coke) {
            return 2400;
        }
        if (fuel.getItem() == Items.coal) {
            return 1200;
        }
        if (fuel.getItem() == Item.getItemFromBlock(Blocks.coal_block)) {
            return 10800;
        }
        return 0;
    }

}
