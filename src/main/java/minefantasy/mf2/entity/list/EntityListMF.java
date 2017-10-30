package minefantasy.mf2.entity.list;

import cpw.mods.fml.common.registry.EntityRegistry;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.config.ConfigExperiment;
import minefantasy.mf2.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;

public class EntityListMF {
    public static void register() {
        addEntity(EntityArrowMF.class, "arrowMF", 1, 16, ConfigExperiment.dynamicArrows ? 1 : 20);
        addEntity(EntityBomb.class, "bombMF", 2, 16, ConfigExperiment.dynamicArrows ? 1 : 20);
        addEntity(EntityShrapnel.class, "shrapnel_mf", 3, 16, ConfigExperiment.dynamicArrows ? 1 : 20);
        addEntity(EntityFireBlast.class, "fire_blast", 4, 16, ConfigExperiment.dynamicArrows ? 2 : 20);
        addEntity(EntitySmoke.class, "smoke_mf", 5, 16, ConfigExperiment.dynamicArrows ? 2 : 20);
        addEntity(EntityItemUnbreakable.class, "special_eitem_mf", 6, 16, ConfigExperiment.dynamicArrows ? 2 : 20);

        addEntity(EntityMine.class, "landmineMF", 7, 16, 10);
        addEntity(EntityParachute.class, "parachute_mf", 8, 16, 20);

        addEntity(EntityDragonBreath.class, "dragonbreath", 9, 16, ConfigExperiment.dynamicArrows ? 2 : 20);

        MobListMF.register();
    }

    public static int autoAssign() {
        for (int a = 0; a <= 255; a++) {
            if (!EntityList.IDtoClassMapping.containsKey(Integer.valueOf(a))) {
                System.out.println("MineFantasy: Autoassigned EntityID " + a);
                return a;
            }
        }
        throw new IllegalArgumentException(
                "MineFantasy: No Available Entity ID!, you can try manually adding them in Config/Mobs.cfg");
    }

    private static void addEntity(Class<? extends Entity> entityClass, String entityName, int id, int range,
                                  int ticks) {
        EntityRegistry.registerModEntity(entityClass, entityName, id, MineFantasyII.instance, range, ticks, true);
    }
}
