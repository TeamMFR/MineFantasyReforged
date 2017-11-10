package minefantasy.mf2.integration.minetweaker.helpers;

import minefantasy.mf2.api.MineFantasyAPI;
import minefantasy.mf2.api.material.CustomMaterial;
import minefantasy.mf2.material.MetalMaterial;
import stanhebben.zenscript.annotations.NotNull;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;


@ZenExpansion("minefantasy.mf2.api.material.CustomMaterial")
public class MaterialExpansion {

    @ZenMethod("setColor")
    public static CustomMaterial setColor(@NotNull CustomMaterial material, int r, int g, int b) {
        return material.setColour(r, g, b);
    }

    @ZenMethod("setMeltingPoint")
    public static CustomMaterial setMeltingPoint(@NotNull CustomMaterial material, float meltingPoint) {
        if (!(material instanceof MetalMaterial)) {
            throw new IllegalArgumentException("Method setMeltingPoint can be applied only to Metal materials!");
        }
        material.setMeltingPoint(meltingPoint);
        int[] stats = material.getHeatableStats();
        MineFantasyAPI.setHeatableStats("ingot" + material.name, stats[0], stats[1], stats[2]);
        MineFantasyAPI.setHeatableStats("hunk" + material.name, stats[0], stats[1], stats[2]);
        return material;
    }

    @ZenMethod("setCrafterTiers")
    public static CustomMaterial setCrafterTiers(@NotNull CustomMaterial material, int craftingTier) {
        return material.setCrafterTiers(craftingTier);

    }

    @ZenMethod("setRarity")
    public static CustomMaterial setRarity(@NotNull CustomMaterial material, int rarity) {
        return material.setRarity(rarity);
    }

    @ZenMethod("setUnbreakable")
    public static CustomMaterial setUnbreakable(@NotNull CustomMaterial material) {
        return material.setUnbreakable();

    }

    @ZenMethod("setArmourStats")
    public static CustomMaterial setArmourStats(@NotNull CustomMaterial material, float cutting, float blunt, float piercing) {
        return material.setArmourStats(cutting, blunt, piercing);
    }

    @ZenMethod("modifyCraftTime")
    public static CustomMaterial modifyCraftTime(@NotNull CustomMaterial material, float time) {
        return material.modifyCraftTime(time);
    }
}
