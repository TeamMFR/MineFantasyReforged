package minefantasy.mf2.entity.list;

import cpw.mods.fml.common.registry.EntityRegistry;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.config.ConfigMobs;
import minefantasy.mf2.entity.EntityCogwork;
import minefantasy.mf2.entity.mob.DragonBreath;
import minefantasy.mf2.entity.mob.EntityDragon;
import minefantasy.mf2.entity.mob.EntityHound;
import minefantasy.mf2.entity.mob.EntityMinotaur;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class MobListMF {
    public static void register() {
        DragonBreath.init();
        addEntity(10, EntityDragon.class, "MF_Dragon");
        addEntity(11, EntityMinotaur.class, "MF_Minotaur");
        addEntity(12, EntityCogwork.class, "MF_CogSuit");
        addEntity(13, EntityHound.class, "MF_Hound");

        addSpawn(EntityDragon.class, 1, 1, 1, EnumCreatureType.monster, Type.NETHER);
        if (ConfigMobs.minotaurSpawnrate > 0) {
            addSpawn(EntityMinotaur.class, ConfigMobs.minotaurSpawnrate, 1, 1, EnumCreatureType.monster);
        }
        if (ConfigMobs.minotaurSpawnrateNether > 0) {
            addSpawn(EntityMinotaur.class, ConfigMobs.minotaurSpawnrateNether, 1, 1, EnumCreatureType.monster,
                    Type.NETHER);
        }
    }

    private static void addEntity(int IDBase, Class<? extends Entity> entityClass, String entityName) {
        EntityRegistry.registerModEntity(entityClass, entityName, IDBase, MineFantasyII.instance, 128, 1, true);
    }

    public static void addSpawn(Class<? extends EntityLiving> entityClass, int weightedProb, int min, int max,
                                EnumCreatureType typeOfCreature) {
        for (BiomeGenBase biome : BiomeGenBase.getBiomeGenArray()) {
            if (biome != null) {
                if (BiomeDictionary.isBiomeRegistered(biome)) {
                    if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.END)) {
                        return;
                    }
                    if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.NETHER)) {
                        return;
                    }
                    if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.MUSHROOM)) {
                        return;
                    }
                }

                EntityRegistry.addSpawn(entityClass, weightedProb, min, max, typeOfCreature, biome);
            }
        }
    }

    public static void addSpawn(Class<? extends EntityLiving> entityClass, int weightedProb, int min, int max,
                                EnumCreatureType typeOfCreature, BiomeDictionary.Type type) {
        for (BiomeGenBase biome : BiomeGenBase.getBiomeGenArray()) {
            if (biome != null) {
                if (BiomeDictionary.isBiomeRegistered(biome)) {
                    if (BiomeDictionary.isBiomeOfType(biome, type)) {
                        EntityRegistry.addSpawn(entityClass, weightedProb, min, max, typeOfCreature, biome);
                    }
                }
            }
        }
    }
}
