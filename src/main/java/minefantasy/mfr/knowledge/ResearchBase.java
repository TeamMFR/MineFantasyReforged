package minefantasy.mfr.knowledge;

import minefantasy.mfr.config.ConfigResearch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ResearchBase extends IForgeRegistryEntry.Impl<ResearchBase> {
	protected String name;
	protected List<ResearchBase> parentResearches;
	protected List<Ingredient> artifacts;
	protected int requiredArtifactCount;
	protected List<SkillRequirement> skillRequirements;
	protected boolean isPerk;

	public ResearchBase(
			String name,
			List<Ingredient> artifacts,
			int requiredArtifactCount,
			List<SkillRequirement> skillRequirements,
			boolean isPerk) {
		this(name, Collections.emptyList(), artifacts, requiredArtifactCount, skillRequirements, isPerk);
	}

	public ResearchBase(
			String name,
			List<ResearchBase> parentResearches,
			List<Ingredient> artifacts,
			int requiredArtifactCount,
			List<SkillRequirement> skillRequirements,
			boolean isPerk) {
		this.name = name;
		this.parentResearches = parentResearches;
		this.artifacts = artifacts;
		this.requiredArtifactCount = requiredArtifactCount;
		this.skillRequirements = skillRequirements;
		this.isPerk = isPerk;
	}

	public boolean hasSkillsUnlocked(EntityPlayer player) {
		if (skillRequirements.isEmpty()) {
			return true;
		}
		for (SkillRequirement requirement : skillRequirements) {
			if (!requirement.isAvailable(player)) {
				return false;
			}
		}
		return true;
	}

	public Integer getArtifactIndexForStack(ItemStack stack) {
		Optional<Ingredient> artifactForStackOptional = getArtifacts()
				.stream()
				.filter(ingredient -> ingredient.apply(stack))
				.findFirst();
		if (artifactForStackOptional.isPresent()) {
			return getArtifacts().indexOf(artifactForStackOptional.get());
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public List<ResearchBase> getParentResearches() {
		return parentResearches;
	}

	public void setParentResearches(List<ResearchBase> parentResearch) {
		this.parentResearches = parentResearch;
	}

	public List<Ingredient> getArtifacts() {
		return artifacts;
	}

	public int getRequiredArtifactCount() {
		return requiredArtifactCount;
	}

	public List<SkillRequirement> getSkillRequirements() {
		return skillRequirements;
	}

	public boolean isPerk() {
		return isPerk;
	}

	public boolean isPreUnlocked() {
		return !isPerk && ConfigResearch.UNLOCK_ALL;
	}
}
