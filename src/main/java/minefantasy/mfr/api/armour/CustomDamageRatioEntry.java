package minefantasy.mfr.api.armour;

import net.minecraft.item.ItemStack;
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
	 * @param weapon   the weapon itemstack
	 * @param vars the damage type ratio cutting:blunt
	 */
	public static void registerItem(ItemStack weapon, float[] vars) {
		entries.put(weapon.getItem().getRegistryName(), new CustomDamageRatioEntry(vars));
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
	public static float[] getTraits(ItemStack weapon) {
		if (entries.get(weapon.getItem().getRegistryName()) != null) {
			return entries.get(weapon.getItem().getRegistryName()).vars;
		}
		else {
			return null;
		}
	}

	/**
	 * Gets the ratio for an entity, null if it's not found
	 */
	public static float[] getEntityTraits(ResourceLocation resourceLocation) {
		return entriesProj.get(resourceLocation) != null ? entriesProj.get(resourceLocation).vars : new float[] {1, 1, 1};
	}
}
