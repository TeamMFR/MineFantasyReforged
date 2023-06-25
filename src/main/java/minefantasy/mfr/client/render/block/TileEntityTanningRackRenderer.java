package minefantasy.mfr.client.render.block;

import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.util.TransformUtils;
import minefantasy.mfr.block.BlockTanningRack;
import minefantasy.mfr.client.model.block.ModelEngTanningRack;
import minefantasy.mfr.tile.TileEntityTanningRack;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;

public class TileEntityTanningRackRenderer<T extends TileEntity> extends TileEntitySpecialRenderer<T> implements IItemRenderer {

	private final ModelEngTanningRack engmodel;

	public TileEntityTanningRackRenderer() {
		engmodel = new ModelEngTanningRack();
	}

	@Override
	public void render(T te, double x, double y, double z, float partialTicks, int destroyStage, float partial) {
		renderAModelAt((TileEntityTanningRack) te, x, y, z);
	}

	public void renderAModelAt(TileEntityTanningRack tile, double d, double d1, double d2) {
		EnumFacing facing = EnumFacing.NORTH;
		if (tile.hasWorld()) {
			IBlockState state = tile.getWorld().getBlockState(tile.getPos());
			facing = state.getValue(BlockTanningRack.FACING);
		}

		this.bindTexture(new ResourceLocation("minefantasyreforged:textures/blocks/tanner_metal.png"));
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) d + 0.5F, (float) d1 + 1.45F, (float) d2 + 0.5F);
		GlStateManager.rotate(-facing.getHorizontalAngle(), 0.0F, 1.0F, 0.0F);
		GlStateManager.scale(1.0F, -1F, -1F);
		if (tile.isAutomated()) {
			engmodel.renderModel(0.0625F);
			GlStateManager.pushMatrix();
			GlStateManager.translate(0F, tile.acTime, 0F);
			engmodel.renderBlade(0.0625F);
			GlStateManager.popMatrix();
			engmodel.rotateLever(tile.acTime);
			engmodel.renderLever(0.0625F);
		}

		renderHungItem(tile);

		GlStateManager.popMatrix();

	}

	public void renderInvModel(boolean isAuto, double x, double y, double z) {
		int j = 90;
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x + 0.5F, (float) y + 1.45F, (float) z + 0.5F);
		GlStateManager.rotate(j, 0.0F, 1.0F, 0.0F);
		GlStateManager.scale(1.0F, -1F, -1F);
		if (isAuto) {
			engmodel.renderModel(0.0625F);
			GlStateManager.pushMatrix();
			engmodel.renderBlade(0.0625F);
			GlStateManager.popMatrix();
			engmodel.renderLever(0.0625F);
		}
		GlStateManager.popMatrix();

	}

	@Override
	public void renderItem(ItemStack stack, ItemCameraTransforms.TransformType transformType) {

		GlStateManager.pushMatrix();

		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("minefantasyreforged:textures/blocks/tanner_metal.png"));

		renderInvModel(true, 0F, 0F, 0F);

		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.popMatrix();
	}

	private void renderHungItem(TileEntityTanningRack tile) {
		ItemStack stack = tile.getInputInventory().getStackInSlot(0);
		if (!stack.isEmpty()) {
			GlStateManager.scale(0.9F, 0.9F, 0.9F);
			GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
			GlStateManager.translate(0.0F, -1.1F, 0.0F);
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