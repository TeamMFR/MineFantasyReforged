package minefantasy.mfr.registry.knowledge.types;

import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public enum KnowledgeBookEntryType implements IStringSerializable {
	KNOWLEDGE_BOOK_ENTRY,
	NONE;

	@Override
	public String getName() {
		return this.name().toLowerCase();
	}

	public static KnowledgeBookEntryType deserialize(String name) {
		for (KnowledgeBookEntryType type : values()) {

			if (type.getName().equals(name)) {
				return type;
			}
		}
		return NONE;
	}

	public static KnowledgeBookEntryType getByNameWithModId(String name, String modId) {
		for (KnowledgeBookEntryType type : values()) {

			if ((modId + ":" + type.getName()).equals(name)) {
				return type;
			}
		}
		return NONE;
	}
}
