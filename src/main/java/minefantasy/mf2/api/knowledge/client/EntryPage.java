package minefantasy.mf2.api.knowledge.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;

@SideOnly(Side.CLIENT)
public abstract class EntryPage {
    public static final int universalBookImageWidth = 178;
    public static final int universalBookImageHeight = 227;

    public abstract void render(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick);

    public abstract void preRender(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick);
}
