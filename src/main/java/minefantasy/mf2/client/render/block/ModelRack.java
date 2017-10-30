package minefantasy.mf2.client.render.block;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelRack extends ModelBase {
    // fields
    ModelRenderer rStrut;
    ModelRenderer beam;
    ModelRenderer rack1;
    ModelRenderer lStrut;
    ModelRenderer divider3;
    ModelRenderer divider1;
    ModelRenderer dividerR;
    ModelRenderer divider2;
    ModelRenderer dividerL;

    public ModelRack() {
        textureWidth = 64;
        textureHeight = 32;

        rStrut = new ModelRenderer(this, 0, 0);
        rStrut.addBox(7F, 0F, 6F, 1, 16, 2);
        rStrut.setRotationPoint(0F, 0F, 0F);

        beam = new ModelRenderer(this, 0, 29);
        beam.addBox(-8F, 13F, 5F, 16, 1, 1);
        beam.setRotationPoint(0F, 0F, 0F);

        rack1 = new ModelRenderer(this, 0, 29);
        rack1.addBox(-8F, 3F, 5F, 16, 2, 1);
        rack1.setRotationPoint(0F, 0F, 0F);

        lStrut = new ModelRenderer(this, 0, 0);
        lStrut.addBox(-8F, 0F, 6F, 1, 16, 2);
        lStrut.setRotationPoint(0F, 0F, 0F);

        divider3 = new ModelRenderer(this, 0, 29);
        divider3.addBox(3F, 4F, 3F, 2, 1, 2);
        divider3.setRotationPoint(0F, 0F, 0F);

        divider1 = new ModelRenderer(this, 0, 29);
        divider1.addBox(-1F, 4F, 3F, 2, 1, 2);
        divider1.setRotationPoint(0F, 0F, 0F);

        dividerR = new ModelRenderer(this, 1, 29);
        dividerR.addBox(7F, 4F, 3F, 1, 1, 2);
        dividerR.setRotationPoint(0F, 0F, 0F);

        divider2 = new ModelRenderer(this, 0, 29);
        divider2.addBox(-5F, 4F, 3F, 2, 1, 2);
        divider2.setRotationPoint(0F, 0F, 0F);

        dividerL = new ModelRenderer(this, 0, 29);
        dividerL.addBox(-8F, 4F, 3F, 1, 1, 2);
        dividerL.setRotationPoint(0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        rStrut.render(f5);
        beam.render(f5);
        rack1.render(f5);
        lStrut.render(f5);
        divider3.render(f5);
        divider1.render(f5);
        dividerR.render(f5);
        divider2.render(f5);
        dividerL.render(f5);
    }

    public void renderModel(float scale) {
        rStrut.render(scale);
        beam.render(scale);
        rack1.render(scale);
        lStrut.render(scale);
        divider3.render(scale);
        divider1.render(scale);
        dividerR.render(scale);
        divider2.render(scale);
        dividerL.render(scale);
    }

}