package minefantasy.mf2.client.render.block.component;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelSheetStack extends ModelBase {
    private ModelRenderer[] sheet = new ModelRenderer[16];

    public ModelSheetStack() {
        textureWidth = 64;
        textureHeight = 16;

        sheet[0] = new ModelRenderer(this, 0, 0);
        sheet[0].addBox(-7F, 0F, -7F, 14, 1, 14);
        sheet[0].setRotationPoint(0F, 15F, 0F);
        sheet[0].setTextureSize(64, 16);

        setRotation(sheet[0], 0F, 0F, 0F);
        sheet[1] = new ModelRenderer(this, 0, 0);
        sheet[1].addBox(-7F, 0F, -7F, 14, 1, 14);
        sheet[1].setRotationPoint(0F, 14F, 0F);
        sheet[1].setTextureSize(64, 16);

        setRotation(sheet[1], 0F, -0.122173F, 0F);
        sheet[2] = new ModelRenderer(this, 0, 0);
        sheet[2].addBox(-7F, 0F, -7F, 14, 1, 14);
        sheet[2].setRotationPoint(0F, 13F, 0F);
        sheet[2].setTextureSize(64, 16);
        setRotation(sheet[2], 0F, 0F, 0F);
        sheet[3] = new ModelRenderer(this, 0, 0);
        sheet[3].addBox(-7F, 0F, -7F, 14, 1, 14);
        sheet[3].setRotationPoint(0F, 12F, 0F);
        sheet[3].setTextureSize(64, 16);
        setRotation(sheet[3], 0F, 0.1047198F, 0F);
        sheet[4] = new ModelRenderer(this, 0, 0);
        sheet[4].addBox(-7F, 0F, -7F, 14, 1, 14);
        sheet[4].setRotationPoint(0F, 11F, 0F);
        sheet[4].setTextureSize(64, 16);
        setRotation(sheet[4], 0F, 0F, 0F);
        sheet[5] = new ModelRenderer(this, 0, 0);
        sheet[5].addBox(-7F, 0F, -7F, 14, 1, 14);
        sheet[5].setRotationPoint(0F, 10F, 0F);
        sheet[5].setTextureSize(64, 16);
        setRotation(sheet[5], 0F, -0.0698132F, 0F);
        sheet[6] = new ModelRenderer(this, 0, 0);
        sheet[6].addBox(-7F, 0F, -7F, 14, 1, 14);
        sheet[6].setRotationPoint(0F, 9F, 0F);
        sheet[6].setTextureSize(64, 16);
        setRotation(sheet[6], 0F, 0.0174533F, 0F);
        sheet[7] = new ModelRenderer(this, 0, 0);
        sheet[7].addBox(-7F, 0F, -7F, 14, 1, 14);
        sheet[7].setRotationPoint(0F, 8F, 0F);
        sheet[7].setTextureSize(64, 16);
        setRotation(sheet[7], 0F, 0.122173F, 0F);
        sheet[8] = new ModelRenderer(this, 0, 0);
        sheet[8].addBox(-7F, 0F, -7F, 14, 1, 14);
        sheet[8].setRotationPoint(0F, 7F, 0F);
        sheet[8].setTextureSize(64, 16);
        setRotation(sheet[8], 0F, 0.0349066F, 0F);
        sheet[9] = new ModelRenderer(this, 0, 0);
        sheet[9].addBox(-7F, 0F, -7F, 14, 1, 14);
        sheet[9].setRotationPoint(0F, 6F, 0F);
        sheet[9].setTextureSize(64, 16);
        setRotation(sheet[9], 0F, -0.0523599F, 0F);
        sheet[10] = new ModelRenderer(this, 0, 0);
        sheet[10].addBox(-7F, 0F, -7F, 14, 1, 14);
        sheet[10].setRotationPoint(0F, 5F, 0F);
        sheet[10].setTextureSize(64, 16);
        setRotation(sheet[10], 0F, 0F, 0F);
        sheet[11] = new ModelRenderer(this, 0, 0);
        sheet[11].addBox(-7F, 0F, -7F, 14, 1, 14);
        sheet[11].setRotationPoint(0F, 4F, 0F);
        sheet[11].setTextureSize(64, 16);
        setRotation(sheet[11], 0F, -0.0523599F, 0F);
        sheet[12] = new ModelRenderer(this, 0, 0);
        sheet[12].addBox(-7F, 0F, -7F, 14, 1, 14);
        sheet[12].setRotationPoint(0F, 3F, 0F);
        sheet[12].setTextureSize(64, 16);
        setRotation(sheet[12], 0F, 0.0349066F, 0F);
        sheet[13] = new ModelRenderer(this, 0, 0);
        sheet[13].addBox(-7F, 0F, -7F, 14, 1, 14);
        sheet[13].setRotationPoint(0F, 2F, 0F);
        sheet[13].setTextureSize(64, 16);
        setRotation(sheet[13], 0F, -0.0349066F, 0F);
        sheet[14] = new ModelRenderer(this, 0, 0);
        sheet[14].addBox(-7F, 0F, -7F, 14, 1, 14);
        sheet[14].setRotationPoint(0F, 1F, 0F);
        sheet[14].setTextureSize(64, 16);
        setRotation(sheet[14], 0F, -0.122173F, 0F);
        sheet[15] = new ModelRenderer(this, 0, 0);
        sheet[15].addBox(-7F, 0F, -7F, 14, 1, 14);
        sheet[15].setRotationPoint(0F, 0F, 0F);
        sheet[15].setTextureSize(64, 16);
        setRotation(sheet[15], 0F, 0F, 0F);
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
