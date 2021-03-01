package minefantasy.mfr.client.render.item;

import codechicken.lib.model.bakedmodels.WrappedItemModel;
import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.util.TransformUtils;
import minefantasy.mfr.api.heating.TongsHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.Supplier;

@SideOnly(Side.CLIENT)
public class RenderTong extends WrappedItemModel implements IItemRenderer {

	public RenderTong(Supplier<ModelResourceLocation> wrappedModel) {
		super(wrappedModel);
	}

	@Override
	public void renderItem(ItemStack stack, TransformType transformType) {
		ItemStack heldItem = TongsHelper.getHeldItem(stack);

		GlStateManager.pushMatrix();

		renderWrapped(stack);
		if (!heldItem.isEmpty()) {
			GlStateManager.scale(0.5F, 0.5F, 0.5F);
			GlStateManager.translate(1.5F, 1.5F, 1F);
			Minecraft.getMinecraft().getRenderItem().renderItem(heldItem, TransformType.NONE);
		}

		GlStateManager.popMatrix();

	}

	@Override
	public IModelState getTransforms() {
		return TransformUtils.DEFAULT_ITEM;
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
