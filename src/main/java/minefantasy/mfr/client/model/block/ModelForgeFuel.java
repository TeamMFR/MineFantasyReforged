package minefantasy.mfr.client.model.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelForgeFuel extends ModelBase {
	ModelRenderer model;

	public ModelForgeFuel() {
		textureWidth = 16;
		textureHeight = 16;

		model = new ModelRenderer(this, 0, 0);
		model.addBox(-7.5F, 0F, -7.5F, 15, 2, 15);
		model.setRotationPoint(0F, 18.5F, 0F);
		model.setTextureSize(16, 16);
		model.mirror = true;
		model.rotateAngleX = 0;
		model.rotateAngleY = 0;
		model.rotateAngleZ = 0;
	}

	public void renderModel(float scale, float height) {
		model.offsetY = height;
		model.render(scale);
	}
}
