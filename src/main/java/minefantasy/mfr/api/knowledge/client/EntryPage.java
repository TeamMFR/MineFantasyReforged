package minefantasy.mfr.api.knowledge.client;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;

@SideOnly(Side.CLIENT)
public abstract class EntryPage {
    public static final int universalBookImageWidth = 178;
    public static final int universalBookImageHeight = 227;

    public abstract void render(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick);

    public abstract void preRender(GuiScreen parent, int x, int y, float f, int posX, int posY, boolean onTick);
}
