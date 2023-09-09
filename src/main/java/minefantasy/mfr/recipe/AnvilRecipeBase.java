package minefantasy.mfr.recipe;

import minefantasy.mfr.api.heating.Heatable;
import minefantasy.mfr.api.heating.IHotItem;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.material.CustomMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

public abstract class AnvilRecipeBase extends IForgeRegistryEntry.Impl<AnvilRecipeBase> {
	public static final int MAX_WIDTH = 6;
	public static final int MAX_HEIGHT = 4;
	protected ItemStack output;
	protected NonNullList<Ingredient> inputs;
	protected Skill requiredSkill;
	protected String requiredResearch;
	protected String toolType;
	protected int anvilTier;
	protected int hammerTier;
	protected int craftTime;
	protected boolean hotOutput;

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

	protected boolean modifyTiers(AnvilCraftMatrix matrix, String tier, boolean isMain) {
		CustomMaterial material = CustomMaterial.getMaterial(tier);
		if (material != CustomMaterial.NONE) {
			int newTier = hammerTier < 0 ? material.crafterTier : hammerTier;
			int newAnvil = anvilTier < 0 ? material.crafterAnvilTier : anvilTier;
			matrix.modifyTier(newTier, newAnvil, (int) (craftTime * material.craftTimeModifier));
			if (isMain) {
				matrix.modifyResearch("smelt_" + material.getName());
			}
			return true;
		}
		return false;
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

	public boolean isHotOutput() {
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

	public int getWidth() {return MAX_WIDTH;}
	public int getHeight() {return MAX_HEIGHT;}

	public boolean isTierModifyOutputCount() {
		return false;
	}
}
