package minefantasy.mfr.constants;

import minefantasy.mfr.util.ToolHelper;
import net.minecraft.client.resources.I18n;

/**
 * An enum to represent a tool type. See {@link ToolHelper} for helper methods and {@link minefantasy.mfr.api.tool.IToolMFR} to
 * register an item as a tool.
 */
public enum Tool {

	HANDS("hands"),
	OTHER("other"),
	BRUSH("brush"),
	HAMMER("hammer"),
	HEAVY_HAMMER("heavy_hammer"),
	NEEDLE("needle"),
	KNIFE("knife"),
	MALLET("mallet"),
	SHEARS("shears"),
	SAW("saw"),
	SPANNER("spanner"),
	SPOON("spoon"),
	WASH("wash");

	private final String unlocalizedName;

	Tool(String name) {
		this.unlocalizedName = name;
	}

	public String getName() {
		return unlocalizedName;
	}

	/**
	 * Returns the tool with the given name, or throws an {@link java.lang.IllegalArgumentException} if no such
	 * tool exists.
	 */
	public static Tool fromName(String name) {

		for (Tool tool : values()) {
			if (tool.unlocalizedName.equals(name))
				return tool;
		}

		throw new IllegalArgumentException("No such tool with unlocalized name: " + name);
	}

	public String getDisplayName() {
		return I18n.format("tooltype." + unlocalizedName);
	}

}
