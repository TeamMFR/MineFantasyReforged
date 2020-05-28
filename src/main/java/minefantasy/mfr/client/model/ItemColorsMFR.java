package minefantasy.mfr.client.model;

import minefantasy.mfr.init.BlockListMFR;
import minefantasy.mfr.init.FoodListMFR;
import minefantasy.mfr.init.ToolListMFR;
import minefantasy.mfr.item.tool.ItemAxe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ColorizerFoliage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ItemColorsMFR {
	private ItemColorsMFR() {} // no instances!

	public static void init() {
		ItemColors itemColors = Minecraft.getMinecraft().getItemColors();

		// Leaf ItemBlock Coloration
		itemColors.registerItemColorHandler(((stack, tintIndex) -> tintIndex = ColorizerFoliage.getFoliageColorBasic()), BlockListMFR.LEAVES_YEW);
		itemColors.registerItemColorHandler(((stack, tintIndex) -> tintIndex = ColorizerFoliage.getFoliageColorBasic()), BlockListMFR.LEAVES_IRONBARK);
		itemColors.registerItemColorHandler(((stack, tintIndex) -> tintIndex = ColorizerFoliage.getFoliageColorBasic()), BlockListMFR.LEAVES_EBONY);

		// TODO just an example, should be removed later
		itemColors.registerItemColorHandler((stack, tintIndex) -> tintIndex == 0 ? 0xEF5757 : 0xFF00FF, ToolListMFR.STONE_SWORD);
	}
}
