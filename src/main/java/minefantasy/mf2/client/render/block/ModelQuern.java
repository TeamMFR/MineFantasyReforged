package minefantasy.mf2.client.render.block;

import minefantasy.mf2.block.tileentity.TileEntityQuern;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * @author Anonymous Productions
 * <p>
 * Sources are provided for educational reasons. though small bits of
 * code, or methods can be used in your own creations.
 */
public class ModelQuern extends ModelBase {
    ModelRenderer base;
    ModelRenderer top;
    ModelRenderer handle;
    ModelRenderer core;

    public ModelQuern() {
        textureWidth = 64;
        textureHeight = 64;

        base = new ModelRenderer(this, 0, 0);
        base.addBox(-8F, 3F, -8F, 16, 13, 16);
        base.setRotationPoint(0F, 0F, 0F);
        base.setTextureSize(64, 32);

        top = new ModelRenderer(this, 0, 29);
        top.addBox(-6F, 2F, -6F, 12, 1, 12);
        top.setRotationPoint(0F, 0F, 0F);
        top.setTextureSize(64, 32);

        handle = new ModelRenderer(this, 48, 29);
        handle.addBox(3F, 0F, -5F, 2, 2, 2);
        handle.setRotationPoint(0F, 0F, 0F);
        handle.setTextureSize(64, 32);

        core = new ModelRenderer(this, 48, 29);
        core.addBox(-1, 0F, -1F, 2, 2, 2);
        core.setRotationPoint(0F, 0F, 0F);
        core.setTextureSize(64, 32);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
    }

    public void renderModel(TileEntityQuern quern, float f) {
        if (quern != null) {
            top.rotateAngleY = handle.rotateAngleY = (float) (Math.PI * quern.turnAngle) / TileEntityQuern.getMaxRevs()
                    * 2;
        }

        base.render(f);
        top.render(f);
        core.render(f);
        handle.render(f);
    }

}