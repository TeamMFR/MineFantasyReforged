package minefantasy.mfr.client.model;

import minefantasy.mfr.init.LeatherArmourListMFR;
import minefantasy.mfr.init.MineFantasyBlocks;
import minefantasy.mfr.init.MineFantasyItems;
import minefantasy.mfr.item.ItemArmourMFR;
import minefantasy.mfr.item.ItemHeated;
import minefantasy.mfr.util.CustomToolHelper;
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
		IItemColor itemColorForArmor = (stack, tintIndex) -> {
			if (tintIndex == 0) {
				if (stack.getItem() instanceof ItemArmourMFR) {
					ItemArmourMFR itemArmourMFR = (ItemArmourMFR) stack.getItem();
					if (itemArmourMFR.hasColor(stack)) {
						return itemArmourMFR.getColor(stack);
					} else {
						return CustomToolHelper.getColourFromItemStack(stack, tintIndex);
					}
				}
			}
			return 0xFFFFFF;
		};

		/// TOOL ITEMS

		// Standard Tools
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.STANDARD_PICK);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.STANDARD_AXE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.STANDARD_SPADE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.STANDARD_HOE);
		itemColors.registerItemColorHandler(itemColorForOneLayer, MineFantasyItems.STANDARD_SHEARS);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.STANDARD_HAMMER);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.STANDARD_HANDPICK);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.STANDARD_HEAVY_HAMMER);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.STANDARD_HEAVY_PICK);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.STANDARD_HEAVY_SHOVEL);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.STANDARD_KNIFE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.STANDARD_MATTOCK);
		itemColors.registerItemColorHandler(itemColorForOneLayer, MineFantasyItems.STANDARD_NEEDLE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.STANDARD_SAW);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.STANDARD_SCYTHE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.STANDARD_SPANNER);
		itemColors.registerItemColorHandler(itemColorForOneLayer, MineFantasyItems.STANDARD_TONGS);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.STANDARD_TROW);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.STANDARD_SPOON);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.STANDARD_MALLET);

		itemColors.registerItemColorHandler(((stack, tintIndex) -> tintIndex = tintIndex == 1 ? PotionUtils.getColor(stack) : -1), MineFantasyItems.SYRINGE);

		//Standard Weapons
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.STANDARD_SWORD);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.STANDARD_BATTLEAXE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.STANDARD_DAGGER);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.STANDARD_GREATSWORD);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.STANDARD_HALBEARD);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.STANDARD_KATANA);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.STANDARD_LANCE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.STANDARD_LUMBER);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.STANDARD_MACE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.STANDARD_SPEAR);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.STANDARD_WARAXE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.STANDARD_WARHAMMER);

		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.STANDARD_BOW);
		itemColors.registerItemColorHandler(itemColorForOneLayer, MineFantasyItems.STANDARD_ARROW);
		itemColors.registerItemColorHandler(itemColorForOneLayer, MineFantasyItems.STANDARD_ARROW_BODKIN);
		itemColors.registerItemColorHandler(itemColorForOneLayer, MineFantasyItems.STANDARD_ARROW_BROAD);
		itemColors.registerItemColorHandler(itemColorForOneLayer, MineFantasyItems.STANDARD_BOLT);

		// Dragonforged tools
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.DRAGONFORGED_AXE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.DRAGONFORGED_HAMMER);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.DRAGONFORGED_HANDPICK);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.DRAGONFORGED_HOE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.DRAGONFORGED_HEAVY_PICK);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.DRAGONFORGED_HEAVY_SHOVEL);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.DRAGONFORGED_KNIFE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.DRAGONFORGED_LUMBER);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.DRAGONFORGED_MATTOCK);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.DRAGONFORGED_NEEDLE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.DRAGONFORGED_PICK);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.DRAGONFORGED_SAW);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.DRAGONFORGED_SCYTHE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.DRAGONFORGED_SHEARS);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.DRAGONFORGED_SPADE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.DRAGONFORGED_SPANNER);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.DRAGONFORGED_TONGS);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.DRAGONFORGED_TROW);

		// Dragonforged Weapons
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.DRAGONFORGED_BATTLEAXE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.DRAGONFORGED_DAGGER);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.DRAGONFORGED_GREATSWORD);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.DRAGONFORGED_HALBEARD);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.DRAGONFORGED_HEAVY_HAMMER);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.DRAGONFORGED_KATANA);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.DRAGONFORGED_LANCE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.DRAGONFORGED_MACE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.DRAGONFORGED_SPEAR);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.DRAGONFORGED_SWORD);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.DRAGONFORGED_WARAXE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.DRAGONFORGED_WARHAMMER);

		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.DRAGONFORGED_BOW);

		// Ornate tools
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.ORNATE_AXE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.ORNATE_HAMMER);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.ORNATE_HANDPICK);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.ORNATE_HOE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.ORNATE_HEAVY_PICK);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.ORNATE_HEAVY_SHOVEL);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.ORNATE_KNIFE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.ORNATE_LUMBER);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.ORNATE_MATTOCK);
		itemColors.registerItemColorHandler(itemColorForOneLayer, MineFantasyItems.ORNATE_NEEDLE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.ORNATE_PICK);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.ORNATE_SAW);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.ORNATE_SCYTHE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.ORNATE_SHEARS);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.ORNATE_SPADE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.ORNATE_SPANNER);
		itemColors.registerItemColorHandler(itemColorForOneLayer, MineFantasyItems.ORNATE_TONGS);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.ORNATE_TROW);

		// Ornate Weapons
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.ORNATE_BATTLEAXE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.ORNATE_DAGGER);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.ORNATE_GREATSWORD);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.ORNATE_HALBEARD);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.ORNATE_HEAVY_HAMMER);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.ORNATE_KATANA);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.ORNATE_LANCE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.ORNATE_MACE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.ORNATE_SPEAR);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.ORNATE_SWORD);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.ORNATE_WARAXE);
		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.ORNATE_WARHAMMER);

		itemColors.registerItemColorHandler(itemColorForTwoLayers, MineFantasyItems.ORNATE_BOW);

		/// ARMOUR ITEMS

		// Standard armour
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.STANDARD_SCALE_HELMET);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.STANDARD_SCALE_CHESTPLATE);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.STANDARD_SCALE_LEGGINGS);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.STANDARD_SCALE_BOOTS);

		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.STANDARD_CHAIN_HELMET);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.STANDARD_CHAIN_CHESTPLATE);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.STANDARD_CHAIN_LEGGINGS);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.STANDARD_CHAIN_BOOTS);

		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.STANDARD_SPLINT_HELMET);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.STANDARD_SPLINT_CHESTPLATE);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.STANDARD_SPLINT_LEGGINGS);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.STANDARD_SPLINT_BOOTS);

		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.STANDARD_PLATE_HELMET);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.STANDARD_PLATE_CHESTPLATE);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.STANDARD_PLATE_LEGGINGS);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.STANDARD_PLATE_BOOTS);

		// Ornate armour
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.ORNATE_SCALE_HELMET);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.ORNATE_SCALE_CHESTPLATE);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.ORNATE_SCALE_LEGGINGS);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.ORNATE_SCALE_BOOTS);

		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.ORNATE_CHAIN_HELMET);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.ORNATE_CHAIN_CHESTPLATE);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.ORNATE_CHAIN_LEGGINGS);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.ORNATE_CHAIN_BOOTS);

		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.ORNATE_SPLINT_HELMET);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.ORNATE_SPLINT_CHESTPLATE);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.ORNATE_SPLINT_LEGGINGS);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.ORNATE_SPLINT_BOOTS);

		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.ORNATE_PLATE_HELMET);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.ORNATE_PLATE_CHESTPLATE);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.ORNATE_PLATE_LEGGINGS);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.ORNATE_PLATE_BOOTS);

		// Dragonforge armour
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.DRAGONFORGED_SCALE_HELMET);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.DRAGONFORGED_SCALE_CHESTPLATE);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.DRAGONFORGED_SCALE_LEGGINGS);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.DRAGONFORGED_SCALE_BOOTS);

		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.DRAGONFORGED_CHAIN_HELMET);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.DRAGONFORGED_CHAIN_CHESTPLATE);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.DRAGONFORGED_CHAIN_LEGGINGS);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.DRAGONFORGED_CHAIN_BOOTS);

		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.DRAGONFORGED_SPLINT_HELMET);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.DRAGONFORGED_SPLINT_CHESTPLATE);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.DRAGONFORGED_SPLINT_LEGGINGS);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.DRAGONFORGED_SPLINT_BOOTS);

		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.DRAGONFORGED_PLATE_HELMET);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.DRAGONFORGED_PLATE_CHESTPLATE);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.DRAGONFORGED_PLATE_LEGGINGS);
		itemColors.registerItemColorHandler(itemColorForArmor, MineFantasyItems.DRAGONFORGED_PLATE_BOOTS);

		//Leather Armours
		itemColors.registerItemColorHandler(((stack, tintIndex) -> tintIndex == 0 ? ((ItemArmourMFR) stack.getItem()).getColor(stack) : 0xFFFFFF), LeatherArmourListMFR.LEATHER);
		itemColors.registerItemColorHandler(((stack, tintIndex) -> tintIndex == 0 ? ((ItemArmourMFR) stack.getItem()).getColor(stack) : 0xFFFFFF), LeatherArmourListMFR.LEATHER_APRON);

		/// COMMODITIES
		itemColors.registerItemColorHandler(itemColorForOneLayer, MineFantasyItems.TIMBER);
		itemColors.registerItemColorHandler(itemColorForOneLayer, MineFantasyItems.TIMBER_CUT);
		itemColors.registerItemColorHandler(itemColorForOneLayer, MineFantasyItems.TIMBER_PANE);
		itemColors.registerItemColorHandler(itemColorForOneLayer, MineFantasyItems.BAR);

		itemColors.registerItemColorHandler(((stack, tintIndex) -> ItemHeated.getColorIntFromItemStack(stack)), MineFantasyItems.HOT_ITEM);

		itemColors.registerItemColorHandler(itemColorForOneLayer, MineFantasyItems.COGWORK_ARMOUR);

		itemColors.registerItemColorHandler(itemColorForOneLayer, MineFantasyItems.CHAIN_MESH);
		itemColors.registerItemColorHandler(itemColorForOneLayer, MineFantasyItems.SCALE_MESH);
		itemColors.registerItemColorHandler(itemColorForOneLayer, MineFantasyItems.SPLINT_MESH);

		itemColors.registerItemColorHandler(itemColorForOneLayer, MineFantasyItems.METAL_HUNK);

		itemColors.registerItemColorHandler(itemColorForOneLayer, MineFantasyItems.PLATE);
		itemColors.registerItemColorHandler(itemColorForOneLayer, MineFantasyItems.PLATE_HUGE);

		itemColors.registerItemColorHandler(itemColorForOneLayer, MineFantasyItems.ARROWHEAD);
		itemColors.registerItemColorHandler(itemColorForOneLayer, MineFantasyItems.BROAD_HEAD);
		itemColors.registerItemColorHandler(itemColorForOneLayer, MineFantasyItems.BODKIN_HEAD);

		/// ITEMBLOCKS

		// Leaf ItemBlock Coloration
		itemColors.registerItemColorHandler(((stack, tintIndex) -> tintIndex = ColorizerFoliage.getFoliageColorBasic()), MineFantasyBlocks.LEAVES_YEW);
		itemColors.registerItemColorHandler(((stack, tintIndex) -> tintIndex = ColorizerFoliage.getFoliageColorBasic()), MineFantasyBlocks.LEAVES_IRONBARK);
		itemColors.registerItemColorHandler(((stack, tintIndex) -> tintIndex = ColorizerFoliage.getFoliageColorBasic()), MineFantasyBlocks.LEAVES_EBONY);
		itemColors.registerItemColorHandler(((stack, tintIndex) -> tintIndex = ColorizerFoliage.getFoliageColorBasic()), MineFantasyBlocks.BERRY_BUSH);

		// Static model Wood Decoration ItemBlock Coloration
		itemColors.registerItemColorHandler(itemColorForOneLayer, MineFantasyBlocks.TOOL_RACK_WOOD);
		itemColors.registerItemColorHandler(itemColorForOneLayer, MineFantasyBlocks.TROUGH_WOOD);
	}
}
