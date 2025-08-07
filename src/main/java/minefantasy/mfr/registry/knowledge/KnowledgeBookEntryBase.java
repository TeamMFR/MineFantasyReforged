package minefantasy.mfr.registry.knowledge;

import minefantasy.mfr.client.knowledge.EntryPage;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Collections;
import java.util.List;

@SideOnly(Side.CLIENT)
public class KnowledgeBookEntryBase extends IForgeRegistryEntry.Impl<KnowledgeBookEntryBase> {
	protected KnowledgeBookCategoryBase category;
	protected int displayColumn;
	protected int displayRow;
	protected ItemStack displayStack;
	protected String name;
	protected String description;
	protected List<Object> descriptionParameters;
	protected List<KnowledgeBookEntryBase> parentEntries;
	protected List<ResearchBase> researches;
	protected List<EntryPage> entryPages;
	protected boolean isUnlocked;
	protected boolean isSpecial;

	public KnowledgeBookEntryBase(
			String name,
			KnowledgeBookCategoryBase category,
			int displayColumn,
			int displayRow,
			ItemStack displayStack,
			String description,
			List<Object> descriptionParameters,
			List<ResearchBase> researches,
			List<EntryPage> entryPages,
			boolean isUnlocked,
			boolean isSpecial) {
		this(name, category, displayColumn, displayRow, displayStack, description, descriptionParameters,
				Collections.emptyList(), researches, entryPages, isUnlocked, isSpecial);
	}

	public KnowledgeBookEntryBase(
			String name,
			KnowledgeBookCategoryBase category,
			int displayColumn,
			int displayRow,
			ItemStack displayStack,
			String description,
			List<Object> descriptionParameters,
			List<KnowledgeBookEntryBase> parentEntries,
			List<ResearchBase> researches,
			List<EntryPage> entryPages,
			boolean isUnlocked,
			boolean isSpecial) {
		this.name = name;
		this.category = category;
		this.displayColumn = displayColumn;
		this.displayRow = displayRow;
		this.displayStack = displayStack;
		this.description = description;
		this.descriptionParameters = descriptionParameters;
		this.parentEntries = parentEntries;
		this.researches = researches;
		this.entryPages = entryPages;
		this.isUnlocked = isUnlocked;
		this.isSpecial = isSpecial;
	}

	public KnowledgeBookCategoryBase getCategory() {
		return category;
	}

	public int getDisplayColumn() {
		return displayColumn;
	}

	public int getDisplayRow() {
		return displayRow;
	}

	public ItemStack getDisplayStack() {
		return displayStack;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public List<Object> getDescriptionParameters() {
		return descriptionParameters;
	}

	public void setParentEntries(List<KnowledgeBookEntryBase> parentEntries) {
		this.parentEntries = parentEntries;
	}

	public List<KnowledgeBookEntryBase> getParentEntries() {
		return parentEntries;
	}

	public List<ResearchBase> getResearches() {
		return researches;
	}

	public List<EntryPage> getEntryPages() {
		return entryPages;
	}

	public boolean isUnlocked() {
		return isUnlocked;
	}

	public boolean isSpecial() {
		return isSpecial;
	}
}
