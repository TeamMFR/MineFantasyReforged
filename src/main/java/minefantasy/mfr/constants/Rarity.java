package minefantasy.mfr.constants;

import net.minecraft.item.EnumRarity;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.EnumHelper;

public enum Rarity implements net.minecraftforge.common.IRarity {

	POOR(TextFormatting.DARK_GRAY, "Poor"), // used to be "-1"
	COMMON(TextFormatting.WHITE, EnumRarity.COMMON.rarityName), // used to be "0"
	UNCOMMON(TextFormatting.YELLOW, EnumRarity.UNCOMMON.rarityName), // used to be "1"
	RARE(TextFormatting.AQUA, EnumRarity.RARE.rarityName), // used to be "2"
	EPIC(TextFormatting.LIGHT_PURPLE, EnumRarity.EPIC.rarityName); // used to be "3"

	/**
	 * A decimal representation of the hex color codes of a the color assigned to this rarity type. (13 becomes d as in
	 * \247d which is light purple)
	 */
	public final TextFormatting rarityColor;
	/**
	 * Rarity name.
	 */
	public final String rarityName;

	Rarity(TextFormatting color, String name) {
		this.rarityColor = color;
		this.rarityName = name;
	}

	@Override
	public TextFormatting getColor() {
		return this.rarityColor;
	}

	@Override
	public String getName() {
		return this.rarityName;
	}

	static {
		EnumHelper.addRarity("poor", TextFormatting.DARK_GRAY, "poor");
	}
}