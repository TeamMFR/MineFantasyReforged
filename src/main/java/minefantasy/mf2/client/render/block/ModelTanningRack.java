package minefantasy.mf2.client.render.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * @author Anonymous Productions
 * <p>
 * Sources are provided for educational reasons. though small bits of
 * code, or methods can be used in your own creations.
 */
public class ModelTanningRack extends ModelBase {
    // fields
    ModelRenderer Base;
    ModelRenderer side2;
    ModelRenderer side1;
    ModelRenderer Top;

    public ModelTanningRack() {
        textureWidth = 64;
        textureHeight = 32;

        Base = new ModelRenderer(this, 0, 0);
        Base.addBox(-8F, 22F, -2.5F, 16, 2, 5);
        Base.setRotationPoint(0F, 0F, 0F);
        Base.setTextureSize(64, 32);
        Base.mirror = true;
        setRotation(Base, 0F, 0F, 0F);
        side2 = new ModelRenderer(this, 54, 0);
        side2.addBox(6F, 5F, -1.5F, 2, 17, 3);
        side2.setRotationPoint(0F, 0F, 0F);
        side2.setTextureSize(64, 32);
        side2.mirror = true;
        setRotation(side2, 0F, 0F, 0F);
        side1 = new ModelRenderer(this, 54, 0);
        side1.mirror = true;
        side1.addBox(-8F, 5F, -1.5F, 2, 17, 3);
        side1.setRotationPoint(0F, 0F, 0F);
        side1.setTextureSize(64, 32);
        side1.mirror = true;
        setRotation(side1, 0F, 0F, 0F);
        side1.mirror = false;
        Top = new ModelRenderer(this, 0, 27);
        Top.addBox(-6F, 6F, -0.5F, 12, 3, 2);
        Top.setRotationPoint(0F, 0F, 0F);
        Top.setTextureSize(64, 32);
        Top.mirror = true;
        setRotation(Top, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Base.render(f5);
        Top.render(f5);
        side1.render(f5);
        side2.render(f5);
    }

    public void renderModel(float f5) {
        Base.render(f5);
        Top.render(f5);
        side1.render(f5);
        side2.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}