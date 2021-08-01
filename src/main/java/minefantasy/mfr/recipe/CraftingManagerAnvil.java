package minefantasy.mfr.recipe;

import minefantasy.mfr.constants.Skill;
import minefantasy.mfr.util.CustomToolHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author AnonymousProductions
 */
public class CraftingManagerAnvil {
	/**
	 * The static instance of this class
	 */
	private static final CraftingManagerAnvil instance = new CraftingManagerAnvil();

	/**
	 * A list of all the recipes added
	 */
	public List recipes = new ArrayList<IRecipe>();
	public HashMap<String, Object> recipeMap = new HashMap<>();

	private CraftingManagerAnvil() {
		Collections.sort(this.recipes, new RecipeSorterAnvil(this));
	}

	/**
	 * Returns the static instance of this class
	 */
	public static CraftingManagerAnvil getInstance() {
		return instance;
	}

	public static IAnvilRecipe getRecipeByName(String name) {
		return (IAnvilRecipe) getInstance().recipeMap.get(name);
	}

	public static IAnvilRecipe[] getRecipeByName(String... name) {
		// using for each loop to display contents of a
		List<IAnvilRecipe> recipes = new ArrayList<>();
		for (String i : name) {
			recipes.add(getRecipeByName(i));
		}
		IAnvilRecipe[] array = new IAnvilRecipe[recipes.size()];
		recipes.toArray(array);
		return array;
	}

	public IAnvilRecipe addRecipe(ItemStack result, Skill skill, String research, boolean hot, String tool, int hammer, int anvil, int time, Object... input) {
		return null;
		//		return addRecipe(result, skill, research, hot, tool, hammer, anvil, time, (byte) 0, input);
	}

	public IAnvilRecipe addToolRecipe(ItemStack result, Skill skill, String research, boolean hot,
			String tool, int hammer, int anvil, int time, Object... input) {
		return null;
		//		return addRecipe(result, skill, research, hot, tool, hammer, anvil, time, (byte) 1, input);
	}

	/**
	 * Adds a recipe. See spreadsheet on first page for details.
	 */
	public IAnvilRecipe addRecipe(String name, ItemStack result, Skill skill, String research, boolean hot, String tool, int hammer, int anvil, int time, String recipeType, String oreDictList, Object... input) {
		StringBuilder keyString = new StringBuilder();
		int inputSelector = 0;
		int width = 0;
		int height = 0;
		int keyStringsLength;

		if (input[inputSelector] instanceof String[]) {
			String[] keyStrings = ((String[]) input[inputSelector++]);
			keyStringsLength = keyStrings.length;

			for (int i = 0; i < keyStringsLength; ++i) {
				String keyStringBuilder = keyStrings[i];
				++height;
				width = keyStringBuilder.length();
				keyString.append(keyStringBuilder);
			}
		} else {
			while (input[inputSelector] instanceof String) {
				String keyStringBuilder = (String) input[inputSelector++];
				++height;
				width = keyStringBuilder.length();
				keyString.append(keyStringBuilder);
			}
		}

		HashMap keyHashMap;
		for (keyHashMap = new HashMap(); inputSelector < input.length; inputSelector += 2) {

			Character inputCharacter = (Character) input[inputSelector];
			ItemStack inputItem = ItemStack.EMPTY;
			ItemStack[] inputItems = null;

			if (input[inputSelector + 1] instanceof Item) {
				inputItem = new ItemStack((Item) input[inputSelector + 1], 1, 32767);
			} else if (input[inputSelector + 1] instanceof Block) {
				inputItem = new ItemStack((Block) input[inputSelector + 1], 1, 32767);
			} else if (input[inputSelector + 1] instanceof ItemStack) {
				inputItem = (ItemStack) input[inputSelector + 1];
			} else if (input[inputSelector + 1] instanceof ItemStack[]){
				inputItems = ((ItemStack[]) input[inputSelector + 1]);
			}

			if (!inputItem.isEmpty()){
				keyHashMap.put(inputCharacter, inputItem);
			}
			else {
				keyHashMap.put(inputCharacter, inputItems);
			}
		}

		ItemStack[] inputs = new ItemStack[width * height];

		for (keyStringsLength = 0; keyStringsLength < width * height; ++keyStringsLength) {
			char charAt = keyString.charAt(keyStringsLength);

			if (keyHashMap.containsKey(charAt)) {
				if (keyHashMap.get(charAt) instanceof ItemStack){
					inputs[keyStringsLength] = ((ItemStack) keyHashMap.get(charAt)).copy();
				}
				else if (keyHashMap.get(charAt) instanceof ItemStack[]) {
					inputs = (ItemStack[]) keyHashMap.get(charAt);
				}
			}
			else {
				inputs[keyStringsLength] = ItemStack.EMPTY;
			}
		}

		IAnvilRecipe recipe;
		if (recipeType.equals("CustomToolRecipe")) {
			recipe = new CustomToolRecipeAnvil(width, height, inputs, result, tool, time, hammer, anvil, hot, research, skill);
		}
		else if ( recipeType.equals("CustomToolOreDictAnvilRecipes")){
			recipe = new CustomToolRecipeAnvil(width, height, inputs, result, tool, time, hammer, anvil, hot, research, skill, oreDictList);
		}
		else {
			recipe = new ShapedAnvilRecipes(width, height, inputs, result, tool, time, hammer, anvil, hot, research, skill);
		}
		this.recipes.add(recipe);
		this.recipeMap.put(name, recipe);
		return recipe;
	}

