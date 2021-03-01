package minefantasy.mfr.entity.mob.ai.hound;

import minefantasy.mfr.entity.mob.EntityHound;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class AI_HoundFollow extends EntityAIBase {
	World world;
	float maxDist;
	float minDist;
	private EntityHound thePet;
	private EntityLivingBase theOwner;
	private double followSpeed;
	private PathNavigate petPathfinder;
	private int timeToRecalcPath;
	private float oldWaterCost;
	private boolean field_75344_i;

	public AI_HoundFollow(EntityHound dog, double followSpeedIn, float min, float max) {
		this.thePet = dog;
		this.world = dog.world;
		this.followSpeed = followSpeedIn;
		this.petPathfinder = dog.getNavigator();
		this.minDist = min;
		this.maxDist = max;
		this.setMutexBits(3);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute() {
		EntityLivingBase entitylivingbase = this.thePet.getOwner();

		if (entitylivingbase == null) {
			return false;
		} else if (this.thePet.isSitting()) {
			return false;
		} else if (this.thePet.getDistanceSq(entitylivingbase) < this.minDist * this.minDist) {
			return false;
		} else {
			this.theOwner = entitylivingbase;
			return true;
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting() {
		return !this.petPathfinder.noPath()
				&& this.thePet.getDistanceSq(this.theOwner) > this.maxDist * this.maxDist
				&& !this.thePet.isSitting();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting() {
		this.timeToRecalcPath = 0;
		this.oldWaterCost = this.thePet.getPathPriority(PathNodeType.WATER);
		this.thePet.setPathPriority(PathNodeType.WATER, 0.0F);
	}

	/**
	 * Resets the task
	 */
	public void resetTask() {
		this.theOwner = null;
		this.petPathfinder.clearPath();
		this.thePet.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
	}

	/**
	 * Updates the task
	 */
	public void updateTask() {
		this.thePet.getLookHelper().setLookPositionWithEntity(this.theOwner, 10.0F, this.thePet.getVerticalFaceSpeed());

		if (!this.thePet.isSitting() && this.thePet.shouldFollowOwner()) {
			if (--this.timeToRecalcPath <= 0) {
				this.timeToRecalcPath = 10;

				if (!this.petPathfinder.tryMoveToEntityLiving(this.theOwner, this.followSpeed)) {
					if (!this.thePet.getLeashed()) {
						if (this.thePet.getDistanceSq(this.theOwner) >= 144.0D)// Teleport
						{
							int i = MathHelper.floor(this.theOwner.posX) - 2;
							int j = MathHelper.floor(this.theOwner.posZ) - 2;
							int k = MathHelper.floor(this.theOwner.getCollisionBoundingBox().minY);

							for (int l = 0; l <= 4; ++l) {
								for (int i1 = 0; i1 <= 4; ++i1) {
									if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && this.isTeleportFriendlyBlock(i, j, k, l, i1)) {
										this.thePet.setLocationAndAngles(i + l + 0.5F, k, j + i1 + 0.5F, this.thePet.rotationYaw, this.thePet.rotationPitch);
										this.petPathfinder.clearPath();
										return;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	protected boolean isTeleportFriendlyBlock(int x, int p_192381_2_, int y, int p_192381_4_, int p_192381_5_) {
		BlockPos blockpos = new BlockPos(x + p_192381_4_, y - 1, p_192381_2_ + p_192381_5_);
		IBlockState iblockstate = this.world.getBlockState(blockpos);
		return iblockstate.getBlockFaceShape(this.world, blockpos, EnumFacing.DOWN) == BlockFaceShape.SOLID && iblockstate.canEntitySpawn(this.thePet) && this.world.isAirBlock(blockpos.up()) && this.world.isAirBlock(blockpos.up(2));
	}
}