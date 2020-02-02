package minefantasy.mfr.block.basic;

import minefantasy.mfr.MineFantasyReborn;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockReinforcedStone extends BlockMeta {
    private String currentName;

    public BlockReinforcedStone(String name, String... alternates) {
        super(name, Material.ROCK, alternates);
        this.currentName = name;
        GameRegistry.findRegistry(Block.class).register(this);
        setRegistryName(name);
        setUnlocalizedName(MineFantasyReborn.MODID + "." + name);
    }

    public BlockReinforcedStone setBlockSoundType(SoundType soundType) {
        setSoundType(soundType);
        return this;
    }

    @Override
    public String getUnlocalisedName(int meta) {
        if (meta < names.length
                && (this.names[meta].startsWith("dshall") || this.names[meta].equalsIgnoreCase("engraved"))) {
            return "tile." + currentName + "_" + names[1];
        }
        return "tile." + currentName + "_" + names[Math.min(meta, names.length - 1)];
    }
}
