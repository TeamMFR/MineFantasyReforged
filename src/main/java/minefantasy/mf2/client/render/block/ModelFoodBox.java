package minefantasy.mf2.client.render.block;

import net.minecraft.client.model.ModelRenderer;

/**
 * @author Anonymous Productions
 * <p>
 * Sources are provided for educational reasons. though small bits of
 * code, or methods can be used in your own creations.
 */
public class ModelFoodBox extends ModelAmmoBox {
    public ModelFoodBox() {
        textureWidth = 64;
        textureHeight = 32;

        LeftWall = new ModelRenderer(this, 20, 0);
        LeftWall.addBox(-4F, -2F, -2F, 1, 3, 4);
        LeftWall.setRotationPoint(0F, 0F, 0F);
        LeftWall.setTextureSize(64, 32);
        setRotation(LeftWall, 0F, 0F, 0F);
        FrontWall = new ModelRenderer(this, 0, 5);
        FrontWall.addBox(-4F, -2F, -3F, 8, 3, 1);
        FrontWall.setRotationPoint(0F, 0F, 0F);
        FrontWall.setTextureSize(64, 32);
        setRotation(FrontWall, 0F, 0F, 0F);
        BackWall = new ModelRenderer(this, 0, 5);
        BackWall.addBox(-4F, -2F, 2F, 8, 3, 1);
        BackWall.setRotationPoint(0F, 0F, 0F);
        BackWall.setTextureSize(64, 32);
        setRotation(BackWall, 0F, 0F, 0F);
        RightWall = new ModelRenderer(this, 20, 0);
        RightWall.addBox(3F, -2F, -2F, 1, 3, 4);
        RightWall.setRotationPoint(0F, 0F, 0F);
        RightWall.setTextureSize(64, 32);
        setRotation(RightWall, 0F, 0F, 0F);
        lid = new ModelRenderer(this, 0, 11);
        lid.addBox(-4F, -1F, -6F, 8, 1, 6);
        lid.setRotationPoint(0F, -2F, 3F);
        lid.setTextureSize(64, 32);
        setRotation(lid, 0F, 0F, 0F);
        Base = new ModelRenderer(this, 0, 0);
        Base.addBox(-3F, 0F, -2F, 6, 1, 4);
        Base.setRotationPoint(0F, 0F, 0F);
        Base.setTextureSize(64, 32);
        setRotation(Base, 0F, 0F, 0F);
        lock1 = new ModelRenderer(this, 0, 0);
        lock1.addBox(-0.5F, -1.5F, -6.5F, 1, 2, 1);
        lock1.setRotationPoint(0F, -2F, 3F);
        lock1.setTextureSize(64, 32);
        setRotation(lock1, 0F, 0F, 0F);

        RightWall.mirror = true;
    }

    @Override
    public void renderLid(float f, float angle) {
        lock1.rotateAngleX = lid.rotateAngleX = -(float) Math.toRadians(angle);
        lid.render(f);
        lock1.render(f);
    }
}