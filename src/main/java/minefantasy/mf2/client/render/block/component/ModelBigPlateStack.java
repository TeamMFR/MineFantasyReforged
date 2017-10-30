package minefantasy.mf2.client.render.block.component;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelBigPlateStack extends ModelBase {
    private ModelRenderer[] sheet = new ModelRenderer[8];

    public ModelBigPlateStack() {
        textureWidth = 64;
        textureHeight = 16;

        sheet[0] = new ModelRenderer(this, 0, 0);
        sheet[0].addBox(-6.5F, 0F, -6.5F, 13, 2, 13);
        sheet[0].setRotationPoint(0F, 14F, 0F);
        sheet[0].setTextureSize(64, 16);
        setRotation(sheet[0], 0F, 0F, 0F);

        sheet[1] = new ModelRenderer(this, 0, 0);
        sheet[1].addBox(-6.5F, 0F, -6.5F, 13, 2, 13);
        sheet[1].setRotationPoint(0F, 12F, 0F);
        sheet[1].setTextureSize(64, 16);
        setRotation(sheet[1], 0F, 0.0349066F, 0F);

        sheet[2] = new ModelRenderer(this, 0, 0);
        sheet[2].addBox(-6.5F, 0F, -6.5F, 13, 2, 13);
        sheet[2].setRotationPoint(0F, 10F, 0F);
        sheet[2].setTextureSize(64, 16);
        setRotation(sheet[2], 0F, -0.0349066F, 0F);

        sheet[3] = new ModelRenderer(this, 0, 0);
        sheet[3].addBox(-6.5F, 0F, -6.5F, 13, 2, 13);
        sheet[3].setRotationPoint(0F, 8F, 0F);
        sheet[3].setTextureSize(64, 16);
        setRotation(sheet[3], 0F, 0.1047198F, 0F);

        sheet[4] = new ModelRenderer(this, 0, 0);
        sheet[4].addBox(-6.5F, 0F, -6.5F, 13, 2, 13);
        sheet[4].setRotationPoint(0F, 6F, 0F);
        sheet[4].setTextureSize(64, 16);
        setRotation(sheet[4], 0F, 0F, 0F);

        sheet[5] = new ModelRenderer(this, 0, 0);
        sheet[5].addBox(-6.5F, 0F, -6.5F, 13, 2, 13);
        sheet[5].setRotationPoint(0F, 4F, 0F);
        sheet[5].setTextureSize(64, 16);
        setRotation(sheet[5], 0F, -0.0872665F, 0F);

        sheet[6] = new ModelRenderer(this, 0, 0);
        sheet[6].addBox(-6.5F, 0F, -6.5F, 13, 2, 13);
        sheet[6].setRotationPoint(0F, 2F, 0F);
        sheet[6].setTextureSize(64, 16);
        setRotation(sheet[6], 0F, 0.0174533F, 0F);

        sheet[7] = new ModelRenderer(this, 0, 0);
        sheet[7].addBox(-6.5F, 0F, -6.5F, 13, 2, 13);
        sheet[7].setRotationPoint(0F, 0F, 0F);
        sheet[7].setTextureSize(64, 16);
        setRotation(sheet[7], 0F, 0.0698132F, 0F);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void render(int stackSize, float f) {
        for (int i = 0; i < stackSize; i++) {
            sheet[i].render(f);
        }
    }
}
