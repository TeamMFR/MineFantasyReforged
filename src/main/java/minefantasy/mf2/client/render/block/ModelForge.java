package minefantasy.mf2.client.render.block;

import minefantasy.mf2.block.tileentity.TileEntityForge;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * @author Anonymous Productions
 * <p>
 * Sources are provided for educational reasons. though small bits of
 * code, or methods can be used in your own creations.
 */
public class ModelForge extends ModelBase {
    // fields
    ModelRenderer Back;
    ModelRenderer Right;
    ModelRenderer cornerBR;
    ModelRenderer Left;
    ModelRenderer Base;
    ModelRenderer front;
    ModelRenderer cornerBL;
    ModelRenderer cornerFR;
    ModelRenderer cornerFL;
    ModelRenderer fuel;

    public ModelForge() {
        textureWidth = 64;
        textureHeight = 64;

        Back = new ModelRenderer(this, 0, 0);
        Back.addBox(-6F, -6F, -8F, 12, 7, 2);
        Back.setRotationPoint(0F, 22F, 0F);
        Back.setTextureSize(64, 64);
        Back.mirror = true;
        setRotation(Back, 0F, 1.570796F, 0F);
        Right = new ModelRenderer(this, 0, 0);
        Right.addBox(-6F, -8F, -8F, 12, 7, 2);
        Right.setRotationPoint(0F, 24F, 0F);
        Right.setTextureSize(64, 64);
        Right.mirror = true;
        setRotation(Right, 0F, 3.154392F, 0F);
        cornerBR = new ModelRenderer(this, 0, 9);
        cornerBR.addBox(-8F, -7F, -8F, 2, 6, 2);
        cornerBR.setRotationPoint(0F, 24F, 0F);
        cornerBR.setTextureSize(64, 64);
        cornerBR.mirror = true;
        setRotation(cornerBR, 0F, 1.570796F, 0F);
        Left = new ModelRenderer(this, 0, 0);
        Left.addBox(-6F, -6F, -8F, 12, 7, 2);
        Left.setRotationPoint(0F, 22F, 0F);
        Left.setTextureSize(64, 64);
        Left.mirror = true;
        setRotation(Left, 0F, 0F, 0F);
        Base = new ModelRenderer(this, 0, 15);
        Base.addBox(-8F, 1F, -8F, 16, 1, 16);
        Base.setRotationPoint(0F, 22F, 0F);
        Base.setTextureSize(64, 64);
        Base.mirror = true;
        setRotation(Base, 0F, 0F, 0F);
        front = new ModelRenderer(this, 0, 0);
        front.addBox(-6F, -8F, -8F, 12, 7, 2);
        front.setRotationPoint(0F, 24F, 0F);
        front.setTextureSize(64, 64);
        front.mirror = true;
        setRotation(front, 0F, -1.570796F, 0F);
        cornerBL = new ModelRenderer(this, 0, 9);
        cornerBL.addBox(6F, -5F, -8F, 2, 6, 2);
        cornerBL.setRotationPoint(0F, 22F, 0F);
        cornerBL.setTextureSize(64, 64);
        cornerBL.mirror = true;
        setRotation(cornerBL, 0F, 1.570796F, 0F);
        cornerFR = new ModelRenderer(this, 0, 9);
        cornerFR.addBox(-8F, -7F, 6F, 2, 6, 2);
        cornerFR.setRotationPoint(0F, 24F, 0F);
        cornerFR.setTextureSize(64, 64);
        cornerFR.mirror = true;
        setRotation(cornerFR, 0F, 1.570796F, 0F);
        cornerFL = new ModelRenderer(this, 0, 9);
        cornerFL.addBox(6F, -7F, 6F, 2, 6, 2);
        cornerFL.setRotationPoint(0F, 24F, 0F);
        cornerFL.setTextureSize(64, 64);
        cornerFL.mirror = true;
        setRotation(cornerFL, 0F, 1.570796F, 0F);
        fuel = new ModelRenderer(this, 32, 0);
        fuel.addBox(-8F, -1F, -8F, 16, 0, 16);
        fuel.addBox(-8F, -0.5F, -8F, 16, 0, 16);
        fuel.addBox(-8F, -0F, -8F, 16, 0, 16);
        fuel.setRotationPoint(0F, 18.5F, 0F);
        fuel.setTextureSize(64, 64);
        fuel.mirror = true;
        setRotation(fuel, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        Back.render(f5);
        Right.render(f5);
        cornerBR.render(f5);
        Left.render(f5);
        Base.render(f5);
        front.render(f5);
        cornerBL.render(f5);
        cornerFR.render(f5);
        cornerFL.render(f5);
        fuel.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void renderModel(TileEntityForge forge, float f, boolean hasFuel, float height) {
        boolean[] sides = new boolean[]{true, true, true, true};
        if (forge != null) {
            sides = forge.showSides();
        }

        if (sides[0])
            front.render(f);

        if (sides[1])
            Left.render(f);

        if (sides[2])
            Right.render(f);

        if (sides[3])
            Back.render(f);

        if (sides[3] || sides[2])
            cornerBR.render(f);

        if (sides[3] || sides[1])
            cornerBL.render(f);

        if (sides[0] || sides[2])
            cornerFR.render(f);

        if (sides[0] || sides[1])
            cornerFL.render(f);
        Base.render(f);
        if (hasFuel) {
            fuel.offsetY = -height * 0.3F + 0.3F;
            fuel.render(f);
        }
    }
}
