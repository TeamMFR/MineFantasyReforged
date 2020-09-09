// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


public class custom_model extends EntityModel<Entity> {
	private final ModelRenderer top;
	private final ModelRenderer base;
	private final ModelRenderer neck;
	private final ModelRenderer body;
	private final ModelRenderer bloom;

	public custom_model() {
		textureWidth = 128;
		textureHeight = 64;

		top = new ModelRenderer(this);
		top.setRotationPoint(0.0F, 0.0F, 0.0F);
		top.setTextureOffset(84, 43).addBox(-5.5F, 0.0F, -5.5F, 11.0F, 2.0F, 11.0F, 0.0F, false);

		base = new ModelRenderer(this);
		base.setRotationPoint(0.0F, 0.0F, 0.0F);
		base.setTextureOffset(0, 0).addBox(-9.0F, 14.0F, -9.0F, 18.0F, 2.0F, 18.0F, 0.0F, false);

		neck = new ModelRenderer(this);
		neck.setRotationPoint(0.0F, 0.0F, 0.0F);
		neck.setTextureOffset(0, 46).addBox(-7.0F, 2.0F, -7.0F, 14.0F, 3.0F, 14.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.setTextureOffset(64, 11).addBox(-8.0F, 5.0F, -8.0F, 16.0F, 9.0F, 16.0F, 0.0F, false);

		bloom = new ModelRenderer(this);
		bloom.setRotationPoint(0.0F, 6.0F, 0.0F);
		bloom.setTextureOffset(0, 20).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		top.render(matrixStack, buffer, packedLight, packedOverlay);
		base.render(matrixStack, buffer, packedLight, packedOverlay);
		neck.render(matrixStack, buffer, packedLight, packedOverlay);
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		bloom.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}