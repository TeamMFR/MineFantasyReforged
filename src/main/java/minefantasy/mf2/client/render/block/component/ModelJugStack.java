package minefantasy.mf2.client.render.block.component;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelJugStack extends ModelBase {
    private ModelRenderer[] jug = new ModelRenderer[32];

    public ModelJugStack() {
        textureWidth = 32;
        textureHeight = 16;
        int i = 0;
        for (int y = 0; y < 2; y++) {
            for (int z = 0; z < 4; z++) {
                for (int x = 0; x < 4; x++) {
                    float xCoord = -8F + (x * 4F);
                    float yCoord = 8F - (y * 8F);
                    float zCoord = 8F - (z * 4F);

                    jug[i] = new ModelRenderer(this, 0, 4);
                    jug[i].addBox(0F, 2F, -3F, 3, 6, 3);
                    jug[i].setTextureOffset(12, 0);
                    jug[i].addBox(1F, 1F, -2F, 3, 6, 1);
                    jug[i].setTextureOffset(0, 0);
                    jug[i].addBox(0.5F, 0F, -2.5F, 2, 2, 2);

                    jug[i].setRotationPoint(xCoord, yCoord, zCoord);
                    jug[i].setTextureSize(31, 16);
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
            jug[i].render(f);
        }
    }
}
