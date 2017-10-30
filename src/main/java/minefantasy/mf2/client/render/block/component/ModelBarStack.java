package minefantasy.mf2.client.render.block.component;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelBarStack extends ModelBase {
    private ModelRenderer[] bar = new ModelRenderer[64];

    public ModelBarStack() {
        textureWidth = 32;
        textureHeight = 16;
        int i = 0;
        for (int y = 0; y < 4; y++) {
            for (int z = 0; z < 2; z++) {
                for (int x = 0; x < 2; x++) {
                    float xCoord = -7.5F + (x * 8F);
                    float yCoord = 12F - (y * 4F);
                    float zCoord = 7.5F - (z * 8F);

                    bar[i] = new ModelRenderer(this, 0, 0);
                    bar[i].addBox(0F, 2F, -7F, 3, 2, 7);
                    bar[i].setRotationPoint(xCoord, yCoord, zCoord);
                    bar[i].setTextureSize(64, 32);
                    setRotation(bar[i], 0F, 0F, 0F);
                    ++i;

                    bar[i] = new ModelRenderer(this, 0, 0);
                    bar[i].addBox(4F, 2F, -7F, 3, 2, 7);
                    bar[i].setRotationPoint(xCoord, yCoord, zCoord);
                    bar[i].setTextureSize(64, 32);
                    setRotation(bar[i], 0F, 0F, 0F);
                    ++i;

                    bar[i] = new ModelRenderer(this, 0, 0);
                    bar[i].addBox(-3F, 0F, -7F, 3, 2, 7);
                    bar[i].setRotationPoint(xCoord, yCoord, zCoord);
                    bar[i].setTextureSize(64, 32);
                    setRotation(bar[i], 0F, -1.570796F, 0F);
                    ++i;

                    bar[i] = new ModelRenderer(this, 0, 0);
                    bar[i].addBox(-7F, 0F, -7F, 3, 2, 7);
                    bar[i].setRotationPoint(xCoord, yCoord, zCoord);
                    bar[i].setTextureSize(64, 32);
                    setRotation(bar[i], 0F, -1.570796F, 0F);
                    ++i;
                }
            }
        }

    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void render(int stackSize, float f) {
        for (int i = 0; i < stackSize; i++) {
            bar[i].render(f);
        }
    }
}
