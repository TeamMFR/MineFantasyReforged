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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class EntryPageBlastFurnace extends EntryPage
{
	private Minecraft mc = Minecraft.getMinecraft();
	private ItemStack input, output;
	
	public EntryPageBlastFurnace(ItemStack in, ItemStack out)
	{
		this.input = in;
		this.output = out;
	}
	
	public EntryPageBlastFurnace(Item i1, Item i2)
	{
		this(new ItemStack(i1), new ItemStack(i2));
	}

	@Override
	public void render(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick)
	{
		tooltipStack = null;
		
		int xPoint = (parent.width - universalBookImageWidth) / 2;
        int yPoint = (parent.height - universalBookImageHeight) / 2;
        
		this.mc.getTextureManager().bindTexture(TextureHelperMF.getResource("textures/gui/knowledge/blastfurnaceGrid.png"));
        parent.drawTexturedModalRect(xPoint, yPoint, 0, 0, this.universalBookImageWidth, this.universalBookImageHeight);
        
        String cft = "<" + StatCollector.translateToLocal("method.blastfurnace") + ">";
        mc.fontRenderer.drawSplitString(cft, posX+(universalBookImageWidth/2) - (mc.fontRenderer.getStringWidth(cft)/2), posY+150, 117, 0);
        
        renderRecipe(parent, x, y, f, posX, posY);
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

	private void renderRecipe(GuiScreen parent, int mx, int my, float f, int posX, int posY)
	{
		renderResult(parent, output, false, posX, posY+74, mx, my);
		renderResult(parent, input, false, posX, posY, mx, my);
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
