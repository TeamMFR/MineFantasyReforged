package minefantasy.mfr.init;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.config.ConfigExperiment;
import minefantasy.mfr.entity.EntityArrowMFR;
import minefantasy.mfr.entity.EntityBomb;
import minefantasy.mfr.entity.EntityDragonBreath;
import minefantasy.mfr.entity.EntityFireBlast;
import minefantasy.mfr.entity.EntityItemUnbreakable;
import minefantasy.mfr.entity.EntityMine;
import minefantasy.mfr.entity.EntityParachute;
import minefantasy.mfr.entity.EntityShrapnel;
import minefantasy.mfr.entity.EntitySmoke;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod.EventBusSubscriber(modid = MineFantasyReborn.MOD_ID)
public class EntityListMF {
    @SubscribeEvent
    public static void register(RegistryEvent.Register<EntityEntry> event) {
        addEntity(EntityArrowMFR.class, "arrowMF", 1, 16, ConfigExperiment.dynamicArrows ? 1 : 20);
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

    private static void addEntity(Class<? extends Entity> entityClass, String entityName, int id, int range, int ticks) {
        ResourceLocation registryName = new ResourceLocation(MineFantasyReborn.MOD_ID, entityName);
        EntityRegistry.registerModEntity(registryName, entityClass, entityName, id, MineFantasyReborn.INSTANCE, range, ticks, true);
    }
}
