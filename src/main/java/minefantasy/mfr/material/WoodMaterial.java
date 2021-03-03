package minefantasy.mfr.material;

import com.google.common.base.CaseFormat;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class WoodMaterial extends CustomMaterial {

	public WoodMaterial(String name, int tier, float hardness, float durability, float flexibility, float resistance, float density, int[] color) {
		super(name, "wood", tier, hardness, durability, flexibility, resistance, 0F, density, color);
	}

	@Override
	public String getMaterialString() {
		return I18n.format("materialtype." + this.type + ".name", this.tier);
	}

	@Override
	public ItemStack getItemStack() {
		NonNullList<ItemStack> list = OreDictionary.getOres("planks" + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name));
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return ItemStack.EMPTY;
	}

}
