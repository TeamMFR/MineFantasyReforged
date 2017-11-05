package minefantasy.mf2.integration.minetweaker.helpers;

import minefantasy.mf2.api.MineFantasyAPI;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.material.MetalMaterial;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;


@ZenExpansion("minefantasy.mf2.api.material.CustomMaterial")
public class MaterialExpansion {

    @ZenMethod("setColor")
    public static CustomMaterial setColor(CustomMaterial material, int r, int g, int b) {
        if (material != null) {
            material.setColour(r, g, b);
        }
        return material;
    }

    @ZenMethod("setMeltingPoint")
    public static CustomMaterial setMeltingPoint(CustomMaterial material, float meltingPoint) {
        if (material != null) {
            if(material instanceof MetalMaterial) {
                material.setMeltingPoint(meltingPoint);
                int[] stats = material.getHeatableStats();
                MineFantasyAPI.setHeatableStats("ingot" + material.name, stats[0], stats[1], stats[2]);
                MineFantasyAPI.setHeatableStats("hunk" + material.name, stats[0], stats[1], stats[2]);
            } else {
                throw new IllegalArgumentException("Method setMeltingPoint can be applied only to Metal materials!");
            }
        }
        return material;
    }

    @ZenMethod("setCrafterTiers")
    public static CustomMaterial setCrafterTiers(CustomMaterial material, int craftingTier) {
        if (material != null) {
            material.setCrafterTiers(craftingTier);
        }
        return material;
    }

    @ZenMethod("setRarity")
    public static CustomMaterial setRarity(CustomMaterial material, int rarity) {
        if (material != null) {
            material.setRarity(rarity);
        }
        return material;
    }

    @ZenMethod("setUnbreakable")
    public static CustomMaterial setUnbreakable(CustomMaterial material) {
        if (material != null) {
            material.setUnbreakable();
        }
        return material;
    }

    @ZenMethod("setArmourStats")
    public static CustomMaterial setArmourStats(CustomMaterial material, float cutting, float blunt, float piercing) {
        if (material != null) {
            material.setArmourStats(cutting, blunt, piercing);
        }
        return material;
    }

    @ZenMethod("modifyCraftTime")
    public static CustomMaterial modifyCraftTime(CustomMaterial material, float time) {
        if (material != null) {
            material.modifyCraftTime(time);
        }
        return material;
    }
}
