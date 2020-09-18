package minefantasy.mfr.client.render.block;

import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.util.TransformUtils;
import minefantasy.mfr.client.model.block.ModelQuern;
import minefantasy.mfr.tile.TileEntityQuern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.animation.FastTESR;
import net.minecraftforge.common.model.IModelState;
import org.lwjgl.opengl.GL11;

public class TileEntityQuernRenderer<T extends TileEntity> extends FastTESR<T> implements IItemRenderer {
    private ModelQuern model;

    private static final ResourceLocation texture = new ResourceLocation("minefantasyreborn:textures/blocks/quern_basic.png");

    public TileEntityQuernRenderer() {
        model = new ModelQuern();
    }

    @Override
    public void renderTileEntityFast(T te, double x, double y, double z, float partialTick, int breakStage, float partial, BufferBuilder renderer) {

        renderAModelAt((TileEntityQuern) te, x, y, z, partial);
    }

    public void renderAModelAt(TileEntityQuern tile, double d, double d1, double d2, float f) {
        this.renderModelAt(tile, d, d1, d2, f);
    }

    public void renderModelAt(TileEntityQuern tile, double d, double d1, double d2, float f) {
        this.bindTexture(texture); // texture

        GL11.glPushMatrix(); // start
        float scale = 1.0F;
        float yOffset = 1.0F;
        GL11.glTranslatef((float) d + 0.5F, (float) d1 + yOffset, (float) d2 + 0.5F); // size
        GL11.glScalef(scale, -scale, -scale); // if you read this comment out this line and you can see what happens
        GL11.glPushMatrix();
        model.renderModel(tile, 0.0625F);

        GL11.glPopMatrix();
        GL11.glColor3f(255, 255, 255);
        GL11.glPopMatrix(); // end

    }

    public void renderInvModelAt(double d, double d1, double d2, float f) {

        GL11.glPushMatrix(); // start
        float scale = 1.0F;
        float yOffset = 1.0F;
        GL11.glTranslatef((float) d + 0.5F, (float) d1 + yOffset, (float) d2 + 0.5F); // size
        GL11.glScalef(scale, -scale, -scale); // if you read this comment out this line and you can see what happens
        GL11.glPushMatrix();
        model.renderModel(0.0625F);

        GL11.glPopMatrix();
        GL11.glColor3f(255, 255, 255);
        GL11.glPopMatrix(); // end

    }

    @Override
    public void renderItem(ItemStack stack, ItemCameraTransforms.TransformType transformType) {

        GlStateManager.pushMatrix();

        Minecraft.getMinecraft().renderEngine.bindTexture(texture);

        renderInvModelAt(0F, 0F, 0F, 0F);

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.popMatrix();
    }

    @Override
    public IModelState getTransforms() {
        return TransformUtils.DEFAULT_BLOCK;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return true;
    }
}
