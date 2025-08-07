package minefantasy.mfr.util;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.registry.knowledge.KnowledgeManagerResearch;
import minefantasy.mfr.registry.knowledge.ResearchBase;
import minefantasy.mfr.tile.TileEntityBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

	public static Set<ResearchBase> deserializeResearches(NBTTagCompound nbt) {
		Collection<String> researches = NbtUtils
				.mapNbtTagListToList(nbt.getTagList(TileEntityBase.KNOWN_RESEARCHES_TAG, 8), String.class);
		return deserializeResearches(researches);
	}

	public static Set<ResearchBase> deserializeResearches(Collection<String> researches) {
		return researches.stream()
				.map(researchKey -> KnowledgeManagerResearch
						.getResearchByKey(new ResourceLocation(researchKey), true))
				.collect(Collectors.toSet());
	}

	public static NBTTagList serializeResearches(Collection<ResearchBase> knownResearches) {
		return NbtUtils.mapListToNbtTagList(knownResearches
				.stream()
				.map(research -> research.getRegistryName().toString())
				.collect(Collectors.toSet()), 8);

	}

	public static <T> List<T> mapNbtTagListToList(NBTTagList tagList, Class<T> type) {
		List<T> list = new ArrayList<>();
		for (int i = 0; i < tagList.tagCount(); i++) {
			list.add(create(tagList.get(i), type));
		}
		return list;
	}

	public static <T> NBTTagList mapListToNbtTagList(Collection<T> list, int type) {
		NBTTagList tagList = new NBTTagList();
		for (T object : list) {
			NBTBase nbt = create(object, type);
			if (nbt != null) {
				tagList.appendTag(nbt);
			}
			else {
				throw new IllegalArgumentException("Could not convert value to NBT");
			}
		}
		return tagList;
	}

	private static <T> NBTBase create(T value, int type) {
		switch (type)
		{
			case 1:
				return new NBTTagByte((Byte) value);
			case 2:
				return new NBTTagShort((Short) value);
			case 3:
				return new NBTTagInt((Integer) value);
			case 4:
				return new NBTTagLong((Long) value);
			case 5:
				return new NBTTagFloat((Float) value);
			case 6:
				return new NBTTagDouble((Double) value);
			case 7:
				return new NBTTagByteArray((byte[]) value);
			case 8:
				return new NBTTagString((String) value);
			case 11:
				return new NBTTagIntArray((int[]) value);
			default:
				return null;
		}
	}

	private static <T> T create(NBTBase value, Class<T> type) {
		switch (value.getId())
		{
			case 1:
				return type.cast(((NBTTagByte) value).getByte());
			case 2:
				return type.cast(((NBTTagShort) value).getShort());
			case 3:
				return type.cast(((NBTTagInt) value).getInt());
			case 4:
				return type.cast(((NBTTagLong) value).getLong());
			case 5:
				return type.cast(((NBTTagFloat) value).getFloat());
			case 6:
				return type.cast(((NBTTagDouble) value).getDouble());
			case 7:
				return type.cast(((NBTTagByteArray) value).getByteArray());
			case 8:
				return type.cast(((NBTTagString) value).getString());
			case 11:
				return type.cast(((NBTTagIntArray) value).getIntArray());
			default:
				return null;
		}
	}
}
