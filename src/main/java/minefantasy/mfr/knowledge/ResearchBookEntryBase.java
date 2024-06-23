package minefantasy.mfr.knowledge;

import minefantasy.mfr.client.knowledge.EntryPage;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;


//Todo possible subtypes
// Perk
// Special
public abstract class ResearchBookEntryBase extends IForgeRegistryEntry.Impl<ResearchBookEntryBase> {
	protected ResearchBookCategoryBase category;
	protected int displayColumn;
	protected int displayRow;
	protected ItemStack imageStack;
	protected String name;
	protected String description;
	protected List<Object> descriptionParameters = new ArrayList<>();
	protected List<String> hints = new ArrayList<>();
	protected List<ResearchBase> researches = new ArrayList<>();
	protected List<EntryPage> entryPages = new ArrayList<>();
	protected boolean isUnlocked;

}
