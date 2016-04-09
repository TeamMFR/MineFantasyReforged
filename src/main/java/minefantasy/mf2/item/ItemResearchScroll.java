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
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemResearchScroll extends ItemComponentMF
{
	private boolean isComplete;
	public ItemResearchScroll(String name, boolean complete)
	{
		super(name, 0);
		setMaxStackSize(1);
		this.isComplete = complete;
		setTextureName("minefantasy2:Other/"+name);
		this.setCreativeTab(CreativeTabMF.tabGadget);
		setHasSubtypes(true);
		setMaxDamage(0);
		
		if(!isComplete)
		{
			InformationBase.scroll = this;
		}
	}
	
	@Override
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
    }
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, EntityPlayer user, List list, boolean fullInfo)
	{
		if(item.getItemDamage() >= InformationList.knowledgeList.size())
		{
			return;
		}
		InformationBase info = InformationList.knowledgeList.get(item.getItemDamage());
		if(info != null)
		{
			list.add(info.getName());
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer user)
	{
		if(!isComplete)
		{
			return item;
		}
		boolean used = false;
		if(item.getItemDamage() >= InformationList.knowledgeList.size())
		{
			return item;
		}
		InformationBase info = InformationList.knowledgeList.get(item.getItemDamage());
		if(info != null)
		{
			if(ResearchLogic.hasInfoUnlocked(user, info))
			{
				user.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("knowledge.known")));
			}
			else if(!info.hasSkillsUnlocked(user))
			{
				user.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("skill.lowskill")));
			}
			else
			{
				used = ResearchLogic.tryUnlock(user, info);
			}
		}
		if(used)
		{
			if(user.worldObj.isRemote)
			{
				user.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("knowledge.unlocked") + ": " + StatCollector.translateToLocal(info.getName())));
			}
			user.worldObj.playSoundEffect(user.posX, user.posY, user.posZ, "minefantasy2:updateResearch", 1.0F, 1.0F);
			if(!user.capabilities.isCreativeMode)
			{
				--item.stackSize;
			}
		}
		return item;
	}
}
