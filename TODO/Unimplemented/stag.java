package Mob;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * stag - Undefined
 * Created using Tabula 5.1.0
 */
public class stag extends ModelBase {
    public ModelRenderer Bodyfront;
    public ModelRenderer Bodyback;
    public ModelRenderer ShoulderR;
    public ModelRenderer ShoulderL;
    public ModelRenderer Neck1;
    public ModelRenderer ThighR;
    public ModelRenderer ThighL;
    public ModelRenderer Tail1;
    public ModelRenderer ShinR;
    public ModelRenderer HeelR;
    public ModelRenderer FootR;
    public ModelRenderer shape33;
    public ModelRenderer shape34;
    public ModelRenderer ShinL;
    public ModelRenderer HeelL;
    public ModelRenderer FootL;
    public ModelRenderer shape35;
    public ModelRenderer shape36;
    public ModelRenderer UpperarmR;
    public ModelRenderer UnderarmR;
    public ModelRenderer FootfrontR;
    public ModelRenderer shape29;
    public ModelRenderer shape30;
    public ModelRenderer UpperarmL;
    public ModelRenderer UnderarmL;
    public ModelRenderer FootfrontL;
    public ModelRenderer shape31;
    public ModelRenderer shape32;
    public ModelRenderer Neck2;
    public ModelRenderer Neck3;
    public ModelRenderer Head1;
    public ModelRenderer Head2;
    public ModelRenderer Lowerjaw;
    public ModelRenderer AntlerbaseR;
    public ModelRenderer Antlerbase;
    public ModelRenderer shape73;
    public ModelRenderer shape74;
    public ModelRenderer Headtop;
    public ModelRenderer AntlerR;
    public ModelRenderer AntlerR2;
    public ModelRenderer AntlerR3;
    public ModelRenderer AntlerR7;
    public ModelRenderer AntlerR4;
    public ModelRenderer AntlerR6;
    public ModelRenderer AntlerR5;
    public ModelRenderer AntlerL;
    public ModelRenderer AntlerL3;
    public ModelRenderer AntlerL2;
    public ModelRenderer AntlerL7;
    public ModelRenderer AntlerL4;
    public ModelRenderer AntlerL6;
    public ModelRenderer AntlerL5;

