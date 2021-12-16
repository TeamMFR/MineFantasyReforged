package minefantasy.mfr.recipe;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.config.ConfigHardcore;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class RecipeRemover {
	@Mod.EventBusSubscriber(modid = MineFantasyReforged.MOD_ID)
	public static class RegistrationHandler {

		/**
		 * Remove crafting recipes.
		 *
		 * @param event The event
		 */
		@SubscribeEvent(priority = EventPriority.LOWEST)
		public static void removeRecipes(final RegistryEvent.Register<IRecipe> event) {
			removeRecipes(Items.STICK);
			if (ConfigHardcore.HCCRemoveCraftBread) {
				removeRecipes(Items.BREAD);
			}
			if (ConfigHardcore.HCCRemoveCraftPumpkinPie) {
				removeRecipes(Items.PUMPKIN_PIE);
			}
			if (ConfigHardcore.HCCRemoveCraftCake) {
				removeRecipes(Items.CAKE);
			}
			if (ConfigHardcore.HCCRemoveCraftFlintAndSteel) {
				removeRecipes(Items.FLINT_AND_STEEL);
			}
			if (ConfigHardcore.HCCRemoveCraftBucket) {
				removeRecipes(Items.BUCKET);
			}
			MineFantasyReforged.LOG.warn("Overriding recipes with dummy recipes, please ignore the above \"Dangerous alternative prefix\" warnings.");
		}

		/**
		 * Remove all crafting recipes with the specified {@link Item} as their output.
		 *
		 * @param output The output Item
		 */
		private static void removeRecipes(final Item output) {
			final int recipesRemoved = removeRecipes(recipe -> {
				final ItemStack recipeOutput = recipe.getRecipeOutput();
				return !recipeOutput.isEmpty() && recipe.getRegistryName().getResourceDomain().equals("minecraft") && recipeOutput.getItem() == output;
			});
		}

		/**
		 * Remove all crafting recipes that match the specified predicate.
		 *
		 * @param predicate The predicate
		 * @return The number of recipes removed
		 */
		private static int removeRecipes(final Predicate<IRecipe> predicate) {
			int recipesRemoved = 0;

			final IForgeRegistry<IRecipe> registry = ForgeRegistries.RECIPES;
			final List<IRecipe> toRemove = new ArrayList<>();

			for (final IRecipe recipe : registry) {
				if (predicate.test(recipe)) {
					toRemove.add(recipe);
					recipesRemoved++;
				}
			}
			toRemove.forEach(recipe -> {
				final ResourceLocation registryName = Objects.requireNonNull(recipe.getRegistryName());
				final IRecipe replacement = new DummyRecipe().setRegistryName(registryName);
				registry.register(replacement);
			});

			return recipesRemoved;
		}
	}
}
