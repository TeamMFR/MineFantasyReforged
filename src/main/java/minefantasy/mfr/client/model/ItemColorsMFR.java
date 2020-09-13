package minefantasy.mfr.client.model;

import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.init.BlockListMFR;
import minefantasy.mfr.init.CustomToolListMFR;
import minefantasy.mfr.init.DragonforgedStyle;
import minefantasy.mfr.init.OrnateStyle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.world.ColorizerFoliage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ItemColorsMFR {
	private ItemColorsMFR() {} // no instances!

	public static void init() {
		ItemColors itemColors = Minecraft.getMinecraft().getItemColors();
		IItemColor itemColor = (stack, tintIndex) -> {
			if (tintIndex == 0) {
				return CustomToolHelper.getColourFromItemStack(stack, tintIndex);
			}
			if (tintIndex == 1) {
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

		// Dragonforged tools
		itemColors.registerItemColorHandler(itemColor, DragonforgedStyle.DRAGONFORGED_AXE);
		itemColors.registerItemColorHandler(itemColor, DragonforgedStyle.DRAGONFORGED_HAMMER);
		itemColors.registerItemColorHandler(itemColor, DragonforgedStyle.DRAGONFORGED_HANDPICK);
		itemColors.registerItemColorHandler(itemColor, DragonforgedStyle.DRAGONFORGED_HOE);
		itemColors.registerItemColorHandler(itemColor, DragonforgedStyle.DRAGONFORGED_HVYPICK);
		itemColors.registerItemColorHandler(itemColor, DragonforgedStyle.DRAGONFORGED_HVYSHOVEL);
		itemColors.registerItemColorHandler(itemColor, DragonforgedStyle.DRAGONFORGED_KNIFE);
		itemColors.registerItemColorHandler(itemColor, DragonforgedStyle.DRAGONFORGED_LUMBER);
		itemColors.registerItemColorHandler(itemColor, DragonforgedStyle.DRAGONFORGED_MATTOCK);
		itemColors.registerItemColorHandler(itemColor, DragonforgedStyle.DRAGONFORGED_NEEDLE);
		itemColors.registerItemColorHandler(itemColor, DragonforgedStyle.DRAGONFORGED_PICK);
		itemColors.registerItemColorHandler(itemColor, DragonforgedStyle.DRAGONFORGED_SAW);
		itemColors.registerItemColorHandler(itemColor, DragonforgedStyle.DRAGONFORGED_SCYTHE);
		itemColors.registerItemColorHandler(itemColor, DragonforgedStyle.DRAGONFORGED_SHEARS);
		itemColors.registerItemColorHandler(itemColor, DragonforgedStyle.DRAGONFORGED_SPADE);
		itemColors.registerItemColorHandler(itemColor, DragonforgedStyle.DRAGONFORGED_SPANNER);
		itemColors.registerItemColorHandler(itemColor, DragonforgedStyle.DRAGONFORGED_TONGS);
		itemColors.registerItemColorHandler(itemColor, DragonforgedStyle.DRAGONFORGED_TROW);

		// Dragonforged Weapons
		itemColors.registerItemColorHandler(itemColor, DragonforgedStyle.DRAGONFORGED_BATTLEAXE);
		itemColors.registerItemColorHandler(itemColor, DragonforgedStyle.DRAGONFORGED_DAGGER);
		itemColors.registerItemColorHandler(itemColor, DragonforgedStyle.DRAGONFORGED_GREATSWORD);
		itemColors.registerItemColorHandler(itemColor, DragonforgedStyle.DRAGONFORGED_HALBEARD);
		itemColors.registerItemColorHandler(itemColor, DragonforgedStyle.DRAGONFORGED_HVYHAMMER);
		itemColors.registerItemColorHandler(itemColor, DragonforgedStyle.DRAGONFORGED_KATANA);
		itemColors.registerItemColorHandler(itemColor, DragonforgedStyle.DRAGONFORGED_LANCE);
		itemColors.registerItemColorHandler(itemColor, DragonforgedStyle.DRAGONFORGED_MACE);
		itemColors.registerItemColorHandler(itemColor, DragonforgedStyle.DRAGONFORGED_SPEAR);
		itemColors.registerItemColorHandler(itemColor, DragonforgedStyle.DRAGONFORGED_SWORD);
		itemColors.registerItemColorHandler(itemColor, DragonforgedStyle.DRAGONFORGED_WARAXE);
		itemColors.registerItemColorHandler(itemColor, DragonforgedStyle.DRAGONFORGED_WARHAMMER);

		// Ornate tools
		itemColors.registerItemColorHandler(itemColor, OrnateStyle.ORNATE_AXE);
		itemColors.registerItemColorHandler(itemColor, OrnateStyle.ORNATE_HAMMER);
		itemColors.registerItemColorHandler(itemColor, OrnateStyle.ORNATE_HANDPICK);
		itemColors.registerItemColorHandler(itemColor, OrnateStyle.ORNATE_HOE);
		itemColors.registerItemColorHandler(itemColor, OrnateStyle.ORNATE_HVYPICK);
		itemColors.registerItemColorHandler(itemColor, OrnateStyle.ORNATE_HVYSHOVEL);
		itemColors.registerItemColorHandler(itemColor, OrnateStyle.ORNATE_KNIFE);
		itemColors.registerItemColorHandler(itemColor, OrnateStyle.ORNATE_LUMBER);
		itemColors.registerItemColorHandler(itemColor, OrnateStyle.ORNATE_MATTOCK);
		itemColors.registerItemColorHandler(itemColor, OrnateStyle.ORNATE_NEEDLE);
		itemColors.registerItemColorHandler(itemColor, OrnateStyle.ORNATE_PICK);
		itemColors.registerItemColorHandler(itemColor, OrnateStyle.ORNATE_SAW);
		itemColors.registerItemColorHandler(itemColor, OrnateStyle.ORNATE_SCYTHE);
		itemColors.registerItemColorHandler(itemColor, OrnateStyle.ORNATE_SHEARS);
		itemColors.registerItemColorHandler(itemColor, OrnateStyle.ORNATE_SPADE);
		itemColors.registerItemColorHandler(itemColor, OrnateStyle.ORNATE_SPANNER);
		itemColors.registerItemColorHandler(itemColor, OrnateStyle.ORNATE_TONGS);
		itemColors.registerItemColorHandler(itemColor, OrnateStyle.ORNATE_TROW);

		// Ornate Weapons
		itemColors.registerItemColorHandler(itemColor, OrnateStyle.ORNATE_BATTLEAXE);
		itemColors.registerItemColorHandler(itemColor, OrnateStyle.ORNATE_DAGGER);
		itemColors.registerItemColorHandler(itemColor, OrnateStyle.ORNATE_GREATSWORD);
		itemColors.registerItemColorHandler(itemColor, OrnateStyle.ORNATE_HALBEARD);
		itemColors.registerItemColorHandler(itemColor, OrnateStyle.ORNATE_HVYHAMMER);
		itemColors.registerItemColorHandler(itemColor, OrnateStyle.ORNATE_KATANA);
		itemColors.registerItemColorHandler(itemColor, OrnateStyle.ORNATE_LANCE);
		itemColors.registerItemColorHandler(itemColor, OrnateStyle.ORNATE_MACE);
		itemColors.registerItemColorHandler(itemColor, OrnateStyle.ORNATE_SPEAR);
		itemColors.registerItemColorHandler(itemColor, OrnateStyle.ORNATE_SWORD);
		itemColors.registerItemColorHandler(itemColor, OrnateStyle.ORNATE_WARAXE);
		itemColors.registerItemColorHandler(itemColor, OrnateStyle.ORNATE_WARHAMMER);
	}

}
