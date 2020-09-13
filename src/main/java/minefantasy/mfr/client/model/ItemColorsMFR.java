package minefantasy.mfr.client.model;

import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.init.BlockListMFR;
import minefantasy.mfr.init.CustomToolListMFR;
import minefantasy.mfr.init.ToolListMFR;
import minefantasy.mfr.item.tool.ItemPickMF;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemBlock;
import net.minecraft.world.ColorizerFoliage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ItemColorsMFR {
	private ItemColorsMFR() {} // no instances!

	public static void init() {
		ItemColors itemColors = Minecraft.getMinecraft().getItemColors();
		IItemColor itemColor = (stack, tintIndex) -> {
			if (tintIndex == 0){
				return CustomToolHelper.getColourFromItemStack(stack, tintIndex);
			}
			if (tintIndex == 1){
				return CustomToolHelper.getColourFromItemStack(stack, tintIndex);
			}
			return 0xFFFFFF;
		};

		// Leaf ItemBlock Coloration
		itemColors.registerItemColorHandler(((stack, tintIndex) -> tintIndex = ColorizerFoliage.getFoliageColorBasic()), BlockListMFR.LEAVES_YEW);
		itemColors.registerItemColorHandler(((stack, tintIndex) -> tintIndex = ColorizerFoliage.getFoliageColorBasic()), BlockListMFR.LEAVES_IRONBARK);
		itemColors.registerItemColorHandler(((stack, tintIndex) -> tintIndex = ColorizerFoliage.getFoliageColorBasic()), BlockListMFR.LEAVES_EBONY);

		// Standard Tools
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_PICK);
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_AXE);
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_SPADE);
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_HOE);
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_SHEARS);
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_HAMMER);
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_HANDPICK);
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_HVYHAMMER);
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_HVYPICK);
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_HVYSHOVEL);
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_KNIFE);
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_MATTOCK);
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_NEEDLE);
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_SAW);
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_SCYTHE);
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_SHEARS);
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_SPANNER);
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_TONGS);
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_TROW);
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_SPOON);
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_MALLET);

		//Standard Weapons
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_SWORD);
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_BATTLEAXE);
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_DAGGER);
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_GREATSWORD);
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_HALBEARD);
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_KATANA);
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_LANCE);
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_LUMBER);
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_MACE);
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_SPEAR);
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_WARAXE);
		itemColors.registerItemColorHandler(itemColor, CustomToolListMFR.STANDARD_WARHAMMER);
	}
}
