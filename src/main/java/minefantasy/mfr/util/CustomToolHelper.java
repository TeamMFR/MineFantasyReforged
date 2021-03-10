package minefantasy.mfr.util;

import minefantasy.mfr.api.crafting.ITieredComponent;
import minefantasy.mfr.api.crafting.exotic.ISpecialDesign;
import minefantasy.mfr.init.MineFantasyMaterials;
import minefantasy.mfr.item.ItemHeated;
import minefantasy.mfr.material.CustomMaterial;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class CustomToolHelper {
	public static final String slot_main = "main_metal";
	public static final String slot_haft = "haft_wood";
	public static EnumRarity poor = EnumHelper.addRarity("poor", TextFormatting.DARK_GRAY, "poor");
	public static EnumRarity[] rarity = new EnumRarity[] {poor, EnumRarity.COMMON, EnumRarity.UNCOMMON, EnumRarity.RARE, EnumRarity.EPIC};
	/**
	 * A bit of the new system, gets custom materials for the head
	 */
	public static CustomMaterial getCustomPrimaryMaterial(ItemStack item) {
		if (item.isEmpty())
			return CustomMaterial.NONE;

		CustomMaterial material = CustomMaterial.getMaterialFor(item, slot_main);
		if (material != null) {
			return material;
		}
		return CustomMaterial.NONE;
	}

	public static CustomMaterial getCustomSecondaryMaterial(ItemStack item) {
		if (item.isEmpty())
			return CustomMaterial.NONE;

		CustomMaterial material = CustomMaterial.getMaterialFor(item, slot_haft);
		if (material != null) {
			return material;
		}
		return CustomMaterial.NONE;
	}

	public static ItemStack construct(Item base, String main) {
		return construct(base, main, MineFantasyMaterials.Names.OAK_WOOD);
	}

	public static ItemStack construct(Item base, String main, String haft) {
		ItemStack item = new ItemStack(base);
		CustomMaterial.addMaterial(item, slot_main, main.toLowerCase());
		if (haft != null) {
			CustomMaterial.addMaterial(item, slot_haft, haft.toLowerCase());
		}
		return item;
	}

	public static ItemStack constructSingleColoredLayer(Item base, String main) {
		return constructSingleColoredLayer(base, main, 1);
	}

	public static ItemStack constructSingleColoredLayer(Item base, String main, int stacksize) {
		ItemStack item = new ItemStack(base, stacksize);
		CustomMaterial.addMaterial(item, slot_main, main.toLowerCase());
		return item;
	}

	/**
	 * Gets the rarity for a custom item
	 *
	 * @param itemRarity is the default id
	 */
	public static EnumRarity getRarity(ItemStack item, int itemRarity) {
		int lvl = itemRarity + 1;
		CustomMaterial material = CustomMaterial.getMaterialFor(item, slot_main);
		if (material != null) {
			lvl = material.rarityID + 1;
		}

		if (item.isItemEnchanted()) {
			if (lvl == 0) {
				lvl++;
			}
			lvl++;
		}
		if (lvl >= rarity.length) {
			lvl = rarity.length - 1;
		}
		return rarity[lvl];
	}

	/**
	 * Gets the max durability
	 *
	 * @param dura is the default dura
	 */
	public static int getMaxDamage(ItemStack stack, int dura) {
		CustomMaterial head = getCustomPrimaryMaterial(stack);
		CustomMaterial haft = getCustomSecondaryMaterial(stack);
		if (head != null) {
			dura = (int) (head.durability * 100);
		}
		if (haft != null) {
			dura += (int) (haft.durability * 100);// Hafts add 50% to the durability
		}
		return ToolHelper.setDuraOnQuality(stack, dura);
	}

	/**
	 * Gets the colour for a layer
	 *
	 * @param layer 0 is base, haft is 1, 2 is detail
	 */
	public static int getColourFromItemStack(ItemStack item, int layer) {
		if (layer == 0) {
			CustomMaterial material = CustomMaterial.getMaterialFor(item, slot_main);
			if (material != null) {
				return material.getColourInt();
			}
		}
		if (layer == 1) {
			CustomMaterial material = CustomMaterial.getMaterialFor(item, slot_haft);
			if (material != null) {
				return material.getColourInt();
			}
		}
		return 0;
	}

	public static float getWeightModifier(ItemStack item, float base) {
		CustomMaterial metal = getCustomPrimaryMaterial(item);
		CustomMaterial wood = getCustomSecondaryMaterial(item);

		if (metal != null) {
			base = (metal.density / 2.5F) * base;
		}
		if (wood != null) {
			base += (wood.density / 2.5F);
		}
		return base;
	}

	/**
	 * Gets the material modifier if it exists
	 *
	 * @param defaultModifier default if no material exists
	 */
	public static float getMeleeDamage(ItemStack item, float defaultModifier) {
		CustomMaterial custom = getCustomPrimaryMaterial(item);
		if (custom != null) {
			return custom.sharpness;
		}
		return defaultModifier;
	}

	public static float getBowDamage(ItemStack item, float defaultModifier) {
		CustomMaterial base = getCustomSecondaryMaterial(item);
		CustomMaterial joints = getCustomPrimaryMaterial(item);

		if (base != null) {
			defaultModifier = base.flexibility;
		}
		if (joints != null) {
			defaultModifier *= joints.flexibility;
		}
		return defaultModifier;
	}

	/**
	 * The total damage of a bow and arrow
	 */
	public static float getBaseDamages(ItemStack item, float defaultModifier) {
		CustomMaterial custom = getCustomPrimaryMaterial(item);
		if (custom != null) {
			return getBaseDamage(custom.sharpness * custom.flexibility);
		}
		return getBaseDamage(defaultModifier);
	}

	/**
	 * The damage a bow and arrow should do (same as a sword)
	 */
	public static float getBaseDamage(float modifier) {
		return 4F + modifier;
	}

	public static float getEfficiencyForHds(ItemStack item, float value, float mod) {
		CustomMaterial custom = getCustomPrimaryMaterial(item);
		if (custom != null) {
			value = 2.0F + (custom.hardness * 4F);// Efficiency starts at 2 and each point of sharpness adds 2
		}
		return ToolHelper.modifyDigOnQuality(item, value) * mod;
	}

	public static float getEfficiency(ItemStack item, float value, float mod) {
		CustomMaterial custom = getCustomPrimaryMaterial(item);
		if (custom != null) {
			value = 2.0F + (custom.sharpness * 2F);// Efficiency starts at 2 and each point of sharpness adds 2
		}
		return ToolHelper.modifyDigOnQuality(item, value) * mod;
	}

	public static int getCrafterTier(ItemStack item, int value) {
		CustomMaterial custom = getCustomPrimaryMaterial(item);
		if (custom != null) {
			return custom.crafterTier;
		}
		return value;
	}

	public static int getHarvestLevel(ItemStack item, int value) {
		if (value <= 0) {
			return value;// If its not effective
		}

		CustomMaterial custom = getCustomPrimaryMaterial(item);
		if (custom != null) {
			if (custom.tier == 0)
				return 1;
			if (custom.tier <= 2)
				return 2;
			return Math.max(custom.tier, 2);
		}
		return value;
	}

	@SideOnly(Side.CLIENT)
	public static void addInformation(ItemStack item, List<String> list) {
		CustomMaterial secondaryMaterial = getCustomSecondaryMaterial(item);

		if (materialOnTooltip()) {
			CustomMaterial mainMaterial = getCustomPrimaryMaterial(item);
			if (mainMaterial != null && mainMaterial != CustomMaterial.NONE) {
				String matName = I18n.format(I18n.format(Utils.convertSnakeCaseToSplitCapitalized(mainMaterial.getName())));
				list.add(TextFormatting.GOLD + matName);
			}
		}

		if (secondaryMaterial != null && secondaryMaterial != CustomMaterial.NONE) {
			String matName = I18n.format("item.mod_haft.name", I18n.format(Utils.convertSnakeCaseToSplitCapitalized(secondaryMaterial.getName())));
			list.add(TextFormatting.GOLD + matName);
		}

	}

	/**
	 * Gets if the language puts tiers in the tooltip, leaving the name blank
	 *
	 * @return material boolean
	 */
	public static boolean materialOnTooltip() {
		String cfg = I18n.format("languagecfg.tooltiptier");
		return cfg.equalsIgnoreCase("true");
	}

	@SideOnly(Side.CLIENT)
	public static void addBowInformation(ItemStack item, List<String> list) {

		CustomMaterial material = getCustomPrimaryMaterial(item);
		if (material != null && material != CustomMaterial.NONE) {
			String matName = I18n.format("item.mod_joint.name",
					I18n.format(Utils.convertSnakeCaseToSplitCapitalized(material.getName())));
			list.add(TextFormatting.GOLD + matName);
		}

	}

	public static String getWoodenLocalisedName(ItemStack item, String unlocalizedName) {
		if (materialOnTooltip()) {
			I18n.format(unlocalizedName);
		}

		CustomMaterial material = getCustomSecondaryMaterial(item);
		String name = "any";
		if (material != null && material != CustomMaterial.NONE) {
			name = material.getName();
		}
		return I18n.format(unlocalizedName, I18n.format(Utils.convertSnakeCaseToSplitCapitalized(name)));
	}

	public static String getLocalisedName(ItemStack item, String unlocalName) {
		if (materialOnTooltip()) {
			I18n.format(unlocalName);
		}

		CustomMaterial material = getCustomPrimaryMaterial(item);
		String name = "any";
		if (material != null && material != CustomMaterial.NONE) {
			name = material.getName();
		}
		return I18n.format(unlocalName, I18n.format(Utils.convertSnakeCaseToSplitCapitalized(name)));
	}

	public static boolean areEqual(ItemStack recipeItem, ItemStack inputItem) {
		if (recipeItem == null) {
			return inputItem == null;
		}
		if (inputItem == null)
			return false;

		return recipeItem.isItemEqual(inputItem) && doesMainMatchForRecipe(recipeItem, inputItem)
				&& doesHaftMatchForRecipe(recipeItem, inputItem);
	}

	/**
	 * Checks if two items' materials match
	 */
	public static boolean doesMatchForRecipe(ItemStack recipeItem, ItemStack inputItem) {
		return doesMainMatchForRecipe(recipeItem, inputItem) && doesHaftMatchForRecipe(recipeItem, inputItem);
	}

	public static boolean doesMainMatchForRecipe(ItemStack recipeItem, ItemStack inputItem) {
		CustomMaterial recipeMat = CustomToolHelper.getCustomPrimaryMaterial(recipeItem);
		CustomMaterial inputMat = CustomToolHelper.getCustomPrimaryMaterial(inputItem);

		if (recipeMat == null) {
			return true;
		}

		if (inputMat == null && recipeMat != null) {
			return false;
		}
		if (recipeMat != inputMat) {
			return false;
		}
		return true;
	}

	public static boolean doesHaftMatchForRecipe(ItemStack recipeItem, ItemStack inputItem) {
		CustomMaterial recipeMat = CustomToolHelper.getCustomSecondaryMaterial(recipeItem);
		CustomMaterial inputMat = CustomToolHelper.getCustomSecondaryMaterial(inputItem);

		if (recipeMat == null) {
			return true;
		}

		if (inputMat == null && recipeMat != null) {
			return false;
		}
		if (recipeMat != inputMat) {
			return false;
		}
		return true;
	}

	public static void addComponentString(ItemStack tool, List<String> list, CustomMaterial base) {
		addComponentString(tool, list, base, 1);
	}

	public static void addComponentString(ItemStack tool, List<String> list, CustomMaterial base, float units) {
		if (base != null) {
			float mass = base.density * units;
			list.add(TextFormatting.GOLD + base.getMaterialString());
			if (mass > 0)
				list.add(CustomMaterial.getWeightString(mass));

			if (base.isHeatable()) {
				int maxTemp = base.getHeatableStats()[0];
				int beyondMax = base.getHeatableStats()[1];
				list.add(I18n.format("materialtype.workable.name", maxTemp, beyondMax));
			}
		}
	}

	public static float getBurnModifier(ItemStack fuel) {
		CustomMaterial mat = CustomMaterial.getMaterialFor(fuel, slot_main);
		if (mat != null && mat.type.equalsIgnoreCase("wood")) {
			return (2 * mat.density) + 0.5F;
		}
		return 1.0F;
	}

	public static String getReferenceName(ItemStack item) {
		String dam = "any";
		int d = item.getItemDamage();
		if (d != OreDictionary.WILDCARD_VALUE) {
			dam = "" + d;
		}

		return getReferenceName(item, dam);
	}

	public static String getReferenceName(ItemStack item, String dam) {
		return getReferenceName(item, dam, true);
	}

	public static String getReferenceName(ItemStack item, String dam, boolean tiered) {
		String reference = getSimpleReferenceName(item.getItem(), dam);

		if (tiered) {
			CustomMaterial base = getCustomPrimaryMaterial(item);
			CustomMaterial haft = getCustomSecondaryMaterial(item);

			if (base != null) {
				reference += "_" + base.getName();
			}
			if (haft != null) {
				reference += "_" + haft.getName();
			}
		}

		return reference;
	}

	public static String getSimpleReferenceName(Item item, String dam) {
		String reference = String.valueOf(Item.REGISTRY.getNameForObject(item));
		if (reference == null) {
			return "";
		}
		return reference.toLowerCase() + "_@" + dam;
	}

	public static String getSimpleReferenceName(Item item) {
		return getSimpleReferenceName(item, "any");
	}

	public static boolean areToolsSame(ItemStack item1, ItemStack item2) {
		CustomMaterial mainMaterial1 = getCustomPrimaryMaterial(item1);
		CustomMaterial secondaryMaterial1 = getCustomSecondaryMaterial(item2);
		CustomMaterial mainMaterial2 = getCustomPrimaryMaterial(item1);
		CustomMaterial secondaryMaterial2 = getCustomSecondaryMaterial(item2);
		if ((mainMaterial1 == null && secondaryMaterial1 != null) || (secondaryMaterial1 == null && mainMaterial1 != null))
			return false;
		if ((mainMaterial2 == null && secondaryMaterial2 != null) || (secondaryMaterial2 == null && mainMaterial2 != null))
			return false;

		if (mainMaterial1 != null && secondaryMaterial1 != null && mainMaterial1 != secondaryMaterial1)
			return false;
		if (mainMaterial2 != null && secondaryMaterial2 != null && mainMaterial2 != secondaryMaterial2)
			return false;

		return true;
	}

	public static boolean isMythic(ItemStack result) {
		CustomMaterial main1 = getCustomPrimaryMaterial(result);
		CustomMaterial haft1 = getCustomPrimaryMaterial(result);
		if (main1 != null && main1.isUnbrekable()) {
			return true;
		}
		if (haft1 != null && haft1.isUnbrekable()) {
			return true;
		}
		return false;
	}

	public static String getComponentMaterial(ItemStack item, String type) {
		if (item.isEmpty() || type == null)
			return null;

		if (item.getItem() instanceof ItemHeated) {
			return getComponentMaterial(ItemHeated.getStack(item), type);
		}

		CustomMaterial material = CustomToolHelper.getCustomPrimaryMaterial(item);
		if (material != null) {
			return material.type.equalsIgnoreCase(type) ? material.name : null;
		}
		return null;
	}

	public static boolean hasAnyMaterial(ItemStack item) {
		return getCustomPrimaryMaterial(item) != null || getCustomSecondaryMaterial(item) != null;
	}

	public static ItemStack tryDeconstruct(ItemStack newitem, ItemStack mainItem) {
		String type = null;
		if (!newitem.isEmpty() && newitem.getItem() instanceof ITieredComponent) {
			type = ((ITieredComponent) newitem.getItem()).getMaterialType(newitem);
		}

		if (type != null) {
			CustomMaterial primary = CustomToolHelper.getCustomPrimaryMaterial(mainItem);
			CustomMaterial secondary = CustomToolHelper.getCustomSecondaryMaterial(mainItem);

			if (primary != null && primary.type.equalsIgnoreCase(type)) {
				CustomMaterial.addMaterial(newitem, slot_main, primary.name);
			} else {
				if (secondary != null && secondary.type.equalsIgnoreCase(type)) {
					CustomMaterial.addMaterial(newitem, slot_main, secondary.name);
				}
			}
		}
		return newitem;
	}

	public static String getCustomStyle(ItemStack weapon) {
		if (!weapon.isEmpty() && weapon.getItem() instanceof ISpecialDesign) {
			return ((ISpecialDesign) weapon.getItem()).getDesign(weapon);
		}
		return null;
	}

}
