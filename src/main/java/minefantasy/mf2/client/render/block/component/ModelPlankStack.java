package minefantasy.mf2.client.render.block.component;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelPlankStack extends ModelBase {
    private ModelRenderer[] plank = new ModelRenderer[64];

    public ModelPlankStack() {
        textureWidth = 32;
        textureHeight = 32;
        int i = 0;
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 4; x++) {
                float xCoord = -8F + x * 4F;
                float yCoord = 15F - y;
                float zCoord = 8F;
                if (y % 2 == 0) {
                    xCoord += 1F;
                }

                plank[i] = new ModelRenderer(this, 0, 0);
                plank[i].addBox(0F, 0F, 0F, 3, 16, 1);
                plank[i].setRotationPoint(xCoord, yCoord, zCoord);
                plank[i].setTextureSize(32, 32);
                setRotation(plank[i], -1.570796F, 0F, 0F);
                ++i;
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
            plank[i].render(f);
        }
    }
}
