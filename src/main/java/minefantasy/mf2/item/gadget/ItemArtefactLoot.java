package minefantasy.mf2.item.gadget;

import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.item.ItemComponentMF;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.item.list.ToolListMF;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;

public class ItemArtefactLoot extends ItemLootSack
{
	public ItemArtefactLoot(String name, String pool, int ammount)
	{
		super(name, ammount, 2);
		this.pool=pool;
        setTextureName("minefantasy2:artefact/box/"+name);
	}
}