	private boolean canRepair(ItemStack item1, ItemStack item2) {
		if (item1.getItem() == item2.getItem() && item1.getCount() == 1 && item2.getCount() == 1
				&& item1.getItem().isRepairable()) {
			return true;
		}
		if (item1.getItem() == item2.getItem() && item1.getCount() == 1 && item2.getCount() == 1 && item1.isItemDamaged()
				&& CustomToolHelper.areToolsSame(item1, item2)) {
			return true;
		}
		return false;
	}

	public ItemStack findMatchingRecipe(IAnvil anvil, AnvilCraftMatrix matrix, World world) {
		int time;
		int anvilTier;
		boolean hot;
		int hammer;
		int matrixItemStackCount = 0;
		String toolType;
		SoundEvent sound;
		ItemStack stack0 = ItemStack.EMPTY;
		ItemStack stack1 = ItemStack.EMPTY;

		for (int currentSlotIndex = 0; currentSlotIndex < matrix.getSizeInventory(); ++currentSlotIndex) {
			ItemStack matrixSlotStack = matrix.getStackInSlot(currentSlotIndex);

			// Logic to check if the matrix should match an item repair recipe. (The same two items next to each other in the first two slow).
			if (!matrixSlotStack.isEmpty()) {
				if (matrixItemStackCount == 0) {
					stack0 = matrixSlotStack;
				}

				if (matrixItemStackCount == 1) {
					stack1 = matrixSlotStack;
				}

				++matrixItemStackCount;
			}
		}

		// Logic to check if the matrix should match an item repair recipe. (The same two items next to each other in the first two slow).
		if (matrixItemStackCount == 2 && stack0.getItem() == stack1.getItem() && stack0.getCount() == 1 && stack1.getCount() == 1
				&& stack0.getItem().isRepairable()) {

			Item item0 = stack0.getItem();

			int item0RemainingDurability = item0.getMaxDamage() - stack0.getItemDamage();
			int itemDamageDifference = item0.getMaxDamage() - stack1.getItemDamage();
			int itemDamageModifier =  (int) (item0RemainingDurability + itemDamageDifference + item0.getMaxDamage() * 0.1);
			int newItemDamage = Math.max(0, item0.getMaxDamage() - itemDamageModifier);

			return new ItemStack(stack0.getItem(), 1, newItemDamage);
		} else {
			//// Normal, registered recipes.
			Iterator recipeIterator = this.recipes.iterator();
			IAnvilRecipe iAnvilRecipe = null;

			while (recipeIterator.hasNext()) {
				IAnvilRecipe rec = (IAnvilRecipe) recipeIterator.next();

				if (((IRecipe) rec).matches(matrix, world)) {
					iAnvilRecipe = rec;
					break;
				}
			}

			if (iAnvilRecipe != null) {
				time = iAnvilRecipe.getCraftTime();
				hammer = iAnvilRecipe.getRecipeHammer();
				anvilTier = iAnvilRecipe.getAnvilTier();
				hot = iAnvilRecipe.outputHot();
				toolType = iAnvilRecipe.getToolType();

				if (!iAnvilRecipe.useCustomTiers()){
					anvil.setProgressMax(time);
					anvil.setRequiredHammerTier(hammer);
					anvil.setRequiredAnvilTier(anvilTier);
				}

				anvil.setHotOutput(hot);
				anvil.setRequiredToolType(toolType);

				if (!iAnvilRecipe.getResearch().equalsIgnoreCase("tier")){
					anvil.setRequiredResearch(iAnvilRecipe.getResearch());
				}

				anvil.setRequiredSkill(iAnvilRecipe.getSkill());

				return iAnvilRecipe.getCraftingResult(matrix);
			}
			return ItemStack.EMPTY;
		}
	}
}
