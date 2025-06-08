package minefantasy.mfr.recipe;

import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.util.CustomToolHelper;
import minefantasy.mfr.util.Utils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AlloyRatioRecipe extends AlloyRecipeBase {
	protected int repeatAmount;

	public AlloyRatioRecipe(ItemStack output, NonNullList<Ingredient> inputs, int tier,
			String requiredResearch, Skill skill, int skillXp, float xp, int repeatAmount) {
		super(output, inputs, tier, requiredResearch, skill, skillXp, xp);
		this.repeatAmount = repeatAmount;
	}

	@Override
	public boolean matches(CrucibleCraftMatrix matrix) {
		Map<Ingredient, Long> ingredientsRatioMap = getInputs().stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		Map<Ingredient, Long> inputsRatioMap = new HashMap<>();
		for (Ingredient ingredient : ingredientsRatioMap.keySet()) {
			for (int i = 0; i < matrix.getSizeInventory(); i++) {
				ItemStack inputItem = matrix.getStackInSlot(i);
				if (!inputItem.isEmpty() && ingredient.apply(inputItem)
						&& CustomToolHelper.doesMatchForRecipe(ingredient, inputItem)) {
					if (ingredient.isSimple()) {
						inputsRatioMap.merge(ingredient, 1L, Math::addExact);
					}
					else {
						inputsRatioMap.merge(ingredient, 1L, Math::addExact);
					}
				}
			}
		}

		if (!ingredientsRatioMap.keySet().equals(inputsRatioMap.keySet())) {
			return false;
		}

		return compareRatios(ingredientsRatioMap, inputsRatioMap);
	}

	private boolean compareRatios(Map<Ingredient, Long> ingredientRatioMap, Map<Ingredient, Long> inputRatioMap) {
		List<Long> ingredientLongs = new ArrayList<>(ingredientRatioMap.values());
		List<Long> inputLongs = new ArrayList<>(inputRatioMap.values());
		Long gcd = Utils.gcd(inputLongs);

		List<Long> inputRatio = new ArrayList<>();
		inputLongs.forEach(l -> inputRatio.add(l/gcd));

		return ingredientLongs.equals(inputRatio);
	}

	@Override
	public ItemStack getCraftingResult(CrucibleCraftMatrix matrix) {
		ItemStack output = getAlloyRecipeOutput().copy();

		Map<Ingredient, Long> inputsRatioMap = new HashMap<>();
		for (Ingredient ingredient : getInputs()) {
			for (int i = 0; i < matrix.getSizeInventory(); i++) {
				ItemStack inputItem = matrix.getStackInSlot(i);
				if (!inputItem.isEmpty() && ingredient.apply(inputItem)
						&& CustomToolHelper.doesMatchForRecipe(ingredient, inputItem)) {
					inputsRatioMap.merge(ingredient, 1L, Math::addExact);
				}
			}
		}
		Long gcd = Utils.gcd(new ArrayList<>(inputsRatioMap.values()));
		output.setCount(output.getCount() * Math.toIntExact(gcd));
		return output;
	}

	public int getRepeatAmount() {
		return repeatAmount;
	}
}
