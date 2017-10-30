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
public class ModelAmmoBox extends ModelBase {
    protected ModelRenderer LeftWall;
    protected ModelRenderer FrontWall;
    protected ModelRenderer BackWall;
    protected ModelRenderer RightWall;
    protected ModelRenderer lid;
    protected ModelRenderer Base;
    protected ModelRenderer lock2;
    protected ModelRenderer lock1;

    public ModelAmmoBox() {
        textureWidth = 64;
        textureHeight = 32;

        LeftWall = new ModelRenderer(this, 34, 0);
        LeftWall.addBox(-6F, -4F, -3F, 1, 5, 6);
        LeftWall.setRotationPoint(0F, 0F, 0F);
        LeftWall.setTextureSize(64, 32);
        setRotation(LeftWall, 0F, 0F, 0F);
        FrontWall = new ModelRenderer(this, 0, 7);
        FrontWall.addBox(-6F, -4F, -4F, 12, 5, 1);
        FrontWall.setRotationPoint(0F, 0F, 0F);
        FrontWall.setTextureSize(64, 32);
        setRotation(FrontWall, 0F, 0F, 0F);
        BackWall = new ModelRenderer(this, 0, 7);
        BackWall.addBox(-6F, -4F, 3F, 12, 5, 1);
        BackWall.setRotationPoint(0F, 0F, 0F);
        BackWall.setTextureSize(64, 32);
        setRotation(BackWall, 0F, 0F, 0F);
        RightWall = new ModelRenderer(this, 34, 0);
        RightWall.addBox(5F, -4F, -3F, 1, 5, 6);
        RightWall.setRotationPoint(0F, 0F, 0F);
        RightWall.setTextureSize(64, 32);

        setRotation(RightWall, 0F, 0F, 0F);
        lid = new ModelRenderer(this, 0, 13);
        lid.addBox(-6F, -1F, -8F, 12, 1, 8);
        lid.setRotationPoint(0F, -4F, 4F);
        lid.setTextureSize(64, 32);
        setRotation(lid, 0F, 0F, 0F);
        Base = new ModelRenderer(this, 2, 0);
        Base.addBox(-5F, 0F, -3F, 10, 1, 6);
        Base.setRotationPoint(0F, 0F, 0F);
        Base.setTextureSize(64, 32);
        setRotation(Base, 0F, 0F, 0F);
        lock2 = new ModelRenderer(this, 0, 0);
        lock2.addBox(2F, -1.5F, -9.5F, 2, 3, 2);
        lock2.setRotationPoint(0F, -4F, 5F);
        lock2.setTextureSize(64, 32);
        setRotation(lock2, 0F, 0F, 0F);
        lock1 = new ModelRenderer(this, 0, 0);
        lock1.addBox(-4F, -1.5F, -9.5F, 2, 3, 2);
        lock1.setRotationPoint(0F, -4F, 5F);
        lock1.setTextureSize(64, 32);
        setRotation(lock1, 0F, 0F, 0F);

        RightWall.mirror = true;
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        renderModel(f5);
    }

    protected void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void renderModel(float f) {
        LeftWall.render(f);
        RightWall.render(f);
        FrontWall.render(f);
        BackWall.render(f);
        Base.render(f);
    }

    public void renderLid(float f, float angle) {
        lock1.rotateAngleX = lock2.rotateAngleX = lid.rotateAngleX = -(float) Math.toRadians(angle);
        lid.render(f);
        lock1.render(f);
        lock2.render(f);
    }
}