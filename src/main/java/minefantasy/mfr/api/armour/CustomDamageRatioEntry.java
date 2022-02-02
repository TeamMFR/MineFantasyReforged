package minefantasy.mfr.api.armour;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public class CustomDamageRatioEntry {
	public static HashMap<ResourceLocation, CustomDamageRatioEntry> entries = new HashMap<>();
	public static HashMap<ResourceLocation, CustomDamageRatioEntry> entriesProj = new HashMap<>();

	public float[] vars;

	private CustomDamageRatioEntry(float[] vars) {
		this.vars = vars;
	}

	/**
	 * Register a weapon to give variables
	 *
	 * @param item the item
	 * @param vars the damage type ratio cutting:blunt
	 */
	public static void registerItem(Item item, float[] vars) {
		registerItem(item.getRegistryName(), vars);
	}

	/**
	 * Register a weapon to give variables
	 *
	 * @param resourceLocation   the item resourceLocation
	 * @param vars the damage type ratio cutting:blunt
	 */
	public static void registerItem(ResourceLocation resourceLocation, float[] vars) {
		entries.put(resourceLocation, new CustomDamageRatioEntry(vars));
	}

	/**
	 * Register an entity (like arrows) to give variables
	 *
	 * @param resourceLocation   the entity resourceLocation
	 * @param vars the damage type ratio cutting:blunt
	 */
	public static void registerEntity(ResourceLocation resourceLocation, float[] vars) {
		entriesProj.put(resourceLocation, new CustomDamageRatioEntry(vars));
	}

	/**
	 * Gets the ratio for an item, null if it's not found
	 */
	public static float[] getTraits(Item item) {
		return getTraits(item.getRegistryName());
	}

	/**
	 * Gets the ratio for an item, null if it's not found
	 */
	public static float[] getTraits(ResourceLocation resourceLocation) {
		return entries.get(resourceLocation) != null ? entries.get(resourceLocation).vars : null;
	}

	/**
	 * Gets the ratio for an entity, null if it's not found
	 */
	public static float[] getEntityTraits(ResourceLocation resourceLocation) {
		return entriesProj.get(resourceLocation) != null ? entriesProj.get(resourceLocation).vars : new float[] {1, 1, 1};
	}
}
