package minefantasy.mfr.client.render.item;

import codechicken.lib.model.bakedmodels.WrappedItemModel;
import codechicken.lib.render.CCModel;
import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.util.TransformUtils;
import com.google.common.collect.Lists;
import minefantasy.mfr.item.heatable.ItemHeated;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

//TODO: Make a more dynamic ItemHeated model coloration
@SideOnly(Side.CLIENT)
public class RenderHotItem extends WrappedItemModel implements IItemRenderer {


	public RenderHotItem(Supplier<ModelResourceLocation> wrappedModel) {
		super(wrappedModel);

	}

	@Override
	public void renderItem(ItemStack stack, ItemCameraTransforms.TransformType type) {
		ItemStack held = ItemHeated.getStack(stack);

		GlStateManager.pushMatrix();

		int color = ((ItemHeated) stack.getItem()).getColorFromItemStack(stack);
		IBakedModel model = wrapped.getOverrides().handleItemState(wrapped, stack, world, entity);
		renderModel(model, color, stack);

		renderWrapped(stack);

		GlStateManager.popMatrix();

	}

	@Override
	public IModelState getTransforms() {
		return TransformUtils.DEFAULT_ITEM;
	}

	@Override
	public boolean isAmbientOcclusion() {
		return true;
	}

	@Override
	public boolean isGui3d() {
		return true;
	}

	@Override
	public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
		return Lists.newArrayList();
	}

	@Override
	public ItemOverrideList getOverrides() {
		return ItemOverrideList.NONE;
	}

	@Override
	public boolean isBuiltInRenderer() {
		return false;
	}

	private void renderModel(IBakedModel model, int color, ItemStack stack) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.ITEM);

		for (EnumFacing enumfacing : EnumFacing.values()) {
			this.renderQuads(bufferbuilder, model.getQuads((IBlockState) null, enumfacing, 0L), color, stack);
		}

		this.renderQuads(bufferbuilder, model.getQuads((IBlockState) null, (EnumFacing) null, 0L), color, stack);
		tessellator.draw();
	}

	private void renderQuads(BufferBuilder renderer, List<BakedQuad> quads, int color, ItemStack stack) {
		boolean flag = color == -1 && !stack.isEmpty();
		int i = 0;

		for (int j = quads.size(); i < j; ++i) {
			BakedQuad bakedquad = quads.get(i);
			int k = color;

			if (flag && bakedquad.hasTintIndex()) {
				k = Minecraft.getMinecraft().getItemColors().colorMultiplier(stack, bakedquad.getTintIndex());

				if (EntityRenderer.anaglyphEnable) {
					k = TextureUtil.anaglyphColor(k);
				}

				k = k | -16777216;
			}

			net.minecraftforge.client.model.pipeline.LightUtil.renderQuadColor(renderer, bakedquad, k);
		}
	}
}
