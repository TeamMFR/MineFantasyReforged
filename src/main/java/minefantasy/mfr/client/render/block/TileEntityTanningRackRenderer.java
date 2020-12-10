package minefantasy.mfr.client.render.block;

import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.util.TransformUtils;
import minefantasy.mfr.block.BlockTanningRack;
import minefantasy.mfr.client.model.block.ModelEngTanningRack;
import minefantasy.mfr.tile.TileEntityTanningRack;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.animation.FastTESR;
import net.minecraftforge.common.model.IModelState;
import org.lwjgl.opengl.GL11;

public class TileEntityTanningRackRenderer <T extends TileEntity> extends FastTESR<T> implements IItemRenderer {

    private ModelEngTanningRack engmodel;

    public TileEntityTanningRackRenderer() {
        engmodel = new ModelEngTanningRack();
    }

    @Override
    public void renderTileEntityFast(T te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {
        renderAModelAt((TileEntityTanningRack) te, x, y, z);
    }

    public void renderAModelAt(TileEntityTanningRack tile, double d, double d1, double d2) {
        EnumFacing facing = EnumFacing.NORTH;
        if (tile.hasWorld()) {
            IBlockState state = tile.getWorld().getBlockState(tile.getPos());
            facing = state.getValue(BlockTanningRack.FACING);
        }

        this.bindTexture(new ResourceLocation("minefantasyreborn:textures/blocks/tanner_metal.png"));
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d + 0.5F, (float) d1 + 1.45F, (float) d2 + 0.5F);
        GL11.glRotatef(-facing.getHorizontalAngle(), 0.0F, 1.0F, 0.0F);
        GL11.glScalef(1.0F, -1F, -1F);
        if (tile.isAutomated()) {
            engmodel.renderModel(0.0625F);
            GL11.glPushMatrix();
            GL11.glTranslatef(0F, tile.acTime, 0F);
            engmodel.renderBlade(0.0625F);
            GL11.glPopMatrix();
            engmodel.rotateLever(tile.acTime);
            engmodel.renderLever(0.0625F);
        }

        renderHungItem(tile);

        GL11.glPopMatrix();

    }

    public void renderInvModel(boolean isAuto, double x, double y, double z) {
        int j = 90;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.45F, (float) z + 0.5F);
        GL11.glRotatef(j, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(1.0F, -1F, -1F);
        if (isAuto) {
            engmodel.renderModel(0.0625F);
            GL11.glPushMatrix();
            engmodel.renderBlade(0.0625F);
            GL11.glPopMatrix();
            engmodel.renderLever(0.0625F);
        }
        GL11.glPopMatrix();

    }

    @Override
    public void renderItem(ItemStack stack, ItemCameraTransforms.TransformType transformType) {

        GlStateManager.pushMatrix();

        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("minefantasyreborn:textures/blocks/tanner_metal.png"));

        renderInvModel(true, 0F, 0F, 0F);

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.popMatrix();
    }

    private void renderHungItem(TileEntityTanningRack tile) {
        ItemStack stack = tile.getInventory().getStackInSlot(0);
        if (!stack.isEmpty()) {
            GL11.glScalef(0.9F, 0.9F, 0.9F);
            GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(0.0F, -1.1F, 0.0F);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
        }
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