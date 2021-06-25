package minefantasy.mfr.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import minefantasy.mfr.MineFantasyReforged;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.MissingResourceException;

public class JsonUtils {

	private JsonUtils() { }

	public static ItemStack getItemStack(JsonElement json) {
		return getItemStack(json, (i, c, m, t, n) -> {
			ItemStack stack = new ItemStack(i, c, m);
			stack.setTagCompound(t);
			return stack;
		});
	}

	private static <T> T getItemStack(JsonElement element, ItemStackCreator<T> creator) {
		if (element.isJsonPrimitive()) {
			return creator.instantiate(getItem(element.getAsString()), 1, -1, null, false);
		}

		JsonObject obj = element.getAsJsonObject();
		String registryName = net.minecraft.util.JsonUtils.getString(obj, "item");
		Item item = getItem(registryName);

		int count = net.minecraft.util.JsonUtils.hasField(obj, "count") ? net.minecraft.util.JsonUtils.getInt(obj, "count") : 1;

		int meta = -1;
		if (net.minecraft.util.JsonUtils.hasField(obj, "data")) {
			meta = net.minecraft.util.JsonUtils.getInt(obj, "data");
		}
		NBTTagCompound tagCompound = null;
		if (net.minecraft.util.JsonUtils.hasField(obj, "nbt")) {
			try {
				tagCompound = JsonToNBT.getTagFromJson(net.minecraft.util.JsonUtils.getString(obj, "nbt"));
			}
			catch (NBTException e) {
				MineFantasyReforged.LOG.error("Error reading item stack nbt {}", net.minecraft.util.JsonUtils.getJsonObject(obj, "nbt"));
			}
		}

		boolean ignoreNbt = net.minecraft.util.JsonUtils.hasField(obj, "ignore_nbt") && net.minecraft.util.JsonUtils.getBoolean(obj, "ignore_nbt");

		return creator.instantiate(item, count, meta, tagCompound, ignoreNbt);
	}

	private interface ItemStackCreator<R> {
		R instantiate(Item item, int count, int meta, @Nullable NBTTagCompound tagCompound, boolean ignoreNbt);
	}

	public static Item getItem(String registryName) {
		return getRegistryEntry(registryName, ForgeRegistries.ITEMS);
	}

	private static <T extends IForgeRegistryEntry<T>> T getRegistryEntry(String registryName, IForgeRegistry<T> registry) {
		ResourceLocation key = new ResourceLocation(registryName);
		if (!registry.containsKey(key)) {
			throw new MissingResourceException("Unable to find entry with registry name \"" + registryName + "\"",
					registry.getRegistrySuperType().getName(), registryName);
		}
		//noinspection ConstantConditions
		return registry.getValue(key);
	}

}
