package minefantasy.mfr.knowledge;

import minefantasy.mfr.constants.Skill;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.LinkedList;

public class ResearchBookCategoryBase extends IForgeRegistryEntry.Impl<ResearchBookCategoryBase> {
	protected LinkedList<ResearchBookEntryBase> entries = new LinkedList<>();
	protected Skill baseSkill;
	protected String name;
}
