package minefantasy.mf2.client.render.armour;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;

public class ModelFullplate extends ModelBiped 
{
	ModelRenderer jaw;
	ModelRenderer rightPauldron;
	ModelRenderer leftPauldron;
	ModelRenderer ridge;

	public ModelFullplate(float f)
	{
		super(f, 0F, 64, 64);

		jaw = new ModelRenderer(this, 0, 32);
		jaw.addBox(-3.5F, -4.5F, -4F, 7, 4, 2, f);
		jaw.setRotationPoint(0F, -0.5F, 0F);
		jaw.setTextureSize(64, 64);
		setRotation(jaw, 0.3316126F, 0F, 0F);
		
		rightPauldron = new ModelRenderer(this, 40, 32);
		rightPauldron.addBox(-3.5F, -3.5F, -2.5F, 5, 5, 5, f);
		//rightPauldron.setRotationPoint(-5F, 2F, 0F);
		rightPauldron.setTextureSize(64, 64);
		setRotation(rightPauldron, 0F, 0F, -0.418879F);
		
		leftPauldron = new ModelRenderer(this, 40, 32);
		leftPauldron.addBox(-1.5F, -3.5F, -2.5F, 5, 5, 5, f);
		//leftPauldron.setRotationPoint(5F, 2F, 0F);
		leftPauldron.setTextureSize(64, 64);
		setRotation(leftPauldron, 0F, 0F, 0.418879F);
		leftPauldron.mirror = true;
		
		ridge = new ModelRenderer(this, 0, 38);
		ridge.addBox(-1F, -8.5F, -3.5F, 2, 8, 8, f);
		ridge.setRotationPoint(0F, 0F, 0F);
		ridge.setTextureSize(64, 64);
		
		this.bipedRightArm.addChild(rightPauldron);
		this.bipedLeftArm.addChild(leftPauldron);
		this.bipedHead.addChild(ridge);
		this.bipedHead.addChild(jaw);
		
		this.bipedLeftLeg.showModel = this.bipedRightLeg.showModel = this.bipedEars.showModel = false;
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) 
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
