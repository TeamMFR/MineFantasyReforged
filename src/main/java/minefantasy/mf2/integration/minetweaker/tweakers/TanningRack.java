package minefantasy.mf2.integration.minetweaker.tweakers;

import minefantasy.mf2.api.crafting.tanning.TanningRecipe;
import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.minefantasy.TanningRack")
public class TanningRack {
	
	@ZenMethod
	public static void addTieredRecipe(IItemStack output, IIngredient input, float time, int tier){
		MineTweakerAPI.apply(new TanningAction(output, input, time, tier, "knife"));
	}
	
	@ZenMethod
	public static void addTieredAndToolRecipe(IItemStack output, IIngredient input, float time, int tier, String tool){
		MineTweakerAPI.apply(new TanningAction(output, input, time, tier, tool));
	}
	
	
	public static class TanningAction extends OneWayAction{
		
		IItemStack output;
		IIngredient input;
		float time;
		int tier;
		String tool;
		
		public TanningAction(IItemStack output, IIngredient input, float time, int tier, String tool) {
			this.output = output;
			this.input = input;
			this.time = time;
			this.tier = tier;
			this.tool = tool;
		}
		
		@Override
		public void apply() {
			for(IItemStack s : input.getItems()){
				ItemStack stack = MineTweakerMC.getItemStack(s);
				TanningRecipe.addRecipe(stack, time, tier, tool, MineTweakerMC.getItemStack(output));
			}
		}

		@Override
		public String describe() {
			return "Creating Tanning Recipe";
		}
		
		@Override
		public Object getOverrideKey() {
			return null;
		}
		
	}
	
}
