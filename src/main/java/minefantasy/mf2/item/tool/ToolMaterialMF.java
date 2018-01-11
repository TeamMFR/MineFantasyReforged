package minefantasy.mf2.item.tool;

import minefantasy.mf2.material.BaseMaterialMF;
import net.minecraft.item.Item.ToolMaterial;

public class ToolMaterialMF {
    /*
     * WOOD(0, 59, 2.0F, 0, 15), STONE(1, 131, 4.0F, 1, 5), IRON(2, 250, 6.0F, 2,
     * 14), EMERALD(3, 1561, 8.0F, 3, 10), GOLD(0, 32, 12.0F, 0, 22);
     */
    // public static EnumToolMaterial STEEL = EnumHelper.addToolMaterial("STEEL", 2,
    // 1000, 7.8F, 2.5F, 22);

    // Misc

    // Basics stage1

    public static boolean isUnbreakable(ToolMaterial toolMaterial) {
        return toolMaterial == BaseMaterialMF.enderforge.getToolConversion()
                || toolMaterial == BaseMaterialMF.ignotumite.getToolConversion()
                || toolMaterial == BaseMaterialMF.mithium.getToolConversion();
    }
}
