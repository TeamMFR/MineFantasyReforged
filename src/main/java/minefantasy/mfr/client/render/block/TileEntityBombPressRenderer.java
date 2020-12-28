package minefantasy.mfr.client.render.block;

import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.util.TransformUtils;
import minefantasy.mfr.block.BlockBombPress;
import minefantasy.mfr.client.model.block.ModelBombPress;
import minefantasy.mfr.tile.TileEntityBombPress;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.animation.FastTESR;
import net.minecraftforge.common.model.IModelState;

/**
 * @author Anonymous Productions
 * <p>
 * Sources are provided for educational reasons. though small bits of
 * code, or methods can be used in your own creations.
 * <p>
 * Custom renderers based off render tutorial by MC_DucksAreBest
 */
public class TileEntityBombPressRenderer <T extends TileEntity> extends FastTESR<T> implements IItemRenderer {
    private ModelBombPress model;
    private static final ResourceLocation texture = new ResourceLocation("minefantasyreborn:textures/blocks/bomb_press.png");

    public TileEntityBombPressRenderer() {
        model = new ModelBombPress();
    }

    @Override
    public void renderTileEntityFast(T te, double x, double y, double z, float partialTick, int breakStage, float partial, BufferBuilder renderer) {
        renderModelAt((TileEntityBombPress) te, x, y, z, partial, ((TileEntityBombPress) te).animation);
    }

    public void renderModelAt(TileEntityBombPress tile, double d, double d1, double d2, float f, float animation) {
        EnumFacing facing = EnumFacing.NORTH;
        if (tile.hasWorld()) {
            IBlockState state = tile.getWorld().getBlockState(tile.getPos());
            facing = state.getValue(BlockBombPress.FACING);
        }

        this.bindTexture(texture);

        GlStateManager.pushMatrix(); // start
        GlStateManager.translate((float) d + 0.5F, (float) d1 + 1, (float) d2 + 0.5F); // size
        GlStateManager.rotate(-facing.getHorizontalAngle(), 0.0F, 1.0F, 0.0F); // rotate based on facing
        GlStateManager.scale(1F, -1F, -1F); // if you read this comment out this line and you can see what happens
        GlStateManager.pushMatrix();

        GlStateManager.disableLighting();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 15 * 16, 15 * 16);
        model.renderModel(0.0625F, animation);
        GlStateManager.enableLighting();

        GlStateManager.popMatrix();
        GlStateManager.color(255, 255, 255);
        GlStateManager.popMatrix(); // end

    }

    public void renderInvModelAt(double d, double d1, double d2, float f, float animation) {
        EnumFacing facing = EnumFacing.NORTH;

        GlStateManager.pushMatrix(); // start
        GlStateManager.translate((float) d + 0.5F, (float) d1 + 1, (float) d2 + 0.5F); // size
        GlStateManager.rotate(-facing.getHorizontalAngle(), 0.0F, 1.0F, 0.0F); // rotate based on facing
        GlStateManager.scale(1F, -1F, -1F); // if you read this comment out this line and you can see what happens
        GlStateManager.pushMatrix();
        model.renderModel(0.0625F, animation);

        GlStateManager.popMatrix();
        GlStateManager.color(255, 255, 255);
        GlStateManager.popMatrix(); // end

    }

    @Override
    public void renderItem(ItemStack stack, ItemCameraTransforms.TransformType transformType) {

        GlStateManager.pushMatrix();

        Minecraft.getMinecraft().renderEngine.bindTexture(texture);

        renderInvModelAt(0, 0F, 0F, 0F, 0F);

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
