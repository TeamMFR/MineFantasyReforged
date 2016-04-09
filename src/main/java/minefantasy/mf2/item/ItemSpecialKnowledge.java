package minefantasy.mf2.item;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import minefantasy.mf2.api.knowledge.InformationBase;
import minefantasy.mf2.api.knowledge.InformationList;
import minefantasy.mf2.api.knowledge.ResearchLogic;
import minefantasy.mf2.api.rpg.RPGElements;
import minefantasy.mf2.api.rpg.Skill;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.knowledge.KnowledgeListMF;
import minefantasy.mf2.util.MFLogUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemSpecialKnowledge extends ItemComponentMF
{
	private String type;
	public ItemSpecialKnowledge(String type)
	{
		super("skillbook_"+type, 0);
		setMaxStackSize(1);
		setTextureName("minefantasy2:Other/skillbook_"+type);
		this.setCreativeTab(CreativeTabMF.tabGadget);
		setHasSubtypes(true);
		setMaxDamage(0);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer user)
	{
		boolean used = false;
		
		InformationBase knowledge = this == ToolListMF.skillbook_gnomish ? KnowledgeListMF.gnomishKnowledge : KnowledgeListMF.dwarvernKnowledge;
		if(knowledge != null)
		{
			if(ResearchLogic.hasInfoUnlocked(user, knowledge))
			{
				user.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("knowledge.known")));
			}
			else
			{
				used = ResearchLogic.tryUnlock(user, knowledge);
			}
		
			if(used)
			{
				if(user.worldObj.isRemote)
				{
					user.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("knowledge.unlocked") + ": " + StatCollector.translateToLocal(knowledge.getName())));
				}
				user.worldObj.playSoundEffect(user.posX, user.posY, user.posZ, "minefantasy2:updateResearch", 1.0F, 1.0F);
			}
		}
		return item;
	}
	@Override
	public EnumRarity getRarity(ItemStack item)
	{
    	return EnumRarity.epic;
	}
}
