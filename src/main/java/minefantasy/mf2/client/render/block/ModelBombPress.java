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
public class ModelBombPress extends ModelBase {
    ModelRenderer frame3;
    ModelRenderer socket;
    ModelRenderer frame2;
    ModelRenderer Leg2;
    ModelRenderer frame4;
    ModelRenderer handle;
    ModelRenderer frame1;
    ModelRenderer body;
    ModelRenderer Leg1;
    ModelRenderer Lever;

    public ModelBombPress() {
        textureWidth = 64;
        textureHeight = 32;

        frame4 = new ModelRenderer(this, 0, 0);
        frame4.addBox(-8F, 2F, 7F, 16, 2, 1);
        frame4.setRotationPoint(0F, 0F, 0F);
        frame4.setTextureSize(64, 32);
        setRotation(frame4, 0F, 1.570796F, 0F);
        socket = new ModelRenderer(this, 0, 21);
        socket.addBox(-1F, 5F, -1F, 2, 3, 2);
        socket.setRotationPoint(0F, 0F, 0F);
        socket.setTextureSize(64, 32);
        setRotation(socket, 0F, 0F, 0F);
        frame2 = new ModelRenderer(this, 0, 0);
        frame2.addBox(-8F, 0F, -6F, 16, 2, 1);
        frame2.setRotationPoint(0F, 0F, 0F);
        frame2.setTextureSize(64, 32);
        setRotation(frame2, 0F, 0F, 0F);
        Leg2 = new ModelRenderer(this, 0, 3);
        Leg2.addBox(-7F, 2F, 3F, 3, 14, 4);
        Leg2.setRotationPoint(0F, 0F, 0F);
        Leg2.setTextureSize(64, 32);
        setRotation(Leg2, 0F, 0F, 0F);
        frame3 = new ModelRenderer(this, 0, 0);
        frame3.addBox(-8F, 2F, -8F, 16, 2, 1);
        frame3.setRotationPoint(0F, 0F, 0F);
        frame3.setTextureSize(64, 32);
        setRotation(frame3, 0F, 1.570796F, 0F);
        handle = new ModelRenderer(this, 32, 16);
        handle.addBox(-0.5F, 9.5F, -1.5F, 2, 3, 2);
        handle.setRotationPoint(7F, 3F, 6F);
        handle.setTextureSize(64, 32);
        setRotation(handle, -1.308997F, 0F, 0F);
        frame1 = new ModelRenderer(this, 0, 0);
        frame1.addBox(-8F, 0F, 5F, 16, 2, 1);
        frame1.setRotationPoint(0F, 0F, 0F);
        frame1.setTextureSize(64, 32);
        setRotation(frame1, 0F, 0F, 0F);
        body = new ModelRenderer(this, 14, 3);
        body.addBox(-2F, -5F, -5F, 4, 10, 5);
        body.setRotationPoint(0F, 0F, 0F);
        body.setTextureSize(64, 32);
        setRotation(body, 1.570796F, 0F, 0F);
        Leg1 = new ModelRenderer(this, 0, 3);
        Leg1.addBox(4F, 2F, 3F, 3, 14, 4);
        Leg1.setRotationPoint(0F, 0F, 0F);
        Leg1.setTextureSize(64, 32);
        setRotation(Leg1, 0F, 0F, 0F);
        Leg1.mirror = false;
        Lever = new ModelRenderer(this, 32, 3);
        Lever.addBox(0F, 0F, -1F, 1, 12, 1);
        Lever.setRotationPoint(7F, 3F, 6F);
        Lever.setTextureSize(64, 32);
        setRotation(Lever, -1.308997F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        renderModel(f5, 0F);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void renderModel(float f, float f2) {
        Lever.rotateAngleX = handle.rotateAngleX = -0.75F + (0.55F * f2);
        frame1.rotationPointY = frame2.rotationPointY = frame3.rotationPointY = frame4.rotationPointY = socket.rotationPointY = body.rotationPointY = (12F
                * f2);
        frame3.render(f);
        socket.render(f);
        frame2.render(f);
        Leg2.render(f);
        frame4.render(f);
        handle.render(f);
        frame1.render(f);
        body.render(f);
        Leg1.render(f);
        Lever.render(f);
    }
}
