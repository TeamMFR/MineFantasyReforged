package minefantasy.mf2.integration.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.api.knowledge.ResearchLogic;
import minefantasy.mf2.api.refine.BlastFurnaceRecipes;
import minefantasy.mf2.knowledge.KnowledgeListMF;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.Map.Entry;

public class RecipeHandlerBlastFurnace extends TemplateRecipeHandler {

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal("method.blastfurnace");
    }

    @Override
    public String getGuiTexture() {
        return "minefantasy2:textures/gui/blast_chamber.png";
    }

    @Override
    public void drawBackground(int recipe) {
        GL11.glColor4f(1, 1, 1, 1);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 5, 0, 133, 112);
    }

    @Override
    public int recipiesPerPage() {
        return 1;
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        if (ResearchLogic.hasInfoUnlocked(Minecraft.getMinecraft().thePlayer, KnowledgeListMF.blastfurn)) {
            for (Entry<ItemStack, ItemStack> entry : BlastFurnaceRecipes.smelting().getSmeltingList().entrySet()) {
                if (CustomToolHelper.areEqual(entry.getValue(), result)) {
                    CachedBlastFurnaceRecipe recipe = new CachedBlastFurnaceRecipe(entry.getKey(), result);
                    arecipes.add(recipe);
                }
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        if (ResearchLogic.hasInfoUnlocked(Minecraft.getMinecraft().thePlayer, KnowledgeListMF.blastfurn)) {
            ItemStack result = BlastFurnaceRecipes.smelting().getSmeltingResult(ingredient);
            if (result != null) {
                CachedBlastFurnaceRecipe recipe = new CachedBlastFurnaceRecipe(ingredient, result);
                arecipes.add(recipe);
            }
        }
    }

    private class CachedBlastFurnaceRecipe extends CachedRecipe {

        private PositionedStack input;
        private PositionedStack output;

        private CachedBlastFurnaceRecipe(ItemStack inputStack, ItemStack outputStack) {
            input = new PositionedStack(inputStack, 75, 30);
            output = new PositionedStack(outputStack, 75, 68);
        }

        @Override
        public PositionedStack getOtherStack() {
            return input;
        }

        @Override
        public PositionedStack getResult() {
            return output;
        }

    }
}
