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
public class ModelAnvil extends ModelBase {
    // fields
    ModelRenderer End;
    ModelRenderer Post;
    ModelRenderer Body;
    ModelRenderer Tip;
    ModelRenderer Back;
    ModelRenderer floor;
    ModelRenderer Base;

    public ModelAnvil() {
        textureWidth = 64;
        textureHeight = 32;

        End = new ModelRenderer(this, 0, 24);
        End.addBox(-10F, -3F, -2F, 3, 4, 4);
        End.setRotationPoint(0F, -5F, 0F);
        End.setTextureSize(64, 32);
        End.mirror = true;
        setRotation(End, 0F, 0F, 0F);
        Post = new ModelRenderer(this, 48, 24);
        Post.addBox(-2F, 0F, -2F, 4, 4, 4);
        Post.setRotationPoint(0F, -4F, 0F);
        Post.setTextureSize(64, 32);
        Post.mirror = true;
        setRotation(Post, 0F, 0F, 0F);
        Body = new ModelRenderer(this, 32, 0);
        Body.addBox(-5F, -3F, -3F, 10, 4, 6);
        Body.setRotationPoint(0F, -5F, 0F);
        Body.setTextureSize(64, 32);
        Body.mirror = true;
        setRotation(Body, 0F, 0F, 0F);
        Tip = new ModelRenderer(this, 0, 10);
        Tip.addBox(5F, -2F, -1F, 3, 2, 2);
        Tip.setRotationPoint(0F, -5F, 0F);
        Tip.setTextureSize(64, 32);
        Tip.mirror = true;
        setRotation(Tip, 0F, 0F, 0F);
        Back = new ModelRenderer(this, 0, 19);
        Back.addBox(-7F, -2F, -1F, 2, 3, 2);
        Back.setRotationPoint(0F, -5F, 0F);
        Back.setTextureSize(64, 32);
        Back.mirror = true;
        setRotation(Back, 0F, 0F, 0F);
        Base = new ModelRenderer(this, 0, 0);
        Base.addBox(-3F, 0F, -3F, 6, 2, 6);
        Base.setRotationPoint(0F, 0F, 0F);
        Base.setTextureSize(64, 32);
        Base.mirror = true;
        setRotation(Base, 0F, 0F, 0F);
        floor = new ModelRenderer(this, 14, 21);
        floor.addBox(-4F, 2F, -4F, 8, 3, 8);
        floor.setRotationPoint(0F, 0F, 0F);
        floor.setTextureSize(64, 32);
        floor.mirror = true;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        End.render(f5);
        Base.render(f5);
        Post.render(f5);
        Body.render(f5);
        Tip.render(f5);
        Back.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void renderModel(float f) {
        End.render(f);
        Base.render(f);
        Post.render(f);
        Body.render(f);
        Tip.render(f);
        Back.render(f);
        floor.render(f);
    }
}
