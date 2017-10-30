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
public class ModelRoast extends ModelBase {
    ModelRenderer side2B;
    ModelRenderer side1B;
    ModelRenderer Top;
    ModelRenderer side2;
    ModelRenderer side1;

    public ModelRoast() {
        textureWidth = 64;
        textureHeight = 32;

        side2B = new ModelRenderer(this, 10, 4);
        side1B = new ModelRenderer(this, 10, 4);
        Top = new ModelRenderer(this, 0, 0);
        side2 = new ModelRenderer(this, 0, 4);
        side1 = new ModelRenderer(this, 0, 4);

        side2B.addBox(7F, 22F, -1.5F, 2, 17, 3);
        side2B.setRotationPoint(0F, 2F, 0F);
        side2B.setTextureSize(64, 32);
        side2B.mirror = true;
        side1B.mirror = true;
        side1B.addBox(-9F, 22F, -1.5F, 2, 17, 3);
        side1B.setRotationPoint(0F, 2F, 0F);
        side1B.setTextureSize(64, 32);
        side1B.mirror = true;
        side1B.mirror = false;
        Top.addBox(-8F, 12F, -1F, 16, 2, 2);
        Top.setRotationPoint(0F, 2F, 0F);
        Top.setTextureSize(64, 32);
        Top.mirror = true;
        side2.addBox(7F, 5F, -1.5F, 2, 17, 3);
        side2.setRotationPoint(0F, 2F, 0F);
        side2.setTextureSize(64, 32);
        side2.mirror = true;
        side1.addBox(-9F, 5F, -1.5F, 2, 17, 3);
        side1.setRotationPoint(0F, 2F, 0F);
        side1.setTextureSize(64, 32);
        side1.mirror = true;
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        Top.render(f5);
        side1.render(f5);
        side2.render(f5);
    }

    public void renderModel(float f5) {
        Top.render(f5);
        side1.render(f5);
        side2.render(f5);
    }

    public void renderModel(boolean L, boolean R, float scale, boolean base) {
        Top.render(scale);
        if (L)
            side1.render(scale);
        if (R)
            side2.render(scale);
        if (base) {
            if (L)
                side1B.render(scale);
            if (R)
                side2B.render(scale);
        }
    }
}