package minefantasy.mf2.client.render.mob;

import minefantasy.mf2.entity.mob.EntityHound;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class ModelHound extends ModelBase {

    /**
     * main box for the wolf head
     */
    public ModelRenderer WolfHead;
    /**
     * The wolf's body
     */
    public ModelRenderer Body;
    /**
     * Wolf'se first leg
     */
    public ModelRenderer Leg1;
    /**
     * Wolf's second leg
     */
    public ModelRenderer Leg2;
    /**
     * Wolf's third leg
     */
    public ModelRenderer Leg3;
    /**
     * Wolf's fourth leg
     */
    public ModelRenderer Leg4;
    /**
     * The wolf's tail
     */
    public ModelRenderer Tail;
    /**
     * The wolf's mane
     */
    public ModelRenderer Mane;
    public ModelRenderer Collar;
    public ModelRenderer Jaw;
    public ModelRenderer Ear1;
    public ModelRenderer Ear2;
    public ModelRenderer Nose;

    public ModelHound() {
        this(0F);
    }

    public ModelHound(float scale) {
        float var1 = 0F;
        float var2 = 13.5F;
        textureWidth = 64;
        textureHeight = 32;

        Collar = new ModelRenderer(this, 20, 24);
        Collar.addBox(-3.5F, -3.5F, 0.5F, 7, 7, 1, scale);
        Collar.setRotationPoint(-1F, 12.5F, -7F);
        Collar.setTextureSize(64, 32);
        Collar.mirror = true;
        setRotation(Collar, 0F, 0F, 0F);
        Body = new ModelRenderer(this, 38, 17);
        Body.addBox(-4F, -2F, -3F, 6, 8, 7, scale);
        Body.setRotationPoint(-1F, 15F, 3F);
        Body.setTextureSize(64, 32);
        Body.mirror = true;
        setRotation(Body, 1.570796F, 0F, 0F);
        Mane = new ModelRenderer(this, 32, 0);
        Mane.addBox(-4F, -3F, -4F, 8, 8, 8, scale);
        Mane.setRotationPoint(0F, 14F, -3F);
        Mane.setTextureSize(64, 32);
        Mane.mirror = true;
        setRotation(Mane, 1.570796F, 0F, 0F);
        Leg1 = new ModelRenderer(this, 0, 18);
        Leg1.addBox(-3F, 0F, -2F, 3, 8, 3, scale);
        Leg1.setRotationPoint(-1.5F, 16F, 7F);
        Leg1.setTextureSize(64, 32);
        Leg1.mirror = true;
        setRotation(Leg1, 0F, 0F, 0F);
        Leg2 = new ModelRenderer(this, 0, 18);
        Leg2.addBox(0F, 0F, -2F, 3, 8, 3, scale);
        Leg2.setRotationPoint(-0.5F, 16F, 7F);
        Leg2.setTextureSize(64, 32);
        Leg2.mirror = true;
        setRotation(Leg2, 0F, 0F, 0F);
        Leg3 = new ModelRenderer(this, 0, 18);
        Leg3.addBox(-3F, 0F, -1F, 3, 8, 3, scale);
        Leg3.setRotationPoint(-1.5F, 16F, -4F);
        Leg3.setTextureSize(64, 32);
        Leg3.mirror = true;
        setRotation(Leg3, 0F, 0F, 0F);
        Leg4 = new ModelRenderer(this, 0, 18);
        Leg4.addBox(0F, 0F, -1F, 3, 8, 3, scale);
        Leg4.setRotationPoint(-0.5F, 16F, -4F);
        Leg4.setTextureSize(64, 32);
        Leg4.mirror = true;
        setRotation(Leg4, 0F, 0F, 0F);
        Tail = new ModelRenderer(this, 12, 27);
        Tail.addBox(-1F, 0F, -1F, 2, 3, 2, scale);
        Tail.setRotationPoint(-1F, 13F, 8F);
        Tail.setTextureSize(64, 32);
        Tail.mirror = true;
        setRotation(Tail, 1.130069F, 0F, 0F);
        Ear1 = new ModelRenderer(this, 16, 11);
        Ear1.addBox(-3F, -6F, 0F, 2, 5, 1, scale);
        Ear1.setRotationPoint(-1F, 12.5F, -7F);
        Ear1.setTextureSize(64, 32);
        Ear1.mirror = true;
        setRotation(Ear1, 0F, 0F, 0F);
        Ear2 = new ModelRenderer(this, 16, 11);
        Ear2.mirror = true;
        Ear2.addBox(1F, -6F, 0F, 2, 5, 1, scale);
        Ear2.setRotationPoint(-1F, 12.5F, -7F);
        Ear2.setTextureSize(64, 32);
        Ear2.mirror = true;
        setRotation(Ear2, 0F, 0F, 0F);
        Ear2.mirror = false;
        Jaw = new ModelRenderer(this, 12, 17);
        Jaw.addBox(-1.5F, 2F, -7F, 3, 1, 4, scale);
        Jaw.setRotationPoint(-1F, 12.5F, -7F);
        Jaw.setTextureSize(64, 32);
        Jaw.mirror = true;
        setRotation(Jaw, 0F, 0F, 0F);
        Nose = new ModelRenderer(this, 0, 11);
        Nose.addBox(-1.5F, 0F, -7F, 3, 2, 4, scale);
        Nose.setRotationPoint(-1F, 12.5F, -7F);
        Nose.setTextureSize(64, 32);
        Nose.mirror = true;
        setRotation(Nose, 0F, 0F, 0F);
        WolfHead = new ModelRenderer(this, 0, 0);
        WolfHead.addBox(-3F, -3F, -3F, 6, 6, 5, scale);
        WolfHead.setRotationPoint(-1F, 12.5F, -7F);
        WolfHead.setTextureSize(64, 32);
        WolfHead.mirror = true;
        setRotation(WolfHead, 0F, 0F, 0F);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    @Override
    public void render(Entity entity, float f1, float f2, float f3, float f4, float f5, float f6) {
        super.render(entity, f1, f2, f3, f4, f5, f6);
        EntityHound var5 = (EntityHound) entity;
        this.setRotationAngles(var5, f1, f2, f3, f4, f5, f6);
        if (this.isChild) {
            float var8 = 2F;
            GL11.glPushMatrix();
            GL11.glTranslatef(0F, 5F * f6, 2F * f6);
            this.WolfHead.renderWithRotation(f6);
            this.Collar.renderWithRotation(f6);
            this.Ear1.renderWithRotation(f6);
            this.Ear2.renderWithRotation(f6);
            this.Jaw.renderWithRotation(f6);
            this.Nose.renderWithRotation(f6);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(1F / var8, 1F / var8, 1F / var8);
            GL11.glTranslatef(0F, 24F * f6, 0F);
            this.Body.render(f6);
            this.Leg1.render(f6);
            this.Leg2.render(f6);
            this.Leg3.render(f6);
            this.Leg4.render(f6);
            this.Tail.renderWithRotation(f6);
            this.Mane.render(f6);
            GL11.glPopMatrix();
        } else {
            this.WolfHead.renderWithRotation(f6);
            this.Collar.renderWithRotation(f6);
            this.Ear1.renderWithRotation(f6);
            this.Ear2.renderWithRotation(f6);
            this.Jaw.renderWithRotation(f6);
            this.Nose.renderWithRotation(f6);
            this.Body.render(f6);
            this.Leg1.render(f6);
            this.Leg2.render(f6);
            this.Leg3.render(f6);
            this.Leg4.render(f6);
            this.Tail.renderWithRotation(f6);
            this.Mane.render(f6);
        }
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third
     * float params here are the same second and third as in the setRotationAngles
     * method.
     */
    @Override
    public void setLivingAnimations(EntityLivingBase living, float step1, float step2, float z) {
        EntityHound var5 = (EntityHound) living;

        float walk1 = MathHelper.cos(step1 * 0.6662F + (float) Math.PI) * 1.4F * step2;
        float walk2 = MathHelper.cos(step1 * 0.6662F) * 1.4F * step2;

        if (var5.isAngry()) {
            this.Tail.rotateAngleY = 0F;
        } else {
            this.Tail.rotateAngleY = MathHelper.cos(step1 * 0.6662F) * 1.4F * step2;
        }

        if (var5.isSitting()) {
            float dig = 0;
            this.Mane.setRotationPoint(-1F, 16F, -2F);
            this.Mane.rotateAngleX = ((float) Math.PI * 2F / 5F);
            this.Mane.rotateAngleY = 0F;
            this.Body.setRotationPoint(0F, 18F, 0F);
            this.Body.rotateAngleX = ((float) Math.PI / 4F);
            this.Tail.setRotationPoint(-1F, 21F, 6F);
            this.Leg1.setRotationPoint(-2.5F, 22F, 2F);
            this.Leg1.rotateAngleX = ((float) Math.PI * 3F / 2F);
            this.Leg2.setRotationPoint(0.5F, 22F, 2F);
            this.Leg2.rotateAngleX = ((float) Math.PI * 3F / 2F);
            this.Leg3.rotateAngleX = 5.811947F + dig;
            this.Leg3.setRotationPoint(-2.49F, 17F, -4F);
            this.Leg4.rotateAngleX = 5.811947F - dig;
            this.Leg4.setRotationPoint(0.51F, 17F, -4F);
        } else // WALK
        {
            this.Body.setRotationPoint(0F, 14F, 2F);
            this.Body.rotateAngleX = ((float) Math.PI / 2F);
            this.Mane.setRotationPoint(-1F, 14F, -2F);
            this.Mane.rotateAngleX = this.Body.rotateAngleX;
            this.Tail.setRotationPoint(-1F, 12F, 8F);
            this.Leg1.setRotationPoint(-2.5F, 16F, 7F);
            this.Leg2.setRotationPoint(0.5F, 16F, 7F);
            this.Leg3.setRotationPoint(-2.5F, 16F, -4F);
            this.Leg4.setRotationPoint(0.5F, 16F, -4F);

            if (var5.isChild() || var5.isSprinting())// Alternate run
            {
                this.Leg1.rotateAngleX = walk1;
                this.Leg2.rotateAngleX = walk1;
                this.Leg3.rotateAngleX = walk2;
                this.Leg4.rotateAngleX = walk2;
            } else {
                this.Leg2.rotateAngleX = walk1;
                this.Leg3.rotateAngleX = walk1;
                this.Leg1.rotateAngleX = walk2;
                this.Leg4.rotateAngleX = walk2;
            }
        }

        this.WolfHead.rotateAngleZ = var5.getInterestedAngle(z) + var5.getShakeAngle(z, 0F);
        this.Collar.rotateAngleZ = var5.getInterestedAngle(z) + var5.getShakeAngle(z, 0F);
        this.Ear1.rotateAngleZ = var5.getInterestedAngle(z) + var5.getShakeAngle(z, 0F);
        this.Ear2.rotateAngleZ = var5.getInterestedAngle(z) + var5.getShakeAngle(z, 0F);
        this.Jaw.rotateAngleZ = var5.getInterestedAngle(z) + var5.getShakeAngle(z, 0F);
        this.Nose.rotateAngleZ = var5.getInterestedAngle(z) + var5.getShakeAngle(z, 0F);
        this.Mane.rotateAngleZ = var5.getShakeAngle(z, -0.08F);
        this.Body.rotateAngleZ = var5.getShakeAngle(z, -0.16F);
        this.Tail.rotateAngleZ = var5.getShakeAngle(z, -0.2F);
    }

    /**
     * Sets the models various rotation angles.
     */
    public void setRotationAngles(EntityHound wolf, float f1, float f2, float f3, float f4, float f5, float f6) {
        super.setRotationAngles(f1, f2, f3, f4, f5, f6, wolf);
        int neck = wolf.eatAnimation;
        if (neck < -15)
            neck = -15;

        float neckAngle = f5 + (4F * neck);
        float jawAngle = neckAngle + (1.5F * wolf.jawMove);
        this.WolfHead.rotateAngleX = neckAngle / (180F / (float) Math.PI);
        this.WolfHead.rotateAngleY = f4 / (180F / (float) Math.PI);

        this.Collar.rotateAngleX = neckAngle / (180F / (float) Math.PI);
        this.Collar.rotateAngleY = f4 / (180F / (float) Math.PI);

        this.Ear1.rotateAngleX = neckAngle / (180F / (float) Math.PI);
        this.Ear1.rotateAngleY = f4 / (180F / (float) Math.PI);

        this.Ear2.rotateAngleX = neckAngle / (180F / (float) Math.PI);
        this.Ear2.rotateAngleY = f4 / (180F / (float) Math.PI);

        this.Jaw.rotateAngleX = jawAngle / (180F / (float) Math.PI);
        this.Jaw.rotateAngleY = f4 / (180F / (float) Math.PI);

        this.Nose.rotateAngleX = neckAngle / (180F / (float) Math.PI);
        this.Nose.rotateAngleY = f4 / (180F / (float) Math.PI);

        this.Tail.rotateAngleX = f3;
    }

    private void rotateSleeping() {
        Leg1.rotationPointY = 23F;
        Leg2.rotationPointY = 23F;
        Leg3.rotationPointY = 23F;
        Leg4.rotationPointY = 23F;

        Leg1.rotationPointZ = 9F;
        Leg2.rotationPointZ = 9F;
        Leg3.rotationPointZ = -1F;
        Leg4.rotationPointZ = -1F;
        this.Leg1.rotateAngleX = -(float) (Math.PI / 2);
        this.Leg2.rotateAngleX = -(float) (Math.PI / 2);
        this.Leg3.rotateAngleX = -(float) (Math.PI / 2);
        this.Leg4.rotateAngleX = -(float) (Math.PI / 2);
        Body.rotateAngleX = 1;
        Mane.rotateAngleX = 0;
        Tail.rotateAngleX = 0;

        Body.rotationPointZ = 5;
        this.Collar.rotationPointY = 17.5F;
        this.Ear1.rotationPointY = 17.5F;
        this.Ear2.rotationPointY = 17.5F;
        this.WolfHead.rotationPointY = 17.5F;
        this.Nose.rotationPointY = 17.5F;
        this.Jaw.rotationPointY = 17.5F;

        this.WolfHead.rotateAngleX = Ear1.rotateAngleX = Ear2.rotateAngleX = Nose.rotateAngleX = Collar.rotateAngleX = Jaw.rotateAngleX = 0.5F;
        this.WolfHead.rotateAngleY = Ear1.rotateAngleY = Ear2.rotateAngleY = Nose.rotateAngleY = Collar.rotateAngleY = Jaw.rotateAngleY = 0F;
    }
}
