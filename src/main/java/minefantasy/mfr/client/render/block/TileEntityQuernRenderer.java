package minefantasy.mfr.client.render.block;

import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.util.TransformUtils;
import minefantasy.mfr.client.model.block.ModelQuern;
import minefantasy.mfr.tile.TileEntityQuern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;

public class TileEntityQuernRenderer<T extends TileEntity> extends TileEntitySpecialRenderer<T> implements IItemRenderer {
	private final ModelQuern model;

	private static final ResourceLocation texture = new ResourceLocation("minefantasyreforged:textures/blocks/quern_basic.png");

	public TileEntityQuernRenderer() {
		model = new ModelQuern();
	}

	@Override
	public void render(T te, double x, double y, double z, float partialTick, int breakStage, float partial) {
		renderModelAt((TileEntityQuern) te, x, y, z);
	}

	public void renderModelAt(TileEntityQuern tile, double d, double d1, double d2) {
		this.bindTexture(texture); // texture

		GlStateManager.pushMatrix(); // start
		float scale = 1.0F;
		float yOffset = 1.0F;
		GlStateManager.translate((float) d + 0.5F, (float) d1 + yOffset, (float) d2 + 0.5F); // size
		GlStateManager.scale(scale, -scale, -scale); // if you read this comment out this line and you can see what happens
		GlStateManager.pushMatrix();

		model.renderModel(tile, 0.0625F);

		GlStateManager.popMatrix();
		GlStateManager.color(255, 255, 255);
		GlStateManager.popMatrix(); // end

	}

	public void renderInvModelAt(double d, double d1, double d2) {

		GlStateManager.pushMatrix(); // start
		float scale = 1.0F;
		float yOffset = 1.0F;
		GlStateManager.translate((float) d + 0.5F, (float) d1 + yOffset, (float) d2 + 0.5F); // size
		GlStateManager.scale(scale, -scale, -scale); // if you read this comment out this line and you can see what happens
		GlStateManager.pushMatrix();
		model.renderModel(0.0625F);

		GlStateManager.popMatrix();
		GlStateManager.color(255, 255, 255);
		GlStateManager.popMatrix(); // end

	}

	@Override
	public void renderItem(ItemStack stack, ItemCameraTransforms.TransformType transformType) {

		GlStateManager.pushMatrix();

		Minecraft.getMinecraft().renderEngine.bindTexture(texture);

		renderInvModelAt(0F, 0F, 0F);

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
