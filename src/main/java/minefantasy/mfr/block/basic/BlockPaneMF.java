package minefantasy.mfr.block.basic;

import minefantasy.mfr.MineFantasyReborn;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;

public class BlockPaneMF extends BlockPane {

    public BlockPaneMF(String name, Material material, boolean recoverable) {
        super(material, recoverable);
        GameRegistry.findRegistry(Block.class).register(this);
        setRegistryName(name);
        setUnlocalizedName(MineFantasyReborn.MODID + "." + name);
    }

    public BlockPaneMF setBlockSoundType(SoundType soundType) {
        setSoundType(soundType);
        return this;
    }

}
