package minefantasy.mfr.entity.mob;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.MoverType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public abstract class EntityFlyingMF extends EntityLiving {
	public EntityFlyingMF(World world) {
		super(world);
	}

	/**
	 * Called when the mob is falling. Calculates and applies fall damage.
	 */
	protected void fall(float distance) {
	}

	/**
	 * Takes in the distance the entity has fallen this tick and whether its on the
	 * ground to update the fall distance and deal fall damage if landing on the
	 * ground. Args: distanceFallenThisTick, onGround
	 */
	protected void updateFallState(double distance, boolean hitground, IBlockState state, BlockPos pos) {
		if (isTerrestrial()) {
			super.updateFallState(distance, hitground, state, pos);
		}
	}

	/**
	 * Moves the entity based on the specified heading. Args: strafe, forward
	 */
	@Override
	public void moveRelative(float strafe, float up, float forward, float friction) {
		if (isTerrestrial()) {
			super.moveRelative(strafe, up, forward, friction);
			return;
		}
		if (this.isInWater()) {
			this.moveRelative(strafe, up, forward, 0.02F);
			this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.800000011920929D;
			this.motionY *= 0.800000011920929D;
			this.motionZ *= 0.800000011920929D;
		} else if (this.isInLava()) {
			this.moveRelative(strafe, up, forward, 0.02F);
			this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.5D;
			this.motionY *= 0.5D;
			this.motionZ *= 0.5D;
		} else {
			float f2 = 0.91F;

			if (this.onGround) {
				f2 = this.world
						.getBlockState(
								new BlockPos(MathHelper.floor(this.posX), this.posY, MathHelper.floor(this.posZ)))
						.getBlock().slipperiness * 0.91F;
			}

			float f3 = 0.16277136F / (f2 * f2 * f2);
			this.moveRelative(strafe, up, forward, this.onGround ? 0.1F * f3 : 0.02F);
			f2 = 0.91F;

			if (this.onGround) {
				f2 = this.world
						.getBlockState(new BlockPos(MathHelper.floor(this.posX),
								MathHelper.floor(this.getCollisionBoundingBox().minY) - 1, MathHelper.floor(this.posZ)))
						.getBlock().slipperiness * 0.91F;
			}

			this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
			this.motionX *= f2;
			this.motionY *= f2;
			this.motionZ *= f2;
		}

		this.prevLimbSwingAmount = this.limbSwingAmount;
		double d1 = this.posX - this.prevPosX;
		double d0 = this.posZ - this.prevPosZ;
		float f4 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;

		if (f4 > 1.0F) {
			f4 = 1.0F;
		}

		this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4F;
		this.limbSwing += this.limbSwingAmount;
	}

	/**
	 * returns true if this entity is by a ladder, false otherwise
	 */
	public boolean isOnLadder() {
		return isTerrestrial() ? super.isOnLadder() : false;
	}

	public abstract boolean isTerrestrial();
}