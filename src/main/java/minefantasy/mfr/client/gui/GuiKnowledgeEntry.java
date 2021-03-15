package minefantasy.mfr.client.gui;

import codechicken.lib.gui.GuiDraw;
import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.client.knowledge.EntryPage;
import minefantasy.mfr.client.knowledge.EntryPageCraft;
import minefantasy.mfr.init.MineFantasySounds;
import minefantasy.mfr.mechanics.knowledge.InformationBase;
import minefantasy.mfr.util.TextureHelperMFR;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	public ItemStack hoveredItem = ItemStack.EMPTY;
	public int hoverX = 0;
	public int hoverY = 0;

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
		this.buttonList.add(this.buttonDone = new CloseButton(0, this.width / 2 + 162, 2 + yPoint));
		this.buttonList.add(this.buttonNextPage = new GuiKnowledgeEntry.NextPageButton(1, xPoint + bookImageWidth - 22,
				yPoint + 216, true));
		this.buttonList.add(this.buttonPreviousPage = new GuiKnowledgeEntry.NextPageButton(2,
				xPoint - bookImageWidth + 4, yPoint + 216, false));
		this.updateButtons();
	}

	private void updateButtons() {
		this.buttonNextPage.visible = (this.currentPage < this.pages - 2);
		this.buttonPreviousPage.visible = this.currentPage > 1;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float ticks) {
		boolean onTick = false;

		boolean currTick = mc.world.getTotalWorldTime() % 10 == 0;// has a second passed

		if (currTick != lastTick) {
			canTick = !canTick;
			if (canTick) {
				onTick = true;
			}
		}
		lastTick = currTick;

		drawPage(mouseX, mouseY, ticks, currentPage, -(bookImageWidth / 2), "left_page", onTick);
		drawPage(mouseX, mouseY, ticks, currentPage + 1, (bookImageWidth / 2), "right_page", onTick);
		super.drawScreen(mouseX, mouseY, ticks);

		if (!hoveredItem.isEmpty() && (mouseX > hoverX && mouseX < (hoverX + 16) && mouseY > hoverY && mouseY < (hoverY + 16))) {
			List<String> tooltipData = hoveredItem.getTooltip(Minecraft.getMinecraft().player, ITooltipFlag.TooltipFlags.NORMAL);
			List<String> parsedTooltip = new ArrayList<>();
			boolean first = true;

			for (String s : tooltipData) {
				String s_ = s;
				if (!first)
					s_ = TextFormatting.GRAY + s;
				parsedTooltip.add(s_);
				first = false;
			}

			GuiDraw.drawMultiLineTip(hoveredItem, mouseX, mouseY, parsedTooltip);
		}
	}

	public void drawPage(int x, int y, float ticks, int num, int offset, String tex, boolean onTick) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.enableBlend();
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		int xPoint = (this.width - this.bookImageWidth) / 2 + offset;
		int yPoint = (this.height - this.bookImageHeight) / 2;

		this.mc.getTextureManager().bindTexture(new ResourceLocation(MineFantasyReborn.MOD_ID, "textures/gui/knowledge/" + tex + ".png"));
		this.drawTexturedModalRect(xPoint, yPoint, 0, 0, this.bookImageWidth, this.bookImageHeight);

		if (num < infoBase.getPages().size()) {
			EntryPage page = infoBase.getPages().get(num);
			if (page != null) {
				page.preRender(this, x, y, ticks, xPoint, yPoint, onTick);
				page.render(this, x, y, ticks, xPoint, yPoint, onTick);
			}
		}

		if (num < this.pages) {
			String s = I18n.format("book.pageIndicator", num + 1, this.pages);
			int l = mc.fontRenderer.getStringWidth(s) / 2;

			this.fontRenderer.drawString(s, xPoint + (bookImageWidth / 2) - l, yPoint + bookImageHeight - 16, 0);
		}

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
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
			mc.player.openGui(MineFantasyReborn.INSTANCE, 1, mc.player.world, 0, -1, 0);
		} else {
			super.keyTyped(typedChar, keyCode);
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	static class NextPageButton extends GuiButton {
		private final boolean isNextPage;

		public NextPageButton(int buttonId, int x, int y, boolean isNextPage) {
			super(buttonId, x, y, 23, 13, "");
			this.isNextPage = isNextPage;
		}

		@Override
		public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
			if (this.visible) {
				this.hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				mc.getTextureManager().bindTexture(TextureHelperMFR.getResource("textures/gui/knowledge/book.png"));
				int k = 0;
				int l = 228;

				if (hovered) {
					k += 18;
				}

				if (!this.isNextPage) {
					l += 10;
				}

				this.drawTexturedModalRect(this.x, this.y, k, l, 18, 10);
			}
		}

		@Override
		public void playPressSound(SoundHandler soundHandler) {
			soundHandler.playSound(
					PositionedSoundRecord.getMasterRecord(MineFantasySounds.FLIP_PAGE, 1.0F));
		}
	}

	@SideOnly(Side.CLIENT)
	static class CloseButton extends GuiButton {

		public CloseButton(int buttonId, int x, int y) {
			super(buttonId, x, y, 16, 16, "");
		}

		@Override
		public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
			if (this.visible) {
				this.hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				mc.getTextureManager().bindTexture(TextureHelperMFR.getResource("textures/gui/knowledge/book.png"));
				int k = 67;
				int l = 228;

				if (hovered) {
					k += 16;
				}

				this.drawTexturedModalRect(this.x, this.y, k, l, 16, 16);
			}
		}

		@Override
		public void playPressSound(SoundHandler soundHandler) {
			soundHandler.playSound(
					PositionedSoundRecord.getMasterRecord(MineFantasySounds.FLIP_PAGE, 1.0F));
		}
	}
}
