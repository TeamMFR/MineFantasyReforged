package minefantasy.mfr.api;

import com.google.common.collect.Lists;
import minefantasy.mfr.api.crafting.engineer.ICrossbowPart;
import minefantasy.mfr.api.heating.Heatable;
import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.constants.Tool;
import minefantasy.mfr.recipe.AlloyRatioRecipe;
import minefantasy.mfr.recipe.AlloyShapedRecipe;
import minefantasy.mfr.recipe.AnvilShapedCustomMaterialRecipe;
import minefantasy.mfr.recipe.AnvilShapedRecipe;
import minefantasy.mfr.recipe.AnvilShapelessCustomMaterialRecipe;
import minefantasy.mfr.recipe.AnvilShapelessRecipe;
import minefantasy.mfr.recipe.BigFurnaceRecipeBase;
import minefantasy.mfr.recipe.BlastFurnaceRecipeBase;
import minefantasy.mfr.recipe.CookRecipe;
import minefantasy.mfr.recipe.CraftingManagerAlloy;
import minefantasy.mfr.recipe.CraftingManagerAnvil;
import minefantasy.mfr.recipe.CraftingManagerBigFurnace;
import minefantasy.mfr.recipe.CraftingManagerBlastFurnace;
import minefantasy.mfr.recipe.CraftingManagerQuern;
import minefantasy.mfr.recipe.QuernRecipeBase;
import minefantasy.mfr.util.MFRLogUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class MineFantasyReforgedAPI {
	/**
	 * This variable saves if MineFantasy is in debug mode
	 */
	public static boolean isInDebugMode;
	/**
	 * This fuel handler is for MineFantasy equipment, it uses real fuel(like coal)
	 * not wood
	 */
	private static List<IFuelHandler> fuelHandlers = Lists.newArrayList();

	@SideOnly(Side.CLIENT)
	public static void init() {
	}

	public static void debugMsg(String msg) {
		MFRLogUtil.logDebug(msg);
	}

	/**
	 * Adds a shaped recipe for anvils with all variables
	 *
	 * @param inputs              The ingredients of this recipe
	 * @param output     		  What the recipe results in
	 * @param tool                What is the tool type of this recipe
	 * @param craftTime           How long should this recipe take to make
	 * @param hammerTier          The required tier of the hammer for making this recipe
	 * @param anvilTier 		  The required tier of the anvil this recipe is used on
	 * @param outputHot           Does this recipe result in a hot output
	 * @param requiredResearch    The required Research for this recipe
	 * @param requiredSkill		  The required Skill for this recipe
	 * @param width               The width of the recipe (Max = 6)
	 * @param height              The height of the recipe (Max = 4)
	 */
	public static void addShapedAnvilRecipe(NonNullList<Ingredient> inputs, ItemStack output, Tool tool,
			int craftTime, int hammerTier, int anvilTier, boolean outputHot, String requiredResearch,
			Skill requiredSkill, int width, int height) {
		CraftingManagerAnvil.addRecipe(new AnvilShapedRecipe(inputs, output, tool.getName(),
				craftTime, hammerTier, anvilTier, outputHot, requiredResearch, requiredSkill,
				width, height), true);
	}

	/**
	 * Adds a shapeless recipe for anvils with all variables
	 *
	 * @param inputs              The ingredients of this recipe
	 * @param output     		  What the recipe results in
	 * @param tool                What is the tool type of this recipe
	 * @param craftTime           How long should this recipe take to make
	 * @param hammerTier          The required tier of the hammer for making this recipe
	 * @param anvilTier 		  The required tier of the anvil this recipe is used on
	 * @param outputHot           Does this recipe result in a hot output
	 * @param requiredResearch    The required Research for this recipe
	 * @param requiredSkill		  The required Skill for this recipe
	 */
	public static void addShapelessAnvilRecipe(NonNullList<Ingredient> inputs, ItemStack output, Tool tool,
			int craftTime, int hammerTier, int anvilTier, boolean outputHot, String requiredResearch, Skill requiredSkill) {
		CraftingManagerAnvil.addRecipe(new AnvilShapelessRecipe(inputs, output, tool.getName(),
				craftTime, hammerTier, anvilTier, outputHot, requiredResearch, requiredSkill), true);
	}

	/**
	 * Adds a shaped Custom Material recipe for anvils with all variables
	 *
	 * @param inputs              		The ingredients of this recipe
	 * @param output     		  		What the recipe results in
	 * @param tool                		What is the tool type of this recipe
	 * @param craftTime           		How long should this recipe take to make
	 * @param hammerTier          		The required tier of the hammer for making this recipe
	 * @param anvilTier 		  		The required tier of the anvil this recipe is used on
	 * @param outputHot           		Does this recipe result in a hot output
	 * @param requiredResearch    		The required Research for this recipe
	 * @param requiredSkill		  		The required Skill for this recipe
	 * @param width               		The width of the recipe (Max = 6)
	 * @param height              		The height of the recipe (Max = 4)
	 * @param tierModifyOutputCount		Does this recipe modify the output count
	 */
	public static void addShapedCustomMaterialAnvilRecipe(NonNullList<Ingredient> inputs, ItemStack output, Tool tool,
			int craftTime, int hammerTier, int anvilTier, boolean outputHot, String requiredResearch, Skill requiredSkill,
			int width, int height, boolean tierModifyOutputCount) {
		CraftingManagerAnvil.addRecipe(new AnvilShapedCustomMaterialRecipe(inputs, output, tool.getName(),
				craftTime, hammerTier, anvilTier, outputHot, requiredResearch, requiredSkill,
				width, height, tierModifyOutputCount), true);
	}

	/**
	 * Adds a shapeless Custom Material recipe for anvils with all variables
	 *
	 * @param inputs              		The ingredients of this recipe
	 * @param output     		  		What the recipe results in
	 * @param tool                		What is the tool type of this recipe
	 * @param craftTime           		How long should this recipe take to make
	 * @param hammerTier          		The required tier of the hammer for making this recipe
	 * @param anvilTier 		  		The required tier of the anvil this recipe is used on
	 * @param outputHot           		Does this recipe result in a hot output
	 * @param requiredResearch    		The required Research for this recipe
	 * @param requiredSkill		  		The required Skill for this recipe
	 * @param tierModifyOutputCount		Does this recipe modify the output count
	 */
	public static void addShapelessCustomMaterialAnvilRecipe(NonNullList<Ingredient> inputs, ItemStack output, Tool tool,
			int craftTime, int hammerTier, int anvilTier, boolean outputHot, String requiredResearch, Skill requiredSkill, boolean tierModifyOutputCount) {
		CraftingManagerAnvil.addRecipe(new AnvilShapelessCustomMaterialRecipe(inputs, output, tool.getName(),
				craftTime, hammerTier, anvilTier, outputHot, requiredResearch, requiredSkill, tierModifyOutputCount), true);
	}

	public static void addBlastFurnaceRecipe(NonNullList<Ingredient> inputs, ItemStack output) {
		CraftingManagerBlastFurnace.addRecipe(new BlastFurnaceRecipeBase(output, inputs), true);
	}

	public static void registerFuelHandler(IFuelHandler handler) {
		fuelHandlers.add(handler);
	}

	public static int getFuelValue(ItemStack itemStack) {
		int fuelValue = 0;
		for (IFuelHandler handler : fuelHandlers) {
			fuelValue = Math.max(fuelValue, handler.getBurnTime(itemStack));
		}
		return fuelValue;
	}

	/**
	 * Adds an alloy ratio recipe with a minimal crucible level
	 *
	 * @param out            The result
	 * @param tier           The minimal crucible tier
	 * @param inputs         The list of required items
	 * @param repeatAmount   How many times the ratio can repeat
	 */
	public static void addAlloyRatioRecipe(ItemStack out, NonNullList<Ingredient> inputs, int tier, int repeatAmount) {
		CraftingManagerAlloy.addRecipe(new AlloyRatioRecipe(out, inputs, tier, repeatAmount), true);
	}

	/**
	 * Adds an alloy ratio recipe with a minimal crucible level
	 *
	 * @param out       The result
	 * @param tier      The minimal crucible tier
	 * @param inputs    The list of required items
	 * @param height    The height of the recipe
	 * @param width     The width of the recipe
	 */
	public static void addAlloyShapedRecipe(ItemStack out, NonNullList<Ingredient> inputs, int tier, int height, int width) {
		CraftingManagerAlloy.addRecipe(new AlloyShapedRecipe(out, inputs, tier, height, width), true);
	}

	/**
	 * Allows an item to be heated
	 *
	 * @param item     the item to heat
	 * @param min      the minimum heat to forge with(celcius)
	 * @param max      the maximum heat until the item is ruined(celcius)
	 * @param unstable when the ingot is unstable(celcius)
	 */
	public static void setHeatableStats(ItemStack item, int min, int unstable, int max) {
		Heatable.addItem(item, min, unstable, max);
	}

	/**
	 * Allows an item to be heated ignoring subId
	 *
	 * @param id       the item to heat
	 * @param min      the minimum heat to forge with(celcius)
	 * @param max      the maximum heat until the item is ruined(celcius)
	 * @param unstable when the ingot is unstable(celcius)
	 */
	public static void setHeatableStats(Item id, int min, int unstable, int max) {
		Heatable.addItem(new ItemStack(id, 1, OreDictionary.WILDCARD_VALUE), min, unstable, max);
	}

	public static void setHeatableStats(String oredict, int min, int unstable, int max) {
		for (ItemStack item : OreDictionary.getOres(oredict)) {
			setHeatableStats(item, min, unstable, max);
		}
	}

	/**
	 * Adds a crossbow part Make sure to do this when adding the item
	 */
	public static void registerCrossbowPart(ICrossbowPart part) {
		ICrossbowPart.components.put(part.getComponentType() + part.getID(), part);
	}

	/**
	 * For Hardcore Crafting: Can remove smelting recipes
	 *
	 * @param input can be an "Item", "Block" or "ItemStack"
	 */
	public static boolean removeSmelting(Object input) {
		ItemStack object = ItemStack.EMPTY;
		if (input instanceof Item) {
			object = new ItemStack((Item) input, 1, 32767);
		} else if (input instanceof Block) {
			object = new ItemStack((Block) input, 1, 32767);
		} else if (input instanceof ItemStack) {
			object = (ItemStack) input;
		}
		if (!object.isEmpty()) {
			return removeFurnaceInput(object);
		}
		return false;
	}

	private static boolean removeFurnaceInput(ItemStack input) {
		Iterator iterator = FurnaceRecipes.instance().getSmeltingList().entrySet().iterator();
		Entry entry;

		do {
			if (!iterator.hasNext()) {
				return false;
			}

			entry = (Entry) iterator.next();
		} while (!checkMatch(input, (ItemStack) entry.getKey()));

		FurnaceRecipes.instance().getSmeltingList().remove(entry.getKey());
		return true;
	}

	private static boolean checkMatch(ItemStack item1, ItemStack item2) {
		return item2.getItem() == item1.getItem() && (item2.getItemDamage() == 32767 || item2.getItemDamage() == item1.getItemDamage());
	}

	/**
	 * Add a cooking recipe
	 *
	 * @param input           what goes in
	 * @param output          what is made
	 * @param min_temperature minimal temperature to work
	 * @param max_temperature maximum temperature until burn
	 * @param time            how many ticks until it finishes
	 * @param requireBaking   whether it needs to be in an oven
	 */
	public static CookRecipe addCookingRecipe(ItemStack input, ItemStack output, int min_temperature, int max_temperature, int time, boolean requireBaking) {
		return CookRecipe.addRecipe(input, output, min_temperature, max_temperature, time, requireBaking, true);
	}

	/**
	 * Add a cooking recipe
	 *
	 * @param input           what goes in
	 * @param output          what is made
	 * @param min_temperature minimal temperature to work
	 * @param max_temperature maximum temperature until burn
	 * @param time            how many ticks until it finishes
	 * @param requireBaking   whether it needs to be in an oven
	 * @param canBurn         false if it cannot burn by traditional means (such as if its in a
	 *                        container)
	 */
	public static CookRecipe addCookingRecipe(ItemStack input, ItemStack output, int min_temperature, int max_temperature, int time, boolean requireBaking, boolean canBurn) {
		return CookRecipe.addRecipe(input, output, min_temperature, max_temperature, time, requireBaking, canBurn);
	}

	/**
	 * Add a cooking recipe
	 *
	 * @param input           what goes in
	 * @param output          what is made
	 * @param min_temperature minimal temperature to work
	 * @param max_temperature maximum temperature until burn
	 * @param time            how many ticks until it finishes
	 * @param requireBaking   whether it needs to be in an oven
	 */
	public static CookRecipe addCookingRecipe(ItemStack input, ItemStack output, ItemStack burnItem, int min_temperature, int max_temperature, int time, boolean requireBaking) {
		return CookRecipe.addRecipe(input, output, burnItem, min_temperature, max_temperature, time, requireBaking,
				true);
	}

	/**
	 * Add a cooking recipe
	 *
	 * @param input           what goes in
	 * @param output          what is made
	 * @param min_temperature minimal temperature to work
	 * @param max_temperature maximum temperature until burn
	 * @param time            how many ticks until it finishes
	 * @param burn_time       how long until a finished product burns
	 * @param requireBaking   whether it needs to be in an oven
	 */
	public static CookRecipe addCookingRecipe(ItemStack input, ItemStack output, ItemStack burnItem,
			int min_temperature, int max_temperature, int time, int burn_time, boolean requireBaking) {
		return CookRecipe.addRecipe(input, output, burnItem, min_temperature, max_temperature, time, burn_time,
				requireBaking, true);
	}

	public static CookRecipe addCookingRecipe(ItemStack input, ItemStack output, ItemStack burnItem,
			int min_temperature, int max_temperature, int time, int burn_time, boolean requireBaking, boolean canBurn) {
		return CookRecipe.addRecipe(input, output, burnItem, min_temperature, max_temperature, time, burn_time,
				requireBaking, canBurn);
	}

	public static void addQuernRecipe(NonNullList<Ingredient> inputs, NonNullList<Ingredient> inputPots, ItemStack output, boolean consumePot) {
		CraftingManagerQuern.addRecipe(new QuernRecipeBase(output, inputs, inputPots, consumePot), true);
	}

	public static void addBigFurnaceRecipe(NonNullList<Ingredient> input, ItemStack output, int tier) {
		CraftingManagerBigFurnace.addRecipe(new BigFurnaceRecipeBase(output, input, tier), true);
	}
}
