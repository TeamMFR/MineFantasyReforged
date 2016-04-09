package minefantasy.mf2.block.crafting;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemBlockAnvilMF extends ItemBlock
{
	private BlockAnvilMF anvil;
	public ItemBlockAnvilMF(Block block)
	{
		super(block);
		this.anvil = (BlockAnvilMF) block;
	}

	@Override
	public void addInformation(ItemStack item, EntityPlayer user, List list, boolean extra)
	{
		list.add(StatCollector.translateToLocal("attribute.mfcrafttier.name") + ": " + anvil.getTier());
		super.addInformation(item, user, list, extra);
	}
}
