package minefantasy.mfr.util;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Sets;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Utils {

	public Utils() {
		throw new IllegalStateException("Util class cannot be instantiated");
	}

	public static <T> T nullValue() {
		return null;
	}

	public static boolean doesMatch(ItemStack item1, ItemStack item2) {
		return item2.getItem() == item1.getItem() && (item2.getItemDamage() == OreDictionary.WILDCARD_VALUE
				|| item2.getItemDamage() == item1.getItemDamage());
	}

	public static String convertSnakeCaseToSplitCapitalized(String string) {
		return WordUtils.capitalize(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, string).replaceAll("_", " "));
	}

	public static String convertSplitCapitalizedToSnakeCase(String string) {
		return string.toLowerCase().replaceAll(" ", "_");
	}

	public static String serializeList(Set<String> list) {
		return list.toString().replaceAll("[\\[|\\]]", "");
	}

	public static Set<String> deserializeList(String string) {
		List<String> list = Arrays.asList(StringUtils.splitByWholeSeparator(string, ","));
		list.replaceAll(String::trim);
		return Sets.newHashSet(list);
	}

	public static <T> Collection<T> emptyIfNull(List<T> list) {
		if (list == null) {
			return new ArrayList<>();
		}
		else {
			return list;
		}
	}

	public interface IItemPropertyGetterFix extends IItemPropertyGetter {
		float applyPropertyGetter(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn);

		static IItemPropertyGetterFix create(final IItemPropertyGetterFix lambda) {
			return lambda;
		}

		@Override
		@SideOnly(Side.CLIENT)
		default float apply(final ItemStack stack, @Nullable final World worldIn, @Nullable final EntityLivingBase entityIn) {
			return applyPropertyGetter(stack, worldIn, entityIn);
		}
	}

	/**
	 * Provides the percent difference between two integers, provided in percentage form.
	 * @param a the first input integer
	 * @param b the second input integer
	 * @return the percent difference
	 */
	public static float percentDifferenceCalculator (int a, int b){
		float absoluteDifference = Math.abs(a - b);
		float average = (a + b) / 2F;
		return 100 * (absoluteDifference/average);
	}

	public static Long gcd(List<Long> input) {
		long result = input.get(0);
		for (int i = 1; i < input.size(); i++)
			result = gcd(result, input.get(i));
		return result;
	}

	private static long gcd(Long a, Long b) {
		while (b > 0) {
			long temp = b;
			b = a % b; // % is remainder
			a = temp;
		}
		return a;
	}


	public static <T> int findSmallestListSize(Stream<List<T>> lists){
		return lists
				.min(Comparator.comparingInt(List::size))
				.orElse(new ArrayList<>()).size();
	}

	public static boolean isInteger(String str) {
		if (str == null) {
			return false;
		}
		int length = str.length();
		if (length == 0) {
			return false;
		}
		int i = 0;
		if (str.charAt(0) == '-') {
			if (length == 1) {
				return false;
			}
			i = 1;
		}
		for (; i < length; i++) {
			char c = str.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}
		return true;
	}
}
