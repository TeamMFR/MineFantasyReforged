package minefantasy.mfr.entity.mob.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;

public class AI_CommonSprint extends EntityAIBase {
    public EntityLiving entity;
    public float range;

    public AI_CommonSprint(EntityLiving entity, float range) {
        this.entity = entity;
        this.range = range;
    }

    @Override
    public boolean shouldExecute() {
        return !this.entity.isChild();
    }

    @Override
    public void updateTask() {
        double distance = 0;
        Path path = this.entity.getNavigator().getPath();
        if (path != null) {
            PathPoint end = path.getFinalPathPoint();
            if (path != null) {
                distance = this.entity.getDistanceSq(end.x, end.y, end.z);
            }

        }
        boolean isSprinting = this.entity.isSprinting();
        boolean shouldSprint = distance >= this.range;
        if (isSprinting != shouldSprint) {
            this.entity.setSprinting(shouldSprint);
        }
    }

}
