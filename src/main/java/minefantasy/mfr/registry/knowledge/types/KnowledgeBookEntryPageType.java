package minefantasy.mfr.registry.knowledge.types;

import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public enum KnowledgeBookEntryPageType implements IStringSerializable {
	ENTRY_PAGE_RECIPE,
	ENTRY_PAGE_TEXT,
	ENTRY_PAGE_IMAGE,
	NONE;

	@Override
	public String getName() {
		return this.name().toLowerCase();
	}

	public static KnowledgeBookEntryPageType deserialize(String name) {
		for (KnowledgeBookEntryPageType type : values()) {

			if (type.getName().equals(name)) {
				return type;
			}
		}
		return NONE;
	}

	public static KnowledgeBookEntryPageType getByNameWithModId(String name, String modId) {
		for (KnowledgeBookEntryPageType type : values()) {

			if ((modId + ":" + type.getName()).equals(name)) {
				return type;
			}
		}
		return NONE;
	}
}
