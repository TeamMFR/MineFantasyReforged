package minefantasy.mf2.integration.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import minefantasy.mf2.api.crafting.anvil.CraftingManagerAnvil;
import minefantasy.mf2.api.crafting.anvil.IAnvilRecipe;
import minefantasy.mf2.api.crafting.anvil.ShapedAnvilRecipes;
import minefantasy.mf2.api.crafting.anvil.ShapelessAnvilRecipes;
import minefantasy.mf2.api.crafting.exotic.SpecialForging;
import minefantasy.mf2.api.heating.Heatable;
import minefantasy.mf2.api.heating.IHotItem;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.knowledge.ResearchLogic;
import minefantasy.mf2.knowledge.KnowledgeListMF;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecipeHandlerAnvil extends TemplateRecipeHandler {

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("method.anvil");
    }

    @Override
    public String getGuiTexture() {
        return "minefantasy2:textures/gui/knowledge/anvilGrid.png";
    }

    @Override
    public void drawBackground(int recipe) {
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 5, 22, 166, 147);
    }

    @Override
    public int recipiesPerPage() {
        return 1;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadCraftingRecipes(ItemStack inputStack) {
        ItemStack hiddenStack = null;

        if (inputStack.getItem() instanceof IHotItem) {
            inputStack = Heatable.getItem(inputStack);
        }

        if (ResearchLogic.hasInfoUnlocked(Minecraft.getMinecraft().thePlayer, KnowledgeListMF.smeltDragonforge)) {
            for (Map.Entry<Item, Item> entry : SpecialForging.dragonforgeCrafts.entrySet()) {
                if (CustomToolHelper.areEqual(new ItemStack(entry.getValue()), inputStack)) {
                    hiddenStack = CustomToolHelper.tryDeconstruct(new ItemStack(entry.getKey()), inputStack);
                }
            }
        }

        /*if (inputStack.getItem() instanceof ISpecialCraftItem) {
            for (Map.Entry<String, Item> entry : SpecialForging.specialCrafts.entrySet()) {
                ItemStack dragonForgedStack = new ItemStack(entry.getValue());
                if (CustomToolHelper.areEqual(dragonForgedStack, inputStack)) {
                    String design = ((ISpecialCraftItem)entry.getValue()).getDesign(dragonForgedStack);
                    for (IAnvilRecipe irecipe : (List<IAnvilRecipe>) CraftingManagerAnvil.getInstance().getRecipeList()) {
                        ItemStack ingridient = new ItemStack(SpecialForging.getSpecialCraft(design, irecipe.getRecipeOutput()));
                        if(ingridient != null) {
                            hiddenStack = CustomToolHelper.tryDeconstruct(ingridient, inputStack);
                        }
                    }
                }
            }
        }*/

        for (IAnvilRecipe irecipe : (List<IAnvilRecipe>) CraftingManagerAnvil.getInstance().getRecipeList()) {
            if ((hiddenStack != null && CustomToolHelper.areEqual(irecipe.getRecipeOutput(), hiddenStack)) || CustomToolHelper.areEqual(irecipe.getRecipeOutput(), inputStack)) {
                if (ResearchLogic.hasInfoUnlocked(Minecraft.getMinecraft().thePlayer, irecipe.getResearch())) {
                    CachedAnvilRecipe recipe = handleRecipe(irecipe, inputStack);

                    if (recipe == null) {
                        continue;
                    }

                    arecipes.add(recipe);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        if (ingredient.getItem() instanceof IHotItem) {
            ingredient = Heatable.getItem(ingredient);
        }

        for (IAnvilRecipe irecipe : (List<IAnvilRecipe>) CraftingManagerAnvil.getInstance().getRecipeList()) {
            if (!ResearchLogic.hasInfoUnlocked(Minecraft.getMinecraft().thePlayer, irecipe.getResearch())) {
                continue;
            }

            CachedAnvilRecipe recipe = handleRecipe(irecipe, null);
            if (recipe == null || !recipe.contains(recipe.ingredients, ingredient)) {
                continue;
            }

            recipe.setIngredientPermutation(recipe.ingredients, ingredient);
            arecipes.add(recipe);
        }
    }

    private CachedAnvilRecipe handleRecipe(IAnvilRecipe irecipe, ItemStack inputStack) {
        if (irecipe instanceof ShapedAnvilRecipes) {
            return new CachedAnvilRecipe((ShapedAnvilRecipes) irecipe, inputStack);
        }
        if (irecipe instanceof ShapelessAnvilRecipes) {
            return new CachedAnvilRecipe((ShapelessAnvilRecipes) irecipe);
        }

        return null;
    }

    //TODO: Implement wood permutations, add additional helper method for custom material support in crafting and usage handlers
    private class CachedAnvilRecipe extends CachedRecipe {

        private final ArrayList<PositionedStack> ingredients = new ArrayList<PositionedStack>();
        private ItemStack inputStack;
        private IAnvilRecipe iAnvilRecipe;

        private CachedAnvilRecipe(ShapedAnvilRecipes recipe, ItemStack inputStack) {
            iAnvilRecipe = recipe;
            if (inputStack != null) {
                this.inputStack = inputStack;
                this.inputStack.stackSize = recipe.getRecipeOutput().stackSize;
            } else {
                this.inputStack = recipe.getRecipeOutput();
            }
            setShapedRecipeIngredients(recipe.recipeWidth, recipe.recipeHeight, recipe.recipeItems);
        }

        private CachedAnvilRecipe(ShapelessAnvilRecipes recipe) {
            iAnvilRecipe = recipe;
            inputStack = recipe.getRecipeOutput();
        }

        private void setShapedRecipeIngredients(int width, int height, Object[] items) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (items[y * width + x] == null) {
                        continue;
                    }

                    ItemStack cachedStack = ((ItemStack) items[y * width + x]).copy();
                    NEIHelper.fillMaterials(iAnvilRecipe, cachedStack, inputStack);
                    PositionedStack stack = new PositionedStack(cachedStack, 31 + x * 18, 54 + y * 18, false);
                    stack.setMaxSize(1);
                    ingredients.add(stack);
                }
            }
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 20, ingredients);
        }

        @Override
        public PositionedStack getResult() {
            return new PositionedStack(inputStack, 75, 20);
        }
    }
}
