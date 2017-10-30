package minefantasy.mf2.api.knowledge.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class EntryPageText extends EntryPage {
    private Minecraft mc = Minecraft.getMinecraft();
    private String paragraph;
    private Object[] additional;

    public EntryPageText(String paragraph, Object... additional) {
        this(paragraph);
        this.additional = additional;
    }

    public EntryPageText(String paragraph) {
        this.paragraph = paragraph;
    }

    @Override
    public void render(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick) {
        String local = StatCollector.translateToLocal(paragraph);
        if (additional != null && additional.length > 0) {
            local = StatCollector.translateToLocalFormatted(paragraph, additional);
        }
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
                    text = text + temp + EnumChatFormatting.DARK_BLUE + EnumChatFormatting.BOLD;
                    temp = "";
                } else if (c == "d".charAt(0)) {
                    text = text + temp + EnumChatFormatting.DARK_RED;
                    temp = "";
                } else if (c == "y".charAt(0)) {
                    text = text + temp + EnumChatFormatting.GOLD;
                    temp = "";
                } else if (c == "u".charAt(0)) {
                    text = text + temp + EnumChatFormatting.UNDERLINE;
                    temp = "";
                } else if (c == "r".charAt(0)) {
                    text = text + temp + EnumChatFormatting.RESET + EnumChatFormatting.BLACK;
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
        mc.fontRenderer.drawSplitString(text, posX + 14, posY + 15, 155, 0);
    }

    @Override
    public void preRender(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick) {
    }
}
