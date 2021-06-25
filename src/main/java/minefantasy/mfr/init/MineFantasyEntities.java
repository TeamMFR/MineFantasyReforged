package minefantasy.mfr.init;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.config.ConfigMobs;
import minefantasy.mfr.config.ConfigSpecials;
import minefantasy.mfr.entity.EntityArrowMFR;
import minefantasy.mfr.entity.EntityBomb;
import minefantasy.mfr.entity.EntityCogwork;
import minefantasy.mfr.entity.EntityDragonBreath;
import minefantasy.mfr.entity.EntityFireBlast;
import minefantasy.mfr.entity.EntityItemUnbreakable;
import minefantasy.mfr.entity.EntityMine;
import minefantasy.mfr.entity.EntityParachute;
import minefantasy.mfr.entity.EntityShrapnel;
import minefantasy.mfr.entity.EntitySmoke;
import minefantasy.mfr.entity.mob.DragonBreath;
import minefantasy.mfr.entity.mob.EntityDragon;
import minefantasy.mfr.entity.mob.EntityHound;
import minefantasy.mfr.entity.mob.EntityMinotaur;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod.EventBusSubscriber(modid = MineFantasyReforged.MOD_ID)
public class MineFantasyEntities {
	@SubscribeEvent
	public static void register(RegistryEvent.Register<EntityEntry> event) {
		addEntity(EntityArrowMFR.class, "arrowMF", 1, 16, ConfigSpecials.dynamicArrows ? 1 : 20);
		addEntity(EntityBomb.class, "bombMF", 2, 16, ConfigSpecials.dynamicArrows ? 1 : 20);
		addEntity(EntityShrapnel.class, "shrapnel_mf", 3, 16, ConfigSpecials.dynamicArrows ? 1 : 20);
		addEntity(EntityFireBlast.class, "fire_blast", 4, 16, ConfigSpecials.dynamicArrows ? 2 : 20);
		addEntity(EntitySmoke.class, "smoke_mf", 5, 16, ConfigSpecials.dynamicArrows ? 2 : 20);
		addEntity(EntityItemUnbreakable.class, "special_eitem_mf", 6, 16, ConfigSpecials.dynamicArrows ? 2 : 20);

		addEntity(EntityMine.class, "landmineMF", 7, 16, 10);
		addEntity(EntityParachute.class, "parachute_mf", 8, 16, 20);

		addEntity(EntityDragonBreath.class, "dragonbreath", 9, 16, ConfigSpecials.dynamicArrows ? 2 : 20);

		DragonBreath.init();
		addEntity(10, EntityDragon.class, "dragon");
		addEntity(11, EntityMinotaur.class, "minotaur");
		addEntity(12, EntityCogwork.class, "cogwork");
		addEntity(13, EntityHound.class, "hound");

		addSpawn(EntityDragon.class, 1, 1, 1, EnumCreatureType.MONSTER, BiomeDictionary.Type.NETHER);
		if (ConfigMobs.minotaurSpawnrate > 0) {
			addSpawn(EntityMinotaur.class, ConfigMobs.minotaurSpawnrate, 1, 1, EnumCreatureType.MONSTER);
		}
		if (ConfigMobs.minotaurSpawnrateNether > 0) {
			addSpawn(EntityMinotaur.class, ConfigMobs.minotaurSpawnrateNether, 1, 1, EnumCreatureType.MONSTER,
					BiomeDictionary.Type.NETHER);
		}
	}

	private static void addEntity(Class<? extends Entity> entityClass, String entityName, int id, int range, int ticks) {
		ResourceLocation registryName = new ResourceLocation(MineFantasyReforged.MOD_ID, entityName);
		EntityRegistry.registerModEntity(registryName, entityClass, entityName, id, MineFantasyReforged.INSTANCE, range, ticks, true);
	}

	private static void addEntity(int IDBase, Class<? extends Entity> entityClass, String entityName) {
		ResourceLocation registryName = new ResourceLocation(MineFantasyReforged.MOD_ID, entityName);
		EntityRegistry.registerModEntity(registryName, entityClass, entityName, IDBase, MineFantasyReforged.INSTANCE, 128, 1, true);
	}

	public static void addSpawn(Class<? extends EntityLiving> entityClass, int weightedProb, int min, int max,
			EnumCreatureType typeOfCreature) {
		for (Biome biome : Biome.REGISTRY) {
			if (biome != null) {
				if (BiomeDictionary.hasAnyType(biome)) {
					if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.END)) {
						return;
					}
					if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER)) {
						return;
					}
					if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.MUSHROOM)) {
						return;
					}
				}

				EntityRegistry.addSpawn(entityClass, weightedProb, min, max, typeOfCreature, biome);
			}
		}
	}

	public static void addSpawn(Class<? extends EntityLiving> entityClass, int weightedProb, int min, int max,
			EnumCreatureType typeOfCreature, BiomeDictionary.Type type) {
		for (Biome biome : Biome.REGISTRY) {
			if (biome != null) {
				if (BiomeDictionary.hasAnyType(biome)) {
					if (BiomeDictionary.hasType(biome, type)) {
						EntityRegistry.addSpawn(entityClass, weightedProb, min, max, typeOfCreature, biome);
					}
				}
			}
		}
	}
}
