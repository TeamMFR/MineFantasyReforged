package minefantasy.mfr.recipe.ingredients;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreIngredient;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author Credit: P3pp3rF1y, Ancient Warfare 2 1.12
 **/
public class IngredientOreCount extends OreIngredient implements IIngredientCount {
	private final int count;
	private ItemStack[] array = null;

	public IngredientOreCount(String ore, int count) {
		super(ore);
		this.count = count;
	}

	@Override
	public ItemStack[] getMatchingStacks() {
		if (array == null) {
			List<ItemStack> matchingStacks = Arrays.stream(super.getMatchingStacks()).map(s -> new ItemStack(s.serializeNBT())).collect(Collectors.toList());
			matchingStacks.forEach(s -> s.setCount(count));
			array = matchingStacks.toArray(new ItemStack[matchingStacks.size()]);
		}
		return array;
	}

	@Override
	public boolean apply(@Nullable ItemStack input) {
		return super.apply(input) && input != null && input.getCount() >= count;
	}

	@Override
	public int getCount() {
		return count;
	}
}
