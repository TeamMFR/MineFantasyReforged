package minefantasy.mfr.material;

import com.google.common.base.CaseFormat;
import minefantasy.mfr.api.MineFantasyReforgedAPI;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.util.MFRLogUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class MetalMaterial extends CustomMaterial {

	public String oreDictList;

	public MetalMaterial(String name, int tier, float hardness, float durability, float flexibility, float sharpness, float resistance, float density, float[] armourProtection, int[] color, String oreDictList) {
		super(name, "metal", tier, hardness, durability, flexibility, resistance, sharpness, density, armourProtection, color);
		this.oreDictList = oreDictList;
		setArmourStats(1.0F, flexibility, 1F / flexibility);// Harder materials absorb blunt less but resist cutting and piercing more

		// Adding this is necessary to preserve the old system where defaults are dynamically calculated above with setArmourStats and non-default values take precedence over the calculated values
		// old formula: hardness = ((sharpness + 5F) / 2F) - 1F;
		for (float value : armourProtection) {
			if (value != 1.0) {
				setArmourStats(armourProtection[0], armourProtection[1], armourProtection[2]);
				break;
			}
		}
	}

	public static void addHeatables() {
		ArrayList<CustomMaterial> metal = CustomMaterial.getList("metal");
		for (CustomMaterial customMat : metal) {
			int[] stats = customMat.getHeatableStats();
			MFRLogUtil.logDebug("Set Heatable Stats for " + customMat.name + ": " + stats[0] + "," + stats[1] + "," + stats[2]);

			MineFantasyReforgedAPI.setHeatableStats(((MetalMaterial)customMat).oreDictList, stats[0], stats[1], stats[2]);
			MineFantasyReforgedAPI.setHeatableStats("hunk" + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, customMat.name), stats[0], stats[1], stats[2]);
		}

		MineFantasyReforgedAPI.setHeatableStats(MineFantasyItems.RIVET, 1000, 2000, 3000);
		MineFantasyReforgedAPI.setHeatableStats(MineFantasyItems.METAL_HUNK, -1, -1, -1);
		MineFantasyReforgedAPI.setHeatableStats(MineFantasyItems.BAR, -1, -1, -1);
	}

	@Override
	public ItemStack getItemStack() {
		NonNullList<ItemStack> list = OreDictionary.getOres(oreDictList);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return ItemStack.EMPTY;
	}
}
