package minefantasy.mfr;

import minefantasy.mfr.api.armour.ISpecialArmourMFR;
import minefantasy.mfr.api.helpers.ArmourCalculator;
import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.api.helpers.ToolHelper;
import minefantasy.mfr.api.knowledge.ResearchLogic;
import minefantasy.mfr.api.material.CustomMaterial;
import minefantasy.mfr.api.rpg.RPGElements;
import minefantasy.mfr.api.weapon.WeaponClass;
import minefantasy.mfr.config.ConfigClient;
import minefantasy.mfr.item.ClientItemsMFR;
import minefantasy.mfr.item.ItemArmourBaseMFR;
import minefantasy.mfr.item.ItemWeaponMFR;
import minefantasy.mfr.mechanics.EventManagerMFRToRemove;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

import static minefantasy.mfr.constants.Constants.CRAFTED_BY_NAME_TAG;

/**
 * General-purpose event handler for things, as a replacement for {@link EventManagerMFRToRemove}
 *
 * @since MFR
 */

@Mod.EventBusSubscriber
public final class MFREventHandler {

	private MFREventHandler() {} // No instances!

	/**
	 * Shows a tooltip list of the different damage reduction attributes of an armor item (like blunt, cutting, pierce). Also displays the difference between
	 * the currently worn armor's stats and the hovered item.
	 *
	 * @param armour The armor ItemStack currently being checked
	 * @param user   The user hovering over the ItemStack
	 * @param list   The tooltip list of the ItemStack
	 * @param extra  If advanced tooltips are enabled (=true), show some extra info
	 */
	public static void addArmorDamageReductionTooltip(ItemStack armour, EntityPlayer user, List<String> list, boolean extra) {
		list.add("");
		String armourClass = ArmourCalculator.getArmourClass(armour);  // Light/Medium/Heavy
		if (armourClass != null) {
			list.add(I18n.format("attribute.armour." + armourClass));
		}
		if (armour.getItem() instanceof ISpecialArmourMFR) {
			if (ArmourCalculator.advancedDamageTypes) {
				list.add(TextFormatting.BLUE + I18n.format("attribute.armour.protection"));
				addSingleDamageReductionTooltip(armour, user, 0, list, true);
				addSingleDamageReductionTooltip(armour, user, 2, list, true);
				addSingleDamageReductionTooltip(armour, user, 1, list, true);
			} else {
				addSingleDamageReductionTooltip(armour, user, 0, list, false);
			}
		}
	}

	/**
	 * Shows adds a damage reduction attribute label to the ItemStack of the specified damage reduction type
	 *
	 * @param armour   The armor ItemStack currently being checked
	 * @param user     The user hovering over the ItemStack
	 * @param id       The id of the armor rating type
	 * @param list     The tooltip list of the ItemStack
	 * @param advanced If advanced tooltips are enabled or not
	 */
	public static void addSingleDamageReductionTooltip(ItemStack armour, EntityPlayer user, int id, List<String> list, boolean advanced) {
		EntityEquipmentSlot slot = ((ItemArmor) armour.getItem()).armorType;
		String attach = "";

		int rating = (int) (ArmourCalculator.getDamageReductionForDisplayPiece(armour, id) * 100F);
		int equipped = (int) (ArmourCalculator.getDamageReductionForDisplayPiece(user.getItemStackFromSlot(slot), id) * 100F);

		if (rating > 0 || equipped > 0) {
			if (equipped > 0 && rating != equipped) {
				float d = rating - equipped;

				attach += d > 0 ? TextFormatting.DARK_GREEN : TextFormatting.RED;

				String d2 = ItemWeaponMFR.decimal_format.format(d);
				attach += " (" + (d > 0 ? "+" : "") + d2 + ")";
			}
			if (advanced) {
				list.add(TextFormatting.BLUE + I18n.format("attribute.armour.rating." + id) + " "
						+ rating + attach);
			} else {
				list.add(TextFormatting.BLUE + I18n.format("attribute.armour.protection") + ": "
						+ rating + attach);
			}
		}
	}

