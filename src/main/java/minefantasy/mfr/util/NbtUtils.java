package minefantasy.mfr.util;

import minefantasy.mfr.MineFantasyReforged;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class NbtUtils {

	private NbtUtils() {
		throw new IllegalStateException("Util class cannot be instantiated");
	}

	public static NBTTagCompound getOrCreateNBT(ItemStack stack) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		return stack.getTagCompound();
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
			MineFantasyReforged.LOG.error("Cannot store tag of type {} under key '{}' as it would result in a circular reference! Please report this (including your full log) to MFR's issue tracker.",
					NBTBase.getTypeName(tag.getId()), key);
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
}
