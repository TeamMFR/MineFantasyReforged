package minefantasy.mfr.gui;

import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.helpers.GuiHelper;
import minefantasy.mfr.api.helpers.TextureHelperMFR;
import minefantasy.mfr.api.helpers.ToolHelper;
import minefantasy.mfr.tile.TileEntityCarpenterMFR;
import minefantasy.mfr.container.ContainerCarpenterMFR;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiCarpenterMF extends GuiContainer {
    private TileEntityCarpenterMFR tile;
    private int regularXSize = 176;

    public GuiCarpenterMF(InventoryPlayer user, TileEntityCarpenterMFR tile) {
        super(new ContainerCarpenterMFR(user, tile));
        this.xSize = 195;
        this.ySize = 240;
        this.tile = tile;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the
     * items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        boolean knowsCraft = tile.doesPlayerKnowCraft(mc.player);
        String s = MineFantasyReborn.isDebug() ? "Carpenter Bench Crafting"
                : knowsCraft ? I18n.translateToLocal(tile.getResultName()) : "????";
        this.fontRenderer.drawString(s, 10, 8, 0);

        int xPoint = (this.width - this.xSize) / 2;
        int yPoint = (this.height - this.ySize) / 2;

        if (knowsCraft && !tile.resName.equalsIgnoreCase("")) {
            if (tile.getToolNeeded() != null) {
                if (x < xPoint && x > xPoint - 20 && y < yPoint + 20 && y > yPoint) {
                    String s2 = I18n.translateToLocal("tooltype." + tile.getToolNeeded()) + ", "
                            + (tile.getToolTierNeeded() > -1
                            ? I18n.translateToLocal("attribute.mfcrafttier.name") + " "
                            + tile.getToolTierNeeded()
                            : I18n.translateToLocal("attribute.nomfcrafttier.name"));
                    this.fontRenderer.drawStringWithShadow(s2, -18, -12,
                            isToolSufficient() ? 16777215 : GuiHelper.getColourForRGB(150, 0, 0));
                }
            }
            if (x < xPoint + regularXSize + 20 && x > xPoint + regularXSize && y < yPoint + 20 && y > yPoint) {
                String s2 = I18n.translateToLocal("tooltype.carpenter") + ", "
                        + (tile.getCarpenterTierNeeded() > -1
                        ? I18n.translateToLocal("attribute.mfcrafttier.name") + " "
                        + tile.getCarpenterTierNeeded()
                        : I18n.translateToLocal("attribute.nomfcrafttier.name"));
                this.fontRenderer.drawStringWithShadow(s2, regularXSize - fontRenderer.getStringWidth(s2) + 18,
                        -12, 16777215);
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TextureHelperMFR.getResource("textures/gui/carpenter.png"));
        int xPoint = (this.width - this.xSize) / 2;
        int yPoint = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(xPoint, yPoint, 0, 0, this.xSize, this.ySize);

        if (tile.progressMax > 0 && tile.progress > 0) {
            int progressWidth = (int) (160F / tile.progressMax * tile.progress);
            this.drawTexturedModalRect(xPoint + 8, yPoint + 21, 0, 240, progressWidth, 3);
        }
        if (tile.doesPlayerKnowCraft(mc.player) && !tile.resName.equalsIgnoreCase("")) {
            GuiHelper.renderToolIcon(this, "carpenter", tile.getCarpenterTierNeeded(), xPoint + regularXSize, yPoint,
                    true);

            if (tile.getToolNeeded() != null) {
                GuiHelper.renderToolIcon(this, tile.getToolNeeded(), tile.getToolTierNeeded(), xPoint - 20, yPoint,
                        isToolSufficient());
            }
        }
    }

    private boolean isToolSufficient() {
        if (mc.player != null) {
            return ToolHelper.isToolSufficient(mc.player.getHeldItem(EnumHand.MAIN_HAND), tile.getToolNeeded(),
                    tile.getToolTierNeeded());
        }
        return false;
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        super.drawScreen(x, y, f);
    }

    private void renderItem(ItemStack itemstack, int x, int y, int mouseX, int mouseY) {
        itemRender.renderItemAndEffectIntoGUI(itemstack, x, y);
        itemRender.renderItemOverlayIntoGUI(this.fontRenderer, itemstack, x, y, this.mc.getTextureManager().toString());
    }

    private void renderItemName(ItemStack itemstack, int x, int y, int mouseX, int mouseY) {
        if (this.isPointInRegion(x - guiLeft, y - guiTop, 16, 16, mouseX, mouseY)) {
            this.renderToolTip(itemstack, mouseX, mouseY);
        }
    }
}