package minefantasy.mfr.registry.knowledge;

import minefantasy.mfr.constants.Skill;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.List;

@SideOnly(Side.CLIENT)
public class KnowledgeBookCategoryBase extends IForgeRegistryEntry.Impl<KnowledgeBookCategoryBase> {
	protected List<KnowledgeBookEntryBase> entries;
	protected Skill baseSkill;
	protected String name;
	protected Integer order;

	public KnowledgeBookCategoryBase(List<KnowledgeBookEntryBase> entries, Skill baseSkill, String name, Integer order) {
		this.entries = entries;
		this.baseSkill = baseSkill;
		this.name = name;
		this.order = order;
	}

	public List<KnowledgeBookEntryBase> getEntries() {
		return entries;
	}

	public Skill getBaseSkill() {
		return baseSkill;
	}

	public String getName() {
		return name;
	}

	public Integer getOrder() {
		return order;
	}
}
