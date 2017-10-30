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
public class ModelWoodCarpenter extends ModelBase {
    // fields
    ModelRenderer top;
    ModelRenderer FRleg;
    ModelRenderer bottom;
    ModelRenderer BRleg;
    ModelRenderer BLleg;
    ModelRenderer FLleg;
    ModelRenderer plank2;
    ModelRenderer hammerhandle;
    ModelRenderer block;
    ModelRenderer nail;
    ModelRenderer vice;
    ModelRenderer hammerhead;
    ModelRenderer plank;

    public ModelWoodCarpenter() {
        textureWidth = 64;
        textureHeight = 64;

        top = new ModelRenderer(this, 0, 0);
        top.addBox(-8F, 4F, -8F, 16, 4, 16);
        top.setRotationPoint(0F, 0F, 0F);
        top.setTextureSize(64, 64);
        top.mirror = true;
        setRotation(top, 0F, 0F, 0F);
        FRleg = new ModelRenderer(this, 0, 35);
        FRleg.addBox(3F, 8F, -7F, 4, 12, 4);
        FRleg.setRotationPoint(0F, 0F, 0F);
        FRleg.setTextureSize(64, 64);
        FRleg.mirror = true;
        setRotation(FRleg, 0F, 0F, 0F);
        bottom = new ModelRenderer(this, 0, 20);
        bottom.addBox(-6.5F, 14F, -6.5F, 13, 2, 13);
        bottom.setRotationPoint(0F, 0F, 0F);
        bottom.setTextureSize(64, 64);
        bottom.mirror = true;
        setRotation(bottom, 0F, 0F, 0F);
        BRleg = new ModelRenderer(this, 0, 35);
        BRleg.addBox(3F, 8F, 3F, 4, 12, 4);
        BRleg.setRotationPoint(0F, 0F, 0F);
        BRleg.setTextureSize(64, 64);
        BRleg.mirror = true;
        setRotation(BRleg, 0F, 0F, 0F);
        BLleg = new ModelRenderer(this, 0, 35);
        BLleg.addBox(-7F, 8F, 3F, 4, 12, 4);
        BLleg.setRotationPoint(0F, 0F, 0F);
        BLleg.setTextureSize(64, 64);
        BLleg.mirror = true;
        setRotation(BLleg, 0F, 0F, 0F);
        FLleg = new ModelRenderer(this, 0, 35);
        FLleg.addBox(-7F, 8F, -7F, 4, 12, 4);
        FLleg.setRotationPoint(0F, 0F, 0F);
        FLleg.setTextureSize(64, 64);
        FLleg.mirror = true;
        setRotation(FLleg, 0F, 0F, 0F);
        plank2 = new ModelRenderer(this, 0, 51);
        plank2.addBox(-5F, -1F, -1F, 10, 1, 2);
        plank2.setRotationPoint(2F, 4F, 6F);
        plank2.setTextureSize(64, 64);
        plank2.mirror = true;
        setRotation(plank2, 0F, -0.0698132F, 0F);
        hammerhandle = new ModelRenderer(this, 5, 51);
        hammerhandle.addBox(-3F, 0F, 0F, 6, 1, 1);
        hammerhandle.setRotationPoint(-2F, 13F, -2F);
        hammerhandle.setTextureSize(64, 64);
        hammerhandle.mirror = true;
        setRotation(hammerhandle, 0F, 1.570796F, 0F);
        block = new ModelRenderer(this, 0, 0);
        block.addBox(-2F, -2F, -2F, 4, 4, 4);
        block.setRotationPoint(0F, 2F, 0F);
        block.setTextureSize(64, 64);
        block.mirror = true;
        setRotation(block, 0F, -0.0523599F, 0F);
        nail = new ModelRenderer(this, 0, 8);
        nail.addBox(0F, -2F, 0F, 1, 3, 1);
        nail.setRotationPoint(0F, 0F, 0F);
        nail.setTextureSize(64, 64);
        nail.mirror = true;
        setRotation(nail, 0F, 0F, 0F);
        vice = new ModelRenderer(this, 4, 11);
        vice.addBox(-2F, -1F, -1F, 4, 3, 2);
        vice.setRotationPoint(5F, 4F, -8F);
        vice.setTextureSize(64, 64);
        vice.mirror = true;
        setRotation(vice, 0F, 0F, 0F);
        hammerhead = new ModelRenderer(this, 4, 12);
        hammerhead.addBox(-1F, -1F, -1F, 3, 2, 2);
        hammerhead.setRotationPoint(-2F, 13F, 2F);
        hammerhead.setTextureSize(64, 64);
        hammerhead.mirror = true;
        setRotation(hammerhead, 0F, 0F, 0F);
        plank = new ModelRenderer(this, 0, 51);
        plank.addBox(-5F, -1F, -1F, 10, 1, 2);
        plank.setRotationPoint(-4F, 4F, -3F);
        plank.setTextureSize(64, 64);
        plank.mirror = true;
        setRotation(plank, 0F, 0.7679449F, 0F);
    }

    @Override
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
        top.render(f);
        FRleg.render(f);
        bottom.render(f);
        BRleg.render(f);
        BLleg.render(f);
        FLleg.render(f);
        plank2.render(f);
        hammerhandle.render(f);
        block.render(f);
        nail.render(f);
        vice.render(f);
        hammerhead.render(f);
        plank.render(f);
    }
}
