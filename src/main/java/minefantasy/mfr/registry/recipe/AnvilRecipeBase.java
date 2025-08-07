package minefantasy.mfr.registry.recipe;

import minefantasy.mfr.api.heating.Heatable;
import minefantasy.mfr.api.heating.IHotItem;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.constants.Tool;
import minefantasy.mfr.registry.material.CustomMaterial;
import minefantasy.mfr.registry.material.CustomMaterialRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

public abstract class AnvilRecipeBase extends IForgeRegistryEntry.Impl<AnvilRecipeBase> implements IRecipeMFR {
	public static final int MAX_WIDTH = 6;
	public static final int MAX_HEIGHT = 4;
	protected ItemStack output;
	protected NonNullList<Ingredient> inputs;
	protected Skill requiredSkill;
	protected String requiredResearch;
	protected int skillXp;
	protected float vanillaXp;
	protected Tool toolType;
	protected int toolTier;
	protected int anvilTier;

	protected int craftTime;
	protected boolean hotOutput;

	public AnvilRecipeBase(ItemStack output, NonNullList<Ingredient> inputs, String toolType,
			int craftTime, int toolTier, int anvilTier, boolean hotOutput, String requiredResearch,
			Skill requiredSkill, int skillXp, float vanillaXp) {
		this.output = output;
		this.inputs = inputs;
		this.requiredSkill = requiredSkill;
		this.requiredResearch = requiredResearch;
		this.toolType = Tool.fromName(toolType);
		this.toolTier = toolTier;
		this.anvilTier = anvilTier;
		this.craftTime = craftTime;
		this.hotOutput = hotOutput;
		this.skillXp = skillXp;
		this.vanillaXp = vanillaXp;
	}

	public abstract boolean matches(@Nonnull AnvilCraftMatrix inv, @Nonnull World world);

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
		CustomMaterial material = CustomMaterialRegistry.getMaterial(tier);
		if (material != CustomMaterialRegistry.NONE) {
			int newTier = toolTier < 0 ? material.getCrafterTier() : toolTier;
			int newAnvil = anvilTier < 0 ? material.getCrafterAnvilTier() : anvilTier;
			matrix.modifyTier(newTier, newAnvil, (int) (craftTime * material.getCraftTimeModifier()));
			if (isMain) {
				matrix.modifyResearch("smelt_" + material.getName());
			}
			return true;
		}
		return false;
	}

	@Override
	public String getResourceLocation() {
		return this.getRegistryName() != null ? this.getRegistryName().toString() : "";
	}

	public ItemStack getCraftingResult(AnvilCraftMatrix var1) {
		return output.copy();
	}
	public ItemStack getAnvilRecipeOutput() {
		return output;
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

	public int getToolTier() {
		return toolTier;
	}

	public int getAnvilTier() {
		return anvilTier;
	}

	public boolean isHotOutput() {
		return hotOutput;
	}

	public Tool getToolType() {
		return toolType;
	}

	@Override
	public String getRequiredResearch() {
		return requiredResearch;
	}

	@Override
	public Skill getSkill() {
		return requiredSkill;
	}

	@Override
	public int getSkillXp() {
		return skillXp;
	}

	@Override
	public boolean shouldSlotGiveSkillXp() {
		return false;
	}

	@Override
	public float getVanillaXp() {
		return vanillaXp;
	}

	public boolean useCustomTiers() {
		return false;
	}

	public int getWidth() {return MAX_WIDTH;}
	public int getHeight() {return MAX_HEIGHT;}

	public boolean isTierModifyOutputCount() {
		return false;
	}

	public static boolean isCustomRecipe(AnvilRecipeBase recipe) {
		return recipe instanceof AnvilShapedCustomMaterialRecipe
				|| recipe instanceof AnvilShapelessCustomMaterialRecipe;
	}
}
