package minefantasy.mfr.client.gui;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.helpers.GuiHelper;
import minefantasy.mfr.api.helpers.TextureHelperMFR;
import minefantasy.mfr.api.helpers.ToolHelper;
import minefantasy.mfr.container.ContainerBase;
import minefantasy.mfr.tile.TileEntityAnvil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiAnvilMF extends GuiContainer {
    private TileEntityAnvil tile;
    private int regularXSize = 176;
    private int xInvOffset = 28;

    public GuiAnvilMF(ContainerBase container, TileEntityAnvil tile) {
        super(container);
        this.xSize = 235;
        this.ySize = 210;
        this.tile = tile;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the
     * items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        boolean knowsCraft = tile.doesPlayerKnowCraft(mc.player);
        String s = MineFantasyReborn.isDebug() ? "Anvil Crafting"
                : knowsCraft ? I18n.format(tile.getResultName()) : "????";
        this.fontRenderer.drawString(s, 10 + xInvOffset, 8, 0);

        int xPoint = (this.width - this.xSize) / 2 + 30;
        int yPoint = (this.height - this.ySize) / 2;

        if (knowsCraft && !tile.getResultName().equalsIgnoreCase("")) {
            if (tile.getRequiredToolType() != null) {
                if (x < xPoint && x > xPoint - 20 && y < yPoint + 20 && y > yPoint) {
                    String s2 = I18n.format("tooltype." + tile.getRequiredToolType()) + ", "
                            + (tile.getToolTierNeeded() > -1
                            ? I18n.format("attribute.mfcrafttier.name") + " "
                            + tile.getToolTierNeeded()
                            : I18n.format("attribute.nomfcrafttier.name"));
                    this.fontRenderer.drawStringWithShadow(s2, -18 + xInvOffset, -12,
                            isToolSufficient() ? 16777215 : GuiHelper.getColourForRGB(150, 0, 0));
                }
            }
            if (x < xPoint + regularXSize + 20 && x > xPoint + regularXSize && y < yPoint + 20 && y > yPoint) {
                String s2 = I18n.format("tooltype.anvil") + ", "
                        + (tile.getRequiredAnvilTier() > -1
                        ? I18n.format("attribute.mfcrafttier.name") + " "
                        + tile.getRequiredAnvilTier()
                        : I18n.format("attribute.nomfcrafttier.name"));
                this.fontRenderer.drawStringWithShadow(s2,
                        regularXSize - fontRenderer.getStringWidth(s2) + 18 + xInvOffset, -12,
                        isBlockSufficient() ? 16777215 : GuiHelper.getColourForRGB(150, 0, 0));
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager()
                .bindTexture(TextureHelperMFR.getResource("textures/gui/" + tile.getTextureName() + ".png"));
        int xPoint = (this.width - this.xSize) / 2;
        int yPoint = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(xPoint, yPoint, 0, 0, this.xSize, this.ySize);

        if (tile.progressMax > 0 && tile.progress > 0) {
            int progressWidth = (int) (160F / tile.progressMax * tile.progress);
            this.drawTexturedModalRect(xPoint + 8 + xInvOffset, yPoint + 21, 0, 210, progressWidth, 3);
        }
        if (tile.doesPlayerKnowCraft(mc.player) && !tile.getResultName().equalsIgnoreCase("")) {
            GuiHelper.renderToolIcon(this, "anvil", tile.getRequiredAnvilTier(), xPoint + regularXSize + xInvOffset,
                    yPoint, isBlockSufficient());

            if (tile.getRequiredToolType() != null) {
                GuiHelper.renderToolIcon(this, tile.getRequiredToolType(), tile.getToolTierNeeded(), xPoint - 20 + xInvOffset,
                        yPoint, isToolSufficient());
            }
        }
    }

    private boolean isBlockSufficient() {
        return tile.getTier() >= tile.getRequiredAnvilTier();
    }

    private boolean isToolSufficient() {
        if (mc.player != null) {
            return ToolHelper.isToolSufficient(mc.player.getHeldItem(EnumHand.MAIN_HAND), tile.getRequiredToolType(),
                    tile.getToolTierNeeded());
        }
        return false;
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