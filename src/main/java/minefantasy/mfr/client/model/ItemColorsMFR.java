package minefantasy.mfr.client.model;

import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.init.BlockListMFR;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.CustomArmourListMFR;
import minefantasy.mfr.init.CustomToolListMFR;
import minefantasy.mfr.init.DragonforgedStyle;
import minefantasy.mfr.init.LeatherArmourListMFR;
import minefantasy.mfr.init.OrnateStyle;
import minefantasy.mfr.init.ToolListMFR;
import minefantasy.mfr.item.armour.ItemArmourMFR;
import minefantasy.mfr.item.heatable.ItemHeated;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.potion.PotionUtils;
import net.minecraft.world.ColorizerFoliage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ItemColorsMFR {
	private ItemColorsMFR() {} // no instances!

	public static void init() {
		ItemColors itemColors = Minecraft.getMinecraft().getItemColors();
		IItemColor itemColorForOneLayer = (stack, tintIndex) -> {
			if (tintIndex == 0) {
				return CustomToolHelper.getColourFromItemStack(stack, tintIndex);
			}
			return 0xFFFFFF;
		};
		IItemColor itemColorForTwoLayers = (stack, tintIndex) -> {
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
		itemColors.registerItemColorHandler(((stack, tintIndex) -> tintIndex = ColorizerFoliage.getFoliageColorBasic()), BlockListMFR.BERRY_BUSH);
		itemColors.registerItemColorHandler(((stack, tintIndex) -> tintIndex = tintIndex == 1 ? PotionUtils.getColor(stack) : -1 ), ToolListMFR.SYRINGE);

		// Standard Tools
		itemColors.registerItemColorHandler(itemColorForTwoLayers, CustomToolListMFR.STANDARD_PICK);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, CustomToolListMFR.STANDARD_AXE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, CustomToolListMFR.STANDARD_SPADE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, CustomToolListMFR.STANDARD_HOE);
		itemColors.registerItemColorHandler(itemColorForOneLayer, CustomToolListMFR.STANDARD_SHEARS);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, CustomToolListMFR.STANDARD_HAMMER);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, CustomToolListMFR.STANDARD_HANDPICK);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, CustomToolListMFR.STANDARD_HVYHAMMER);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, CustomToolListMFR.STANDARD_HVYPICK);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, CustomToolListMFR.STANDARD_HVYSHOVEL);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, CustomToolListMFR.STANDARD_KNIFE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, CustomToolListMFR.STANDARD_MATTOCK);
		itemColors.registerItemColorHandler(itemColorForOneLayer, CustomToolListMFR.STANDARD_NEEDLE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, CustomToolListMFR.STANDARD_SAW);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, CustomToolListMFR.STANDARD_SCYTHE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, CustomToolListMFR.STANDARD_SPANNER);
		itemColors.registerItemColorHandler(itemColorForOneLayer, CustomToolListMFR.STANDARD_TONGS);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, CustomToolListMFR.STANDARD_TROW);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, CustomToolListMFR.STANDARD_SPOON);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, CustomToolListMFR.STANDARD_MALLET);

		//Standard Weapons
		itemColors.registerItemColorHandler(itemColorForTwoLayers, CustomToolListMFR.STANDARD_SWORD);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, CustomToolListMFR.STANDARD_BATTLEAXE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, CustomToolListMFR.STANDARD_DAGGER);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, CustomToolListMFR.STANDARD_GREATSWORD);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, CustomToolListMFR.STANDARD_HALBEARD);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, CustomToolListMFR.STANDARD_KATANA);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, CustomToolListMFR.STANDARD_LANCE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, CustomToolListMFR.STANDARD_LUMBER);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, CustomToolListMFR.STANDARD_MACE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, CustomToolListMFR.STANDARD_SPEAR);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, CustomToolListMFR.STANDARD_WARAXE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, CustomToolListMFR.STANDARD_WARHAMMER);

		itemColors.registerItemColorHandler(itemColorForTwoLayers, CustomToolListMFR.STANDARD_BOW);
		itemColors.registerItemColorHandler(itemColorForOneLayer, CustomToolListMFR.STANDARD_ARROW);
		itemColors.registerItemColorHandler(itemColorForOneLayer, CustomToolListMFR.STANDARD_ARROW_BODKIN);
		itemColors.registerItemColorHandler(itemColorForOneLayer, CustomToolListMFR.STANDARD_ARROW_BROAD);
		itemColors.registerItemColorHandler(itemColorForOneLayer, CustomToolListMFR.STANDARD_BOLT);

		// Dragonforged tools
		itemColors.registerItemColorHandler(itemColorForTwoLayers, DragonforgedStyle.DRAGONFORGED_AXE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, DragonforgedStyle.DRAGONFORGED_HAMMER);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, DragonforgedStyle.DRAGONFORGED_HANDPICK);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, DragonforgedStyle.DRAGONFORGED_HOE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, DragonforgedStyle.DRAGONFORGED_HVYPICK);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, DragonforgedStyle.DRAGONFORGED_HVYSHOVEL);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, DragonforgedStyle.DRAGONFORGED_KNIFE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, DragonforgedStyle.DRAGONFORGED_LUMBER);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, DragonforgedStyle.DRAGONFORGED_MATTOCK);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, DragonforgedStyle.DRAGONFORGED_NEEDLE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, DragonforgedStyle.DRAGONFORGED_PICK);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, DragonforgedStyle.DRAGONFORGED_SAW);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, DragonforgedStyle.DRAGONFORGED_SCYTHE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, DragonforgedStyle.DRAGONFORGED_SHEARS);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, DragonforgedStyle.DRAGONFORGED_SPADE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, DragonforgedStyle.DRAGONFORGED_SPANNER);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, DragonforgedStyle.DRAGONFORGED_TONGS);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, DragonforgedStyle.DRAGONFORGED_TROW);

		// Dragonforged Weapons
		itemColors.registerItemColorHandler(itemColorForTwoLayers, DragonforgedStyle.DRAGONFORGED_BATTLEAXE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, DragonforgedStyle.DRAGONFORGED_DAGGER);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, DragonforgedStyle.DRAGONFORGED_GREATSWORD);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, DragonforgedStyle.DRAGONFORGED_HALBEARD);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, DragonforgedStyle.DRAGONFORGED_HVYHAMMER);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, DragonforgedStyle.DRAGONFORGED_KATANA);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, DragonforgedStyle.DRAGONFORGED_LANCE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, DragonforgedStyle.DRAGONFORGED_MACE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, DragonforgedStyle.DRAGONFORGED_SPEAR);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, DragonforgedStyle.DRAGONFORGED_SWORD);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, DragonforgedStyle.DRAGONFORGED_WARAXE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, DragonforgedStyle.DRAGONFORGED_WARHAMMER);

		itemColors.registerItemColorHandler(itemColorForTwoLayers, DragonforgedStyle.DRAGONFORGED_BOW);

		// Ornate tools
		itemColors.registerItemColorHandler(itemColorForTwoLayers, OrnateStyle.ORNATE_AXE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, OrnateStyle.ORNATE_HAMMER);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, OrnateStyle.ORNATE_HANDPICK);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, OrnateStyle.ORNATE_HOE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, OrnateStyle.ORNATE_HVYPICK);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, OrnateStyle.ORNATE_HVYSHOVEL);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, OrnateStyle.ORNATE_KNIFE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, OrnateStyle.ORNATE_LUMBER);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, OrnateStyle.ORNATE_MATTOCK);
		itemColors.registerItemColorHandler(itemColorForOneLayer, OrnateStyle.ORNATE_NEEDLE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, OrnateStyle.ORNATE_PICK);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, OrnateStyle.ORNATE_SAW);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, OrnateStyle.ORNATE_SCYTHE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, OrnateStyle.ORNATE_SHEARS);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, OrnateStyle.ORNATE_SPADE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, OrnateStyle.ORNATE_SPANNER);
		itemColors.registerItemColorHandler(itemColorForOneLayer, OrnateStyle.ORNATE_TONGS);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, OrnateStyle.ORNATE_TROW);

		// Ornate Weapons
		itemColors.registerItemColorHandler(itemColorForTwoLayers, OrnateStyle.ORNATE_BATTLEAXE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, OrnateStyle.ORNATE_DAGGER);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, OrnateStyle.ORNATE_GREATSWORD);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, OrnateStyle.ORNATE_HALBEARD);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, OrnateStyle.ORNATE_HVYHAMMER);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, OrnateStyle.ORNATE_KATANA);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, OrnateStyle.ORNATE_LANCE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, OrnateStyle.ORNATE_MACE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, OrnateStyle.ORNATE_SPEAR);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, OrnateStyle.ORNATE_SWORD);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, OrnateStyle.ORNATE_WARAXE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, OrnateStyle.ORNATE_WARHAMMER);

		itemColors.registerItemColorHandler(itemColorForTwoLayers, OrnateStyle.ORNATE_BOW);


		/// ARMOUR ITEMS

		// Standard armour
		itemColors.registerItemColorHandler(itemColorForOneLayer,  CustomArmourListMFR.STANDARD_SCALE_HELMET);
		itemColors.registerItemColorHandler(itemColorForOneLayer,  CustomArmourListMFR.STANDARD_SCALE_CHEST);
		itemColors.registerItemColorHandler(itemColorForOneLayer,  CustomArmourListMFR.STANDARD_SCALE_LEGS);
		itemColors.registerItemColorHandler(itemColorForOneLayer,  CustomArmourListMFR.STANDARD_SCALE_BOOTS);

		itemColors.registerItemColorHandler(itemColorForOneLayer,  CustomArmourListMFR.STANDARD_CHAIN_HELMET);
		itemColors.registerItemColorHandler(itemColorForOneLayer,  CustomArmourListMFR.STANDARD_CHAIN_CHEST);
		itemColors.registerItemColorHandler(itemColorForOneLayer,  CustomArmourListMFR.STANDARD_CHAIN_LEGS);
		itemColors.registerItemColorHandler(itemColorForOneLayer,  CustomArmourListMFR.STANDARD_CHAIN_BOOTS);

		itemColors.registerItemColorHandler(itemColorForOneLayer,  CustomArmourListMFR.STANDARD_SPLINT_HELMET);
		itemColors.registerItemColorHandler(itemColorForOneLayer,  CustomArmourListMFR.STANDARD_SPLINT_CHEST);
		itemColors.registerItemColorHandler(itemColorForOneLayer,  CustomArmourListMFR.STANDARD_SPLINT_LEGS);
		itemColors.registerItemColorHandler(itemColorForOneLayer,  CustomArmourListMFR.STANDARD_SPLINT_BOOTS);

		itemColors.registerItemColorHandler(itemColorForOneLayer,  CustomArmourListMFR.STANDARD_PLATE_HELMET);
		itemColors.registerItemColorHandler(itemColorForOneLayer,  CustomArmourListMFR.STANDARD_PLATE_CHEST);
		itemColors.registerItemColorHandler(itemColorForOneLayer,  CustomArmourListMFR.STANDARD_PLATE_LEGS);
		itemColors.registerItemColorHandler(itemColorForOneLayer,  CustomArmourListMFR.STANDARD_PLATE_BOOTS);

		// Ornate armour
		itemColors.registerItemColorHandler(itemColorForOneLayer, OrnateStyle.ORNATE_SCALE_HELMET);
		itemColors.registerItemColorHandler(itemColorForOneLayer, OrnateStyle.ORNATE_SCALE_CHEST);
		itemColors.registerItemColorHandler(itemColorForOneLayer, OrnateStyle.ORNATE_SCALE_LEGS);
		itemColors.registerItemColorHandler(itemColorForOneLayer, OrnateStyle.ORNATE_SCALE_BOOTS);

		itemColors.registerItemColorHandler(itemColorForOneLayer, OrnateStyle.ORNATE_CHAIN_HELMET);
		itemColors.registerItemColorHandler(itemColorForOneLayer, OrnateStyle.ORNATE_CHAIN_CHEST);
		itemColors.registerItemColorHandler(itemColorForOneLayer, OrnateStyle.ORNATE_CHAIN_LEGS);
		itemColors.registerItemColorHandler(itemColorForOneLayer, OrnateStyle.ORNATE_CHAIN_BOOTS);

		itemColors.registerItemColorHandler(itemColorForOneLayer, OrnateStyle.ORNATE_SPLINT_HELMET);
		itemColors.registerItemColorHandler(itemColorForOneLayer, OrnateStyle.ORNATE_SPLINT_CHEST);
		itemColors.registerItemColorHandler(itemColorForOneLayer, OrnateStyle.ORNATE_SPLINT_LEGS);
		itemColors.registerItemColorHandler(itemColorForOneLayer, OrnateStyle.ORNATE_SPLINT_BOOTS);

		itemColors.registerItemColorHandler(itemColorForOneLayer, OrnateStyle.ORNATE_PLATE_HELMET);
		itemColors.registerItemColorHandler(itemColorForOneLayer, OrnateStyle.ORNATE_PLATE_CHEST);
		itemColors.registerItemColorHandler(itemColorForOneLayer, OrnateStyle.ORNATE_PLATE_LEGS);
		itemColors.registerItemColorHandler(itemColorForOneLayer, OrnateStyle.ORNATE_PLATE_BOOTS);

		// Dragonforge armour
		itemColors.registerItemColorHandler(itemColorForOneLayer, DragonforgedStyle.DRAGONFORGED_SCALE_HELMET);
		itemColors.registerItemColorHandler(itemColorForOneLayer, DragonforgedStyle.DRAGONFORGED_SCALE_CHEST);
		itemColors.registerItemColorHandler(itemColorForOneLayer, DragonforgedStyle.DRAGONFORGED_SCALE_LEGS);
		itemColors.registerItemColorHandler(itemColorForOneLayer, DragonforgedStyle.DRAGONFORGED_SCALE_BOOTS);

		itemColors.registerItemColorHandler(itemColorForOneLayer, DragonforgedStyle.DRAGONFORGED_CHAIN_HELMET);
		itemColors.registerItemColorHandler(itemColorForOneLayer, DragonforgedStyle.DRAGONFORGED_CHAIN_CHEST);
		itemColors.registerItemColorHandler(itemColorForOneLayer, DragonforgedStyle.DRAGONFORGED_CHAIN_LEGS);
		itemColors.registerItemColorHandler(itemColorForOneLayer, DragonforgedStyle.DRAGONFORGED_CHAIN_BOOTS);

		itemColors.registerItemColorHandler(itemColorForOneLayer, DragonforgedStyle.DRAGONFORGED_SPLINT_HELMET);
		itemColors.registerItemColorHandler(itemColorForOneLayer, DragonforgedStyle.DRAGONFORGED_SPLINT_CHEST);
		itemColors.registerItemColorHandler(itemColorForOneLayer, DragonforgedStyle.DRAGONFORGED_SPLINT_LEGS);
		itemColors.registerItemColorHandler(itemColorForOneLayer, DragonforgedStyle.DRAGONFORGED_SPLINT_BOOTS);

		itemColors.registerItemColorHandler(itemColorForOneLayer, DragonforgedStyle.DRAGONFORGED_PLATE_HELMET);
		itemColors.registerItemColorHandler(itemColorForOneLayer, DragonforgedStyle.DRAGONFORGED_PLATE_CHEST);
		itemColors.registerItemColorHandler(itemColorForOneLayer, DragonforgedStyle.DRAGONFORGED_PLATE_LEGS);
		itemColors.registerItemColorHandler(itemColorForOneLayer, DragonforgedStyle.DRAGONFORGED_PLATE_BOOTS);

		//Leather Armours
		itemColors.registerItemColorHandler(((stack, tintIndex) -> tintIndex == 0 ? ((ItemArmourMFR) stack.getItem()).getColor(stack) : 0xFFFFFF), LeatherArmourListMFR.LEATHER);
		itemColors.registerItemColorHandler(((stack, tintIndex) -> tintIndex == 0 ? ((ItemArmourMFR) stack.getItem()).getColor(stack) : 0xFFFFFF), LeatherArmourListMFR.LEATHER_APRON);


		/// COMMODITIES
		itemColors.registerItemColorHandler(itemColorForOneLayer, ComponentListMFR.TIMBER);
		itemColors.registerItemColorHandler(itemColorForOneLayer, ComponentListMFR.TIMBER_CUT);
		itemColors.registerItemColorHandler(itemColorForOneLayer, ComponentListMFR.TIMBER_PANE);
		itemColors.registerItemColorHandler(itemColorForOneLayer, ComponentListMFR.BAR);

		itemColors.registerItemColorHandler(((stack, tintIndex) -> tintIndex == 0 ? ((ItemHeated) stack.getItem()).getColorFromItemStack(stack) : 0xFFFFFF), ComponentListMFR.HOT_ITEM);

		itemColors.registerItemColorHandler(itemColorForOneLayer, ComponentListMFR.COGWORK_ARMOUR);

		itemColors.registerItemColorHandler(itemColorForOneLayer, ComponentListMFR.CHAIN_MESH);
		itemColors.registerItemColorHandler(itemColorForOneLayer, ComponentListMFR.SCALE_MESH);
		itemColors.registerItemColorHandler(itemColorForOneLayer, ComponentListMFR.SPLINT_MESH);

		itemColors.registerItemColorHandler(itemColorForOneLayer, ComponentListMFR.METAL_HUNK);

		itemColors.registerItemColorHandler(itemColorForOneLayer, ComponentListMFR.PLATE);
		itemColors.registerItemColorHandler(itemColorForOneLayer, ComponentListMFR.PLATE_HUGE);

		itemColors.registerItemColorHandler(itemColorForOneLayer, ComponentListMFR.ARROWHEAD);
		itemColors.registerItemColorHandler(itemColorForOneLayer, ComponentListMFR.BROAD_HEAD);
		itemColors.registerItemColorHandler(itemColorForOneLayer, ComponentListMFR.BODKIN_HEAD);
	}

}
