package minefantasy.mfr.client.render.item;

import codechicken.lib.model.bakedmodels.WrappedItemModel;
import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.util.TransformUtils;
import com.google.common.collect.Lists;
import minefantasy.mfr.item.ItemHeated;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

@SideOnly(Side.CLIENT)
public class RenderHotItem extends WrappedItemModel implements IItemRenderer {

	public RenderHotItem(Supplier<ModelResourceLocation> wrappedModel) {
		super(wrappedModel);

	}

	@Override
	public void renderItem(ItemStack stack, ItemCameraTransforms.TransformType type) {
		ItemStack held = ItemHeated.getStack(stack);

		GlStateManager.pushMatrix();

		GlStateManager.disableLighting();
		GlStateManager.disableBlend();
		GlStateManager.depthMask(true);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.f, 240.f);

		IBakedModel heldModel = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(held, world, entity);

		GlStateManager.translate(0.5F, 0.5F, 0.5F);
		Minecraft.getMinecraft().getRenderItem().renderItem(stack, heldModel);

		GlStateManager.enableLighting();
		GlStateManager.enableBlend();

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
}