    public stag() {
        this.textureWidth = 256;
        this.textureHeight = 128;
        this.AntlerR6 = new ModelRenderer(this, 70, 55);
        this.AntlerR6.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.AntlerR6.addBox(-2.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
        this.setRotateAngle(AntlerR6, 0.0F, 0.9560913642424937F, 0.0F);
        this.Lowerjaw = new ModelRenderer(this, 40, 30);
        this.Lowerjaw.setRotationPoint(0.0F, 4.0F, -4.5F);
        this.Lowerjaw.addBox(-1.0F, -1.0F, -5.0F, 2, 1, 5, 0.0F);
        this.setRotateAngle(Lowerjaw, -0.045553093477052F, 0.0F, 0.0F);
        this.Head1 = new ModelRenderer(this, 40, 60);
        this.Head1.setRotationPoint(0.0F, -0.4F, -3.5F);
        this.Head1.addBox(-1.5F, 0.0F, -5.0F, 3, 4, 5, 0.0F);
        this.setRotateAngle(Head1, 0.6373942428283291F, 0.0F, 0.0F);
        this.shape32 = new ModelRenderer(this, 70, 75);
        this.shape32.setRotationPoint(-0.8F, 0.0F, -0.3F);
        this.shape32.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.setRotateAngle(shape32, -0.18203784098300857F, -0.40980330836826856F, 0.0F);
        this.shape74 = new ModelRenderer(this, 0, 0);
        this.shape74.setRotationPoint(1.5F, -0.1F, -0.5F);
        this.shape74.addBox(-1.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(shape74, 0.31869712141416456F, 0.136659280431156F, 0.0F);
        this.AntlerR2 = new ModelRenderer(this, 70, 50);
        this.AntlerR2.setRotationPoint(-0.01F, 0.5F, 0.8F);
        this.AntlerR2.addBox(0.0F, 0.0F, -2.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(AntlerR2, -0.8196066167365371F, -0.045553093477052F, 0.0F);
        this.Bodyback = new ModelRenderer(this, 5, 75);
        this.Bodyback.setRotationPoint(0.0F, 0.1F, 12.0F);
        this.Bodyback.addBox(-4.0F, 0.0F, 0.0F, 8, 10, 11, 0.0F);
        this.setRotateAngle(Bodyback, -0.18203784098300857F, 0.0F, 0.0F);
        this.Neck1 = new ModelRenderer(this, 5, 60);
        this.Neck1.setRotationPoint(0.0F, 1.0F, 3.0F);
        this.Neck1.addBox(-2.5F, 0.0F, -5.0F, 5, 7, 5, 0.0F);
        this.setRotateAngle(Neck1, -0.40980330836826856F, 0.0F, 0.0F);
        this.Neck3 = new ModelRenderer(this, 5, 35);
        this.Neck3.setRotationPoint(0.0F, 0.0F, -4.0F);
        this.Neck3.addBox(-1.5F, 0.0F, -4.0F, 3, 4, 4, 0.0F);
        this.setRotateAngle(Neck3, 0.22759093446006054F, 0.0F, 0.0F);
        this.AntlerL5 = new ModelRenderer(this, 70, 55);
        this.AntlerL5.setRotationPoint(-0.6F, 0.0F, 0.4F);
        this.AntlerL5.addBox(-2.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
        this.setRotateAngle(AntlerL5, -0.091106186954104F, 0.36425021489121656F, 0.22759093446006054F);
        this.Head2 = new ModelRenderer(this, 40, 40);
        this.Head2.setRotationPoint(0.0F, 0.9F, -3.8F);
        this.Head2.addBox(-1.0F, 0.0F, -6.0F, 2, 2, 6, 0.0F);
        this.setRotateAngle(Head2, 0.045553093477052F, 0.0F, 0.0F);
        this.HeelL = new ModelRenderer(this, 100, 85);
        this.HeelL.setRotationPoint(-0.5F, 8.0F, 3.8F);
        this.HeelL.addBox(-2.0F, 0.0F, -2.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(HeelL, -1.0471975511965976F, 0.0F, 0.0F);
        this.ShoulderR = new ModelRenderer(this, 70, 115);
        this.ShoulderR.setRotationPoint(-5.0F, 3.5F, 2.0F);
        this.ShoulderR.addBox(0.0F, 0.0F, 0.0F, 3, 7, 5, 0.0F);
        this.setRotateAngle(ShoulderR, 0.045553093477052F, 0.0F, 0.22759093446006054F);
        this.Neck2 = new ModelRenderer(this, 5, 45);
        this.Neck2.setRotationPoint(0.0F, 0.5F, -3.5F);
        this.Neck2.addBox(-2.0F, 0.0F, -4.0F, 4, 5, 5, 0.0F);
        this.setRotateAngle(Neck2, -0.40980330836826856F, 0.0F, 0.0F);
        this.AntlerL2 = new ModelRenderer(this, 70, 50);
        this.AntlerL2.setRotationPoint(0.01F, 0.5F, 0.8F);
        this.AntlerL2.addBox(-1.0F, 0.0F, -2.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(AntlerL2, -0.8196066167365371F, 0.045553093477052F, 0.0F);
        this.HeelR = new ModelRenderer(this, 100, 85);
        this.HeelR.setRotationPoint(0.5F, 8.0F, 3.8F);
        this.HeelR.addBox(0.0F, 0.0F, -2.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(HeelR, -1.0471975511965976F, 0.0F, 0.0F);
        this.AntlerL7 = new ModelRenderer(this, 70, 55);
        this.AntlerL7.setRotationPoint(-0.4F, 0.0F, 1.5F);
        this.AntlerL7.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
        this.setRotateAngle(AntlerL7, 0.0F, -0.6373942428283291F, -0.27314402793711257F);
        this.UnderarmR = new ModelRenderer(this, 70, 90);
        this.UnderarmR.setRotationPoint(0.0F, 6.0F, -3.0F);
        this.UnderarmR.addBox(0.0F, 0.0F, 0.0F, 2, 6, 2, 0.0F);
        this.setRotateAngle(UnderarmR, -0.091106186954104F, 0.0F, 0.0F);
        this.shape33 = new ModelRenderer(this, 70, 75);
        this.shape33.setRotationPoint(0.8F, 0.0F, -0.2F);
        this.shape33.addBox(-1.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.setRotateAngle(shape33, -0.09599310885968812F, 0.36425021489121656F, 0.0F);
        this.AntlerR = new ModelRenderer(this, 70, 65);
        this.AntlerR.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.AntlerR.addBox(0.0F, 0.0F, 0.0F, 1, 1, 4, 0.0F);
        this.setRotateAngle(AntlerR, 0.4553564018453205F, -0.27314402793711257F, 0.0F);
        this.UpperarmR = new ModelRenderer(this, 70, 100);
        this.UpperarmR.setRotationPoint(0.2F, 6.0F, 4.5F);
        this.UpperarmR.addBox(0.0F, 0.0F, -3.0F, 2, 7, 3, 0.0F);
        this.setRotateAngle(UpperarmR, -0.31869712141416456F, -0.091106186954104F, -0.18203784098300857F);
        this.AntlerL3 = new ModelRenderer(this, 70, 65);
        this.AntlerL3.setRotationPoint(0.0F, 0.0F, 3.5F);
        this.AntlerL3.addBox(-1.0F, 0.0F, 0.0F, 1, 1, 4, 0.0F);
        this.setRotateAngle(AntlerL3, 0.27314402793711257F, -0.27314402793711257F, 0.0F);
        this.FootR = new ModelRenderer(this, 100, 75);
        this.FootR.setRotationPoint(0.0F, 8.0F, -2.0F);
        this.FootR.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(FootR, 0.22759093446006054F, 0.0F, 0.0F);
        this.FootfrontR = new ModelRenderer(this, 70, 80);
        this.FootfrontR.setRotationPoint(0.0F, 6.0F, 0.0F);
        this.FootfrontR.addBox(0.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(FootfrontR, 0.22759093446006054F, 0.0F, 0.0F);
        this.shape73 = new ModelRenderer(this, 0, 0);
        this.shape73.setRotationPoint(-1.5F, 0.1F, -0.5F);
        this.shape73.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2, 0.0F);
        this.setRotateAngle(shape73, 0.31869712141416456F, -0.136659280431156F, 0.0F);
        this.UnderarmL = new ModelRenderer(this, 70, 90);
        this.UnderarmL.setRotationPoint(0.0F, 6.0F, -3.0F);
        this.UnderarmL.addBox(-2.0F, 0.0F, 0.0F, 2, 6, 2, 0.0F);
        this.setRotateAngle(UnderarmL, -0.091106186954104F, 0.0F, 0.0F);
        this.shape36 = new ModelRenderer(this, 70, 75);
        this.shape36.setRotationPoint(-1.3F, 0.0F, -0.1F);
        this.shape36.addBox(-1.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.setRotateAngle(shape36, -0.18203784098300857F, 0.40980330836826856F, 0.0F);
        this.ThighL = new ModelRenderer(this, 100, 113);
        this.ThighL.setRotationPoint(4.6F, 2.0F, 3.9F);
        this.ThighL.addBox(-4.0F, 0.0F, 0.0F, 4, 8, 6, 0.0F);
        this.setRotateAngle(ThighL, 0.091106186954104F, 0.0F, -0.136659280431156F);
        this.shape31 = new ModelRenderer(this, 70, 75);
        this.shape31.setRotationPoint(-1.2F, 0.0F, -0.2F);
        this.shape31.addBox(-1.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.setRotateAngle(shape31, -0.18203784098300857F, 0.40980330836826856F, 0.0F);
        this.AntlerR4 = new ModelRenderer(this, 70, 60);
        this.AntlerR4.setRotationPoint(0.0F, 0.0F, 3.7F);
        this.AntlerR4.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(AntlerR4, 0.27314402793711257F, 0.5462880558742251F, 0.0F);
        this.shape35 = new ModelRenderer(this, 70, 75);
        this.shape35.setRotationPoint(-0.8F, 0.0F, -0.2F);
        this.shape35.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.setRotateAngle(shape35, -0.18203784098300857F, -0.36425021489121656F, 0.0F);
        this.UpperarmL = new ModelRenderer(this, 70, 100);
        this.UpperarmL.setRotationPoint(-0.2F, 6.0F, 4.5F);
        this.UpperarmL.addBox(-2.0F, 0.0F, -3.0F, 2, 7, 3, 0.0F);
        this.setRotateAngle(UpperarmL, -0.31869712141416456F, 0.091106186954104F, 0.18203784098300857F);
        this.ThighR = new ModelRenderer(this, 100, 113);
        this.ThighR.setRotationPoint(-4.6F, 2.0F, 3.9F);
        this.ThighR.addBox(0.0F, 0.0F, 0.0F, 4, 8, 6, 0.0F);
        this.setRotateAngle(ThighR, 0.091106186954104F, 0.0F, 0.136659280431156F);
        this.shape34 = new ModelRenderer(this, 70, 75);
        this.shape34.setRotationPoint(1.3F, 0.0F, -0.1F);
        this.shape34.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.setRotateAngle(shape34, -0.18203784098300857F, -0.40980330836826856F, 0.0F);
        this.ShoulderL = new ModelRenderer(this, 70, 115);
        this.ShoulderL.setRotationPoint(5.0F, 3.5F, 2.0F);
        this.ShoulderL.addBox(-3.0F, 0.0F, 0.0F, 3, 7, 5, 0.0F);
        this.setRotateAngle(ShoulderL, 0.091106186954104F, 0.0F, -0.22759093446006054F);
        this.ShinR = new ModelRenderer(this, 100, 100);
        this.ShinR.setRotationPoint(0.5F, 8.0F, 0.0F);
        this.ShinR.addBox(0.0F, 0.0F, 0.0F, 3, 8, 4, 0.0F);
        this.setRotateAngle(ShinR, 0.8196066167365371F, 0.0F, -0.136659280431156F);
        this.AntlerbaseR = new ModelRenderer(this, 70, 45);
        this.AntlerbaseR.setRotationPoint(-1.5F, 0.0F, -2.1F);
        this.AntlerbaseR.addBox(0.0F, -1.0F, 0.0F, 1, 2, 1, 0.0F);
        this.setRotateAngle(AntlerbaseR, -0.136659280431156F, -0.136659280431156F, -0.136659280431156F);
        this.shape29 = new ModelRenderer(this, 70, 75);
        this.shape29.setRotationPoint(0.8F, 0.0F, -0.3F);
        this.shape29.addBox(-1.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.setRotateAngle(shape29, -0.18203784098300857F, 0.40980330836826856F, 0.0F);
        this.shape30 = new ModelRenderer(this, 70, 75);
        this.shape30.setRotationPoint(1.2F, 0.0F, -0.2F);
        this.shape30.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.setRotateAngle(shape30, -0.18203784098300857F, -0.40980330836826856F, 0.0F);
        this.Headtop = new ModelRenderer(this, 40, 50);
        this.Headtop.setRotationPoint(0.0F, -0.9F, -1.0F);
        this.Headtop.addBox(-1.0F, 0.0F, -5.0F, 2, 1, 5, 0.0F);
        this.setRotateAngle(Headtop, 0.18203784098300857F, 0.0F, 0.0F);
        this.FootfrontL = new ModelRenderer(this, 70, 80);
        this.FootfrontL.setRotationPoint(0.0F, 6.0F, 0.0F);
        this.FootfrontL.addBox(-2.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(FootfrontL, 0.22759093446006054F, 0.0F, 0.0F);
        this.Tail1 = new ModelRenderer(this, 5, 25);
        this.Tail1.setRotationPoint(0.0F, 0.3F, 11.0F);
        this.Tail1.addBox(-1.5F, 0.0F, -2.0F, 3, 4, 2, 0.0F);
        this.setRotateAngle(Tail1, 0.31869712141416456F, 0.0F, 0.0F);
        this.ShinL = new ModelRenderer(this, 100, 100);
        this.ShinL.setRotationPoint(-0.5F, 8.0F, 0.0F);
        this.ShinL.addBox(-3.0F, 0.0F, 0.0F, 3, 8, 4, 0.0F);
        this.setRotateAngle(ShinL, 0.8196066167365371F, 0.0F, 0.136659280431156F);
        this.AntlerR7 = new ModelRenderer(this, 70, 55);
        this.AntlerR7.setRotationPoint(0.4F, 0.0F, 1.5F);
        this.AntlerR7.addBox(-2.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
        this.setRotateAngle(AntlerR7, 0.0F, 0.6373942428283291F, 0.27314402793711257F);
        this.AntlerR3 = new ModelRenderer(this, 70, 65);
        this.AntlerR3.setRotationPoint(0.0F, 0.0F, 3.5F);
        this.AntlerR3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 4, 0.0F);
        this.setRotateAngle(AntlerR3, 0.27314402793711257F, 0.27314402793711257F, 0.0F);
        this.AntlerL6 = new ModelRenderer(this, 70, 55);
        this.AntlerL6.setRotationPoint(0.0F, 0.0F, 3.0F);
        this.AntlerL6.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
        this.setRotateAngle(AntlerL6, 0.0F, -0.9560913642424937F, 0.0F);
        this.Bodyfront = new ModelRenderer(this, 5, 100);
        this.Bodyfront.setRotationPoint(0.0F, 0.0F, -10.0F);
        this.Bodyfront.addBox(-4.5F, 0.0F, 0.0F, 9, 10, 12, 0.0F);
        this.setRotateAngle(Bodyfront, 0.091106186954104F, 0.0F, 0.0F);
        this.FootL = new ModelRenderer(this, 100, 75);
        this.FootL.setRotationPoint(0.0F, 8.0F, -2.0F);
        this.FootL.addBox(-2.0F, 0.0F, 0.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(FootL, 0.22759093446006054F, 0.0F, 0.0F);
        this.Antlerbase = new ModelRenderer(this, 70, 45);
        this.Antlerbase.setRotationPoint(1.5F, 0.0F, -2.1F);
        this.Antlerbase.addBox(-1.0F, -1.0F, 0.0F, 1, 2, 1, 0.0F);
        this.setRotateAngle(Antlerbase, -0.136659280431156F, 0.136659280431156F, 0.136659280431156F);
        this.AntlerR5 = new ModelRenderer(this, 70, 55);
        this.AntlerR5.setRotationPoint(0.6F, 0.0F, 0.4F);
        this.AntlerR5.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
        this.setRotateAngle(AntlerR5, -0.091106186954104F, -0.36425021489121656F, -0.22759093446006054F);
        this.AntlerL = new ModelRenderer(this, 70, 65);
        this.AntlerL.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.AntlerL.addBox(-1.0F, 0.0F, 0.0F, 1, 1, 4, 0.0F);
        this.setRotateAngle(AntlerL, 0.4553564018453205F, 0.27314402793711257F, 0.0F);
        this.AntlerL4 = new ModelRenderer(this, 70, 60);
        this.AntlerL4.setRotationPoint(0.0F, 0.0F, 3.7F);
        this.AntlerL4.addBox(-1.0F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(AntlerL4, 0.27314402793711257F, -0.5462880558742251F, 0.0F);
        this.AntlerR3.addChild(this.AntlerR6);
        this.Head1.addChild(this.Lowerjaw);
        this.Neck3.addChild(this.Head1);
        this.FootfrontL.addChild(this.shape32);
        this.Head1.addChild(this.shape74);
        this.AntlerR.addChild(this.AntlerR2);
        this.Bodyfront.addChild(this.Bodyback);
        this.Bodyfront.addChild(this.Neck1);
        this.Neck2.addChild(this.Neck3);
        this.AntlerL4.addChild(this.AntlerL5);
        this.Head1.addChild(this.Head2);
        this.ShinL.addChild(this.HeelL);
        this.Bodyfront.addChild(this.ShoulderR);
        this.Neck1.addChild(this.Neck2);
        this.AntlerL.addChild(this.AntlerL2);
        this.ShinR.addChild(this.HeelR);
        this.AntlerL.addChild(this.AntlerL7);
        this.UpperarmR.addChild(this.UnderarmR);
        this.FootR.addChild(this.shape33);
        this.AntlerbaseR.addChild(this.AntlerR);
        this.ShoulderR.addChild(this.UpperarmR);
        this.AntlerL.addChild(this.AntlerL3);
        this.HeelR.addChild(this.FootR);
        this.UnderarmR.addChild(this.FootfrontR);
        this.Head1.addChild(this.shape73);
        this.UpperarmL.addChild(this.UnderarmL);
        this.FootL.addChild(this.shape36);
        this.Bodyback.addChild(this.ThighL);
        this.FootfrontL.addChild(this.shape31);
        this.AntlerR3.addChild(this.AntlerR4);
        this.FootL.addChild(this.shape35);
        this.ShoulderL.addChild(this.UpperarmL);
        this.Bodyback.addChild(this.ThighR);
        this.FootR.addChild(this.shape34);
        this.Bodyfront.addChild(this.ShoulderL);
        this.ThighR.addChild(this.ShinR);
        this.Head1.addChild(this.AntlerbaseR);
        this.FootfrontR.addChild(this.shape29);
        this.FootfrontR.addChild(this.shape30);
        this.Head2.addChild(this.Headtop);
        this.UnderarmL.addChild(this.FootfrontL);
        this.Bodyback.addChild(this.Tail1);
        this.ThighL.addChild(this.ShinL);
        this.AntlerR.addChild(this.AntlerR7);
        this.AntlerR.addChild(this.AntlerR3);
        this.AntlerL3.addChild(this.AntlerL6);
        this.HeelL.addChild(this.FootL);
        this.Head1.addChild(this.Antlerbase);
        this.AntlerR4.addChild(this.AntlerR5);
        this.Antlerbase.addChild(this.AntlerL);
        this.AntlerL3.addChild(this.AntlerL4);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.Bodyfront.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}