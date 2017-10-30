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
public class ModelForgeTop extends ModelBase {
    // fields
    ModelRenderer Back;
    ModelRenderer Right;
    ModelRenderer cornerBR;
    ModelRenderer Left;
    ModelRenderer front;
    ModelRenderer cornerBL;
    ModelRenderer cornerFR;
    ModelRenderer cornerFL;
    ModelRenderer Top;

    public ModelForgeTop() {
        textureWidth = 64;
        textureHeight = 32;

        Back = new ModelRenderer(this, 0, 0);
        Back.addBox(-4F, -5F, -6F, 8, 6, 2);
        Back.setRotationPoint(0F, 22F, 0F);
        Back.setTextureSize(64, 32);
        Back.mirror = true;
        setRotation(Back, 0F, 1.570796F, 0F);
        Right = new ModelRenderer(this, 0, 0);
        Right.addBox(-4F, -7F, -6F, 8, 6, 2);
        Right.setRotationPoint(0F, 24F, 0F);
        Right.setTextureSize(64, 32);
        Right.mirror = true;
        setRotation(Right, 0F, 3.154392F, 0F);
        cornerBR = new ModelRenderer(this, 0, 8);
        cornerBR.addBox(-6F, -13F, -6F, 2, 12, 2);
        cornerBR.setRotationPoint(0F, 24F, 0F);
        cornerBR.setTextureSize(64, 32);
        cornerBR.mirror = true;
        setRotation(cornerBR, 0F, 1.570796F, 0F);
        Left = new ModelRenderer(this, 0, 0);
        Left.addBox(-4F, -5F, -6F, 8, 6, 2);
        Left.setRotationPoint(0F, 22F, 0F);
        Left.setTextureSize(64, 32);
        Left.mirror = true;
        setRotation(Left, 0F, 0F, 0F);
        front = new ModelRenderer(this, 0, 0);
        front.addBox(-4F, -7F, -6F, 8, 6, 2);
        front.setRotationPoint(0F, 24F, 0F);
        front.setTextureSize(64, 32);
        front.mirror = true;
        setRotation(front, 0F, -1.570796F, 0F);
        cornerBL = new ModelRenderer(this, 0, 8);
        cornerBL.addBox(4F, -11F, -6F, 2, 12, 2);
        cornerBL.setRotationPoint(0F, 22F, 0F);
        cornerBL.setTextureSize(64, 32);
        cornerBL.mirror = true;
        setRotation(cornerBL, 0F, 1.570796F, 0F);
        cornerFR = new ModelRenderer(this, 0, 8);
        cornerFR.addBox(-6F, -13F, 4F, 2, 12, 2);
        cornerFR.setRotationPoint(0F, 24F, 0F);
        cornerFR.setTextureSize(64, 32);
        cornerFR.mirror = true;
        setRotation(cornerFR, 0F, 1.570796F, 0F);
        cornerFL = new ModelRenderer(this, 0, 8);
        cornerFL.addBox(4F, -13F, 4F, 2, 12, 2);
        cornerFL.setRotationPoint(0F, 24F, 0F);
        cornerFL.setTextureSize(64, 32);
        cornerFL.mirror = true;
        setRotation(cornerFL, 0F, 1.570796F, 0F);
        Top = new ModelRenderer(this, 20, 0);
        Top.addBox(-6F, -14F, -6F, 12, 3, 12);
        Top.setRotationPoint(0F, 22F, 0F);
        Top.setTextureSize(64, 32);
        Top.mirror = true;
        setRotation(Top, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
    }

    public void render(float f) {
        Back.render(f);
        Right.render(f);
        cornerBR.render(f);
        Left.render(f);
        front.render(f);
        cornerBL.render(f);
        cornerFR.render(f);
        cornerFL.render(f);
        Top.render(f);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
