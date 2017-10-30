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
public class ModelResearch extends ModelBase {
    // fields
    ModelRenderer top;
    ModelRenderer FRleg;
    ModelRenderer bottom;
    ModelRenderer BRleg;
    ModelRenderer BLleg;
    ModelRenderer FLleg;
    ModelRenderer papers2;
    ModelRenderer ink;
    ModelRenderer book;
    ModelRenderer papers;

    public ModelResearch() {
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
        papers = new ModelRenderer(this, 0, 51);
        papers.addBox(-3F, -1F, -4F, 6, 0, 8);
        papers.setRotationPoint(0F, 4.5F, -2F);
        papers.setTextureSize(64, 64);
        papers.mirror = true;
        setRotation(papers, 0F, 0.296706F, 0F);
        ink = new ModelRenderer(this, 0, 0);
        ink.addBox(-1F, -1F, -1F, 2, 2, 2);
        ink.setRotationPoint(5F, 3F, 4F);
        ink.setTextureSize(64, 64);
        ink.mirror = true;
        setRotation(ink, 0F, 0F, 0F);
        book = new ModelRenderer(this, 16, 35);
        book.addBox(-3F, -1F, -4F, 6, 3, 8);
        book.setRotationPoint(-4F, 2F, 4F);
        book.setTextureSize(64, 64);
        book.mirror = true;
        setRotation(book, 0F, -0.1396263F, 0F);
        papers2 = new ModelRenderer(this, 0, 51);
        papers2.addBox(-3F, -1F, -4F, 6, 1, 8);
        papers2.setRotationPoint(0F, 14F, 0F);
        papers2.setTextureSize(64, 64);
        papers2.mirror = true;
        setRotation(papers2, 0F, 0.6283185F, 0F);
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
        papers.render(f);
        papers2.render(f);
        ink.render(f);
        book.render(f);

    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
