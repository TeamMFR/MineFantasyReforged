package minefantasy.mf2.integration.minetweaker;

import minefantasy.mf2.api.crafting.anvil.AnvilCraftMatrix;
import minefantasy.mf2.api.crafting.anvil.IAnvilRecipe;
import minefantasy.mf2.api.rpg.Skill;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;

public class TweakedShapedAnvilRecipe implements IAnvilRecipe {

	private int hammer, anvil, /* craft, */ time, width, height;
	private float exp;
	private IItemStack result;
	private IIngredient[][] ingreds;
	private Skill s;
	private String research, tool;
	private boolean hot;

	public TweakedShapedAnvilRecipe(IIngredient[][] input, IItemStack output, String tool, int time, int hammer,
			int anvil, float exp, boolean hot, String research, Skill s) {
		this.height = 4;
		this.width = 6;
		this.ingreds = input;
		this.result = output;
		this.tool = tool;
		this.hammer = hammer;
		this.anvil = anvil;
		this.exp = exp;
		this.hot = hot;
		this.research = research;
		this.s = s;
		this.time = time;
	}

	@Override
	public int getAnvil() {
		return anvil;
	}

	@Override
	public int getCraftTime() {
		return time;
	}

	@Override
	public ItemStack getCraftingResult(AnvilCraftMatrix arg0) {
		return this.getRecipeOutput().copy();
	}

	@Override
	public float getExperiance() {
		return exp;
	}

	@Override
	public int getRecipeHammer() {
		return hammer;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return MineTweakerMC.getItemStack(result).copy();
	}

	@Override
	public int getRecipeSize() {
		return this.width * this.height;
	}

	@Override
	public String getResearch() {
		return research;
	}

	@Override
	public Skill getSkill() {
		return this.s;
	}

	@Override
	public String getToolType() {
		return tool;
	}

	@Override
	public boolean matches(AnvilCraftMatrix inv) {
		boolean matches = true;
		for (int x = 0; x < 6; x++) {
			for (int y = 0; y < 4; y++) {
				if (!matches)
					return false;
				ItemStack stack = inv.getStackInRowAndColumn(x, y);

				// int a = y + x * 4;
				if (stack == null && this.ingreds[y][x] == null) {
					continue;
				}
				boolean hasMatch = false;
				if (stack != null && this.ingreds[y][x] != null) {
					for (IItemStack i : this.ingreds[y][x].getItems()) {
						ItemStack ingred = MineTweakerMC.getItemStack(i);
						if (stack.getItem() == ingred.getItem() && stack.getItemDamage() == stack.getItemDamage()) {
							hasMatch = true;
						}
					}
				} else {
					matches = false;
				}
				if (!hasMatch)
					matches = false;
			}
		}
		return matches;
	}

	@Override
	public boolean outputHot() {
		return hot;
	}

	@Override
	public boolean useCustomTiers() {
		// TODO Auto-generated method stub
		return false;
	}

}
