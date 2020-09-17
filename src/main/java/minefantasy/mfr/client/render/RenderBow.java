package minefantasy.mfr.client.render;

import codechicken.lib.model.bakedmodels.WrappedItemModel;
import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.util.TransformUtils;
import minefantasy.mfr.api.archery.AmmoMechanicsMFR;
import minefantasy.mfr.api.helpers.CustomToolHelper;
import minefantasy.mfr.item.archery.ItemBowMFR;
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
public class RenderBow extends WrappedItemModel implements IItemRenderer {

	public RenderBow(Supplier<ModelResourceLocation> wrappedModel) {
		super(wrappedModel);
	}

	@Override
	public void renderItem(ItemStack stack, TransformType transformType) {
		ItemStack arrowStack = AmmoMechanicsMFR.getArrowOnBow(stack);

		ItemBowMFR bow = null;
		if (!stack.isEmpty() && stack.getItem() instanceof ItemBowMFR) {
			bow = (ItemBowMFR) stack.getItem();
		}

		GlStateManager.pushMatrix();
		for (int layer = 0; layer < 3; layer++) {

			GlStateManager.pushMatrix();
			int colour = CustomToolHelper.getColourFromItemStack(stack, layer);
			float red = (colour >> 16 & 255) / 255.0F;
			float green = (colour >> 8 & 255) / 255.0F;
			float blue = (colour & 255) / 255.0F;

			GlStateManager.color(red, green, blue, 1.0F);
			renderWrapped(stack);
			GlStateManager.color(1F, 1F, 1F);
			GlStateManager.popMatrix();
		}
		GlStateManager.popMatrix();

		if (bow != null){
			if (!arrowStack.isEmpty() && AmmoMechanicsMFR.isFirearmLoaded(stack)) {
				bow.setPulling(true);
				for (int layer = 0; layer < 3; layer++) {

					GlStateManager.pushMatrix();

					int colour = CustomToolHelper.getColourFromItemStack(arrowStack, layer);
					float red = (colour >> 16 & 255) / 255.0F;
					float green = (colour >> 8 & 255) / 255.0F;
					float blue = (colour & 255) / 255.0F;

					GlStateManager.color(red, green, blue, 1.0F);

					GlStateManager.translate((4F + bow.getDrawAmount()) / 16F, (12F - bow.getDrawAmount()) / 16F,8.1F/16F);
					GlStateManager.rotate(90, 0, 0, 1);

					Minecraft.getMinecraft().getRenderItem().renderItem(arrowStack, TransformType.NONE);
					GlStateManager.color(1F, 1F, 1F);

					GlStateManager.popMatrix();
				}
			}
			else{
				bow.setPulling(false);
			}
		}

	}

	@Override
	public IModelState getTransforms() {
		return TransformUtils.DEFAULT_BOW;
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
