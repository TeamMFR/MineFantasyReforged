package minefantasy.mf2.entity;

import net.minecraft.entity.Entity;
import net.minecraft.util.EntityDamageSourceIndirect;

public class EntityDamageSourceBomb extends EntityDamageSourceIndirect {
    private Entity thrower;

    public EntityDamageSourceBomb(Entity bomb, Entity user) {
        super("bomb", bomb, user);
        thrower = user;
    }
}
