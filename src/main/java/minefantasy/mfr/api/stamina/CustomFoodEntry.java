package minefantasy.mfr.api.stamina;

import minefantasy.mfr.config.ConfigStamina;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class CustomFoodEntry {
	public static HashMap<String, CustomFoodEntry> entries = new HashMap<>();

	/**
	 * How much stamina is immediately restored.
	 */
	public float staminaRestore;

	/**
	 * How long should the max stamina increase last in seconds.
	 */
	public int staminaSeconds;

	/**
	 * How much should the max stamina be increased by.
	 */
	public float staminaBuff;

	/**
	 * How long should the stamina regen modifier last in seconds.
	 */
	public int staminaRegenSeconds;

	/**
	 * How much should the stamina regen modifier be increased by.
	 */
	public float staminaRegenBuff;

	/**
	 * How long until the player can eat the food again.
	 */
	public float eatDelay;

	/**
	 * How much fat accumulation is gained.
	 */
	public float fatAccumulation;

	/**
	 * Used by the tooltip to determine if it should show any extra information.
	 */
	public boolean hasEffect;

	/**
	 * Used by the tooltip to determine if the stamina buff length is in minutes.
	 */
	public boolean staminaInMinutes;

	/**
	 * Used by the tooltip to determine if the stamina buff length is in hours.
	 */
	public boolean staminaInHours;

	/**
	 * Used by the tooltip to determine if the stamina regen buff length is in minutes.
	 */
	public boolean staminaRegenInMinutes;

	private CustomFoodEntry(int tier, float sugar, float carbs, float fats) {
		sugar = sugar * tier;
		carbs = carbs * tier;
		fats = fats * tier;

		//Stamina Instant Restore
		this.staminaRestore = sugar * 50;

		//Stamina Total buff
		this.staminaSeconds = (int) (carbs * 3600F);
		this.staminaBuff = 50 * carbs;

		//Stamina Regen Buff
		this.staminaRegenSeconds = (int) ((sugar / 30) * 60);
		this.staminaRegenBuff = sugar;

		//Eat Delay and Fat Accumulation
		this.eatDelay = fats * ConfigStamina.eatDelayModifier;
		this.fatAccumulation = fats * ConfigStamina.fatAccumulationModifier;

		staminaInMinutes = staminaSeconds > 60;
		staminaInHours = staminaSeconds > 3600;
		staminaRegenInMinutes = staminaRegenSeconds > 60;

		hasEffect = staminaRestore > 0 || staminaBuff > 0 || staminaRegenSeconds > 0 || staminaRegenBuff > 0 || eatDelay > 0 || fatAccumulation > 0;
	}

	/**
	 * Registers a piece (called by code or config)
	 *
	 * @param piece    The item to add to the map
	 * @param tier     The tier of the custom food entry. Will multiply the other food stats.
	 * @param sugar    The sugar value of the custom food entry. Will control Stamina restore modifier and Stamina regen modifier.
	 * @param carbs    The carbs value of the custom food entry. Will control max Stamina modifier.
	 * @param fats     The fat value of the custom food entry. Will control eat delay modifier and fat accumulation modifier.
	 */
	public static void registerItem(ItemStack piece, int tier, float sugar, float carbs, float fats) {
		entries.put(piece.getItem().getRegistryName() + ":" + piece.getMetadata(), new CustomFoodEntry(tier, sugar, carbs, fats));
	}

	/**
	 * Gets the entry for an item
	 *
	 * @param item the armour item
	 * @return the entry(if there is one), else null
	 */
	public static CustomFoodEntry getEntry(ItemStack item) {
		if (item != null) {
			if (entries.containsKey(item.getItem().getRegistryName() + ":" + item.getMetadata())) {
				return entries.get(item.getItem().getRegistryName() + ":" + item.getMetadata());
			}
		}
		return null;
	}
}
