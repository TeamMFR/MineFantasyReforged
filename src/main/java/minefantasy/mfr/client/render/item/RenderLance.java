package minefantasy.mfr.client.render.item;

import codechicken.lib.model.bakedmodels.WrappedItemModel;
import codechicken.lib.render.item.IItemRenderer;
import codechicken.lib.util.TransformUtils;
import minefantasy.mfr.util.TextureHelperMFR;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.Supplier;

@SideOnly(Side.CLIENT)
public class RenderLance extends WrappedItemModel implements IItemRenderer {

	public RenderLance(Supplier<ModelResourceLocation> wrappedModel) {
		super(wrappedModel);
	}

	@Override
	public void renderItem(ItemStack stack, TransformType transformType) {
		GlStateManager.pushMatrix();
		if (entity != null && transformType != TransformType.GUI && transformType != TransformType.GROUND) {

			GlStateManager.scale(3F, 3F, 1F);

			GlStateManager.translate(-0.13F, -0.13F, 0F);

			float r = 0F;

			if (entity.isSwingInProgress) {
				if (transformType == TransformType.FIRST_PERSON_RIGHT_HAND || transformType == TransformType.FIRST_PERSON_LEFT_HAND) {
					r = 90F;
				}
			}

			if (entity.isRiding()) {
				Entity mount = entity.getRidingEntity();
				float speed = (float) Math.hypot(mount.motionX, mount.motionZ) * 20F;

				if (speed > 4.0F) {
					if (transformType == TransformType.FIRST_PERSON_RIGHT_HAND || transformType == TransformType.FIRST_PERSON_LEFT_HAND) {
						r = 90F;
					} else {
						r = 20F;
						GlStateManager.translate(0.0F, -0.11F, 0F);
					}
				}
			}

			GlStateManager.rotate(r, 0, 0, 1);

		} else if (transformType == TransformType.GROUND) {
			GlStateManager.scale(3F, 3F, 1F);
		}
		renderWrapped(stack);
		if (!stack.isEmpty() && stack.hasEffect()){
			TextureHelperMFR.renderEffect(wrapped, stack);
		}
		GlStateManager.popMatrix();
	}

	@Override
	public IModelState getTransforms() {
		return TransformUtils.DEFAULT_TOOL;
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
