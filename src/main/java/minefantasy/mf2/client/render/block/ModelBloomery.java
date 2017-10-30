package minefantasy.mf2.client.render.block;

import minefantasy.mf2.block.tileentity.TileEntityBloomery;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * @author Anonymous Productions
 * <p>
 * Sources are provided for educational reasons. though small bits of
 * code, or methods can be used in your own creations.
 */
public class ModelBloomery extends ModelBase {
    // fields
    ModelRenderer top;
    ModelRenderer base;
    ModelRenderer neck;
    ModelRenderer body;
    ModelRenderer bloom;

    public ModelBloomery() {
        textureWidth = 128;
        textureHeight = 64;

        top = new ModelRenderer(this, 84, 43);
        top.addBox(-5.5F, 0F, -5.5F, 11, 2, 11);
        top.setRotationPoint(0F, 0F, 0F);
        top.setTextureSize(128, 64);

        base = new ModelRenderer(this, 0, 0);
        base.addBox(-9F, 14F, -9F, 18, 2, 18);
        base.setRotationPoint(0F, 0F, 0F);
        base.setTextureSize(128, 64);

        neck = new ModelRenderer(this, 0, 46);
        neck.addBox(-7F, 2F, -7F, 14, 3, 14);
        neck.setRotationPoint(0F, 0F, 0F);
        neck.setTextureSize(128, 64);

        body = new ModelRenderer(this, 64, 11);
        body.addBox(-8F, 5F, -8F, 16, 9, 16);
        body.setRotationPoint(0F, 0F, 0F);
        body.setTextureSize(128, 64);

        bloom = new ModelRenderer(this, 0, 20);
        bloom.addBox(-4F, -4F, -4F, 8, 8, 8);
        bloom.setRotationPoint(0F, 6F, 0F);
        bloom.setTextureSize(128, 64);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
    }

    public void renderModel(TileEntityBloomery bloomery, float f) {
        base.render(f);
        body.render(f);

        if (bloomery != null && bloomery.hasBloom) {
            bloom.render(f);
        } else {
            neck.render(f);
            top.render(f);
        }
    }
}