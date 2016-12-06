package minefantasy.mf2.client.render.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelBarStack extends ModelBase 
{
	private ModelRenderer[] bar = new ModelRenderer[32];
	
	public ModelBarStack()
	{
		textureWidth = 32;
	    textureHeight = 16;
	    
	      bar[31] = new ModelRenderer(this, 0, 0);
	      bar[31].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[31].setRotationPoint(0.5F, 4F, -7.5F);
	      bar[31].setTextureSize(32, 16);
	      setRotation(bar[31], 0F, -1.570796F, 0F);
	      bar[28] = new ModelRenderer(this, 0, 0);
	      bar[28].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[28].setRotationPoint(0.5F, 7F, -0.5F);
	      bar[28].setTextureSize(32, 16);
	      setRotation(bar[28], 0F, 0F, 0F);
	      bar[29] = new ModelRenderer(this, 0, 0);
	      bar[29].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[29].setRotationPoint(4.5F, 7F, -0.5F);
	      bar[29].setTextureSize(32, 16);
	      setRotation(bar[29], 0F, 0F, 0F);
	      bar[30] = new ModelRenderer(this, 0, 0);
	      bar[30].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[30].setRotationPoint(0.5F, 4F, -3.5F);
	      bar[30].setTextureSize(32, 16);
	      setRotation(bar[30], 0F, -1.570796F, 0F);
	      bar[24] = new ModelRenderer(this, 0, 0);
	      bar[24].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[24].setRotationPoint(-7.5F, 7F, -0.5F);
	      bar[24].setTextureSize(32, 16);
	      setRotation(bar[24], 0F, 0F, 0F);
	      bar[26] = new ModelRenderer(this, 0, 0);
	      bar[26].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[26].setRotationPoint(-7.5F, 4F, -3.5F);
	      bar[26].setTextureSize(32, 16);
	      setRotation(bar[26], 0F, -1.570796F, 0F);
	      bar[27] = new ModelRenderer(this, 0, 0);
	      bar[27].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[27].setRotationPoint(-7.5F, 4F, -7.5F);
	      bar[27].setTextureSize(32, 16);
	      setRotation(bar[27], 0F, -1.570796F, 0F);
	      bar[25] = new ModelRenderer(this, 0, 0);
	      bar[25].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[25].setRotationPoint(-3.5F, 7F, -0.5F);
	      bar[25].setTextureSize(32, 16);
	      setRotation(bar[25], 0F, 0F, 0F);
	      bar[18] = new ModelRenderer(this, 0, 0);
	      bar[18].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[18].setRotationPoint(-7.5F, 4F, 4.5F);
	      bar[18].setTextureSize(32, 16);
	      setRotation(bar[18], 0F, -1.570796F, 0F);
	      bar[22] = new ModelRenderer(this, 0, 0);
	      bar[22].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[22].setRotationPoint(0.5F, 4F, 4.5F);
	      bar[22].setTextureSize(32, 16);
	      setRotation(bar[22], 0F, -1.570796F, 0F);
	      bar[19] = new ModelRenderer(this, 0, 0);
	      bar[19].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[19].setRotationPoint(-7.5F, 4F, 0.5F);
	      bar[19].setTextureSize(32, 16);
	      setRotation(bar[19], 0F, -1.570796F, 0F);
	      bar[23] = new ModelRenderer(this, 0, 0);
	      bar[23].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[23].setRotationPoint(0.5F, 4F, 0.5F);
	      bar[23].setTextureSize(32, 16);
	      setRotation(bar[23], 0F, -1.570796F, 0F);
	      bar[16] = new ModelRenderer(this, 0, 0);
	      bar[16].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[16].setRotationPoint(-7.5F, 7F, 7.5F);
	      bar[16].setTextureSize(32, 16);
	      setRotation(bar[16], 0F, 0F, 0F);
	      bar[17] = new ModelRenderer(this, 0, 0);
	      bar[17].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[17].setRotationPoint(-3.5F, 7F, 7.5F);
	      bar[17].setTextureSize(32, 16);
	      setRotation(bar[17], 0F, 0F, 0F);
	      bar[20] = new ModelRenderer(this, 0, 0);
	      bar[20].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[20].setRotationPoint(0.5F, 7F, 7.5F);
	      bar[20].setTextureSize(32, 16);
	      setRotation(bar[20], 0F, 0F, 0F);
	      bar[21] = new ModelRenderer(this, 0, 0);
	      bar[21].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[21].setRotationPoint(4.5F, 7F, 7.5F);
	      bar[21].setTextureSize(32, 16);
	      setRotation(bar[21], 0F, 0F, 0F);
	      bar[8] = new ModelRenderer(this, 0, 0);
	      bar[8].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[8].setRotationPoint(-7.5F, 13F, -0.5F);
	      bar[8].setTextureSize(32, 16);
	      setRotation(bar[8], 0F, 0F, 0F);
	      bar[9] = new ModelRenderer(this, 0, 0);
	      bar[9].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[9].setRotationPoint(-3.5F, 13F, -0.5F);
	      bar[9].setTextureSize(32, 16);
	      setRotation(bar[9], 0F, 0F, 0F);
	      bar[12] = new ModelRenderer(this, 0, 0);
	      bar[12].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[12].setRotationPoint(0.5F, 13F, -0.5F);
	      bar[12].setTextureSize(32, 16);
	      setRotation(bar[12], 0F, 0F, 0F);
	      bar[13] = new ModelRenderer(this, 0, 0);
	      bar[13].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[13].setRotationPoint(4.5F, 13F, -0.5F);
	      bar[13].setTextureSize(32, 16);
	      setRotation(bar[13], 0F, 0F, 0F);
	      bar[2] = new ModelRenderer(this, 0, 0);
	      bar[2].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[2].setRotationPoint(-7.5F, 10F, 4.5F);
	      bar[2].setTextureSize(32, 16);
	      setRotation(bar[2], 0F, -1.570796F, 0F);
	      bar[3] = new ModelRenderer(this, 0, 0);
	      bar[3].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[3].setRotationPoint(-7.5F, 10F, 0.5F);
	      bar[3].setTextureSize(32, 16);
	      setRotation(bar[3], 0F, -1.570796F, 0F);
	      bar[10] = new ModelRenderer(this, 0, 0);
	      bar[10].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[10].setRotationPoint(-7.5F, 10F, -3.5F);
	      bar[10].setTextureSize(32, 16);
	      setRotation(bar[10], 0F, -1.570796F, 0F);
	      bar[11] = new ModelRenderer(this, 0, 0);
	      bar[11].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[11].setRotationPoint(-7.5F, 10F, -7.5F);
	      bar[11].setTextureSize(32, 16);
	      setRotation(bar[11], 0F, -1.570796F, 0F);
	      bar[5] = new ModelRenderer(this, 0, 0);
	      bar[5].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[5].setRotationPoint(4.5F, 13F, 7.5F);
	      bar[5].setTextureSize(32, 16);
	      setRotation(bar[5], 0F, 0F, 0F);
	      bar[4] = new ModelRenderer(this, 0, 0);
	      bar[4].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[4].setRotationPoint(0.5F, 13F, 7.5F);
	      bar[4].setTextureSize(32, 16);
	      setRotation(bar[4], 0F, 0F, 0F);
	      bar[1] = new ModelRenderer(this, 0, 0);
	      bar[1].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[1].setRotationPoint(-3.5F, 13F, 7.5F);
	      bar[1].setTextureSize(32, 16);
	      setRotation(bar[1], 0F, 0F, 0F);
	      bar[0] = new ModelRenderer(this, 0, 0);
	      bar[0].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[0].setRotationPoint(-7.5F, 13F, 7.5F);
	      bar[0].setTextureSize(32, 16);
	      setRotation(bar[0], 0F, 0F, 0F);
	      bar[6] = new ModelRenderer(this, 0, 0);
	      bar[6].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[6].setRotationPoint(0.5F, 10F, 4.5F);
	      bar[6].setTextureSize(32, 16);
	      setRotation(bar[6], 0F, -1.570796F, 0F);
	      bar[7] = new ModelRenderer(this, 0, 0);
	      bar[7].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[7].setRotationPoint(0.5F, 10F, 0.5F);
	      bar[7].setTextureSize(32, 16);
	      setRotation(bar[7], 0F, -1.570796F, 0F);
	      bar[14] = new ModelRenderer(this, 0, 0);
	      bar[14].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[14].setRotationPoint(0.5F, 10F, -3.5F);
	      bar[14].setTextureSize(32, 16);
	      setRotation(bar[14], 0F, -1.570796F, 0F);
	      bar[15] = new ModelRenderer(this, 0, 0);
	      bar[15].addBox(0F, 0F, -7F, 3, 3, 7);
	      bar[15].setRotationPoint(0.5F, 10F, -7.5F);
	      bar[15].setTextureSize(32, 16);
	      setRotation(bar[15], 0F, -1.570796F, 0F);
	}
	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
	public void render(int stackSize, float f)
	{
		for(int i = 0; i < stackSize; i ++)
		{
			bar[i].render(f);
		}
	}
}
