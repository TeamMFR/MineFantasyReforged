package minefantasy.mfr.client.render.block;

import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.util.TransformUtils;
import minefantasy.mfr.block.BlockAmmoBox;
import minefantasy.mfr.client.model.block.ModelAmmoBox;
import minefantasy.mfr.client.model.block.ModelFoodBox;
import minefantasy.mfr.client.model.block.ModelSmallCrate;
import minefantasy.mfr.item.ItemBlockAmmoBox;
import minefantasy.mfr.material.CustomMaterial;
import minefantasy.mfr.tile.TileEntityAmmoBox;
import minefantasy.mfr.util.CustomToolHelper;
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

public class TileEntityAmmoBoxRenderer<T extends TileEntity> extends TileEntitySpecialRenderer<T> implements IItemRenderer {
	private final ModelAmmoBox model_big;
	private final ModelAmmoBox model;
	private final ModelAmmoBox model_small;

	public TileEntityAmmoBoxRenderer() {
		model = new ModelAmmoBox();
		model_big = new ModelSmallCrate();
		model_small = new ModelFoodBox();
	}

	@Override
	public void render(T te, double x, double y, double z, float partialTicks, int destroyStage, float partial) {
		renderAModelAt((TileEntityAmmoBox) te, x, y, z);
	}

	public void renderAModelAt(TileEntityAmmoBox tile, double d, double d1, double d2) {
		EnumFacing facing = EnumFacing.NORTH;
		if (tile.hasWorld()) {
			IBlockState state = tile.getWorld().getBlockState(tile.getPos());
			facing = state.getValue(BlockAmmoBox.FACING);
		}
		this.renderModelAt(tile, -facing.getHorizontalAngle(), d, d1, d2);
	}

	public void renderModelAt(TileEntityAmmoBox tile, float facingAngle, double d, double d1, double d2) {
		ModelAmmoBox baseMdl = tile.getStorageType() == 0 ? model_small : tile.getStorageType() == 1 ? model : model_big;

		GlStateManager.pushMatrix(); // start
		float scale = 1.0F;
		float yOffset = 1 / 16F;
		GlStateManager.translate((float) d + 0.5F, (float) d1 + yOffset, (float) d2 + 0.5F); // size
		GlStateManager.rotate(facingAngle, 0.0F, 1.0F, 0.0F); // rotate based on metadata
		GlStateManager.scale(scale, -scale, -scale); // if you read this comment out this line and you can see what happens
		GlStateManager.pushMatrix();

		CustomMaterial material = tile.getMaterial();
		GlStateManager.color(material.getColourRGB()[0] / 255F, material.getColourRGB()[1] / 255F, material.getColourRGB()[2] / 255F);

		this.bindTexture(new ResourceLocation("minefantasyreforged:textures/blocks/" + tile.getTexName() + "_base.png")); // texture
		baseMdl.renderModel(0.0625F);
		baseMdl.renderLid(0.0625F, tile.angle);

		GlStateManager.color(1F, 1F, 1F);

		this.bindTexture(new ResourceLocation("minefantasyreforged:textures/blocks/" + tile.getTexName() + "_detail.png")); // texture
		baseMdl.renderModel(0.0625F);
		baseMdl.renderLid(0.0625F, tile.angle);

		GlStateManager.popMatrix();
		GlStateManager.color(1F, 1F, 1F);
		GlStateManager.popMatrix(); // end

	}

	public void renderInvModel(String tex, byte type, CustomMaterial material, double d, double d1, double d2) {
		ModelAmmoBox baseMdl = type == 0 ? model_small : type == 1 ? model : model_big;

		GlStateManager.pushMatrix(); // start
		float scale = 1.0F;
		float yOffset = 1 / 16F;
		GlStateManager.translate((float) d + 0.5F, (float) d1 + yOffset, (float) d2 + 0.5F); // size
		GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F); // rotate based on metadata
		GlStateManager.scale(scale, -scale, -scale); // if you read this comment out this line and you can see what happens
		GlStateManager.pushMatrix();

		GlStateManager.color(material.getColourRGB()[0] / 255F, material.getColourRGB()[1] / 255F, material.getColourRGB()[2] / 255F);

		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("minefantasyreforged:textures/blocks/" + tex + "_base.png"));
		baseMdl.renderModel(0.0625F);
		baseMdl.renderLid(0.0625F, 0F);

		GlStateManager.color(1F, 1F, 1F);

		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("minefantasyreforged:textures/blocks/" + tex + "_detail.png"));
		baseMdl.renderModel(0.0625F);
		baseMdl.renderLid(0.0625F, 0F);
		GlStateManager.popMatrix();
		GlStateManager.color(1F, 1F, 1F);
		GlStateManager.popMatrix(); // end

	}

	@Override
	public void renderItem(ItemStack stack, ItemCameraTransforms.TransformType transformType) {
		ItemBlock itemBlock;

		BlockAmmoBox box = new BlockAmmoBox("food_box_basic", (byte) 0);

		if (stack.getItem() instanceof ItemBlockAmmoBox) {
			itemBlock = (ItemBlock) stack.getItem();
			box = (BlockAmmoBox) itemBlock.getBlock();
		}

		GlStateManager.pushMatrix();

		renderInvModel(box.getFullTexName(), box.storageType, CustomToolHelper.getCustomPrimaryMaterial(stack), 0F, 0F, 0F);

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
