package minefantasy.mfr.registry.knowledge.types;

import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public enum KnowledgeBookCategoryType implements IStringSerializable {
	KNOWLEDGE_BOOK_CATEGORY,
	NONE;

	@Override
	public String getName() {
		return this.name().toLowerCase();
	}

	public static KnowledgeBookCategoryType deserialize(String name) {
		for (KnowledgeBookCategoryType type : values()) {

			if (type.getName().equals(name)) {
				return type;
			}
		}
		return NONE;
	}

	public static KnowledgeBookCategoryType getByNameWithModId(String name, String modId) {
		for (KnowledgeBookCategoryType type : values()) {

			if ((modId + ":" + type.getName()).equals(name)) {
				return type;
			}
		}
		return NONE;
	}
}
