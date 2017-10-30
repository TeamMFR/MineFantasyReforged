package minefantasy.mf2.integration.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import minefantasy.mf2.api.crafting.MineFantasyFuels;
import minefantasy.mf2.api.crafting.refine.BloomRecipe;
import minefantasy.mf2.api.heating.ForgeFuel;
import minefantasy.mf2.api.heating.ForgeItemHandler;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.block.tileentity.blastfurnace.TileEntityBlastFC;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class RecipeHandlerBloom extends TemplateRecipeHandler {

    private static ArrayList<FuelPair> afuels;

    private static void findFuels() {
        afuels = new ArrayList<FuelPair>();
        for (ForgeFuel fuel : ForgeItemHandler.forgeFuel) {
            ItemStack item = fuel.fuel;
            if (TileEntityBlastFC.isCarbon(item) && MineFantasyFuels.getCarbon(item) > 0) {
                afuels.add(new FuelPair(item.copy()));
            }
        }
    }

    @Override
    public TemplateRecipeHandler newInstance() {
        if (afuels == null || afuels.isEmpty()) {
            findFuels();
        }
        return super.newInstance();
    }

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("method.bloomery");
    }

    @Override
    public String getGuiTexture() {
        return "minefantasy2:textures/gui/bloomery.png";
    }

    @Override
    public int recipiesPerPage() {
        return 1;
    }

    @Override
    public void drawBackground(int recipe) {
        GL11.glColor4f(1, 1, 1, 1);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 0, 11, 176, 89);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        Map<ItemStack, ItemStack> recipes = BloomRecipe.recipeList;
        for (Entry<ItemStack, ItemStack> recipe : recipes.entrySet()) {
            if (CustomToolHelper.areEqual(recipe.getValue(), result)) {
                arecipes.add(new SmeltingPair(recipe.getKey(), recipe.getValue()));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        ItemStack result = BloomRecipe.getSmeltingResult(ingredient);
        if (result != null) {
            SmeltingPair arecipe = new SmeltingPair(ingredient, result);
            arecipe.setIngredientPermutation(Arrays.asList(arecipe.ingred), ingredient);
            arecipes.add(arecipe);
        }
    }

    private static class FuelPair {
        private PositionedStack stack;

        private FuelPair(ItemStack fuel) {
            this.stack = new PositionedStack(fuel, 75, 57, false);
        }
    }

    private class SmeltingPair extends CachedRecipe {
        private PositionedStack ingred;
        private PositionedStack result;

        private SmeltingPair(ItemStack ingred, ItemStack result) {
            this.ingred = new PositionedStack(ingred, 75, 19);
            this.result = new PositionedStack(result, 120, 29);
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 48, Arrays.asList(ingred));
        }

        @Override
        public PositionedStack getResult() {
            return result;
        }

        @Override
        public PositionedStack getOtherStack() {
            return afuels.get((cycleticks / 48) % afuels.size()).stack;
        }
    }
}