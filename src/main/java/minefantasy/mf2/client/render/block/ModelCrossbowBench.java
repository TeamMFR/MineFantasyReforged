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
public class ModelCrossbowBench extends ModelBase {
    // fields
    ModelRenderer top;
    ModelRenderer FRleg;
    ModelRenderer bottom;
    ModelRenderer BRleg;
    ModelRenderer BLleg;
    ModelRenderer FLleg;
    ModelRenderer shaft;
    ModelRenderer stock;
    ModelRenderer arms;
    ModelRenderer chest;

    public ModelCrossbowBench() {
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

        shaft = new ModelRenderer(this, 0, 56);
        shaft.addBox(-3F, -1F, -0.9666666F, 6, 1, 2);
        shaft.setRotationPoint(-2F, 3F, -2F);
        shaft.setTextureSize(64, 64);
        shaft.mirror = true;
        setRotation(shaft, 0F, 0F, 0F);

        stock = new ModelRenderer(this, 0, 59);
        stock.addBox(-3F, -2F, -1F, 6, 3, 2);
        stock.setRotationPoint(4F, 3F, -2F);
        stock.setTextureSize(64, 64);
        stock.mirror = true;
        setRotation(stock, 0F, 0F, 0F);

        arms = new ModelRenderer(this, 0, 54);
        arms.addBox(-5F, 0F, 0F, 10, 1, 1);
        arms.setRotationPoint(-5F, 1F, -2F);
        arms.setTextureSize(64, 64);
        arms.mirror = true;
        setRotation(arms, 0F, 1.570796F, 0F);

        chest = new ModelRenderer(this, 16, 35);
        chest.addBox(-3F, 0F, -2F, 6, 3, 4);
        chest.setRotationPoint(4F, 1F, 5F);
        chest.setTextureSize(64, 64);
        chest.mirror = true;
        setRotation(chest, 0F, 0.3316126F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        renderModel(f5);
    }

    public void renderModel(float f) {
        top.render(f);
        bottom.render(f);
        FLleg.render(f);
        BLleg.render(f);
        FRleg.render(f);
        BRleg.render(f);
        shaft.render(f);
        stock.render(f);
        arms.render(f);
        chest.render(f);

    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
