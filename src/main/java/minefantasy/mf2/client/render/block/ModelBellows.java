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
public class ModelBellows extends ModelBase {
    // fields
    ModelRenderer Flap4;
    ModelRenderer Handle;
    ModelRenderer Flap1;
    ModelRenderer Flap2;
    ModelRenderer Flap3;
    ModelRenderer Base;
    ModelRenderer Top;

    public ModelBellows() {
        textureWidth = 64;
        textureHeight = 32;

        Flap4 = new ModelRenderer(this, 0, 23);
        Flap4.addBox(0F, 0F, -4F, 10, 1, 8);
        Flap4.setRotationPoint(-8F, 23F, 0F);
        Flap4.setTextureSize(64, 32);
        Flap4.mirror = true;
        setRotation(Flap4, 0F, 0F, -0.418879F);
        Handle = new ModelRenderer(this, 40, 0);
        Handle.addBox(7F, -1F, -1F, 10, 2, 2);
        Handle.setRotationPoint(-8F, 23F, 0F);
        Handle.setTextureSize(64, 32);
        Handle.mirror = true;
        setRotation(Handle, 0F, 0F, -0.5235988F);
        Flap1 = new ModelRenderer(this, 34, 23);
        Flap1.addBox(0F, 0F, -3F, 9, 1, 6);
        Flap1.setRotationPoint(-8F, 23F, 0F);
        Flap1.setTextureSize(64, 32);
        Flap1.mirror = true;
        setRotation(Flap1, 0F, 0F, -0.1047198F);
        Flap2 = new ModelRenderer(this, 0, 23);
        Flap2.addBox(0F, 0F, -4F, 10, 1, 8);
        Flap2.setRotationPoint(-8F, 23F, 0F);
        Flap2.setTextureSize(64, 32);
        Flap2.mirror = true;
        setRotation(Flap2, 0F, 0F, -0.2094395F);
        Flap3 = new ModelRenderer(this, 34, 23);
        Flap3.addBox(0F, 0F, -3F, 9, 1, 6);
        Flap3.setRotationPoint(-8F, 23F, 0F);
        Flap3.setTextureSize(64, 32);
        Flap3.mirror = true;
        setRotation(Flap3, 0F, 0F, -0.3141593F);
        Base = new ModelRenderer(this, 0, 0);
        Base.addBox(0F, 0F, -5F, 11, 1, 10);
        Base.setRotationPoint(-8F, 23F, 0F);
        Base.setTextureSize(64, 32);
        Base.mirror = true;
        setRotation(Base, 0F, 0F, 0F);
        Top = new ModelRenderer(this, 0, 14);
        Top.addBox(0F, 0F, -4F, 11, 1, 8);
        Top.setRotationPoint(-8F, 23F, 0F);
        Top.setTextureSize(64, 32);
        Top.mirror = true;
        setRotation(Top, 0F, 0F, -0.5235988F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Flap4.render(f5);
        Handle.render(f5);
        Flap1.render(f5);
        Flap2.render(f5);
        Flap3.render(f5);
        Base.render(f5);
        Top.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void renderModel(int press, float f) {
        Flap4.render(f);
        Handle.render(f);
        Flap1.render(f);
        Flap2.render(f);
        Flap3.render(f);
        Base.render(f);
        Top.render(f);
    }

    public void rotate(int p) {
        Top.rotateAngleZ = Handle.rotateAngleZ = -getRotationForPart(p, 5);
        Flap4.rotateAngleZ = -getRotationForPart(p, 4);
        Flap3.rotateAngleZ = -getRotationForPart(p, 3);
        Flap2.rotateAngleZ = -getRotationForPart(p, 2);
        Flap1.rotateAngleZ = -getRotationForPart(p, 1);
    }

    public float getRotationForPart(int press, int part) {
        float angle = (float) Math.toRadians(50 - press);
        return (angle / 5) * part;
    }
}