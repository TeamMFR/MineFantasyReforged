package minefantasy.mfr.client.render;

import codechicken.lib.model.bakedmodels.WrappedItemModel;
import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.util.TransformUtils;
import minefantasy.mfr.api.archery.AmmoMechanicsMFR;
import minefantasy.mfr.api.helpers.CustomToolHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBow;
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
		boolean pulling = false;

		if (entity != null) {
			pulling = entity.isHandActive() && entity.getActiveItemStack() == stack;
		}

		GlStateManager.pushMatrix(); // bow start
		for (int layer = 0; layer < 3; layer++) {

			GlStateManager.pushMatrix();
			int colour = CustomToolHelper.getColourFromItemStack(stack, layer); //get bow coloration
			float red = (colour >> 16 & 255) / 255.0F;
			float green = (colour >> 8 & 255) / 255.0F;
			float blue = (colour & 255) / 255.0F;

			GlStateManager.color(red, green, blue, 1.0F); //set bow coloration

			renderWrapped(stack);

			GlStateManager.color(1F, 1F, 1F); //reset color (?)
			GlStateManager.popMatrix();
		}
		GlStateManager.popMatrix(); // bow end

		if (!arrowStack.isEmpty() && AmmoMechanicsMFR.isFirearmLoaded(stack) ) {
			for (int layer = 0; layer < 3; layer++) {

				GlStateManager.pushMatrix(); //arrow start

				if (pulling) {

					float pull = Math.min(!(entity.getActiveItemStack().getItem() instanceof ItemBow) ? 0.0F : (float) (stack.getMaxItemUseDuration() - entity.getItemInUseCount()) / 20.0F, 1.0F);
					if (pull > 0 && pull < 0.7F) {
						pull = 0;
					} else if (pull <  0.8F) {
						pull = 0.4F;
					} else if (pull <  0.9F) {
						pull = 0.7F;
					} else {
						pull = 1F;
					}

					GlStateManager.translate((4F + (pull * 4)) / 16F, (12F - (pull * 4)) / 16F, 8.1F / 16F);
				}
				else {
					GlStateManager.translate(4F / 16F, 12F / 16F, 8.1F / 16F);
				}
				GlStateManager.rotate(90, 0, 0, 1);

				int colour = CustomToolHelper.getColourFromItemStack(arrowStack, layer); // get arrow coloration
				float red = (colour >> 16 & 255) / 255.0F;
				float green = (colour >> 8 & 255) / 255.0F;
				float blue = (colour & 255) / 255.0F;

				GlStateManager.color(red, green, blue, 1.0F); // set arrow coloration

				Minecraft.getMinecraft().getRenderItem().renderItem(arrowStack, TransformType.NONE);

				GlStateManager.color(1F, 1F, 1F); //reset coloration(?)

				GlStateManager.popMatrix(); //arrow end
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
