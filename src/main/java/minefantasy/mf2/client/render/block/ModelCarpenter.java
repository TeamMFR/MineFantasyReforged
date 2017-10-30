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
public class ModelCarpenter extends ModelBase {
    // fields
    ModelRenderer leg4;
    ModelRenderer top;
    ModelRenderer bottom;
    ModelRenderer leg3;
    ModelRenderer leg1;
    ModelRenderer leg2;

    public ModelCarpenter() {
        textureWidth = 64;
        textureHeight = 64;

        leg4 = new ModelRenderer(this, 0, 35);
        leg4.addBox(3F, 4F, 3F, 4, 12, 4);
        leg4.setRotationPoint(0F, 0F, 0F);
        leg4.setTextureSize(64, 64);
        leg4.mirror = true;
        setRotation(leg4, 0F, 0F, 0F);
        top = new ModelRenderer(this, 0, 0);
        top.addBox(-8F, 0F, -8F, 16, 4, 16);
        top.setRotationPoint(0F, 0F, 0F);
        top.setTextureSize(64, 64);
        top.mirror = true;
        setRotation(top, 0F, 0F, 0F);
        bottom = new ModelRenderer(this, 0, 20);
        bottom.addBox(-6.5F, 11F, -6.5F, 13, 2, 13);
        bottom.setRotationPoint(0F, 0F, 0F);
        bottom.setTextureSize(64, 64);
        bottom.mirror = true;
        setRotation(bottom, 0F, 0F, 0F);
        leg3 = new ModelRenderer(this, 0, 35);
        leg3.addBox(-7F, 4F, 3F, 4, 12, 4);
        leg3.setRotationPoint(0F, 0F, 0F);
        leg3.setTextureSize(64, 64);
        leg3.mirror = true;
        setRotation(leg3, 0F, 0F, 0F);
        leg1 = new ModelRenderer(this, 0, 35);
        leg1.addBox(-7F, 4F, -7F, 4, 12, 4);
        leg1.setRotationPoint(0F, 0F, 0F);
        leg1.setTextureSize(64, 64);
        leg1.mirror = true;
        setRotation(leg1, 0F, 0F, 0F);
        leg2 = new ModelRenderer(this, 0, 35);
        leg2.addBox(3F, 4F, -7F, 4, 12, 4);
        leg2.setRotationPoint(0F, 0F, 0F);
        leg2.setTextureSize(64, 64);
        leg2.mirror = true;
        setRotation(leg2, 0F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        top.render(f5);
        bottom.render(f5);
        leg1.render(f5);
        leg2.render(f5);
        leg3.render(f5);
        leg4.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void renderModel(float f) {
        top.render(f);
        bottom.render(f);
        leg1.render(f);
        leg2.render(f);
        leg3.render(f);
        leg4.render(f);
    }
}
