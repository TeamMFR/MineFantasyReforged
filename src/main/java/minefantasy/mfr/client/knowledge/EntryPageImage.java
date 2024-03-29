package minefantasy.mfr.client.knowledge;

import minefantasy.mfr.MineFantasyReforged;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class EntryPageImage extends EntryPage {
	private Minecraft mc = Minecraft.getMinecraft();
	private String paragraph;
	/**
	 * Recommend size: 48x48
	 */
	private String image;
	private int[] sizes;

	public EntryPageImage(String tex, String paragraphs) {
		this(tex, 128, 128, paragraphs);
	}

	public EntryPageImage(String tex, int width, int height, String paragraphs) {
		this.paragraph = paragraphs;
		this.image = tex;
		this.sizes = new int[] {width, height};
	}

	@Override
	public void render(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick) {
		String local = I18n.format(paragraph);
		String text = "";
		String temp = "";
		boolean prefix = false;
		for (int a = 0; a < local.length(); a++) {
			char c = local.charAt(a);
			if (a == local.length() - 1) {
				text = text + temp + c;
				temp = "";
			} else if (prefix) {
				if (c == "h".charAt(0)) {
					text = text + temp + TextFormatting.DARK_BLUE + TextFormatting.BOLD;
					temp = "";
				} else if (c == "d".charAt(0)) {
					text = text + temp + TextFormatting.DARK_RED;
					temp = "";
				} else if (c == "y".charAt(0)) {
					text = text + temp + TextFormatting.GOLD;
					temp = "";
				} else if (c == "u".charAt(0)) {
					text = text + temp + TextFormatting.UNDERLINE;
					temp = "";
				} else if (c == "r".charAt(0)) {
					text = text + temp + TextFormatting.RESET + TextFormatting.BLACK;
					temp = "";
				}
				prefix = false;
			} else if (c == "^".charAt(0)) {
				text = text + temp + "\n\n";
				temp = "";
			} else if (c == "$".charAt(0)) {
				prefix = true;
			} else {
				temp = temp + c;
			}
		}

		mc.fontRenderer.drawSplitString(text, posX + 14, posY + 117, 155, 0);
	}

	@Override
	public void preRender(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick) {
		int xOffset = (universalBookImageWidth - sizes[0]) / 2;
		parent.mc.renderEngine.bindTexture(new ResourceLocation(MineFantasyReforged.MOD_ID, this.image));
		Gui.drawModalRectWithCustomSizedTexture(posX + xOffset, posY + 15, 512, 512, sizes[0], sizes[1], 128, 128);
	}

}
