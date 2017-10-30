package minefantasy.mf2.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.helpers.TextureHelperMF;
import minefantasy.mf2.api.knowledge.InformationBase;
import minefantasy.mf2.api.knowledge.client.EntryPage;
import minefantasy.mf2.api.knowledge.client.EntryPageCraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiKnowledgeEntry extends GuiScreen {
    private static boolean lastTick = true;
    private static boolean canTick = true;
    // GuiScreenBook
    private final GuiScreen parentGui;
    private final InformationBase infoBase;
    public int bookImageWidth = EntryPageCraft.universalBookImageWidth;
    public int bookImageHeight = EntryPageCraft.universalBookImageHeight;
    private Minecraft mc = Minecraft.getMinecraft();
    private int pages = 1;
    private int currentPage = 0;
    private GuiKnowledgeEntry.NextPageButton buttonNextPage;
    private GuiKnowledgeEntry.NextPageButton buttonPreviousPage;
    private GuiButton buttonDone;

    public GuiKnowledgeEntry(GuiScreen parent, InformationBase info) {
        this.parentGui = parent;
        this.infoBase = info;
        pages = info.getPages().size();
    }

    @Override
    public void initGui() {
        int xPoint = this.width / 2;
        int yPoint = (this.height - this.bookImageHeight) / 2;

        this.buttonList.clear();
        this.buttonList.add(this.buttonDone = new GuiButton(0, this.width / 2 - 100,
                4 + yPoint + this.bookImageHeight - 16, 200, 20, I18n.format("gui.done", new Object[0])));
        this.buttonList.add(this.buttonNextPage = new GuiKnowledgeEntry.NextPageButton(1, xPoint + bookImageWidth - 22,
                yPoint + 209, true));
        this.buttonList.add(this.buttonPreviousPage = new GuiKnowledgeEntry.NextPageButton(2,
                xPoint - bookImageWidth + 4, yPoint + 209, false));
        this.updateButtons();
    }

    private void updateButtons() {
        this.buttonNextPage.visible = (this.currentPage < this.pages - 2);
        this.buttonPreviousPage.visible = this.currentPage > 1;
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        boolean onTick = false;

        boolean currTick = mc.theWorld.getTotalWorldTime() % 10 == 0;// has a second passed

        if (currTick != lastTick) {
            canTick = !canTick;
            if (canTick) {
                onTick = true;
            }
        }
        lastTick = currTick;

        drawPage(x, y, f, currentPage, -(bookImageWidth / 2), "left_page", onTick);
        drawPage(x, y, f, currentPage + 1, (bookImageWidth / 2), "right_page", onTick);

        super.drawScreen(x, y, f);
    }

    public void drawPage(int x, int y, float f, int num, int offset, String tex, boolean onTick) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        int xPoint = (this.width - this.bookImageWidth) / 2 + offset;
        int yPoint = (this.height - this.bookImageHeight) / 2;

        this.mc.getTextureManager().bindTexture(TextureHelperMF.getResource("textures/gui/knowledge/" + tex + ".png"));
        this.drawTexturedModalRect(xPoint, yPoint, 0, 0, this.bookImageWidth, this.bookImageHeight);

        if (num < infoBase.getPages().size()) {
            EntryPage page = infoBase.getPages().get(num);
            if (page != null) {
                page.preRender(this, x, y, f, xPoint, yPoint, onTick);
                page.render(this, x, y, f, xPoint, yPoint, onTick);
            }
        }

        String s = I18n.format("book.pageIndicator",
                new Object[]{Integer.valueOf(num + 1), Integer.valueOf(this.pages)});
        int l = mc.fontRenderer.getStringWidth(s) / 2;
        this.fontRendererObj.drawString(s, xPoint + (bookImageWidth / 2) - l, yPoint + bookImageHeight - 16, 0);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            this.mc.displayGuiScreen(parentGui);
        }
        if (button.id == 1) {
            if (currentPage < pages - 2) {
                currentPage += 2;
            }
        }
        if (button.id == 2) {
            if (currentPage > 1) {
                currentPage -= 2;
            }
        }
        updateButtons();
    }

    @Override
    protected void keyTyped(char p_73869_1_, int p_73869_2_) {
        if (p_73869_2_ == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
            mc.thePlayer.openGui(MineFantasyII.instance, 1, mc.thePlayer.worldObj, 0, -1, 0);
        } else {
            super.keyTyped(p_73869_1_, p_73869_2_);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    static class NextPageButton extends GuiButton {
        private final boolean isNextPage;

        public NextPageButton(int p_i1079_1_, int p_i1079_2_, int p_i1079_3_, boolean p_i1079_4_) {
            super(p_i1079_1_, p_i1079_2_, p_i1079_3_, 23, 13, "");
            this.isNextPage = p_i1079_4_;
        }

        /**
         * Draws this button to the screen.
         */
        public void drawButton(Minecraft mc, int x, int y) {
            if (this.visible) {
                boolean flag = x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width
                        && y < this.yPosition + this.height;
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(TextureHelperMF.getResource("textures/gui/knowledge/book.png"));
                int k = 0;
                int l = 228;

                if (flag) {
                    k += 18;
                }

                if (!this.isNextPage) {
                    l += 10;
                }

                this.drawTexturedModalRect(this.xPosition, this.yPosition, k, l, 18, 10);
            }
        }

        @Override
        public void func_146113_a(SoundHandler snd) {
            snd.playSound(
                    PositionedSoundRecord.func_147674_a(new ResourceLocation("minefantasy2:block.flipPage"), 1.0F));
        }
    }
}
