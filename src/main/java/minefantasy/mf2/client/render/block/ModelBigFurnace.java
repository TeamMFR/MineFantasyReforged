package minefantasy.mf2.client.render.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBigFurnace extends ModelBase {
    ModelRenderer Wall4t;
    ModelRenderer Wall1;
    ModelRenderer Wall2;
    ModelRenderer Top;
    ModelRenderer Wall3;
    ModelRenderer Wall4;
    ModelRenderer lava;
    ModelRenderer Base;
    ModelRenderer contents;

    public ModelBigFurnace() {
        textureWidth = 128;
        textureHeight = 64;

        Wall4t = new ModelRenderer(this, 76, 50);
        Wall1 = new ModelRenderer(this, 0, 27);
        Wall2 = new ModelRenderer(this, 0, 27);
        Top = new ModelRenderer(this, 0, 0);
        Wall3 = new ModelRenderer(this, 48, 50);
        Wall4 = new ModelRenderer(this, 76, 60);
        lava = new ModelRenderer(this, 26, 27);
        Base = new ModelRenderer(this, 0, 0);
        contents = new ModelRenderer(this, 0, 41);

        Wall4t.addBox(-6F, -12F, -8F, 12, 3, 2);
        Wall4t.setRotationPoint(0F, 0F, 0F);
        Wall4t.setTextureSize(128, 64);
        Wall4t.mirror = true;
        setRotation(Wall4t, 0F, 1.570796F, 0F);

        Wall1.addBox(-8F, -12F, 6F, 16, 12, 2);
        Wall1.setRotationPoint(0F, 0F, 0F);
        Wall1.setTextureSize(128, 64);
        Wall1.mirror = true;
        setRotation(Wall1, 0F, 3.141593F, 0F);

        Wall2.addBox(-8F, -12F, 6F, 16, 12, 2);
        Wall2.setRotationPoint(0F, 0F, 0F);
        Wall2.setTextureSize(128, 64);
        Wall2.mirror = true;
        setRotation(Wall2, 0F, 0F, 0F);

        Top.addBox(-8F, -14F, -8F, 16, 2, 16);
        Top.setRotationPoint(0F, 0F, 0F);
        Top.setTextureSize(128, 64);
        Top.mirror = true;
        setRotation(Top, 0F, 0F, 0F);

        Wall3.addBox(-6F, -12F, 6F, 12, 12, 2);
        Wall3.setRotationPoint(0F, 0F, 0F);
        Wall3.setTextureSize(128, 64);
        Wall3.mirror = true;
        setRotation(Wall3, 0F, 1.570796F, 0F);

        Wall4.addBox(-6F, -2F, -8F, 12, 2, 2);
        Wall4.setRotationPoint(0F, 0F, 0F);
        Wall4.setTextureSize(128, 64);
        Wall4.mirror = true;
        setRotation(Wall4, 0F, 1.570796F, 0F);

        lava.addBox(-5F, -2F, -5F, 10, 0, 10);
        lava.setRotationPoint(0F, 0F, 0F);
        lava.setTextureSize(128, 64);
        lava.mirror = true;
        setRotation(lava, 0F, 0F, 0F);

        Base.addBox(-8F, -2F, -8F, 16, 2, 16);
        Base.setRotationPoint(0F, 0F, 0F);
        Base.setTextureSize(128, 64);
        Base.mirror = true;
        setRotation(Base, 3.141593F, 0F, 0F);

        contents.addBox(-6F, -1F, -6F, 12, 1, 12);
        contents.setRotationPoint(0F, 0F, 0F);
        contents.setTextureSize(128, 64);
        contents.mirror = true;
        setRotation(contents, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        renderModel(false, f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void renderModel(boolean lit, float f) {
        if (lit) {
            lava.render(f);
        }
        Top.render(f);
        contents.render(f);
        Base.render(f);
        Wall4t.render(f);
        Wall1.render(f);
        Wall2.render(f);
        Wall3.render(f);
        Wall4.render(f);
    }
}