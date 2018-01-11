package minefantasy.mf2.client.render.armour;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;

public class ModelFullplate extends ModelBiped {
    ModelRenderer rightPauldron;
    ModelRenderer leftPauldron;

    ModelRenderer armetridge;
    ModelRenderer armetjaw;
    ModelRenderer armetface;

    ModelRenderer bascinetRhorn;
    ModelRenderer bascinetLhorn;
    ModelRenderer bascinetface;

    public ModelFullplate(float f) {
        super(f, 0F, 64, 64);

        rightPauldron = new ModelRenderer(this, 40, 32);
        rightPauldron.addBox(-3.5F, -3.5F, -2.5F, 5, 5, 5, f);

        rightPauldron.setTextureSize(64, 64);
        setRotation(rightPauldron, 0F, 0F, -0.418879F);

        leftPauldron = new ModelRenderer(this, 40, 32);
        leftPauldron.mirror = true;
        leftPauldron.addBox(-1.5F, -3.5F, -2.5F, 5, 5, 5, f);

        leftPauldron.setTextureSize(64, 64);
        setRotation(leftPauldron, 0F, 0F, 0.418879F);

        armetridge = new ModelRenderer(this, 0, 38);
        armetridge.addBox(-1F, -8.5F, -3.5F, 2, 8, 8);
        armetridge.setRotationPoint(0F, 0F, 0F);

        armetjaw = new ModelRenderer(this, 0, 32);
        armetjaw.addBox(-3.5F, -5F, -4F, 7, 4, 2);
        armetjaw.setRotationPoint(0F, 0F, 0F);
        setRotation(armetjaw, 0.3316126F, 0F, 0F);

        armetface = new ModelRenderer(this, 20, 32);
        armetface.addBox(-2.5F, -2.5F, -2.6F, 5, 4, 5);
        armetface.setRotationPoint(0F, -1F, -5F);
        setRotation(armetface, 0F, 0.7853982F, 0F);

        /*
         * armetface = new ModelRenderer(this, 20, 32); armetface.addBox(-2.5F, -2.5F,
         * -2.6F, 5, 4, 5); armetface.setRotationPoint(0F, -0.5F, -4F);
         * setRotation(armetface, 0.2617994F, 0.7853982F, 0.2617994F);
         */

        bascinetface = new ModelRenderer(this, 0, 54);
        bascinetface.addBox(-2.5F, -5F, -7F, 5, 4, 3);
        bascinetface.setRotationPoint(0F, 0F, 0F);
        setRotation(bascinetface, 0.3316126F, 0F, 0F);

        bascinetLhorn = new ModelRenderer(this, 0, 0);
        bascinetLhorn.mirror = true;
        bascinetLhorn.addBox(0F, -14F, -3F, 2, 6, 2);
        bascinetLhorn.setRotationPoint(0F, 0F, 0F);
        setRotation(bascinetLhorn, -0.7853982F, 0.4363323F, 0F);

        bascinetRhorn = new ModelRenderer(this, 0, 0);
        bascinetRhorn.addBox(-2F, -14F, -3F, 2, 6, 2);
        bascinetRhorn.setRotationPoint(0F, 0F, 0F);
        setRotation(bascinetRhorn, -0.7853982F, -0.4363323F, 0F);

        this.bipedRightArm.addChild(rightPauldron);
        this.bipedLeftArm.addChild(leftPauldron);

        this.bipedHead.addChild(armetridge);
        this.bipedHead.addChild(armetjaw);
        this.bipedHead.addChild(armetface);

        this.bipedHead.addChild(bascinetface);
        this.bipedHead.addChild(bascinetLhorn);
        this.bipedHead.addChild(bascinetRhorn);

        this.bipedLeftLeg.showModel = this.bipedRightLeg.showModel = this.bipedEars.showModel = false;
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
