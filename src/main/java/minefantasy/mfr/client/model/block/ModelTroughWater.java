package minefantasy.mfr.client.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelTroughWater extends ModelBase {
	ModelRenderer model;

	public ModelTroughWater() {
		textureWidth = 16;
		textureHeight = 16;

		model = new ModelRenderer(this, 0, 0);
		model.addBox(-7F, 0F, -7F, 16, 0, 16);
		model.setRotationPoint(0F, 0F, 0F);
		model.setTextureSize(16, 16);
		model.mirror = true;
		model.rotateAngleX = 0;
		model.rotateAngleY = 0;
		model.rotateAngleZ = 0;
	}

	public void renderModel(float scale) {
		model.render(scale);
	}
}
