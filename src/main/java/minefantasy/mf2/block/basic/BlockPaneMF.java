package minefantasy.mf2.block.basic;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;

public class BlockPaneMF extends BlockPane {

    public BlockPaneMF(String name, String frontTex, String sideTex, Material material, boolean recoverable) {
        super("minefantasy2:basic/" + frontTex, "minefantasy2:basic/" + sideTex, material, recoverable);

        GameRegistry.registerBlock(this, name);
        setBlockName(name);
    }

}
