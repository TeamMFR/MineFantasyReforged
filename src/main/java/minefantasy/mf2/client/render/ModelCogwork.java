package minefantasy.mf2.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

public class ModelCogwork extends ModelBiped {
    ModelRenderer rightfoot;
    ModelRenderer leftfoot;

    ModelRenderer headMask;
    ModelRenderer bodyBack;
    ModelRenderer rightarmPauldron;
    ModelRenderer rightlegArmour;
    ModelRenderer leftlegArmour;
    ModelRenderer leftarmPauldron;
    ModelRenderer rightarmArmour;
    ModelRenderer leftarmArmour;
    ModelRenderer headArmour;
    ModelRenderer bodyArmour;

    // RenderCogwork
    public ModelCogwork() {
        this(1F);
    }

    public ModelCogwork(float scale) {
        this(scale, -2F * scale, 128, 64);
    }

    public ModelCogwork(float scale, float offset, int texWidth, int texHeight) {
        this.textureWidth = texWidth;
        this.textureHeight = texHeight;
        // FRAME
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, scale);
        this.bipedHead.setRotationPoint(0.0F, 0F + offset, 0.0F);

        this.bipedHeadwear = new ModelRenderer(this, 32, 0);
        this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, scale + 0.5F);
        this.bipedHeadwear.setRotationPoint(0.0F, 0F + offset, 0.0F);

        this.bipedBody = new ModelRenderer(this, 16, 16);
        this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, scale);
        this.bipedBody.setRotationPoint(0.0F, 0.0F + offset, 0.0F);

        this.bipedRightArm = new ModelRenderer(this, 40, 16);
        this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, scale);
        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F + offset, 0.0F);

        this.bipedLeftArm = new ModelRenderer(this, 40, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, scale);
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + offset, 0.0F);

        this.bipedRightLeg = new ModelRenderer(this, 0, 16);
        this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 8, 4, scale);
        this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F + offset, 0.0F);

        this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 8, 4, scale);
        this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F + offset, 0.0F);

        rightfoot = new ModelRenderer(this, 0, 53);
        rightfoot.addBox(-2F, 0F, -3F, 4, 3, 5, scale);
        rightfoot.setRotationPoint(0F, 10F, 0F);
        rightfoot.setTextureSize(128, 64);
        setRotation(rightfoot, 0F, 0F, 0F);

        leftfoot = new ModelRenderer(this, 0, 53);
        leftfoot.mirror = true;
        leftfoot.addBox(-2F, 0F, -3F, 4, 3, 5, scale);
        leftfoot.setRotationPoint(0F, 10F, 0F);
        leftfoot.setTextureSize(128, 64);

        // HEAD ARMOUR
        headMask = new ModelRenderer(this, 76, 18);
        headMask.addBox(-2F, -3.5F, -6.5F, 4, 5, 3, scale);
        headMask.setRotationPoint(0F, 0F, 0F);
        headMask.setTextureSize(128, 64);

        headArmour = new ModelRenderer(this, 78, 0);
        headArmour.addBox(-4.5F, -8.5F, -4.5F, 9, 9, 9, scale);
        headArmour.setRotationPoint(0F, 0F, 0F);
        headArmour.setTextureSize(128, 64);
        setRotation(headArmour, 0F, 0F, 0F);

        // BODY ARMOUR
        bodyBack = new ModelRenderer(this, 34, 32);
        bodyBack.addBox(-3.5F, -0.5F, 4F, 7, 9, 3, scale);
        bodyBack.setRotationPoint(0F, 0F, 0F);
        bodyBack.setTextureSize(128, 64);
        setRotation(bodyBack, 0F, 0F, 0F);

        bodyArmour = new ModelRenderer(this, 0, 32);
        bodyArmour.addBox(-4.5F, -0.5F, -4F, 9, 13, 8, scale);
        bodyArmour.setRotationPoint(0F, 0F, 0F);
        bodyArmour.setTextureSize(128, 64);
        setRotation(bodyArmour, 0F, 0F, 0F);

        // ARMS ARMOUR
        leftarmPauldron = new ModelRenderer(this, 76, 27);
        leftarmPauldron.mirror = true;
        leftarmPauldron.addBox(0F, -4F, -3F, 5, 5, 6, scale);
        leftarmPauldron.setRotationPoint(0F, 0F, 0F);
        leftarmPauldron.setTextureSize(128, 64);
        setRotation(leftarmPauldron, 0F, 0F, 0.2792527F);

        rightarmPauldron = new ModelRenderer(this, 76, 27);
        rightarmPauldron.addBox(-5F, -4F, -3F, 5, 5, 6, scale);
        rightarmPauldron.setRotationPoint(0F, 0F, 0F);
        rightarmPauldron.setTextureSize(128, 64);
        setRotation(rightarmPauldron, 0F, 0F, -0.2792527F);

        leftarmArmour = new ModelRenderer(this, 76, 38);
        leftarmArmour.mirror = true;
        leftarmArmour.addBox(-1.0F, -2.5F, -2.5F, 5, 9, 5, scale);
        leftarmArmour.setRotationPoint(0F, 0F, 0F);
        leftarmArmour.setTextureSize(128, 64);
        setRotation(leftarmArmour, 0F, -0F, 0F);

        rightarmArmour = new ModelRenderer(this, 76, 38);
        rightarmArmour.addBox(-4F, -2.5F, -2.5F, 5, 9, 5, scale);
        rightarmArmour.setRotationPoint(0F, 0F, 0F);
        rightarmArmour.setTextureSize(128, 64);
        setRotation(rightarmArmour, 0F, 0F, 0F);

        // LEGS ARMOUR
        rightlegArmour = new ModelRenderer(this, 56, 16);
        rightlegArmour.addBox(-2.5F, -0.5F, -2.5F, 5, 9, 5, scale);
        rightlegArmour.setRotationPoint(0F, 0F, 0F);
        rightlegArmour.setTextureSize(128, 64);
        setRotation(rightlegArmour, 0F, 0F, 0F);

        leftlegArmour = new ModelRenderer(this, 56, 16);
        leftlegArmour.mirror = true;
        leftlegArmour.addBox(-2.5F, -0.5F, -2.5F, 5, 9, 5, scale);
        leftlegArmour.setRotationPoint(0F, 0F, 0F);
        leftlegArmour.setTextureSize(128, 64);
        setRotation(leftlegArmour, 0F, -0F, 0F);

        // GROUPING
        this.bipedBody.addChild(bodyArmour);
        this.bipedBody.addChild(bodyBack);

        this.bipedLeftArm.addChild(leftarmPauldron);
        this.bipedLeftArm.addChild(leftarmArmour);

        this.bipedRightArm.addChild(rightarmPauldron);
        this.bipedRightArm.addChild(rightarmArmour);

        this.bipedLeftLeg.addChild(leftlegArmour);
        this.bipedRightLeg.addChild(rightlegArmour);
        this.bipedLeftLeg.addChild(leftfoot);
        this.bipedRightLeg.addChild(rightfoot);

        this.bipedHead.addChild(headArmour);
        this.bipedHeadwear.addChild(headMask);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void render(Entity entity, float f1, float f2, float f3, float f4, float f5, float scale) {
        heldItemRight = 0;
        aimedBow = false;
        boolean isEmpty = true;
        boolean isWornByPlayer = false;
        if (entity instanceof EntityPlayer) {
            isEmpty = false;
            boolean CP = Minecraft.getMinecraft().thePlayer == entity;
            isWornByPlayer = Minecraft.getMinecraft().gameSettings.thirdPersonView == 0 && CP;

            EntityPlayer player = (EntityPlayer) entity;

            ItemStack itemstack = player.getHeldItem();
            heldItemRight = itemstack == null ? 0 : 1;
            if (itemstack != null && player.getItemInUseCount() > 0) {
                EnumAction enumaction = itemstack.getItemUseAction();

                if (enumaction == EnumAction.block) {
                    heldItemRight = 3;
                } else if (enumaction == EnumAction.bow) {
                    aimedBow = true;
                }
            }
        }
        this.setRotationAngles(f1, f2, isEmpty ? 0F : f3, f4, f5, scale, entity);
        bipedHead.rotationPointY = bipedHeadwear.rotationPointY = -2F;
        this.bipedBody.rotationPointZ = this.bipedLeftLeg.rotationPointZ = this.bipedRightLeg.rotationPointZ = isWornByPlayer
                ? 6F
                : 0F;
        this.bipedLeftArm.isHidden = this.bipedRightArm.isHidden = this.leftarmArmour.isHidden = this.rightarmArmour.isHidden = isWornByPlayer;

        if (!isWornByPlayer) {
            this.bipedHead.render(scale);
            this.bipedHeadwear.render(scale);
        }
        this.bipedRightArm.render(scale);
        this.bipedLeftArm.render(scale);
        this.bipedBody.render(scale);
        this.bipedRightLeg.render(scale);
        this.bipedLeftLeg.render(scale);
    }
}
