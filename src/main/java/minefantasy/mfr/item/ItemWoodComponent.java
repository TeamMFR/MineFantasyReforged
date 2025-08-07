package minefantasy.mfr.item;

import minefantasy.mfr.registry.material.CustomMaterial;
import minefantasy.mfr.registry.material.CustomMaterialRegistry;
import minefantasy.mfr.registry.material.types.CustomMaterialType;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;

public class ItemWoodComponent extends ItemComponentMFR {
	public ItemWoodComponent(String name) {
		super(name, CustomMaterialType.WOOD_MATERIAL);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {

			ArrayList<CustomMaterial> woods = CustomMaterialRegistry.getList(CustomMaterialType.WOOD_MATERIAL);
			for (CustomMaterial wood : woods) {
				items.add(CustomToolHelper.constructMainSlot(this, wood.getName()));
			}
		}
	}
}
