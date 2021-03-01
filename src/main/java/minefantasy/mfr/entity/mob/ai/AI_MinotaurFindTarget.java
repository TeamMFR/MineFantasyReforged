package minefantasy.mfr.entity.mob.ai;

import com.google.common.base.Predicate;
import minefantasy.mfr.entity.mob.EntityMinotaur;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AI_MinotaurFindTarget<T extends EntityLivingBase> extends EntityAITarget {
	private final Class targetClass;
	private final int targetChance;
	protected final Predicate<? super T> targetEntitySelector;
	protected final EntityAINearestAttackableTarget.Sorter sorter;
	protected T targetEntity;
	private final EntityMinotaur minotaur;

	public AI_MinotaurFindTarget(EntityMinotaur mob, Class target, int chance, boolean sight) {
		this(mob, target, chance, sight, false);
	}

	public AI_MinotaurFindTarget(EntityMinotaur mob, Class target, int chance, boolean sight, boolean nearby) {
		this(mob, target, chance, sight, nearby, null);
	}

	public AI_MinotaurFindTarget(EntityMinotaur mob, Class target, int chance, boolean sight, boolean nearby,
			@Nullable final Predicate<? super T> targetSelector) {
		super(mob, sight, nearby);
		this.minotaur = mob;
		this.targetClass = target;
		this.targetChance = chance;
		this.sorter = new EntityAINearestAttackableTarget.Sorter(mob);
		this.setMutexBits(1);
		this.targetEntitySelector = new Predicate<T>() {
			public boolean apply(@Nullable T p_apply_1_) {
				if (p_apply_1_ == null) {
					return false;
				} else if (targetSelector != null && !targetSelector.apply(p_apply_1_)) {
					return false;
				} else {
					return EntitySelectors.NOT_SPECTATING.apply(p_apply_1_) && AI_MinotaurFindTarget.this.isSuitableTarget(p_apply_1_, false);
				}
			}
		};

	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute() {
		if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0) {
			return false;
		} else {
			double d0 = this.getTargetDistance();
			if (minotaur.isDocile())// Docile are less aggro when calm
			{
				if (minotaur.getRageLevel() < 10) {
					d0 = 1.5D;
				}
			}

			List<T> list = this.taskOwner.world.<T>getEntitiesWithinAABB(this.targetClass, this.getTargetableArea(this.getTargetDistance()), this.targetEntitySelector);

			if (list.isEmpty()) {
				return false;
			} else {
				Collections.sort(list, this.sorter);
				this.targetEntity = list.get(0);
				return true;
			}
		}
	}

	protected AxisAlignedBB getTargetableArea(double targetDistance) {
		return this.taskOwner.getEntityBoundingBox().grow(targetDistance, 4.0D, targetDistance);
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting() {
		this.taskOwner.setAttackTarget(this.targetEntity);
		super.startExecuting();
	}

	public static class Sorter implements Comparator {
		private final Entity theEntity;

		public Sorter(Entity p_i1662_1_) {
			this.theEntity = p_i1662_1_;
		}

		public int compare(Entity p_compare_1_, Entity p_compare_2_) {
			double d0 = this.theEntity.getDistanceSq(p_compare_1_);
			double d1 = this.theEntity.getDistanceSq(p_compare_2_);
			return d0 < d1 ? -1 : (d0 > d1 ? 1 : 0);
		}

		public int compare(Object p_compare_1_, Object p_compare_2_) {
			return this.compare((Entity) p_compare_1_, (Entity) p_compare_2_);
		}
	}
}