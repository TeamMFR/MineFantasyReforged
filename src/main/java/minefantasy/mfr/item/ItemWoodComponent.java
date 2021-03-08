package minefantasy.mfr.item;

import minefantasy.mfr.material.CustomMaterial;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;

public class ItemWoodComponent extends ItemComponentMFR {
	public ItemWoodComponent(String name) {
		super(name);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {

			ArrayList<CustomMaterial> woods = CustomMaterial.getList("wood");
			for (CustomMaterial wood : woods) {
				items.add(this.construct(wood.name));
			}
		}
	}
}
