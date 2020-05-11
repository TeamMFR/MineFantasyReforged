package minefantasy.mfr.item.gadget;

import minefantasy.mfr.api.crafting.ISpecialSalvage;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.ToolListMFR;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemCrudeBomb extends ItemBomb implements ISpecialSalvage {

    public ItemCrudeBomb(String name) {
        super(name);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack item) {
        return 20;
    }

    @Override
    public String getUnlocalizedName(ItemStack item) {
        return "item.bomb_crude";
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        items.add(new ItemStack(this));
    }

    @Override
    public EnumRarity getRarity(ItemStack item) {
        return ToolListMFR.POOR;
    }

    @Override
    public Object[] getSalvage(ItemStack item) {
        return new Object[]{Items.PAPER, ComponentListMFR.THREAD, ComponentListMFR.BLACKPOWDER};
    }

    @Override
    public byte getItemFuse(byte value) {
        return 0;
    }

    @Override
    public byte getItemFilling(byte value) {
        return 0;
    }

    @Override
    public byte getItemCasing(byte value) {
        return -1;
    }

    @Override
    public byte getItemPowder(byte value) {
        return 0;
    }
}