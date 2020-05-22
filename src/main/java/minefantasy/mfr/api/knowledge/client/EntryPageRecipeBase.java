package minefantasy.mfr.api.knowledge.client;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import minefantasy.mfr.api.helpers.TextureHelperMFR;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.ArrayList;
import java.util.List;

public class EntryPageRecipeBase extends EntryPage {
    private Minecraft mc = Minecraft.getMinecraft();
    private IRecipe[] recipes;
    private int recipeID;
    private ItemStack tooltipStack;

    public EntryPageRecipeBase(List<IRecipe> recipes) {
        IRecipe[] array = new IRecipe[recipes.size()];
        for (int a = 0; a < recipes.size(); a++) {
            array[a] = recipes.get(a);
        }
        this.recipes = array;

    }

    public EntryPageRecipeBase(IRecipe... recipes) {
        this.recipes = recipes;
    }

    @Override
    public void render(GuiScreen parent, int x, int y, float ticks, int posX, int posY, boolean onTick) {
        if (onTick) {
            tickRecipes();
        }
        tooltipStack = null;

        this.mc.getTextureManager().bindTexture(TextureHelperMFR.getResource("textures/gui/knowledge/craftGrid.png"));
        parent.drawTexturedModalRect(posX, posY, 0, 0, this.universalBookImageWidth, this.universalBookImageHeight);

        IRecipe recipe = (recipeID < 0 || recipeID >= recipes.length) ? null : recipes[recipeID];
        String cft = "<" + I18n.format("method.workbench") + ">";
        mc.fontRenderer.drawSplitString(cft,
                posX + (universalBookImageWidth / 2) - (mc.fontRenderer.getStringWidth(cft) / 2), posY + 175, 117, 0);
        renderRecipe(parent, x, y, ticks, posX, posY, recipe);

        if (tooltipStack != null) {
            List<String> tooltipData = tooltipStack.getTooltip(Minecraft.getMinecraft().player, ITooltipFlag.TooltipFlags.NORMAL);
            List<String> parsedTooltip = new ArrayList();
            boolean first = true;

            for (String s : tooltipData) {
                String s_ = s;
                if (!first)
                    s_ = TextFormatting.GRAY + s;
                parsedTooltip.add(s_);
                first = false;
            }

            minefantasy.mfr.api.helpers.RenderHelper.renderTooltip(x, y, parsedTooltip);
        }
    }

    private void renderRecipe(GuiScreen parent, int mx, int my, float f, int posX, int posY, IRecipe recipe) {
        if (recipe == null) {
            return;
        }

        ItemStack[] ingredients;

        if (recipe instanceof ShapedRecipes) {
            ShapedRecipes shaped = (ShapedRecipes) recipe;

            for (int y = 0; y < shaped.recipeHeight; y++) {
                for (int x = 0; x < shaped.recipeWidth; x++) {
                    ingredients = shaped.recipeItems.get(y * shaped.recipeWidth + x).getMatchingStacks();
                    renderItemAtGridPos(parent, x, y, ingredients[recipeID % ingredients.length], true, posX, posY,
                            mx, my);
                }
            }
        } else if (recipe instanceof ShapedOreRecipe) {
            ShapedOreRecipe shaped = (ShapedOreRecipe) recipe;
            int width = shaped.getRecipeWidth();
            int height = shaped.getRecipeHeight();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    //used to be shaped.getInput[y * width + x] Might not be correct co
                    Object input = shaped.getIngredients().get(y * width + x);
                    if (input != null)
                        renderItemAtGridPos(parent, x, y,
                                input instanceof ItemStack ? (ItemStack) input : ((ArrayList<ItemStack>) input).get(0),
                                true, posX, posY, mx, my);
                }
            }
        } else if (recipe instanceof ShapelessRecipes) {
            ShapelessRecipes shapeless = (ShapelessRecipes) recipe;

            drawGrid:
            {
                for (int y = 0; y < 3; y++) {
                    for (int x = 0; x < 3; x++) {
                        int index = y * 3 + x;

                        if (index >= shapeless.recipeItems.size())
                            break drawGrid;
                        ingredients = shapeless.recipeItems.get(index).getMatchingStacks();
                        renderItemAtGridPos(parent, x, y, ingredients[recipeID % ingredients.length], true, posX,
                                posY, mx, my);
                    }
                }
            }
        } else if (recipe instanceof ShapelessOreRecipe) {
            ShapelessOreRecipe shapeless = (ShapelessOreRecipe) recipe;

            drawGrid:
            {
                for (int y = 0; y < 3; y++) {
                    for (int x = 0; x < 3; x++) {
                        int index = y * 3 + x;

                        if (index >= shapeless.getIngredients().size())
                            break drawGrid;

                        Object input = shapeless.getIngredients().get(index);
                        if (input != null)
                            renderItemAtGridPos(parent, x, y, input instanceof ItemStack ? (ItemStack) input
                                    : ((ArrayList<ItemStack>) input).get(0), true, posX, posY, mx, my);
                    }
                }
            }
        }

        renderResult(parent, recipe.getRecipeOutput(), false, posX, posY, mx, my);
    }

    private void tickRecipes() {
        if (recipeID < recipes.length - 1) {
            ++recipeID;
        } else {
            recipeID = 0;
        }
    }

    public void renderResult(GuiScreen gui, ItemStack stack, boolean accountForContainer, int xOrigin, int yOrigin,
            int mx, int my) {
        if (stack == null || stack.getItem() == null)
            return;
        stack = stack.copy();

        if (stack.getItemDamage() == Short.MAX_VALUE)
            stack.setItemDamage(0);

        int xPos = xOrigin + 80;
        int yPos = yOrigin + 42;
        ItemStack stack1 = stack.copy();
        if (stack1.getItemDamage() == -1)
            stack1.setItemDamage(0);

        renderItem(gui, xPos, yPos, stack1, accountForContainer, mx, my);
    }

    public void renderItemAtGridPos(GuiScreen gui, int x, int y, ItemStack stack, boolean accountForContainer,
            int xOrigin, int yOrigin, int mx, int my) {
        if (stack == null || stack.getItem() == null)
            return;
        stack = stack.copy();

        if (stack.getItemDamage() == Short.MAX_VALUE)
            stack.setItemDamage(0);

        int xPos = xOrigin + (x * 29) + 51;
        int yPos = yOrigin + (y * 29) + 86;
        ItemStack stack1 = stack.copy();
        if (stack1.getItemDamage() == -1)
            stack1.setItemDamage(0);

        renderItem(gui, xPos, yPos, stack1, accountForContainer, mx, my);
    }

    public int getGridX() {
        return 7;
    }

    public int getGridY() {
        return 36;
    }

    public void renderItem(GuiScreen gui, int xPos, int yPos, ItemStack stack, boolean accountForContainer, int mx,
            int my) {
        RenderItem render = Minecraft.getMinecraft().getRenderItem();
        if (mx > xPos && mx < (xPos + 16) && my > yPos && my < (yPos + 16)) {
            tooltipStack = stack;
        }

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        render.renderItemAndEffectIntoGUI(stack, xPos, yPos);
        render.renderItemAndEffectIntoGUI(stack, xPos, yPos);
        RenderHelper.disableStandardItemLighting();
        GL11.glPopMatrix();

        GL11.glDisable(GL11.GL_LIGHTING);
    }

    @Override
    public void preRender(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick) {
    }
}