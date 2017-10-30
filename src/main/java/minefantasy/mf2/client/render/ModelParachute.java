package minefantasy.mf2.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelParachute extends ModelBase {
    // fields
    ModelRenderer chuteSegment5;
    ModelRenderer harness;
    ModelRenderer chuteSegment1;
    ModelRenderer chuteSegment2;
    ModelRenderer chuteSegment3;
    ModelRenderer chuteSegment4;
    ModelRenderer cable4;
    ModelRenderer cable1;
    ModelRenderer cable2;
    ModelRenderer cable3;

    public ModelParachute() {
        textureWidth = 64;
        textureHeight = 32;

        chuteSegment5 = new ModelRenderer(this, 28, 14);
        chuteSegment5.addBox(-4.5F, -32F, -13.5F, 9, 9, 9);
        chuteSegment5.setRotationPoint(0F, 0F, 0F);
        chuteSegment5.setTextureSize(64, 32);
        setRotation(chuteSegment5, 0F, 0F, 0F);
        harness = new ModelRenderer(this, 28, -9);
        harness.addBox(-4.5F, -4.5F, -4.5F, 9, 9, 9);
        harness.setRotationPoint(0F, 0F, 0F);
        harness.setTextureSize(64, 32);
        setRotation(harness, 0F, 0F, 0F);
        chuteSegment1 = new ModelRenderer(this, 28, 14);
        chuteSegment1.addBox(-4.5F, -41F, -4.5F, 9, 9, 9);
        chuteSegment1.setRotationPoint(0F, 0F, 0F);
        chuteSegment1.setTextureSize(64, 32);
        setRotation(chuteSegment1, 0F, 0F, 0F);
        chuteSegment2 = new ModelRenderer(this, 28, 14);
        chuteSegment2.addBox(-13.5F, -32F, -4.5F, 9, 9, 9);
        chuteSegment2.setRotationPoint(0F, 0F, 0F);
        chuteSegment2.setTextureSize(64, 32);
        setRotation(chuteSegment2, 0F, 0F, 0F);
        chuteSegment3 = new ModelRenderer(this, 28, 14);
        chuteSegment3.addBox(4.5F, -32F, -4.5F, 9, 9, 9);
        chuteSegment3.setRotationPoint(0F, 0F, 0F);
        chuteSegment3.setTextureSize(64, 32);
        setRotation(chuteSegment3, 0F, 0F, 0F);
        chuteSegment4 = new ModelRenderer(this, 28, 14);
        chuteSegment4.addBox(-4.5F, -32F, 4.5F, 9, 9, 9);
        chuteSegment4.setRotationPoint(0F, 0F, 0F);
        chuteSegment4.setTextureSize(64, 32);
        setRotation(chuteSegment4, 0F, 0F, 0F);
        cable4 = new ModelRenderer(this, 0, 0);
        cable4.addBox(-0.5F, -23F, -0.5F, 1, 24, 1);
        cable4.setRotationPoint(0F, -4F, 0F);
        cable4.setTextureSize(64, 32);
        setRotation(cable4, 0F, 0F, -0.5235988F);
        cable1 = new ModelRenderer(this, 0, 0);
        cable1.addBox(-0.5F, -23F, -0.5F, 1, 24, 1);
        cable1.setRotationPoint(0F, -4F, 0F);
        cable1.setTextureSize(64, 32);
        setRotation(cable1, 0.5235988F, 0F, 0F);
        cable2 = new ModelRenderer(this, 0, 0);
        cable2.addBox(-0.5F, -23F, -0.5F, 1, 24, 1);
        cable2.setRotationPoint(0F, -4F, 0F);
        cable2.setTextureSize(64, 32);
        setRotation(cable2, -0.5235988F, 0F, 0F);
        cable3 = new ModelRenderer(this, 0, 0);
        cable3.addBox(-0.5F, -23F, -0.5F, 1, 24, 1);
        cable3.setRotationPoint(0F, -4F, 0F);
        cable3.setTextureSize(64, 32);
        setRotation(cable3, 0F, 0F, 0.5235988F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        chuteSegment5.render(f5);
        harness.render(f5);
        chuteSegment1.render(f5);
        chuteSegment2.render(f5);
        chuteSegment3.render(f5);
        chuteSegment4.render(f5);
        cable4.render(f5);
        cable1.render(f5);
        cable2.render(f5);
        cable3.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
