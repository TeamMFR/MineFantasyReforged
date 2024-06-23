package minefantasy.mfr.knowledge;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.List;

public class ResearchBase extends IForgeRegistryEntry.Impl<ResearchBase> {
	protected String name;
	protected ResearchBase parentResearch;
	protected NonNullList<Ingredient> artifacts;
	protected List<SkillRequirement> skillRequirements;

}
