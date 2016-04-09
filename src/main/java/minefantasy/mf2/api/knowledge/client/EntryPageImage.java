package minefantasy.mf2.api.knowledge.client;

import org.lwjgl.opengl.GL11;

import minefantasy.mf2.api.helpers.RenderHelper;
import minefantasy.mf2.api.helpers.TextureHelperMF;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;

public class EntryPageImage extends EntryPage
{
	private Minecraft mc = Minecraft.getMinecraft();
	private String[] paragraphs;
	/**
	 * Recommend size: 48x48
	 */
	private String image;
	private int[] sizes;
	
	public EntryPageImage(String tex, String... paragraphs)
	{
		this(tex, 128, 128, paragraphs);
	}
	public EntryPageImage(String tex, int width, int height, String... paragraphs)
	{
		this.paragraphs = paragraphs;
		this.image = tex;
		this.sizes = new int[]{width, height};
	}
	@Override
	public void render(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick)
	{
		String text = "";
		for(String s: paragraphs)
		{
			text += StatCollector.translateToLocal(s);
			text += "\n\n";
		}
		mc.fontRenderer.drawSplitString(text, posX+14, posY+117, 117, 0);
	}
	@Override
	public void preRender(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick)
	{
		mc.renderEngine.bindTexture(TextureHelperMF.getResource(image));
		RenderHelper.drawTexturedModalRect(posX+14, posY+8, 2, 0, 0, sizes[0], sizes[1], 1F/(float)sizes[0], 1F/(float)sizes[1]);
	}

}
