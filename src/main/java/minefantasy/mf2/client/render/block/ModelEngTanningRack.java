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
public class ModelEngTanningRack extends ModelBase {
    // fields
    ModelRenderer side2;
    ModelRenderer side1;
    ModelRenderer Blade2;
    ModelRenderer Top;
    ModelRenderer Base;
    ModelRenderer Blade;
    ModelRenderer Lever;
    ModelRenderer pivot;

    public ModelEngTanningRack() {
        textureWidth = 64;
        textureHeight = 32;

        side2 = new ModelRenderer(this, 50, 0);
        side2.addBox(6F, 5F, -2.5F, 2, 17, 5);
        side2.setRotationPoint(0F, 0F, 0F);
        side2.setTextureSize(64, 32);
        setRotation(side2, 0F, 0F, 0F);
        side1 = new ModelRenderer(this, 50, 0);
        side1.addBox(-8F, 5F, -2.5F, 2, 17, 5);
        side1.setRotationPoint(0F, 0F, 0F);
        side1.setTextureSize(64, 32);
        setRotation(side1, 0F, 0F, 0F);
        Blade2 = new ModelRenderer(this, 26, 28);
        Blade2.addBox(-6F, 1F, -0.5F, 12, 3, 1);
        Blade2.setRotationPoint(0F, 4F, 1F);
        Blade2.setTextureSize(64, 32);
        setRotation(Blade2, -0.2443461F, 0F, 0F);
        Top = new ModelRenderer(this, 0, 28);
        Top.addBox(-6F, 5F, -0.5F, 12, 3, 1);
        Top.setRotationPoint(0F, 0F, 0F);
        Top.setTextureSize(64, 32);
        setRotation(Top, 0F, 0F, 0F);
        Base = new ModelRenderer(this, 0, 0);
        Base.addBox(-8F, 22F, -3.5F, 16, 2, 6);
        Base.setRotationPoint(0F, 0F, 0F);
        Base.setTextureSize(64, 32);
        setRotation(Base, 0F, 0F, 0F);
        Blade = new ModelRenderer(this, 26, 28);
        Blade.addBox(-6F, 1F, -0.5F, 12, 3, 1);
        Blade.setRotationPoint(0F, 4F, -1F);
        Blade.setTextureSize(64, 32);
        setRotation(Blade, 0.2443461F, 0F, 0F);
        Lever = new ModelRenderer(this, 0, 8);
        Lever.addBox(-1F, 1F, -0.5F, 1, 10, 1);
        Lever.setRotationPoint(5F, 5F, 0F);
        Lever.setTextureSize(64, 32);
        setRotation(Lever, 0F, 0F, 1.570796F);
        pivot = new ModelRenderer(this, 4, 8);
        pivot.addBox(-1.5F, -1F, -1F, 2, 2, 2);
        pivot.setRotationPoint(5F, 5F, 0F);
        pivot.setTextureSize(64, 32);
        setRotation(pivot, 0F, 0F, 1.570796F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        renderModel(f5);
    }

    public void renderModel(float f5) {
        Base.render(f5);
        Top.render(f5);
        side1.render(f5);
        side2.render(f5);
    }

    public void renderBlade(float f5) {
        Blade.render(f5);
        Blade2.render(f5);
    }

    public void rotateLever(float animation) {
        pivot.rotateAngleX = Lever.rotateAngleX = (float) Math.toRadians(animation * 45F);
    }

    public void renderLever(float f5) {
        pivot.render(f5);
        Lever.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}