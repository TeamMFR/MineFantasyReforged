package minefantasy.mf2.farming;

import minefantasy.mf2.api.MineFantasyAPI;
import minefantasy.mf2.api.farming.CustomHoeEntry;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public class FarmingHelper {
    public static boolean isEnabled = true;
    public static float hoeFailChanceCfg = 1.0F;
    public static float farmBreakCfg = 1.0F;
    private static Random rand = new Random();
    private static float hoeFailChanceModifier = 1.0F;

    public static boolean didHoeFail(ItemStack hoe, World world, boolean grass) {
        if (!isEnabled) {
            return false;
        }
        float chanceMax = 30F * hoeFailChanceModifier * hoeFailChanceCfg;
        float chance = rand.nextFloat() * chanceMax;
        float efficiency = (getHoeEfficiency(hoe) * (grass ? 2.0F : 3.0F));

        MineFantasyAPI.debugMsg("Hoe Chance Fail = " + chance + " / " + efficiency + " (max= " + chanceMax + ")");
        return chance > efficiency;// Dirt is easier to hoe
    }

    public static boolean didHarvestRuinBlock(World world, boolean scythe) {
        if (!isEnabled) {
            return false;
        }
        float chance = 20F + (world.difficultySetting.getDifficultyId() * 10F);
        if (scythe)
            chance *= 2F;
        return rand.nextFloat() * 100F <= chance * farmBreakCfg;
    }

    private static float getHoeEfficiency(ItemStack hoe) {
        if (hoe != null && hoe.getItem() instanceof ItemHoe) {
                String name = ((ItemHoe) hoe.getItem()).getToolMaterialName();
                ToolMaterial mat = ToolMaterial.valueOf(name);

                if (mat != null) {
                    return CustomHoeEntry.getEntryEfficiency(hoe, mat.getEfficiencyOnProperMaterial());
                }
        }
        return CustomHoeEntry.getEntryEfficiency(hoe, 6.0F);
    }
}
