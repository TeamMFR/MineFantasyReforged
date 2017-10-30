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
public class ModelTrough extends ModelBase {
    ModelRenderer LeftWall;
    ModelRenderer Fill;
    ModelRenderer FrontWall;
    ModelRenderer BackWall;
    ModelRenderer RightWall;
    ModelRenderer Base;

    public ModelTrough() {
        textureWidth = 64;
        textureHeight = 32;

        LeftWall = new ModelRenderer(this, 42, 15);
        LeftWall.addBox(-8F, -6F, -5F, 1, 7, 10);
        LeftWall.setRotationPoint(0F, 0F, 0F);
        LeftWall.setTextureSize(64, 32);
        LeftWall.mirror = true;
        setRotation(LeftWall, 0F, 0F, 0F);
        Fill = new ModelRenderer(this, 40, 0);
        Fill.addBox(-7F, 0F, -5F, 14, 0, 10);
        Fill.setRotationPoint(0F, 0F, 0F);
        Fill.setTextureSize(64, 32);
        Fill.mirror = true;
        setRotation(Fill, 0F, 0F, 0F);
        FrontWall = new ModelRenderer(this, 0, 11);
        FrontWall.addBox(-8F, -6F, -6F, 16, 7, 1);
        FrontWall.setRotationPoint(0F, 0F, 0F);
        FrontWall.setTextureSize(64, 32);
        FrontWall.mirror = true;
        setRotation(FrontWall, 0F, 0F, 0F);
        BackWall = new ModelRenderer(this, 0, 11);
        BackWall.addBox(-8F, -6F, 5F, 16, 7, 1);
        BackWall.setRotationPoint(0F, 0F, 0F);
        BackWall.setTextureSize(64, 32);
        BackWall.mirror = true;
        setRotation(BackWall, 0F, 0F, 0F);
        RightWall = new ModelRenderer(this, 42, 15);
        RightWall.addBox(7F, -6F, -5F, 1, 7, 10);
        RightWall.setRotationPoint(0F, 0F, 0F);
        RightWall.setTextureSize(64, 32);
        RightWall.mirror = true;
        setRotation(RightWall, 0F, 0F, 0F);
        Base = new ModelRenderer(this, 0, 0);
        Base.addBox(-7F, 0F, -5F, 14, 1, 10);
        Base.setRotationPoint(0F, 0F, 0F);
        Base.setTextureSize(64, 32);
        Base.mirror = true;
        setRotation(Base, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        renderModel(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
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

    public void renderWater(float f) {
        Fill.render(f);
    }
}