	/**
	 * Adds a tooltip to the specified ItemStack about the crafter (if it has info about the crafter in nbt)
	 * @param tool the ItemStack
	 * @param list the tooltip of the ItemStack
	 */
	private static void showCrafterTooltip(ItemStack tool, List<String> list) {
		String toolType = ToolHelper.getCrafterTool(tool);
		int tier = ToolHelper.getCrafterTier(tool);
		float efficiency = ToolHelper.getCrafterEfficiency(tool);

		list.add(I18n.format("attribute.mfcrafttool.name") + ": " + I18n.format("tooltype." + toolType));
		list.add(I18n.format("attribute.mfcrafttier.name") + ": " + tier);
		list.add(I18n.format("attribute.mfcrafteff.name") + ": " + efficiency);
	}

	// ================================================ Event Handlers ================================================

	/**
	 * Attaches the dynamic tooltips to itemStacks. Called when an item is hovered with the cursor
	 */
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void setTooltip(ItemTooltipEvent event) {
		//  Excludes this from the initial search tree population for tooltips (during game initialization).
		if (event.getEntity() == null) {
			return;
		}

		if (!event.getItemStack().isEmpty()) {
			boolean saidArtefact = false;
			int[] ids = OreDictionary.getOreIDs(event.getItemStack());
			boolean hasInfo = false;
			if (ids != null && event.getEntityPlayer() != null) {
				for (int id : ids) {
					String s = OreDictionary.getOreName(id);
					if (s != null) {
						if (!hasInfo && s.startsWith("ingot")) {
							String s2 = s.substring(5, s.length());
							CustomMaterial material = CustomMaterial.getMaterial(s2);
							if (material != null)
								hasInfo = true;

							CustomToolHelper.addComponentString(event.getItemStack(), event.getToolTip(), material);
						}
						if (s.startsWith("Artefact-")) {
							if (!saidArtefact) {
								String knowledge = s.substring(9).toLowerCase();

								if (!ResearchLogic.hasInfoUnlocked(event.getEntityPlayer(), knowledge)) {
									saidArtefact = true;
									event.getToolTip().add(TextFormatting.AQUA + I18n.format("info.hasKnowledge"));
								}
							}
						} else if (ConfigClient.displayOreDict) {
							event.getToolTip().add("oreDict: " + s);
						}
					}
				}
			}

			if (event.getItemStack().hasTagCompound() && event.getItemStack().getTagCompound().hasKey("MF_Inferior")) {
				if (event.getItemStack().getTagCompound().getBoolean("MF_Inferior")) {
					event.getToolTip().add(TextFormatting.RED + I18n.format("attribute.inferior.name"));
				}
				if (!event.getItemStack().getTagCompound().getBoolean("MF_Inferior")) {
					event.getToolTip().add(TextFormatting.GREEN + I18n.format("attribute.superior.name"));
				}
			}
			if (event.getEntityPlayer() != null && event.getToolTip() != null && event.getFlags() != null) {
				if (event.getItemStack().getItem() instanceof ItemArmor
						&& (!(event.getItemStack().getItem() instanceof ItemArmourBaseMFR)
						|| ClientItemsMFR.showSpecials(event.getItemStack(), event.getEntityPlayer().world, event.getToolTip(), event.getFlags()))) {
					addArmorDamageReductionTooltip(event.getItemStack(), event.getEntityPlayer(), event.getToolTip(), event.getFlags().isAdvanced());
				}
			}
			if (ToolHelper.shouldShowTooltip(event.getItemStack())) {
				showCrafterTooltip(event.getItemStack(), event.getToolTip());
			}
			if (event.getItemStack().hasTagCompound() && event.getItemStack().getTagCompound().hasKey(CRAFTED_BY_NAME_TAG)) {
				String name = event.getItemStack().getTagCompound().getString(CRAFTED_BY_NAME_TAG);
				boolean special = MineFantasyReborn.isNameModder(name);// Mod creators have highlights

				event.getToolTip().add((special ? TextFormatting.GREEN : "")
						+ I18n.format("attribute.mfcraftedbyname.name")
						+ ": " + name
						+ TextFormatting.GRAY);
			}
			WeaponClass WC = WeaponClass.findClassForAny(event.getItemStack());
			if (WC != null && RPGElements.isSystemActive && WC.parentSkill != null) {
				event.getToolTip().add(I18n.format("weaponclass." + WC.name.toLowerCase()));
				float skillMod = RPGElements.getWeaponModifier(event.getEntityPlayer(), WC.parentSkill) * 100F;
				if (skillMod > 100)
					event.getToolTip().add(I18n.format("rpg.skillmod") + ItemWeaponMFR.decimal_format.format(skillMod - 100) + "%");
			}
		}
	}
}
