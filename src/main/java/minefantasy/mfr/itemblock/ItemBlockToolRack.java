package minefantasy.mfr.itemblock;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.material.CustomMaterial;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Iterator;

public class ItemBlockToolRack extends ItemBlockBase {
    public ItemBlockToolRack(Block base) {
        super(base);
    }

    @Override
    public void getSubItems(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        if (!isInCreativeTab(itemIn)) {
            return;
        }
        if (MineFantasyReborn.isDebug()) {
            ArrayList<CustomMaterial> wood = CustomMaterial.getList("wood");
            Iterator iteratorWood = wood.iterator();
            while (iteratorWood.hasNext()) {
                CustomMaterial customMat = (CustomMaterial) iteratorWood.next();
                items.add(this.construct(customMat.name));
            }
        } else {
            items.add(this.construct("RefinedWood"));
        }
    }

    private ItemStack construct(String name) {
        return CustomToolHelper.constructSingleColoredLayer(this, name, 1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack item) {
        return CustomToolHelper.getLocalisedName(item, this.getUnlocalizedNameInefficiently(item) + ".name");
    }
}
