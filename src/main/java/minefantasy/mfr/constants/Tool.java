package minefantasy.mfr.constants;

import minefantasy.mfr.util.ToolHelper;
import net.minecraft.client.resources.I18n;

/**
 * An enum to represent a tool type. See {@link ToolHelper} for helper methods and {@link minefantasy.mfr.api.tool.IToolMFR} to
 * register an item as a tool.
 */
public enum Tool {

	HANDS("hands", false),
	OTHER("other", false),
	BRUSH("brush", false),
	HAMMER("hammer", true),
	HEAVY_HAMMER("heavy_hammer", true),
	NEEDLE("needle", true),
	KNIFE("knife", true),
	MALLET("mallet", true),
	SHEARS("shears", true),
	SAW("saw", true),
	SPANNER("spanner", true),
	SPOON("spoon", true),
	WASH("wash", false);

	private final String unlocalizedName;
	private final boolean hasTiers;

	Tool(String name, boolean hasTiers) {
		this.unlocalizedName = name;
		this.hasTiers = hasTiers;
	}

	public String getName() {
		return unlocalizedName;
	}

	public boolean hasTiers() {
		return hasTiers;
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
