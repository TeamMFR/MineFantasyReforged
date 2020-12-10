package minefantasy.mfr.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockReinforcedStone extends BlockMeta {
    private String currentName;

    public BlockReinforcedStone(String name, String... alternates) {
        super(name, Material.ROCK, alternates);
        this.currentName = name;

       // setRegistryName(name);
        setUnlocalizedName(name);
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
