package minefantasy.mf2.item.tool.crafting;

import java.util.List;

import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.crafting.refine.PaintOilRecipe;
import minefantasy.mf2.api.helpers.ToolHelper;
import minefantasy.mf2.api.knowledge.ResearchLogic;
import minefantasy.mf2.api.rpg.SkillList;
import minefantasy.mf2.api.tier.IToolMaterial;
import minefantasy.mf2.api.tool.IToolMF;
import minefantasy.mf2.api.weapon.IDamageType;
import minefantasy.mf2.item.food.FoodListMF;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.item.tool.ToolMaterialMF;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.collect.Sets;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * @author Anonymous Productions
 */
public class ItemPaintBrush extends ItemBasicCraftTool
{
    public ItemPaintBrush(String name, int uses)
    {
    	super(name, "brush", 0, uses);
        setCreativeTab(CreativeTabMF.tabCraftTool);
        
        //setTextureName("minefantasy2:Tool/Crafting/"+name);
		//this.setUnlocalizedName(name);
		//this.setMaxDamage(uses);
		//setMaxStackSize(1);
		this.setFull3D();
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
    
    private boolean onUsedWithBlock(World world, int x, int y, int z, Block block, ItemStack item, EntityPlayer user) 
    {
    	Block newBlock = null;
    	int meta = world.getBlockMetadata(x, y, z);
    	ItemStack output = PaintOilRecipe.getPaintResult(new ItemStack(block, 1, meta));
    	if(output != null && output.getItem() instanceof ItemBlock)
    	{
    		newBlock = Block.getBlockFromItem(output.getItem());
    		if(output.getItemDamage() != OreDictionary.WILDCARD_VALUE)
    		{
    			meta = output.getItemDamage();
    		}
    	}
    	if(newBlock != null)
    	{
    		world.playAuxSFXAtEntity(user, 2001, x, y, z, Block.getIdFromBlock(newBlock) + (meta << 12));
    		
    		user.inventory.consumeInventoryItem(ComponentListMF.plant_oil);
    		ItemStack jug = new ItemStack(FoodListMF.jug_empty);
    		
    		if(!user.inventory.addItemStackToInventory(jug) && !world.isRemote)
    		{
    			user.entityDropItem(jug, 0F);
    		}
    		if(world.isRemote)return true;
    		
    		SkillList.construction.addXP(user, 1);
    		item.damageItem(1, user);
    		world.setBlock(x, y, z, newBlock);
    		world.setBlockMetadataWithNotify(x, y, z, meta, 2);
    	}
		return false;
	}
    
    @Override
    public boolean onItemUse(ItemStack item, EntityPlayer user, World world, int x, int y, int z, int side, float f, float f1, float f2)
    {
    	if(!ResearchLogic.hasInfoUnlocked(user, "paint_brush"))
    	{
    		return false;
    	}
        if (!user.canPlayerEdit(x, y, z, side, item))
        {
            return false;
        }
        else if(!user.isSwingInProgress && user.inventory.hasItem(ComponentListMF.plant_oil))
        {
            Block block = world.getBlock(x, y, z);
            return onUsedWithBlock(world, x, y, z, block, item, user);
        }
        return false;
    }
}
