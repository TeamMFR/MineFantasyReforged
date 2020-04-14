package minefantasy.mfr.entity.mob.ai.hound;

import minefantasy.mfr.entity.mob.EntityHound;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class AI_HoundBeg extends EntityAIBase {
    private static final String __OBFID = "CL_00001576";
    private EntityHound theWolf;
    private EntityPlayer thePlayer;
    private World worldObject;
    private float minPlayerDistance;
    private int field_75384_e;

    public AI_HoundBeg(EntityHound p_i1617_1_, float p_i1617_2_) {
        this.theWolf = p_i1617_1_;
        this.worldObject = p_i1617_1_.world;
        this.minPlayerDistance = p_i1617_2_;
        this.setMutexBits(2);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        this.thePlayer = this.worldObject.getClosestPlayerToEntity(this.theWolf, this.minPlayerDistance);
        return this.thePlayer != null && this.hasPlayerGotBoneInHand(this.thePlayer);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting() {
        return this.thePlayer.isEntityAlive() && (!(this.theWolf.getDistanceSq(this.thePlayer) > this.minPlayerDistance * this.minPlayerDistance) && (this.field_75384_e > 0 && this.hasPlayerGotBoneInHand(this.thePlayer)));
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.theWolf.setBegging(true);
        this.field_75384_e = 40 + this.theWolf.getRNG().nextInt(40);
    }

    /**
     * Resets the task
     */
    public void resetTask() {
        this.theWolf.setBegging(false);
        this.thePlayer = null;
    }

    /**
     * Updates the task
     */
    public void updateTask() {
        this.theWolf.getLookHelper().setLookPosition(this.thePlayer.posX,
                this.thePlayer.posY + this.thePlayer.getEyeHeight(), this.thePlayer.posZ, 10.0F,
                this.theWolf.getVerticalFaceSpeed());
        --this.field_75384_e;
    }

    /**
     * Gets if the Player has the Bone in the hand.
     */
    private boolean hasPlayerGotBoneInHand(EntityPlayer p_75382_1_) {
        ItemStack itemstack = p_75382_1_.inventory.getCurrentItem();
        return itemstack == null ? false
                : (!this.theWolf.isTamed() && itemstack.getItem() == Items.BONE ? true
                : this.theWolf.isBreedingItem(itemstack));
    }
}