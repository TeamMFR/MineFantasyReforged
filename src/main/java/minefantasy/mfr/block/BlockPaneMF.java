package minefantasy.mfr.block;

import net.minecraft.block.BlockPane;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockPaneMF extends BlockPane {

	public BlockPaneMF(String name, Material material, boolean recoverable) {
		super(material, recoverable);

		setRegistryName(name);
		setUnlocalizedName(name);
	}

	public BlockPaneMF setBlockSoundType(SoundType soundType) {
		setSoundType(soundType);
		return this;
	}

}
