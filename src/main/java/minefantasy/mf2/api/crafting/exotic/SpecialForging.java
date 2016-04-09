package minefantasy.mf2.api.crafting.exotic;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SpecialForging 
{
	public static HashMap<Item, Item>dragonforgeCrafts = new HashMap<Item, Item>();
	
	public static void addDragonforgeCraft(Block blackSteel, Block dragon)
	{
		dragonforgeCrafts.put(Item.getItemFromBlock(blackSteel), Item.getItemFromBlock(dragon));
	}
	public static void addDragonforgeCraft(Item blackSteel, Item dragon)
	{
		dragonforgeCrafts.put(blackSteel, dragon);
	}
	public static Item getDragonCraft(ItemStack blacksteel)
	{
		if(dragonforgeCrafts.containsKey(blacksteel.getItem()))
		{
			return dragonforgeCrafts.get(blacksteel.getItem());
		}
		return null;
	}
}
