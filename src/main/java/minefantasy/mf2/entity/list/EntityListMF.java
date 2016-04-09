package minefantasy.mf2.entity.list;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList.EntityEggInfo;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.config.ConfigExperiment;
import minefantasy.mf2.config.ConfigMobs;
import minefantasy.mf2.entity.EntityArrowMF;
import minefantasy.mf2.entity.EntityBomb;
import minefantasy.mf2.entity.EntityDragonBreath;
import minefantasy.mf2.entity.EntityFireBlast;
import minefantasy.mf2.entity.EntityItemUnbreakable;
import minefantasy.mf2.entity.EntityMine;
import minefantasy.mf2.entity.EntityParachute;
import minefantasy.mf2.entity.EntityShrapnel;
import minefantasy.mf2.entity.EntitySmoke;
import cpw.mods.fml.common.registry.EntityRegistry;

public class EntityListMF 
{
	public static void register()
	{
		int IDBase = ConfigMobs.entityID;
		boolean auto = IDBase == -1;
		
		addEntity(EntityArrowMF.class, "arrowMF", auto ? autoAssign() : IDBase, 16, ConfigExperiment.dynamicArrows ? 1 : 20);++IDBase;
		addEntity(EntityBomb.class, "bombMF", auto ? autoAssign() : IDBase, 16, ConfigExperiment.dynamicArrows ? 1 : 20);++IDBase;
		addEntity(EntityShrapnel.class, "shrapnel_mf", auto ? autoAssign() : IDBase, 16, ConfigExperiment.dynamicArrows ? 1 : 20);++IDBase;
		addEntity(EntityFireBlast.class, "fire_blast", auto ? autoAssign() : IDBase, 16, ConfigExperiment.dynamicArrows ? 2 : 20);++IDBase;
		addEntity(EntitySmoke.class, "smoke_mf", auto ? autoAssign() : IDBase, 16, ConfigExperiment.dynamicArrows ? 2 : 20);++IDBase;
		addEntity(EntityItemUnbreakable.class, "special_eitem_mf", auto ? autoAssign() : IDBase, 16, ConfigExperiment.dynamicArrows ? 2 : 20);++IDBase;
		
		addEntity(EntityMine.class, "landmineMF", auto ? autoAssign() : IDBase, 16, 10);++IDBase;
		addEntity(EntityParachute.class, "parachute_mf", auto ? autoAssign() : IDBase, 16, 20);++IDBase;
		
		addEntity(EntityDragonBreath.class, "dragonbreath", auto ? autoAssign() : IDBase, 16, ConfigExperiment.dynamicArrows ? 2 : 20);++IDBase;
		
		MobListMF.register(auto, IDBase);
	}
	
	public static int autoAssign()
	{
		for(int a = 0; a <= 255; a++)
		{
			if (!EntityList.IDtoClassMapping.containsKey(Integer.valueOf(a)))
	        {
				System.out.println("MineFantasy: Autoassigned EntityID " +a);
				return a;
	        }
		}
		throw new IllegalArgumentException("MineFantasy: No Available Entity ID!, you can try manually adding them in Config/Mobs.cfg");
	}
	private static void addEntity(Class<? extends Entity> entityClass, String entityName, int id, int range, int ticks)
    {
            if (MineFantasyII.isDebug())
            {
            	System.out.println("MineFantasy: register basic entity " + entityClass + " with Mod ID " + id);
            }
            EntityRegistry.registerModEntity(entityClass, entityName, id, MineFantasyII.instance, range, ticks, true);
            EntityList.addMapping(entityClass, entityName, id);
    }
}
