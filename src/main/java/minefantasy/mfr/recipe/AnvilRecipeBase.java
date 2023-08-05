package minefantasy.mfr.recipe;

import minefantasy.mfr.api.heating.Heatable;
import minefantasy.mfr.api.heating.IHotItem;
import minefantasy.mfr.constants.Skill;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

public abstract class AnvilRecipeBase extends IForgeRegistryEntry.Impl<AnvilRecipeBase> {
	public static final int WIDTH = 6;
	public static final int HEIGHT = 4;
	public ItemStack output;
	public NonNullList<Ingredient> inputs;
	public Skill requiredSkill;
	public String requiredResearch;
	public String toolType;
	public int anvilTier;
	public int hammerTier;
	public int craftTime;
	public boolean hotOutput;

	public AnvilRecipeBase(NonNullList<Ingredient> inputs, ItemStack output, String toolType,
			int craftTime, int hammerTier, int anvilTier, boolean hotOutput, String requiredResearch, Skill requiredSkill) {
		this.output = output;
		this.inputs = inputs;
		this.requiredSkill = requiredSkill;
		this.requiredResearch = requiredResearch;
		this.toolType = toolType;
		this.anvilTier = anvilTier;
		this.hammerTier = hammerTier;
		this.craftTime = craftTime;
		this.hotOutput = hotOutput;
	}

	abstract boolean matches(@Nonnull AnvilCraftMatrix inv, @Nonnull World world);

	protected ItemStack getHotItem(ItemStack item) {
		if (item.isEmpty())
			return ItemStack.EMPTY;
		if (!(item.getItem() instanceof IHotItem)) {
			return item;
		}

		ItemStack hotItem = Heatable.getItemStack(item);

		if (!hotItem.isEmpty()) {
			return hotItem;
		}

		return item;
	}

	public ItemStack getCraftingResult(AnvilCraftMatrix var1) {
		return output.copy();
	}

	public NonNullList<Ingredient> getIngredients() {
		return inputs;
	}

	public int getCraftTime() {
		return craftTime;
	}

	public int getRecipeSize() {
		return 0;
	}

	public int getHammerTier() {
		return hammerTier;
	}

	public int getAnvilTier() {
		return anvilTier;
	}

	public boolean outputHot() {
		return hotOutput;
	}

	public String getToolType() {
		return toolType;
	}

	public String getResearch() {
		return requiredResearch;
	}

	public ItemStack getAnvilRecipeOutput() {
		return output;
	}

	public Skill getSkill() {
		return requiredSkill;
	}

	public boolean useCustomTiers() {
		return false;
	}
}
