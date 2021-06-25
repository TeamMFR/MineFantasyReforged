package minefantasy.mfr.item;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.constants.Rarity;
import minefantasy.mfr.proxy.IClientRegister;
import minefantasy.mfr.util.ModelLoaderHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBaseMFR extends Item implements IClientRegister {
	private IRarity rarity = Rarity.COMMON;

	public ItemBaseMFR(String name) {
		setUnlocalizedName(name);
		setRegistryName(new ResourceLocation(MineFantasyReforged.MOD_ID, name));

		MineFantasyReforged.PROXY.addClientRegister(this);
	}

	public ItemBaseMFR(String name, IRarity rarity) {
		this(name);
		this.rarity = rarity;
	}

	@Override
	public IRarity getForgeRarity(ItemStack stack) {
		return rarity == Rarity.COMMON ? rarity : super.getForgeRarity(stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerClient() {
		ModelLoaderHelper.registerItem(this);
	}
}
