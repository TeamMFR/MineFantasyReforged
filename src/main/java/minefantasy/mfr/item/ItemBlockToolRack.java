package minefantasy.mfr.item;

import minefantasy.mfr.api.crafting.IMaterialSingleComponent;
import minefantasy.mfr.registry.material.CustomMaterial;
import minefantasy.mfr.registry.material.CustomMaterialRegistry;
import minefantasy.mfr.registry.material.types.CustomMaterialType;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;

public class ItemBlockToolRack extends ItemBlockBase implements IMaterialSingleComponent {
	public ItemBlockToolRack(Block base) {
		super(base);
	}

	@Override
	public void getSubItems(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		if (!isInCreativeTab(itemIn)) {
			return;
		}
		ArrayList<CustomMaterial> wood = CustomMaterialRegistry.getList(CustomMaterialType.WOOD_MATERIAL);
		for (CustomMaterial customMat : wood) {
			items.add(CustomToolHelper.constructMainSlot(this, customMat.getName()));
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack item) {
		return CustomToolHelper.getLocalisedName(item, this.getUnlocalizedNameInefficiently(item) + ".name");
	}

	@Override
	public CustomMaterialType getMaterialType() {
		return CustomMaterialType.WOOD_MATERIAL;
	}
}
