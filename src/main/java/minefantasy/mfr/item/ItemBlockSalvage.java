package minefantasy.mfr.item;

import minefantasy.mfr.block.BlockSalvage;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ItemBlockSalvage extends ItemBlockBase {
	private BlockSalvage kit;

	public ItemBlockSalvage(Block base) {
		super(base);
		this.setMaxStackSize(8);
		this.kit = (BlockSalvage) base;
	}

	@Override
	public void addInformation(ItemStack item, World world, List list, ITooltipFlag flag) {
		list.add(I18n.format("attribute.kit.salvage.name") + ": " + kit.dropLevel);
		super.addInformation(item, world, list, flag);
	}

	@Override
	public EnumRarity getRarity(ItemStack item) {
		return super.getRarity(item);
	}
}
