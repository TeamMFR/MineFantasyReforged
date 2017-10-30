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
public class ModelOven extends ModelBase {
    ModelRenderer top;
    ModelRenderer base;
    ModelRenderer right;
    ModelRenderer topfront;
    ModelRenderer left;
    ModelRenderer back;
    ModelRenderer front;

    public ModelOven() {
        float offsetY = 1F;
        textureWidth = 64;
        textureHeight = 32;

        top = new ModelRenderer(this, 24, 0);
        top.addBox(-5F, -11F + offsetY, -5F, 10, 1, 10);
        top.setRotationPoint(0F, 0F, 0F);
        top.setTextureSize(64, 32);
        top.mirror = true;
        setRotation(top, 0F, 0F, 0F);

        base = new ModelRenderer(this, 24, 21);
        base.addBox(-5F, offsetY, -5F, 10, 1, 10);
        base.setRotationPoint(0F, 0F, 0F);
        base.setTextureSize(64, 32);
        base.mirror = true;
        setRotation(base, 0F, 0F, 0F);

        right = new ModelRenderer(this, 0, 11);
        right.addBox(-5F, -10F + offsetY, 5F, 10, 10, 1);
        right.setRotationPoint(0F, 0F, 0F);
        right.setTextureSize(64, 32);
        right.mirror = true;
        setRotation(right, 0F, 1.570796F, 0F);

        topfront = new ModelRenderer(this, 22, 11);
        topfront.addBox(-5F, -10F + offsetY, -6F, 10, 2, 1);
        topfront.setRotationPoint(0F, 0F, 0F);
        topfront.setTextureSize(64, 32);
        topfront.mirror = true;
        setRotation(topfront, 0F, 0F, 0F);

        left = new ModelRenderer(this, 0, 11);
        left.addBox(-5F, -10F + offsetY, 5F, 10, 10, 1);
        left.setRotationPoint(0F, 0F, 0F);
        left.setTextureSize(64, 32);
        left.mirror = true;
        setRotation(left, 0F, -1.570796F, 0F);

        back = new ModelRenderer(this, 0, 11);
        back.addBox(-5F, -10F + offsetY, 5F, 10, 10, 1);
        back.setRotationPoint(0F, 0F, 0F);
        back.setTextureSize(64, 32);
        back.mirror = true;
        setRotation(back, 0F, 0F, 0F);

        front = new ModelRenderer(this, 22, 18);
        front.addBox(-5F, -2F + offsetY, -6F, 10, 2, 1);
        front.setRotationPoint(0F, 0F, 0F);
        front.setTextureSize(64, 32);
        front.mirror = true;
        setRotation(front, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
    }

    public void render(float f) {
        top.render(f);
        base.render(f);
        right.render(f);
        topfront.render(f);
        left.render(f);
        back.render(f);
        front.render(f);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
