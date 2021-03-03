package minefantasy.mfr.recipe;

import minefantasy.mfr.api.crafting.Salvage;
import minefantasy.mfr.block.BlockWoodDecor;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.item.ItemComponentMFR;
import minefantasy.mfr.material.CustomMaterial;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class CustomWoodRecipes {
	public static final String basic = "step.wood";

	public static void init() {
		ArrayList<CustomMaterial> wood = CustomMaterial.getList("wood");
		for (CustomMaterial customMat : wood) {
			addRecipes(customMat);
		}
	}

	private static void addRecipes(CustomMaterial material) {
		ItemStack timber = MineFantasyItems.TIMBER.construct(material.name);
		ItemStack cutPlank = ((ItemComponentMFR) MineFantasyItems.TIMBER_CUT).construct(material.name);
		ItemStack woodpane = ((ItemComponentMFR) MineFantasyItems.TIMBER_PANE).construct(material.name);

		ItemStack result;

		result = ((BlockWoodDecor) MineFantasyBlocks.TOOL_RACK_WOOD).construct(material.name);
		Salvage.addSalvage(result, cutPlank, cutPlank, cutPlank, timber, timber, timber, timber,
				new ItemStack(MineFantasyItems.NAIL, 2));

		result = MineFantasyBlocks.FOOD_BOX_BASIC.construct(material.name);
		Salvage.addSalvage(result, cutPlank, cutPlank, cutPlank, cutPlank, new ItemStack(MineFantasyItems.NAIL, 2), MineFantasyItems.HINGE);

		result = MineFantasyBlocks.AMMO_BOX_BASIC.construct(material.name);
		Salvage.addSalvage(result, cutPlank, cutPlank, woodpane, woodpane, new ItemStack(MineFantasyItems.NAIL, 2), new ItemStack(MineFantasyItems.HINGE, 2));

		result = MineFantasyBlocks.CRATE_BASIC.construct(material.name);
		Salvage.addSalvage(result, woodpane, woodpane, woodpane, woodpane, woodpane, woodpane, new ItemStack(MineFantasyItems.NAIL, 4), new ItemStack(MineFantasyItems.HINGE, 2));

		result = ((BlockWoodDecor) MineFantasyBlocks.TROUGH_WOOD).construct(material.name);
		Salvage.addSalvage(result, timber, timber, timber, timber, timber, new ItemStack(MineFantasyItems.NAIL, 3));

	}

}
