package minefantasy.mfr.util;

import com.google.common.base.CaseFormat;
import minefantasy.mfr.MineFantasyReforged;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.text.WordUtils;

import javax.annotation.Nullable;

public class Utils {

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

	public static TileEntityChest getOtherDoubleChest(TileEntity inv) {
		if (inv instanceof TileEntityChest) {
			TileEntityChest chest = (TileEntityChest) inv;

			TileEntityChest adjacent = null;

			if (chest.adjacentChestXNeg != null) {
				adjacent = chest.adjacentChestXNeg;
			}

			if (chest.adjacentChestXPos != null) {
				adjacent = chest.adjacentChestXPos;
			}

			if (chest.adjacentChestZNeg != null) {
				adjacent = chest.adjacentChestZNeg;
			}

			if (chest.adjacentChestZPos != null) {
				adjacent = chest.adjacentChestZPos;
			}

			return adjacent;
		}
		return null;
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
	 * Stores the given NBT tag inside the given NBT tag compound using the given key. Under normal circumstances, this
	 * is equivalent to {@link NBTTagCompound#setTag(String, NBTBase)}, but this method performs safety checks to
	 * prevent circular references. If storing the given tag would cause a circular reference, the tag is not stored
	 * and an error is printed to the console.
	 * @param compound The {@link NBTTagCompound} in which to store the tag.
	 * @param key The key to store the tag under.
	 * @param tag The tag to store.
	 */
	// This is a catch-all fix for issue #299.
	public static void storeTagSafely(NBTTagCompound compound, String key, NBTBase tag){

		if(compound == tag || deepContains(tag, compound)){
			MineFantasyReforged.LOG.error("Cannot store tag of type {} under key '{}' as it would result in a circular reference! Please report this (including your full log) to wizardry's issue tracker.",
					NBTBase.getTagTypeName(tag.getId()), key);
		}else{
			compound.setTag(key, tag);
			//MineFantasyReforged.LOG.warn("writing: " + key + ": " + tag);
		}
	}

	/**
	 * Recursively searches within the first NBT tag for the second NBT tag. This handles both compound and list tags.
	 * @param toSearch The NBT tag to search inside. If this is not a compound or list tag, this method will always
	 *                 return false.
	 * @param searchFor The NBT tag to search for.
	 * @return True if the second tag appears anywhere within the NBT tree contained within the first tag, false if not.
	 */
	public static boolean deepContains(NBTBase toSearch, NBTBase searchFor){

		if(toSearch instanceof NBTTagCompound){

			for(String subKey : ((NBTTagCompound)toSearch).getKeySet()){
				NBTBase subTag = ((NBTTagCompound)toSearch).getTag(subKey);
				if(subTag == searchFor || deepContains(subTag, searchFor)) return true;
			}

		}else if(toSearch instanceof NBTTagList){
			for(NBTBase subTag : (NBTTagList)toSearch){
				if(subTag == searchFor || deepContains(subTag, searchFor)) return true;
			}
		}

		return false;
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
}
