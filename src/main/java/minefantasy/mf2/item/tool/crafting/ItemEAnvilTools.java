package minefantasy.mf2.item.tool.crafting;

import java.util.List;

import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.helpers.ToolHelper;
import minefantasy.mf2.api.tier.IToolMaterial;
import minefantasy.mf2.api.tool.IToolMF;
import minefantasy.mf2.api.weapon.IDamageType;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.item.tool.ToolMaterialMF;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

import com.google.common.collect.Sets;

import cpw.mods.fml.common.registry.GameRegistry;

/**
 * @author Anonymous Productions
 */
public class ItemEAnvilTools extends Item
{
    public ItemEAnvilTools(String name, int uses)
    {
        setCreativeTab(CreativeTabMF.tabCraftTool);
        
        setTextureName("minefantasy2:Tool/Crafting/Engineer/"+name);
		GameRegistry.registerItem(this, name, MineFantasyII.MODID);
		this.setUnlocalizedName(name);
		this.setMaxDamage(uses);
		setMaxStackSize(1);
    }
    @Override
    public ItemStack getContainerItem(ItemStack item)
    {
    	item.setItemDamage(item.getItemDamage()+1);
    	return item.getItemDamage() >= item.getMaxDamage() ? null : item;
    }
    @Override
	public int getMaxDamage(ItemStack stack)
	{
		return ToolHelper.setDuraOnQuality(stack, super.getMaxDamage());
	}
}
