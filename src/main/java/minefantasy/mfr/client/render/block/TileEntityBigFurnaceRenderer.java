package minefantasy.mfr.client.render.block;

import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.util.TransformUtils;
import minefantasy.mfr.block.BlockBigFurnace;
import minefantasy.mfr.client.model.block.ModelBigFurnace;
import minefantasy.mfr.tile.TileEntityBigFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;

/**
 * @author Anonymous Productions
 * <p>
 * Sources are provided for educational reasons. though small bits of
 * code, or methods can be used in your own creations.
 */
public class TileEntityBigFurnaceRenderer<T extends TileEntity> extends TileEntitySpecialRenderer<T> implements IItemRenderer {
	private final ModelBigFurnace model;

	public TileEntityBigFurnaceRenderer() {
		model = new ModelBigFurnace();
	}

	@Override
	public void render(T te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		renderAModelAt((TileEntityBigFurnace) te, x, y, z, partialTicks);
	}

	public void renderAModelAt(TileEntityBigFurnace tile, double d, double d1, double d2, float partialTicks) {
		EnumFacing facing = EnumFacing.NORTH;
		if (tile.hasWorld()) {
			IBlockState state = tile.getWorld().getBlockState(tile.getPos());
			facing = state.getValue(BlockBigFurnace.FACING);
		}

		this.bindTexture(tile.getTexture());

		boolean display = tile.isBurning();

		float doorAngle = tile.getPrevDoorAngle() + (tile.getDoorAngle() - tile.getPrevDoorAngle()) * partialTicks;

		GlStateManager.pushMatrix(); // start
		float scale = 1.0F;

		GlStateManager.translate((float) d + 0.5F, (float) d1 + 1.5F, (float) d2 + 0.5F); // size
		GlStateManager.rotate(-facing.getHorizontalAngle(), 0.0F, 1.0F, 0.0F);
		GlStateManager.scale(scale, -scale, -scale);

		model.renderModel(display, (float) (90F / 20F * -doorAngle / 180F * Math.PI), 0.0625F);

		GlStateManager.popMatrix(); // end

	}

	public void renderInvModel(String type, double d, double d1, double d2) {

		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("minefantasyreforged:textures/blocks/" + type + ".png"));
		GlStateManager.pushMatrix(); // start
		float scale = 1.0F;

		GlStateManager.translate((float) d + 0.5F, (float) d1 + 1.5F, (float) d2 + 0.5F); // size
		GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
		GlStateManager.scale(scale, -scale, -scale);
		model.renderModel(false, 0F, 0.0625F);

		GlStateManager.popMatrix(); // end

	}

	@Override
	public void renderItem(ItemStack stack, ItemCameraTransforms.TransformType transformType) {
		ItemBlock itemBlock;

		BlockBigFurnace furnace = new BlockBigFurnace("furnace_stone", false, 0);

		if (stack.getItem() instanceof ItemBlock) {
			itemBlock = (ItemBlock) stack.getItem();
			furnace = (BlockBigFurnace) itemBlock.getBlock();
		}

		GlStateManager.pushMatrix();

		renderInvModel(furnace.isHeater ? "furnace_heater" : "furnace_rock", 0F, 0F, 0F);

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