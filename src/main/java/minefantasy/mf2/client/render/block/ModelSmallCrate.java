package minefantasy.mf2.client.render.block;

import net.minecraft.client.model.ModelRenderer;

/**
 * @author Anonymous Productions
 * <p>
 * Sources are provided for educational reasons. though small bits of
 * code, or methods can be used in your own creations.
 */
public class ModelSmallCrate extends ModelAmmoBox {
    public ModelSmallCrate() {
        textureWidth = 64;
        textureHeight = 32;

        LeftWall = new ModelRenderer(this, 40, 1);
        LeftWall.addBox(-8F, -7F, -5F, 1, 7, 10);
        LeftWall.setRotationPoint(0F, 0F, 0F);
        LeftWall.setTextureSize(64, 32);
        setRotation(LeftWall, 0F, 0F, 0F);
        FrontWall = new ModelRenderer(this, 0, 10);
        FrontWall.addBox(-7F, -7F, -6F, 14, 8, 1);
        FrontWall.setRotationPoint(0F, 0F, 0F);
        FrontWall.setTextureSize(64, 32);
        setRotation(FrontWall, 0F, 0F, 0F);
        BackWall = new ModelRenderer(this, 0, 10);
        BackWall.addBox(-7F, -7F, 5F, 14, 8, 1);
        BackWall.setRotationPoint(0F, 0F, 0F);
        BackWall.setTextureSize(64, 32);
        setRotation(BackWall, 0F, 0F, 0F);
        RightWall = new ModelRenderer(this, 40, 1);
        RightWall.addBox(7F, -7F, -5F, 1, 7, 10);
        RightWall.setRotationPoint(0F, 0F, 0F);
        RightWall.setTextureSize(64, 32);
        setRotation(RightWall, 0F, 0F, 0F);
        lid = new ModelRenderer(this, 0, 19);
        lid.addBox(-7F, -1F, -12F, 14, 1, 12);
        lid.setRotationPoint(0F, -7F, 6F);
        lid.setTextureSize(64, 32);
        setRotation(lid, 0F, 0F, 0F);
        Base = new ModelRenderer(this, 0, 0);
        Base.addBox(-7F, 0F, -5F, 14, 1, 10);
        Base.setRotationPoint(0F, 0F, 0F);
        Base.setTextureSize(64, 32);
        setRotation(Base, 0F, 0F, 0F);
        lock2 = new ModelRenderer(this, 0, 0);
        lock2.addBox(2F, -1.5F, -12.5F, 2, 3, 2);
        lock2.setRotationPoint(0F, -7F, 6F);
        lock2.setTextureSize(64, 32);
        setRotation(lock2, 0F, 0F, 0F);
        lock1 = new ModelRenderer(this, 0, 0);
        lock1.addBox(-4F, -1.5F, -12.5F, 2, 3, 2);
        lock1.setRotationPoint(0F, -7F, 6F);
        lock1.setTextureSize(64, 32);
        setRotation(lock1, 0F, 0F, 0F);

        RightWall.mirror = true;
    }
}