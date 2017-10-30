package minefantasy.mf2.client.render.mob;

import minefantasy.mf2.entity.mob.EntityMinotaur;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelMinotaur extends ModelBiped {
    ModelRenderer Nose;
    ModelRenderer Mane;
    ModelRenderer bipedLeftForearm;
    ModelRenderer tailEnd;
    ModelRenderer bipedRightThigh;
    ModelRenderer Lhorn1;
    ModelRenderer Lhorn2;
    ModelRenderer Rhorn1;
    ModelRenderer Rhorn2;
    ModelRenderer bipedLeftThigh;
    ModelRenderer bipedLeftFoot;
    ModelRenderer bipedRightFoot;
    ModelRenderer tail;
    ModelRenderer bipedRightForearm;
    ModelRenderer shoulders;
    ModelRenderer trapezius;

    ModelRenderer helmet;
    ModelRenderer armour;
    ModelRenderer warlordhelm;

    public ModelMinotaur() {
        // AXIAL
        textureWidth = 128;
        textureHeight = 128;

        bipedHead = new ModelRenderer(this, 0, 0);
        bipedHead.addBox(-2F, -3F, -5F, 4, 6, 6);
        bipedHead.setRotationPoint(0F, -9F, -2F);
        bipedHead.setTextureSize(128, 128);

        Nose = new ModelRenderer(this, 0, 54);
        Nose.addBox(-2F, -1F, -9F, 4, 4, 4);
        Nose.setRotationPoint(0F, -9F, -2F);
        Nose.setTextureSize(128, 128);

        Mane = new ModelRenderer(this, 0, 88);
        Mane.addBox(-3F, -3F, -2F, 6, 12, 8);
        Mane.setRotationPoint(0F, -8F, 1F);
        Mane.setTextureSize(128, 128);

        tailEnd = new ModelRenderer(this, 64, 0);
        tailEnd.addBox(-1F, 4F, 10F, 2, 2, 11);
        tailEnd.setRotationPoint(0F, 4F, 0F);
        tailEnd.setTextureSize(128, 128);
        setRotation(tailEnd, -0.5363927F, 0F, 0F);

        bipedBody = new ModelRenderer(this, 0, 12);
        bipedBody.addBox(-5F, 0F, -5F, 10, 14, 9);
        bipedBody.setRotationPoint(0F, -8F, 1F);
        bipedBody.setTextureSize(128, 128);

        tail = new ModelRenderer(this, 64, 17);
        tail.addBox(-2F, -3F, 2F, 4, 3, 11);
        tail.setRotationPoint(0F, 4F, 0F);
        tail.setTextureSize(128, 128);
        setRotation(tail, -1.182165F, 0F, 0F);

        shoulders = new ModelRenderer(this, 86, 50);
        shoulders.addBox(-7.5F, -1F, -4F, 15, 7, 6);
        shoulders.setRotationPoint(0F, -8F, 1F);
        shoulders.setTextureSize(128, 128);

        trapezius = new ModelRenderer(this, 0, 64);
        trapezius.addBox(-4F, -2F, -4F, 8, 13, 9);
        trapezius.setRotationPoint(0F, -8F, 1F);
        trapezius.setTextureSize(128, 128);

        // APPENDICULAR - LEFT
        Lhorn1 = new ModelRenderer(this, 20, 5);
        Lhorn1.mirror = true;
        Lhorn1.addBox(2F, -2F, -2F, 5, 2, 2);
        Lhorn1.setRotationPoint(0F, -9F, -2F);
        Lhorn1.setTextureSize(128, 128);

        Lhorn2 = new ModelRenderer(this, 20, 0);
        Lhorn2.mirror = true;
        Lhorn2.addBox(6F, -6F, -2F, 1, 4, 1);
        Lhorn2.setRotationPoint(0F, -9F, -2F);
        Lhorn2.setTextureSize(128, 128);

        bipedLeftArm = new ModelRenderer(this, 38, 33);
        bipedLeftArm.mirror = true;
        bipedLeftArm.addBox(-1F, -2F, -3F, 5, 10, 7);
        bipedLeftArm.setRotationPoint(7F, -6F, 1F);
        bipedLeftArm.setTextureSize(128, 128);

        bipedLeftForearm = new ModelRenderer(this, 62, 33);
        bipedLeftForearm.mirror = true;
        bipedLeftForearm.addBox(0F, 8F, -1F, 4, 10, 4);
        bipedLeftForearm.setRotationPoint(7F, -6F, 1F);
        bipedLeftForearm.setTextureSize(128, 128);

        bipedLeftThigh = new ModelRenderer(this, 38, 0);
        bipedLeftThigh.mirror = true;
        bipedLeftThigh.addBox(-2F, 0F, -3F, 5, 10, 8);
        bipedLeftThigh.setRotationPoint(3F, 4F, 0F);
        bipedLeftThigh.setTextureSize(128, 128);

        bipedLeftLeg = new ModelRenderer(this, 20, 50);
        bipedLeftLeg.mirror = true;
        bipedLeftLeg.addBox(-1F, 10F, 0F, 4, 6, 5);
        bipedLeftLeg.setRotationPoint(3F, 4F, 0F);
        bipedLeftLeg.setTextureSize(128, 128);

        bipedLeftFoot = new ModelRenderer(this, 20, 38);
        bipedLeftFoot.mirror = true;
        bipedLeftFoot.addBox(-1F, 13F, -2F, 4, 7, 5);
        bipedLeftFoot.setRotationPoint(3F, 4F, 0F);
        bipedLeftFoot.setTextureSize(128, 128);

        // APPENDICULAR - RIGHT
        Rhorn1 = new ModelRenderer(this, 20, 5);
        Rhorn1.addBox(-7F, -2F, -2F, 5, 2, 2);
        Rhorn1.setRotationPoint(0F, -9F, -2F);
        Rhorn1.setTextureSize(128, 128);

        Rhorn2 = new ModelRenderer(this, 20, 0);
        Rhorn2.addBox(-7F, -6F, -2F, 1, 4, 1);
        Rhorn2.setRotationPoint(0F, -9F, -2F);
        Rhorn2.setTextureSize(128, 128);

        bipedRightArm = new ModelRenderer(this, 38, 33);
        bipedRightArm.addBox(-4F, -2F, -3F, 5, 10, 7);
        bipedRightArm.setRotationPoint(-7F, -6F, 1F);
        bipedRightArm.setTextureSize(128, 128);

        bipedRightForearm = new ModelRenderer(this, 62, 33);
        bipedRightForearm.addBox(-4F, 8F, -1F, 4, 10, 4);
        bipedRightForearm.setRotationPoint(-7F, -6F, 1F);
        bipedRightForearm.setTextureSize(128, 128);

        bipedRightThigh = new ModelRenderer(this, 38, 0);
        bipedRightThigh.addBox(-3F, 0F, -3F, 5, 10, 8);
        bipedRightThigh.setRotationPoint(-3F, 4F, 0F);
        bipedRightThigh.setTextureSize(128, 128);

        bipedRightLeg = new ModelRenderer(this, 20, 50);
        bipedRightLeg.addBox(-3F, 10F, 0F, 4, 6, 5);
        bipedRightLeg.setRotationPoint(-3F, 4F, 0F);
        bipedRightLeg.setTextureSize(128, 128);

        bipedRightFoot = new ModelRenderer(this, 20, 38);
        bipedRightFoot.addBox(-3F, 13F, -2F, 4, 7, 5);
        bipedRightFoot.setRotationPoint(-3F, 4F, 0F);
        bipedRightFoot.setTextureSize(128, 128);

        // ARMOUR
        helmet = new ModelRenderer(this, 74, 98);
        helmet.addBox(-2.5F, -3.5F, -5.5F, 5, 6, 6);
        helmet.setRotationPoint(0F, -9F, -2F);

        armour = new ModelRenderer(this, 86, 103);
        armour.setTextureOffset(86, 103).addBox(-5.5F, -0.5F, -5.5F, 11, 15, 10);// chest
        armour.setTextureOffset(90, 77).addBox(-4.5F, -4.5F, -2.5F, 9, 16, 10);// back
        armour.setTextureOffset(34, 110).addBox(-8F, -1.5F, -5F, 16, 8, 10);// shoulders
        armour.setRotationPoint(0F, -8F, 1F);

        warlordhelm = new ModelRenderer(this, 46, 97);
        warlordhelm.addBox(-3.5F, -2F, -10F, 7, 6, 7);
        warlordhelm.setRotationPoint(-32F, -9F, -2F);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        renderMinotaur((EntityMinotaur) entity, f, f1, f2, f3, f4, f5);
    }

    public void renderMinotaur(EntityMinotaur mino, float f, float f1, float f2, float f3, float f4, float f5) {
        setRotationAngles(mino, f, f1, f2, f3, f4, f5);
        Nose.render(f5);
        tailEnd.render(f5);
        bipedLeftFoot.render(f5);
        bipedRightFoot.render(f5);
        shoulders.render(f5);
        bipedLeftThigh.render(f5);
        bipedRightThigh.render(f5);
        tail.render(f5);
        Rhorn1.render(f5);
        Rhorn2.render(f5);
        Lhorn1.render(f5);
        if (!mino.isBloodied()) {
            Lhorn2.render(f5);
        }
        bipedHead.render(f5);
        bipedLeftLeg.render(f5);
        bipedRightLeg.render(f5);
        bipedBody.render(f5);
        bipedRightArm.render(f5);
        bipedLeftArm.render(f5);
        bipedRightForearm.render(f5);
        bipedLeftForearm.render(f5);
        bipedRightLeg.render(f5);
        bipedLeftLeg.render(f5);
        Mane.render(f5);
        trapezius.render(f5);

        armour.render(f5);
        helmet.render(f5);
        warlordhelm.render(f5);
    }

    public void setRotationAngles(EntityMinotaur mino, float f, float f1, float f2, float f3, float f4, float f5) {
        int swingAngle = 28 * mino.swing;
        double swingRad = Math.toRadians(swingAngle);

        int swingAngleY = 9 * mino.swing;
        double swingRadY = Math.toRadians(swingAngleY);
        bipedHead.rotateAngleY = f3 / 57.29578F;
        bipedHead.rotateAngleX = f4 / 57.29578F + (float) Math.toRadians(mino.getHeadChargeAngle());
        if (mino.swing <= 0) {
            bipedRightArm.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 2.0F * f1 * 0.5F;
            bipedLeftArm.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
            bipedRightArm.rotateAngleY = 0.0F;
            bipedLeftArm.rotateAngleY = 0.0F;
        } else {
            bipedRightArm.rotateAngleX = (float) -swingRad;
            bipedLeftArm.rotateAngleX = (float) -swingRad;
            bipedRightArm.rotateAngleY = (float) swingRadY;
            bipedLeftArm.rotateAngleY = (float) -swingRadY;
        }
        bipedRightArm.rotateAngleZ = 0.0F;
        bipedLeftArm.rotateAngleZ = 0.0F;
        bipedRightLeg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        bipedLeftLeg.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
        bipedRightLeg.rotateAngleY = 0.0F;
        bipedLeftLeg.rotateAngleY = 0.0F;

        tail.rotateAngleX = -1.182165F;
        tailEnd.rotateAngleX = -0.5363927F;
        Nose.rotateAngleX = bipedHead.rotateAngleX;
        Nose.rotateAngleY = bipedHead.rotateAngleY;
        Rhorn1.rotateAngleX = bipedHead.rotateAngleX;
        Rhorn1.rotateAngleY = bipedHead.rotateAngleY;
        Rhorn2.rotateAngleX = bipedHead.rotateAngleX;
        Rhorn2.rotateAngleY = bipedHead.rotateAngleY;
        Lhorn1.rotateAngleX = bipedHead.rotateAngleX;
        Lhorn1.rotateAngleY = bipedHead.rotateAngleY;
        Lhorn2.rotateAngleX = bipedHead.rotateAngleX;
        Lhorn2.rotateAngleY = bipedHead.rotateAngleY;

        if (onGround > -9990F) {
            float f6 = onGround;
            bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f6) * 3.141593F * 2.0F) * 0.2F;
            bipedRightArm.rotationPointZ = MathHelper.sin(bipedBody.rotateAngleY) * 5F;
            bipedRightArm.rotationPointX = -MathHelper.cos(bipedBody.rotateAngleY) * 5F;
            bipedLeftArm.rotationPointZ = -MathHelper.sin(bipedBody.rotateAngleY) * 5F;
            bipedLeftArm.rotationPointX = MathHelper.cos(bipedBody.rotateAngleY) * 5F;
            bipedRightArm.rotateAngleY += bipedBody.rotateAngleY;
            bipedLeftArm.rotateAngleY += bipedBody.rotateAngleY;
            bipedLeftArm.rotateAngleX += bipedBody.rotateAngleY;
            f6 = 1.0F - onGround;
            f6 *= f6;
            f6 *= f6;
            f6 = 1.0F - f6;
            float f8 = MathHelper.sin(f6 * 3.141593F);
            float f10 = MathHelper.sin(onGround * 3.141593F) * -(bipedHead.rotateAngleX - 0.7F) * 0.75F;
            bipedRightArm.rotateAngleX -= f8 * 1.2D + f10;
            bipedRightArm.rotateAngleY += bipedBody.rotateAngleY * 2.0F;
            bipedRightArm.rotateAngleZ = MathHelper.sin(onGround * 3.141593F) * -0.4F;
        }
        if (mino.riddenByEntity != null) {
            bipedLeftArm.rotateAngleX = (float) Math.toRadians(180F);
        }
        if (mino.getAttack() == 3 && mino.getHeldItem() == null)// if is power attack
        {
            bipedLeftArm.rotateAngleX = (float) Math.toRadians(-135F);
            bipedRightArm.rotateAngleX = (float) Math.toRadians(-135F);
        }

        bipedRightArm.rotateAngleZ += MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
        bipedLeftArm.rotateAngleZ -= MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
        bipedRightArm.rotateAngleX += MathHelper.sin(f2 * 0.067F) * 0.05F;
        bipedLeftArm.rotateAngleX -= MathHelper.sin(f2 * 0.067F) * 0.05F;

        bipedLeftThigh.rotateAngleX = bipedLeftFoot.rotateAngleX = bipedLeftLeg.rotateAngleX;
        bipedRightThigh.rotateAngleX = bipedRightFoot.rotateAngleX = bipedRightLeg.rotateAngleX;

        joinBlocks(Mane, bipedBody);
        joinBlocks(trapezius, bipedBody);
        joinBlocks2(bipedLeftForearm, bipedLeftArm);
        joinBlocks2(bipedRightForearm, bipedRightArm);

        joinBlocks(armour, bipedBody);
        joinBlocks2(helmet, bipedHead);
        joinBlocks2(warlordhelm, bipedHead);
    }

    private void joinBlocks(ModelRenderer model, ModelRenderer anchor) {
        joinBlocks(model, anchor, 1.0F);
    }

    private void joinBlocks2(ModelRenderer model, ModelRenderer anchor) {
        model.rotateAngleX = anchor.rotateAngleX;
        model.rotateAngleY = anchor.rotateAngleY;
        model.rotateAngleZ = anchor.rotateAngleZ;

        model.rotationPointX = anchor.rotationPointX;
        model.rotationPointY = anchor.rotationPointY;
        model.rotationPointZ = anchor.rotationPointZ;
    }

    private void joinBlocks(ModelRenderer model, ModelRenderer anchor, float ratio) {
        model.rotateAngleX = anchor.rotateAngleX * ratio;
        model.rotateAngleY = anchor.rotateAngleY * ratio;
        model.rotateAngleZ = anchor.rotateAngleZ * ratio;
    }
}
