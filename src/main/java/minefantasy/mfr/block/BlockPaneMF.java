package minefantasy.mfr.block;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.proxy.IClientRegister;
import net.minecraft.block.BlockPane;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPaneMF extends BlockPane implements IClientRegister {

	public BlockPaneMF(String name, Material material, boolean recoverable) {
		super(material, recoverable);

		setRegistryName(name);
		setUnlocalizedName(name);
		MineFantasyReforged.PROXY.addClientRegister(this);
	}

	public BlockPaneMF setBlockSoundType(SoundType soundType) {
		setSoundType(soundType);
		return this;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerClient() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "normal"));
	}
}
