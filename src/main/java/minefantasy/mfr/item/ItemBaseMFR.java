package minefantasy.mfr.item;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.proxy.IClientRegister;
import minefantasy.mfr.util.ModelLoaderHelper;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBaseMFR extends Item implements IClientRegister {
	public ItemBaseMFR(String name) {
		setUnlocalizedName(name);
		setRegistryName(new ResourceLocation(MineFantasyReborn.MOD_ID, name));

		MineFantasyReborn.proxy.addClientRegister(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerClient() {
		ModelLoaderHelper.registerItem(this);
	}
}
