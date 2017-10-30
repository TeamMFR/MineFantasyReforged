package minefantasy.mf2.entity.mob.ai;

import minefantasy.mf2.entity.mob.EntityMinotaur;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AI_MinotaurFindTarget extends EntityAITarget {
    private final Class targetClass;
    private final int targetChance;
    private final AI_MinotaurFindTarget.Sorter theNearestAttackableTargetSorter;
    private final IEntitySelector targetEntitySelector;
    private final EntityMinotaur minotaur;
    private EntityLivingBase targetEntity;

    public AI_MinotaurFindTarget(EntityMinotaur mob, Class target, int chance, boolean sight) {
        this(mob, target, chance, sight, false);
    }

    public AI_MinotaurFindTarget(EntityMinotaur mob, Class target, int chance, boolean sight, boolean nearby) {
        this(mob, target, chance, sight, nearby, (IEntitySelector) null);
    }

    public AI_MinotaurFindTarget(EntityMinotaur mob, Class target, int chance, boolean sight, boolean nearby,
                                 final IEntitySelector selector) {
        super(mob, sight, nearby);
        this.minotaur = mob;
        this.targetClass = target;
        this.targetChance = chance;
        this.theNearestAttackableTargetSorter = new AI_MinotaurFindTarget.Sorter(mob);
        this.setMutexBits(1);
        this.targetEntitySelector = new IEntitySelector() {
            public boolean isEntityApplicable(Entity target) {
                if (target instanceof EntityMinotaur) {
                    return false;
                }
                return !(target instanceof EntityLivingBase) ? false
                        : (selector != null && !selector.isEntityApplicable(target) ? false
                        : AI_MinotaurFindTarget.this.isSuitableTarget((EntityLivingBase) target, false));
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

            List list = this.taskOwner.worldObj.selectEntitiesWithinAABB(this.targetClass,
                    this.taskOwner.boundingBox.expand(d0, 4.0D, d0), this.targetEntitySelector);
            Collections.sort(list, this.theNearestAttackableTargetSorter);

            if (list.isEmpty()) {
                return false;
            } else {
                this.targetEntity = (EntityLivingBase) list.get(0);
                return true;
            }
        }
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
            double d0 = this.theEntity.getDistanceSqToEntity(p_compare_1_);
            double d1 = this.theEntity.getDistanceSqToEntity(p_compare_2_);
            return d0 < d1 ? -1 : (d0 > d1 ? 1 : 0);
        }

        public int compare(Object p_compare_1_, Object p_compare_2_) {
            return this.compare((Entity) p_compare_1_, (Entity) p_compare_2_);
        }
    }
}