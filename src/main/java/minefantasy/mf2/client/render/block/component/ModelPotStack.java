package minefantasy.mf2.client.render.block.component;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelPotStack extends ModelBase {
    private ModelRenderer[] pot = new ModelRenderer[64];

    public ModelPotStack() {
        textureWidth = 16;
        textureHeight = 8;
        int i = 0;
        for (int y = 0; y < 4; y++) {
            for (int z = 0; z < 4; z++) {
                for (int x = 0; x < 4; x++) {
                    float xCoord = -8F + (x * 4F);
                    float yCoord = 12F - (y * 4F);
                    float zCoord = 8F - (z * 4F);

                    pot[i] = new ModelRenderer(this, 0, 0);
                    pot[i].addBox(0F, 0F, -4F, 4, 4, 4);
                    pot[i].setRotationPoint(xCoord, yCoord, zCoord);
                    pot[i].setTextureSize(64, 32);
                    pot[i].mirror = true;
                    setRotation(pot[i], 0F, 0F, 0F);
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
            pot[i].render(f);
        }
    }
}
