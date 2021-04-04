package minefantasy.mfr.item;

import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.mechanics.knowledge.IArtefact;
import minefantasy.mfr.proxy.IClientRegister;
import minefantasy.mfr.util.ModelLoaderHelper;
import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSchematic extends ItemBlock implements IArtefact, IClientRegister {

	public final String[] researches;
	Block schematic;

	public ItemSchematic(Block schematic, String... researches) {
		super(schematic);
		this.researches = researches;
		this.schematic = schematic;

		setRegistryName(block.getRegistryName());
		MineFantasyReborn.PROXY.addClientRegister(this);
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.RARE;
	}

	@Override
	public int getStudyTime(ItemStack item) {
		return 50;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerClient() {
		ModelLoaderHelper.registerItem(this);
//		ModelLoaderHelper.registerItem(Item.getItemFromBlock(schematic));
	}
}
