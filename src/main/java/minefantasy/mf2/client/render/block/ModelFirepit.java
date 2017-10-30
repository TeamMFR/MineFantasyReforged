package minefantasy.mf2.client.render.block;

import minefantasy.mf2.block.tileentity.TileEntityFirepit;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * @author Anonymous Productions
 * <p>
 * Sources are provided for educational reasons. though small bits of
 * code, or methods can be used in your own creations.
 */
public class ModelFirepit extends ModelBase {
    // fields
    ModelRenderer Base;
    ModelRenderer Plank1;
    ModelRenderer Plank2;
    ModelRenderer Plank3;
    ModelRenderer Plank4;
    ModelRenderer Pile;

    public ModelFirepit() {
        textureWidth = 64;
        textureHeight = 32;

        Base = new ModelRenderer(this, 0, 15);
        Pile = new ModelRenderer(this, 0, 6);

        Plank1 = new ModelRenderer(this, 24, 0);
        Plank2 = new ModelRenderer(this, 24, 0);
        Plank3 = new ModelRenderer(this, 0, 0);
        Plank4 = new ModelRenderer(this, 0, 0);

        Base.addBox(-5F, 3F, -5F, 10, 3, 10);
        Base.setRotationPoint(0F, 0F, 0F);
        Base.setTextureSize(64, 32);
        Base.mirror = true;
        setRotation(Base, 0F, 0F, 0F);
        Plank2.addBox(-1.5F, -1.5F, -4.5F, 3, 3, 9);
        Plank2.setRotationPoint(0F, 3F, 5F);
        Plank2.setTextureSize(64, 32);
        Plank2.mirror = true;
        Plank1.addBox(-1.5F, -1.5F, -4.5F, 3, 3, 9);
        Plank1.setRotationPoint(0F, 3F, -5F);
        Plank1.setTextureSize(64, 32);
        Plank1.mirror = true;
        Plank3.addBox(-4.5F, -1.5F, -1.5F, 9, 3, 3);
        Plank3.setRotationPoint(-5F, 3F, 0F);
        Plank3.setTextureSize(64, 32);
        Plank3.mirror = true;
        Plank4.addBox(-4.5F, -1.5F, -1.5F, 9, 3, 3);
        Plank4.setRotationPoint(5F, 3F, 0F);
        Plank4.setTextureSize(64, 32);
        Plank4.mirror = true;
        Pile.addBox(-3F, 0F, -3F, 6, 3, 6);
        Pile.setRotationPoint(0F, 0F, 0F);
        Pile.setTextureSize(64, 32);
        Pile.mirror = true;
        setRotation(Pile, 0F, 0F, 0F);

        float angle = 0.7853982F;

        setRotation(Plank1, angle, 0F, 0F);
        setRotation(Plank2, -angle, 0F, 0F);
        setRotation(Plank3, 0F, 0F, -angle);
        setRotation(Plank4, 0F, 0F, angle);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
    }

    public void renderModel(TileEntityFirepit pit, float f) {
        Base.render(f);

        if (pit == null || pit.fuel > 0) {
            Plank1.render(f);
            Plank2.render(f);
            Plank3.render(f);
            Plank4.render(f);
            Pile.render(f);
        }
    }
}