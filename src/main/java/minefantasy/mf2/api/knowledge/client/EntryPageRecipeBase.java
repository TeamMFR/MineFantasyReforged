package minefantasy.mf2.api.knowledge.client;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.MineFantasyAPI;
import minefantasy.mf2.api.helpers.ClientTickHandler;
import minefantasy.mf2.api.helpers.TextureHelperMF;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class EntryPageRecipeBase extends EntryPage
{
	private Minecraft mc = Minecraft.getMinecraft();
	private IRecipe[] recipes = new IRecipe[]{};
	public static int switchRate = 15;
	private int recipeID;
	private boolean shapelessRecipe = false;
	private boolean oreDictRecipe = false;
	
	public EntryPageRecipeBase(List<IRecipe> recipes)
	{
		IRecipe[] array = new IRecipe[recipes.size()];
		for(int a = 0; a < recipes.size(); a++)
		{
			array[a] = recipes.get(a);
		}
		this.recipes = array;
				
	}
	
	public EntryPageRecipeBase(IRecipe... recipes)
	{
		this.recipes = recipes;
	}
	
	@Override
	public void render(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick)
	{
		if(onTick)
		{
			tickRecipes();
		}
		tooltipStack = null;
		
		int xPoint = (parent.width - universalBookImageWidth) / 2;
        int yPoint = (parent.height - universalBookImageHeight) / 2;
        
		this.mc.getTextureManager().bindTexture(TextureHelperMF.getResource("textures/gui/knowledge/craftGrid.png"));
        parent.drawTexturedModalRect(xPoint, yPoint, 0, 0, this.universalBookImageWidth, this.universalBookImageHeight);
        
        IRecipe recipe = recipes[recipeID];
        String cft = "<" + StatCollector.translateToLocal("method.workbench") + ">";
        mc.fontRenderer.drawSplitString(cft, posX+(universalBookImageWidth/2) - (mc.fontRenderer.getStringWidth(cft)/2), posY+150, 117, 0);
        renderRecipe(parent, x, y, f, posX, posY, recipe);
        
        if(tooltipStack != null)
        {
			List<String> tooltipData = tooltipStack.getTooltip(Minecraft.getMinecraft().thePlayer, false);
			List<String> parsedTooltip = new ArrayList();
			boolean first = true;

			for(String s : tooltipData) 
			{
				String s_ = s;
				if(!first)
					s_ = EnumChatFormatting.GRAY + s;
				parsedTooltip.add(s_);
				first = false;
			}

			minefantasy.mf2.api.helpers.RenderHelper.renderTooltip(x, y, parsedTooltip);

			/*
			int tooltipY = 8 + tooltipData.size() * 11;

			if(tooltipEntry) 
			{
				vazkii.botania.client.core.helper.RenderHelper.renderTooltipOrange(mx, my + tooltipY, Arrays.asList(EnumChatFormatting.GRAY + StatCollector.translateToLocal("botaniamisc.clickToRecipe")));
				tooltipY += 18;
			}

			if(tooltipContainerStack != null)
			{
				vazkii.botania.client.core.helper.RenderHelper.renderTooltipGreen(mx, my + tooltipY, Arrays.asList(EnumChatFormatting.AQUA + StatCollector.translateToLocal("botaniamisc.craftingContainer"), tooltipContainerStack.getDisplayName()));
			}
			*/
		}
	}

	private void renderRecipe(GuiScreen parent, int mx, int my, float f, int posX, int posY, IRecipe recipe)
	{
		shapelessRecipe = false;
		oreDictRecipe = false;
		
		if(recipe instanceof ShapedRecipes) 
		{
			ShapedRecipes shaped = (ShapedRecipes)recipe;

			for(int y = 0; y < shaped.recipeHeight; y++)
			{
				for(int x = 0; x < shaped.recipeWidth; x++)
				{
					renderItemAtGridPos(parent, 1 + x, 1 + y, shaped.recipeItems[y * shaped.recipeWidth + x], true, posX, posY, mx, my);
				}
			}
		} 
		else if(recipe instanceof ShapedOreRecipe) 
		{
			ShapedOreRecipe shaped = (ShapedOreRecipe) recipe;
			int width = (Integer) ReflectionHelper.getPrivateValue(ShapedOreRecipe.class, shaped, 4);
			int height = (Integer) ReflectionHelper.getPrivateValue(ShapedOreRecipe.class, shaped, 5);

			for(int y = 0; y < height; y++)
			{
				for(int x = 0; x < width; x++) 
				{
					Object input = shaped.getInput()[y * width + x];
					if(input != null)
						renderItemAtGridPos(parent, 1 + x, 1 + y, input instanceof ItemStack ? (ItemStack) input : ((ArrayList<ItemStack>) input).get(0), true, posX, posY, mx, my);
				}
			}

			oreDictRecipe = true;
		} 
		else if(recipe instanceof ShapelessRecipes)
		{
			ShapelessRecipes shapeless = (ShapelessRecipes) recipe;

			drawGrid :
			{
				for(int y = 0; y < 3; y++)
				{
					for(int x = 0; x < 3; x++) 
					{
						int index = y * 3 + x;

						if(index >= shapeless.recipeItems.size())
							break drawGrid;

						renderItemAtGridPos(parent, 1 + x, 1 + y, (ItemStack) shapeless.recipeItems.get(index), true, posX, posY, mx, my);
					}
				}
			}

			shapelessRecipe = true;
		} 
		else if(recipe instanceof ShapelessOreRecipe)
		{
			ShapelessOreRecipe shapeless = (ShapelessOreRecipe) recipe;

			drawGrid : 
			{
				for(int y = 0; y < 3; y++)
				{
					for(int x = 0; x < 3; x++) 
					{
						int index = y * 3 + x;

						if(index >= shapeless.getRecipeSize())
							break drawGrid;

						Object input = shapeless.getInput().get(index);
						if(input != null)
							renderItemAtGridPos(parent, 1 + x, 1 + y, input instanceof ItemStack ? (ItemStack) input : ((ArrayList<ItemStack>) input).get(0), true, posX, posY, mx, my);
					}
				}
			}

			shapelessRecipe = true;
			oreDictRecipe = true;
		}

		renderResult(parent, recipe.getRecipeOutput(), false, posX, posY, mx, my);
	}
	private void tickRecipes()
	{
		if(recipeID < recipes.length-1)
		{
			++recipeID;
		}
		else
		{
			recipeID = 0;
		}
	}
	
	public void renderResult(GuiScreen gui, ItemStack stack, boolean accountForContainer, int xOrigin, int yOrigin, int mx, int my)
	{
		if(stack == null || stack.getItem() == null)
			return;
		stack = stack.copy();

		if(stack.getItemDamage() == Short.MAX_VALUE)
			stack.setItemDamage(0);

		int xPos = xOrigin + 65;
		int yPos = yOrigin + 20;
		ItemStack stack1 = stack.copy();
		if(stack1.getItemDamage() == -1)
			stack1.setItemDamage(0);

		renderItem(gui, xPos, yPos, stack1, accountForContainer, mx, my);
	}
	
	public void renderItemAtGridPos(GuiScreen gui, int x, int y, ItemStack stack, boolean accountForContainer, int xOrigin, int yOrigin, int mx, int my)
	{
		if(stack == null || stack.getItem() == null)
			return;
		stack = stack.copy();

		if(stack.getItemDamage() == Short.MAX_VALUE)
			stack.setItemDamage(0);

		int xPos = xOrigin + x * 29 + 7 + (y == 0  && x == 3 ? 10 : 0);
		int yPos = yOrigin + y * 29 + 36 - (y == 0 ? 7 : 0);
		ItemStack stack1 = stack.copy();
		if(stack1.getItemDamage() == -1)
			stack1.setItemDamage(0);

		renderItem(gui, xPos, yPos, stack1, accountForContainer, mx, my);
	}
	
	public int getGridX()
	{
		return 7;
	}
	public int getGridY()
	{
		return 36;
	}
	private ItemStack tooltipStack;
	public void renderItem(GuiScreen gui, int xPos, int yPos, ItemStack stack, boolean accountForContainer, int mx, int my) 
	{
		RenderItem render = new RenderItem();
		if(mx > xPos && mx < (xPos+16) && my > yPos && my < (yPos+16))
		{
			tooltipStack = stack;
		}
		boolean mouseDown = Mouse.isButtonDown(0);

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		render.renderItemAndEffectIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), stack, xPos, yPos);
		render.renderItemOverlayIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), stack, xPos, yPos);
		RenderHelper.disableStandardItemLighting();
		GL11.glPopMatrix();

		GL11.glDisable(GL11.GL_LIGHTING);
	}

	@Override
	public void preRender(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick)
	{
	}
}
