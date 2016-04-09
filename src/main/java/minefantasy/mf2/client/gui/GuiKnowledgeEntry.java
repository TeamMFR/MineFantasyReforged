package minefantasy.mf2.client.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.knowledge.client.EntryPageCraft;
import minefantasy.mf2.api.knowledge.client.EntryPage;
import minefantasy.mf2.api.knowledge.InformationBase;
import minefantasy.mf2.api.helpers.TextureHelperMF;
import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiKnowledgeEntry extends GuiScreen
{
	//GuiScreenBook
	private final GuiScreen parentGui;
	private final InformationBase infoBase;
	private Minecraft mc = Minecraft.getMinecraft();
	private int pages = 1;
	private int currentPage = 0;
	public  int bookImageWidth = EntryPageCraft.universalBookImageWidth;
	public  int bookImageHeight = EntryPageCraft.universalBookImageHeight;
    private GuiKnowledgeEntry.NextPageButton buttonNextPage;
    private GuiKnowledgeEntry.NextPageButton buttonPreviousPage;
    private GuiButton buttonDone;
    
	public GuiKnowledgeEntry(GuiScreen parent, InformationBase info)
	{
		this.parentGui = parent;
		this.infoBase = info;
		pages = info.getPages().size();
	}
	
	@Override
	public void initGui()
    {
		int xPoint = (this.width - this.bookImageWidth) / 2;
        int yPoint = (this.height - this.bookImageHeight) / 2;
        
        this.buttonList.clear();
        this.buttonList.add(this.buttonDone = new GuiButton(0, this.width / 2 - 100, 4 + yPoint + this.bookImageHeight, 200, 20, I18n.format("gui.done", new Object[0])));
	    this.buttonList.add(this.buttonNextPage = new GuiKnowledgeEntry.NextPageButton(1, xPoint + 120, yPoint + 170, true));
	    this.buttonList.add(this.buttonPreviousPage = new GuiKnowledgeEntry.NextPageButton(2, xPoint + 6, yPoint + 170, false));
	    this.updateButtons();
    }
	
	private void updateButtons()
    {
        this.buttonNextPage.visible = (this.currentPage < this.pages - 1);
        this.buttonPreviousPage.visible = this.currentPage > 0;
    }
	private static boolean lastTick = true;
	private static boolean canTick = true;
	@Override
	public void drawScreen(int x, int y, float f)
    {
		boolean onTick = false;
		
		boolean currTick = mc.theWorld.getTotalWorldTime() % 10 == 0;//has a second passed
		
		if(currTick != lastTick)
		{
			canTick = !canTick;
			if(canTick)
			{
				onTick = true;
			}
		}
		lastTick = currTick;
		
		int xPoint = (this.width - this.bookImageWidth) / 2;
        int yPoint = (this.height - this.bookImageHeight) / 2;
        
		this.mc.getTextureManager().bindTexture(TextureHelperMF.getResource("textures/gui/knowledge/book.png"));
        this.drawTexturedModalRect(xPoint, yPoint, 0, 0, this.bookImageWidth, this.bookImageHeight);
        EntryPage page = infoBase.getPages().get(currentPage);
        if(page != null)
		{
			page.preRender(this, x, y, f, xPoint, yPoint, onTick);
		}
        
        super.drawScreen(x, y, f);
        
        if(page != null)
		{
			page.render(this, x, y, f, xPoint, yPoint, onTick);
		}
        
        String s = I18n.format("book.pageIndicator", new Object[] {Integer.valueOf(this.currentPage + 1), Integer.valueOf(this.pages)});
        int l = mc.fontRenderer.getStringWidth(s) / 2;
        this.fontRendererObj.drawString(s, xPoint + (bookImageWidth/2) - l, yPoint + bookImageHeight-16, 0);
    }
	
	@Override
	protected void actionPerformed(GuiButton button)
    {
        if (button.id == 0)
        {
            this.mc.displayGuiScreen(parentGui);
        }
        if (button.id == 1)
        {
        	if(currentPage < pages-1)
        	{
        		++currentPage;
        	}
        }
        if (button.id == 2)
        {
        	if(currentPage > 0)
        	{
        		--currentPage;
        	}
        }
        updateButtons();
    }
	
	@Override
	protected void keyTyped(char p_73869_1_, int p_73869_2_)
    {
        if (p_73869_2_ == this.mc.gameSettings.keyBindInventory.getKeyCode())
        {
        	mc.thePlayer.openGui(MineFantasyII.instance, 1, mc.thePlayer.worldObj, 0, -1, 0);
        }
        else
        {
            super.keyTyped(p_73869_1_, p_73869_2_);
        }
    }
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
	
	
	@SideOnly(Side.CLIENT)
    static class NextPageButton extends GuiButton
    {
        private final boolean isNextPage;
        private static final String __OBFID = "CL_00000745";

        public NextPageButton(int p_i1079_1_, int p_i1079_2_, int p_i1079_3_, boolean p_i1079_4_)
        {
            super(p_i1079_1_, p_i1079_2_, p_i1079_3_, 23, 13, "");
            this.isNextPage = p_i1079_4_;
        }

        /**
         * Draws this button to the screen.
         */
        public void drawButton(Minecraft mc, int x, int y)
        {
            if (this.visible)
            {
                boolean flag = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height;
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(TextureHelperMF.getResource("textures/gui/knowledge/book.png"));
                int k = 0;
                int l = 180;

                if (flag)
                {
                    k += 18;
                }

                if (!this.isNextPage)
                {
                    l += 10;
                }

                this.drawTexturedModalRect(this.xPosition, this.yPosition, k, l, 18, 10);
            }
        }
    }
}
