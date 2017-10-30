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
public class ModelBombBench extends ModelBase {
    // fields
    ModelRenderer top;
    ModelRenderer FRleg;
    ModelRenderer bottom;
    ModelRenderer BRleg;
    ModelRenderer BLleg;
    ModelRenderer FLleg;
    ModelRenderer box;
    ModelRenderer casing;
    ModelRenderer powder;
    ModelRenderer bomb;
    ModelRenderer fuse;

    public ModelBombBench() {
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
        box = new ModelRenderer(this, 0, 51);
        box.addBox(-3F, -1F, -2F, 6, 3, 4);
        box.setRotationPoint(0F, 12F, 0F);
        box.setTextureSize(64, 64);
        box.mirror = true;
        setRotation(box, 0F, 0.1396263F, 0F);
        casing = new ModelRenderer(this, 0, 20);
        casing.addBox(-1F, -1F, -1F, 3, 2, 3);
        casing.setRotationPoint(-2F, 3F, -5F);
        casing.setTextureSize(64, 64);
        casing.mirror = true;
        setRotation(casing, 0F, -0.418879F, 0F);
        powder = new ModelRenderer(this, 0, 0);
        powder.addBox(-2F, -2F, -2F, 4, 4, 4);
        powder.setRotationPoint(-2F, 4.5F, 0F);
        powder.setTextureSize(64, 64);
        powder.mirror = true;
        setRotation(powder, 0.7853982F, 0.7853982F, 0.6632251F);
        bomb = new ModelRenderer(this, 0, 8);
        bomb.addBox(-1F, -1F, -1F, 3, 3, 3);
        bomb.setRotationPoint(2F, 2F, -1F);
        bomb.setTextureSize(64, 64);
        bomb.mirror = true;
        setRotation(bomb, 0F, 0.1396263F, 0F);
        fuse = new ModelRenderer(this, 6, 2);
        fuse.addBox(1F, -2F, 0F, 2, 3, 1);
        fuse.setRotationPoint(2F, 2F, -1F);
        fuse.setTextureSize(64, 64);
        fuse.mirror = true;
        setRotation(fuse, 0F, 0.1396263F, 0F);
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
        box.render(f);
        casing.render(f);
        powder.render(f);
        bomb.render(f);
        fuse.render(f);
    }
}
